<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>

<%
	if (session.getAttribute("username") == null || session.getAttribute("username") == null) {
%>
<script>
	window.location.href = "./?sessionVal=expired";
</script>
<%
	}
%>


<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						
						App.init();
						TableEditable.init();
						Tasks.initDashboardWidget();
						FormComponents.init();
						loadSearchAndFilter('sample1');
						TableManaged.init();
						formreset();

						$('#MDMSideBarContents,#staDtlsId,#townDetails,#mpmId').addClass('start active ,selected');
						$("#MDASSideBarContents,#dash-board,#360d-view,#buisnessRoleDetails,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n"
										+ "#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager")
								.removeClass('start active ,selected');
						loadSearchAndFilter("sample1");

						$('#circleId').change(function() {
							var dropVal = $(this).val();
							sessionStorage.setItem("SelectedItem", dropVal);
						});

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

						$("#toid").hide();
					});
</script>

<script>

$(document).ready(function() {
	getAllLocation('${officeCode}', '${officeType}');



});


function getAllLocation(officeCode, officeType) {
	//alert(officeCode+"--"+officeType);
$
		.ajax({
			type : 'GET',
			url : "./getAllLocationHiarchary",
			data : {
				officeCode : officeCode,
				officeType : officeType
			},
			async : false,
			cache : false,
			success : function(response) {
			//alert(response);
				var html = "";
				if (response != null) {
					if (officeType == "corporate") {

           //    alert("inside if");
						html+="<select id='LFzone' name='zone' onchange='showCircle(this.value)' class='form-control' type='text'><option value=''>Select Region</option><option value='%'>ALL</option>";
						for( var i=0;i<response.length;i++)
						{
							html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
						}
						html+="</select><span></span>";
						$("#zoneTd").html(html);
						$('#zone').select2();
						
						

				}

				  else if (officeType == "circle") {
						$('#circle').find('option').remove();


						html+="<select id='circle' name='circle' onchange='showCircle(this.value)' class='form-control' type='text'><option value=''>Select Circle</option>";
						for( var i=0;i<response.length;i++)
						{
							html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
						}
						html+="</select><span></span>";
						$("#circleTd").html(html);
						$('#circle').select2();
						
					}

				}

			}
		});
}


function showCircle(zone)
{  // alert("inside shocircle");
	//alert(zone);

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
    	{   //alert(response);
			var html='';
    		html+="<select id='LFcircle' name='LFcircle' class='form-control' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
			for( var i=0;i<response.length;i++)
			{
				html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
			}
			html+="</select><span></span>";
			$("#LFcircleTd").html(html);
			$('#LFcircle').select2();
    	}
	});
}


</script>
<div class="page-content">
 
	<%-- 	<c:if test="${results ne 'notDisplay'}">
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
						<i class="fa fa-cogs"></i>Bulk Edit(Town Details)				
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
					</div>
				</div>
				<div class="portlet-body">

					<form action="./uploadTownFile" id="uploadFile"
						enctype="multipart/form-data" method="post">

						<div class="form-group">
							<div class="row" style="margin-left: -1px;">
							  <div class="col-md-6" >
									 <div>
								       <label for="exampleInputFile1" >Only Upload xlsx File(<a href='#' id='townSample' onclick='downLoadSampleExcel()' >Download Sample-File</a>)</label> 
										<input type="file" id="excelfileUpload" onchange="ValidateSingleFile(this);" name="excelfileUpload"><br />
										
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
									  <li>User can able to update only GoLive Date,Technical Loss & Baseline Loss.</li>
									  <li>Technical Loss & Baseline Loss should be Number.</li>
									  <li>Date Should be in YYYY-MM-DD format.</li>
									  <li>Invalid format data will not accept to Update.</li>
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

	<div class="portlet box blue">

		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-edit"></i>Town Details
			</div>
		</div>

		<div class="portlet-body">
        <jsp:include page="locationFilter.jsp"/> 

			<div class="row" style="margin-left: -1px;">
				<%-- <div class="col-md-3">
					<label class="control-label">Region:</label>
					<div id="zoneTd" class="form-group">

						<select class="form-control select2me" name="zone" id="zone">
							<option value="">Select Region</option>
							<c:if test="${officeType eq 'discom'|| userType eq 'ADMIN'}">
								<option value="%">ALL</option>
							</c:if>
							<c:forEach var="elements" items="${zoneList}">
								<option value="${elements}">${elements}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-md-3">
					<label class="control-label">Circle:</label>
					<div id="circleTd" class="form-group">
						<select class="form-control select2me" id="circle" name="circle">
							<option value="">Select Circle</option>
							<!-- <option value="ALL">ALL</option> -->
							<c:forEach items="${circleList}" var="elements">
								<option value="${elements}">${elements}</option>
							</c:forEach>
						</select>
					</div>
				</div> --%>
				<div class="col-md-2" id="show" >
							<button type="button" id="viewTownDetails" onclick="viewTownDetails()"  name="viewChangeMeterData" class="btn yellow">
								<b>GET TOWN</b>
							</button>
				</div>
			</div>
			
				
					
			<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
					
			<div class="row">
				<div class="col-md-12">
					<div class="btn-group pull-right">
						<button class="btn dropdown-toggle" data-toggle="dropdown">
							Tools <i class="fa fa-angle-down"></i>
						</button>
						<ul class="dropdown-menu pull-right">
							<!-- <li id="print"><a href="#">Print</a></li> -->
							<li><a href="#" id=""
								onclick="exportPDF('sample1','TownDetails')">Export to PDF</a></li>
							 <li><a href="#" id="excelExport"
								onclick="exportToExcelMethod('sample1','Town Details')">Export to
									Excel</a></li> 
						</ul>
					</div>

					<table class="table table-striped table-hover table-bordered"
						id="sample1">

						<thead>
							<tr>
								<th>SL NO</th>
								<th>REGION CODE</th>
								<th>REGION</th>
								<th>CIRCLE CODE</th>
								<th>CIRCLE NAME</th>
								<th>TOWN CODE</th>
								<th>TOWN NAME</th>
								<th>TECHNICAL LOSS(%)</th>
								<th>BASELINE LOSS(%)</th>
								<th>GOLIVE DATE</th>
								<th>ENTRYBY</th>
								<th>ENTRYDATE</th>
								<th>UPDATEDBY</th>
								<th>UPDATED DATE</th>
								<c:if
									test="${officeType eq 'Office_Staff' ||  userType eq 'ADMIN'}">
									<th>SLD</th>
								</c:if>
							</tr>
						</thead>
						<tbody id="gettowndetails">
						</tbody>
					</table>
					

				</div>
			</div>
		</div>
	</div>

</div>



<div id="stack1" hidden="false" class="modal fade" role="dialog"
	id="popUp" aria-labelledby="myModalLabel10" aria-hidden="true">
	<div class="modal-dialog">

				
		<div class="modal-content">
		<div class="modal-header">
		 <div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-cogs"></i>Upload Town Wise SLD File</div>
							
						</div>
		
			
			<div class="portlet-body">
			<div class="modal-body">
			
			<form action="./uploadSLDFile" id="uploadSLDFile" enctype="multipart/form-data" method="post">
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label">Town Code</label> <input
								type="text" id="townid"
								class="form-control placeholder-no-fix"
								name="townid" maxlength="12"  readonly="readonly"></input>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label">Town Name</label> <input
								type="text" id="townname"
								class="form-control placeholder-no-fix"
								name="townname" readonly="readonly" maxlength="50"></input>
						</div>
					</div>											
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label">Technical Loss(In Percentage)</label>
							<!-- <span style="color: red" class="required">*</span> -->
							<input type="number" min='0' max='9' onkeypress="return (event.charCode == 8 || event.charCode == 0) ? null : event.charCode >= 48 && event.charCode <= 57" id="technicalLoss"
								class="form-control placeholder-no-fix"
								placeholder="Enter Technical Loss" name="technicalLoss"
								maxlength="12"></input>
						</div>
					</div>
					
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label">BaseLine Loss(In Percentage)</label>
							<!-- <span style="color: red" class="required">*</span> -->
							<input type="number" min='0' max='100' onkeypress="return (event.charCode == 8 || event.charCode == 0) ? null : event.charCode >= 48 && event.charCode <= 57" id="baseLineLoss"
								class="form-control placeholder-no-fix"
								placeholder="Enter BaseLine Loss" name="baseLineLoss"
								maxlength="12"></input>
						</div>
					</div>
					
					
					
				</div>
				<div class="row">
				
				<div class="col-md-6">
						<div class="form-group">
							<label class="control-label">Town GoLive Date</label>
							 <!-- <span style="color: red" class="required">*</span>  -->
									<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" data-date-end-date="0d" data-date-viewmode="years">
									<input type="text" class="form-control" placeholder="Select Date" name="goLiveDate" id="goLiveDate"  style="cursor: pointer">
									<span class="input-group-btn">
									<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
									</span>
									</div>
						</div>
					</div>
					<div class="col-md-6">
							<div class="form-group">
								<label class="control-label">Only Upload SLD pdf File</label>
								 <input type="file" id="fileUpload" onchange="ValidateSingleInput(this);" name="fileUpload"><br />
										
							</div>
						</div>
			   </div>

				
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn"
						onclick="return formreset()">Cancel</button>
					<button class="btn blue pull-right" id="addOption" type="submit"
						onclick="return validation()">Add/Modify</button>
				</div>
			</form>

			</div>
			</div>
			</div>
			</div>
		</div>
	</div>
</div>





<script>


	function viewTownDetails(){
		var region =$("#LFzone").val();
		var circle =$("#LFcircle").val();

		if (region == "") {
			bootbox.alert("Please Select Region");
			return false;
		}

		if (circle == "") {
			bootbox.alert("Please Select circle");
			return false;
		}

		 $("#imageee").show();
		$
		.ajax({

			url : './viewTownDetails',
			type : 'POST',
			data : {
				zone : region,
				circle : circle
			},
			dataType : 'json',
			success : function(response) {
			//	alert(response);
				 $("#imageee").hide();

					if (response != null && response.length > 0) {
					var html = "";
					for (var i = 0; i < response.length; i++) {
						var resp = response[i];
						var j = i + 1;
						html += "<tr>" 
								+ "<td>"+ j + "</td>"
								+ "<td>"+ ((resp[13] == null) ? "": resp[13]) + " </td>"
								+ "<td>"+ ((resp[14] == null) ? "": resp[14]) + " </td>"	
								+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"
								+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"
								+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
								+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"
								+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
								+ "<td>"+ ((resp[12] == null) ? "": resp[12]) + " </td>"
								+ "<td>"+ ((resp[8] == null) ? "": resp[8]) + " </td>"
								+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"
								+ "<td>"+ ((resp[5] == null) ? "": moment(resp[5]).format('YYYY-MM-DD HH:mm:ss')) + " </td>"
								+ "<td>"+ ((resp[10] == null) ? "": resp[10]) + " </td>"
								+ "<td>"+ ((resp[11] == null) ? "": moment(resp[11]).format('YYYY-MM-DD HH:mm:ss')) + " </td>";
					
							if(resp[9]!=null){
								html += "<td>"+ "<a class='btn btn-info' onclick='viewSLDData(\""+ resp[0]+ "\",\""+ resp[1]+ "\")' data-toggle='tooltip' title='Open PDF'  aria-label='View'><i class='fa fa-file-pdf-o' ></i> </a>&nbsp;<a class='btn btn-success' onclick='uploadSLDData(\""+ resp[0]+ "\",\""+ resp[1]+ "\",\""+ ((resp[7] == null) ? "": resp[7]) + "\",\""+ ((resp[8] == null) ? "": resp[8]) + "\",\""+ ((resp[12] == null) ? "": resp[12]) + "\")' data-toggle='modal'  data-target='#stack1' title='Add/Modify' aria-label='Add'> <i class='fa fa-upload'  ></i></a>&nbsp;<a class='btn btn-danger' onclick='deleteSLDData(\""+ resp[0]+ "\",\""+ resp[1]+ "\")' data-toggle='tooltip' title='Delete PDF' aria-label='Delete'><i class='fa fa-trash-o'></i></a>"+ "</td>"				
								
							} else{
								html += "<td>"+ "<a class='btn btn-success'  onclick='uploadSLDData(\""+ resp[0]+ "\",\""+ resp[1]+ "\",\""+ ((resp[7] == null) ? "": resp[7]) + "\",\""+ ((resp[8] == null) ? "": resp[8]) + "\",\""+ ((resp[12] == null) ? "": resp[12]) + "\")' data-toggle='modal'  data-target='#stack1' title='Add/Modify'  aria-label='Add'><i class='fa fa-upload' ></i></a>"+ "</td>"				
								
							}	
							html +="</tr>";
					}
					$('#sample1').dataTable().fnClearTable();
					$("#gettowndetails").html(html);
					loadSearchAndFilter('sample1');
				} else {
					bootbox.alert("No Relative Data Found.");
				}
			},
			complete : function() {
				 $("#imageee").hide();
				loadSearchAndFilter('sample1');
			}
		});

	}




	function uploadSLDData(townCode,townName,technicalLoss,goLiveDate,baselineLoss) {
		 $("#townid").val(townCode);
		 $("#townname").val(townName);
		 if(technicalLoss!=""){
		 	$("#technicalLoss").val(technicalLoss);
		 }
		 if(goLiveDate!=""){
			$("#goLiveDate").val(goLiveDate);
		 }
		 if(baselineLoss!=""){
			$("#baseLineLoss").val(baselineLoss);
		 }
		


	}

	function viewSLDData(townCode,townName){
		window.open("./viweSLDData/"+townCode);

		/* $
		.ajax({
			url : './viweSLDData',
			type : 'POST',
			data : {
				townCode : townCode,
				townName : townName
			},
			dataType : 'json',
			success : function(response) {

			if (response != null && response.length > 0) {
				
		       var file = new Blob([response], {type: 'application/pdf'});
		       var fileURL = URL.createObjectURL(file);
		       window.open(fileURL);
			} else {
					bootbox.alert("No PDF File Found.");
			}
		}); */
		
	}

	function deleteSLDData(townCode,townName){
		bootbox.confirm("Are you sure want to delete this record ?", function(result) {
			  if(result == true)
	            {
				  $("#imageee").show();
					$
					.ajax({
						url : './deleteSLDData',
						type : 'POST',
						data : {
							townCode : townCode,
							townName : townName
						},
						dataType : 'text',
						success : function(response) {
						 $("#imageee").hide();
								if(response=='success'){
									bootbox.alert("SLD File Deleted Succusfully");
									$('#sample1').dataTable().fnClearTable();
									$('#viewTownDetails').click();
									$('#viewTownDetails').click();
									loadSearchAndFilter('sample1');
			
								} else{
									bootbox.alert("OOPS! Something went wrong!!");
									$('#sample1').dataTable().fnClearTable();
									loadSearchAndFilter('sample1');
								}
							},
							error: function (e) {
								   $("#imageee").hide();
									bootbox.alert("OOPS! Something went wrong!!");
					        }
					});
	            }

		  });
	}


	function formreset(){

		
	    $('#townid').val('');
	    $('#townname').val('');
	    $('#technicalLoss').val('');
	    $('#goLiveDate').val('');
	    $('#fileUpload').val(''); 
	    $('#excelfileUpload').val(''); 
	    $('#baseLineLoss').val('');
	      
	}

	var _validFileExtensions = [".pdf"];    //,".jpg","jpeg",".png",".gif"
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
	            	bootbox.alert("Only PDF file is allowed to Upload");
	                oInput.value = "";
	                return false;
	            }
	        }
	    }
	    return true;
	}
	
	function exportPDF()
	{
		var region =$("#LFzone").val();
		var circle =$("#LFcircle").val();
	
		var reg="";
		var cir="";
		
		if(region=="%"){
			reg="ALL";
		}else{
			reg=region;
		}
		if(circle=="%"){
			cir="ALL";
		}else{
		    cir=circle;
		}
		
		//window.open("./towndetailspdf/"+reg+"/"+cir)
		window.location.href=("./towndetailspdf?reg="+reg+"&cir="+cir);
	}


	function downLoadSample(type){
		
		window.location.href="http://1.23.144.187:8102/downloads/sldreport/"+type+".xlsx";
	}

	function finalSubmit()
	{

		if(document.getElementById("excelfileUpload").value == "" || document.getElementById("excelfileUpload").value == null )
			  {
			    bootbox.alert(' Please Select xlsx file to upload');
		 	    return false;
			  }
		
	}


	var _validFileExtensions1 = [".xlsx"];    //,".jpg","jpeg",".png",".gif"
	function ValidateSingleFile(oInput) {
	    if (oInput.type == "file") {
	        var sFileName = oInput.value;
	         if (sFileName.length > 0) {
	            var blnValid = false;
	            for (var j = 0; j < _validFileExtensions1.length; j++) {
	                var sCurExtension = _validFileExtensions1[j];
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

	function exportToExcelMethod()
	{	   
		var region =$("#LFzone").val();
		var circle =$("#LFcircle").val();

		var reg="";
		var cir="";
		
		if(region=="%"){
			reg="ALL";
		}else{
			reg=region;
		}
		if(circle=="%"){
			cir="ALL";
		}else{
		    cir=circle;
		}
	
  		window.open("./exportToExcelTownDetailsData?reg="+reg+"&cir="+cir);
	}

	function downLoadSampleExcel()
	{
       var region =$("#LFzone").val();
		var circle =$("#LFcircle").val();

		var reg="";
		var cir="";
		
		if(region=="%"){
			reg="ALL";
		}else{
			reg=region;
		}
		if(circle=="%"){
			cir="ALL";
		}else{
		    cir=circle;
		}

		window.open("./exportToExcelTownDetails?reg="+reg+"&cir="+cir);
		
	}
	
</script>
