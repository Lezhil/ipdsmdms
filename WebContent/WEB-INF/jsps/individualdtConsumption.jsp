
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	$(".page-content").ready(function() {
		App.init();
		TableEditable.init();
		Tasks.initDashboardWidget();
		FormComponents.init();
		TableManaged.init();
		$('#dateselectid').show();
		$('#ipwisedateid').hide();
		$('#monthselectid').hide();
		$('#MDMSideBarContents,#mdmDashBoardId,#reportsId,#individualdtConsumption').addClass('start active ,selected');
    	$('#other-Reports').addClass('start active ,selected');
	});
	
	var zone="%"
		
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
</script>

<div class="page-content">
	<div class="portlet box blue">
	   <div class="portlet-title">
			<div class="caption">
				<i class="fa fa-Book"></i><strong>Individual DT Consumption</strong>
			</div>
		</div>
		
		<div class="portlet-body">
		<div class="row" style="margin-left: -1px; width: 100%;">
		
		     <div class="col-md-3">
					<strong>Circle:</strong><div id="circleTd" class="form-group">
						<select class="form-control select2me"
							id="circle" name="circle"
							onchange="showDivision(this.value);">
							<option value="">Select</option>
							<option value="%">ALL</option> 
							<c:forEach items="${circlelist}" var="elements">
								<option value="${elements}">${elements}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				
				<div class="col-md-3">
					<strong>Division:</strong><div id="divisionTd" class="form-group">
						 <select class="form-control select2me"
							id="division" name="division" onchange="showSubdivByDiv(this.value);">
							<option value="">Select</option>
							<option value="%">ALL</option>
						
						</select>
					</div>
				</div>
				
				<div class="col-md-3">
					<strong>Sub-Division:</strong><div id="subdivTd" class="form-group">
						 <select
							class="form-control select2me" id="sdoCode" name="sdoCode"  onchange="showTownBySubdiv(this.value);">
							<option value="">Select</option>
							<option value="%">ALL</option>
						
						</select>
					</div>
				</div>
				
				<div class="col-md-3">
			        <strong>Town:</strong>
							<div id="townTd" class="form-group"><select
								class="form-control select2me" id="town"
								name="town">
								<option value="">Select</option>
							<option value="%">ALL</option>
							</select></div>
				</div>
				
				<div class="row" style="margin-left: -1px;">
				
				<div class="col-md-3">
			        <strong>DT:</strong>
							<div id="dtTd" class="form-group"><select
								class="form-control select2me" id="dt"
								name="dt">
								<option value="">Select</option>
							<option value="%">ALL</option>
							</select></div>
				</div>
				
				 <div class="col-md-3">
			        <strong>Period:</strong>
							<div id="periodTd" class="form-group"><select
								class="form-control select2me" onchange='showTable(this.value)' id="periodtype"
								name="periodtype">
							<option value="">Select</option>
							<option value="Date">Date</option>
							<option value="Month">Month</option>
							<option value="IP Wise">IP Wise</option>
							</select></div>
				</div>
				
				<div id=dateselectid>
					<div class="col-md-3">
		            <strong>From Date:</strong>
                     <div class="form-group" >
                     <div class="input-group date date-picker"  data-date-format="yyyy-mm-dd"  data-date-end-date="-1d" data-date-viewmode="years" id="fromDate">
							<input type="text"  class="form-control " name="FromDateId" id="FromDateId" placeholder="Select From Date" >
							<span class="input-group-btn" >
							<button class="btn default" type="button" id=""><i class="fa fa-calendar"></i></button>
							</span>
					</div>
			        </div>
		           </div>
		
		       <div class="col-md-3">	
		               <strong>To Date:</strong>															
                       <div class="form-group"> 
                       <div class="input-group date date-picker"  data-date-format="yyyy-mm-dd" data-date-end-date="-1d" data-date-viewmode="years" id="toDate">
							<input type="text" autocomplete="off" class="form-control" name="ToDateId" id="ToDateId" placeholder="Select To Date">
							<span class="input-group-btn">
							<button class="btn default" type="button" id=""><i class="fa fa-calendar"></i></button>
							</span>
			           </div> 
			          </div>	
		      </div>
		   </div>
		   
		    <div id=monthselectid>
			          <div class="col-md-3">
			                    <div class="input-group ">
						        <strong>From Month:</strong><input
							       type="text" class="form-control from"  id="FromMonthId"
							       name="FromMonthId" required="required" placeholder="Select MonthYear"><span
							       class="input-group-btn">
							    <button class="btn default" type="button"
								style="margin-bottom: -17px;">
								<i class="fa fa-calendar"></i>
							</button>
						</span>
					</div></div>
					
			        <div class="col-md-3">
			                   <div class="input-group ">
						       <strong>To Month:</strong><input
							      type="text" class="form-control from"  id="ToMonthId"
							      name="ToMonthId" required="required" placeholder="Select MonthYear"><span
							      class="input-group-btn">
							<button class="btn default" type="button"
								style="margin-bottom: -17px;">
								<i class="fa fa-calendar"></i>
							</button>
						</span>
					</div></div>
			</div>
		   
		   	<div id=ipwisedateid>
					<div class="col-md-3">
		            <strong>Select Date:</strong>
                     <div class="form-group" >
                     <div class="input-group date date-picker"  data-date-format="yyyy-mm-dd"  data-date-end-date="-1d" data-date-viewmode="years" id="IpDate">
							<input type="text"  class="form-control " name="IpDateId" id="IpDateId" placeholder="Select From Date" >
							<span class="input-group-btn" >
							<button class="btn default" type="button" id=""><i class="fa fa-calendar"></i></button>
							</span>
					</div>
			        </div>
		           </div>
		     </div>
		      		
				</div>
				
				<div class="row" style="margin-left: -1px;">
				
			<div class="col-md-3">
			        <strong>Number of Meter Connected:</strong>
							<div id="dtTd" class="form-group"><span
								class="form-control" id="noofmtrs" 
								name="noofmtrs" type="text"></span>
							</div>
				</div>
														
	         <div class="col-md-3">
			           <strong>DT Capacity:</strong>
							<div id="periodTd" class="form-group"><span
								class="form-control"  id="dtCapacity"
								name="dtCapacity"></span>
							</div>
				</div>
				</div><br><br><br> 
				
				<!--BEGIN TABS-->
					<button type="button" id="dtLoadingData" style="margin-left: 500px; margin-top: -75px;"
			                onclick="return viewIndividualDTConsumdata()" name="viewDTConsumptiondata" class="btn green">
		                    <b>Generate</b>
		             </button>
		            
		             
		             <div class="tabbable tabbable-custom" id="showDatewiseDtTableView" hidden="true">
							<div class="table-toolbar">
									<div class="btn-group pull-right" style="margin-top: 15px;">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">
											<li><a href="#" id="excelExport"
												onclick="tableToExcel('sample_1', 'DTConsumptiondatewiseReport')">Export
													to Excel</a></li>
										</ul>
									</div>
								</div>
							<table class="table table-striped table-hover table-bordered"
								id="sample_1">
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
										<th>FREQUENCY</th>
									</tr>
								</thead>
								<tbody id="dtdatewise">

								</tbody>
							</table>
						</div>
						
						<div class="tabbable tabbable-custom" id="showMonthwiseDtTableView" hidden="true">
							<div class="table-toolbar">
									<div class="btn-group pull-right" style="margin-top: 15px;">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">
											<li><a href="#" id="excelExport"
												onclick="tableToExcel('sample_2', 'DTConsumptionmonthwiseReport')">Export
													to Excel</a></li>
										</ul>
									</div>
								</div>
							<table class="table table-striped table-hover table-bordered"
								id="sample_2">
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
										<th>FREQUENCY</th>
										<th>KVA(MD)</th>
										<th>MD TIME</th>
									</tr>
								</thead>
								<tbody id="dtmonthwise">

								</tbody>
							</table>
						</div>
						
						
						<div class="tabbable tabbable-custom" id="showIpwiseDtTableView" hidden="true">
							<div class="table-toolbar">
									<div class="btn-group pull-right" style="margin-top: 15px;">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">
											<li><a href="#" id="excelExport"
												onclick="tableToExcel('sample_2', 'DTConsumptionipwiseReport')">Export
													to Excel</a></li>
										</ul>
									</div>
								</div>
							<table class="table table-striped table-hover table-bordered"
								id="sample_3">
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
										<th>FREQUENCY</th>
									</tr>
								</thead>
								<tbody id="dtipwise">

								</tbody>
							</table>
						</div>
		             
		</div>
		</div>
	</div>
	</div>
	
<script>

function showTable(value)
{
	if (value == "Date") {
		$('#dateselectid').show();
		$('#monthselectid').hide();
		$('#ipwisedateid').hide();
		$('#sample_1').dataTable().fnClearTable();
		loadSearchAndFilter('sample_1');
		$('#showMonthwiseDtTableView').hide();
		$('#showIpwiseDtTableView').hide();
		$('#showDatewiseDtTableView').show();
	}
	if (value == "Month") {
		$('#monthselectid').show();
		$('#dateselectid').hide();
		$('#ipwisedateid').hide();
		$('#sample_2').dataTable().fnClearTable();
		loadSearchAndFilter('sample_2');
		$('#showDatewiseDtTableView').hide();
		$('#showIpwiseDtTableView').hide();
		$('#showMonthwiseDtTableView').show();
		}
	if (value == "IP Wise") {
		$('#ipwisedateid').show();
		$('#dateselectid').hide();
		$('#monthselectid').hide();
		$('#sample_3').dataTable().fnClearTable();
		loadSearchAndFilter('sample_3');
		$('#showDatewiseDtTableView').hide();	
		$('#showMonthwiseDtTableView').hide();
		$('#showIpwiseDtTableView').show();
		}
}


function viewIndividualDTConsumdata()
{
	var circle=$('#circle').val();
	var division=$('#division').val();
	var subdiv=$('#sdoCode').val();
	var town=$('#town').val();
	var dt=$('#dt').val();
    var period=$('#periodtype').val();
    var fromdate=$('#FromDateId').val();
    var todate=$('#ToDateId').val();
    var noofmtrs=$('#noofmtrs').val();
    var capacity=$('#dtCapacity').val();
    var ipwisedate=$('#IpDateId').val();
    var frommonth=$('#FromMonthId').val();
    var tomonth=$('#ToMonthId').val();
    
    if (circle == "" || circle==null) {
		bootbox.alert("Please Select circle");
		return false;
	}

	if (division == "" || division==null) {
		bootbox.alert("Please Select Division");
		return false;
	}

	if (subdiv == "" || subdiv==null) {
		bootbox.alert("Please Select Sub-Division");
		return false;
	}
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
   
    var fromYear=date1.getFullYear()
    var toYear=date2.getFullYear()
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
    	    bootbox.alert("From Date Should be Less than To Date")
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
    	    bootbox.alert("From Month Should be Less than To Month")
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
    	
        if(diffDays>1)
        {
     	bootbox.alert('Date Difference should not be more than 1'); 
			return false;
        }
    }
    
    $.ajax({
    	url :'./viewIndividualDTConsumdata',
		type :'POST',
		data :{
			circle : circle,
		  division : division,
		  subdiv   : subdiv,
		  town     : town,
		  period   : period,
		  fromdate : fromdate,
		  todate   : todate
		},
		dataType : 'json',
		success:function(response)
		{
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
							+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
							+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"
							+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"
							+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"
							+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"
							+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"
							+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
							+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
							+ "<td>"+ ((resp[8] == null) ? "": resp[8]) + " </td>"
							+ "<td>"+ ((resp[9] == null) ? "": resp[9]) + " </td>"
							+ "<td>"+ ((resp[10] == null) ? "": resp[10]) + " </td>"
							+ "<td>"+ ((resp[11] == null) ? "": resp[11]) + " </td>"
							"</tr>";
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
							+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
							+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"
							+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"
							+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"
							+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"
							+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"
							+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
							+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
							+ "<td>"+ ((resp[8] == null) ? "": resp[8]) + " </td>"
							+ "<td>"+ ((resp[9] == null) ? "": resp[9]) + " </td>"
							+ "<td>"+ ((resp[10] == null) ? "": resp[10]) + " </td>"
							+ "<td>"+ ((resp[11] == null) ? "": resp[11]) + " </td>"
							+ "<td>"+ ((resp[12] == null) ? "": resp[12]) + " </td>"
							+ "<td>"+ ((resp[13] == null) ? "": resp[13]) + " </td>"
							"</tr>";

				}
				$('#sample_2').dataTable().fnClearTable();
				$("#dtmonthwise").html(html);
				loadSearchAndFilter('sample_2');
			} else {
				bootbox.alert("No Relative Data Found.");
			}
			}
			
			if (period == "IP Wise")
			{
				$('#sample_3').dataTable().fnClearTable();
				$("#dtipwise").html('');

				if (response != null && response.length > 0) {
				var html = "";
				for (var i = 0; i < response.length; i++) {
					var resp = response[i];
					var j = i + 1;
					html += "<tr>" 
							+ "<td>"+ j + "</td>"
							+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
							+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"
							+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"
							+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"
							+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"
							+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"
							+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
							+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
							+ "<td>"+ ((resp[8] == null) ? "": resp[8]) + " </td>"
							+ "<td>"+ ((resp[9] == null) ? "": resp[9]) + " </td>"
							+ "<td>"+ ((resp[10] == null) ? "": resp[10]) + " </td>"
							+ "<td>"+ ((resp[11] == null) ? "": resp[11]) + " </td>"
							"</tr>";

				}
				$('#sample_3').dataTable().fnClearTable();
				$("#dtipwise").html(html);
				loadSearchAndFilter('sample_3');
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
			if (period == "IP Wise") {
				loadSearchAndFilter('sample_3');
				}
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

</script>