<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<meta http-equiv="content-type"
	content="application/vnd.ms-excel; charset=UTF-8">
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>"
	type="text/javascript"></script>
<script
	src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>"
	type="text/javascript"></script>
<style>
.test {
	background-color: red;
}

.test2 {
	background-color: green;
}
</style>

<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						App.init();
						//alert('${cirlelist}');
						TableEditable.init();
						TableManaged.init();
						FormComponents.init();

						//  UIDatepaginator.init();

						/*   $("#selectedDateId").val(getPresentMonthDate('${selectedMonth}')); */
						$('#MDMSideBarContents,#reportsId,#sustamperrpt').addClass('start active ,selected');
						$("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n"
						   + "#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager")
						.removeClass('start active ,selected');

						loadSearchAndFilter('sample_editable_1');
						$('#sample_editable_2').dataTable(
								{
									"aLengthMenu" : [ [ 20, 50, 100, -1 ],
											[ 20, 50, 100, "All" ] // change per page values here
									],
									// set the initial value
									"iDisplayLength" : 20,

									"sPaginationType" : "bootstrap",
									"oLanguage" : {
										"sLengthMenu" : "_MENU_ records",
										"oPaginate" : {
											"sPrevious" : "Prev",
											"sNext" : "Next"
										}
									},
									"aoColumnDefs" : [ {
										'bSortable' : false,
										'aTargets' : [ 0 ]
									} ]
								});

						/* alarmData('all'); */
						//currDay();
					});

	var tableToExcelNew = (function() {
		var uri = 'data:application/vnd.ms-excel;base64,', template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>', base64 = function(
				s) {
			return window.btoa(unescape(encodeURIComponent(s)))
		}, format = function(s, c) {
			return s.replace(/{(\w+)}/g, function(m, p) {
				return c[p];
			})
		}
		return function(table, name) {
			if (!table.nodeType)
				table = document.getElementById(table)
			var ctx = {
				worksheet : name || 'Worksheet',
				table : table.innerHTML
			}
			window.location.href = uri + base64(format(template, ctx))
		}
	})();
</script>

<script>
	function clearTabledataContent(tableid) {
		//TO CLEAR THE TABLE DATA
		var oSettings = $('#' + tableid).dataTable().fnSettings();
		var iTotalRecords = oSettings.fnRecordsTotal();
		for (i = 0; i <= iTotalRecords; i++) {
			$('#' + tableid).dataTable().fnDeleteRow(0, null, true);
		}

	}

	function loadSearchAndFilter(param) {
		$('#' + param).dataTable().fnDestroy();
		$('#' + param).dataTable({
			"aLengthMenu" : [ [ 50, 100, -1 ], [ 50, 100, "All" ] // change per page values here
			],
			"iDisplayLength" : 50
		});
		jQuery('#' + param + '_wrapper .dataTables_filter input').addClass(
				"form-control input-small"); // modify table search input 
		jQuery('#' + param + '_wrapper .dataTables_length select').addClass(
				"form-control input-xsmall"); // modify table per page dropdown 
		jQuery('#' + param + '_wrapper .dataTables_length select').select2(); // initialize select2 dropdown 
	}
</script>

<script type="text/javascript">
	function circlenadsubdivbaseddata() {
		var circle = $("#circle").val();
		var sudivision = $("#subdivision").val();
		//alert(sudivision);
		//alert(circle);

		clearTabledataContent('sample_editable_1');

		$.ajax({
			url : "./getdatabycircleandsubdiv",
			type : 'GET',
			data : {
				circle : circle,
				subdivision : sudivision
			},
			asynch : false,
			cache : false,
			success : function(response) {
				//alert(response);

				var html = "";
				for (var i = 0; i < response.length; i++) {
					var resp = response[i];

					var daycon = resp[8].toString().split(' ');
					var houcon = daycon[1].split(':');
					var fin = daycon[0] + ' Days' + houcon[0] + ' Hours'
							+ houcon[1] + ' Minutes' + houcon[2] + ' Seconds';

					html += '<tr><td>' + resp[0] + '</td>' + 
								'<td>' + resp[1] + '</td>' + 
								'<td>' + resp[2] + '</td>' + 
								'<td>' + resp[3] + '</td>' + 
								'<td>' + resp[4] + '</td>' + 
								'<td>' + resp[5] + '</td>' + 
								'<td>' + resp[6] + '</td>' + 
								'<td>' + resp[7] + '</td>' + 
								'<td>' + fin + '</td></tr>';

				}

				$("#alaramasData").html(html);
			}

		});
	}
	/* function Weekdata()
	{
	  
	//  $('#alaramasData').dataTable().fnClearTable();
	  
	clearTabledataContent('sample_editable_1');
	  
	 var fr=$("#fromdate").val();
	 
	 
	 $('#imageee').show();
	 
	
	 $.ajax({
		 url : "./getNotRestoredEvents",  
		 type:'GET',
		 data:{
			 fromdate:fr
			 
		 },
		  asynch:false,
		 cache:false, 
		 success:function(response)
		 {
			 
			 $('#imageee').hide();
			  var html="";
	   		 	for(var i=0;i<response.length; i++)
				{
					var resp=response[i];
					
					html+='<tr><td>'+resp[0]+'</td>'
					 +'<td>'+resp[1]+'</td>'
					+'<td>'+resp[2]+'</td>'
					+'<td>'+resp[3]+'</td>'
					+'<td>'+resp[4]+'</td>'
					+'<td>'+moment(resp[5]).format("YYYY-MM-DD HH:mm:ss")+'</td>' 
					+'<td>'+resp[6]+'</td>'
					+'<td>'+resp[7]+'</td></tr>';
					
				}
	   		 	$("#alaramasData").html(html); 
	    	},
	 
	 });	  		  
	  
	} */
	function showSubdivision(circle) {
		$
				.ajax({
					url : './showsubdivbycircle',
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					data : {
						circle : circle
					},
					success : function(response) {
						//alert(response);

						var html = '';
						html += "<select id='subdivision' name='subdivision'  class='form-control input-medium' type='text'><option value='noVal'>Select Subdivision</option><option value='All'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option  value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#subdivTd").html(html);
						$('#sdoCode').select2();
					}
				});
	}


	

	
</script>


<div class="page-content">

	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>Suspected Tamper Report
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
				<table style="width: 38%">
					<tbody>
						<tr>

							<td id="circleTd"><select
								class="form-control select2me input-medium" id="circle"
								name="circle" onchange="showSubdivision(this.value);">
									<option value='noVal'>Select Circle</option>
									<option value='All'>ALL</option>
									<c:forEach items="${cirlelist}" var="element">
										<option value="${element}">${element}</option>
									</c:forEach>
							</select></td>


							<td id="subdivTd"><select
								class="form-control select2me input-medium" id="subdivision"
								name="sdoCodee">
									<option value='noVal'>Select Sub-Division</option>
									<option value='All'>ALL</option>
									<c:forEach items="${subdivList}" var="sdoVal">
										<option value="${sdoVal}">${sdoVal}</option>
									</c:forEach>
							</select></td>

							<td>
								<button id="view" name="view"
									onclick="return circlenadsubdivbaseddata();" class="btn yellow">
									<b>View</b>
								</button>
							</td>
						</tr>
					</tbody>
				</table>

			</div>
		</div>
		<div class="portlet-body form">
			<form class="form-horizontal">
				<div class="form-body">
					<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<!-- <label class="control-label col-md-3">From Date</label> -->
								<!-- <div class="col-md-6">
																<div class="input-icon">
										<i class="fa fa-calendar"></i> 
										<input class="form-control date-picker input-medium"  type="text" value=""  autocomplete="off"
										data-date-format="yyyy-mm-dd" data-date-viewmode="years" name="fromdate" id="fromdate"/>
									</div>
															</div> -->
								<label class="control-label col-md-3"></label>
								<div class="col-md-6"></div>
							</div>
						</div>
						<!--/span-->
						<!--  <div class="col-md-4">
														<div class="form-group">
															<label class="control-label col-md-3">To Date</label>
															<div class="col-md-6">
																<div class="input-group input-medium date date-picker"  data-date-format="yyyy-mm-dd" data-date-viewmode="years" id="five">
																<input type="text" class="form-control" name="todate" id="todate"  readonly >
																<span class="input-group-btn"><button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span></div>
															</div>
														</div>
														
													</div> 
													 -->
						<!-- <div class="form-actions fluid">
												<div class="row"> -->
						<div class="col-md-3">
							<!-- 	<div class="col-md-offset-3 col-md-9">
															<button type="button"  onClick="Weekdata();" class="btn green" >View</button> 
															<button type="button" onclick="reset();" class="btn red">Reset</button>                              
														</div> -->
						</div>
						<div class="col-md-6"></div>


					</div>
				</div>

			</form>

			<div class="box">
				<div class="tab-content">
					<div id="tab_1-1" class="tab-pane active">
						<form role="form" class="form-horizontal" action=" " method="post">


							<div id="imageee" hidden="true" style="text-align: center;">
								<h3 id="loadingText">Loading..... Please wait.</h3>
								<img alt="image" src="./resources/assets/img/loading.gif"
									style="width: 4%; height: 4%; align-content: center;">
							</div>
							<div class="portlet-body">
								<!-- <div class="table-scrollable"> -->
									<div class="btn-group pull-right">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">
											<!-- <li><a href="#" id="print">Print</a></li> -->
											<li><a href="#" id="excelExport"
												onclick="tableToExcelNew('sample_editable_1', 'Suspected Tamper Report')">Export
													to Excel</a></li>
										</ul>
									</div>

									<table class="table table-striped table-hover table-bordered"
										id="sample_editable_1">

										<thead>
											<tr>
												<th>Circle</th>
												<th>Division</th>
												<th>Subdivision</th>
												<th>Meter number</th>
												<th>Account number</th>
												<th>Event time</th>
												<th>Customer name</th>
												<th>Alarm Description</th>
												<th>Duration</th>
											</tr>
										</thead>
										<tbody id="alaramasData">
											<c:forEach var="element" items="${criticaldata}">
												<tr>
													<td>${element[0]}</td>
													<td>${element[1]}</td>
													<td>${element[2]}</td>
													<td>${element[3]}</td>
													<td>${element[4]}</td>
													<td>${element[5]}</td>
													<td>${element[6]}</td>
													<td>${element[7]}</td>

													<td>
														<c:set var="str1" value="${element[8]}" /> 
														<c:set var="datePart1" value="${fn:split(str1, ' ')}" /> 
														<c:set var="str2" value="${datePart1[1]}" /> 
														<c:set var="datePart2" value="${fn:split(str2, ':')}" />
														 <p>${datePart1[0]} &nbsp; Days &nbsp; 
														    ${datePart2[0]} &nbsp; Hours &nbsp; 
															${datePart2[1]} &nbsp; Minutes &nbsp;
															${datePart2[2]} &nbsp; Seconds
														 </p>
													</td>

												</tr>
											</c:forEach>
										</tbody>
									</table>
								<!-- </div> -->


							</div>
						</form>
					</div>
				</div>
			</div>

		</div>
	</div>
</div>
