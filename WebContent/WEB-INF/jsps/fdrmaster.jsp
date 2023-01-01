<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	if (session.getAttribute("username") == null || session.getAttribute("username") == null) {
%>
<script>
	window.location.href = "./?sessionVal=expired";
</script>
<%
	}
%>

<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>

<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>

<script>
	$(".page-content")
			.ready(
					function() {
						
						 
						App.init();
						TableEditable.init();
						FormComponents.init();
						loadSearchAndFilter('sample_1');
						/* var chyes=$('#uniform-fdryes span').attr('class'); 
						  var chno=$('#uniform-fdrno span').attr('class');
						 if(chyes=='checked'){
							 $( "#uniform-fdryes span" ).removeClass( "checked" );
							 $( "#uniform-fdrno span" ).addClass( "checked" ); */
						$(
								'#MDMSideBarContents,#meterpointmgmt,#fdrmaster,#mpmId')
								.addClass('start active ,selected');
						$(
								'#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
								.removeClass('start active ,selected');
						//getAllFeederList();
						$('#zone').val('').trigger('change');
						$('#circle').val('').trigger('change');
						$('#town').val('').trigger('change');
						

					});
</script>


	<script>

	function showCircle(zone) {
		//alert("hiiiii...")
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
	                html += "<select id='LFtown' name='LFtown' class='form-control  select2me'  type='text'><option value=''>Select Town</option><option value='%'>ALL</option>";
	               
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


	function showfeederResultsbasedOntownCode(townCode) {
		//alert(townCode);
		var circle = $("#circle").val();

	$('#feederTpId').val('').trigger('change');
	//var town = $('#townDT').val();
	$('#feederTpId').empty();
	$('#feederTpId').find('option').remove();
	$('#feederTpId').append($('<option>', {
		value : "",
		text : "Select Feeder"
	}));
	$('#feederTpId').append($('<option>', {
		value : "%",
		text : "ALL"
	}));

	$.ajax({
		url : './getFeederTypeBasedOnTown',
		type : 'POST',
		dataType : 'json',
		asynch : false,
		cache : false,
		data : {
			townCode : townCode,
			circle :circle
		},
		success : function(response1) {
			var html = '';
			/*    html += "<select id='feederTpId' name='feederTpId'  class='form-control input-medium'  type='text'><option value=''>Select Feeder</option><option value='%'>ALL</option>";
			   for (var i = 0; i < response1.length; i++) {
			       html += "<option value='"+response1[i][0]+"'>"
			               + response1[i][1] + "</option>";
			   }
			   html += "</select><span></span>";
			   $("#feederDivId").html(html);
			   $('#feederTpId').select2(); */

			for (var i = 0; i < response1.length; i++) {
				var resp = response1[i];
				$('#feederTpId').append($('<option>', {
					value : resp[0],
					text : resp[1]
				}));
			}
			$('#feederTpId').select2();

		}
	});
}


	 
	 function getReport() {
		
			var zone = $('#LFzone').val();
			var circle = $('#LFcircle').val();
			var division = $('#division').val();
			var subdiv = $('#sdoCode').val();
			var town = $('#LFtown').val();
		    //alert(town);
			if(zone=="")
			{
			bootbox.alert("Please Select Region");
			return false;
			}
			if(circle=="" || circle== null)
			{
			bootbox.alert("Please Select circle");
			return false;
			}
		
		
			$('#imageee').show();
			$.ajax({
				url : './getFeederMaster',
				type : 'GET',
				data : {
					subdiv : subdiv,
					 zone:zone,
		        	 circle:circle,
		        	 town : town
					
				},
				dataType : 'JSON',
				asynch : false,
				cache : false,
				success : function(response) {
					$('#imageee').hide();			

						if (response != null && response.length > 0) {
								var html = "";
								for (var i = 0; i < response.length; i++) {
									var resp = response[i];
									var j = i + 1;
									
									html += "<tr>" 
										+ "<td>"+ j + "</td>"
									
										+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"
										+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"
										+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"	
										+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"		
										+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"		
										+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"	
										+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
										+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"	
										+ "<td>"+ ((resp[8] == null) ? "": resp[8]) + " </td>"	
										+ "<td>"+ ((resp[9] == null) ? "": resp[9]) + " </td>"	
										+ "<td>"+ ((resp[10] == null) ? "": resp[10]) + " </td>"						
								html +="</tr>";
								}
								$('#sample_1').dataTable().fnClearTable();
								$("#getfeederReport").html(html);
								loadSearchAndFilter('sample_1');
							
					}  else {
						bootbox.alert("No Relative Data Found.");
					}
		

				},
				complete: function()
				{  

					 $("#imageee").hide();
					loadSearchAndFilter('sample_1'); 
				} 
			});


			 
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
						<i class="fa fa-edit"></i>Feeder-Master Report
					</div>
				</div>
		
			
				<div class="portlet-body">
				
					 <jsp:include page="locationFilter.jsp"/> 
					 		 
			  <div class="row" style="margin-left: -1px;">
					 	
					 	<div class="col-md-3" style="margin-top:15px;">
							<button type="button" id="showFeederData"  style="margin-top: 13px;"
											onclick="return getReport()" name="showFeederData"
											class="btn yellow">
											<b>View</b>
							</button>
						</div>
			 </div>
				
			
				<div id="imageee" hidden="true" style="text-align: center;">
										<h3 id="loadingText">Loading..... Please wait.</h3>
										<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
				</div>
					  
				<div class="tabbable tabbable-custom">
					 
					<div class="table-toolbar">
							<div class="btn-group pull-right" style="margin-top: 15px;">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
									<li><a href="#" id="excelExport"
										onclick="tableToXlxs('sample_1', 'Feeder Master');">Export
											to Excel</a></li>
								</ul>
							</div>
						</div>
						
					 		<table class="table table-striped table-hover table-bordered"
									id="sample_1">
									<thead>
										<tr>
											<th>SI.NO</th>
											<th>Region</th>
											<th>Circle</th>	
											<th>Division</th>
											<th>SubDivision</th>
											<th>Scetion</th>	
											<th>Town Code</th>
											<th>Town</th>
											<th>Feeder Code</th>
											<th>Feeder Name</th>
											<th>Substation Code</th>
											<th>Substation Name</th>
										</tr>
									
									</thead>
									
									
									<tbody id="getfeederReport">
									</tbody>
									
									
								</table>
								
					 </div>
					 </div>
					
				</div>
					
			</div>
		
		</div>
		
	</div>
	