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
						//loadSearchAndFilter('sample_1');
						loadSearchAndFilter('sample_2');
						

						$(
								'#MDMSideBarContents,#meterpointmgmt,#boundary,#mpmId')
								.addClass('start active ,selected');
						$(
								'#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
								.removeClass('start active ,selected');
						//$('#circle').val('').trigger('change');

						var result_flag="${results}";
						var a_flag="${alert_type}";
						if (result_flag !="notDisplay"){
							if (a_flag =="success"){
								bootbox.alert(result_flag);
							}

							if (a_flag =="error"){
								bootbox.alert(result_flag);
							}
							
						} 
						
					});

	$(document).ready(function() {
		//getAllLocation('${officeCode}', '${officeType}');


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


function showCircle(zone) {
		$.ajax({
				url : './getCircleByZone',
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data : {
					zone : zone
				},
				success : function(response) {
				//	alert(response);
					var html = '';
					html += "<select id='LFcircle' name='LFcircle' onchange='showTownNameandCode(this.value)' class='form-control select2me' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
					for (var i = 0; i < response.length; i++) {
						html += "<option value='"+response[i]+"'>"
								+ response[i] + "</option>";
					}
					html += "</select><span></span>";
					$("#LFcircle").html(html);
					$('#LFcircle').select2();
				}
			});
} 


function showTownNameandCode(circle){
//alert(circle);
	var zone = $('#LFzone').val();
	//var zone =  '${newRegionName}';   
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
		  	//	alert(response1);
	  			
                var html = '';
                html += "<select id='LFtown' name='LFtown' onchange='showResultsbasedOntownCode(this.value)' class='form-control  input-medium'  type='text'><option value=''>Select Town </option><option value='%'>ALL</option>";
               
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



	/* function getAllLocation(officeCode, officeType) {
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
								//showTown($("#circle").val());
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
 */
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
						html += "<label class='control-label'>Town:</label><select id='town' name='town' onchange='showSubStaTionByTown(this.value)' class='form-control' type='text'><option value=''>Select Town</option><option value='%'>ALL</option>";

						for (var i = 0; i < response.length; i++) {
							//		alert("response[i]== "+response[i]);
							var resp = response[i];
							html += "<option  value='"+resp[1]+"'>" +resp[1] +"-"+resp[0]
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

	function showResultsbasedOntownCode(town) {
		//alert(town);
		//$('#substation').val('').trigger('change');
		var town = $('#LFtown').val();
		var zone = $('#LFzone').val();
		var circle = $('#LFcircle').val();
		
		$('#substation').empty();
		$('#substation').find('option').remove();
		$('#substation').append($('<option>', {
			value : "",
			text : "Select Sub-Station"
		}));
		$('#substation').append($('<option>', {
			value : "%",
			text : "ALL"
		}));
		
		$.ajax({
			url : './showSubStaTionByTown',
			type : 'GET',
			data : {
				town : town,
				zone:zone,
				circle:circle
			},
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(response1) {
			//	alert(response1);
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
	function getBoundaryData() {
		var ssid = $('#substation').val();
		var town = $('#LFtown').val();
		var circle =$("#LFcircle").val();
		var zone=$("#LFzone").val();
		
		if(zone=='' || zone==null){
			bootbox.alert("Please Select Region");
			return false;
		}
		
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
        $('#imageee').show();
     //  $('#boundayDetailsTbl').dataTable().fnClearTable();
	//	if(town=='013' ){			
		$
				.ajax({
					url : './getBoundaryData',
					type : 'POST',
					data : {
						town : town,
						circle:circle,
					     zone:zone,
						ssid : ssid
					},
					dataType : 'json',
					asynch : false,
					cache : false,
					success : function(response) {
						$('#imageee').hide();
						$("#boundayDetailsTbl").html('');
						//
						if (response != null && response.length > 0) {
							var html = "";
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
								html += "<tr>" 
									+ "<td>"+ (i+1) + "</td>"
									+ "<td>"+ "<a href='#' onclick='editBoundaryMeter(\""+ resp[0]+"\",\""+resp[1]+"\",\""+resp[2]+"\",\""+((resp[3]==null)?"":resp[3])+"\",\""+((resp[4]==null)?"":resp[4])+"\",\""+((resp[5]==null)?"":resp[5])+"\",\""+((resp[6]==null)?"":resp[6])+"\",\""+((resp[7]==null)?"":resp[7])+"\",\""+((resp[8]==null)?"":resp[8])+"\",\""+resp[10]+"\")'  data-toggle='modal' style='padding: 4px;' data-target='#stack4'>Edit</a></td>"
									+ "<td>"+ resp[16] + " </td>"
									+ "<td>"+ resp[17] + " </td>"
									+ "<td>"+ resp[12] + " </td>"
									+ "<td>"+ resp[13] + " </td>"
									+ "<td>"+ resp[0] + " </td>"
									+ "<td>"+ resp[1] + " </td>"
									+ "<td>"+ resp[2] + " </td>"
									+ "<td>"+ resp[3] + " </td>"								
									+ "<td>"+ (resp[14]) + " </td>"
									+ "<td>"+ resp[15] + " </td>"
									+"<td><a target='_blank' href='./viewFeederMeterInfoMDAS?mtrno="+resp[4]+"' style='color:blue;'>"+resp[4]+"</a></td>"
									+ "<td>"+ ((resp[5]==null)?"":resp[5]) + " </td>"
									+ "<td>"+ ((resp[6]==null)?"":resp[6]) + " </td>"
									+ "<td>"+ ((resp[7]==null)?"":resp[7]) + " </td>"
									+ "<td>"+ ((resp[8]==null)?"":resp[8]) + " </td>"
									/* + "<td>"+ ((resp[9]==null)?"":resp[9]) + " </td>" */
									+ "<td>"+ ((resp[10]==null)?"":resp[10]) + " </td>"
									/* + "<td>"+ ((resp[11]==null)?"":((resp[11]==0)?"No":"Yes")) + " </td>" */
									"</tr>";
							 
							}
							$('#sample_2').dataTable().fnClearTable();
							$("#boundayDetailsTbl").html(html);
							loadSearchAndFilter('sample_2');
						} else {
							$('#sample_2').dataTable().fnClearTable();
							$('#imageee').hide();
						//	$('#sample_2').dataTable().fnClearTable();
							bootbox.alert("No Data Found for this Criteria.");
							loadSearchAndFilter('sample_2');
						}
					}
				});
		
			/* }else{
				$('#imageee').hide();
				$('#sample_2').dataTable().fnClearTable();
				loadSearchAndFilter('sample_2');
				bootbox.alert("No Data Found for this Criteria.");
				return false;
		} */

	}
	
	function exportPDF()
    {
		var zone=$('#LFzone').val();
		var circle =$("#LFcircle").val();
		var town = $('#LFtown').val();
		var ssid = $('#substation').val();
  		var townnames = $("#LFtown option:selected").map(function(){return this.text}).get().join(',');
  		var substationname = $("#substation option:selected").map(function(){return this.text}).get().join(',');

		if(zone=="%"){
			zone="ALL";
		}
		
		if(circle=="%"){
			circle="ALL";
		}
		if(town=="%"){
			town="ALL";
		}
		if(ssid=="%"){
			ssid="ALL";
		}			
		//window.open("./boundarypdf/"+circle+"/"+town+"/"+ssid)
		window.location.href=("./boundarypdf?zone="+zone+"&circle="+circle+"&town="+town+"&ssid="+ssid+"&townnames="+townnames+"&substationname="+substationname);
	}

	function Exporttoexcelboundary(){
		
		var zone=$('#LFzone').val();
		var circle =$("#LFcircle").val();
		var town = $('#LFtown').val();
		var ssid = $('#substation').val();
		
		
  // alert(zone);
		if(zone=="%"){
			zone="ALL";
		}
		if(circle=="%"){
			circle="ALL";
		}
		if(town=="%"){
			town="ALL";
		}
		if(ssid=="%"){
			ssid="ALL";
		}
		
		window.location.href=("./boundaryexcel?zone="+zone+"&circle="+circle+"&town="+town+"&ssid="+ssid);
		 

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
				bootbox.alert("RFM assign to Feeder Meter Successfully");
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
	//var feedername= $("#efeedername").val();
	var bdrid= $("#ebdrid").val();	
	var bdrname= $("#ebdrname").val();
	var bdrloc= $("#ebdrloc").val();	
	//var meterStatus= $("input[name='emeter_instl_radio']:checked").val();
	var mtrno= $("#emtrno").val();
	var mtrmkr= $("#emtrmkr").val();
	var ctno= $("#ectno").val();
	var ptno= $("#eptno").val();
	var mfid = $("#emfid").val();
	//var mtrratio= $("#emtrratio").val();
	//var cp=$("#ecp").val();
	var expImp = $("#eexpImpRule").val();

	if (bdrname == '') {
		bootbox.alert("Please Enter Boundary Name");
		return false;
	}

	if (bdrloc == '') {
		bootbox.alert("Please Enter Boundary Location");
		return false;
	}
	if (mtrmkr == '') {
		bootbox.alert("Please Enter Meter Maker");
		return false;
	}
	if (mfid == '') {
		bootbox.alert("Please Calculate MF Value");
		return false;
	}
	if (expImp == '') {
		bootbox.alert("Please Select Export/Import");
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
		url : './editBoundaryDetails',
		type : 'POST',
		data : {
			feedercode  : feederid,
			bdrid	: bdrid,
			bdrname : bdrname,
			bdrloc : bdrloc,
			//meterStatus : meterStatus,
			mtrno : mtrno,
			mtrmkr : mtrmkr,
			ctno : ctno,
			ptno : ptno,
			mfid  : mfid,
			expImp : expImp
		},
		dataType : 'text',
		asynch : false,
		cache : false,
		success : function(response) {
			if(response=='succ'){
				formreset();
				bootbox.alert("Boundary Details Updated SuccessFully");
				$("#stack4").modal('hide');
				getBoundaryData();

			} else{
				formreset();
				bootbox.alert("OOPS! Something went wrong!!");
				$("#stack4").modal('hide');
				getBoundaryData();
			}
		},
		error: function (e) {
			    formreset();
				bootbox.alert("OOPS! Something went wrong!!");
				$("#stack4").modal('hide');
				getBoundaryData();
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

function editBoundaryMeter(fdrcode,bdrid,bdrname,bdrloc,mtrno,mtrmk,ct,pt,mf,exp_imp){
	formreset();	
 	 if(exp_imp == 'Export') {
 		$("#eexpImpRule").val("Export").trigger("change");
	  }
	  if(exp_imp == 'Import')
		{
		  $("#eexpImpRule").val("Import").trigger("change");
		} 

	 $("#efeederid").val(fdrcode);
	 $("#ebdrid").val(bdrid);
	 $("#ebdrname").val(bdrname);
	 $("#ebdrloc").val(bdrloc);

	 $("#emtrno").val(mtrno);
	 $("#emtrmkr").val(mtrmk);
	 $("#ectno").val(ct);
	 $("#eptno").val(pt);
	 //$("#emfid").val(mf);
	
}

function mfCal() {
	//alert("mf cal")
	 $("#emfid").val("");
	 var ct =$("#ectno").val();
	 var pt=$("#eptno").val();

	 if (ct == '') {
			bootbox.alert("Please Enter CT Value");
			return false;
		}

	 if (pt == '') {
			bootbox.alert("Please Enter PT Value");
			return false;
		}
	     
	 
	 if(ct != "" || pt != ""){
		 var  mf =ct*pt;
		 //alert(ct +"   "+pt+" "+mf);
		 $("#emfid").val(mf);
		 }
	 
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
							bootbox.alert("Assign RFM Deleted SuccessFully");
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

function downLoadSample(){

	var zone=$('#LFzone').val();
	var circle =$("#LFcircle").val();
	var town = $('#LFtown').val();
	var ssid = $('#substation').val();

	
	if(zone=="%"){
		zone="ALL";
	}
	if(circle=="%"){
		circle="ALL";
	}
	if(town=="%"){
		town="ALL";
	}
	if(ssid=="%"){
		ssid="ALL";
	}
	window.open("./exportToExcelBoundaryDetails?zone="+zone+"&circle="+circle+"&town="+town+"&ssid="+ssid);
	
	
	//window.location.href="http://1.23.144.187:8102/downloads/sldreport/"+type+".xlsx";
}

function finalSubmit()
{
	 if(document.getElementById("fileUpload").value == "" || document.getElementById("fileUpload").value == null)
		  {
		    bootbox.alert(' Please Select xlsx file to upload');
	 	    return false;
		  }
}


var _validFileExtensions = [".xlsx"];    //,".jpg","jpeg",".png",".gif"
function ValidateSingleInput(oInput) {
    if (oInput.type == "file") {
        var sFileName = oInput.value;
         if (sFileName.length > 0) {
            var blnValid = false;
            for (var j = 0; j < _validFileExtensions.length; j++) {
                var sCurExtension = _validFileExtensions[j];
                if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
                    blnValid = true;
                    break;
                }
            }
             
            if (!blnValid) {
            	//bootbox.alert("Sorry,  " + sFileName + " is invalid, allowed extensions is: " + _validFileExtensions.join(", "));
            	bootbox.alert("Only xlsx file is allowed to Upload");
                oInput.value = "";
                return false;
            }
        }
    }
    return true;
}

</script>



<div class="page-content">
	<c:if test="${not empty msg}">
		<div class="alert alert-success display-show">
			<button class="close" data-close="alert"></button>
			<span style="color: green">${msg}</span>
		</div>
	</c:if>
	
	<div class="row">
		<div class="col-md-12">

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-cogs"></i>Bulk Edit(Boundary Details)			
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
					</div>
				</div>
				<div class="portlet-body">

					<form action="./uploadBoundaryFile" id="uploadFile"
						enctype="multipart/form-data" method="post">

						<div class="form-group">
							<div class="row" style="margin-left: -1px;">
							  <div class="col-md-6" >
							  	<div>
									<label for="exampleInputFile1">Only Upload xlsx File(<a href='#' id='boundarySample' onclick='downLoadSample()' >Download Sample-File</a>)</label> 
									<input type="file" id="fileUpload" onchange="ValidateSingleInput(this);" name="fileUpload"><br />
		
									<button class="btn blue pull-left" style="display: block;"
										id="uploadButton"  onclick="return finalSubmit();">Upload
										File</button>
									&nbsp;
								</div>
							 </div>
							 <div class="col-md-6">
								 <div  style="margin-left: 0px;">
									 <label><b>Note:-</b></label> 
						
									 <ol>
									  <li>User can able to do bulk Update Only.</li>  
									  <li>User can able to update only Boundary_Name,Boundary_Location,Meter_Make,CT,PT and MF.</li>
									  <li>CT,PT and MF should be Number.</li>
									  
									</ol> 
								 </div>
							  </div>
							</div>
						</div>
					</form>

					<div align="center">
						<div class="col-md-6">
							<div id='loadingmessage' style='display: none'>
								<img alt="image" src="./resources/assets/img/Preloader_3.gif"
									class="ajax-loader" width=100px; height=100px;> <span
									id="load" class="wait" style="color: blue;"><b>Uploading
										Data Please Wait</b>!!!!</span>
							</div>
						</div>
					</div>

				</div>

			</div>
		</div>
	</div>


	<div class="row">
		<div class="col-md-12">

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Boundary Details
					</div>
				</div>

				<div class="portlet-body">

					<%--  <c:if test="${officeType eq 'subdivision'|| userType eq 'ADMIN'}">  --%>

					<%-- <div class="row" style="margin-left: -1px;">
						
						
						<div class="col-md-4">
							<div id="ZoneTd" class="form-group">
								<label class="control-label">Region:</label> <select
									class="form-control select2me" id="zone" name="zone"
									onchange="showCircle(this.value);">
									<option value="">Select Region</option>
									<option value="%">ALL</option>
									<c:forEach var="elements" items="${zoneList}">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						
						
						
						<div class="col-md-4">
							<div id="circleTd" class="form-group">
							<label class="control-label">Circle:</label>
								<select class="form-control select2me" id="circle" name="circle"
									onchange="showTownNameandCode(this.value);">
									<option value="">Select Circle</option>
									<option value="%">ALL</option>
									<c:forEach items="${circleList}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-md-4">
							<div id="townId1" class="form-group">
							<label class="control-label">Town:</label>
								<select class="form-control select2me" id="town1" name="town1"
								onchange="showSubStaTionByTown(this.value);">
									<option value="">Select Town</option>
									<option value="%">ALL</option>  
									<c:forEach items="${townList}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>
							</div>
							</div>
							 --%>
							 
							 
							 <jsp:include page="locationFilter.jsp"/> 
							
							
							<div class="row" style="margin-left: -1px;">

					
						<div class="col-md-3">
							<div id="substaTd" class="form-group">
							<label class="control-label"><b>Sub-Station:</b></label>
								<select class="form-control select2me" id="substation"
									name="substation">
									<option value="">Select Sub-Station</option>
									<option value="%">ALL</option> 
									<c:forEach items="${substationList}" var="substation">
										<option value="${substation}">${substation}</option>
									</c:forEach>
								</select>
							</div>

						</div>
						
						<div class="col-md-3" style="margin-top:15px;">
						<button type="button" id="showFeederData"  style="margin-top: 13px;"
											onclick="return getBoundaryData()" name="showFeederData"
											class="btn yellow">
											<b>View</b>
										</button>
						</div>
						
						</div>
				


					<div class="tabbable tabbable-custom">
						<!-- <div id="showFeederData">
							<button type="button" id="viewFeederData"
								style="margin-left: 480px;" onclick="getBoundaryData();"
								name="viewFeederData" class="btn yellow">
								<b>View</b>
							</button>
							<br /> -->

							<div class="table-toolbar">
								<div class="btn-group pull-right" style="margin-top: 16px;">
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<!-- <li><a href="#" id="print">Print</a></li> -->
										<li><a href="#" id=""
								         onclick="exportPDF()">Export to PDF</a></li>
										<li><a href="#" id="excelExport"
											onclick="Exporttoexcelboundary('sample_2', 'Boundary Details')">Export
												to Excel</a></li>
									</ul>
								</div>
							</div>
							<table class="table table-striped table-hover table-bordered"
									id="sample_2">
									<thead>
										<tr>
											<th>SL No</th>
											<th>Action</th> 
											<th>Region</th>
											<th>Circle</th>
											<th>Town </th>
											<th>Feeder Name </th>
											<th>Feeder ID</th>
											<th>Boundary ID</th>
											<th>Boundary Name</th>
											<th>Boundary Location</th>
											<th>Latitude </th>
											<th>Longitude </th>
											<th>Meter No</th>
											<th>Meter Make</th>
											<th>CT</th>
											<th>PT</th>
											<th>MF</th>
											<!-- <th>Meter Ratio</th> -->
											<th>Export/Import</th>
											<!-- <th>Meter Installed</th> -->
										</tr>
									</thead>
									<tbody id="boundayDetailsTbl">
									</tbody>
									<div id="imageee" hidden="true" style="text-align: center;">
										<h3 id="loadingText">Loading..... Please wait.</h3>
										<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
									</div>
								</table>
						</div>

					</div>
					<%--  </c:if> --%>
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
														type="text" id="ebdrname" onkeypress="return ((event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123) || event.charCode == 8 || event.charCode == 32 || (event.charCode >= 48 && event.charCode <= 57));"
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
														type="text" onkeypress="return ((event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123) || event.charCode == 8 || event.charCode == 32 || (event.charCode >= 48 && event.charCode <= 57));" id="ebdrloc"
														class="form-control placeholder-no-fix"
														placeholder="Enter Boundary Location" name="EBDRLOC"
														maxlength="50"></input>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Meter No</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="emtrno" 
														class="form-control placeholder-no-fix"
														placeholder="Enter Meter No" name="EMTRNO" readonly="readonly"
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
																			
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">CT Ratio</label>
													<!--  <span style="color: red" class="required">*</span>  -->
													 <input
														type="number" min='0' max='10000000' onkeypress="return (event.charCode == 8 || event.charCode == 0) ? null : event.charCode >= 48 && event.charCode <= 57" id="ectno" 
														class="form-control placeholder-no-fix"
														placeholder="Enter CT No" name="ECTNO"  
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">PT Ratio</label>
													<!--  <span style="color: red" class="required">*</span>  -->
													 <input
														type="number" min='0' max='10000000' onkeypress="return (event.charCode == 8 || event.charCode == 0) ? null : event.charCode >= 48 && event.charCode <= 57" id="eptno" 
														class="form-control placeholder-no-fix"
														placeholder="Enter PT No" name="EPTNO"
														maxlength="50"></input>
												</div>
											</div>
											
											
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">MF</label>
													<span style="color: red" class="required">*</span> 
													 <input
														type="number" min='0' max='10000000000' onkeypress="return (event.charCode == 8 || event.charCode == 0) ? null : event.charCode >= 48 && event.charCode <= 57" id="emfid"
														class="form-control placeholder-no-fix"
														placeholder="Enter MF" name="EMFID" readonly="readonly"
														maxlength="50"></input>
												</div>
											</div> 
											 
										
									  <!-- <div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Meter Ratio</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="emtrratio"
														class="form-control placeholder-no-fix"
														placeholder="Enter Meter Ratio" name="EMTRRATIO"
														maxlength="50"></input>
												</div>
											</div>  -->
											
										
											
										 <div class="col-md-6" id="EEstActiveRule">
											 <div class="form-group">
													<label class="control-label">Export/Import</label>
													 <span style="color: red" class="required">*</span>  
												 <select class="form-control select2me" name="EEXPIMPRULE" id="eexpImpRule">
													<option value="">Please Select Export/Import</option>
													<option value="Export">Export</option>
													<option value="Import">Import</option>
												</select>
											 </div>
										</div>
										
										 <div class="col-md-6">
												<div class="form-group">
												   <label class="control-label"></label>
													<button class="btn blue pull-left" id="mfCalculation" style="margin-top: 20px;"
												       type="button" onclick="mfCal()">     MF Calculation     </button> 
													  
												</div>
											</div>
									</div>
									<br>
									<br>
									
										<div class="modal-footer">
											
											<button class="btn green pull-right" id="editBoundaryMeter"
												type="button" onclick="editBoundary()">Modify</button>
											<button class="btn red pull-left" type="button"
												data-dismiss="modal" onclick="formreset()">Cancel</button>
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