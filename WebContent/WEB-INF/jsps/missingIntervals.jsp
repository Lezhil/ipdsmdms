<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>

$(".page-content").ready(
		function() {
			App.init();
			loadSearchAndFilter('sample_5');
			
			
			TableEditable.init();
			FormComponents.init();
			
			//loadSearchAndFilter('sample_1');
			 $('#MDMSideBarContents,#missingIntervals,#dataValidId').
			addClass('start active ,selected');
		   $("#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#360d-view,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
			.removeClass('start active ,selected');
		});
</script>

<script>
var gstate="";
function showZone(state) {
	if(state!=null && state !=''){
		gstate=state;
		if(state=='Haryana'){
			$('#zone').find('option').remove();
			$('#zone').append($('<option>', {
				value : 'DHBVN',
				text : 'DHBVN'
			}));
			$('#zone').append($('<option>', {
				value : 'UHBVN',
				text : 'UHBVN'
			}));
		} else if(state=='Himachal'){
			$('#zone').find('option').remove();
			$('#zone').append($('<option>', {
				value : 'HP',
				text : 'HP'
			}));
		}
		
	}
	
}
function showCircle(zone) {
	//alert(zone);
	$.ajax({
				url : './showCircleMDAS' + '/' + zone,
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				success : function(response) {
					var html = '';
					html += "<select id='circle' name='circle'  class='form-control input-medium' type='text'><option value=''>Circle</option>"; //<option value='All'>ALL</option>
					for (var i = 0; i < response.length; i++) {
						html += "<option  value='"+response[i]+"'>"
								+ response[i] + "</option>";
					}
					html += "</select><span></span>";
					$("#circleTd").html(html);
					$('#circle').select2();
				}
			});
}



function getReport() {
	
	var zone=$('#zone').val();
	var circle=$('#circle').val();
	var loadDate=$('#loadDate').val();
	$('#energyBody').empty();
	
	if(loadDate=='' || loadDate==null){
		bootbox.alert('Please Select Date.')
	}
	else{
		$('#imageee').show();
		 $.ajax({
				url : "./getMissingIntervalReport",
				type : "GET",
				data : {
					zone : zone,
					circle : circle,
					loadDate : loadDate
				},
				dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	success:function(data)
		    	{
		    		var html="";
		   		 	for(var i=0;i<data.length; i++)
					{
						var resp=data[i];
						//alert(JSON.stringify(resp));
						 html+="<tr>"
							 +"<td >"+(i+1)+"</td>"
							 +"<td >"+resp[0]+"</td>"
							 +"<td >"+resp[1]+"</td>"
							 +"<td >"+resp[2]+"</td>"
							 +"<td >"+resp[3]+"</td>"
							 +"<td >"+resp[6]+"</td>"
							 +"<td >"+(resp[9]==null?'':resp[9])+"</td>"
							 +"<td >"+(resp[7]==null?'':moment(resp[7]).format('DD-MM-YYYY'))+"</td>"
							 +"<td >"+(resp[8]==null?'':resp[8])+"</td>"
							 +" </tr>";
			   		}
		   		 //$('#discomwiseNotCom').empty();
		   		$('#sample_5').dataTable().fnClearTable();
		   		$('#imageee').hide();
		   		$("#energyBody").append(html);
		    	},
				complete: function()
				{  
					loadSearchAndFilter('sample_5');
				}  
			  }); 
	}
}

function getSummaryReport() {
	
	
	var fromDate=$('#fromDate').val();
	var toDate=$('#toDate').val();
	$('#loadSummary').empty();
	//alert(make+" -- "+dlms+" -- "+mtrno);
	
	if(fromDate=='' || fromDate==null){
		bootbox.alert('Please Select From Date.')
	} 
	else if(toDate=='' || toDate==null){
		bootbox.alert('Please Select To Date.')
	}
	else{
		$('#imageee').show();
		 $.ajax({
				url : "./getLoadSummaryReport",
				type : "GET",
				data : {
					fromDate : fromDate,
					toDate : toDate,
				},
				dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	success:function(data)
		    	{
		    		var html="";
		   		 	for(var i=0;i<data.length; i++)
					{
						var resp=data[i];
						//alert(JSON.stringify(resp));
						 html+="<tr>"
							 +"<td >"+resp[0]+"</td>"
							 +"<td >"+resp[1]+"</td>"
							 +"<td >"+resp[2]+"</td>"
							 +"<td >"+resp[3]+"</td>";
							 /* +"<td >"+resp[4]+"</td>"; */
						html+=" </tr>";
			   		}
		   		 //$('#discomwiseNotCom').empty();
		   		$('#sample_1').dataTable().fnClearTable();
		   		$('#imageee').hide();
		   		$('#basic').modal('show'); 
		   		$("#loadSummary").append(html);
		    	},
				complete: function()
				{  
					loadSearchAndFilter('sample_1');
				}  
			  }); 
			}
}
</script>



<div class="page-content">
	<div class="row">
		<div class="col-md-12">
		

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Missing Interval Report
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
						<table>
								<tbody>
									<tr>
										<!-- <td><select class="form-control select2me input-medium" id="state" name="state" onchange="showZone(this.value);">
											<option value="">State</option>
											<option value="Haryana">Haryana</option>
											<option value="Himachal">Himachal Pradesh</option>
											
										</select></td> -->
									
										<td><select class="form-control select2me input-medium"
										name="zone" id="zone" onchange="return showCircle(this.value)">
											<option value="">Select Zone</option>
											 <c:forEach var="elements" items="${zoneList}">
												<option value="${elements}">${elements}</option>
											</c:forEach> 
										</select></td>
										
										
										<td>
										<td id="circleTd"><select class="form-control select2me input-medium" id="circle" name="circle">
											<option value="">Select Circle</option>
											<!-- <option value="Haryana">Haryana</option>
											<option value="Himachal">Himachal Pradesh</option> -->
											
										</select></td>
										<td>
											<div class="input-icon">
													<i class="fa fa-calendar"></i> <input
													class="form-control date-picker input-medium"  type="text"
													value=""  autocomplete="off"
													data-date-format="yyyy-mm-dd" data-date-viewmode="years" name="loadDate" id="loadDate"/>
											</div>
										
										</td>
										
										
										
										<td style="padding-left: 15px;">
											<button  onclick="return getReport();"  class="btn yellow">
												<b>View</b>
											</button>
										</td>
										
									</tr>
								</tbody>
							</table>
					
					
					<br>
					<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/assets/img/loading.gif"
							style="width: 4%; height: 4%; align-content: center;">
					</div>
					<br>
					
					<div class="table-toolbar" >
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport2"
									onclick="tableToExcel2('sample_5', 'Outage & Reliability Report')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					
					<table class="table table-striped table-hover table-bordered" id="sample_5">					
						<thead>
							
							<tr>
								<th>Sl No</th>
							<!-- 	<th>State</th> -->
								<th>Zone</th>
								<th>Circle</th>
								<th>Division</th>
								<th>Subdivision</th>
							<!-- 	<th>Feeder Name</th> -->
								<th>Meter No</th>
								<th>Load Survey Interval in Minutes</th>
								<th>Date</th>
								<th>Load Survey Count</th>
							</tr>
						</thead>
						<tbody id="energyBody">
							
						</tbody>
					</table>
					
				</div>
			</div>
		</div>
		
		
		
		<div class="modal fade" id="basic" tabindex="-1" role="basic" aria-hidden="true">
					<div class="modal-dialog" style="width: 1000px;">
						<div class="modal-content">
							<div class="modal-header" style="background-color: #4b8cf8;">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true"></button>
								<h4 class="modal-title" ><font style="font-weight: bold; color: white;">DETAILS</font></a></h4>
							</div>
							<div class="modal-body">
								<div class="table-toolbar" >
									<div class="btn-group pull-right" style="margin-top: 15px;">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">
											<li><a href="#" id="excelExport7"
												onclick="javascript:xport.toCSV('sample_1');">Export
													to Excel</a>
											</li>
										</ul>
									</div>
								</div>
								
								<table class="table table-striped table-hover table-bordered" id="sample_1">
									<thead>
										<tr>
											<th>Date</th>
											<th>ROHTAK Total Load</th>
											<th>PANCHKULA Total Load</th>
											<th>Total Load</th>
											<!-- <th>% Total Load</th>
											<th>DHBVN Full Load</th>
											<th>UHBVN Full Load</th>
											<th>Haryana Full Load</th>
											<th>% Full Load</th> -->
											
										</tr>
									</thead>
									<tbody id="loadSummary">
										
									</tbody>
								</table>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn dark btn-outline" data-dismiss="modal">Close</button>
							</div>
						</div>
						<!-- /.modal-content -->
					</div>
					<!-- /.modal-dialog -->
				</div>
		
		
		
	</div>
	
</div>

<style>

.table-scrollable {
    width: 100%;
    overflow-x: auto;
    overflow-y: hidden;
    border: 1px solid #dddddd;
    margin: 10px 0 !important;
} 

</style>

<script>

var xport = {
		  _fallbacktoCSV: true,  
		  toXLS: function(tableId, filename) {   
		    this._filename = (typeof filename == 'undefined') ? tableId : filename;
		    
		    //var ieVersion = this._getMsieVersion();
		    //Fallback to CSV for IE & Edge
		    if ((this._getMsieVersion() || this._isFirefox()) && this._fallbacktoCSV) {
		      return this.toCSV(tableId);
		    } else if (this._getMsieVersion() || this._isFirefox()) {
		      alert("Not supported browser");
		    }

		    //Other Browser can download xls
		    var htmltable = document.getElementById(tableId);
		    var html = htmltable.outerHTML;

		    this._downloadAnchor("data:application/vnd.ms-excel" + encodeURIComponent(html), 'xls'); 
		  },
		  toCSV: function(tableId, filename) {
		    this._filename = (typeof filename === 'undefined') ? tableId : filename;
		    // Generate our CSV string from out HTML Table
		    var csv = this._tableToCSV(document.getElementById(tableId));
		    // Create a CSV Blob
		    var blob = new Blob([csv], { type: "text/csv" });

		    // Determine which approach to take for the download
		    if (navigator.msSaveOrOpenBlob) {
		      // Works for Internet Explorer and Microsoft Edge
		      navigator.msSaveOrOpenBlob(blob, this._filename + ".csv");
		    } else {      
		      this._downloadAnchor(URL.createObjectURL(blob), 'csv');      
		    }
		  },
		  _getMsieVersion: function() {
		    var ua = window.navigator.userAgent;

		    var msie = ua.indexOf("MSIE ");
		    if (msie > 0) {
		      // IE 10 or older => return version number
		      return parseInt(ua.substring(msie + 5, ua.indexOf(".", msie)), 10);
		    }

		    var trident = ua.indexOf("Trident/");
		    if (trident > 0) {
		      // IE 11 => return version number
		      var rv = ua.indexOf("rv:");
		      return parseInt(ua.substring(rv + 3, ua.indexOf(".", rv)), 10);
		    }

		    var edge = ua.indexOf("Edge/");
		    if (edge > 0) {
		      // Edge (IE 12+) => return version number
		      return parseInt(ua.substring(edge + 5, ua.indexOf(".", edge)), 10);
		    }

		    // other browser
		    return false;
		  },
		  _isFirefox: function(){
		    if (navigator.userAgent.indexOf("Firefox") > 0) {
		      return 1;
		    }
		    
		    return 0;
		  },
		  _downloadAnchor: function(content, ext) {
		      var anchor = document.createElement("a");
		      anchor.style = "display:none !important";
		      anchor.id = "downloadanchor";
		      document.body.appendChild(anchor);

		      // If the [download] attribute is supported, try to use it
		      
		      if ("download" in anchor) {
		        anchor.download = this._filename + "." + ext;
		      }
		      anchor.href = content;
		      anchor.click();
		      anchor.remove();
		  },
		  _tableToCSV: function(table) {
		    // We'll be co-opting `slice` to create arrays
		    var slice = Array.prototype.slice;

		    return slice
		      .call(table.rows)
		      .map(function(row) {
		        return slice
		          .call(row.cells)
		          .map(function(cell) {
		            return '"t"'.replace("t", cell.textContent);
		          })
		          .join(",");
		      })
		      .join("\r\n");
		  }
		};


</script>