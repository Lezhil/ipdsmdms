a<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {     
   	    	     App.init();
   	    	  TableManaged.init();
   	 
   	    	   $('#cumulative-Analysis').addClass('start active ,selected');
   	    	 $("#dash-board,#360d-view,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
   	    	     
   	    	   });
  
  </script>
<div class="page-content" >
<!-- BEGIN PAGE CONTENT-->
          

			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box light-grey">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Cumulative CDF files Analysis Of Current Month Readings</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-toolbar">
								<div class="btn-group"  hidden="true">
									<button id="sample_editable_1_new" class="btn green">
									Select Previous Month<i class="fa fa-plus"></i>
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
										<th>Views</th>
										<th >Account Number</th>
										<th >Meter Searial Number</th>
										<th >CDF File</th>
										<th >Read Date and Time</th>
										
									</tr>
								</thead>
								<tbody>
									<tr class="odd gradeX">
										<td hidden="true"><input type="checkbox" class="checkboxes" value="1" /></td>
										<td>
										<a href="#"><i class="fa fa-bar-chart-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa fa-clock-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa-warning"></i></a>&nbsp;
										<a href="#"><i class="fa fa-tachometer"></i></a>&nbsp;
										<a href="#"><i class="fa fa-flag"></i></a>&nbsp;
										</td>
										<td >236199000251</td>
										<td >RSE47410</td>
										<td >3401CDF.xml</td>
										<td class="center">2013-03-30 00:00:00</td>
										
									</tr>
									<tr class="odd gradeX">
										<td hidden="true"><input type="checkbox" class="checkboxes" value="1" /></td>
										<td>
										<a href="#"><i class="fa fa-bar-chart-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa fa-clock-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa-warning"></i></a>&nbsp;
										<a href="#"><i class="fa fa-tachometer"></i></a>&nbsp;
										<a href="#"><i class="fa fa-flag"></i></a>&nbsp;
										</td>
										<td >236199000252</td>
										<td >RSE47411</td>
										<td >3101CDF.xml</td>
										<td class="center">2013-03-30 00:00:00</td>
										
									</tr>
									<tr class="odd gradeX">
										<td hidden="true"><input type="checkbox" class="checkboxes" value="1" /></td>
										<td>
										<a href="#"><i class="fa fa-bar-chart-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa fa-clock-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa-warning"></i></a>&nbsp;
										<a href="#"><i class="fa fa-tachometer"></i></a>&nbsp;
										<a href="#"><i class="fa fa-flag"></i></a>&nbsp;
										</td>
										<td >236199000253</td>
										<td >RSE47414</td>
										<td >3101EDF.xml</td>
										<td class="center">2013-03-30 00:00:00</td>
										
									</tr>
									<tr class="odd gradeX">
										<td hidden="true"><input type="checkbox" class="checkboxes" value="1" /></td>
										<td>
										<a href="#"><i class="fa fa-bar-chart-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa fa-clock-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa-warning"></i></a>&nbsp;
										<a href="#"><i class="fa fa-tachometer"></i></a>&nbsp;
										<a href="#"><i class="fa fa-flag"></i></a>&nbsp;
										</td>
										<td >236199000254</td>
										<td >RSE47450</td>
										<td >3101DDF.xml</td>
										<td class="center">2013-03-30 00:00:00</td>
										
									</tr>
									<tr class="odd gradeX">
										<td hidden="true"><input type="checkbox" class="checkboxes" value="1" /></td>
										<td>
										<a href="#"><i class="fa fa-bar-chart-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa fa-clock-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa-warning"></i></a>&nbsp;
										<a href="#"><i class="fa fa-tachometer"></i></a>&nbsp;
										<a href="#"><i class="fa fa-flag"></i></a>&nbsp;
										</td>
										<td >236199000255</td>
										<td >RSE47710</td>
										<td >3181CDF.xml</td>
										<td class="center">2013-03-30 00:00:00</td>
										
									</tr>
									<tr class="odd gradeX">
										<td hidden="true"><input type="checkbox" class="checkboxes" value="1" /></td>
										<td>
										<a href="#"><i class="fa fa-bar-chart-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa fa-clock-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa-warning"></i></a>&nbsp;
										<a href="#"><i class="fa fa-tachometer"></i></a>&nbsp;
										<a href="#"><i class="fa fa-flag"></i></a>&nbsp;
										</td>
										<td >236199000256</td>
										<td >RFE47410</td>
										<td >3301CDF.xml</td>
										<td class="center">2013-03-30 00:00:00</td>
										
									</tr>
									<tr class="odd gradeX">
									<td hidden="true"><input type="checkbox" class="checkboxes" value="1" /></td>
										<td>
										<a href="#"><i class="fa fa-bar-chart-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa fa-clock-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa-warning"></i></a>&nbsp;
										<a href="#"><i class="fa fa-tachometer"></i></a>&nbsp;
										<a href="#"><i class="fa fa-flag"></i></a>&nbsp;
										</td>
										<td >236199000257</td>
										<td >SSE47410</td>
										<td >3201CDF.xml</td>
										<td class="center">2013-03-30 00:00:00</td>
										
									</tr>
									<tr class="odd gradeX">
										<td hidden="true"><input type="checkbox" class="checkboxes" value="1" /></td>
										<td>
										<a href="#"><i class="fa fa-bar-chart-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa fa-clock-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa-warning"></i></a>&nbsp;
										<a href="#"><i class="fa fa-tachometer"></i></a>&nbsp;
										<a href="#"><i class="fa fa-flag"></i></a>&nbsp;
										</td>
										<td >236199000258</td>
										<td >HSE47410</td>
										<td >3151CDF.xml</td>
										<td class="center">2013-03-30 00:00:00</td>
										
									</tr>
									<tr class="odd gradeX">
										<td hidden="true"><input type="checkbox" class="checkboxes" value="1" /></td>
										<td>
										<a href="#"><i class="fa fa-bar-chart-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa fa-clock-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa-warning"></i></a>&nbsp;
										<a href="#"><i class="fa fa-tachometer"></i></a>&nbsp;
										<a href="#"><i class="fa fa-flag"></i></a>&nbsp;
										</td>
										<td >236199000259</td>
										<td >WSE47410</td>
										<td >3109CDF.xml</td>
										<td class="center">2013-03-30 00:00:00</td>
										
									</tr>
									<tr class="odd gradeX">
										<td hidden="true"><input type="checkbox" class="checkboxes" value="1" /></td>
										<td>
										<a href="#"><i class="fa fa-bar-chart-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa fa-clock-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa-warning"></i></a>&nbsp;
										<a href="#"><i class="fa fa-tachometer"></i></a>&nbsp;
										<a href="#"><i class="fa fa-flag"></i></a>&nbsp;
										</td>
										<td >236199000260</td>
										<td >RTE47410</td>
										<td >3103CDF.xml</td>
										<td class="center">2013-03-30 00:00:00</td>
										
									</tr>
									<tr class="odd gradeX">
									<td hidden="true"><input type="checkbox" class="checkboxes" value="1" /></td>
										<td>
										<a href="#"><i class="fa fa-bar-chart-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa fa-clock-o"></i></a>&nbsp;
										<a href="#"><i class="fa fa-warning"></i></a>&nbsp;
										<a href="#"><i class="fa fa-tachometer"></i></a>&nbsp;
										<a href="#"><i class="fa fa-flag"></i></a>&nbsp;
										</td>
										<td >236199000261</td>
										<td >RSE49410</td>
										<td >3101CDF.xml</td>
										<td class="center">2013-03-30 00:00:00</td>
										
									</tr>
									
								</tbody>
							</table>
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
</div>