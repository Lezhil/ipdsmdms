<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- <script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script> -->

<script src="<c:url value='/highcharts/highcharts.js'/>"type="text/javascript"></script>
<script src="<c:url value='/highcharts/exporting.js'/>" type="text/javascript"></script>
<script src="<c:url value='/highcharts/export-data.js'/>"type="text/javascript"></script>
<script src="<c:url value='/highcharts/accessibility.js'/>" type="text/javascript"></script>



<%
	if (session.getAttribute("username") == null || session.getAttribute("username") == null) {
%>
<script>
	window.location.href = "./?sessionVal=expired";
</script>
<%
	}
%>
<style>
#chart_2 {
	height: 700px;
}

.highcharts-figure, .highcharts-data-table table {
	min-width: 560px;
	max-width: 1020px;
	margin: 1em auto;
}

.highcharts-data-table table {
	font-family: Verdana, sans-serif;
	border-collapse: collapse;
	border: 1px solid #EBEBEB;
	margin: 10px auto;
	text-align: center;
	width: 100%;
	max-width: 1000px;
}

.highcharts-data-table caption {
	padding: 1em 0;
	font-size: 1.2em;
	color: #555;
}

.highcharts-data-table th {
	font-weight: 600;
	padding: 0.5em;
}

.highcharts-data-table td, .highcharts-data-table th,
	.highcharts-data-table caption {
	padding: 0.5em;
}

.highcharts-data-table thead tr, .highcharts-data-table tr:nth-child(even)
	{
	background: #f8f8f8;
}

.highcharts-data-table tr:hover {
	background: #f1f7ff;
}
</style>

<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						App.init();
						FormComponents.init();
						getConsumercate();
						var circleVal = "${circleVal}";
						
						$('#reportsId,#consumLoadAnalys')
						.addClass('start active ,selected');
						$('#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
						.removeClass('start active ,selected');
					
						$("#dtcheck").click(
								function() {
									$(".checkboxesd").prop('checked',
											$(this).prop('checked'));
								});
						$("#check1").click(
								function() {
									$(".checkboxes1").prop('checked',
											$(this).prop('checked'));
								});
						$("#fdrcheck").click(
								function() {
									$(".checkboxesf").prop('checked',
											$(this).prop('checked'));
								});
						$('#circle').val('').trigger('change');
					});

	$(document).ready(function() {
		getAllLocation('${officeCode}', '${officeType}');
		/* $('#Consumer_2').click();
		$('#Consumer_2').click();
		$('#Consumer_2').click();
		$('#Consumer_2').click(); */
		$('#DT').click();
		$('#DT').click();
		$('#DT').click();
		$('#DT').click();
		$('#DT').click();
		$('#DT').click();
		$('#DT').click();
		
		reset();
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

							} else if (officeType == "circle") {
								$('#circle').find('option').remove();
								html += "";
								for (var i = 0; i < response.length; i++) {
									$('#circle').append($('<option>', {
										value : response[i],
										text : response[i]
									}));
									$("#circle").val(response[i]).trigger(
											"change");
									//html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
								}

								showDivision($("#circleId").val());
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
</script>



<script>
	function getConsumercate() {
		$('#consumerCatgry').find('option').remove();
		$('#consumerCatgry').append($('<option>', {
			value : "",
			text : "Select Consumer Category"
		}));
		$.ajax({
			url : "./getConsumercatedata",
			type : 'POST',
			success : function(response) {
				for (var i = 0; i <= response.length; i++) {
					$('#consumerCatgry').append($('<option>', {
						value : response[i].trim(),
						text : response[i].trim()
					}));

				}
			}
		});
	}
	/* function showDivision(circle) {
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
						html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control' type='text'><option value=''>Division</option><option value='ALL'>ALL</option>";
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
						html += "<select id='sdoCode' name='sdoCode'  class='form-control' type='text'><option value=''>Sub-Division</option><option value='ALL'>ALL</option>";
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
</script>

<script>

function showCircle(zone) {
	//alert(zone);
	if(officeType == 'region'){
		var zone =  '${newRegionName}';   
	}
			$	.ajax({
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
						html += "<select id='LFcircle' name='circle' onchange='showTownNameandCode(this.value)' class='form-control select2me' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#LFcircleTd").html(html);
						$('#LFcircle').select2();
					}
				});
	}
	
function showTownNameandCode(circle){
	//alert(circle);
		var zone = $('#LFzone').val();
		//var zone =  '${newRegionName}';   
		/* alert(zone); */
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
	                html += "<select id='LFtown' name='LFtown' onchange='showSubStaTionByTown(this.value)' class='form-control  input-medium'  type='text'><option value=''>Select Town </option><option value='%'>ALL</option>";
	               
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
  
  
</script>

<script>

 function showDivision(circle) {
	 $("#circledivsub").show();
	 var zone = '%';
	
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
		
	    var zone = '%';
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
						html += "<select id='sdoCode' name='sdoCode'  class='form-control' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
						for (var i = 0; i < response1.length; i++) {
							html += "<option  value='"+response1[i]+"'>"
									+ response1[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#subdivTd").html(html);
						$('#sdoCode').select2();
					}
				});
	} 

</script>


<script>
	/* function showDivisionDT(circle) {
		var circle = $("#circledt").val();
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
						html += "<select id='divisiondt' name='divisiondt' onchange='showSubdivByDivDT(this.value)' class='form-control ' type='text'><option value=''>Division</option><option value='ALL'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option  value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#divisionTdDt").html(html);
						$('#divisiondt').select2();
					}
				});
	}
	function showSubdivByDivDT(division) {
		var circle = $('#circledt').val();
		var division = $('#divisiondt').val();
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
						html += "<select id='sdoCodedt' name='sdoCodedt'  class='form-control' type='text'><option value=''>Sub-Division</option><option value='ALL'>ALL</option>";
						for (var i = 0; i < response1.length; i++) {
							html += "<option  value='"+response1[i]+"'>"
									+ response1[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#subdivTddt").html(html);
						$('#sdoCodedt').select2();
					}
				});
	} */



	 function showDivisionDT(circle) {
		var circle = $("#circledt").val();
		 var zone = '%';
		
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
							html += "<select id='divisiondt' name='divisiondt' onchange='showSubdivByDivDT(this.value)' class='form-control' type='text'><option value=''>Select Division</option><option value='%'>ALL</option>";
							for (var i = 0; i < response.length; i++) {
								html += "<option  value='"+response[i]+"'>"
										+ response[i] + "</option>";
							}
							html += "</select><span></span>";
							$("#divisionTdDt").html(html);
							$('#divisiondt').select2();
						}
					});
		}
	function showSubdivByDivDT(division) {
			
		    var zone = '%';
		    var circle = $('#circledt').val();
			var division = $('#divisiondt').val();
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
							html += "<select id='sdoCodedt' name='sdoCodedt' onchange='showTownsBySubDivDT(this.value)' class='form-control' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
							for (var i = 0; i < response1.length; i++) {
								html += "<option  value='"+response1[i]+"'>"
										+ response1[i] + "</option>";
							}
							html += "</select><span></span>";
							$("#subdivTddt").html(html);
							$('#sdoCodedt').select2();
						}
					});
		}

	//get Towns 
	function showTownsBySubDivDT(subdivIdName)
	{

		var zone = '%';
	    var circle = $('#circledt').val();
		var division = $('#divisiondt').val();
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
				//alert(response);
				var html="";
				var htmFeeder="";
				var htmlDTC="";
				if(response!=null)
					{
					//htmlDTC+="<option value=0>Select DTC</option>"; 
					html+="<option value=''>Select Town</option><option value='%'>ALL</option>"; 
				//	htmFeeder+="<option value=0>Select Feeder</option>"; 
					for(var i=0;i<response.length;i++)
						{
						html+="<option value='"+response[i][0]+"'>"+ response[i][0]+"-" +response[i][1]+"</option>"; 
					
					$("#dttown").empty();
					$("#dttown").append(html);
					$("#dttown").select2();
					
					}
					}
				
			}
		});
	}

	function showTownNameandCodes(circle){
		var zonee =  $('#zone').val(); 
		if(zonee == null ||zonee == ""){
				zone="%";
			}
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
		  		   /* $('#dttown').append($('<option>', {
	 					value : '%',
	 					text : 'ALL',
	 				})); */ 
	              for (var i = 0; i < response.length; i++) {
	                 
	                  $('#dttown').append($('<option>', {
	  					value : response[i][0],
	  					text : response[i][0] + "-" + response[i][1],
	  				}));
	              }
	            }
		  	});
		  }

	function showTownNameandCode1(circle){
		var zonee =  $('#zone').val(); 
		if(zonee == null ||zonee == ""){
				zone="%";
			}
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
		  		   /* $('#dttown').append($('<option>', {
	 					value : '%',
	 					text : 'ALL',
	 				})); */ 
	              for (var i = 0; i < response.length; i++) {
	                 
	                  $('#fttown').append($('<option>', {
	  					value : response[i][0],
	  					text : response[i][0] + "-" + response[i][1],
	  				}));
	              }
	            }
		  	});
		  }
	  
</script>


<script>
	/* function showDivisionFT(circle) {
		var circle = $("#circleft").val();
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
						html += "<select id='divisionft' name='divisionft' onchange='showSubdivByDivFT(this.value)' class='form-control' type='text'><option value=''>Division</option><option value='ALL'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option  value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#divisionTdFt").html(html);
						$('#divisionft').select2();
					}
				});
	}
	function showSubdivByDivFT(division) {
		var circle = $('#circleft').val();
		var division = $('#divisionft').val();
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
						html += "<select id='sdoCodeft' name='sdoCodeft'  class='form-control ' type='text'><option value=''>Sub-Division</option><option value='ALL'>ALL</option>";
						for (var i = 0; i < response1.length; i++) {
							html += "<option  value='"+response1[i]+"'>"
									+ response1[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#subdivTdFt").html(html);
						$('#sdoCodeft').select2();
					}
				});
	} */

	 function showDivisionFT(circle) {
		var circle = $("#circleft").val();
		 var zone = '%';
		
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
							html += "<select id='divisionft' name='divisionft' onchange='showSubdivByDivFT(this.value)' class='form-control' type='text'><option value=''>Select Division</option><option value='%'>ALL</option>";
							for (var i = 0; i < response.length; i++) {
								html += "<option  value='"+response[i]+"'>"
										+ response[i] + "</option>";
							}
							html += "</select><span></span>";
							$("#divisionTdFt").html(html);
							$('#divisionft').select2();
						}
					});
		}
	function showSubdivByDivFT(division) {
			
		    var zone = '%';
		    var circle = $('#circleft').val();
			var division = $('#divisionft').val();
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
							html += "<select id='sdoCodeft' onchange='showTownsBySubDivFT(this.value)' name='sdoCodeft'  class='form-control' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
							for (var i = 0; i < response1.length; i++) {
								html += "<option  value='"+response1[i]+"'>"
										+ response1[i] + "</option>";
							}
							html += "</select><span></span>";
							$("#subdivTdFt").html(html);
							$('#sdoCodeft').select2();
						}
					});
		}

	function showTownsBySubDivFT(subdivIdName)
	{

		var zone = '%';
		var circle = $('#circleft').val();
	    var division = $('#divisionft').val();
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
				//alert(response);
				var html="";
				var htmFeeder="";
				var htmlDTC="";
				if(response!=null)
					{
					//htmlDTC+="<option value=0>Select DTC</option>"; 
					html+="<option value=''>Select Town</option><option value='%'>ALL</option>"; 
				//	htmFeeder+="<option value=0>Select Feeder</option>"; 
					for(var i=0;i<response.length;i++)
						{
						//html+="<option value='"+response[i][0]+"'>"+response[i][1]+"</option>"; 
						html+="<option value='"+response[i][0]+"'>"+ response[i][0]+"-" +response[i][1]+"</option>"; 
					
					$("#fttown").empty();
					$("#fttown").append(html);
					$("#fttown").select2();
					
					}
					}
				
			}
		});
	}
</script>


<script>
	function getConsumerData() {

		var circle = $('#circle').val();
		var division = $('#division').val();
		var sdoCode = $('#sdoCode').val();
		var consumerCatgry = $('#consumerCatgry').val();
		var acno = $('#acno').val();
		var kno = $('#kno').val();
		var mtrno = $('#mtrno').val();
		$("#sample_1").show();
		if (circle == "") {
			bootbox.alert("Please Select circle");
			return false;
		}

		if (division == "") {
			bootbox.alert("Please Select division");
			return false;
		}

		if (sdoCode == "") {
			bootbox.alert("Please Select Subdivision");
			return false;
		}
		$('#imageee').show();
		$
				.ajax({
					url : './getConsumerConsumpDetailsData',
					type : 'POST',
					data : {
						circle : circle,
						division : division,
						sdoCode : sdoCode,
						consumerCatgry : consumerCatgry,
						acno : acno,
						kno : kno,
						mtrno : mtrno
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
								html += "<tr>"
										+ "<td><input id='check1' name='checkboxm' type='checkbox' value='"+resp[8]+"' class='checkboxes1'/></td>"
										+ "<td>"+ resp[1] + " </td>"
										+ "<td>"+ resp[2] + " </td>"
										+ "<td>"+ (resp[3]==null?"":resp[3]) + " </td>"
										+ "<td>"+ (resp[4]==null?"":resp[4]) + " </td>"
										+ "<td>"+ (resp[5]==null?"":resp[5]) + " </td>"
										+ "<td>"+ (resp[6]==null?"":resp[6]) + " </td>"
										+ "<td>"+ (resp[7]==null?"":resp[7]) + " </td>"
										+ "<td>"+ (resp[8]==null?"":resp[8]) + " </td>" + "</tr>";
							}
							$('#sample_1').dataTable().fnClearTable();
							$("#updateMaster").html(html);
							loadSearchAndFilter('sample_1');
						}else {
							$('#sample_1').dataTable().fnClearTable();
							loadSearchAndFilter('sample_1');
						}
					},
					complete : function() {		
						loadSearchAndFilter('sample_1');
					}
				});

	}
	</script>
	
	
<script>
	function getDTDataDetails() {
		var region = $('#LFzone').val();
		var circle = $('#LFcircle').val();
		//var division = $('#divisiondt').val();
		//var sdoCode = $('#sdoCodedt').val();
		var town = $('#LFtown').val();
		//var crossdt = $("input[name='unasg_dt_radio']").is(":checked");
		var dtcode = $('#udtcode').val();
		//var dtmtrno = $('#umtrcode').val();
		if (region == "") {
			bootbox.alert("Please Select Region");
			return false;
		}
		
		if (circle == "") {
			bootbox.alert("Please Select circle");
			return false;
		}
		if (division == "") {
			bootbox.alert("Please Select Division");
			return false;
		}
		if (sdoCode == "") {
			bootbox.alert("Please Select Sub-Division");
			return false;
		}
		if (town == "") {
			bootbox.alert("Please Select Town");
			return false;
		}
		$('#imageee').show();
		$
				.ajax({
					url : './getDTConsumpDetailsData',
					type : 'GET',
					data : {
						circle : circle,
						region : region,
						town : town,
						crossdt : false,
						dtcode : dtcode,
						//dtmtrno : dtmtrno,
					},
					dataType : 'json',
					success : function(response) {
						$('#imageee').hide();
						$("#view_DT").show();
						//alert("res"+response);
						loadSearchAndFilter('Sample_DTDATA');
						$("#updateMasterDTDATA").html('');
						if (response != null && response.length > 0) {
						
							var html = "";
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
								html += "<tr>"
										+ "<td><input id='dtcheck' name='dtcheck' type='checkbox' class='checkboxesd' value="+resp[4]+"></td>"
										+ "<td>" + resp[0] + " </td>" 
										+ "<td>" + resp[1] + " </td>" 
										+ "<td>" + (resp[2]==null?"":resp[2]) + " </td>" 
										/* + "<td>" + (resp[3]=="0"?"No":"Yes" )+ " </td>"  */
										+ "<td>" + (resp[7]==null?"":resp[7]) + " </td>"
										+ "<td>" + (resp[4]==null?"":resp[4]) + " </td>" 
										+ "<td>" + (resp[5]==null?"":resp[5]) + " </td>" 
										+ "<td>" + (resp[6]==null?"":resp[6]) + " </td>" 
										+ "</tr>";
							}
							$('#Sample_DTDATA').dataTable().fnClearTable();
							$("#updateMasterDTDATA").html(html);
							loadSearchAndFilter('Sample_DTDATA');
							$("#Sample_DTDATA").show()
						} else {
							$('#Sample_DTDATA').dataTable().fnClearTable();
							loadSearchAndFilter('Sample_DTDATA');
							$("#view_DT").hide();
						    bootbox.alert("No DT Meters Found");
						    
						}

					},
					complete: function()
					{  
						//$('#Sample_DTDATADATA').dataTable().fnClearTable();
						//loadSearchAndFilter('Sample_DTDATA');
					}

				});
		//$("#Sample_DTDATA").show();
		
	}
	var crossfdr =true;
	function getFeederDataDetails() {
		$("#view_2").hide();
		$("#view_1").hide();
		var fdrcode = "";
		var region=$("#zoneForFdr").val();
		var circle = $('#circleForFdr').val();
		//var division = $('#divisionft').val();
		//var sdoCode = $('#sdoCodeft').val();
		//var crossfdr = $("input[name='asg_ft_radio']").is(":checked");
		if(crossfdr){
			fdrcode = $('#boundarycode').val();
			}else{
				fdrcode = $('#ufdcode').val();
				} 
		var town = $('#townForFdr').val();
		var fdrmtrno = $('#fdmtrcode').val();
		
		if (region == "") {
			bootbox.alert("Please Select Region");
			return false;
		}
		
		if (circle == "") {
			bootbox.alert("Please Select Circle");
			return false;
		}
		/* if (division == "") {
			bootbox.alert("Please Select Division");
			return false;
		}
		if (sdoCode == "") {
			bootbox.alert("Please Select Sub-Division");
			return false;
		} */
		if (town == "") {
			bootbox.alert("Please Select Town");
			return false;
		}
		$('#imageeee').show();
		//alert("DTTT"+circle+division+"SDO--"+sdoCode+crossfdr+fdrcode+fdrmtrno);
		$
				.ajax({
					url : './getFeederConsumpDetailsData',
					type : 'GET',
					data : {
						
						region : region,
						circle : circle,
						//division : division,
						//sdoCode : sdoCode,
						town : town,
						crossfdr : crossfdr,
						fdrcode : fdrcode,
						fdrmtrno : fdrmtrno,
					},
					dataType : 'json',
					success : function(response) {
						$('#imageeee').hide();
						//alert("res"+response);
						if(crossfdr){
							$("#sample_feeder").hide();
							$("#view_1").hide();
							$("#view_2").show();
							loadSearchAndFilter('sample_boundary');
							$("#updateBoundaryMaster").html('');
							if (response != null && response.length > 0) {
								var html = "";
								for (var i = 0; i < response.length; i++) {
									var resp = response[i];
									html += "<tr>"
											+ "<td><input id='fdrcheck' name='fdrcheck' type='checkbox' class='checkboxesf' value="+resp[5]+"></td>"
											+ "<td>" + resp[0] + " </td>" 
											+ "<td>" + resp[1] + " </td>" 
											+ "<td>" + resp[2] + " </td>" 
											/* + "<td>" + (resp[3]=="0"?"No":"Yes" )+ " </td>" */ 
											+ "<td>" + (resp[6]==null?"":resp[6]) + " </td>" 
											+ "<td>" + (resp[4]==null?"":resp[4]) + " </td>" 
											+ "<td>" + (resp[5]==null?"":resp[5]) + " </td>" 
											+ "</tr>";
								}
								$('#sample_boundary').dataTable().fnClearTable();
								$("#updateBoundaryMaster").html(html);
								loadSearchAndFilter('sample_boundary');
								$("#sample_boundary").show();
							} else {
								$('#sample_feeder').dataTable().fnClearTable();
								loadSearchAndFilter('sample_boundary');
								bootbox.alert("No Boundary Meters Found");
								$("#view_1").hide();
								$("#view_2").hide();
							}
							}
						else{
							$("#view_2").hide();
							$("#view_1").show();
						$("#sample_boundary").hide();
						loadSearchAndFilter('sample_feeder');
						$("#updateFeederMaster").html('');
						if (response != null && response.length > 0) {
							var html = "";
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
								html += "<tr>"
										+ "<td><input id='fdrcheck' name='fdrcheck' type='checkbox' class='checkboxesf' value="+resp[5]+"></td>"
										+ "<td>" + resp[0] + " </td>" 
										+ "<td>" + resp[1] + " </td>" 
										+ "<td>" + resp[2] + " </td>" 
										/* + "<td>" + (resp[3]=="0"?"No":"Yes" )+ " </td>" */ 
										+ "<td>" + (resp[6]==null?"":resp[6]) + " </td>" 
										+ "<td>" + (resp[4]==null?"":resp[4]) + " </td>" 
										+ "<td>" + (resp[5]==null?"":resp[5]) + " </td>" 
										+ "</tr>";
							}
							$('#sample_feeder').dataTable().fnClearTable();
							$("#updateFeederMaster").html(html);
							loadSearchAndFilter('sample_feeder');
							$("#sample_feeder").show();
						} else {
							$('#sample_feeder').dataTable().fnClearTable();
							loadSearchAndFilter('sample_feeder');
							bootbox.alert("No Feeder Meters Found");
							$("#view_1").hide();
							$("#view_2").hide();
						}
					}//outer else end
					}//end  of response
					,
					complete: function()
					{  
						//$('#Sample_DTDATADATA').dataTable().fnClearTable();
						//loadSearchAndFilter('sample_feeder');
					}

				});
		//$("#sample_feeder").show();
	}
</script>
	
	
<script>	

	function viewLoadAnalysisData() {
		$("#chart_2").empty();
		var all_types = [];

		var radioValue = $("input[name='timeRadios']:checked").val();
		var locationType = $("input[name='locationRadios']:checked").val();
		var crossfdr = "";
		
		if(locationType == "DT")
			{
			crossfdr = "false";
			}
		else if(locationType == "feeder")
			{
			crossfdr = "false";
			}
		else
			{
			crossfdr = "true";
			}
		var checkboxes = "";
		var cheeckedboxes = "";
		if (!"Consumer".localeCompare(locationType)) {
			checkboxes = document.getElementsByName('checkboxm');
		} else if (!"DT".localeCompare(locationType)) {
			checkboxes = document.getElementsByName('dtcheck');
		} else {
			checkboxes = document.getElementsByName('fdrcheck');
		}
		for (var i = 0; i < checkboxes.length; i++) {
			if (checkboxes[i].checked) {
				//selected.push(checkboxes[i].value);
				cheeckedboxes = cheeckedboxes + checkboxes[i].value + ",";	
			}
		}
	//	alert("cheeckedboxes === " + cheeckedboxes.replace('on,','').slice(0,cheeckedboxes.length - 1));
		var meterNo=cheeckedboxes.replace('on,','').slice(0,cheeckedboxes.length - 1);

		if("dayWise"==radioValue){
			var reportDate=$("#reportDate").val();
			if(meterNo==""){
				$('#tab1 a[href="#tab_1_1"]').trigger('click');
				navigationButtonshowhide(1);
				bootbox.alert("Please Select Meter");
				
				return false;
				
			}
			if(reportDate==""){
				//navigationButtonshowhide(2);
				/* $("#tab2 a[href='#tab_1_2']").click();
				$("#tab2 a[href='#tab_1_2']").click();
				$("#tab2 a[href='#tab_1_2']").click();
				$("#tab2 a[href='#tab_1_2']").click(); */
				/* $("#tab2 a[href='#tab_1_2']").trigger('click');
				$("#tab2 a[href='#tab_1_2']").trigger('click');
				$("#tab2 a[href='#tab_1_2']").trigger('click');
				$("#tab2 a[href='#tab_1_2']").trigger('click');  */
				
				bootbox.alert("Please Select Date");
				return false;
			}

			var time =[];
			
			var chart;
			var respData;
			var currentDate;
			$.ajax({
				url : './viewDayWiseConsmpData',
				type : 'POST',
				async : false,
				data : {
					reportDate : reportDate,
					meterNo : meterNo,
					locationType :locationType,
					crossfdr : crossfdr
					
				},
				dataType : 'json',
				success : function(response) {
					respData=response;
					var activeArray = [];
					var uploadArray = [];
					var dates = [];
					if (response != null && response.length > 0) {

						var resp = response[0];
						var data = resp.data;
						for (var y = 0; y < data.length; y++) {
							 var mapdata = data[y];
							 time.push(moment(mapdata[0]).format('HH:mm'));
						     currentDate= moment(mapdata[0]).format('DD-MM-YYYY');					
						 }				  		
					}

				}

		});

			chart = new Highcharts.Chart('chart_2', {

			    chart: {
			        type: 'line',
			        zoomType : 'x',
			        panning: true,
			        panKey: 'shift',
			        scrollablePlotArea: {
			            minWidth: 600
			        }
			    },
			    title: {
			        text: 'Consumption Analysis'
			    },
			    subtitle: {
			        text: 'Day Wise Load Analysis for ' +currentDate
			    },
			    xAxis: {
			    	
			        categories: time,
			        title: {
			            text: 'TIME INTERVAL'
			        }
			     
			    },
			    yAxis: {
			    	allowDecimals : true,
			        title: {
			            text: 'KWH'
			        }
			    },
			    plotOptions: {
			        line: {
			            dataLabels: {
			                enabled: true,
			                format: '{value}',
			                padding:'5'
			            },
			            enableMouseTracking: true
			        }
			    }
			    
			});

			for (var i = 0; i < respData.length; i++) {
				var resp = respData[i];
				var meterno = resp.meterno;
				var data = resp.data;
				var kwhdata =[];
				for (var y = 0; y < data.length; y++) {
					 var mapdata = data[y];
					 kwhdata.push(parseFloat(mapdata[1].toFixed(3)));
				}
				chart.addSeries({                        
			           name: meterno,
			           lineWidth:2.0,
			           data: kwhdata
			       }, true);		 
			}
			chart.redraw();

		}
		if("dailyWise" == radioValue){
			
			var reportFromDate=$('#reportFromDate').val();
			var reportToDate=$('#reportToDate').val();
			if(meterNo==""){
				bootbox.alert("Please Select Meter");
				return false;
			}

			if(reportFromDate==""){
				bootbox.alert("Please Select From Date");
				return false;
			}
			if(reportToDate==""){
				bootbox.alert("Please Select To Date");
				return false;
			}

			if(new Date(reportFromDate)>new Date(reportToDate))
			{
				bootbox.alert("Please Select Correct Date Range");
				return false;
			}
	

			var time =[];
			
			var chart;
			var respData;
			var currentDate;
			$.ajax({
				url : './viewDailyWiseConsmpData',
				type : 'POST',
				async : false,
				data : {
					reportFromDate : reportFromDate,
					reportToDate : reportToDate,
					meterNo : meterNo,
					locationType : locationType
				},
				dataType : 'json',
				success : function(response) {
					respData=response;
					var activeArray = [];
					var uploadArray = [];
					var dates = [];
					if (response != null && response.length > 0) {
						var resp = response[0];
						var data = resp.data;
						for (var y = 0; y < data.length; y++) {
							 var mapdata = data[y];
							 time.push(moment(mapdata[0]).format('DD-MM-YYYY'));						
						 }
				  		
					}

				}

		});

			chart = new Highcharts.Chart('chart_2', {

			    chart: {
			        type: 'line',
			        zoomType : 'x',
			        panning: true,
			        panKey: 'shift',
			        scrollablePlotArea: {
			            minWidth: 600
			        }
						        
			    },
			    title: {
			        text: 'Consumption Analysis'
			    },
			    subtitle: {
			        text: 'Daily Wise Load Analysis'
			    },
			    xAxis: {
			    	
			        categories: time,
			        title: {
			            text: 'DATE INTERVAL'
			        }
			     
			    },
			    yAxis: {
			    	allowDecimals : true,
			        title: {
			            text: 'KWH'
			        }
			    },
			    plotOptions: {
			        line: {
			            dataLabels: {
			                enabled: true,
			                format: '{value}',
			                padding:'5'
			            },
			            enableMouseTracking: true
			        }
			    }
			    
			});

			for (var i = 0; i < respData.length; i++) {
				var resp = respData[i];
				var meterno = resp.meterno;
				var data = resp.data;
				var kwhdata =[];
				for (var y = 0; y < data.length; y++) {
					 var mapdata = data[y];
					 kwhdata.push(parseFloat(mapdata[1].toFixed(3)));
				}
				chart.addSeries({                        
			           name: meterno,
			           lineWidth:2.0,
			           data: kwhdata
			       }, true);		 
			}
			chart.redraw();


			
		}
		if("weeklyWise" == radioValue){

			var fwmonth=$('#fwmonth').val();
			var twmonth=$('#twmonth').val();
			var WeklyFWId=$('#WeklyFWId').val();
			var WeklyTFWId=$('#WeklyTFWId').val();
			
				if(meterNo==""){
					bootbox.alert("Please Select Meter");
					return false;
				}
				if(fwmonth==""){			
					bootbox.alert("Please Select From Month");
					return false;
				}
				if(WeklyTFWId==""){
					bootbox.alert("Please Select From Week");
					return false;
				}
				if(twmonth==""){
					bootbox.alert("Please Select To Month");
					return false;
				}
				if(WeklyTFWId==""){
					bootbox.alert("Please Select To Week");
					return false;
				}

	/* 			if(new Date(fwmonth)>new Date(twmonth))
				{
					bootbox.alert("Please Select Correct Date Range");
					return false;
				} */



				var time =[];
				
				var chart;
				var respData;
				var currentDate;
				$.ajax({
					url : './viewWeeklyWiseConsmpData',
					type : 'POST',
					async : false,
					data : {
						fwmonth : fwmonth,
						twmonth : twmonth,
						meterNo : meterNo,
						WeklyFWId : WeklyFWId,
						WeklyTFWId : WeklyTFWId
					},
					dataType : 'json',
					success : function(response) {
						respData=response;
						var activeArray = [];
						var uploadArray = [];
						var dates = [];
						if (response != null && response.length > 0) {
							var resp = response[0];
							var data = resp.data;
							for (var y = 0; y < data.length; y++) {
								 var mapdata = data[y];
								 time.push(moment(mapdata[0]).format('DD-MM-YYYY'));						
							 }
					  		
						}

					}

			});

				chart = new Highcharts.Chart('chart_2', {

				    chart: {
				        type: 'line',
				        zoomType : 'x',
				        panning: true,
				        panKey: 'shift',
				        scrollablePlotArea: {
				            minWidth: 600
				        }
							        
				    },
				    title: {
				        text: 'Consumption Analysis'
				    },
				    subtitle: {
				        text: 'Weekly Wise Load Analysis'
				    },
				    xAxis: {
				    	
				        categories: time,
				        title: {
				            text: 'DAILY INTERVAL'
				        }
				     
				    },
				    yAxis: {
				    	allowDecimals : true,
				        title: {
				            text: 'KWH'
				        }
				    },
				    plotOptions: {
				        line: {
				            dataLabels: {
				                enabled: true,
				                format: '{value}',
				                padding:'5'
				            },
				            enableMouseTracking: true
				        }
				    }
				    
				});

				for (var i = 0; i < respData.length; i++) {
					var resp = respData[i];
					var meterno = resp.meterno;
					var data = resp.data;
					var kwhdata =[];
					for (var y = 0; y < data.length; y++) {
						 var mapdata = data[y];
						 kwhdata.push(parseFloat(mapdata[1].toFixed(3)));
					}
					chart.addSeries({                        
				           name: meterno,
				           lineWidth:2.0,
				           data: kwhdata
				       }, true);		 
				}
				chart.redraw();


			
			
		}
		if("monthlyWise" == radioValue){

		var fmonth=$('#fmonth').val();
		var tmonth=$('#tmonth').val();
			if(meterNo==""){
				bootbox.alert("Please Select Meter");
				return false;
			}

			if(fmonth==""){
				
				bootbox.alert("Please Select From Month");
				navigationButtonshowhide(2);
				return false;
			}
			if(tmonth==""){
				bootbox.alert("Please Select To Month");
				navigationButtonshowhide(2);
				return false;
			}


			var time =[];
			
			var chart;
			var respData;
			var currentDate;
			$.ajax({
				url : './viewMonthlyWiseConsmpData',
				type : 'POST',
				async : false,
				data : {
					fmonth : fmonth,
					tmonth : tmonth,
					meterNo : meterNo,
					locationType : locationType
				},
				dataType : 'json',
				success : function(response) {
					respData=response;
					var activeArray = [];
					var uploadArray = [];
					var dates = [];
					if (response != null && response.length > 0) {
						var resp = response[0];
						var data = resp.data;
						for (var y = 0; y < data.length; y++) {
							 var mapdata = data[y];
							 time.push(mapdata[0]);						
						 }
				  		
					}

				}

		});

			chart = new Highcharts.Chart('chart_2', {

			    chart: {
			        type: 'line',
			        zoomType : 'x',
			        panning: true,
			        panKey: 'shift',
			        scrollablePlotArea: {
			            minWidth: 600
			        }
						        
			    },
			    title: {
			        text: 'Consumption Analysis'
			    },
			    subtitle: {
			        text: 'Monthly Wise Load Analysis'
			    },
			    xAxis: {
			    	
			        categories: time,
			        title: {
			            text: 'MONTH INTERVAL'
			        }
			     
			    },
			    yAxis: {
			    	allowDecimals : true,
			        title: {
			            text: 'KWH'
			        }
			    },
			    plotOptions: {
			        line: {
			            dataLabels: {
			                enabled: true,
			                format: '{value}',
			                padding:'5'
			            },
			            enableMouseTracking: true
			        }
			    }
			    
			});

			for (var i = 0; i < respData.length; i++) {
				var resp = respData[i];
				var meterno = resp.meterno;
				var data = resp.data;
				var kwhdata =[];
				for (var y = 0; y < data.length; y++) {
					 var mapdata = data[y];
					 kwhdata.push(parseFloat(mapdata[1].toFixed(3)));
				}
				chart.addSeries({                        
			           name: meterno,
			           lineWidth:2.0,
			           data: kwhdata
			       }, true);		 
			}
			chart.redraw();


		
		}
		

		
		return;
	}
	</script>
	<script>

	function consumerMeterLoc() {
		$('#consumerCatgry').val('');
		$('#acno').val('');
		$('#kno').val('');
		$('#mtrno').val('');
		$("#circledivsub").show();
		$("#ushowDT").hide();
		$("#ushowFeeder").hide();
		$("#Sample_DTDATA").hide();
		$("#sample_feeder").hide();
		$('#circle').val('').trigger('change');

		$('#sdoCode').val('').trigger('change');
		$('#sdoCodedt').val('').trigger('change');
		$('#dttown').val('').trigger('change');
		$('#fttown').val('').trigger('change');
	}

	function dtMeterLoc() {
		$("#view_DT").hide();
		
		$('#udtcode').val('');
		$('#umtrcode').val('');
		$("#circledivsub").hide();
		$("#ushowDT").show();
		$("#sample_1").hide();
		$("#ushowFeeder").hide();
		$("#sample_feeder").hide();
		$("#sample_1").hide();
		$("#sample_1_wrapper").hide();
		crossfdr =false;
		$('#circledt').val('').trigger('change');
		$('#sdoCodedt').val('').trigger('change');
		$('#dttown').val('').trigger('change');
		

	}

	function feederMeterLoc() {
		$('#sample_feeder').dataTable().fnClearTable();
		$("#updateBoundaryMaster").html('');
		$('#sample_boundary').dataTable().fnClearTable();
		$("#updateFeederMaster").html('');
		$("#view_2").hide();
		$("#view_1").hide();
		
		$("#sample_boundary").hide();
		$('#boundarycode').val('');
		$('#ufdcode').val('');
		$('#fdmtrcode').val('');
		$("#circledivsub").hide();
		$("#ushowDT").hide();
		$("#ushowFeeder").show();
		$("#sample_1").hide();
		$("#Sample_DTDATA").hide();
		$("#sample_1_wrapper").hide();
		$("#boundaryDiv").hide();
		$("#feederDiv").show();
		crossfdr =false;

		$('#circleft').val('').trigger('change');
		$('#sdoCodeft').val('').trigger('change');  
		//$('#dttown').val('').trigger('change');
		$('#fttown').val('').trigger('change');

	}

	function boundaryMeterLoc() {
		$('#sample_feeder').dataTable().fnClearTable();
		$("#updateBoundaryMaster").html('');
		$('#sample_boundary').dataTable().fnClearTable();
		$("#updateFeederMaster").html('');

		$("#view_2").hide();
		$("#view_1").hide();
		
		$("#sample_feeder").hide();
		$('#boundarycode').val('');
		$('#ufdcode').val('');
		$('#fdmtrcode').val('');
		$("#circledivsub").hide();
		$("#ushowDT").hide();
		$("#ushowFeeder").show();
		$("#sample_1").hide();
		$("#Sample_DTDATA").hide();
		$("#sample_1_wrapper").hide();
		$("#feederDiv").hide();
		$("#boundaryDiv").show();
		crossfdr =true;

		$('#circleft').val('').trigger('change');
		$('#sdoCodeft').val('').trigger('change');  
		//$('#dttown').val('').trigger('change');
		$('#fttown').val('').trigger('change');

	}
</script>

<div class="page-content">
	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-Book"></i><strong>Consumption Analysis</strong>
			</div>
		</div>
		<div class="portlet-body">
			<div class="tabbable tabbable-custom">
				<ul class="nav nav-tabs">
					<li class="active" id="tab1"><a href="#tab_1_1" data-toggle="tab"  onclick="navigationButtonshowhide(1);">Metering Location</a></li>
					<li id="tab2"><a href="#tab_1_2" data-toggle="tab" id="b1" onclick="navigationButtonshowhide(2);">Time Period</a></li>
					<li id="tab3"><a href="#tab_1_3" data-toggle="tab" id="b2" onclick="navigationButtonshowhide(3);">Consumption Analysis</a></li>
				</ul>
				<div class="form-group" style="margin-top: 19px; float: right;">
					<a class="btn green btnPrevious" id="previous" style="display:none">Previous</a>
					<a class="btn green btnNext" id="next">Next </a>
				</div>
				<br>
				<div class="tab-content">

					<!-- ------------Metering loaction tab------------ -->
					<div class="tab-pane active" id="tab_1_1">
						<div class="box">
							<div class="tab-content">
								<div id="tab_1-1" class="tab-pane active">
									<div class="form-group" id="meteringtype">
										<div class="mt-radio-inline">
											<!-- <label class="mt-radio"> <input type="radio"
												name="locationRadios" id="Consumer_2" value="Consumer"
												onclick="return consumerMeterLoc();"> Consumer <span></span></label> -->
											<label class="mt-radio"> <input type="radio"
												name="locationRadios" id="DT" value="DT"
												onclick="return dtMeterLoc();"> DT <span></span></label> <label
												class="mt-radio"> <input type="radio"
												name="locationRadios" id="feeder" value="feeder"
												onclick="return feederMeterLoc();"> Feeder <span></span></label>
												<label class="mt-radio"><input type="radio"
												name="locationRadios" id="boundary" value="boundary"
												onclick="return boundaryMeterLoc();"> Boundary <span></span></label>
										</div>
									</div>
									<!-- -------------Consumer------------- -->
									<div class="row" style="margin-left: -1px;" id="circledivsub"
										hidden="true">
										<div class="col-md-3">
											<div id="circleTd" class="form-group">
												<select class="form-control select2me" id="circle"
													name="circle" onchange=" return showDivision(this.value);">
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
													name="division" onchange="showSubdivByDiv(this.value);">
													<option value="">Select Division</option>
													<option value="ALL">ALL</option>
													<c:forEach items="${divisionList1}" var="elements">
														<option value="${elements}">${elements}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="col-md-3">
											<div id="subdivTd" class="form-group">
												<select class="form-control select2me" id="sdoCode"
													name="sdoCode">
													<option value="">Select Sub-Division</option>
													<option value="ALL">ALL</option>
													<c:forEach items="${subdivList}" var="sdoVal">
														<option value="${sdoVal}">${sdoVal}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="row" style="margin-left: -1px;">
											<div class="col-md-3">
												<div class="form-group">
													<select class="form-control select2me" id="consumerCatgry"
														name="consumerCatgry">
														<option value="">Select Consumer Category</option>
													</select>
												</div>
											</div>
											<div class="row" style="margin-left: -1px;">
												<div class="col-md-3" style="width: 265px;">
													<div class="form-group">
														<input type="text" id="acno"
															class="form-control placeholder-no-fix"
															placeholder="Enter Account No." name="acno"
															maxlength="12"></input>
													</div>
												</div>
												<div class="col-md-3" style="width: 264px;">
													<div class="form-group">
														<input type="text" id="kno"
															class="form-control placeholder-no-fix"
															placeholder="Enter K No." name="kno" maxlength="12"></input>
													</div>
												</div>
												<div class="col-md-3" style="width: 262px;">
													<div class="form-group">
														<input type="text" id="mtrno"
															class="form-control placeholder-no-fix"
															placeholder="Enter Meter Sr No." name="mtrno"
															maxlength="12"></input>
													</div>
												</div>
											</div>
										</div>

										<button type="button" id="viewConsumerData"
											style="margin-left: 480px;" onclick="getConsumerData()"
											name="viewConsumerData" class="btn yellow">
											<b>View</b>
										</button>
										<br> <br>
									</div>
								</div>
								<table class="table table-striped table-hover table-bordered"
									id="sample_1" hidden="true">
									<thead>
										<tr>
											<th><input id='check1' name='checkboxm' type='checkbox'
												class='checkboxes1'></th>
											<th>Circle</th>
											<th>Division</th>
											<th>Sub Division</th>
											<th>Account No</th>
											<th>K No</th>
											<th>Customer Name</th>
											<th>Category</th>
											<th>Meter Sr. No</th>
										</tr>
									</thead>
									<tbody id="updateMaster">

									</tbody>
								</table>
								
			
								<!-- -----------DT ------------- -->
								<div id="ushowDT" style="display:none">
									<div class="row" style="margin-left: -1px;">
											<jsp:include page="locationFilter.jsp"/> 
									
										<%-- <div class="col-md-4"><b>Circle:</b>
											<div id="circleTd" class="form-group">
												<select class="form-control select2me" id="circledt"
													name="circle"
													onchange=" return showTownNameandCode(this.value);">
													<option value="">Select Circle</option>
													<option value="%">ALL</option>
													<c:forEach items="${circleList}" var="elements">
														<option value="${elements}">${elements}</option>
													</c:forEach>
												</select>
											</div>
										</div> --%>
										<%-- <div class="col-md-4"><b>Division:</b>
											<div id="divisionTdDt" class="form-group">
												<select class="form-control select2me" id="divisiondt"
													name="divisiondt">
													<option value="">Select Division</option>
													<option value="%">ALL</option>
													<c:forEach items="${divisionList1}" var="elements">
														<option value="${elements}">${elements}</option>
													</c:forEach>
												</select>
											</div>
										</div> --%>
										<%-- <div class="col-md-4"><b>Sub-Division:</b>
											<div id="subdivTddt" class="form-group">
												<select class="form-control select2me" id="sdoCodedt"
													name="sdoCodedt">
													<option value="">Select Sub-Division</option>
													<option value="ALL">ALL</option>
													<c:forEach items="${subdivList}" var="sdoVal">
														<option value="${sdoVal}">${sdoVal}</option>
													</c:forEach>
												</select>
											</div>
										</div> --%>
									
									<!-- <div class="row" style="margin-left: -1px;">
										<div class="col-md-4" id="dttownId"><b>Town:</b>
											<div class="form-group">
													<select class="form-control select2me" id="dttown" name="dttown">
															<option value="">Select Town</option>
															<option value="%">ALL</option>
															</select>
											</div>
										</div> -->
										<div class="col-md-3"><b>DT Code:</b>
											<div class="form-group">
												<!-- <label class="control-label">DT Code</label>  -->
												<input type="text" id="udtcode"
													class="form-control placeholder-no-fix"
													placeholder="Enter DT Code." name="udtcode" maxlength="12"></input>
											</div>
										</div>
										<!-- </div> -->
										<!-- <div class="col-md-4">
											<div class="form-group">
												<label>Cross DT : </label>
												<div class="make-switch" data-on="success"
													data-off="warning" style="position: relative; left: 11%;">
													<input type="checkbox" id="unasg_dt_radio"
														name="unasg_dt_radio" checked="checked" class="toggle" />
												</div>
											</div>
										</div> -->
										<!-- <div class="col-md-4"><b>MeterNo:</b>
											<div class="form-group">
												<label class="control-label">Meter Sr No</label> <span
																style="color: red" class="required">*</span> 
												<input type="text" id="umtrcode"
													class="form-control placeholder-no-fix"
													placeholder="Enter Meter Sr No." name="umtrcode"
													maxlength="12"></input>
											</div>
										</div> -->
										<div class="col-md-3">
											<div class="form-group">
										<button type="button" id="viewDTData"
											style="margin-top: 18px;"
											onclick="return getDTDataDetails()" name="viewDTDataDetails"
											class="btn yellow">
											<b>View</b>
										</button>
										</div>
										</div>
										<br> <br> <br>
                     <div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
					<br>
					<br>
                       <div id="view_DT" style="margin-right: 9px;">
										<table class="table table-striped table-hover table-bordered"
											id="Sample_DTDATA" hidden="true">
											<thead>
												<tr>
													<th><input id='dtcheck' name='dtcheck' type='checkbox'
												    class='checkboxesd'></th>
													<th>Circle</th>
													<th>Division</th>
													<th>Sub Division</th>
													<!-- <th>Cross DT</th> -->
													<th>DT Name</th>
													<th>DT Code</th>
													<th>DT Capacity</th>
													<th>Meter Sr. No</th>
												</tr>
											</thead>
											<tbody id="updateMasterDTDATA">
											</tbody>
										</table>
						              </div>
								</div>
							</div>
								<!-- -------------Feeder------------------ -->
								<div id="ushowFeeder" style="display:none">
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

										<%-- <div class="col-md-3"><b>Circle:</b>
											<div id="circleTd" class="form-group">
												<select class="form-control select2me" id="circleft"
													name="circleft"
													onchange=" return showTownNameandCode1(this.value);">
													<option value="">Select Circle</option>
													<option value="%">ALL</option>
													<c:forEach items="${circleList}" var="elements">
														<option value="${elements}">${elements}</option>
													</c:forEach>
												</select>
											</div>
										</div> --%>
										<%-- <div class="col-md-3"><b>Division:</b>
											<div id="divisionTdFt" class="form-group">
												<select class="form-control select2me" id="divisionft"
													name="divisionft">
													<option value="">Select Division</option>
													<option value="ALL">ALL</option>
													 <c:forEach items="${divisionList1}" var="elements">
														<option value="${elements}">${elements}</option>
													</c:forEach> 
												</select>
											</div>
										</div> --%>
										<%-- <div class="col-md-3"><b>Sub-Division:</b>
											<div id="subdivTdFt" class="form-group">
												<select class="form-control select2me" id="sdoCodeft"
													name="sdoCodeft">
													<option value="">Select Sub-Division</option>
													<option value="ALL">ALL</option>
													<c:forEach items="${subdivList}" var="sdoVal">
														<option value="${sdoVal}">${sdoVal}</option>
													</c:forEach>
												</select>
											</div>
										</div> --%>
										
										<!-- <div class="col-md-3" id="fttownId"><b>Town:</b>
											<div class="form-group">
													<select class="form-control select2me" id="fttown" name="fttown">
															<option value="">Select Town</option>
															<option value="%">ALL</option>
															</select>
											</div>
										</div> -->
										
										
										
									</div>
									<div class="row" style="margin-left: -1px;">
									
									<div class="col-md-3" id="feederDiv" style="display:none"><b>Feeder Code:</b>
											<div class="form-group">
												<!-- <label class="control-label">DT Code</label>  -->
												<input type="text" id="ufdcode"
													class="form-control placeholder-no-fix"
													placeholder="Enter Feeder Code." name="ufdcode"
													maxlength="12" style="width: 241px;"></input>
											</div>
										</div>
										
									<div class="col-md-3" id="boundaryDiv" style="display:none"><b>Boundary Code:</b>
											<div class="form-group">
												<!-- <label class="control-label">DT Code</label>  -->
												<input type="text" id="boundarycode"
													class="form-control placeholder-no-fix"
													placeholder="Enter Boundary Code." name="boundarycode"
													maxlength="12" style="width: 241px;"></input>
											</div>
										</div>
									
										 <!-- <div class="col-md-3">
											<div class="form-group"><b>Boundary Feeder:</b><br>
												<div class="make-switch" data-on="success"
													data-off="warning" style="position: relative; left: 11%;">
													<input type="checkbox" id="asg_ft_radioo id is wrong"
														name="asg_ft_radio" checked="checked" class="toggle" />
												</div>
											</div>
										</div> -->
										<div class="col-md-3"><b>MeterNo:</b>
											<div class="form-group">
												<!-- <label class="control-label">Meter Sr No</label> <span
																style="color: red" class="required">*</span>  -->
												<input type="text" id="fdmtrcode"
													class="form-control placeholder-no-fix"
													placeholder="Enter Meter Sr No." name="fdmtrcode"
													maxlength="12" style="width: 241px;"></input>
											</div>
										</div>
										<div class="col-md-3"> 
											<div class="form-group">
										<button type="button" id="viewFeederData" style="margin-top: 18px;"
											onclick="return getFeederDataDetails();"
											name="viewFeederDataDetails" class="btn yellow">
											<b>View</b>
										</button>
										</div>
										</div>
										<br> 
										<br> 
										<br>
						<div id="imageeee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					    </div>
					    <br>
					    <br>
										
                             <div id="view_1" style="margin-right: 9px;">
										<table class="table table-striped table-hover table-bordered"
											id="sample_feeder" hidden="true">
											<thead>
												<tr>
													<th><input id='fdrcheck' name='fdrcheck' type='checkbox'
												class='checkboxesf'></th>
													<th>Circle</th>
													<th>Division</th>
													<th>Sub Division</th>
													<!-- <th>Boundary Feeder</th> -->
													<th>Feeder Name</th>
													<th>Feeder Code</th>
													<th>Meter Sr. No</th>
												</tr>
											</thead>
											<tbody id="updateFeederMaster">
											</tbody>
										</table>
								</div>
								<div id="view_2" style="margin-right: 9px;">
										<table class="table table-striped table-hover table-bordered"
											id="sample_boundary" hidden="true">
											<thead>
												<tr>
													<th><input id='fdrcheck' name='fdrcheck' type='checkbox'
												class='checkboxesf'></th>
													<th>Circle</th>
													<th>Division</th>
													<th>Sub Division</th>
													<!-- <th>Boundary Feeder</th> -->
													<th>Boundary Name</th>
													<th>Boundary Code</th>
													<th>Meter Sr. No</th>
												</tr>
											</thead>
											<tbody id="updateBoundaryMaster">
											</tbody>
										</table>
									</div>	
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="tab-pane" id="tab_1_2">
						<div class="box">
							<div id="tab_1-1" class="tab-pane active">
								<div class="row">
									<div class="form-group col-md-6">
										<div class="form-group" id="meteringtype">
											<div class="mt-radio-inline">
												<label class="mt-radio"> <input type="radio"
													name="timeRadios" id="dayWise" value="dayWise"
													onchange="hidedlywm(this)"> Date Wise <span></span>
												</label> 
												<label class="mt-radio"> <input type="radio"
													name="timeRadios" id="dailyWise" value="dailyWise"
													onchange="hidedwm(this)"> Daily Wise <span></span>
												</label> 
												 <!-- <label class="mt-radio"> <input type="radio"
													name="timeRadios" id="weeklyWise" value="weeklyWise"
													onchange="hidedlydm(this)"> Weekly Wise <span></span>
												</label>  -->  
												<label class="mt-radio"> <input type="radio"
													name="timeRadios" id="monthlyWise" value="monthlyWise"
													onchange="hidedlydw(this)"> Monthly Wise <span></span>
												</label>
											</div>
										</div>
										
										<div id="showDay" style="display: none">
												<div class="row" style="margin-left: -1px;">
														 <div class="col-md-6">
																<div class="form-group">
																	<label><b>Date : </b></label>

																		<div class="input-group date date-picker" data-date-format="yyyy-mm-dd"  data-date-end-date="0d" data-date-viewmode="years">
																		<input type="text" class="form-control" placeholder="Select Date" name="reportDate" id="reportDate"  style="cursor: pointer">
																		<span class="input-group-btn">
																		<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
																		</span>
																		</div>
																</div>
														</div>		
												</div>	
								         </div>

										<div id="showDaily" style="display: none">
												<div class="row" style="margin-left: -1px;">
														        <div class="col-md-6">
																	<div class="form-group">
																		<label>From Date : </label>
	
																			<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" data-date-end-date="0d" data-date-viewmode="years">
																			<input type="text" placeholder="Select From Date" class="form-control" name="reportFromDate" id="reportFromDate"  style="cursor: pointer">
																			<span class="input-group-btn">
																			<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
																			</span>
																			</div>
																	</div>	
																</div>	
																<div class="col-md-6">
																	<div class="form-group">
																		<label>To Date : </label>
	
																			<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" data-date-end-date="0d" data-date-viewmode="years">
																			<input type="text" class="form-control" placeholder="Select To Date" name="reportToDate" id="reportToDate"  style="cursor: pointer">
																			<span class="input-group-btn">
																			<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
																			</span>
																			</div>
																	</div>
																</div>
																
												</div>	
								         </div>

										<div id="showWeekly" style="display: none">
												<div class="row" style="margin-left: -1px;">
														        <div class="col-md-6">
														        <label>Select From Month : </label>
																	<div class="input-group ">
																	
																		<input type="text" class="form-control from"  id="fwmonth"
																			name="fwmonth" placeholder="Select From Month" onchange="getWeeksInMonth()" style="cursor: pointer"> 
																		 <span class="input-group-btn">
																			<button class="btn default" type="button">
																				<i class="fa fa-calendar"></i>
																			</button>
																		</span>
																	</div>	
																</div>	
																<div class="col-md-6">
															        <label class="control-label">From Week:</label>  
																	<select class="form-control select2me input-small" id="WeklyFWId" name="WeklyFWId">
																			<option value="">Select Week</option>
																			
																	</select>	
																</div>
																
												</div>	
												
												<div class="row" style="margin-left: -1px;">
														        <div class="col-md-6">
														        <label>Select To Month : </label>
																	<div class="input-group ">
																	
																		<input type="text" class="form-control from"  id="twmonth"
																			name="twmonth" placeholder="Select To Month" onchange="getToWeeksInMonth()" style="cursor: pointer"> <span
																			class="input-group-btn">
																			<button class="btn default" type="button">
																				<i class="fa fa-calendar"></i>
																			</button>
																		</span>
																	</div>	
																</div>	
																<div class="col-md-6">
																	<label class="control-label">To Week:</label>  
																	<select class="form-control select2me input-small" id="WeklyTFWId" name="WeklyTFWId">
																			<option value="">Select Week</option>
																	</select>
																</div> 
																
												</div>
								         </div>

										<div id="showMonthly" style="display: none">
												<div class="row" style="margin-left: -1px;">
														        <div class="col-md-6">
														        <label>Select From Month : </label>
																	<div class="input-group ">
																	
																		<input type="text" class="form-control from"  id="fmonth"
																			name="fmonth" placeholder="Select From Month" style="cursor: pointer"> <span
																			class="input-group-btn">
																			<button class="btn default" type="button">
																				<i class="fa fa-calendar"></i>
																			</button>
																		</span>
																	</div>	
																</div>	
																
																 <div class="col-md-6">
														        <label>Select To Month : </label>
																	<div class="input-group ">
																	
																		<input type="text" class="form-control from"  id="tmonth"
																			name="tmonth" placeholder="Select To Month" style="cursor: pointer"> <span
																			class="input-group-btn">
																			<button class="btn default" type="button">
																				<i class="fa fa-calendar"></i>
																			</button>
																		</span>
																	</div>	
																</div>
																
																
												</div>	
								         </div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="tab-pane" id="tab_1_3">
						<div class="box">
							<div id="tab_1-2" class="tab-pane active">
							<br>
							<br>						
								<div class="row">
								<div class="col-md-12">
						
									<div class="portlet box blue">
										<div class="portlet-title"></div>					
										<div class="portlet-body">
										<div id="chart_2"  style="min-width: 310px; height: 400px; margin: 0 auto" class="chart"></div>
											<!-- <div id="chart_2" style="min-width: 310px; height: 400px; margin: 0 auto" class="chart"></div> -->
										</div>
						
									</div>
								</div>
								</div>
								
											
							
											
											
										
							</div>
						</div>
					</div>
					
					
					
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	function navigationButtonshowhide(val) {
		if (val == 1) {
			//$('#circle').val('').trigger('change');
			//$('#circledt').val('').trigger('change');
			//$('#circleft').val('').trigger('change');
			//$('#sdoCodeft').val('').trigger('change');  
			//$('#sdoCode').val('').trigger('change');
			//$('#sdoCodedt').val('').trigger('change');
			//$('#Consumer_2').click();
			//$('#Consumer_2').click();
			$("#previous").hide();
			$("#next").show();
		} else if (val == 2) {	
			/* $("#showDay").show();
			$('#dayWise').click();
			$('#dayWise').click(); */	
			//$("#reportDate").val('');	
			validations(1);
			
			$("#previous").show();
			$("#next").show();
		} else if (val == 3) {
			$("#previous").show();
			$("#next").hide();
			viewLoadAnalysisData();
		}
	}

	var tabInd = 0;
	function navigateTabs(val) {
		var p = tabInd - 1;
		var n = tabInd + 1;
		if (val == 1) {
			$("#tab" + p).removeClass('active');
			$("#tab" + tabInd).addClass('active');
			tabInd = tabInd - 1;
			$("#tab" + tabInd).click();
		} else if (val == 2) {
			$("#tab" + tabInd).removeClass('active');
			$("#tab" + n).addClass('active');
			tabInd = tabInd + 1;
			$("#tab" + tabInd).click();
		}
	}
	

	$('.btnNext').click(function() {

		var obj=$('.nav-tabs > .active').next('li').find('a');
		if(validations(obj[0].id))
		{
		  $('.nav-tabs > .active').next('li').find('a').trigger('click');
		}
		
		//$('.nav-tabs > .active').next('li').find('a').trigger('click');
	});

	$('.btnPrevious').click(function() {
		$('.nav-tabs > .active').prev('li').find('a').trigger('click');
	});


	function validations(val)
	{
	    if (val == 'b1')
		{
	    	var locationType = $("input[name='locationRadios']:checked").val();
	    	var checkboxes = "";
			var cheeckedboxes = "";
			if (!"Consumer".localeCompare(locationType)) {
				checkboxes = document.getElementsByName('checkboxm');
			} else if (!"DT".localeCompare(locationType)) {
				checkboxes = document.getElementsByName('dtcheck');
			} else {
				checkboxes = document.getElementsByName('fdrcheck');
			}
			for (var i = 0; i < checkboxes.length; i++) {
				if (checkboxes[i].checked) {
					//selected.push(checkboxes[i].value);
					cheeckedboxes = cheeckedboxes + checkboxes[i].value + ",";	
				}
			}
		//	alert("cheeckedboxes === " + cheeckedboxes.replace('on,','').slice(0,cheeckedboxes.length - 1));
			var meterNo=cheeckedboxes.replace('on,','').slice(0,cheeckedboxes.length - 1);
				
			if(meterNo==""){
				bootbox.alert("Please Select Meter");
				return false; 
				}
	    	
			   
		}
		if(val == 'b2')
		{

			var radioValue = $("input[name='timeRadios']:checked").val();
			if("dayWise"==radioValue){
				var reportDate=$("#reportDate").val();
				//alert(reportDate);
				//$("#showDay").show();
				if(reportDate==""){
					$("#showDay").show();
					bootbox.alert("Please Select Date");
					return false;
				}
				 
				}
			if("dailyWise" == radioValue){
				var reportFromDate=$('#reportFromDate').val();
				var reportToDate=$('#reportToDate').val();

				if(reportFromDate==""){
					$("#showDaily").show();
					bootbox.alert("Please Select From Date");
					return false;
				}
				if(reportToDate==""){
					$("#showDaily").show();
					bootbox.alert("Please Select To Date");
					return false;
				}

				if(new Date(reportFromDate)>new Date(reportToDate))
				{
					$("#showDaily").show();
					bootbox.alert("Please Select Correct Date Range");
					return false;
				}

				}
			if("weeklyWise" == radioValue){
				$("#showWeekly").show();
				}
			if("monthlyWise" == radioValue){
				var fmonth=$('#fmonth').val();
				var tmonth=$('#tmonth').val();

				if(fmonth==""){
					$("#showMonthly").show();
					bootbox.alert("Please Select From Month");
					//navigationButtonshowhide(2);
					return false;
				}
				if(tmonth==""){
					$("#showMonthly").show();
					bootbox.alert("Please Select To Month");
					//navigationButtonshowhide(2);
					return false;
				}
				
			}

			
		} 
		return true;
	}

	
	
</script>

<script>
function hidedlywm(x)
{
	if (x.checked) {  
		//alert("dayWise");
		$("#showDay").show();
		//$('#dayWise').click();
		//$('#dayWise').click();
		//$('#dayWise').click();
		//$('#dayWise').click();
		//$("#reportDate").val('');		
		$("#showDaily").hide();
		$("#showWeekly").hide(); 
		$("#showMonthly").hide(); 
     }

}

function hidedwm(x)
{
	if (x.checked) {
		//$("#reportFromDate").val('');
		//$("#reportToDate").val('');
		$("#showDay").hide(); 
		$("#showDaily").show();
		$("#showWeekly").hide(); 
		$("#showMonthly").hide(); 
	}
}

function  hidedlydm(x)
{
	if (x.checked) {
		//$("#fwmonth").val('');
		//$("#twmonth").val('');
		//$('#WeklyFWId').val('').trigger('change');
		//$('#WeklyTFWId').val('').trigger('change');		
		$("#showDay").hide();
		$("#showDaily").hide();
		$("#showWeekly").show(); 
		$("#showMonthly").hide();
	}
}

function  hidedlydw(x)
{
	if (x.checked) {
	//	$("#fmonth").val('');
	//	$("#tmonth").val('');
		$("#showDay").hide();
		$("#showDaily").hide();
		$("#showWeekly").hide(); 
		$("#showMonthly").show();
	}
}


function  reset()
{
	$('#dayWise').click();
	$('#dayWise').click();
	$('#dayWise').click();
	$('#dayWise').click();

	$('#circle').val('').trigger('change');
	$('#circledt').val('').trigger('change');
	$('#circleft').val('').trigger('change');
	$('#sdoCodeft').val('').trigger('change');  
	$('#sdoCode').val('').trigger('change');
	$('#sdoCodedt').val('').trigger('change');
	$('#dttown').val('').trigger('change');
	$('#fttown').val('').trigger('change');
	
	$("#reportDate").val('');	
	$("#reportFromDate").val('');
	$("#reportToDate").val('');
	$("#fwmonth").val('');
	$("#twmonth").val('');
	$('#WeklyFWId').val('').trigger('change');
	$('#WeklyTFWId').val('').trigger('change');
	$("#fmonth").val('');
	$("#tmonth").val('');
	$('#circledivsub').hide();
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

function getWeeksInMonth(){
	var fwmonth=$('#fwmonth').val();
	var year=fwmonth.substring(0,4);
	var month=fwmonth.substring(4).replace(/^0+/, '')-1;
    var weeks=[],
	       firstDate=new Date(year, month, 1),
	       lastDate=new Date(year, month+1, 0), 
	       numDays= lastDate.getDate();	
	 //  alert(" firstDate "+firstDate+" lastDate "+lastDate);    
   var start=1;
   var end=7-firstDate.getDay();
   while(start<=numDays){
       weeks.push({start:start,end:end});
       start = end + 1;
       end = end + 7;
       if(end>numDays)
           end=numDays;    
    }  

    $('#WeklyFWId').find('option').remove();
	$('#WeklyFWId').append($('<option>', {
		value : "",
		text : "Select Week"
	}));
	var count=1;
   for (var i = 0; i < weeks.length; i++) {
	  // alert("Week"+i);
	   var resp = weeks[i];
	   $('#WeklyFWId').append($('<option>', {
			value : resp.start+"-"+resp.end,
			text : "Week-"+count
		}));
	   count++;
	}
}

function getToWeeksInMonth(){

	var twmonth=$('#twmonth').val();
	var year=twmonth.substring(0,4);
	var month=twmonth.substring(4).replace(/^0+/, '')-1;
    var weeks=[],
	       firstDate=new Date(year, month, 1),
	       lastDate=new Date(year, month+1, 0), 
	       numDays= lastDate.getDate();	
	 //  alert(" firstDate "+firstDate+" lastDate "+lastDate);    
   var start=1;
   var end=7-firstDate.getDay();
   while(start<=numDays){
       weeks.push({start:start,end:end});
       start = end + 1;
       end = end + 7;
       if(end>numDays)
           end=numDays;    
    }  

    $('#WeklyTFWId').find('option').remove();
	$('#WeklyTFWId').append($('<option>', {
		value : "",
		text : "Select Week"
	}));
   var count=1;
   for (var i = 0; i < weeks.length; i++) {
	   var resp = weeks[i];
	   $('#WeklyTFWId').append($('<option>', {
			value : resp.start+"-"+resp.end,
			text : "Week-"+count
		}));
	   count++;
	}
	
}

</script>