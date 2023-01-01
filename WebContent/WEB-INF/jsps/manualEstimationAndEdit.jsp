<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>
	$(".page-content")
			.ready(
					
					
					function() {
						
						TableEditable.init();
						FormComponents.init();
						loadSearchAndFilter('sample_1');
						var zoneVal = "${zoneVal}";
						var circleVal = "${circleVal}";
						getVeeRuleDetails();
						$('#stack4').hide();
						$("#month").val('');
						/* 	$('#viewConsumerData').on('click', function () {
							   // $dates.datepicker('setDate', null);
							    $("#month").datepicker('setDate', null);
							}); */

						if (zoneVal != '' && circleVal != '') {
							$('#zone').find('option').remove().end().append(
									'<option value="'+ zoneVal +'">' + zoneVal
											+ '</option>');
							$("#zone").val(zoneVal).trigger("change");

							setTimeout(function() {
								$('#circle').find('option').remove().end()
										.append(
												'<option value="'+ circleVal +'">'
														+ circleVal
														+ '</option>');
								$("#circle").val(circleVal).trigger("change");
							}, 200);
						} else {
							$("#zone").val("").trigger("change");
						}

						App.init();
						$('#MDMSideBarContents,#dataValidId,#manualEstimationAndEdit')
								.addClass('start active ,selected');
						$(
								"#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#360d-view,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
								.removeClass('start active ,selected');

					});

	$("#month").datepicker({
		format : "mm-yyyy",
		startView : "months",
		minViewMode : "months"
	});
</script>
<script type="text/javascript">
  var gMtrNo;
  var gfromDate;
  var gToDate;
  var gEstRuleId;
  var gEstimatedData;
	function getVeeRuleDetails() {
		$('#validationRule').find('option').remove();
		$('#validationRule').append($('<option>', {
			value : "",
			text : "Select Validation Rule"
		}));

		$.ajax({
			url : "./getVeeRuleDetails",
			type : 'POST',
			success : function(response) {
				for (var i = 0; i <= response.length; i++) {
					if(response[i].erulename !=''){
					$('#validationRule').append($('<option>', {
						value : response[i].ruleid,
						text : response[i].rulename
					}));
					}
				}
			}
		});
	}
</script>
<script>
	function getValidationData() {
		var zone = $('#zone').val();
		var circle = $('#circle').val();
		//var division = $('#division').val();
		//var sdoCode = $('#sdoCode').val();
		var town = $('#town').val();
		var ruleId = $("#validationRule").val();
		var month = $('#month').val();
       //alert(" zone="+zone+" circle="+circle+"  division="+division+" SubDiv="+sdoCode);
		if (zone == "") {
			bootbox.alert("Please Select Region");
			return false;
		}

		if (circle == "") {
			bootbox.alert("Please Select circle");
			return false;
		}

		/* if (division == "") {
			bootbox.alert("Please Select division");
			return false;
		}

		if (sdoCode == "") {
			bootbox.alert("Please Select Subdivision");
			return false;
		} */

		if (town == "") {
			bootbox.alert("Please Select Town");
			return false;
		}

		if (ruleId == "") {
			bootbox.alert("Please Select Validation Rule");
			return false;
		}

		if (month == "") {
			bootbox.alert("Please Select Month");
			return false;
		}
		$('#imageee').show();
		$
				.ajax({
					url : './getValidationData',
					type : 'POST',
					data : {
						zone : zone,
						circle : circle,
						//division : division,
						//sdoCode : sdoCode,
						ruleId : ruleId,
						month : month,
						town : town
					},
					dataType : 'json',
					success : function(response) {
                        $('#imageee').hide();
						$('#sample_1').dataTable().fnClearTable();
						$("#updateMaster").html('');
						if (response != null && response.length > 0) {
							var html = "";
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
								var j = i + 1;
								html += "<tr>" + "<td>"
										+ j
										+ "</td>"
										+ "<td>"+ month + " </td>"
										+ "<td>"+ resp.myKey.v_rule_id + " </td>"
										+ "<td>"+ resp.rulename + " </td>"
										+ "<td>"+ ((resp.subdivision == null) ? "": resp.subdivision) + " </td>"
										+ "<td>"+ ((resp.town_code == null) ? "": resp.town_code) + " </td>"
										+ "<td>"+ ((resp.location_type == null) ? "": resp.location_type) + " </td>"
										/* + "<td>"+ ((resp.location_id == null) ? "": resp.location_id) + " </td>" */
										+ "<td>"+ ((resp.location_name == null) ? "": resp.location_name) + " </td>"
										+ "<td>"+ ((resp.myKey.meter_number == null) ? "": resp.myKey.meter_number)+ " </td>"
										+
										/* "<td>"+((resp.myKey.meter_number==null)?"":resp.myKey.meter_number)+" </td>"+
											 "<td>"+((resp.meter_number==null)?"":resp.meter_number)+" </td>"+
										"<td>"+((resp.meter_number==null)?"":resp.meter_number)+" </td>"+ */
										/* "<td>"
										+ "<button  onclick='viewData(\""
										+ resp.myKey.meter_number
										+ "\",\""
										+ month
										+ "\",\""
										+ resp.myKey.v_rule_id
										+ "\")' class='btn green' data-toggle='modal'  data-target='#stack2'>View</button>"
										+ "</td>" */
									     "<td>"
										+ "<button  onclick='estimateData(\""
										+ resp.myKey.meter_number
										+ "\",\""
										+ resp.fromDate
										+ "\",\""
										+ resp.toDate
										+ "\",\""
										+ resp.myKey.v_rule_id
										+ "\")' class='btn blue' data-toggle='modal'  data-target='#stack3'>Estimate</button>"
										+ "</td>" +

										"</tr>";
							}
							$('#sample_1').dataTable().fnClearTable();
							$("#updateMaster").html(html);
							loadSearchAndFilter('sample_1');
						} else {
							bootbox.alert("No Relative Data Found.");
						}
					},
					complete : function() {
						loadSearchAndFilter('sample_1');
						// $("#month").val('');
					}
				});

	}
	function estimateData(meterNumber, fromDate, toDate, ruleId) {
		gMtrNo=meterNumber;
		gfromDate=fromDate;
	    gToDate=toDate;
	    
		 
       $('#estrule').empty();
		$.ajax({
			url : './getEstimationRules/' + ruleId,
			type : 'GET',
			dataType : 'json',
			success : function(response) {
				if(response.length !=0 || response==null ){
					/* gEstRuleId=response[0][0];
					$('#estrule').append($('<option>', {
						value : response[0][0],
						text : response[0][1]
					}));  */
				  var html='';
					html += "<select id='estrule' name='estrule' class='form-control' type='text'style='width: 240px;'><option value=''>Select Estimation Rule</option>";
				for( var i=0;i<response.length;i++)
				{
					html+="<option  value='"+response[i][0]+"'>"+response[i][1]+"</option>";
				}
				html+="</select><span></span>";
				$("#estRuleTd").html(html);
				$('#estrule').select2();
				//processData(meterNumber, fromDate, toDate, estRuleId);
			}
				else{
				     $('#stack3').modal('hide');
					bootbox.alert("No Estimation assigned");
				}
			}

		});
		
	}
	    var kwhId =1;
		var kvahId =1;
		var kwId =1;
		var kvaId =1;
	function processData(){
		$('#stack4').hide();
		//var x = document.getElementById("stack4");
		$('#estimated_data').dataTable().fnClearTable();
		//alert("hghg+"+gMtrNo+gfromDate+gToDate+gEstRuleId);
		var estRuleId =$("#estrule").val();
		//document.getElementById("ele1").style.border='0px';

		if(estRuleId==""){
			bootbox.alert("Please select Estimation Rule");
			$('#stack4').modal('hide');
			$('#stack3').modal('show');
			//$('#stack4 .modal-body').html('');
			//x.close();
		    return false;
		}
		$('#stack3').modal('hide');
		$('#esimageee').show();
		var tourl="";
		 if(!"EST01".localeCompare(estRuleId)){
			 tourl='./processAvgIpData';
		 }
		 else if(!"EST02".localeCompare(estRuleId)){
			 tourl='./processlastYearData';
		 }
		     kwhId =1;
			 kvahId =1;
			 kwId =1;
			 kvaId =1;
		 
		$.ajax({
			url : tourl,
			type : 'GET',
			data : {
				gMtrNo : gMtrNo,
				gfromDate : gfromDate,
				gToDate : gToDate
			},
			dataType : 'json',
			success : function(response) {
				//alert(response);
				$('#esimageee').hide();
				gEstimatedData=response;
				if (response != null && response.length > 0) {
					 $('#stack3').modal('hide');
					 $('#stack4').modal('show');
					 $("#view_estimate").show();
					var html = "";
					for (var i = 0; i < response.length; i++) {
						var element = response[i];
						html += "<tr>" 
						+"<td>"+ element[0]+"</td>"
						+ "<td>"+ element[1]+ "</td>"
						+ "<td>"+ element[2]+ "</td>"
						+ "<td>"+ element[3]+ "</td>"
						+ "<td>"+ element[4]+ "</td>"
						+ "<td>"+ element[5]+ "</td>"
						+ "<td>"+ element[6]+ "</td>"
						+ "<td>"+ element[7]+ "</td>"
						+ "<td>"+ element[8]+ "</td>"
						+ "<td>"+ moment(element[9]).format('DD-MM-YYYY') +"</td>" 
						/* +"<td>"+ element[10] +"</td>" */
						+"<td id='element'>"+"<input type='text' name='ele1' id='kwh"+kwhId+"' maxlength='15' value='"+parseFloat((element[11])).toFixed(3)+"' style='border:none' onchange='checkValidations()'>"+"</td>"
						/* +"<td>"+ parseFloat((element[11]/1000)).toFixed(3)+"</td>" */
						+"<td>"+"<input type='text' name='ele2' id='kvah"+kvahId+"' maxlength='15' value='"+parseFloat((element[12])).toFixed(3)+"' style='border:none' onchange='checkValidations()'>"+"</td>"
						/* +"<td>"+ parseFloat((element[12]/1000)).toFixed(3) +"</td>" */
						+"<td>"+"<input type='text' name='ele3' id='kw"+kwId+"' maxlength='15' value='"+parseFloat((element[13])).toFixed(3)+"' style='border:none' onchange='checkValidations()'>"+"</td>"
						/* +"<td>"+ parseFloat((element[13]/1000)).toFixed(3) +"</td>" */
						+"<td>"+"<input type='text' name='ele4' id='kva"+kvaId+"' maxlength='15' value='"+parseFloat((element[14])).toFixed(3)+"' style='border:none' onchange='checkValidations()'>"+"</td>";
						/* +"<td>"+ parseFloat((element[14]/1000)).toFixed(3) +"</td>"; */
					 kwhId++;
					 kvahId++;
				     kwId++;
					 kvaId++;
					}
					$('#estimated_data').dataTable().fnClearTable();
					$('#estimatedData').html(html);
					loadSearchAndFilter('estimated_data');
				}
				else{
					$('#stack3').modal('hide');
					$('#stack4').modal('hide');
					bootbox.alert("data not found");
				}
			}
		});
		//alert(meter_number+ fromDate+toDate);
	}
	var floatNo=/^[0-9]*\.?[0-9]*$/;
	/* function checkValidations(){
	 		for(var i=1;i< kwhId; i++){
			var kwh =$("#kwh"+i+"").val();
			if(kwh==""){
				bootbox.alert("Please Enter The Kwh Value");
				return false;
				}
			if(!kwh.match(floatNo)){
				bootbox.alert("Only numbers allowed in kwh");
			    return false;
			}
			}
		for(var i=1;i< kvahId; i++){
			var kvah =$("#kvah"+i+"").val();
			if(kvah==""){
				bootbox.alert("Please Enter The Kvah Value");
				return false;
				}
			if(!kvah.match(floatNo)){
				bootbox.alert("Only numbers allowed in kvah");
			    return false;
			}
			}
		for(var i=1;i< kwId; i++){
			var kw =$("#kw"+i+"").val();
			if(kw==""){
				bootbox.alert("Please Enter The Kw Value");
				return false;
				}
			if(!kw.match(floatNo)){
				bootbox.alert("Only numbers allowed in kw");
			    return false;
			}
			}
		for(var i=1;i< kvaId; i++){
			var kva =$("#kva"+i+"").val();
			if(kva==""){
				bootbox.alert("Please Enter The Kva Value");
				return false;
				}
			if(!kva.match(floatNo)){
				bootbox.alert("Only numbers allowed in kva");
			    return false;
			}
		    }
		 
		} */
	function saveData(){
		//alert("in=-=-"+gEstimatedData);
		var estimatedavgIpData = JSON.stringify(gEstimatedData);
		//alert("json=--"+estimatedavgIpData);
		//alert(gfromDate+gToDate);
		var correctData = true;
		for(var i=1;i< kwhId; i++){
			var kwh =$("#kwh"+i+"").val();
			if(kwh==""){
				correctData =false;
				bootbox.alert("Please Enter The Kwh Value");
				return false;
				}
			/* if(!kwh.match(floatNo)){
				correctData =false;
				bootbox.alert("Only numbers allowed in kwh");
			    return false;
			} */
			}
		for(var i=1;i< kvahId; i++){
			var kvah =$("#kvah"+i+"").val();
			if(kvah==""){
				correctData =false;
				bootbox.alert("Please Enter The Kvah Value");
				return false;
				}
			/* if(!kvah.match(floatNo)){
				correctData =false;
				bootbox.alert("Only numbers allowed in kvah");
			    return false;
			} */
			}
		for(var i=1;i< kwId; i++){
			var kw =$("#kw"+i+"").val();
			if(kw==""){
				correctData =false;
				bootbox.alert("Please Enter The Kw Value");
				return false;
				}
			/* if(!kw.match(floatNo)){
				correctData =false;
				bootbox.alert("Only numbers allowed in kw");
			    return false;
			} */
			}
		for(var i=1;i< kvaId; i++){
			var kva =$("#kva"+i+"").val();
			if(kva==""){
				correctData =false;
				bootbox.alert("Please Enter The Kva Value");
				return false;
				}
			/* if(!kva.match(floatNo)){
				correctData =false;
				bootbox.alert("Only numbers allowed in kva");
			    return false;
			} */
		    }
		var kwhData="";
		var kvahData ="";
		var kwData ="";
		var kvaData="";
			if(correctData){
				for(var i=1;i< kvaId; i++){
					var kwh =$("#kwh"+i+"").val();
					kwhData+=kwh+",";
					var kvah =$("#kvah"+i+"").val();
					kvahData+=kvah+",";
					var kw =$("#kw"+i+"").val();
					kwData+=kw+",";
					var kva =$("#kva"+i+"").val();
					kvaData+=kva+",";
				}
				kwhData = kwhData.substring(0, kwhData.length - 1);
				kvahData = kvahData.substring(0, kvahData.length - 1);
				kwData = kwData.substring(0, kwData.length - 1);
				kvaData = kvaData.substring(0, kvaData.length - 1);
				
			}
	 	//	alert(kwhData);
	 		$('#stack4').modal('hide');
	 		$('#imgProssPop').modal('show');
	 		$("#esimageeeForPross").show();
		 $.ajax({
			url : './saveEstimatedData',
			type : 'POST',
			data : {
				estimatedavgIpData :estimatedavgIpData,
				gfromDate : gfromDate,
				gToDate : gToDate,
				kwhData : kwhData,
				kvahData : kvahData,
				kwData : kwData,
				kvaData : kvaData
				},
			success : function(response) {
				//$('#stack4').modal('hide');
				$('#imgProssPop').modal('hide');
		 		$("#esimageeeForPross").hide();
				bootbox.alert(response);
	}
		});
	}
	function viewData(meter_number, month, ruleId) {
	//	alert(meter_number);
		$.ajax({
			url : './viewValidationMeterData',
			type : 'POST',
			data : {
				meter_number : meter_number,
				month : month,
				ruleId : ruleId
			},
			dataType : 'json',
			success : function(response) {
			//	alert(response);
				$('#sample_2').dataTable().fnClearTable();
				$('#sample_3').dataTable().fnClearTable();
				$('#sample_4').dataTable().fnClearTable();
				$('#sample_5').dataTable().fnClearTable();
				$('#sample_6').dataTable().fnClearTable();
				$('#sample_7').dataTable().fnClearTable();
				$('#sample_8').dataTable().fnClearTable();
				$('#sample_9').dataTable().fnClearTable();
				$('#sample_10').dataTable().fnClearTable();
				$('#sample_11').dataTable().fnClearTable();
				$('#sample_12').dataTable().fnClearTable();
				$('#sample_13').dataTable().fnClearTable();
				$('#sample_14').dataTable().fnClearTable();
				$('#sample_15').dataTable().fnClearTable();
				$('#sample_16').dataTable().fnClearTable();
				$('#sample_17').dataTable().fnClearTable();
				
				if (response != null && response.length > 0) {
					$('#sample_2').dataTable().fnClearTable();
					$("#updateMaster2").html('');
					$('#sample_3').dataTable().fnClearTable();
					$("#updateMaster3").html('');
					$('#sample_4').dataTable().fnClearTable();
					$("#updateMaster4").html('');
					$('#sample_5').dataTable().fnClearTable();
					$("#updateMaster5").html('');
					$('#sample_6').dataTable().fnClearTable();
					$("#updateMaster6").html('');
					$('#sample_7').dataTable().fnClearTable();
					$("#updateMaster7").html('');
					$('#sample_8').dataTable().fnClearTable();
					$("#updateMaster8").html('');
					$('#sample_9').dataTable().fnClearTable();
					$("#updateMaster9").html('');
					$('#sample_10').dataTable().fnClearTable();
					$("#updateMaster10").html('');
					$('#sample_11').dataTable().fnClearTable();
					$("#updateMaster11").html('');
					$('#sample_12').dataTable().fnClearTable();
					$("#updateMaster12").html('');
					$('#sample_13').dataTable().fnClearTable();
					$("#updateMaster13").html('');
					$('#sample_14').dataTable().fnClearTable();
					$("#updateMaster14").html('');
					$('#sample_15').dataTable().fnClearTable();
					$("#updateMaster15").html('');
					$('#sample_16').dataTable().fnClearTable();
					$("#updateMaster16").html('');
					$('#sample_17').dataTable().fnClearTable();
					$("#updateMaster17").html('');
					
					
					if (ruleId == "VEE01") {

						$("#view_2").hide();
						$("#view_3").hide();
						$("#view_4").hide();
						$("#view_5").hide();
						$("#view_6").hide();
						$("#view_7").hide();
						$("#view_8").hide();
						$("#view_9").hide();
						$("#view_10").hide();
						$("#view_11").hide();
						$("#view_12").hide();
						$("#view_13").hide();
						$("#view_14").hide();
						$("#view_15").hide();
						$("#view_1").show();

						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" + 
							"<td>" + j + "</td>" + 
							"<td>" + ruleId + " </td>" + 
							"<td>" + resp[0]+ " </td>" + 
							"<td>" + moment(resp[1]).format('DD-MM-YYYY HH:mm:ss') + " </td>" + 
							"<td>" + moment(resp[2]).format('DD-MM-YYYY HH:mm:ss') + " </td>"+ 
							"<td>" + resp[3] + " </td>"+
							"</tr>";
						}
						$('#sample_2').dataTable().fnClearTable();
						$("#updateMaster2").html(html);
						loadSearchAndFilter1('sample_2');
					}
					if (ruleId == "VEE02") {
						$("#view_1").hide();
						$("#view_3").hide();
						$("#view_4").hide();
						$("#view_5").hide();
						$("#view_6").hide();
						$("#view_7").hide();
						$("#view_8").hide();
						$("#view_9").hide();
						$("#view_10").hide();
						$("#view_11").hide();
						$("#view_12").hide();
						$("#view_13").hide();
						$("#view_14").hide();
						$("#view_15").hide();
						$("#view_2").show();
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" + 
								"<td>" + j + "</td>" + 
								"<td>" + ruleId + " </td>" + 
								"<td>" + resp[1] + " </td>" + 
								"<td>" + moment(resp[0]).format('DD-MM-YYYY') + " </td>"+ 
								"<td>" + resp[2] + " </td>" +
								 "</tr>";
						}
						$('#sample_3').dataTable().fnClearTable();
						$("#updateMaster3").html(html);
						loadSearchAndFilter1('sample_3');
					}
					if (ruleId == "VEE03") {
						$("#view_1").hide();
						$("#view_2").hide();

						$("#view_4").hide();
						$("#view_5").hide();
						$("#view_6").hide();
						$("#view_7").hide();
						$("#view_8").hide();
						$("#view_9").hide();
						$("#view_10").hide();
						$("#view_11").hide();
						$("#view_12").hide();
						$("#view_13").hide();
						$("#view_14").hide();
						$("#view_15").hide();
						$("#view_3").show();
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" + 
							"<td>" + j + "</td>" + 
							"<td>"+ ruleId + " </td>" + 
							"<td>" + resp[0]+ " </td>" + 
							"<td>" + resp[1] + " </td>"+ 
							"<td>" + resp[2] + " </td>" + //(parseFloat(resp[2]).toFixed(3))
							 "</tr>";
						}
						$('#sample_4').dataTable().fnClearTable();
						$("#updateMaster4").html(html);
						loadSearchAndFilter1('sample_4');
					}
					if (ruleId == "VEE04") {
						$("#view_1").hide();
						$("#view_2").hide();
						$("#view_3").hide();

						$("#view_5").hide();
						$("#view_6").hide();
						$("#view_7").hide();
						$("#view_8").hide();
						$("#view_9").hide();
						$("#view_10").hide();
						$("#view_11").hide();
						$("#view_12").hide();
						$("#view_13").hide();
						$("#view_14").hide();
						$("#view_15").hide();
						$("#view_4").show();
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" 
								+ "<td>" + j + "</td>" 
								+ "<td>"+ ruleId + " </td>" 
								+ "<td>" + resp[0]+ " </td>" 
								+ "<td>" + resp[1] + " </td>"
								+ "<td>" + resp[2] + " </td>" 
								+ "</tr>";
						}
						$('#sample_5').dataTable().fnClearTable();
						$("#updateMaster5").html(html);
						loadSearchAndFilter1('sample_5');
					}
					if (ruleId == "VEE05") {
						$("#view_1").hide();
						$("#view_2").hide();
						$("#view_3").hide();
						$("#view_4").hide();
						$("#view_6").hide();
						$("#view_7").hide();
						$("#view_8").hide();
						$("#view_9").hide();
						$("#view_10").hide();
						$("#view_11").hide();
						$("#view_12").hide();
						$("#view_13").hide();
						$("#view_14").hide();
						$("#view_15").hide();
						$("#view_5").show();
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" 
								    + "<td>"+ j+ "</td>"
									+ "<td>"+ ruleId+ " </td>"
									+ "<td>"+ resp.rulename+ " </td>"
									+ "<td>"+ ((resp.subdivision == null) ? "": resp.subdivision)+ " </td>"
									+ "<td>"+ ((resp.location_type == null) ? "": resp.location_type)+ " </td>"
									+ "<td>"+ ((resp.location_id == null) ? ""	: resp.location_id)+ " </td>"
									+ "<td>"+ ((resp.location_name == null) ? "": resp.location_name)+ " </td>"
									+ "<td>"+ ((resp.myKey.meter_number == null) ? "": resp.myKey.meter_number)+ " </td>" 
									+ "</tr>";
						}
						$('#sample_6').dataTable().fnClearTable();
						$("#updateMaster6").html(html);
						loadSearchAndFilter1('sample_6');
					}
					if (ruleId == "VEE06") {
						$("#view_1").hide();
						$("#view_2").hide();
						$("#view_3").hide();
						$("#view_4").hide();
						$("#view_5").hide();

						$("#view_7").hide();
						$("#view_8").hide();
						$("#view_9").hide();
						$("#view_10").hide();
						$("#view_11").hide();
						$("#view_12").hide();
						$("#view_13").hide();
						$("#view_14").hide();
						$("#view_15").hide();
						$("#view_6").show();
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" + "<td>" + j + "</td>" + "<td>"
									+ ruleId + " </td>" + "<td>" + resp[0]
									+ " </td>" + "<td>" + resp[1] + " </td>"
									+ "<td>" + resp[2] + " </td>" + "</tr>";
						}
						$('#sample_7').dataTable().fnClearTable();
						$("#updateMaster7").html(html);
						loadSearchAndFilter1('sample_7');
					}

					if (ruleId == "VEE07") {
						$("#view_1").hide();
						$("#view_2").hide();
						$("#view_3").hide();
						$("#view_4").hide();
						$("#view_5").hide();
						$("#view_6").hide();
						$("#view_8").hide();
						$("#view_9").hide();
						$("#view_10").hide();
						$("#view_11").hide();
						$("#view_12").hide();
						$("#view_13").hide();
						$("#view_14").hide();
						$("#view_15").hide();
						$("#view_7").show();
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" 
								+ "<td>" + j + "</td>" 
								+ "<td>" + ruleId + " </td>" 
								+ "<td>" + resp[0] + " </td>" 
								+ "<td>" + resp[1] + " </td>"
								+ "<td>" + resp[2] + " </td>" 
								+ "<td>"+ resp[3] + " </td>" 
								+ "</tr>";
						}
						$('#sample_8').dataTable().fnClearTable();
						$("#updateMaster8").html(html);
						loadSearchAndFilter1('sample_8');
					}

					if (ruleId == "VEE08") {
						$("#view_1").hide();
						$("#view_2").hide();
						$("#view_3").hide();
						$("#view_4").hide();
						$("#view_5").hide();
						$("#view_6").hide();
						$("#view_7").hide();
						$("#view_9").hide();
						$("#view_10").hide();
						$("#view_11").hide();
						$("#view_12").hide();
						$("#view_13").hide();
						$("#view_14").hide();
						$("#view_15").hide();
						$("#view_8").show();
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" 
								 + "<td>" + j + "</td>" 
								 + "<td>"+ ruleId + " </td>" 
								 + "<td>" + resp[0]+ " </td>" 
								 + "<td>" + resp[1] + " </td>"
								 + "<td>" + resp[2] + " </td>" 
								 + "<td>" + resp[3] + " </td>" 
								 + "</tr>";
						}
						$('#sample_9').dataTable().fnClearTable();
						$("#updateMaster9").html(html);
						loadSearchAndFilter1('sample_9');
					}
					/* if (ruleId == "VEE09") {
						$("#view_1").hide();
						$("#view_2").hide();
						$("#view_3").hide();
						$("#view_4").hide();
						$("#view_5").hide();
						$("#view_6").hide();
						$("#view_8").hide();
						$("#view_7").hide();
						$("#view_10").hide();
						$("#view_11").hide();
						$("#view_12").hide();
						$("#view_13").hide();
						$("#view_14").hide();
						$("#view_15").hide();
						$("#view_9").show();
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" + "<td>" + j + "</td>" + "<td>"
									+ ruleId + " </td>" + "<td>" + resp[0]
									+ " </td>" + "<td>" + resp[1] + " </td>"
									+ "<td>" + resp[2] + " </td>" + "<td>"
									+ resp[3] + " </td>" + "</tr>";
						}
						$('#sample_10').dataTable().fnClearTable();
						$("#updateMaster10").html(html);
						loadSearchAndFilter1('sample_10');
					} */
					/* if (ruleId == "VEE10") {
						$("#view_1").hide();
						$("#view_2").hide();
						$("#view_3").hide();
						$("#view_4").hide();
						$("#view_5").hide();
						$("#view_6").hide();
						$("#view_8").hide();
						$("#view_9").hide();
						$("#view_7").hide();
						$("#view_11").hide();
						$("#view_12").hide();
						$("#view_13").hide();
						$("#view_14").hide();
						$("#view_15").hide();
						$("#view_10").show();
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" + "<td>" + j + "</td>" + "<td>"
									+ ruleId + " </td>" + "<td>" + resp[0]
									+ " </td>" + "<td>" + resp[1] + " </td>"
									+ "<td>" + resp[2] + " </td>" + "<td>"
									+ resp[3] + " </td>" + "<td>" + resp[4]
									+ " </td>" + "</tr>";
						}
						$('#sample_11').dataTable().fnClearTable();
						$("#updateMaster11").html(html);
						loadSearchAndFilter1('sample_11');
					} */
					/* if (ruleId == "VEE11") {
						$("#view_1").hide();
						$("#view_2").hide();
						$("#view_3").hide();
						$("#view_4").hide();
						$("#view_5").hide();
						$("#view_6").hide();
						$("#view_8").hide();
						$("#view_9").hide();
						$("#view_10").hide();
						$("#view_7").hide();
						$("#view_12").hide();
						$("#view_13").hide();
						$("#view_14").hide();
						$("#view_15").hide();
						$("#view_11").show();
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" + "<td>" + j + "</td>" + "<td>"
									+ ruleId + " </td>" + "<td>" + resp[0]
									+ " </td>" + "<td>" + resp[1] + " </td>"
									+ "<td>" + resp[2] + " </td>" + "<td>"
									+ resp[3] + " </td>" + "<td>" + resp[4]
									+ " </td>" + "</tr>";
						}
						$('#sample_12').dataTable().fnClearTable();
						$("#updateMaster12").html(html);
						loadSearchAndFilter1('sample_12');
					} */
					/* if (ruleId == "VEE12") {
						$("#view_1").hide();
						$("#view_2").hide();
						$("#view_3").hide();
						$("#view_4").hide();
						$("#view_5").hide();
						$("#view_6").hide();
						$("#view_8").hide();
						$("#view_9").hide();
						$("#view_10").hide();
						$("#view_11").hide();
						$("#view_7").hide();
						$("#view_13").hide();
						$("#view_14").hide();
						$("#view_15").hide();
						$("#view_12").show();
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" 
								+ "<td>" + j + "</td>" 
								+ "<td>" + ruleId + " </td>" 
								+ "<td>" + resp[0] + " </td>" 
								+ "<td>" + resp[1] + " </td>"
								+ "<td>" + resp[2] + " </td>" 
								+ "<td>" + resp[3] + " </td>" 
								+ "<td>" + resp[4] + " </td>" 
								+ "</tr>";
						}
						$('#sample_13').dataTable().fnClearTable();
						$("#updateMaster13").html(html);
						loadSearchAndFilter1('sample_13');
					} */
					if (ruleId == "VEE13") {
						$("#view_1").hide();
						$("#view_2").hide();
						$("#view_3").hide();
						$("#view_4").hide();
						$("#view_5").hide();
						$("#view_6").hide();
						$("#view_8").hide();
						$("#view_9").hide();
						$("#view_10").hide();
						$("#view_11").hide();
						$("#view_12").hide();
						$("#view_7").hide();
						$("#view_14").hide();
						$("#view_15").hide();
						$("#view_13").show();
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" 
								+ "<td>" + j + "</td>" 
								+ "<td>"+ ruleId + " </td>" 
								+ "<td>" + resp[0]+ " </td>" 
								+ "<td>" + resp[1] + " </td>"
								+ "<td>" + resp[2] + " </td>" 
								+ "<td>" + resp[3] + " </td>"
								+ "</tr>";
						}
						$('#sample_14').dataTable().fnClearTable();
						$("#updateMaster14").html(html);
						loadSearchAndFilter1('sample_14');
					}
					if (ruleId == "VEE14") {
						$("#view_1").hide();
						$("#view_2").hide();
						$("#view_3").hide();
						$("#view_4").hide();
						$("#view_5").hide();
						$("#view_6").hide();
						$("#view_8").hide();
						$("#view_9").hide();
						$("#view_10").hide();
						$("#view_11").hide();
						$("#view_12").hide();
						$("#view_13").hide();
						$("#view_7").hide();
						$("#view_15").hide();
						$("#view_14").show();
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" 
								+ "<td>" + j + "</td>" 
								+ "<td>" + ruleId + " </td>" 
								+ "<td>" + resp[1]+ " </td>" 
								+ "<td>" + resp[0] + " </td>"
								+ "<td>" + resp[2] + " </td>"
								+ "<td>" + resp[3] + " </td>" 
								+ "<td>" + resp[4] + " </td>"
								+ "</tr>";
						}
						$('#sample_15').dataTable().fnClearTable();
						$("#updateMaster15").html(html);
						loadSearchAndFilter1('sample_15');
					}
					if (ruleId == "VEE15") {
						$("#view_1").hide();
						$("#view_2").hide();
						$("#view_3").hide();
						$("#view_4").hide();
						$("#view_5").hide();
						$("#view_6").hide();
						$("#view_8").hide();
						$("#view_9").hide();
						$("#view_10").hide();
						$("#view_11").hide();
						$("#view_12").hide();
						$("#view_13").hide();
						$("#view_14").hide();
						$("#view_7").hide();
						$("#view_15").show();
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" 
								+ "<td>" + j + "</td>" 
								+ "<td>" + ruleId + " </td>" 
								+ "<td>" + resp[0] + " </td>" 
								+ "<td>" + moment(resp[1]).format('YYYYMM') + " </td>"
								+ "<td>" + resp[2] + " </td>"
								+ "<td>" + moment(resp[3]).format('YYYYMM') + " </td>" 
								+ "<td>" + resp[4] + " </td>" 
								+ "</tr>";
						}
						$('#sample_16').dataTable().fnClearTable();
						$("#updateMaster16").html(html);
						loadSearchAndFilter1('sample_16');
					}


					if (ruleId == "VEE09") {
						$("#view_1").hide();
						$("#view_2").hide();
						$("#view_3").hide();
						$("#view_4").hide();
						$("#view_5").hide();
						$("#view_6").hide();
						$("#view_8").hide();
						$("#view_9").hide();
						$("#view_10").hide();
						$("#view_11").hide();
						$("#view_12").hide();
						$("#view_13").hide();
						$("#view_14").hide();
						$("#view_7").hide();
						$("#view_15").hide();
						$("#view_17").hide();
						$("#view_18").hide();
						$("#view_19").hide();
						$("#view_20").hide();
						$("#view_16").show();
						
						
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" 
								+ "<td>" + j + "</td>" 
								+ "<td>" + ruleId + " </td>" 
								+ "<td>" + resp[0] + " </td>" 
								+ "<td>" + moment(resp[9]).format('YYYYMM') + " </td>"
								+ "<td>" + resp[7] + " </td>"
								+ "<td>" + resp[8] + " </td>"
								+ "<td>" + resp[11].toFixed(2) + " </td>"
								+ "<td>" + (resp[12]).toFixed(2) + " </td>"
								/* + "<td>" + moment(resp[3]).format('YYYYMM') + " </td>" 
								+ "<td>" + resp[4] + " </td>"  */
								+ "</tr>";
						}
						$('#sample_17').dataTable().fnClearTable();
						$("#updateMaster17").html(html);
						loadSearchAndFilter1('sample_17');
					}




					if (ruleId == "VEE11") {
						$("#view_1").hide();
						$("#view_2").hide();
						$("#view_3").hide();
						$("#view_4").hide();
						$("#view_5").hide();
						$("#view_6").hide();
						$("#view_8").hide();
						$("#view_9").hide();
						$("#view_10").hide();
						$("#view_11").hide();
						$("#view_12").hide();
						$("#view_13").hide();
						$("#view_14").hide();
						$("#view_7").hide();
						$("#view_15").hide();
						$("#view_16").hide();
						$("#view_17").hide();
						$("#view_18").show();
						$("#view_19").hide();
						$("#view_20").hide();
						
						
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" 
								+ "<td>" + j + "</td>" 
								+ "<td>" + ruleId + " </td>" 
								+ "<td>" + resp[0] + " </td>" 
								+ "<td>" + moment(resp[9]).format('YYYYMM') + " </td>"
								+ "<td>" + resp[10] + " </td>"
								+ "<td>" + resp[11] + " </td>"
								+ "<td>" + resp[13] + " </td>"
								/* + "<td>" + moment(resp[3]).format('YYYYMM') + " </td>" 
								+ "<td>" + resp[4] + " </td>"  */
								+ "</tr>";
						}
						$('#sample_18').dataTable().fnClearTable();
						$("#updateMaster18").html(html);
						loadSearchAndFilter1('sample_18');
					}


					if (ruleId == "VEE10") {
						$("#view_1").hide();
						$("#view_2").hide();
						$("#view_3").hide();
						$("#view_4").hide();
						$("#view_5").hide();
						$("#view_6").hide();
						$("#view_8").hide();
						$("#view_9").hide();
						$("#view_10").hide();
						$("#view_11").hide();
						$("#view_12").hide();
						$("#view_13").hide();
						$("#view_14").hide();
						$("#view_7").hide();
						$("#view_15").hide();
						$("#view_16").hide();
						$("#view_17").hide();
						$("#view_18").hide();
						$("#view_20").hide();
						$("#view_19").show();
						
						
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" 
								+ "<td>" + j + "</td>" 
								+ "<td>" + ruleId + " </td>" 
								+ "<td>" + resp[0] + " </td>" 
							 + "<td>" + moment(resp[9]).format('YYYYMM') + " </td>" 
								+ "<td>" + resp[7] + " </td>"
								 + "<td>" + resp[10] + " </td>" 
								/* + "<td>" + resp[13] + " </td>" */
								/* + "<td>" + moment(resp[3]).format('YYYYMM') + " </td>" 
								+ "<td>" + resp[4] + " </td>"  */
								+ "</tr>";
						}
						$('#sample_19').dataTable().fnClearTable();
						$("#updateMaster19").html(html);
						loadSearchAndFilter1('sample_19');
					}




					if (ruleId == "VEE12") {
						$("#view_1").hide();
						$("#view_2").hide();
						$("#view_3").hide();
						$("#view_4").hide();
						$("#view_5").hide();
						$("#view_6").hide();
						$("#view_8").hide();
						$("#view_9").hide();
						$("#view_10").hide();
						$("#view_11").hide();
						$("#view_12").hide();
						$("#view_13").hide();
						$("#view_14").hide();
						$("#view_7").hide();
						$("#view_15").hide();
						$("#view_16").hide();
						$("#view_17").hide();
						$("#view_18").hide();
						$("#view_19").hide();
						$("#view_20").show();
						
						
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" 
								+ "<td>" + j + "</td>" 
								+ "<td>" + ruleId + " </td>" 
								+ "<td>" + resp[0] + " </td>" 
							 + "<td>" + moment(resp[8]).format('YYYYMM') + " </td>" 
								+ "<td>" + resp[10] + " </td>"
								 + "<td>" + resp[9] + " </td>" 
								/* + "<td>" + resp[13] + " </td>" */
								/* + "<td>" + moment(resp[3]).format('YYYYMM') + " </td>" 
								+ "<td>" + resp[4] + " </td>"  */
								+ "</tr>";
						}
						$('#sample_20').dataTable().fnClearTable();
						$("#updateMaster20").html(html);
						loadSearchAndFilter1('sample_20');
					}
					
					
				}
			},
			complete : function() {	
				if (ruleId == "VEE01") {
					$("#view_1").show();
					loadSearchAndFilter('sample_2');
				}
				if (ruleId == "VEE02") {
					$("#view_2").show();
					loadSearchAndFilter1('sample_3');
				}
				if (ruleId == "VEE03") {
					$("#view_3").show();
					loadSearchAndFilter1('sample_4');
				}
				if (ruleId == "VEE04") {
					$("#view_4").show();
					loadSearchAndFilter1('sample_5');
				}
				if (ruleId == "VEE05") {
					$("#view_5").show();
					loadSearchAndFilter1('sample_6');
				}
				if (ruleId == "VEE06") {
					$("#view_6").show();
					loadSearchAndFilter1('sample_7');
				}
				if (ruleId == "VEE07") {
					$("#view_7").show();
					loadSearchAndFilter1('sample_8');
				}
				if (ruleId == "VEE08") {
					$("#view_8").show();
					loadSearchAndFilter1('sample_9');
				}
				if (ruleId == "VEE09") {
					$("#view_16").show();
					loadSearchAndFilter1('sample_17');
				}
				if (ruleId == "VEE10") {
					$("#view_19").show();
					loadSearchAndFilter1('sample_19');
				}
				if (ruleId == "VEE11") {
					$("#view_18").show();
					loadSearchAndFilter1('sample_18');
				}
				if (ruleId == "VEE12") {
					$("#view_20").show();
					loadSearchAndFilter1('sample_20');
				}
				if (ruleId == "VEE13") {
					$("#view_13").show();
					loadSearchAndFilter1('sample_14');
				}
				if (ruleId == "VEE14") {
					$("#view_14").show();
					loadSearchAndFilter1('sample_15');
				}
				if (ruleId == "VEE15") {
					$("#view_15").show();
					loadSearchAndFilter1('sample_16');
				}
	//spike			

				if (ruleId == "VEE16") {
					$("#view_16").show();
					loadSearchAndFilter1('sample_17');
				}

				if (ruleId == "VEE18") {
					$("#view_18").show();
					loadSearchAndFilter1('sample_18');
				}


				if (ruleId == "VEE17") {
					$("#view_19").show();
					loadSearchAndFilter1('sample_19');
				}

				if (ruleId == "VEE19") {
					$("#view_20").show();

				}
				

			}
		});

	}
</script>

<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<c:if test="${results ne 'notDisplay'}">
				<c:choose>
					<c:when test="${alert_type eq 'success'}">
						<div class="alert alert-success display-show">
							<button class="close" data-close="alert"></button>
							<span style="color: green">${results}</span>
						</div>
					</c:when>
					<c:otherwise>
						<div class="alert alert-danger display-show">
							<button class="close" data-close="alert"></button>
							<span style="color: red">${results}</span>
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Manual Estimation and Edit
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">			

					<div class="row" style="margin-left: -1px;">
						<div class="col-md-3"><b>Region:</b>
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
						<div class="col-md-3"><b>Circle:</b>
							<div id="circleTd" class="form-group">
								<select class="form-control select2me" id="circle" name="circle" onchange="showTownNameandCode(this.value);">
									<option value="">Select Circle</option>
									<!-- <option value="ALL">ALL</option> -->
									<c:forEach items="${circleList}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<%-- <div class="col-md-3"><b>Division:</b>
							<div id="divisionTd" class="form-group">
								<select class="form-control select2me" id="division"
									name="division">
									<option value="">Select Division</option>
									<!-- <option value="ALL">ALL</option> -->
									<c:forEach items="${divisionList}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-md-3"><b>Sub-Division:</b>
							<div id="subdivTd" class="form-group">
								<select class="form-control select2me" id="sdoCode"
									name="sdoCode">
									<option value="">Select Sub-Division</option>
									<!-- 	<option value="ALL">ALL</option> -->
									<c:forEach items="${subdivList}" var="sdoVal">
										<option value="${sdoVal}">${sdoVal}</option>
									</c:forEach>
								</select>
							</div>
						</div> --%>
						<div class="col-md-3"><b>Town:</b>
							<div id="townId" class="form-group">
								<select class="form-control select2me" name="town" id="town">
									<option value="">Select Town</option>
<!-- 									<option value="%">ALL</option>
 -->									<c:forEach var="elements" items="${townList}">
									<option value="${elements}">${elements}</option>
									</c:forEach>
							</select>
							</div>
						</div>
					</div>
					<div class="row" style="margin-left: -1px;">

						
						<div class="col-md-3"><b>Validation&nbsp;Rule&nbsp;Name:</b>
							<div id="" class="form-group">
								<select class="form-control select2me" id="validationRule"
									name="validationRule">
									<option value="">Select Validation Rule</option>
								</select>
							</div>
						</div>

						<div class="col-md-3"><b>Select&nbsp;Month:</b>
							<div class="input-group ">
								<input type="text" class="form-control from" id="month"
									name="month" placeholder="Select Month" style="cursor: pointer">
								<span class="input-group-btn">
									<button class="btn default" type="button">
										<i class="fa fa-calendar"></i>
									</button>
								</span>
							</div>

						</div>
					</div>
					<!--BEGIN TABS-->
					<div class="tabbable tabbable-custom">
						<div id="showConsumer">
							<button type="button" id="viewConsumerData"
								style="margin-left: 480px;" onclick="getValidationData()"
								name="viewConsumerData" class="btn yellow">
								<b>View</b>
							</button>
							<br />
							
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
											<li><a href="#" id="excelExport"
												onclick="tableToExcel('sample_1', 'ValidatioReport')">Export
													to Excel</a></li>
										</ul>
									</div>
								</div>
							<table class="table table-striped table-hover table-bordered"
								id="sample_1">
								<thead>
									<tr>
										<th>Sl No.</th>
										<th>MonthYear</th>
										<th>Rule Id</th>
										<th>Rule Name</th>
										<th>Sub Division</th>
										<th>TownCode</th>
										<th>Location Type</th>
										<!-- <th>Location Code</th> -->
										<th>Location Name</th>
										<th>Meter No</th>
										<!-- <th>View</th> -->
										<th>Estimate</th>
										<!--<th>Edit</th> -->
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

	</div>

	<div id="stack2" class="modal fade" role="dialog"
		aria-labelledby="myModalLabel10" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div id="view_1" style="display: none">
					
						<div class="table-toolbar">
								<div class="btn-group pull-right" >
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li>
										<a href="#" id="excelExport" onclick="tableToXlxs('sample_2', 'RTCfailureReports')">Export to Excel</a>
										</li>
									</ul>
								</div>
							</div>
							
						<table class="table table-striped table-hover table-bordered"
							id="sample_2">
							<thead>
								<tr>
									<th>Sl No.</th>
									<th>Rule Id</th>
									<th>Meter No</th>
									<th>Meter Reading Date</th>
									<th>Meter RTC</th>
									<th>Diff(In Mins)</th>
								</tr>
							</thead>
							<tbody id="updateMaster2">
							</tbody>
						</table>
					</div>
					<div id="view_2" style="display: none">
						
						<div class="table-toolbar">
								<div class="btn-group pull-right" >
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<!-- <li><a href="#" id="print">Print</a></li> -->
										<li>
										<a href="#" id="excelExport" onclick="tableToXlxs('sample_3', 'MissingLoadDataReports')">Export to Excel</a>
										</li>
									</ul>
								</div>
							</div>
						<table class="table table-striped table-hover table-bordered"
							id="sample_3">
							<thead>
								<tr>
									<th>Sl No.</th>
									<th>Rule Id</th>
									<th>Meter No</th>
									<th>Date</th>
									<th>Count</th>
								</tr>
							</thead>
							<tbody id="updateMaster3">
							</tbody>
						</table>
					</div>
					<div id="view_3" style="display: none">
						
						<div class="table-toolbar">
								<div class="btn-group pull-right" >
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<!-- <li><a href="#" id="print">Print</a></li> -->
										<li>
										<a href="#" id="excelExport" onclick="tableToXlxs('sample_4', 'IncompleteLoadDataReports')">Export to Excel</a>
										</li>
									</ul>
								</div>
							</div>
						<table class="table table-striped table-hover table-bordered"
							id="sample_4">
							<thead>
								<tr>
									<th>Sl No.</th>
									<th>Rule Id</th>
									<th>Meter No</th>
									<th>Date</th>
									<th>Available Count</th>
								</tr>
							</thead>
							<tbody id="updateMaster4">
							</tbody>
						</table>
					</div>
					<div id="view_4" style="display: none">
						
						<div class="table-toolbar">
								<div class="btn-group pull-right" >
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li>
										<a href="#" id="excelExport" onclick="tableToXlxs('sample_5', 'MissingEventReports')">Export to Excel</a>
										</li>
									</ul>
								</div>
							</div>
						<table class="table table-striped table-hover table-bordered"
							id="sample_5">
							<thead>
								<tr>
									<th>Sl No.</th>
									<th>Rule Id</th>
									<th>Meter No</th>
									<th>Date</th>
									<th>Count</th>
								</tr>
							</thead>
							<tbody id="updateMaster5">
							</tbody>
						</table>
					</div>
					<div id="view_5" style="display: none">
						<table class="table table-striped table-hover table-bordered"
							id="sample_6">
							<thead>
								<tr>
									<th>Sl No.</th>
									<th>Rule Id</th>
									<th>Meter No</th>
									<th>Diff</th>
								</tr>
							</thead>
							<tbody id="updateMaster6">
							</tbody>
						</table>
					</div>
					<div id="view_6" style="display: none">
					
						<div class="table-toolbar">
								<div class="btn-group pull-right" >
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li>
										<a href="#" id="excelExport" onclick="tableToXlxs('sample_7', 'InvalidPowerFactorReports')">Export to Excel</a>
										</li>
									</ul>
								</div>
							</div>
							
						<table class="table table-striped table-hover table-bordered"
							id="sample_7">
							<thead>
								<tr>
									<th>Sl No.</th>
									<th>Rule Id</th>
									<th>Meter No</th>
									<th>Date</th>
									<th>PowerFactor</th>
								</tr>
							</thead>
							<tbody id="updateMaster7">
							</tbody>
						</table>
					</div>
					<div id="view_7" style="display: none">
						
						<div class="table-toolbar">
								<div class="btn-group pull-right" >
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li>
										<a href="#" id="excelExport" onclick="tableToXlxs('sample_8', 'LessKVAReports')">Export to Excel</a>
										</li>
									</ul>
								</div>
							</div>
						<table class="table table-striped table-hover table-bordered"
							id="sample_8">
							<thead>
								<tr>
									<th>Sl No.</th>
									<th>Rule Id</th>
									<th>Meter No</th>
									<th>Date</th>
									<th>KVA</th>
									<th>DemandKW</th>
								</tr>
							</thead>
							<tbody id="updateMaster8">
							</tbody>
						</table>
					</div>
					<div id="view_8" style="display: none">
					
						<div class="table-toolbar">
								<div class="btn-group pull-right" >
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li>
										<a href="#" id="excelExport" onclick="tableToXlxs('sample_9', 'ThresoldFreqReports')">Export to Excel</a>
										</li>
									</ul>
								</div>
						</div>
						<table class="table table-striped table-hover table-bordered"
							id="sample_9">
							<thead>
								<tr>
									<th>Sl No.</th>
									<th>Rule Id</th>
									<th>Meter No</th>
									<th>Date</th>	
									<th>Actual Freq</th>
									<th>Freq Range</th>
								</tr>
							</thead>
							<tbody id="updateMaster9">
							</tbody>
						</table>
					</div>
					<div id="view_9" style="display: none">
						<table class="table table-striped table-hover table-bordered"
							id="sample_10">
							<thead>
								<tr>
									<th>Sl No.</th>
									<th>Rule Id</th>
									<th>Meter No</th>
									<th>BillMonth</th>
									<th>Kwh</th>
									<th>PreMonth</th>
									<th>PreKwh</th>
								</tr>
							</thead>
							<tbody id="updateMaster10">
							</tbody>
						</table>
					</div>
					<div id="view_10" style="display: none">
						<table class="table table-striped table-hover table-bordered"
							id="sample_11">
							<thead>
								<tr>
									<th>Sl No.</th>
									<th>Rule Id</th>
									<th>Meter No</th>
									<th>BillMonth</th>
									<th>Kwh</th>
									<th>PreMonth</th>
									<th>PreKwh</th>

								</tr>
							</thead>
							<tbody id="updateMaster11">
							</tbody>
						</table>
					</div>
					<div id="view_11" style="display: none">
						<table class="table table-striped table-hover table-bordered"
							id="sample_12">
							<thead>
								<tr>
									<th>Sl No.</th>
									<th>Rule Id</th>
									<th>Meter No</th>
									<th>BillMonth</th>
									<th>Kwh</th>
									<th>PreMonth</th>
									<th>PreKwh</th>

								</tr>
							</thead>
							<tbody id="updateMaster12">
							</tbody>
						</table>
					</div>
					<div id="view_12" style="display: none">
						<table class="table table-striped table-hover table-bordered"
							id="sample_13">
							<thead>
								<tr>
									<th>Sl No.</th>
									<th>Rule Id</th>
									<th>Meter No</th>
									<th>BillMonth</th>
									<th>Kwh</th>
									<th>PreMonth</th>
									<th>PreKwh</th>


								</tr>
							</thead>
							<tbody id="updateMaster13">
							</tbody>
						</table>
					</div>
					<div id="view_13" style="display: none">
						<table class="table table-striped table-hover table-bordered"
							id="sample_14">
							<thead>
								<tr>
									<th>Sl No.</th>
									<th>Rule Id</th>
									<th>Meter No</th>
									<th>PrevMonth</th>
									<th>PrevMonth Kwh</th>
									<th>CurrentMonth Kwh</th>

								</tr>
							</thead>
							<tbody id="updateMaster14">
							</tbody>
						</table>
					</div>
					<div id="view_14" style="display: none">
						<table class="table table-striped table-hover table-bordered"
							id="sample_15">
							<thead>
								<tr>
									<th>Sl No.</th>
									<th>Rule Id</th>
									<th>Meter No</th>
									<th>BillMonth</th>
									<th>Bill Kwh</th>
									<th>Load Kwh</th>
									<th>Diff</th>
								</tr>
							</thead>
							<tbody id="updateMaster15">
							</tbody>
						</table>
					</div>

					<div id="view_15" style="display: none">
						<table class="table table-striped table-hover table-bordered"
							id="sample_16">
							<thead>
								<tr>
									<th>Sl No.</th>
									<th>Rule Id</th>
									<th>Meter No</th>
									<th>CurrentMonth</th>
									<th>CurrentMonthKwh</th>
									<th>PreMonth</th>
									<th>PreMonthKwh</th>
								</tr>
							</thead>
							<tbody id="updateMaster16">
							</tbody>
						</table>
					</div>



<div id="view_16" style="display: none">
						<table class="table table-striped table-hover table-bordered"
							id="sample_17">
							<thead>
								<tr>
									<th>Sl No.</th>
									<th>Rule Id</th>
									<th>Meter No</th>
									<th>Month</th>
									<th>Previous Kwh</th>
									<th>Current  Kwh</th>
									<th>Spike Difference</th>
									<th>Spike % Difference</th>
									
									<!-- <th>PreMonth</th>
									<th>PreMonthKwh</th> -->
								</tr>
							</thead>
							<tbody id="updateMaster17">
							</tbody>
						</table>
					</div>
					
					<div id="view_18" style="display: none">
						<table class="table table-striped table-hover table-bordered"
							id="sample_18">
							<thead>
								<tr>
									<th>Sl No.</th>
									<th>Rule Id</th>
									<th>Meter No</th>
									<th>Month</th>
									<th>Actual Consumption</th>
									<th>Consumption</th>
									<th>Location Type</th>
									<!-- <th>PreMonth</th>
									<th>PreMonthKwh</th> -->
								</tr>
							</thead>
							<tbody id="updateMaster18">
							</tbody>
						</table>
					</div>
					
					
					<div id="view_19" style="display: none">
						<table class="table table-striped table-hover table-bordered"
							id="sample_19">
							<thead>
								<tr>
									<th>Sl No.</th>
									<th>Rule Id</th>
									<th>Meter No</th>
									<th>Month</th>
									<!-- <th>Flag</th> -->
									 <th>Town Code</th> 
									<th>Location Type</th>
									<!-- <th>PreMonth</th>
									<th>PreMonthKwh</th> -->
								</tr>
							</thead>
							<tbody id="updateMaster19">
							</tbody>
						</table>
					</div>
					
					<div id="view_20" style="display: none">
						<table class="table table-striped table-hover table-bordered"
							id="sample_20">
							<thead>
								<tr>
									<th>Sl No.</th>
									<th>Rule Id</th>
									<th>Meter No</th>
									<th>Month</th>
									<th>Location Type</th>
								 <th>Kwh</th>
									
								</tr>
							</thead>
							<tbody id="updateMaster20">
							</tbody>
						</table>
					</div>
					


					
				</div>
				<div class="modal-footer">
						<button class="btn red pull-right" type="button"
							data-dismiss="modal" onclick="formreset()">Cancel</button>
				</div>
			</div>
		</div>
	</div>

	<div id="stack3" class="modal fade" role="dialog"
		aria-labelledby="myModalLabel10" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">

					<!-- <h5>select</h5> -->
					<table style="width: 38%"
						id="est_tabl">
						<tbody>
							<tr>
								<th id="rule" class="block">Select&nbsp;Rule&nbsp;:</th>
								<th id="estRuleTd"> <select
									class="form-control select2me" id="estrule"
									name="estrule" >

										<option selected value="">Select Estimation Rule</option>
								</select></th>
							</tr>
						</tbody>
					</table>
					



					<div class="modal-footer">
						<button class="btn green pull-right" onclick="processData()"
							  data-toggle='modal'>Estimate</button>
						<button class="btn red pull-left" type="button"
							data-dismiss="modal" onclick="formreset()">Cancel</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="stack4" class="modal fade" role="dialog"
		aria-labelledby="myModalLabel10" aria-hidden="true">
		<div class="modal-dialog" id="showPopId">
		               <div id="esimageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
			<div class="modal-content">
			<div class="modal-header">
			<h4 class="modal-title">Estimated Data</h4>
			</div>
				<div class="modal-body">

					<!-- <h5>select</h5> -->
					
					<div id="view_estimate" style="display: none">
						<table class="table table-striped table-hover table-bordered"
							id="estimated_data">
							<thead>
								<tr>
									<th>Meter No</th>
									<th>Location Id</th>
									<th>Region</th>
									<th>Circle</th>
									<th>Division</th>
									<th>Sub Division</th>
									<th>Town Code</th>
									<th>Location Name</th>
									<th>Location Type</th>
									<th>Reading Date</th>
									<!-- <th>Missing Time</th> -->
									<th>Avg Kwh</th>
									<th>Avg Kvah</th>
									<th>Avg Kw</th>
									<th>Avg Kva</th>
									
								</tr>
							</thead>
							<tbody id="estimatedData">
							</tbody>
						</table>
					</div>


					<div class="modal-footer" id ="modelFooterId">
						<button class="btn green pull-right" type="button"
							onclick="saveData()">Save</button>
						<button class="btn red pull-left" type="button"
							data-dismiss="modal" onclick="formreset()">Cancel</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="imgProssPop" class="modal fade" role="dialog"
		aria-labelledby="myModalLabel10" aria-hidden="true">
		<div class="modal-dialog" id="showPopId">
   
			<div class="modal-content">
			<div id="esimageeeForPross" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Processing..... Please wait.</h3>
						<br>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 5%; height: 5%;">
						<br>
						
					</div>
			</div>
			
			</div>
			
			</div>

</div>



<script>
	/* function showCircle(zone) {
		$
				.ajax({
					url : './showCircleMDAS' + '/' + zone,
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					success : function(response) {
						var html = '';
					html += "<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control' type='text'><option value=''>Select Circle</option>";
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
						html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control' type='text'><option value=''>Select Division</option>";

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
		$('#sdoCode').empty();
		$('#sdoCode').find('option').remove();
		$('#sdoCode').append($('<option>', {
			value : "",
			text : "Select Sub-Division"
		}));
		$.ajax({
			url : './showSubdivByDivMDAS' + '/' + zone + '/' + circle + '/'
					+ division,
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(response1) {
				for (var i = 0; i < response1.length; i++) {
					$('#sdoCode').append($('<option>', {
						value : response1[i],
						text : response1[i]
					}));
				}
			}
		});
	}
 */




/* 	function showCircle(zone) {
		$
				.ajax({
					url : './getcirclebyzone',
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					data : {
						zone : zone
					},
					success : function(response) {
						var html = '';
					html += "<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control' type='text'><option value=''>Select Circle</option><option value='ALL'>ALL</option>";
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
		$("#circledivsub").show();
		var circle = $("#circle").val();
		$
				.ajax({
					url : './getdivisionbycircle',
					type : "GET",
					dataType : 'json',
					data : {
						circle : circle
					},
					success : function(response) {
						var html = "";
						html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control' type='text'><option value=''>Select Division</option><option value='ALL'>ALL</option>";
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
		var division = $('#division').val();
		$
				.ajax({
					url : './getSubdivByDiv',
					type : 'GET',
					dataType : 'json',
					data : {
						circle : circle,
						division : division
					},
					asynch : false,
					cache : false,
					success : function(response1) {
						var html = '';
						html += "<select id='sdoCode' name='sdoCode'  class='form-control' type='text'><option value=''>Select Sub-Division</option><option value='ALL'>ALL</option>";
						for (var i = 0; i < response1.length; i++) {
							html += "<option  value='"+response1[i]+"'>"
									+ response1[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#subdivTd").html(html);
						$('#sdoCode').select2();
					}
				});
	} */
	function showCircle(zone)
	{
		 $.ajax({
		    	url:'./getCircleByZone',
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	data : {
					zone : zone
				},
		    	success:function(response)
		    	{
	  			var html='';
		    		html+="<select id='circle' name='circle' onchange='showTownNameandCode(this.value)' class='form-control' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
					for( var i=0;i<response.length;i++)
					{
						html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
					}
					html+="</select><span></span>";
					$("#circleTd").html(html);
					$('#circle').select2();
		    	}
			});
	}

	 function showDivision(circle) {
		 var zone = $('#zone').val();
		
			$.ajax({
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
							html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control' type='text'><option value=''>Select Division</option><option value='%'>ALL</option>";
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
			$.ajax({
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
							html += "<select id='sdoCode' name='sdoCode'  onchange='showTownsBySubDiv(this.value)' class='form-control' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
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

	 function showTownsBySubDiv(subdivIdName)
		{

		    var zone = $('#zone').val();
			var circle = $('#circle').val();
		    var division = $('#division').val();
			var subdivision=subdivIdName;
			
			$.ajax({
				type : 'GET',
				
				url : "./getTownsBaseOnSubdivision",
				data:{
					zone : zone,
					circle : circle,
					division : division,
					subdivision : subdivision
					},
				async : false,
				cache : false,
				success : function(response)
				{
					var html="";
					if(response!=null)
						{
						html+="<option value=''>Select Town</option><option value='%'>ALL</option>"; 
						for(var i=0;i<response.length;i++)
							{
							html+="<option value='"+response[i][0]+"'>"+ response[i][0]+"-" +response[i][1]+"</option>"; 
						
						$("#town").empty();
						$("#town").append(html);
						$("#town").select2();
						
						}
						}
					
				}
			});
		}
		
	 function showTownNameandCode(circle){
			var zone =  $('#zone').val(); 
			//$('#town').find('option').remove();
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
			  		success : function(response) {
			  		   $('#town').append($('<option>', {
		 					value : '%',
		 					text : 'ALL',
		 				}));
		              for (var i = 0; i < response.length; i++) {
		                 
		                  $('#town').append($('<option>', {
		  					value : response[i][0],
		  					text : response[i][0] + "-" + response[i][1],
		  				}));
		              }
		            }
			  	});
			  }
	
</script>

<script>
	/* 	var startDate = new Date();
	 var fechaFin = new Date();
	 var FromEndDate = new Date();
	 var ToEndDate = new Date();
	 $('.from').datepicker({
	 autoclose : true,
	 minViewMode : 1,
	 format : 'yyyymm'
	 }).on(
	 'changeDate',
	 function(selected) {
	 startDate = new Date(selected.date.valueOf());
	 startDate.setDate(startDate.getDate(new Date(selected.date
	 .valueOf())));

	 }); */

	var date = new Date();
	var year = date.getFullYear();
	var currentMonth = date.getMonth();
	var month = date.getMonth();

	$('.from').datepicker({
		format : "yyyymm",
		minViewMode : 1,
		autoclose : true,
		startDate : new Date(new Date().getFullYear()),
		endDate : new Date(year, currentMonth - 1, '30')
	});
	
function formreset(){

	
	$("#updateMaster2").html('');
	$('#sample_2').dataTable().fnClearTable();
	$("#updateMaster3").html('');
	$('#sample_3').dataTable().fnClearTable();
	$("#updateMaster4").html('');
	$('#sample_4').dataTable().fnClearTable();
	$("#updateMaster5").html('');
	$('#sample_5').dataTable().fnClearTable();
	$("#updateMaster6").html('');
	$('#sample_6').dataTable().fnClearTable();
	$("#updateMaster7").html('');
	$('#sample_7').dataTable().fnClearTable();
	$("#updateMaster8").html('');
	$('#sample_8').dataTable().fnClearTable();
	$("#updateMaster9").html('');
	$('#sample_9').dataTable().fnClearTable();
	$("#updateMaster10").html('');
	$('#sample_10').dataTable().fnClearTable();
	$("#updateMaster11").html('');
	$('#sample_11').dataTable().fnClearTable();
	$("#updateMaster12").html('');
	$('#sample_12').dataTable().fnClearTable();
	$("#updateMaster13").html('');
	$('#sample_13').dataTable().fnClearTable();
	$("#updateMaster14").html('');
	$('#sample_14').dataTable().fnClearTable();
	$("#updateMaster15").html('');
	$('#sample_15').dataTable().fnClearTable();
	
	$("#view_2").hide();
	$("#view_3").hide();
	$("#view_4").hide();
	$("#view_5").hide();
	$("#view_6").hide();
	$("#view_7").hide();
	$("#view_8").hide();
	$("#view_9").hide();
	$("#view_10").hide();
	$("#view_11").hide();
	$("#view_12").hide();
	$("#view_13").hide();
	$("#view_14").hide();
	$("#view_15").hide();
	$("#view_1").hide();
	
}
	
</script>
