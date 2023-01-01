<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script src="<c:url value='http://maps.google.com/maps/api/js?sensor=true'/>"  type="text/javascript" ></script>
<script src="<c:url value='/resources/assets/plugins/gmaps/gmaps.js'/>"  type="text/javascript" ></script>

<style>
.page-content .page-breadcrumb.breadcrumb>li>i {
	color: #fff;
}

.btn {
	padding: 7px 33px;
}

div.scrollmenu {
	background-color: #333;
	overflow: auto;
	white-space: nowrap;
	padding: 0px;
}

div.scrollmenu a {
	display: inline-block;
	color: white;
	text-align: center;
	padding: 10px;
	text-decoration: none;
}

div.scrollmenu a:hover {
	background-color: #777;
}

.dateHead {
	font-size: xx-small !important;
	padding: 0px !important;
	text-align: center !important;
	font-weight: normal !important; 
}
.dateData {
	padding: 0px !important; 
	height: 20px !important; 
	text-align: center !important;
}
.dateDataRed {
	padding: 0px !important;
	background: #e02222 !important;
	height: 20px !important; 
	text-align: center !important;
	color:white;
}

.dateDataGreen {
	padding: 0px !important;
	background: #3cc051 !important;
	height: 20px !important; 
	text-align: center !important;
	color:white;
}
.fa {
    font-size: 9px;
}

.noBorder{
border:0px ! important;
}
.largeFont{
 font-size: large !important;
 color: black;
 font-weight: bold !important;
 background: white !important;
 min-width: 176px;
 text-align: left;
}

.portlet.box.gray > .portlet-title {
    background-color: #5f5f5f;
}
.bold{
font-weight: bold;
}

.width140{
width: 122px;
}

.mainHead{
font-size: x-large ! important;
font-weight: bold ! important;
}
.mainHead2{
font-size: small ! important;
font-weight: bold ! important;
color: teal ! important;
}

.portfolio-info {   
    padding: 7px 15px;
    margin-bottom: 5px;
    text-transform: uppercase;
}
.portfolio-block{
margin-bottom:5px ! important;
}

</style>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&key=AIzaSyDFsv7MwN3q9GNl-kasQWAWqLtgAi1aaF4"></script>

<script>
	$(".page-content")
			.ready(
					function() {

						if ('${showTotalModems.size()}' > 0) {
							$('#totalFeeder').show();
						}
						if ('${showWorkingsModems.size()}' > 0) {
							$('#workingFeeder').show();
						}
						if ('${showNotWorkingsModems.size()}' > 0) {
							$('#notWorkingFeeder').show();
						}
						App.init();
						Index.initMiniCharts();
						TableEditable.init();
						/*  loadSearchAndFilter('table_1'); 
						 loadSearchAndFilter('table_2'); 
						 loadSearchAndFilter('table_3');  */
						$('#dash-board2').addClass('start active ,selected');
						$(
								'#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
								.removeClass('start active ,selected');

					});
</script>
<div class="page-content">
 

<div class="portlet box blue" style="margin-bottom: 0px;">
						<div class="portlet-title blue">
									<label 
				style='color: #000000; font-size: 14px; float:left;'><span style="color: white;"><i class="fa fa-building"  style="font-size: medium;"></i> Meter Information </span> </label>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								
							</div>
						</div>
						<div class="portlet-body">


		<div class="row" style="margin-bottom: 10px;">

			<div class="col-sm-8" style="padding-left: 33px;">
					<div class="row portfolio-block">

						<div>
							<div class="portfolio-info" style="padding: 0px ! important;">
								<img style="width: 68px;" src="resources/assets/img/house.png" alt="" />
							</div>
							
							<c:choose>
									<c:when test="${fdrcat eq 'DT'}">
									<div class="portfolio-info"> DT Name <span class="mainHead">${customer_name}</span></div>
									</c:when>
									<c:otherwise>
									<div class="portfolio-info"> FEEDER Name <span class="mainHead">${customer_name}</span></div>
									</c:otherwise>
							</c:choose>
							
							<%-- <div class="portfolio-info">
								Consumer Name <span class="mainHead">${customer_name}</span>
								<!-- Contact No <span class="mainHead2">9900112233</span> -->
								<!-- Adress <span class="mainHead2">H.no 123,</span>
								 <span class="mainHead2">Jaipur</span>
								 <span class="mainHead2">5001122</span>
							 -->
							</div> --%>
							<%-- <div class="portfolio-info">
								Acc No <span class="mainHead2">${accno}</span>
							</div> 
							<div class="portfolio-info">
								K No <span class="mainHead2">${kno}</span>
							</div> 
							<div class="portfolio-info">
								Tariff<span class="mainHead2">${tariffcode}</span>
							</div> 
								<div class="portfolio-info">
								Contact No <span class="mainHead2">${customer_mobile}</span>
							</div> 
							<div class="portfolio-info">
								Address <span class="mainHead2">${customer_address}</span>
								
							</div>  --%>
							
							<%-- <div class="portfolio-info">
								Modem <span class="mainHead2">	
								<a   href="./modemDetailsInactiveMDAS?modem_sl_no=${imei}&mtrno=${mtrno}&substation=${imei}" style=" text-decoration: underline; font-size: 14px; color:blue;">${imei}</a>
									</span>
							</div>  --%>
						 
						</div>

					</div>

					<div class="row portfolio-block"> 

						<div> 
							
							<div class="portfolio-info">
								Circle <span class="mainHead2">${circle}</span>
							</div>
							<%-- <div class="portfolio-info">
								Division<span class="mainHead2">${division}</span>
							</div>  --%>
							<div class="portfolio-info">
								Town<span class="mainHead2">${town}</span>
							</div>
							<div class="portfolio-info">
								Substation <span class="mainHead2">${substation}</span>
							</div>
							<div class="portfolio-info">
								Feeder <span class="mainHead2">${FEEDERNAME}</span>
							</div>
							
							<div class="portfolio-info">
								Meter Type <span class="mainHead2">${fdrcat}</span>
							</div>
							<!-- <div class="portfolio-info">
								DTC <span class="mainHead2"></span>
							</div> -->
						</div> 
					</div>

	<div class="row portfolio-block">
		<div>
			<div class="portfolio-info">
				<button type="button" id="${mtrno}" onclick="return viewOnMap('${mtrno}')" data-toggle="modal" data-target="#stack6" class="btn btn-default">
					<i class="fa glyphicon glyphicon-map-marker"></i>&nbsp;View On Map
				</button>
			</div>
			<%-- <div class="portfolio-info">
				<button type="button" onclick="return viewByImage(this.id,'${kno}');" id="billedData${mtrno}" class="btn btn-default" style="margin-left: 10px;">
				<i class="fa glyphicon glyphicon-picture"></i>&nbsp;View Image
			</button>
			</div> --%>
		</div>

	</div>

				</div>
		<!-- 	<div class="col-sm-1"></div> -->
			<div class="col-sm-4"> 
			<div class="dropdown inline clearfix"> 
					 <ul class="dropdown-menu" role="menu"  style="width: 100%; pointer-events: none; padding-bottom: 15px; " > 
									 
		 <li ><a style="font-size: medium;"  href="#"><i class="fa fa-clock-o"  style="font-size: medium;"></i><b>Meter Communication</b> </a></li>
									 
								 <li role="presentation" class="divider" style="margin: 2px 0;"></li> 
									 
									 <li    role="presentation"><a style="background: #4b8df8; color: white;"  role="menuitem" tabindex="-1" href="#"><i class="fa fa-clock-o"  style="font-size: medium; background: "></i>Last Communicated 
									 <c:choose>
									<c:when test="${fn:contains(last_communicationActive, 'true')}">
									<span class="badge badge-success bold width140">${last_communication}</span>
									</c:when>
									<c:otherwise>
									<span class="badge badge-danger bold width140">${last_communication}</span>
									</c:otherwise>
								</c:choose> </a></li>
																	 <li   role="presentation"><a role="menuitem" tabindex="-1" href="#"><i class="fa fa-clock-o"  style="font-size: medium;"></i>Last Instantaneous Synced 
									 <c:choose>
									<c:when test="${fn:contains(last_sync_instActive, 'true')}">
									<span class="badge badge-success bold width140">${last_sync_inst}</span>
									</c:when>
									<c:otherwise>
									<span class="badge badge-danger bold width140">${last_sync_inst}</span>
									</c:otherwise>
								</c:choose> </a></li>
									 <li   role="presentation"><a role="menuitem" tabindex="-1" href="#"><i class="fa fa-clock-o"  style="font-size: medium;"></i>Last Load Synced 
										<c:choose>
									<c:when test="${fn:contains(last_sync_loadActive, 'true')}">
									<span class="badge badge-success bold width140">${last_sync_load}</span>
									</c:when>
									<c:otherwise>
									<span class="badge badge-danger bold width140">${last_sync_load}</span>
									</c:otherwise>
								</c:choose> </a></li> 
										<li   role="presentation"><a role="menuitem" tabindex="-1" href="#"><i class="fa fa-clock-o"  style="font-size: medium;"></i>Last Bill Synced 
										<c:choose>
									<c:when test="${fn:contains(last_sync_billActive, 'true')}">
									<span class="badge badge-success bold width140">${last_sync_bill}</span>
									</c:when>
									<c:otherwise>
									<span class="badge badge-danger bold width140">${last_sync_bill}</span>
									</c:otherwise>
								</c:choose> </a></li>
												<li   role="presentation"><a role="menuitem" tabindex="-1" href="#"><i class="fa fa-clock-o"  style="font-size: medium;"></i>Last Event Synced 
											<c:choose>
									<c:when test="${fn:contains(last_sync_eventActive, 'true')}">
									<span class="badge badge-success bold width140">${last_sync_event}</span>
									</c:when>
									<c:otherwise>
									<span class="badge badge-danger bold width140">${last_sync_event}</span>
									</c:otherwise>
								</c:choose> </a></li>
									</ul>
				 </div>
		  
			</div>

		</div>
 <div class="portlet-body" style="margin-top: -10px; margin-left: 3px;">
			<div class="col-sm-12">
				<div class="row portfolio-block">

					<div>
					<div class="portfolio-info" style="padding: 0px ! important;">
								<img style="width: 68px;" src="resources/assets/img/meter.png" alt="" />
							</div>
						<div class="portfolio-info">
							Meter No<span class="mainHead">${mtrno}</span>
						</div>
						<div class="portfolio-info">
							Meter Make <span class="mainHead2">${mtrmake}</span>
						</div>
						<div class="portfolio-info">
							Protocol Type<span class="mainHead2">${dlms}</span>
						</div>
						<%-- <div class="portfolio-info">
							Port Config<span class="mainHead2">${comm}</span>
						</div> --%>
						<%-- <div class="portfolio-info">
							Year of Manufacturing <span class="mainHead2">${year_of_man}</span>
						</div> --%>
						<div class="portfolio-info">
							CT.Ratio  <span class="mainHead2">${ct_ratio}</span>
						</div>
						<div class="portfolio-info">
							PT.Ratio <span class="mainHead2">${pt_ratio}</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		</div>

	</div>


 

	<div class="portlet box green" style="margin-bottom: 0px; margin-top: 10px;">
		<div class="portlet-title blue"><label style='color: #FFF; font-size: 14px; float:left;'>
		 <i class="fa fa-calendar"  style="font-size: medium;"></i>&nbsp;Last 30 Days Activity</label>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								
							</div>
						</div>
		<div class="portlet-body scrollmenu" style="padding: 0px;">
		
			<table class="table table-bordered " style="margin-top: 5px; border: white;">
			 
				<tbody >
				<tr id="eventTd"/>
				<tr id="activeTd" />
				<tr id="uploadTd" /> 
				<tr id="dataHeadTh" />
				</tbody>
			</table>

		</div>
	</div> 
  

		<div class="portlet box blue" style="margin-bottom: 0px; margin-top: 10px;">
						<div class="portlet-title blue">
									<label 
				style='color: #000000; font-size: 14px; float:left;'><span style="color: white;"><i class="fa fa-flash" style="font-size: medium;"></i> Instantaneous Data </span> </label>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								
							</div>
						</div>
						<div class="portlet-body">
			<div class="row">
			
				<div class="col-sm-4">
						<table class="table table-bordered table-striped">
								<tbody>
									<tr> <td><i class="fa fa-flash"  style="font-size: small; width: 16px;"></i> KWH </td> <td> <b>${kwh}</b></td> </tr>
									<tr> <td><i class="fa fa-flash" style="font-size: small; width: 16px;"></i> KVAH </td>  <td><b>${kvah}</b></td>  </tr>
									<tr> <td><i class="fa fa-flash" style="font-size: small; width: 16px;"></i> KVA</td>  <td><b>${kva}</b></td>  </tr>
									<tr> <td><i class="fa fa-flash" style="font-size: small; width: 16px;"></i> Avg. PF </td>  <td><b>${pf_threephase}</b></td>  </tr>
									<tr> <td><i class="fa fa-random" style="font-size: small; width: 16px;"></i> Frequency</td>  <td><span class="badge badge-info "><b>${frequency}</b></span> <span style="margin-left: 5px; color: blue;">Hz</span></td>  </tr>
								</tbody>
					 </table>
				</div>
				<!--  -->
				<div class="col-sm-3">
				<table class="table table-bordered">
								<tbody>
									<tr> <td> Total Power Outage </td> <td> <span class="badge badge-default" style="background-color: #565656 !important"><b>${power_off_count}</b></span> </td> </tr>
									<tr> <td> Outage Duration </td>  <td> <span class="badge badge-danger"><b>${power_off_duration}</b></span> Hrs</td>  </tr>
									<tr> <td> Md Reset Count</td>  <td> <span class="badge badge-primary"><b>${md_reset_count}</b></span> </td>  </tr>
									<tr> <td> Programming Count </td>  <td> <span class="badge badge-info"><b>${programming_count}</b></span> </td>  </tr>
									<tr> <td> Tamper Count </td>  <td> <span class="badge badge-warning"><b>${tamper_count}</b></span> </td>  </tr>
								</tbody>
					 </table>
				</div>
				<div class="col-sm-5">
 
					<div class="table-responsive">
						<table class="table table-bordered table-hover"
							 >
							<thead>
								<tr>
									<th style="border-bottom: 1px solid #ddd"></th>
									<th style="color: white; background: #ed4e2a">R</th>
									<th style="color: white; background: #fcb322;">Y</th>
									<th style="color: white; background: #0362fd">B</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>Voltage <span style="color: blue;">(V)</span></td>
									<td class="danger"><b>${v_r}</b></td>
									<td class="warning"><b>${v_y}</b></td>
									<td class="note-info"><b>${v_b}</b></td>
								</tr>
								<tr>
									<td>CURRENT  <span style="color: blue;">(A)</span></td>
									<td class="danger"><b>${i_r}</b></td>
									<td class="warning"><b>${i_y}</b></td>
									<td class="note-info"><b>${i_b}</b></td>
								</tr>
								<tr>
									<td>PF</td>
									<td class="danger"><b>${pf_r}</b></td>
									<td class="warning"><b>${pf_y}</b></td>
									<td class="note-info"><b>${pf_b}</b></td>
								</tr>
							  
							</tbody>
						</table>
						 <button type="button" class="btn btn-default blue" onclick="return mtrDetails('${mtrno}','${zone}','${circle}','${town}');"	style="width: 100%; border: 0px ! important;   color: white; font-weight: bold;">	<i class="fa fa-bullseye"></i> View More In 360 View	</button>
						
					</div>


				</div>
			</div>

		</div>
	</div>
	
	<div id="stack2" class="modal fade"
		style="display: none; overflow-y: scroll; overflow-x: hidden;  "
		tabindex="-1" data-width="400" data-backdrop="static"
		data-keyboard="false">
		<div class="modal-dialog" style="width: 75%">
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
								<table class="table table-striped table-bordered table-advance table-hover">
									<thead>
										<tr>
											<th>SL No.</th>
											<th>Date</th>
											<th>Diag. Type</th>
											<th>Status</th>
											<th>Time</th> 
										</tr>
									</thead>
									<tbody id="inactiveModemDetailsTbl"> 
									</tbody>
								</table>
							</div>
						</div>
					</div>				
				</div>
			</div>
		</div>
	</div>
	<div id="stack10" class="modal fade" tabindex="-1" data-backdrop="static" data-keyboard="false">
						        <div class="modal-dialog">
							       <div class="modal-content" style="width:200%;margin-left:-200px">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true"></button>
									<h4 class="modal-title" id="titleId">Feeder Images</h4>
								</div>
								<!-- <div class="modal-body">
									<div class="row">
										<div class="col-md-12" id="billedData">
											

										</div>
									</div>
								</div> -->
								<div class="portlet box purple" style="display: none; width: 97%; margin-left: 15px;" id="fimge">
                      <div class="portlet-title">
							<div class="caption"><i class="fa fa-building"></i>Consumer Images&nbsp;&nbsp;&nbsp;&nbsp;</div>
							
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body" >
							<div class="table-responsive" >
							 <table class='table table-striped table-hover'>
							 <tbody id="feederTable">
							 
							 
							 </tbody>
							 </table>
								
			       	</div>
			</div>
			</div>
							</div>
						</div>
					</div>
					
					<div class="modal-body" id="closeShow1">
										<div class="row">				
											<!-- BEGIN MARKERS PORTLET-->
											<div class="portlet box green" style="display: none;" id="gmapsContent">
												<div class="portlet-title">
													<div class="caption"><i class="fa fa-reorder"></i>GMAP VIEW</div>
												</div>
												<div class="portlet-body">
													<div id="gmap_marker" class="gmaps" style="height: 500px"></div>				 
					               				<!-- END PORTLET-->
						              			</div>
				              				</div>
               							</div>
									</div>
					<jsp:include page="modalPhotoMDAS.jsp" />	
</div>
<style>
table thead>tr>th, .table tbody>tr>th, .table tfoot>tr>th, .table thead>tr>td,
	.table tbody>tr>td, .table tfoot>tr>td {
	border-top: none;
}
</style>
<script>
 $( document ).ready(function() {
	//GET DIAGNOSIS ALERTS
			$.ajax({
			url : "./getLastDaysActiveXmlSingleMtrMDAS/${mtrno}",
			data:"",
			type : "GET",
			dataType : "text",
			async : false,
			success : function(response) {
				var array=JSON.parse(response);
			 
				var activeTd="";
				var uploadTd="";
				var eventTd="";
				for(var i=0;i<array.length;i++){
					var obj=array[i];  
					 
					if(i==0){
						$('#dataHeadTh').append('<td  style="width:41px; border:none;" ></td>');//ADDING DATE FIRST COLUMN
						$('#activeTd').append('<td class="dateHead" style="width:41px; border:none;" ><b>Active</b></td>');
						// $('#uploadTd').append('<td class="dateHead" style="width:41px; border:none;" ><b>Upload</b></td>'); 
						// $('#eventTd').append('<td class="dateHead" style="width:41px; border:none;" ><b>Issues</b></td>'); 
						
					}
					var viewDate=moment(obj.date).format('MMM<br/>DD');
					$('#dataHeadTh').append('<th class="dateHead" >'+viewDate+'</th>');//ADDING DATE
					
					if(obj.active==1){
						activeTd='<td class="dateDataGreen"><span class="fa fa-check"/></td>';
					}else{
						activeTd='<td class="dateDataRed"><span class="fa fa-times"/></td>';
					}
					
// 					if(obj.upload==1){
// 						uploadTd='<td class="dateDataGreen"><span class="fa fa-check"/></td>';
// 					}else{
// 						uploadTd='<td class="dateDataRed"><span class="fa fa-times"/></td>';
// 					}
					
// 					if(obj.issues>0){    
// 						eventTd='<td data-toggle="modal" data-target="#stack2" data-dismiss="modal" onclick="getMeterIssues(\''+${mtrno}+'\',\''+obj.date+'\');" class="dateData" ><span class="badge badge-important"  style="margin-bottom:3px; font-weight:bold; text-decoration:underline ! important;" >'+obj.issues+'</span></td>';
// 					}else{
// 						eventTd='<td class="dateData" ><span  class="badge badge-success fa fa-check" style="margin-bottom:3px; font-size: 7px ! important; color:#FFF;"> </span></td>';
// 					}
					
					$('#eventTd').append(eventTd);
					$('#activeTd').append(activeTd);
					$('#uploadTd').append(uploadTd);
					
				}
		  
				 
			}
			}); 
 
	 
	
}); 
 

 function getMeterIssues(mtrNo,date)
{  
	$ .ajax({
		url : "./getModemIssues/" + mtrNo + "/"+ date,
		data : "",
		type : "GET",
		dataType : "json",
		async : false,
		success : function(response) { 
			var tableData = "";
			var j = 1;
			for (var i = 0; i < response.length; i++) {

				var res = response[i]; 
				
				console.log(res.diag_type);
				console.log(res.status);
				console.log(res.tracked_time);
				tableData += "<tr> " + "<td>"
						+ j
						+ "</td> " 
						+ "<td>"
						+ res.date
						+ "</td> "
						+ "<td>"
						+ res.diag_type
						+ "</td> "
						+ "<td>"
						+ res.status
						+ "</td> "
						+ "<td>"
						+ moment(res.tracked_time).format('DD-MM-YYYY HH:mm:ss');
				+"</td> " + "</tr>";
				j++;
			}
			$('#inactiveModemDetailsTbl').html(tableData);

		}
	});
}
 
 function mtrDetails(mtrNo,zone,circle,town)
 { 
 	  
 	 	  $("#LFzone").val(zone);
 		 $("#LFcircle").val(circle);
 		 $("#LFtown").val(town);
 		  $("#meterNum").val(mtrNo); 
 	  
 	  
 	/*  window.location.href="./mtrNoDetailsMDAS?mtrno=" + mtrNo; */
 	  window.location.href = ('./mtrNoDetailsMDAS?mtrno='+mtrNo+'&zone='+ zone+ '&circle='+circle+ '&town='+town+'');
 }
  


function getConsumerData(param1,param2,param3)
{
	getMeterLifeCycleData(param1);
	//param3='210474029414'
	$.ajax({  
    	//url : 'http://220.227.2.202:7070/bsmartsurvey/getMeterData?kno='+param3,
    	 // url : 'http://192.168.4.229:6060/bsmartsurvey/getMeterData?kno=210463030033', 
    	url : './getSurveyDataImages?kno='+param3,
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
        	 var html ="<tr>"
					+" <td>"+result[2]+"</td>" //meterno
					+" <td>"+result[9]+"</td>" //phase
					+" <td>"+result[33]+"</td>" //mtrtype
					+" <td>"+result[48]+"</td>" //mtrmke
					+" <td>"+result[61]+"</td>" //category
					+" <td>"+data[4]+"</td>" // Location
					+" <td>"+result[60]+"</td>" // subdiv
					+" <td><a href='#' data-toggle='modal'  data-target='#popup_image'  onclick=viewDocument("+data[17]+",'meterimage');><b>&nbsp Meter Images</b></a> <a href='#' data-toggle='modal'  data-target='#GISVIEW'  onclick=viewInMap('"+lattitude+"','"+logititde+"','"+data[6]+"');><b>&nbsp&nbsp&nbspGIS VIEW</b></a></td>"
					+" </tr>"; 
				$("#meterBody").empty();
				$("#meterBody").append(html); 
        }
    });
}



function viewOnMap_backup(kno)
{
	//kno='210474029414';
		$.ajax({
		  	//url : './getViewOnMapMtrDataMDAS/'+mtrNum,
		  //	url : 'http://220.227.2.202:7070/bsmartsurvey/getMeterData?kno='+kno,
		  	 url : './getSurveyDataImages?kno='+kno,
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
		    	//alert('----response----'+response1);
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
						            div: '#gmap_marker',
						            lat: parseFloat(response[10]),
						            lng :parseFloat(response[11]),
						            zoom:10,
						            gestureHandling: 'greedy',
							        fullscreenControl: true,
						        });
			    			     initialLocation=true;
		    			  }
		    		  
		    		if(initialLocation)
	    			 {
							   map.addMarker({
					            lat:response[10],
					            lng:response[11],
					            //icon:'./resources/assets/img/active.gif',
					            optimized: false,
					            //animation: google.maps.Animation.DROP,
					            
					            title:"Consumer Name- "+response[3],
					             infoWindow: {
					                  content: 
					                  "<div>"
					                  +"<table  id=billTable1 class='table table-striped table-bordered table-hover' style='background: #99ccff;'>"
									  +"<tr><th>Consumer Name</th><td>"+response[3]+"</td></tr>"
									  +"<tr><th>Meter No</th><td>"+response[6]+"</td></tr>"
									  +"<tr><th>DLMS</th><td>DLMS</td></tr>"
									  +"<tr><th>Latitude</th><td>"+response[10]+"</td></tr>"
									  +"<tr><th>Longitude</th><td>"+response[11]+"</td></tr>"
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
		$("#closeShow1").ready(function(){
		    $("html, body").delay(10).animate({
		        scrollTop: $('#closeShow1').offset().top 
		    }, 1000);
		});
	   return false;
}



function viewOnMap(mtrNum)
{
	//alert(mtrNum);
		$.ajax({
		  	url : './getViewOnMapMtrDataNew/'+mtrNum,
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
		    	//alert('----response----'+response1);
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
						            div: '#gmap_marker',
						            lat: parseFloat(response[3]),
						            lng :parseFloat(response[4]),
						            zoom:10,
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
					            
					            title:"Location Name- "+response[0],
					             infoWindow: {
					                  content: 
					                  "<div>"
					                  +"<table  id=billTable1 class='table table-striped table-bordered table-hover' style='background: #99ccff;'>"
									  +"<tr><th>Feeder Name</th><td>"+response[0]+"</td></tr>"
									  +"<tr><th>Meter No</th><td>"+response[1]+"</td></tr>"
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
		$("#closeShow1").ready(function(){
		    $("html, body").delay(10).animate({
		        scrollTop: $('#closeShow1').offset().top 
		    }, 1000);
		});
	   return false;
}

function viewByImage(param,mtrNumber)
{
	//mtrNumber='210474029414';
	$.ajax(
			{
					type : "GET",
					url : "./getViewOnImageMtrData/"+mtrNumber,
					dataType : "json",
					async:false,
					beforeSend: function(){
				        $('#imageee').show();
				    },
				    complete: function(){
				        $('#imageee').hide();
				    },
					success : function(response)
					{	
                            $('#titleId').html('Consumer No- ' +mtrNumber);
			    			if (response != null) {
							var html = "";

							var rep = response;
							html = html
									+ "<tr><td><input type='hidden' id='first' value='"+rep[0]+"_Front'><img  border='0'  data-toggle='modal'  data-target='#popup_image'  class='magnify' src='./getFeederImages/"+rep[0]+"_Front'  onclick='viewDocument1();' height='100' width='100' /><figcaption>Meter Image</figcaption><figure></td>"
									+ "<td><input type='hidden' id='second' value='"+rep[0]+"_Left'><img border='0'  data-toggle='modal'  data-target='#popup_image'  class='magnify' src='./getFeederImages/"+rep[0]+"_Left' onclick='viewDocument2();' height='100' width='100' /><figcaption>MCRReporting Image</figcaption><figure></td>"
									+ "<td><input type='hidden' id='third' value='"+rep[0]+"_Right'><img border='0'  data-toggle='modal'  data-target='#popup_image'  class='magnify' src='./getFeederImages/"+rep[0]+"_Right' onclick='viewDocument3();'  height='100' width='100'/><figcaption>NewMeter Image</figcaption><figure></td>"
									//+ "<td><input type='hidden' id='fourth' value='"+rep[0]+"_Port'><img border='0'  data-toggle='modal'  data-target='#popup_image'  class='magnify' src='./getFeederImages/"+rep[0]+"_Port' onclick='viewDocument4();' height='100' width='100'/><figcaption>Port Image</figcaption><figure></td>"
									+ "<td><input type='hidden' id='sixth' value='"+rep[0]+"_TTL'><img border='0'  data-toggle='modal'  data-target='#popup_image'  class='magnify' src='./getFeederImages/"+rep[0]+"_TTL' onclick='viewDocument5();'  height='100' width='100'/><figcaption>Premise Image</figcaption><figure></td></tr>";

							$('#fimge').show();
							$('#feederTable').empty();
							$('#feederTable').html(html);

						} else {
							bootbox.alert('No Data Available');
							$('#fimge').hide();
							 $('#feederListId').hide();
						}
					}
			}
	    );
		$('#'+param).attr("data-toggle", "modal");
		$('#'+param).attr("data-target","#stack10");
}
function viewDocument1()
{  
    $('#Imageview').empty(); 
    rotation=0;
    rotateRight();
    rotateLeft();
    var id=$('#first').val();
	  var html="<img data-magnifyby='10'  height='400' width='500' class='magnify' src='./getFeederImages/"+id+"'/>";
    $('#Imageview').append(html);
    	
}

function viewDocument2()
{  
    $('#Imageview').empty(); 
    rotation=0;
    rotateRight();
    rotateLeft();
    var id=$('#second').val();
	  var html="<img data-magnifyby='10'  height='400' width='500' class='magnify' src='./getFeederImages/"+id+"'/>";
    $('#Imageview').append(html);
    	
}

function viewDocument3()
{  
    $('#Imageview').empty(); 
    rotation=0;
    rotateRight();
    rotateLeft();
    var id=$('#third').val();
	  var html="<img data-magnifyby='10'  height='400' width='500' class='magnify' src='./getFeederImages/"+id+"'/>";
    $('#Imageview').append(html);
    	
}

function viewDocument4()
{  
    $('#Imageview').empty(); 
    rotation=0;
    rotateRight();
    rotateLeft();
    var id=$('#fourth').val();
	  var html="<img data-magnifyby='10'  height='400' width='500' class='magnify' src='./getFeederImages/"+id+"'/>";
    $('#Imageview').append(html);
    	
}
function viewDocument5()
{  
    $('#Imageview').empty(); 
    rotation=0;
    rotateRight();
    rotateLeft();
    var id=$('#sixth').val();
   
	  var html="<img data-magnifyby='10'  height='400' width='500' class='magnify' src='./getFeederImages/"+id+"'/>";
    $('#Imageview').append(html);
    	
}



function viewByImage_backup(param,mtrNumber)
{
	$.ajax(
			{
					type : "GET",
					//url : "http://220.227.2.202:7070/bsmartsurvey/getViewOnImageMtrData/"+mtrNumber,
					//url : 'http://220.227.2.202:7070/bsmartsurvey/getMeterData?kno='+mtrNumber,
					url : './getSurveyDataImages?kno='+mtrNumber,
					dataType : "json",
					async:false,
					beforeSend: function(){
				        $('#imageee').show();
				    },
				    complete: function(){
				        $('#imageee').hide();
				    },
					success : function(response)
					{	
                            $('#titleId').html('Consumer Details- Kno:  ' +mtrNumber);
			    			if (response != null) {
							var html = "";

							var rep = response[0];
							html = html
									//+ "<tr><td><input type='hidden' id='first' value='"+rep[17]+"_Front'><img  border='0'  data-toggle='modal'  data-target='#popup_image'  class='magnify' src='http://220.227.2.202:7070/bsmartsurvey/getImage/"+rep[17]+"/meterimage'  onclick='viewDocument1("+rep[17]+");' height='100' width='100' /><figcaption>Meter Image</figcaption><figure></td>"
									+ "<tr><td><input type='hidden' id='first' value='"+rep[17]+"_Front'><img  border='0'  data-toggle='modal'  data-target='#popup_image'  class='magnify' src='./meterImageDisplay/"+rep[17]+"/'  onclick='viewDocument1("+rep[17]+");' height='100' width='100' /><figcaption>Meter Image</figcaption><figure></td>"
									//+ "<td><input type='hidden' id='second' value='"+rep[17]+"_Left'><img border='0'  data-toggle='modal'  data-target='#popup_image'  class='magnify' src='http://220.227.2.202:7070/bsmartsurvey/getImage/"+rep[17]+"/consumer' onclick='viewDocument2("+rep[17]+");' height='100' width='100' /><figcaption>Consumer Plot Image</figcaption><figure></td>";
									+ "<td><input type='hidden' id='second' value='"+rep[17]+"_Left'><img border='0'  data-toggle='modal'  data-target='#popup_image'  class='magnify' src='./meterImageDisplay/"+rep[17]+"/' onclick='viewDocument2("+rep[17]+");' height='100' width='100' /><figcaption>Consumer Plot Image</figcaption><figure></td>";
									/*+ "<td><input type='hidden' id='third' value='"+rep[0]+"_Right'><img border='0'  data-toggle='modal'  data-target='#popup_image'  class='magnify' src='http://220.227.2.202:7070/bsmartsurvey/getImage/"+rep[0]+"/meterimage' onclick='viewDocument3();'  height='100' width='100'/><figcaption>Right Image</figcaption><figure></td>"
									+ "<td><input type='hidden' id='fourth' value='"+rep[0]+"_Port'><img border='0'  data-toggle='modal'  data-target='#popup_image'  class='magnify' src='http://220.227.2.202:7070/bsmartsurvey/getImage/"+rep[0]+"/meterimage' onclick='viewDocument4();' height='100' width='100'/><figcaption>Port Image</figcaption><figure></td>"
									+ "<td><input type='hidden' id='sixth' value='"+rep[0]+"_TTL'><img border='0'  data-toggle='modal'  data-target='#popup_image'  class='magnify' src='http://220.227.2.202:7070/bsmartsurvey/getImage/"+rep[0]+"/meterimage' onclick='viewDocument5();'  height='100' width='100'/><figcaption>TTL Image</figcaption><figure></td></tr>"; */

							$('#fimge').show();
							$('#feederTable').empty();
							$('#feederTable').html(html);

						} else {
							alert("No Data Found");
							$('#fimge').hide();
							 $('#feederListId').hide();
						}
					}
			}
	    );
		$('#'+param).attr("data-toggle", "modal");
		$('#'+param).attr("data-target","#stack10");
}
var  rotation=0;
function viewDocument1backup(id)
{  
    $('#Imageview').empty(); 
    rotation=0;
    rotateRight();
    rotateLeft();
    //var id=$('#first').val();
//	var html="<img data-magnifyby='10'  height='500' width='500' src='http://220.227.2.202:7070/bsmartsurvey/getImage/"+id+"/meterimage'/>";
	var html="<img data-magnifyby='10'  height='500' width='500' src='./meterImageDisplay/"+id+"/'>";
	$('#Imageview').append(html);
	  
	  /* $('#Imageview').empty(); 
	    var url="http://192.168.4.229:6060/bsmartsurvey/getImage/"+id+"/"+imagetype;
	    rotation=0;
	    rotateRight();
	    rotateLeft();
	    $('#Imageview').append("<img id=\"tempImg\" style=\"width:500px;height:500px;\" src='"+url+"' />"); */
	  
    	
}

function viewDocument2backup(id)
{  
    $('#Imageview').empty(); 
    rotation=0;
    rotateRight();
    rotateLeft();
    //var id=$('#second').val();
	//var html="<img data-magnifyby='10'  height='500' width='500' src='http://220.227.2.202:7070/bsmartsurvey/getImage/"+id+"/consumer'/>";
    var html="<img data-magnifyby='10'  height='500' width='500' src='./meterImageDisplay/"+id+"/'>";
    $('#Imageview').append(html);
    	
}

function viewDocument3backup()
{  
    $('#Imageview').empty(); 
    rotation=0;
    rotateRight();
    rotateLeft();
    var id=$('#third').val();
	  var html="<img data-magnifyby='10'  height='400' width='500' class='magnify' src='./getFeederImages/"+id+"'/>";
    $('#Imageview').append(html);
    	
}

function viewDocument4backup()
{  
    $('#Imageview').empty(); 
    rotation=0;
    rotateRight();
    rotateLeft();
    var id=$('#fourth').val();
	  var html="<img data-magnifyby='10'  height='400' width='500' class='magnify' src='./getFeederImages/"+id+"'/>";
    $('#Imageview').append(html);
    	
}
function viewDocument5backup()
{  
    $('#Imageview').empty(); 
    rotation=0;
    rotateRight();
    rotateLeft();
    var id=$('#sixth').val();
   
	  var html="<img data-magnifyby='10'  height='400' width='500' class='magnify' src='./getFeederImages/"+id+"'/>";
    $('#Imageview').append(html);
    	
}

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
</script>
