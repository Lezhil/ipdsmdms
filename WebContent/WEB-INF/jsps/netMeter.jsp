<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {

						App.init();
						TableManaged.init();
						FormComponents.init();
						$(
								'#MDMSideBarContents,#netMeter,#ondempro,#meterGroupM')
								.addClass('start active ,selected');
						$(
								"#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
								.removeClass('start active ,selected');
						prelist();
						mtrlist();
						//loadSearchAndFilter1('sample_2');
						

					});

	 $(document).ready(function() {
	$('.js-example-basic-multiple').select2();
	});  
</script>
<script>
function clearTabledataContent(tableid)
{
	 //TO CLEAR THE TABLE DATA
	var oSettings = $('#'+tableid).dataTable().fnSettings();
	var iTotalRecords = oSettings.fnRecordsTotal();
	for (i = 0; i <= iTotalRecords; i++) 
	{
		$('#'+tableid).dataTable().fnDeleteRow(0, null, true);
	} 
	
}
	function prelist() {

		$.ajax({
			type : "POST",
			url : "./preMeterList",
			success : function(res) {
				var html = '';
				$.each(res, function(i, v) {
					html += '<tr><td>' + (i+1) + '</td><td>' + v[2] + '</td><td>'
							+ v[3] + '</td><td>' + v[5] + '</td><td>' + v[29] + '</td></tr>'
				});
				clearTabledataContent('sample_2');
				$("#prelistid").html(html);
			},
			complete: function()
			{  
				loadSearchAndFilter1('sample_2');
			}  
		});
	}
	function mtrlist() {

		$.ajax({
			type : "POST",
			url : "./allMeterList",
			success : function(res) {
				var html = '';
				$.each(res, function(i, v) {
					html += '<option value="'+v+'">'+v+'</option>';
				});
				$("#metersid").html(html);
			}
		});
	}
	function mtrUpdate() {
var mtrno=$("#metersid").val();
		$.ajax({
			type : "POST",
			url : "./preMeter/"+mtrno,
			success : function(res) {
				if(res==1){
				bootbox.alert("Successfully Changed Meter Type ");
				prelist();
				mtrlist();
				}
			}
		});
	}
</script>
<!-- <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script> -->
<!-- <script type="text/javascript" src="./resources/loader.js"></script> -->

<div class="clearfix"></div>

<div class="page-container">

	<div class="page-content">

		<div class="modal fade" id="portlet-config" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"></button>
						<h4 class="modal-title">Modal title</h4>
					</div>
					<div class="modal-body">Widget settings form goes here</div>
					<div class="modal-footer">
						<button type="button" class="btn blue">Save changes</button>
						<button type="button" class="btn default" data-dismiss="modal">Close</button>
					</div>
				</div>

			</div>

		</div>



		<div class="row">
			<div class="col-md-12">
				<div class="portlet box yellow">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-reorder"></i>Prepaid Meters
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a> <a
								href="#portlet-config" data-toggle="modal" class="config"></a> <a
								href="javascript:;" class="reload"></a> <a href="javascript:;"
								class="remove"></a>
						</div>
					</div>
					<div class="portlet-body">
						<div class="row">
							<div class="col-md-6 col-sm-12">
								<!-- BEGIN EXAMPLE TABLE PORTLET-->
								<div class="portlet box grey">
									<div class="portlet-title">
										<div class="caption">
											<i class="fa fa-user"></i>Prepaid Meters List
										</div>
										<div class="actions">
											
											<div class="btn-group">
												
												<ul class="dropdown-menu pull-right">
													<li><a href="#"><i class="fa fa-pencil"></i> Edit</a></li>
													<li><a href="#"><i class="fa fa-trash-o"></i>
															Delete</a></li>
													<li><a href="#"><i class="fa fa-ban"></i> Ban</a></li>
													<li class="divider"></li>
													<li><a href="#"><i class="i"></i> Make admin</a></li>
												</ul>
											</div>
										</div>
									</div>
									<div class="portlet-body">
										<table class="table table-striped table-bordered table-hover"
											id="sample_2">
											<thead>
												<tr>
													<th>S.no</th>
													<th>Circle</th>
													<th>Division</th>
													<th>Sub Division</th>
													<th>Meter Number</th>
												</tr>
											</thead>
											<tbody id="prelistid">
												<!-- <tr class="odd gradeX">
										<td><input type="checkbox" class="checkboxes" value="1" /></td>
										<td>shuxer</td>
										<td ><a href="mailto:shuxer@gmail.com">shuxer@gmail.com</a></td>
										<td><span class="label label-sm label-success">Approved</span></td>
									</tr> -->

											</tbody>
										</table>
									</div>
								</div>

							</div>
<div class="col-md-6 col-sm-12">
								<!-- BEGIN EXAMPLE TABLE PORTLET-->
								<div class="portlet box grey">
									<div class="portlet-title">
										<div class="caption">
											<i class="fa fa-user"></i>Prepaid Meters Configuration
										</div>
										<div class="actions">
											
											<div class="btn-group">
												
												<ul class="dropdown-menu pull-right">
													<li><a href="#"><i class="fa fa-pencil"></i> Edit</a></li>
													<li><a href="#"><i class="fa fa-trash-o"></i>
															Delete</a></li>
													<li><a href="#"><i class="fa fa-ban"></i> Ban</a></li>
													<li class="divider"></li>
													<li><a href="#"><i class="i"></i> Make admin</a></li>
												</ul>
											</div>
										</div>
									</div>
									<div class="portlet-body">
										<form class="form-horizontal" role="form">
								<div class="form-body">
									
									<div class="form-group">
										<label class="col-md-3 control-label">Meter Number</label>
										<div class="col-md-9">
											<!-- <select
								class="js-example-basic-multiple placeholder-no-fix input-large"
								id="metrno" multiple="multiple" autofocus="autofocus">
									<option value="1">1</option>
                                    <option value="2">2</option>
							</select> -->
							<select id="metersid" name=""
								class="form-control placeholder-no-fix select2me" type="text"
								autocomplete="off" placeholder="" >
									<option value="" disabled="disabled" selected="selected">Select</option>

							</select>
										</div>
									</div>
									<div class="form-group">
													<label class="control-label col-md-3">Start Date</label>
													<div class="col-md-9">
														<input class="form-control date-picker"  size="16" type="text" value="" />
													</div>
												</div>
									
									
									
								</div>
								<div class="form-actions right">                           
									<button type="button" class="btn default">Cancel</button>  
									<button type="button" onclick="mtrUpdate()" class="btn green">Submit</button>                            
								</div>
							</form>
									</div>
								</div>

							</div>
						</div>



					</div>
				</div>

			</div>
		</div>


	</div>


</div>




<script type="text/javascript">
	google.charts.load('current', {
		'packages' : [ 'gauge' ]
	});
	google.charts.setOnLoadCallback(drawChart);

	function drawChart() {

		var data = google.visualization.arrayToDataTable([
				[ 'Label', 'Value' ], [ 'Total', 5 ], [ 'Active', 5 ],
				[ 'Inactive', 0 ]

		]);

		var options = {
			width : 600,
			height : 350,
			redFrom : 0,
			redTo : 2500,
			yellowFrom : 2500,
			yellowTo : 4000,
			greenFrom : 4000,
			greenTo : 5000,
			minorTicks : 5,
			max : 5000
		};

		var chartp = new google.visualization.Gauge(document
				.getElementById('chart_div_p'));
		var chartn = new google.visualization.Gauge(document
				.getElementById('chart_div_n'));
		chartp.draw(data, options);
		chartn.draw(data, options);

		setInterval(function() {
			data.setValue(0, 1, 40 + Math.round(60 * Math.random()));
			chart.draw(data, options);
		}, 13000);
		setInterval(function() {
			data.setValue(1, 1, 40 + Math.round(60 * Math.random()));
			chart.draw(data, options);
		}, 5000);
		setInterval(function() {
			data.setValue(2, 1, 60 + Math.round(20 * Math.random()));
			chart.draw(data, options);
		}, 26000);

	}
</script>
