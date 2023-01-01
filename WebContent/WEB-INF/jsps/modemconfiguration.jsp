<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>

$(".page-content").ready(
		function() {
			$('#MDASSideBarContents,#modemMang,#modemconfig').addClass('start active ,selected');
   	    	$("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
			
		});
</script>
<script type="text/javascript">
var mtrNum;
function mtrDetails(mtrNo)
{
	mtrNum=mtrNo;
	window.location.href="./viewFeederMeterInfoMDAS?mtrno=" + mtrNo;
	 /* window.open("./viewFeederMeterInfo?mtrno="+ mtrNo,"_blank"); */
}
function eventDetails()
{
	alert(mtrNum);
	var mtrNo=mtrNum;
	$.ajax({
    	url : './getEventDataa/'+mtrNo,
    	type:'GET',
    	dataType:'json',
    	asynch:false,
    	cache:false,
    	success:function(data)
    	{
    		var html="";
   		 	for(var i=0;i<data.length; i++)
			{
				var resp=data[i];
				 html+="<tr>"
						+" <td>"+resp[0]+"</td>"
						+" <td>"+resp[1]+"</td>"
						+" <td>"+resp[2]+"</td>"
						+" <td>"+resp[3]+"</td>"
						+" <td>"+resp[4]+"</td>"
						+" <td>"+resp[5]+"</td>"
						+" <td>"+resp[6]+"</td>"
						+" <td>"+resp[7]+"</td>"
						+" <td>"+resp[8]+"</td>"
						+" <td>"+resp[9]+"</td>"
						+" <td>"+resp[10]+"</td>"
						+" <td>"+resp[11]+"</td>"
						+" <td>"+resp[12]+"</td>"
						+" </tr>";
	   		}
   		 $('#sample_2').dataTable().fnClearTable();
   		 	$('#eventTR').html(html);
    	},
		complete: function()
		{  
			loadSearchAndFilter('sample_2');
		}  
	  }); 
}
function instansDetails()
{
	var mtrNo=mtrNum;
	$.ajax({
    	url : './getInstansData/'+mtrNo,
    	type:'GET',
    	dataType:'json',
    	asynch:false,
    	cache:false,
    	success:function(data)
    	{
    		var html="";
   		 	for(var i=0;i<data.length; i++)
			{
				var resp=data[i];
				 html+="<tr>"
						+" <td>"+resp[0]+"</td>"
						+" <td>"+resp[1]+"</td>"
						+" <td>"+resp[2]+"</td>"
						+" <td>"+resp[3]+"</td>"
						+" </tr>";
	   		}
   		 $('#sample_3').dataTable().fnClearTable();
   		 	$('#instantsTR').html(html);
    	},
		complete: function()
		{  
			loadSearchAndFilter('sample_3');
		}  
	  }); 
}

function loadSurveyDetails()
{
	var mtrNo=mtrNum;
	$.ajax({
    	url : './getLoadSurveyData/'+mtrNo,
    	type:'GET',
    	dataType:'json',
    	asynch:false,
    	cache:false,
    	success:function(data)
    	{
    		var html="";
   		 	for(var i=0;i<data.length; i++)
			{
				var resp=data[i];
				 html+="<tr>"
						+" <td>"+resp[0]+"</td>"
						+" <td>"+resp[1]+"</td>"
						+" <td>"+resp[2]+"</td>"
						+" <td>"+resp[3]+"</td>"
						+" <td>"+resp[4]+"</td>"
						+" <td>"+resp[5]+"</td>"
						+" <td>"+resp[6]+"</td>"
						+" <td>"+resp[7]+"</td>"
						+" <td>"+resp[8]+"</td>"
						+" <td>"+resp[9]+"</td>"
						+" <td>"+resp[10]+"</td>"
						+" <td>"+resp[11]+"</td>"
						+" <td>"+resp[12]+"</td>"
						+" </tr>";
	   		}
   		 $('#sample_4').dataTable().fnClearTable();
   		 	$('#loadSurveyTR').html(html);
    	},
		complete: function()
		{  
			loadSearchAndFilter('sample_4');
		}  
	  }); 
}
</script>
<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<c:if test="${results ne 'notDisplay'}">
				<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${results}</span>
				</div>
			</c:if>

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Modem Configuration
					</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
					</div>
				</div>
				<div class="portlet-body">
				
					<div class="row" style="margin-left: -1px;">

						<table style="width: 38%">
							<tbody>
								<tr>
									<td id="circleTd"><select
										class="form-control select2me input-medium" id="circle"
										name="circle">
											 <option value="noVal">SELECT CIRCLE</option>
										<c:forEach items="${circleVal}" var="element">
											<option value="${element}">${element}</option>
										</c:forEach>	
									</select></td>

									<td id="divisionTd"><select
										class="form-control select2me input-medium" id="division"
										name="division">
											<option value="noVal">Select division</option>
									    <c:forEach var="elements" items="${divlist}">
									    	<option value="${elements}">${elements}</option>
									    </c:forEach>
									</select></td>

									<td id="subdivTd"><select
										class="form-control select2me input-medium" id="sdoCode"
										name="sdoCode">
											<option value="noVal">Select Subdiv</option>
									    <c:forEach var="elements" items="${subdivlist}">
									    	<option value="${elements}">${elements}</option>
									    </c:forEach>
									</select></td>

								</tr>
							</tbody>
						</table>
						<p>&nbsp;</p>
						<table class="table ">

						<tbody>

							<tr>
								<td
									style="font-size: 13px; width: 208px; height: 42px; padding-right: 0px;" onkeyup="return mtrno();">
									<lable style="font-size:18px;font-weight: oblique">Enter
									Meter No</lable> <input class="form-control" type="text" name="mtr"
									id="mtr" autocomplete="off" style="width: 206px;">

								</td>
								<td>
									<p>&nbsp</p>

									<button type="button"
										style="margin-top: -3px; margin-left: 10px;"
										data-dismiss="modal" class="btn blue"
										onclick="viewdata()">SUBMIT</button>
								</td>
								<!-- <td style="width: 100px"></td>
								<td
									style="font-size: 13px; width: 208px; height: 42px; padding-right: 0px;">
									<lable style="font-size:18px;font-weight: oblique">Enter
									Meter No</lable> <input class="form-control" type="text" name="rev_charge"
									id="mterno" autocomplete="off" style="width: 206px;">

								</td>
								<td>
									<p>&nbsp</p>

									<button type="button" style="margin-top: -3px;  margin-left: 10px;"
										data-dismiss="modal" class="btn blue"
										onclick="updateFdrTable()">SUBMIT</button>


								</td> -->
							</tr>
						</tbody>
					</table>
					<p>&nbsp;</p>	
					</div>
					<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Total Meters')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					<div class="table-scrollable">
					<table class="table table-striped table-hover table-bordered"  id="sample_1">
						<thead>
							<tr>
								<th>Consumer No.</th>
								<th>Meter Make</th>
								<th>Substation Name</th>
							</tr>
						</thead>
						<tbody id="updateMaster">
						</tbody>
					</table>
					</div>
					
					<div class="table-scrollable">
					<form:form action="./addmodemconfig"
										modelAttribute="addmodemconfig" commandName="addmodemconfig"
										method="post" id="addmodemconfig" enctype="multipart/form-data">
					<table class="table table-striped table-hover table-bordered"  id="sample_1">
						
						<tbody id="updateMaster">
						<tr>
						
						<td hidden="true"><form:input class="form-control select2me input-medium" id="dummymtr_no"
										name="mtr_no" path="mtr_no"/>
											
									</td>
									
								<td>Instant Data Poll Mode</td>
								<td><form:select
										class="form-control select2me input-medium" id="instant_data"
										name="instant_data" path="instant_data">
											 <option value="noVal">SELECT</option>
											 <option value="15mins">15mins</option>
											 <option value="30mins">30mins</option>
											 <option value="hourly">hourly</option>
											 <option value="daily">daily</option>
											 <option value="monthly">monthly</option>
									</form:select></td>
							</tr>
							<tr>
							<td>History Data Poll Mode</td>
								<td><form:select
										class="form-control select2me input-medium" id="history_data"
										name="history_data" path="history_data">
											 <option value="noVal">SELECT</option>
											 <option value="15mins">15mins</option>
											 <option value="30mins">30mins</option>
											 <option value="hourly">hourly</option>
											 <option value="daily">daily</option>
											 <option value="monthly">monthly</option>
									</form:select></td>
							</tr>
							<tr>
							<td>Load Survey Data Poll Mode</td>
								<td><form:select
										class="form-control select2me input-medium" id="loadsurveypoll"
										name="loadsurveypoll" path="loadsurveypoll">
											 <option value="noVal">SELECT</option>
											 <option value="15mins">15mins</option>
											 <option value="30mins">30mins</option>
											 <option value="hourly">hourly</option>
											 <option value="daily">daily</option>
											 <option value="monthly">monthly</option>
									</form:select></td>
							</tr>
							
							<tr>
							<td>Load Survey Data Days</td>
								<td><form:input name="loadsurveydays"
										class="form-control select2me input-medium" id="loadsurveydays"
										path="loadsurveydays"/>
											 
									</td>
							</tr>
							
							<tr>
							<td>Event Data Poll Mode</td>
								<td><form:select
										class="form-control select2me input-medium" id="event_data"
										name="event_data" path="event_data">
											 <option value="noVal">SELECT</option>
											 <option value="15mins">15mins</option>
											 <option value="30mins">30mins</option>
											 <option value="hourly">hourly</option>
											 <option value="daily">daily</option>
											 <option value="monthly">monthly</option>
									</form:select></td>
							</tr>
							
							<tr>
							<td>Mid night Data Poll Mode</td>
								<td><form:select
										class="form-control select2me input-medium" id="midnight"
										name="midnight" path="midnight">
											 <option value="noVal">SELECT</option>
											 <option value="15mins">15mins</option>
											 <option value="30mins">30mins</option>
											 <option value="hourly">hourly</option>
											 <option value="daily">daily</option>
											 <option value="monthly">monthly</option>
									</form:select></td>
							</tr>
							
							<tr>
							<td>Migrate MDM and DB</td>
								<td><form:select
										class="form-control select2me input-medium" id="migrate_mdm"
										name="migrate_mdm" path="migrate_mdm">
											 <option value="noVal">SELECT</option>
											 <option value="15mins">15mins</option>
											 <option value="30mins">30mins</option>
											 <option value="hourly">hourly</option>
											 <option v0alue="daily">daily</option>
											 <option value="monthly">monthly</option>
									</form:select></td>
							</tr>
							
							<tr>
							<td>Transactions Data Poll Mode</td>
								<td><form:select
										class="form-control select2me input-medium" id="transaction_data"
										name="transaction_data" path="transaction_data">
											 <option value="noVal">SELECT</option>
											 <option value="15mins">15mins</option>
											 <option value="30mins">30mins</option>
											 <option value="hourly">hourly</option>
											 <option value="daily">daily</option>
											 <option value="monthly">monthly</option>
									</form:select></td>
							</tr>
							
							<tr>
							<td>Retry Interval</td>
								<td><form:select
										class="form-control select2me input-medium" id="retryinterval"
										name="retryinterval" path="retryinterval">
											 <option value="noVal">SELECT</option>
											 <option value="15mins">15mins</option>
											 <option value="30mins">30mins</option>
											 <option value="hourly">hourly</option>
											 <option value="daily">daily</option>
											 <option value="monthly">monthly</option>
									</form:select></td>
							</tr>
							
							<tr>
							<td> No. of retry</td>
								<td><form:input
										class="form-control select2me input-medium" id="noofretry"
										name="noofretry" path="noofretry"/>
											
									</td>
							</tr>
						</tbody>
					</table>
					 <div class="modal-footer">															    
						&nbsp;&nbsp;<button class="btn blue pull-right" id="updateOption" >Save</button>		
						<button type="button" data-dismiss="modal" onclick="return myClearFunction1()" class="btn white">Clear</button>
													 
					 </div>	
					</form:form>					
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
function viewdata()
{
		var circle=$('#circle').val();
	    var subdivision=$('#sdoCode').val();
	    var division=$('#division').val();
	    var mtrno=$('#mtr').val();
	    
		$.ajax({
			type : "GET",
			url : "./getmodemdata/"+circle+"/"+subdivision+"/"+division+"/"+mtrno,
			dataType: "json",
			cache : false,
			async : false,
			success : function(response)
			{
				if(response.length==0)
				{
				  bootbox.alert('Data Not Available');
				}
				else
				{
					var html = "";
			    	
			    	for(var j=0;j<response.length;j++)
		    		{		 
			    		var expRptData=response[j];
			    		for(var i=0;i<expRptData.length;i++)
			    		{
				    		if(expRptData[i]==null)
			    			{
				    			expRptData[i]='';
			    			}
				    	}
			    		
			    		html += "<tr><td>"+expRptData[0]+"</td>"+
			    				"<td>"+expRptData[1]+"</td>"+
			    				"<td>"+expRptData[2]+"</td>"+
			    				"</tr>";
		    		}
			    	
			    	$("#updateMaster").html(html);
			    	//$('#excelPrintId').html("<a href=# id=excelExport onclick=tableToExcel('table_1','Export_Reading_Data_"+mrNameVal+"_"+mrNameVal1+"')/>");
			    	//document.getElementById("excelExport").click();
			    	loadSearchAndFilter('sample_1');
			    	
				}
			}
		});
		
	}
	
	function mtrno()
	{
		var mtr=$("#mtr").val();
		$("#dummymtr_no").val(mtr);
	}
	
	 function myClearFunction1()
	    {
	     	$("#instant_data").val("noVal");
	    	$("#history_data").val("noVal");
	    	$("#loadsurveypoll").val("noVal");
	    	$("#loadsurveydays").val("");
	    	$("#event_data").val("noVal");
	    	$("#midnight").val("noVal");
	    	$("#migrate_mdm").val("noVal");
	    	$("#transaction_data").val("noVal");
	    	$("#retryinterval").val("noVal");
	    	$("#noofretry").val("");
	   
	    }
</script>