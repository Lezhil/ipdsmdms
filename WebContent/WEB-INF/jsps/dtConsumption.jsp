
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<%
	if (session.getAttribute("username") == null || session.getAttribute("username") == null) {
%>
<script>
	window.location.href = "./?sessionVal=expired";
</script>
<%
	}
%>

<script>
$(".page-content").ready(	 	
		function() {
			App.init();
			TableEditable.init();
			
		    $('#dtWiseReport,#dtconsumptionid').addClass('start active ,selected');
			   $("#MDASSideBarContents,#dash-board2,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#360d-view,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
				.removeClass('start active ,selected');
			FormComponents.init();
			//$('#tab2id').hide();
			$('#fromtoid').hide();
			$('#selectdateid').hide();
			$('#dateselectid').show();
			$('#ipwisedateid').hide();
			$('#monthselectid').hide();
			$("#circle1").val("").trigger("change");
			$("#periodTd").val("").trigger("change");

		});

</script>
<script type="text/javascript">


function viewDTConsumptiondata()
{
	//alert("come inside--viewDTconsumption");
	var zone=$('#LFzone').val();
	var circle=$('#LFcircle').val();
	
	var division=$('#division1').val();
	var subdiv=$('#sdoCode1').val();
	var town=$('#LFtown').val();
	var feederTpId=$("#feederTpId").val();
    var period=$('#periodtype').val();
    var seldate=$('#SelectDateId').val();
    var selmonth=$('#month').val();

    if (zone == "" || zone==null) {
		bootbox.alert("Please Select Region");
		return false;
	}

    if (circle == "" || circle==null) {
		bootbox.alert("Please Select Circle");
		return false;
	}

	/* if (division == "" || division==null) {
		bootbox.alert("Please Select Division");
		return false;
	}

	if (subdiv == "" || subdiv==null) {
		bootbox.alert("Please Select Sub-Division");
		return false;
	} */
	if (town == "" || town==null) {
		bootbox.alert("Please Select Town");
		return false;
	}

	if (feederTpId == "" || feederTpId==null) {
		bootbox.alert("Please Select Feeder");
		return false;
	}
	
	if (period == "" || period==null) {
		bootbox.alert("Please Select Period");
		return false;
	}
    
   if(period == "Month")
   {
		if (selmonth == "" || selmonth==null) {
		bootbox.alert("Please Select Month");
		return false;
	}
   }
   if(period == "Date" )
   { 
	 if (seldate == "" || seldate==null) 
	 {
			bootbox.alert("Please Select Date");
			return false;
	     }
   }
   $('#imageee').show();
	$.ajax({
		 url :'./viewDTConsumptionData',
		type :'POST',
		data :{
			zone:zone,
			circle : circle,
		  division : division,
		  subdiv   : subdiv,
		  town     : town,
		  feederTpId:feederTpId,
		  period   : period,
		  seldate  : seldate,
		  selmonth : selmonth
		},
		dataType : 'json',
		success:function(response)
		{
			$('#imageee').hide();
			if (period == "Date")
			{
				$('#sample_1').dataTable().fnClearTable();
				$("#dtdatewise").html('');

				if (response != null && response.length > 0) {
				var html = "";
				for (var i = 0; i < response.length; i++) {
					var resp = response[i];
					var j = i + 1;
					html += "<tr>" 
						+ "<td>"+ j + "</td>"
						+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"
						+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"
						+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"
						+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
						+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
						+ "<td>"+ ((resp[8] == null) ? "": resp[8]) + " </td>"
						+ "<td style='text-align: center;'><a href='#' id='modifyid'+j onclick='return getIndividualdtConsum(\""+resp[8]+"\")'>"+resp[9] + "</a></td>"
						+ "<td>"+ ((resp[10] == null) ? "": resp[10]) + " </td>"
						+ "<td>"+ ((resp[11] == null) ? "": resp[11]) + " </td>"
						+ "<td>"+ ((resp[12] == null) ? "": resp[12]) + " </td>"
						+ "<td>"+ ((resp[13] == null) ? "": resp[13]) + " </td>"
						+ "<td>"+ ((resp[15] == null) ? "": resp[15]) + " </td>"
						+ "<td>"+ ((resp[17] == null) ? "": resp[17]) + " </td>"
						+ "<td>"+ ((resp[18] == null) ? "": resp[18]) + " </td>"
						+ "<td>"+ ((resp[19] == null) ? "": resp[19]) + " </td>"
						+ "<td>"+ ((resp[20] == null) ? "": resp[20]) + " </td>"
						+ "<td>"+ ((resp[21] == null) ? "": resp[21]) + " </td>"
						+ "<td>"+ ((resp[22] == null) ? "": resp[22]) + " </td>"
					/* 	+ "<td>"+ ((resp[23] == null) ? "": resp[23]) + " </td>"*/
						+ "<td>"+ ((resp[16] == null) ? "": resp[16]) + " </td>"
						
					/* 	+ "<td>"+ ((resp[12] == null) ? "": resp[12]) + " </td>"
						
						+ "<td>"+ ((resp[15] == null) ? "": resp[15]) + " </td>" */
						
					
					
						/* + "<td>"+ ((resp[19] == null) ? "": resp[19]) + " </td>"
						
						+ "<td>"+ ((resp[14] == null) ? "": resp[14]) + " </td>" */
						/* + "<td>"+ ((resp[21] == null) ? "": resp[21]) + " </td>" */
							+ "</tr>";
				}
				$('#sample_1').dataTable().fnClearTable();
				$("#dtdatewise").html(html);
				loadSearchAndFilter('sample_1');
			} else {
				bootbox.alert("No Relative Data Found.");
			}
			} 
			
			 if (period == "Month")
			{
				$('#sample_2').dataTable().fnClearTable();
				$("#dtmonthwise").html('');

				if (response != null && response.length > 0) {
				var html = "";
				for (var i = 0; i < response.length; i++) {
					var resp = response[i];
					var j = i + 1;
					html += "<tr>" 
						+ "<td>"+ j + "</td>"
						+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"
						+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"
						+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"
						+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
						+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
						+ "<td>"+ ((resp[8] == null) ? "": resp[8]) + " </td>"
						/* + "<td>"+ ((resp[9] == null) ? "": resp[9]) + " </td>" */
						+ "<td style='text-align: center;'><a href='#' id='modifyid'+j onclick='return getIndividualdtConsum(\""+resp[8]+"\")'>"+resp[9] + "</a></td>"
						
						+ "<td>"+ ((resp[10] == null) ? "": resp[10]) + " </td>"
						+ "<td>"+ ((resp[11] == null) ? "": resp[11]) + " </td>"
					
						+ "<td>"+ ((resp[12] == null) ? "": resp[12]) + " </td>"
						+ "<td>"+ ((resp[13] == null) ? "": resp[13]) + " </td>"
						+ "<td>"+ ((resp[15] == null) ? "": resp[15]) + " </td>"
						+ "<td>"+ ((resp[17] == null) ? "": resp[17]) + " </td>"
						+ "<td>"+ ((resp[18] == null) ? "": resp[18]) + " </td>"
						+ "<td>"+ ((resp[19] == null) ? "": resp[19]) + " </td>"
						+ "<td>"+ ((resp[20] == null) ? "": resp[20]) + " </td>"
						
						+ "<td>"+ ((resp[21] == null) ? "": resp[21]) + " </td>"
						+ "<td>"+ ((resp[22] == null) ? "": resp[22]) + " </td>" 			
						
						+ "<td>"+ ((resp[16] == null) ? "": resp[16]) + " </td>"
					
						 + "<td>"+ ((resp[25] == null) ? "": resp[25]) + " </td>" 
						+ "<td>"+ ((resp[26] == null) ? "": (moment(resp[26]).format('YYYY-MM-DD'))) + " </td>";
					/* 	+ "<td>"+ ((resp[22] == null) ? "": resp[22]) + " </td>"
						+ "<td>"+ ((resp[23] == null) ? "": resp[23]) + " </td>"
						+ "<td>"+ ((resp[17] == null) ? "": resp[17]) + " </td>"
						
						 + "<td>"+ ((resp[26] == null) ? "": resp[26]) + " </td>"  
					/* 	 + "<td>"+ ((resp[12] == null) ? "": resp[12]) + " </td>"
						 + "<td>"+ ((resp[15] == null) ? "": resp[15]) + " </td>" */
						
						//+ "<td>"+ ((resp[27] == null) ? "": (moment(resp[27]).format('YYYY-MM-DD'))) + " </td>"; 
						
						+ "</tr>";

				}
				$('#sample_2').dataTable().fnClearTable();
				$("#dtmonthwise").html(html);
				loadSearchAndFilter('sample_2');
			} else {
				bootbox.alert("No Relative Data Found.");
			}
			} 
		},
		complete : function() {
			if (period == "Date") {
				loadSearchAndFilter('sample_1');
			}
			if (period == "Month") {
				loadSearchAndFilter('sample_2');
				}
		}
		
	});

}


function viewIndividualDTConsumptiondata()
{
    var zone=$('#LFzone').val();
	var circle=$('#LFcircle').val();
	var division=$('#division1').val();
	var subdiv=$('#sdoCode1').val();
	var town=$('#LFtown').val();
	var dt=$('#dt1').val();
    var period=$('#periodtype1').val();
    var fromdate=$('#FromDateId1').val();
    var todate=$('#ToDateId1').val();
    var noofmtrs=$('#noofmtrs1').val();
    var capacity=$('#dtCapacity1').val();
    var ipwisedate=$('#IpDateId1').val();
    var frommonth=$('#FromMonthId1').val();
    var tomonth=$('#ToMonthId1').val();

    if (zone == "" || zone==null) {
		bootbox.alert("Please Select Region");
		return false;
	}
    
    
    if (circle == "" || circle==null) {
		bootbox.alert("Please Select circle");
		return false;
	}

	/* if (division == "" || division==null) {
		bootbox.alert("Please Select Division");
		return false;
	}

	if (subdiv == "" || subdiv==null) {
		bootbox.alert("Please Select Sub-Division");
		return false;
	} */
	if (town == "" || town==null) {
		bootbox.alert("Please Select Town");
		return false;
	}
	if (dt == "" || dt==null) {
		bootbox.alert("Please Select DT");
		return false;
	}
	if (period == "" || period==null) {
		bootbox.alert("Please Select Period");
		return false;
	}
	
    var date1 = new Date(fromdate);
    var date2 = new Date(todate);
    var timeDiff = Math.abs(date2.getTime() - date1.getTime());
    var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
   
    var fromYear=date1.getFullYear();
    var toYear=date2.getFullYear();
    var diffyear =toYear-fromYear;

    if(period == "Date" )
    {
    	if (fromdate == "" || fromdate==null) 
    	{
    		bootbox.alert("Please Select From Date");
    		return false;
    	}
    	
    	if (todate == "" || todate==null) 
    	{
    		bootbox.alert("Please Select To Date");
    		return false;
    	}
    	
    	if(fromdate>todate)
    	{
    	    bootbox.alert("From Date Should be Less than To Date");
    	    return false;
    	}
    	
        if(diffDays>30)
        {
        	bootbox.alert('Date Difference should not be more than 30');
        	return false;
        }
       }
	if(period == "Month")
    {
		if (frommonth == "" || frommonth==null) 
    	{
    		bootbox.alert("Please Select From Date");
    		return false;
    	}
    	
    	if (tomonth == "" || tomonth==null) 
    	{
    		bootbox.alert("Please Select To Date");
    		return false;
    	}
    	
    	if(frommonth>tomonth)
    	{
    	    bootbox.alert("From Month Should be Less than To Month");
    	    return false;
    	}
    	
       if(diffDays>365 || diffDays>366)
       {
     	   bootbox.alert('Month Difference should not be more than 12 months'); 
		   return false;
       }
    }
    if(period == "IP Wise")
    {
    	if (ipwisedate == "" || ipwisedate==null) 
    	{
    		bootbox.alert("Please Select Date");
    		return false;
    	}
    	
        /* if(diffDays>1)
        {
     	bootbox.alert('Date Difference should not be more than 1'); 
			return false;
        } */
    }
    $("#imageee1").show();
    $.ajax({
    	url :'./viewIndividualDTConsumptiondata',
		type :'POST',
		data :{
			circle : circle,
		  division : division,
		  subdiv   : subdiv,
		  town     : town,
		  	dt     : dt,
		  period   : period,
		  fromdate : fromdate,
		  todate   : todate,
		 frommonth : frommonth,
		  tomonth  : tomonth,
		ipwisedate :ipwisedate
		},
		dataType : 'json',
		success:function(response)
		{
			   $("#imageee1").hide();
			if(response.length>0){
			if (period == "Date")
			{
				$('#sample_3').dataTable().fnClearTable();
				$("#dtdatewise1").html('');

				if (response != null && response.length > 0) {
				var html = "";
				var html1 = "";
				var respns = response[0];
				var resp1 = response[1];
				if(respns.length!=0){
				for (var i = 0; i < respns.length; i++) {
					var resp = respns[i];
					var j = i + 1;
					
					html += "<tr>" 
							+ "<td>"+ j + "</td>"
							+ "<td>"+ ((resp[5] == null) ? "": (moment(resp[5]).format('YYYY-MM-DD '))) + " </td>"
							+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
							+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
							+ "<td>"+ ((resp[9] == null) ? "": resp[9]) + " </td>"
							+ "<td>"+ ((resp[11] == null) ? "": resp[11]) + " </td>"
							+ "<td>"+ ((resp[12] == null) ? "": resp[12]) + " </td>"
							+ "<td>"+ ((resp[13] == null) ? "": resp[13]) + " </td>"
							+ "<td>"+ ((resp[14] == null) ? "": resp[14]) + " </td>"
							+ "<td>"+ ((resp[15] == null) ? "": resp[15]) + " </td>"
							+ "<td>"+ ((resp[16] == null) ? "": resp[16]) + " </td>"
							+ "<td>"+ ((resp[10] == null) ? "": resp[10]) + " </td>"
							/* + "<td>"+ ((resp[17] == null) ? "": resp[17]) + " </td>" */
							/* + "<td>"+ ((resp[11] == null) ? "": resp[11]) + " </td>" */
							+ "</tr>";
				}
				}
				else {
					bootbox.alert("No Relative Data Found.");
				}
				$('#sample_3').dataTable().fnClearTable();
				$("#dtdatewise1").html(html);
				loadSearchAndFilter('sample_3');
				$("#noOfMtrsId").val(resp1.length);
				 $("#dtMeterNosId").html(""); 
				
				var html="";
				for (var i = 0; i < resp1.length; i++) {
					var resp2 = resp1[i];
					html+="<a style='cursor: pointer' onclick=mtrDetails('"+resp2[1]+"')>"+resp2[1]+"</a>"+"&nbsp;";
					$("#dtCapacityId").val(resp2[2]);
					}
				 $("#dtMeterNosId").html(html); 
				 
			} else {
				bootbox.alert("No Relative Data Found.");
			}
			}
			}
			if (period == "Month")
			{
				$('#sample_4').dataTable().fnClearTable();
				$("#dtmonthwise1").html('');

				if (response != null && response.length > 0) {
					var html = "";
					var html1 = "";
					var respns = response[0];
					var resp1 = response[1];
					if(respns.length!=0){
				for (var i = 0; i < respns.length; i++) {
					var resp = respns[i];
					var j = i + 1;
					html += "<tr>" 
							+ "<td>"+ j + "</td>"
							+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"
							+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
							+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
							+ "<td>"+ ((resp[9] == null) ? "": resp[9]) + " </td>"
							+ "<td>"+ ((resp[11] == null) ? "": resp[11]) + " </td>"
							+ "<td>"+ ((resp[12] == null) ? "": resp[12]) + " </td>"
							+ "<td>"+ ((resp[13] == null) ? "": resp[13]) + " </td>"
							+ "<td>"+ ((resp[14] == null) ? "": resp[14]) + " </td>"
							+ "<td>"+ ((resp[15] == null) ? "": resp[15]) + " </td>"
							+ "<td>"+ ((resp[16] == null) ? "": resp[16]) + " </td>"
							+ "<td>"+ ((resp[10] == null) ? "": resp[10]) + " </td>"
							/* + "<td>"+ ((resp[17] == null) ? "": resp[17]) + " </td>" */
							+ "<td>"+ ((resp[19] == null) ? "": resp[19]) + " </td>"
							+ "<td>"+ ((resp[20] == null) ? "": (moment(resp[20]).format('YYYY-MM-DD HH:mm:ss'))) + " </td>"
							
							+ "</tr>";
				}
					}
				else {
					bootbox.alert("No Relative Data Found.");
				}
				$('#sample_4').dataTable().fnClearTable();
				$("#dtmonthwise1").html(html);
				loadSearchAndFilter('sample_4');
				$("#noOfMtrsId").val(resp1.length);
				
				var html="";
				for (var i = 0; i < resp1.length; i++) {
					var resp2 = resp1[i];
					html+="<a style='cursor: pointer' onclick=mtrDetails('"+resp2[1]+"')>"+resp2[1]+"</a>"+"&nbsp;";
					$("#dtCapacityId").val(resp2[2]);
					}
				 $("#dtMeterNosId").html(html);
			} else {
				bootbox.alert("No Relative Data Found.");
			}
			}
			
			if (period == "IP Wise")
			{
				$('#sample_5').dataTable().fnClearTable();
				$("#dtipwise1").html('');


				if (response != null && response.length > 0) {
					var html = "";
					var html1 = "";
					var respns = response[0];
					var resp1 = response[1];
					if(respns.length!=0){
				for (var i = 0; i < respns.length; i++) {
					var resp = respns[i];
					var j = i + 1;
					
					html += "<tr>" 
							+ "<td>"+ j + "</td>"
							+ "<td>"+ ((resp[5] == null) ? "": (moment(resp[5]).format('YYYY-MM-DD HH:mm:ss'))) + " </td>"
							+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
							+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
							+ "<td>"+ ((resp[9] == null) ? "": resp[9]) + " </td>"
							+ "<td>"+ ((resp[11] == null) ? "": resp[11]) + " </td>"
							+ "<td>"+ ((resp[12] == null) ? "": resp[12]) + " </td>"
							+ "<td>"+ ((resp[13] == null) ? "": resp[13]) + " </td>"
							+ "<td>"+ ((resp[14] == null) ? "": resp[14]) + " </td>"
							+ "<td>"+ ((resp[15] == null) ? "": resp[15]) + " </td>"
							+ "<td>"+ ((resp[16] == null) ? "": resp[16]) + " </td>"
							+ "<td>"+ ((resp[10] == null) ? "": resp[10]) + " </td>"
							/* + "<td>"+ ((resp[17] == null) ? "": resp[17]) + " </td>" */
							/* + "<td>"+ ((resp[11] == null) ? "": resp[11]) + " </td>" */
							+ "</tr>";
				}
					}
					else {
						bootbox.alert("No Relative Data Found.");
					}
				$('#sample_5').dataTable().fnClearTable();
				$("#dtipwise1").html(html);
				loadSearchAndFilter('sample_5');
				$("#noOfMtrsId").val(resp1.length);
				
				var html="";
				for (var i = 0; i < resp1.length; i++) {
					var resp2 = resp1[i];
					html+="<a style='cursor: pointer' onclick=mtrDetails('"+resp2[1]+"')>"+resp2[1]+"</a>"+"&nbsp;";
					$("#dtCapacityId").val(resp2[2]);
					}
				 $("#dtMeterNosId").html(html);
              
			} else {
				bootbox.alert("No Relative Data Found.");
			}
			}
		},
		complete : function() {
			 $("#imageee1").hide();
			if (period == "Date") {
				loadSearchAndFilter('sample_3');
			}
			if (period == "Month") {
				loadSearchAndFilter('sample_4');
				}
			if (period == "IP Wise") {
				loadSearchAndFilter('sample_5');
				}
		}
    });
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
               // html+="<select id='circle' name='circle' onchange='showTownNameandCode(this.value)' class='form-control input-medium' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
                html+="<select id='LFcircle' name='LFcircle' class='form-control' onchange='showTownNameandCode(this.value)' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
               
                for( var i=0;i<response.length;i++)
                {
                    html+="<option value='"+response[i]+"'>"+response[i]+"</option>";
                }
				html+="</select><span></span>";
                $("#LFcircle").html(html);
                $('#LFcircle').select2();
            }
        });
}


function showTownNameandCode(circle){
		var zone =  $('#LFzone').val(); 
		
		   $.ajax({
		      	url:'./getTownNameandCodebyCircle',
		      	type:'GET',
		      	dataType:'json',
		      	asynch:false,
		      	cache:false,
		      	data : {
		  			zone : zone,
		  			circle:circle
		  		},
		  		success : function(response1) {
		  			
	                var html = '';
	                html += "<select id='LFtown' name='LFtown'  class='form-control  input-medium'    onchange='showResultsbasedOntownCode(this.value)' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
	               
	                for (var i = 0; i < response1.length; i++) {
	                    //var response=response1[i];
	                    
	                    html += "<option value='"+response1[i][0]+"'>"
	                            +response1[i][0] +"-"  +response1[i][1] + "</option>";
	                               }
	                html += "</select><span></span>";
	                $("#LFtownTd").html(html);
	                $('#LFtown').select2();
	                
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
						<i class="fa fa-edit"></i>DT Consumption
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>
				<div class="portlet-body">
				
				
		<jsp:include page="locationFilter.jsp"/> 
		
					
	
				
				
		<%-- 		<div class="row" style="margin-left: -1px;">

									<div class="col-md-3">
									<label class='control-label'>Circle:</label>
										<div id="circleTd" class="form-group">
										
											<select class="form-control select2me" id="circle1"
												name="circle1" onchange="showDivision1(this.value);">
												<option value="">Select</option>
												<option value="%">ALL</option>
												<c:forEach items="${circlelist}" var="elements">
													<option value="${elements}">${elements}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-3">
									<label class='control-label'>Division:</label>
										<div id="division1Td" class="form-group">
										
		
											<select class="form-control select2me" id="division1"
												name="division1" onchange="showSubdivByDiv1(this.value);">
												<option value="">Select</option>
												<option value="%">ALL</option>
											</select>
										</div>
									</div>

									<div class="col-md-3">
									<label class='control-label'>Sub-Division:</label>
										<div id="subdiv1Td" class="form-group">
										
											<select class="form-control select2me" id="sdoCode1"
												name="sdoCode1" onchange="showTownBySubdiv(this.value);">
												<option value="">Select</option>
												<option value="%">ALL</option>
											</select>
										</div>
									</div>

									<div class="col-md-3">
									<label class='control-label'>Town:</label>
										<div id="town1Td" class="form-group">
										
											<select class="form-control select2me" id="town1"
												name="town1" onchange="getDTBaseOnTowns(this.value);">
												<option value="">Select</option>
												<!-- <option value="%">ALL</option> -->
											</select>
										</div>
									</div>
								</div> --%>
					<!--BEGIN TABS-->
					
					
					<div class="tabbable tabbable-custom">
					
										
				<div class="col-md-3">
					<strong>Feeder:</strong>
						<div id="feederDivId" class="form-group"><select
							class="form-control select2me input-medium" id="feederTpId" 
							name="feederTpId">
							<option value="">Select Feeder</option>
							<!-- <option value="%">ALL</option> -->
						</select></div>
				</div> 	
						<ul class="nav nav-tabs">
							<li class="active" id="tab1"><a href="#tab_1_1" data-toggle="tab" onclick="tabShow(1)">DT
									Consumption</a></li>
							<li id="tab2"><a href="#tab_1_2" data-toggle="tab"  hidden="true" onclick="tabShow(2)" >Individual DT
									Consumption</a></li>
						</ul>
						<div class="tab-content" >
						<div id="tabOneDivId" >
							 <div  class="tab-pane active" id="tab_1_1" >
								
								<div class="row" style="margin-left: -1px;" >
								
									

									<div class="col-md-3">
									<label class='control-label'>Period:</label>
										<div id="periodTd" class="form-group">
											<select class="form-control select2me"
												onchange='showTable(this.value)' id="periodtype"
												name="periodtype">
												<option value="">Select</option>
												<option value="Date">Date</option>
												<option value="Month">Month</option>
											</select>
										</div>
									</div>
									
									<div id=fromtoid>
										<div class="col-md-3">
										<label class='control-label'>Select Month:</label>
											<div class="input-group ">
											<input type="text"
													class="form-control from" id="month" name="month"
													required="required" placeholder="Select MonthYear">
												
												<span class="input-group-btn">
													<button class="btn default" type="button">
														<i class="fa fa-calendar"></i>
													</button>
												</span>
											</div>
										</div>
									</div>

									<div id=selectdateid>
										<div class="col-md-3">
										<label class='control-label'>Select Date:</label>
											<div class="form-group">
												<div class="input-group date date-picker"
													data-date-format="yyyy-mm-dd" data-date-end-date="-1d"
													data-date-viewmode="years" id="fromDate">
													<input type="text" class="form-control" name="SelectDateId"
														id="SelectDateId" placeholder="Select Date"> <span
														class="input-group-btn">
														<button class="btn default" type="button" id="">
															<i class="fa fa-calendar"></i>
														</button>
													</span>
												</div>
											</div>
										</div>
									</div>

								</div>

								<button type="button" id="dtLoadingData"
									style="margin-left: 500px; margin-top: 10px;"
									onclick="return viewDTConsumptiondata()"
									name="viewDTConsumptiondata" class="btn yellow">
									<b>Generate</b>
								</button>

								<div id="imageee" hidden="true" style="text-align: center;">
									<h3 id="loadingText">Loading..... Please wait.</h3>
									<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
										style="width: 4%; height: 4%;">
								</div>
							</div>


							<div class="tabbable tabbable-custom"
								id="showDatewiseDtTableView" hidden="true">
								<div class="table-toolbar">
									<div class="btn-group pull-right" style="margin-top: 15px;">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">
											
											<li><a href="#" id=""
							onclick="exportpdf('sample_1','DtConsumption')">Export to PDF</a></li>
											
											<li><a href="#" id="excelExport"
												onclick="tableToXlxs('sample_1', 'DTConsumptiondatewiseReport')">Export
													to Excel</a></li>
										</ul>
									</div>
								</div>
								<table class="table table-striped table-hover table-bordered"
									id="sample_1">
									<thead>
										<tr>
											<th>SL.NO</th>
											<th>TOWN</th>
											<th>TOWN CODE</th>
											<th>FEEDER</th>
											<th>FEEDER CODE</th>
											<th>DT NAME</th>
											<th>DT CODE</th>
											<th>NO.OF METERS CONNECTED</th>
											<th>DT CAPACITY(KVA)</th>
											<th>DATE</th>
											<th>KWH</th>
											<th>KVAH</th>
											<th>KVA</th>
											<th>IR</th>
											<th>IY</th>
											<th>IB</th>
											<th>VR</th>
											<th>VY</th>
											<th>VB</th>
											<th>PF</th>
											<!-- <th>FREQUENCY</th> -->
										</tr>
									</thead>
									<tbody id="dtdatewise">

									</tbody>
								</table>
							</div>

							<div class="tabbable tabbable-custom"
								id="showMonthwiseDtTableView" hidden="true">
								<div class="table-toolbar">
									<div class="btn-group pull-right" style="margin-top: 15px;">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">
										
										
											<li><a href="#" id=""
							onclick="exportpdf('sample_2','DtConsumption')">Export to PDF</a></li>
											
											<li><a href="#" id="excelExport"
												onclick="tableToXlxs('sample_2', 'DTConsumptionmonthwiseReport')">Export
													to Excel</a></li>
										</ul>
									</div>
								</div>
								<table class="table table-striped table-hover table-bordered"
									id="sample_2">
									<thead>
										<tr>
											<th>SL.NO</th>
											<th>TOWN</th>
											<th>TOWN CODE</th>
											<th>FEEDER</th>
											<th>FEEDER CODE</th>
											<th>DT NAME</th>
											<th>DT CODE</th>
											<th>NO.OF METERS CONNECTED</th>
											<th>DT CAPACITY(KVA)</th>
											<th>MONTH</th>
											<th>KWH</th>
											<th>KVAH</th>
											<th>KVA</th>
											<th>IR</th>
											<th>IY</th>
											<th>IB</th>
											<th>VR</th>
											<th>VY</th>
											<th>VB</th>
											<th>PF</th>
											<!-- <th>FREQUENCY</th> -->
											 <th>KVA(MD)</th>
											<th>MD DATE</th> 
										</tr>
									</thead>
									<tbody id="dtmonthwise">

									</tbody>
								</table>
							</div>
							</div>
							<!--  </div> -->

							<!-- New Tab DATE WISE -->
						<div id="tabTwoDivId" hidden="true">
							<div class="tab-pane " id="tab_1_2" >
								<!-- <div id='tab2id'>  -->
								
								<div class="row" style="margin-left: -1px;">
									<div class="col-md-3">
										<label class='control-label'>Dt Code:</label>
										<div id="dtTd" class="form-group">
											<select class="form-control select2me" id="dt1" name="dt1" onclick="clearFeilds()">
												<option value="">Select</option>
												<%-- <option value="%">ALL</option>
												<c:forEach items="${dtlist}" var="elements">
													<option value="${elements}">${elements}</option>
												</c:forEach> --%>
											</select>
										</div>
									</div>

									<div class="col-md-3">
										<div id="periodTd" class="form-group">
										<label class='control-label'>Period:</label>
											<select class="form-control select2me"
												onchange='showTable1(this.value)' id="periodtype1"
												name="periodtype1">
												<option value="">Select</option>
												<option value="Date">Date</option>
												<option value="Month">Month</option>
												<option value="IP Wise">IP Wise</option>
											</select>
										</div>
									</div>

									<div id=dateselectid>
										<div class="col-md-3">
											<label class='control-label'>From Date:</label>
											<div class="form-group">
												<div class="input-group date date-picker"
													data-date-format="yyyy-mm-dd" data-date-end-date="-1d"
													data-date-viewmode="years" id="fromDate">
													<input type="text" class="form-control " name="FromDateId1"
														id="FromDateId1" placeholder="Select From Date">
													<span class="input-group-btn">
														<button class="btn default" type="button" id="">
															<i class="fa fa-calendar"></i>
														</button>
													</span>
												</div>
											</div>
										</div>

										<div class="col-md-3">
											<label class='control-label'>To Date:</label>
											<div class="form-group">
												<div class="input-group date date-picker"
													data-date-format="yyyy-mm-dd" data-date-end-date="-1d"
													data-date-viewmode="years" id="toDate">
													<input type="text" autocomplete="off" class="form-control"
														name="ToDateId1" id="ToDateId1"
														placeholder="Select To Date"> <span
														class="input-group-btn">
														<button class="btn default" type="button" id="">
															<i class="fa fa-calendar"></i>
														</button>
													</span>
												</div>
											</div>
										</div>
									</div>

									<div id=monthselectid>
										<div class="col-md-3">
											<div class="input-group ">
												<label class='control-label'>From Month:</label>
												<input type="text"
													class="form-control from" id="FromMonthId1"
													name="FromMonthId1" required="required"
													placeholder="Select MonthYear"><span
													class="input-group-btn">
													<button class="btn default" type="button"
														style="margin-bottom: -17px;">
														<i class="fa fa-calendar"></i>
													</button>
												</span>
											</div>
										</div>

										<div class="col-md-3">
											<div class="input-group ">
												<label class='control-label'>To Month:</label>
												<input type="text"
													class="form-control from" id="ToMonthId1" name="ToMonthId1"
													required="required" placeholder="Select MonthYear"><span
													class="input-group-btn">
													<button class="btn default" type="button"
														style="margin-bottom: -17px;">
														<i class="fa fa-calendar"></i>
													</button>
												</span>
											</div>
										</div>
									</div>

									<div id=ipwisedateid>
										<div class="col-md-3">
											<label class='control-label'>Select Date:</label>
											<div class="form-group">
												<div class="input-group date date-picker"
													data-date-format="yyyy-mm-dd" data-date-end-date="-1d"
													data-date-viewmode="years" id="IpDate">
													<input type="text" class="form-control " name="IpDateId1"
														id="IpDateId1" placeholder="Select From Date"> <span
														class="input-group-btn">
														<button class="btn default" type="button" id="">
															<i class="fa fa-calendar"></i>
														</button>
													</span>
												</div>
											</div>
										</div>
									</div>
								</div>

								<button type="button" id="dtLoadingData"
									style="margin-left: 500px; margin-top: 15px;"
									onclick="return viewIndividualDTConsumptiondata()"
									name="viewDTConsumptiondata" class="btn yellow">
									<b>Generate</b>
								</button>
								<div id="imageee1" hidden="true" style="text-align: center;">
									<h3 id="loadingText">Loading..... Please wait.</h3>
									<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
										style="width: 4%; height: 4%;">
								</div>
<br/>
<br/>
					<table style="color:#000000;  padding-bottom: 25px;  width:100%; " class="table table-bordered">
                 <tr>
               <td style="color: #000000; width:50px; background-color:#DCDCDC; "><b>No Of Meter Connected :</b>
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control"  name="" autocomplete="off" id="noOfMtrsId" readonly type="text" style="width: 20%;" >
               
               </td>
               </tr>
               
                <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC;"><b>Dt Capacity :</b>
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control"  name="" autocomplete="off" id="dtCapacityId" readonly type="text" style="width: 20%;" >
               </td>
               </tr>
               
               <tr>
               <td style="color: #000000;  background-color:#DCDCDC;"><b>Meter No's :</b>
               </td>
               <!-- <td style="background-color:#DCDCDC;" id="dtMeterstdId"> -->
               <td style="background-color:#DCDCDC;" id="dtMeterNosId">
               <!-- <input class="form-control"  name="" autocomplete="off" id="dtMeterNosId"   style="width: 40%;" > -->
                
            <!--  <select class="form-control select2me input-small"  id="dtMeterNosId" name="dtMeterNosId">
												<option value="">Select</option>
												<option value="Date">Date</option>
												<option value="Month">Month</option>
												<option value="IP Wise">IP Wise</option>
												</select> -->
					</td>
               </tr>						
              </table>
								<!-- </div>	 -->

								


								<div class="tabbable tabbable-custom"
									id="showDatewiseDtTableView1" hidden="true">
									<div class="table-toolbar">
										<div class="btn-group pull-right" style="margin-top: 15px;">
											<button class="btn dropdown-toggle" data-toggle="dropdown">
												Tools <i class="fa fa-angle-down"></i>
											</button>
											<ul class="dropdown-menu pull-right">
												<li><a href="#" id="excelExport"
													onclick="tableToXlxs('sample_3', 'DTConsumptiondatewiseReport')">Export
														to Excel</a></li>
											</ul>
										</div>
									</div>
									<table class="table table-striped table-hover table-bordered"
										id="sample_3">
										<thead>
											<tr>
												<th>SL.NO</th>
												<th>DATE</th>
												<th>KWH</th>
												<th>KVAH</th>
												<th>KVA</th>
												<th>IR</th>
												<th>IY</th>
												<th>IB</th>
												<th>VR</th>
												<th>VY</th>
												<th>VB</th>
												<th>PF</th>
												<!-- <th>FREQUENCY</th> -->
											</tr>
										</thead>
										<tbody id="dtdatewise1">

										</tbody>
									</table>
								</div>

								<div class="tabbable tabbable-custom"
									id="showMonthwiseDtTableView1" hidden="true">
									<div class="table-toolbar">
										<div class="btn-group pull-right" style="margin-top: 15px;">
											<button class="btn dropdown-toggle" data-toggle="dropdown">
												Tools <i class="fa fa-angle-down"></i>
											</button>
											<ul class="dropdown-menu pull-right">
											
											<li><a href="#" id=""
							onclick="exportpdf2('sample_4','DTConsumptionmonthwiseReport')">Export to PDF</a></li>
												<li><a href="#" id="excelExport"
													onclick="tableToXlxs('sample_4', 'DTConsumptionmonthwiseReport')">Export
														to Excel</a></li>
											</ul>
										</div>
									</div>
									<table class="table table-striped table-hover table-bordered"
										id="sample_4">
										<thead>
											<tr>
												<th>SL.NO</th>
												<th>MONTH</th>
												<th>KWH</th>
												<th>KVAH</th>
												<th>KVA</th>
												<th>IR</th>
												<th>IY</th>
												<th>IB</th>
												<th>VR</th>
												<th>VY</th>
												<th>VB</th>
												<th>PF</th>
												<!-- <th>FREQUENCY</th> -->
												 <th>KVA(MD)</th>
												<th>MD TIME</th>
											</tr>
										</thead>
										<tbody id="dtmonthwise1">

										</tbody>
									</table>
								</div>

								<div class="tabbable tabbable-custom"
									id="showIpwiseDtTableView1" hidden="true">
									<div class="table-toolbar">
										<div class="btn-group pull-right" style="margin-top: 15px;">
											<button class="btn dropdown-toggle" data-toggle="dropdown">
												Tools <i class="fa fa-angle-down"></i>
											</button>
											<ul class="dropdown-menu pull-right">
												<li><a href="#" id="excelExport"
													onclick="tableToXlxs('sample_5', 'DTConsumptionipwiseReport')">Export
														to Excel</a></li>
											</ul>
										</div>
									</div>
									<table class="table table-striped table-hover table-bordered"
										id="sample_5">
										<thead>
											<tr>
												<th>SL.NO</th>
												<th>IP</th>
												<th>KWH</th>
												<th>KVAH</th>
												<th>KVA</th>
												<th>IR</th>
												<th>IY</th>
												<th>IB</th>
												<th>VR</th>
												<th>VY</th>
												<th>VB</th>
												<th>PF</th>
												<!-- <th>FREQUENCY</th> -->
											</tr>
										</thead>
										<tbody id="dtipwise1">

										</tbody>
									</table>
								</div>
							</div>
							<!--  end new tab-->
						</div>
						</div>
					</div>
					
					
				</div>
			</div>
		</div>
	</div>
</div>


<script>

var zone="%";
	
	function showDivision(circle) {
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
                    html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control select2me' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
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
    //var zone = $('#zone').val();
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
                     html += "<select id='sdoCode' name='sdoCode' onchange='showTownBySubdiv(this.value)' class='form-control select2me' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
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
 
function showTownBySubdiv(subdiv) {
    var circle = $('#circle').val();
    var division = $('#division').val();
    var subdiv = $('#sdoCode').val();
    $.ajax({
        url : './getTownsBaseOnSubdiv',
        type : 'GET',
        dataType : 'json',
        asynch : false,
        cache : false,
        data : {
            circle : circle,
            division : division,
           subdivision :subdiv
        },
                success : function(response1) {
                 var html = '';
                    html += "<select id='town' name='town'  class='form-control select2me' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
                    for (var i = 0; i < response1.length; i++) {
                        //var response=response1[i];
                        html += "<option value='"+response1[i]+"'>"
                                + response1[i] + "</option>";
                    }
                    html += "</select><span></span>";
                    $("#townTd").html(html);
                    $('#town').select2(); 
                }
            });
}


function showDivision1(circle) {
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
                    html += "<select id='division1' name='division1' onchange='showSubdivByDiv1(this.value)' class='form-control select2me' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
                    for (var i = 0; i < response.length; i++) {
                        html += "<option value='"+response[i]+"'>"
                                + response[i] + "</option>";
                    }
                    html += "</select><span></span>";
                    $("#division1Td").html(html);
                    $('#division1').select2();
                }
            });
}


function showSubdivByDiv1(division) {
    //var zone = $('#zone').val();
     var circle = $('#circle1').val();
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
                     html += "<select id='sdoCode1' name='sdoCode1' onchange='showTownBySubdiv1(this.value)' class='form-control select2me' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
                     for (var i = 0; i < response1.length; i++) {
                         //var response=response1[i];
                         html += "<option value='"+response1[i]+"'>"
                                 + response1[i] + "</option>";
                     }
                     html += "</select><span></span>";
                     $("#subdiv1Td").html(html);
                     $('#sdoCode1').select2();
                 }
             });
 }



 
function showTownBySubdiv1(subdiv) {
    var circle = $('#circle1').val();
    var division = $('#division1').val();
    var subdiv = $('#sdoCode1').val();
    $.ajax({
        url : './getTownsBaseOnSubdiv',
        type : 'GET',
        dataType : 'json',
        asynch : false,
        cache : false,
        data : {
            circle : circle,
            division : division,
           subdivision :subdiv
        },
                success : function(response1) {
                 var html = '';
                    html += "<select id='town1' name='town1'   onchange='showResultsbasedOntownCode(this.value);' class='form-control select2me' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
                    for (var i = 0; i < response1.length; i++) {
                        //var response=response1[i];
                        html += "<option value='"+response1[0][0]+"'>"
                                + response1[i] + "</option>";
                    }
                    html += "</select><span></span>";
                    $("#town1Td").html(html);
                    $('#town1').select2(); 
                }
            });
}

function showTable(value)
{
	if (value == "Date") {
		$('#fromtoid').hide();
		$('#selectdateid').show();
		$('#sample_1').dataTable().fnClearTable();
		loadSearchAndFilter('sample_1');
		$('#showMonthwiseDtTableView').hide();
		$('#showDatewiseDtTableView').show();
	}
	if (value == "Month") {
		$('#fromtoid').show();
		$('#selectdateid').hide();
		$('#sample_2').dataTable().fnClearTable();
		loadSearchAndFilter('sample_2');
		$('#showDatewiseDtTableView').hide();	
		$('#showMonthwiseDtTableView').show();
		}
}

function showTable1(value)
{
	if (value == "Date") {
		$('#dateselectid').show();
		$('#monthselectid').hide();
		$('#ipwisedateid').hide();
		$('#sample_1').dataTable().fnClearTable();
		loadSearchAndFilter('sample_3');
		$('#showMonthwiseDtTableView1').hide();
		$('#showIpwiseDtTableView1').hide();
		$('#showDatewiseDtTableView1').show();
	}
	if (value == "Month") {
		$('#monthselectid').show();
		$('#dateselectid').hide();
		$('#ipwisedateid').hide();
		$('#sample_2').dataTable().fnClearTable();
		loadSearchAndFilter('sample_4');
		$('#showDatewiseDtTableView1').hide();
		$('#showIpwiseDtTableView1').hide();
		$('#showMonthwiseDtTableView1').show();
		}
	if (value == "IP Wise") {
		$('#ipwisedateid').show();
		$('#dateselectid').hide();
		$('#monthselectid').hide();
		$('#sample_3').dataTable().fnClearTable();
		loadSearchAndFilter('sample_5');
		$('#showDatewiseDtTableView1').hide();	
		$('#showMonthwiseDtTableView1').hide();
		$('#showIpwiseDtTableView1').show();
		}
}


function getIndividualdtConsum(dtcode)
{
	//alert(dtcode);
 var tabInd=1;
 var n=2;
	$("#tabOneDivId").hide();
	$("#tabTwoDivId").show();
	
	//$('#t2 a[href="tab_1_2"]').trigger('click');

	$("#tab" + tabInd).removeClass('active');
	$("#tab" + n).addClass('active');
	$("#tab" + n).click();
	
	$("#dt1").val(dtcode).trigger("change");

	$("#noOfMtrsId").val("");
	$("#dtCapacityId").val("");
	$("#dtMeterNosId").html("");
	
	

	$.ajax({
		 url :'./getDTDataBasedOnDtcode',
		type :'POST',
		data :{
			dtcode : dtcode,
		},
		dataType : 'json',
		success:function(response)
		{
			var resp1=response;
			$("#noOfMtrsId").val(resp1.length);
			
			var html="";
			for (var i = 0; i < resp1.length; i++) {
				var resp2 = resp1[i];
				html+="<a style='cursor: pointer' onclick=mtrDetails('"+resp2[1]+"')>"+resp2[1]+"</a>"+"&nbsp;";
				$("#dtCapacityId").val(resp2[2]);
			}
			 $("#dtMeterNosId").html(html);
        
			 
		}
	});
}

var date=new Date();
var year=date.getFullYear();
var month=date.getMonth();


$('.from').datepicker
({
    format: "yyyymm",
    minViewMode: 1,
    autoclose: true,
    startDate :new Date(new Date().getFullYear(),'-55'),
    endDate: new Date(year, month-1, '31')
});



function showResultsbasedOntownCode(townCode) {
	//alert(townCode);
	var region = $("#LFzone").val();
	var circle = $("#LFcircle").val();
	 var towncode = $('#LFtown').val();
$('#feederTpId').val('').trigger('change');
//var town = $('#townDT').val();
$('#feederTpId').empty();
$('#feederTpId').find('option').remove();
$('#feederTpId').append($('<option>', {
	value : "",
	text : "Select Feeder"
}));
/* $('#feederTpId').append($('<option>', {
	value : "%",
	text : "ALL"
}));
*/
$.ajax({
	url : './getFeederBySelection',
	type:'GET',
	dataType : 'json',
	asynch : false,
	cache : false,
	data : {
		townCode : townCode,
		circle :circle,
		region : region
	},
	success : function(response1) {
		var html = '';
		    html += "<select id='feederTpId' name='feederTpId' onchange='showResultsbasedtownCode(this.value);'  class='form-control input-medium'  type='text'><option value=''>Select Feeder</option>";
		   for (var i = 0; i < response1.length; i++) {
		       html += "<option value='"+response1[i][0]+"'>"
		               + response1[i][1] + "</option>";
		   }
		   html += "</select><span></span>";
		   $("#feederDivId").html(html);
		   $('#feederTpId').select2(); 

		/* for (var i = 0; i < response1.length; i++) {
			var resp = response1[i];
			$('#feederTpId').append($('<option>', {
				value : resp[0],
				text : resp[1]
			}));
		}
	   html += "</select><span></span>"; */
	 

	}
});
} 



function showResultsbasedtownCode(towncode) {
    var towncode = $('#LFtown').val();
    //alert(towncode);
    $.ajax({
    	
        url : './getDTBaseOnTowns',
        type : 'GET',
        dataType : 'json',
        asynch : false,
        cache : false,
        data : {
        	towncode : towncode,
        },
                success : function(response1) {
                 var html = '';
                    html += "<select id='dt1' name='dt1'  class='form-control select2me' type='text'  onclick='clearFeilds()'><option value='0'>Select</option>";
                    for (var i = 0; i < response1.length; i++) {
                        //var response=response1[i];
                        html += "<option value='"+response1[i]+"'>"
                                + response1[i] + "</option>";
                    }
                    html += "</select><span></span>";
                    $("#dtTd").html(html);
                    $('#dt1').select2(); 
                }
            });
}





</script>
<script>

 function tabShow(param){
if(param=='1'){
$("#tabTwoDivId").hide();

$("#tabOneDivId").show();
}
if(param=='2'){
		$("#tabOneDivId").hide();
		$("#tabTwoDivId").show();
		
		}
}

 function mtrDetails(mtrNo)
 {
 	mtrNum=mtrNo;
 	
 	/* window.location.href="./viewFeederMeterInfoMDAS?mtrno=" + mtrNo; */
 	  window.open("./viewFeederMeterInfoMDAS?mtrno="+ mtrNo); 
 }
  

 function clearFeilds(){
	 $("#noOfMtrsId").val("");
		$("#dtCapacityId").val("");
		$("#dtMeterNosId").html("");
	 }



 function exportpdf()
 {
    //alert("inside");
    var zone=$('#LFzone').val();
	 var circle=$('#LFcircle').val();
	var division=$('#division1').val();
	var subdiv=$('#sdoCode1').val();
	var town=$('#LFtown').val();
    var period=$('#periodtype').val();
    var seldate=$('#SelectDateId').val();
    var selmonth=$('#month').val();
    var frommonth=$('#FromMonthId1').val();
    var tomonth=$('#ToMonthId1').val();
    var dt=$('#dt1').val();

	    window.location.href = ("./dtConsumptionpdf?circle=" + circle + "&town="+ town + "&period=" + period + "&seldate="+seldate + "&selmonth="+selmonth + "&zone="+zone + "&frommonth="+frommonth+ "&tomonth="+tomonth+ "&dt="+dt );



		 }


 /* function exportpdf2()
 {
   alert(" inside second generate");
	 var circle=$('#LFcircle').val();
	var division=$('#division1').val();
	var subdiv=$('#sdoCode1').val();
	var town=$('#LFtown').val();
    var period=$('#periodtype').val();
    var seldate=$('#SelectDateId').val();
    var selmonth=$('#month').val();

	    window.location.href = ("./dtConsumptionpdf2?circle=" + circle + "&town="+ town + "&period=" + period + "&seldate="+seldate + "&selmonth="+selmonth );



		 } */

 
</script>

<style>
#s2id_estimationrule, #s2id_unestimationrule  {
width: 236px;
}

</style>