<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<script src="<c:url value='/resources/bsmart.lib.js/shim.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/xlsx.full.min.js'/>" type="text/javascript"></script>
<%
	if (session.getAttribute("username") == null || session.getAttribute("username") == null) {
%>
<script>
	window.location.href = "./?sessionVal=expired";
</script>
<%
	}
%>
<script>
$(".page-content").ready(	 	
		function() {
			App.init();
			TableEditable.init();
			FormComponents.init();
			loadSearchAndFilter('sample');		
			var zoneVal="${zoneVal}";
			var circleVal="${circleVal}";
			$("#month").val('');
			
	       if(zoneVal!='' && circleVal!=''){	    	   
				$('#zone').find('option').remove().end().append('<option value="'+ zoneVal +'">'+ zoneVal +'</option>');
				$("#zone").val(zoneVal).trigger("change");
				setTimeout(function(){ 
					$('#circle').find('option').remove().end().append('<option value="'+ circleVal +'">'+ circleVal +'</option>');
					$("#circle").val(circleVal).trigger("change");
				}, 200);
			} else{
				$("#zone").val("").trigger("change");
			}
			 $('#MDMSideBarContents,#reportsId,#consMonthReadingReport').addClass('start active ,selected');
			 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
		   	    
			
		});

		$("#month").datepicker({
			format : "mm-yyyy",
			startView : "months",
			minViewMode : "months"
		});
		$(document).ready(function() {
			getAllLocation('${officeCode}', '${officeType}');
		});



</script>


<script>
	function getAllLocation(officeCode, officeType) {
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
					//alert(response);
						var html = "";
						if (response != null) {
							if (officeType == "discom") {
								html += "<option value=''>Select Circle</option><option value='%'>ALL</option>";
								for (var i = 0; i < response.length; i++) {
									html += "<option value='"+response[i]+"'>"
											+ response[i] + "</option>";
								}
								$("#circle").empty();
								$("#circle").append(html);
							}

							else if (officeType == "division") {
								var htmlCircle = "";
								var htmlDivision = "";
								for (var i = 0; i < response.length; i++) {
									htmlCircle += "<option value='"+response[i][0]+"'>"
											+ response[i][0] + "</option>";
									htmlDivision += "<option value='"+response[i][1]+"'>"
											+ response[i][1] + "</option>";
									}
								$("#circle").empty();
								$("#circle").append(htmlCircle);
								$("#division").empty();
								$("#division").append(htmlDivision);

							} else if (officeType == "circle") {
								$('#circle').find('option').remove();
								html += "";
								for (var i = 0; i < response.length; i++) {
									 $('#circle').append($('<option>', {
										value : response[i],
										text : response[i]
									})); 
								}
								getDivByCircle($("#circle").val());
							//	showTown($("#circle").val());
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
								$("#circle").empty();
								$("#circle").append(htmlCircle);
								$("#division").empty();
								$("#division").append(htmlDivision);
								$("#sdoCode").empty();
								$("#sdoCode").append(htmlSubdivision);
								//getSubStations($('#sdoname').val());
							}

						}

					}
				});
	}

	function getCircleByZone(zone)
	{
		
		//alert(zone);
		$.ajax({
			type : 'GET',
			url : "./getCircleByZone",
			data:{zone:zone},
			async : false,
			cache : false,
			success : function(response)
			{
				//alert(response);
				var html='';
				if(response!=null)
					{
					html += "<select id='circle' name='circle' onchange='getDivByCircle(this.value)' class='form-control' type='text'><option value=''>Select Circle</option>"; 
					for(var i=0;i<response.length;i++)
						{
						html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
						}
					html += "</select><span></span>";
					$("#circleTd").html(html);
					$('#circle').select2();
					// $("#circle").empty();
					//$("#circle").append(html); 
					}
				
			}
		});

		
	}

	/* function getDivByCircle(circle)
	{
		//alert(circle);
		$.ajax({
			type : 'GET',
			url : "./getDivByCircle",
			data:{circle:circle},
			async : false,
			cache : false,
			success : function(response)
			{
				//alert(response);
				var html="";
				if(response!=null)
					{
					html += "<select id='division' name='division' onchange='getSubDivByDivision(this.value)' class='form-control' type='text'><option value=''>Select Division</option>";
					for(var i=0;i<response.length;i++)
						{
						html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
						}
					html += "</select><span></span>";
					$("#divisionTd").html(html);
					$('#division').select2();
					// $("#division").empty();
					//$("#division").append(html);
					}
				
			}
		});
	}

	function getSubDivByDivision(division)
	{
		//alert(division);
		$('#sdoCode').empty();
		$('#sdoCode').find('option').remove();
		$('#sdoCode').append($('<option>', {
			value : "",
			text : "Sub-Division"
		}));
		$.ajax({
			type : 'GET',
			url : "./getSubdivByDiv",
			data:{division:division},
			async : false,
			cache : false,
			success : function(response)
			{
				//alert(response);
				var html="";
				if(response!=null)
					{
				//	html+="<option value=0>Select Sub-Division</option>"; 
					for(var i=0;i<response.length;i++)
						{
						//html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
						$('#sdoCode').append($('<option>', {
							value : response[i],
							text : response[i]
						}));

						}
					//$("#sdoCode").empty();
					//$("#sdoCode").append(html);
					}
				
			}
		});
	} */


	function getDivByCircle(circle) {
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
							html += "<select id='division' name='division' onchange='getSubDivByDivision(this.value)' class='form-control input-medium' type='text'><option value=''>Select Division</option><option value='%'>ALL</option>";
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
	 function getSubDivByDivision(division) {
			
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
							html += "<select id='sdoCode' name='sdoCode'  class='form-control input-medium' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
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
	

</script>
<script>

var xport = {
		  _fallbacktoCSV: true,  
		  toXLS: function(tableId, filename) {   
		    this._filename = (typeof filename == 'undefined') ? tableId : filename;
		    
		    //var ieVersion = this._getMsieVersion();
		    //Fallback to CSV for IE & Edge
		    if ((this._getMsieVersion() || this._isFirefox()) && this._fallbacktoCSV) {
		      return this.toCSV(tableId);
		    } else if (this._getMsieVersion() || this._isFirefox()) {
		      alert("Not supported browser");
		    }

		    //Other Browser can download xls
		    var htmltable = document.getElementById(tableId);
		    var html = htmltable.outerHTML;

		    this._downloadAnchor("data:application/vnd.ms-excel" + encodeURIComponent(html), 'xls'); 
		  },
		  toCSV: function(tableId, filename) {
		    this._filename = (typeof filename === 'undefined') ? tableId : filename;
		    // Generate our CSV string from out HTML Table
		    var csv = this._tableToCSV(document.getElementById(tableId));
		    // Create a CSV Blob
		    var blob = new Blob([csv], { type: "text/csv" });

		    // Determine which approach to take for the download
		    if (navigator.msSaveOrOpenBlob) {
		      // Works for Internet Explorer and Microsoft Edge
		      navigator.msSaveOrOpenBlob(blob, this._filename + ".csv");
		    } else {      
		      this._downloadAnchor(URL.createObjectURL(blob), 'csv');      
		    }
		  },
		  _getMsieVersion: function() {
		    var ua = window.navigator.userAgent;

		    var msie = ua.indexOf("MSIE ");
		    if (msie > 0) {
		      // IE 10 or older => return version number
		      return parseInt(ua.substring(msie + 5, ua.indexOf(".", msie)), 10);
		    }

		    var trident = ua.indexOf("Trident/");
		    if (trident > 0) {
		      // IE 11 => return version number
		      var rv = ua.indexOf("rv:");
		      return parseInt(ua.substring(rv + 3, ua.indexOf(".", rv)), 10);
		    }

		    var edge = ua.indexOf("Edge/");
		    if (edge > 0) {
		      // Edge (IE 12+) => return version number
		      return parseInt(ua.substring(edge + 5, ua.indexOf(".", edge)), 10);
		    }

		    // other browser
		    return false;
		  },
		  _isFirefox: function(){
		    if (navigator.userAgent.indexOf("Firefox") > 0) {
		      return 1;
		    }
		    
		    return 0;
		  },
		  _downloadAnchor: function(content, ext) {
		      var anchor = document.createElement("a");
		      anchor.style = "display:none !important";
		      anchor.id = "downloadanchor";
		      document.body.appendChild(anchor);

		      // If the [download] attribute is supported, try to use it
		      
		      if ("download" in anchor) {
		        anchor.download = this._filename + "." + ext;
		      }
		      anchor.href = content;
		      anchor.click();
		      anchor.remove();
		  },
		  _tableToCSV: function(table) {
		    // We'll be co-opting `slice` to create arrays
		    var slice = Array.prototype.slice;

		    return slice
		      .call(table.rows)
		      .map(function(row) {
		        return slice
		          .call(row.cells)
		          .map(function(cell) {
		            return '"t"'.replace("t", cell.textContent);
		          })
		          .join(",");
		      })
		      .join("\r\n");
		  }
		};


</script>

<script>
function viewMonthlyReportData(){
	//var zone = $('#zone').val();
	var circle = $('#circle').val();
	var division = $('#division').val();
	var sdoCode = $('#sdoCode').val();
	var acno = $('#acno').val();
	var kno = $('#kno').val();
	var mtrno = $('#mtrno').val();
	var month = $('#month').val();

/* 	if (zone == "") {
		bootbox.alert("Please Select Zone");
		return false;
	} */

	if (circle == "") {
		bootbox.alert("Please Select Circle");
		return false;
	}

	if (division == "") {
		bootbox.alert("Please Select Division");
		return false;
	}

	if (sdoCode == "") {
		bootbox.alert("Please Select Subdivision");
		return false;
	}
	if(month==""){
    	bootbox.alert("Please Select Month");
		return false;
    }
	$
	.ajax({
	    url : './getConsMonthlyReportData',
    	type:'POST',
    	data : {
	   // 	zone : zone,
	    	circle : circle,
	    	division : division,
	    	sdoCode : sdoCode,
	    	acno : acno,
	    	kno : kno,
	    	mtrno : mtrno,
	    	month:month	
	    },
		dataType : 'json',
    	success:function(response)
    	{
    		$('#sample').dataTable().fnClearTable();
    		$("#updateMaster").html('');
    		if(response!=null && response.length>0 )
			{
		      var html="";
			  for (var i = 0; i < response.length; i++) 
			  {
				  var resp=response[i];
				  var j = i + 1;
				   html+="<tr>"+
					  "<td>"+j+"</td>"+
					  "<td>"+sdoCode+" </td>"+
					  "<td>"+((resp[0]==null)?"":resp[0])+" </td>"+
					  "<td>"+((resp[1]==null)?"":resp[1])+" </td>"+
					  "<td>"+((resp[2]==null)?"":resp[2])+" </td>"+
					  "<td>"+((resp[3]==null)?"":resp[3])+" </td>"+
					  "<td>"+((resp[4]==null)?"":resp[4])+" </td>"+
					  "<td>"+((resp[5]==null)?"":resp[5])+" </td>"+
					  "<td>"+((resp[6]==null)?"":moment(resp[6]).format('DD-MM-YYYY hh:mm:ss'))+" </td>"+
					  "<td>"+((resp[7]==null)?"":resp[7])+" </td>"+
					  "<td>"+((resp[8]==null)?"":resp[8])+" </td>"+
					  "<td>"+((resp[9]==null)?"":resp[9])+" </td>"+
					  "</tr>";					
			   }                                
			  $('#sample').dataTable().fnClearTable();
			  $("#updateMaster").html(html);
			  loadSearchAndFilter('sample'); 
			}	else{
				bootbox.alert("No Relative Data Found.");
			}	 
		},
		complete: function()
		{  
			loadSearchAndFilter('sample'); 
		}
	});

}





</script>

<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption"><i class="fa fa-globe"></i>Consumer Monthly Reading Report</div>
					<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
					</div>
				</div>

				<div class="portlet-body">
				
				<div class="row" style="margin-left: -1px;">
						<%-- <div class="col-md-3">
							<div class="form-group">
								<select class="form-control select2me" name="zone" id="zone"
										onchange="getCircleByZone(this.value);">
										<option value="">Select Zone</option>
										<!-- <option value="ALL">ALL</option> -->
										<c:forEach var="elements" items="${zoneList}">
										<option value="${elements}">${elements}</option>
										</c:forEach>
								</select>
							</div>
						</div> --%>
						<div class="col-md-3">
							<div id="circleTd" class="form-group">
								<select class="form-control select2me" id="circle" name="circle" onchange="getDivByCircle(this.value)">
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
								<select class="form-control select2me" id="division" name="division" onchange="getSubDivByDivision(this.value)">
										<option value="">Select Division</option>
										<!-- <option value="ALL">ALL</option> -->
										<c:forEach items="${divisionList}" var="elements">
										<option value="${elements}">${elements}</option>
										</c:forEach>
								</select>
							</div>
						</div>
					
					
					<div class="col-md-3">
						<div id="subdivTd" class="form-group">
							<select class="form-control select2me" id="sdoCode" name="sdoCode">
									<option value="">Select Sub-Division</option>
								<!-- 	<option value="ALL">ALL</option> -->
									<c:forEach items="${subdivList}" var="sdoVal">
									<option value="${sdoVal}">${sdoVal}</option>
									</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-md-3">
							<div class="input-group ">
									<input type="text" class="form-control from"  id="month"
											name="month" placeholder="Select Month" style="cursor: pointer"> <span
											class="input-group-btn">
										<button class="btn default" type="button">
												<i class="fa fa-calendar"></i>
											</button>
										</span>
							</div> 
								
					</div>
				</div>
				
				<div class="row" style="margin-left: -1px;">
											 <div class="col-md-3">
													<div class="form-group">
														<input type="text" id="acno"
																class="form-control placeholder-no-fix"
																placeholder="Enter Account No." name="acno"
																maxlength="12"></input>
													</div>
											</div>
											<div class="col-md-3">
													<div class="form-group">
															<!-- <label class="control-label">K No</label>  
															<span style="color: red" class="required">*</span> --> 
															<input type="text" id="kno"
																class="form-control placeholder-no-fix"
																placeholder="Enter K No." name="kno" maxlength="12"></input>
													</div>
											</div>
											<div class="col-md-3">
													<div class="form-group">
															<!-- <label class="control-label">Meter Sr No</label> <span
																style="color: red" class="required">*</span> -->
															 <input type="text" id="mtrno"
																class="form-control placeholder-no-fix"
																placeholder="Enter Meter Sr No." name="mtrno"
																maxlength="12"></input>
													</div>
												</div>
											
									</div>
				
					<!--BEGIN TABS-->
					<div class="tabbable tabbable-custom">
								<div id="showConsumer">
									<button type="button" id="viewConsumerData" style="margin-left: 480px;" onclick="viewMonthlyReportData()" name="viewConsumerData" class="btn green">
										<b>Generate Report</b>
									</button>
									<br />
									
									<div class="table-toolbar">
										<div class="btn-group pull-right" style="margin-top: 15px;">
											<button class="btn dropdown-toggle" data-toggle="dropdown">
												Tools <i class="fa fa-angle-down"></i>
											</button>
											<ul class="dropdown-menu pull-right">

												<li>
												<a href="#" id="excelExport" onclick="tableToXlxs('sample', 'ConsumerMonthlyReading_Report')">Export to Excel</a>
												</li>
												
												<li><a href="#" id="excelExport1" onclick="javascript:xport.toCSV('sample', 'ConsumerMonthlyReading_Report');">Export to CSV</a>
											   </li>

												
											</ul>
										</div>
									</div>
									<table class="table table-striped table-bordered table-hover"
										id="sample">
										<thead>
											<tr>
												<th>Sl No</th>
												<th>Sub-Division</th>
												<th>Acc No</th>
												<th>K No</th>
												<th>Consumer Name</th>
												<th>Meter No</th>
												<th>MF</th>
												<th>Bill Month</th>
												<th>Reading Date&Time</th>
												<th>KWH</th>
												<th>KVAH</th>
												<th>KVA</th>
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
	
	
</div>



<script>

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
		text : "Sub-Division"
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


	var date=new Date();
	var year=date.getFullYear();
	var month=date.getMonth();
	//var startDate = new Date();
    

	$('.from').datepicker
	({
	    format: "yyyymm",
	    minViewMode: 1,
	    autoclose: true,
	    startDate :new Date(new Date().getFullYear(),'-55'),
	    endDate: new Date(year, month-1, '31')
	});


</script>
