 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
 <style>
.table-toolbar {
    margin-bottom: 4px;
}

</style>
 
 <script  type="text/javascript">
 var mtrNum=null;
 var frmDate=null;
 var tDate=null;
  $(".page-content").ready(function()
   {     
	  $('#fromDate').val('${fromDate}');
	  $('#toDate').val('${toDate}');
	  $('#meterNum').val('${mtrno}');
	  	frmDate='${fromDate}';
		tDate='${toDate}';
	 	mtrNum='${mtrno}';
	 	
		App.init();
		TableEditable.init();
		FormComponents.init();
		$('#MDASSideBarContents,#viewdata').addClass('start active ,selected');
		$("#dash-board,#user-location,#MIS-Reports,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn").removeClass('start active ,selected');
   });	 
  </script>
  
  <script type="text/javascript">
 
function eventDetails()
{
	var mtrNo=mtrNum;
	$.ajax({
    	url : './getEventsData/'+mtrNo+'/'+frmDate,
    	type:'GET',
    	asynch:false,
    	cache:false,
    	success:function(data)
    	{
    		if(data.length==0 || data.length==null)
    		{
    			bootbox.alert("Data Not Available for this Meter No. : "+mtrNo);
    		}
    		else 
    		{
	    		var html="";
	   		 	for(var i=0;i<data.length; i++)
				{
					var resp=data[i];
					 html+="<tr>"
							+"<td>"+moment(resp[0]).format('DD-MM-YYYY HH:mm:ss')+"</td>"
							+" <td>"+resp[1]+"</td>";
					var edesc=getEventDesc(resp[1]);
					
					html+=" <td>"+edesc+"</td>";		
					
					  var resp2 = resp[2];
	    			 if(resp2 == null){
	    				 resp2 =0;
					}else {
						resp2 = resp[2];
						} 
	    			 
	    			 var resp3 = resp[3];
	    			 if(resp3 == null){
	    				 resp3 =0;
					}else {
						resp3 = resp[3];
						} 
	    			 
	    			 var resp4 = resp[4];
	    			 if(resp4 == null){
	    				 resp4 =0;
					}else {
						resp4 = resp[4];
						} 
	    			 
	    			 var resp5 =resp[5];
	    			 if(resp5 == null){
	    				 resp5 =0;
					}else {
						resp5 = resp[5];
						} 
	    			 
	    			 var resp6 = resp[6];
	    			 if(resp6 == null){
	    				 resp6 =0;
					}else {
						resp6 = resp[6];
						} 
	    			 
	    			 var resp7 = resp[7];
	    			 if(resp7 == null){
	    				 resp7 =0;
					}else {
						resp7 = resp[7];
						} 
	    			 
	    			 var resp8 = resp[8];
	    			 if(resp8 == null){
	    				 resp8 =0;
					}else {
						resp8 = resp[8];
						} 
	    			 
	    			 var resp9 = resp[9];
	    			 if(resp9 == null){
	    				 resp9 =0;
					}else {
						resp9 = resp[9];
						} 
	    			 
	    			 var resp10 = resp[10];
	    			 if(resp10 == null){
	    				 resp10 =0;
					}else {
						resp10 = resp[10];
						} 
	    			 
	    			 var resp11 = resp[11];
	    			 if(resp11 == null){
	    				 resp11 =0;
					}else {
						resp11 = resp[11];
						}  
					
					
					html+=" <td>"+resp2+"</td>"
							+" <td>"+resp3+"</td>"
							+" <td>"+resp4+"</td>"
							+" <td>"+resp5+"</td>"
							+" <td>"+resp6+"</td>"
							+" <td>"+resp7+"</td>"
							+" <td>"+resp8+"</td>"
							+" <td>"+resp9+"</td>"
							+" <td>"+resp10+"</td>"
							+" <td>"+resp11+"</td>"
							+" </tr>";
		   		}
	   			 $('#sample_2').dataTable().fnClearTable();
	   		 	$('#eventTR').html(html);
	    	}
    	},
		complete: function()
		{  
			loadSearchAndFilter('sample_2');
		}  
	  }); 
}

function getEventDesc(eCode) {
	var eDesc='';
	if(eCode=='101'){ eDesc='Power faliure (3 Phase) - Occurrence';}
	else if(eCode=='102'){ eDesc='Power faliure (3 Phase) - Restoration';}
	else if(eCode=='1'){ eDesc='R-Phase PT link Missing (Missing Potential) - Occurrence';}
	else if(eCode=='2'){ eDesc='R-Phase PT link Missing (Missing Potential) - Restoration';}
	else if(eCode=='3'){ eDesc='Y-Phase PT link Missing (Missing Potential) - Occurrence';}
	else if(eCode=='5'){ eDesc='B-Phase PT link Missing (Missing Potential) - Occurrence';}
	else if(eCode=='4'){ eDesc='Y-Phase PT link Missing (Missing Potential) - Restoration';}
	else if(eCode=='8'){ eDesc='Over Voltage in any Phase - Restoration';}
	else if(eCode=='9'){ eDesc='Low Voltage in any Phase - Occurrence';}
	else if(eCode=='6'){ eDesc='B-Phase PT link Missing (Missing Potential) - Restoration';}
	else if(eCode=='7'){ eDesc='Over Voltage in any Phase - Occurrence';}
	else if(eCode=='10'){ eDesc='Low Voltage in any Phase - Restoration';}
	else if(eCode=='11'){ eDesc='Voltage Unbalance - Occurrence';}
	else if(eCode=='12'){ eDesc='Voltage Unbalance - Restoration';}
	else if(eCode=='51'){ eDesc='Phase  R CT reverse - Occurrence';}
	else if(eCode=='52'){ eDesc='Phase  R CT reverse - Restoration';}
	else if(eCode=='53'){ eDesc='Phase  Y CT reverse - Occurrence';}
	else if(eCode=='54'){ eDesc='Phase  Y CT reverse - Restoration';}
	else if(eCode=='55'){ eDesc='Phase  B CT reverse - Occurrence';}
	else if(eCode=='56'){ eDesc='Phase  B CT reverse - Restoration';}
	else if(eCode=='57'){ eDesc='Phase  R CT Open - Occurrence';}
	else if(eCode=='58'){ eDesc='Phase  R CT Open - Restoration';}
	else if(eCode=='59'){ eDesc='Phase  Y CT Open - Occurrence';}
	else if(eCode=='60'){ eDesc='Phase  Y CT Open - Restoration';}
	else if(eCode=='61'){ eDesc='Phase  B CT Open - Occurrence';}
	else if(eCode=='62'){ eDesc='Phase  B CT Open - Restoration';}
	else if(eCode=='63'){ eDesc='Current Unbalance - Occurrence';}
	else if(eCode=='64'){ eDesc='Current Unbalance - Restoration';}
	else if(eCode=='65'){ eDesc='CT Bypass - Occurrence';}
	else if(eCode=='66'){ eDesc='CT Bypass - Restoration';}
	else if(eCode=='67'){ eDesc='Over Current in any Phase - Occurrence';}
	else if(eCode=='68'){ eDesc='Over Current in any Phase - Restoration';}
	else if(eCode=='151'){ eDesc='Real Time Clock  Date and Time';}
	else if(eCode=='152'){ eDesc='Demand Integration Period';}
	else if(eCode=='153'){ eDesc='Profile Capture Period';}
	else if(eCode=='154'){ eDesc='Single-action Schedule for Billing Dates';}
	else if(eCode=='155'){ eDesc='Activity Calendar for Time Zones etc.';}
	else if(eCode=='201'){ eDesc='Influence of Permanent Magnet or AC/ DC Electromagnet - Occurrence';}
	else if(eCode=='202'){ eDesc='Influence of Permanent Magnet or AC/ DC Electromagnet - Restoration';}
	else if(eCode=='203'){ eDesc='Neutral Disturbance - HF AND DC - Occurence';}
	else if(eCode=='204'){ eDesc='Neutral Disturbance - HF AND DC - Restoration';}
	else if(eCode=='205'){ eDesc='Very Low PF - Occurrence';}
	else if(eCode=='206'){ eDesc='Very Low PF - Restoration';}
	else if(eCode=='251'){ eDesc='Meter Cover Opening - Occurrence';}
	else if(eCode=='301'){ eDesc='Meter Disconnected';}
	else if(eCode=='302'){ eDesc='Meter Connected';}

	return eDesc;
}

function instansDetails()
{
	var mtrNo=mtrNum;
	 $("#loadingmessage").show();
	$.ajax({
    	url : './getInstansData/'+mtrNo+'/'+frmDate,
    	type:'GET',
    	dataType:'json',
    	asynch:false,
    	cache:false,
    	success:function(data)
    	{
    		$("#loadingmessage").hide();
    		if(data.length==0 || data.length==null)
    		{
    			bootbox.alert("Data Not Available for this Meter No. : "+mtrNo);
    		}
    		else 
    		{
	    		var html="";
	   		 	for(var i=0;i<data.length; i++)
				{
					var resp=data[i];
					

					var rPhaseVal = resp.rPhaseVal;
					 if(rPhaseVal == null){
   						 rPhaseVal =0;
						}else {
						rPhaseVal = resp.rPhaseVal;
						} 

					var rPhaseLineVal = resp.rPhaseLineVal;
					 if(rPhaseLineVal == null){
						 rPhaseLineVal =0;
						}else {
						rPhaseLineVal = resp.rPhaseLineVal;
						} 

					var rPhasePfVal = resp.rPhasePfVal;
					 if(rPhasePfVal == null){
						 rPhasePfVal =0;
						}else {
						rPhasePfVal = resp.rPhasePfVal;
						} 

					var yPhaseVal = resp.yPhaseVal;
					 if(yPhaseVal == null){
						 yPhaseVal =0;
						}else {
							yPhaseVal = resp.yPhaseVal;
						} 

					var yPhaseLineVal = resp.yPhaseLineVal;
					 if(yPhaseLineVal == null){
						 yPhaseLineVal =0;
						}else {
							yPhaseLineVal = resp.yPhaseLineVal;
						} 

					
					 var yPhasePfVal = resp.yPhasePfVal;
					 if(yPhasePfVal == null){
						 yPhasePfVal =0;
						}else {
							yPhasePfVal = resp.yPhasePfVal;
						} 

					
					 var bPhaseVal = resp.bPhaseVal;
					 if(bPhaseVal == null){
						 bPhaseVal =0;
						}else {
							bPhaseVal = resp.bPhaseVal;
						} 	
						
					
					 var bPhaseLineVal = resp.bPhaseLineVal;
					 if(bPhaseLineVal == null){
						 bPhaseLineVal =0;
						}else {
							bPhaseLineVal = resp.bPhaseLineVal;
						} 	
						
					 var bPhasePfVal = resp.bPhasePfVal;
						if(bPhasePfVal == null){
						 bPhasePfVal =0;
						}else {
							bPhasePfVal = resp.bPhasePfVal;
						} 	


					 html+="<tr>"
							 +" <td><b>"+"R"+"</b></td>"
							 +" <td>"+moment(resp.readTime).format('YYYY-MM-DD HH:mm:ss')+"</td>"
							+" <td>"+rPhaseVal+"</td>"
							+" <td>"+rPhaseLineVal+"</td>"
							+" <td>"+rPhasePfVal+"</td>" 
							+" </tr>"
					 		+"<tr>"
							+" <td><b>"+"Y"+"</b></td>"
							 +" <td>"+moment(resp.readTime).format('YYYY-MM-DD HH:mm:ss')+"</td>"
							+" <td>"+yPhaseVal+"</td>"
							+" <td>"+yPhaseLineVal+"</td>"
							+" <td>"+yPhasePfVal+"</td>" 
							+" </tr>"
					 		+"<tr>"
							+" <td><b>"+"B"+"</b></td>"
							 +" <td>"+moment(resp.readTime).format('YYYY-MM-DD HH:mm:ss')+"</td>"
							+" <td>"+bPhaseVal+"</td>"
							+" <td>"+bPhaseLineVal+"</td>"
							+" <td>"+bPhasePfVal+"</td>" 
							+" </tr>";
		   		}
	   		 	$('#sample_3').dataTable().fnClearTable();
	   		 	$('#instantsTR').html(html);
	    	}
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
	  $("#loadingmessage").show();
	$.ajax({
  	url : './getLoadSurveyData/'+mtrNo+'/'+frmDate,
  	type:'GET',
  	dataType:'json',
  	asynch:false,
  	cache:false,
  	success:function(data)
  	{
  		$("#loadingmessage").hide();
  		if(data.length==0 || data.length==null)
  		{
  			bootbox.alert("Data Not Available for this Meter No. : "+mtrNo);
  		}
  		else 
  		{
	    		var html2="";
	   		 	for(var i=0;i<data.length; i++)
				{
					var resp=data[i];
	    			 var mtrdate=moment(resp.dayProfileDate).format('YYYY-MM-DD HH:mm:ss'); 
	    			 /*  var mtrTime=moment(resp[1]).format('HH:mm:ss'); 
	    			 if(mtrTime=='00:00:00'){
	    				    var newdate = new Date(mtrdate);
	    				    newdate.setDate(newdate.getDate() - 1);
	    				    var dd = newdate.getDate();
	    				    var mm = newdate.getMonth() + 1;
	    				    var y = newdate.getFullYear(); 
	    				    var someFormattedDate = y + '-' + mm + '-' + dd +' 24:00:00';
	    				    mtrdate=someFormattedDate;
	    			 } */
	    			 var vrValue = resp.vrValue;
	    			 if(vrValue == null){
	    				 vrValue =0;
					}else {
						vrValue = resp.vrValue;
						} 
	    			 
	    			 var vyValue = resp.vyValue;
	    			 if(vyValue == null){
	    				 vyValue =0;
						}else {
							vyValue = resp.vyValue;
						} 
	    			 var vbValue = resp.vbValue;
	    			 if(vbValue == null){
	    				 vbValue =0;
						}else {
							vbValue = resp.vbValue;
						} 
	    			 
	    			 
	    			
	    			 var arValue = resp.arValue;
	    			 if(arValue == null){
	    				 arValue =0;
						}else {
							arValue = resp.arValue;
						} 
	    			 var ayValue = resp.ayValue;
	    			 if(ayValue == null){
	    				 ayValue =0;
						}else {
							ayValue = resp.ayValue;
						} 
	    			 var abValue = resp.abValue;
	    			 if(abValue == null){
	    				 abValue =0;
						}else {
							abValue = resp.abValue;
						} 
	    			 
	    			 
	    			 var kwh = resp.kwh;
	    			 if(kwh == null){
	    				 kwh =0;
						}else {
							kwh = resp.kwh;
						} 
	    			 
	    			 var kvarhLag = resp.kvarhLag;
	    			 if(kvarhLag == null){
	    				 kvarhLag =0;
						}else {
							kvarhLag = resp.kvarhLag;
						} 

	    			 var kvarhLead = resp.kvarhLead;
	    			 if(kvarhLead == null){
	    				 kvarhLead =0;
						}else {
							kvarhLead = resp.kvarhLead;
						} 
	    			 
	    			 
	    			 var kvah = resp.kvah;
	    			 if(kvah == null){
	    				 kvah =0;
						}else {
							kvah = resp.kvah;
						} 
	    			var frequency = resp.frequency; 					
					if(frequency == null){
						frequency =0;
					}
	    			 
					 html2+="<tr>"
							 /* +" <td>"+resp[1]+"</td>"  */
							  +" <td>"+mtrdate+"</td>" 
							+" <td>"+vrValue+"</td>"
							+" <td>"+vyValue+"</td>"
							+" <td>"+vbValue+"</td>"
							+" <td>"+arValue+"</td>"
							+" <td>"+ayValue+"</td>"
							+" <td>"+abValue+"</td>"
							+" <td>"+kwh+"</td>"
							+" <td>"+kvarhLag+"</td>"
							+" <td>"+kvarhLead+"</td>"
							+" <td>"+kvah+"</td>"
							+" <td>"+frequency+"</td>"
							+" </tr>";
		   		}
	   		 	$('#sample_4').dataTable().fnClearTable();
	   		 	$('#loadSurveyTR').html(html2);
	    	}
		},
		complete: function()
		{  
			loadSearchAndFilter('sample_4');
		}  
	  }); 
}

function dailyLoadSurveyDetails()
{
	var mtrNo=mtrNum;
	$.ajax({
    	url : './getDailyLoadSurveyData/'+mtrNo+'/'+frmDate,
    	type:'GET',
    	asynch:false,
    	cache:false,
    	success:function(data)
    	{
    		if(data.length==0 || data.length==null)
    		{
    			bootbox.alert("Data Not Available for this Meter No. : "+mtrNo);
    		}
    		else 
    		{
	    		var html="";
	   		 	for(var i=0;i<data.length; i++)
				{
					var resp=data[i];
					 html+="<tr>"
							 +" <td>"+resp[1]+"</td>" 
							+" <td>"+resp[2]+"</td>"
							+" <td>"+moment(resp[3]).format('YYYY-MM-DD HH:mm:ss')+"</td>"
							+" </tr>";
		   		}
	   		 	$('#sample_5').dataTable().fnClearTable();
	   		 	$('#dailyLoadSurveyTR').html(html);
	    	}
		},
		complete: function()
		{  
			loadSearchAndFilter('sample_5');
		}  
	  }); 
}


function billHistoryDetails() {
	var mtrNo=mtrNum;
	$.ajax({
    	url : './getBillingDatas/'+mtrNo+'/'+frmDate,
    	type:'GET',
    	dataType:'json',
    	asynch:false,
    	cache:false,
    	success:function(data)
    	{
    		if(data.length==0 || data.length==null)
    		{
    			bootbox.alert("Data Not Available for this Meter No. : "+mtrNo);
    		}
    		else 
    		{
	    		var html="";
	   		 	for(var i=0;i<data.length; i++)
				{
					var resp=data[i];
					var billdate=null;
					if(resp.billing_date==null)
						{
						
						}
					else
						{
							billdate=moment(resp.billing_date).format("YYYY-MM-DD");
						}
					
					
					
					var kwh = resp.kwh;
					if(kwh == null){
					 kwh =0;
					}else {
						kwh = resp.kwh;
					} 
					
					var kvah = resp.kvah;
					if(kvah == null){
					 kvah =0;
					}else {
						kvah = resp.kvah;
					} 							

					var kvarhLag = resp.kvarhLag;
					if(kvarhLag == null){
					 kvarhLag =0;
					}else {
						kvarhLag = resp.kvarhLag;
					} 
					
					var kvarhLead = resp.kvarhLead;
					if(kvarhLead == null){
					 kvarhLead =0;
					}else {
						kvarhLead = resp.kvarhLead;
					} 
					
					var kva = resp.kva;
					if(kva == null){
					 kva =0;
					}else {
						kva = resp.kva;
					} 
					
					var demandKw = resp.demandKw;
					if(demandKw == null){
					 demandKw =0;
					}else {
						demandKw = resp.demandKw;
					} 
					
					
					
					 html+="<tr>"
					 +" <td>"+billdate+"</td>" 
					+" <td>"+kwh+"</td>"
					+" <td>"+kvah+"</td>"
					+" <td>"+kvarhLag+"</td>"
					+" <td>"+kvarhLead+"</td>"
					+" <td>"+kva+"</td>"
					+" <td>"+demandKw+"</td>"
					
					+" </tr>"
		   		}
	   		 	$('#sample_6').dataTable().fnClearTable();
	   		 	$('#billHistoryBody').html(html);
	    	}
		},
		complete: function()
		{  
			loadSearchAndFilter('sample_6');
		}  
	  }); 
	
	
	
}

</script>
  
  
  
<div class="page-content" >
    
	<div class="portlet box blue">
	 <c:if test="${not empty msg}">
		<div class="alert alert-danger display-show">
			<button class="close" data-close="alert"></button>
			<span style="color: red">${msg}</span>
		</div>
	</c:if>
                        
									<!-- /.col-md-6 -->
          
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-reorder"></i>HT Meter Data : <span style="color: aqua;font-size:18px;font-weight: bold;;">${mtrno}</span></div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						
						
						<div class="portlet-body">
							<div class="row" style="margin-left:-1px;">
								<form action="./fullviewmdas" method="post">
									<table style="width: 25%">
									<tbody>
										<tr>
											<td><input class="form-control input-medium" placeholder="Enter Meter No." name="meterNum" id="meterNum" autocomplete="off" /></td>
											<td><div class="input-group input-medium date date-picker"  data-date-format="yyyymm" data-date-viewmode="years" id="five">
												<input type="text" class="form-control" name="fromDate" id="fromDate"   readonly >
												<span class="input-group-btn"><button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
												</span></div></td>
										    
										    
										    <td><button  type="submit" id="dataview" class="btn yellow"><b>View</b></button></td>
										</tr>
									</tbody>
									</table>
								</form>
				
							</div>
					<div class="modal-body">
					
						<div class="tabbable tabbable-custom tabbable-full-width">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab_1_2" data-toggle="tab">Meter Data</a></li>
				<li><a href="#tab_1_4" onclick="return instansDetails();" data-toggle="tab">Instants Details</a></li>
				<li><a href="#tab_1_8" onclick="return billHistoryDetails();" data-toggle="tab">Bill History Details</a></li>
				<li><a href="#tab_1_6" onclick="return loadSurveyDetails();" data-toggle="tab">Load Survey Details</a></li>
				<li><a href="#tab_1_3" onclick="return eventDetails();" data-toggle="tab">Events Details</a></li>
				
				<!-- <li><a href="#tab_1_5" data-toggle="tab">Analysis Details</a></li> -->
				
				<!-- <li><a href="#tab_1_7" onclick="return dailyLoadSurveyDetails();" data-toggle="tab">Transaction Details</a></li> -->
				
				<!-- <li><a href="#tab_1_8" data-toggle="tab">Graph Details</a></li> -->
			</ul>
			<div class="tab-content">
											
				<!--tab_1_2-->
			<div class="tab-pane active" id="tab_1_2">						
						
			<div class="box">
				
					<div id="tab_1-1" class="tab-pane active">
						<form role="form" class="form-horizontal" action=" " method="post">
							<div class="form-body">
				  				<div class="table-responsive">
									
									<table class="table table-striped table-bordered">
						<tbody>
							<c:forEach var="element" items="${mtrFdrList1}">
								<tr><th>Zone</th><td>${zone}</td><th>Circle </th><td>${circle}</td></tr>
								<tr><th>Division</th><td>${division}</td><th>SubDivision </th><td>${subdiv}</td></tr>
								 <tr><th>Meter Make</th><td>${mtrmake}</td><th>Meter Type </th><td>${mtrtype}</td></tr>
								 <tr><th>Meter Status</th><td>${mtrstatus}</td><th>Account Number</th><td>${accno}</td></tr>
								<tr><th>Name</th><td>${name}</td><th>Address </th><td>${address}</td></tr>
								<%--
								<tr><th>Substation</th><td>${element.substation}</td><th>Feeder Number(UNIN)</th><td>${element.fdrcode}</td></tr>
								<tr><th>Substation</th><td>${element.substation}</td><th>Account No.</th><td>${element.acc_no}</td></tr>
								<tr><th>consumer Name</th><td>${element.name}</td><th>Village </th><td>${element.village}</td></tr> --%>
								<%-- <tr><th>Latitude</th><td>${element.latitude}</td><th>Longitude</th><td>${element.longitude}</td></tr> --%>
							</c:forEach>
						</tbody>
									
									</table>
									<%-- <table class="table table-striped table-bordered">
									<tbody>
									<c:forEach var="element" items="${mtrFdrList}">
									<tr><th>Meter Number</th><td>${element.mtrno}</td><th>Meter Type </th><td>${mtrmake}</td><th>Meter Type </th><td>${element.dlms}</td></tr>
									<tr><th>Year Of Man</th><td>${element.year_of_man}</td><th>CT Ratio </th><td>${element.ct_ratio}</td><th>PT Ratio </th><td>${element.pt_ratio}</td></tr>
									<tr><th>MF</th><td>${element.mf}</td><th>SIM Number </th><td>${element.simno}</td><th>Modem S.No </th><td>${element.modem_sl_no}</td></tr>
									</c:forEach>
									</tbody>
									
									</table> --%>
							</div>
						</div>
					</form>
				</div>
			
			</div>
		</div>
		<div class="tab-pane" id="tab_1_3">						
						
			<div class="box">
				<div class="tab-content">
					<div id="tab_1-1" class="tab-pane active">
						<form role="form" class="form-horizontal" action=" " method="post">
				  				
				<div class="portlet-body">
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 1px;">
					<div id="excelExportDiv2">
					<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport2" 
									onclick="tableToExcel2('sample_2', 'Events Details')">Export
										to Excel</a></li>
							</ul>
										</div>
										</div></div>
					<table class="table table-striped table-hover table-bordered"
						id="sample_2">
						<thead>
							<tr>
								<!-- <th>Meter No</th> -->
								<th>Event Time</th>
								<th>Event Code</th>
								<th>Event Desc</th>
								<th>VR</th>
								<th>VY</th>
								<th>VB</th>
								<th>IR</th>
								<th>IY</th>
								<th>IB</th>
								<th>PF-R</th>
								<th>PF-Y</th>
								<th>PF_B</th>
								<th>KWH</th>
							</tr>
						</thead>
						<tbody id="eventTR">
						</tbody>
					</table>
				</div>
			
						
					</form>
				</div>
			</div>
			</div>
		</div>
		<div class="tab-pane" id="tab_1_4">						
						
			<div class="box">
				<div class="tab-content">
					<div id="tab_1-1" class="tab-pane active">
						<form role="form" class="form-horizontal" action=" " method="post">
				<div class="portlet-body">
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 1px;">
					<div id="excelExportDiv3">
					<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport3" 
									onclick="tableToExcel3('sample_3', 'Instants Details')">Export
										to Excel</a></li>
							</ul>
										</div>
										</div></div>
					<table class="table table-striped table-hover table-bordered"
						id="sample_3">
						<thead>
							<tr>
								<th>Phase</th>
								<th>ReadTime</th>
								<th>Voltage(KV)</th>
								<th>Current(Amps)</th>
								<th>Power Factor</th>
							</tr>
						</thead>
						<tbody id="instantsTR">
							
						</tbody>
					</table>
				</div>
					</form>
				</div>
			</div>
			</div>
		</div>
		
		<%-- <div class="tab-pane" id="tab_1_5">						
						
			<div class="box">
				<div class="tab-content">
					<div id="tab_1-1" class="tab-pane active">
						<form role="form" class="form-horizontal" action=" " method="post">
							<div class="form-body">
				  				<div class="table-responsive">
									<h4><b>Analysis Details</b></h4>
							</div>
						</div>
					</form>
				</div>
			</div>
			</div>
		</div> --%>
		
		<div class="tab-pane" id="tab_1_6">						
						
			<div class="box">
				<div class="tab-content">
					<div id="tab_1-1" class="tab-pane active">
						<form role="form" class="form-horizontal" action=" " method="post">
				<div class="portlet-body">
						<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 1px;">
					<div id="excelExportDiv4">
					<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport4" 
									onclick="tableToExcel4('sample_4', 'Load Survey Details')">Export
										to Excel</a></li>
							</ul>
							</div>
						</div>	
					</div>
					
					<table class="table table-striped table-hover table-bordered"
						id="sample_4">
						<thead>
							<tr>
								<!-- <th>Meter No</th> -->
								<th>Read Time</th>
								<th>VR</th>
								<th>VY</th>
								<th>VB</th>
								<th>IR</th>
								<th>IY</th>
								<th>IB</th>
								<th>KWH</th>
								<th>KVARH Lag</th>
								<th>KVARH Lead</th>
								<th>KVAH</th>
								<th>Frequency</th>
							</tr>
						</thead>
						<tbody id="loadSurveyTR">
							
						</tbody>
					</table>
					<div id='loadingmessage' style='display:none'>
  									<img src="resources/assets/img/home/ajax-loader(1).gif" class="ajax-loader" width=150px;  height=130px;>
  									<span id="load" class="wait" style="color:blue;"></span>
								</div>
				</div>
					</form>
				</div>
			</div>
			</div>
		</div>
		
		<div class="tab-pane" id="tab_1_7">						
						
			<div class="box">
				<div class="tab-content">
					<div id="tab_1-1" class="tab-pane active">
						<form role="form" class="form-horizontal" action=" " method="post">
				<div class="portlet-body">
						<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 1px;">
					<div id="excelExportDiv5">
					<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport5" 
									onclick="tableToExcel5('sample_5', 'Daily Load Survey')">Export	to Excel</a></li>
							</ul>
							</div>
						</div>	
					</div>
					
					<table class="table table-striped table-hover table-bordered"
						id="sample_5">
						<thead>
							<tr>
								<th>TransactionCode</th>
								<th>Transaction Description</th>
								<th>TransactionTime</th>
							</tr>
						</thead>
						<tbody id="dailyLoadSurveyTR">
							
						</tbody>
					</table>
				</div>
					</form>
				</div>
			</div>
			</div>
		</div>
		
		<div class="tab-pane" id="tab_1_8">						
						
			<div class="box">
				<div class="tab-content">
					<div id="tab_1-1" class="tab-pane active">
						<form role="form" class="form-horizontal" action=" " method="post">
				<div class="portlet-body">
						<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 1px;">
					<div id="excelExportDiv5">
					<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport5" 
									onclick="tableToExcel5('sample_5', 'Bill History')">Export	to Excel</a></li>
							</ul>
							</div>
						</div>	
					</div>
					
					<table class="table table-striped table-hover table-bordered"
						id="sample_6">
						<thead>
							<tr>
								<!-- <th>Meter No</th> -->
								<th>Date</th>
								<th>kwh</th>
								<th>kvah</th>
								<th>kvarh_lag</th>
								<th>kvarh_lead</th>
								<th>kva</th>
								<th>kw</th>
							</tr>
						</thead>
						<tbody id="billHistoryBody">
							
						</tbody>
					</table>
				</div>
					</form>
				</div>
			</div>
			</div>
		</div>
		
		<!--Graph Info  -->
		<%-- <div class="tab-pane" id="tab_1_8">						
			<div class="box">
				<div class="tab-content">
					<div id="tab_1-1" class="tab-pane active">
						<form role="form" class="form-horizontal" action=" " method="post">
							<div class="form-body">
				  				<div class="table-responsive">
									<h4><b>Graph Details</b></h4>
							</div>
						</div>
					</form>
				</div>
			</div>
			</div>
		</div> --%>
		<!--Graph Info end -->
		<!--end tab-pane 2-->
		<!-- <button type="button" style="margin-left: 1000px;" data-dismiss="modal" class="btn red">Close</button> -->
		</div>
		</div>
					</div>
					
						
							
						</div>
		</div>
	
</div>
<style>						
#loadingmessage {
  height: 400px;
  position: relative;
//  background-color: gray; /* for demonstration */
}
.ajax-loader {
  position: absolute;
  left: 50%;
  top: 50%;
  margin-left: -32px; /* -1 * image width / 2 */
  margin-top: -32px; /* -1 * image height / 2 */
}
#load {
  height: 500px;
  position: relative;
//  background-color: gray; /* for demonstration */
}
.wait{
  position: absolute;
  left: 50%;
  top: 60%;
  margin-left: -2px; /* -1 * image width / 2 */
  margin-top: -32px; /* -1 * image height / 2 */
}
							
</style>