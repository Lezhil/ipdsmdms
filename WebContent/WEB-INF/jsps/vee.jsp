<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {     
   	    	     App.init();
   	    	    TableManaged.init();
   	 
   	    	$('#MDMSideBarContents,#VEE-RulesEngine').addClass('start active ,selected');
   	    	 $("#dash-board,#360d-view,#cumulative-Analysis,#seal-Manager,#cdf-Import,#cmri-Manager,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
   	    	     
   	    	   });
  
  </script>
<div class="page-content" >
<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box light-grey">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Validation and Estimation Configurations</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-toolbar">
								<div class="btn-group">
									<button id="sample_editable_1_new" class="btn green">
									Add New Validation<i class="fa fa-plus"></i>
									</button>
								</div>
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#">Print</a></li>
										<li><a href="#">Save as PDF</a></li>
										<li><a href="#">Export to Excel</a></li>
									</ul>
								</div>
							</div>
							<table class="table table-striped table-bordered table-hover" id="sample_1">
								<thead>
									<tr>
										<th class="table-checkbox" hidden="true"><input type="checkbox" class="group-checkable" data-set="#sample_1 .checkboxes" /></th>
										<th>ID</th>
										<th >Validation Description</th>
										<th >Status</th>
										<th >Estimation Configuration</th>
										<th >Run</th>
									
									</tr>
								</thead>
								<tbody>
									<tr class="odd gradeX">
										<td hidden="true"><input type="checkbox" class="checkboxes" value="1" /></td>
										<td>12321</td>
										<td >Meters without RR data</td>
										<td ><span class="label label-sm label-success">Active</span></td>
										<td ><a href="#">Configure</a></td>
										<td ><a href="#">Run</a></td>
										
									</tr>
									<tr class="odd gradeX">
										<td hidden="true"><input type="checkbox" class="checkboxes" value="1" /></td>
										<td>24232</td>
										<td >Meters without LP data</td>
										<td ><span class="label label-sm label-success">Active</span></td>
										<td ><a href="#">Configure</a></td>
										<td ><a href="#">Run</a></td>
										
									</tr>
									<tr class="odd gradeX">
										<td hidden="true"><input type="checkbox" class="checkboxes" value="1" /></td>
										<td>09809</td>
										<td >Meters with gaps</td>
										<td ><span class="label label-sm label-success">Active</span></td>
										<td ><a href="#">Configure</a></td>
										<td ><a href="#">Run</a></td>
										
									</tr>
									<tr class="odd gradeX">
										<td hidden="true"><input type="checkbox" class="checkboxes" value="1" /></td>
										<td>254543</td>
										<td >Meters with LPs not validated or failed validation</td>
										<td ><span class="label label-sm label-success">Active</span></td>
										<td ><a href="#">Configure</a></td>
										<td ><a href="#">Run</a></td>
										
									</tr>
									<tr class="odd gradeX">
										<td hidden="true"><input type="checkbox" class="checkboxes" value="1" /></td>
										<td>08675</td>
										<td >Meters not exported</td>
										<td ><span class="label label-sm label-success">Active</span></td>
										<td ><a href="#">Configure</a></td>
										<td ><a href="#">Run</a></td>
										
									</tr>
									
									
								</tbody>
							</table>
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
</div>