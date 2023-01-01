<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<style>
.page-content .page-breadcrumb.breadcrumb>li>i {
	color: #fff;
}
.btn {
padding: 7px 33px;

}
 .modal-dialog {
	width: 70%;
}
.instantaneous{
 font-size: medium !important;
 color: black !important;
 font-weight: bold !important;
 background: white !important;
 width: 120px;
 text-align: left;
}
.input-medium {
    width: 180px !important;
}
.btn {
    padding: 7px 26px;
}

.col-xs-1-5 {
  width: 12.49995%;
}

a {
    color: orange;
    text-shadow: none !important;
}
</style>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&key=AIzaSyDFsv7MwN3q9GNl-kasQWAWqLtgAi1aaF4"></script>

<script>
$(".page-content").ready(function() 
{
	App.init();
	Index.initMiniCharts();
	TableEditable.init();
	$('#dash-board2').addClass('start active ,selected');
	$('dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment').removeClass('start active ,selected');

    var initialLocation=false;
    var map="";
    var response1='${substationPoints}';
    
    response1=JSON.parse(response1);
    
	  for(var i=0;i<response1.length;i++)
		{ 
		  var response=response1[i];
		  
		  if(!initialLocation)
			  {	
					map = new GMaps({
			            div: '#map-canvas',
			            lat: parseFloat(response.latitude),
			            lng :parseFloat(response.longitude),
			            zoom:10,
			            gestureHandling: 'greedy',
				        fullscreenControl: true,
			        });
  			     initialLocation=true;
			  }
		  
		if(initialLocation)
		 {
				   map.addMarker({
					   lat: parseFloat(response.latitude),
			            lng :parseFloat(response.longitude),
		            //icon:'./resources/assets/img/active.gif',
		            optimized: false,
		            
		             title:"SubStation Name- "+response.substation_name,
		              infoWindow: {
		                  content: 
		                  "<div>"
		                  +"<table  id=billTable1 class='table table-striped table-bordered table-hover' style='background: #99ccff;'>"
						  +"<tr><th>Substation Name</th><td>"+response.substation_name+"</td></tr>"
						  +"<tr><th>Address</th><td>"+response.substation_address+"</td></tr>"
						  +"<tr><th>Latitude</th><td>"+response.latitude+"</td></tr>"
						  +"<tr><th>Longitude</th><td>"+response.longitude+"</td></tr>"
						 
						 +"<tr><th>No Of Feeders</th><td><a style='font-size: medium; color:blue;' data-toggle=modal data-target=#stack2 data-dismiss=modal onclick='return getListFdrToSubSt(\""+(response.substation_name).trim()+"\")'><b>"+response.feederCount +"</b></a></td></tr>"
						  +"</table></div>"
		              }   
				   });
			 } 				    		  		    		  
		}
	  
if(!initialLocation)
{
	$('#gmapsContent').hide(); 
	document.getElementById('gmapsContent').style.display='none';
	bootbox.alert('No Data Available');
}
	
});

function getListFdrToSubSt(subStation)
{
	if(subStation.includes("."))
	  {
		subStation=subStation.replace(/\./g, "@");
	  }
	  if(subStation.includes("/"))
	  {
		  subStation=subStation.replace(/\//g, "_");
	  } 
	$.ajax({
		url:'./showListFdrConnToSub'+'/'+subStation,
		type:'GET',
		dataType:'json',
		asynch:false,
		cache:false,
		beforeSend: function(){
		    $('#imageee').show();
		},
		complete: function(){
		    $('#imageee').hide();
		},
		success:function(response)
		{
			if(response=='' || response==null)
			{
			 	$('#imageee').hide();
				bootbox.alert("No Data Available");
				return false;
			}
			else {
			 var html2 = "<thead ><tr style='background-color: lightgray;'><th style='text-align: center;'>SLNO.</th><th style='text-align: center;'>Feeder Name</th><th style='text-align: center;'>District</th><th style='text-align: center;'>Meter No.</th><th style='text-align: center;'>IMEI No</th><th style='text-align: center;'>Status</th></tr></thead><tbody>";
		    	    for(var j=0;j<response.length;j++)
		    		{ 	
		    		     html2 += "<tr style='text-align: center;'><td>"+(j+1)+"</td>";
		    		     html2 += "<td style='text-align: center;'>"+response[j][0]+"</td>";
		    		     html2 += "<td style='text-align: center;'>"+response[j][1]+"</td>"; 
		    		     html2 += "<td  style='text-align: center;'><a style='color:blue;' onclick='return mtrDetails(\""+(response[j][2]).trim()+"\")'>"+response[j][2]+"</a></td>"; 
		    		     html2 += "<td style='text-align: center;'> <a style='color:blue;'  href='./modemDetails?METERNO="
								+ response[j][2]
								+ "&MODEM="
								+ response[j][3]
								+ "'</a>"+response[j][3]+"</td>";  
		    		     html2 += "<td style='text-align: center;'>"+response[j][4]+"</td>";  
		    		     
		    		     //html2 += "<tr style='text-align: center;'><td>"+(j+1)+"</td>";
		    		     /* html2 += "<td style='text-align: center;'>ABCD</td>";
		    		     html2 += "<td style='text-align: center;'>Bangalore</td>"; 
		    		     html2 += "<td style='text-align: center;'>123456</td>"; 
		    		     html2 += "<td style='text-align: center;'>0987654321</td></tr>"; 
		    		     html2 += "<td style='text-align: center;'>"+response[j][4]+"</td>";   */
		    		}
				
			html2+="</tbody>"; 
			$('#sample_4').html(html2);
			//$("#feederListBySubStn").html(html2);
	    	loadSearchAndFilter('sample_4'); }
			
		}
		}); 
}

function mtrDetails(mtrNo) {
	window.location.href = "./viewFeederMeterInfo?mtrno=" + mtrNo;
}

</script>

<div  class="page-content" >
	<div class="row">
		<div class="col-md-12">
			<c:if test="${results ne 'notDisplay'}">
				<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color: red; font-size: 15px;">${results}</span>
				</div>
			</c:if>
		</div>
	</div> 
	
	<div id="imageee" hidden="true" style="text-align: center;">
		<h3 id="loadingText">Loading..... Please wait.</h3>
		<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
			style="width: 4%; height: 4%;">
	</div>
	<div class="row">

		<!-- BEGIN MARKERS PORTLET-->
		<div class="portlet box green"
			id="gmapsContent">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-map-marker"></i>Map
				</div>
				<div class="tools">
					<a href="#" class="close" onclick="hideMap()"></a>
				</div>
			</div>
			<div class="portlet-body">
				<div id='appendMapVal'>
					<div id="map-canvas" class="gmaps"
						style="height: 700px; width: 100%;"></div>
				</div>
				<!-- END PORTLET-->
			</div>
		</div>

	</div>
	
	<div id="stack2" class="modal fade"
		style="display: none; overflow-y: scroll; overflow-x: hidden;"
		tabindex="-1" data-width="400" data-backdrop="static"
		data-keyboard="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button class="bootbox-close-button close" type="button"
						style="margin-top: 1px;" data-dismiss="modal">X</button>
					<h4 class="modal-title" id="header_stack2"></h4>
				</div>

				<div class="modal-body">
						<div class="portlet">
					
						<div class="portlet-body">
							<div class="table-responsive">
								<table class="table table-striped table-bordered table-advance table-hover" id="sample_4">
									<thead>
										<tr>
											 <th>SL No.</th> 
											<th>Feeder Name</th>
											<th>District</th>
											<th>Meter No.</th>
											<th>IMEI NO</th>
											<th>Status</th>
										</tr>
									</thead>
									<tbody id="feederListBySubStn">
										
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
				