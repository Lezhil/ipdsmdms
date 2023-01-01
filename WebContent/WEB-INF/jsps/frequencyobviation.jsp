<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"	type="text/javascript"></script>
<script	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js"	type="text/javascript"></script>

<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>
<script src="<c:url value='/highcharts/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/highcharts/exportingnew.js'/>" type="text/javascript"></script>



<!-- <script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script> 
<script src="https://code.highcharts.com/modules/exporting.js"></script>
-->



<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {

						$("#MIS-Reports").addClass('start active , selected');
						$("#month").val('');
						var zone = '%';
						//showCircle(zone);
						//$('#sample1').hide();
						$('#pagediv').hide();
						
						App.init();
						TableEditable.init();
						Tasks.initDashboardWidget();
						FormComponents.init();
						TableManaged.init();
						
						$('#mdmDashBoardId,#reportsId,#frequencyId')
								.addClass('start active ,selected');
						$(
								"#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n"
										+ "#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager")
								.removeClass('start active ,selected');

						loadSearchAndFilter('sample1');
						loadSearchAndFilter1('sample2');
						/* loadSearchAndFilter('sample_2');
						loadSearchAndFilter('sample_3'); */
						//getConsumercategory();
						$('#Consumer').click();
						$('#Consumer').click();

					});
</script>

<script>
var zone='%';
function showCircle(zone)
    {
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
                {
                  var html='';
                   // html+="<select id='circle' name='circle' onchange='showTownNameandCode(this.value)' class='form-control input-medium' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
                    html+="<select id='LFcircle' name='LFcircle' class='form-control' onchange='showTownNameandCode(this.value)' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
                   
                    for( var i=0;i<response.length;i++)
                    {
                        html+="<option value='"+response[i]+"'>"+response[i]+"</option>";
                    }
					html+="</select><span></span>";
                    $("#LFcircle").html(html);
                    $('#LFcircle').select2();
                }
            });
    }



function showTownNameandCode(circle){
		var zone =  $('#LFzone').val(); 
	//	alert(zone);
		
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
	                html += "<select id='LFtown' name='LFtown'  class='form-control  input-medium'  type='text'><option value=''>Select</option><option value='%'>ALL</option>";
	               
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

    
   
   
	  
	function getReport() {
		var zone=$('#LFzone').val();
		var circle = $('#LFcircle').val();
		var town = $('#LFtown').val();
		var locType = $('#loctype').val();
		var date = $('#month').val();
	//	var rdngmonth = $('#month').val();
		var loctype=$("#loctype").val();

		if (zone == "") {
			bootbox.alert("Please Select Region");
			return false;
		}
		if (circle == "") {
			bootbox.alert("Please Select circle");
			return false;
		}
		if(town=="")
		{
		bootbox.alert("Please Enter town");
		return false;
		}
 
		if (locType == "") {
			bootbox.alert("Please select Location Type");
			return false;
		}
 		
		if (date == "") {
			bootbox.alert("Please select Date");
			return false;
		}
		//alert(locType+" "+circle+"  "+division+"  "+subdivision+rdngmonth);
		$('#sample1').dataTable().fnClearTable();
		$('#imageee').show();
		$('#pagediv').show();
		$.ajax({
			url : './getfrequencyobliviondata/'  + locType
					+ '/' + date,
			type : 'POST',
			data : {
				zone:zone,
				circle : circle,
				town : town,
				date : date,
				loctype : loctype
			},
			dataType : 'json',
			success : function(response) {
			//	alert(response);
				 $('#imageee').hide();
				 $('#sample1').show();
				$("#updateMaster").html('');
					$('#dtHealth').show();
					if (response != null && response.length > 0) {
						var html = "";
						for (var i = 0; i < response.length; i++) {

							var resp = response[i];
							html += "<tr>" +
						  "<td>" + (i + 1) + "</td>"+ 
						  "<td>" + (resp[0]==null?"": resp[0]) + " </td>" +
						  "<td>" + (resp[1]==null?"": resp[1]) + " </td>" +
						  "<td>" + (resp[2]==null?"": resp[2]) + " </td>" +
						  
						  "<td >"+(resp[3]==null?'':moment(resp[3]).format('DD-MM-YYYY'))+"</td>"+
						  "<td>" + (resp[4]==null?"": resp[4]) + " </td>" +
						  "<td>" + (resp[5]==null?"": resp[5]) + " </td>" +
                          "</tr>";
						}
						$('#sample1').dataTable().fnClearTable();
						$("#updateMaster").html(html);
						loadSearchAndFilter('sample1');
					} else {
						$('#sample1').dataTable().fnClearTable();
					     $('#imageee').hide();
						$("#updateMaster").html(html);
						loadSearchAndFilter('sample1');
						bootbox.alert("No Data Found");
					}
				
			},
			complete : function() {
				loadSearchAndFilter('sample1');
				 $('#imageee').hide();
			}
		});
		

	}

	function exporttogrid(){
		var locType = $('#loctype').val();
		if (("feeder").localeCompare(locType)) {
			tableToExcel('sample1', 'Frequency Deviation');
		}
		else {
		tableToExcel('sample2', 'Feeder Network Performance');
	}
	}

	 function showResultsbasedOntownCode (){
	  		
     }

	 
	  
</script>

<div class="page-content">
	<div class="row">
		<div class="col-md-12">

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Frequency Deviation
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">

			<jsp:include page="locationFilter.jsp"/> 

					<div class="row" style="margin-left: -1px;">
							<div class="col-md-3" id="LocType">
						<strong>Location Type:</strong>
						<div class="form-group">
					<select class="form-control select2me" id="loctype" name="loctype">	
                                            <option value=""></option>
											<option value="FEEDER METER">FEEDER</option>
											<option value="DT">DT</option>
											<option value="BOUNDARY METER">BOUNDARY</option>
											</select>
											</div>
											</div>
								
								
							<div class="col-md-3"><strong>Date:</strong><font color="red">*</font>
					<div class="form-group">
							<div class="input-group input-medium date date-picker"  data-date-format="yyyy-mm-dd"  data-date-end-date="0d" data-date-viewmode="years" id="monthyear">
 							<input type="text"  class="form-control " name="month" id="month"   >
							<span class="input-group-btn">
							<button class="btn default" type="button" id="month" > <i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</div>
							</div>
							
											<div class="col-md-2">

										<button type="button" id="viewFeeder" style="margin-top: 19px;"
											onclick="return getReport();" name="viewconsumers"
											class="btn yellow">
											<b>View</b>
										</button> 
									</div>
									</div>
									
				<!-- <th id="LocType" class="block">Location&nbsp;Type:<span style="color: red">*</span></th>
									<th id="LocType"><select
										class="form-control select2me input-medium" id="loctype"
										name="loctype">
											<option value=""></option>
											<option value="feeder">Feeder</option>
											<option value="dt">Dt</option>
									</select></th> -->

									<!-- <th class="block">Month&nbsp;Year<span style="color: red">*</span></th>
									<td>
										<div class="input-group ">
											<input type="text" class="form-control from" id="month"
												name="month" placeholder="Select Month"
												style="cursor: pointer"> <span
												class="input-group-btn">
												<button class="btn default" type="button">
													<i class="fa fa-calendar"></i>
												</button>
											</span>
										</div>
									</td> -->
						
		<%-- 						<tr>
								
								<th class="block">Region&nbsp;:&nbsp;<span style="color: red">*</span></th>
									<th id="circleTd"><select
										class="form-control select2me input-medium" id="zone"
										name="zone" onchange="showCircle(this.value);">
											<option value="">Select</option>
											<option value="%">ALL</option> 
											<c:forEach items="${ZoneList}" var="elements">
											<option value="${elements}">${elements}</option>
											</c:forEach>
									</select></th>
									

									<th class="block">Circle&nbsp;:&nbsp;<span style="color: red">*</span></th>
									<th id="circleTd"><select
										class="form-control select2me input-medium" id="circle"
										name="circle" onchange="showTownNameandCode(this.value);">
											<option id="getCircles" value=""></option>
											<option value=""></option>
									</select></th>
									
									<th class="block">Town&nbsp;:<span style="color: red">*</span></th>
							<th id="townTd"><select
								class="form-control select2me input-medium" id="town"
								name="town">
								<option value="">Select</option>
							<option value="%">ALL</option>
							</select></th>
							
									<!-- <th class="block">Division&nbsp;:<span style="color: red">*</span></th>
									<th id="divisionTd"><select
										class="form-control select2me input-medium" id="division"
										name="division" onchange="showSubdivByDiv(this.value)">
										<option value=''>Select Division</option>
									</select></th> -->
									<!-- <th class="block">Sub&nbsp;Division&nbsp;:<span style="color: red">*</span></th>
									<th id="subdivTd"><select
										class="form-control select2me input-medium" id="sdoCode"  onchange="showTownBySubdiv(this.value);
										name="sdoCode" ><option value=''>Select Sub-Division</option></select>
										</th> -->
										
								</tr> --%>
							<!-- 	<tr>
									<th colspan="8"></th>
								</tr>
								<tr>
									<th colspan="8"></th>
								</tr>
								<tr>
								
					
									<td>
										<button type="button" id="viewFeeder"
											style="margin-left: 20px;" onclick="return getReport()"
											name="viewconsumers" class="btn yellow">
											<b>View</b>
										</button> <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button>
									</td>
								</tr>
							</tbody>
						</table> -->
						<p>&nbsp;</p>
						<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
					
					<div class="row" style="margin-left: -1px;">
						<div class="table-toolbar">
							<div class="btn-group pull-right" style="margin-top: 15px;">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id=""
								onclick="exportPDF()">Export to PDF</a></li> -->
									<li><a href="#" id="excelExport"
										onclick="exporttogrid();">Export
											to Excel</a></li>
								</ul>
							</div>
						</div>
						<div id="dtHealth" style="display: none;">
							<table 
								class="table table-striped table-hover table-bordered"
								id="sample1">
								<thead>
									<tr>
										<th>Sl No</th>	
										<th>Meterno</th>
										<th>Feeder Name</th>
										<th>Location Type</th>
										<th>Date </th>
										<th>AVG Frequency (Daily) </th>
										<th>Status </th>
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

</div>

<script>


	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth();

	$('.from').datepicker({
		format : "yyyymm",
		minViewMode : 1,
		autoclose : true,
		startDate : new Date(new Date().getFullYear()),
		endDate : new Date(year, month - 1, '30')
	});
	
	
	function exportPDF()
	{
		
		var zone=$('#LFzone').val();
		var circle = $('#LFcircle').val();
		var locType = $('#loctype').val();
		var rdngmonth = $('#month').val();
		var town1 = $('#LFtown').val();
		var a=[];
		var townname = $("#LFtown option:selected").map(function(){return this.text}).get().join(',');
		
		
		var zne="",cir="",town;

		if(zone=="%"){
			zne="ALL";
		}else{
		    zne=zone;
		}
		if(circle=="%"){
			cir="ALL";
		}else{
		    cir=circle;
		}
		
		    
		}
		/* alert(zone);
		alert(circle);
		alert(town);
		 */
		

		window.location.href=("./NetworkAssetDetailsPdf?zne="+zne+"&cir="+cir+"&locType="+locType+"&rdngmonth="+rdngmonth+"&town="+town+"&townname="+townname+"&subdvn=All&div=All");
	}
</script>