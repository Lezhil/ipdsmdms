<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<style>
.page-content .page-breadcrumb.breadcrumb>li>i {
	color: #fff;
}
.btn {
padding: 7px 33px;

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


<script>

$('#MDMSideBarContents,#dwnXMLData')
.addClass('start active ,selected');
					
</script>

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
		$('#frmDate').val('${cuurentDate}');
	}
	if('${mdmMngtList}'!="")
	{
		$('#viewFdrOnMapTab').show();
		$('#sdoCode').val('${subdiv}');
	}
	App.init();
	TableEditable.init();
	 FormComponents.init();
		$('#MDMSideBarContents,#360MeterDataViewID,#dwnXMLData').addClass('start active ,selected');
	/* $('#dwnXMLData').addClass('start active ,selected'); */
	$('#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment').removeClass('start active ,selected');

});
</script>

<script>
/* function showCircle(zone)
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
} */

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
	    		html+="<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control input-medium' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
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
						html += "<select id='sdoCode' name='sdoCode'  class='form-control input-medium' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
						for (var i = 0; i < response1.length; i++) {
							//var response=response1[i];
							html += "<option  value='"+response1[i]+"'>"
									+ response1[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#subdivTd").html(html);
						$('#sdoCode').select2();
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
	    	url:'./showSStaionBySubdivByDiv'+'/'+subdiv,
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

function showModemStatus(subName)
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
}

function mtrDetails(mtrNo)
{
	mtrNum=mtrNo;
	 window.location.href="./mtrNoDetails?mtrno=" + mtrNo;
}

function getListFdrToSubSt(subST)
{
	alert(subST);
}


function downlodingFile()
{
	var zn=$('#zone').val(); 
	var cir=$('#circle').val(); 
	var div=$('#division').val(); 
	var subdiv=$('#sdoCode').val(); 
	var fDate=$('#fileDate').val(); 
	window.open("./downlodingFileMDAS/"+zn+"/"+cir+"/"+div+"/"+subdiv+"/"+fDate);
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

<div  class="page-content" >
	<div class="row">
		<div class="col-md-12">
			<c:if test="${results ne 'notDisplay'}">
				<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${results}</span>
				</div>
			</c:if>
			
	<div class="portlet box green">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>Download XML Data
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a href="javascript:;"
					class="reload"></a>
			</div>
		</div>

		<div class="portlet-body">
			<div class="row" style="margin-left: -1px;">
				<form action="" method="post">
					<table style="width: 38%">
						<tbody>
							<tr>
								<td style="width:100px;"><b>Region:</b><select class="form-control select2me input-medium" name="zone" id="zone" onchange="showCircle(this.value);">
										<option value="noVal">Region</option>
										<c:forEach var="elements" items="${zoneList}">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select></td>
								
								<td id="circleTd" style="width:100px;"><b>Circle:</b><select class="form-control select2me input-medium"
									id="circle" name="circle">
										<option value='noVal'>Circle</option>
										<option value='%'>ALL</option>
										<c:forEach items="${circleList}" var="elements">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select></td>

								<td id="divisionTd" style="width:100px;"><b>Division:</b><select
									class="form-control select2me input-medium" id="division"
									name="division">
										<option value='noVal'>Division</option>
										<option value='%'>ALL</option>
										<c:forEach items="${divisionList}" var="elements">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select></td>

								<td id="subdivTd" style="width:100px;"><b>Sub-Division:</b><select
									class="form-control select2me input-medium" id="sdoCode"
									name="sdoCode">
										<option value='noVal'>Sub-Division</option>
										<option value='%'>ALL</option>
										<c:forEach items="${subdivList}" var="sdoVal">
											<option value="${sdoVal}">${sdoVal}</option>
										</c:forEach>
								</select></td>
								<td><b>Select&nbsp;Date</b>
									<div class="input-icon">
										<i class="fa fa-calendar"></i> <input
										class="form-control date-picker input-medium"  type="text"
										value=""  autocomplete="off"
										data-date-format="yyyy-mm-dd" data-date-viewmode="years" name="fileDate" id="fileDate"/>
									</div>
								</td>
								<td>
								<button id="viewFdrOnMap" onclick="return downlodingFile();" name="viewFdrOnMap" class="btn yellow" style="margin-top: 19px;"><b>Download XML</b></button>
								<!-- <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button> -->
								</td>
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
				