<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script> -->
<script src="<c:url value='/resources/assets/scripts/sweetalert.min.js' />" type="text/javascript"></script>
<script  type="text/javascript">
$(".page-content").ready(function()
    	   {  
		
	App.init();
	TableEditable.init();
	FormComponents.init();
	//loadSearchAndFilter('sample_1');
	loadSearchAndFilter('sample_2');
    $('#MDMSideBarContents,#metermang,#meterReplacementId').addClass('start active ,selected');
 	$("#MDASSideBarContents,#MDMSideBarContents#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
 	getMtrChangeData();
	});
</script>

<div class="page-content">
<div class="row">
		<div class="col-md-12">
			
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Meter Replacement 
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				
				<div class="btn-group">
								<button class="btn green" data-target="#stack1" data-toggle="modal"  id="addData" value="addSingle" >
									      Meter Change 
								 </button>
									    
								         </div>
								
								         
								         
                   <div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> --> 
								<!-- <li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Feeder Details')">Export
										to Excel</a></li> -->
							</ul>
						</div>
					</div>
					
					<table class="table table-striped table-hover table-bordered" id="sample_2">
						<thead>
							<tr>
								
								<th>OldMeterNo</th>
								<th>NewMeterNo</th>
								<th>Accno</th>
								<th>ConsumerName</th>
								<th>OldMeter Last_Instaneous-Date</th>
								<th>OldMeter Last_Instaneous(KWh)</th>
								<th>OldMeter Last_Load Date</th>
								<th>OldMeter Load_Load(KWh)</th>
								<th>NewMeter Initial_Reading(KWh)</th>
								<th>Meter_Change_Date</th>
								<th>Entry By</th>
								<th>Entry Date</th>
								
							</tr>
						</thead>
							<tbody id="meterDetailsId" >
									
								<%-- <c:forEach var="element" items="${meterDetails}">
									<tr >
										<td><a href="#" onclick="editMeterDetails(this.id,${element.id})" id="editData${element.id}">Edit</a></td>
										<td>${element.meterno}</td>
										<td>${element.meter_connection_type}</td>
										<td>${element.meter_make}</td>
										<td>${element.meter_commisioning}</td>
										<td>${element.meter_model}</td>
										<td>${element.meter_ip_period}</td>
										<td>${element.pt_ratio}</td>
										<td>${element.meter_constant}</td>
										<td>${element.ct_ratio}</td>
										<td>${element.tender_no}</td>
										<td>${element.meter_accuracy_class}</td>
										<td>${element.month}</td>
										<td>${element.meter_current_rating}</td>
										<td>${element.warrenty_years}</td>
										<td>${element.meter_voltage_rating}</td>
										<td>${element.meter_status}</td>
										
									</tr>
									</c:forEach> --%>
									 							
								</tbody>
						
					</table>
					
					</div>
					</div>
					</div>
					</div>
					</div>
					
					
					
					<div id="stack1" class="modal fade" role="dialog"  aria-labelledby="myModalLabel10" >
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<h4 class="modal-title"><span id="addMeterStackId" style="color: #474627;font-weight: bold;">Meter Replacement</span></h4>
										</div>
										<span style="color:red;font-size: 16px;"  id="accountNotExistMsg" ></span>
										<div class="modal-body">
						 	               <form action=""  method="post" id="addMeterDetailsFormId" > 		  
													<table style="width: 25%">
						<tbody>
							<tr>
							<label>Please Enter Accno:</label></tr><tr><td>
								<td><input class="form-control input-medium" placeholder="XXXXXXX" name="meterNumId" autocomplete="off" id="meterNumId" /></td>

								<td><button type="button" id="dataview" class="btn yellow"
										onclick="return getMeterData();" formmethod="post">
										<b>View</b>
									</button></td>
							</tr>
						</tbody>
					</table>
					<br>
					<br>
													
													<div class="row" id="mtrSlnoDivId">
													<div class="col-md-6" ><label>Meterno</label><span style="color: red"></span>
													<input type=text placeholder=XXXX name="meternoId" id="meternoId" class=form-control  value="" readonly=readonly />
													</div>
													<div class="col-md-6"><label>Accno</label><span style="color: red"></span>
													<input type=text placeholder=XXXX name="accnoId" id="accnoId" class=form-control  value='' readonly=readonly />
													</div>
													</div>
													
													<br>
													
													<div class="row"  >
													<div class="col-md-6" id="mtrPrfxDivId" ><label>Last Communication(Date)</label><span style="color: red"></span>
													<input type=text name="lastCommDateId" class=form-control  id="lastCommDateId" value='' readonly=readonly  />
													</div>
													<div class="col-md-6" id="mtrdivId"><label>Last Instataneous(KWh)</label><span style="color: red"></span>
													<input type="text" placeholder="XXXX" name="lastInstaDataId" id="lastInstaDataId" class=form-control  value="" readonly=readonly />
													</div>
													</div>
													
													<br>
													
													<div class="row"  >
													<div class="col-md-6" id="mtrdivId"><label>last LoadSurvey(Date)</label><span style="color: red"></span>
													<input type="text" name="lastLoadDateId" class=form-control id="lastLoadDateId"   value='' readonly=readonly />
													</div>
													
													<div class="col-md-6" id="mtrdivId"><label>last LoadSurvey(KWh)</label><span style="color: red"></span>
													<input type="text" placeholder="XXXX" name="lastLoadId" id="lastLoadId" class=form-control value="" readonly=readonly />
													</div>
													</div>
												
													<br>
													
													<div class="row">
													<div class="col-md-6" id="mtrdivId"><label>Consumer Name</label><span style="color: red" ></span>
													<input  type="text" name="consumerNameId" id="consumerNameId" class="form-control" placeholder="XXXXXXX" readonly="readonly" />
													</div>
													<div class="col-md-6" id="mtrPrfxDivId" ><label>New MeterNo</label><span style="color: red"></span>
													<input type=text name="newmeternoId"   id="newmeternoId" class=form-control  value=''  onchange="return checkMeternoExist(this.value)" />
													</div>
													</div>
													
													<br>
													
													<div class="row"  >
													<div class="col-md-6" id="mtrPrfxDivId" ><label>Initial Reading(KWh)</label><span style="color: red"></span>
													<input type=text name="initialReadingId"   id="initialReadingId" class=form-control  value=''   />
													</div>
													<div class="col-md-6"><label>Meter Change Date</label><span style="color: red"></span>
													<div class="input-group input-medium date date-picker" data-date-format="dd-mm-yyyy " data-date-viewmode="years">
													<input  type="text" class="form-control" name="mtrChangeDateId" id="mtrChangeDateId"  ></input>
													<span class="input-group-btn"><button class="btn default" type="button"><i class="fa fa-calendar"></i></button></span></div>
													</div>
													</div>
													
													<br>
													
													<!-- <div class="row"  >
													<div class="col-md-6" id="mtrdivId"><label>ConsumerName</label><span style="color: red"></span>
													<input type="text" name="lastLoadDateId" class=form-control id="lastLoadDateId"   value='' readonly=readonly />
													</div>
													
													<div class="col-md-6" id="mtrdivId"><label>ConsumerName</label><span style="color: red"></span>
													<input type="text" placeholder="XXXX" name="meterNumId1" id="lastLoadId" class=form-control value="" readonly=readonly />
													</div>
													</div> -->
													
													<div class="modal-footer">
														<button type="button" id="dataview33" class="btn blue"onclick="return getMeterData33();" formmethod="post"><b>SUBMIT</b></button>  
													</div>	
										</form>
										</div>
										</div>
										</div>
										</div>
										
					
					<script>
function getMeterData()
{
	var meterno= $("#meterNumId").val();
	//$("#addMeterDetailsFormId").clear();
	$('#addMeterDetailsFormId').trigger("reset");
	$("#meterNumId").val(meterno);
	//meterno="22040295";
	$.ajax(
			{
					type : "GET",
					url : "./getMeterDataForReplace/" + meterno,
					dataType : "json",
					success : function(response)
					{
						if(response.length>0)
						{/* 
							var html="";
							var conType="";
							for(var i=0;i<response.length;i++){
								var data=response[i];
					var last_communicated_date="";
								if(data[3]!=null)
				    			{
									last_communicated_date=moment(data[3]).format('YYYY-MM-DD hh:mm:ss');
				    			}
					var last_load_date="";
								if(data[5]!=null)
				    			{
									last_load_date=moment(data[5]).format('YYYY-MM-DD hh:mm:ss');
				    			}
									
								var datepickMtrChange='<div class="input-group input-medium date date-picker" data-date-format="dd-mm-yyyy " data-date-viewmode="years" id="datepickerId" onclick="getDate()">'
									+'<input  type="text" class="form-control" name="mtrChangeDateId" id="mtrChangeDateId"  />'
									+'<span class="input-group-btn"><button class="btn default" type="button"><i class="fa fa-calendar"></i></button></span></div>';

								var datepickIntial='<div class="input-group input-medium date date-picker" data-date-format="dd-mm-yyyy " data-date-viewmode="years" id="datepickerId2" >'
										+'<input  type="text" class="form-control" name="intialReadingId" id="intialReadingId"  />'
										+'<span class="input-group-btn"><button class="btn default" type="button"><i class="fa fa-calendar"></i></button></span></div>';
	 							
							var btn='<button type="button" id="dataview33" class="btn yellow"onclick="return getMeterData33();" formmethod="post"><b>SUBMIT</b></button>'
							var data5='<input type="text"    name="mtrVltgRatId" id="mtrVltgRatId" class="form-control" >';

								 html+="<tr>"
										+" <td><input type=text placeholder=XXXX name=meternoId id=meternoId class=form-control style=width:180px value="+(data[0]==null?"":data[0])+" readonly=readonly /></td>" 
										+" <td><input type=text placeholder=XXXX name=accnoId id=accnoId class=form-control style=width:180px value='"+(data[1]==null?"":data[1])+"' readonly=readonly /></td>" 
										+" <td><input type=text placeholder=XXXX name=consumerNameId id=consumerNameId class=form-control style=width:180px value='"+(data[2]==null?"":data[2])+"' readonly=readonly /></td>" 
										+" <td><input type=text name=lastCommDateId class=form-control style=width:180px  id=lastCommDateId value='"+(last_communicated_date)+"' readonly=readonly  /></td>" 
										+" <td><input type=text placeholder=XXXX name=lastInstaDataId id=lastInstaDataId class=form-control style=width:180px value="+(data[4]==null?"":data[4])+" readonly=readonly /></td>" 
										+" <td><input type=text name=lastLoadDateId class=form-control id=lastLoadDateId style=width:180px  value='"+last_load_date+"' readonly=readonly /></td>" //mtrtype 
										+" <td><input type=text placeholder=XXXX name=meterNumId1 id=lastLoadId class=form-control style=width:180px value="+(data[6]==null?"":data[6])+" readonly=readonly /></td>" //mtrtype
										+" <td><input type=text placeholder=XXXX name=meterNumId3 id=newmeternoId class=form-control style=width:180px /></td>" 
										+" <td><input type=text placeholder=XXXX name=meterNumId2 id=initialReadingId class=form-control  style=width:180px /></td>" 
										+" <td>"+datepickMtrChange+"</td>" 
										+" <td>"+btn+"</td>" 
										+" </tr>"; 
								}
							$("#meterDetailsId").html(html);
						 */
						
							
						 for(var i=0;i<response.length;i++)
							 {
								var data=response[i];
								var last_communicated_date="";
								var last_load_date="";
								
								if(data[3]!=null)
				    			{
									last_communicated_date=moment(data[3]).format('YYYY-MM-DD hh:mm:ss');
				    			}
								
								if(data[5]!=null)
				    			{
									last_load_date=moment(data[5]).format('YYYY-MM-DD hh:mm:ss');
				    			}
								$("#meternoId").val(data[0]==null?"":data[0]);
								$("#accnoId").val(data[1]==null?"":data[1]);
								$("#consumerNameId").val(data[2]==null?"":data[2]);
								$("#lastCommDateId").val(last_communicated_date);
								$("#lastInstaDataId").val(data[4]==null?"":data[4]);
								$("#lastLoadDateId").val(last_load_date);
								$("#lastLoadId").val(data[6]==null?"":data[6]);
								$("#newmeternoId").val();
								$("#initialReadingId").val();
								$("#mtrChangeDateId").val();

							 }
						}
						else {
							
						bootbox.alert("Data Not Found for Entered Accno");
						return false;
							}
					}
			});
}



function getDate()
{
	$(document).ready(function(){
		 $('#datepickerId').datepicker({
			 type:  'datetime'
			// format: "DD/MM/YYYY HH24:mm",
		  /* "format": "mm-dd-yy HH:mm:ss",
		  "startDate": "-5d",
		  "endDate": "09-15-2017", */
		 }); 
		 
		});
	}


function getMeterData33()
{
	
	var oldmeterno=$("#meternoId").val();
	var accno=$("#accnoId").val();
	var consumerName=$("#consumerNameId").val();
	var lastCommDate=$("#lastCommDateId").val();
	var lastInstaData=$("#lastInstaDataId").val();
	var lastLoadDate=$("#lastLoadDateId").val();
	var lastLoad=$("#lastLoadId").val();
	var newmeterno=$("#newmeternoId").val();
	var initialReading=$("#initialReadingId").val();
	var mtrChangeDate=$("#mtrChangeDateId").val();

	if(newmeterno=="")
	{
	bootbox.alert("Please Enter New Meterno");
	return false();	
	}
	if(newmeterno.length<5)
	{
	bootbox.alert("New Meterno should be 6 character legnth");
	
	$("#newmeternoId").val();
	return false();	
	}
	if(initialReading=="")
	{
	bootbox.alert("Please Enter New Meter Initial Reading");
	return false();	
	}
	if(mtrChangeDate==""||mtrChangeDate==0)
	{
	bootbox.alert("Please Select MeterChangeDate");
	return false();	
	}
	
	/* alert("1-"+oldmeterno+" 2-"+accno+" 3-"+consumerName);
	alert("lastCommDate-"+lastCommDate+" lastInstaData-"+lastInstaData+" lastLoadDate-"+lastLoadDate);
	alert("lastLoad-"+lastLoad+" newmeterno-"+newmeterno+" initialReading-"+initialReading+" mtrChangeDate-"+mtrChangeDate);
	 */
	$.ajax(
			{
					type : "GET",
					url : "./replaceMeterNo",
					data:{oldmeterno:oldmeterno,accno:accno,consumerName:consumerName,
						  lastCommDate:lastCommDate,lastInstaData:lastInstaData,lastLoadDate:lastLoadDate,
						  lastLoadDate:lastLoadDate,lastLoad:lastLoad,newmeterno:newmeterno,initialReading:initialReading,
						  mtrChangeDate:mtrChangeDate},
					dataType : "text",
					success : function(response)
					{
						bootbox.alert(response);
						getMtrChangeData();
						$("#meterDetailsId").empty();
						$('#stack1').modal('hide');
					}
			});
	}

function getMtrChangeData()
{
	$.ajax(
			{
					type : "GET",
					url : "./getAllMeterChangeData",
					dataType : "json",
					success : function(response)
					{
						if(response.length>0)
						{
							var html="";
							for(var i=0;i<response.length;i++){
								var data=response[i];
								var lastinstataneousdate="";
								var entryby="";
								var lastloaddate="";
								var mtrchangedate="";
								if(data.lastinstataneousdate!=null){
								lastinstataneousdate=moment(data.lastinstataneousdate).format('YYYY-MM-DD hh:mm:ss');
								}
								if(data.lastloaddate!=null){
									 lastloaddate=moment(data.lastloaddate).format('YYYY-MM-DD hh:mm:ss');
									}
								if(data.entrydate!=null){
									 entrydate=moment(data.entrydate).format('YYYY-MM-DD hh:mm:ss');
									}
								if(data.mtrchangedate!=null){
									mtrchangedate=moment(data.mtrchangedate).format('YYYY-MM-DD');
									}
								html+="<tr>";          
								html+="<td>"+(data.oldmeterno==null?"":data.oldmeterno)+"</td>";
								html+="<td>"+(data.newmeterno==null?"":data.newmeterno)+"</td>";
								html+="<td>"+(data.accno==null?"":data.accno)+"</td>";
								html+="<td>"+(data.consumername==null?"":data.consumername)+"</td>";
								html+="<td>"+(lastinstataneousdate==null?"":lastinstataneousdate)+"</td>";
								html+="<td>"+(data.lastinstataneouskwh==null?"":data.lastinstataneouskwh)+"</td>";
								
								html+="<td>"+(lastloaddate==null?"":lastloaddate)+"</td>";
								html+="<td>"+(data.lastloadkwh==null?"":data.lastloadkwh)+"</td>";
								html+="<td>"+(data.initialreading==null?"":data.initialreading)+"</td>";
								html+="<td>"+(mtrchangedate==null?"":mtrchangedate)+"</td>";
								html+="<td>"+(data.entryby==null?"":data.entryby)+"</td>";
								html+="<td>"+(entrydate==null?"":entrydate)+"</td>";
								html+="</tr>";
								}
							$("#meterDetailsId").html(html);
						}
						
					}
			});
}

function checkMeternoExist(newmeterno)
{
	if(newmeterno=="")
		{
		bootbox.alert("Please Enter New Meterno");
		return false;	
		}
	//alert(newmeterno.length);
	if(newmeterno.length<5)
	{
	bootbox.alert("New Meterno should be minimum 6 character legnth");
	$("#newmeternoId").val("");
	return false;	
	}
	
}


					</script>	