<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/openlayers/4.6.5/ol.css" type="text/css">
 <script src="https://cdn.polyfill.io/v2/polyfill.min.js?features=requestAnimationFrame,Element.prototype.classList,URL"></script>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/openlayers/4.6.5/ol.js"></script>
<style>
.page-content .page-breadcrumb.breadcrumb>li>i {
	color: #fff;
}
.btn {
padding: 7px 33px;

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

.checkbox{
padding:5px;
}

</style>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&key=AIzaSyDFsv7MwN3q9GNl-kasQWAWqLtgAi1aaF4"></script>
		
<script>

var container = '';
var content = '';
var closer = '';
var  overlay='';
 

   
$(".page-content").ready(function() 
{
	//alert('${inActiveModems}');
	if ('${mdmMngtList.size()}' > 0) 
	{
		$('#zone').val('${zone}');
		$('#circle').val('${circle}');
		$('#division').val('${division}');
		$('#sdoCode').val('${subdiv}');
	}
	if('${mdmMngtList}'!="")
	{
		$('#viewFdrOnMapTab').show();
		$('#sdoCode').val('${subdiv}');
	}
	App.init();
	Index.initMiniCharts();
	TableEditable.init();
	$('#MDMSideBarContents,#mpmId,#gisview').addClass('start active ,selected');
	$('#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment').removeClass('start active ,selected');
	
	var clicklatlong='';
	initMap();

});
</script>

<script>
function showCircle(zone)
{
	$.ajax({
		url : "./getCircleByZone",
	    	type:'GET',
	    	dataType:'json',
	    	data:{zone:zone},
	    	asynch:false,
	    	cache:false,
	    	success:function(response)
	    	{
  			var html='';
	    		html+="<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control input-medium' type='text'><option value='noVal'>Circle</option>";
				for( var i=0;i<response.length;i++)
				{
					html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
				}
				html+="</select><span></span>";
				$("#circleTd").html(html);
				$('#circle').select2();
	    	}
		});
}

function showDivision(circle)
{
	
	var zone=$('#zone').val();
	$.ajax({
		url : "./getDivByCircle",
	    	type:'GET',
	    	dataType:'json',
	    	data:{circle:circle,
	    		zone:zone},
	    	asynch:false,
	    	cache:false,
	    	success:function(response)
	    	{
  			var html='';
	    		html+="<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value='noVal'>Division</option>";
				for( var i=0;i<response.length;i++)
				{
					html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
				}
				html+="</select><span></span>";
				$("#divisionTd").html(html);
				$('#division').select2();
	    	}
		});
}

function showSubdivByDiv(division)
{
	
	var zone=$('#zone').val();
	var circle=$('#circle').val();
	$.ajax({
			url : "./getSubdivandSitecodeByDiv",
		   	type:'GET',
	    	data:{division:division,
	    		zone:zone,
	    		circle:circle
	    		},
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response1)
	    	{
  			var html='';
	    		html+="<select id='sdoCode' name='sdoCode' class='form-control input-medium' type='text'><option value=''>Sub-Division</option>";
				for( var i=0;i<response1.length;i++)
				{
					//var response=response1[i];
					
					var data=response1[i];
					
					html+="<option  value='"+data[1]+"'>"+data[0]+"</option>";
				}
				html+="</select><span></span>";
				$("#subdivTd").html(html);
				$('#subdiv').select2();
	    	}
		});
}

function modemMngtVal(form1)
{
	if(form1.zone.value=='noVal')
  	{
  	   bootbox.alert('Please select Zone');
	   return false;
  	}
	if(form1.circle.value=='noVal')
  	{
  	   bootbox.alert('Please select Circle');
	   return false;
  	}
	if(form1.division.value=='noVal')
  	{
  	   bootbox.alert('Please select Division');
	   return false;
  	}
	if(form1.sdoCode.value=='noVal')
  	{
  	   bootbox.alert('Please select Sub-Division');
	   return false;
  	}
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
	

			<div class="row" style="margin-left: -1px;">
				<form action="./fdrOnMapdetailsMDAS" method="post">
				            <div class="form-group col-md-2 ">
								<select class="form-control select2me input-medium " name="zone" id="zone" onchange="showCircle(this.value);">
										<option value="noVal">Zone</option>
										<c:forEach var="elements" items="${zoneList}">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select>
								</div>
								
								 <div class="form-group col-md-2" id="circleTd"> 
								<select class="form-control select2me input-medium"
									id="circle" name="circle">
										<option value=''>Circle</option>
								</select></div>

								 <div class="form-group col-md-2" id="divisionTd"> 
									<select class="form-control select2me input-medium" id="division" name="division">
										<option value=''>Division</option>
										
										
								</select>
								</div>

								<div class="form-group col-md-2" id="subdivTd"> 
									<select class="form-control select2me input-medium" id="sdoCode" name="subdivision">
										<option value=''>Sub-Division</option>
								    </select>
								</div>
								<div class="form-group col-md-2" > 
									<button type= "button" viewFdrOnMap" onclick="return viewOnMap('ALL');" name="viewFdrOnMap" class="btn yellow"><b>View</b></button></div>
						</form>
					</div>
				
			
		
	 <div class="portlet box green">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>Meters Details on Map
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a href="javascript:;"
					class="reload"></a>
			</div>
		</div>
		<div class="portlet-body">
		<div class="row">
		<div id="map" class="map col-md-10"  style="height: 600px; width:83%;margin-left: 1%;"></div>
		<div  style="margin-top: -44%;" class="col-md-offset-10 col-md-2 ">
		<div class="checkbox" style="padding-left: 0%;">
		 <h4><b>Legends</b></h4>
		<div class=checkbox><label><img src="./resources/assets/img/dcu.gif" style="width:50px;height:42px;"><b><span id="activedcu" > ACTIVE DCU</span></b></label></div>
		<div class=checkbox><label><img src="./resources/assets/img/dcu_off.png" style="width:43px;height:36px;"><b><span style="margin-left:2px;" id="inactivedcu">INACTIVE DCU</span></b></label></div>
		<div class=checkbox><label><img src="./resources/assets/img/meter_on.png" style="width:35px;height:32px;"><b><span style="margin-left: 2px;" id="activemeter"> ACTIVE METER</span></b></label></div>
		<div class=checkbox><label><img src="./resources/assets/img/meter_off.png" style="width:35px;height:32px;"><b><span style="margin-left: 2px;" id="inactivemeter">INACTIVE METER</span></b></label></div>
		</div>
		<br>
		<br>
		  <div class=checkbox><label><input type=checkbox class="styled"  id="lineid" onclick="DCTlineDraw();" checked="checked"><span style="margin-left: 12px;"><b>LINES</b></span></label></div>
		  <div><label style="margin-left: 20px;">DCU TO METER</label></div>
		  <div><img src="./resources/assets/img/line.jpg" style="width: 50%; margin-left: 20px;"></div>
		  <div><label style="margin-left: 20px;">METER TO METER</label></div>
		  <div><img src="./resources/assets/img/line2.jpg" style="width: 50%; margin-left: 20px;"></div>
		</div>	
		</div>
		</div>
		</div>
		</div>
	
	
<script>
var lineList=[];
var map;
var india={lat: 26.606522599999998, lng: 77.46907709999999}
function initMap() {
	map = new google.maps.Map(document.getElementById('map'), 
		{ 
	      center:india,
	      zoom: 6 ,	 
	      mapTypeControl: true,
	      scaleControl: true,
	      gestureHandling: 'greedy',
	      zoomControlOptions: {style: google.maps.ZoomControlStyle.LARGE },
	      mapTypeId: google.maps.MapTypeId.ROADMAP ,
	      mapTypeControlOptions: {
	                              style: google.maps.MapTypeControlStyle.DROPDOWN_MENU,
	                              position: google.maps.ControlPosition.TOP_RIGHT
	                             } 	    	      
	   }); 
}  

var linedrawObject=[];

function viewOnMap(flag)
{
	var location=$("#sdoCode").val();
	linedrawObject=[];
	initMap();
  if(location !='')
  {
	  if(flag ==='ALL')
	  {
		  $.ajax({
		    	url:'./getViewDataBySubdivision',
		    	type:'post',
		    	dataType:'json',
		    	data:{subdivision:location},
		    	asynch:false,
		    	cache:false,
		    	success:function(res)
		    	{
		    		var totaldata=res[0];
		    	    var countData=totaldata[0];
		    		
		    	    var totaldcu=countData[0];
		    	    var totalmeter=countData[1];
		    	    var commdcu=countData[2];
		    	    var nonCommdcu=countData[3];
		    	    var commMeter=countData[4];
		    	    var noncommMeter=countData[5];
		    	    
		    	    $("#activedcu").text("ACTIVEDCU("+commdcu+")");
		    	    $("#inactivedcu").text("INACTIVEDCU("+nonCommdcu+")");
		    	    
		    	    $("#activemeter").text("ACTIVEMETER("+commMeter+")");
		    	    $("#inactivemeter").text("INACTIVEMETER("+noncommMeter+")");
		    	    
		    		
		    		var response=res[1];
		    		if(response.length>0)
		    		{
		    			
		    			var markers=[];
		    		    var contents=[];
		    		    var infowindows =[];
		    		    var url="";
				  	      for(var i=0;i<response.length;i++)
				  		       {
				  		            	data=response[i];
				  		            	
				  		            	var node_id=data[0];
				  		            	var latitude=data[1];
				  		            	var longitude=data[2];
				  		            	var status=data[3];
				  		            	var sdocode=data[4];
				  		            	var dcu_sn=data[5];
				  		            	var dcu_name=data[6];
				  		            	var metercount=data[7]+"";
				  		            	var connstatus='';
				  		            	if(latitude !='' && longitude !='')
				  		            	{
				  		            		  zoomlng=longitude;
					  		            	  zoomlat=latitude;
				  		            	}
				  		            	if( status=='C' )
				  		            	{
				  		            		 url='./resources/assets/img/dcu.gif';
				  		            		connstatus="<div><h3 style=color:green;><b>Connected</b></h3></div>";
				  		            	}
				  		            	else if( status=='N' )
				  		            	{
				  		            		 url='./resources/assets/img/dcu_off.png';
				  		            		connstatus="<div><h3 style=color:green;><b>Disconnected</b></h3></div>";
				  		            	}
				  		            	
				  		            	var icon = {
				  		            		     url: url, // url
				  		            		     scaledSize: new google.maps.Size(60, 50), // size
				  		            		     origin: new google.maps.Point(0,0), // origin
				  		            		     anchor: new google.maps.Point(0, 0) // anchor 
				  		            		 };
				  		            	
                                        	var latlng1 = new google.maps.LatLng(parseFloat(latitude), parseFloat(longitude));
     				  		            	markers[i]= new google.maps.Marker({
     				  			  		          map: map,
     				  			  		          position: latlng1,
     				  			  		          icon:icon,
     				  			  		          label:metercount,
     				  			  		          title: node_id
     				  			  		      });
     				  			  		    markers[i].index = i;
     				  			  		    contents[i] ="<div>"
     				  			  		          +connstatus
     							                  +"<table  id=billTable1 class='table table-striped table-bordered table-hover' style='background: #99ccff;'>"
     											  +"<tr><th>SDO CODE</th><td>"+sdocode+"</td></tr>"
     											  +"<tr><th>NODE_ID</th><td>"+node_id+"</td></tr>"
     											  +"<tr><th>DCU NAME</th><td>"+dcu_name+"</td></tr>"
     											  +"</table></div>";

     				  			  		    infowindows[i] = new google.maps.InfoWindow({
     				  			  		        content: contents[i],
     				  			  		        maxWidth: 300
     				  			  		        });
     				  			  		   markers[i].set('nodeid',node_id);
     				  			  		   markers[i].set('sdocode',sdocode);
     				  			  		   google.maps.event.addListener(markers[i], 'click', function() 
     				  			  		   {
     				  			  			   
     				  			  			    var nodeid = this.get('nodeid');
     		    			  		    	    var sdocode = this.get('sdocode');
     		    			  		    	    deletemarkers();
     		    			  		    	    getMeterDataByDCU(nodeid,sdocode);
     		    			  		    	 for(var j=0;j<lineList.length;j++)
     		    			  				{
     		    			  					lineList[j].setMap(null);
     		    			  				}
     				  			  	            infowindows[this.index].open(map,markers[this.index]);
     				  			  	            map.panTo(markers[this.index].getPosition());
     				  			  	            
     				  			  	       
     				  			  	      });
                                     
		    		           }
				  	       map.setCenter({lat:parseFloat(zoomlat), lng: parseFloat(zoomlng)}),
		    	 		   google.maps.event.addListenerOnce(map, 'bounds_changed', function() 
		    	 			{
		    				  map.setZoom(17);
		    				});
				  	      
		    		}	            
		    		else
		    		{
		    			alert("No data");
		    		}
		    	}
			});  
	  }
  } 
  else
  {
		bootbox.alert("Select SubDivion");
		return false;
  }
	
	
}
var markers=[];
function getMeterDataByDCU(nodeid,sdocode)
{
$.ajax({
	url:'./getMeterDataByNodeId',
	type:'post',
	dataType:'json',
	data:{nodeid:nodeid,sdocode:sdocode},
	asynch:false,
	cache:false,
	success:function(response)
	{
		if(response.length>0)
		{
			
		    var contents=[];
		    var infowindows =[];
		    linedrawObject=response;
			for(var i=0;i<response.length;i++)
	            {
				 var data=response[i];
				 var name=data[0];
				 var address=data[1];
				 var accno=data[2];
				 var mtrno=data[4];
				 var node_id=data[7];
				 var duc_id=data[6];
				 var latitude=data[8];
				 var longitude=data[9];
				 var status=data[11];
				 var connstatus='';
				 var dtcCode=data[15];
				 
				 if( status=='C' )
	            	{
	            		 url='./resources/assets/img/meter_on.png';
	            		 connstatus="<div><h3 style=color:green;><b>Connected</b></h3></div>";
	            	}
	            	else if( status=='N' )
	            	{
	            		 url='./resources/assets/img/meter_off.png';
	            		 connstatus="<div><h3 style=color:red;><b>Disconnected<b></h3></div>";
	            	}
				 
				 var icon = {
	            		     url: url, // url
	            		     scaledSize: new google.maps.Size(25, 20), // size
	            		     origin: new google.maps.Point(0,0), // origin
	            		     anchor: new google.maps.Point(0, 0) // anchor 
	            		 };
                 	var latlng1 = new google.maps.LatLng(parseFloat(latitude), parseFloat(longitude));
		            	markers[i]= new google.maps.Marker({
			  		          map: map,
			  		          position: latlng1,
			  		          icon:icon,
			  		          title: node_id
			  		      });
			  		    markers[i].index = i;
			  		    contents[i] ="<div>"
			  		    	   +connstatus
			                  +"<table  id=billTable1 class='table table-striped table-bordered table-hover' style='background: #99ccff;'>"
							  +"<tr><th>Name</th><td>"+name+"</td></tr>"
							  +"<tr><th>Address</th><td>"+address+"</td></tr>"
							  +"<tr><th>Account NO</th><td>"+accno+"</td></tr>"
							  +"<tr><th>Meter NO</th><td>"+mtrno+"</td></tr>"
							  +"<tr><th>DTC CODE</th><td>"+dtcCode+"</td></tr>"
							  +"<tr><th>GateWay ID</th><td>"+duc_id+"</td></tr>"
							  +"<tr><th>Node ID</th><td>"+node_id+"</td></tr>"
							  +"</table></div>";

			  		    infowindows[i] = new google.maps.InfoWindow({
			  		        content: contents[i],
			  		        maxWidth: 300
			  		        });
			  		   
			  		   google.maps.event.addListener(markers[i], 'click', function() 
			  		   {
			  	            infowindows[this.index].open(map,markers[this.index]);
			  	            map.panTo(markers[this.index].getPosition());
			  	         
			  	       });
	            }
			DCTlineDraw();
		  
		}
	}
});
}


function deletemarkers()
{
	for (var i = 0; i < markers.length; i++ ) {
	    markers[i].setMap(null);
	  }
	  markers.length = 0;
}

function   addmarker(lat,lng,id,urls,content,map)
{
   var infowindow = new google.maps.InfoWindow();
   var newMarker = new google.maps.Marker({
            position: new google.maps.LatLng(lat,lng),
            url:'./resources/assets/img/dcu.gif',
            map: map,
            title: id
        });
        google.maps.event.addListener(newMarker, 'click', (function (newMarker, i) {
            return function () {
                infowindow.setContent(content);
                infowindow.open(map, newMarker);
            }
        })(newMarker, id));

}
var DCUTOMETER="";
function DCTlineDraw()
{
	if(document.getElementById("lineid").checked==true)
	{
		
		for(var i=0;i<linedrawObject.length;i++)
	    {
		  var data=linedrawObject[i];
		  var latitude=data[8];
		  var longitude=data[9];
		  var gatewayid=data[6];
		  var parent_id=data[10];
		  var platitude=data[13];
		  var plongitude=data[14];	  
		  
		  var Linepath=[];
		
		  var startpoint = {lat:parseFloat(latitude), lng: parseFloat(longitude)};
		  var endpoint = {lat:parseFloat(platitude), lng: parseFloat(plongitude)};
		  var colurcode='#F08080';
		  if(gatewayid !==parent_id)
		  {
			  colurcode='#A50063';
		  }
		  Linepath[0]=startpoint;
		  Linepath[1]=endpoint;
		  DCUTOMETER= new google.maps.Polyline({
	        path: Linepath,
	        strokeColor:colurcode,
	        strokeOpacity: 1,
	        strokeWeight: 2
	      });
		   DCUTOMETER.setMap(map);
		   lineList[i]=DCUTOMETER;
	    } 
		
	}
	else
	{
		for(var j=0;j<lineList.length;j++)
		{
			lineList[j].setMap(null);
		}
	}
	
}

</script>	
	
	
				