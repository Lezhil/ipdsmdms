<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="<c:url value='http://maps.google.com/maps/api/js?sensor=true'/>"  type="text/javascript" ></script>
<script src="<c:url value='/resources/assets/plugins/gmaps/gmaps.js'/>"  type="text/javascript" ></script>
<style>
.page-content .page-breadcrumb.breadcrumb>li>i {
	color: #fff;
}

.btn {
	padding: 7px 33px;
}

div.scrollmenu {
	background-color: #333;
	overflow: auto;
	white-space: nowrap;
	padding: 0px;
}

div.scrollmenu a {
	display: inline-block;
	color: white;
	text-align: center;
	padding: 10px;
	text-decoration: none;
}

div.scrollmenu a:hover {
	background-color: #777;
}

.dateHead {
	font-size: xx-small !important;
	padding: 0px !important;
	text-align: center !important;
	font-weight: normal !important; 
}
.dateData {
	padding: 0px !important; 
	height: 20px !important; 
	text-align: center !important;
}
.dateDataRed {
	padding: 0px !important;
	background: #e02222 !important;
	height: 20px !important; 
	text-align: center !important;
	color:white;
}

.dateDataGreen {
	padding: 0px !important;
	background: #3cc051 !important;
	height: 20px !important; 
	text-align: center !important;
	color:white;
}
.fa {
    font-size: 9px;
}

.noBorder{
border:0px ! important;
}
.largeFont{
 font-size: large !important;
 color: black;
 font-weight: bold !important;
 background: white !important;
 min-width: 176px;
 text-align: left;
}

.portlet.box.gray > .portlet-title {
    background-color: #5f5f5f;
}
.bold{
font-weight: bold;
}

.width140{
width: 122px;
}

.mainHead{
font-size: x-large ! important;
font-weight: bold ! important;
}
.mainHead2{
font-size: small ! important;
font-weight: bold ! important;
color: teal ! important;
}

.portfolio-info {   
    padding: 7px 15px;
    margin-bottom: 5px;
    text-transform: uppercase;
}
.portfolio-block{
margin-bottom:5px ! important;
}

</style>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&key=AIzaSyDFsv7MwN3q9GNl-kasQWAWqLtgAi1aaF4"></script>

<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {

						App.init();
						TableEditable.init();
						FormComponents.init();
						loadSearchAndFilter('sample_1');

						$('#SurveyAndInstallation,#MeterAllocationID')
								.addClass('start active ,selected');
						$(
								"#MDASSideBarContents,#MDMSideBarContents,#ADMINSideBarContents,#DATAEXCHsideBarContents")
								.removeClass('start active ,selected');
						$("#meterViewID").hide();
						$("#meterStatusViewId").hide();
						//allSurveyorList();
					});
</script>
<div class="page-content">
	<div class="row">
		<div class="col-md-12">

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Meter Issue/Return
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">




					<div class="btn-group">
						<button class="btn green" data-target="#stack1"
							data-toggle="modal" id="addData" value="addSingle"
							onclick="activeSurveyorList()">Meter Issue/Return</button>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<!--  <button class="btn blue" data-target="#stack1" data-toggle="modal"  id="addUserType" value="addBatch" onclick="clearMyForm(this.value)">
									      Meter Return
									    </button> -->
					</div>
					<br /> <br />
					<table style="width: 25%">
						<tbody>
							<tr>
								<td><select name="SortTypeID" id="SortTypeID"
									class="form-control select2me input-medium"
									onchange="sortView()">
										<option value="sel">Select</option>
										<option value="sw">Supervisor wise</option>
										<option value="fw">Feeder wise</option>
										<option value="dw">DT wise</option>
										<option value="cw">Consumer wise</option>
										<!-- <option value="dcuw">DCU wise</option> -->

								</select></td>
								<td id="tsurveyorSelId" style="display: none"><select
									name="surveyorSelId" id="surveyorSelId"
									class="form-control select2me input-medium">
								</select></td>
								<td id="tfeederWiseSelId" style="display: none"><select
									name="feederWiseSelId" id="feederWiseSelId"
									class="form-control select2me input-medium">
								</select></td>
								<td id="tdtWiseSelId" style="display: none"><select
									name="dtWiseSelId" id="dtWiseSelId"
									class="form-control select2me input-medium">
								</select></td>
								<td id="tconWiseSelId" style="display: none"><select
									name="conWiseSelId" id="conWiseSelId"
									class="form-control select2me input-medium">
								</select></td>
							 <td id="fromToDateID" ><div class="input-group input-large date-picker input-daterange"  data-date-format="dd-mm-yyyy">
												<input type="text" class="form-control" id="fromdateid" name="from">
												<span class="input-group-addon">to</span>
												<input type="text" class="form-control" id="todateid" name="to">
											</div></td> 
                            
								<td><button type="button" id="dataview" class="btn yellow"
										onclick="return surveyListPersonWise();" formmethod="post">
										<b>View</b>
									</button></td>
							</tr>
						</tbody>
					</table>

					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> -->
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

								<th>S No</th>
								<th>Supervisor Name</th>
								<th>Installation Time</th>
								<th>IMEI No</th>
								<th>Consumer No</th>
								<th>Images</th>
								<th>Quality</th>
                                <th>Review</th>
								<th>Consumer Name</th>
								<th>Address</th>
								<th>Mobile No</th>
								<th>DT No</th>
								<th>Pole No</th>
								
								<th>Old Meter No</th>
								<th>Connection Type</th>
								<!-- <th>Old Meter Make</th>
								<th>Old Meter MF</th>
								<th>Old CTRN</th>
								
								<th>Old CTRD</th> -->
								<th>Premise</th>
								<th>Latitude</th>
								<th>Longitude</th>
								<th>Old Meter Reading</th>
								
								<th>Old Meter kVah</th>
								<th>Old Meter kVa</th>
								<th>New Meter No</th>
								<th>New Meter Make</th>
								<th>New Meter Type</th>
								
								<!-- <th>New Meter MF</th>
								<th>New Meter CT Ratio</th> -->
								<th>New Meter Initial Reading</th>
								<th>New Meter kVah</th>
								<th>New Meter kVa</th>
								
								<th>Sticker No</th>
								<!-- <th>Quality</th>
                                <th>Review</th> -->

							</tr>
						</thead>
						<tbody id="meterDetailsId">



						</tbody>

					</table>

				</div>
			</div>
		</div>
	</div>
</div>

<div id="stack1" class="modal fade" role="dialog"
	aria-labelledby="myModalLabel10">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h4 class="modal-title">
					<span id="addMeterStackId"
						style="color: #474627; font-weight: bold;">Meter
						Issue/Return</span>
				</h4>
			</div>
			<span style="color: red; font-size: 16px;" id="accountNotExistMsg"></span>
			<div class="modal-body">
				<form action="" method="post" id="addMeterDetailsFormId"
					modelAttribute="addMeterDetailsFormId"
					commandName="addMeterDetailsFormId">
					<div class="row">
						<div class="col-md-6">
							<label>Issue/Return</label><span style="color: red">*</span> <select
								path="meter_connection_type" name="issueReturnId"
								id="issueReturnId" onchange="issueReturn()"
								class="form-control placeholder-no-fix">
								<option value="select">Select</option>
								<option value="ISSUED">Issue</option>
								<option value="INSTOCK">Return</option>
							</select>
						</div>

					</div>
					<br>

					<div class="row">
						<div class="col-md-6">
							<label>Surveyor Name</label><span style="color: red">*</span> <select
								path="meter_connection_type" name="surveyorNameId"
								id="surveyorNameId" class="form-control placeholder-no-fix">

							</select>
						</div>
						<div class="col-md-6">
							<label>Store Location</label><span style="color: red">*</span> <select
								path="meter_connection_type" name="storeLocationId"
								id="storeLocationId" onchange="instockMeterList()"
								class="form-control placeholder-no-fix">
								<option value="select">Select</option>
								<option value="BCITS">BCITS</option>
								<option value="APSPDCL">APSPDCL</option>
							</select>
						</div>
					</div>

					<!--  form-control placeholder-no-fix-->
					<br>
					<div class="row">
						<div class="col-md-6">
							<label>Issue Date</label><span style="color: red">*</span> <input
								class="form-control form-control-inline input-medium date-picker"
								size="16" id="iDate" type="text" value="" />

						</div>
						<div class="col-md-6 form-group">
							<label>Meter</label><span style="color: red">*</span>
							<div class="form-group">


								<div class="radio-list">
									<label class="radio-inline"> <input type="radio"
										name="optionsRadios" id="optionsRadios25" value="option1"
										checked> Multiple
									</label> <label class="radio-inline"> <input type="radio"
										name="optionsRadios" id="optionsRadios26" value="option2"
										checked> Sequence Based
									</label>

								</div>

							</div>
						</div>
					</div>

					<div class="row" id="smnEMNID">
						<div class="col-md-6 form-group">
							<label>Starting Meter Number</label><span style="color: red">*</span>
							<input type="text" class="form-control" id="smnId"
								placeholder="123456">
						</div>
						<div class="col-md-6">
							<label>Ending Meter Number</label><span style="color: red">*</span>
							<input type="text" class="form-control" id="emnId"
								placeholder="123456">
						</div>

					</div>

					<div class="row">
						<div class="col-md-6 form-group" id="meterViewID">
							<label>Meter</label><span style="color: red">*</span> <select
								name="meterId" id="meterId"
								class="form-control select2me input-medium" multiple="multiple">
							</select>
						</div>
						<div class="col-md-6" id="meterStatusViewId">
							<label>Meter Status</label><span style="color: red">*</span> <select
								path="meter_connection_type" name="surveyorNameId"
								id="meterStatusId" class="form-control placeholder-no-fix">
								<option value="Sel">Select</option>
								<option value="OK">OK</option>
								<option value="Damaged">Damaged</option>
							</select>
						</div>

					</div>

					<br>

					<div class="modal-footer">
						<button class="btn blue pull-right" id="addMeterBatchbtnId"
							type="button" value="addbatch" onclick="meterIssueDataSave()"
							hidden="true">ADD</button>
						<button type="button" data-dismiss="modal" class="btn">Cancel</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<div id="stack10" class="modal fade" tabindex="-1"
	data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog">
		<div class="modal-content" style="width: 200%; margin-left: -200px">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h4 class="modal-title" id="titleId">Feeder Images</h4>
			</div>
			<!-- <div class="modal-body">
									<div class="row">
										<div class="col-md-12" id="billedData">
											

										</div>
									</div>
								</div> -->
			<div class="portlet box purple"
				style="display: none; width: 97%; margin-left: 15px;" id="fimge">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-building"></i>Consumer
						Images&nbsp;&nbsp;&nbsp;&nbsp;
					</div>

					<!-- <div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="#portlet-config" data-toggle="modal" class="config"></a> <a
							href="javascript:;" class="reload"></a> <a href="javascript:;"
							class="remove"></a>
					</div> -->
				</div>
				<div class="portlet-body">
					<div class="table-responsive">
						<table class='table table-striped table-hover'>
							<tbody id="feederTable">
             

							</tbody>
						</table>

					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="popup_image" tabindex="-1" data-backdrop="static" data-keyboard="false">
		       <div class="modal-dialog" id="image">
		        <div class="modal-content" style="width:550px;height:660px">
		         <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal" aria-hidden="true" >
		           &times;
		          </button>
		          <h4><b>METER IMAGE</b></h4>
		          <div>
		          <img  id="rl2" src="./resources/assets/img/RotateLeft.jpg" onclick="rotateLeft('0');" style="margin-left:200px; width:10%"/>&nbsp;&nbsp;&nbsp;&nbsp;
		         <img  id="rr1" src="./resources/assets/img/RotateRight.jpg" onclick="rotateRight('0');" style="width:10%"/>
		         </div>
		         <div class="modal-body">
		          <div class="rotatecontrol" id="Imageview" >
		           <img id="tempImg" src="" />
		          </div>
		         </div>
		        </div>		        
		       </div>		       
      </div>
  </div>
  <div class="modal fade" id="basic" tabindex="-1" role="basic" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header blue">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<h4 class="modal-title">Review</h4>
										</div>
										<div class="modal-body">
											
											<table>
											<thead>
											<tr><th>Image Quality</th><th>
											<div id="reviewid" class="" ></div>
											<div class="form-body"><div class="form-group">
										
										<div class="col-md-12">
											<div class="radio-list">
												<label class="radio-inline">
												<input type="radio" name="optionsRadios1" id="optionsRadios251" value="G" checked> Good
												</label>
												<label class="radio-inline">
												<input type="radio" name="optionsRadios1" id="optionsRadios261" value="A" > Average
												</label>
												<label class="radio-inline">
												<input type="radio" name="optionsRadios1" id="optionsRadios271" value="B" > Bad
												</label>  
											</div>
										</div>
									</div></div></th></tr><tr><th>Remarks</th><th><textarea class="form-control" id="remarkTextId" rows="3"></textarea></th></tr></thead>
											</table>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn default" data-dismiss="modal">Close</button>
											<button type="button" class="btn green" onclick="reviewDataSave()">Submit</button>
										</div>
									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal-dialog -->
							</div>
<script>
	function sortView() {
		var v = $("#SortTypeID").val();
		if (v == 'sel') {

			$("#tsurveyorSelId").hide();
			$("#tfeederWiseSelId").hide();
			$("#tdtWiseSelId").hide();
			$("#tconWiseSelId").hide();
			$("#tdcuWiseSelId").hide();
		}
		if (v == 'sw') {
			allSurveyorList();
			$("#tsurveyorSelId").show();
			$("#tfeederWiseSelId").hide();
			$("#tdtWiseSelId").hide();
			$("#tconWiseSelId").hide();
			$("#tdcuWiseSelId").hide();
		}
		if (v == 'fw') {
			$("#tsurveyorSelId").hide();
			allfeedList();
			$("#tfeederWiseSelId").show();
			$("#tdtWiseSelId").hide();
			$("#tconWiseSelId").hide();
			$("#tdcuWiseSelId").hide();
		}
		if (v == 'dw') {
			$("#tsurveyorSelId").hide();
			$("#tfeederWiseSelId").hide();
			allDtList();
			$("#tdtWiseSelId").show();
			$("#tconWiseSelId").hide();
			$("#tdcuWiseSelId").hide();
		}
		if (v == 'cw') {
			$("#tsurveyorSelId").hide();
			$("#tfeederWiseSelId").hide();
			$("#tdtWiseSelId").hide();
			allconList();
			$("#tconWiseSelId").show();
			$("#tdcuWiseSelId").hide();
		}
		/* if (v == 'dcuw') {
			$("#tsurveyorSelId").hide();
			$("#tfeederWiseSelId").hide();
			$("#tdtWiseSelId").hide();
			$("#tconWiseSelId").hide();
			$("#tdcuWiseSelId").show();
		}
 */
	}
	function allSurveyorList() {
		$.ajax({
			url : './surveyorList',
			type : 'GET',
			success : function(res) {
				var html = '<option value="0">Select</option>';
				$.each(res, function(k, v) {
					html += '<option value='+v.suid+'>' + v.surveyorname
							+ '</option>'
				});
				$("#surveyorSelId").html(html);
				//instockMeterList();
			}
		});
	}
	function allfeedList() {
		$.ajax({
			url : './feederDetails',
			type : 'GET',
			success : function(res) {
				var html = '<option value="0">Select</option>';
				$.each(res, function(k, v) {
					html += '<option value='+v+'>' + v
							+ '</option>'
				});
				$("#feederWiseSelId").html(html);
				//instockMeterList();
			}
		});
	}
	function allDtList() {
		$.ajax({
			url : './dtDetails',
			type : 'GET',
			success : function(res) {
				var html = '<option value="0">Select</option>';
				$.each(res, function(k, v) {
					html += '<option value='+v+'>' + v
							+ '</option>'
				});
				$("#dtWiseSelId").html(html);
				//instockMeterList();
			}
		});
	}
	function allconList() {
		$.ajax({
			url : './consumerDetails',
			type : 'GET',
			success : function(res) {
				var html = '<option value="0">Select</option>';
				$.each(res, function(k, v) {
					html += '<option value='+v+'>' + v
							+ '</option>'
				});
				$("#conWiseSelId").html(html);
				//instockMeterList();
			}
		});
	}
	function activeSurveyorList() {
		$.ajax({
			url : './activeSurveyorList',
			type : 'GET',
			success : function(res) {
				var html = '<option value="0">Select</option>';
				$.each(res, function(k, v) {
					html += '<option value='+v[0]+'>' + v[1] + '</option>'
				});
				$("#surveyorNameId").html(html);
				//instockMeterList();
			}
		});
	}
	function instockMeterList() {
		var strLoc = $("#storeLocationId").val();
		var iortype = $("#issueReturnId").val();
		$.ajax({
			url : './inStockMeterList/' + strLoc + '/' + iortype,
			type : 'GET',
			success : function(res) {
				var html = '<option value="0">Select</option>';
				$.each(res, function(k, v) {
					html += '<option value='+v+'>' + v + '</option>'
				});
				$("#meterId").html(html);
				$("#meterId").select2();
			}
		});
	}

	function meterIssueDataSave() {
		var ml;
		var id = $("#iDate").val();
		var sn = $("#surveyorNameId").val();
		var strLoc = $("#storeLocationId").val();
		var type = $("#issueReturnId").val();
		var status = $("#meterStatusId").val();
		var smnId = $("#smnId").val();
		var emnId = $("#emnId").val();
		var radioValue = $("input[name='optionsRadios']:checked").val();
		if (radioValue == 'option1') {
			ml = $("#meterId").val();
		}
		if (radioValue == 'option2') {

			var ssn = $("#smnId").val();
			var esl = $("#emnId").val();
			var seqList = '';
			$
					.ajax({
						url : './meterSequenceVerification/' + strLoc + "/"
								+ ssn + "/" + esl + "/" + type,
						type : 'GET',
						async : false,
						success : function(res) {

							if ((res == 'Wrong Sequence Inputs')
									|| (res == 'Application allow maximum 100 meters')
									|| (res == 'Few Meters are not avialble in this store')) {
								bootbox.alert(res);
								return false;
							} else {
								//seqList= res;
								//var v=seqList.replace("'","");
								ml = res.split(",");
							}
						}
					});
		}

		if (status == 'Sel') {
			status = 'OK'
		}
		var jsons = {
			"surveyorID" : sn,
			"iDate" : id,
			"meters" : ml,
			"storeLoc" : strLoc,
			"type" : type,
			"status" : status
		};
		var metersf = JSON.stringify(jsons);
		$
				.ajax({
					url : './meterIssueDataSave',
					type : 'GET',
					dataType : 'json',
					async : false,
					data : {
						"metersf" : metersf
					},
					success : function(res) {
						if (res == 'Succ') {
							/*  $("#meterId").html("");  */
							$("#storeLocationId").html("");
							$("#storeLocationId")
									.html(
											'<option value="select">Select</option>									<option value="BCITS">BCITS</option>									<option value="APSPDCL">APSPDCL</option>');
							instockMeterList();
							/* $("#meterId option[value='']").attr('selected', true) */
							$('#stack1').modal('hide');
							bootbox.alert("Successfully Saved");

						} else {
							bootbox
									.alert("Insert data is wrong.Please verify and save once again");
						}
					}

				});

	}

	function surveyListPersonWise() {
		var fromdateid=$("#fromdateid").val();
		var todateid=$("#todateid").val();
		 if(fromdateid=="" || todateid=="" ){
            bootbox.alert("Please select from date and to date");
            return false;
			} 
		var url='';
		var selid=$("#SortTypeID").val();
		if(selid=='sel'){
			bootbox.alert("Please Select Any One Option");
			return false;
			}
		if(selid=='sw'){
			
			var v = $("#surveyorSelId").val();
			if(v=='0'){
				bootbox.alert("Please Select Supervisor Name");
				return false;
				}
          url='./surveyListPersonWise/'+v+'/'+fromdateid+'/'+todateid;
			}
if(selid=='fw'){
			
			var v = $("#feederWiseSelId").val();
			if(v=='0'){
				bootbox.alert("Please Select feeder No");
				return false;
				}
          url='./feederWise/' + v+'/'+fromdateid+'/'+todateid;
			}
if(selid=='dw'){
	
	var v = $("#dtWiseSelId").val();
	if(v=='0'){
		bootbox.alert("Please Select DT No");
		return false;
		}
  url='./dtWise/' + v+'/'+fromdateid+'/'+todateid;
	}
if(selid=='cw'){
	
	var v = $("#conWiseSelId").val();
	if(v=='0'){
		bootbox.alert("Please Select Consumer Number");
		return false;
		}
  url='./consumerWise/' + v+'/'+fromdateid+'/'+todateid;
	}
		
		$.ajax({
			url : url,
			type : 'GET',
			success : function(res) {
				var html = '';
				$.each(res, function(k, v) {
					
					html += '<tr>';
					html += "<td>" + (k + 1) + "</td>";
					html += "<td>" + v[0] + "</td>";
					html += "<td>" + v[1] + "</td>";
					html += "<td>" + v[2] + "</td>";
					html += "<td>" + v[3] + "</td>";
					html+="<td><button type='button' onclick='return viewByImage(this.id,"+v[3]+");' id='billedData"+v[3]+"' class='btn btn-default' style='margin-left: 10px;'><i class='fa glyphicon glyphicon-picture'></i>&nbsp;View Image</button></td>";
					if(v[30]==null){
						html += "<td>Review Pending</td>";
						}
					else{
						html += "<td>" + v[30] + "</td>";
						}
					html += "<td><a class='btn default' data-toggle='modal' href='#basic' id='"+v[3]+"#"+v[9]+"#"+v[21]+"' onclick='reviewid(this.id)'>Review</a></td>";

					html += "<td>" + v[4] + "</td>";
					html += "<td>" + v[5] + "</td>";
					if(v[6]==undefined || v[6]=='' ||v[6]==null ){
						html += "<td></td>";
						}
					else{
						html += "<td>" + v[6] + "</td>";
						}
					
					html += "<td>" + v[7] + "</td>";
					if(v[8]==undefined || v[8]=='' ||v[8]==null ){
						html += "<td></td>";
						}
					else{
						html += "<td>" + v[8] + "</td>";
						}
					html += "<td>" + v[9] + "</td>";
					 html += "<td>" + v[10] + "</td>";
					 /*html += "<td>" + v[11] + "</td>";
					html += "<td>" + v[12] + "</td>";
					html += "<td>" + v[13] + "</td>";
					html += "<td>" + v[14] + "</td>"; */
					html += "<td>" + v[15] + "</td>";
					html += "<td>" + v[16] + "</td>";
					html += "<td>" + v[17] + "</td>";
					html += "<td>" + v[18] + "</td>";
					if(v[19]==undefined || v[19]=='' ||v[19]==null ){
						html += "<td></td>";
						}
					else{
						html += "<td>" + v[19] + "</td>";
						}
					if(v[20]==undefined || v[20]=='' ||v[20]==null ){
						html += "<td></td>";
						}
					else{
						html += "</td>" + v[20] + "</td>";
						}
					html += "<td>" + v[21] + "</td>";
					html += "<td>" + v[22] + "</td>";
					html += "<td>" + v[23] + "</td>";
					/* html += "<td>" + v[24] + "</td>";
					html += "<td>" + v[25] + "</td>"; */
					html += "<td>" + v[26] + "</td>";
					if(v[27]==undefined || v[27]=='' ||v[27]==null ){
						html += "<td></td>";
						}
					else{
						html += "<td>" + v[27] + "</td>";
						}
					if(v[28]==undefined || v[28]=='' ||v[28]==null ){
						html += "<td></td>";
						}
					else{
						html += "<td>" + v[28] + "</td>";
						}
					html += "<td>" + v[29] + "</td>";
					/* if(v[30]==null){
						html += "<td>Review Pending</td>";
						}
					else{
						html += "<td>" + v[30] + "</td>";
						} */
					
					
/* 					html += "<td><a class='btn default' data-toggle='modal' href='#basic' id='"+v[3]+"#"+v[9]+"#"+v[21]+"' onclick='reviewid(this.id)'>Review</a></td>";
 */                    //html+="<button type='button' class='btn btn-info'>Review</button>"
					html += "</tr>";
				});
				clearTabledataContent('sample_1');
				$("#meterDetailsId").html(html);
				loadSearchAndFilter('sample_1');
			}
		});
	}

	$(document).ready(function() {
		$("#optionsRadios25").click(function() {
			var radioValue = $("input[name='optionsRadios']:checked").val();
			if (radioValue) {
				$("#smnEMNID").hide();
				$("#meterViewID").show();
			}
		});
		$("#optionsRadios26").click(function() {
			var radioValue = $("input[name='optionsRadios']:checked").val();
			if (radioValue) {
				$("#smnEMNID").show();
				$("#meterViewID").hide();
			}
		});

	});
	function issueReturn() {
		var v = $("#issueReturnId").val();
		if (v == 'ISSUED') {
			$("#meterStatusViewId").hide();
		}
		if (v == 'INSTOCK') {
			$("#meterStatusViewId").show();
		}

	}
 	function viewByImage(param, mtrNumber) {
		
		$
				.ajax({
					type : "GET",
					url : "./getViewOnImageMtrData/" + mtrNumber,
					dataType : "json",
					async : false,
					beforeSend : function() {
						$('#imageee').show();
					},
					complete : function() {
						$('#imageee').hide();
					},
					success : function(response) {
						$('#titleId').html('Consumer No- ' + mtrNumber);
						if (response != null) {
							var html = "";

							var rep = response;
							html = html
									+ "<tr><td><input type='hidden' id='first' value='"+rep[0]+"_Front'><img  border='0'  data-toggle='modal'  data-target='#popup_image'  class='magnify' src='./getFeederImages/"
									+ rep[0]
									+ "_Front'  onclick='viewDocument1();' height='100' width='100' /><figcaption>Old Meter Image</figcaption><figure></td>"
									+ "<td><input type='hidden' id='second' value='"+rep[0]+"_Left'><img border='0'  data-toggle='modal'  data-target='#popup_image'  class='magnify' src='./getFeederImages/"
									+ rep[0]
									+ "_Left' onclick='viewDocument2();' height='100' width='100' /><figcaption>House Image</figcaption><figure></td>"
									+ "<td><input type='hidden' id='third' value='"+rep[0]+"_Right'><img border='0'  data-toggle='modal'  data-target='#popup_image'  class='magnify' src='./getFeederImages/"
									+ rep[0]
									+ "_Right' onclick='viewDocument3();'  height='100' width='100'/><figcaption>New Meter Image</figcaption><figure></td>"
									//+ "<td><input type='hidden' id='fourth' value='"+rep[0]+"_Port'><img border='0'  data-toggle='modal'  data-target='#popup_image'  class='magnify' src='./getFeederImages/"+rep[0]+"_Port' onclick='viewDocument4();' height='100' width='100'/><figcaption>Port Image</figcaption><figure></td>"
									+ "<td><input type='hidden' id='sixth' value='"+rep[0]+"_TTL'><img border='0'  data-toggle='modal'  data-target='#popup_image'  class='magnify' src='./getFeederImages/"
									+ rep[0]
									+ "_TTL' onclick='viewDocument5();'  height='100' width='100'/><figcaption>Premise Image</figcaption><figure></td></tr>";

							$('#fimge').show();
							$('#feederTable').empty();
							$('#feederTable').html(html);

						} else {
							alert("No Data Found");
							$('#fimge').hide();
							$('#feederListId').hide();
						}
					}
				});
		$('#' + param).attr("data-toggle", "modal");
		$('#' + param).attr("data-target", "#stack10");
	} 
 	function viewDocument1()
 	{  
 	    $('#Imageview').empty(); 
 	    rotation=0;
 	    rotateRight();
 	    rotateLeft();
 	    var id=$('#first').val();
 		  var html="<img data-magnifyby='10'  height='400' width='500' class='magnify' src='./getFeederImages/"+id+"'/>";
 	    $('#Imageview').append(html);
 	    	
 	}

 	function viewDocument2()
 	{  
 	    $('#Imageview').empty(); 
 	    rotation=0;
 	    rotateRight();
 	    rotateLeft();
 	    var id=$('#second').val();
 		  var html="<img data-magnifyby='10'  height='400' width='500' class='magnify' src='./getFeederImages/"+id+"'/>";
 	    $('#Imageview').append(html);
 	    	
 	}

 	function viewDocument3()
 	{  
 	    $('#Imageview').empty(); 
 	    rotation=0;
 	    rotateRight();
 	    rotateLeft();
 	    var id=$('#third').val();
 		  var html="<img data-magnifyby='10'  height='400' width='500' class='magnify' src='./getFeederImages/"+id+"'/>";
 	    $('#Imageview').append(html);
 	    	
 	}

 	function viewDocument4()
 	{  
 	    $('#Imageview').empty(); 
 	    rotation=0;
 	    rotateRight();
 	    rotateLeft();
 	    var id=$('#fourth').val();
 		  var html="<img data-magnifyby='10'  height='400' width='500' class='magnify' src='./getFeederImages/"+id+"'/>";
 	    $('#Imageview').append(html);
 	    	
 	}
 	function viewDocument5()
 	{  
 	    $('#Imageview').empty(); 
 	    rotation=0;
 	    rotateRight();
 	    rotateLeft();
 	    var id=$('#sixth').val();
 	   
 		  var html="<img data-magnifyby='10'  height='400' width='500' class='magnify' src='./getFeederImages/"+id+"'/>";
 	    $('#Imageview').append(html);
 	    	
 	}

 	function rotateRight()
 	{
 		rotation =rotation+90;
 	    var rotate = "rotate(" + rotation + "deg)";
 	     var trans = "all 0.3s ease-out"; 
 	    $(".rotatecontrol").css({
 	        "-webkit-transform": rotate,
 	        "-moz-transform": rotate,
 	        "-o-transform": rotate,
 	        "msTransform": rotate,
 	        "transform": rotate,
 	        "-webkit-transition": trans,
 	        "-moz-transition": trans,
 	        "-o-transition": trans,
 	        "transition": trans
 	    });
 	    
 	}


 	function rotateLeft()
 	{
 		rotation =rotation-90;
 	    var rotate = "rotate(" + rotation + "deg)";
 	     var trans = "all 0.3s ease-out"; 
 	    $(".rotatecontrol").css({
 	        "-webkit-transform": rotate,
 	        "-moz-transform": rotate,
 	        "-o-transform": rotate,
 	        "msTransform": rotate,
 	        "transform": rotate,
 	        "-webkit-transition": trans,
 	        "-moz-transition": trans,
 	        "-o-transition": trans,
 	        "transition": trans
 	    });
 	}
 	function reviewid(val){
 	 	var v=$('#reviewid').attr('class');
 		$('#reviewid').removeClass(v);
 		$('#reviewid').addClass(val);
 		//$('#reviewid').html(val);
 		//$("#basic").modal("show");
 	 	}
	 	function reviewDataSave(){
          var v=$('#reviewid').attr('class');
           var rv = $("input[name='optionsRadios1']:checked").val();
          var rt=$("#remarkTextId").val();
          $.ajax({
            type:'get',
                url:'./reviewData',
                data:{
                	rid:v,
          qual:rv,
          remark:rt
                    },
          success:function(res){
              if(res=='succ'){
            	  $("#basic").modal("hide");
                  bootbox.alert("Successfully Review Saved ");
                  surveyListPersonWise();
                  }
              else{
            	  bootbox.alert("Review Not Saved. Please Try Once Again ");
            	 
                  }
              }
              });
		 	}
	 	
</script>

