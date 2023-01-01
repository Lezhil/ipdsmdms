<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
#tzid th {
	text-align: center !important;
}

#tzid td {
	text-align: center !important;
}

#weid th {
	text-align: center !important;
}

#weid td {
	text-align: center !important;
}

#seid th {
	text-align: center !important;
}

#seid td {
	text-align: center !important;
}
</style>
<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {

						App.init();
						FormComponents.init();
						$('#MDASSideBarContents,#metergroup,#meterOper').addClass('start active ,selected');
						$("#dash-board,#user-location,#MIS-Reports,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn").removeClass('start active ,selected');

						mtrgrplist();

						//loadSearchAndFilter('sample_model');
						//loadSearchAndFilter('sample_4');
					});

	$(document).ready(function() {
		$(".daysc").click(function() {
			if ($('#daycs1').prop('checked')) {
				return true;
			}
			bootbox.alert("Please select any on of the day");
			return false;
		});

		$('#clockface_11_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_11_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_11_modal').clockface('toggle');
		});
		$('#clockface_12_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_12_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_12_modal').clockface('toggle');
		});
		$('#clockface_13_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_13_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_13_modal').clockface('toggle');
		});
		$('#clockface_14_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_14_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_14_modal').clockface('toggle');
		});
		$('#clockface_15_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_15_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_15_modal').clockface('toggle');
		});
		$('#clockface_16_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_16_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_16_modal').clockface('toggle');
		});
		$('#clockface_21_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_21_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_21_modal').clockface('toggle');
		});
		$('#clockface_22_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_22_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_22_modal').clockface('toggle');
		});
		$('#clockface_23_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_23_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_23_modal').clockface('toggle');
		});
		$('#clockface_24_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_24_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_24_modal').clockface('toggle');
		});
		$('#clockface_25_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_25_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_25_modal').clockface('toggle');
		});
		$('#clockface_26_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_26_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_26_modal').clockface('toggle');
		});
		$('#clockface_31_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_31_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_31_modal').clockface('toggle');
		});
		$('#clockface_32_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_32_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_32_modal').clockface('toggle');
		});
		$('#clockface_33_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_33_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_33_modal').clockface('toggle');
		});
		$('#clockface_34_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_34_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_34_modal').clockface('toggle');
		});
		$('#clockface_35_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_35_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_35_modal').clockface('toggle');
		});
		$('#clockface_36_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_36_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_16_modal').clockface('toggle');
		});
		$('#clockface_41_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_41_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_41_modal').clockface('toggle');
		});
		$('#clockface_42_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_42_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_42_modal').clockface('toggle');
		});
		$('#clockface_43_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_43_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_43_modal').clockface('toggle');
		});
		$('#clockface_44_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_44_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_44_modal').clockface('toggle');
		});
		$('#clockface_45_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_45_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_45_modal').clockface('toggle');
		});
		$('#clockface_46_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_46_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_46_modal').clockface('toggle');
		});
		$('#clockface_51_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_51_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_51_modal').clockface('toggle');
		});
		$('#clockface_52_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_52_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_52_modal').clockface('toggle');
		});
		$('#clockface_53_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_53_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_53_modal').clockface('toggle');
		});
		$('#clockface_54_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_54_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_54_modal').clockface('toggle');
		});
		$('#clockface_55_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_55_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_55_modal').clockface('toggle');
		});
		$('#clockface_56_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_56_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_56_modal').clockface('toggle');
		});
		$('#clockface_61_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_61_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_61_modal').clockface('toggle');
		});
		$('#clockface_62_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_62_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_62_modal').clockface('toggle');
		});
		$('#clockface_63_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_63_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_63_modal').clockface('toggle');
		});
		$('#clockface_64_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_64_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_64_modal').clockface('toggle');
		});
		$('#clockface_65_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_65_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_65_modal').clockface('toggle');
		});
		$('#clockface_66_modal').clockface({
			format : 'HH:mm',
			trigger : 'manual'
		});

		$('#clockface_66_modal_toggle').click(function(e) {
			e.stopPropagation();
			$('#clockface_66_modal').clockface('toggle');
		});
		$('.js-example-basic-multiple').select2();

	});
</script>
<script>
	function clearTabledataContent(tableid) {
		//TO CLEAR THE TABLE DATA
		var oSettings = $('#' + tableid).dataTable().fnSettings();
		var iTotalRecords = oSettings.fnRecordsTotal();
		for (i = 0; i <= iTotalRecords; i++) {
			$('#' + tableid).dataTable().fnDeleteRow(0, null, true);
		}

	}
	function mtrgrplist() {
		$
				.ajax({
					url : "./metergrouplistHES",
					type : "GET",
					success : function(response) {
						var r = response;
						var html = '<option value="" disabled="disabled" selected="selected">Select</option>';

						for (var i = 0; i < r.length; i++) {
							html += '<option value="'+r[i]+'">' + r[i]
									+ '</option>';

						}
						$("#mtrgrpname").html(html);
					}
				});

	}
	function selectingoptions() {
		var api = $("#api").val();
		var bool = $("#boolean").val();

		if (api == "metergroup") {
			$("#tf").hide();
			$("#mgdid").show();
			$("#mjdid").hide();
			$("#mqjid").hide();
			$("#msfjid").hide();
			$("#managejob").hide();
			$("#createMtrjob").hide();
			$("#meterstatus").hide();
			metergrouplist();
		}

		else if (api == "meterjob") {
			$("#tf").show();
			$("#mgdid").hide();
			$("#mjdid").show();
			$("#mqjid").hide();
			$("#msfjid").hide();
			$("#managejob").hide();
			$("#createMtrgrp").hide();
			$("#queryjob").hide();
			meterjoblist();

		} else if (api == "managejob") {
			$("#tf").show();
			$("#mgdid").hide();
			$("#mjdid").hide();
			$("#mqjid").hide();
			$("#msfjid").hide();
			$("#createMtrjob").hide();
			$("#createMtrgrp").hide();
			$("#queryjob").hide();
			$("#meterstatus").hide();
		} else if (api == "Queryjob") {
			$("#tf").show();
			$("#mgdid").hide();
			$("#mjdid").hide();
			$("#mqjid").hide();
			$("#msfjid").hide();
			$("#createMtrjob").hide();
			$("#createMtrgrp").hide();
			$("#managejob").hide();
			$("#meterstatus").hide();
		} else if (api == "MeterStatusForJob") {
			$("#tf").show();
			$("#mgdid").hide();
			$("#mjdid").hide();
			$("#mqjid").hide();
			$("#msfjid").hide();
			$("#createMtrjob").hide();
			$("#createMtrgrp").hide();
			$("#queryjob").hide();
			$("#managejob").hide();
		}

		else {
			$("#tf").show();
			$("#mgdid").hide();
		}

	}
	function metergrouplist() {
		$("#mgid tr").remove();
		$
				.ajax({
					url : "./metergrouplist",
					type : "GET",
					success : function(response) {
						var r = response;
						var html = "";
						for (var i = 0; i < r.length; i++) {
							html += "<tr>";
							html += "<td>" + (i + 1) + "</td>";
							html += "<td>" + r[i][1] + "</td>";
							//html += "<td>" + r[i][2] + "</td>";
							html += '<td><a class="btn default" data-toggle="modal" href="#basic" id="'
									+ r[i][1]
									+ '" onclick="groupmeterslist(this.id)">View</a></td>';
							html += "<td>"
									+ moment(r[i][4]).format(
											"YYYY-MM-DD HH:mm:ss") + "</td>";
							html += '<td><button type="button" class="btn btn-danger" id="'
									+ r[i][1]
									+ '" onclick="groupDelete(this.id)">Delete</button></td>';
						}
						clearTabledataContent('sample_3');
						$("#mgid").html(html);
						/* $('#sample_3').DataTable( {
								destroy: true,
								"pageLength": 10,
						       "pagingType": "full_numbers",
								
						        dom: 'Bfrtip',
						        buttons: [
							                {
							                    extend: 'excelHtml5',
							                    title: 'Meter Group'
							                },
							                {
							                    extend: 'pdfHtml5',
							                    title: 'Meter Group'
							                }
							            ]
						    } ); */

					},

					complete : function() {
						loadSearchAndFilter1('sample_3');
					}
				});
	}
	function groupDelete(grpname) {
		$.ajax({
			url : "./deleteMeterGroup/" + grpname,
			type : "GET",
			success : function(res) {
				if (res == 'S') {
					bootbox.alert("Successfully deleted");
					metergrouplist();
				} else {
					bootbox.alert("Server Down");
				}

			}
		});
	}
	function groupmeterslist(grpname) {
		$.ajax({
			url : "./grpMeterList/" + grpname,
			type : "GET",
			success : function(res) {
				var html = '';
				$.each(res, function(i, value) {
					html += '<tr>';
					html += '<td>' + (i + 1) + '</td>';
					html += '<td>' + value[0] + '</td>';
					html += '<td>'
							+ moment(value[1])
									.format('YYYY-MM-DD HH:mm:ss SSS')
							+ '</td>';
					html += '</tr>';
				});
				clearTabledataContent('sample_model');
				$("#mtrgmlistid").html(html);

			},

			complete : function() {
				loadSearchAndFilter1('sample_model');
			}
		});
	}
	function joblist() {
		var v = '';
		$.ajax({
			url : "./jobs",
			type : "GET",
			success : function(response) {
				var elements = jQuery.parseJSON(response);
				var html = "";
				jQuery.each(elements, function(i, val) {
					html += '<option value='+val.jobName+'>' + val.jobName
							+ '</option>';
				});
				$("#jobnamestatus").html(html);
				$("#jobnameQ").html(html);
				$("#managemeterjobid").html(html);

			}
		});
	}
	function meterjoblist() {
		$("#mjid tr").remove();
		$.ajax({
			url : "./meterjoblist",
			type : "GET",
			success : function(response) {
				var r = response;
				var html = "";
				for (var i = 0; i < r.length; i++) {
					html += "<tr>";
					html += "<td>" + (i + 1) + "</td>";
					html += "<td>" + r[i][1] + "</td>";
					html += "<td>" + r[i][2] + "</td>";
					html += "<td>" + r[i][3] + "</td>";
					html += "<td>"
							+ moment(r[i][5]).format("YYYY-MM-DD HH:mm:ss")
							+ "</td>";
				}
				clearTabledataContent('sample_4');
				$("#mjid").html(html);

			},

			complete : function() {
				loadSearchAndFilter1('sample_4');
			}
		});
	}

	function allapis() {
		var api = $("#api").val();
		var bool = $("#boolean").val();
		joblist();
		if (api == "metergroup") {
			$("#createMtrgrp").show();
			$("#createMtrjob").hide();
			$("#managejob").hide();
			$("#queryjob").hide();
			$("#meterstatus").hide();
		}

		if (api == "meterjob" && (bool == "true" || bool == "False")) {
			mtrgrplist();
			$(" #createMtrjob #sample_1_length label").hide();
			$(" #createMtrjob #sample_1_filter label").hide();
			$(" #createMtrjob #sample_1_info").hide();
			$(" #createMtrjob .pagination").hide();
			$("#createMtrgrp").hide();
			$("#createMtrjob").show();
			$("#queryjob").hide();
			$("#managejob").hide();
			$("#meterstatus").hide();
		}
		if (api == "managejob" && (bool == "true" || bool == "False")) {
			$("#createMtrgrp").hide();
			$("#createMtrjob").hide();
			$("#managejob").show();
			$("#queryjob").hide();
			$("#meterstatus").hide();
		}

		if (api == "MeterStatusForJob" && (bool == "true" || bool == "False")) {
			$("#createMtrgrp").hide();
			$("#createMtrjob").hide();
			$("#managejob").hide();
			$("#queryjob").hide();
			$("#meterstatus").show();
		}

		if (api == "Queryjob" && (bool == "true" || bool == "False")) {
			$("#createMtrgrp").hide();
			$("#createMtrjob").hide();
			$("#managejob").hide();
			$("#meterstatus").hide();
			$("#queryjob").show();
		}

	}

	function managemeterjob() {
		var jobName = $("#managemeterjobid").val();
		$
				.ajax({
					url : "./manageMeterJob/" + jobName,
					type : 'GET',
					dataType : 'json',
					cache : false,
					success : function(response) {
						if (response == "200") {
							$("#alertId").show();
							document.getElementById("alertId").style.display = 'block';
							document.getElementById("spanId").innerHTML = '<span style="color: green">Successfully Created</span>';
							meterjoblist();
							return true;
						}

						$("#alertId").show();
						document.getElementById("alertId").style.display = 'block';
						document.getElementById("spanId").innerHTML = '<span style="color: red">Job Command Fail or Server Down</span>';
					}
				});

	}

	function querymeterjob() {
		var jobname = $("#jobnameQ").val();
		$
				.ajax({
					url : "./querymeterjobl/" + jobname,
					type : 'GET',

					cache : false,
					success : function(response) {
						//var v=response;
						var v = jQuery.parseJSON(response);
						//alert( obj.name === "jobName" );

						//var v = JSON.stringify(response);
						$("#jn").html(v.jobName);
						$("#mg").html(v.meterGroup);
						$("#st").html(v.scheduledTime);
						$("#tt").html(v.timeoutTime);
						$("#ct").html(v.createTime);
						$("#ut").html(v.updateTime);
						$("#sta").html(v.status);
						$("#jt").html(v.jobType);
						$("#tpd").html(v.totalProcessedDevices);
						$("#tsd").html(v.totalSuccessfulDevices);
						$("#tfd").html(v.totalFailedDevices);
						var jc = v.jobConfiguration;
						var t1 = jc.name;
						var t2 = jc.commands;
						if (v.jobType == 'METER_COMMAND_SET') {

							var html = '';
							var alahtml = '';
							var Evtdidhtml = '';
							var pctrue;
							var aftrue;
							var ettrue;
							for (var i = 0; i < t2.length; i++) {
								if (t2[i].type == 'PROFILE_CAPTURE_PERIOD') {
									pctrue = true;
									html += '<tr><td>' + t2[i].type
											+ '</td><td>'
											+ t2[i].activationTime
											+ '</td><td>' + t2[i].profileType
											+ '</td><td>'
											+ (t2[i].capturePeriod / 60)
											+ ' Min</td><td></td></tr>';

								} else if (t2[i].type == 'DEMAND_INTEGRATION_PERIOD') {
									pctrue = true;
									html += '<tr><td>' + t2[i].type
											+ '</td><td>'
											+ t2[i].activationTime
											+ '</td><td></td><td>'
											+ (t2[i].demandPeriod / 60)
											+ ' Min</td><td></td></tr>';

								} else if (t2[i].type == 'BILLING_PERIOD') {
									pctrue = true;

									html += '<tr><td>' + t2[i].type
											+ '</td><td>'
											+ t2[i].activationTime
											+ '</td><td></td><td></td><td>'
											+ t2[i].date.dayOfMonth
											+ '</td></tr>';

								} else if (t2[i].type == 'ALARM_FILTER') {
									aftrue = true;
									$.each(t2[i].filterMask, function(i, v) {
										alahtml += '<tr><td>' + t2[i].type
												+ '</td>'

												+ '<td>' + t2[i].activationTime
												+ '</td><td>' + v
												+ '</td></tr>';
									});

								} else if (t2[i].type == 'EVENT_THRESHOLD') {

									ettrue = true;
									Evtdidhtml += '<tr><td>'
											+ t2[i].type
											+ '</td>'

											+ '<td>'
											+ t2[i].activationTime
											+ '</td>'
											+ '</td><td>'
											+ t2[i].voltageHighLimitNormal
											+ '</td><td>'
											+ t2[i].voltageHighLimitMinOverThresholdDuration
											+ '</td><td>'
											+ t2[i].voltageHighLimitMinUnderThresholdDuration
											+ '</td><td>'
											+ t2[i].voltageLowLimitNormal
											+ '</td><td>'
											+ t2[i].voltageLowLimitMinOverThresholdDuration
											+ '</td><td>'
											+ t2[i].voltageLowLimitMinUnderThresholdDuration
											+ '</td></tr>';

								} else if (t2[i].type == 'TARIFF_CALENDAR') {
									$("#sample_9_type").html(t2[i].type);
									$("#sample_9_at")
											.html(t2[i].activationTime);
									var seasonhtml = '';
									$.each(t2[i].seasons, function(i, v) {
										seasonhtml += '<tr><td>'
												+ v.start.month + '</td><td>'
												+ v.start.dayOfMonth
												+ '</td><td>' + v.week
												+ '</td></tr>';
									});
									$("#sample_10_b").html(seasonhtml);
									var weekshtml = '';
									$.each(t2[i].weeks, function(i, v) {
										weekshtml += '<tr><td>' + i
												+ '</td><td>' + v.days[0]
												+ '</td><td>' + v.days[1]
												+ '</td><td>' + v.days[2]
												+ '</td><td>' + v.days[3]
												+ '</td><td>' + v.days[4]
												+ '</td><td>' + v.days[5]
												+ '</td><td>' + v.days[6]
												+ '</td></tr>';
									});
									$("#sample_11_b").html(weekshtml);
									var dayshtml = '';
									$.each(t2[i].days, function(i, v) {
										dayshtml += '<tr>';
										$.each(v.schedule, function(ii, vv) {
											if (vv.zone == 1) {
												dayshtml += '<td>' + vv.time
														+ '</td> ';
											}

											else if (vv.zone == 2) {
												dayshtml += '<td>' + vv.time
														+ '</td> ';
											}

											else if (vv.zone == 3) {
												dayshtml += '<td>' + vv.time
														+ '</td>  ';
											}

											else if (vv.zone == 4) {
												dayshtml += '<td>' + vv.time
														+ '</td>  ';

											}

											else if (vv.zone == 5) {
												dayshtml += '<td>' + vv.time
														+ '</td>  ';
											}

											else if (vv.zone == 6) {
												dayshtml += '<td>' + vv.time
														+ '</td>  ';
											} else {
												dayshtml += '<td></td>  ';
											}
										});

										dayshtml += '</tr>';
									});
									$("#sample_12_b").html(dayshtml);
								}

							}
							if (pctrue) {
								$('#sample_6').show();
							}
							if (aftrue) {
								$('#sample_7').show();
							}
							if (ettrue) {
								$('#sample_8').show();
							}
							$("#sample_9").show();
							$("#mqjbbid").html(html);
							$("#alarmqjbbid").html(alahtml);
							$("#Evtdid").html(Evtdidhtml);

							$("#mqjid").show();
							$("#sample_13").hide();
						}
						if (v.jobType == 'METER_COMMAND_GET') {
							var mghtml = '';
							$.each(t2, function(i, v) {
								mghtml += '<tr><td>' + (i + 1) + '</td><td>'
										+ v.type + '</td><td>' + v.active
										+ '</td></tr>'

							});
							$('#sample_13_b').html(mghtml);
							$("#sample_13").show();
							$("#mqjid").show();

						}
					}
				});

	}

	function meterstatus() {

		var jobname = $("#jobnamestatus").val();
		var count = $("#count").val();
		var devicestatus = $("#device").val();
		var startid = $("#startid").val();
		$
				.ajax({
					url : "./meterStatusForJob/" + jobname + '/' + count + '/'
							+ devicestatus + '/' + startid,
					type : 'GET',
					cache : false,
					success : function(response) {
						var html = '';
						var thtml = '';

						var objJSON = typeof jData != 'object' ? JSON
								.parse(response) : jData;
						var ma = objJSON.meters;
						jQuery
								.each(
										ma,
										function(i, val) {
											var mi = val.meterId;
											var su = val.status;
											var fs = val.failureStep;
											var res = val.response;
											if (res == undefined) {
												html += '<tr><td >' + mi
														+ '</td><td >' + su
														+ '</td>';
												html += '	<td >' + fs
														+ '</td></tr>';

												thtml = '<tr><th>Meter Number</th><th>Status</th><th>Failure Step</th></tr>';
											} else {
												thtml = '<tr><th>Meter Number</th><th>Status</th><th>Failure Step</th><th>Type</th><th></th><th></th></tr>';
												jQuery
														.each(
																res,
																function(j, val) {
																	if (val.type == "BILLING_PERIOD") {

																		html += '<tr><td rowspan="2">'
																				+ mi
																				+ '</td><td rowspan="2">'
																				+ su
																				+ '</td>';
																		html += '	<td rowspan="2">'
																				+ fs
																				+ '</td><td rowspan="2">Billing Period</td>';
																		html += '<td>Active</td><td>'
																				+ val.active
																				+ '</td>';
																		html += '</tr><tr><td>Day Of Month</td><td>'
																				+ val.date.dayOfMonth
																				+ '</td></tr>';
																		var pt = val.profileType;
																	} else if (val.type == "DEMAND_INTEGRATION_PERIOD") {
																		html += '<tr><td rowspan="2">'
																				+ mi
																				+ '</td><td rowspan="2">'
																				+ su
																				+ '</td>';
																		html += '	<td rowspan="2">'
																				+ fs
																				+ '</td><td rowspan="2">Demand Integration Period</td>';
																		html += '<td>Active</td><td>'
																				+ val.active
																				+ '</td>';
																		html += '</tr><tr><td>Demand Period</td><td>'
																				+ val.demandPeriod
																				+ '</td></tr>';

																	} else if (val.type == "PROFILE_CAPTURE_PERIOD") {
																		html += '<tr><td rowspan="3">'
																				+ mi
																				+ '</td><td rowspan="3">'
																				+ su
																				+ '</td>';
																		html += '	<td rowspan="3">'
																				+ fs
																				+ '</td><td rowspan="3">Profile Capture Period</td>';
																		html += '<td>Profile Type</td><td>'
																				+ val.profileType
																				+ '</td></tr>';
																		html += '<tr><td>Capture Period</td><td>'
																				+ val.capturePeriod
																				+ '</td></tr>';
																		html += '<tr><td>Active</td><td>'
																				+ val.active
																				+ '</td></tr>';
																	} else if (val.type == "ALARM_FILTER") {
																		var vfm = '';
																		$
																				.each(
																						val.filterMask,
																						function(
																								i,
																								v) {
																							vfm += '<div>'
																									+ v
																									+ '</div>';
																						});
																		html += '<tr><td rowspan="2">'
																				+ mi
																				+ '</td><td rowspan="2">'
																				+ su
																				+ '</td>';
																		html += '	<td rowspan="2">'
																				+ fs
																				+ '</td><td rowspan="2">Alarm Filter</td>';
																		html += '<td>Filter Mask</td><td>'
																				+ vfm
																				+ '</td></tr>';

																		html += '<tr><td>Active</td><td>'
																				+ val.active
																				+ '</td></tr>';
																	} else if (val.type == "EVENT_THRESHOLD") {

																		html += '<tr><td rowspan="10">'
																				+ mi
																				+ '</td><td rowspan="10">'
																				+ su
																				+ '</td>';
																		html += '	<td rowspan="10">'
																				+ fs
																				+ '</td><td rowspan="10">Event Threshold</td>';
																		html += '<td>Activtion Time</td><td>'
																				+ val.activationTime;
																		+'</td></tr>';
																		html += '<tr><td>Active</td><td>'
																				+ val.active
																				+ '</td></tr>';
																		html += '<tr><td>Voltage High Limit Normal</td><td>'
																				+ val.voltageHighLimitNormal
																				+ '</td></tr>';
																		html += '<tr><td>Voltage High Limit Emergency</td><td>'
																				+ val.voltageHighLimitEmergency
																				+ '</td></tr>';
																		html += '<tr><td>Voltage High Limit  Min Over Threshold Duration</td><td>'
																				+ val.voltageHighLimitMinOverThresholdDuration
																				+ '</td></tr>';
																		html += '<tr><td>Voltage High Limit Min Under Threshold Duration</td><td>'
																				+ val.voltageHighLimitMinUnderThresholdDuration
																				+ '</td></tr>';
																		html += '<tr><td>Voltage Low Limit Normal</td><td>'
																				+ val.voltageLowLimitNormal
																				+ '</td></tr>';
																		html += '<tr><td>Voltage Low Limit Emergency</td><td>'
																				+ val.voltageLowLimitEmergency
																				+ '</td></tr>';
																		html += '<tr><td>Voltage Low Limit Min Over Threshold Duration</td><td>'
																				+ val.voltageLowLimitMinOverThresholdDuration
																				+ '</td></tr>';
																		html += '<tr><td>Voltage Low Limit Min Under Threshold Duration</td><td>'
																				+ val.voltageLowLimitMinUnderThresholdDuration
																				+ '</td></tr>';
																	} else if (val.type == "TARIFF_CALENDAR") {

																		html += '<tr><td rowspan="5">'
																				+ mi
																				+ '</td><td rowspan="5">'
																				+ su
																				+ '</td>';
																		html += '	<td rowspan="5">'
																				+ fs
																				+ '</td><td rowspan="5">Time Of Use</td>';
																		html += '<td>Activtion Time</td><td>'
																				+ val.activationTime;
																		+'</td></tr>';
																		html += '<tr><td>Active</td><td>'
																				+ val.active
																				+ '</td></tr>';
																		var season = '';
																		$
																				.each(
																						val.seasons,
																						function(
																								i,
																								v) {
																							season += '<tr><td>'
																									+ v.start.month
																									+ '</td><td>'
																									+ v.start.dayOfMonth
																									+ '</td><td>'
																									+ v.week
																									+ '</td></tr>';
																						});
																		html += '<tr><td>Seasons</td><td>'
																				+ '<table><thead><tr><th>Month</th><th>Day Of Month</th><th>Week</th></tr></thead>'
																				+ '<tbody>'
																				+ season
																				+ '</tbody>'
																				+ '</table>'
																				+ '</td></tr>';
																		var week = '';
																		$
																				.each(
																						val.weeks,
																						function(
																								i1,
																								v1) {
																							week += '<tr><td>'
																									+ v1.days[0]
																									+ '</td><td>'
																									+ v1.days[1]
																									+ '</td><td>'
																									+ v1.days[2]
																									+ '</td><td>'
																									+ v1.days[3]
																									+ '</td><td>'
																									+ v1.days[4]
																									+ '</td><td>'
																									+ v1.days[5]
																									+ '</td><td>'
																									+ v1.days[6]
																									+ '</td></tr>';
																						});
																		html += '<tr><td>Weeks</td><td>'
																				+ '<table><thead><tr><th>M</th><th>T</th><th>W</th><th>T</th><th>F</th><th>S</th><th>S</th></tr></thead>'
																				+ '<tbody>'
																				+ week
																				+ '</tbody>'
																				+ '</table>'
																				+ '</td></tr>';
																		var day = '';
																		$
																				.each(
																						val.days,
																						function(
																								i2,
																								v2) {
																							day += '<tr>';
																							$
																									.each(
																											v2.schedule,
																											function(
																													i3,
																													v3) {
																												day += '<td>'
																														+ v3.time
																														+ '</td><td>'
																														+ v3.zone
																														+ '</td>';
																											});
																							day += '</tr>';
																						});
																		html += '<tr><td>Days</td><td>'
																				+ '<table><thead><tr><th>Time</th><th>Zone</th><th>Time</th><th>Zone</th><th>Time</th><th>Zone</th><th>Time</th><th>Zone</th><th>Time</th><th>Zone</th><th>Time</th><th>Zone</th></tr></thead>'
																				+ '<tbody>'
																				+ day
																				+ '</tbody>'
																				+ '</table>'
																				+ '</td></tr>';

																	}

																});
											}
										});
						$("#msfjbidhead").html(thtml);
						$("#msfjbid").html(html);
						$('#msfjid').show();
					}
				});

	}
	function ConvertToTable(jData) {
		var arrJSON = typeof jData != 'object' ? JSON.parse(jData) : jData;
		var $table = $('');
		var $headerTr = $('');

		for ( var index in arrJSON[0]) {
			$headerTr.append($('Â ').html(index));
		}
		$table.append($headerTr);
		for (var i = 0; i < arrJSON.length; i++) {
			var $tableTr = $('');
			for ( var index in arrJSON[i]) {
				$tableTr.append($('Â ').html(arrJSON[i][index]));
			}
			$table.append($tableTr);
		}
		$('body').append($table);
	}

	function getLoadCurtlmntParams(val) {
		//alert(val);
		if (val == 'LOAD_CURTAILMENT') {
			//$('#loadDetails').toggle();
			$('#loadDetails').modal('show');
		}

	}
	function dtconvert(val) {
		var datesp = val.split('-');
		var d = new Date(datesp[0]);
		var str = $.datepicker.formatDate('yy-mm-dd', d);
		var fstr = str + "T" + datesp[1] + ":00+0530";
		return fstr;
	}
	function createmeterjobupdate() {

		var jobName = $("#meterjobid").val();
		var meterGroup = $("#mtrgrpname").val();
		var jobType = $("#jobtype").val();
		var commands = {};
		var types = []
		//pcpbtid#pcpblid,pcpitid#pcpilid,diptid#diplid,bptid#bpdid
		if (jobType == 'METER_COMMAND_SET') {

			if ($("#pcpbcheckid").is(":checked")) {
				var pcpbtid = $("#pcpbtid").val();
				var pcpblid = $("#pcpblid").val();
				var datesp = dtconvert(pcpbtid).replace(/ /g, '');
				types.push({
					"type" : "PROFILE_CAPTURE_PERIOD",
					"activationTime" : datesp,
					"profileType" : "BLOCK_LOAD",
					"capturePeriod" : pcpblid

				});
			}
			if ($("#pcpinscheckid").is(":checked")) {
				var pcpitid = $("#pcpitid").val();
				var pcpilid = $("#pcpilid").val();
				var datesp = dtconvert(pcpitid).replace(/ /g, '');
				types.push({
					"type" : "PROFILE_CAPTURE_PERIOD",
					"activationTime" : datesp,
					"profileType" : "INSTANTANEOUS",
					"capturePeriod" : pcpilid

				});
			}
			if ($("#dipcheckid").is(":checked")) {
				var diptid = $("#diptid").val();
				var diplid = $("#diplid").val();
				var datesp = dtconvert(diptid).replace(/ /g, '');
				types.push({
					"type" : "DEMAND_INTEGRATION_PERIOD",
					"activationTime" : datesp,
					"demandPeriod" : diplid

				});
			}
			if ($("#bpcheckid").is(":checked")) {
				var bptid = $("#bptid").val();
				var bpdid = $("#bpdid").val();
				var datesp = dtconvert(bptid).replace(/ /g, '');
				types.push({
					"type" : "BILLING_PERIOD",
					"activationTime" : datesp,
					"date" : {
						"dayOfMonth" : bpdid
					}

				});
			}

			if ($("#evethaid").is(":checked")) {

				var aldid = $("#evethatid").val();
				var datesp = dtconvert(aldid).replace(/ /g, '');
				var v1 = parseInt($("#vhlnid").val());
				var v2 = parseInt($("#vhlmtd1id").val());
				var v3 = parseInt($("#vhlmtd2id").val());
				var v4 = parseInt($("#vllnid").val());
				var v5 = parseInt($("#vllmtd1id").val());
				var v6 = parseInt($("#vllmtd2id").val());
				types.push({
					"type" : "EVENT_THRESHOLD",
					"activationTime" : datesp,
					"voltageHighLimitNormal" : v1,
					"voltageHighLimitMinOverThresholdDuration" : v2,
					"voltageHighLimitMinUnderThresholdDuration" : v3,
					"voltageLowLimitNormal" : v4,
					"voltageLowLimitMinOverThresholdDuration" : v5,
					"voltageLowLimitMinUnderThresholdDuration" : v6

				});
			}
			if ($("#alafilid").is(":checked")) {

				var aldid = $("#alafiltid").val();
				var datesp = dtconvert(aldid).replace(/ /g, '');
				var fm = $("#alarmeventsid").val();
				types.push({
					"type" : "ALARM_FILTER",
					"activationTime" : datesp,
					"filterMask" : fm

				});
			}
			if ($("#touid").is(":checked")) {

				var datesp = dtconvert($("#toudateid").val()).replace(/ /g, '');
				var season = [];
				if ($('#seasoncid1').prop('checked')) {
					var m1 = $("#monthid1").val();
					var d1 = $("#monthdid1").val();
					var w1 = $("#wstid1").val();

					season.push({
						"start" : {
							"dayOfMonth" : parseInt(d1),
							"month" : parseInt(m1)
						},
						"week" : parseInt(w1)
					});
				}
				if ($('#seasoncid2').prop('checked')) {
					var m2 = $("#monthid2").val();
					var d2 = $("#monthdid2").val();
					var w2 = $("#wstid2").val();
					//var season2='{"start": {"dayOfMonth": '+d2+',"month": '+m2+'	},	"week": '+w2+'}';
					season.push({
						"start" : {
							"dayOfMonth" : parseInt(d2),
							"month" : parseInt(m2)
						},
						"week" : parseInt(w2)
					});
				}
				if ($('#seasoncid3').prop('checked')) {
					var m3 = $("#monthid3").val();
					var d3 = $("#monthdid3").val();
					var w3 = $("#wstid3").val();
					// var season3='{"start": {"dayOfMonth": '+d3+',"month": '+m3+'	},	"week": '+w3+'}';
					season.push({
						"start" : {
							"dayOfMonth" : parseInt(d3),
							"month" : parseInt(m3)
						},
						"week" : parseInt(w3)
					});
				}
				var weeks = [];
				if ($('#wc1').prop('checked')) {
					var wc1d1 = $('#wc1d1').val();
					var wc1d2 = $('#wc1d2').val();
					var wc1d3 = $('#wc1d3').val();
					var wc1d4 = $('#wc1d4').val();
					var wc1d5 = $('#wc1d5').val();
					var wc1d6 = $('#wc1d6').val();
					var wc1d7 = $('#wc1d7').val();
					var weeks1 = [];
					weeks1.push(parseInt(wc1d1));
					weeks1.push(parseInt(wc1d2));
					weeks1.push(parseInt(wc1d3));
					weeks1.push(parseInt(wc1d4));
					weeks1.push(parseInt(wc1d5));
					weeks1.push(parseInt(wc1d6));
					weeks1.push(parseInt(wc1d7));
					//var weeks1='{"days": ['+wc1d1+','+wc1d2+','+wc1d3+','+wc1d4+','+wc1d5+','+wc1d6+','+wc1d7+']}';
					weeks.push({
						"days" : weeks1
					});
				}
				if ($('#wc2').prop('checked')) {
					var wc2d1 = $('#wc2d1').val();
					var wc2d2 = $('#wc2d2').val();
					var wc2d3 = $('#wc2d3').val();
					var wc2d4 = $('#wc2d4').val();
					var wc2d5 = $('#wc2d5').val();
					var wc2d6 = $('#wc2d6').val();
					var wc2d7 = $('#wc2d7').val();
					var weeks2 = [];
					weeks2.push(parseInt(wc2d1));
					weeks2.push(parseInt(wc2d2));
					weeks2.push(parseInt(wc2d3));
					weeks2.push(parseInt(wc2d4));
					weeks2.push(parseInt(wc2d5));
					weeks2.push(parseInt(wc2d6));
					weeks2.push(parseInt(wc2d7));
					/* var weeks2 = '{"days": [' + wc2d1 + ',' + wc2d2 + ',' + wc2d3
							+ ',' + wc2d4 + ',' + wc2d5 + ',' + wc2d6 + ',' + wc2d7
							+ ']}'; */
					//weeks.push(weeks2); 
					weeks.push({
						"days" : weeks2
					});
				}
				if ($('#wc3').prop('checked')) {
					var wc3d1 = $('#wc3d1').val();
					var wc3d2 = $('#wc3d2').val();
					var wc3d3 = $('#wc3d3').val();
					var wc3d4 = $('#wc3d4').val();
					var wc3d5 = $('#wc3d5').val();
					var wc3d6 = $('#wc3d6').val();
					var wc3d7 = $('#wc3d7').val();
					var weeks3 = [];
					weeks3.push(parseInt(wc3d1));
					weeks3.push(parseInt(wc3d2));
					weeks3.push(parseInt(wc3d3));
					weeks3.push(parseInt(wc3d4));
					weeks3.push(parseInt(wc3d5));
					weeks3.push(parseInt(wc3d6));
					weeks3.push(parseInt(wc3d7));
					/* var weeks3 = '{"days": [' + wc3d1 + ',' + wc3d2 + ',' + wc3d3
							+ ',' + wc3d4 + ',' + wc3d5 + ',' + wc3d6 + ',' + wc3d7
							+ ']}'; */
					//weeks.push(weeks3); 
					weeks.push({
						"days" : weeks3
					});
				}
				var days = [];
				if ($('#daycs1').prop('checked')) {
					var c11 = $("#clockface_11_modal").val();
					var c12 = $("#clockface_12_modal").val();
					var c13 = $("#clockface_13_modal").val();
					var c14 = $("#clockface_14_modal").val();
					var c15 = $("#clockface_15_modal").val();
					var c16 = $("#clockface_16_modal").val();
					var cm = [];
					if (c11 != "") {
						cm.push({
							"time" : c11,
							"zone" : 1
						});
					}
					if (c12 != "") {
						cm.push({
							"time" : c12,
							"zone" : 2
						});
					}
					if (c13 != "") {
						cm.push({
							"time" : c13,
							"zone" : 3
						});
					}
					if (c14 != "") {
						cm.push({
							"time" : c14,
							"zone" : 4
						});
					}
					if (c15 != "") {
						cm.push({
							"time" : c15,
							"zone" : 5
						});
					}
					if (c16 != "") {
						cm.push({
							"time" : c16,
							"zone" : 6
						});
					}

					days.push({
						"schedule" : cm
					});
				}
				if ($('#daycs2').prop('checked')) {
					var c21 = $("#clockface_21_modal").val();
					var c22 = $("#clockface_22_modal").val();
					var c23 = $("#clockface_23_modal").val();
					var c24 = $("#clockface_24_modal").val();
					var c25 = $("#clockface_25_modal").val();
					var c26 = $("#clockface_26_modal").val();
					var cm = [];
					if (c21 != "") {
						cm.push({
							"time" : c21,
							"zone" : 1
						});
					}
					if (c22 != "") {
						cm.push({
							"time" : c22,
							"zone" : 2
						});
					}
					if (c23 != "") {
						cm.push({
							"time" : c23,
							"zone" : 3
						});
					}
					if (c24 != "") {
						cm.push({
							"time" : c24,
							"zone" : 4
						});
					}
					if (c25 != "") {
						cm.push({
							"time" : c25,
							"zone" : 5
						});
					}
					if (c26 != "") {
						cm.push({
							"time" : c26,
							"zone" : 6
						});
					}

					days.push({
						"schedule" : cm
					});
				}
				if ($('#daycs3').prop('checked')) {
					var c31 = $("#clockface_31_modal").val();
					var c32 = $("#clockface_32_modal").val();
					var c33 = $("#clockface_33_modal").val();
					var c34 = $("#clockface_34_modal").val();
					var c35 = $("#clockface_35_modal").val();
					var c36 = $("#clockface_36_modal").val();
					var cm = [];
					if (c31 != "") {
						cm.push({
							"time" : c31,
							"zone" : 1
						});
					}
					if (c32 != "") {
						cm.push({
							"time" : c32,
							"zone" : 2
						});
					}
					if (c33 != "") {
						cm.push({
							"time" : c33,
							"zone" : 3
						});
					}
					if (c34 != "") {
						cm.push({
							"time" : c34,
							"zone" : 4
						});
					}
					if (c35 != "") {
						cm.push({
							"time" : c35,
							"zone" : 5
						});
					}
					if (c36 != "") {
						cm.push({
							"time" : c36,
							"zone" : 6
						});
					}

					days.push({
						"schedule" : cm
					});
				}
				if ($('#daycs4').prop('checked')) {
					var c41 = $("#clockface_41_modal").val();
					var c42 = $("#clockface_42_modal").val();
					var c43 = $("#clockface_43_modal").val();
					var c44 = $("#clockface_44_modal").val();
					var c45 = $("#clockface_45_modal").val();
					var c46 = $("#clockface_46_modal").val();
					var cm = [];
					if (c41 != "") {
						cm.push({
							"time" : c41,
							"zone" : 1
						});
					}
					if (c42 != "") {
						cm.push({
							"time" : c42,
							"zone" : 2
						});
					}
					if (c43 != "") {
						cm.push({
							"time" : c43,
							"zone" : 3
						});
					}
					if (c44 != "") {
						cm.push({
							"time" : c44,
							"zone" : 4
						});
					}
					if (c45 != "") {
						cm.push({
							"time" : c45,
							"zone" : 5
						});
					}
					if (c46 != "") {
						cm.push({
							"time" : c46,
							"zone" : 6
						});
					}

					days.push({
						"schedule" : cm
					});
				}
				if ($('#daycs5').prop('checked')) {
					var c51 = $("#clockface_51_modal").val();
					var c52 = $("#clockface_52_modal").val();
					var c53 = $("#clockface_53_modal").val();
					var c54 = $("#clockface_54_modal").val();
					var c55 = $("#clockface_55_modal").val();
					var c56 = $("#clockface_56_modal").val();
					var cm = [];
					if (c51 != "") {
						cm.push({
							"time" : c51,
							"zone" : 1
						});
					}
					if (c52 != "") {
						cm.push({
							"time" : c52,
							"zone" : 2
						});
					}
					if (c53 != "") {
						cm.push({
							"time" : c53,
							"zone" : 3
						});
					}
					if (c54 != "") {
						cm.push({
							"time" : c54,
							"zone" : 4
						});
					}
					if (c55 != "") {
						cm.push({
							"time" : c55,
							"zone" : 5
						});
					}
					if (c56 != "") {
						cm.push({
							"time" : c56,
							"zone" : 6
						});
					}

					days.push({
						"schedule" : cm
					});
				}
				if ($('#daycs6').prop('checked')) {
					var c61 = $("#clockface_61_modal").val();
					var c62 = $("#clockface_62_modal").val();
					var c63 = $("#clockface_63_modal").val();
					var c64 = $("#clockface_64_modal").val();
					var c65 = $("#clockface_65_modal").val();
					var c66 = $("#clockface_66_modal").val();
					var cm = [];
					if (c61 != "") {
						cm.push({
							"time" : c61,
							"zone" : 1
						});
					}
					if (c62 != "") {
						cm.push({
							"time" : c62,
							"zone" : 2
						});
					}
					if (c63 != "") {
						cm.push({
							"time" : c63,
							"zone" : 3
						});
					}
					if (c64 != "") {
						cm.push({
							"time" : c64,
							"zone" : 4
						});
					}
					if (c65 != "") {
						cm.push({
							"time" : c65,
							"zone" : 5
						});
					}
					if (c66 != "") {
						cm.push({
							"time" : c66,
							"zone" : 6
						});
					}

					days.push({
						"schedule" : cm
					});
				}
				if ($('#daycs7').prop('checked')) {
					var c71 = $("#clockface_71_modal").val();
					var c72 = $("#clockface_72_modal").val();
					var c73 = $("#clockface_73_modal").val();
					var c74 = $("#clockface_74_modal").val();
					var c75 = $("#clockface_75_modal").val();
					var c76 = $("#clockface_76_modal").val();
					var cm = [];
					if (c71 != "") {
						cm.push({
							"time" : c71,
							"zone" : 1
						});
					}
					if (c72 != "") {
						cm.push({
							"time" : c72,
							"zone" : 2
						});
					}
					if (c73 != "") {
						cm.push({
							"time" : c73,
							"zone" : 3
						});
					}
					if (c74 != "") {
						cm.push({
							"time" : c74,
							"zone" : 4
						});
					}
					if (c75 != "") {
						cm.push({
							"time" : c75,
							"zone" : 5
						});
					}
					if (c76 != "") {
						cm.push({
							"time" : c76,
							"zone" : 6
						});
					}

					days.push({
						"schedule" : cm
					});
				}
				types.push({
					"type" : "TARIFF_CALENDAR",
					"activationTime" : datesp,
					"seasons" : season,
					"weeks" : weeks,
					"days" : days
				});

			}
		}
		if (jobType == 'METER_COMMAND_GET') {
			if ($("#pcptf").is(":checked")) {
				var pcptfs = $("#pcptfs").val();
				$.each(pcptfs, function(i, v) {
					types.push({
						"type" : "PROFILE_CAPTURE_PERIOD",
						"active" : v,
					});
				});

			}
			if ($("#diptf").is(":checked")) {
				var diptfs = $("#diptfs").val();
				$.each(diptfs, function(i, v) {
					types.push({
						"type" : "DEMAND_INTEGRATION_PERIOD",
						"active" : v,
					});
				});

			}
			if ($("#bptf").is(":checked")) {
				var bptfs = $("#bptfs").val();
				$.each(bptfs, function(i, v) {
					types.push({
						"type" : "BILLING_PERIOD",
						"active" : v,
					});
				});

			}
			if ($("#aftf").is(":checked")) {
				var aftfs = $("#aftfs").val();
				$.each(aftfs, function(i, v) {
					types.push({
						"type" : "ALARM_FILTER",
						"active" : v,
					});
				});

			}
			if ($("#ettf").is(":checked")) {
				var ettfs = $("#ettfs").val();
				$.each(ettfs, function(i, v) {
					types.push({
						"type" : "EVENT_THRESHOLD",
						"active" : v,
					});
				});

			}
			if ($("#toutf").is(":checked")) {
				var toutfs = $("#toutfs").val();
				$.each(toutfs, function(i, v) {
					types.push({
						"type" : "TARIFF_CALENDAR",
						"active" : v,
					});
				});

			}

		}
		commands = {
			"commands" : types
		};
		var jsons = {
			"jobConfiguration" : commands,
			"jobType" : jobType,
			"jobName" : jobName,
			"meterGroup" : meterGroup
		};

		var metersf = JSON.stringify(jsons);

		$('html, body').css("cursor", "wait");
		$
				.ajax({
					url : "./createmeterjobupdate",
					type : 'GET',
					dataType : 'text',
					data : {
						"mtrnos" : metersf
					},
					cache : false,
					success : function(response) {
						//alert(response);
						if (response == "201") {
							$("#alertId").show();
							document.getElementById("alertId").style.display = 'block';
							document.getElementById("spanId").innerHTML = '<span style="color: green">Successfully Created Job</span>';
							meterjoblist();
							return true;
						}

						$("#alertId").show();
						document.getElementById("alertId").style.display = 'block';
						document.getElementById("spanId").innerHTML = '<span style="color: red">Current Job Exist or Server Down</span>';

					}

				});
		$('html, body').css("cursor", "auto");
		resetLoadCurtParams();

	}

	function createmeterjob() {
		var type = $("#type").val();

		//alert(type.length);

		var active = $("#active").val();

		//alert(active);

		var jobType = $("#jobtype").val();
		var jobName = $("#jobname").val();
		var meterGroup = $("#mtrgrpname").val();
		/* commandlist=[];
		
		for(var i=0;i<type.length;i++)
		{
			obj=new Object();
			obj.type=type[i];
			obj.active=active[i];
			commandlist[i]=obj;
		}
		

		   obj2=new Object();
		   obj2.commands=commandlist;
		var jsons={"jobConfiguration":obj2,"jobType":jobType,"jobName":jobName,"meterGroup":meterGroup}; */

		/*var commands2 = new Object();
		commands2.jobType=jobType1;
		commands2.jobName=jobName1;
		commands2.meterGroup=meterGroup1;
		alert(JSON.stringify(commands2));
		 var commands={"type":type,"active":active}; */
		var commands = {};
		var types = []
		for (var i = 0; i < type.length; i++) {
			if (type[i] == 'LOAD_CURTAILMENT') {
				types.push({
					"type" : type[i],
					"active" : active,
					"loadCurtailmentState" : lc_state,
					"powerLimitNormal" : lc_p_limit,
					"powerLimitMinOverThresholdDuration" : lc_limit_od,
					"powerLimitMinUnderThresholdDuration" : lc_limit_ud,
					"alertPeriod" : alert_period,
					"lockoutPeriod" : lockout_prd,
					"lockoutMaxCounter" : lockout_m_counter

				});

			} else {
				types.push({
					"type" : type[i],
					"active" : active

				});
			}
		}
		commands = {
			"commands" : types
		};
		var jsons = {
			"jobConfiguration" : commands,
			"jobType" : jobType,
			"jobName" : jobName,
			"meterGroup" : meterGroup
		};

		var metersf = JSON.stringify(jsons);
		//alert(metersf);
		$('html, body').css("cursor", "wait");
		$
				.ajax({
					url : "./createmeterjob",
					type : 'GET',
					dataType : 'text',
					data : {
						"mtrnos" : metersf
					},
					cache : false,
					success : function(response) {
						//alert(response);
						if (response == "201") {
							$("#alertId").show();
							document.getElementById("alertId").style.display = 'block';
							document.getElementById("spanId").innerHTML = '<span style="color: green">Successfully Created Job</span>';
							meterjoblist();
							return true;
						}

						$("#alertId").show();
						document.getElementById("alertId").style.display = 'block';
						document.getElementById("spanId").innerHTML = '<span style="color: red">Current Job Exist or Server Down</span>';

					}

				});
		$('html, body').css("cursor", "auto");
		resetLoadCurtParams();
	}

	function ondemand() {
		var grpname = $("#grpname").val();
		var grptype = $("#grptype").val();
		var mtrnos = $("#metrno").val();
		var jsons = {
			"meterGroupName" : grpname,
			"meterGroupType" : grptype,
			"meters" : mtrnos
		};
		var metersf = JSON.stringify(jsons);
		// alert(mtrnos);
		$
				.ajax({
					url : "./createmetergroup",
					type : 'GET',
					dataType : 'json',
					data : {
						"mtrnos" : metersf
					},
					cache : false,
					success : function(response) {

						if (response == "201") {
							$("#alertId").show();
							document.getElementById("alertId").style.display = 'block';
							document.getElementById("spanId").innerHTML = '<span style="color: green">Successfully Created Group</span>';
							metergrouplist();
							return true;
						}

						$("#alertId").show();
						document.getElementById("alertId").style.display = 'block';
						document.getElementById("spanId").innerHTML = '<span style="color: red">Current Group Exist or Server Down</span>';

					}

				});
	}
</script>
<div class="page-content">

	<div id="alertId" class="alert alert-warning display-show"
		style="display: none">
		<button class="close" data-close="alert"></button>
		<span id="spanId"></span>
	</div>
	<div class="row">
		<div class="col-md-12">

			<div class="portlet-body">

				<table>
					<tr>
						<td>SELECT :</td>
						<td><select id="api" class="form-control placeholder-no-fix"
							autocomplete="off" placeholder="" onchange="selectingoptions()">
								<option value="0"></option>
								<option value="metergroup">Create Meter Group</option>
								<option value="meterjob">Create Meter Job</option>
								<option value="managejob">Manage Meter Job</option>
								<option value="Queryjob">Query Meter Job</option>
								<option value="MeterStatusForJob">Meter Status for Job</option>
						</select></td>
					</tr>
					<tr id="tf">
						<td>SELECT :</td>
						<td><select id="boolean"
							class="form-control placeholder-no-fix" autocomplete="off"
							placeholder="">
								<option value="0"></option>
								<option value="true">True</option>
								<option value="False">False</option>
						</select></td>
					</tr>
				</table>
				<div class="modal-footer">
					<button class="btn blue pull-left" onclick="allapis()">Submit</button>

				</div>

			</div>



			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box blue" hidden="true" id="createMtrgrp">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>Create Meter Group
					</div>
					<div class="tools">


						<a href="javascript:;" class="collapse"></a> <a
							href="#portlet-config" data-toggle="modal" class="config"></a> <a
							href="javascript:;" class="reload"></a> <a href="javascript:;"
							class="remove"></a>

					</div>
				</div>

				<div class="portlet-body">

					<%-- <form > --%>
					<table>
						<tr>
							<td>SELECT METER GROUP NAME :</td>
							<td><input type="text" name="grpname" id="grpname"
								class="form-control"></td>
						</tr>
						<tr>
							<td>SELECT METER GROUP TYPE :</td>
							<td><select id="grptype"
								class="form-control placeholder-no-fix" type="text"
								autocomplete="off" placeholder="" name="event">
									<option value="0"></option>
									<option value="STATIC">STATIC</option>
							</select></td>
						<tr>
							<td>Select METER NUMBERS :</td>
							<td>
								<!-- <input type="text" name="metrno" id="metrno"  class="form-control"> -->
								<select
								class="js-example-basic-multiple placeholder-no-fix input-large"
								id="metrno" multiple="multiple" autofocus="autofocus">
									<!-- <option value="8000003">8000003</option>
												<option value="8000004">8000004</option> -->
									<c:forEach items="${meters}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>

							</select>
							</td>
						</tr>
					</table>
					<div class="modal-footer">
						<button class="btn blue pull-left" onclick="ondemand()">Submit</button>

					</div>
				</div>
			</div>

			<div class="portlet box blue" style="display: none;" id="managejob">

				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>Manage Meter Job
					</div>
					<div class="tools">


						<a href="javascript:;" class="collapse"></a> <a
							href="#portlet-config" data-toggle="modal" class="config"></a> <a
							href="javascript:;" class="reload"></a> <a href="javascript:;"
							class="remove"></a>

					</div>
				</div>

				<div class="portlet-body">

					<%-- <form > --%>
					<table>
						<tr>
							<td>JOB NAME:</td>
							<td><select id="managemeterjobid" name="jobname"
								class="form-control placeholder-no-fix select2me" type="text"
								autocomplete="off" placeholder="" style="width: 300px;">


							</select></td>
						</tr>

					</table>
					<div class="modal-footer">
						<button class="btn blue pull-left" onclick="managemeterjob()">Submit</button>

					</div>
				</div>
			</div>


			<div class="portlet box blue" hidden="true" id="queryjob">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>To Query Meter Job
					</div>
					<div class="tools">


						<a href="javascript:;" class="collapse"></a> <a
							href="#portlet-config" data-toggle="modal" class="config"></a> <a
							href="javascript:;" class="reload"></a> <a href="javascript:;"
							class="remove"></a>

					</div>
				</div>

				<div class="portlet-body">

					<%-- <form > --%>
					<table>
						<tr>
							<td>JOB NAME:</td>
							<td><select id="jobnameQ" name="jobname"
								class="form-control placeholder-no-fix select2me" type="text"
								autocomplete="off" placeholder="" style="width: 300px;">


							</select> <!-- <input type="text" name="jobname" id="jobnameQ"
								class="form-control"> --></td>
						</tr>

					</table>
					<div class="modal-footer">
						<button class="btn blue pull-left" onclick="querymeterjob()">Submit</button>

					</div>
				</div>
			</div>


			<div class="portlet box blue" hidden="true" id="meterstatus">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>Meter Status For Job
					</div>
					<div class="tools">


						<a href="javascript:;" class="collapse"></a> <a
							href="#portlet-config" data-toggle="modal" class="config"></a> <a
							href="javascript:;" class="reload"></a> <a href="javascript:;"
							class="remove"></a>

					</div>
				</div>

				<div class="portlet-body">

					<%-- <form > --%>
					<table>
						<tr>
							<td>JOB NAME:</td>
							<td><select id="jobnamestatus" name="jobnamestatus"
								class="form-control placeholder-no-fix select2me" type="text"
								autocomplete="off" placeholder="" style="width: 300px;">
									<option value="" disabled="disabled" selected="selected">Select</option>

							</select> <!-- <select  name="jobnamestatus" id="jobnamestatus" class="form-control" onclick="joblist()">
								
								</select> --></td>


							<td>COUNT:</td>
							<td><input type="text" name="count" id="count"
								class="form-control" placeholder="111"></td>

							<td>DEVICE STATUS:</td>
							<td><input type="text" name="device" id="device"
								class="form-control" placeholder="SUCCESS"></td>


							<td>START ID:</td>
							<td><input type="text" name="startid" id="startid"
								class="form-control" placeholder="1"></td>

						</tr>

					</table>
					<div class="modal-footer">
						<button class="btn blue pull-left" onclick="meterstatus()">Submit</button>

					</div>
				</div>
			</div>

			<div class="portlet box blue" hidden="true" id="createMtrjob">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>Create Meter Job
					</div>
					<div class="tools">


						<a href="javascript:;" class="collapse"></a> <a
							href="#portlet-config" data-toggle="modal" class="config"></a> <a
							href="javascript:;" class="reload"></a> <a href="javascript:;"
							class="remove"></a>

					</div>
				</div>

				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form class="form-horizontal">
						<div class="form-body">
							<h3 class="form-section">Job Creation</h3>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">Job Name</label>
										<div class="col-md-9">
											<input type="text" class="form-control" id="meterjobid"
												placeholder="job123">
											<!-- <span class="help-block">This is inline help</span> -->
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-6"></div>
								<!--/span-->
							</div>
							<!--/row-->
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">Meter Group</label>
										<div class="col-md-9">
											<select id="mtrgrpname"
												class="form-control placeholder-no-fix select2me"
												type="text" autocomplete="off" placeholder="">
												<%-- <option value="" disabled="disabled" selected="selected">Select</option>
									<c:forEach var="elements" items="${groupList}">
										<option value="${elements}">${elements}</option>
									</c:forEach> --%>
											</select>
											<!-- <span class="help-block">Select your gender.</span> -->
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-6"></div>
								<!--/span-->
							</div>
							<!--/row-->
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">Job Type</label>
										<div class="col-md-9">
											<select class="form-control placeholder-no-fix select2me"
												id="jobtype" autofocus="autofocus"
												onchange="meterCommandSetOrGet()">
												<option value="0">Select job</option>
												<option value="METER_COMMAND_GET">METER_COMMAND_GET</option>
												<option value="METER_COMMAND_SET">METER_COMMAND_SET</option>
											</select>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-6"></div>
								<!--/span-->
							</div>
							<h3 class="form-section">Types</h3>
							<!--/table1-->
							<table class="table table-striped table-bordered table-hover"
								id="sample_1" style="display: none">
								<thead>
									<tr>
										<th class="table-checkbox"></th>
										<th>Type</th>
										<th>Activation Time</th>
										<th>Profile Type</th>
										<th>CP/DP/DOM/FM</th>


									</tr>
								</thead>
								<tbody>



									<tr class="odd gradeX">
										<td><input type="checkbox" class="checkboxes" value="1"
											id="pcpbcheckid" /></td>
										<td>Profile Capture Period</td>
										<td><div class="input-group date form_datetime">
												<input type="text" size="16" id="pcpbtid" readonly
													class="form-control"> <span class="input-group-btn">
													<button class="btn default date-reset" type="button">
														<i class="fa fa-times"></i>
													</button>
												</span> <span class="input-group-btn">
													<button class="btn default date-set" type="button">
														<i class="fa fa-calendar"></i>
													</button>
												</span>
											</div></td>
										<td><a href="#">Block Load</a></td>
										<td class="center"><div class="form-group">

												<div class="col-md-12">
													<select class="form-control input-sm" id="pcpblid">
														<!-- <option value="900">15Min</option> -->
														<option value="1800">30Min</option>
														<option value="3600">1Hr</option>
														<option value="7200">2Hr</option>
														<option value="14400">3Hr</option>

													</select>
												</div>
											</div></td>


									</tr>
									<tr class="odd gradeX">
										<td><input type="checkbox" class="checkboxes" value="1"
											id="pcpinscheckid" /></td>
										<td>Profile Capture Period</td>
										<td><div class="input-group date form_datetime">
												<input type="text" size="16" readonly class="form-control"
													id="pcpitid"> <span class="input-group-btn">
													<button class="btn default date-reset" type="button">
														<i class="fa fa-times"></i>
													</button>
												</span> <span class="input-group-btn">
													<button class="btn default date-set" type="button">
														<i class="fa fa-calendar"></i>
													</button>
												</span>
											</div></td>
										<td><a href="#">Instantaneous</a></td>
										<td class="center"><div class="form-group">

												<div class="col-md-12">
													<select class="form-control input-sm" id="pcpilid">
														<!-- <option value="900">15Min</option> -->
														<option value="1800">30Min</option>
														<option value="3600">1Hr</option>
														<option value="7200">2Hr</option>
														<option value="14400">3Hr</option>

													</select>
												</div>
											</div></td>


									</tr>

									<tr class="odd gradeX">
										<td><input type="checkbox" class="checkboxes" value="1"
											id="dipcheckid" /></td>
										<td>Demand Integration Period</td>
										<td><div class="input-group date form_datetime">
												<input type="text" size="16" readonly class="form-control"
													id="diptid"> <span class="input-group-btn">
													<button class="btn default date-reset" type="button">
														<i class="fa fa-times"></i>
													</button>
												</span> <span class="input-group-btn">
													<button class="btn default date-set" type="button">
														<i class="fa fa-calendar"></i>
													</button>
												</span>
											</div></td>
										<td>&nbsp;</td>
										<td class="center"><div class="form-group">

												<div class="col-md-12">
													<select class="form-control input-sm" id="diplid">
														<!-- <option value="900">15Min</option> -->
														<option value="1800">30Min</option>
														<option value="3600">1Hr</option>
														<option value="7200">2Hr</option>
														<option value="14400">3Hr</option>

													</select>
												</div>
											</div></td>

									</tr>
									<tr class="odd gradeX">
										<td><input type="checkbox" class="checkboxes" value="1"
											id="bpcheckid" /></td>
										<td>Billing Period</td>
										<td><div class="input-group date form_datetime">
												<input type="text" size="16" readonly class="form-control"
													id="bptid"> <span class="input-group-btn">
													<button class="btn default date-reset" type="button">
														<i class="fa fa-times"></i>
													</button>
												</span> <span class="input-group-btn">
													<button class="btn default date-set" type="button">
														<i class="fa fa-calendar"></i>
													</button>
												</span>
											</div></td>
										<td>Day Of Month</td>

										<td><div class="form-group">

												<div class="col-md-12">
													<select class="form-control input-sm" id="bpdid">
														<option>1</option>
														<option>2</option>
														<option>3</option>
														<option>4</option>
														<option>5</option>
														<option>6</option>
														<option>7</option>
														<option>8</option>
														<option>9</option>
														<option>10</option>
														<option>11</option>
														<option>12</option>
														<option>13</option>
														<option>14</option>
														<option>15</option>
														<option>16</option>
														<option>17</option>
														<option>18</option>
														<option>19</option>
														<option>20</option>
														<option>21</option>
														<option>22</option>
														<option>23</option>
														<option>24</option>
														<option>25</option>
														<option>26</option>
														<option>27</option>
														<option>28</option>

													</select>
												</div>
											</div></td>

									</tr>
									<tr class="odd gradeX">
										<td><input type="checkbox" class="checkboxes" value="1"
											id="alafilid" /></td>
										<td>Alarm Filter</td>
										<td><div class="input-group date form_datetime">
												<input type="text" size="16" readonly class="form-control"
													id="alafiltid"> <span class="input-group-btn">
													<button class="btn default date-reset" type="button">
														<i class="fa fa-times"></i>
													</button>
												</span> <span class="input-group-btn">
													<button class="btn default date-set" type="button">
														<i class="fa fa-calendar"></i>
													</button>
												</span>
											</div></td>
										<td>&nbsp;</td>
										<td class="center"><div class="form-group">

												<div class="col-md-12">
													<select
														class="js-example-basic-multiple placeholder-no-fix form-control input-sm"
														id="alarmeventsid" multiple="multiple"
														autofocus="autofocus">

														<option value="METER_PHASE_MISSING">METER PHASE
															MISSING</option>
														<option value="METER_OVER_VOLTAGE">METER OVER
															VOLTAGE</option>
														<option value="METER_UNDER_VOLTAGE">METER UNDER
															VOLTAGE</option>
														<option value="METER_VOLT_UNBALANCE">METER VOLT
															UNBALANCE</option>
														<option value="METER_CURRENT_REVERSE">METER
															CURRENT REVERSE</option>
														<option value="METER_CURRENT_UNBALANCE">METER
															CURRENT UNBALANCE</option>
														<option value="METER_CURRENT_BYPASS">METER
															CURRENT BYPASS</option>
														<option value="METER_OVER_CURRENT">METER OVER
															CURRENT</option>
														<option value="METER_LOW_PF">METER LOW PF</option>
														<option value="METER_EARTH_LEAKAGE">METER EARTH
															LEAKAGE</option>
														<option value="METER_MAGNETIC_INFLUENCE">METER
															MAGNETIC INFLUENCE</option>
														<option value="METER_NEUTRAL_DISTURBANCE">METER
															NEUTRAL DISTURBANCE</option>
														<option value="METER_COVER_OPEN">METER COVER OPEN</option>
														<option value="METER_CONNECT_DISCONNECT">METER
															CONNECT DISCONNECT</option>
														<option value="METER_LAST_GASP">METER LAST GASP</option>
														<option value="METER_FIRST_BREATH">METER FIRST
															BREATH</option>
														<option value="METER_BILLING_INCREMENT">METER
															BILLING INCREMENT</option>
														<option value="METER_PHASE_OPEN">METER PHASE OPEN</option>
														<option value="METER_SWITCH_WELD">METER SWITCH
															WELD</option>
														<option value="METER_NEUTRAL_MISS">METER NEUTRAL
															MISS</option>
														<option value="METER_OVER_LOAD">METER OVER LOAD</option>


													</select>
												</div>
											</div></td>

									</tr>
									<tr class="odd gradeX">
										<td><input type="checkbox" class="checkboxes" value="1"
											id="evethaid" /></td>
										<td>Event Threshold</td>
										<td><div class="input-group date form_datetime">
												<input type="text" size="16" readonly class="form-control"
													id="evethatid"> <span class="input-group-btn">
													<button class="btn default date-reset" type="button">
														<i class="fa fa-times"></i>
													</button>
												</span> <span class="input-group-btn">
													<button class="btn default date-set" type="button">
														<i class="fa fa-calendar"></i>
													</button>
												</span>
											</div></td>
										<td>&nbsp;</td>
										<td>


											<form class="form-horizontal" role="form">
												<div class="form-body">

													<div class="form-group">
														<label class="col-md-3 control-label"><div id=""
																class=" activeper popovers" data-percent="10"
																data-container="body" data-trigger="hover"
																data-placement="top"
																data-content="Voltage High Limit Normal">
																<span id="">VHLN</span>
															</div></label>
														<div class="col-md-9">
															<input type="text" class="form-control input-sm"
																id="vhlnid" placeholder="260000">
														</div>
													</div>
													<div class="form-group">
														<label class="col-md-3 control-label"><div id=""
																class=" activeper popovers" data-percent="10"
																data-container="body" data-trigger="hover"
																data-placement="top"
																data-content="Voltage High Limit MinOver Threshold Duration">
																<span id="">VHLMTD</span>
															</div></label>
														<div class="col-md-9">
															<input type="text" class="form-control input-sm"
																id="vhlmtd1id" placeholder="60000">
														</div>
													</div>
													<div class="form-group">
														<label class="col-md-3 control-label"><div id=""
																class=" activeper popovers" data-percent="10"
																data-container="body" data-trigger="hover"
																data-placement="top"
																data-content="Voltage High Limit MinUnder Threshold Duration">
																<span id="">VHLMTD</span>
															</div></label>
														<div class="col-md-9">
															<input type="text" class="form-control input-sm"
																id="vhlmtd2id" placeholder="60000">
														</div>
													</div>
													<div class="form-group">
														<label class="col-md-3 control-label"><div id=""
																class=" activeper popovers" data-percent="10"
																data-container="body" data-trigger="hover"
																data-placement="top"
																data-content="Voltage Low Limit Normal">
																<span id="">VLLN</span>
															</div></label>
														<div class="col-md-9">
															<input type="text" class="form-control input-sm"
																id="vllnid" placeholder="200000">
														</div>
													</div>
													<div class="form-group">
														<label class="col-md-3 control-label"><div id=""
																class=" activeper popovers" data-percent="10"
																data-container="body" data-trigger="hover"
																data-placement="top"
																data-content="Voltage Low Limit MinOver Threshold Duration">
																<span id="">VLLMTD</span>
															</div></label>
														<div class="col-md-9">
															<input type="text" class="form-control input-sm"
																id="vllmtd1id" placeholder="60000">
														</div>
													</div>
													<div class="form-group">
														<label class="col-md-3 control-label"><div id=""
																class=" activeper popovers" data-percent="10"
																data-container="body" data-trigger="hover"
																data-placement="top"
																data-content="Voltage Low Limit MinUnder Threshold Duration">
																<span id="">VLLMTD</span>
															</div></label>
														<div class="col-md-9">
															<input type="text" class="form-control input-sm"
																id="vllmtd2id" placeholder="60000">
														</div>
													</div>
												</div>

											</form>
										</td>

									</tr>
									<tr class="odd gradeX">
										<td><input type="checkbox" class="checkboxes" value="1"
											id="touid" /></td>
										<td>Time Of Use</td>
										<td colspan="3">
											<div>
												<div class="input-group date form_datetime">
													<input type="text" size="64" readonly class="form-control"
														id="toudateid"> <span class="input-group-btn">
														<button class="btn default date-reset" type="button">
															<i class="fa fa-times"></i>
														</button>
													</span> <span class="input-group-btn">
														<button class="btn default date-set" type="button">
															<i class="fa fa-calendar"></i>
														</button>
													</span>
												</div>
											</div> <!-- BEGIN EXAMPLE TABLE PORTLET-->
											<div class="portlet box purple">
												<div class="portlet-title">
													<div class="caption">
														<i class="fa fa-calendar"></i>Seasons
													</div>

												</div>
												<div class="portlet-body">

													<table
														class="table table-striped table-bordered table-hover"
														id="seid">
														<thead>
															<tr>
																<th>Season Selection</th>
																<th>Month</th>
																<th>Day of Month</th>
																<th>Week</th>
															</tr>
														</thead>
														<tbody>
															<tr class="odd gradeX">
																<td><input type="checkbox" class="checkboxes"
																	value="1" id="seasoncid1" onclick="monthsel()" /></td>

																<td><select class="form-control input-sm"
																	id="monthid1" onchange="monthdays()">
																</select></td>
																<td><select class="form-control input-sm"
																	id="monthdid1">


																</select></td>
																<td><select class="form-control input-sm"
																	onclick="weeksselt()" id="wstid1">




																</select></td>
															</tr>
															<tr class="odd gradeX">
																<td><input type="checkbox" class="checkboxes"
																	value="1" id="seasoncid2" /></td>

																<td><select class="form-control input-sm"
																	id="monthid2" onchange="monthdays()">
																</select></td>
																<td><select class="form-control input-sm"
																	id="monthdid2">

																</select></td>
																<td><select class="form-control input-sm"
																	onclick="weeksselt()" id="wstid2">



																</select></td>
															</tr>
															<tr class="odd gradeX">
																<td><input type="checkbox" class="checkboxes"
																	value="1" id="seasoncid3" /></td>

																<td><select class="form-control input-sm"
																	id="monthid3" onchange="monthdays()">
																</select></td>
																<td><select class="form-control input-sm"
																	id="monthdid3">

																</select></td>
																<td><select class="form-control input-sm"
																	onclick="weeksselt()" id="wstid3">




																</select></td>
															</tr>


														</tbody>
													</table>
												</div>
											</div>
											<div class="portlet box purple">
												<div class="portlet-title">
													<div class="caption">
														<i class="fa fa-calendar"></i>Weeks
													</div>
													<!-- <div class="actions">
														<a href="#" class="btn green"><i class="fa fa-plus"></i>
															Add</a>

													</div> -->
												</div>
												<div class="portlet-body">
													<table
														class="table table-striped table-bordered table-hover"
														id="weid">
														<thead>
															<tr>
																<th colspan="2">Week Number</th>
																<th>Day(M)</th>
																<th>Day(T)</th>
																<th>Day(W)</th>
																<th>Day(T)</th>
																<th>Day(F)</th>
																<th>Day(S)</th>
																<th>Day(S)</th>
															</tr>
														</thead>
														<tbody>
															<tr class="odd gradeX">
																<td><input type="checkbox" class="checkboxes"
																	value="1" id="wc1" onclick="weekssel()" /></td>
																<td>0</td>
																<td><select class="form-control input-sm daysc"
																	id="wc1d1">

																</select></td>
																<td><select class="form-control input-sm daysc"
																	id="wc1d2">

																</select></td>
																<td><select class="form-control input-sm daysc"
																	id="wc1d3">

																</select></td>
																<td><select class="form-control input-sm daysc"
																	id="wc1d4">

																</select></td>
																<td><select class="form-control input-sm daysc"
																	id="wc1d5">

																</select></td>
																<td><select class="form-control input-sm daysc"
																	id="wc1d6">

																</select></td>
																<td><select class="form-control input-sm daysc"
																	id="wc1d7">

																</select></td>
															</tr>
															<tr class="odd gradeX">
																<td><input type="checkbox" class="checkboxes"
																	value="1" id="wc2" onclick="weekssel()" /></td>
																<td>1</td>
																<td><select class="form-control input-sm daysc"
																	id="wc2d1">

																</select></td>
																<td><select class="form-control input-sm daysc"
																	id="wc2d2">

																</select></td>
																<td><select class="form-control input-sm daysc"
																	id="wc2d3">

																</select></td>
																<td><select class="form-control input-sm daysc"
																	id="wc2d4">

																</select></td>
																<td><select class="form-control input-sm daysc"
																	id="wc2d5">

																</select></td>
																<td><select class="form-control input-sm daysc"
																	id="wc2d6">

																</select></td>
																<td><select class="form-control input-sm daysc"
																	id="wc2d7">

																</select></td>
															</tr>
															<tr class="odd gradeX">
																<td><input type="checkbox" class="checkboxes"
																	value="1" id="wc3" onclick="weekssel()" /></td>
																<td>2</td>
																<td><select class="form-control input-sm daysc"
																	id="wc3d1">

																</select></td>
																<td><select class="form-control input-sm daysc"
																	id="wc3d2">

																</select></td>
																<td><select class="form-control input-sm daysc"
																	id="wc3d3">

																</select></td>
																<td><select class="form-control input-sm daysc"
																	id="wc3d4">

																</select></td>
																<td><select class="form-control input-sm daysc"
																	id="wc3d5">

																</select></td>
																<td><select class="form-control input-sm daysc"
																	id="wc3d6">

																</select></td>
																<td><select class="form-control input-sm daysc"
																	id="wc3d7">

																</select></td>
															</tr>

														</tbody>
													</table>
												</div>
											</div>
											<div class="portlet box purple">
												<div class="portlet-title">
													<div class="caption">
														<i class="fa fa-calendar"></i>Days
													</div>
													<!-- <div class="actions" onclick="addDays()">
														<a class="btn green"><i class="fa fa-plus"></i> Add</a>

													</div> -->
												</div>
												<div class="portlet-body">
													<table
														class="table table-striped table-bordered table-hover"
														id="tzid">
														<thead>
															<tr class="align-middle">
																<th></th>
																<th class="midali">TZ1</th>
																<th>TZ2</th>
																<th>TZ3</th>
																<th>TZ4</th>
																<th>TZ5</th>
																<th>TZ6</th>
															</tr>

														</thead>
														<tbody id="groupDaysid">

															<tr class="odd gradeX">
																<td><input type="checkbox" class="checkboxes"
																	value="1" id="daycs1" class=""
																	onclick="weekdaysvalset()" /></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_11_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_11_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_12_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_12_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_13_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_13_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>

																<td><div class="input-group">
																		<input type="text" id="clockface_14_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_14_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_15_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_15_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>

																<td><div class="input-group">
																		<input type="text" id="clockface_16_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_16_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>
															</tr>
															<tr class="odd gradeX">
																<td><input type="checkbox" class="checkboxes"
																	value="1" id="daycs2" class=""
																	onclick="weekdaysvalset()" /></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_21_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_21_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_22_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_22_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_23_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_23_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>

																<td><div class="input-group">
																		<input type="text" id="clockface_24_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_24_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_25_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_25_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>

																<td><div class="input-group">
																		<input type="text" id="clockface_26_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_26_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>
															</tr>
															<tr class="odd gradeX">
																<td><input type="checkbox" class="checkboxes"
																	value="1" id="daycs3" class=""
																	onclick="weekdaysvalset()" /></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_31_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_31_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_32_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_32_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_33_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_33_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>

																<td><div class="input-group">
																		<input type="text" id="clockface_34_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_34_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_35_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_35_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>

																<td><div class="input-group">
																		<input type="text" id="clockface_36_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_36_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>
															</tr>
															<tr class="odd gradeX">
																<td><input type="checkbox" class="checkboxes"
																	value="1" id="daycs4" class=""
																	onclick="weekdaysvalset()" /></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_41_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_41_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_42_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_42_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_43_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_43_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>

																<td><div class="input-group">
																		<input type="text" id="clockface_44_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_44_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_45_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_45_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>

																<td><div class="input-group">
																		<input type="text" id="clockface_46_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_46_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>
															</tr>
															<tr class="odd gradeX">
																<td><input type="checkbox" class="checkboxes"
																	value="1" id="daycs5" class=""
																	onclick="weekdaysvalset()" /></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_51_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_51_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_52_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_52_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_53_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_53_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>

																<td><div class="input-group">
																		<input type="text" id="clockface_54_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_54_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_55_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_55_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>

																<td><div class="input-group">
																		<input type="text" id="clockface_56_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_56_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>
															</tr>
															<tr class="odd gradeX">
																<td><input type="checkbox" class="checkboxes"
																	value="1" id="daycs6" class=""
																	onclick="weekdaysvalset()" /></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_61_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_61_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_62_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_62_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_63_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_63_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>

																<td><div class="input-group">
																		<input type="text" id="clockface_64_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_64_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_65_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_65_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>

																<td><div class="input-group">
																		<input type="text" id="clockface_66_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_66_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>
															</tr>
															<tr class="odd gradeX">
																<td><input type="checkbox" class="checkboxes"
																	value="1" id="daycs7" class=""
																	onclick="weekdaysvalset()" /></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_71_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_71_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_72_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_72_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_73_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_73_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>

																<td><div class="input-group">
																		<input type="text" id="clockface_74_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_74_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>


																<td><div class="input-group">
																		<input type="text" id="clockface_75_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_75_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>

																<td><div class="input-group">
																		<input type="text" id="clockface_76_modal" value=""
																			class="form-control" readonly="" /> <span
																			class="input-group-btn">
																			<button class="btn default" type="button"
																				id="clockface_76_modal_toggle">
																				<i class="fa fa-clock-o"></i>
																			</button>
																		</span>
																	</div></td>
															</tr>


														</tbody>
													</table>
												</div>
											</div> <!-- END EXAMPLE TABLE PORTLET-->

										</td>



									</tr>
								</tbody>
							</table>
							<!--/table2-->
							<table class="table table-striped table-bordered table-hover"
								id="sample_1n" style="display: none">
								<thead>
									<tr>
										<th class="table-checkbox"></th>
										<th>Type</th>
										<th>Active</th>




									</tr>
								</thead>
								<tbody>
									<tr class="odd gradeX">
										<td><input type="checkbox" class="checkboxes" value="1"
											id="pcptf" /></td>
										<td>Profile Capture Period</td>
										<td><select
											class="js-example-basic-multiple placeholder-no-fix input-large"
											id="pcptfs" multiple="multiple" autofocus="autofocus">

												<option value="True">True</option>
												<option value="False">False</option>

										</select></td>
									</tr>
									<tr class="odd gradeX">
										<td><input type="checkbox" class="checkboxes" value="1"
											id="diptf" /></td>
										<td>Demand Integration period</td>
										<td><select
											class="js-example-basic-multiple placeholder-no-fix input-large"
											id="diptfs" multiple="multiple" autofocus="autofocus">

												<option value="True">True</option>
												<option value="False">False</option>

										</select></td>
									</tr>
									<tr class="odd gradeX">
										<td><input type="checkbox" class="checkboxes" value="1"
											id="bptf" /></td>
										<td>Billing Period</td>
										<td><select
											class="js-example-basic-multiple placeholder-no-fix input-large"
											id="bptfs" multiple="multiple" autofocus="autofocus">

												<option value="True">True</option>
												<option value="False">False</option>

										</select></td>
									</tr>
									<tr class="odd gradeX">
										<td><input type="checkbox" class="checkboxes" value="1"
											id="aftf" /></td>
										<td>Alarm Filter</td>
										<td><select
											class="js-example-basic-multiple placeholder-no-fix input-large"
											id="aftfs" multiple="multiple" autofocus="autofocus">

												<option value="True">True</option>
												<option value="False">False</option>

										</select></td>
									</tr>
									<tr class="odd gradeX">
										<td><input type="checkbox" class="checkboxes" value="1"
											id="ettf" /></td>
										<td>Event Threshold</td>
										<td><select
											class="js-example-basic-multiple placeholder-no-fix input-large"
											id="ettfs" multiple="multiple" autofocus="autofocus">

												<option value="True">True</option>
												<option value="False">False</option>

										</select></td>
									</tr>
									<tr class="odd gradeX">
										<td><input type="checkbox" class="checkboxes" value="1"
											id="toutf" /></td>
										<td>Time Of Use</td>
										<td><select
											class="js-example-basic-multiple placeholder-no-fix input-large"
											id="toutfs" multiple="multiple" autofocus="autofocus">

												<option value="True">True</option>
												<option value="False">False</option>

										</select></td>
									</tr>
								</tbody>
							</table>
							<!--/row-->

						</div>
						<div class="form-actions fluid">
							<div class="row">
								<div class="col-md-6"></div>
								<div class="col-md-6">
									<div class="col-md-offset-8 col-md-12">
										<button type="button" class="btn green"
											onclick="createmeterjobupdate()">Submit</button>
										<button type="button" class="btn default">Cancel</button>
									</div>
								</div>
							</div>
						</div>
					</form>
					<!-- END FORM-->



				</div>
























			</div>
		</div>
		<div class="row" style="display: none" id="mgdid">
			<div class="col-md-12">
				<!-- BEGIN EXAMPLE TABLE PORTLET-->
				<div class="portlet box blue">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-globe"></i>Meter Groups
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a> <a
								href="javascript:;" class="reload"></a> <a href="javascript:;"
								class="remove"></a>
						</div>
					</div>

					<div class="portlet-body">


						<!-- <div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_3', 'Total Meters')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
 -->

						<BR> <BR>
						<!-- <div class="table-scrollable">	 -->
						<table class="table table-striped table-bordered table-hover"
							id="sample_3">
							<thead>
								<tr>
									<th>S.no</th>
									<th>Meter Group Name</th>
									<th>Meter Group Meters</th>
									<th>Created Time</th>
									<th></th>
								</tr>
							</thead>
							<tbody id="mgid">


							</tbody>
						</table>

					</div>
				</div>
			</div>
		</div>
		<div class="row" style="display: none" id="mjdid">
			<div class="col-md-12">
				<!-- BEGIN EXAMPLE TABLE PORTLET-->
				<div class="portlet box blue">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-globe"></i>Meter Jobs
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a> <a
								href="javascript:;" class="reload"></a> <a href="javascript:;"
								class="remove"></a>
						</div>
					</div>

					<div class="portlet-body">


						<div class="table-toolbar">
							<div class="btn-group pull-right" style="margin-top: 15px;">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
									<li><a href="#" id="excelExport"
										onclick="tableToExcel('sample_3', 'Total Meters')">Export
											to Excel</a></li>
								</ul>
							</div>
						</div>


						<BR> <BR>
						<!-- <div class="table-scrollable">	 -->
						<table class="table table-striped table-bordered table-hover"
							id="sample_4">
							<thead>
								<tr>
									<th>S.no</th>
									<th>Job Type</th>
									<th>Job Name</th>
									<th>Meter Group</th>
									<th>Created Time</th>

								</tr>
							</thead>
							<tbody id="mjid">


							</tbody>
						</table>

					</div>
				</div>
			</div>
		</div>
		<div class="row" id="mqjid" style="display: none">
			<div class="col-md-12">
				<!-- BEGIN EXAMPLE TABLE PORTLET-->
				<div class="portlet box blue">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-globe"></i>Query Data
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a> <a
								href="javascript:;" class="reload"></a> <a href="javascript:;"
								class="remove"></a>
						</div>
					</div>

					<div class="portlet-body">


						<div class="table-toolbar"></div>


						<BR> <BR>
						<!-- <div class="table-scrollable">	 -->
						<table class="table table-striped table-bordered table-hover"
							id="sample_5">

							<tbody id="mqjbid">
								<tr>
									<td class="hid">Job Name</td>
									<td id="jn"></td>
								</tr>
								<tr>
									<td class="hid">Meter Group</td>
									<td id="mg"></td>
								</tr>
								<tr>
									<td class="hid">Scheduled Time</td>
									<td id="st"></td>
								</tr>
								<tr>
									<td class="hid">Timeout Time</td>
									<td id="tt"></td>
								</tr>
								<tr>
									<td class="hid">Create Time</td>
									<td id="ct"></td>
								</tr>
								<tr>
									<td class="hid">Update Time</td>
									<td id="ut"></td>
								</tr>
								<tr>
									<td class="hid">Status</td>
									<td id="sta"></td>
								</tr>
								<tr>
									<td class="hid">Job Type</td>
									<td id="jt"></td>
								</tr>
								<tr>
									<td class="hid">Total Processed Devices</td>
									<td id="tpd"></td>
								</tr>
								<tr>
									<td class="hid">Total Successful Devices</td>
									<td id="tsd"></td>
								</tr>
								<tr>
									<td class="hid">Total Failed Devices</td>
									<td id="tfd"></td>
								</tr>

								<table class="table table-striped table-bordered table-hover "
									id="sample_6" style="display: none">
									<thead>
										<tr>
											<th colspan="5" style="text-align: center;">Config
												Commands(PC/DIP/BP)</th>
										</tr>
										<tr>
											<th>Type</th>
											<th>Activation Time</th>
											<th>Profile Type</th>
											<th>CP/DP</th>
											<th>Day Of The Month</th>
										</tr>
									</thead>
									<tbody id="mqjbbid">




									</tbody>
								</table>
								<table class="table table-striped table-bordered table-hover "
									id="sample_7" style="display: none">
									<thead>
										<tr>
											<th colspan="3" style="text-align: center;">Config
												Commands(Alarm)</th>
										</tr>
										<tr>
											<th>Type</th>
											<th>Activation Time</th>
											<th>Command</th>

										</tr>
									</thead>
									<tbody id="alarmqjbbid">




									</tbody>
								</table>
								<table class="table table-striped table-bordered table-hover "
									id="sample_8" style="display: none">
									<thead>
										<tr>
											<th colspan="8" style="text-align: center;">Config
												Commands(Event Threshold)</th>
										</tr>
										<tr>
											<th>Type</th>
											<th>Activation Time</th>
											<th><div id="" class=" activeper popovers"
													data-percent="10" data-container="body"
													data-trigger="hover" data-placement="top"
													data-content="Voltage High Limit Normal">
													<span id="">VHLN</span>
												</div></th>
											<th><div id="" class=" activeper popovers"
													data-percent="10" data-container="body"
													data-trigger="hover" data-placement="top"
													data-content="Voltage High Limit MinOver Threshold Duration">
													<span id="">VHLMTD</span>
												</div></th>
											<th><div id="" class=" activeper popovers"
													data-percent="10" data-container="body"
													data-trigger="hover" data-placement="top"
													data-content="Voltage High Limit MinUnder Threshold Duration">
													<span id="">VHLMTD</span>
												</div></th>
											<th><div id="" class=" activeper popovers"
													data-percent="10" data-container="body"
													data-trigger="hover" data-placement="top"
													data-content="Voltage Low Limit Normal">
													<span id="">VLLN</span>
												</div></th>
											<th><div id="" class=" activeper popovers"
													data-percent="10" data-container="body"
													data-trigger="hover" data-placement="top"
													data-content="Voltage Low Limit MinOver Threshold Duration">
													<span id="">VLLMTD</span>
												</div></th>
											<th><div id="" class=" activeper popovers"
													data-percent="10" data-container="body"
													data-trigger="hover" data-placement="top"
													data-content="Voltage Low Limit MinUnder Threshold Duration">
													<span id="">VLLMTD</span>
												</div></th>

										</tr>
									</thead>
									<tbody id="Evtdid">




									</tbody>
								</table>

								<table class="table table-striped table-bordered table-hover "
									id="sample_9" style="display: none">
									<thead>
										<tr>
											<th colspan="3" style="text-align: center;">Config
												Commands(Time Of Use)</th>
										</tr>
										<tr>
											<th>Type</th>
											<th>Activation Time</th>
											<th></th>

										</tr>
									</thead>
									<tbody id="">

										<tr>
											<td id="sample_9_type"></td>
											<td id="sample_9_at"></td>
											<td><div>
													<table
														class="table table-striped table-bordered table-hover "
														id="sample_10">
														<thead>
															<tr>
																<th colspan="3" style="text-align: center;">Seasons
																</th>
															</tr>
															<tr>
																<th>Month</th>
																<th>Day Of Month</th>
																<th>week</th>
															</tr>
														</thead>
														<tbody id="sample_10_b">




														</tbody>
													</table>
												</div>
												<div>
													<table
														class="table table-striped table-bordered table-hover "
														id="sample_11">
														<thead>
															<tr>
																<th colspan="8" style="text-align: center;">Weeks</th>
															</tr>
															<tr>
																<th>Week Number</th>
																<th>Day(M)</th>
																<th>Day(T)</th>
																<th>Day(W)</th>
																<th>Day(T)</th>
																<th>Day(F)</th>
																<th>Day(S)</th>
																<th>Day(S)</th>

															</tr>
														</thead>
														<tbody id="sample_11_b">




														</tbody>
													</table>
												</div>
												<div>
													<table
														class="table table-striped table-bordered table-hover "
														id="sample_12">
														<thead>
															<tr>
																<th colspan="6" style="text-align: center;">Days</th>
															</tr>
															<tr>

																<th>TZ1</th>
																<th>TZ2</th>
																<th>TZ3</th>
																<th>TZ4</th>
																<th>TZ5</th>
																<th>TZ6</th>

															</tr>
														</thead>
														<tbody id="sample_12_b">



														</tbody>
													</table>
												</div></td>
										</tr>


									</tbody>
								</table>
								<table class="table table-striped table-bordered table-hover "
									id="sample_13" style="display: none">
									<thead>
										<tr>
											<th colspan="3" style="text-align: center;">Get Commands</th>
										</tr>
										<tr>
											<th>S.No</th>
											<th>Type</th>
											<th>Active</th>
										</tr>
									</thead>
									<tbody id="sample_13_b">




									</tbody>
								</table>
							</tbody>
						</table>

					</div>
				</div>
			</div>
		</div>
		<div class="row" id="msfjid" style="display: none">
			<div class="col-md-12">
				<!-- BEGIN EXAMPLE TABLE PORTLET-->
				<div class="portlet box blue">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-globe"></i>Meter Status Response Data
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a> <a
								href="javascript:;" class="reload"></a> <a href="javascript:;"
								class="remove"></a>
						</div>
					</div>

					<div class="portlet-body">


						<div class="table-toolbar"></div>


						<BR> <BR>
						<!-- <div class="table-scrollable">	 -->
						<table class="table table-striped table-bordered table-hover"
							style="overflow-y: auto;">
							<thead id="msfjbidhead">







							</thead>
							<tbody id="msfjbid">


							</tbody>
						</table>

					</div>
				</div>
			</div>
		</div>

		<div class="modal fade" id="loadDetails" tabindex="-1" role="basic"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #4b8cf8;">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"></button>
						<h4 class="modal-title">
							<font style="font-weight: bold; color: white;">LOAD
								CURTAILMENT PARAMETERS</font></a>
						</h4>
					</div>

					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-6">
								<label>Load Curtailment State</label><font color="red">*</font>
								<div class="input-group" id="circleTd">
									<select class="form-control select2me input-medium"
										id="lc_state" name="lc_state">
										<option value="ENABLE">ENABLE</option>
										<option value="DISABLE">DISABLE</option>
									</select>
								</div>
							</div>

							<div class="form-group col-md-6">
								<label>Power Limit Normal</label><font color="red">*</font>
								<div>
									<input type="text" class="form-control " id="lc_p_limit"
										name="lc_p_limit" placeholder="Power Limit Normal"></input>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-6">
								<label>Limit Over Duration </label><font color="red">*</font>
								<div>
									<input type="text" class="form-control " id="lc_limit_od"
										name="lc_limit_od" placeholder="Limit Over Duration"></input>
								</div>
							</div>

							<div class="form-group col-md-6">
								<label>Limit Under Duration</label><font color="red">*</font>
								<div>
									<input type="text" class="form-control " id="lc_limit_ud"
										name="lc_limit_ud" placeholder="Limit Under Duration"></input>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-4">
								<label>Alert Period </label><font color="red">*</font>
								<div>
									<input type="text" class="form-control " id="alert_period"
										name="alert_period" placeholder="Alert Period"></input>
								</div>
							</div>

							<div class="form-group col-md-4">
								<label>Lockout Period</label><font color="red">*</font>
								<div>
									<input type="text" class="form-control " id="lockout_prd"
										name="lockout_prd" placeholder="Lockout Period"></input>
								</div>
							</div>
							<div class="form-group col-md-4">
								<label>Lockout Max Counter </label><font color="red">*</font>
								<div>
									<input type="text" class="form-control " id="lockout_m_counter"
										name="lockout_m_counter" placeholder="Lockout Max Counter"></input>
								</div>
							</div>
						</div>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn blue btn-outline"
							onclick="setLoadCurtlParameters()">Submit</button>
						<button type="button" class="btn dark btn-outline"
							data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>


	</div>
	<div class="modal fade" id="basic" tabindex="-1" role="basic"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="portlet box purple">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-list-ul"></i>Meters
						</div>
						<!-- <div class="actions">
								<a href="#" class="btn green"><i class="fa fa-plus"></i> Add</a>
								<a href="#" class="btn yellow"><i class="fa fa-print"></i> Print</a>
							</div> -->
					</div>
					<div class="portlet-body">
						<table class="table table-striped table-bordered table-hover"
							id="sample_model">
							<thead>
								<tr>
									<th>S.No</th>
									<th>Meter Number</th>
									<th>Added Time</th>

								</tr>
							</thead>
							<tbody id="mtrgmlistid">


							</tbody>
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn default" data-dismiss="modal">Close</button>

				</div>
				<!-- END EXAMPLE TABLE PORTLET-->


			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>

	<style>
.hid {
	color: #333;
	font-size: medium;
	font-weight: bold;
}

.tdcls1 {
	padding-bottom: 15px;
	width: 10%;
	text-align: right;
	font-weight: bold;
}

.tdcls2 {
	padding-bottom: 15px;
	width: 40%;
	/* text-align: left; */
	text-align: justify;
}

.tdcls3 {
	padding-bottom: 15px;
	width: 10%;
	text-align: right;
	font-weight: bold;
}

.tdcls4 {
	padding-bottom: 15px;
	width: 40%;
	/* text-align: left; */
	text-align: justify;
}

.col-container {
	display: flex;
	width: 100%;
}

.col {
	flex: 1;
	padding: 16px;
}
</style>

	<script>
		var lc_state, lc_p_limit, lc_limit_od, lc_limit_ud;
		var alert_period, lockout_prd, lockout_m_counter;
		function setLoadCurtlParameters() {
			lc_state = $("#lc_state").val();
			lc_p_limit = $("#lc_p_limit").val();
			lc_limit_od = $("#lc_limit_od").val();
			lc_limit_ud = $("#lc_limit_ud").val();
			alert_period = $("#alert_period").val();
			lockout_prd = $("#lockout_prd").val();
			lockout_m_counter = $("#lockout_m_counter").val();

			if (lc_p_limit == null || lc_p_limit == "") {
				bootbox.alert("Please Enter Power Limit.");
				return false;
			}
			if (lc_limit_od == null || lc_limit_od == "") {
				bootbox.alert("Please Enter Limit Over Duration.");
				return false;
			}
			if (lc_limit_ud == null || lc_limit_ud == "") {
				bootbox.alert("Please Enter Limit Under Duration.");
				return false;
			}
			if (alert_period == null || alert_period == "") {
				bootbox.alert("Please Enter Alert Period.");
				return false;
			}
			if (lockout_prd == null || lockout_prd == "") {
				bootbox.alert("Please Enter Lockout Period.");
				return false;
			}
			if (lockout_m_counter == null || lockout_m_counter == "") {
				bootbox.alert("Please Enter Loackout Max Counter .");
				return false;
			}
			$('#loadDetails').modal('hide');

			lc_p_limit = parseInt(lc_p_limit);
			lc_limit_od = parseInt(lc_limit_od);
			lc_limit_ud = parseInt(lc_limit_ud);
			alert_period = parseInt(alert_period);
			lockout_prd = parseInt(lockout_prd);
			lockout_m_counter = parseInt(lockout_m_counter);

			bootbox.alert(" LOAD CURTAILMENT PARAMETERS saved Successfully");
			return true;

		}

		function resetLoadCurtParams() {

			$("#lc_state").val("ENABLE").trigger("change");
			$("#lc_p_limit").val("");
			$("#lc_limit_od").val("");
			$("#lc_limit_ud").val("");
			$("#alert_period").val("");
			$("#lockout_prd").val("");
			$("#lockout_m_counter").val("");

		}
		function monthsel() {
			var html = '<option value="0"></option>';
			for (var i = 1; i <= 12; i++) {
				html += '<option value="'+i+'">' + i + '</option>';
			}
			$("#monthid1").html(html);
			$("#monthid2").html(html);
			$("#monthid3").html(html);
		}
		function monthdays() {
			var monthval = $("#monthid1").val();
			var monthval1 = $("#monthid2").val();
			var monthval2 = $("#monthid3").val();
			var html = '<option value="0"></option>';
			if (monthval == 2) {

				for (var i = 1; i <= 28; i++) {
					html += '<option value="'+i+'">' + i + '</option>';
				}
			} else if (monthval == '1' || monthval == '3' || monthval == '5'
					|| monthval == '7' || monthval == '8' || monthval == '10'
					|| monthval == '12') {

				for (var i = 1; i <= 31; i++) {
					html += '<option value="'+i+'">' + i + '</option>';
				}
			} else {

				for (var i = 1; i <= 30; i++) {
					html += '<option value="'+i+'">' + i + '</option>';
				}
			}
			var html1 = '<option value="0"></option>';
			if (monthval1 == 2) {

				for (var i = 1; i <= 28; i++) {
					html1 += '<option value="'+i+'">' + i + '</option>';
				}
			} else if (monthval1 == '1' || monthval1 == '3' || monthval1 == '5'
					|| monthval1 == '7' || monthval1 == '8'
					|| monthval1 == '10' || monthval1 == '12') {

				for (var i = 1; i <= 31; i++) {
					html1 += '<option value="'+i+'">' + i + '</option>';
				}
			} else {

				for (var i = 1; i <= 30; i++) {
					html1 += '<option value="'+i+'">' + i + '</option>';
				}
			}
			var html2 = '<option value="0"></option>';
			if (monthval2 == 2) {

				for (var i = 1; i <= 28; i++) {
					html2 += '<option value="'+i+'">' + i + '</option>';
				}
			} else if (monthval == '1' || monthval == '3' || monthval == '5'
					|| monthval == '7' || monthval == '8' || monthval == '10'
					|| monthval == '12') {

				for (var i = 1; i <= 31; i++) {
					html2 += '<option value="'+i+'">' + i + '</option>';
				}
			} else {

				for (var i = 1; i <= 30; i++) {
					html2 += '<option value="'+i+'">' + i + '</option>';
				}
			}
			$("#monthdid1").html(html);
			$("#monthdid2").html(html1);
			$("#monthdid3").html(html2);
		}

		function addDays() {
			var rc = $("#tzid > tbody").children().length;
			if (rc < 7) {

				html = '<tr class="odd gradeX">';
				html += '<td><input type="checkbox" class="checkboxes" value="1" id="daycs'
						+ (rc + 1) + '" onclick="weekdaysvalset()" /></td>';

				html += '<td><div class="input-group"><input type="text" id="clockface_'
						+ (rc + 1)
						+ '1_modal" value="" class="form-control" readonly="" /> <span class="input-group-btn"><button class="btn default" type="button" id="clockface_'
						+ (rc + 1)
						+ '1_modal_toggle"><i class="fa fa-clock-o"></i></button></span></div></td>';

				html += '<td><div class="input-group"><input type="text" id="clockface_'
						+ (rc + 1)
						+ '2_modal" value="" class="form-control" readonly="" /> <span class="input-group-btn"><button class="btn default" type="button" id="clockface_'
						+ (rc + 1)
						+ '2_modal_toggle"><i class="fa fa-clock-o"></i></button></span></div></td>';

				html += '<td><div class="input-group"><input type="text" id="clockface_'
						+ (rc + 1)
						+ '3_modal" value="" class="form-control" readonly="" /> <span class="input-group-btn"><button class="btn default" type="button" id="clockface_'
						+ (rc + 1)
						+ '3_modal_toggle"><i class="fa fa-clock-o"></i></button></span></div></td>';

				html += '<td><div class="input-group"><input type="text" id="clockface_'
						+ (rc + 1)
						+ '4_modal" value="" class="form-control" readonly="" /> <span class="input-group-btn"><button class="btn default" type="button" id="clockface_'
						+ (rc + 1)
						+ '4_modal_toggle"><i class="fa fa-clock-o"></i></button></span></div></td>';

				html += '<td><div class="input-group"><input type="text" id="clockface_'
						+ (rc + 1)
						+ '5_modal" value="" class="form-control" readonly="" /> <span class="input-group-btn"><button class="btn default" type="button" id="clockface_'
						+ (rc + 1)
						+ '5_modal_toggle"><i class="fa fa-clock-o"></i></button></span></div></td>';

				html += '<td><div class="input-group"><input type="text" id="clockface_'
						+ (rc + 1)
						+ '6_modal" value="" class="form-control" readonly="" /> <span class="input-group-btn"><button class="btn default" type="button" id="clockface_'
						+ (rc + 1)
						+ '6_modal_toggle"><i class="fa fa-clock-o"></i></button></span></div></td>';
				html += '</tr>';
				$('#groupDaysid').append(html);
			} else {
				bootbox.alert("Max limit is 7");
				return false;
			}

		}
		function weekssel() {

			$('#wstid1 option').remove();
			$('#wstid2 option').remove();
			$('#wstid3 option').remove();
			var html = '<option value=""></option>';
			var v1 = '';
			if ($('#wc1').prop('checked')) {
				v1 += '0';
			}
			if ($('#wc2').prop('checked')) {
				v1 += '1';
			}
			if ($('#wc3').prop('checked')) {
				v1 += '2';
			}

			if (v1 == '0') {
				html += '<option value="0">0</option>';
			} else if (v1 == '1') {
				html += '<option value="1">1</option>';
			} else if (v1 == '2') {
				html += '<option value="2">2</option>';
			} else if (v1 == '01' || v1 == '10') {
				html += '<option value="0">0</option>';
				html += '<option value="1">1</option>';
			} else if (v1 == '02' || v1 == '20') {
				html += '<option value="0">0</option>';
				html += '<option value="2">2</option>';
			} else if (v1 == '12' || v1 == '21') {
				html += '<option value="1">1</option>';
				html += '<option value="2">2</option>';
			} else {
				html += '<option value="0">0</option>';
				html += '<option value="1">1</option>';
				html += '<option value="2">2</option>';
			}
			$('#wstid1').html(html);
			$('#wstid2').html(html);
			$('#wstid3').html(html);

		}
		function weeksselt() {
			if ($('#wc1').prop('checked') == false
					&& $('#wc2').prop('checked') == false
					&& $('#wc3').prop('checked') == false) {
				bootbox.alert("Please select any one of the week");
				return false;
			}
		}

		function weekdaysvalset() {
			$(".daysc option").remove();
			var v1 = '';
			if ($('#daycs1').prop('checked')) {
				v1 += '0,';
			}
			if ($('#daycs2').prop('checked')) {
				v1 += '1,';
			}
			if ($('#daycs3').prop('checked')) {
				v1 += '2,';
			}
			if ($('#daycs4').prop('checked')) {
				v1 += '3,';
			}
			if ($('#daycs5').prop('checked')) {
				v1 += '4,';
			}
			if ($('#daycs6').prop('checked')) {
				v1 += '5,';
			}
			if ($('#daycs7').prop('checked')) {
				v1 += '6,';
			}
			var list = (v1.substring(0, v1.length - 1)).split(',').sort();
			html = "<option></option>";
			$.each(list, function(index, value) {
				html += '<option value="'+value+'">' + value + '</option>';
			});
			$(".daysc").html(html);

		}

		function meterCommandSetOrGet() {
			var jt = $("#jobtype").val();
			if (jt == 'METER_COMMAND_SET') {
				$("#sample_1").show();
				$("#sample_1n").hide();
			}
			if (jt == 'METER_COMMAND_GET') {
				$("#sample_1").hide();
				$("#sample_1n").show();
			}
		}
	</script>