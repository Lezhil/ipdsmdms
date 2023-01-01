<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {     
   	    	      App.init();
	   	    	  TableManaged.init();
	   	    	   $('#360d-view').addClass('start active ,selected');
	   	    	   $("#dash-board,#cumulative-Analysis,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	   	    	     
	   	    	   });
  
  </script>
<div class="page-content" >
    
		<div class="row">
				<div class="col-md-12 ">
					<!-- BEGIN SAMPLE FORM PORTLET-->   
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-reorder"></i>Meter Data Profiling
							</div>
							<div class="tools">
								<a href="" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="" class="reload"></a>
								<a href="" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body form">
							<form role="form">
								<div class="form-body">
									
									<div class="form-group">
										<label >Profile Based on</label>
										<select  class="form-control">
													<option>Tamper Events Meter make Wise</option>
													<option>Meter Type wise Assesment</option>
													<option>LocationWise Assesment </option>
													<option>Location Wise Meter Transactions</option>
										</select>
									</div>
									<div class="form-group">
										<label >Profiling Parameters</label>
										<select multiple class="form-control">
											<option>Option 1</option>
											<option>Option 2</option>
											<option>Option 3</option>
											<option>Option 4</option>
											<option>Option 5</option>
										</select>
									</div>
								<div class="form-actions">
									<button type="submit" class="btn blue">Submit</button>
									<button type="button" class="btn default">Cancel</button>                              
								</div>
							</form>
						</div>
					</div>
					<!-- END SAMPLE FORM PORTLET-->
		</div>
		<div class="row">
				<div class="col-md-6">
					<!-- BEGIN SAMPLE TABLE PORTLET-->
					<div class="portlet box red">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-cogs"></i>Name Plate Details</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
								<table class="table table-hover">
									<thead>
										<tr>
											<th>#</th>
											<th>Parameter</th>
											<th>Value</th>
											
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>1</td>
											<td>Meter Serial Number</td>
											<td>18234644</td>
											
										</tr>
										<tr>
											<td>1</td>
											<td>Meter Date & Time</td>
											<td>2014-01-01 00:00:00</td>
											
										</tr>
										<tr>
											<td>1</td>
											<td>Meter Type</td>
											<td>Direct 3ph</td>
											
										</tr>
										<tr>
											<td>1</td>
											<td>Meter Processor Family</td>
											<td>3Es</td>
											
										</tr>
										
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<!-- END SAMPLE TABLE PORTLET-->
				</div>
				<div class="col-md-6">
					<!-- BEGIN BORDERED TABLE PORTLET-->
					<div class="portlet box yellow">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-picture-o"></i>Vector Diagram</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body">
							<img height="180" width="180" src='/bsmartmdm/resources/assets/img/vector_diagram.JPG'>
						</div>
					</div>
					<!-- END BORDERED TABLE PORTLET-->
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN SAMPLE TABLE PORTLET-->
					<div class="portlet box purple">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-info-circle"></i>Line Voltage,Current and PF Parameters</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
								<table class="table table-striped table-hover">
									<thead>
										<tr>
											<th>#</th>
											<th>Parameter</th>
											<th>L1/Element1</th>
											<th >L2/Element2</th>
											<th>L3/Element3</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>1</td>
											<td>Voltages</td>
											<td>232.86 v</td>
											<td >232.84 v</td>
											<td>232.84 v</td>
										</tr>
										<tr>
											<td>2</td>
											<td>Line Current</td>
											<td>10.32 A</td>
											<td >8.98 A</td>
											<td>9.78 A</td>
										</tr>
										<tr>
											<td>3</td>
											<td>Active Current</td>
											<td>-8.89 A</td>
											<td >-8.62 A </td>
											<td>-8.65 A</td>
										</tr>
										<tr>
											<td>4</td>
											<td>Reactive Current</td>
											<td>-2.24 A</td>
											<td >-0.15 A</td>
											<td>-0.18 A</td>
										</tr>
										<tr>
											<td>5</td>
											<td>Power Factor</td>
											<td>0.98</td>
											<td >0.99</td>
											<td>1.00</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<!-- END SAMPLE TABLE PORTLET-->
				</div>
				
			</div>
</div>