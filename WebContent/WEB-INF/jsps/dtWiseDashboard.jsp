<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>"
	type="text/javascript"></script>
<script
	src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>"
	type="text/javascript"></script>

<%@ page import="java.util.ResourceBundle"%>
<%
	ResourceBundle jasperResource = ResourceBundle.getBundle("application");
	String jasperUrl = jasperResource.getString("JasperServerUrl");
%>

<%
	String newRegionName = (String) request.getSession().getAttribute("newRegionName");
	String officeName = (String) request.getSession().getAttribute("officeName");
	String officeType = (String) request.getSession().getAttribute("officeType");
	String editRights = (String) request.getSession().getAttribute("editRights");
	String viewRights = (String) request.getSession().getAttribute("viewRights");

%>

<html>
<body>

	<div class="page-content" style="width:1540px;">
		<div class="portlet box blue " id="boxid">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-reorder"></i>DT Wise DashBoard
				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse"></a>
				</div>
			</div>


			<div class="portlet-body ">

				<jsp:include page="locationFilter.jsp" />
				
				<div class="col-md-6" id="link" style="margin-left: 750px;margin-top: -40px;" >
						<a href="#" id="openLink" onclick="viewPage()" style="margin-bottom: 10px;margin-left: 14px;"><b>Click Here For... DT Dashboard Reports</b></a>
					    </div> 

				<div class="row" style="margin-left: -1px;">
					
					<div class="col-md-2" id="show">
						<button type="button" id="viewTownDetails"
							onclick="viewDTWiseDashboard()" name="viewChangeMeterData"
							class="btn yellow">
							<b>GENERATE</b>
						</button>
					</div>
				</div>
				
				

				<div class="contain" id="box" style="display: none;">
					<div class="row well" >
						<div id="container" style="margin-top: 15px;"></div>

					</div>
				</div>

				<div id="imageee" hidden="true" style="text-align: center;">
					<h3 id="loadingText">Loading..... Please wait.</h3>
					<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
						style="width: 4%; height: 4%;">
				</div>

			</div>
		</div>
	</div>



	<div class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="gridSystemModalLabel" id="subreportmodal"
		data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog" role="document" style="width: 100%">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>

				</div>
				<div class="modal-body">
					<div id="loadingsub" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
							style="width: 3%; height: 3%;">
					</div>
					<div class="row well">
						<button id="previousPage" style="margin-left: 18%;"
							onclick="privious()">prev</button>
						<input id="currentPageNumber" value="1"
							style="width: 30px; text-align: center;" readonly"/>
						<button id="nextPage" onclick="next()">next</button>
						<select id="exporttype" style="margin-left: 48%;">
							<option value="PDF">pdf</option>
							<option value="Excel (Paginated)">Excel</option>
			
						</select> <a id="download" onclick="exportReport()">Export</a>

						<div id="subreport"></div>
					</div>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->



	<script type="text/javascript">

  var jasperUrl ='<%=jasperUrl%>';
  $(".page-content").ready(function(){
	  /*  var editRight = '${editRights}';
	   var viewRight = '${viewRights}';
	   
	   var officeName = '${officeName}';
	   var officeType = '${officeType}';
	   var newRegionName = '${newRegionName}'; */
	  
	    	
	    	  TableEditable.init();
 	    	  TableManaged.init();
 	    	  FormComponents.init();
 	    	  //UIDatepaginator.init(); 
 	    	  if(officeType == 'circle'){
 	    		 viewDTWiseDashboard();
 	    		 $('#viewTownDetails').hide();
 	    	  } 
 	    	  var pageNumber = 1;
 	    	  var manual_path ='';
 	    	  
 	    	 $("#DashboardContent,#dtWiseDashboard").addClass(
				'start active ,selected');
		     $('#MDMSideBarContents,#ADMINSideBarContents,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
				.removeClass('start active ,selected');

		     $("#toid").hide();

		     App.init();
   	    });

	
  </script>

	<script>
		function viewDTWiseDashboard() {

			var region = $("#LFzone").val();
			var circle = $("#LFcircle").val();
			/* alert(region);
			alert(circle); */
			if (region == "") {
				bootbox.alert("Please Select Region");
				return false;
			}

			if (circle == "") {
				bootbox.alert("Please Select circle");
				return false;
			}

			$('#container').empty();
			//var reportName = "/TNEB/DT_ANALYSIS_DASHBOARD";
			var reportName = "/TNEB/DT_Dashboard_Circle_Level";
			$("#imageee").show();

			$
					.ajax({
						url : "./viewDTWiseJasperDashboard",
						type : 'GET',
						asynch : false,
						cache : false,
						data : {
							reportName : reportName,
							region : region,
							circle : circle
						},
						success : function(data) {
							if (data === 'EoR') {
								$("#imageee").hide();
								//$('#currentPageNumber').val(pageNumber)
								bootbox
										.alert("Oops! An error has occurred in fetching report!!");

							} else {
								$("#imageee").hide();
								$('#box').css('display', '');
								$('#container').empty();
								$('#container').append(data);

								getSubReport();

							}

						},
						error : function(xhr) {
							bootbox
									.alert("Oops! An error has occurred in fetching report");
							$("#imageee").hide();

						}
						
					});

		}

		function showCircle(zone) { // alert("inside shocircle");
			//alert(zone);

			$
					.ajax({
						url : './getCircleByZone',
						type : 'GET',
						dataType : 'json',
						asynch : false,
						cache : false,
						data : {
							zone : zone
						},
						success : function(response) { //alert(response);
							var html = '';
							html += "<select id='LFcircle' name='LFcircle' class='form-control' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
							for (var i = 0; i < response.length; i++) {
								html += "<option  value='"+response[i]+"'>"
										+ response[i] + "</option>";
							}
							html += "</select><span></span>";
							$("#LFcircleTd").html(html);
							$('#LFcircle').select2();
						}
					});
		}
	</script>

	<script type="text/javascript">
		//############# Only for sub reports #############
		 function getSubReport() {
			 
			// var report = $("#jrReport").val();
			 $('.jrPage img').attr('alt','Click Here');
			 $('.jrPage img')
				.click(
						function() {

							//alert(this.src);

							if(this.src.includes("img_0_0_275") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT_overload_last_day_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_277") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_noof_DT_inst_overload_Last_day_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_276") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT_overload_last_week_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_278") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_noof_DT_inst_overload_last_week_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_279") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_overload_current_month_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_280") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_instant_overload_current_month__circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_281") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT_overload_previos_month_circle_based";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_282") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_noof_DT_inst_overload_previos_month__circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_283") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT__underload_last_day_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_285") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT_instant_underload_last_day_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_284") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT__underload_last_week_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_286") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT_instant_underload_last_week_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_287") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of__DT_underload_current_month_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_288") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_instant_underload_current_month_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_289") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of__DT_underload_previos_month_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_290") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT_instant_underload_previos_month_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_77") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT_unbalance_last_day_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_78") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT__instant_unbalance_last_day_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_79") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT_unbalance_last_week_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_80") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT__instant_unbalance_last_week_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_81") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT_unbalance_current_month_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_82") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT__instant_unbalance_current_month_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_83") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT_unbalance_previos_month_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_84") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT__instant_unbalance_last_month_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_113") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT_power_failur_last_day_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_114") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT__instant_power_failur_last_day_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_115") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT_power_failur_last_week_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_116") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT__instant_power_failur_last_week_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_117") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT_power_failur_current_month_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_118") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report/Total_no_of_DT__instant_power_failur_current_month_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_119") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT_power_failur_previos_month_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_120") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report_circle_based/Total_no_of_DT__instant_power_failur_previos_month_circle_based_";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_1131") ){
								//alert("Total No of DT's recording Overloading"); //Total No DT
								manual_path = "/TNEB/sub_report/Total_No_of_DT_s";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_1131") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report/Total_No_of_DT_s_Communicating";
								createSubReport(manual_path);
								
							}
							if(this.src.includes("img_0_0_1131") ){
								//alert("Total No of DT's recording Overloading");
								manual_path = "/TNEB/sub_report/Total_No_of_DT_s_Not_Communicatings";
								createSubReport(manual_path);
								
							}
							
														
						});
		}

</script>
	<script type="text/javascript">
		 function createSubReport(path) {
			var region = $("#LFzone").val();
			var circle = $("#LFcircle").val();

			resetPageNumber();
			$('#subreport').empty(); 
			$('#subreportmodal').modal('toggle');
			$('#loadingsub').show();
		    		$.ajax({
		    			url : "./viewDTWiseJasperSubReportDashboard",
		    			type : 'GET',
		    			cache : false,
		    			async : false,
		    			data : {
		    				reportname : path,
		    				pageNumber : pageNumber,
		    				region : region,
		    				circle : circle
		    			},
		    			success : function(data) {
			    			//alert(data);
			    			
		    				if (data === 'EoR') {
		    					bootbox.alert("Oops! An error has occurred in fetching report!!");

		    					$('#subreportmodal').modal('toggle');
		    				} else {
		    					
		    					//alert("Hiiii data");
		    					$('#subreport').empty();
		    					$('#subreport').append(data);
		    					$('#loadingsub').hide();
		    				}
		    			 
		    			},
		    			error : function(xhr, textStatus, errorThrown) {
		    				$('#subreportmodal').modal('toggle');
		    				bootbox.alert("Oops! An error has occurred in fetching report!!");
		    			}
		    		});
		    	}
		      

		 function next() {
		    	$('#currentPageNumber').val(parseInt($('#currentPageNumber').val()) + 1);
		    	pageNumber = $('#currentPageNumber').val();
		    	executeResult();
		    	
		    }

		    function privious() {
		    	if($('#currentPageNumber').val()>1){
		    				$('#currentPageNumber').val(parseInt($('#currentPageNumber').val()) - 1)
		    				pageNumber = $('#currentPageNumber').val();
		    				executeResult();
		    		}
		    	
		    }
		    function resetPageNumber(){
		    	pageNumber = 1;
		    	$('#currentPageNumber').val(pageNumber);
		    	}

		    function executeResult(){
		    	
		    	var region = $("#LFzone").val();
				var circle = $("#LFcircle").val();
			    
		    		$('#subreport').empty();
			    	$('#subreportmodal').modal({
			    		show : true,
			    		keyboard : false,
			    		backdrop : 'static'
			    	});
			    	
				$('#loadingsub').show();
			    		$.ajax({
			    			url : "./viewDTWiseJasperSubReportDashboard",
			    			type : 'GET',
			    			cache : false,
			    			async : false,
			    			data : {
			    				reportname : manual_path,
			    				pageNumber : pageNumber,
			    				region : region,
			    				circle : circle
			    			},
			    			success : function(data) {
				    			//alert(data);
				    			
			    				if (data === 'EoR') {
			    					$('#currentPageNumber').val(pageNumber);
			    					bootbox.alert("No data for selected criteria");

			    					$('#subreportmodal').modal('toggle');
			    				} else {
			    					
			    					           
					           	 	$('#subreport').empty();
					           	  	$('#subreport').append(data);      
					           		$('#currentPageNumber').val(pageNumber);
									$("#previousPage").show();
									$("#currentPage").show();
									$("#nextPage").show();
			    					$('#loadingsub').hide();
			    				}
			    			 
			    			},
			    			error : function(xhr, textStatus, errorThrown) {
			    				$('#subreportmodal').modal('toggle');
			    				$('#currentPageNumber').val(pageNumber);
			    				bootbox.alert("Oops! An error has occurred in fetching report!!");
			    			}
			    		});
			    	}

		    function exportReport(){
		    	var region = $("#LFzone").val();
				var circle = $("#LFcircle").val();
				
		    	//location.href = suburl + $('#exporttypesub').val();
		    	var exporttype=$("#exporttype").val();
		    	window.open("./downLoadDTWiseSubReports?reportName="+manual_path+"&exporttype="+exporttype+"&region="+region+"&circle="+circle);
				
		    }

	</script>

	

</body>

<script>
function viewPage(){
	window.location.href=("./dtdashboardDetailedReport");
	}
</script>

</html>

