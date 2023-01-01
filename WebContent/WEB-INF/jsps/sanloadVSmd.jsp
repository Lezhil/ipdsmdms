<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>

$(".page-content").ready(
		function() {
			App.init();
			TableEditable.init();
			FormComponents.init();
			loadSearchAndFilter('sample_1');
			 $('#MDMSideBarContents,#reportsId,#sanloadvsmd').addClass('start active ,selected');
			$('#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment').removeClass('start active ,selected');
		});
</script>

<script>
var mtrNum;
function mtrDetails(mtrNo)
{
	mtrNum=mtrNo;
	window.location.href="./viewFeederMeterInfoMDAS?mtrno=" + mtrNo;
	 /* window.open("./viewFeederMeterInfo?mtrno="+ mtrNo,"_blank"); */
}

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
                    html+="<option value='"+response[i]+"'>"+response[i]+"</option>";
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
                            html += "<option value='"+response[i]+"'>"
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
                            html += "<option value='"+response1[i]+"'>"
                                    + response1[i] + "</option>";
                        }
                        html += "</select><span></span>";
                        $("#subdivTd").html(html);
                        $('#sdoCode').select2();
                    }
                });
    }
 
 
</script>
<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Sanction Load vs Maximum Demand
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				
					<div class="row" style="margin-left: -1px;">

						<table style="width: 38%" >
								<tbody>
								<tr>
								<th id="zone1" class="block">Zone&nbsp;:</th>
								<th id="zones"><select
									class="form-control select2me input-medium" id="zone"
									name="circle" onchange="showCircle(this.value);">
										<option value=""></option>
										<option value="%">ALL</option>
										<c:forEach var="elements" items="${zonelist}">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select></th>
								<th class="block">Circle&nbsp;:</th>
								<th id="circleTd"><select
									class="form-control select2me input-medium" id="circle"
									name="circle" onchange="showDivision(this.value);">
									<option id="getCircles" value=""></option>
										<option value=""></option>
								</select></th>
								<th class="block">Division&nbsp;:</th>
								<th id="divisionTd"><select
									class="form-control select2me input-medium" id="division"
									name="division" onchange="showSubdivByDiv(this.value)">

								</select></th></tr>
							<tr>
								<th colspan="8"></th>
							</tr>
							<tr>
								<th colspan="8"></th>
							</tr>
							<tr>
								<th class="block">Sub&nbsp;Division&nbsp;:</th>
								<th id="subdivTd"><select
									class="form-control select2me input-medium" id="sdoCode"
									name="sdoCode"></select>
									
									<th class="block">Rdngmonth </th>
							<td>
							<div class="input-group date date-picker" data-date-format="yyyymm" data-date-viewmode="years">
							<input type="text" class="form-control" name="reportFromDate" id="reportFromDate" value="${readingMonth}" readonly>
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</td>				
																
							
									<td>
										<button type="button" id="viewFeeder" style="margin-left: 20px;"
											onclick="return getReport()" name="viewFeeder"
											class="btn yellow">
											<b>View</b>
										</button> <!-- <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button> -->
									</td>
								</tr>
							</tbody>
						</table>
						<p>&nbsp;</p>
					</div>
				
					
					<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Total Meters')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					
					<table class="table table-striped table-hover table-bordered" id="sample_1">
						<thead>
							<tr>
								<th>SL No.</th>
								<th>Month-Year</th>
								<th>Meter No.</th>
								<th>KNo</th>
								<th>Consumer Name</th>
								<th>Account No</th>
								<th>SubDivision</th>
								<th>Sanction Load (kW)</th>
								<th>Maximum Demand (kW)</th>
								<th>MD Date</th>
								<th>MD as % of SL</th>
							</tr>
						</thead>
						<tbody id="updateMaster">
						 
						</tbody>
					</table>
					
					
				</div>
			</div>
		</div>
	</div>
	
</div>



<script>


function getReport() {
	
	var zone=$("#zone").val();
	var circle=$('#circle').val();
	var division=$('#division').val();
	var subdiv = $('#sdoCode').val();
    var monthid=$('#reportFromDate').val();
 
  if(zone=='All'){
    	zone="%";
    }
    if(circle=='All'||circle==null){
    	circle="%";
    }
    if(division=='All'||division==null){
    	division="%";    
    }
    if(subdiv=="All" || subdiv==null){
    	subdiv="%";    
    } 
    if(monthid=="" || monthid==null){
    	bootbox.alert("Select Month-Year");
    	return false;
    }
    
	$('#updateMaster').empty();
	$.ajax({
		url : './getSanloadVSmdReport',
		type : 'GET',
		data : {
			subdiv : subdiv,
			monthid: monthid,
		},
		dataType : 'JSON',
		asynch : false,
		cache : false,
		success : function(response) {
			
			if (response.length != 0) {
				var html = "";
				var j=0;
				for (var i = 0; i < response.length; i++) {
					var element = response[i];
					
					var sl=parseFloat(element[6]).toFixed(3);
					var md=parseFloat(element[7]).toFixed(3);
					var per=parseFloat((sl-md)/sl*100).toFixed(3);
				
						
					html += "<tr><td>"+(j+1)+"</td>" 
					+"<td>"+ moment(element[0]).format("YYYY-MM")+"</td>"
					+ "<td>"
					+ "<a style='color:blue;' onclick='return mtrDetails(\""+element[1]+"\");'>"+element[1]+"</a>"
					+ "</td>"
					+"<td>"+ element[2]+"</td>"
					+ "<td>"+ element[3]+ "</td>"
					+ "<td>"+ element[4]+ "</td>"
					+ "<td>"+ element[5]+ "</td>"
					+ "<td>"+ sl+ "</td>"
					+ "<td>"+ md+ "</td>";
					
					if(element[8]!=null && element[8]!=''){
						//alert(element[7]);
						html +="<td>"
							+ moment(element[8]).format("YYYY-MM-DD HH:mm:ss")
							+ "</td>";
					} else{
						html +="<td>"
							+ "</td>";
					}
					
					html +="<td>"+per+ "</td>";
					
					html + " </tr>";
					j++;
					
				}
				$('#sample_1').dataTable().fnClearTable();
				$('#updateMaster').html(html);
				loadSearchAndFilter('sample_1');
				
			} else {
				bootbox.alert("No meters ");
			}

		}
	});

}

</script>