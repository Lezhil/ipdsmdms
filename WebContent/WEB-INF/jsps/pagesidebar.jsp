<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- BEGIN SIDEBAR -->
<%
	String accessRight = (String) request.getSession().getAttribute(
			"accessRights");
%>

<c:set var="accessRights" value="<%=accessRight%>"></c:set>
<c:set var="str" value="${fn:split(accessRights, ',')}" />

<head>
	<meta charset="utf-8" />
	<title>Metronic | Admin Dashboard Template</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<meta name="MobileOptimized" content="320">
	<!-- BEGIN GLOBAL MANDATORY STYLES -->          
	<link href="resources/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" href="resources/assets/css/icofont.min.css">
	<link href="resources/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
	<!-- END GLOBAL MANDATORY STYLES -->
	<!-- BEGIN PAGE LEVEL PLUGIN STYLES --> 
	<link href="resources/assets/plugins/gritter/css/jquery.gritter.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css" />
	<link href="resources/assets/plugins/fullcalendar/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/plugins/jqvmap/jqvmap/jqvmap.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/plugins/jquery-easy-pie-chart/jquery.easy-pie-chart.css" rel="stylesheet" type="text/css"/>
	<!-- END PAGE LEVEL PLUGIN STYLES -->
	<!-- BEGIN THEME STYLES --> 
	<link href="resources/assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/css/plugins.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/css/pages/tasks.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>
	<link href="resources/assets/css/custom.css" rel="stylesheet" type="text/css"/>
	<!-- END THEME STYLES -->
	<link rel="shortcut icon" href="favicon.ico" />
</head>

<div class="page-sidebar navbar-collapse collapse" style="width: 230px;">
	<!-- BEGIN SIDEBAR MENU -->
	<!--  <a href="http://182.18.144.86:80/login/srinu@bcits.co.in/sticb$123"target="_blank"><button style="width:225px; background-color: #1a1f1a;"><font color="red" size="4">MDAS-E</font></button></a> 
		 -->

	<ul class="page-sidebar-menu">
		
		<li class=" " id="DashboardContent">
				<a href=""> <i class="fa fa-bolt"></i>
					<span class="title">DASHBOARD </span></a>

			<ul class="sub-menu">
				
				<li id="livedashboard" >
					<a href="./dashBoard2MDAS?type=${officeType}&value=${officeName}">
					<i class="icofont-dashboard-web"></i> Dashboard (Home)</a> 
				</li>
				
				<li class="" id="healthDashboard"> 
					<a href="./healthDashboard?type=${officeType}&value=${officeName}"> 
					<i class="icofont-dashboard-web"></i> DashBoard (Communication) </a>
				</li> 
					
				<li class="" id="dtWiseDashboard" hidden="true"><a href="./dtWiseDashboard"> 
								<i class="icofont-data"></i><span class="title"> DT Wise DashBoard</span></a></li>
				

			</ul>
			
			</li>
		
		<li class=" " id="MDASSideBarContents">
				<a href=""> <i class="fa fa-cogs"></i>
					<span class="title">DATA ACQUISITION</span></a>

			<ul class="sub-menu">
				
				<%-- <li id="livedashboard" >
					<a href="./dashBoard2MDAS?type=${officeType}&value=${officeName}">
					<i class="icofont-dashboard-web"></i> Live Dashboard</a> 
				</li>
				
				<li class="" id="healthDashboard"> 
					<a href="./healthDashboard?type=${officeType}&value=${officeName}"> 
					<i class="icofont-dashboard-web"></i> DashBoard  </a>
				</li> 
					
				<li class="" id="dtWiseDashboard" hidden="true"><a href="./dtWiseDashboard"> 
								<i class="icofont-data"></i><span class="title"> DT Wise DashBoard</span></a></li> --%>
					
				 <li class="" id="mdmDashId">
					<a href="#"> <i class="icofont-dashboard"></i>
					  <span class="title"> Meter Communication </span></a>
					
						<ul class="sub-menu">
				
									<li class="" id="mtws" hidden="true"><a href="./meterTypeWiseSummary"> 
									<span class="title"> Meter Type Wise Summary  </span></a></li>
					
									<li class="" id="pwsumm" hidden="true"><a href="./periodWiseCommSummary"> 
									<span class="title"> Meter Communication Summary  </span></a></li>
										
									<li class="" id="unmtrs" hidden="true"><a href="./unmappedMeters"> 
										<span class="title"> Unmapped Meters </span></a></li>
										
									<li class=" " id="dcuDetails" hidden="true"><a href="./dcuDetails" id="dcuDetails">
							    		 DCU Communication</a></li>
							    		 
							     	<li class="" id="calenderEventsMDAS" hidden="true"><a href="./calenderEventsMDAS"> 
									<span class="title"> Communication Calendar</span></a></li>
									
									
									<!-- <li class="" id="dtWiseDashboard" hidden="true"><a href="./dtWiseDashboard"> 
									<span class="title"> DT Wise DashBoard  </span></a></li> -->
						

					</ul>
				 </li>
			
					<li class=" " id="meterOper" style="display: none;">
						<a href="#" id="meterOper">
							<i class="icofont-speed-meter"></i><span class="title"> Meter Operations</span> 
						</a>
				
					 <ul class="sub-menu">
				   				  <li class="" id="mftrd" hidden="true">
								    <a href="./mftrd" > Meter First Time Read Data</a>
								  </li>		
										
								  <li class="" id="meterAlarm" hidden="true">
									<a href="./meterAlarm" > Meter Exceptions/Alarms</a>
								  </li>
							
								   <li id="statusUpdate" hidden="true"><a href="./meterStatus">
								    <span class="title"> Connect/Disconnect</span></a>
								   </li>
							
			         				<li class=" " id="loadCurtailmentLi" hidden="true">
			         				 <a	href="./loadCurtailment" id="loadCurtailment">Send Load Curtailment Signals	</a>
			         				</li>
																		
								<!-- 	<li class=" " id="ondemand"><a href="./newOnDemandProfileAMI" id="ondempro">
									  <span class="title"> On Demand Read</span></a>
									 </li> -->
									 <li class=" " id="ondemand" hidden="true"><a href="./onDemandProfileAMI" id="ondempro">
									 	<i class="icofont-download-alt"></i>
									  <span class="title"> On Demand Read</span></a>
									 </li>
									 
									 <li class=" " id="cmriupload" hidden="true"><a href="./cdfImportManager"
										id="cmriupload"><i class="icofont-download-alt"></i><span class="title">
											CMRI Upload</span></a>
									</li>
											
									<li class=" " id="ChangeAndInstallation"  hidden="true" ><a href=""> <i
											class="fa fa-wrench"></i> <span class="title">Meter Change Process</span></a>
										<ul class="sub-menu">					
											<li class=" " id="changeMeter"  hidden="true"><a href="./changeMeter">Change Meter</a>
											</li>
											<li class=" " id="meterChangeRpt" hidden="true"><a href="./meterChangeRpt">Meter Change Report</a>
											</li>	
											
									   </ul>
									</li>
			
									 <!-- <li class=" " id="ondemand"><a href="./newOnDemandProfileAMI" id="ondempro">
									  <span class="title"> On Demand Read New </span></a>
									 </li> -->
									 
									<li class=" " id="schedule-meter" hidden="true"><a href="./smc?event=10"
										id="schedulemeter"><span
											class="title">Schedule Meter Reading</span></a></li>
					
									<li class=" " id="mdreset" hidden="true"><a href="./mdReset"
										id="loadCurtailment">
											MD Reset
									</a></li>
									
									<li class=" " id="firmwareupgrade" hidden="true"><a href="./firmwareupgrade"
										id="frmupgrade"> Update Firmware Version	</a>
									</li>
									
									<li class=" " id="metergroup" hidden="true" >
									<a href="./metergroupl" id="hierarchyLevels"> Create Meter Group</a>
									</li>
						</ul>
					</li>

				<li class=" " id="modemOper" style="display: none;"><a href="#"
					id="meterOper"> <i class="icofont-automation"></i> <span
						class="title"> Modem Diagnosis</span>
				</a>

					<ul class="sub-menu">
						<li class="" id="modemDiagnosis" hidden="true"><a
							href="./modemdiagnosis">Modem Diagnosis</a></li>


					</ul>
				</li>

			</ul>
			</li>
			
		<!-- --------------MDM Menu Starts ------------------- -->	
				
		
		<li class=" " id="MDMSideBarContents">
			<a href="">	<i class="fa fa-tachometer" aria-hidden="true"></i>
					 <span class="title">MDMS</span></a>

		<ul class="sub-menu">
		
		
					<!-- ---------------------DashBoard Menu Starts here------------------ -->
				
					 <li class="" id="mdmDashBoardId" style="display: none;">
					 	<a href="#"> <span class="title" ><i class="icofont-dashboard"></i>	DashBoard</span></a>
							<ul class="sub-menu">
								
								<li class="" id="showVariationOfEnergyID" hidden="true"><a href="./showVariationOfEnergy"> 
									<span class="title">Supply Variation Report</span></a></li>	
							</ul>
					 </li>

					<li class=" " id="metermang" style="display: block;"><a
							href="#" id="metermang"><i class="icofont-list"></i>
							<span class="title">Asset Management</span></a>

						<ul class="sub-menu">

								<li class=" " id="mtrmngDtls" hidden="true"><a href="./manageMeters"
									id="mtrDtlsHref"> Total Meters</a></li>
								
								<li id="mtrDtls" hidden="true"><a id="mtrDtls"
									href="./meterDetails" >Meter Details</a></li>
								
								<li class=" " id="MeterLifeCycle" hidden="true"><a href="./meterLifeCycleData" id="MeterLifeCycle">
								 Meter Life-cycle</a></li> 
								 
								 <!-- <li class=" " id="modemInventory" hidden="true"><a href="./inventoryMDAS" id="MeterLifeCycle">
							     Modem Inventory </a></li>  -->
							     
							      <li class=" " id="simdetailsID" hidden="true"><a href="./simdetails" id="MeterLifeCycle">
							     SIM Details </a></li> 
								
								 <!-- 	<li class="" id="simdetailsID"><a href="./simdetails"> 
						<span class="title"> SIM Details</span></a></li> -->
						</ul>
						
					</li>
							
					<li class="" id="surveydetails" hidden="true"><a href="#"><i class="icofont-chart-pie-alt"></i>
						<span class="title">Manage Survey Details</span></a>			
						
			
						<ul class="sub-menu">
						
							<!-- <li class=" " id="installation" hidden="true"><a href="./installationDetailsMDAS?date=nodate"> 
							<span class="title"> Survey and Installation Report</span></a></li>	 -->
						
						
							<li class="" id="meterChangeReport" hidden="true"><a href="./meterChangeReport"> 
							<span class="title">Meter Change Report </span></a>		
							
						</ul>
					</li>		
				
							
		
				<li class="" id="mpmId"><a href="#"> <i class="icofont-connection"></i>
							Masters</span></a>
							
				   	<ul class="sub-menu">
				   		
				   				<li class="" id="townDetails" hidden="true"><a href="./townDetails"> 
								<span class="title">Town Details</span></a></li>
								
								<li class="" id="addSubstationId" hidden="true"><a href="./substationdetails"> 
								<span class="title">Sub-Station Details</span></a></li>	
								
								<li class="" id="mastersubstationDetailsForCirclewise"hidden="true"><a href="./mastersubstationdetails"> 
								<span class="title">Substation-Master</span></a></li>	
								
								<li class="" id="fdrmaster"><a href="./fdrMasterDetails"> 
								<span class="title">Feeder-Master </span></a></li>
								
								<li class="" id="fdrdetails"><a href="./fdrdata"> 
								<span class="title">Feeder Meter Details </span></a></li>
								
								<!-- <li class="" id="boundaryDetails" hidden="true"><a href="./boundaryDetails"> 
								<span class="title">Boundary Details </span></a></li> -->
								
								<li class="" id="boundary" hidden="true"><a href="./boundary"> 
								<span class="title">Boundary Details </span></a></li>
									
								<li class="" id="dtDetailsId" hidden="true"><a href="./DTdetails"> 
								<span class="title">DT Details</span></a></li>
									
								<!-- <li class="" id="addConsumerId" hidden="true"><a href="./addNewConsumer"> 
								<span class="title">Consumer Detail</span></a></li> -->
							
								<li class="" id="manageVirtualLoaction" hidden="true"><a href="./manageVirtualLoaction"> 
								<span class="title">Manage Virtual Location</span></a></li>
							
								<!-- <li id="fdrOnMap" hidden="true"><a href="./feedersOnMapMDAS">
								 <span class="title">Google Map View</span></a></li> -->
								
								<!-- <li id="gisview" hidden="true"><a href="./gisview"> 
								<span class="title">Google Map View</span></a></li> -->
								
								<li class="" id="dtMeterApproval" hidden="true"><a href="./dtMeterApproval"> 
								<span class="title">DT Meter Mapping Approval</span></a></li>
						</ul>
				</li>		
					
							
					<li class="" id="360MeterDataViewID"><a href="#">
						<i class="icofont-database"></i><span class="title">
							Meter Data- 360 D Data View</span></a>
							
						<ul class="sub-menu">
							<li class="" id="360d-view" hidden="true"><a href="./mtrNoDetailsMDAS?mtrno=" id="360dView"> 
							<span class="title">360 D Data View</span></a></li>
					
							<li class="" id="getMinMaxData" hidden="true"><a href="./getMinMaxData"> 
							<span class="title">Min / Max/ Avg- Data</span></a></li>		
							<!-- 		
							<li id="dwnXMLData" hidden="true"><a href="./downloadXMLDataMDAS">
							<span class="title"> Meter Data Export</span></a></li> -->
						</ul>
					</li>		
				
				
				<li class="" id="dataValidId"><a href="#" hidden="true"><i class="icofont-verification-check"></i> 
					<span class="title">Data- VEE Module</span></a>
							
					<ul class="sub-menu">
					
						<!-- <li class="" id="veeValidationCheck"><a href="./veeValidationCheck"> 
						<span class="title">Validations</span></a></li> -->
						
						<li class="" id="defineValidation" hidden="true"><a href="./defineValidation"> 
						<span class="title">Define Validation</span></a></li>
		
				  	    <li class="" id="assignValidation" hidden="true"><a href="./assignValidation"> 
						<span class="title">Assign Validation Rule </span></a></li>
							
						<li class="" id="validationReport" hidden="true"><a href="./validationReport"> 
						 <span class="title">Data Validation Exception Reports</span></a></li> 
						 
					   <li class="" id="manualEstimationAndEdit" hidden="true"><a href="./manualEstimationAndEdit">
					     <span class="title">Manual Estimation and Edit</span></a></li>	
								
						<li class="" id="estimationRule" hidden="true"><a href="./estimationRule"> 
						 <span class="title">Define Estimation</span></a></li>
									
						<li class="" id="assignEstimation" hidden="true"><a href="./assignEstimation"> 
						 <span class="title">Assign Estimation Rule</span></a></li>		
						 
					   <li class="" id="dataEstimation" hidden="true"><a href="./dataestimationreport">
					     <span class="title">Data Estimation Report</span></a></li>						
					
						<!-- <li id="ruleConfig"><a href="./currentEstimation">
						 <span 	class="title"> Estimates & Edits</span>	</a></li>
					
						<li id="missingIntervals"><a href="./missingIntervals"> <span
						class="title"> Missing Intervals</span></a></li>
								
						<li id="energySumCheck"><a href="./energySumCheck">
						 <span	class="title"> Energy Sum Check</span></a></li> -->
					
						<li class="" id="auditTrailsAMR" hidden="true"><a href="./auditTrailsAMR"> 
						 <span class="title"> Audit Trail</span></a></li>
											
					    
					</ul>
				</li>
		
					<li class="" id="DPId" style="display: none;"><a href="#"> <span class="title">
								<i class="icofont-automation"></i> 	Data Processing</span></a>
									
									<ul class="sub-menu">
										
									 		<li class="" id="todviewid" hidden="true"><a href="./todview"> 
										 	<span class="title">TOD Defination</span></a></li> 
										
											<li class="" id="dailyConsumption" hidden="true"><a href="./dailyConsumption"> 
										 	<span class="title">Daily Data Aggregation </span></a></li>
										
											<!-- <li class="" id="vlparameters" hidden="true"><a href="./vlParameters"> 
										    <span class="title">Virtual Location Parameters </span></a></li> -->		
												
							 				<li class="" id="monthlyConsumption" hidden="true"><a href="./monthlyConsumptionReport"> 
											<span class="title"> Monthly Data Aggregation </span></a></li>
										
											<li class="" id="TodreportId" hidden="true"><a href="./toddaywisereport"> 
											<span class="title"> TOD wise daily data aggregation </span></a></li>
								 </ul>
					</li>
		
				
					<li class="" id="alarmID" style="display: none"><a href="#"> 
							<i class="icofont-ui-alarm"></i><span class="title"> Alarm and Notifications</span></a>
							<ul class="sub-menu">
								 
								 <!-- 	<li class="" id="alarm" hidden="true"> <a href="./alarms" id="alarm">
								 	<span class="title"> Alarms</span></a></li> -->
								 
									<li class=" " id="alarmSetting" hidden="true"> <a href="./alarmSetting"   id="alarmSetting">
			       					<span class="title">Alarm Setting</span></a> </li>	
			       					
			       					<li class=" " id="removealarms" hidden="true" > <a href="./removealarms" id="removealarms">
									<span class="title">Removal Alarm Setting</span></a> </li>
								
									<li class="" id="viewalarms" hidden="true"><a href="./viewalarms"> 
									<span class="title">View Alarms </span></a></li>
									
									<li class="" id="viewalarmshst" hidden="true"><a href="./viewalarmshst">
									<span class="title">View Alarms History   </span></a></li>
			 
			 						<li id="alarmDispatch" hidden="true"><a href="./alarmDispatchdetails">
			 						<span class="title">Alarm Dispatch Details</span></a></li>
			 						
									
									
									<!-- <li id="networkgateway" hidden="true"><a href="./networkGateWay">
									<span class="title">Network	File Directory</span></a></li> -->
																	
							</ul>
						</li>
		
					<!-- ---------------------Reports Menu Starts here------------------ -->
					
					
					<!-- <li class=" " id="dtWiseReport" ><a href="#" id="dtWiseReport">
						 <i class="icofont-data"></i><span class="title"> DT- Data Analysis</span></a>
									
										<ul class="sub-menu">
											<li class="" id="dtHealthReport" hidden="true"><a href="./dtHealthReport">DT Health Report</a></li>								 
											<li class="" id="reliabilityIndicesDT" hidden="true"><a href="./reliabilityIndicesDT">DT- Reliability Indices</a></li>				
											<li class="" id="dtLoadingSummary" hidden="true"><a href="./dtLoading">DT Loading Summary</a></li>		
											<li class="" id="dtconsumptionid" hidden="true"><a href="./dtConsumption">Multi Meter- DT Consumption</a></li>
											<li class="" id="dtdashboardReports" hidden="true"><a href="./dtdashboardReports">DT DashBoard Reports </a></li>
										</ul>
										
									</li> -->
					
						

					<!-----------------------Usage Analysis Menu Starts here------------------ -->
					
			 		 	<li class=" " id="consumerConsumptionMDAS" hidden="true" ><a
								href="./consumptionCurveMDAS" id="consumerConsumptionMDAS"><i class="icofont-chart-pie"></i>
								<span class="title">Usage Analysis</span></a>
						</li>
						
						<!-----------------------Service order management------------------ -->	
						
						<li class=" " id="serviceOrderManagementID" hidden="true"><a
								href="#" id="serviceOrderManagement" ><i class="fa fa-wrench"></i>
								<span class="title">Service order management</span></a>
					
								<ul class="sub-menu">
					
					 				<li class="" id="generateServiceOrder" hidden="true"><a href="./generateServiceOrder"> 
									 <span class="title"> Generate Service Order</span></a></li>  
									
									 <li class="" id="serviceOrderFeedback" hidden="true"><a href="./serviceOrderFeedback">
									 <span class="title"> Service Order feedback</span></a></li>	
									 	
									  <li class="" id="serviceOrderSummary" hidden="true"><a href="./serviceOrderSummary">
									 <span class="title"> Service Order summary</span></a></li>	
					
								</ul>
						</li>
		</ul>
	
	 </li>
	 
	 
	
	 
	 
	 
	 
	 
	 
	 <li class="" id="reportsId"><a href="#"	>
						 <i class="icofont-data"></i><span class="title"> DATA ANALYTICS</span></a>
							
							<ul class="sub-menu">
							
								<li class="" id="consumLoadAnalys" hidden="true"><a href="./consumLoadAnalys"> 
									<span class="title">Consumption Analysis </span></a></li>	
									
								<li class="" id="feederHealthRpt" hidden="true"><a href="./feederHealthReport"> 
									<span class="title">Feeder Health Report  </span></a></li>
									
								<li class="" id="nonReadMetersReport" hidden="true"><a href="./consisCommFailRep">
									 <span class="title">Detailed - Meter Communication Report</span></a></li>
									 
								<li class="" id="saifiSaidiLi" hidden="true"><a href="./saifiSaidi">
								    <span class="title">Feeder - Reliability Indices </span></a></li>
								    
								<li class="" id="feederOutage" hidden="true"><a href="./feederOutage">
								    <span class="title">Feeder Outage Report</span></a></li>
								    
								<li class="" id="pfanalysis" hidden="true"><a href="./powerfactoranalysis"> 
									<span class="title">Revenue Protection Reports</span></a></li>
									
								<li class="" id="networkId" hidden="true"><a href="./netwotkperformance"> 
									<span class="title">Network Asset Performance</span></a></li>
									
									<li class="" id="saidId" ><a href="./saidicaidi"> 
									<span class="title">SAIDI / CAIDI</span></a></li>
								
									<li class="" id="frequencyId" hidden="true"><a href="./frequencyobviation">
										<span class="title">Frequency Deviation</span></a></li>
								
					
								<!-- <li class="" id="exceptionalEnergyUsage" hidden="true"><a href="./excptionalenergyusageConsumers"> 
								<span class="title">Exceptional Energy Usage report</span></a></li>
								
								<li class="" id="suspectedEnergyReadingHistory" hidden="true"><a href="./suspectedenergyReadinghistory"> 
					 			</i><span class="title">Suspected Energy Reading History Report </span></a></li> -->
						
								<!-- <li class="" id="consumptionCurveMDM" hidden="true"><a href="./consumptionCurveMDM">
								 <span class="title"> Consumption Curve</span></a></li> -->
							
								<!-- <li class="" id="ConsistentCommunicationFailMeters"><a href="./ConsistentCommunicationFailMeters"> 
								<span class="title"> Consistent Communication Fail Meters Report   </span></a></li>	 -->		
							
							<!-- Depricated --> 
							<!-- 	<li class="" id="getBillingDataSyncDetails" hidden="true"><a href="./getBillingDataSyncDetails"> 
								<span class="title"> RMS Reading Transfer Report   </span></a></li>	 -->
							
								<!-- <li class="" id="analysedTamperReport" hidden="true"><a href="./analysedTamperReport"> 
								<span class="title"> Tamper Data Report   </span></a></li>	
				
								
								<li class="" id="sustamperrpt" hidden="true"><a href="./suspectedtamperreport"> 
								<span class="title">Suspected Tamper Report   </span></a></li> -->
							
								<!-- <li class="" id="analyzedReports" hidden="true"><a href="./analyzedReports"> 
								<span class="title">Analyzed Reports </span></a></li>		 -->
					
								
								<!-- <li class="" id="TamperEventSummaryReportId" hidden="true"><a href="./TamperEventSummaryReport"> 
								<span class="title">TamperEventSummaryReport</span></a></li>
								
								
								<li class="" id="suspwrtheftrpt" hidden="true"><a href="./suspectedpowertheftreport"> 
								<span class="title">Suspected Power Theft Report   </span></a></li>	
							
								<li class="" id="oldDismantleMetersId" hidden="true"><a href="./oldDismantleMeters">
					          	<span class="title">  Old Dismantle Meters</span></a></li>	
		
						        <li id="consumerConsumptionanalysis" hidden="true"><a href="./consumerConsumptionanalysis">
						        <span class="title">Consumer Consumption Analysis Report</span></a></li>
						        
						        <li class="" id="consMonthReadingReport" hidden="true"> <a href="./consMonthReadingReport"> 
								<span class="title">Consumer Monthly Reading Report</span></a></li>
								
								 <li class="" id="consumerReadingSummRepId" hidden="true"><a href="./consumerReadingSummRep"> 
								<span class="title">Consumer Reading Summary Report</span></a></li>
							
								<li id="sanloadvsmd" hidden="true"><a href="./sanloadVSmd"> 
								<span class="title"> Sanload vs MD</span></a></li> -->
									
							<%-- 	<c:if test = "${userType eq 'ADMIN'}"> 
								<li class="" id="boundaryDetailsReport" hidden="true"><a href="./boundaryDetailsReport">
									 <span class="title">RFM Report</span></a></li>
								</c:if> --%>
							 
								<!-- <li class="" id="reliabilityIndicesFeeder" hidden="true"><a href="./reliabilityIndicesFeeder"> 
									<span class="title">Reliability Indices (Feeder)</span></a></li> -->
									
								
									<!-- <li class="" id="multipleMtrDtRpt" ><a href="./multipleMeterDTreport"> 
									<span class="title">Multiple Meter DT Report </span></a></li>	 -->
									
								<!-- <li class="" id="multipleMtrDtRpt" ><a href="./multipleMeterDTreport"> 
									<span class="title">Multiple Meter DT Report </span></a></li>	 -->
							  
								<!-- <li class="" id="individualdtconsumptionid"><a href="./individualdtConsumption">
								    <span class="title">Individual DT Consumption Report</span></a></li> -->
							<!-- 	<li class="" id="saifiSaidiLi"><a href="./saifiSaidi">
								    <span class="title">SAIFI/SAIDI</span></a></li> -->
								    
						
						</ul>
					</li>
	 
	 			<li class=" " id="dtWiseReport" ><a href="#" id="dtWiseReport">
						 <i class="icofont-data"></i><span class="title"> DT- DATA ANALYSIS</span></a>
									
										<ul class="sub-menu">
											<li class="" id="dtHealthReport" hidden="true"><a href="./dtHealthReport">DT Health Report</a></li>								 
											<li class="" id="reliabilityIndicesDT" hidden="true"><a href="./reliabilityIndicesDT">DT- Reliability Indices</a></li>				
											<li class="" id="dtLoadingSummary" hidden="true"><a href="./dtLoading">DT Loading Summary</a></li>		
											<li class="" id="dtconsumptionid" hidden="true"><a href="./dtConsumption">Multi Meter- DT Consumption</a></li>
											<!--  <li class="" id="dtdashboardReports" hidden="true"><a href="./dtdashboardReports">ADMIN Reports</a></li> -->
											 <li class="" id="dtdashboardReportsForCirclewise" hidden="true"><a href="dtdashboardDetailedReport">DT DashBoard Reports</a></li> 
										  	 <li class="" id="dtpowerfailureForCirclewise" hidden="true"><a href="./dtpowerfailureReport">DT-PowerONOFF Report</a></li>
											 <li class="" id="dtcommForCirclewise" hidden="true"><a href="./dtcommReport">DT-Communication Report</a></li>
											 <li class="" id="showloadphaseID" hidden="true"><a href="./showloadphaseReport">DT-PhaseVoltageMissingReport</a></li>	

											    
										</ul>
										
								</li>
									
									
					<!-- --------------------------------------------DATA AVAILABILITY REPORT----------------------------------------------------- -->
					
					
					<li class=" " id="dataAvailReport" ><a href="#" id="dataAvailReport">
						 <i class="icofont-folder"></i><span class="title"> DATA AVAILABILITY</span></a>
									
										<ul class="sub-menu">	
											 <li class="" id="dataAvailability"><a href="./dataAvailabilityReport">DT-DataAvailability </a></li>	
											  <li class="" id="loadataAvailability"><a href="./loadataAvailabilityReport">DT-LoadDataAvailability</a></li>
											  <li class="" id="instdataAvailability" hidden="true"><a href="./instdataAvailabilityReport">DT-InstantaneousDataAvailability</a></li>
											    
										</ul>		
					</li>
					
					
					
									
	 <!-- 	------------------------------------------------------------EVENT ANALYSIS----------------------------------------------- -->

	
		<!-- <li class=" " id="eventAnalysis" ><a href="#" id="eventAnalysis">
						 <i class="icofont-data"></i><span class="title">Event-Missing</span></a>
									
										<ul class="sub-menu">
											<li class="" id="showloadphaseID" hidden="true"><a href="./showloadphaseReport">DT-PhaseMissingReport</a></li>								 
										
										</ul>
										
		</li> -->
	 <!-- ---------------------Energy Accounting Menu Starts here------------------ -->
					
				<li class="" id="eaId"><a href="#"><span class="title"><i class="icofont-calculator"></i>
					ENERGY ACCOUNTING</span></a>
							
							<ul class="sub-menu">
					
					
									<li class="" id="energyAccountingTownLevel" hidden="true"><a href="./energyAccountingTownLevel"> 
								     <i class="icofont-data"></i><span class="title"> Town Level- Energy Accounting</span></a></li>
						
									<li class="" id="energyAccountingFeederLevel" hidden="true"><a href="./feederWiseEAreport"> 
										<i class="icofont-data"></i><span class="title"> Feeder Level- Energy Accounting</span></a>
										
										
									<!-- 	<ul class="sub-menu">	
								
												<li class="" id="subdivisionEAreport"><a href="./eneryAccoutingDataSubdivisionWise"> 
									  				<span class="title">Subdivision EA	Report </span></a>
							
												<li class="" id="feederWiseEAreport"><a href="./feederWiseEAreport"> 
												<span class="title"> Feeder EA Report </span></a>
										
										</ul> -->
									</li>
									
										
								 	
								 	 <li class="" id="energyAccountingDTLevel" hidden="true"><a href="./energyAccountingDTLevel"> 
								    	 <i class="icofont-data"></i><span class="title"> DT Level- Energy Accounting</span></a></li>
								    	 
								    	 <li class="" id="dtlosses" hidden="true"><a href="./dtlosses"> 
								    	 <i class="icofont-data"></i><span class="title"> DT Energy Loss (LT)</span></a></li>
								    	 
								    	 <li class="" id="fdrlosses" ><a href="./fdrlosses"> 
								    	 <i class="icofont-data"></i><span class="title"> Feeder Level Transmission Loss</span></a></li>
								     
								      <li class="" id="energyAccountingMultiple" hidden="true"><a href="./energyAccountingMultiple"> 
								     	<i class="icofont-data"></i><span class="title">  EA Month Wise (AT&C) </span></a></li>
								     	
								  <li class="" id="townenergyAccounting" hidden="true"><a href="./townenergyAccounting"> 
								     	<i class="icofont-data"></i><span class="title">  EA Town Level (AT&C) </span></a></li>
								     
								   	 <li class="" id="fdrandDTmntlyConsumptionId" hidden="true"><a href="./fdrandDTmntlyConsumption"> 
								     	<i class="icofont-data"></i><span class="title"> Feeder/DT Monthly Consumption</span></a></li>
								     	
								     <li class="" id="fdrEneryUpdateIdd"><a href="./fdrEneryUpdateIdd"> 
								     	<i class="icofont-data"></i><span class="title"> Feeder Energy Updation </span></a></li>
								     	
								     	<li class="" id="lossAnalysis" hidden="true"><a href="./lossAnalysis">
												 <i class="icofont-data"></i> <span class="title"> Loss Analysis</span></a></li>	
								     
								     <li class="" id="lossCalculator" hidden="true"><a href="./lossCalculator"> 
										<i class="icofont-calculator"></i><span class="title"> AT&C Loss Calculator</span></a></li> 
										
										 <li class="" id="substationEnergyBalanceId" hidden="true"><a href="./substationEnergyBalance">
												<i class="fa fa-plug"></i> <span class="title">Bus-Bar Loss (Sub-Station)</span></a></li>
												
									<li class="" id="htlosses" hidden="true"><a href="./htloss">
												<i class="fa fa-plug"></i> <span class="title">Feeder Energy Loss (HT)</span></a></li>
								     
								     <li class=" " id="eaWiseReport" ><a href="#" id="eaWiseReport">
										 <i class="icofont-data"></i><span class="title"> EA Reports - PFC / NPPs</span></a>
									
										<ul class="sub-menu">
													
												
										 	 <li class="" id="PFCreportD1" hidden="true"><a href="./PFCreportD1">
										 		 <span class="title"> PFC Report-D1</span></a></li>
								
											 <li class="" id="pfcReportD2Id" hidden="true"><a href="./pfcReportD2">
											 <span class="title"> PFC Report-D2</span></a></li>
											       
											 <li class="" id="pfcReportD3" hidden="true"><a href="./pfcReportD3">
												 <span class="title"> PFC Report-D3</span></a></li>	
											
								      		  <li class="" id="PFCreportD4" hidden="true"><a href="./PFCreportD4"> 
									         	<span class="title"> PFC Report-D4</span></a></li>
													
											 <li class="" id="pfcReportD5" hidden="true"><a href="./pfcReportD5">
												 <span class="title"> PFC Report-D5</span></a></li>	
											       
											 <li class="" id="fdrrpt" hidden="true"><a href="./feedrcommstatusrpt"> 
												 <span class="title"> PFC Report-D6</span></a></li>  
											
											 <li class="" id="pfcd7report" hidden="true"><a href="./pfcd7report">
												 <span class="title"> PFC Report-D7</span></a></li>	
												 
											 <li class="" id="NPPReport" hidden="true"><a href="./NPPReport"> 
							     				 <span class="title"> NPP - JSON - Consumer Report</span></a></li>		
							     	 
									     	 <li class="" id="NPPReportFeeder" hidden="true"><a href="./nppFeederDataReport"> 
										     	 <span class="title"> NPP - JSON - Feeder Report</span></a></li>
											 
											  
									  
										</ul>
										
									</li>
								     
								   
							</ul>
							
						</li>
						
						
	
			<!---------------------------------Administration Menu --------------------------- -->
	
			<li class=" " id="ADMINSideBarContents" ><a href=""> <i class="icofont-user-suited"></i>
				<span class="title"> ADMINISTRATION</span></a>
		
				<ul class="sub-menu">
		
		            <li class=" " id="userManagement" > <a href="./usersAndMrNames"
						id="userManagement"><i class="icofont-users-alt-5"></i><span class="title">
								 User Management</span></a>
					
						<ul class="sub-menu">
								<!-- <li id="buisnessRoleDetails" hidden="true"><a href="./buisnessRoleDetails"> Buisness Role Details</a></li> -->
							   <li id="accessTypes" hidden="true"><a href="./accessTypes">Role Privilege Details</a></li>
								<li id="jvvnlUsersId" hidden="true"><a href="./usersAndMrNames"> Add User</a></li>
							<!-- 	<li id="accessTypes"  hidden="true" ><a href="./accessTypes">User Type</a></li> -->
						</ul>
						
					</li>
					
					<!-- <li class=" " id="uploadStatusId"><a href="./uploadStatusTrack"
						id="cmriupload"><i class="icofont-upload"></i><span class="title">
								CMRI Upload Status</span></a></li> -->
						<li class=" " id="processScehdularId" hidden="true"><a href="./processScheduling"
						id="cmriupload" hidden="true"><i class="fa fa-clock-o"></i><span class="title">
								Process Scheduler</span></a></li>	
								
					
					 <li class=" " id="adminArea" > <a href="#"
						id="userManagement"><i class="fa fa-university"></i><span class="title">
								 Administrative Area</span></a>
					
						<ul class="sub-menu">
								<li id="adminAreaDetails" hidden="true"><a href="./hierarchyLevels"> Administrative Area Details</a></li>
								<!--  <li id="IntermediateDataSyn" ><a href="./IntermediateDataSyn"> Report Data Sync</a></li>  -->
							  
						</ul>
						
						<ul class="sub-menu">
								<li id="adminAreaDetails" hidden="true"><a href="./hierarchyLevels"> Administrative Area Details</a></li>
								 <li id="IntermediateDataSyn" ><a href="./IntermediateDataSyn"> Report Data Sync</a></li>
							  
						</ul>
						
						
					</li>
					
					<li id="emailgateway" hidden="true"><a href="./EmailGatewaySettings">
							<i class="fa fa-envelope-o"></i><span class="title">Email</span></a></li>
									
					<li id="smsgateway" hidden="true"> <a href="./SMSGatewaySettings">
							<i class="fa fa-comments-o"></i><span class="title">SMS	Gateway</span> </a></li>
					
					<li class="" id="querybuilder" hidden="true"><a href="./querybuilder"> 
							<i class="fa fa-edit"></i><span class="title">Query Builder </span></a></li>
		
							
					 <li class="" id="meterSyncReport" hidden="true"><a href="./meterSyncStatusReport">
							<i class="icofont-data"></i><span class="title">Meter Sync Report</span></a></li>
							
							<li class="" id="dtdashboardReports" hidden="true"><a href="./dtdashboardReports">
							<i class="icofont-data"></i><span class="title">ADMIN Reports</span></a></li>
					
				</ul>
			</li>

            

  			<!-- -----------------------Data Exchange Menu-------------------------- -->

			 <li class=" " id="ServiceMonitoringId"><a href=""> <i
					class="fa fa-book"></i> <span class="title"> SERVICE MONITORING</span></a>
		
				
							<ul class="sub-menu">
								<li id="ServiceStatusId" hidden="true"><a href="./serviceStatus"><span class="title">Service Status</span></a></li>
								<li id="ServiceExceptionsReportId" hidden="true"><a href="./serviceExceptionsreport"><span class="title">Service Exception Notification report</span></a></li>
								<li id="serviceExceptionId" hidden="true"><a href="./serviceException"><span class="title">Service Exception</span></a></li>
								<li id="serviceExceptionNotifSettingID" hidden="true"><a href="./serviceExceptionNotifSetting"><span class="title">Service Exceptions Notification Setting</span></a></li>
						    </ul>
					
			</li> 
			
			<!-- -----------------------Survey And Installation Menu-------------------------- -->	
			 <!-- <li class=" " id="SurveyAndInstallation" ><a href=""> <i
					class="fa fa-wrench"></i> <span class="title">Survey And Installation</span></a>
				<ul class="sub-menu">
					<li class=" " id="addSurveyorId" hidden="true"><a href="./surveyor" id="SurveyAndInstallation">
					 <i	class="fa fa-plus-circle"></i> 	<span class="title">Add Surveyor</span></a>
					</li>
		
					<li class=" " id="MeterAllocationID" hidden="true"><a href="./meterAllocation">
					 <i	class="fa fa-microchip"></i> 	<span class="title">Meter Allocation</span></a>
					</li>
					
					<li class=" " id="dtMeterReplacementRpt" hidden="true"><a href="./dtMeterReplacementRpt">
					 <i	class="icofont-data"></i> 	<span class="title"> Meter Replacement Report</span></a>
					</li>
					
			   </ul>
			</li>  -->
			
			<!-- -----------------------Meter Change & Installation Menu-------------------------- -->	
			
			 
			
			

	</ul>

	<c:forEach var="element" items="${str }">
		<script>
			var s = '${element}';
			$("#" + s).show();
		</script>
	</c:forEach>
	<!-- END SIDEBAR MENU -->
</div>
<!-- END SIDEBAR -->

<style>
ul.page-sidebar-menu>li.active>a {
	/* background: #0288D1 !important; */
	/* background: #e02222 !important; */
	border-top-color: transparent !important;
	color: #ffffff;
}

.page-content {
	margin-left: 230px;
	margin-top: 0px;
	min-height: 760px;
	padding: 25px 20px 20px 20px;
}
</style>