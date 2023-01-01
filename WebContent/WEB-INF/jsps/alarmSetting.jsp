<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<meta http-equiv="content-type"
	content="application/vnd.ms-excel; charset=UTF-8">
<style>
.table-toolbar {
	margin-bottom: 4px;
}

.col-md-2 {
	width: 24.500%;
}

.col-md-1 {
	width: 24.333%;
}
/* .paginate_button {
width: 10px;
} */
.paginate_button {
	padding: 15px 25px;
	font-size: 15px;
	text-align: center;
	cursor: pointer;
	outline: none;
	color: #fff;
	background-color: #717887;
	border: none;
	border-radius: 15px;
	box-shadow: 0 9px #999;
}

.paginate_button:hover {
	background-color: #597bc7
}

.paginate_button:active {
	background-color: #3e8e41;
	box-shadow: 0 5px #666;
	transform: translateY(4px);
}
</style>
<style>
a {
	text-decoration: none;
	display: inline-block;
	padding: 8px 16px;
}

a:hover {
	background-color: #ddd;
	color: black;
}

.previous {
	background-color: #f1f1f1;
	color: black;
}

.next {
	background-color: #4CAF50;
	color: white;
}

.round {
	border-radius: 50%;
}
</style>
<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						App.init();
						FormComponents.init();
						getConsumercate();
						getConsumercate1();
						$("#previous").hide();
						$('#MDMSideBarContents,#alarmID,#alarmSetting')
								.addClass('start active ,selected');
						 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
							"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
						$("#check").click(
								function() {
									$(".checkboxes").prop('checked',
											$(this).prop('checked'));
								});
						$("#check1").click(
								function() {
									$(".checkboxes1").prop('checked',
											$(this).prop('checked'));
								});
						$("#check2").click(
								function() {
									$(".checkboxes2").prop('checked',
											$(this).prop('checked'));
								});
						$('#zoneForFdr').val('').trigger('change');
						$('#circleForFdr').val('').trigger('change');
						$('#townForFdr').val('').trigger('change');
					});
</script>
<script>

function showCircle(zone) {
	//alert(zone);
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
		
		function showCircleforntse(zoneForntse) {
			$	.ajax({
					url : './getCircleByZoneForFdrntse',
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					data : {
						zoneForntse : zoneForntse
					},
					success : function(response) {
					//	alert(response);
						var html = '';
						html += "<select id='circleForntse' name='circleForntse' onchange='showTownNameandCodeforntse(this.value)' class='form-control select2me' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#circleForntse").html(html);
						$('#circleForntse').select2();
					}
				});
	} 


	function showTownNameandCodeforntse(circleForntse){
		
		 var zoneForntse = $('#zoneForntse').val();
		// alert(zoneForFdr);
		//var zone =  '${newRegionName}';   
		   $.ajax({
		      	url:'./getTownNameandCodebyCircleForntse',
		      	type:'GET',
		      	dataType:'json',
		      	asynch:false,
		      	cache:false,
		      	data : {
		      		zoneForntse : zoneForntse,
		      		circleForntse : circleForntse
		  		},
		  		success : function(response1) {
			  	//	alert(response1);
		  			
	                var html = '';
	                html += "<select id='townForntse' name='townForntse' onchange='showSubStaTionByTown(this.value)' class='form-control  input-medium'  type='text'><option value=''>Select Town </option><option value='%'>ALL</option>";
	               
	                for (var i = 0; i < response1.length; i++) {
	                    //var response=response1[i];
	                    
	                    html += "<option value='"+response1[i][0]+"'>"
	                            +response1[i][0] +"-"  +response1[i][1] + "</option>";
	                               }
	                html += "</select><span></span>";
	                $("#townForntse").html(html);
	                $('#townForntse').select2();
	               
	                
	            }
		  	});
		  }
var zone="%";
	/* function getConsumercate() {
		 $('#consumerCatgry').find('option').remove();
		$('#consumerCatgry').append($('<option>', {
			value : "",
			text : "Select Consumer Category"
		}));
		$('#unconsumerCatgry').find('option').remove();
		$('#unconsumerCatgry').append($('<option>', {
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
					$('#unconsumerCatgry').append($('<option>', {
					    value : response[i].trim(),
						text : response[i].trim() 
					}));
				}
			} 
			
		});
	} */
	
	function getConsumercate()
	{
		$.ajax({
			url : "./getConsumercatedata",
			type:'POST',
			success:function(response){
				var html = "";
				html += "<select id='consumerCatgry' name='consumerCatgry' class='form-control input-medium' type='text'><option value=''>Select Consumer Category</option><option value='%'>ALL</option>";
				for (var i = 0; i < response.length; i++) {
					html += "<option  value='"+response[i]+"'>"
							+ response[i] + "</option>";
				}
				html += "</select><span></span>";
				$("#consumerCatgry").html(html);
				$('#consumerCatgry').select2();
			}
		});
	}
	
	/* function getConsumercate1() {
		$('#consumerCatgry1').find('option').remove();
		$('#consumerCatgry1').append($('<option>', {
			value : "",
			text : "Select Consumer Category"
		}));
		$('#unconsumerCatgry1').find('option').remove();
		$('#unconsumerCatgry1').append($('<option>', {
			value : "",
			text : "Select Consumer Category"
		}));

		$.ajax({
			url : "./getConsumercatedata",
			type : 'POST',
			success : function(response) {
				for (var i = 0; i <= response.length; i++) {
					$('#consumerCatgry1').append($('<option>', {
						value : response[i].trim(),
						text : response[i].trim()
					}));

					$('#unconsumerCatgry1').append($('<option>', {
						value : response[i].trim(),
						text : response[i].trim()
					}));
				}
			}
		});
	} */
	
	function getConsumercate1()
	{
		$.ajax({
			url : "./getConsumercatedata",
			type:'POST',
			success:function(response){
				var html = "";
				html += "<select id='consumerCatgry1' name='consumerCatgry1' class='form-control input-medium' type='text'><option value=''>Select Consumer Category</option><option value='%'>ALL</option>";
				for (var i = 0; i < response.length; i++) {
					html += "<option  value='"+response[i]+"'>"
							+ response[i] + "</option>";
				}
				html += "</select><span></span>";
				$("#consumerCatgry1").html(html);
				$('#consumerCatgry1').select2();
			}
		});
	}
	
	function showDivision(circle) {
		$("#circledivsub").show();
		
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
						var html = "";
						html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Division</option><option value='%'>ALL</option>";
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
						html += "<select id='sdoCode' name='sdoCode'  class='form-control input-medium' type='text'><option value=''>Sub-Division</option><option value='%'>ALL</option>";
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

	function showDivisionDT(circle) {
		var circle = $("#circledt").val();
		
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
						var html = "";
						html += "<select id='divisiondt' name='divisiondt' onchange='showSubdivByDivDT(this.value)' class='form-control input-medium' type='text'><option value=''>Division</option><option value='%'>ALL</option>";
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
						html += "<select id='sdoCodedt' name='sdoCodedt' onchange='showTowndivByDivDT(this.value)' class='form-control input-medium' type='text'><option value=''>Sub-Division</option><option value='%'>ALL</option>";
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
	function showTowndivByDivDT(subdivision) {
		var zone='%'
		var circle = $('#circledt').val();
		var division = $('#divisiondt').val();
	      $.ajax({
	          url : './getTownsBaseOnSubdivision',
	          type : 'GET',
	          dataType : 'json',
	          asynch : false,
	          cache : false,
	          data : {
	              zone : zone,
	              circle : circle,
	              division : division,
	              subdivision :subdivision
	          },
	                  success : function(response1) {
	                      var html = '';
	                      html += "<select id='townCodedt' name='townCodedt'  class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
	                      for (var i = 0; i < response1.length; i++) {
	                          //var response=response1[i];
	                          html += "<option value='"+response1[i][0]+"'>"
	                                  +response1[i][0]+"-"+response1[i][1] + "</option>";
	                      }
	                      html += "</select><span></span>";
	                      $("#towndivTddt").html(html);
	                      $('#townCodedt').select2();
	                  }
	              });
	}
	/* function showDivisionFT(circle) {
		
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
						var html = "";
						html += "<select id='divisionft' name='divisionft' onchange='showSubdivByDivFT(this.value)' class='form-control input-medium' type='text'><option value=''>Division</option><option value='%'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option  value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#divisionTdFt").html(html);
						$('#divisionft').select2();
					}
				});
	} */
	
	
	function showSubdivByDivFT(division) {
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
						html += "<select id='sdoCodeft' name='sdoCodeft' onchange='showTowndivByDivFT(this.value)' class='form-control input-medium' type='text'><option value=''>Sub-Division</option><option value='%'>ALL</option>";
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
	function showTowndivByDivFT(subdivision) {
		var zone='%'
		var circle = $('#circleft').val();
		var division = $('#divisionft').val();
	      $.ajax({
	          url : './getTownsBaseOnSubdivision',
	          type : 'GET',
	          dataType : 'json',
	          asynch : false,
	          cache : false,
	          data : {
	              zone : zone,
	              circle : circle,
	              division : division,
	              subdivision :subdivision
	          },
	                  success : function(response1) {
	                      var html = '';
	                      html += "<select id='townCodeft' name='townCodeft'  class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
	                      for (var i = 0; i < response1.length; i++) {
	                          //var response=response1[i];
	                          html += "<option value='"+response1[i][0]+"'>"
	                                  +response1[i][0]+"-"+response1[i][1] + "</option>";
	                      }
	                      html += "</select><span></span>";
	                      $("#towndivTdFt").html(html);
	                      $('#townCodeft').select2();
	                  }
	              });
	}
	function showDivision1(circle) {
		$("#circledivsub").show();
		var circle = $("#circle1").val();
		
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
						var html = "";
						html += "<select id='division1' name='division1' onchange='showSubdivByDiv1(this.value)' class='form-control input-medium' type='text'><option value=''>Division</option><option value='%'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option  value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#divisionTd1").html(html);
						$('#division1').select2();
					}
				});
	}
</script>
<script>
	function showSubdivByDiv1(division) {
		var circle = $('#circle1').val();
		var division = $('#division1').val();
		
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
					success : function(response2) {
						var html = '';
						html += "<select id='sdoCode1' name='sdoCode1'  class='form-control input-medium' type='text'><option value=''>Sub-Division</option><option value='%'>ALL</option>";
						for (var i = 0; i < response2.length; i++) {
							html += "<option  value='"+response2[i]+"'>"
									+ response2[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#subdivTd1").html(html);
						$('#sdoCode1').select2();
					}
				});
	}

	/* function showCircle(zone) {
		var zone = $('#Zone').val();
		
		
				$.ajax({
					 url:'./getCircleByZone',
	                 type:'GET',
	                 dataType:'json',
	                 asynch:false,
	                 cache:false,
	                 data : {
	                     zone : zone
	                 },
					success : function(response) {
						
						var htmll = '';
						htmll += "<select id='circle2' name='circle2' onchange='showDivision2(this.value)' class='form-control' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							htmll += "<option  value='"+response[i]+"'>"
									+ response[i] + "</option>";

						}
						htmll += "</select><span></span>";
						$("#circleTd2").html(htmll);
						$('#circle2').select2();

					}
				});
	}


	
	function showTownNameandCode(circle){
		var zone =  $('#zone').val(); 
		
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
	                html += "<select id='town' name='town'  class='form-control  input-medium'  type='text'><option value=''>Select</option>";
	                for (var i = 0; i < response1.length; i++) {
	                    //var response=response1[i];
	                    
	                    html += "<option value='"+response1[i][0]+"'>"
	                            +response1[i][0] +"-"  +response1[i][1] + "</option>";
	                }
	                html += "</select><span></span>";
	                $("#townTd").html(html);
	                $('#town').select2();
	            }
		  	});
		  } */


	  function showTownBySubdiv(subdiv) {
		     // var zone = $('#zone').val();
		      var circle = $('#circle').val();
		      var division = $('#division').val();
		      $.ajax({
		          url : './getTownNameandCodeBySubDiv',
		          type : 'GET',
		          dataType : 'json',
		          asynch : false,
		          cache : false,
		          data : {
		        	  sitecode :subdiv
		          },
		                  success : function(response1) {
		                      var html = '';
		                      html += "<select id='town' name='town'  class='form-control  input-medium'  type='text'><option value=''>Select</option>";
		                      for (var i = 0; i < response1.length; i++) {
		                          //var response=response1[i];
		                          
		                          html += "<option value='"+response1[i][0]+"'>"
		                                  + response1[i][1] + "</option>";
		                      }
		                      html += "</select><span></span>";
		                      $("#townTd").html(html);
		                      $('#town').select2();
		                  }
		              });
		  }

	function showDivision2(circle) {
		
		var circle = $("#circle2").val();
		var zone = $('#Zone').val();
		
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
						var html = "";
						html += "<select id='division2' name='division2' onchange='showSubdivByDiv2(this.value)' class='form-control input-medium' type='text'><option value=''>Select Division</option><option value='%'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option  value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#divisionTd2").html(html);
						$('#division2').select2();
					}
				});
	}

	function showSubdivByDiv2(division) {
		var circle = $('#circle2').val();
		
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
					success : function(response2) {
						var html = '';
						html += "<select id='sdoCode2' name='sdoCode2'  class='form-control input-medium' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
						for (var i = 0; i < response2.length; i++) {
							html += "<option  value='"+response2[i]+"'>"
									+ response2[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#subdivTd2").html(html);
						$('#sdoCode2').select2();
					}
				});
	}
</script>
<script>
	
	function eventdetails() {
        $("#getselectedevent").val("0");
		$('#addData').hide();
		$('#sample_val_wrapper').hide();
		$("#sample_val").hide();
		$("#sample_new").show();
		$("#previous").show();
		$('#sample_new_wrapper').show();

				$.ajax({
					url : "./geteventdescription",
					type : "POST",
					success : function(response) {
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							html += "<tr>"
									+ "<td  id='eventcode_"
									+ (i + 1)
									+ "'>"
									+ resp[0]
									+ "</td>"
									+ "<td>"
									+ resp[1]
									+ "</td>"
									+ "<td><select class='form-control select2me input-medium' id='priority_data_"
									+ (i + 1)
									+ "' name='priority_data_"
									+ (i + 1)
									+ "' > <option value='noVal'>SELECT</option> <option value='High'>HIGH</option> <option value='medium'>MEDIUM</option> <option value='low'>LOW</option>	</select></td>"
									+ "<td><button type='button' class='btn btn-success' onclick='chnageButton(this.id,this.value,"+(i + 1)+",1)' id='event_active_"
									+ (i + 1)
									+ "' value='inac'>Active</button></td>"
									
									+ "</tr>";
						}
						/* <button type='button' class='btn btn-danger'>Danger</button> */
						$('#sample_new').dataTable().fnClearTable();
						$('#eventTR').html(html);
						loadSearchAndFilter('sample_new');
					},
					complete : function() {
						loadSearchAndFilter('sample_new');
					}
				});

	}

	/* function showTownNameandCode(circle){
		var zone =  $('#zone').val(); 
		
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
	                html += "<select id='town' name='town'  class='form-control  input-medium'  type='text'><option value=''>Select</option>";
	                for (var i = 0; i < response1.length; i++) {
	                    //var response=response1[i];
	                    
	                    html += "<option value='"+response1[i][0]+"'>"
	                            +response1[i][0] +"-"  +response1[i][1] + "</option>";
	                }
	                html += "</select><span></span>";
	                $("#townTd").html(html);
	                $('#town').select2();
	            }
		  	});
		  } */


	  function showTownBySubdiv(subdiv) {
		     // var zone = $('#zone').val();
		      var circle = $('#circle').val();
		      var division = $('#division').val();
		      $.ajax({
		          url : './getTownNameandCodeBySubDiv',
		          type : 'GET',
		          dataType : 'json',
		          asynch : false,
		          cache : false,
		          data : {
		        	  sitecode :subdiv
		          },
		                  success : function(response1) {
		                      var html = '';
		                      html += "<select id='town' name='town'  class='form-control  input-medium'  type='text'><option value=''>Select</option>";
		                      for (var i = 0; i < response1.length; i++) {
		                          //var response=response1[i];
		                          
		                          html += "<option value='"+response1[i][0]+"'>"
		                                  + response1[i][1] + "</option>";
		                      }
		                      html += "</select><span></span>";
		                      $("#townTd").html(html);
		                      $('#town').select2();
		                  }
		              });
		  }
		

	function validatureFailure() {
		$("#getselectedevent").val("0");
		$('#addData').hide();
		$('#sample_new_wrapper').hide();
		$("#sample_new").hide();
		$("#sample_val").show();
		$("#previous").show();
		
				$.ajax({
					url : "./getvalidationfailure",
					type : "POST",
					success : function(response) {
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							html += "<tr>"
									+ "<td  id='valcode_"
									+ (i + 1)
									+ "'>"
									+ resp[0]
									+ "</td>"
									+ "<td>"
									+ resp[1]
									+ "</td>"
									+ "<td><select class='form-control select2me input-medium' id='priority_data1_"
									+ (i + 1)
									+ "' name='priority_data1_"
									+ (i + 1)
									+ "'> <option value='noVal'>SELECT</option> <option value='High'>HIGH</option> <option value='medium'>MEDIUM</option> <option value='low'>LOW</option></select></td>"
									+ "<td><button type='button' class='btn btn-success' onclick='chnageButton(this.id,this.value,"+(i+1)+",2)' id='valfail_active_"
									+ (i + 1)
									+ "' value='inac'>Active</button></td>"
									
							"</tr>";
						}
						$('#sample_val').dataTable().fnClearTable();
						$('#valfail').html(html);
						loadSearchAndFilter('sample_val');
					},
					complete : function() {
						loadSearchAndFilter('sample_val');
					}

				});
	}

	function notifysetting() {
		$('#addData').show();

	}

	function getConsumerData() {
		$('#addData').hide();
		var circle = $('#circle').val();
		var division = $('#division').val();
		var sdoCode = $('#sdoCode').val();
		var consumerCatgry = $('#consumerCatgry').val();
		var acno = $('#acno').val();
		var kno = $('#kno').val();
		var mtrno = $('#mtrno').val();
		$('#sample_new_wrapper').hide();
		$('#sample_val_wrapper').hide();
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
		/* if(consumerCatgry == "") {
			bootbox.alert("please Select Consumer Category");
			return false;
		} */
		$('#mtrngimageee').show();
				$.ajax({
					url : './getConsumerDetailsData',
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
						$('#mtrngimageee').hide();
						$("#updateMaster").html('');
						if (response != null && response.length > 0) {
							var html = "";
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
								html += "<tr>"
										+ "<td><input id='check1' name='checkboxm' type='checkbox' value='"+resp[4]+"' class='checkboxes1'/></td>"
										+ "<td id> "
										+ resp[1]
										+ " </td>"
										+ "<td>"
										+ resp[2]
										+ " </td>"
										+ "<td>"
										+ resp[3]
										+ " </td>"
										+ "<td>"
										+ resp[4]
										+ " </td>"
										+ "<td>"
										+ resp[5]
										+ " </td>"
										+ "<td>"
										+ resp[6]
										+ " </td>"
										+ "<td>"
										+ resp[7]
										+ " </td>"
										+ "<td>"
										+ resp[8]
										+ " </td>" + "</tr>";
							}
							$('#sample_1').dataTable().fnClearTable();
							$("#updateMaster").html(html);
							loadSearchAndFilter('sample_1');
						}
					},
					complete : function() {
						loadSearchAndFilter('sample_1');
					}
				});

	}
	function getUserroleData() {
		
	   clearTabledataContent('sample_3');
		$('#sample_2').hide();
		$('#sample_3').show();
		/* var discom=$('#discom').val(); */
		var zone = $('#zoneForntse').val();
		var circle = $('#circleForntse').val();
		var division = $('#division2').val();
		var sdoCode = $('#sdoCode2').val();
		var town = $('#townForntse').val();
		var userrole = $('#userrole').val();
		var locType = $("input[name='notificationRadios']:checked").val();
		
		/* if (discom == "") {
			bootbox.alert("Please Select Discom");
			return false;
		} */
		if (zone == "") {
			bootbox.alert("Please Select Region");
			return false;
		}
		if (circle == "") {
			bootbox.alert("Please Select Circle");
			return false;
		}if(town == ""){
			bootbox.alert("Please Select Town");
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
		if (userrole == "") {
			bootbox.alert("Please Select Userrole");
			return false;
		}

		
		
		//var disco="discom";
		var zn="";
		var cr="";
		var tw ="";
		var dvs="";
		var subdvn="";
		var userrol="";
		/* if(discom=="%"){
			disco="ALL"
		}else{
			disco=discom;
		}  */
		if(zone=="%"){
			zn="ALL";
		}else{
			zn=zone;
		}
		if(circle=="%"){
			cr="ALL";
		}else{
			cr=circle;
		}if(town == "%"){
			tw="ALL"
		}else{
			tw=town;
		}
		if(division=="%"){
			dvs="ALL";
		}else{
		    dvs=division;
		}
		if(sdoCode=="%"){
			subdvn="ALL";
		}else{
			subdvn=sdoCode;
		}
		if(userrole=="%"){
			usrole="ALL";
		}else{
			usrole=userrole;
		}
		$('#notifyimageee').show();
				$.ajax({
					url : './getUserRoleDetailsDatas',
					type : 'POST',
					data : {
						zn : zn,
						cr : cr,
						tw : tw,
						usrole : usrole
					},
					dataType : 'json',
					success : function(response) {
						$('#notifyimageee').hide();
						$("#discomuser").html('');
						if (response != null && response.length > 0) {
							var html = "";
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
								html += "<tr>"
										+ "<td><input id='check' name='checkdiscom' value='"+locType+"' type='checkbox' class='checkboxes' /></td>"
										+ "<td>"
										+ (resp[1] == null ? "" : resp[1])
										+ "</td>"
										+ "<td></td>"
										+ "<td>"
										+ (resp[2] == null ? "" : resp[2])
										+ "</td>"
										+ "<td>"
										+ (resp[3] == null ? "" : resp[3])
										+ "</td>"
										+ "<td>"
										+ (resp[4] == null ? "" : resp[4])
										+ "</td>"
										+ "<td>"
										+ (resp[5] == null ? "" : resp[5])
										+ "</td>"
										+ "<td><input id='check' name='mobilenum' type='checkbox' value='"+resp[4]+"' class='checkboxes'></td>"
										+ "<td><input id='check' name='emailid' type='checkbox' value='"+resp[5]+"' class='checkboxes'></td>"
										+ "</tr>";
							}
							$('#sample_3').dataTable().fnClearTable();
							$("#discomuser").html(html);
							loadSearchAndFilter('sample_3');
							$('#addData').show();
						}
					},
					complete : function() {
						loadSearchAndFilter('sample_3');
					}
				});

	}


	function getConsumerDataSetting() {
		
		var circle = $('#circle1').val();
		var division = $('#division1').val();
		var sdoCode = $('#sdoCode1').val();
		var consumerCatgry = $('#consumerCatgry1').val();
		var acno = $('#acno1').val();
		var kno = $('#kno1').val();
		var mtrno = $('#mtrno1').val();

		$('#sample_new_wrapper').hide();
		$('#sample_val_wrapper').hide();
		$('#sample_3').hide();
		$('#sample_2').show();
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
		/* if (consumerCatgry == "") {
			bootbox.alert("Please Select Consumer Category");
			return false;
		} */
		 
		$('#notifyimageee').show();
				$.ajax({
					url : './getNotifySettingDetailsData',
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
						$('#notifyimageee').hide();
						$("#notifyMaster").html('');
						if (response != null && response.length > 0) {
							var html = "";
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
								html += "<tr>"
										+ "<td><input id='check2' name='checkboxns' type='checkbox' value='"+resp[4]+"' class='checkboxes2' /></td>"
										+ "<td>"
										+ resp[1]
										+ " </td>"
										+ "<td>"
										+ resp[2]
										+ " </td>"
										+ "<td>"
										+ resp[3]
										+ " </td>"
										+ "<td>"
										+ resp[4]
										+ " </td>"
										+ "<td>"
										+ resp[5]
										+ " </td>"
										+ "<td>"
										+ resp[6]
										+ " </td>"
										+ "<td>"
										+ resp[7]
										+ " </td>"
										+ "<td>"
										+ resp[8]
										+ " </td>"
										+ "<td><input id='checkconsms' name='checkconsms' type='checkbox'  value='"+resp[9]+"' class='checkboxes2'></td>"
										+ "<td><input id='checkconemail' name='checkconemail' type='checkbox'  value='"+resp[10]+"' class='checkboxes2'></td>"
										+ "</tr>";
							}
							$('#sample_2').dataTable().fnClearTable();
							$("#notifyMaster").html(html);
							loadSearchAndFilter('sample_2');
							$('#addData').show();
						}
					},
					complete : function() {
						loadSearchAndFilter('sample_2');
					}
				});

	}
	function getDTDataDetails()
	{

		var zone=$("#LFzone").val();
		var circle = $('#LFcircle').val();
		var division = $('#divisiondt').val()
		var sdoCode = $('#sdoCodedt').val();
		var town = $('#LFtown').val();
		var crossdt = $("input[name='unasg_dt_radio']").is(":checked");
		var dtcode = $('#udtcode').val();
		var dtmtrno = $('#umtrcode').val();
  		//var zone1 = $("#LFzone option:selected").map(function(){return this.text}).get().join(',');
  		
		if(zone=="")
		{
		bootbox.alert("Please Select Region");
		return false;
		}
		if(circle=="")
		{
		bootbox.alert("Please Select Circle");
		return false;
		}
		/* if(division=="")
		{
		bootbox.alert("Please Select division");
		return false;
		}
		if(sdoCode=="")
		{
		bootbox.alert("Please Select subdivision");
		return false;
		} */
		if(town=="")
		{
		bootbox.alert("Please Select Town");
		return false;
		}
		$('#mtrngimageee').show();
		$.ajax({
			url : './getDtData',
			type : 'GET',
			data : {
				zone : zone,
				circle : circle,
				division : division,
				sdoCode : sdoCode,
				town : town,
				crossdt : crossdt,
				dtcode : dtcode,
				dtmtrno : dtmtrno,
			},
			dataType:'json',
			success : function(response){
				$('#mtrngimageee').hide();
				$("#view_DT").show();
				$("#updateMasterDT").html('');
				if (response != null && response.length > 0) {
					var html = "";
					for (var i = 0; i < response.length; i++) {
						var resp = response[i];
						html+="<tr>"+
						   "<td><input id='dtcheck' name='dtcheck' type='checkbox' class='checkboxes' value="+resp[5]+"></td>"+
						"<td>"+resp[0]+" </td>"+
						  "<td>"+resp[1]+" </td>"+
						  "<td>"+resp[2]+" </td>"+
						  "<td>"+resp[3]+" </td>"+
						  "<td hidden = 'true'>"+resp[4]+" </td>"+
						  "<td>"+resp[8]+" </td>"+
						  "<td>"+resp[5]+" </td>"+
						  "<td>"+(resp[6]==null? "":resp[6])+" </td>"+
						  "<td>"+resp[7]+" </td>"+
						  "</tr>";
					}
					$('#sample_DT').dataTable().fnClearTable();
					  $("#updateMasterDT").html(html);
					  loadSearchAndFilter('sample_DT');
					  $("#sample_DT").show();
				}
				else{
					 $('#sample_DT').dataTable().fnClearTable();
					 $("#updateMasterDT").html(html);
					 $("#view_DT").hide();
					bootbox.alert("No Dt Meters Found");
				}
				
			}
			
		});
		//$("#sample_DT").show();
	}
	var crossfdr =true;
	function getFeederDataDetails()
	{
		$("#view_2").hide();
		$("#view_1").hide();
		var fdrcode = "";
		
		var zone=$("#zoneForFdr").val();
		var circle = $('#circleForFdr').val();
	 	var division = $('#divisionft').val();
		var sdoCode = $('#sdoCodeft').val(); 
		var town = $('#townForFdr').val();
		//var crossfdr = $("input[name='asg_ft_radio']").is(":checked");
		//var fdrcode = $('#ufdcode').val();
		if(crossfdr){
			fdrcode = $('#boundarycode').val();
			}else{
				fdrcode = $('#ufdcode').val();
				} 
		var fdrmtrno = $('#fdmtrcode').val();
		
		if(zone=="")
		{
		bootbox.alert("Please Select Region");
		return false;
		}
		
		if(circle=="")
		{
		bootbox.alert("Please Select Circle");
		return false;
		}
		 if(division=="")
		{
		bootbox.alert("Please Select division");
		return false;
		}
		if(sdoCode=="")
		{
		bootbox.alert("Please Select subdivision");
		return false;
		} 
		if(town=="")
		{
		bootbox.alert("Please Select town");
		return false;
		}
		$('#mtrngimageee').show();
		$.ajax({
			url : './getFdrData',
			type : 'GET',
			data : {
				zone : zone,
				circle : circle,
				division : division,
				sdoCode : sdoCode,
				town : town,
				crossfdr : crossfdr,
				fdrcode : fdrcode,
				fdrmtrno : fdrmtrno,
			},
			dataType:'json',
			success : function(response){
				$('#mtrngimageee').hide();
				//$("#updateFeederMaster").html('');
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
						html+="<tr>"+
						   "<td><input id='fdrcheck' name='fdrcheck' type='checkbox' class='checkboxes' value="+resp[5]+"></td>"+
						   "<td>"+resp[0]+" </td>"+
						   "<td>"+resp[1]+" </td>"+
						   "<td>"+resp[2]+" </td>"+
						   "<td>"+resp[3]+" </td>"+
						   "<td>"+resp[7]+" </td>"+
						  /* "<td>"+((resp[4] == "0" ) ? "No" : (resp[4] == "1") ? "Yes" : resp[4])+" </td>"+ */
						   "<td>"+resp[5]+" </td>"+
						   "<td>"+resp[6]+" </td>"+
						   "</tr>";
					}
					//alert(html)
					$('#sample_boundary').dataTable().fnClearTable();
					$("#updateBoundaryMaster").html(html);
					loadSearchAndFilter('sample_boundary');
					$("#sample_boundary").show();
				} else {
					$('#sample_boundary').dataTable().fnClearTable();
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
					html+="<tr>"+
					   "<td><input id='fdrcheck' name='fdrcheck' type='checkbox' class='checkboxes' value="+resp[5]+"></td>"+
					   "<td>"+resp[0]+" </td>"+
					   "<td>"+resp[1]+" </td>"+
					   "<td>"+resp[2]+" </td>"+
					   "<td>"+resp[3]+" </td>"+
					   "<td>"+resp[7]+" </td>"+
					  /* "<td>"+((resp[4] == "0" ) ? "No" : (resp[4] == "1") ? "Yes" : resp[4])+" </td>"+ */
					   "<td>"+resp[5]+" </td>"+
					   "<td>"+resp[6]+" </td>"+
					   "</tr>";
				}
				//alert(html);
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

	function getConsumernotify() {
		$('#addData').show();
		$('#notifyset').show();
		$('#userlistdiscom').hide();
		$('#sample_3').hide();
		$('#sample_2').show();
		$('#sample_3_wrapper').hide();
	}

	function getdiscomUser() {
		$('#addData').show();
		$('#notifyset').hide();
		$('#userlistdiscom').show();
		$('#sample_3').show();
		$('#sample_2').hide();
		$('#sample_2_wrapper').hide();
		$
				.ajax({
					url : "./getDiscomUsersList",
					type : 'POST',
					dataType : 'json',
					success : function(response) {
						var html = '';
						html += "<select id='discom' name='discom'  class='form-control input-medium' type='text'><option value=''>DISCOM</option><option value='ALL'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option  value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#listdiscom").html(html);
						$('#discom').select2();
					}
				});
	}
	</script>
<script>
	function addingDatas() {
		var fname = $('#fname').val();
		var rowCount = $('#eventTR tr').length;
		var valrowCount = $('#valfail tr').length;

		var all_types = [];
		var notificationDetails = [];

		var radioValue = $("input[name='optionsRadios']:checked").val();
		
		var radioValuens = $("input[name='optionsRadios']:checked").val();
		var locationType = $("input[name='locationRadios']:checked").val();
		var notifyTypes = $("input[name='notificationRadios']:checked").val();
		var notifycheckboxes = "";
		var sms="";
		var email="";
		
		var checkboxes="";
		var cheeckedboxes = "";
		if (!"Consumer".localeCompare(locationType)) {
			checkboxes = document.getElementsByName('checkboxm');
			
		}
		else if(!"DT".localeCompare(locationType)){
			checkboxes = document.getElementsByName('dtcheck');
		}
		else{
			checkboxes = document.getElementsByName('fdrcheck');
		}
		for (var i = 0; i < checkboxes.length; i++) {
			
			if (checkboxes[i].checked) {
				cheeckedboxes = cheeckedboxes + checkboxes[i].value + ",";
			}
			
		}
		
		
		var notifycheeckedboxes = "";
		/* if (!"Consumer".localeCompare(notifyTypes)) {

			notifycheckboxes = document.getElementsByName('checkboxns');
			 sms = document.getElementsByName('checkconsms');
			 email = document.getElementsByName('checkconemail');
		} else { */
			notifycheckboxes = document.getElementsByName('checkdiscom');
			 sms = document.getElementsByName('mobilenum');
			 email = document.getElementsByName('emailid');
		//}
		/* for (var i = 0; i < notifycheckboxes.length; i++) {

			if (notifycheckboxes[i].checked) {
				//selected.push(checkboxes[i].value);
				notifycheeckedboxes = notifycheeckedboxes
						+ notifycheckboxes[i].value + ",";

				alert("dcdc" + notifycheeckedboxes);
			}
		} */
		
		
		var toNotify = [];
		var accNum="";
		var consumerMailId="";
		var consumerMobileNum="";
		for (var i = 0; i < notifycheckboxes.length; i++) {
			if(notifycheckboxes[i].checked){
				accNum=notifycheckboxes[i].value;
				if(sms[i].checked){
					consumerMobileNum=sms[i].value;
				}
				else{
					consumerMobileNum="";
				}
				if(email[i].checked){
					consumerMailId=email[i].value;
				}
				else{
					consumerMailId="";
				}

				
				toNotify.push({
					"acc_num" : accNum,
					"email_id" : consumerMailId,
					"contact_num" : consumerMobileNum

				});
			}

			/* if (notifycheckboxes[i].checked) {
				if(sms[i].checked){
					contactNos=contactNos + sms[i].value + ",";
				}
				else{
					contactNos=contactNos + sms[i].value + ",";
				}
				if(email[i].checked){
					emailIds=emailIds+email[i].value + ",";
					
				} 
				cheeckedboxes = cheeckedboxes + checkboxes[i].value + ",";

				//alert("dcdc" + cheeckedboxes);
			}*/
		} 
		notificationDetails=toNotify;

		if (radioValue == 'Event') {
			var all_event = [];
			for (var i = 1; i <= rowCount; i++) {
				var status = $("#event_active_" + i).val();
				var prio = $("#priority_data_" + i).val();
				if (status == 'act') {
					var event_id = $("#eventcode_" + i).text();

					all_event.push({
						"event_id" : event_id,
						"status" : status,
						"priority" : prio

					});
				}
			}
			all_types = all_event;
		} else if (radioValue == 'Communication') {

		} else if (radioValue == 'Validation') {
			var all_validations = [];
			//alert("row val Count: " + valrowCount);
			for (var i = 1; i <= valrowCount; i++) {
				var status1 = $("#valfail_active_" + i).val()
				var prio1 = $("#priority_data1_" + i).val();
				if (status1 == 'act') {
					var val_id = $("#valcode_" + i).text();

					all_validations.push({
						"val_id" : val_id,
						"status1" : status1,
						"priority1" : prio1

					});
				}
			}
			all_types = all_validations;
		}

		/* var flag3=$('input[name=notificationRadios]:checked').val();
		if(flag3=='undefined' || flag3==null || flag3=="")
		{
			bootbox.alert("Please Select any one option in Notification Setting");
			return false; 
		}
		else if (flag3 == 'Consumer')
		{
			var connsumacc =$('input[name=checkboxns]:checked').val(); 
			var consumsms =$('input[name=checkconsms]:checked').val();
			var consummail =$('input[name=checkconemail]:checked').val();
			if(connsumacc == 'undefined' || connsumacc == null || connsumacc == "") 		
			{
				bootbox.alert("Please Select atleast one ConsumerNotification");
				return false;
			}
			if ( (consumsms == 'undefined' || consumsms == null || consumsms == "") && (consummail == 'undefined' || consummail == null || consummail == ""))
			{
				bootbox.alert("Please Select SMS/Email");
				return false;
			}
			
		}
		else if (flag3 == 'DISCOMuser')
		{ */
			var discomacc =$('input[name=checkdiscom]:checked').val(); 
			var discomsms =$('input[name=mobilenum]:checked').val();
			var discommail =$('input[name=emailid]:checked').val();
			/* if(discomacc == 'undefined' || discomacc == null || discomacc == "")
			{
				bootbox.alert("Please Select atleast one DiscomNotification");
				return false;
			} */
			if ( (discomsms == 'undefined' || discomsms == null || discomsms == "") && (discommail == 'undefined' || discommail == null || discommail == ""))
			{
				bootbox.alert("Please Select SMS/Email");
				return false;
			}
		//}
		$.ajax({

			url : './addingAllAlarmData',
			type : 'POST',
			data : {
				'all_types' : JSON.stringify(all_types),
				fname : fname,
				'accno' : cheeckedboxes,
				'idaccno' : notifycheeckedboxes,
				'meteringloctype' : locationType,
				'notifyloctype' : notifyTypes,
				notificationDetails : JSON.stringify(notificationDetails)
			},
			dataType : 'text',
			success : function(response) {
				
				if (response!= null || response>0) {
					bootbox.alert("Data Saved Successfully!!!");
					/* $('#tab1').addClass('active');
					$('#tab4').removeClass('active');
					$('#tab_1_2').addClass('active');
					$('#tab_1_5').removeClass('active');
					navigationButtonshowhide(1); */
					//location.href="./alarmSetting";
					setTimeout(function(){
			            window.location.href = './alarmSetting';
			         }, 2000);
					
					return false;
				}
				else{
					bootbox.alert("Data is not saved!!!");
					return false;
					}
			},
		});
	}
	
	</script>
<script>
	function chnageButton(id, val,param,evenflag) {
		
		var eventstatus='noVal';
		$("#getselectedevent").val("0");
		if(evenflag=='1')
		{
		    eventstatus=$('#priority_data_'+param).val();
		}else if (evenflag=='2')
		{
			 eventstatus=$('#priority_data1_'+param).val();
		}
		if(eventstatus=='noVal' || eventstatus==null || eventstatus=="" || eventstatus=='undefined')
		{
			bootbox.alert("Please select alarm Priority!!!");
			return false;
		}else
		{
			if (val == 'inac') {
				$('#' + id).removeClass('btn-success');
				$('#' + id).addClass('btn-danger');
				$('#' + id).text('Inactive');
				$('#' + id).val('act');
				$("#getselectedevent").val("1");
			} else {
				$('#' + id).removeClass('btn-danger');
				$('#' + id).addClass('btn-success');
				$('#' + id).text('Active');
				$('#' + id).val('inac');
				$("#getselectedevent").val("0");
			}
		}
		

	}


function consumerMeterLoc()
{
	$("#circledivsub").show();
	$("#ushowDT").hide();
	$("#ushowFeeder").hide();
	$("#sample_DT").hide();
	$("#sample_feeder").hide();
}

function dtMeterLoc()
{
	$("#view_DT").hide();
	
	$("#circledivsub").hide();
	$("#ushowDT").show();
	$("#sample_1").hide();
	$("#ushowFeeder").hide();
	$("#sample_feeder").hide();
	$("#sample_1").hide();
	$("#sample_1_wrapper").hide();
	
}

function feederMeterLoc()
{   $('#sample_feeder').dataTable().fnClearTable();
    $("#updateBoundaryMaster").html('');
    $('#sample_boundary').dataTable().fnClearTable();
    $("#updateFeederMaster").html('');
    $("#view_2").hide();
    $("#view_1").hide();
   
	
	$("#circledivsub").hide();
	$("#ushowDT").hide();
	$("#ushowFeeder").show();
	$("#sample_1").hide();
	$("#sample_DT").hide();
	$("#sample_1_wrapper").hide();

	$("#boundaryDiv").hide();
	$("#feederDiv").show();
	crossfdr =false;
}

function boundaryMeterLoc()
{   $('#sample_feeder').dataTable().fnClearTable();
    $("#updateBoundaryMaster").html('');
    $('#sample_boundary').dataTable().fnClearTable();
    $("#updateFeederMaster").html('');
    $("#view_2").hide();
    $("#view_1").hide();
   
	
	$("#circledivsub").hide();
	$("#ushowDT").hide();
	$("#ushowFeeder").show();
	$("#sample_1").hide();
	$("#sample_DT").hide();
	$("#sample_1_wrapper").hide();

	$("#feederDiv").hide();
	$("#boundaryDiv").show();
	crossfdr =true;
}
</script>
					

<div class="page-content">
	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-Book"></i><strong>ALARM SETTING</strong>
			</div>
		</div>
		<div class="portlet-body">
            

			<div class="tabbable tabbable-custom">
				<ul class="nav nav-tabs">
					<li class="active" id="tab1"><a class="tabalink" href="#tab_1_2" data-id="1"
						data-toggle="tab">Alarm
							Setting</a></li>
					<li id="tab2"><a class="tabalink" href="#tab_1_3" id="b1" data-toggle="tab" data-id="2"
						>Alarm Type</a></li>
					<li id="tab3"><a class="tabalink" href="#tab_1_4" id="b2" data-toggle="tab" data-id="3"
						>Metering Location</a></li>
					<li id="tab4"><a class="tabalink" href="#tab_1_5" id="b3" data-toggle="tab" data-id="4"
						>Notification Setting</a></li>

				</ul>
				<div class="form-group" style="margin-top: 19px; float: right;"
					id="addData" hidden="true">
					<a class="btn green" id = "addData" name="addData" onclick="return addingDatas();">ADD
					</a>
				</div>
				<div class="form-group" style="margin-top: 19px; float: right;">
					<a class="btn green btnPrevious" id="previous" hidden="true">Previous
					</a> <a class="btn green btnNext" id="next" >Next
					</a>

				</div>
				<br>

				<div class="tab-content">

					<!-- -------------Alarm setting tab------------ -->
					<div class="tab-pane active" id="tab_1_2">
						<div class="box">
							<div id="tab_1-1" class="tab-pane active">
								<div class="row">
									<div class="form-group col-md-4">
										<label form="Alarmname" id="alarmname"><b>Alarm Setting
											Name :-</b></label>
										<div class="">
											<input type="text" id="fname" name="fname"
												class="form-control">
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<!-- ------------Alarm type tab---------------- -->
					<div class="tab-pane" id="tab_1_3">

						<div class="box">
							<div class="tab-content">
								<div id="tab_1-1" class="tab-pane active">
									<div class="form-group" id="alarmtype">
									    <input type="text" id="getselectedevent" value="0" name="getselectedevent" hidden="true">
										<div class="mt-radio-inline">
											<label class="mt-radio"> <input type="radio"
												name="optionsRadios" id="Event" value="Event"
												onclick="return eventdetails();"> <b>Event</b> <span></span></label>
											<!-- <label class="mt-radio"> <input type="radio"
												name="optionsRadios" id="Communication"
												value="Communication"> Communication <span></span></label> --><!--  <label
												class="mt-radio"> <input type="radio"
												name="optionsRadios" id="ValidationFailure"
												value="Validation" onclick="return validatureFailure();">
												Validation Failure <span></span></label> -->
										</div>
									</div>
									<!-- ---------Events table------------- -->
									<table class="table  table-hover table-bordered"
										id="sample_new" hidden="true">
										<thead>
											<tr>
												<th>Event Code</th>
												<th>Event Description</th>
												<th>Alarm Priority</th>
												<th>Alarm State</th>
											</tr>
										</thead>
										<tbody id="eventTR">
										</tbody>
									</table>

									<!-- ----------Validation Failure table----------- -->
									<table class="table  table-hover table-bordered"
										id="sample_val" hidden="true">
										<thead>
											<tr>
												<th>Rule ID</th>
												<th>Rule Name</th>
												<th>Alarm Priority</th>
												<th>Alarm Status</th>
											</tr>
										</thead>
										<tbody id="valfail">
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>

					<!-- ------------Metering loaction tab------------ -->
					<div class="tab-pane" id="tab_1_4">
						<div class="box">
							<div class="tab-content">
								<div id="tab_1-1" class="tab-pane active">
									<div class="form-group" id="meteringtype">
										<div class="mt-radio-inline">
											<!-- <label class="mt-radio"> <input type="radio"
												name="locationRadios" id="Consumer_2" value="Consumer" onclick="return consumerMeterLoc();">
												Consumer <span></span></label> --> <label class="mt-radio"> <input
												type="radio" name="locationRadios" id="DT" value="DT" onclick="return dtMeterLoc();">
												<b>DT</b> <span></span></label> <label class="mt-radio"> <input
												type="radio" name="locationRadios" id="feeder"
												value="feeder" onclick="return feederMeterLoc();"><b>Feeder</b>  <span></span></label>
												<label class="mt-radio"><input type="radio"
												name="locationRadios" id="boundary" value="boundary"
												onclick="return boundaryMeterLoc();"> <b>Boundary</b><span></span></label>
										</div>
									</div>
									<!-- -------------Consumer------------- -->
									<div class="row" style="margin-left: -1px;" id="circledivsub" hidden="true">
										<div class="col-md-3">
											<div id="circleTd" class="form-group">
												<select class="form-control select2me" id="circle"
													name="circle" onchange=" return showDivision(this.value);">
													<option value="">Circle</option>
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
													<option value="">Division</option>
													<%-- <option value="%">ALL</option>
													<c:forEach items="${divisionList1}" var="elements">
														<option value="${elements}">${elements}</option>
													</c:forEach> --%>
												</select>
											</div>
										</div>
										<div class="col-md-3">
											<div id="subdivTd" class="form-group">
												<select class="form-control select2me" id="sdoCode"
													name="sdoCode">
													<option value="">Sub-Division</option>
													<%-- <option value="%">ALL</option>
													<c:forEach items="${subdivList}" var="sdoVal">
														<option value="${sdoVal}">${sdoVal}</option>
													</c:forEach> --%>
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
												<div class="col-md-1">
													<div class="form-group">
														<input type="text" id="acno"
															class="form-control placeholder-no-fix"
															placeholder="Enter Account No." name="acno"
															maxlength="20"></input>
													</div>
												</div>
												<div class="col-md-2"width: 23.667%;>
													<div class="form-group">
														<input type="text" id="kno"
															class="form-control placeholder-no-fix"
															placeholder="Enter K No." name="kno" maxlength="20"></input>
													</div>
												</div>
												<div class="col-md-3">
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
								<div id="ushowDT" hidden="true">
								<%-- <div class="row" style="margin-left: -1px;">
								 <div class="col-md-3">
											<div id="circleTd" class="form-group">
												<select class="form-control select2me" id="circledt"
													name="circle" onchange=" return showDivisionDT(this.value);">
													<option value="">Circle</option>
													<option value="%">ALL</option>
													<c:forEach items="${circleList}" var="elements">
														<option value="${elements}">${elements}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="col-md-3">
											<div id="divisionTdDt" class="form-group">
												<select class="form-control select2me" id="divisiondt"
													name="divisiondt" >
													 <option value="">Division</option>
													<option value="ALL">ALL</option>
													 <c:forEach items="${divisionList1}" var="elements">
														<option value="${elements}">${elements}</option>
													</c:forEach> 
												</select>
											</div>
										</div>
										<div class="col-md-3">
											<div id="subdivTddt" class="form-group">
												<select class="form-control select2me" id="sdoCodedt"
													name="sdoCodedt">
													<option value="">Sub-Division</option>
													<option value="ALL">ALL</option>
													<c:forEach items="${subdivList}" var="sdoVal">
														<option value="${sdoVal}">${sdoVal}</option>
													</c:forEach>
												</select>
											</div>
										</div> 
										<div class="col-md-3">
											<div id="towndivTddt" class="form-group">
												<select class="form-control select2me" id="townCodedt"
													name="townCodedt">
													<option value="">Town</option>
												</select>
											</div>
										</div>
							
										</div> --%>
								<jsp:include page="locationFilter.jsp"/> 
										
									<div class="row" style="margin-left: -1px;">
										<div class="col-md-3">
											<div class="form-group">
												<label class="control-label"><b>DT Code:</b></label>
												<input type="text" id="udtcode"
													class="form-control placeholder-no-fix"
													placeholder="Enter DT Code." name="udtcode" maxlength="12"></input>
											</div>
										</div>
										<!-- <div class="col-md-3">
											<div class="form-group">
												<label>Cross DT : </label>
												<div class="make-switch" data-on="success"
													data-off="warning" style="position: relative; left: 11%;">
													<input type="checkbox" id="unasg_dt_radio"
														name="unasg_dt_radio" checked="checked" class="toggle" />
												</div>
											</div>
										</div> -->
										<div class="col-md-3">
											<div class="form-group">
												 <label class="control-label"><b>Meter Sr No:</b></label> <!--<span
																style="color: red" class="required">*</span>  -->
												<input type="text" id="umtrcode"
													class="form-control placeholder-no-fix"
													placeholder="Enter Meter Sr No." name="umtrcode"
													maxlength="12"></input>
											</div>
										</div>
										
								<div class="col-md-3">
								<div class="form-group">
								<button type="button" id="viewDTData"
											style="margin-top: 26px;"
											onclick="return getDTDataDetails()"
											name="viewDTDataDetails" class="btn yellow">
											<b>View</b>
										</button>
								</div>
								</div>
									<br>
									<br>
									<br>
									<br>
								<div id="view_DT" style="margin-right: 9px;">
									<table class="table table-striped table-hover table-bordered"
										id="sample_DT" hidden="true">
										<thead>
											<tr>
												<th>Select</th>
												<th>Region</th>
												<th>Circle</th>
												<th>Division</th>
												<th>Sub Division</th>
												<th>Town</th>
												<th hidden = "true">Cross DT</th>
												<th>DT Code</th>
												<th>Dt Capacity</th>
												<th>Meter Sr. No</th>
											</tr>
										</thead>
										<tbody id="updateMasterDT">
										</tbody>
									</table>
								</div>
								</div>
							</div>
							<!-- -------------Feeder------------------ -->
							<div id="ushowFeeder" hidden="true">
									<div class="row" style="margin-left: -1px;">
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

										<!-- <div class="col-md-3" id="circid">
											<strong>Circle:</strong>
											<div id="circleForFdr" class="form-group">
												<select class="form-control select2me input-medium" id="circleForFdr"
													name="circleForFdr" onchange="showTownNameandCodeforFeeder(this.value);">
													<option value="">Select Circle</option>
													<option value="%">ALL</option>
												</select>
											</div>
										</div> -->
										<div class="col-md-3" >
											<b>Circle:</b> <select
												class="form-control select2me input-medium" name="circleForFdr"
												id="circleForFdr" onchange="showTownNameandCodeforFeeder(this.value);" >
												<option value="">Select Circle</option>
												<option value="%">ALL</option>

											</select>
										</div>

										<!-- <div class="col-md-3" id="toid">
											<strong>Town:</strong>
											<div id="townForFdr" class="form-group">
												<select class="form-control select2me input-medium" id="townForFdr"
													name="townForFdr"
													onchange="showResultsbasedOntownCode(this.value);">
													<option value="">Select Town</option>
												</select>
											</div>
										</div> -->

										<div class="col-md-3">
											<b>Town:</b> <select
												class="form-control select2me input-medium" name="townForFdr"
												id="townForFdr">
												<option value="">Select Town</option>

											</select>
										</div>
									</div>
									<%-- 	<jsp:include page="locationFilter.jsp"/>  --%>
										
										<div class="row" style="margin-left: -1px;">
										<div class="col-md-3" id="feederDiv" style="display:none">
											<div class="form-group" >
												<label class="control-label"><b>Feeder Code:</b></label>
												<input type="text" id="ufdcode"
													class="form-control placeholder-no-fix"
													placeholder="Enter Feeder Code." name="ufdcode" maxlength="12"
													 style="width: 241px;"></input>
											</div>
										</div>
										
										<div class="col-md-3" id="boundaryDiv" style="display:none">
										<label class="control-label"><b>Boundary Code:</b></label>
											<div class="form-group">
												<input type="text" id="boundarycode"
													class="form-control placeholder-no-fix"
													placeholder="Enter Boundary Code." name="boundarycode"
													maxlength="12" style="width: 241px;"></input>
											</div>
										</div>
										<!-- <div class="col-md-3"  style="position: relative; top: 30px;">
											<div class="form-group">
												<label><b>Cross Feeder :</b> </label>
												<div class="make-switch" data-on="success"
													data-off="warning" >
													<input type="checkbox" id="asg_ft_radio"
														name="asg_ft_radio" checked="checked" class="toggle" />
												</div>
											</div>
										</div> -->
										<div class="col-md-3">
											<div class="form-group">
												<label class="control-label"><b>Meter Sr No:</b></label> 				
												<input type="text" id="fdmtrcode"
													class="form-control placeholder-no-fix"
													placeholder="Enter Meter Sr No." name="fdmtrcode"
													maxlength="12" style="width: 241px;"></input>
											</div>
										</div>
										<div class="col-md-3">
											<div class="form-group">
								<button type="button" id="viewFeederData"
											style="margin-top: 26px;"
											onclick="return getFeederDataDetails();"
											name="viewFeederDataDetails" class="btn yellow">
											<b>View</b>
										</button>
								</div>
								</div>
									<br>
									<br>
									<br>
									<br>
									
							<div id="view_1" style="margin-right: 9px;">
									<table class="table table-striped table-hover table-bordered"
										id="sample_feeder" hidden="true">
										<thead>
											<tr>
												<th>Select</th>
												<th>Region</th>
												<th>Circle</th>
												<th>Division</th>
												<th>Sub Division</th>
												<th>Town</th>
												<!-- <th>Cross Feeder</th> -->
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
												<th>Select</th>
												<th>Region</th>
												<th>Circle</th>
												<th>Division</th>
												<th>Sub Division</th>
												<th>Town</th>
												<!-- <th>Cross Feeder</th> -->
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
					
       <div id="mtrngimageee" hidden="true" style="text-align: center;">
		     <h3 id="loadingText">Loading..... Please wait.</h3>
		     <img alt="image" src="./resources/assets/img/loading.gif" style="width: 4%; height: 4%; align-content: center;">
       </div>

					<!-- ------------Notification Setting tab---------------- -->
					<div class="tab-pane" id="tab_1_5">
						<div class="box">
							<div class="tab-content">
								<div id="tab_1-1" class="tab-pane active">
									<div class="form-group" id="notifysetting">
										<div class="mt-radio-inline">
											<!-- <label class="mt-radio"> <input type="radio"
												name="notificationRadios" id="Consumer" value="Consumer"
												checked onclick="return getConsumernotify();">
												Consumer <span></span></label> --> <label class="mt-radio"><b>DISCOM User</b>  <span></span></label>
												 <input
												type="hidden" name="notificationRadios" id="DISCOMuser"
												value="DISCOMuser" >
												
										</div>
																		<%-- <jsp:include page="locationFilter.jsp"/>  --%>
										
									</div>
									<%-- <div id="notifyset">
										<div class="row" style="margin-left: -1px;">
											<div class="col-md-3">
												<div id="circleTd" class="form-group">
													<select class="form-control select2me" id="circle1"
														name="circle1"
														onchange="return showDivision1(this.value);">
														<option value="">Select Circle</option>
														<option value="%">ALL</option>
														<c:forEach items="${circleList}" var="elements">
															<option value="${elements}">${elements}</option>
														</c:forEach>
													</select>
												</div>
											</div>
											<div class="col-md-3">
												<div id="divisionTd1" class="form-group">
													<select class="form-control select2me" id="division1"
														name="division1" onchange="showSubdivByDiv1(this.value);">
														<option value="">Select Division</option>
														
													</select>
												</div>
											</div>
											<div class="col-md-3">
												<div id="subdivTd1" class="form-group">
													<select class="form-control select2me" id="sdoCode1"
														name="sdoCode1">
														<option value="">Select Sub-Division</option>
														
													</select>
												</div>
											</div>

											<div class="col-md-3">
												<div class="form-group">
													<!-- <label class="control-label">Consumer Category:</label> -->
													<select class="form-control select2me" id="consumerCatgry1"
														name="consumerCatgry1">
														<option value="">Select Consumer Category</option>
													</select>
												</div>
											</div>
										</div>
										<div class="row" style="margin-left: -1px;">
											<div class="col-md-3">
												<div class="form-group">
													<input type="text" id="acno1"
														class="form-control placeholder-no-fix"
														placeholder="Enter Account No." name="acno1" maxlength="20"></input>
												</div>
											</div>
											<div class="col-md-3">
												<div class="form-group">
													<input type="text" id="kno1"
														class="form-control placeholder-no-fix"
														placeholder="Enter K No." name="kno1" maxlength="20"></input>
												</div>
											</div>
											<div class="col-md-3">
												<div class="form-group">
													<input type="text" id="mtrno1"
														class="form-control placeholder-no-fix"
														placeholder="Enter Meter Sr No." name="mtrno1"
														maxlength="12"></input>
												</div>
											</div>
										</div>

										<button type="button" id="viewConsumerDataSetting"
											style="margin-left: 480px;"
											onclick="getConsumerDataSetting()"
											name="viewConsumerDataSetting" class="btn yellow">
											<b>View</b>
										</button>
									</div>
									<br> <br>

									<table class="table table-striped table-hover table-bordered"
										id="sample_2">
										<thead>
											<tr>
												<th><input id=check2 name=check type=checkbox
													class=checkboxes2></th>
												<th>Circle</th>
												<th>Division</th>
												<th>Sub Division</th>
												<th>Account No</th>
												<th>K No</th>
												<th>Customer Name</th>
												<th>Category</th>
												<th>Meter Sr. No</th>
												<th>SMS</th>
												<th>Email</th>
											</tr>
										</thead>
										<tbody id="notifyMaster">
										</tbody>
									</table> --%>

									<!-- -----------discom user ---------------- -->
									<div id="userlistdiscom" >
										<div class="row" style="margin-left: -15px;">
										
											<%-- <div class="col-md-3">
												<div id="listdiscom" class="form-group">
													<select class="form-control select2me" id="discom"
														name="discom">
														<option value="">Select Discom</option>
														 <option value="%">ALL</option>
														<c:forEach items="${discomList}" var="elements">
															<option value="${elements}">${elements}</option>
														</c:forEach>
													</select>
												</div>
											</div> --%>

											<%-- <div class="col-md-3">
												<div id="zoneTD" class="form-group">
													<select class="form-control select2me" id="Zone"
														name="Zone" onchange="showCircle(this.value)";>
														<option value="">Select ZONE</option>
														<option value="%">ALL</option>
														<c:forEach items="${ZoneList}" var="elements">
															<option value="${elements}">${elements}</option>
														</c:forEach>
													</select>
												</div>
											</div> --%>
											<!-- <div class="col-md-3">
												<div id="circleTd2" class="form-group">
													<select class="form-control select2me" id="circle2"
														name="circle2"
														onchange="return showDivision2(this.value);">
														<option value="">Select Circle</option>
													
													</select>
												</div>
											</div> -->
										<!-- </div>
										<div class="row">
											<div class="col-md-3">
												<div id="divisionTd2" class="form-group">
													<select class="form-control select2me" id="division2"
														name="division2" onchange="showSubdivByDiv2(this.value);">
														<option value="">Select Division</option>
														
													</select>
												</div>
											</div>


											<div class="col-md-3">
												<div id="subdivTd2" class="form-group">
													<select class="form-control select2me" id="sdoCode2"
														name="sdoCode2">
														<option value="">Select Sub Division</option>
													
													</select>
												</div>
											</div> -->
											<div class="col-md-3" id="regid">
												<strong>Region:</strong>
												<div id="zonentse1" class="form-group">
													<select class="form-control select2me input-medium"
														id="zoneForntse" name="zoneForntse"
														onchange="showCircleforntse(this.value);">
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
												class="form-control select2me input-medium" name="circleForntse"
												id="circleForntse" onchange="showTownNameandCodeforntse(this.value);" >
												<option value="">Select Circle</option>
												<option value="%">ALL</option>

											</select>
										</div>

										
										<div class="col-md-3">
											<b>Town:</b> <select
												class="form-control select2me input-medium" name="townForntse"
												id="townForntse">
												<option value="">Select Town</option>

											</select>
										</div>
									</div>
											<div class="col-md-3" class="row" style="margin-left: -31px;">
												<%-- <div  class="form-group">
												<b>UserRole:</b>
													<select class="form-control select2me" id="userrole"
														name="userrole">
														<option value="">Select User Role</option>
														<option value="%">ALL</option>
														<c:forEach items="${userroleList}" var="userrole">
															<option value="${userrole}">${userrole}</option>
														</c:forEach>
													</select>
												</div> --%>
											<%-- <div class="col-md-3">
												<b>UserRole:</b> <select
													class="form-control select2me input-medium" name="userrole"
													id="userrole">
													<option value="">Select User Role</option>
													<option value="%">ALL</option>
													<c:forEach items="${userroleList}" var="userrole">
														<option value="${userrole}">${userrole}</option>
													</c:forEach>
												</select>
											</div> --%>

											<div class="col-md-3">
												<strong>UserRole:</strong>
												<div id="selectoptions" class="form-group">
													<select class="form-control select2me input-medium"  name="userrole"
													id="userrole">
														<option value="">Select User Role</option>
													<option value="%">ALL</option>
													<c:forEach items="${userroleList}" var="userrole">
														<option value="${userrole}">${userrole}</option>
													</c:forEach>
												</select>
												</div>
											</div>
										</div>
											
										</div>
										<button type="button" id="viewUserroleData"
											style="margin-left: 120px; position: relative; top: 15px;"  onclick="getUserroleData();"
											name="getUserroleData" class="btn yellow">
											<b>View</b>
										</button>
									</div>
								</div>
								<br> <br>
								<!-- --------------Discom user table------------- -->
								<table class="table table-striped table-hover table-bordered"
									id="sample_3" hidden="true">
									<thead>
										<tr>
											<th><input id=check name=check type=checkbox
												class=checkboxes></th>
											<th>Office</th>
											<th>Login Name</th>
											<th>User Name</th>
											<th>User Role</th>
											<th>Mobile No</th>
											<th>Email ID</th>
											<th>SMS</th>
											<th>Email</th>
										</tr>
									</thead>
									<tbody id="discomuser">
									</tbody>
								</table>
								
	   <div id="notifyimageee" hidden="true" style="text-align: center;">
		     <h3 id="loadingText">Loading..... Please wait.</h3>
		     <img alt="image" src="./resources/assets/img/loading.gif" style="width: 4%; height: 4%; align-content: center;">
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
	

/* $('.tabalink').click(function(event) 
	{
	
	var obj=$('.nav-tabs > .active').next('li').find('a');
	if(validations(obj[0].id))
	{
			   
	   $('.nav-tabs > .active').next('li').find('a').triggerHandler('click'); 
	   event.preventDefault(); 
	}
	else{
	  return false;
	}
	navigationButtonshowhide($(this).attr("data-id"));
    
}); */
$('.nav').on('click', '.tabalink', function(event) { 
	var obj=$('.nav-tabs > .active').next('li').find('a');
	if(validations(obj[0].id))
	{
			   
	   $('.nav-tabs > .active').next('li').find('a').triggerHandler('click'); 
	   event.preventDefault(); 
	}
	else{
	  return false;
	}
	navigationButtonshowhide($(this).attr("data-id"));
   
});

	function navigationButtonshowhide(val) {
		if (val == 1) {
			$("#previous").hide();
			$("#next").show();
			
		} else if (val == 2) {
			$("#previous").show();
			$("#next").show();
			
			
		} else if (val == 3) {
			$("#previous").show();
			$("#next").show();
			
		} else if (val == 4) {
			$("#previous").show();
			$("#next").hide();
			$('#addData').show();
			
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
	
	$('.btnNext').click(function() 
	{
		
		var obj=$('.nav-tabs > .active').next('li').find('a');
		if(validations(obj[0].id))
		{
		  $('.nav-tabs > .active').next('li').find('a').trigger('click');
		}
	});

	$('.btnPrevious').click(function() {
		$('.nav-tabs > .active').prev('li').find('a').trigger('click');
	});
	
	function validations(val)
	{
		
		if (val == 'b1')
		{
			if ($('#fname').val() == "" || $('#fname').val() == null || $('#fname').val() == 'undefined') 
			{
				bootbox.alert("Please Enter Alarm Setting Name");
				return false; 
		    } 
			   
		}
		if(val == 'b2')
		{
			var flag1=$('input[name=optionsRadios]:checked').val();
			if(flag1=='undefined' || flag1==null || flag1=="")
			{
				bootbox.alert("Please Select any one option in Alarm Type");
				return false; 
			}
			else 
			{
				if ($('#getselectedevent').val() == "0")
				{
					bootbox.alert("Please Select  Alarm State");
					return false; 
				} 
			}
			
		}
		if(val == 'b3')
		{
			var flag2=$('input[name=locationRadios]:checked').val();
			if(flag2=='undefined' || flag2==null || flag2=="")
			{
				bootbox.alert("Please Select any one option in Metering Location");
				return false; 
			}
			else if (flag2 == 'Consumer')
			{
			    var consumeracc =$('input[name=checkboxm]:checked').val(); 
			       if(consumeracc == 'undefined' || consumeracc == null || consumeracc == "")
			       {
			    	   bootbox.alert("Please Select atleast one Consumer");
			    	   return false;
			       }
			   
			}
			else if (flag2 == 'DT')
			{
				var dtacc =$('input[name=dtcheck]:checked').val(); 
			       if(dtacc == 'undefined' || dtacc == null || dtacc == "")
			       {
			    	   bootbox.alert("Please Select atleast one DT");
			    	   return false;
			       }
			}
			else if (flag2 == 'Feeder')
			{
				var feederacc =$('input[name=fdrcheck]:checked').val(); 
			       if(feederacc == 'undefined' || feederacc == null || feederacc == "")
			       {
			    	   bootbox.alert("Please Select atleast one Feeder");
			    	   return false;
			       }
			}
		}
		
		return true;
	}
	
	 function clearTabledataContent(tableid)
	 {
	 	var oSettings = $('#'+tableid).dataTable().fnSettings();
	 	var iTotalRecords = oSettings.fnRecordsTotal();
	 	for (i = 0; i <= iTotalRecords; i++) 
	 	{
	 		$('#'+tableid).dataTable().fnDeleteRow(0, null, true);
	 	} 
	 	
	 }
</script>