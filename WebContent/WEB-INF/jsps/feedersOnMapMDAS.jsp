<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
</style>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&key=AIzaSyDFsv7MwN3q9GNl-kasQWAWqLtgAi1aaF4"></script>
		
<script>
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
	$('#MDMSideBarContents,#mpmId,#fdrOnMap').addClass('start active ,selected');
	$('#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment').removeClass('start active ,selected');

});
</script>

<script>
function showCircle(zone)
{
	$.ajax({
	    	url:'./showCircleMDAS'+'/'+zone,
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response)
	    	{
  			var html='';
	    		html+="<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control input-medium' type='text'><option value='noVal'>Circle</option><option value='All'>ALL</option>";
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

//for google maps
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
	      mapTypeId: google.maps.MapTypeId.HYBRID,
	      mapTypeControlOptions: {
	                              style: google.maps.MapTypeControlStyle.DROPDOWN_MENU,
	                              position: google.maps.ControlPosition.TOP_RIGHT
	                             } 	    	      
	   }); 
}  

function viewOnMap()
{
	
	var mtrNum =  "8000003";
	
		$.ajax({
		  	url : './getAllViewOnMapMtrDataMDAS/'+mtrNum,
		  //	url : 'http://220.227.2.202:7070/bsmartsurvey/getMeterData?kno='+kno,
		  	type:"GET",
		  	dataType : "json",
			cache:false,
			async:false,
		  	beforeSend: function(){
			        $('#imageee').show();
			        $('#gmapsContent').hide(); 
			    },
			    complete: function(){
			        $('#imageee').hide();
			        $('#gmapsContent').show();
			    },
		  	success:function(response1)
		 	{

		    	$('#gmapsContent').show();
		          var initialLocation=false;
		    	  for(var i=0;i<response1.length;i++)
					{ 
		    		  
		    		  var count=i+1;
		    		  var response=response1[i];
		    		 
		    		  if(!initialLocation)
		    			  {	
						         initMap();
			    			     initialLocation=true;
		    			  }
		    		  
		    		if(initialLocation)
	    			 {
		    			var content="<div>"
			                  +"<table  id=billTable1 class='table table-striped table-bordered table-hover' style='background: #99ccff;'>"
							  +"<tr><th>Consumer Name</th><td>"+response[0]+"</td></tr>"
							  +"<tr><th>Meter No</th><td>"+response[1]+"</td></tr>"
							  +"<tr><th>DLMS</th><td>DLMS</td></tr>"
							  +"<tr><th>Latitude</th><td>"+response[3]+"</td></tr>"
							  +"<tr><th>Longitude</th><td>"+response[4]+"</td></tr>"
							  +"</table></div>";
							  
					         var lat=parseFloat(response[3]);
					         var longitide= parseFloat(response[4]);
					         var meterno=response[1];
					         addmarker(lat,longitide,meterno,content,map);
		    			 } 				    		  		    		  
					}
		    	  
		     if(!initialLocation)
			  {
		    	$('#gmapsContent').hide(); 
			  	document.getElementById('gmapsContent').style.display='none';
			  	bootbox.alert('No Data Available');
			  }
	    	}
		});
		$("#closeShow1").ready(function(){
		    $("html, body").delay(10).animate({
		        scrollTop: $('#closeShow1').offset().top 
		    }, 1000);
		});
	   return false;
}

function   addmarker(lat,lng,id,content,map)
{
   var infowindow = new google.maps.InfoWindow();
   var newMarker = new google.maps.Marker({
            position: new google.maps.LatLng(lat,lng),
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

function showDivision(circle)
{
	var zone=$('#zone').val();
	$.ajax({
	    	url:'./showDivisionMDAS'+'/'+zone+'/'+circle,
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response)
	    	{
  			var html='';
	    		html+="<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value='noVal'>Division</option><option value='All'>ALL</option>";
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
	    	url:'./showSubdivByDivMDAS'+'/'+zone+'/'+circle+'/'+division,
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response1)
	    	{
  			var html='';
	    		html+="<select id='sdoCode' name='sdoCode' class='form-control input-medium' type='text'><option value='noVal'>Sub-Division</option><option value='All'>ALL</option>";
				for( var i=0;i<response1.length;i++)
				{
					//var response=response1[i];
					html+="<option  value='"+response1[i]+"'>"+response1[i]+"</option>";
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

<script>

function showSStaionBySubdivByDiv(subdiv)
{
	  $.ajax({
	    	url:'./showSStaionBySubdivByDivMDAS'+'/'+subdiv,
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response1)
	    	{
  			var html='';
	    		html+="<select id='subStation' name='subStation' class='form-control input-medium' type='text'><option value='noVal'>Sub-Station</option><option value='All'>ALL</option>";
				for( var i=0;i<response1.length;i++)
				{
					//var response=response1[i];
					html+="<option  value='"+response1[i]+"'>"+response1[i]+"</option>";
				}
				html+="</select><span></span>";
				$("#subStationTd").html(html);
				$('#subStation').select2();
	    	}
		});
}

function fdrOnMapVal(form1)
{
	if(form1.sdoCode.value=='noVal')
  	{
  	   bootbox.alert('Please select Sub-Division');
	   return false;
  	}
	if(form1.subStation.value=='noVal')
  	{
  	   bootbox.alert('Please select Sub-Station');
	   return false;
  	}
	
} 

function showSubStnBySubDiv(subDiv)
{
	$('#appendMapVal').html('');
	var html='<div id="map-canvas" class="gmaps" style="height: 500px;"></div>';
	$('#appendMapVal').html(html);
	
	$.ajax({
	  	url : './getSubstnLatLongBySubDivMDAS/'+subDiv,
	  	type:"GET",
	  	dataType : "json",
		cache:false,
		async:false,
	  	beforeSend: function(){
		        $('#imageee').show();
		        $('#gmapsContent').hide(); 
		    },
		    complete: function(){
		        $('#imageee').hide();
		        $('#gmapsContent').show();
		    },
	  	success:function(response1)
	 	{
	    	
	    	$('#gmapsContent').show();
	          var initialLocation=false;
	          var map="";
	    	  for(var i=0;i<response1.length;i++)
				{ 
	    		  var count=i+1;
	    		  var response=response1[i];
	    		
	    		  if(!initialLocation)
	    			  {	
  							map = new GMaps({
					            div: '#map-canvas',
					            lat: parseFloat(response[2]),
					            lng :parseFloat(response[3]),
					            zoom:10,
					            gestureHandling: 'greedy',
						        fullscreenControl: true,
					        });
		    			     initialLocation=true;
	    			  }
	    		  
	    		if(initialLocation)
    			 {
						   map.addMarker({
				            lat: parseFloat(response[2]),
				            lng: parseFloat(response[3]),
				            //icon:'./resources/assets/img/active.gif',
				            optimized: false,
				            //animation: google.maps.Animation.DROP,
				            
				             title:"SubStation Name- "+response[0],
				             infoWindow: {
				                  content: 
				                  "<div>"
				                  +"<table  id=billTable1 class='table table-striped table-bordered table-hover' style='background: #99ccff;'>"
								  +"<tr><th>Substation Name</th><td>"+response[0]+"</td></tr>"
								  +"<tr><th>District</th><td>"+response[1]+"</td></tr>"
								  +"<tr><th>Latitude</th><td>"+response[2]+"</td></tr>"
								  +"<tr><th>Longitude</th><td>"+response[3]+"</td></tr>"
								  +"<tr><th>No Of Feeders</th><td><a onclick='return getListFdrToSubSt(\""+(response[0]).trim()+"\")'>"+response[4] +"</a></td></tr>"
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
    	}
	});
   return false;
}

/* function showModemStatus(subName)
{	
	  var subDiv=$('#sdoCode').val();
	   if(subName.includes("."))
	  {
		   subName=subName.replace(/\./g, "@");
	  }
	  if(subName.includes("/"))
	  {
		  subName=subName.replace(/\//g, "_");
	  } 
		$('#appendMapVal').html('');
		var html='<div id="map-canvas" class="gmaps" style="height: 500px;"></div>';
		$('#appendMapVal').html(html);
		
		$.ajax({
		  
		  url : './longitudeLatitude/'+subDiv+'/'+subName,
		  type:"GET",
		  dataType : "json",
			cache:false,
			async:false,
		  beforeSend: function(){
		        $('#imageee').show();
		        $('#gmapsContent').hide(); 
		    },
		    complete: function(){
		        $('#imageee').hide();
		        $('#gmapsContent').show();
		    },
		  success:function(response1)
		  {
				 
		    	//alert('----response----'+response);
		    	$('#gmapsContent').show();
		          var initialLocation=false;
		          var map="";
		    	  for(var i=0;i<response1.length;i++)
					{ 
		    		  var count=i+1;
		    		  var response=response1[i];
		    		  if(!initialLocation)
		    			  {	
	  							map = new GMaps({
						            div: '#map-canvas',
						            lat: parseFloat(response[3]),
						            lng :parseFloat(response[2]),
						            zoom:7,
						            gestureHandling: 'greedy',
							        fullscreenControl: true,
						        });
			    			     initialLocation=true;
		    			  }
		    		  
		    		if(initialLocation)
	    			 {
							if(response[6]=="Active")
							{
							   map.addMarker({
					            lat:response[3],
					            lng:response[2],
					            icon:'./resources/assets/img/active.gif',
					            optimized: false,
					            animation: google.maps.Animation.DROP,
					            
					             title:"Modem No- "+response[1],
					             infoWindow: {
					                  content: 
					                  "<div>"
					                    +"<table  id=billTable1 class='table table-striped table-bordered table-hover' style='background: #99ccff;'>"
									  +"<tr><th>Modem No</th><td ><a href='./modemDetailsInactive?modem_sl_no="+response[1]+"&mtrno="+response[4]+"&substation="+response[5]+"' style='color:DarkCyan;'>"+response[1]+"</td></tr>"
									  +"<tr><th>Zone Name</th><td>"+response[0]+"</td></tr>"
									  +"<tr><th>Meter No</th><td><a onclick='return mtrDetails(\""+(response[4]).trim()+"\")'>"+response[4] +"</a></td></tr>"
									  +"<tr><th>Status</th><td><font color='green'><b>"+response[6] +"</b></font></td></tr>"
									  +"<tr><th>Latitude</th><td>"+response[3]+"</td></tr>"
									  +"<tr><th>Longitude</th><td>"+response[2]+"</td></tr>"
									  +"</table></div>"
					              } 
							   });
						   }
						   if(response[6]=="Inactive")
							{
								   map.addMarker({
						            lat:response[3],
						            lng:response[2],
						            icon:'./resources/assets/img/inactive.gif',
						            optimized: false,
						            animation: google.maps.Animation.DROP,
						            
						             title:"Modem No- "+response[1],
						             infoWindow: {
						                  content: 
						                	  "<div>"
							                    +"<table  id=billTable1 class='table table-striped table-bordered table-hover' style='background: #99ccff;'>"
											  +"<tr><th>Modem No</th><td ><a href='./modemDetailsInactive?modem_sl_no="+response[1]+"&mtrno="+response[4]+"&substation="+response[5]+"' style='color:DarkCyan;'>"+response[1]+"</td></tr>"
											  +"<tr><th>Zone Name</th><td>"+response[0]+"</td></tr>"
											  +"<tr><th>Meter No</th><td><a onclick='return mtrDetails(\""+(response[4]).trim()+"\")'>"+response[4] +"</a></td></tr>"
											  +"<tr><th>Status</th><td><font color='red'><b>"+response[6] +"</b></font></td></tr>"
											  +"<tr><th>Latitude</th><td>"+response[3]+"</td></tr>"
											  +"<tr><th>Longitude</th><td>"+response[2]+"</td></tr>"
											  +"</table></div>"
						              } 
								   });
						    }
		    			 } 				    		  		    		  
					}
		    	  
		     if(!initialLocation)
			  {
		    	 $('#gmapsContent').hide(); 
			  document.getElementById('gmapsContent').style.display='none';
			  bootbox.alert('No Data Available');
				
			  }
			    	}
				}		
		       );
	   return false;
} */

function mtrDetails(mtrNo)
{
	mtrNum=mtrNo;
	window.location.href="./viewFeederMeterInfoMDAS?mtrno=" + mtrNo;
}

function getListFdrToSubSt(subStation)
{
	//PIECHART====================================================
	$.ajax({
				url : "./getFeedersByInstalledMDAS/"+subStation,
				data : "",
				type : "GET",
				dataType : "text",
				async : false,
				success : function(response) {
					var object = JSON.parse(response);
					
					Highcharts
							.chart(
									'containerPieChart',
									{
										chart : {
											plotBackgroundColor : null,
											plotBorderWidth : null,
											plotShadow : false,
											type : 'pie'
										},
										title : {
											text : ''
										},
										tooltip : {
											pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
										},
										plotOptions : {
											pie : {
												allowPointSelect : true,
												cursor : 'pointer',
												dataLabels : {
													enabled : false
												},
												showInLegend : true
											}
										},
										series : [ {
											name : '',
											colorByPoint : true,
											data : object.graphData
										} ]
									});
				}
			});
	
	//var subDivision='${subdiv}';
		$('#gmapsContent').hide();
 		$('#mobileDiv').hide();
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
    		/* var html2 = "<thead ><tr style='background-color: lightgray;'><th style='text-align: center;'>SLNO.</th><th style='text-align: center;'>Feeder Name</th><th style='text-align: center;'>Feeder Code</th><th style='text-align: center;'>District</th><th style='text-align: center;'>Meter No.</th><th style='text-align: center;'>DLMS</th></tr></thead><tbody>";
    	       		 
    	    	   for(var j=0;j<response.length;j++)
		    		{	
		    		     html2 += "<tr style='text-align: center;'><td>"+(j+1)+"</td>";
		    		     html2 += "<td style='text-align: center;'>"+response[j][0]+"</td>";
		    		     html2 += "<td style='text-align: center;'>"+response[j][1]+"</td>"; 
		    		     html2 += "<td style='text-align: center;'>"+response[j][2]+"</td>"; 
		    		     html2 += "<td style='text-align: center;'>"+response[j][3]+"</td>"; 
		    		     html2 += "<td style='text-align: center;'>"+response[j][4]+"</td>"; 
		    		}
	    		
	    	html2+="</tbody>"; */
	    	var html2="<ul class='list-group'>"
			+"<li class='list-group-item'>Total Feeder<span class='badge instantaneous'>"+response[0][0]+"</span></li>"
			+"<li class='list-group-item'>Modem Installed <span class='badge instantaneous'>"+response[0][1]+"</span></li>"
			+"<li class='list-group-item'>Pending<span class='badge instantaneous'>"+response[0][2]+"</span></li>"
			+"<li class='list-group-item'>View on Map<span class='badge instantaneous'><a onclick='return showFeederBySubStn(\""+subStation+"\")'><i class='fa fa-map-marker'></i></a></span></li>"
			+"</ul>";
			$("#fdrDetails").html(html2);
	    	$('#mobileDiv').show();
    	}
  }); 
}

function showFeederBySubStn(subStation)
{
	$('#appendMapVal').html('');
	var html='<div id="map-canvas" class="gmaps" style="height: 500px;"></div>';
	$('#appendMapVal').html(html);
	
	$.ajax({
	  	url : './getFeederBySubStnMDAS/'+subStation,
	  	type:"GET",
	  	dataType : "json",
		cache:false,
		async:false,
	  	beforeSend: function(){
		        $('#imageee').show();
		        $('#gmapsContent').hide(); 
		    },
		    complete: function(){
		        $('#imageee').hide();
		        $('#gmapsContent').show();
		    },
	  	success:function(response1)
	 	{
	    	//alert('----response----'+response);
	    	$('#gmapsContent').show();
	          var initialLocation=false;
	          var map="";
	    	  for(var i=0;i<response1.length;i++)
				{ 
	    		  var count=i+1;
	    		  var response=response1[i];
	    		  if(!initialLocation)
	    			  {	
  							map = new GMaps({
					            div: '#map-canvas',
					            lat: parseFloat(response[3]),
					            lng :parseFloat(response[4]),
					            zoom:18,
					            gestureHandling: 'greedy',
						        fullscreenControl: true,
					        });
		    			     initialLocation=true;
	    			  }
	    		  
	    		if(initialLocation)
    			 {
						   map.addMarker({
				            lat:response[3],
				            lng:response[4],
				            //icon:'./resources/assets/img/active.gif',
				            optimized: false,
				            //animation: google.maps.Animation.DROP,
				            
				             title:"Feeder Name- "+response[0],
				             infoWindow: {
				                  content: 
				                  "<div>"
				                  +"<table  id=billTable1 class='table table-striped table-bordered table-hover' style='background: #99ccff;'>"
								  +"<tr><th>Feeder Name</th><td>"+response[0]+"</td></tr>"
								  +"<tr><th>Meter No</th><td><a style='color:blue;' onclick='return mtrDetails(\""+response[1]+"\")'>"+response[1]+"</a></td></tr>"
								  +"<tr><th>DLMS</th><td>"+response[2]+"</td></tr>"
								  +"<tr><th>Latitude</th><td>"+response[3]+"</td></tr>"
								  +"<tr><th>Longitude</th><td>"+response[4]+"</td></tr>"
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
    	}
	});
   return false;
}
var tableToExceldata = (function() {
	
    var uri = 'data:application/vnd.ms-excel;base64,'
      , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
      , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s)))}
      , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; })}
    return function(table, name) {
      if (!table.nodeType) table = document.getElementById(table)
      var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
      document.getElementById("excelExport").href = uri + base64(format(template, ctx));
      document.getElementById("excelExport").download = name+'_'+moment().format("DD-MM-YYYY_hh.mm.ss")+'.xls';
    }
  })();
function mlddata(){
	var zone=$("#zone").val();
	var circle=$("#circle").val();
	var division=$("#division").val();
	var sdoCode=$("#sdoCode").val();
	if(zone=="noVal" || zone=="All"){
		zone="%";
	}
	else{
		zone=zone+"%";	
	}
	if(circle=="noVal" || circle=="All"){
		circle="%";
	}
	else{
		circle=circle+"%";	
	}
	if(division=="noVal" || division=="All"){
		division="%";
	}
	else{
		division=division+"%";	
	}
	if(sdoCode=="noVal" || sdoCode=="All"){
		sdoCode="%";
	}
	else{
		sdoCode=sdoCode+"%";	
	}
	$.ajax({
		url:'./meterLocationData',
		type:'get',
		data:{
			zone:zone,
			circle:circle,
			division:division,
			sdoCode:sdoCode
		},
		success:function(res){
			var html='';
			$.each(res,function(i,v){
				html+='<tr><td>'+(i+1)+'</td><td>'+v[0]+'</td><td>'+v[1]+'</td><td>'+v[2]+'</td><td>'+v[3]+'</td><td>'+v[4]+'</td><td>'+v[5]+'</td><td>'+v[6]+'</td><td>'+v[7]+'</td></tr>';
			});
			$("#mldbodyid").html(html);
			tableToExceldata('sample_1', 'Meter Location Details');
		}
	});
	
	
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
	<div class="portlet box green">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>Meters on Map
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a href="javascript:;"
					class="reload"></a>
			</div>
		</div>

		<div class="portlet-body">
			<div class="row" style="margin-left: -1px;">
				<form action="./fdrOnMapdetailsMDAS" method="post">
					<table style="width: 38%">
						<tbody>
							<tr>
								<td><select class="form-control select2me input-medium" name="zone" id="zone" onchange="showCircle(this.value);">
										<option value="noVal">Zone</option>
										<option value='All'>ALL</option>
										<c:forEach var="elements" items="${zoneList}">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select></td>
								
								<td id="circleTd"><select class="form-control select2me input-medium"
									id="circle" name="circle">
										<option value='noVal'>Circle</option>
										<option value='All'>ALL</option>
										<c:forEach items="${circleList}" var="elements">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select></td>

								<td id="divisionTd"><select
									class="form-control select2me input-medium" id="division"
									name="division">
										<option value='noVal'>Division</option>
										<option value='All'>ALL</option>
										<c:forEach items="${divisionList}" var="d">
										
										
                            <li ><input id="${d.id}" name="${d.id}" type="checkbox" > <label
                             for="${d.id}"  class="lbl" > ${d.divisionList}</label>
                            </li>
										
										
										<%-- 	<option value="${elements}">${elements}</option> --%>
										</c:forEach>
								</select></td>

								<td id="subdivTd"><select
									class="form-control select2me input-medium" id="sdoCode"
									name="sdoCode">
										<option value='noVal'>Sub-Division</option>
										<option value='All'>ALL</option>
										<c:forEach items="${subdivList}" var="sdoVal">
											<option value="${sdoVal}">${sdoVal}</option>
										</c:forEach>
								</select></td>
								
								<td>
								<button type= "button" viewFdrOnMap" onclick="return viewOnMap();" name="viewFdrOnMap" class="btn yellow"><b>View</b></button>
								</td><td>
								<button type= "button"   name="exporttoexcel" class="btn green"><a href="#" id="excelExport"
									onclick="mlddata()">Export
										to Excel</a></button>
								<table class="table table-striped table-hover table-bordered" id="sample_1" style="display: none">
				<thead>
					<tr>
					<th style="text-align: center; color: white; background: #cacfd2">Id</th>
						<th style="text-align: center; color: white; background: #cacfd2">Zone</th>
						<th style="text-align: center; color: white; background: #cacfd2">Circle</th>
						<th style="text-align: center; color: white; background: #cacfd2">Division</th>
						<th style="text-align: center; color: white; background: #cacfd2">Sub Division</th>
						<th style="text-align: center; color: white; background: #cacfd2">Consumer Name</th>
						<th style="text-align: center; color: white; background: #cacfd2">Meter Number</th>
						<th style="text-align: center; color: white; background: #cacfd2">Longitude</th>
						<th style="text-align: center; color: white; background: #cacfd2">Latitude</th>
					</tr>
				</thead>
				<tbody id="mldbodyid" >

					
				</tbody>
			</table>
								<!-- <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button> -->
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<div class="modal-body" id="closeShow1">
										<div class="row">				
											<!-- BEGIN MARKERS PORTLET-->
											<div id="map"  style="height: 700px; width:96%;margin-left: 2%;"></div>
				</div>
											<!-- <div class="portlet box green" style="display: none;" id="gmapsContent">
												<div class="portlet-title">
													<div class="caption"><i class="fa fa-reorder"></i>GMAP VIEW</div>
												</div>
												<div class="portlet-body">
													<div id="gmap_marker" class="gmaps" style="height: 500px"></div>				 
					               				END PORTLET
						              			</div>
				              				</div> -->
               							</div>
									</div>
					<jsp:include page="modalPhotoMDAS.jsp" />	
		</div>
	</div>
	
	<div class="portlet box blue" id="viewFdrOnMapTab" style="display: none;">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-globe"></i>Meter On Map Details
				<c:if test="${not empty subdiv}">
					&nbsp;&nbsp;&nbsp;[SUB-DIVISION:-${subdiv}]
				</c:if>
				<c:if test="${not empty subStation}">
					&nbsp;&nbsp;&nbsp;[SUB-STATION:-${subStation}]
				</c:if>
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>
		<div class="portlet-body" id="viewFOM">
			<!-- <div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_editable_1', 'Total Meters')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div> -->
			<table class="table table-striped table-hover table-bordered">
				<thead>
					<tr>
						<th style="text-align: center; color: white; background: #cacfd2">View on Map</th>
						<th style="text-align: center; color: white; background: #cacfd2">Sub-Division</th>
						<th style="text-align: center; color: white; background: #cacfd2">No.of Substation</th>
					</tr>
				</thead>
				<tbody>

					<c:forEach var="element" items="${mdmMngtList}">
						<tr style="text-align: center;">
							<td><a href="#" id="view1" title="View Map" onclick="return showSubStnBySubDiv('${element[0]}')"><i class="fa fa-map-marker"></i></a></td>
							<td>${element[0]}</td>
							<%-- <td><a href='#' onclick="return getListFdrToSubSt('${element[0]}');" >${element[1]}</a></td> --%>
							 <td>${element[1]}</td> 
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	
	<div class="portlet box blue" id="mobileDiv" style="display: none;" style="margin-bottom: 0px; margin-top: 10px;">
						<div class="portlet-title blue">
									<label 
				style='color: #000000; font-size: 14px; float:left;'><span style="color: white;"><i class="fa fa-flash" style="font-size: medium;"></i> Feeder Data </span> </label>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								
							</div>
						</div>
						<div class="portlet-body">
			<div class="row">
			
				<br/><div class="col-sm-5" id="fdrDetails">
				
				</div>
				
				<div class="col-md-4">
			<div id="containerPieChart" style="width: 40%; height:200px;"></div> 
	</div>
			</div>

		</div>
	</div>
	
	<div id="imageee" hidden="true" style="text-align: center;">
		<h3 id="loadingText">Loading..... Please wait.</h3>
		<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
			style="width: 4%; height: 4%;">
	</div>
	<!-- <div class="row">

		BEGIN MARKERS PORTLET
		<div class="portlet box green" style="display: none;"
			id="gmapsContent">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-map-marker"></i>Map123
				</div>
				<div class="tools">
					<a href="#" class="close" onclick="hideMap()"></a>
				</div>
			</div>
			<div class="portlet-body">
				<div id='appendMapVal'>
					<div id="map-canvas" class="gmaps"
						style="height: 700px; width: 1090px;"></div>
				</div>
				END PORTLET
			</div>
		</div>

	</div>
	 -->
</div>
				