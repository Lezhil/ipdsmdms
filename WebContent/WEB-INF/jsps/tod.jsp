<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<script  type="text/javascript">
	$(".page-content").ready(function(){  
	
	   			App.init();
				TableEditable.init();
				FormComponents.init();
				loadSearchAndFilter('sample_1');
				$('#MDMSideBarContents,#DPId,#TodreportId').addClass('start active ,selected');
		    	 $("#MDASSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
		    	
	
	});
	</script>

<div class="page-content">
	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-edit"></i>Tod wise daily data aggregation report
			</div>
		</div>
		
		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="btn-group pull-right" style="margin-top: 1px;"></div>
			</div>
			<form class="horizontal-form">
				<div class="form-body">
					<div class="row">
					
						<div class="col-md-3" style="margin-left: -1%;">
							<div class="form-group">
								<label class="control-label ">Enter Meter No</label>
								<!-- <select class="form-control select2me"  name="mtrno" id="mtrno"  style="width: 206px;">
						 	   <option value="">Select</option> -->
								 <input class="form-control" type="text" name="mtrno" id="mtrno"  style="width: 206px;">
							</div>
						</div>

						<div class="col-md-3" style="margin-left: -3%;">
							<label class="control-label ">Select Date</label>
							<div class="input-group input-large date-picker input-daterange "
								data-date-format="yyyy-mm-dd">

								<input type="text" autocomplete="off" placeholder="From Date"
									class="form-control" data-date-format="yyyy-mm-dd"
									data-date-viewmode="years" name="fromDate" id="fromDate">
									
								<span class="input-group-addon">To</span> <input type="text"
									autocomplete="off" placeholder="To Date" class="form-control"
									data-date-format="yyyy-mm-dd" data-date-viewmode="years"
									name="toDate" id="toDate">
							</div>
						</div>


						<div class="col-md-1">
							<div class="form-group">
							<label class="control-label "></label>
								<div>
									<button type="button"
										style="margin-top: 8px; margin-left: 75px;"
										data-dismiss="modal" class="btn blue"
										onclick="return todwisereport();">SUBMIT</button>
								</div>
							</div>
						</div>
						<div class="btn-group pull-right" style="margin-left: 420px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Tod wise daily data aggregation report')">Export
										to Excel</a></li>
							</ul>
						</div>

			</div>
				</div>
			</form>
					<table class="table table-striped table-hover table-bordered" id="sample_1">
						<thead>
						
							<tr>
							<th>Sl No</th>
							    
							    <th>DATE</th>
								<th>METER NO</th>
								<th>TOD1 ST </th>
								<th>TOD1 ET</th>
								<th>KWH1</th>
								<th>KVAH1</th>
								<th>TOD2 ST</th>
								<th>TOD2 ET</th>
								<th>KWH2</th>
								<th>KVAH2</th>
								<th>TOD3 ST</th>
								<th>TOD3 ET</th>
								<th>KWH3</th>
								<th>KVAH3</th>
								<th>TOD4 ST</th>
								<th>TOD4 ET</th>
								<th>KWH4</th>
								<th>KVAH4</th>
								<th>TOD5 ST</th>
								<th>TOD5 ET</th>
								<th>KWH5</th>
								<th>KVAH5</th>
								<th>TOD6 ST</th>
								<th>TOD6 ET</th>
								<th>KWH6</th>
								<th>KVAH6</th>
								<th>TOD7 ST</th>
								<th>TOD7 ET</th>
								<th>KWH7</th>
								<th>KVAH7</th>
								<th>TOD8 ST</th>
								<th>TOD8 ET</th>
								<th>KWH8</th>
								<th>KVAH8</th>
								
							</tr>
						</thead>
						<tbody id="TbodyID">
							
						</tbody>
					</table>
					</div>
				</div>
			</div>
<script>

function todwisereport() {
	var fdate = $("#fromDate").val();
	var tdate = $("#toDate").val();
	var mtrno = $("#mtrno").val();
	          if(mtrno=="")
			 		 {
			 		 	bootbox.alert("Please Enter meter no.");
			 		 	return false;
			 		 }
      if(fdate=='' || fdate==null){
		bootbox.alert('Please Select from date');
		return false;
	} 
	else if(tdate=='' || tdate==null){
		bootbox.alert('Please Select todate.');
		return false;
	}
	else if(mtrno=='' || mtrno==null)
		{
		bootbox.alert('Please Select meter number.');
		return false;
		}
	else
     {
		
	    $.ajax({
		url : './gettodReport',
		type : 'POST',
		data : {
			fdate : fdate,
			tdate : tdate,
			mtrno : mtrno
		},
		dataType : 'json',
		asynch : false,
		cache : false,
		success : function(response) {
			if (response.length != 0) {
			var html = "";
			var select = new Array();
			for (var i = 0; i < response.length; i++) {
			var resp = response[i];
			var srno=i+1;
			if(resp[8]==null)
			{
			  resp[8]='';						
			}
			if(resp[9]==null)
			{
			  resp[9]='';						
			}
			if(resp[10]==null)
			{
			  resp[10]='';						
			}
			if(resp[11]==null)
			{
			  resp[11]='';						
			}
			if(resp[24]==null)
			{
			  resp[24]='';						
			}
			if(resp[25]==null)
			{
			  resp[25]='';						
			}
			if(resp[26]==null)
			{
			  resp[26]='';						
			}

			if(resp[27]==null)
			{
			  resp[27]='';						
			}

			if(resp[28]==null)
			{
			  resp[28]='';						
			}

			if(resp[29]==null)
			{
			  resp[29]='';						
			}
			if(resp[30]==null)
			{
			  resp[30]='';						
			}
			if(resp[31]==null)
			{
			  resp[31]='';						
			}
			if(resp[32]==null)
			{
			  resp[32]='';						
			}
			if(resp[33]==null)
			{
			  resp[33]='';						
			}
			if(resp[34]==null)
			{
			  resp[34]='';						
			}
			
			html+="<tr><td>"+srno+"</td>"
			+"<td>"+resp[1]+"</td>" 
			+"<td>"+resp[2]+"</td>" 
			+"<td>"+resp[3]+"</td>" 
			+"<td>"+resp[4]+"</td>" 
			+"<td>"+resp[5]+"</td>" 
			+"<td>"+resp[6]+"</td>" 
			+"<td>"+resp[7]+"</td>" 
			+"<td>"+resp[8]+"</td>" 
			+"<td>"+resp[9]+"</td>"
			+"<td>"+resp[10]+"</td>" 
			+"<td>"+resp[11]+"</td>" 
			+"<td>"+resp[12]+"</td>" 
			+"<td>"+resp[13]+"</td>"  
			+"<td>"+resp[14]+"</td>" 
			+"<td>"+resp[15]+"</td>" 
			+"<td>"+resp[16]+"</td>" 
			+"<td>"+resp[17]+"</td>" 
			+"<td>"+resp[18]+"</td>" 
		    +"<td>"+resp[19]+"</td>" 
		    +"<td>"+resp[20]+"</td>" 
		    +"<td>"+resp[21]+"</td>" 
		    +"<td>"+resp[22]+"</td>" 
		    +"<td>"+resp[23]+"</td>" 
		    +"<td>"+resp[24]+"</td>" 
		    +"<td>"+resp[25]+"</td>" 
		    +"<td>"+resp[26]+"</td>" 
		    +"<td>"+resp[27]+"</td>" 
		    +"<td>"+resp[28]+"</td>" 
		    +"<td>"+resp[29]+"</td>" 
		    +"<td>"+resp[30]+"</td>" 
		    +"<td>"+resp[31]+"</td>" 
		    +"<td>"+resp[32]+"</td>" 
		    +"<td>"+resp[33]+"</td>" 
		    +"<td>"+resp[34]+"</td></tr>";

				}
			$('#sample_1').dataTable().fnClearTable();
				$('#TbodyID').html(html);
				loadSearchAndFilter('sample_1');

			} 
			else
			{
				alert("No data");
				$('#sample_1').dataTable().fnClearTable();
			}

		}
		  
        });
	 
     }
        }
$('.input-daterange').datepicker({
	autoclose : true,
	endDate : '+0d'
      });

		</script>
