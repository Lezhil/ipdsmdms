<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>

$(".page-content").ready(
		function() {
			App.init();
			TableEditable.init();
			FormComponents.init();
			loadSearchAndFilter('sample_1');
			$('#MDMSideBarContents,#metermang,#meterChangeModule').addClass('start active ,selected');
			$("#MDASSideBarContents,#dash-board,#user-location,#MIS-Reports,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn").removeClass('start active ,selected');
			
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



<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<c:if test="${results ne 'notDisplay'}">
				<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color: green">${results}</span>
				</div>
			</c:if>

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>METER CHANGE
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
					<table class="table ">
						<tbody>
							<tr>
								<td
									style="font-size: 13px; width: 208px; height: 42px; padding-right: 0px;">
									<lable style="font-size:18px;font-weight: oblique">Enter
									AccNo / Meter No</lable> <input class="form-control" type="text" name="IMEI"
									id="IMEI" autocomplete="off" style="width: 206px;">
								</td>
								<td>
									<p>&nbsp;</p>
									<button type="button"
										style="margin-top: -3px; margin-left: 10px;"
										data-dismiss="modal" class="btn blue"
										onclick="return dataByImeiAccno();">SUBMIT</button>
								</td>
							</tr>
						</tbody>
					</table>
					
					<table class="table table-striped table-hover table-bordered" id="sample_1">
						<thead>
							<tr>
								<th>ACCOUNT NO</th>
								<th>CONSUMER NAME</th>
								<th>IMEI NO</th>
								<th>METER NO</th>
								<th>ZONE</th>
								<th>CIRCLE</th>
								<th>DIVISION</th>
								<th>SUBDIVISION</th>
								<th>SUB-STATION</th>
							</tr>
						</thead>
						<tbody id="updateMaster_main">
							<%-- <c:forEach var="element" items="${mDetailList}">
								<tr>
								
									<td><a href='./modemDetailsInactiveMDAS?modem_sl_no=${element[0]}&mtrno=${element[1]}&substation=${element[3]}' style='color:blue;'>${element[0]}</a></td> 
									<td><a href='./mtrNoDetailsMDAS?mtrno=${element[1]}' style='color:blue;' onclick="return mtrDetails('${element[1]}');">${element[1]}</a></td> 
									<td>${element[9]}</td> 
									<td>${element[12]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>
									<td>${element[7]}</td>
									<td><fmt:formatDate value="${element[8]}"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
								</tr>
							</c:forEach> --%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<div id="popUpGrid">
			<div id="changingMeterPopUp" class="modal fade" tabindex="-1">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" id="closePopUp">
							</button>
							<h4 class="modal-title">
								<span id="popUpHeading"></span>
							</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col-md-12">
									<div class="portlet box blue">
										<div class="portlet-title">
											<div class="caption">
												<i class="fa fa-bar-chart-o"></i>METER CHANGE ENTRY:<font color="yellow"><b><span id="oldMeterNo"></span></b></font>
											</div>
										</div>

										<div class="portlet-body">
										<form action="./changingMeter" method="POST" id="changingMeterForm">
												<%--  <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/> --%> 
											<table id="changingMeterPopUpDataTable"
												class='table table-striped table-hover table-bordered'>
												<tbody>
												<tr>
												<td  hidden="true"><input id="oldMeterno" name="oldMeterno"  type="text"></input> </td>
												<td style="text-align: center;"><font color="purple"><b>ENTER NEW METER NO:</b></font></td>
												<td style="text-align: center;"><input class="form-control input-medium" type="text" id="newMeterNo" name="newMeterNo" placeholder="Enter new METER NO"></td>
												<td style="text-align: center;"><button type="submit" class="btn green"
													id="changingMeterNo" title="Submit new METER No"
													>Submit</button></td> <!-- onclick="return addNewZone();" -->
												</tr>
												</tbody>
											</table>
											</form>
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
				url : './showDivisionMDAS' + '/' + zone + '/' + circle,
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
		url : './showSubdivByDivMDAS' + '/' + zone + '/' + circle + '/'
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
					+ "<a href='./modemDetailsInactiveMDAS?modem_sl_no="+element[0]+"&mtrno="+element[1]+"&substation="+element[3]+"' style='color:blue;'>"+(element[0]==null?"":element[0])+"</a>"
					+ "</td>"
					+ "<td>"
					+ "<a style='color:blue;' onclick='return mtrDetails(\""+element[1]+"\");'>"+element[1]+"</a>"
					/*  href='./mtrNoDetails?mtrno="+element[1]+"' */
					+ "</td>"
					+ "<td>"+ element[9]+ "</td>"
					+ "<td>"+ element[12]+ "</td>"
					+ "<td>"+ element[3]+ "</td>"
					+ "<td>"+ element[4]+ "</td>"
					+ "<td>"+ element[5]+ "</td>"
					+ "<td>"+ element[7]+ "</td>";
					
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


function dataByImeiAccno() {

	var imeiVal = $('#IMEI').val();
	
	var aurl="";
	//alert(imeiVal.length);
	if(imeiVal.length<10){
		aurl="./getFeedersBasedOnMeterNoMDAS/" + imeiVal;
	} else {
		aurl="./getFeedersBasedOnaccno/" + imeiVal;
	}
	
	/* alert('IMEI====='+imeiVal); */
	$.ajax({
				url : aurl,
				type : "GET",
				dataType : "JSON",
				async : false,
				success : function(response) {
					var x = JSON.stringify(response);
					if (response.length == 0 || response.length == null) {
						bootbox
								.alert("Data Not Available for this IMEI No. / Meter No : "
										+ imeiVal);
						$('#updateMaster').empty();
					} else {
						var html = "";
						var select=new Array();
						for (var i = 0; i < response.length; i++) 
						{
							var resp = response[i];
								html += "<tr>" 
										+ "<td>"+ resp.accno+ "</td>"
										+ "<td>"+ resp.customer_name+ "</td>";
										if(resp.modem_sl_no==null)
											{
											html+= "<td>"+''+ "</td>";
											}else
												{
												html+= "<td>"+ resp.modem_sl_no+ "</td>";
												}
										
										html+= "<td>"
										+ "<a style='color:blue;' data-toggle='modal' data-target='#changingMeterPopUp'>"+resp.mtrno+"</a>"
										+ "</td>"
										+ "<td>"+ resp.zone+ "</td>"
										+ "<td>"+ resp.circle+ "</td>"
										+ "<td>"+ resp.division+ "</td>"
										+ "<td>"+ resp.subdivision+ "</td>"
										+ "<td>"+ resp.substation+ "</td>";
										html+= " </tr>";
										
									$("#oldMeterno").val(resp.mtrno);
						   }
						/* $('#sample_editable_1').dataTable().fnClearTable(); */
							$('#sample_1').dataTable().fnClearTable();
							$('#updateMaster_main').html(html);
							loadSearchAndFilter('sample_1');
						
						
					}

				}

			});

}
</script>