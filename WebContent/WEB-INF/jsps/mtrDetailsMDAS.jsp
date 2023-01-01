
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDFsv7MwN3q9GNl-kasQWAWqLtgAi1aaF4&libraries=geometry"></script> 
<script>

$(".page-content").ready(
		function() {
			App.init();
			TableEditable.init();
			FormComponents.init();
			loadSearchAndFilter('sample_1');
			$('#MDMSideBarContents,#metermang,#mtrmngDtls').addClass('start active ,selected');
			$("#MDASSideBarContents,#dash-board,#user-location,#MIS-Reports,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn").removeClass('start active ,selected');
			//showCircle();
			$('#LocationId').val('');
			//test();
		});
</script>
<script type="text/javascript">
var mtrNum;
function test(){
	alert("test");
}
function mtrDetails(mtrNo)
{
	mtrNum=mtrNo;
	window.location.href="./viewFeederMeterInfoMDAS?mtrno=" + mtrNo;
	 /* window.open("./viewFeederMeterInfo?mtrno="+ mtrNo,"_blank"); */
}

var name="";
var kno="";
var meterno="";
var address="";
var accno="";
var mtrdata="";
function getConsumerData(param1,param2,param3)
{
	//param1="92590381";
	getMeterLifeCycleData(param1);
	//param3='210474029414';
	$.ajax({  
    	url : './getSurveyDataImages?kno='+param3,
    	//url : 'http://192.168.4.229:6060/bsmartsurvey/getMeterData?kno=210463030033', 
    	type:'GET',
    	dataType:'json',
    	asynch:false,
    	cache:false,
    	success:function(obj)
    	{
        	 data =obj[0];
        	 lattitude=data[10];
        	 logititde=data[11];
        	 name=data[3];
        	 kno=data[1];
        	 meterno=data[6];
        	 address=data[4];
        	 accno=data[2];
        	 var result=mtrdata[0];
        	 if(result[1]==null)
        		{
        		 result[1]="";
        		 }
			if(result[3]==null)
			{
				result[3]="";
			}
			if(result[4]==null)
			{
				result[4]="";
			}
			if(result[5]==null)
			{
				result[5]="";
			}
			if(result[6]==null)
			{
				result[6]="";
			}
			if(result[7]==null)
			{
				result[7]="";
			}
			if(result[10]==null)
			{
				result[10]="";
			}
        	 
        	 var html ="<tr>"
					+" <td>"+result[0]+"</td>" //meterno
					+" <td>"+result[1]+"</td>" //phase
					+" <td>"+result[2]+"</td>" //mtrtype
					+" <td>"+result[3]+"</td>" //mtrmke
					+" <td>"+result[4]+"</td>" //category
					+" <td>"+result[6]+"</td>" // Location
					+" <td>"+result[7]+"</td>" // subdiv
					+" <td>"+result[10]+"</td>" // subdiv
					+" <td><a href='#' data-toggle='modal'  data-target='#popup_image'  onclick=viewDocument("+data[17]+",'meterimage');><b>&nbsp Meter Images</b></a> <a href='#' data-toggle='modal'  data-target='#GISVIEW'  onclick=viewInMap('"+lattitude+"','"+logititde+"','"+data[6]+"');><b>&nbsp&nbsp&nbspGIS VIEW</b></a></td>"
					+" </tr>"; 
				$("#meterBody").empty();
				$("#meterBody").append(html); 
        }
    });
}


function getMeterLifeCycleData(meterno)
{
	 var mtrno=meterno;
	 if(mtrno=="")
		 {
		 	bootbox.alert("Please Enter MeterNo.");
		 	return false;
		 }
	 $.ajax({
	    	url:"./getMetrDetailsAstMngt",
	    	type:"GET",
	    	dataType:"JSON",
	    	asynch:false,
	    	cache:false,
	    	data:{mtrno:mtrno},
	    	success:function(response)
		    	{
	    		mtrdata=response;
				}
	    	});
}
  /*lat: 30.170381 ,long :75.808406*/
function viewInMap(latitude,longitude,id)
{  
	//latitude=30.170381;
	//longitude=75.808406;
	var content="<div style=width:260px;>"
        +"<table  id=billTable style='margin-left: 0px;margin-bottom: 0px;width: 100%'>"
        +"<tr><th>Account No &nbsp&nbsp  : </th><td>"+accno+"</td></tr>"
        +"<tr><th>K No &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp   :</th><td>"+kno+"</td></tr>"
		+"<tr><th>Meter No &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp  :</th><td>&nbsp;"+meterno+"</td></tr>"
		+"<tr><th>Name  &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp  :</th><td>"+name+"</td></tr>"
		+"<tr><th>Address &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp :</th><td >"+address+"</td></tr>"
		+"<tr><th>Latitude  &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp  :</th><td >"+latitude+"</td></tr>"
		+"<tr><th>Longitude &nbsp&nbsp&nbsp&nbsp&nbsp :</th><td >"+longitude+"</td></tr>"
		+"</table></div>";

	var map;
	  var india={lat: 26.606522599999998, lng: 77.46907709999999}
	  map = new google.maps.Map(document.getElementById('map'), 
		  		{ 
		  	      center:india,
		  	      zoom: 8 ,	 
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

	  var infowindow = new google.maps.InfoWindow();
	     var newMarker = new google.maps.Marker({
	              position: new google.maps.LatLng(latitude,longitude),
	              map: map,
	              title: id
	          });
	          google.maps.event.addListener(newMarker, 'click', (function (newMarker, i) {
	              return function () {
	                  infowindow.setContent(content);
	                  infowindow.open(map, newMarker);
	              }
	          })(newMarker, id));

	          	map.setCenter({lat:parseFloat(latitude), lng: parseFloat(longitude)}),
  	 			google.maps.event.addListenerOnce(map, 'bounds_changed', function() 
  	 			{
  				  map.setZoom(11);
  				});
	          
}
</script>

<div class="page-content">
 <div  id="alertMsg1" >
</div>
	<div class="row">
		<div class="col-md-12">
			<c:if test="${results ne 'notDisplay'}">
				<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${results}</span>
				</div>
			</c:if>

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Total Meters
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				 <jsp:include page="locationFilter.jsp"/> 
				
				<div class="row" style="margin-left: -1px;">
				
				<div class="col-md-3" id="LocationId">
						<strong>Location Type:</strong>
						<div class="form-group">
						<select class="form-control select2me input-medium" id="Location" name="Location">	
						<option value=""></option>
											<option value="BOUNDARY METER">BOUNDARY METER</option>
												<option value="FEEDER METER">FEEDER METER</option>
												<option value="DT">DT</option>
												<option value="ALL">ALL</option>
												
											</select>
						</div>
						</div>
						
						<div class="col-md-3">
							<button type="button" id="viewFeeder;"
								onclick="return getFeeder()" name="viewFeeder"
								class="btn yellow" style="margin-top: 19px;">View</button></div>
					
				</div>
						
					<%-- 	<div class="col-md-3" id="zoneTd">
					<label class="control-label">Region:</label>
						<div class="form-group">
						<select
										class="form-control select2me input-medium" id="zone"
										name="zone">
											<option value="">Region</option>
											<option value="%">ALL</option>
										<c:forEach items="${ZoneList}" var="elements">
															<option value="${elements}">${elements}</option>
														</c:forEach>
									</select>
						</div>
						</div>
					<div class="col-md-3" id="circleTd">
					<label class="control-label">Circle:</label>
						<div class="form-group">
						<select
										class="form-control select2me input-medium" id="circle"
										name="circle">
											<option value="">Circle</option>
											<option value='%'>ALL</option>
											<c:forEach items="${circleList}" var="elements">
												<option value="${elements}">${elements}</option>
											</c:forEach>
									</select>
						</div>
						</div>
						<div class="col-md-3" id="divisionTd">
						<label class="control-label">Division:</label>
						<div class="form-group">
						<select
										class="form-control select2me input-medium" id="division"
										name="division">
											<option value="">Division</option>
											<option value='%'>ALL</option>
											<c:forEach items="${divisionList}" var="elements">
												<option value="${elements}">${elements}</option>
											</c:forEach>
									</select>
						</div>
						</div>
						<div class="col-md-3" id="subdivTd">
						<label class="control-label">Sub-Division:</label>
						<div class="form-group">
						<select
										class="form-control select2me input-medium" id="sdoCode"
										name="sdoCode" onchange="return getTowns(this.value);">
											<option value="">Sub-Division</option>
											<option value='%'>ALL</option>
											<c:forEach items="${subdivList}" var="sdoVal">
												<option value="${sdoVal}">${sdoVal}</option>
											</c:forEach>
									</select>
						
						</div>
						</div>
						<!-- </div>
						<div class="row" style="margin-left: -1px;"> -->
						<div class="col-md-3" id="townId">
						<label class="control-label">Town:</label>
						<div class="form-group">
						<select class="form-control select2me input-medium" id="town" name="town">
											<option value="">Town</option>
											</select>
						</div>
						</div> --%>
						
							
						
						
						
						
						
						<%--  <table style="width: 99%">
							<tbody>
								<tr>
									<td style="width: 20% !important;"><select class="form-control select2me input-medium"
										name="zone" id="zone" onchange="return showCircle(this.value);">
											<option value="">Zone</option>
											<option value='%'>ALL</option>
											<c:forEach var="elements" items="${zoneList}">
												<option value="${elements}">${elements}</option>
											</c:forEach>
									</select></td>

									<td id="circleTd" style="width: 20% !important;"><select
										class="form-control select2me input-medium" id="circle"
										name="circle">
											<option value="">Circle</option>
											<option value='%'>ALL</option>
											<c:forEach items="${circleList}" var="elements">
												<option value="${elements}">${elements}</option>
											</c:forEach>
									</select></td>

									<td id="divisionTd" style="width: 20% !important;"><select
										class="form-control select2me input-medium" id="division"
										name="division">
											<option value="">Division</option>
											<option value='%'>ALL</option>
											<c:forEach items="${divisionList}" var="elements">
												<option value="${elements}">${elements}</option>
											</c:forEach>
									</select></td>
									
									<td id="subdivTd" style="width: 20% !important;"><select
										class="form-control select2me input-medium" id="sdoCode"
										name="sdoCode" onchange="return getTowns(this.value);">
											<option value="">Sub-Division</option>
											<option value='%'>ALL</option>
											<c:forEach items="${subdivList}" var="sdoVal">
												<option value="${sdoVal}">${sdoVal}</option>
											</c:forEach>
									</select></td>
									<td id="townId" style="width: 20% !important;">
										<select class="form-control select2me input-medium" id="town" name="town">
											<option value="">Town</option>
											
										</select>
									</td>
									</tr>
									
									
									<!-- <tr>
									
							
							
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>
										<button type="button" id="viewFeeder" style="margin-left: 20px;"
											onclick="return getFeeder()" name="viewFeeder"
											class="btn yellow">
											<b>View</b>
										</button> <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button>
									</td>
								</tr> -->
							</tbody>
						</table> --%> 
						
					
					
					<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> -->
								<li><a href="#" id=""
								onclick="exportPDF('sample_1','Total Meters')">Export to PDF</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Total Meters')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					<table class="table table-striped table-hover table-bordered" id="sample_1">
						<thead>
							<tr>

								<th>ACTIONS/VIEW</th>
								<th>Region</th>
								<th>Circle</th>
								<th>Town</th>
								<th>Meter No.</th>
								<th>Meter Make</th>
								
								<th>Location Name</th>
								<th>Location Id</th>
								<!-- <th>SubStation</th> -->
								<th>LocationType</th>
								<th>Division</th>
								
								<!-- <th>Last Sync Time</th> -->
							</tr>
						</thead>
						
					<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/assets/img/loading.gif"
							style="width: 4%; height: 4%; align-content: center;">
					</div>
					
						<tbody id="updateMaster">
						
						<%-- <c:forEach var="element" items="${mDetailList}">
								<tr>
									<td ><a href='./modemDetailsInactiveMDAS?modem_sl_no=${element[0]}&mtrno=${element[1]}&substation=${element[3]}' style='color:blue;'>${element[0]}</a></td>  
									<td><a href='./mtrNoDetailsMDAS?mtrno=${element[1]}' style='color:pink;' onclick="return mtrDetails('${element[1]}');">${element[1]}</a></td> 
									<td>${element[9]}</td> 
									<td>${element[12]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>
									<td>${element[7]}</td>
									<td><fmt:formatDate value="${element[8]}"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
								</tr>
							</c:forEach> 	 --%> 
							
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
	
	<div id="stack6" class="modal fade" tabindex="-1" data-width="400" data-backdrop="static" data-keyboard="false">
							<div class="modal-dialog" style="width: 90%;" >
							
								<div class="modal-content">
								
									<div class="modal-header">
									<div id="image" hidden="true" style="text-align: center;height: 100%;width: 100%;" >
									<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true"></button>
                         <h3 id="loadingText"><font id="masterTd">Loading..... Please wait.</font> 
						 </h3> 
<!-- 						 <img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width:4%;height: 4%;margin-right: 10px;"> -->
						</div>
									<div id="closeShow">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true"></button>
										<h4 class="modal-title"><b>NAMEPLATE DETAILS</b>
										
										</h4>
										</div>
									</div>

									<div class="modal-body">
										<br>
										<div class="row">
											<div class="col-md-12">
											 <table class="table table-striped table-hover table-bordered" id="sample_4"  >
											 <thead>
											 <tr>
											 <th>METERNO</th>
											 <th>PHASE</th>
											 <th>METER MAKE</th>
											 <th>FIRMWARE VERSION</th>
											 <th>METERTYPE</th>
											 <th>NODEID</th>
											 <th>CURRENTRATING</th>
											 <th>YOM</th>
											 <th>VIEW</th>
											 </tr>
											 <tr></tr>
											 </thead>
								                 <tbody id="meterBody" >
								                </tbody>
										    </table> 
													
											</div>
										</div>

									</div>

								</div>
							</div>
						</div>
						
			<div class="modal fade" id="popup_image" tabindex="-1" data-backdrop="static" data-keyboard="false">
		       <div class="modal-dialog" id="image">
		        <div class="modal-content" style="width:550px;height:660px">
		         <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal" aria-hidden="true" >
		           &times;
		          </button>
		          <h4><b>METER IMAGE</b></h4>
		          <div>
		          <img  id="rl2" src="./resources/assets/img/RotateLeft.jpg" onclick="rotateLeft('0');" style="margin-left:200px; width:10%"/>&nbsp;&nbsp;&nbsp;&nbsp;
		         <img  id="rr1" src="./resources/assets/img/RotateRight.jpg" onclick="rotateRight('0');" style="width:10%"/>
		         </div>
		         <div class="modal-body">
		          <div class="rotatecontrol" id="Imageview" >
		           <img id="tempImg" src="" />
		          </div>
		         </div>
		        </div>		        
		       </div>		       
      </div>
  </div>
						
		<div id="stack10" class="modal fade" tabindex="-1" data-width="400" data-backdrop="static" data-keyboard="false">
							<div class="modal-dialog" style="width: 90%;" >
							
								<div class="modal-content">
								
									<div class="modal-header">
									<div id="image" hidden="true" style="text-align: center;height: 100%;width: 100%;" >
									<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true"></button>
                         <h3 id="loadingText"><font id="masterTd">Loading..... Please wait.</font> 
						 </h3> 
<!-- 						 <img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width:4%;height: 4%;margin-right: 10px;"> -->
						</div>
									<div id="closeShow">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true"></button>
										<h4 class="modal-title"><b>METER CONFIGURATION DETAILS</b>
										</h4>
										</div>
									</div>

									<div class="modal-body">
										<br>
										<div class="row">
											<div class="col-md-12">
											 <table class="table table-striped table-hover table-bordered" id="sample_4"  >
											 <thead>
											 <tr>
											 
											 <th>METR NO</th>
											 
											 <th>DEVICE ID</th>
											 <th>HARDWARE </th>
											 <th>SOFTWARE VERSION</th>
											 <th>PROVIDER</th>
											
											 </tr>
											 <tr></tr>
											 </thead>
								                 <tbody id="meterBodyConfig" >
								                </tbody>
										    </table> 
													
											</div>
										</div>

									</div>

								</div>
							</div>
						</div>
		
		<div id="stack8" class="modal fade" tabindex="-1" data-width="400" data-backdrop="static" data-keyboard="false">
							<div class="modal-dialog" style="width: 600px;" >
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"></button>
										<h4 class="modal-title">ADD LOG Damage Issues</h4>
										<div >
										<table >
										<tr >
										 <td>Meterno</td>
										 <td><input type="text" class="form-control input-medium"  placeholder="" id="meterNoId" disabled="disabled"></td>
										</tr>
										<tr>
										 <td>ISSUES</td>
										 <td ><input type="text" class="form-control input-medium" style=" height: 40px; width:280px; placeholder="" id="Dmgremark" /></td>
										</tr>
										</table>
										</div>
										
									</div>

									<div class="modal-body" >
										<div class="row">
											<div class="col-md-12">
												<form>
													
													<div>
													 <button type="button" data-dismiss="modal" class="btn" id="closePopUp">Close</button>
													 <button type ="button" class="btn blue pull-right"  onclick="return updateRemark();">Add</button>
												</div>
												</form>
											</div>
										</div>
									</div>
								</div>
							</div>
              </div>
              
              	<div class="modal fade" id="GISVIEW" tabindex="-1" data-backdrop="static" data-keyboard="false">
		       <div class="modal-dialog" id="image">
		        <div class="modal-content" style="width:550px;height:660px">
		         <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal" aria-hidden="true" >
		           &times;
		          </button>
		          <h4><b>GIS VIEW</b></h4>
		         <div class="modal-body">
		          <div id="map"  style="height:560px; width:96%;margin-left: 2%;"></div>
		          </div>
		         </div>
		        </div>		        
		       </div>		       
      </div>
  </div>
              

<script>


var  rotation=0;
function rotateRight()
{
	rotation =rotation+90;
    var rotate = "rotate(" + rotation + "deg)";
     var trans = "all 0.3s ease-out"; 
    $(".rotatecontrol").css({
        "-webkit-transform": rotate,
        "-moz-transform": rotate,
        "-o-transform": rotate,
        "msTransform": rotate,
        "transform": rotate,
        "-webkit-transition": trans,
        "-moz-transition": trans,
        "-o-transition": trans,
        "transition": trans
    });
    
}

function rotateLeft()
{
	rotation =rotation-90;
    var rotate = "rotate(" + rotation + "deg)";
     var trans = "all 0.3s ease-out"; 
    $(".rotatecontrol").css({
        "-webkit-transform": rotate,
        "-moz-transform": rotate,
        "-o-transform": rotate,
        "msTransform": rotate,
        "transform": rotate,
        "-webkit-transition": trans,
        "-moz-transition": trans,
        "-o-transition": trans,
        "transition": trans
    });
}

function viewDocument(id,imagetype)
{  
    $('#Imageview').empty(); 
    //var url="http://220.227.2.202:7070/bsmartsurvey/getImage/"+id+"/"+imagetype;
    var url="./meterImageDisplay/"+id;
    rotation=0;
    rotateRight();
    rotateLeft();
    $('#Imageview').append("<img id=\"tempImg\" style=\"width:500px;height:500px;\" src='"+url+"' />");
    	
}

function showCircle(zone) {
	$.ajax({
				/* //url : './showCircleMDM' + '/' + zone,
				url : './showCircleAmi' + '/' + zone,
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false, */
				 url:'./getCircleByZone',
	                type:'GET',
	                dataType:'json',
	                asynch:false,
	                cache:false,
	                data : {
	                    zone : zone
	                }, 
				success : function(response) {
					var html = '';
					//html += "<label class='control-label'>Circle:</label><select id='circle' name='circle' onchange='getTowns(this.value)' class='form-control input-medium' type='text'><option value=''>Circle</option><option value='%'>ALL</option>";
				html+="<select id='LFcircle' name='LFcircle' class='form-control' onchange='showTownNameandCode(this.value)' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
					for (var i = 0; i < response.length; i++) {
						html += "<option  value='"+response[i]+"'>"
								+ response[i] + "</option>";
					}
					html += "</select><span></span>";
					$("#LFcircleTd").html(html);
		  			$('#LFcircle').select2();
				}
			});
}
function showDivision(circle) {
	/* var zone = $('#zone').val(); */
	var zone="%";
	var circle="%";
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
					html += "<label class='control-label'>Division:</label><select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Division</option><option value='%'>ALL</option>";
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
	var zone="%";
	var circle="%";
	var division="%";
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
					html += "<select id='sdoCode' name='sdoCode' onchange='getTowns(this.value)'  class='form-control input-medium' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
					for (var i = 0; i < response1.length; i++) {
						//var response=response1[i];
						html += "<option  value='"+response1[i]+"'>"
								+ response1[i] + "</option>";
					}
					html += "</select><span></span>";
					$("#sdoCode").html(html);
					$('#sdoCode').select2();
				}
			});
}

function showResultsbasedOntownCode (){
	
}

//get Towns 
 function showTownNameandCode(circle)
{var zone = $('#LFzone').val();
//alert(zone);
	$.ajax({
		type : 'GET',
		url : "./showTownByCircle",
		data:{
			zone:zone,
			circle:circle
			
			},
		success : function(response)
		{
			//alert(response);
			var html="";
			var htmFeeder="";
			var htmlDTC="";
			html += "<select id='LFtown' name='LFtown'  class='form-control input-medium' type='text'><option value=''>Select Town</option><option value='%'>ALL</option>";
			if(response!=null)
			{
			
				for(var i=0;i<response.length;i++)
				{
					html += "<option value='"+response[i][1]+"'>"
                    +response[i][0] +"-"  +response[i][1] + "</option>";
				}
				html += "</select><span></span>";
				
				$("#LFtown").html(html);
				$('#LFtown').select2();
				
			}
		}
	});
} 

function getFeeder() {
	 var zone = $('#LFzone').val(); 
	var circle = $('#LFcircle').val();
	var division = "%";
	var subdiv = "%";
	var towncode = $('#LFtown').val();
	var location=$('#Location').val();
	//alert(location);

  if(circle==""){
     bootbox.alert("Please select Circle");
     return false;
	  }

  if(division==""){
	     bootbox.alert("Please select Division");
	     return false;
		  }

  if(subdiv==""){
	     bootbox.alert("Please select Sub-Division");
	     return false;
		  }

  if(towncode==""){
	     bootbox.alert("Please select Town");
	     return false;
		  }
  if(location==""){
	     bootbox.alert("Please select Location Type");
	     return false;
		  }
  


	
	$('#updateMaster').empty();
	$('#imageee').show();
	$.ajax({
		url : './getAllMetersBasedOnMDM',
		type : 'GET',
		data:{
			zone:zone,
			circle:circle,
			towncode:towncode,
			location:location
			},
		dataType : 'json',
		asynch : false,
		cache : false,
		success : function(response) {
		//	alert(response);
			$('#imageee').hide();
			if (response.length != 0) {
				var html = "";
				for (var i = 0; i < response.length; i++) {
					var element = response[i];
					html += "<tr>" + "<td>"
					 + "<input type='button' class='btn green' id='latId'  value='LogDamageReport' onclick='return getdamange(\""+element[0]+"\")' data-target='#stack8' data-toggle='modal' />" 
					// + "<input type='button' class='btn green' id='latId'  value='MeterDetails' onclick='return getConsumerData(\""+element[0]+"\",\""+element[2]+"\",\""+element[6]+"\")' data-target='#stack6' data-toggle='modal' />&nbsp;&nbsp<input type='button' class='btn yellow' id='latId'  value='LogDamageReport' onclick='return getdamange(\""+element[0]+"\")' data-target='#stack8' data-toggle='modal' />" 
/* 					 + "<input type='button' class='btn green' id='latId'  value='meterDetails' onclick='return getConsumerData(\""+element[0]+"\",\""+element[2]+"\",\""+element[6]+"\")' data-target='#stack6' data-toggle='modal' /><input type='button' class='btn yellow' id='latId'  value='DamageReport'  data-target='#stack8' data-toggle='modal' /><input type='button' class='btn blue' id='config'  value='View Config'  onclick='return getMetrConfigData(\""+element[0]+"\")'  data-target='#stack10' data-toggle='modal' />"  */
					/* + "<input type='button' class='btn green' id='latId'  value='MeterDetails' onclick='return getConsumerData(\""+element[0]+"\",\""+element[2]+"\",\""+element[6]+"\")' data-target='#stack6' data-toggle='modal' />" */
					+ "</td>"

					/*  + "<td><a onclick=mtrDetails('"+element[0]+"')>"+element[0]+"</a></td>"  */ /* <a style='color:green;' onclick='return mtrDetails(\""+element[1]+"\");'>"+element[0]+"</a>" */
					 /*  href='./mtrNoDetails?mtrno="+element[1]+"'  */
					 + "<td>"+(element[8]==null?"":element[8])+ "</td>"
					 + "<td>"+(element[5]==null?"":element[5])+ "</td>"
					 + "<td>"+(element[9]==null?"":element[9])+ "</td>"
					+"<td><a target='_blank' href='./viewFeederMeterInfoMDAS?mtrno="+element[0]+"' style='color:blue;'>"+element[0]+"</a></td>"
					+ "<td>"+(element[7]==null?"":element[7])+ "</td>"
					+ "<td>"+(element[1]==null?"":element[1])+ "</td>"
					+ "<td>"+(element[2]==null?"":element[2])+ "</td>"
					+ "<td>"+(element[3]==null?"":element[3])+ "</td>"
					+ "<td>"+(element[4]==null?"":element[4])+ "</td>";
					
					
					/* if(element[8]!=null){
						html +="<td>"
							+ moment(element[8]).format("YYYY-MM-DD HH:mm:ss")
							+ "</td>";
					} else{
						html +="<td>"
							+ "</td>";
					} */
					
					+ " </tr>";
				}
				$('#sample_1').dataTable().fnClearTable();
				$('#updateMaster').html(html);
				loadSearchAndFilter('sample_1');
				
			} else {
				$('#sample_1').dataTable().fnClearTable();
				$('#updateMaster').html(html);
				bootbox.alert("Data Not Found");
			}
		}
	});

}

function getdamange(meretno)
{
	$("#meterNoId").val(meretno);	
}
function updateRemark()
{
	var remark=$("#Dmgremark").val();
	var meterno=$("#meterNoId").val();

	if(remark=="")
		{
		bootbox.alert("Enter issues");
		return false;
		}
		 $.ajax({
						type : "GET",
						url : "./addRemarks",
						dataType: "text",
						data:{remark:remark,meterno:meterno},
						cache:false,
						async:false,
						success:function(response)
						{
							 $('#stack8').modal('hide'); 
							if(response=='updated')
								{
								   $('#alertMsg1').html('<div class="alert alert-danger display-show"><button class="close" data-close="alert"></button><span style="color:blue" >Log Damage issue Added Successfully.</span></div>'); 
								     /*  $('#stack8').modal('toggle');  */
								}
							if(response=='notupdated')
							{
							   $('#alertMsg1').html('<div class="alert alert-danger display-show"><button class="close" data-close="alert"></button><span style="color:red" >Log Damage issue Adding UnSuccessfull</span></div>'); 
							     /*  $('#stack8').modal('toggle');  */
							}
							 
						}
				}
			); 
		}


/* function mtrDetails(mtrNo)
{
	mtrNum=mtrNo;
	
	window.location.href="./viewFeederMeterInfoMDAS?mtrno=" + mtrNo;
	
}
 */


//get pdf

  function exportPDF()
   {
		var zone=$('#LFzone').val();
		var circle = $('#LFcircle').val();
		var division  ="%";
		var subdiv  ="%";
		var towncode = $('#LFtown').val();
		var townname = $("#LFtown option:selected").map(function(){return this.text}).get().join(',');
		var location=$('#Location').val();
		//alert(zone);
		//alert(towncode);
		//alert(townname);
		
		var cr="";
		var dvs="";
		var subdvn="";
		var tn="";
		
		if(circle=="%"){
			cr="ALL";
		}else{
			cr=circle;
		}
		if(division=="%"){
			dvs="ALL";
		}else{
		    dvs=division;
		}
		if(subdiv=="%"){
			subdvn="ALL";
		}else{
			subdvn=subdiv;
		}


		if(towncode=="%"){
			tn="ALL";
		}else{
			tn=towncode;
		}
		
		//window.open("./meterdetailspdf/"+cr+"/"+dvs+"/"+subdvn+"/"+towncode)
		window.location.href=("./meterdetailspdf?cr="+cr+"&dvs="+dvs+"&subdvn="+subdvn+"&tn="+tn+"&townname="+townname+"&location="+location);

   }


  function exportToExcelMethod()
	{
	  var zone=$('#LFzone').val();
	  var circle = $('#LFcircle').val();
		var towncode = $('#LFtown').val();
		var townname = $("#LFtown option:selected").map(function(){return this.text}).get().join(',');
		var location=$('#Location').val();
		//alert(towncode);
		var cr="";
		if(zone=="%"){
			zone="ALL";
		}else{
			zone=zone;
		}
		if(circle=="%"){
			cr="ALL";
		}else{
			cr=circle;
		}
		
		window.open("./exportToExcelmanagemeters?cr="+cr+"&towncode="+towncode+"&townname="+townname+"&zone="+zone+"&location="+location);
	}


 
  

</script>

<style>


</style>