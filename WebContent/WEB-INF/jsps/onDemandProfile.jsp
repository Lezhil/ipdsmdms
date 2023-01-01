<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {

					 	$("#3gCellData").hide();
						$("#otherHes").hide();
						$("#fromdateTS").hide();
						$("#nameFromDate").hide();
						$("#toDateTS").hide();
						$("#nameToDate").hide(); 

						
						TableManaged.init();
						FormComponents.init();
						$('#MDASSideBarContents,#ondemand,#meterOper,#onDemandProfile').addClass('start active ,selected');
								
						$(
								"#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
								.removeClass('start active ,selected');
						transdata();
						App.init();
					});

	</script>
	
	
<div class="page-content">
	<div id="taid"></div>
	<div class="row">
		<div class="col-md-12">

			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>On Demand Profile
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
							<td style="width: 100px;">Meter Id :</td>
							<td><input class="form-control input-large" placeholder="Enter Meter ID" type="text" name="meterNum" autocomplete="off" id="meterNum" onchange="return getHes(this.value);" style="width: 320px;">
							</td>
						</tr>
						<tr>
							<td>MDAS Type:</td>
							<td><input class="form-control input-large" readonly="readonly" type="text" name="hesType" autocomplete="off" id="hesType" style="width: 320px;"></td>
						</tr>
					</table>

					<div class="" id="otherHes">
						<table>
							<tr>
								<td style="width: 100px;">Select Profile :</td>
								<td><select id="demId" class="form-control placeholder-no-fix" onchange="return hideDates(this.value)" autocomplete="off" placeholder="" name="event"style="width: 320px;">
										<option value="">Select Profile Type</option>
										<option value="INSTANTANEOUS">Instantaneous Data</option>
										<option value="BILLING">Bill Data</option>
										<option value="BULKLOAD">Load Data</option>
										<option value="EVENTLOGS">Event Data</option>
										<!-- <option value="NONROLLOVEREVENTLOG">Non roll over event log</option> -->
										<!-- <option value="CONTROLEDEVENTLOG">Controled event log</option> -->
										<!-- <option value="NAMEPLATE">Name Plate Profile</option> -->
										<!-- <option value="TRANSACTIONEVENTLOG">Transaction event log</option> -->
										<!-- <option value="nroel">Non Roll Over event log </option> -->
										<!-- <option value="CURRENTEVENTLOG">Current event log </option> -->
										<!-- <option value="VOLTAGEEVENTLOG">Voltage event log</option> -->
										<!-- <option value="OTHEREVENTLOG">Other event log</option> -->
										<!-- <option value="DAILYLOAD">Daily load</option> -->

										<!-- <option value="CONTROLEDEVENTLOG">CONTROLED EVENT LOG</option> -->
										<!-- <option value="METERSEARCH">Meter search</option> -->


								</select></td>
							</tr>



						</table>
					</div>

					<div class="" id="3gCellData">

						<table>

							<tr>
								<td style="width: 100px;">Select Type :</td>
								<td>
									<div class="icheck-inline">
										<label> <input type="radio" name="reqtype" value="sms"
											class="icheck" checked>SMS
										</label> <label> <input type="radio" name="reqtype"
											value="mqtt" class="icheck"> MQTT
										</label>

									</div>
								</td>
							</tr>
							<tr>
								<td style="width: 100px;">Select Profile :</td>
								<td>
									<!-- 	<select id="onDemTyp" class="form-control select2me input-medium: col-xs-12" name="onDemTyp" > -->
									<select id="onDemTyp" class="form-control placeholder-no-fix"
									onchange="return hideDates(this.value)" type="text"
									autocomplete="off" placeholder="" name="onDemTyp"
									style="width: 320px;">
										<option value="0"></option>
										<option value="Instant">Instantaneous</option>
										<option value="Billing">Billing</option>
										<option value="Load">Load</option>
										<option value="Event">Event</option>

										<!-- <option value="CONTROLEDEVENTLOG">CONTROLED EVENT LOG</option> -->
										<!-- <option value="METERSEARCH">Meter search</option> -->


								</select>
								</td>
							</tr>



						</table>
					</div>

					<table>
						<tr>
							<td style="width: 100px;" id="nameFromDate">From Date :</td>
							<td>
								<!-- <div class="input-group input-medium date date-picker" data-date-format="dd-mm-yyyy" data-date-viewmode="years">
							<input type="text" class="form-control" name="eventFromDate" id="eventFromDate"  readonly>
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div> -->
								<div class="input-group date form_datetime" id="fromdateTS">
									<input type="text" size="24" id="eventFromDate" readonly
										class="form-control" style="width: 239px;"> <span
										class="input-group-btn">
										<button class="btn default date-reset" type="button">
											<i class="fa fa-times"></i>
										</button>
									</span> <span class="input-group-btn">
										<button class="btn default date-set" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>
							</td>
						</tr>

						<tr>
							<td style="width: 100px;" id="nameToDate">To Date :</td>
							<td>
								<!-- <div class="input-group input-medium date date-picker" data-date-format="dd-mm-yyyy" data-date-viewmode="years">
							<input type="text" class="form-control" name="eventToDate" id="eventToDate"  readonly>
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div> -->
								<div class="input-group date form_datetime" id="toDateTS">
									<input type="text" size="24" id="eventToDate" readonly
										class="form-control" style="width: 239px;"> <span
										class="input-group-btn">
										<button class="btn default date-reset" type="button">
											<i class="fa fa-times"></i>
										</button>
									</span> <span class="input-group-btn">
										<button class="btn default date-set" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>
							</td>
						</tr>


					</table>
					
					
					<div class="modal-footer">
						<button class="btn blue pull-left" onclick="ondemand()">Collect Data</button>
					</div>
				
					<%-- </form>	 --%>

					<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
							style="width: 4%; height: 4%;">
					</div>
			
			
			
			
						<jsp:include page="locationFilter.jsp"/>
						<div class="col-md-3">
								<strong>MeterNo:</strong><div id="error" class="form-group">
								<select class="form-control select2me" id="meterno" name="meterno">
								<option value="">Select MeterNo</option>
								</select>
							</div>
						</div>
			
			
						<div class="col-md-1" style="padding-top: 15px;" id="kno">
							<button type="button" class="btn btn green" onclick="return transdatatest();">Track</button>
						</div>

					<%-- <c:if test="${not empty eventLength1 }">
								<font color="red">Data Not Found</font>
								</c:if> --%>


					<%-- <c:if test="${not empty ondedata }"> --%>

					<BR>
					<BR>
					
				
					<div class="portlet-body">
						<!-- <div class="table-scrollable">	 -->
						
					
						<table class="table table-striped table-bordered table-hover"
							id="sample_3">
							<thead>
								<tr>
									<th>S.no</th>
									<th>Meter Number</th>
									<th>Type</th>
									<th>OnDemand Time</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody id="tbodyid">
								<%-- <c:set var="count" value="1" scope="page" />								
									<c:forEach var="tl" items='${ondedata}'> 
										 	<tr id="sampel" class="odd gradeX">	
										 		 <td>${count}</td>
											    <td>${tl[0]}</td>
											    <td>${tl[1]}</td>
											    <td>${tl[2]}</td>
											    <td  onclick="view('${tl[0]}','${tl[1]}','${tl[3]}' )"><a href="#">view</a></td>
											   
											   </tr>
											<c:set var="count" value="${count + 1}" scope="page"/>   
										 </c:forEach>	 --%>

							</tbody>
						</table>
						
						<BR>
						<BR>
						
							
							
						
					<!--  <table class="table table-striped table-bordered table-hover"
							id="sample_2">
							<thead>
								<tr>
									<th>S.no</th>
									<th>Meter Number</th>
									<th>Type</th>
									<th>OnDemand Time</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody id="tbodyid1">
						

							</tbody>
						</table>   -->
						
												
						<div id='div_4' style="overflow-x: auto;">
							<table
								class="table table-striped table-bordered table-hover"
								id="sample_4" style="table-layout: auto;">
								<thead id="thid">
		
								</thead>
								<tbody id="tbid">
		
		
								</tbody>
							</table>
						</div> 
									
					</div>
			
				
				 
			</div>
		</div>
	</div>
</div>

</div>
<style>
#sample_4 th {
	padding-top: 12px;
	padding-bottom: 12px;
	text-align: center;
	background-color: #4b8df8;
	color: white;
	overflow: hidden;
	white-space: nowrap;
}

#sample_4 td {
	text-align: center;
	overflow: hidden;
	white-space: nowrap;
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
</style>

<script>
function dtconvert(val) {
	var datesp = val.split('-');
	var d = new Date(datesp[0]);
	var str = $.datepicker.formatDate('yy-mm-dd', d);
	var fstr = str + "T" + datesp[1] + ":00+0530";
	return fstr;
}


/*function showCircle(zone) {
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
		  			
	                var html = '';

	                html += "<select id='LFtown' name='LFtown'   onchange='showResultsbasedOntownCode (this.value)'class='form-control  input-medium'  type='text'><option value=''>Select </option><option value='%'>ALL</option>";

	                

	               
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



function showResultsbasedOntownCode(town){
//	alert(town);
		var zone = $('#LFzone').val();
		var circle = $('#LFcircle').val();
		
		   $.ajax({
		      	url:'./showmeteronBasisofTown',
		      	type:'GET',
		      	dataType:'json',
		      	asynch:false,
		      	cache:false,
		      	data : {
			      	zone:zone,
	      	        circle:circle,
		  			town:town
		  		},
		  		success : function(response1) {
			  	//	alert(response1);
		  			
	                var html = '';

	                html += "<select id='meterno' name='meterno' class='form-control select2'  type='text'><option value=''>Select </option> ";
	                    
	                for (var i = 0; i < response1.length; i++) {
	                    //var response=response1[i];
	                    
	                    html += "<option value='"+response1[i]+"'>"
	                            +response1[i]+ "</option>";
	                               }
	                html += "</select><span></span>";
	                $("#error").html(html);
	                $('#meterno').select2();
	                
	            }
		  	});
		  }*/
var refreshTab;
function ondemand() {

	$("#alertId").hide();
	var type = $("#demId").val();
	var mnum = $("#meterNum").val();
	var fromdm = $("#eventFromDate").val();
	var fromd = dtconvert(fromdm);
	var todm = $("#eventToDate").val();
	var tod = dtconvert(todm);
	var hesType = $("#hesType").val();
	var onDemTyp = $("#onDemTyp").val();
	var reqtype = $("input[name=reqtype]:checked").val();

	if (type == '0' && onDemTyp == '0') {
		bootbox.alert("Please select profile type.");
		return false;
	}
	if (mnum == '') {
		bootbox.alert("Please enter meter no.");
		return false;
	}
	if (type == '') {
		bootbox.alert("Please select profile type.");
		return false;
	}

	bootbox.confirm("Meter no :" + mnum + " will receive data from MDAS: "+ hesType + ", would you like to continue?",
					function(confirmed) {
						if (confirmed == true) {
							if (hesType == '3GCELL') {
								$("#imageee").show();
								$
										.ajax({

											type : "GET",
											url : './OnDemandPull',
											dataType : 'text',
											data : {
												fromDate : fromdm,
												toDate : todm,
												onDemTyp : onDemTyp,
												reqtype : reqtype,
												mIDOndem : mnum
											},
											async : false,
											success : function(response) {
												$("#imageee").show();

												if (response == "smsSuccess") {
													alert('SMS Successfully sent to pull Data.');
													$('#onDemandPull').modal('hide');
													$('#demandPullForm')[0].reset();
													$('#onDemTyp').val("").trigger('change');
													transdata();

												} else if (response == 'smsFail') {
													alert('SMS Not sent!');
												} else {
													alert(response);
												}

											}
										});
							} else {
								$("#imageee").show();
								$
										.ajax({
											type : "POST",
											url : "./MDASonDemandProfile/"+ type,
											data : {
												hesType : hesType,
												meterId : mnum,
												fromdate : fromd,
												todate : tod
											},
											dataType : "TEXT",
											success : function(response) {
												$("#imageee").hide();
												if (response == "NoData") {
													$("#alertId").remove();
													$("#taid").append('<div id="alertId" class="alert alert-success display-show" style="display: none"><button class="close" data-close="alert"></button><span style="color:red" id="spanId"></span></div>');
													$("#alertId").show();
													//document.getElementById("alertId").style.display = 'block';
													document.getElementById("spanId").innerHTML = "Server Down OR No Data Present For given meterId and Date";
													$("#taid").append('<div id="alertId" class="alert alert-danger  display-show" style="display: none"><button class="close" data-close="alert"></button><span style="color:red" id="spanId"></span></div>');
												} else if (response == "Succ") {
													$("#alertId").remove();
													$("#taid").append('<div id="alertId" class="alert  alert-success display-show" style="display: none"><button class="close" data-close="alert"></button><span style="color:Green" id="spanId"></span></div>');
													$("#alertId").show();
													//document.getElementById("alertId").style.display = 'block';
													document.getElementById("spanId").innerHTML = "Data Successfully Saved";

												}
												
												transdata();
												transdatatest();

											}

										});
							}
						}
					});
}



//setTimeout("location.reload(true);", 50000);
var refreshTab; 
function transdata() {

	var mnum = $("#meterNum").val();

	$("#tbodyid tr").remove();
	$.ajax({
		url : "./onDemandProfileAMITransData",
		type : 'POST',
		

		//dataType : 'text',
		data : {
			mIDOndem : mnum
		}, 
		success : function(response) {


			if (response != null && response.length > 0 || response==null && response.length>0) {

			var html = "";
			/*for (var i = 0; i < response.length; i++) {
				html += "<tr>";
				html += "<td>" + (i + 1) + "</td>";
				html += "<td>" + response[i][0] + "</td>";
				html += "<td>" + response[i][1] + "</td>";
				html += "<td>" + moment(response[i][2]).format('YYYY-MM-DD HH:mm:ss.SSS') + "</td>";
				html += "<td  onclick='view(this.id)' id='"+ response[i][0] + "," + response[i][1] + ","+ response[i][3] + "'><a href='#'>view</a></td>";
				html += "</tr>";

			}*/

			   for (var i = 0; i < response.length; i++) {
					var resp = response[i];
					var j = i + 1;
					html += "<tr>" 
						+ "<td>"+ j + "</td>"
						+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
						+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"

						+ "<td>"+ ((resp[2] == null) ? "": moment(resp[2]).format('YYYY-MM-DD HH:mm:ss')) + " </td>";
					html += "<td  onclick='view(this.id)' id='"+ response[i][0] + "," + response[i][1] + ","+ response[i][3] + "'><a href='#'>view</a></td>";
						
						+"</tr>";
				
		
					
				}
			$("#imageee").hide();
			clearTabledataContent('sample_3');
			
			$('#sample_3').dataTable().fnClearTable();
			$("#tbodyid").html(html);
			loadSearchAndFilter('sample_3');
			}  else{
				//window.location.reload();
				bootbox.alert("There is no data received from MDAS");
			}  
		},
		complete : function() {
			loadSearchAndFilter('sample_3');
		}
		
	});
	//location.reload();
	
}

var refreshTab; 
function transdatatest() {

	var zone =$('#LFzone').val();
	var circle =$('#LFcircle').val();
	var town =$('#LFtown').val();

	var mnum = $("#meterNum").val();

	$("#tbodyid1 tr").remove();
	$.ajax({
		url : "./onDemandProfileAMITransDataTest",
		type : "GET",
		

		//dataType : 'text',
		data : {
			zone:zone,
			circle:circle,
			town:town,
			mIDOndem : mnum
		}, 
		success : function(response) {


		
			 if (response != null && response.length > 0 || response==null && response.length>0) {

			var html = "";
	
			   for (var i = 0; i < response.length; i++) {
					var resp = response[i];
					var j = i + 1;
					html += "<tr>" 
						+ "<td>"+ j + "</td>"
						+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
						+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"

						+ "<td>"+ ((resp[2] == null) ? "": moment(resp[2]).format('YYYY-MM-DD HH:mm:ss')) + " </td>";
					html += "<td  onclick='view(this.id)' id='"+ response[i][0] + "," + response[i][1] + ","+ response[i][3] + "'><a href='#'>view</a></td>";
						
						+"</tr>";
				
		
					
				}
			$("#imageee").hide();
			clearTabledataContent('sample_2');
			$("#tbodyid1").html(html);
			}
			 else{
					//$("#sample_2").show();
					//window.location.reload();
					//bootbox.alert("There is no data received from MDAS");
				}
					
			  
		},
		complete : function() {
			loadSearchAndFilter('sample_2');
		}

	});
	
}
function clearTabledataContent(tableid) {
	//TO CLEAR THE TABLE DATA
	var oSettings = $('#' + tableid).dataTable().fnSettings();
	var iTotalRecords = oSettings.fnRecordsTotal();
	for (i = 0; i <= iTotalRecords; i++) {
		$('#' + tableid).dataTable().fnDeleteRow(0, null, true);
	}

}

function view(seqId) {
	var v = seqId.split(',');
	$.ajax({
		type : "GET",
		url : "./viewODRequest/" + v[0] + "/" + v[1] + "/" + v[2],

		success : function(response) {
			$("#thid").html("");
			$("#tbid").html("");
			var v = response[0][0];
			var v1 = response[1];
			var html = '<tr ">';
			
			for (var i = 0; i < v.length; i++) {
				html += "<th>" + v[i] + "</th>";
			}
			html += "</tr>";
			var html1 = '<tr>';
			for (var k = 0; k < v1.length; k++) {
				for (var j = 0; j < v.length; j++) {
					if (v1[k][j] == null) {
						html1 += "<td></td>";
					} else {
						html1 += "<td>" + v1[k][j] + "</td>";
					}

				}
			}

			html1 += "</tr>";
			$("#thid").html(html);
			$("#tbid").html(html1);
		}
	});

}
</script>

<script>
function hideDates(value) {

	if (value == 'INSTANTANEOUS' || value == "Instant")
	{

		/* $("#fromdateTS").hide();
		$("#nameFromDate").hide();
		$("#toDateTS").hide();
		$("#nameToDate").hide(); */

		$("#fromdateTS").show();
		$("#nameFromDate").show();
		$("#toDateTS").show();
		$("#nameToDate").show();

	} else {
		$("#fromdateTS").show();
		$("#nameFromDate").show();
		$("#toDateTS").show();
		$("#nameToDate").show();

	}

}

function getHes(meterNo) {
	var id = meterNo;
	if (id == "" || id == null) {
		bootbox.alert("Please enter meter no");
		return false;
	}

	$.ajax({

		type : "GET",
		url : "./getHESType/" + id,
		dataType : "text",
		success : function(response) {
			if (response == 'No HES type') {
				bootbox.alert("No MDAS defined for the entered Meter No");
				$("#meterNum").val('');
			} else {
				$("#hesType").val(response);
				if (response == '3GCELL') {
					$("#3gCellData").show();
					$("#otherHes").hide();
				} else {
					$("#3gCellData").hide();
					$("#otherHes").show();
				}

			}

		}
	});

}
</script>


