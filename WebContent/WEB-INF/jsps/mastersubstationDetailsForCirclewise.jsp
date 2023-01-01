<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	if (session.getAttribute("username") == null || session.getAttribute("username") == null) {
%>
<script>
	window.location.href = "./?sessionVal=expired";
</script>
<%
	}
%>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
  <script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
	<!-- <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> -->
	 <script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
	<script src="resources/assets/scripts/app.js"></script>
	<script src="resources/assets/scripts/ui-extended-modals.js"></script>

	<script  type="text/javascript">

$(".page-content").ready(function() {
		
		App.init();
		TableEditable.init();
		Tasks.initDashboardWidget();
		FormComponents.init();
		loadSearchAndFilter('sample1');
		TableManaged.init();
		 
		 $('#MDMSideBarContents,#staDtlsId,#mastersubstationDetailsForCirclewise,#mpmId').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#buisnessRoleDetails,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
		 loadSearchAndFilter("sample1");
		 
		 $("#analysedPrintId").click(function(){ 
	    	 printcontent($("#excelUpload .table-scrollable").html());


	    	 getsubstData();
		});
});



	</script>
	
	<script>

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
                    //html+="<select id='circle' name='circle' onchange='showTownBySubdiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
                  html+="<select id='LFcircle' name='LFcircle' class='form-control' onchange='showTownNameandCode(this.value)' type='text'><option value=''></option><option value='%'>ALL</option>";
                    
                    for( var i=0;i<response.length;i++)
                    {
                        html+="<option value='"+response[i]+"'>"+response[i]+"</option>";
                    }
					html+="</select><span></span>";
                    $("#LFcircleTd").html(html);
                    $('#LFcircle').select2();
                }
            });
    }

 
	 function showDivision(circle) {
	   	  var zone = $('#zone').val();
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
	                            html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select Division</option><option value='%'>ALL</option>";
	                            for (var i = 0; i < response.length; i++) {
	                                html += "<option value='"+response[i]+"'>"
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
         //alert("zone---"+zone);
         //alert("circle---"+circle);
         //alert("div---"+division);
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
                         html += "<select id='sdoCode' name='sdoCode' onchange='showTownBySubdiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
                         for (var i = 0; i < response1.length; i++) {
                             //var response=response1[i];
                             html += "<option value='"+response1[i]+"'>"
                                     + response1[i] + "</option>";
                         }
                         html += "</select><span></span>";
                         $("#subdivTd").html(html);
                         $('#sdoCode').select2();
                     }
                 });
     }

	 function showResultsbasedOntownCode (){
	 		
     }

	 function showTownNameandCode(subdiv) {
	      var zone = $('#LFzone').val()
	      var circle = $('#LFcircle').val();
	      var division = $('#division').val();
	      $.ajax({
	          url : './getTownsBaseOnSubdivision',
	          type : 'GET',
	          dataType : 'json',
	          asynch : false,
	          cache : false,
	          data : {
	              zone : zone,
	              circle : circle,
	              division : '%',
	              subdivision :'%'
	          },
	                  success : function(response1) {
   	                //  alert(response1);
	                      var html = '';
	                     // html += "<select id='town' name='town'  class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
	                   html += "<select id='LFtown' name='LFtown'  class='form-control input-medium' type='text'><option value=''></option><option value='%'>ALL</option>";
	                     
	                      for (var i = 0; i < response1.length; i++) {
   	                  //    towncode=response1[i][0];
	                          //var response=response1[i];
	                          html += "<option value='"+response1[i][0]+"'>"
	                                  +response1[i][0]+"-"+response1[i][1] + "</option>";
	                      }
	                      html += "</select><span></span>";
	                      $("#LFtownTd").html(html);
	                      $('#LFtown').select2();
	                  }
	              });
	  }
	
	
 
	</script>
	
	<script>
		function getsubstData(){

			/* var town = $('#LFtown').val(); */
				var zone=$("#LFzone").val();
				var circle =$("#LFcircle").val();
				var town = $('#LFtown').val();
				

			if(zone=='' || zone==null){
				bootbox.alert("Please Select Region");
				return false;
			}
			
			if(circle=='' || circle==null){
				bootbox.alert("Please Select circle");
				return false;
			}
			
			if(town=='' || town==null){
				bootbox.alert("Please Select Town");
				return false;
			}  
			 $('#imageee').show();

				$.ajax({

					url : './getmastersubstationdetails',
					type : 'GET',
					 data:{
						   zone:zone,
						   circle:circle,
							town : town
							
						  
					 },
					success : function(response) {


						$('#imageee').hide();
						if (response.length != 0) {

							var html = "";
							for (var i = 0; i < response.length; i++) {
								var element = response[i];
								html += "<tr>";
								html += "<td>" + (i + 1) + "</td>";
								if ( officeType == 'corporate') {
									html += "<td><a href='#' onclick='editUserNew(\""+element[2]+"\")' id="+element[2]+">Edit</a></td>";

									}

								html +=	
									"<td>"
									+ (element[13] == null ? "" : element[13])
									+ "</td>"
									+"<td>"
									+ (element[14] == null ? "" : element[14])
									+ "</td>"
									+ "<td>"
									+ (element[15] == null ? "" : element[15])
									+ "</td>"
									+ "<td>"
									+ (element[0] == null ? "" : element[0])
									+ "</td>"
									
									+ "<td>"
									+ (element[1] == null ? "" : element[1])
									+ "</td>"
									+ "<td>"
									+ (element[2] == null ? "" : element[2])
									+ "</td>"
									+ "<td style='text-align: center;'>"
									+ (element[4] == null ? "": "<a href='#' style='font-weight: bold;' onclick='showSubdivisionsModal(\""
													+ element[2]
													+ "\")'>"
													+ element[4] + "</a>")
									+ "</td>"

									+ "<td style='text-align: center;'>"
									+ (element[3] == null ? ""
											: "<a href='#' style='font-weight: bold;' onclick='showTownsModal(\""
													+ element[2]
													+ "\")'>"
													+ element[3] + "</a>")
									+ "</td>"
									+ "<td>"
									+ (element[9] == null ? "" : element[9])
									+ "</td>"
									+ "<td>"
									+ (element[10] == null ? "" : element[10])
									+ "</td>"
									+ "<td>"
									+ (element[11] == null ? "" : element[11])
									+ "</td>"
									+ "<td>"
									+ (element[12] == null ? "" : element[12])
									+ "</td>"
									+ "<td>"
									+ (element[5] == null ? "" : element[5])
									+ "</td>"
									+ "<td>"
									+ (element[6] == null ? "" : moment(
											element[6]).format(
											'DD-MM-YYYY hh:mm:ss'))
									+ "</td>"
									+ "<td>"
									+ (element[7] == null ? "" : element[7])
									+ "</td>"
								
									+ "<td>"
									+ (element[8] == null ? "" : moment(
											element[8]).format(
											'DD-MM-YYYY hh:mm:ss'))
									+ "</td>";
								
								html += "</tr>";
								

								
						}
							$('#sample1').dataTable().fnClearTable();
							$('#getmastersubstationdetails').html(html);
							loadSearchAndFilter('sample1');
						
							$('#stack1').modal('hide');

					}
						 else {
								bootbox.alert("No substation found");
							}
					}
				});

			}
		
		function editUserNew(param) {
			
			var id = param;

			$.ajax({
				type : "GET",
				url : "./editSubStationDetailsNew/" + id,
				dataType : "json",

				
				
				success : function(response) {
					
					var data = response[0];
				//	alert(data[7]);
					$("#subdivcodee").val(data[3]);
					$("#town_code_edit").val(data[4]);
					$("#substaidd").val(data[2]);
					$("#substanamee").val(data[0]);
					$("#substacapp").val(data[1]);
					$("#latitude").val(data[5]);
					$("#longitude").val(data[6]);
					$("#dcuno").val(data[7]);
					$("#editSubstationCapacityinMVA").val(data[8]);
					

				}
			});
			$("#stack2").modal('show');
		}

				


	</script>
	
	<script>


	function showSubdivisionsModal(ssid){
		$("#stack3").modal('show');
		$.ajax({
		     url:'./getSubdivisionForSubstation',
		     type:'POST',
		     data:{
		    	 ssid : ssid,
		     },
		     success:function(response)
		     {
		    	 var html = '';
		    	 for (var i = 0; i < response.length; i++) {
						var element = response[i];
						html += "<tr>";
						html +="<td>" + (i + 1) + "</td>"
							 + "<td>" + element[1] + "</td>"
							 + "<td>" + element[0] + "</td>";
						html += "</tr>";
		    	}
		    	$('#subdibPopTbl').dataTable().fnClearTable();
				$('#subdivDetailsForSubstation').html(html);
				loadSearchAndFilter('subdibPopTbl');

					
		     }
		});
		
	}
	function showTownsModal(ssid){
		$("#stack4").modal('show');
		$.ajax({
		     url:'./getTownForSubstation',
		     type:'POST',
		     data:{
		    	 ssid : ssid,
		     },
		     success:function(response)
		     {
		    	 var html = '';
		    	 for (var i = 0; i < response.length; i++) {
						var element = response[i];
						html += "<tr>";
						html +="<td>" + (i + 1) + "</td>"
							 + "<td>" + element[1] + "</td>"
							 + "<td>" + element[0] + "</td>";
						html += "</tr>";
		    	}
		    	$('#townPopTbl').dataTable().fnClearTable();
				$('#townDetailsForSubstation').html(html);
				loadSearchAndFilter('townPopTbl');
		     }
		});
		
	}

	function exportPDF()
	{

		var zone=$('#LFzone').val();
		var circle =$("#LFcircle").val();
		var town = $('#LFtown').val();

		if(zone=="%"){
			zone="ALL";
		}
		
		if(circle=="%"){
			circle="ALL";
		}
		if(town=="%"){
			town="ALL";
		}

		
		window.location.href=("./SubstationDetailsPDF?zone="+zone+"&circle="+circle+"&town="+town);
	}
	

	function downLoadSample(type){
		
		window.location.href="http://1.23.144.187:8102/downloads/sldreport/SubstationDetailsSample.xlsx";
	}

	 function exportToExcelSubMethod(){

		 var zone=$('#LFzone').val();
			var circle =$("#LFcircle").val();
			var town_code = $('#LFtown').val();

			if(zone=="%"){
				zone="ALL";
			}
			if(circle=="%"){
				circle="ALL";
			}
			if(town=="%"){
				town="ALL";
			}
		 
    	 window.open("./SubstationmasterExcel?zone="+zone+"&circle="+circle+"&town_code="+town_code);
           
         }


function downLoadSampleExcel()
{
	window.open("./SampleSubstationExcelDownload");
	}
	

	</script>
	
	
	<div class="page-content">
	
			<c:if test="${not empty msg}">
		<div class="alert alert-success display-show">
			<button class="close" data-close="alert"></button>
			<span style="color: green">${msg}</span>
		</div>
	</c:if>
		<div class="row">
			<div class="col-md-12">
				<div class="portlet box blue">
				
					<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Master_Substation_details
					</div>
					
					
				</div>
				<div class="portlet-body">
				
					  <jsp:include page="locationFilter.jsp"/> 
					  
					 
					 <div class="row" style="margin-left: -1px;">
					 	
					 	<div class="col-md-3" style="margin-top:15px;">
						<button type="button" id="showFeederData"  style="margin-top: 13px;"
											onclick="return getsubstData()" name="showFeederData"
											class="btn yellow">
											<b>View</b>
										</button>
						</div>
						
					 
					 
					 </div>
					 
					 
					 <div class="tabbable tabbable-custom">
					 <div class="col-md-12">
					 	<div class="table-toolbar">
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<!-- <li id="print"><a href="#">Print</a></li> -->	
										<li><a href="#" id=""onclick="exportPDF('sample1','SubStationData')">Export to PDF</a></li>	
								 												
										<li><a href="#" id="excelExport" onclick="exportToExcelSubMethod('sample1', 'SubstationDetails')">Export to Excel</a></li>
									</ul>
								</div>
								
							</div>
					 
					 		<table class="table table-striped table-hover table-bordered"
									id="sample1">
									<thead>
										<tr>
											<th>SL No</th>
						    				  <c:if test = "${editRights eq 'yes'}">
				              				  <th>EDIT</th></c:if>
				            
								<!-- <th>ID</th> -->
								<!-- <th>Substation Id</th>  -->
								<th>Region</th>
								<th>circle</th>
								<th>Town</th>
								<th>Substation Name</th>
								<th>Voltage level</th>
								<th>Substation Code</th>
								<th>Feeder Count</th>		
								<th>Town Count</th>
								<th>Latitude</th>
								<th>Longitude</th>
								<th>Dcu NO</th>
								<th>Substation Capacity(in MVA)</th>
								<th>EntryBy</th>
								<th>EntryDate</th>
								<th>UpdateBY</th>
								<th>UpdateDate</th>
											<!-- <th>Meter Ratio</th> -->
										<!-- 	<th>Export/Import</th> -->
											<!-- <th>Meter Installed</th> -->
										</tr>
									</thead>
									<tbody id="getmastersubstationdetails">
									</tbody>
									<div id="imageee" hidden="true" style="text-align: center;">
										<h3 id="loadingText">Loading..... Please wait.</h3>
										<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
									</div>
								</table>
							</div> 
					 </div>
					 
					 
					 
		
		<div id="stack3" class="modal fade" role="dialog"
			aria-labelledby="myModalLabel10" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #4b8cf8;">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"></button>
						<h5 class="modal-title" ><font style="font-weight: bold; color: white;">Subdivision Details</font></a></h5>
					</div>
					<div class="modal-body">
						<table id="subdibPopTbl" class="table table-striped table-hover table-bordered ">
							<thead>
								<tr>
									<th>SL No</th>
									<th>Feeder Name</th>
									<th>Feeder Code</th>
								</tr>
								
							</thead>
							<tbody id="subdivDetailsForSubstation">
							</tbody>
						
						</table>
						<div class="modal-footer">
							<button type="button" data-dismiss="modal" class="btn">Cancel</button>
		
						</div>
		
					</div>
				</div>
			</div>
		</div>
				
				
	<div id="stack4" class="modal fade" role="dialog"
		aria-labelledby="myModalLabel10" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #4b8cf8;">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h5 class="modal-title" ><font style="font-weight: bold; color: white;">Town Details</font></a></h5>
				</div>
				<div class="modal-body">
					<table id="townPopTbl" class="table table-striped table-hover table-bordered ">
						<thead>
							<tr>
								<th>SL No</th>
								<th>Town Name</th>
								<th>Town Code</th>
							</tr>
							
						</thead>
						<tbody id="townDetailsForSubstation">
						</tbody>
					</table>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn">Cancel</button>
	
					</div>
	
				</div>
			</div>
		</div>
	</div>
				
<div id="stack2" class="modal fade" role="dialog"
	aria-labelledby="myModalLabel10" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h5 class="modal-title">Edit Substation Details</h5>
			</div>
			<div class="modal-body">
				<table id="stack2id"
					class="table table-striped table-hover table-bordered ">
					<thead>
						<tr id="Subdivision_Code">
							<td>Subdivision Code<font color="red">*</font></td>
							<td><input type="text" id="subdivcodee"
								class="form-control placeholder-no-fix"
								placeholder="Enter Subdivision Code" name="subdivcodee"
								readonly="readonly" ></input></td>
						</tr>
						<tr id="Town_Code_edit_tr">
							<td>Town Code<font color="red">*</font></td>
							<td><input type="text" id="town_code_edit"
								class="form-control placeholder-no-fix"
								name="town_code_edit"
								readonly="readonly" ></input></td>
						</tr>
						<tr id="Substation_ID">
							<td>Substation ID<font color="red">*</font></td>
							<td><input type="text" id="substaidd"
								class="form-control placeholder-no-fix" readonly="readonly"
								placeholder="Enter Substation ID" name="substaidd"
								maxlength="12"></input></td>
						</tr>
						<tr id="Substation_Name">
							<td>Substation Name<font color="red">*</font></td>
							<td><input type="text" id="substanamee"
								class="form-control placeholder-no-fix"
								placeholder="Enter Substation Name" name="substanamee"
								maxlength="200"></input></td>
						</tr>
						<tr id="Substation_Capacity">


							<td>Substation Voltage level<!-- <font color="red">*</font> --></td>
							<td><!-- <input type="text" id="substacapp"
								class="form-control placeholder-no-fix"
								placeholder="Enter Substation Capacity" name="substacapp"
								maxlength="6" /></input> -->
								<select  name="substacapp"
								id="substacapp" class="form-control">
								<option selected disabled value="">Select Voltage level</option>
								<option value=110/11>110/11KV</option>
								<option value=33/11>33/11KV</option>
								<option value=110/33/11>110/33/11KV</option>
								<option value=110/33/22>110/33/22KV</option>
								<option value=110/22>110/22KV</option>
							</select>
								</td>
						</tr>
						<tr id="Substation_Capacityin_MVA)">
													<td>Substation Capacity(in MVA)</td>
													<td><input type="text" id="editSubstationCapacityinMVA" class="form-control placeholder-no-fix" placeholder="Enter Substation Capacity"  name="editSubstationCapacityinMVA" maxlength="10" ></input>
											        </td>
						  </tr>
						
						<tr id="Latitude">
							<td>Latitude<!-- <font color="red">*</font> --></td>
							<td><input type="text" id="latitude"
								class="form-control placeholder-no-fix"
								name="Latitude" ></input></td>
						</tr>
						<tr id="Longitude">
							<td>Longitude<!-- <font color="red">*</font> --></td>
							<td><input type="text" id="longitude"
								class="form-control placeholder-no-fix"
								name="longitude"></input></td>
						</tr>
						
						<tr id="DcuNo">
							<td>DCU No<!-- <font color="red">*</font> --></td>
							<td><input type="text" id="dcuno"
								class="form-control placeholder-no-fix"
								name="dcuno"></input></td>
						</tr>
											</thead>
				</table>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn">Cancel</button>
					<button class="btn blue pull-right" id="modifyId" type="submit"
						value="" onclick="return modifySubstation();">Modify</button>
				</div>
				<%-- </form> --%>
			</div>
		</div>
	</div>
</div>


				
					 
			
				</div>
				
				</div>
			</div>
		</div>
	</div>