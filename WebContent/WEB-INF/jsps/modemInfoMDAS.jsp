<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


 
<link href="../assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" /> 
<link href="./resources/assets/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css" /> 

<link href="./resources/assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />

<script src="./resources/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.js" type="text/javascript"></script>
<script src="./resources/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<script src="./resources/assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js" type="text/javascript"></script>
<script src="./resources/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>

 
 <script type="text/javascript">
  $(function() {
    $('#datetimepicker1').datetimepicker({
      language: 'pt-BR'
    });
  });
</script>
<style>
.bootstrap-datetimepicker-widget{ z-index: 10050 !important;}
.btn {
    padding: ;
}

.btn-default {
    background-color: #008CBA;
    border: none;
    color: white;
   
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
}

.page-content .page-breadcrumb.breadcrumb>li>i {
	color: #fff;
}
.icon-chevron-up{
  background-image: url("https://static.pexels.com/photos/15239/flower-roses-red-roses-bloom.jpg");
}
.btn {
	padding: 7px 33px;
}

.badge {
	font-size: 14px !important;
	font-weight: 306;
	text-align: center;
	height: 20px;
	padding: 3px 6px 3px 6px;
	-webkit-border-radius: 12px !important;
	-moz-border-radius: 12px !important;
	border-radius: 12px !important;
	text-shadow: none !important;
	text-align: center;
	vertical-align: middle;
}

.label, .badge {
	font-weight: 351;
	text-shadow: aqua !important;
}
</style>
<script>
	$(".page-content").ready(function() {
					

						if ('${showTotalModems.size()}' > 0) {
							$('#totalFeeder').show();
						}
						if ('${showWorkingsModems.size()}' > 0) {
							$('#workingFeeder').show();
						}
						if ('${showNotWorkingsModems.size()}' > 0) {
							$('#notWorkingFeeder').show();
						}
						App.init();
						 FormComponents.init();
						Index.initMiniCharts();
						TableEditable.init();
						$('#MDASSideBarContents,#dash-board2').addClass('start active ,selected');
						$(
								'#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
								.removeClass('start active ,selected');

					});
</script>

<!--  -->

<div class="page-content" style="background-color: white;">
	<label style="color: navy; font-size: 24px; font-weight: bold; padding-top: 5px;">Modem Information </label>
	<div>
		<div class="row" style="margin-bottom: 10px;">
		
			<div class="col-sm-7" >
				<!--  mark-->

				<table class="table ">

					<tbody>

						<tr class="success">
							<td style="font-size: 13px; width: 150px;">Modem No</td>
							<td id="modemNoID"style="color: #000000; font-size: 18px; font-weight: bold;"> ${MODEM} </td>
						</tr>

						 <tr class="active">
							<td style="font-size: 13px">Version</td>
							<td id="totalFeeder" style="color: #000000; font-size: 18px; font-weight: bold;">3.6</td>
						</tr>

						<tr class="warning">
							<td style="font-size: 13px">Meter No</td>
							<td id="totalFeeder" onclick="return mtrDetails('${METERNO}');" style="color: blue; text-decoration: underline; font-size: 18px; font-weight: bold;">${METERNO}</td>
						</tr>

						<tr class="danger">
							<td style="font-size: 13px">Sim No</td>
							<td id="totalFeeder" style="color: #000000; font-size: 18px; font-weight: bold;">${simno}</td>
						</tr>

						<tr class="success">
							<td style="font-size: 13px; width: 100px;">Sub Station</td>
							<td id="totalFeeder" style="color: #000000; font-size: 18px; white-space: nowrap; font-weight: bold;">${substation}</td>
						</tr>
						
						 <tr class="active">
							<td style="font-size: 13px">Acc No</td>
							<td style="color: #000000; font-size: 18px; font-weight: bold;">${accno}</td>
						</tr>

						<tr class="warning">
							<td style="font-size: 13px">Consumer Name</td>
							<td style="color: #000000;  font-size: 18px; font-weight: bold;">${cname}</td>
						</tr>
					</tbody>
				</table>




			</div>
			<div class="col-sm-5" style="border: 1px solid purple; background: white; box-shadow: 2px 2px 2px #bfbfbf; float: right;"  >

					<table class="table ">

						<tbody>
						 
								<tr>
								<td style="font-size: 13px"><i class="fa fa-adjust"></i>&nbsp;Meter Status</td>
								<td style="font-size: 18px">
								
								<c:choose>
									<c:when test="${fn:contains(status, 'Active')}">
									<span class="label label-sm label-success">${status}</span>
									</c:when>
									<c:otherwise>
									<span class="label label-sm label-danger">${status}</span>
									</c:otherwise>
								</c:choose> 
								 
								
								</td>
							</tr>
								<tr>
								<td style="font-size: 13px"><i class="fa fa-clock-o"></i>&nbsp;Last Communicated</td>
								<td><span class="">${last_communication}</span></td>
							</tr>
							<tr>
								<td style="font-size: 13px"><i class="fa fa-signal"></i>&nbsp;Signal Strength</td>
								<td><span class="badge badge-info">${signal}</span></td>
							</tr>

							<tr>
								<td style="font-size: 13px"><i class="fa fa-fire"></i>&nbsp;Temperature</td>
								<td><span class="badge badge-danger">${temperature}</span></td>
							</tr>
							<tr>

								<td style="font-size: 13px"><i class="fa fa-exchange"></i>&nbsp;GPRS Fail Count</td>
								<td><span class="badge badge-info">${gprs_conn_fail_count}</span></td>
							</tr>
							<tr>

								<td style="font-size: 13px"><i class="fa fa-unlink"></i>&nbsp;Meter Connection Fail Count</td>
								<td><span class="badge badge-warning">${meter_conn_fail_count}</span></td>
							</tr>
						</tbody>
					</table>


			</div>

		</div>


	</div>

	

<div class="clearfix">
	<h4 class="block">Manage Modem</h4>
	<a onclick="return toClearChangeIP();" data-toggle="modal" data-target="#changeIP" class="btn default red-stripe">Change IP</a>
	<a onclick="return toClearChangePort();" data-toggle="modal" data-target="#ChangePort" class="btn default blue-stripe">Change Port</a>           
	<a data-toggle="modal" data-target="#setFre" class="btn default green-stripe">Set	Frequency</a>
	<a data-toggle="modal" data-target="#restMod" class="btn default yellow-stripe">Restart</a>
	<a data-toggle="modal" data-target="#changeMet" class="btn default purple-stripe">Change Meter</a>
	<a data-toggle="modal" data-target="#onDemandPull" class="btn default green-stripe">On Demand Pull</a> 
	<a data-toggle="modal" data-target="#scan" class="btn default red-stripe">Scan</a>
 </div>





</div>


		<!-- To Set Frequency 		-->
		<div id="setFre" class="modal fade" style="margin: 0 auto;"
			tabindex="-1" data-keyboard="false">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">

						<h4 class="modal-title" style="text-align: center;">
							<span
								style="font-size: 24px; font-style: normal; font-weight: bold;">Set
								Frequency
							</span>
						</h4>
					</div>
					<div class="modal-body">
							<form action='./changeFrequency' method='post'>


						<div class="col-sm-6" style="padding-left: 33px">

							<table class="table ">

								<tbody>

									<tr>
										<th><label
											style="color: #181616; font-size: 14px; font-weight: normal; padding-top: 5px;">Select
												the type </label></th>
									</tr>



									<tr>
										<td><select id="typeFrq"
											class="form-control select2me input-medium: col-xs-12"
											name="typeFrq">
												<option></option>
												<option id="instantTime">Instant Time</option>
												<option id="billingTime">Billing Time</option>
												<option id="loadTime">Load Time</option>
												<option id="eventTime">Event Time</option>
										</select></td>
									</tr>

								</tbody>
							</table>
						</div>

					<div > <input type="text"  value="${MODEM}" name="modemID" hidden="false"></div>
						<div class="col-sm-6" style="padding-left: 33px">
							<table class="table ">

								<tbody>

									<tr>
										<th><label
											style="color: #181616; font-size: 14px; font-weight: normal; padding-top: 5px;">Select
												the interval </label></th>
									</tr>
									<tr>
										<td><select id="frq"
											class="form-control select2me input-medium: col-xs-12"
											name="frq">
												<option></option>
												<option id="">24hrs</option>
												<option>1hrs</option>
												<option>30 mins</option>
												<option>15 mins</option>
										</select></td>
									</tr>

								</tbody>
							</table>
						</div>
						<p>&nbsp</p>
						<div class="text">
						
										<button type="button" data-dismiss="modal"
											class="btn blue"style="padding: 10px;margin-left: 20px; margin-right: 20px;">Cancel</button>
								
										<button class="btn blue"  style="padding: 10px; margin-left: 20px;margin-right: 20px;" value="frqmsgVal" name="frqMsg">Set frequency by MQTT</button>
									
								
										<button class="btn blue" style="padding: 10px; margin-left: 20px;" value="frqSmsVal" name="frqSms">Set frequency by SMS</button>
								</div>
						</form>
					</div>
				</div>
			</div>
</div>

       <!-- To changeIP the  -->
      <div id="changeIP" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">

					<h4 class="modal-title" style="text-align: center;">
						<span
							style="font-size: 24px; font-style: normal;; font-weight: bold;">Change	Ip 
						</span>
					</h4>
					<br />
					<div>
						<lable style="font-size:18px;font-weight: oblique">Enter
						New Ip</lable>
						<input class="form-control" type="text" name="rev_charge"
							id="ChangeIptxt" autocomplete="off" style="width: 206px;">
					</div>
					<p>&nbsp</p>
					
					 <div class="text">
						<button type="button" data-dismiss="modal"
										class="btn blue" style="padding:10px;margin-right:30px"	>Cancel</button>
						<button type="button" style="padding:10px;margin-left:30px;margin-right:30px" data-dismiss="modal" class="btn blue"
							onclick="changeIP('mqtt')">Save changes by MQTT</button>
							
							
						<button type="button" style="padding:10px;margin-left:30px" data-dismiss="modal" class="btn blue"
							onclick="changeIP('sms')">Save changes by SMS</button>
							
							</div>
							
					</div>
				</div>
			</div>
		</div>


	<!-- To Xhange the port -->
	<div id="ChangePort" class="modal fade" style="margin: 0 auto;"
	
	
		tabindex="-1" data-keyboard="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">

					<h4 class="modal-title" style="text-align: center;">
						<span
							style="font-size: 24px; font-style: normal;; font-weight: bold;">Change
							Port</font>
						</span>
					</h4>
					<br />
					<div>
						<lable style="font-size:18px;font-weight: oblique">Enter
						New Port</lable>
						<input class="form-control" type="text" name="rev_charge"
							id="changePortTxt" style="width: 206px;">
					</div>
					<p>&nbsp</p>
					<div class="text">
						<button type="button" data-dismiss="modal"
											class="btn blue"style="
																	    padding-right: 32;
																	    padding-left: 32px;
																	    margin-right: 20px;
																	    ">Cancel</button>
						<button type="button" style="padding:10px;margin-right: 20px;"data-dismiss="modal" class="btn blue"
							onclick="changePort('mqtt')">Save changes by MQTT</button>
							<button type="button" data-dismiss="modal" class="btn blue"style="padding:10px;margin-left: 20px;"
							onclick="changePort('sms')">Save changes by SMS</button>
							</div>
						</div>
			</div>
		</div>
	</div>


	<!-- To Restart Modem -->
	<div id="restMod" class="modal fade" style="margin: 0 auto;"
		tabindex="-1" data-keyboard="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">

					<h4 class="modal-title" style="text-align: center;">
						<span
							style="font-size: 24px; font-style: normal;; font-weight: bold;">Restart
							Modem </font>
						</span>
					</h4>
					<br />
					<div>
						<lable style="font-size:18px;font-weight: oblique">Are you
						sure to restart the modem (${MODEM}) </lable>
					</div>
					<p>&nbsp</p>
					<div class="text">
						<button type="button" data-dismiss="modal"
											class="btn blue"style=" margin-right:10px;padding:10px;">Cancel</button>
						<button type="button" data-dismiss="modal" class="btn blue"
							style=" margin-right:10px;padding:10px;"onclick="restartModem('mqtt')">Save changes by MQTT</button>
							<button type="button" data-dismiss="modal" class="btn blue"style=" margin-right:10px;padding:10px;"
							onclick="restartModem('sms')">Save changes by SMS</button>
							</div>
					
							
					
				</div>
			</div>
		</div>
	</div>


	<!-- To change Meter -->
	<div id="changeMet" class="modal fade" style="margin: 0 auto;"
		tabindex="-1" data-keyboard="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">

					<h4 class="modal-title" style="text-align: center;">
						<span
							style="font-size: 24px; font-style: normal;; font-weight: bold;">ChangeMeter</font></span>
					</h4>
					<br />
					<div>
						<lable style="font-size:18px;font-weight: oblique">Are you
						sure to send  change meter command to the modem (${MODEM}) </lable>
					</div>
					<p>&nbsp</p>
							<div class="text">
										<button type="button" style="padding:10px;margin:10px" data-dismiss="modal"
											class="btn blue">Cancel</button>
					
										<button class="btn blue" style="padding:10px;margin:20px"  onclick="changeMeter('mqtt')">Change meter by MQTT</button>
									
								
										<button class="btn blue" style="padding:10px;margin:px" onclick="changeMeter('sms')">Change meter by SMS</button>
								</div>
							
							
							
					
					
				</div>
			</div>
		</div>
	</div>

<!-- TO On Demand Pull -->
<div id="onDemandPull" class="modal fade" style="margin: 0 auto;"
	tabindex="-1" data-keyboard="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">

				<h4 class="modal-title" style="text-align: center;">
					<span
						style="font-size: 24px; font-style: normal; font-weight: bold;">ON
						DEMAND PULL </span>
				</h4>
			</div>
			<div class="modal-body">
				<form action='#' method='post' id="demandPullForm">

					
					<div class="input-group" style="margin-left: 20px">
							<div class="icheck-inline">
								<label> <input type="radio" name="reqtype" value="sms" class="icheck" checked>SMS</label> 
								<label> <input type="radio" name="reqtype" value="mqtt"  class="icheck"> MQTT</label> 
							</div>
						</div>
					
					<div class="col-sm-6">


						


						<table class="table ">

							<tbody>
								
								<tr>
									<th><label
										style="color: #181616; font-size: 14px; font-weight: normal; padding-top: 5px;">Select
											the type </label></th>
								</tr>

								<tr>
									<td><select id="onDemTyp" class="form-control select2me input-medium: col-xs-12" name="onDemTyp" >
											<option value="" disabled="disabled" selected="selected">Select</option>
											<option value="Instant">Instant</option>
											<option value="Billing">Billing</option>
											<option value="Load">Load</option>
											<option value="Event">Event</option>
									</select></td>
								</tr>

							</tbody>
						</table>
					</div>


					<!-- <div class="col-sm-6" style="padding-left: 33px"> -->
					<table class="table ">

						<tbody>

							<tr>
								<th><label
									style="color: #181616; font-size: 14px; font-weight: normal; padding-top: 5px; margin-left: 15px;">Select
										the interval time </label></th>
							</tr>
							<tr>
								<td>

									<div class="col-md-3">
										<!--           <div class="form-group">
										<label class="control-label ">Date Range</label>
										<div class="col-md-4">
											<div class="input-group input-large date-picker input-daterange" data-date="10/11/2012" data-date-format="mm/dd/yyyy">
												<input type="text" class="form-control" name="from">
												<span class="input-group-addon">to</span>
												<input type="text" class="form-control" name="to">
											</div>
											/input-group
											
										</div>
									</div> -->
										<!-- <div>
											<lable style="font-size:18px;font-weight: oblique">Enter
											Date</lable>
											From<input class="form-control" type="text" name="fromDate"
												autocomplete="off" style="width: 206px;"> To<input
												class="form-control" type="text" name="toDate"
												autocomplete="off" style="width: 206px;">
										</div> -->
										<div>
											<lable style="font-size:18px;font-weight: oblique">Enter
											Date</lable>
											From
											 <input type="text" class="form-control date form_datetime form_datetime bs-datetime" style="width: 206px;" name="fromDate" id="fromDate">
											 To
											  <input type="text" class="form-control date form_datetime form_datetime bs-datetime" style="width: 206px;" name="toDate" id="toDate">
										</div>
										
										<!-- <div>
                                            
                                                <input type="text" class="form-control timepicker timepicker-24">
                                                
                                           
                                         </div> -->
                                         
                                         <!-- <div>
                                             
                                                 <input type="text" class="form-control date form_datetime form_datetime bs-datetime" style="width: 206px;">
                                                
                                             
                                         </div> -->
                                         
									</div>

								</td>
							</tr>

						</tbody>
					</table>
					<!-- </div> -->
					<div>
						<input type="text" value="${MODEM}" name="mIDOndem" id="mIDOndem" hidden="false">
					</div>
					<div class="text">

						<button type="button" data-dismiss="modal" class="btn blue" style="margin-left: 20px;" onclick="resetForm()">Cancel</button>
						
						<button type="button" class="btn blue" style="margin: 10px" onclick="return ondemandPull()">SUBMIT</button>
						
						<!-- <button class="btn blue" style="padding: 10px; margin: 10px"
							value="ODmsgVal" name="ODMsg">Set OnDemad by MQTT</button>


						<button class="btn blue" style="padding: 10px; margin: 10px"
							value="ODSmsVal" name="ODSms">Set OnDemad by SMS</button> -->
					</div>
				</form>
			</div>
		</div>
	</div>
</div>



<!--  To scan the meter-->
	<div id="scan" class="modal fade" style="margin: 0 auto;" tabindex="-1"
		data-keyboard="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">

					<h4 class="modal-title" style="text-align: center;">
						<span
							style="font-size: 24px; font-style: normal;; font-weight: bold;">Scan
							Meter</font>
						</span>
					</h4>
					<br />
					<div>
						<lable style="font-size:18px;font-weight: oblique">Are you
						sure to scan the Modem(${MODEM})</lable>
					</div>
					<p>&nbsp</p>
					<div class="text">
						<button type="button" style="padding:10px;margin-right: 10px"data-dismiss="modal"
											class="btn blue" ;">Cancel</button>
																	    
														    
						<button class="btn blue"   style="padding:10px;margin-right: 10px"onclick="scanModem('mqtt')">Change meter by MQTT</button>
									
						<button class="btn blue"  style="padding:10px;"onclick="scanModem('sms')">Change meter by SMS</button>											    
						
						
																
					</div>
				</div>
			</div>
		</div>
	</div>











<style>
table thead>tr>th, .table tbody>tr>th, .table tfoot>tr>th, .table thead>tr>td,
	.table tbody>tr>td, .table tfoot>tr>td {
	border-top: none;
}
</style>

<script type="text/javascript">
	var modemNo = '${MODEM}';
	function scanModem(via_type) {


		$.ajax({

			url : './scanModem' + '/' + modemNo+'/'+via_type+'/'+'${username}',

			data : "",
			type : "GET",
			dataType : ' text',
			async : false,
			success : function(response) {

				if (response == "SUCCESS") {
					alert('Scannig was success');
				} else {
					alert('Scannig was failed');
				}

			}
		});
	}

	function changeIP(x) {
	
		var IP = $('#ChangeIptxt').val();
		var via_type=x;
		var patt = /[.]/g;
	   /*  var result = IP.match(patt);
	 		if(result.length!=3)
			{
			alert('enter a valid IP');
			}
		else
			{ */
				
				$.ajax({

				url : './changeIP' + '/' + IP + '/' + modemNo+'/'+via_type+'/'+'${username}',

				data : "",
				type : "GET",
				dataType : 'text',
				async : false,
				success : function(response) {
				alert(response);
				if (response == 'SUCCESS') 
				{
					alert('IP change was success');
				} else 
				{
					alert(response);
					alert('IP change was failed');
				}

									}
					});
			/* } */
		
	}

	function changePort(via_type) 
	{
		
		var port = $('#changePortTxt').val();
		if($.isNumeric(port)) 
		{
			
			$.ajax({

				url : './changePort' + '/' + port + '/' + modemNo+'/'+via_type+'/'+'${username}',

				data : "",
				type : "GET",
				dataType : 'text',
				async : false,
				success : function(response) {
				alert(response);
					if (response == 'SUCCESS') {
						alert('Port changed successfully');
					} else {
						alert('Port change was failed');
					}

				}
			});
		}
		else
			{
		alert('Enter a valid port');
			}
		}

	function changeFrequency() {

		
	}

	function restartModem(via_type) {
		
		$.ajax({
			
			url : './restartModem' + '/' + modemNo+'/'+via_type+'/'+'${username}',

			data : "",
			type : "GET",
			dataType : 'text',
			async : false,
			success : function(response) {

				if (response == "SUCCESS") {
					alert('Modem restart command send successfully');
				} else {
					alert('Modem restart command sending was failed');
				}

			}
		});
	}
	
	function changeMeter(via_type){
		
	$.ajax({
		          
			url : './changeMeter' + '/' + modemNo+'/'+via_type+'/'+'${username}',

			data : "",
			type : "GET",
			dataType : 'text',
			async : false,
			success : function(response) {

				if (response == "SUCCESS") {
					alert('Meter Change command send successfully');
				} else {
					alert('Meter Change command sending was failed');
				}

			}
		});

		
	}

	function toClearChangeIP() {

		$('#ChangeIptxt').val('');

	}

	function toClearChangePort() {

		$('#changePortTxt').val('');
	}

	function toClearSetFrequency() {

		$('#ChangeIptxt').val('');
	}
	function mtrDetails(mtrNo)
	{ 
		 window.location.href="./viewFeederMeterInfoMDAS?mtrno=" + mtrNo;
	}
	
	
	
	
	
	
function ondemandPull() {
		var fromDate=$('#fromDate').val();
		var toDate=$('#toDate').val();
		var onDemTyp=$('#onDemTyp').val();
		var mIDOndem=$('#mIDOndem').val();
		var reqtype=$("input[name=reqtype]").val();
		
		if(onDemTyp==null ||onDemTyp==''){
			alert('Please Select Data Type');
			return false;
		} else if(fromDate==null ||fromDate==''){
			alert('Please Select From Date.');
			return false;
		} else if(toDate==null ||toDate==''){
			alert('Please Select From Date.');
			return false;
		} else{
			/* var str=$('#demandPullForm').serialize(); */
			
			$.ajax({
				
				url : './OnDemandPull',
				type : "POST",
				dataType : 'text',
				data : {
					fromDate : fromDate,
					toDate : toDate,
					onDemTyp : onDemTyp,
					reqtype : reqtype,
					mIDOndem : mIDOndem,
				},
				async : false,
				success : function(response) {

					
					if (response == "smsSuccess") {
						alert('SMS Successfully sent to pull Data.');
						$('#onDemandPull').modal('hide');
						$('#demandPullForm')[0].reset();
						$('#onDemTyp').val("").trigger('change');
					} else if(response == 'smsFail') {
						alert('SMS Not sent!');
					} else{
						alert(response);
					}

				}
			});
			
			
		}
				
		
	}
	
	
	function resetForm() {
		$('#demandPullForm')[0].reset();
		$('#onDemTyp').val("").trigger('change');
	}
	
</script>
