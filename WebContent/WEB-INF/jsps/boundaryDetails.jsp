<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	if (session.getAttribute("username") == null || session.getAttribute("username") == null) {
%>
<script>
	window.location.href = "./?sessionVal=expired";
</script>
<%
	}
%>

<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
<script>
	$(".page-content")
			.ready(
					function() {
						App.init();
						TableEditable.init();
						FormComponents.init();
						loadSearchAndFilter('sample_1');
						loadSearchAndFilter('sample_2');
						

						$(
								'#MDMSideBarContents,#meterpointmgmt,#boundaryDetails,#mpmId')
								.addClass('start active ,selected');
						$(
								'#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
								.removeClass('start active ,selected');
						$('#circle').val('').trigger('change');
					});

	$(document).ready(function() {
		getAllLocation('${officeCode}', '${officeType}');


		$("input[name='meter_instl_radio']").click(function() {
			if ($("input[name='meter_instl_radio']:checked").val() == 'True') {
		    	//$("#activeRule").show();
		    	//$("#consPer").hide();
		    } else {
		    	//$("#activeRule").hide();
		    	//$("#consPer").show();
		    }
	    }); 

		$("input[name='emeter_instl_radio']").click(function() {
			if ($("input[name='emeter_instl_radio']:checked").val() == 'True') {
		    	//$("#eactiveRule").show();
		    	//$("#econsPer").hide();
		    } else {
		    	//$("#eactiveRule").hide();
		    	//$("#econsPer").show();
		    }
	    }); 

		$('#meter_notinstlled_radio').click();
		$('#meter_notinstlled_radio').click();
	});
</script>

<script>
	function getAllLocation(officeCode, officeType) {
		//	alert(officeCode+"--"+officeType);
		$
				.ajax({
					type : 'GET',
					url : "./getAllLocationData",
					data : {
						officeCOde : officeCode,
						officeType : officeType
					},
					async : false,
					cache : false,
					success : function(response) {
					//	alert(response);
						var html = "";
						if (response != null) {
							if (officeType == "discom") {
								html += "<option value=0>Select Circle</option>";
								for (var i = 0; i < response.length; i++) {
									html += "<option value='"+response[i]+"'>"
											+ response[i] + "</option>";
								}
								$("#circleId").empty();
								$("#circleId").append(html);
							}

							else if (officeType == "division") {
								var htmlCircle = "";
								var htmlDivision = "";
								//var htmlSubdivision=""; 
								for (var i = 0; i < response.length; i++) {
									htmlCircle += "<option value='"+response[i][0]+"'>"
											+ response[i][0] + "</option>";
									htmlDivision += "<option value='"+response[i][1]+"'>"
											+ response[i][1] + "</option>";
									//htmlSubdivision+="<option value='"+response[i][2]+"'>"+response[i][2]+"</option>"; 
								}
								$("#circleId").empty();
								$("#circleId").append(htmlCircle);
								$("#divisionId").empty();
								$("#divisionId").append(htmlDivision);
								//$("#sdonameId").empty();
								//$("#sdonameId").append(htmlSubdivision);

							} else if (officeType == "circle") {
								$('#circle').find('option').remove();
								html += "";
								for (var i = 0; i < response.length; i++) {
									 $('#circle').append($('<option>', {
										value : response[i],
										text : response[i]
									})); 
									//$("#circle").val(response[i]).trigger("change");
									//html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
								}
								//$("#circle").empty();
								//$("#circle").append(html);
								//	getDivByCircle($("#circleId").val());
								showTown($("#circle").val());
							}

							else if (officeType == "subdivision") {
								var htmlCircle = "";
								var htmlDivision = "";
								var htmlSubdivision = "";
								for (var i = 0; i < response.length; i++) {
									htmlCircle += "<option value='"+response[i][0]+"'>"
											+ response[i][0] + "</option>";
									htmlDivision += "<option value='"+response[i][1]+"'>"
											+ response[i][1] + "</option>";
									htmlSubdivision += "<option value='"+response[i][2]+"'>"
											+ response[i][2] + "</option>";
								}
								$("#circleId").empty();
								$("#circleId").append(htmlCircle);
								$("#divisionId").empty();
								$("#divisionId").append(htmlDivision);
								$("#sdonameId").empty();
								$("#sdonameId").append(htmlSubdivision);
								getSubStations($('#sdonameId').val());
							}

						}

					}
				});
	}

	function showTown(circle) {
		$
				.ajax({
					url : './showTownByCircle',
					type : 'GET',
					data : {
						circle : circle
					},
					dataType : 'json',
					asynch : false,
					cache : false,
					success : function(response) {
						var html = '';
						html += "<label class='control-label'>Town:</label><select id='town' name='town' onchange='showSubStaTionByTown(this.value)' class='form-control' type='text'><option value=''>Select Town</option>";

						for (var i = 0; i < response.length; i++) {
							//		alert("response[i]== "+response[i]);
							var resp = response[i];
							html += "<option  value='"+resp[1]+"'>" + resp[0]
									+ "</option>";
						}
						html += "</select><span></span>";
						$("#townId").html(html);
						$('#town').select2();
						$("#substation").val("").trigger("change");
						$('#substation').empty();
						$('#substation').find('option').remove();
						$('#substation').append($('<option>', {
							value : "",
							text : "Select Sub-Station"
						}));
					}
				});
	}

	function showSubStaTionByTown(town) {
		$('#substation').val('').trigger('change');
		var town = $('#town').val();
		$('#substation').empty();
		$('#substation').find('option').remove();
		$('#substation').append($('<option>', {
			value : "",
			text : "Select Sub-Station"
		}));
		
		$.ajax({
			url : './showSubStaTionByTown',
			type : 'GET',
			data : {
				town : town
			},
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(response1) {
				//$("#substation").val("").trigger("change");
				for (var i = 0; i < response1.length; i++) {
					var resp = response1[i];
					$('#substation').append($('<option>', {
						value : resp[0],
						text : resp[1]
					}));
				}
			}
		});
	}
</script>
<script>
	function getFeederData() {
		var ssid = $('#substation').val();
		var town = $('#town').val();
		var circle =$("#circle").val()
		if(circle=='' || circle==null){
			bootbox.alert("Please Select circle");
			return false;
		}
		
		if(town=='' || town==null){
			bootbox.alert("Please Select Town");
			return false;
		} 
		if(ssid=='' || ssid==null){
			bootbox.alert("Please Select Sub-Station");
			return false;
		}

		if(town=='013' ){			
		$
				.ajax({
					url : './showFeederDetailsData',
					type : 'POST',
					data : {
						town : town,
						ssid : ssid
					},
					dataType : 'json',
					asynch : false,
					cache : false,
					success : function(response) {
						//	alert(response1)
						var count = 1;
						$("#updateMaster").html('');
						if (response != null && response.length > 0) {
							var html = "";
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
							//	alert(resp[12]);
								html += "<tr>" 
										+ "<td>"+ count++ + "</td>"
										/* <button onclick='addBoundaryMeters(\""+ resp[2]+"\",\""+resp[3]+"\",\""+resp[8]+"\",\""+resp[9]+"\",\""+resp[10]+"\",\""+resp[11]+"\",\""+resp[12]+"\",\""+resp[7]+"\")' class='btn yellow' data-toggle='modal' style='padding: 6px;' data-target='#stack3'>ADD RFM</button> */
										+"<td>"+ "<button  onclick='viewBoundaryMeters(\""+ resp[2]+ "\",\""+resp[3]+"\")' class='btn green' style='padding: 6px;' data-toggle='modal'  data-target='#stack2'>VIEW RFM</button><button onclick='addBoundaryMeters(\""+ resp[2]+"\",\""+resp[3]+"\",\""+resp[8]+"\",\""+resp[9]+"\",\""+resp[10]+"\",\""+resp[11]+"\",\""+resp[12]+"\",\""+resp[7]+"\")' class='btn yellow' data-toggle='modal' style='padding: 6px;' data-target='#stack3'>ADD RFM</button>"
										/* + "<td>"+ resp[0]+ " </td>" */
										+ "<td>"+ resp[1]+ " </td>"
										+ "<td>"+ resp[2]+ " </td>"
										+ "<td>"+ resp[3]+ " </td>"
										+ "<td>"+ ((resp[4]==null)?"":resp[4])+ " </td>"
										+ "<td>"+ ((resp[5]==null)?"":resp[5])+ " </td>"
										+ "<td>"+ ((resp[6]==null)?"":resp[6])+ " </td>"
										/* + "<td>"+ ((resp[7]==null)?"":resp[7])+ " </td>" */
										+ "</td>" +

										"</tr>";

							}
							$('#sample_1').dataTable().fnClearTable();
							$("#updateMaster").html(html);
							loadSearchAndFilter('sample_1');
						} else {
							bootbox.alert("No Data Found for this Criteria.");
							loadSearchAndFilter('sample_1');
						}
					}
				});
		
			}else{
				$('#sample_1').dataTable().fnClearTable();
				loadSearchAndFilter('sample_1');
				bootbox.alert("No Data Found for this Criteria.");
				return false;
		}

	}


	function viewBoundaryMeters(fdrname, fdrcode){
		//alert(fdrname+" -- "+fdrcode);
		var temp=fdrcode+"-"+fdrname;
		$("#bndryMtrDtlLvl").text(temp);
		$('#sample_2').dataTable().fnClearTable();
		$
		.ajax({
			url : './getBoundaryDetailsData',
			type : 'POST',
			data : {
				fdrcode : fdrcode,
				fdrname : fdrname
			},
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(response) {
		
				if (response != null && response.length > 0) {
					var html = "";
					for (var i = 0; i < response.length; i++) {
						var resp = response[i];
						html += "<tr>" 
							+ "<td>"+ (i+1) + "</td>"
							/* + "<c:if test = "${userType eq 'ADMIN'}"> <td>"+ "<button  onclick='deleteBoundaryMeter(\""+ resp[0]+ "\",\""+resp[1]+"\")' class='btn red' style='padding: 6px;' data-toggle='modal' >DELETE</button> <button onclick='editBoundaryMeter(\""+ resp[0]+"\",\""+resp[1]+"\",\""+resp[2]+"\",\""+((resp[3]==null)?"":resp[3])+"\",\""+((resp[4]==null)?"":resp[4])+"\",\""+((resp[5]==null)?"":resp[5])+"\",\""+((resp[6]==null)?"":resp[6])+"\",\""+((resp[7]==null)?"":resp[7])+"\",\""+fdrcode+"\",\""+fdrname+"\",\""+resp[10]+"\")' class='btn yellow' data-toggle='modal' style='padding: 6px;' data-target='#stack4'>MODIFY</button>"+"</td></c:if>" */ 						
							/* + "<td>"+ "<button  onclick='deleteBoundaryMeter(\""+ resp[0]+ "\",\""+resp[1]+"\")' class='btn red' style='padding: 6px;' data-toggle='modal' >DELETE</button> <button onclick='editBoundaryMeter(\""+ resp[0]+"\",\""+resp[1]+"\",\""+resp[2]+"\",\""+((resp[3]==null)?"":resp[3])+"\",\""+((resp[4]==null)?"":resp[4])+"\",\""+((resp[5]==null)?"":resp[5])+"\",\""+((resp[6]==null)?"":resp[6])+"\",\""+((resp[7]==null)?"":resp[7])+"\",\""+fdrcode+"\",\""+fdrname+"\",\""+resp[10]+"\")' class='btn yellow' data-toggle='modal' style='padding: 6px;' data-target='#stack4'>MODIFY</button>"+"</td>" 	 */					
							+ "<td>"+ resp[0] + " </td>"
							+ "<td>"+ resp[1] + " </td>"
							+ "<td>"+ resp[2] + " </td>"
							+ "<td>"+ ((resp[3]==null)?"":resp[3]) + " </td>"
							+ "<td>"+ ((resp[4]==null)?"":resp[4]) + " </td>"
							+ "<td>"+ ((resp[5]==null)?"":resp[5]) + " </td>"
							+ "<td>"+ ((resp[6]==null)?"":resp[6]) + " </td>"
							+ "<td>"+ ((resp[7]==null)?"":resp[7]) + " </td>"
							+ "<td>"+ ((resp[8]==null)?"":resp[8]) + " </td>"
							+ "<td>"+ ((resp[9]==null)?"":resp[9]) + " </td>"
							+ "<td>"+ ((resp[10]==null)?"":((resp[10]==0)?"No":"Yes")) + " </td>"
							"</tr>";
					 
					}
					$('#sample_2').dataTable().fnClearTable();
					$("#boundayDetailsTbl").html(html);
					loadSearchAndFilter('sample_2');
				} 
			}
		});
		
		
	}
	
</script>

<script>
function formreset(){
	
    $('#bdrname').val('');
    $('#bdrloc').val('');
    $('#mtrno').val('');
    $('#mtrmkr').val('');
    $('#ctno').val('');
    $('#ptno').val('');
    $('#mfid').val('');
    $('#mtrratio').val('');
    $('#cp').val(''); 
    $("#expImpRule").val("").trigger("change");
    $('#meter_notinstlled_radio').click();
	$('#meter_notinstlled_radio').click();

	$('#ebdrname').val('');
    $('#ebdrloc').val('');
    $('#emtrno').val('');
    $('#emtrmkr').val('');
    $('#ectno').val('');
    $('#eptno').val('');
    $('#emfid').val('');
    $('#emtrratio').val('');
    $('#ecp').val(''); 
    $("#eexpImpRule").val("").trigger("change");
    $('#emeter_notinstlled_radio').click();
	$('#emeter_notinstlled_radio').click();
     
}

var gfdrid='';
var gsscode='';
var gtp_sscode='';
var gsdocode='';
var gtp_sdocode='';
var gvoltagelevel='';



function getlatestRuleId(feedercode) {
	$.ajax({
			url : './getlatestBoundaryId',
			type : 'GET',
			data : {
				feedercode : feedercode
			},
			dataType : 'TEXT',
			success : function(response) {
				 $("#bdrid").val(response);
			}
		});
}

function addBoundary(){
	var feederid= $("#feederid").val();
	var feedername= $("#feedername").val();
	var bdrid= $("#bdrid").val();	
	var bdrname= $("#bdrname").val();
	var bdrloc= $("#bdrloc").val();	
	var meterStatus= $("input[name='meter_instl_radio']:checked").val();
	var mtrno= $("#mtrno").val();
	var mtrmkr= $("#mtrmkr").val();
	var ctno= $("#ctno").val();
	var ptno= $("#ptno").val();
	var mfid = $("#mfid").val();
	var mtrratio= $("#mtrratio").val();
	var cp=$("#cp").val();
	var expImp = $("#expImpRule").val();
	var town = $('#town').val();
	if (bdrname == '') {
		bootbox.alert("Please Enter Boundary Name");
		return false;
	}

	if (bdrloc == '') {
		bootbox.alert("Please Enter Boundary Location");
		return false;
	}
	/* if ($("input[name='meter_instl_radio']:checked").val() == 'True'){
		if (mtrno == '') {
			bootbox.alert("Please Enter Meter No");
			return false;
		}

		if (mtrmkr == '') {
			bootbox.alert("Please Enter Meter Maker");
			return false;
		}
		if (mfid == '') {
			bootbox.alert("Please Enter MF Value");
			return false;
		} 
		

	}else if($("input[name='meter_instl_radio']:checked").val() == 'False'){
		if (cp == '') {
			bootbox.alert("Please Enter Consumption Percentage");
			return false;
		} 
	} */

	$
	.ajax({
		url : './addBoundaryDetailsData',
		type : 'POST',
		data : {
			fdrid : gfdrid,
			feedercode  : feederid,
			feedername : feedername,
			bdrid	: bdrid,
			bdrname : bdrname,
			bdrloc : bdrloc,
			meterStatus : meterStatus,
			mtrno : mtrno,
			mtrmkr : mtrmkr,
			ctno : ctno,
			ptno : ptno,
			mfid  : mfid,
			mtrratio : mtrratio,
			expImp : expImp,
			sscode :gsscode ,
			tp_sscode : gtp_sscode,
			sdocode : gsdocode,
			tp_sdocode : gtp_sdocode,
			cp:cp,
			voltagelevel:gvoltagelevel,
			town : town
		},
		dataType : 'text',
		asynch : false,
		cache : false,
		success : function(response) {
			if(response=='succ'){
				formreset();
				bootbox.alert("RFM assign to Feeder Meter Succusfully");
				$("#stack3").modal('hide');

			} else{
				formreset();
				bootbox.alert("OOPS! Something went wrong!!");
				$("#stack3").modal('hide');
			}
		},
		error: function (e) {
			    formreset();
				bootbox.alert("OOPS! Something went wrong!!");
				$("#stack3").modal('hide');
        }
	});

	
}


function editBoundary(){
	
	var feederid= $("#efeederid").val();
	var feedername= $("#efeedername").val();
	var bdrid= $("#ebdrid").val();	
	var bdrname= $("#ebdrname").val();
	var bdrloc= $("#ebdrloc").val();	
	var meterStatus= $("input[name='emeter_instl_radio']:checked").val();
	var mtrno= $("#emtrno").val();
	var mtrmkr= $("#emtrmkr").val();
	var ctno= $("#ectno").val();
	var ptno= $("#eptno").val();
	var mfid = $("#emfid").val();
	var mtrratio= $("#emtrratio").val();
	var cp=$("#ecp").val();
	var expImp = $("#eexpImpRule").val();

	if (bdrname == '') {
		bootbox.alert("Please Enter Boundary Name");
		return false;
	}

	if (bdrloc == '') {
		bootbox.alert("Please Enter Boundary Location");
		return false;
	}
	/* if ($("input[name='emeter_instl_radio']:checked").val() == 'True'){
		if (mtrno == '') {
			bootbox.alert("Please Enter Meter No");
			return false;
		}

		if (mtrmkr == '') {
			bootbox.alert("Please Enter Meter Maker");
			return false;
		}
		if (mfid == '') {
			bootbox.alert("Please Enter MF Value");
			return false;
		} 
		

	}else if($("input[name='emeter_instl_radio']:checked").val() == 'False'){
		if (cp == '') {
			bootbox.alert("Please Enter Consumption Percentage");
			return false;
		} 
	} */

	$
	.ajax({
		url : './editBoundaryDetailsData',
		type : 'POST',
		data : {
			feedercode  : feederid,
			feedername : feedername,
			bdrid	: bdrid,
			bdrname : bdrname,
			bdrloc : bdrloc,
			meterStatus : meterStatus,
			mtrno : mtrno,
			mtrmkr : mtrmkr,
			ctno : ctno,
			ptno : ptno,
			mfid  : mfid,
			mtrratio : mtrratio,
			expImp : expImp,
			cp:cp
		},
		dataType : 'text',
		asynch : false,
		cache : false,
		success : function(response) {
			if(response=='succ'){
				formreset();
				bootbox.alert("RFM Details Updated Succusfully");
				$("#stack4").modal('hide');

			} else{
				formreset();
				bootbox.alert("OOPS! Something went wrong!!");
				$("#stack4").modal('hide');
			}
		},
		error: function (e) {
			    formreset();
				bootbox.alert("OOPS! Something went wrong!!");
				$("#stack4").modal('hide');
        }
	});

	
}


</script>
<script>
function addBoundaryMeters(feedername,feedercode,fdrid,sscode,tp_sscode,sdocode,tp_sdocode,voltagelevel){

	formreset();
//	 alert(feedername+" "+feedercode+" "+fdrid+" "+sscode+" "+tp_sscode+" "+sdocode+" "+tp_sdocode);
	 getlatestRuleId(feedercode);
	 gfdrid=fdrid;
	 gsscode=sscode;
	 gtp_sscode=tp_sscode;
	 gsdocode=sdocode;
	 gtp_sdocode=tp_sdocode;
	 gvoltagelevel=voltagelevel;
	 

	 $("#feederid").val(feedercode);
	 $("#feedername").val(feedername);

	
}

function editBoundaryMeter(bdrid,bdrname,bdrloc,mtrno,mtrmk,ct,pt,mf,fdrcode,fdrname,meter_installed){
	$("#stack2").modal('hide');
	formreset();	
	 if(meter_installed == 0) {
		 $('#emeter_notinstlled_radio').click();
		 $('#emeter_notinstlled_radio').click();
		}
	  if(meter_installed == 1)
		{
		//  $("#emeter_instl_radio").prop("checked", true);
		  $('#emeter_instlled_radio').click();
		  $('#emeter_instlled_radio').click();
		//  $("input[name='emeter_instl_radio']:checked").val();
		}

	 $("#efeederid").val(fdrcode);
	 $("#efeedername").val(fdrname);
	 $("#ebdrid").val(bdrid);
	 $("#ebdrname").val(bdrname);
	 $("#ebdrloc").val(bdrloc);

	 $("#emtrno").val(mtrno);
	 $("#emtrmkr").val(mtrmk);
	 $("#ectno").val(ct);
	 $("#eptno").val(pt);
	 $("#emfid").val(mf);
	
}


function deleteBoundaryMeter(bdrid,bdrname){

	 bootbox.confirm("Are you sure want to delete this record ?", function(result) {
		  if(result == true)
            {
			  $
				.ajax({
					url : './deleteBoundaryMeterData',
					type : 'POST',
					data : {
						bdrid	: bdrid,
						bdrname : bdrname
					},
					dataType : 'text',
					asynch : false,
					cache : false,
					success : function(response) {
						if(response=='success'){
							bootbox.alert("Assign RFM Deleted Succusfully");
							$("#stack2").modal('hide');

						} else{
							bootbox.alert("OOPS! Something went wrong!!");
							$("#stack2").modal('hide');
						}
					},
					error: function (e) {
							bootbox.alert("OOPS! Something went wrong!!");
							$("#stack2").modal('hide');
			        }
				});
            }

	  });

	
	
}


function checkMtrNoCFYES(meterno)
{
	 $.ajax(
			{
					type : "GET",
					url : "./checkMeterNoexistance/" + meterno,
					dataType : "json",
					success : function(response)
					{
						if(response != '0')
						{
							bootbox.alert("Entered Meter No is already Assigned");
							$("#mtrno").val("");
							$("#emtrno").val("");
							return false;
						}else{
							$.ajax(
						 			{
				 					type : "GET",
				 					url : "./validateMeterfromMM/" + meterno,
				 					dataType : "json",
				 					success : function(response)
				 					{
				 					
				 						if(response.length == '0')
				 						{
				 							bootbox.alert("Please enter valid meter No.");
				 							$("#mtrno").val("");
				 							$("#emtrno").val("");
				 							return false;
						 				}else{
						 					$("#mtrmkr").val(response[0][1]);
						 					$("#emtrmkr").val(response[0][1]);
						 				
						 				}
						 			}
						 			});
						}
						
					}
			});
}

    function exportPDF()
    {
		var circle =$("#circle").val()
		var town = $('#town').val();
		var ssid = $('#substation').val();
		
		window.open("./boundarydetailspdf/"+circle+"/"+town+"/"+ssid)

	}
    
    
</script>



<div class="page-content">
	<c:if test="${not empty msg}">
		<div class="alert alert-success display-show">
			<button class="close" data-close="alert"></button>
			<span style="color: green">${msg}</span>
		</div>
	</c:if>
	<%-- <c:if test="${results ne 'notDisplay'}">
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
			</c:if> --%>
	<div class="row">
		<div class="col-md-12">

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Feeder-Boundary Details
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>


				</div>

				<div class="portlet-body">

					<%--  <c:if test="${officeType eq 'subdivision'|| userType eq 'ADMIN'}">  --%>

					<div class="row" style="margin-left: -1px;">
						<div class="col-md-4">
							<div id="circleTd" class="form-group">
							<label class="control-label">Circle:</label>
								<select class="form-control select2me" id="circle" name="circle"
									onchange="showTown(this.value);">
									<option value="">Select Circle</option>
									<%-- <option value="ALL">ALL</option>  --%>
									<c:forEach items="${circleList}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-md-4">
							<div id="townId" class="form-group">
							<label class="control-label">Town:</label>
								<select class="form-control select2me" id="town" name="town">
									<option value="">Select Town</option>
									<%-- <option value="ALL">ALL</option>  --%>
									<c:forEach items="${townList}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>

						</div>
						<div class="col-md-4">
							<div id="substaTd" class="form-group">
							<label class="control-label">Sub-Station:</label>
								<select class="form-control select2me" id="substation"
									name="substation">
									<option value="">Select Sub-Station</option>
									<%-- 	<option value="ALL">ALL</option>  --%>
									<c:forEach items="${substationList}" var="substation">
										<option value="${substation}">${substation}</option>
									</c:forEach>
								</select>
							</div>

						</div>
					</div>


					<div class="tabbable tabbable-custom">
						<div id="showFeederData">
							<button type="button" id="viewFeederData"
								style="margin-left: 480px;" onclick="getFeederData();"
								name="viewFeederData" class="btn yellow">
								<b>View</b>
							</button>
							<br />

							<div class="table-toolbar">
								<div class="btn-group pull-right" style="margin-top: 16px;">
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<!-- <li><a href="#" id="print">Print</a></li> -->
										<li><a href="#" id=""
								         onclick="exportPDF('sample_1','BoundaryDetails')">Export to PDF</a></li>
										<li><a href="#" id="excelExport"
											onclick="tableToExcel('sample_1', 'Feeder Details')">Export
												to Excel</a></li>
									</ul>
								</div>
							</div>
							<table class="table table-striped table-hover table-bordered"
								id="sample_1">
								<thead>
									<tr>
										<th>SL No.</th>
										<!-- <th>Circle</th>	 -->
										<th>Action</th>
										<!-- <th>Town</th> -->
										<th>Sub Station</th>
										<th>Feeder Name</th>
										<th>TP Feeder Code</th>
										<th>Meter Sr Number</th>
										<th>Meter Manufacturer</th>
										<th>MF</th>
										<!-- <th>Voltage Level</th> -->

									
									</tr>
								</thead>
								<tbody id="updateMaster">

								</tbody>
							</table>
						</div>

					</div>
					<%--  </c:if> --%>
				</div>
				
				<div id="stack2" class="modal fade" role="dialog"
					aria-labelledby="myModalLabel10" aria-hidden="true">
					<div class="modal-dialog" style="width: 85%;">
						<div class="modal-content">
							<div class="modal-header" style="background-color: #4b8cf8;">
										<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
										<h4 class="modal-title" style="color: white;">Boundary Meter Details of : <span id="bndryMtrDtlLvl" style="color: white;font-weight: bold;"  > </span></h4>
							</div>
						
							<div class="modal-body">
								<table class="table table-striped table-hover table-bordered"
									id="sample_2">
									<thead>
										<tr>
											<th>SL No</th>
											<%-- <c:if test = "${userType eq 'ADMIN'}">  --%>
												<!-- <th>Action</th> -->
											<%-- </c:if> --%>
											<th>Boundary ID</th>
											<th>Boundary Name</th>
											<th>Boundary Location</th>
											<th>Meter No</th>
											<th>Meter Make</th>
											<th>CT</th>
											<th>PT</th>
											<th>MF</th>
											<th>Meter Ratio</th>
											<th>Export/Import</th>
											<th>Meter Installed</th>
										</tr>
									</thead>
									<tbody id="boundayDetailsTbl">
									</tbody>
								</table>	
							</div>	
							
							<div class="modal-footer">
                          		<button class="btn red pull-right" data-dismiss="modal" class="btn">Close</button>
                            </div>
								
						</div>
					</div>
				</div>
				
				
				<div id="stack3" class="modal fade" role="dialog"
						aria-labelledby="myModalLabel10" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<h4 class="modal-title"><span id="addMeterStackId" style="color: #474627;font-weight: bold;"  >Add Boundary Meter</span></h4>
								</div>
							
								<div class="modal-body">
									<form action="#" id="addBoundaryMeter" method="post">
										
							 <div class="row"> 
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Feeder ID</label> <input
														type="text" id="feederid"
														class="form-control placeholder-no-fix"
														name="FDRID" maxlength="50"  readonly="readonly"></input>
												</div>
											</div>
																					
										
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Feeder Name</label> <input
														type="text" id="feedername"
														class="form-control placeholder-no-fix"
														name="FDRNAME" readonly="readonly" maxlength="50"></input>
												</div>
											</div>	
										
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Boundary ID</label>
													<!-- <span style="color: red" class="required">*</span> -->
													<input type="text" id="bdrid"
														class="form-control placeholder-no-fix"
														readonly="readonly" name="BDRID"
														maxlength="50"></input>
												</div>
											 </div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Boundary Name</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="bdrname"
														class="form-control placeholder-no-fix"
														placeholder="Enter Boundary Name" name="BDRNAME"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Boundary Location</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="bdrloc"
														class="form-control placeholder-no-fix"
														placeholder="Enter Boundary Location" name="BDRLOC"
														maxlength="50"></input>
												</div>
											</div>
											
											<div class="col-md-6">
											  <div class="form-group">
												<label>Meter Installed :</label><span style="color: red" class="required">*</span><br>&nbsp;
														<label><input type="radio" id="meter_instlled_radio" name="meter_instl_radio" value="True" >Yes</label> &nbsp;
														<label><input type="radio" id="meter_notinstlled_radio" name="meter_instl_radio" value="False">No</label>
											</div>
										</div>
										<div class="col-md-6" id="consPer" style="display: none;">
												<div class="form-group">
													<label class="control-label">Consumption Percentage</label>
												 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="cp"
														class="form-control placeholder-no-fix"
														placeholder="Enter Consumption percentage" name="CP"  placeholder="0.00" value="0.00"
														maxlength="50"></input>
												</div>
											</div>
										</div>
										<div class="row" style="display: none;">
										<div class="col-md-12 form-group" id="activeRule" style="display: none;">
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Meter No</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="mtrno" 
														class="form-control placeholder-no-fix"
														placeholder="Enter Meter No" name="MTRNO"
														onchange="return checkMtrNoCFYES(this.value)" maxlength="50"></input>
												</div>
											</div>	
											
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Meter Maker</label>
													 <span style="color: red" class="required">*</span> 
													
													 <input
														type="text" id="mtrmkr" 
														class="form-control placeholder-no-fix"
														placeholder="Enter Meter Maker" name="MTRMKR"
														maxlength="50"></input>
												</div>
											</div>									
																			
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label">CT Ratio</label>
													<!--  <span style="color: red" class="required">*</span>  -->
													 <input
														type="text" id="ctno" 
														class="form-control placeholder-no-fix"
														placeholder="Enter CT No" name="CTNO"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label">PT Ratio</label>
													<!--  <span style="color: red" class="required">*</span>  -->
													 <input
														type="text" id="ptno" 
														class="form-control placeholder-no-fix"
														placeholder="Enter PT No" name="PTNO"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label">MF</label>
													<span style="color: red" class="required">*</span> 
													 <input
														type="text" id="mfid"
														class="form-control placeholder-no-fix"
														placeholder="Enter MF" name="MFID"
														maxlength="50"></input>
												</div>
											</div>
										
										 <div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Meter Ratio</label>
												<!-- 	 <span style="color: red" class="required">*</span>  -->
													 <input
														type="text" id="mtrratio"
														class="form-control placeholder-no-fix"
														placeholder="Enter Meter Ratio" name="MTRRATIO"
														maxlength="50"></input>
												</div>
											</div>
											
										
											
											 <div class="col-md-6" id="EstActiveRule">
											 <div class="form-group">
													<label class="control-label">Export/Import</label>
													<!--  <span style="color: red" class="required">*</span>  -->
												 <select class="form-control select2me" name="EXPIMPRULE" id="expImpRule">
													<option value="">Please Select Export/Import</option>
													<option value="Export">Export</option>
													<option value="Import">Import</option>
												</select>
												</div>
											</div>
											</div>
										</div>
									
										<div class="modal-footer">
											<button class="btn red pull-right" type="button"
												data-dismiss="modal" onclick="formreset()">Cancel</button>
											<button class="btn green pull-left" id="addBoundaryMeter"
												type="button" onclick="addBoundary()">ADD</button>
									  </div>
									</form>
								</div>
							</div>
						</div>
					</div>
					
					
				<div id="stack4" class="modal fade" role="dialog"
						aria-labelledby="myModalLabel10" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<h4 class="modal-title"><span id="editMeterStackId" style="color: #474627;font-weight: bold;"  >Edit Boundary Meter</span></h4>
								</div>
							
								<div class="modal-body">
									<form action="#" id="editBoundaryMeter" method="post">
										
							 <div class="row"> 
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Feeder ID</label> <input
														type="text" id="efeederid"
														class="form-control placeholder-no-fix"
														name="EFDRID" maxlength="50"  readonly="readonly"></input>
												</div>
											</div>
																					
										
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Feeder Name</label> <input
														type="text" id="efeedername"
														class="form-control placeholder-no-fix"
														name="EFDRNAME" readonly="readonly" maxlength="50"></input>
												</div>
											</div>	
										
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Boundary ID</label>
													<!-- <span style="color: red" class="required">*</span> -->
													<input type="text" id="ebdrid"
														class="form-control placeholder-no-fix"
														readonly="readonly" name="EBDRID"
														maxlength="50"></input>
												</div>
											 </div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Boundary Name</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="ebdrname"
														class="form-control placeholder-no-fix"
														placeholder="Enter Boundary Name" name="EBDRNAME"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Boundary Location</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="ebdrloc"
														class="form-control placeholder-no-fix"
														placeholder="Enter Boundary Location" name="EBDRLOC"
														maxlength="50"></input>
												</div>
											</div>
											
											<div class="col-md-6">
											  <div class="form-group">
												<label>Meter Installed :</label><span style="color: red" class="required">*</span><br>&nbsp;
														<label><input type="radio" id="emeter_instlled_radio" name="emeter_instl_radio" value="True" >Yes</label> &nbsp;
														<label><input type="radio" id="emeter_notinstlled_radio" name="emeter_instl_radio" value="False">No</label>
											</div>
										</div>
										<div class="col-md-6" id="econsPer" style="display: none;">
												<div class="form-group">
													<label class="control-label">Consumption Per</label>
												 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="ecp"
														class="form-control placeholder-no-fix"
														placeholder="Enter Consumption percentage" name="ECP"  placeholder="0.00"
														maxlength="50"></input>
												</div>
											</div>
										</div>
										<div class="row">
										<div class="col-md-12 form-group" id="eactiveRule" style="display: none;">
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Meter No</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="emtrno" 
														class="form-control placeholder-no-fix"
														placeholder="Enter Meter No" name="EMTRNO"
														onchange="return checkMtrNoCFYES(this.value)" maxlength="50"></input>
												</div>
											</div>	
											
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Meter Maker</label>
													 <span style="color: red" class="required">*</span> 
													
													 <input
														type="text" id="emtrmkr" 
														class="form-control placeholder-no-fix"
														placeholder="Enter Meter Maker" name="EMTRMKR"
														maxlength="50"></input>
												</div>
											</div>									
																			
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label">CT Ratio</label>
													<!--  <span style="color: red" class="required">*</span>  -->
													 <input
														type="text" id="ectno" 
														class="form-control placeholder-no-fix"
														placeholder="Enter CT No" name="ECTNO"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label">PT Ratio</label>
													<!--  <span style="color: red" class="required">*</span>  -->
													 <input
														type="text" id="eptno" 
														class="form-control placeholder-no-fix"
														placeholder="Enter PT No" name="EPTNO"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label">MF</label>
													<span style="color: red" class="required">*</span> 
													 <input
														type="text" id="emfid"
														class="form-control placeholder-no-fix"
														placeholder="Enter MF" name="EMFID"
														maxlength="50"></input>
												</div>
											</div>
										
										 <div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Meter Ratio</label>
												<!-- 	 <span style="color: red" class="required">*</span>  -->
													 <input
														type="text" id="emtrratio"
														class="form-control placeholder-no-fix"
														placeholder="Enter Meter Ratio" name="EMTRRATIO"
														maxlength="50"></input>
												</div>
											</div>
											
										
											
											 <div class="col-md-6" id="EEstActiveRule">
											 <div class="form-group">
													<label class="control-label">Export/Import</label>
													<!--  <span style="color: red" class="required">*</span>  -->
												 <select class="form-control select2me" name="EEXPIMPRULE" id="eexpImpRule">
													<option value="">Please Select Export/Import</option>
													<option value="Export">Export</option>
													<option value="Import">Import</option>
												</select>
												</div>
											</div>
											</div>
										</div>
									
										<div class="modal-footer">
											<button class="btn red pull-right" type="button"
												data-dismiss="modal" onclick="formreset()">Cancel</button>
											<button class="btn green pull-left" id="editBoundaryMeter"
												type="button" onclick="editBoundary()">Modify</button>
									  </div>
									</form>
								</div>
							</div>
						</div>
					</div>
			
			
			
			</div>
		</div>
	</div>
</div>