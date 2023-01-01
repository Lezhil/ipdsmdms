<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<%@include file="headerFiles.jsp" %>	
<script>

$(".page-content").ready(
		function() {
			App.init();
			TableEditable.init();
			FormComponents.init();
			loadSearchAndFilter('sample_1');
			
		});
</script>
<script type="text/javascript">
var mtrNum;
function mtrDetails(mtrNo)
{
	mtrNum=mtrNo;
	window.location.href="./viewFeederMeterInfoMDAS?mtrno=" + mtrNo;
	 /* window.open("./viewFeederMeterInfo?mtrno="+ mtrNo,"_blank"); */
}


function eventDetails()
{
	alert(mtrNum);
	var mtrNo=mtrNum;
	$.ajax({
    	url : './getEventData/'+mtrNo,
    	type:'GET',
    	dataType:'json',
    	asynch:false,
    	cache:false,
    	success:function(data)
    	{
    		var html="";
   		 	for(var i=0;i<data.length; i++)
			{
				var resp=data[i];
				 html+="<tr>"
						+" <td>"+resp[0]+"</td>"
						+" <td>"+resp[1]+"</td>"
						+" <td>"+resp[2]+"</td>"
						+" <td>"+resp[3]+"</td>"
						+" <td>"+resp[4]+"</td>"
						+" <td>"+resp[5]+"</td>"
						+" <td>"+resp[6]+"</td>"
						+" <td>"+resp[7]+"</td>"
						+" <td>"+resp[8]+"</td>"
						+" <td>"+resp[9]+"</td>"
						+" <td>"+resp[10]+"</td>"
						+" <td>"+resp[11]+"</td>"
						+" <td>"+resp[12]+"</td>"
						+" </tr>";
	   		}
   		 $('#sample_2').dataTable().fnClearTable();
   		 	$('#eventTR').html(html);
    	},
		complete: function()
		{  
			loadSearchAndFilter('sample_2');
		}  
	  }); 
}


function instansDetails()
{
	var mtrNo=mtrNum;
	$.ajax({
    	url : './getInstansData/'+mtrNo,
    	type:'GET',
    	dataType:'json',
    	asynch:false,
    	cache:false,
    	success:function(data)
    	{
    		var html="";
   		 	for(var i=0;i<data.length; i++)
			{
				var resp=data[i];
				 html+="<tr>"
						+" <td>"+resp[0]+"</td>"
						+" <td>"+resp[1]+"</td>"
						+" <td>"+resp[2]+"</td>"
						+" <td>"+resp[3]+"</td>"
						+" </tr>";
	   		}
   		 $('#sample_3').dataTable().fnClearTable();
   		 	$('#instantsTR').html(html);
    	},
		complete: function()
		{  
			loadSearchAndFilter('sample_3');
		}  
	  }); 
}

function loadSurveyDetails()
{
	var mtrNo=mtrNum;
	$.ajax({
    	url : './getLoadSurveyData/'+mtrNo,
    	type:'GET',
    	dataType:'json',
    	asynch:false,
    	cache:false,
    	success:function(data)
    	{
    		var html="";
   		 	for(var i=0;i<data.length; i++)
			{
				var resp=data[i];
				 html+="<tr>"
						+" <td>"+resp[0]+"</td>"
						+" <td>"+resp[1]+"</td>"
						+" <td>"+resp[2]+"</td>"
						+" <td>"+resp[3]+"</td>"
						+" <td>"+resp[4]+"</td>"
						+" <td>"+resp[5]+"</td>"
						+" <td>"+resp[6]+"</td>"
						+" <td>"+resp[7]+"</td>"
						+" <td>"+resp[8]+"</td>"
						+" <td>"+resp[9]+"</td>"
						+" <td>"+resp[10]+"</td>"
						+" <td>"+resp[11]+"</td>"
						+" <td>"+resp[12]+"</td>"
						+" </tr>";
	   		}
   		 $('#sample_4').dataTable().fnClearTable();
   		 	$('#loadSurveyTR').html(html);
    	},
		complete: function()
		{  
			loadSearchAndFilter('sample_4');
		}  
	  }); 
}

</script>



<div class="content">
	<div class="row">
		<div class="col-md-12">
			

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Search Meter/ Modem For Communication Details
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				
					<div class="row" style="margin-left: -1px;">

						
						<table class="table ">

						<tbody>

							<tr>
								<td
									style="font-size: 13px; width: 208px; height: 42px; padding-right: 0px;">
									<lable style="font-size:18px;font-weight: oblique">Enter
									IMEI / Meter No</lable> <input class="form-control" type="text" name="IMEI"
									id="IMEI" autocomplete="off" style="width: 206px;">

								</td>
								<td>
									<p>&nbsp</p>

									<button type="button"
										style="margin-top: -3px; margin-left: 10px;"
										data-dismiss="modal" class="btn blue"
										onclick="updateFdrTable()">SUBMIT</button>


								</td>
								
								<td>
									<p>&nbsp;</p>

									<button type="button"
										style="margin-top: -3px; margin-left: 10px;"
										data-dismiss="modal" class="btn blue"
										onclick="getAll()">Get All Meter/Modem Communication List</button>


								</td>
								<!-- <td>
								<p>&nbsp;</p>
									<div class="input-icon">
										<i class="fa fa-calendar"></i> <input
										class="form-control date-picker input-medium"  type="text"
										value="" 
										data-date-format="yyyy-mm-dd" data-date-viewmode="years" name="fileDate" id="fileDate"/>
									</div>
								</td>
								<td>
								<p>&nbsp;</p>
								<button id="viewFdrOnMap" onclick="return downlodingFile();" name="viewFdrOnMap" class="btn yellow"><b>Download XML</b></button>
								<button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button>
								</td> -->
								
								<!-- <td style="width: 100px"></td>
								<td
									style="font-size: 13px; width: 208px; height: 42px; padding-right: 0px;">
									<lable style="font-size:18px;font-weight: oblique">Enter
									Meter No</lable> <input class="form-control" type="text" name="rev_charge"
									id="mterno" autocomplete="off" style="width: 206px;">

								</td>
								<td>
									<p>&nbsp</p>

									<button type="button" style="margin-top: -3px;  margin-left: 10px;"
										data-dismiss="modal" class="btn blue"
										onclick="updateFdrTable()">SUBMIT</button>


								</td> -->
								
								
							</tr>
						</tbody>
					</table>
					<p>&nbsp;</p>	
					</div>
				
					
					<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Total Meters')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					
					<table class="table table-striped table-hover table-bordered" id="sample_1">
						<thead>
							<tr>
								<th>Meter No.</th>
								<th>Modem IMEI</th>
								<th>Date</th>
								<th>Last Sync Time</th>
							</tr>
						</thead>
						<tbody id="updateMaster">
						<%-- <c:forEach var="element" items="${mDetailList}">
								<tr>
								
									<td><a href='./modemDetailsInactive?modem_sl_no=${element[0]}&mtrno=${element[1]}&substation=${element[3]}' style='color:blue;'>${element[0]}</a></td> 
									<td><a href='./mtrNoDetails?mtrno=${element[1]}' style='color:blue;' onclick="return mtrDetails('${element[1]}');">${element[1]}</a></td> 
									<td>${element[2]}</td> 
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td><fmt:formatDate value="${element[8]}"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
								</tr>
							</c:forEach>	 --%> 
						</tbody>
					</table>
					<form id="DeleteForm" method="POST" >
						<input type="hidden"  name="deviceid" value="" id="deleteID"/>
						<input type="hidden"  name="editVal" value="" id="editVal"/>
					</form>
					
				</div>
			</div>
		</div>
	</div>
	
</div>



<script>

function showCircle(zone) {
	$
			.ajax({
				url : './showCircle' + '/' + zone,
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				success : function(response) {
					var html = '';
					html += "<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control input-medium' type='text'><option value=''>Circle</option><option value='All'>ALL</option>";
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
				url : './showDivision' + '/' + zone + '/' + circle,
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				success : function(response) {
					var html = '';
					html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Division</option><option value='All'>ALL</option>";
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
	$('#sdoCode').find('option').remove();
	$.ajax({
		url : './showSubdivByDiv' + '/' + zone + '/' + circle + '/'
				+ division,
		type : 'GET',
		dataType : 'json',
		asynch : false,
		cache : false,
		success : function(response1) {
			$('#sdoCode').append($('<option>', {
				value : "",
				text : "Sub-Division"
			}));
			$('#sdoCode').append($('<option>', {
				value : "All",
				text : "All"
			}));

			/* var html='';
			html+="<select id='sdoCode' name='sdoCode' class='form-control select2me input-medium' type='text'><option value='noVal'>Sub-Division</option><option value='All'>ALL</option>"; */
			for (var i = 0; i < response1.length; i++) {
				//var response=response1[i];
				/* html+="<option  value='"+response1[i]+"'>"+response1[i]+"</option>"; */

				$('#sdoCode').append($('<option>', {
					value : response1[i],
					text : response1[i]
				}));
			}
			/* html+="</select><span></span>";
			$("#subdivTd").html(html); */
			//$('#subdiv').select2();
		}
	});
}


function getFeeder() {
	var zone = $('#zone').val();
	var circle = $('#circle').val();
	var division = $('#division').val();
	var subdiv = $('#sdoCode').val();
	$('#updateMaster').empty();
	$.ajax({
		url : './getAllMetersBasedOnMDAS' + '/' + zone + '/' + circle + '/'
				+ division + '/' + subdiv,
		type : 'GET',
		dataType : 'json',
		asynch : false,
		cache : false,
		success : function(response) {
			if (response.length != 0) {
				var html = "";
				
				for (var i = 0; i < response.length; i++) {
					var element = response[i];

					html += "<tr>" + "<td>"
					+ "<a href='./modemDetailsInactiveMDAS?modem_sl_no="+element[0]+"&mtrno="+element[1]+"&substation="+element[3]+"' style='color:blue;'>"+element[0]+"</a>"
					+ "</td>"
					+ "<td>"
					+ "<a style='color:blue;' onclick='return mtrDetails(\""+element[1]+"\");'>"+element[1]+"</a>"
					/*  href='./mtrNoDetails?mtrno="+element[1]+"' */
					+ "</td>"
					+ "<td>"
					+ element[2]
					+ "</td>"
					+ "<td>"
					+ element[3]
					+ "</td>"
					+ "<td>"
					+ element[4]
					+ "</td>"
					+ "<td>"
					+ element[5]
					+ "</td>"
					+ "<td>"
					+ element[6]
					+ "</td>"
					+ "<td>"
					+ element[7]
					+ "</td>";
					if(element[8]!=null){
						html +="<td>"
							+ moment(element[8]).format("YYYY-MM-DD HH:mm:ss")
							+ "</td>";
					} else{
						html +="<td>"
							+ "</td>";
					}
					
					
					
					+ " </tr>";
				}
				$('#sample_1').dataTable().fnClearTable();
				$('#updateMaster').html(html);
				loadSearchAndFilter('sample_1');
				
			} else {
				bootbox
				.alert("Meter not exist in the master main table ");
			}

		}
	});

}


function updateFdrTable() {

	var imeiVal = $('#IMEI').val();
	var type="";
	var aurl="";
	//alert(imeiVal.length);
	if(imeiVal.length<14){
		type="METER";
	} else {
		type="IMEI";
	}
	
	/* alert('IMEI====='+imeiVal); */
	$
			.ajax({
				url : "./getCommunicationForInstallationMDAS",
				type : "GET",
				data: {
					type :  type,
					imeiVal : imeiVal
				},
				dataType : "json",
				async : false,
				success : function(response) {
					var x = JSON.stringify(response);
					/* alert('result====='+x); */

					if (response.length == 0 || response.length == null) {
						bootbox
								.alert("Data Not Available for this IMEI No. / Meter No : "
										+ imeiVal);
						$('#updateMaster').empty();
					} else {
						var html = "";
						var select=new Array();
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];

							

								html += "<tr>" + "<td>"
										+ resp[0]
										+ "</td>"
										+ "<td>"
										+ resp[1]
										+ "</td>"
										+ "<td>"
										+ resp[2]
										+ "</td>";
									if( resp[3]!=null){
										html +="<td>"
												+ moment(resp[3]).format("YYYY-MM-DD HH:mm:ss")
												+ "</td>";
									} else{
										html +="<td></td>";
									}
										
										html+= " </tr>";
							
						}

						/* $('#sample_editable_1').dataTable().fnClearTable(); */
							$('#sample_1').dataTable().fnClearTable();
							$('#updateMaster').html(html);
							loadSearchAndFilter('sample_1');
						
						
					}

				}

			});

}


function getAll() {

	$
			.ajax({
				url : "./getAllCommunicationForInstallationMDAS",
				type : "GET",
				dataType : "json",
				async : false,
				success : function(response) {
					var x = JSON.stringify(response);
					/* alert('result====='+x); */

					if (response.length == 0 || response.length == null) {
						bootbox
								.alert("No Meter/Modem Communicated Today.");
						$('#updateMaster').empty();
					} else {
						var html = "";
						var select=new Array();
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
								html += "<tr>" + "<td>"
										+ resp[0]
										+ "</td>"
										+ "<td>"
										+ resp[1]
										+ "</td>"
										+ "<td>"
										+ resp[2]
										+ "</td>";
									if( resp[3]!=null){
										html +="<td>"
												+ moment(resp[3]).format("YYYY-MM-DD HH:mm:ss")
												+ "</td>";
									} else{
										html +="<td></td>";
									}
										
										html+= " </tr>";
							
						}

							$('#sample_1').dataTable().fnClearTable();
							$('#updateMaster').html(html);
							loadSearchAndFilter('sample_1');
						
						
					}

				}

			});

}



function loadSearchAndFilter(param) 
	{ 
		$('#'+param).dataTable().fnDestroy(); 
		$('#'+param).dataTable(
				 { "aLengthMenu": [
	                    [20, 50, 100, -1],
	                    [20, 50, 100, "All"] // change per page values here
	                ],
	                "iDisplayLength": 10
  }); 
		jQuery('#'+param+'_wrapper .dataTables_filter input').addClass("form-control input-small"); // modify table search input 
		jQuery('#'+param+'_wrapper .dataTables_length select').addClass("form-control input-xsmall"); // modify table per page dropdown 
		jQuery('#'+param+'_wrapper .dataTables_length select').select2(); // initialize select2 dropdown 
	}
	
	
	
	
function downlodingFile()
{
	var fDate=$('#fileDate').val(); 
	window.open("./generateXmlAndDownload/"+fDate);
	 /* $.ajax({
	    	url:'./downlodingFile/'+zn+'/'+cir+'/'+div+'/'+subdiv+'/'+fDate,
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response1)
	    	{
	    		if(response1.length==0){
	    			bootbox.alert("No Data Available");
	    		}
	    		else {
	    			bootbox.confirm("You want Download?");
	    			window.open("./downlodingFile/"+zn+"/"+cir+"/"+div+"/"+subdiv+"/"+fDate);
	    		} 
	    	}
		}); */
	return false;
}
</script>