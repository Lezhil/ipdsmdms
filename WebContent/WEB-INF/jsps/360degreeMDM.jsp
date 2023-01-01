<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>


<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
 <script  type="text/javascript">
App.init();
		TableEditable.init();
		FormComponents.init();
		$('#MDASSideBarContents,#meterOper,#mrd,#mftrd').addClass('start active ,selected');
		$("#dash-board,#user-location,#MIS-Reports,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn").removeClass('start active ,selected');

</script>




<script>
function MeterDetails(){
	
	$('#ir').empty();
	$('#iy').empty();
	$('#ib').empty();
	$('#vr').empty();
	$('#vy').empty();
	$('#vb').empty();
	$('#pfr').empty();
	$('#pfy').empty();
	$('#pfb').empty();
	$('#freq').empty();
	$('#kwh').empty();
	$('#kvah').empty();
	$('#kvarh_lag').empty();
	$('#kwh_expo').empty();
	$('#kvah_expo').empty();
	$('#kvarh_lead').empty();
	$('#kva').empty();
	$('#kvar').empty();
	$('#power_off_count').empty();
	$('#power_off_duration').empty();
	$('#tamper_count').empty();
	$('#ir_angle').empty();
	$('#iy_angle').empty();
	$('#ib_angle').empty();
	$('#vry_angle').empty();
	$('#vrb_angle').empty();
	$('#vyb_angle').empty();

	var mtrno = $("#mtrNo").val();
	
	if(mtrno==""){
		bootbox.alert("Enter Meter No")
		return false;
	}
	
	var mtrno = $("#mtrNo").val();
	
	$.ajax({
		url:'./getMeterDetailsAMI/'+mtrno,
		type:'GET',
		success:function(data)
		{
			
			if(data.lenght==0 || data.length==null)
			{
				bootbox.alert("Data not available for this meter NO. : "+mtrNo)
			}
			else{
				var html="";
				for(var i=0;i<data.length;i++)
				{
					var resp=data[0];
				
					
					
				 	 if(resp[0]!=null)
					 {
				 		$("#ir").text(resp[0]);
					 }
				 else
					 {
					 $("#ir").val("");
					 } 
				 	 
					 
					 if(resp[1]!=null)
					 {
						 $("#iy").text(resp[1]);
					 }
				 else
					 {
					 $("#iy").val("");
					 } 
					 
			       if(resp[2]!=null)
					 {
					 $("#ib").text(resp[2]);
					 }
				 else
					 {
					 $("#ib").val("");
					 } 
			 
					 
				 if(resp[3]!=null)
					 {
					 $("#vr").text(resp[3]);
					 }
				 else
					 {
					 $("#vr").val("");
					 } 
				 
					 
					 if(resp[4]!=null)
					 {
					 $("#vy").text(resp[4]);
					 }
				 else
					 {
					 $("#vy").val("");
					 } 
					 
					 
				   if(resp[5]!=null)
					 {
					 $("#vb").text(resp[5]);
					 }
				 else
					 {
					 $("#vb").val("");
					 } 
				   
					
				    if(resp[6]!=null)
					 {
					  $("#pfr").text(resp[6]);
					 }
				 else
					 {
					 $("#pfr").val("");
					 } 
				    
					
					 if(resp[7]!=null)
					 {
					  $("#pfy").text(resp[7]);
					 }
				 else
					 {
					 $("#pfy").val("");
					 } 
					 
					 
				   if(resp[8]!=null)
					 {
					$("#pfb").text(resp[8]);
					 }
				 else
					 {
					 $("#pfb").val("");
					 } 
					 
					 
					 if(resp[9]!=null)
					 {
					 $("#freq").text(resp[9]);
					 }
				 else
					 {
					 $("#freq").val("");
					 } 
					 
					 if(resp[10]!=null)
					 {
					 $("#kwh").text((resp[10]==""?resp[10]:(parseInt(resp[10])/1000)));
					 }
				 else
					 {
					 $("#kwh").val("");
					 }
					 
					 if(resp[11]!=null)
					 {
					 $("#kvah").text((resp[11]==""?resp[11]:(parseInt(resp[11])/1000)));
					 }
				 else
					 {
					 $("#kvah").val("");
					 }
					 /* $("#in").text(resp[13]);
					 $("#isys").text(resp[13]);
					 $("#vsys").text(resp[13]); */
					 /* $("#pfsys").text(resp[13]); */
					 
					 /* $("#phase_seq").text(resp[22]); */
					 if(resp[12]!=null)
						 {
						 $("#kvarh_lag").text((resp[12]==""?resp[12]:(parseInt(resp[12])/1000)));
						 }
					 else
						 {
						 $("#kvarh_lag").val("");
						 }
					 
					 if(resp[13]!=null)
					 {
					 $("#kwh_expo").text((resp[13]==""?resp[13]:(parseInt(resp[13])/1000)));
					 }
				 else
					 {
					 $("#kwh_expo").val("");
					 }
					 
					 if(resp[14]!=null)
					 {
					 $("#kvah_expo").text((resp[14]==""?resp[14]:(parseInt(resp[14])/1000)));
					 }
				 else
					 {
					 $("#kvah_expo").val("");
					 }
					 
					 if(resp[15]!=null)
					 {
					 $("#kvarh_lead").text((resp[15]==""?resp[15]:(parseInt(resp[15])/1000)));
					 }
				 else
					 {
					 $("#kvarh_lead").val("");
					 }
					 
					 if(resp[16]!=null)
					 {
					 $("#kva").text((resp[16]==""?resp[16]:(parseInt(resp[16])/1000)));
					 }
				 else
					 {
					 $("#kva").val("");
					 }
					 
					 /* $("#kw").text(resp[13]); */
					 
					 if(resp[17]!=null)
					 {
					 $("#kvar").text((resp[17]==""?resp[17]:(parseInt(resp[17])/1000)));
					 }
				 else
					 {
					 $("#kvar").val("");
					 }
					 
					 if(resp[18]!=null)
					 {
					 $("#power_off_count").text(resp[18]);
					 }
				 else
					 {
					 $("#power_off_count").val("");
					 } 
					 
					 
					 if(resp[19]!=null)
					 {
					 $("#power_off_duration").text(resp[19]);
					 }
				 else
					 {
					 $("#power_off_duration").val("");
					 }
					 
				 if(resp[20]!=null)
					 {
					 $("#tamper_count").text(resp[20]);
					 }
				 else
					 {
					 $("#tamper_count").val("");
					 } 
					 
					 
					 
					 
					 if(resp[21]!=null)
					 {
					 $("#ir_angle").text((resp[21]==""));
					 }
				 else
					 {
					 $("#ir_angle").val("");
					 }
					 
					 if(resp[22]!=null)
					 {
					 $("#iy_angle").text((resp[22]==""));
					 }
				 else
					 {
					 $("#iy_angle").val("");
					 }
					 
					 if(resp[23]!=null)
					 {
					 $("#ib_angle").text((resp[23]==""));
					 }
				 else
					 {
					 $("#ib_angle").val("");
					 }
					 
					 if(resp[24]!=null)
					 {
					 $("#vry_angle").text((resp[24]==""));
					 }
				 else
					 {
					 $("#vry_angle").val("");
					 }
					 
					 if(resp[25]!=null)
					 {
					 $("#vrb_angle").text((resp[25]==""));
					 }
				 else
					 {
					 $("#vrb_angle").val("");
					 }
					
					 if(resp[26]!=null)
					 {
					 $("#vyb_angle").text((resp[26]==""));
					 }
				 else
					 {
					 $("#vyb_angle").val("");
					 }
					
					$("#submittername1").text(resp[28]);
					$("#submittername").text(resp[27]).text(moment(resp[27]).format('DD-MM-YYYY && hh:mm:ss'));
					
			}
				/* $('#sample_3').dataTable().fnClearTable(); */
				/* $('#meter_data').html(html); */
			}
		},
		
		
	});
}
</script>

<div class="page-content">
<form action="" id="mtrdata" method="POST">
<div class="portlet box blue">
<div class="portlet-title">
<div class="caption"><i class="fa fa-reorder"></i>Meter First time read data<span style="color: aqua;font-size:18px;font-weight: bold;;">${mtrno}</span></div>
</div>
<div class="portlet-body">
<label class="radio-inline">
<input type="radio" name="optradio" checked>Meter No.
</label>
<table style="width: 25%">
<tbody>
<tr>
<td hidden="true"><input class="form-control input-medium" name="radioValu" id="radioValu" value="meterno" /></td>
<td><input class="form-control input-medium" placeholder="Enter Meter No." name="mtrNo" id="mtrNo" /></td>
<td><button  type="button" id="dataview" class="btn yellow" onclick="return MeterDetails()"><b>View</b></button></td>
</tr>
<tr>
<td><b>Meter No : </b> <span id="submittername1"></span></td>
</tr>
<tr>
<td><b>Reading Date and Time : </b><div id="submittername"></div></td>
</tr>
</tbody>
</table>
<br>
 <div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_3', 'Meter Data')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
<table class="table table-striped table-hover table-bordered" id="sample_3">
<tbody id="">
<tr><th>Ir</th><td id="ir" style="min-width: 120px;"></td><th>Iy</th><td id="iy" style="min-width: 120px;"></td><th>Ib</th><td id="ib" style="min-width: 120px;"></td></tr>
<tr><th>In</th><td id="in"></td><th>Isys</th><td id="isys"><th>Vsys</th><td id="vsys"></td></tr>
<tr><th>Vr</th><td id="vr"></td><th>Vy </th><td id="vy"></td><th>Vb</th><td id="vb"></td></tr>
 <tr><th>Pfr</th><td id="pfr"></td><th>Pfy</th><td id="pfy"></td><th>Pfb</th><td id="pfb"></td></tr>
<tr><th>Pfsys</th><td id="pfsys"></td><th>Frequency(Hz)</th><td  id="freq"></td><th>Phase Sequence</th><td id="phase_seq"></td></tr>
<tr><th>Kwh</th><td id="kwh"></td><th>Kvah</th><td id="kvah"></td><th>Kvarh Lag</th><td id="kvarh_lag"></td></tr>
<tr><th>Kwh(Export)</th><td id="kwh_expo"></td><th>Kvah(Export)</th><td id="kvah_expo"></td><th>Kvarh Lead</th><td id="kvarh_lead"></td></tr>
<tr><th>Kw</th><td id="kw"></td><th>kVA</th><td id="kva"><th>kVAr</th><td id="kvar"></td></tr>
<tr><th>Power off Count</th><td id="power_off_count"></td><th>Power off Duration (m)</th><td id="power_off_duration"></td><th>Tamper Count</th><td id="tamper_count"></tr>
<tr><th>Ir Angle</th><td id="ir_angle"><th>Iy Angle</th><td id="iy_angle"><th>Ib Angle</th><td id="ib_angle">
<tr><th>Voltage Angle(RY)</th><td id="vry_angle"><th>Voltage Angle(RB)</th><td id="vrb_angle"><th>Voltage Angle(YB)</th><td id="vyb_angle">
</tbody>

</table>


</div>
</div>
</form>
</div>
