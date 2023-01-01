<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						$("#selectedDateId").val(getPresentMonthDate('${selectedMonth}'));
						App.init();
						TableManaged.init();
						FormComponents.init();
						$(
								'#MDMSideBarContents,#showVariationOfEnergyID,#mdmDashBoardId')
								.addClass('start active ,selected');
						$(
								"#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
								.removeClass('start active ,selected');
						if ('${size}' === '10') {

							$('#consumptionDiv').show();
							var circleVal = '${circles}';
							document.getElementById("circle").value = circleVal;
							// getDivisionList(circleVal);
							showDivision(circleVal);
						}
						$('#circle').select2();

						/* 	 if('${dateList.size()}'>0)
						{
						  $('#d4ListId' ).show();
						}
						else
						{
						   $('#d4ListId' ).hide();
						} */

					});

	/* function getDivisionList(circle)
	{
	 
	  $.ajax({
	    	url:'./getAllDivisonBasedOnCircle'+'/'+circle,
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response)
	    	{
	    		
	    		var substation='';
	    		 substation+="<select id='division' name='division' class='form-control input-medium' type='text'   onchange='getSubDivisionList(this.value)'><option value='select' selected='selected' >Select Division</option>";
				for( var i=0;i<response.length;i++){
					substation+="<option  value='"+response[i][0].trim()+"'>"+response[i][0]+"</option>";
				}
				substation+="</select><span></span>";
				
				$("#divisionTd").html(substation);
				if('${division}'!='')
	  			{
	  			document.getElementById("division").value='${division}';
	  			getSubDivisionList('${division}');
	  			}
				$('#division').select2();
	    	}
	  }); 
	  
	}
	function getSubDivisionList(division)
	{
	  $.ajax({
	    	url:'./getAllSubDivisonBasedOnDivision'+'/'+division,
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response)
	    	{
	    		
	    		var subDivision='';
	    		subDivision+="<select id='subDivision' name='subDivision' class='form-control input-medium' type='text'  onchange='callshowId()'><option value='select' selected='selected' >Select SubDivision</option>";
				for( var i=0;i<response.length;i++){
					
					subDivision+="<option  value='"+response[i][0]+"'>"+response[i][0]+"</option>";
				}
				subDivision+="</select><span></span>";
				
				$("#subDivisionTd").html(subDivision);
				if('${sdoCode}'!='')
	  			{
					document.getElementById("subDivision").value='${sdoCode}';
	  			}
				$('#subDivision').select2();
	    	}
	  }); 
	} */

	function showDivision(circle) {
		var zone = '%';

		$
				.ajax({
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
						html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select Division</option><option value='%'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option  value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#divisionTd").html(html);
						if ('${division}' != '') {
							document.getElementById("division").value = '${division}';
							showSubdivByDiv('${division}');
						}
						$('#division').select2();
					}
				});
	}
	function showSubdivByDiv(division) {

		var zone = '%';
		var circle = $('#circle').val();
		$
				.ajax({
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
						html += "<select id='subDivision' name='subDivision'  class='form-control input-medium' type='text' onchange='callshowId()'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
						for (var i = 0; i < response1.length; i++) {
							//var response=response1[i];
							html += "<option  value='"+response1[i]+"'>"
									+ response1[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#subDivisionTd").html(html);
						if ('${sdoCode}' != '') {
							document.getElementById("subDivision").value = '${sdoCode}';
						}
						$('#subDivision').select2();
					}
				});
	}

	function callshowId() {
		$('#downId').show();
		$('#incId').show();
	}
</script>
<div class="page-content">

	<div>
		<form method="post" action="./showVariationOfEnergy">
			<table>

				<tr>
					<td>Select Month</td>
					<td>
						<div class="input-group input-medium date date-picker"
							data-date-format="yyyymm" data-date-viewmode="years">
							<input type="text" required="required" class="form-control"
								readonly="readonly" id="selectedDateId" name="month"> <span
								class="input-group-btn">
								<button class="btn default" type="button">
									<i class="fa fa-calendar"></i>
								</button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</span>
						</div>
					</td>
					<td><select id="circle" class="form-control input-medium"
						name="circle" onchange="showDivision(this.value)">
							<option value="select">Select Circle</option>
							<c:forEach var="element" items="${circles}">
								<option value="${element}">${element}</option>
							</c:forEach>
					</select></td>
					<td id="divisionTd"></td>
					<td id="subDivisionTd"></td>
				</tr>
				<tr>
					<td id="incId" hidden="true"><button
							class="btn purple pull-left" value="increased" name="category">Increased</button>
					</td>
					<td id="downId" hidden="true"><button
							class="btn blue pull-left" value="downfall" name="category">Downfall</button>
					</td>
				</tr>
			</table>

		</form>
	</div>
	<br />
	<br />
	<div class="portlet box green" style="display: none"
		id="consumptionDiv">
		<div class="portlet-title line">
			<div class="caption">
				<i class="fa fa-globe"></i>${gridData} for the month&nbsp;&nbsp;
				<fmt:parseDate value="${selectedMonth}" pattern="yyyyMM"
					var="yearMonth" />
				<fmt:formatDate value="${yearMonth}" pattern="yyyy-MMM" />
				&nbsp;&nbsp;&nbsp;Circle : ${circle}&nbsp;&nbsp;&nbsp; Division :
				${division}&nbsp;&nbsp;&nbsp;SubDivision : ${sdoCode}
			</div>
			<div class="tools">
				<a href="" class="collapse"></a> <a href="" class="remove"></a>
			</div>
		</div>

		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="btn-group">

					<br /> <br />
				</div>
				<div class="btn-group pull-right">
					<button class="btn dropdown-toggle" data-toggle="dropdown">
						Tools <i class="fa fa-angle-down"></i>
					</button>
					<ul class="dropdown-menu pull-right">
						<li><a href="#" id="print">Print</a></li>
						<li><a href="#" id="excelExport"
							onclick="tableToExcel('sample_2', '${gridData} Consumption Details')">Export
								to Excel</a></li>
					</ul>
				</div>
			</div>
			<!--BEGIN TABS-->
			<div class="tabbable tabbable-custom">



				<table class="table table-striped table-bordered table-hover"
					id="sample_2">
					<thead>
						<tr>
							<th rowspan="2">AccNo.</th>
							<th rowspan="2">Name</th>
							<th rowspan="2">Address</th>
							<th rowspan="2">MeterNo</th>
							<th rowspan="2">Industry Type</th>
							<th rowspan="2">Sanction Load</th>
							<th rowspan="2">UnitsKWH</th>
							<!-- <th rowspan="2">SixMonthAvg</th>
										<th rowspan="2">TwelveMonthAvg</th> -->
							<th colspan="2" align="center">Variation Of Energy(in %)</th>
						</tr>
						<tr>
							<th>SixMonth</th>
							<th>TwelveMonth</th>
						</tr>
					</thead>
					<tbody>

						<%-- <c:forEach var="element" items="${VariationOfEnergyList}"> --%>
						<tr>

							<td>
								<%-- <a href="#"  onclick="myValidate('${element[0]}','${selectedMonth}')"> --%>17090059<!-- </a> -->
							</td>

							<td>M/S DEEPAK MACHIENRY MART</td>
							<td>JAIPUR</td>
							<td>8000003</td>
							<td>SMALL SCALE</td>

							<td>228</td>
							<td>228</td>
							<td>28</td>
							<td>23</td>

						</tr>
						<%-- </c:forEach> --%>

					</tbody>
					<tbody>

						<%-- <c:forEach var="element" items="${VariationOfEnergyList}"> --%>
						<tr>

							<td>
								<%-- <a href="#"  onclick="myValidate('${element[0]}','${selectedMonth}')"> --%>17401009<!-- </a> -->
							</td>

							<td>PANDIT PARADISE</td>
							<td>JAIPUR</td>
							<td>8000004</td>
							<td>SMALL SCALE</td>

							<td>212</td>
							<td>112</td>
							<td>12</td>
							<td>33</td>

						</tr>
						<%-- </c:forEach> --%>

					</tbody>
					<tbody>

						<%-- <c:forEach var="element" items="${VariationOfEnergyList}"> --%>
						<tr>

							<td>
								<%-- <a href="#"  onclick="myValidate('${element[0]}','${selectedMonth}')"> --%>17090059<!-- </a> -->
							</td>

							<td>NAVAL INTER PRIZEZ</td>
							<td>JAIPUR</td>
							<td>GS5500133</td>
							<td>SMALL SCALE</td>

							<td>330</td>
							<td>228</td>
							<td>64</td>
							<td>45</td>

						</tr>
						<%-- </c:forEach> --%>

					</tbody>
				</table>

			</div>
		</div>



	</div>
	<!-- <div id="stack11" class="" tabindex="-1" data-width="500" data-backdrop="static" data-keyboard="false">
					   <div class="modal-dialog" style="width: 100%">
					      <div class="modal-content">
					      <div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
								<h4 class="modal-title"> Seal Issued To Meter Reader</h4>
										</div>
					         <div class="modal-body">
					        
					            <div class="row">
					              
					              <div class="col-md-12"> -->
	<%-- <div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Search</div>
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
											
						<div class="portlet-body">
								<form:form action="./searchMeterMasterToVarEnergy"  method="post" id="searchMeterMaster" modelAttribute="metermasterSearch" commanName="metermasterSearch">
								
									<div class="form-body">
										<div class="form-group">
										<table>
										
										<tr><td>Date</td>
										
											<td><div class="input-group input-medium date date-picker"  data-date-format="yyyymm" data-date-viewmode="years" id="five">
																<form:input path="rdngmonth" type="text" class="form-control" name="searchDate" id="searchDate" ></form:input>
																<span class="input-group-btn">
																<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></td>
								       <td>Account No.</td>
								       <td><form:input path="accno" name="accno" id="accno" class="form-control" value="${accno}"></form:input>
								       
								       <!-- <span id="accnocheck" style="color:red;font-weight:bold;"></span> -->
								       </td>
										
										
										
										  <tr>
										  <td>Meter No</td>
									      <td><form:input path="metrno" type="text" class="form-control" name="metrno" id="metrno" value="${metrno}" maxlength="12"></form:input>
									      <span id="meternocheck" style="color:red;font-weight:bold;"></span>
									      </td>
										  <td>New Seal No</td>
									      <td><form:input path="newseal" type="text" class="form-control" name="newseal" id="newseal" value="${newseal}" maxlength="12"></form:input>
									     <!--  <span id="meternocheck" style="color:red;font-weight:bold;"></span> -->
									      </td>
										
										  </tr>
											
										      <tr><td>Name&nbsp;&nbsp;:</td>
										      <td>${masterdata.name}</td>
										      <td>MTRTYPE&nbsp;&nbsp;:</td>
										      <td>${mtrtype}</td>
										      
										      </tr>
										
									          <tr><td >Address&nbsp;&nbsp;:</td> 
						                      <td>${masterdata.address1}</td>
						                      <td>KWORHP&nbsp;&nbsp;:</td>
										      <td>${masterdata.kworhp}</td>
						                      
						                      </tr>
						                     
						                      <tr><td>CD&nbsp;&nbsp;:</td>
										      <td>${masterdata.contractdemand}</td>
										      <td>SANLOAD&nbsp;&nbsp;:</td>
										      <td>${masterdata.sanload}</td>
										      
										      </tr>
						                      
						                      <tr><td>Status&nbsp;&nbsp;:</td>
						                       <td>${masterdata.consumerstatus}</td>
						                       
						                       <td>MRNAME&nbsp;&nbsp;:</td>
										      <td>${masterdata.mrname}</td>
						                      </tr>
						          
						                       <tr><td>MF&nbsp;&nbsp;:</td>
						                       <td>${data}</td>
						                       
						                       <td>TN&nbsp;&nbsp;:</td>
										      <td>${masterdata.tn}</td>
						                       
						                      </tr>
						                      
						                      <tr><td>MTR Make&nbsp;&nbsp;:</td>
						                       <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${mtrmake}</td>
						                        <td>SUPPLYVOLTAGE&nbsp;&nbsp;:</td>
										      <td>${masterdata.supplyvoltage}</td>
						                       
						                      </tr>
						                      
						                
						                     <tr><td>Meter Make</td>
						                         <c:forEach var='meter' items='${meterMasterData}'>
						                       <td><c:out value="${meter.mtrmake}"></c:out></td></c:forEach>
						                      </tr>
						      
						                      <tr><td>TarrifCode&nbsp;&nbsp;:</td>
						                       <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${masterdata.tariffcode}</td>
						                       
						                       <td>TADESC&nbsp;&nbsp;:</td>
										      <td>${masterdata.tadesc}</td>
						                      </tr>
						                      
						                      
						                      <tr><td>INDUSTRYTYPE&nbsp;&nbsp;:</td>
						                       <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${masterdata.industrytype}</td>
						                       
						                       <td>SUPPLYTYPE&nbsp;&nbsp;:</td>
										      <td>${masterdata.supplytype}</td>
						                      </tr>
						                      
						                      
						                       <tr><td>OLDACCNO&nbsp;&nbsp;:</td>
						                       <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${masterdata.oldaccno}</td>
						                       
						                       <td>MNP&nbsp;&nbsp;:</td>
										      <td>${masterdata.mnp}</td>
						                      </tr>
						                       <tr><td>Mobile No &nbsp;&nbsp;:</td>
						                       <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${masterdata.phoneno }</td>
						                       
						                       <td>Phone No.&nbsp;&nbsp;</td>
										      <td>${masterdata.phoneno2 }</td>
						                      </tr>
						                      
						                      
						                     
						                      </table>
										
													
										</div>																																	
													
										
										
							              </div>
									
								</form:form>
									
								
							
						</div>
					</div> --%>
	<!-- <div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li id="print"><a href="#">Print</a></li>										
										<li><a href="#" onclick="tableToExcel('allValuetable','Export Meter Data');">Export to Excel</a></li>
										
									</ul>
								</div> -->
	<%-- <BR><BR>
					
					<table class="table table-striped table-bordered table-hover" id="sample_3" id="allValuetable">
									<thead>									
										<tr>								
												<th hidden="true">#</th>
											    <th>READING MONTH</th>
												<th>MCST</th>
												<th>METERNO</th>
												<th>MRNAME</th>
												<th>CURRENT KWH</th>
												
												<th>CURRENTKVAH</th>
												<th>CURRENT KVA</th>
												<th>DEMAND TYPE</th>
											    <th>PF</th>
											    <th>XCURRDNGKWH</th>
											     <th>XCURRDNGKVAH</th>
											    <th>XCURRDNGKVA</th> 
											   
												<th>READING REMARK</th>
												<th>REMARK</th>
												<th>READINGDATE</th>
												<th>OLDSEAL</th>
												<th>NEWSEAL</th>
											    <th>UNITSKWH</th>
												<th>XPF</th>
												<th>MRDSTATUS</th>
												<th>XMLDATE</th>
												<th>DNAME</th>
												
												
	            						</tr>
									</thead>
									<tbody>
																		
									<c:forEach var='meter' items='${meterMasterData}'>
										 	<tr id="sampel" class="odd gradeX">	
										 	<td hidden="true"></td>								 		
											 	<td><c:out value="${meter.rdngmonth}"/></td>
											 	<td><c:out value="${meter.mcst}"/></td>
											    <td><c:out value="${meter.metrno}"/></td>
											    <td><c:out value="${meter.mrname}"/></td>	
											    <td><c:out value="${meter.currdngkwh}"/></td>
											     <td><c:out value="${meter.currrdngkvah}"/></td>
											    <td><c:out value="${meter.currdngkva}"/></td>
											    <td><c:out value="${meter.demandType}"/></td>
											    <td><c:out value="${meter.pf}"/></td>
											    <td><c:out value="${meter.xcurrdngkwh}"/></td>
											    <td><c:out value="${meter.xcurrrdngkvah}"/></td>
											    <td><c:out value="${meter.xcurrdngkva}"/></td>
											    
											    <td><c:out value="${meter.readingremark}"/></td>
											    <td><c:out value="${meter.remark}"/></td>
											    <td><fmt:formatDate pattern="dd-MMM-yy" value="${meter.readingdate}" /></td>
											    <td><c:out value="${meter.readingdate}"/></td>
											    <td><c:out value="${meter.oldseal}"/></td>
											    <td><c:out value="${meter.newseal}"/></td>
											    <td><c:out value="${meter.unitskwh}"/></td>
											    <td><c:out value="${meter.xpf}"/></td>
											    <td><c:out value="${meter.mrdstatus}"/></td>
											    <td><fmt:formatDate pattern="dd-MMM-yy" value="${meter.xmldate}" /></td>
											    <td><c:out value="${meter.xmldate}"/></td>
											    <td><c:out value="${meter.dname}"/></td>
											     
											     
											 	
											 	
										 	</tr>
										 </c:forEach>								 									 
												
									</tbody>
								</table>  	 
									 
					     <!--          </div>
					           </div>
					       </div>
					   </div>
					  </div>
					</div> -->	
							
							
					
</div> --%>
	<%-- <div id="stack123" hidden="true">
<form:form action="./searchMeterMaster"  method="post" id="searchMeterMaster" modelAttribute="metermasterSearch" commanName="metermasterSearch">
<div class="input-group input-medium date date-picker"  data-date-format="yyyymm" data-date-viewmode="years" id="five">
																<form:input path="rdngmonth" type="text" class="form-control" name="searchDate" id="searchDate" ></form:input>
																<span class="input-group-btn">
																<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span>
																</div>
																<form:input path="accno" name="accno" id="accno" class="form-control" value="${accno}"></form:input>
</form:form>

</div> --%>



	<script>
		function myValidate(accno, selectedDate) {
			$('#searchDate').val(selectedDate);
			$('#accno').val(accno);
			$('#searchMeterMaster').submit();
		}
	</script>