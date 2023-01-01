 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
 <style>
.table-toolbar {
    margin-bottom: 4px;
}
/* .paginate_button {
width: 10px;
} */
.paginate_button {
  padding: 15px 25px;
  font-size: 15px;
  text-align: center;
  cursor: pointer;
  outline: none;
  color: #fff;
  background-color:#717887;
  border: none;
  border-radius: 15px;
  box-shadow: 0 9px #999;
}

.paginate_button:hover {background-color: #597bc7}

.paginate_button:active {
  background-color: #3e8e41;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
}

</style>

<!-- <script  type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script> -->
<script  type="text/javascript" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script  type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js"></script>
<script  type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.flash.min.js"></script>
<script  type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
<script  type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
<script  type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
<script  type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.html5.min.js"></script>
<script  type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.print.min.js"></script>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
  <script  type="text/javascript">
  
  $(document).ready(function() {
	    /* $('#sample_3').DataTable( {
	        dom: 'Bfrtip',
	        buttons: [
	            'copy', 'csv', 'excel', 'pdf', 'print'
	        ]
	    } ); */
	} ); 
 var mtrNum=null;
 var frmDate=null;
 var tDate=null;
   $(".page-content").ready(function()

   {     
	  //alert('${mtrno}');
	  
	  $('#fromDate').val('${fromDate}');
	  $('#toDate').val('${toDate}');
	  $('#meterNum').val('${mtrno}');
	  	frmDate='${fromDate}';
		tDate='${toDate}';
	 	mtrNum='${mtrno}';
	 	
	 	App.init();
		TableEditable.init();
		FormComponents.init();
		$('#360d-view').addClass('start active ,selected');
		$("#MDASSideBarContents,#dash-board,#user-location,#MIS-Reports,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn").removeClass('start active ,selected');
   });	 
  
  $('#MDMSideBarContents,#360d-view').addClass('start active ,selected');
  
  /* $( "a.paginate_button" ).last().addClass( "button" ); */
  
  
  </script>
  
  <script type="text/javascript">
 
  
  
  function billHistoryDetailsCurve() {
	  
	  var dataPoints = [];
	  var consumption="";
	  var mtrNo=mtrNum;
	  
		$.ajax({
	    	url : './getbillHistoryDetails/'+mtrNo+'/'+frmDate+'/'+tDate,
	    	type:'GET',
	    	dataType:'json',
	    	
	    	success:function(data)
	    	{	    		
		       for (var i = 0; i < data.length; i++) {
		        	var resp=data[i];
		   	    /* 	if(i==0)
					{
					 consumption=resp[15]-0;
					}else{
						  var result=data[i-1];
						  consumption=resp[15]-result[15];
						}	 */   
						
		    	    	dataPoints.push({
					    	 x:	new Date(resp[5]),
					    	 y: ((parseInt(resp[15]))/1000)
					      });
					    }
					    var chart = new CanvasJS.Chart("chartContainer", {
					      title: {
					        text: "Bill History Details Curve (Month Wise)"
					      },
					      axisX: {
					        title: "Month",
					        	 interval:1,
					             intervalType: "month"
					      
					      },
					      axisY: {
					        includeZero: false,
					        title: "Consumption",
					        
					      },
					      data: [{
					        type: "column",
					        dataPoints: dataPoints
					      }]
					    });

					    chart.render();		    			    	
	    		},
			  });		    	    		
  }	
  
  function downloadReciept()
  {
   var meterNo=mtrNum;	
  	 if(meterNo=="")
  	 {
  	     alert("Please Enter Meter Number.");
  	     return false;
  	 }
  	 var month = $("#fromDate").val();
  	 window.open("./downloadReciept?meterNo="+meterNo+"&month="+month);
  }
  
function eventDetails()
{
	
	var mtrNo=mtrNum;
	if(mtrNo!=""){
		$("#imageeeE").show();
	}
	
	if(frmDate==''&&tDate=='')
	{
	  frmDate='${presentDate}';
	  tDate='${firstDate}';
	}
	$.ajax({
    	url : './getEventData/'+mtrNo+'/'+frmDate+'/'+tDate,
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
					var evtdate=moment(resp[1]).format('YYYY-MM-DD hh:mm:ss');
					 html+="<tr>"
							/* +" <td>"+resp[0]+"</td>" */
							+" <td>"+evtdate+"</td>"
							+" <td>"+resp[2]+"</td>";
					var edesc=getEventDesc(resp[2]);
					
	    			 var resp3 = resp[3];
	    			 if(resp3 == null){
	    				 resp3 ='';
					}else {
						var v=resp[3];
						var v1=v.split(',');
						var starts = moment(v1[0]);
						var ends   = moment(v1[1]);
						
						var ms = starts.diff(ends,'days');
						var ms1 = (starts.diff(ends,'hours'))%24;
						var ms2 = (starts.diff(ends,'minutes'))%60;
						var ms3 = (starts.diff(ends,'seconds'))%60;
						if(ms1.toString().length==1){
							ms1='0'+ms1;
						}
						if(ms2.toString().length==1){
							ms2='0'+ms2;
						}
						if(ms3.toString().length==1){
							ms3='0'+ms3;
						}
					
						if(ms=='1'){
							resp3 = ms+'day '+ms1+':'+ms2+':'+ms3;
						}
						
						resp3 = ms+'days '+ms1+':'+ms2+':'+ms3;
						
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
	    			 var resp12 = resp[12];
	    			 if(resp12 == null){
	    				 resp12 =0;
					}else {
						resp12 = resp[12];
						}
	    			 var resp13 = resp[13];
	    			 if(resp13 == null){
	    				 resp13 =0;
					}else {
						resp13 = resp[13];
						}
					
	    			resp13=parseInt(resp13)/1000; 
	    			
					html+=" <td>"+edesc+"</td>";		
					html+=" <td>"+resp3+"</td>"
							+" <td>"+resp4+"</td>"
							+" <td>"+resp5+"</td>"
							+" <td>"+resp6+"</td>"
							+" <td>"+resp7+"</td>"
							+" <td>"+resp8+"</td>"
							+" <td>"+resp9+"</td>"
							+" <td>"+resp10+"</td>"
							+" <td>"+resp11+"</td>"
							+" <td>"+resp12+"</td>"
							+" <td>"+resp13+"</td>"
							+" </tr>";
		   		}
	   			 /* $('#sample_2').dataTable().fnClearTable(); */
	   		 	$('#eventTR').html(html);
	   			 $('#meterNoHeading').html(mtrNo);
	   		 $('#sample_2').DataTable( {
	   			destroy: true,
	 	        dom: 'Bfrtip',
	 	       "ordering": false,
	 	        /* buttons: [
	 	             'excel', 'pdf', 'print'
	 	        ] */
	 	       buttons: [
	 	                {
	 	                    extend: 'excelHtml5',
	 	                    title: 'Event Details' //METER NO:'+mtrNo+'   
	 	                },
	 	                {
	 	                    extend: 'pdfHtml5',
	 	                    title: 'Event Details'
	 	                }
	 	            ]
	 	    } );
	    	}
    		$("#imageeeE").hide();
    	}
		/* complete: function()
		{  
			loadSearchAndFilter('sample_2');
		}   */
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

 function phaseDiagram(Ia,Ib,Ic)
{
	  var iA1 = 180-parseInt(Ia);
	  var iB1 = 180-parseInt(Ib);
	  var iC1 = 180-parseInt(Ic);
	  
	 /*  var vA1 = 180-parseInt(Va);
	  var vB1 = 180-parseInt(Vb);
	  var vC1 = 180-parseInt(Vc); */
	  
	  var c = document.getElementById("myCanvas");
	  var ctx = c.getContext("2d");

	  ctx.beginPath();
	  ctx.arc(110,150,90,0,2*Math.PI);
	  ctx.strokeStyle="black";
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.moveTo(110,150);
	  //ctx.lineTo((110+90*Math.sin(180* (Math.PI / 180))),(150+90*Math.cos(180* (Math.PI / 180))));
	  ctx.strokeStyle="red";
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'black';
	 // ctx.strokeText('V1', (110+90*Math.sin(180* (Math.PI / 180))),(150+90*Math.cos(180* (Math.PI / 180))));
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.moveTo(110,150);
	 // ctx.lineTo((110+90*Math.sin(60* (Math.PI / 180))),(150+90*Math.cos(60* (Math.PI / 180))));
	  ctx.strokeStyle="yellow";
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'black';
	 // ctx.strokeText('V2', (110+90*Math.sin(60* (Math.PI / 180))),(150+90*Math.cos(60* (Math.PI / 180))));
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.moveTo(110,150);
	  //ctx.lineTo((110+90*Math.sin(-60* (Math.PI / 180))),(150+90*Math.cos(-60* (Math.PI / 180))));
	  ctx.strokeStyle="blue";
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'black';
	 // ctx.strokeText('V3', (110+90*Math.sin(-65* (Math.PI / 180))),(150+90*Math.cos(-55* (Math.PI / 180))));
	  ctx.stroke();

	  //current values

	  ctx.beginPath();
	  ctx.moveTo(110,150);
	  //ctx.lineTo((110+90*Math.sin(iA1* (Math.PI / 180))),(150+90*Math.cos(iA1* (Math.PI / 180))));
	  ctx.strokeStyle="red";
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'black';
	  //ctx.strokeText('L1', (110+90*Math.sin(iA1* (Math.PI / 180))),(150+90*Math.cos(iA1* (Math.PI / 180))));
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.moveTo(110,150);
	 // ctx.lineTo((110+90*Math.sin(iB1* (Math.PI / 180))),(150+90*Math.cos(iB1* (Math.PI / 180))));
	  ctx.strokeStyle="yellow";
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'black';
	  //ctx.strokeText('L2', (110+90*Math.sin(iB1* (Math.PI / 180))),(150+90*Math.cos(iB1* (Math.PI / 180))));
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.moveTo(110,150);
	  //ctx.lineTo((110+90*Math.sin(iC1* (Math.PI / 180))),(150+90*Math.cos(iC1* (Math.PI / 180))));
	  ctx.strokeStyle="blue";
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'black';
	  //ctx.strokeText('L3', (110+90*Math.sin(iC1* (Math.PI / 180))),(150+100*Math.cos(iC1* (Math.PI / 180))));
	  ctx.stroke(); 

	  

	  //Degrees labels
	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'red';
	  //ctx.strokeText('0', (110+90*Math.sin(180* (Math.PI / 180))),(140+90*Math.cos(180* (Math.PI / 180))));
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'red';
	  //ctx.strokeText('90', (120+90*Math.sin(90* (Math.PI / 180))),(150+90*Math.cos(90* (Math.PI / 180))));
	  ctx.stroke();


	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'red';
	  //ctx.strokeText('180', (110+90*Math.sin(0* (Math.PI / 180))),(160+90*Math.cos(0* (Math.PI / 180))));
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'red';
	 // ctx.strokeText('270', (90+90*Math.sin(-90* (Math.PI / 180))),(150+90*Math.cos(-90* (Math.PI / 180))));
	  ctx.stroke();
	  
	  ctx.beginPath();
	  ctx.fillStyle = "#FF0000";
	  ctx.fillRect(10, 260, 10, 10);
	  ctx.fillStyle = "#1a1b1c";
	  ctx.textAlign = "start";      
	  ctx.fillText("V1 L1", 30, 270);
	  
	  ctx.fillStyle = "#e2df31";
	  ctx.fillRect(70, 260, 10, 10);
	  ctx.fillStyle = "#1a1b1c";
	  ctx.textAlign = "start";      
	  ctx.fillText("V2 L2", 90, 270);
	  
	  ctx.fillStyle = "#2130a0";
	  ctx.fillRect(130, 260, 10, 10);
	  ctx.fillStyle = "#1a1b1c";
	  ctx.textAlign = "start";      
	  ctx.fillText("V3 L3", 150, 270);
	  ctx.stroke();

} 


//var KeymtrNo="";

function instansDetails()
{
	
	//KeymtrNo=mtrNum;
	var mtrNo=mtrNum;
	
	if(mtrNo!=""){
		$("#imageeeI").show();
	}
	$.ajax({
    	url : './getInstansDetails/'+mtrNo,
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
					if(resp[12]==null)
						{
						resp[12]="";
						}
					if(resp[10]==null)
					{
					resp[10]="";
					}
					if(resp[11]==null)
					{
					resp[11]="";
					}
					
					 html+="<tr>"
							+" <td>"+(i+1)+"</td>"
							/* +"<td>"+moment(resp[3]).format('DD-MM-YYYY hh:mm:ss')+</td>"
							/* +" <td>"+moment(resp[2]).format('YYYY-MM-DD HH:mm:ss')+"</td>" */
							/*+" <td>"+resp[5]+"</td>" */
							+" <td>"+resp[4]+"</td>"
							/* +" <td>"+resp[6]+"</td>" */
							+" <td>"+(resp[7]==null?resp[7]:(parseInt(resp[7])/1000))+"</td>"
							+" <td>"+(resp[8]==null?resp[8]:(parseInt(resp[8])/1000))+"</td>"
							
							+" <td>"+(resp[9]==null?resp[9]:(parseInt(resp[9])/1000))+"</td>"
							+" <td>"+resp[10]+"</td>"
							+" <td>"+resp[11]+"</td>"
							+" <td>"+resp[12]+"</td>"
							+" <td>"+resp[13]+"</td>"
							+" <td>"+resp[14]+"</td>"
							+" <td>"+resp[15]+"</td>"
							+" <td>"+resp[16]+"</td>"
							+" <td>"+resp[17]+"</td>"
							+" <td>"+resp[18]+"</td>"
							+" <td>"+resp[19]+"</td>"
							+" <td>"+resp[20]+"</td>"
							+" <td>"+resp[21]+"</td>"
							+" <td>"+resp[22]+"</td>"
							+" <td>"+resp[23]+"</td>"
							+" <td>"+resp[28]+"</td>"
							
							+" <td>"+(resp[29]==null?resp[29]:(parseInt(resp[29])/1000))+"</td>"
							+" <td>"+(resp[30]==null?resp[30]:(parseInt(resp[30])/1000))+"</td>"
							+" <td>"+(resp[31]==null?resp[31]:(parseInt(resp[31])/1000))+"</td>"
							
							+" <td>"+resp[32]+"</td>"
							+" <td>"+resp[33]+"</td>"
							/* +" <td>"+resp[34]+"</td>" */
							+" <td>"+resp[36]+"</td>"
							/* +" <td>"+moment(resp[37]).format('YYYY-MM-DD HH:mm:ss')+"</td>" */
							/* +" <td></td>" */
						    +" </tr>";
					
					/*  html+="<tr>"
							+" <td>"+"1"+"</td>"
							+" <td>"+"Voltage"+"</td>"
							+" <td>"+resp[16]+"</td>"
							+" <td>"+resp[17]+"</td>"
							+" <td>"+resp[18]+"</td>"
							+" </tr>"
							+"<tr>"
							+" <td>"+"2"+"</td>"
							+" <td>"+"Line Current"+"</td>"
							+" <td>"+resp[13]+"</td>"
							+" <td>"+resp[14]+"</td>"
							+" <td>"+resp[15]+"</td>"
							+" </tr>"
							+"<tr>"
							+" <td>"+"3"+"</td>"
							+" <td>"+"Power Factor"+"</td>"
							+" <td>"+resp[19]+"</td>"
							+" <td>"+resp[20]+"</td>"
							+" <td>"+resp[21]+"</td>"
							+" </tr>"
							+"<tr>"
							+" <td>"+"4"+"</td>"
							+" <td>"+"Phase Angle"+"</td>"
							+" <td>"+resp[10]+"</td>"
							+" <td>"+resp[11]+"</td>"
							+" <td>"+resp[12]+"</td>"
							+" </tr>"; */
					 phaseDiagram(resp[10],resp[11],resp[12]);
					 $("#submittername").text(moment(resp[3]).format('DD-MM-YYYY hh:mm:ss'));
		   		}
	   		
	   		 	$('#instantsTR').html(html);
	   		 	
	   		 $('#sample_3').DataTable( {
		   			destroy: true,
		 	        dom: 'Bfrtip',
		 	      "pagingType": "numbers",
		 	     "scrollX": true,
		 	       buttons: [
			 	                /*{
			 	                    extend: 'excelHtml5',
			 	                    title: 'Instants Details'  //METER NO:'+mtrNo+'&nbsp;&nbsp;&nbsp;
			 	                }  ,
			 	                {
			 	                    extend: 'pdfHtml5',
			 	                    title: 'Instants Details'
			 	                } */
			 	            ]
		 	    } );
	   		$("#imageeeI").hide();
	    	}
    	}/* ,
		complete: function(data)
		{  
			loadSearchAndFilter('sample_3');
			
		}  */
	  }); 
}

function loadSurveyDetails()
{
	
	if(frmDate==''&&tDate=='')
	{
	  frmDate='${presentDate}';
	  tDate='${firstDate}';
	}
	var mtrNo=mtrNum;
	if(mtrNo!=""){
		$("#imageeeL").show();
	}
	$.ajax({
    	url : './getLoadSurveyData/'+mtrNo+'/'+frmDate+'/'+tDate,
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
	    		var html2="";
	   		 	for(var i=0;i<data.length; i++)
				{
   		 		
					var resp=data[i];
	    			 var mtrdate=moment(resp[1]).format('YYYY-MM-DD HH:mm:ss'); 
	    			 var mtrTime=moment(resp[1]).format('HH:mm:ss'); 
	    			 if(mtrTime=='00:00:00'){
	    				    var newdate = new Date(mtrdate);
	    				    newdate.setDate(newdate.getDate() - 1);
	    				    var dd = newdate.getDate();
	    				    var mm = newdate.getMonth() + 1;
	    				    var y = newdate.getFullYear(); 
	    				    var someFormattedDate = y + '-' + mm + '-' + dd +' 24:00:00';
	    				    mtrdate=someFormattedDate;
	    			 }
	    			
					var resp2 = resp[2];
					if (resp2 == null) {
						resp2 = 0;
					} else {
						resp2 = resp[2];
					}

					var resp3 = resp[3];
					if (resp3 == null) {
						resp3 = 0;
					} else {
						resp3 = resp[3];
					}

					var resp4 = resp[4];
					if (resp4 == null) {
						resp4 = 0;
					} else {
						resp4 = resp[4];
					}

					var resp5 = resp[5];
					if (resp5 == null) {
						resp5 = 0;
					} else {
						resp5 = resp[5];
					}

					var resp6 = resp[6];
					if (resp6 == null) {
						resp6 = 0;
					} else {
						resp6 = resp[6];
					}

					var resp7 = resp[7];
					if (resp7 == null) {
						resp7 = 0;
					} else {
						resp7 = resp[7];
					}

					var resp8 = resp[8];
					if (resp8 == null) {
						resp8 = 0;
					} else {
						resp8 = resp[8];
					}

					var resp9 = resp[9];
					if (resp9 == null) {
						resp9 = 0;
					} else {
						resp9 = resp[9];
					}

					var resp10 = resp[10];
					if (resp10 == null) {
						resp10 = 0;
					} else {
						resp10 = resp[10];
					}

					var resp11 = resp[11];
					if (resp11 == null) {
						resp11 = 0;
					} else {
						resp11 = resp[11];
					}
					var resp12 = resp[12];
					if (resp12 == null) {
						resp12 = 0;
					} else {
						resp12 = resp[12];
					}

										/* 	var mtrdate=moment(resp[1]).format('HH:mm:ss'); */
										html2 += "<tr>"
										/* +" <td>"+resp[1]+"</td>"  */
										+ " <td>"
												+ mtrdate
												+ "</td>"

												+ " <td>"
												+ resp[2]
												+ "</td>"
												+ " <td>"
												+ resp[3]
												+ "</td>"
												+ " <td>"
												+ resp[4]
												+ "</td>"
												+ " <td>"
												+ resp[5]
												+ "</td>"
												+ " <td>"
												+ resp[6]
												+ "</td>"
												+ " <td>"
												+ resp[7]
												+ "</td>"
												+ " <td>"
												+ (resp[8] == null ? resp[8]
														: (parseInt(resp[8]) / 1000))
												+ "</td>"
												+ " <td>"
												+ (resp[9] == null ? resp[9]
														: (parseInt(resp[9]) / 1000))
												+ "</td>"
												+ " <td>"
												+ (resp[10] == null ? resp[10]
														: (parseInt(resp[10]) / 1000))
												+ "</td>"
												+ " <td>"
												+ (resp[11] == null ? resp[11]
														: (parseInt(resp[11]) / 1000))
												+ "</td>"
												/* +" <td>"+resp[12]+"</td>" */
												+ " </tr>";
									}
									/* $('#sample_4').dataTable().fnClearTable(); */
									$('#loadSurveyTR').html(html2);
									$('#sample_4').DataTable({
										destroy : true,
										"pageLength" : 10,
										"pagingType" : "full_numbers",

										dom : 'Bfrtip',
										buttons : [ {
											extend : 'excelHtml5',
											title : 'Load Survey Details'
										}, {
											extend : 'pdfHtml5',
											title : 'Load Survey Details'
										} ]
									});
									$("#imageeeL").hide();
								}
							}/* ,
									complete: function()
									{  
										loadSearchAndFilter('sample_4');
									}   */
						});
			}

			function dailyLoadSurveyDetails() {

				if (frmDate == '' && tDate == '') {
					frmDate = '${presentDate}';
					tDate = '${firstDate}';
				}
				var mtrNo = mtrNum;
				if (mtrNo != "") {
					$("#imageeeD").show();
				}
				$
						.ajax({
							url : './getDailyLoadSurveyData/' + mtrNo + '/'
									+ frmDate + '/' + tDate,
							type : 'GET',
							dataType : 'json',
							asynch : false,
							cache : false,
							success : function(data) {
								if (data.length == 0 || data.length == null) {
									bootbox
											.alert("Data Not Available for this Meter No. : "
													+ mtrNo);
								} else {
									var html = "";
									for (var i = 0; i < data.length; i++) {
										var resp = data[i];
										//alert(resp[0]);
										html += "<tr>" + " <td>"
												+ resp[0]
												+ "</td>"
												+ " <td>"
												+ resp[1]
												+ "</td>"
												+ " <td>"
												+ resp[2]
												+ "</td>"
												+ " <td>"
												+ resp[3]
												+ "</td>"
												+ " <td>"
												+ resp[4]
												+ "</td>"
												+ " <td>"
												+ resp[5]
												+ "</td>"
												+ " <td>"
												+ resp[6]
												+ "</td>"
												+ " <td>"
												+ (resp[7] == null ? resp[7]
														: (parseInt(resp[7]) / 1000))
												+ "</td>"
												+ " <td>"
												+ (resp[8] == null ? resp[8]
														: (parseInt(resp[8]) / 1000))
												+ "</td>"
												+ " <td>"
												+ (resp[9] == null ? resp[9]
														: (parseInt(resp[9]) / 1000))
												+ "</td>"
												+ " <td>"
												+ (resp[10] == null ? resp[10]
														: (parseInt(resp[10]) / 1000))
												+ "</td>"

												+ " </tr>";
									}
									/* $('#sample_5').dataTable().fnClearTable(); */
									$('#dailyLoadSurveyTR').html(html);
									$('#sample_5').DataTable({
										destroy : true,
										paging : true,

										dom : 'Bfrtip',
										buttons : [ {
											extend : 'excelHtml5',
											title : 'Daily Basis Details'
										}, {
											extend : 'pdfHtml5',
											title : 'Daily Basis Details'
										} ]
									});
									$("#imageeeD").hide();
								}
							}/* ,
									complete: function()
									{  
										loadSearchAndFilter('sample_5');
									}   */
						});
			}

			function billHistoryDetails() {

				var mtrNo = mtrNum;
				if (mtrNo != "") {
					$("#imageeeB").show();
				}
				$
						.ajax({
							url : './getbillHistoryDetails/' + mtrNo + '/'
									+ frmDate + '/' + tDate,
							type : 'GET',
							dataType : 'json',
							asynch : false,
							cache : false,
							success : function(data) {
								//alert(data);
								if (data.length == 0 || data.length == null) {
									bootbox
											.alert("Data Not Available for this Meter No. : "
													+ mtrNo);
								} else {
									var html = "";
									var consumption = "";
									for (var i = 0; i < data.length; i++) {
										var resp = data[i];

										//alert(resp);
										/* if(i==0)
												{
												 consumption=0; //resp[15]-0
												}else
													{
													  var result=data[i-1];
													  consumption=resp[15]-result[15];
													} */

										html += "<tr>" + "<td>"
												+ moment(resp[5]).format(
														"YYYY-MM-DD HH:mm:ss")
												+ "</td>"
												+ "<td>"
												+ (resp[15] == null ? resp[15]
														: (parseInt(resp[15]) / 1000))
												+ "</td>"
												+ "<td>"
												+ (resp[18] == null ? resp[18]
														: (parseInt(resp[18]) / 1000))
												+ "</td>"
												+ "<td>"
												+ (resp[45] == null ? resp[45]
														: (parseInt(resp[45]) / 1000))
												+ "</td>"
												+ "<td>"
												+ (resp[46] == null ? resp[46]
														: (parseInt(resp[46]) / 1000))
												+ "</td>"
												+ "<td>"
												+ (resp[68] == null ? resp[68]
														: (parseInt(resp[68]) / 1000))
												+ "</td>"
												+ "<td>"
												+ (resp[69] == null ? resp[69]
														: (parseInt(resp[69]) / 1000))
												+ "</td>"
												+ "<td>"
												+ (resp[7] == null ? resp[7]
														: (parseInt(resp[7]) / 1000))
												+ "</td>"
												+ " <td>"
												+ (resp[8] == null ? resp[8]
														: (parseInt(resp[8]) / 1000))
												+ "</td>"
												+ " <td>"
												+ (resp[9] == null ? resp[9]
														: (parseInt(resp[9]) / 1000))
												+ "</td>"
												+ " <td>"
												+ (resp[10] == null ? resp[10]
														: (parseInt(resp[10]) / 1000))
												+ "</td>"
												+ " <td>"
												+ (resp[11] == null ? resp[11]
														: (parseInt(resp[11]) / 1000))
												+ "</td>"

												+ "<td>"
												+ (resp[12] == null ? resp[12]
														: (parseInt(resp[12]) / 1000))
												+ "</td>"
												+ "<td>"
												+ (resp[13] == null ? resp[13]
														: (parseInt(resp[13]) / 1000))
												+ "</td>"
												+ "<td>"
												+ (resp[14] == null ? resp[14]
														: (parseInt(resp[14]) / 1000))
												+ "</td>"

												+ "<td>"
												+ (resp[19] == null ? resp[19]
														: (parseInt(resp[19]) / 1000))
												+ "</td>"
												+ "<td>"
												+ (resp[20] == null ? resp[20]
														: (parseInt(resp[20]) / 1000))
												+ "</td>"
												+ "<td>"
												+ (resp[21] == null ? resp[21]
														: (parseInt(resp[21]) / 1000))
												+ "</td>"
												+ "<td>"
												+ (resp[22] == null ? resp[22]
														: (parseInt(resp[22]) / 1000))
												+ "</td>"
												+ "<td>"
												+ (resp[23] == null ? resp[23]
														: (parseInt(resp[23]) / 1000))
												+ "</td>"
												+ "<td>"
												+ (resp[24] == null ? resp[24]
														: (parseInt(resp[24]) / 1000))
												+ "</td>"
												+ "<td>"
												+ (resp[25] == null ? resp[25]
														: (parseInt(resp[25]) / 1000))
												+ "</td>"
												+ "<td>"
												+ (resp[26] == null ? resp[26]
														: (parseInt(resp[26]) / 1000))
												+ "</td>"
												+ "<td>"
												+ (resp[27] == null ? resp[27]
														: (parseInt(resp[27]) / 1000))
												+ "</td>"
												+ "<td>"
												+ (resp[28] == null ? resp[28]
														: (parseInt(resp[28]) / 1000))
												+ "</td>"

												+ "<td>"
												+ (resp[16] == null ? resp[16]
														: (parseInt(resp[16]) / 1000))
												+ "</td>"
												+ "<td>"
												+ (resp[17] == null ? resp[17]
														: (parseInt(resp[17]) / 1000))
												+ "</td>" + " </tr>";

									}
									/* $('#sample_6').dataTable().fnClearTable(); */
									$('#billHistoryBody').html(html);
									//$('#graphId').show();

									$('#sample_6').DataTable({
										destroy : true,
										"pagingType" : "numbers",
										"scrollX" : true,

										dom : 'Bfrtip',
										buttons : [ {
											extend : 'excelHtml5',
											title : 'Bill History Details'
										}, {
											extend : 'pdfHtml5',
											title : 'Bill History Details'
										} ]
									});
									$("#imageeeB").hide();
								}
							}/* ,
									complete: function()
									{  
										loadSearchAndFilter('sample_6');
									}   */
						});

				billHistoryDetailsCurve();
			}

			function downloadPdfs() {

				var mtrNum = $('#meterNum').val();
				//var radioVal = $("input[name='optionsRadios']:checked").val();

				window.open("./downloadPdfs?key=instant&meterNo=" + mtrNum);
			}

			function loadSearchAndFilter(param) {
				$('#' + param).dataTable().fnDestroy();
				$('#' + param).dataTable(
						{
							"aLengthMenu" : [ [ 10, 20, 50, 100, -1 ],
									[ 10, 20, 50, 100, "All" ] // change per page values here
							],
							"iDisplayLength" : 10
						});
				jQuery('#' + param + '_wrapper .dataTables_filter input')
						.addClass("form-control input-small"); // modify table search input 
				jQuery('#' + param + '_wrapper .dataTables_length select')
						.addClass("form-control input-xsmall"); // modify table per page dropdown 
				jQuery('#' + param + '_wrapper .dataTables_length select')
						.select2(); // initialize select2 dropdown 
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
			<div class="caption">
				<i class="fa fa-reorder"></i>360 Degree View of the Meter No/AccNo :<span
					style="color: aqua; font-size: 18px; font-weight: bold;">${mtrno}</span>
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a
					href="#portlet-config" data-toggle="modal" class="config"></a> <a
					href="javascript:;" class="reload"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>


		<div class="portlet-body">
			<div class="row" style="margin-left: -1px;">
				<form>
					<table style="width: 25%">
						<tbody>
							<tr>
								<td><input class="form-control input-medium"
									placeholder="Enter Meter No. or AccNo" name="meterNum" autocomplete="off"
									id="meterNum" /></td>
								<td><div class="col-md-3">
										<div
											class="input-group input-large date-picker input-daterange"
											data-date-format="yyyy-mm-dd">
											<input type="text" placeholder="From Date"
												class="form-control"
												value="<fmt:formatDate value="${currentDate}" pattern="yyyy-mm-dd" />"
												data-date="${currentDate}" data-date-format="yyyy-mm-dd" autocomplete="off"
												data-date-viewmode="years" name="fromDate" id="fromDate">
											<span class="input-group-addon">to</span> <input type="text"
												placeholder="To Date" class="form-control"
												value="<fmt:formatDate value="${currentDate}" pattern="yyyy-mm-dd" />"
												data-date="${currentDate}" data-date-format="yyyy-mm-dd" autocomplete="off"
												data-date-viewmode="years" name="toDate" id="toDate">
										</div>
									</div></td>


								<td><button type="submit" id="dataview" class="btn yellow"
										formaction="./fullViewMDAS" formmethod="post">
										<b>View</b>
									</button></td>
							</tr>
						</tbody>
					</table>
				</form>

			</div>
			<div class="modal-body">

				<div class="tabbable tabbable-custom tabbable-full-width">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#tab_1_2" data-toggle="tab">Consumer
								Data</a></li>
						<li><a href="#tab_1_3" onclick="return eventDetails();"
							data-toggle="tab">Events Details</a></li>
						<li><a href="#tab_1_4" onclick="return instansDetails();"
							data-toggle="tab">Instants Details</a></li>
						<!-- <li><a href="#tab_1_5" data-toggle="tab">Analysis Details</a></li> -->
						<li><a href="#tab_1_6" onclick="return loadSurveyDetails();"
							data-toggle="tab">Load Survey Details</a></li>
						<li><a href="#tab_1_7"
							onclick="return dailyLoadSurveyDetails();" data-toggle="tab">Daily
								Load</a></li>
						<li><a href="#tab_1_8" onclick="return billHistoryDetails();"
							data-toggle="tab">Bill History Details</a></li>
						<!-- <li><a href="#tab_1_8" data-toggle="tab">Graph Details</a></li> -->
					</ul>
					<div class="tab-content">

						<!--tab_1_2-->
						<div class="tab-pane active" id="tab_1_2">

							<div class="box">

								<div id="tab_1-1" class="tab-pane active">
									<form role="form" class="form-horizontal" action=" "
										method="post">
										<div class="form-body">
											<div class="table-responsive">
												<h4>
													<b>Consumer Details</b>
												</h4>

												<table class="table table-striped table-bordered">
													<tbody>
														<c:forEach var="element" items="${mtrFdrList}">
															<%-- <tr>
																<th>Zone</th>
																<td>${element.zone}</td>
																<th>Consumer Name</th>
																<td>${element.customer_name}</td>
															</tr> --%>
															<tr>
																<th>Circle</th>
																<td>${element[0]}</td>
																<th>Consumer Mobile</th>
																<td>${element[1]}</td>
															</tr>
															<tr>
																<th>Division</th>
																<td>${element[2]}</td>
																<th>Consumer Address</th>
																<td>${element[3]}</td>
															</tr>
															<tr>
																<th>SubDivision</th>
																<td>${element[4]}
																</th>
																<th>Account No
																</td>
																<td>${element[5]}</td>
															</tr>
															 <tr>
																<th>MeterNo</th>
																<td>${element[6]}</td>
																<th>Tariff Code</th>
																<td>${element[7]}</td>
															</tr> 
															<tr>
																<th>Industry Type</th>
																<td>${element[8]}</td>
																<th>KNo</th>
																<td>${element[9]}</td>
															</tr>
														 <tr>
																<th>Consumer Name</th>
																<td>${element[10]}</td>
																<th>Supply Type</th>
																<td>${element[11]}</td>
															</tr> 
															
															<%-- <tr>
																<th>MR Name</th>
																<td>${element.mrname}</td>
																<th>Status</th>
																<td>${element.consumerstatus}</td>
															</tr> --%>
															<%-- <tr>
																<th>Latitude</th>
																<td>${element.latitude}</td>
																<th>Longitude</th>
																<td>${element.longitude}</td>
															</tr> --%>
														</c:forEach>
													</tbody>

												</table>
												<%-- <h4>
													<br>
													<b>Meter & Modem Details</b></br>
												</h4>
												<table class="table table-striped table-bordered">
													<tbody>
														<c:forEach var="element" items="${mtrFdrList}">
															<tr>
																<th>Meter Number</th>
																<td>${element.mtrno}</td>
																<th>Meter Make</th>
																<td>${element.mtrmake}</td>
																<th>Meter Type</th>
																<td>${element.dlms}</td>
															</tr>
															<tr>
																<th>Year Of Man</th>
																<td>${element.year_of_man}</td>
																<th>CT Ratio</th>
																<td>${element.ct_ratio}</td>
																<th>PT Ratio</th>
																<td>${element.pt_ratio}</td>
															</tr>
															<tr>
																<th>MF</th>
																<td>${element.mf}</td>
																<th>SIM Number</th>
																<td>${element.simno}</td>
																<th>Modem S.No</th>
																<td>${element.modem_sl_no}</td>
															</tr>
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
								<div id="imageeeE" hidden="true" style="text-align: center;">
		<h3 id="loadingText">Loading..... Please wait.</h3>
		<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
			style="width: 4%; height: 4%;">
	</div>
									<div id="tab_1-1" class="tab-pane active">
										<form role="form" class="form-horizontal" action=" "
											method="post">

											<div class="portlet-body">
												<div class="table-toolbar">
													<div class="btn-group pull-right" style="margin-top: 1px;">
														<!-- <div id="excelExportDiv2">
					<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport2" 
									onclick="tableToExcel('sample_2', 'Events Details')">Export
										to Excel</a></li>
							</ul>
										</div> -->
													</div>
												</div>
												<table
													class="table table-striped table-hover table-bordered"
													id="sample_2">
													<thead>
														<!-- <th>Meter No</th> -->
														<tr hidden="true">
															<th colspan="2">METER NO:
																<div id="meterNoHeading"></div>
															</th>
														</tr>
														<tr>
															<th>Event Time</th>
															<th>Event Code</th>
															<th>Event Desc</th>
															<th>Duration</th>
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
								<div id="imageeeI" hidden="true" style="text-align: center;">
		<h3 id="loadingText">Loading..... Please wait.</h3>
		<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
			style="width: 4%; height: 4%;">
	</div>
									<div id="tab_1-1" class="tab-pane active">
									<div align="center"><!-- <input type='text' id="mterKey"/> --><button class="btn green" type="button" onclick=" downloadPdfs();">Export to PDF</button></div>
										<form role="form" class="form-horizontal" action=" "
											method="post">
											<div class="portlet-body">
												<div class="table-toolbar">
													<div class="btn-group pull-right" style="margin-top: 1px;">
														
														<!-- <button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button> -->
														<!--<div id="excelExportDiv3">
							 <button type="button" class="btn btn-primary"  onclick="downloadReciept();" style="margin-left: 96px;"><strong>Export To PDF</strong></button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport3" 
									onclick="tableToExcel('sample_3', 'Instants Details')">Export
										to Excel</a></li>
							</ul>
										</div> -->
													</div>
												</div>
												<!-- <table class="table table-striped table-hover table-bordered"
						id="sample_3">
						<thead>
							<tr>
								<th>Phase</th>
								<th>Voltage(KV)</th>
								<th>Current(Amps)</th>
								<th>Power Factor</th>
							</tr>
						</thead>
						<tbody id="instantsTR">
							
						</tbody>
					</table> -->
												<h5>
													<b>Reading Time :<span id="submittername"></span></b>
												</h5>
												<table
													class="table table-striped table-hover table-bordered"
													id="sample_3">

													<thead>
														<!-- <tr>
															<th>SlNo</th>
															<th>Parameter</th>
															<th>R-Phase</th>
															<th>Y-Phase</th>
															<th>B-Phase</th>
														</tr> -->
														<tr>
															<th>S.No</th>
															<!-- <th>Read Time</th> -->
															<th>Meter No</th>
															<!-- <th>Imei</th> -->
															<th>KWh</th>
															<th>KVah</th>
															<th>KVA</th>
															<th>i_r angle</th>
															<th>i_y angle</th>
															<th>i_b angle</th>
															<th>i_r</th>
															<th>i_y</th>
															<th>i_b</th>
															<th>v_r</th>
															<th>v_y</th>
															<th>v_b</th>
															<th>pf_r</th>
															<th>pf_y</th>
															<th>pf_b</th>
															<th>pf_threephase</th>
															<th>frequency</th>
															<th>power_kw</th>
															<th>Kvar</th>
															<th>Kvarh_lag</th>
															<th>Kvarh_lead</th>
															<th>power_off_count</th>
															<th>power_off_duration</th>
															<!-- <th>modem_reset_count</th> -->
															<th>tamper_count</th>
															<!-- <th>md_reset_date</th> -->
														</tr>
													</thead>
													<tbody id="instantsTR">

													</tbody>
												</table>
												<span><canvas id='myCanvas' width='250' height='270'
														style='border: 1px solid #d3d3d3; margin-left: 10px; margin-top: 36px;' hidden="true"></canvas></span>
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
								<div id="imageeeL" hidden="true" style="text-align: center;">
		<h3 id="loadingText">Loading..... Please wait.</h3>
		<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
			style="width: 4%; height: 4%;">
	</div>
									<div id="tab_1-1" class="tab-pane active">
										<form role="form" class="form-horizontal" action=" "
											method="post">
											<div class="portlet-body">
												<div class="table-toolbar">
													<div class="btn-group pull-right" style="margin-top: 1px;">
														<!-- <div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="print">Print</a></li>
										<li><a href="#" id="cmd" onclick="$('#sample_4').tableExport({fileName:'Load survey Report',type:'pdf',tableId:'sample_1'})">Export to pdf</a></li>
										<li><a href="#" id="excelExport" onclick="tableToExcel('sample_4', 'Load survey Report')">Export to Excel</a></li>
									</ul>
								</div> -->
													</div>
												</div>

												<table
													class="table table-striped table-hover table-bordered"
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
															<!-- <th>Frequency</th> -->
														</tr>
													</thead>
													<tbody id="loadSurveyTR">

													</tbody>
												</table>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>

						<div class="tab-pane" id="tab_1_7">

							<div class="box">
								<div class="tab-content">
								<div id="imageeeD" hidden="true" style="text-align: center;">
		<h3 id="loadingText">Loading..... Please wait.</h3>
		<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
			style="width: 4%; height: 4%;">
	</div>
									<div id="tab_1-1" class="tab-pane active">
										<form role="form" class="form-horizontal" action=" "
											method="post">
											<div class="portlet-body">
												<div class="table-toolbar">
													<div class="btn-group pull-right" style="margin-top: 1px;">
														<!-- <div id="excelExportDiv5">
					<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport5" 
									onclick="tableToExcel('sample_5', 'Daily Load Survey')">Export	to Excel</a></li>
							</ul>
							</div> -->
													</div>
												</div>

												<table
													class="table table-striped table-hover table-bordered"
													id="sample_5">
													<thead>
														<tr>
															<!-- <th>Meter No</th> -->
															<th>Date</th>
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
								<div id="imageeeB" hidden="true" style="text-align: center;">
		<h3 id="loadingText">Loading..... Please wait.</h3>
		<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
			style="width: 4%; height: 4%;">
	</div>
									<div id="tab_1-1" class="tab-pane active">
										<form role="form" class="form-horizontal" action=" "
											method="post">
											<div class="portlet-body">
												<div class="table-toolbar">
													<div class="btn-group pull-right" style="margin-top: 1px;">
														<!-- <div id="excelExportDiv5">
					<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport5" 
									onclick="tableToExcel('sample_5', 'Bill History')">Export	to Excel</a></li>
							</ul>
							</div> -->
													</div>
												</div>

												<table
													class="table table-striped table-hover table-bordered"
													id="sample_6">
													<thead>
														<tr>
															<!-- <th>Meter No</th> -->
															<th>Date</th>
															<th>KWh</th>
															<th>KVah</th>
															<th>KVa</th>
															<th>KVa OCC Date</th>
															<th>KWh-Units</th>
															<th>KVah-Units</th>
															<th>KWh TOD1</th>
															<th>KWh TOD2</th>
															<th>KWh TOD3</th>
															<th>KWh TOD4</th>
															<th>KWh TOD5</th>
															<th>KWh TOD6</th>
															<th>KWh TOD7</th>
															<th>KWh TOD8</th>
															<th>KVah TOD1</th>
															<th>KVah TOD2</th>
															<th>KVah TOD3</th>
															<th>KVah TOD4</th>
															<th>KVah TOD5</th>
															<th>KVah TOD6</th>
															<th>KVah TOD7</th>
															<th>KVah TOD8</th>
															<th>KW</th>
															<th>KW OCC DATE</th>
															<th>KVarh_lag</th>
															<th>KVarh_lead</th>
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
						
						<div  id="chartContainer" style="height: 370px; width: 100%;" ></div>
						    	<!-- <div hidden="true" id="graphId"></div> -->
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