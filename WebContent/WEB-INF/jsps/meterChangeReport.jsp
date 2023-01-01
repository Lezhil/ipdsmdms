<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%@ page import = "java.util.ResourceBundle" %>
 <% ResourceBundle resource = ResourceBundle.getBundle("messages");
				 String header=resource.getString("pageheader");
				 String logopath=resource.getString("pageheaderlogo");
			%>
 
 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>


<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
<script>
$(".page-content").ready(
		function() {
			App.init();
			TableEditable.init();
			FormComponents.init();
			loadSearchAndFilter('sample_1');
			$('#MDMSideBarContents,#surveydetails,#meterChangeReport').addClass('start active ,selected');
			$('#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment').removeClass('start active ,selected');
		});
</script>

<script>
function printData()
{
   var divToPrint=document.getElementById("tableData");
  
   newWin= window.open("");
   newWin.document.write(divToPrint.outerHTML);
   newWin.print();
   newWin.close();

}
</script>
 
<script>
function validation() {
	/* $("#sample_1").empty(); */
	var accno=$("#accNo").val()
	var newMetrNo=$("#meterNo").val() ;
	var divis=$("#division").val();
	var subdivi=$("#parsubdiv").val() ;
	var circle=$("#circleId").val() ;
	

	 if(circle=='' && divis=='' && subdivi=='' &&  accno=='' && newMetrNo=='')
	{
	bootbox.alert("Select Any One Option");
    return false;
	    }
	 
	/*  if(divis=='')
	 	{
	 	bootbox.alert("Select Division");
	     return false;
		    }
	 if(subdivi=='')
	 	{
	 	bootbox.alert("Select Subdivision");
	     return false;
		    }	  
	
	 if(accno=='')
 	{
 	bootbox.alert("Enter Account Number");
     return false;
	    }
	 
	 if(newMetrNo=='')
 	{
 	bootbox.alert("Enter new meter number");
     return false;
	    } */

	
	/* 	var accno ='22041149';
		var newMetrNo = '34556'; */
		
		
var html1="";
$.ajax({
	
	/* url : './getMeterReplacementdata', */
	type:'GET',
	url:"./getMeterReplacementdata",
	
	data:{accno:accno,newMetrNo:newMetrNo},
	success:function(response)
	
	{
		 
	  $("#sample_1").show();
	  
	   for (var i = 0; i < response.length; i++) 
	 {
		  var res=response[i];
		 
		  html1+='<tr><td>'+res[0]+'</td>'+
			'<td>'+res[1]+'</td>'+
			'<td>'+res[2]+'</td>'+
			'<td>'+res[3]+'</td>'+
			'<td>'+res[4]+'</td>'+
			'<td>'+moment(res[5]).format("YYYY-MM-DD HH:mm:ss")+'</td>'+
			
			'<td><a class=""  onclick= "getMeterReport('+res[4]+','+res[1]+','+res[2]+')" data-target="#stack1" data-toggle="modal"  id="addData" >View Report </i></a></td></tr>'
		 
	}
	   $("#newMeterData").empty();
	   $("#newMeterData").html(html1);
	  
		loadSearchAndFilter('sample_1');
	},
	
	
  });
}
function getMeterReport(meterNo,kno,accno){
	var divis=$("#division").val();
	var subdivi=$("#parsubdiv").val() ;
	var circle=$("#circleId").val() ;
	
	
	var today = new Date();
	var dd = String(today.getDate()).padStart(2, '0');
	var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
	var yyyy = today.getFullYear();

	today = dd + '-' + mm + '-' + yyyy;
	$.ajax({
		
		/* url : './getMeterReplacementdata', */
		type:'GET',
		url:"./getMeterReportData",
		
		data:{meterNo:meterNo,
			accno:accno,
			kno:kno,
			divis:divis,
			subdivi:subdivi,
			circle:circle
			},
		success:function(response)
		
	{
			
		   for (var i = 0; i < response.length; i++) 
		 {
			  var res=response[i];
			
			  $("#knoData").html(res[4]);
			  
			  $("#accountNumberData").html(res[5]);
			  $("#consumerNameData").html(res[6]);
			  $("#consumerAddressData").html(res[7]);
			  $("#oldMeterData").html(res[9]);
			  $("#connectionTypeData").html(res[10]);
			  $("#LatitudeData").html(res[13]);
			  $("#LongitudeData").html(res[14]);
			  $("#typeOfPremisesData").html(res[15]);
			  $("#stickerNumberData").html(res[16]);
			  $("#landMarkData").html(res[17]);
			  $("#meterChangeDateData").html(res[24]);
			  $("#oldMeterMakeData").html(res[26]);
			  $("#newCummkwhReadingData").html(res[30]);
			  $("#newMeterNoData").html(res[32]);
			  $("#newMeterMakeData").html(res[33]);
			  $("#overAllMFData").html(res[35]);
			  $("#newCummkVaheReadingData").html(res[40]);
			  $("#oldCummkVahReadingData").html(res[44]);
			  $("#newTenderNumberData").html(res[45]);
			  
			  $("#MCRnoDATA").html(res[0]+'_' +res[4]+'_' +res[32] );
			  $("#dateData").html(today);
			  $("#zoneData").html(res[0]);
			  $("#circleData").html(res[0]);
			  $("#divisionData").html(res[2]);
			  $("#subDivisionData").html(res[3]);
			  
			/*   $("#premisisPhotographData").html("base64,"+res[44]); */
			  
		
			  
		}
		
	}
		
		
		
	  });
	
}


function showDivision(circle)
{
	
	
	$.ajax({
		url:'./showDivision'+'/'+circle,
		type:'GET',
		success:function(response)
		{
			var html = '';
			html += "<option value=''></option>";
			for (var i = 0; i < response.length; i++) {
				html += "<option  value='"+response[i]+"'>" + response[i]
						+ "</option>";
			}

			$("#division").html(html);
			$('#division').select2();
		}
	});
}

function showSubdivision(division)
{
	$.ajax({
		     url:'./showSubdivision'+'/'+division,
		     type:'GET',
		     success:function(response)
		     {
		    	 var html = '';
					html += "<option value=''></option>";
					for (var i = 0; i < response.length; i++) {
						html += "<option  value='"+response[i]+"'>" + response[i]
								+ "</option>";
					}

					$("#parsubdiv").html(html);
					$('#parsubdiv').select2();
		     }
	});
}
</script>

	

<div class="page-content">
               <div class="portlet box blue">
                   <div class="portlet-title">
                   <div class="caption">
                   <i class="fa fa-bars"></i>Meter Change Report
                   </div>
                   </div>
                <div class="portlet-body">
                 
                
				
               
              <table style="border-collapse: separate; border-spacing: 8px;"> 
               
               <tr>
                <td style="width:100px;"><b>Select Circle:</b>
               <select class="form-control select2me input-large" onchange="showDivision(this.value)" name="circleName" id="circleId">
               <option value="">Select Circle</option>
               <c:forEach var="elements" items="${circleList}">
						<option value="${elements}">${elements}</option>
						</c:forEach> 
               </select>
               </td>
               
              <td style="width:100px;"><b>Select Division:</b>
               <select class="form-control select2me input-large" id="division" name="division" onchange="showSubdivision(this.value);">
               <option value="">Select Division</option>
              
               </select>
               </td>
				   <td style="width:100px;"><b>Select Sub-Division:</b>
              		 <select class= "form-control select2me input-large" id="parsubdiv"  placeholder="select Subdivision" name="ParentSubdivision" required="required" >
               <option value="">Select Sub-Division</option>
              
               </select>
               </td>
			   </tr>
               </br>
               <tr>
               
                <td style="width:100px;"><b>Account No</b>
             <input  type="text" class="form-control" placeholder="Enter Acc No"  id="accNo" name="accNo" ;"/>
               </td>
		       
		       
                <td style="width:100px;"><b>New Meter No</b>
             <input  type="text" class="form-control" id="meterNo" placeholder="Enter new Meter no" name="meterNo" ;"/>
               </td>
		       
		        
		        
               </tr>
               
               
               </table>
               <div class="row"><br>
               <div class="col-md-4">
                <button class="btn blue pull-left" id="viewrpt" type="button" onclick="validation()">View Report</button>
                <!-- <a href="#" id="excelExport" class="btn green pull-right" type="button"  onclick="tableToExcel('sample_1', 'Feeder Count')">Export to Excel</a> -->
               </div>
               </div>
               
               </div>
              
               </div>
               
           
               
           
              
              <div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 10px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> --> 
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Feeder Count Details')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
              
			   
               <table class="table table-striped table-hover table-bordered" id="sample_1">
               <thead>
			   <tr>
						
						<th>Sub-Division</th>
						
						<th>Account Number</th>
						<th>K Number</th>
						<th>Consumer Name</th>
						<!-- <th>Santioned Load</th>
						
						<th>Supply Voltage</th>
						<th>Category</th>
						<th>Service</th>
						<th>Load Unit</th>
						
						<th>Service Status</th> -->
						<th>New Meter Sr.No</th>
						<th>Meter Replacement Date</th>
						<th>Replacement Report</th>
					
					</tr>
			    </thead>
			    	  <tbody id="newMeterData">
					 
					 </tbody> 
               </table>
               
               </div>	
               
               <div id="stack1" class="modal fade" role="dialog" aria-labelledby="myModalLabe220" aria-hidden="true">
					   <div class="modal-dialog">
					   <div class="modal-content">
					   <div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">Close</button>
						</button><button onclick="printData()">Print this page</button>
						</button><button onclick="printData()">Save Page</button>
						<%-- <h4 class="modal-title" align="center"><span style="font-weight:bold"><%=header %></span></h3>
						<h4 class="modal-title" align="center">Meter Change Report</h2> --%>
						
						</div>
						<div class="modal-body">
                        <form action="./addSubstationdetails" id="addSubstationdetailsId"> 
                       	<!-- <table class="table table-striped table-hover table-bordered" id="sample_1">
								 <thead>
										<tr >
											<th colspan="4">Consumer Particulars</th>
										</tr>
										<tr>
											<th>K Number</th>
											<th></th>
											<th>Account Number</th>
											<th></th>
										</tr>
										<tr>
											<th >Consumer Name</th>
										</tr>
										<tr>
											<th >Consumer Address</th>
										</tr>
									</thead>
									<tbody id="loadSummary">
										
									</tbody>
								</table> -->
                       
                        <table class="" cellpadding="2px" cellspacing="0px" border="1" id="tableData">
                        <thead>
                        <h4 class="modal-title" align="center"><span style="font-weight:bold"><%=header %></span></h4>
						<h4 class="modal-title" align="center">Meter Change Report</h4>
               
			  <tr>
				  <tr>
			    <th class="tg-s268">MCR No</th>
			    <th class="tg-s268" id="MCRnoDATA" ></th>
			    <th class="tg-s268">Date</th>
			    <th class="tg-0lax" id="dateData" ></th>
			  </tr>
			  <tr>
			    <td class="tg-0lax" >Zone</td>
			    <td class="tg-0lax" id="zoneData" ></td>
			    <td class="tg-0lax">Circle</td>
			    <td class="tg-0lax" id="circleData" ></td>
			  </tr>
			  <tr>
			    <td class="tg-0lax">Division</td>
			    <td class="tg-0lax" id="divisionData"></td>
			    <td class="tg-0lax">Subdivision</td>
			    <td class="tg-0lax" id="subDivisionData"></td>
			  </tr>
			   
			  <!--  </table> 
                       
                    
                   <table class="table table-striped table-hover table-bordered " border="1"  id= "tableData"> -->
              <%--      <tr><a href="#" class="companyName"><img src="<%=logopath%>" alt="" /><span class="title" style="color: white;"><%=header %></span></a></tr> --%>
                   
  
  <tr>
    <th class="tg-0lax" colspan="4"><span style="font-weight:bold"> Consumer Particulars </span></th>
  </tr>
  <tr>
    <td class="tg-vask">Kno</td>
    <td class="tg-0lax" id ="knoData"></td>
    <td class="tg-0lax">Account Number</td>
    <td class="tg-0lax" id = "accountNumberData"></td>
  </tr>
  <tr>
    <td class="tg-0lax">Consumer Name</td>
    <td class="tg-0lax" colspan="3" id = "consumerNameData"></td>
  </tr>
  <tr>
    <td class="tg-0lax">Consumer Address </td>
    <td class="tg-0lax" colspan="3" id = "consumerAddressData"></td>
  </tr>
  <tr>
    <td class="tg-0lax">Land Mark</td>
    <td class="tg-0lax" colspan="3" id= "landMarkData"></td>
  </tr>
  <tr>
    <td class="tg-0lax">Connection Type</td>
    <td class="tg-0lax" id ="connectionTypeData"></td>
    <td class="tg-0lax">Type of Premises</td>
    <td class="tg-0lax" id ="typeOfPremisesData"></td>
  </tr>
  <tr>
    <td class="tg-0lax">DTC Connected</td>
    <td class="tg-0lax" id ="DTCconnectedData"></td>
    <td class="tg-0lax">Sticker Number</td>
    <td class="tg-0lax" id ="stickerNumberData"></td>
  </tr>
  <tr>
    <td class="tg-ltad" rowspan="3">Premise Photograph</td>
    <td class="tg-0lax" colspan="3" rowspan="3" id= "premisisPhotographData" >
  
    </td>
  </tr>
  <tr>
  </tr>
  <tr>
  </tr>
  <tr>
    <td class="tg-0lax" colspan="4"><span style="font-weight:bold"> Geo-Coordinates     </span></td>
  </tr>
  <tr>
    <td class="tg-0lax">Latitude</td>
    <td class="tg-0lax" id ="LatitudeData"></td>
    <td class="tg-0lax">Longitude</td>
    <td class="tg-0lax" id ="LongitudeData"></td>
  </tr>
  <tr>
    <td class="tg-0lax" colspan="4"><span style="font-weight:bold">Details Of Meters</span></td>
  </tr>
  <tr>
    <td class="tg-0lax" colspan="2">Particulars</td>
    <td class="tg-0lax">Old Meters</td>
    <td class="tg-0lax">New Smart Meters</td>
  </tr>
  <tr>
    <td class="tg-0lax" colspan="2"  cellpadding="2px" >Meter Sr Number</td>
    <td class="tg-0lax" id= "oldMeterData"></td>
    <td class="tg-0lax" id= "newMeterNoData"></td>
  </tr>
  <tr>
    <td class="tg-0lax" colspan="2">Meter Make</td>
    <td class="tg-0lax" id= "oldMeterMakeData"></td>
    <td class="tg-0lax" id= "newMeterMakeData"></td>
  </tr>
  <tr>
    <td class="tg-0lax" colspan="2" style="width:100px;">Tender Number</td>
    <td class="tg-0lax" id= "oldTenderNumberData"></td>
    <td class="tg-0lax" id= "newTenderNumberData"></td>
  </tr>
  <tr>
    <td class="tg-0lax" colspan="2">Accuracy class</td>
    <td class="tg-0lax" id= "oldAccuracyClassData"></td>
    <td class="tg-0lax" id= "newAccuracyClassData"></td>
  </tr>
  <tr>
    <td class="tg-0lax" colspan="2">Current Rating</td>
    <td class="tg-0lax" id= "oldACurrentRatingData"></td>
    <td class="tg-0lax" id= "newACurrentRatingData"></td>
  </tr>
  <tr>
    <td class="tg-0lax" colspan="2">Voltage Rating</td>
    <td class="tg-0lax" id= "oldVoltageRatingData"></td>
    <td class="tg-0lax" id= "newVoltageRatingData"></td>
  </tr>
  <tr>
    <td class="tg-0lax" colspan="2">Pulse Constant</td>
    <td class="tg-0lax" id= "oldPulseConstantData"></td>
    <td class="tg-0lax" id= "newPulseConstantData"></td>
  </tr>
  <tr>
    <td class="tg-0lax" colspan="2">Cumm kWh Reading</td>
    <td class="tg-0lax" id= "oldCummkwhReadingData"></td>
    <td class="tg-0lax" id= "newCummkwhReadingData"></td>
  </tr>
  <tr>
    <td class="tg-0lax" colspan="2">Cumm kVah Reading</td>
    <td class="tg-0lax" id= "oldCummkVahReadingData"></td>
    <td class="tg-0lax" id= "newCummkVaheReadingData"></td>
  </tr>
  <tr>
    <td class="tg-0lax" colspan="2">kVa -MDI</td>
    <td class="tg-0lax" id= "oldCummkVaMDIReadingData"></td>
    <td class="tg-0lax" id= "newCummkVaMDIReadingData"></td>
  </tr>
  <tr>
    <td class="tg-0lax" colspan="2">Meter Change Date </td>
    <td class="tg-0lax" colspan="2" id= "meterChangeDateData"></td>
  </tr>
  <tr>
    <td class="tg-0lax" colspan="2">Over All MF</td>
    <td class="tg-0lax" colspan="2" id= "overAllMFData"></td>
  </tr>
  <tr>
    <td class="tg-0lax" rowspan="4">Meter Photograph</td>
    <td class="tg-0lax" colspan="3" rowspan="4" id= "meterPhotographData"></td>
  </tr>
  <tr>
  </tr>
  <tr>
  </tr>
  <tr>
  </tr>
  
</table>      
                         
                        
                        
                        
                         							  
                           <!-- <table id="" class="table table-striped table-hover table-bordered ">
                           <thead>
                          <tr>
                     
                            <tr id="mcrNo">
												    <td>MCR NO</td>
													<td><input type="hidden" id="mcrNodata"  readonly="readonly"   class="form-control placeholder-no-fix"  name="subdivcode" maxlength="200" ></input>
											        </td>
						   </tr>
						    
						     		 			
						   <tr id="zone">
													<td>Zone</td>
													<td><input type="text" id="zonedata" value = "JAIPUR" class="form-control placeholder-no-fix" readonly="readonly"  name="SubstationName" maxlength="200" ></input>
											        </td>
						  </tr>
										
						  		
													
						  <tr id="circle">
													<td>Circle</td>
													<td><input type="text" id="circledata"   class="form-control placeholder-no-fix"  name="Substationcapacity" maxlength="6"/></input>
													</td>
					 	  </tr>
							<tr id="division">
														
														
														<td>Division</td>
														<td><input type="text"  id="divisiondata" class="form-control placeholder-no-fix" 
														name="TPSubstationCode" maxlength="12"></input>
														
														</td>
																
													</tr>
							<tr id="subDivision">
														
														
														<td>Sub-Division</td>
														<td><input type="text"   id="subdivisionData"  class="form-control placeholder-no-fix"  
														name="TPParentCode"  maxlength="12"></input>
														onchange="parentsubdiv()"
														</td>
																
													</tr>
						    <tr id="kno">
						    
						                               <td>K Number</td>
						                              <td><input type="text"   id="knodata"  class="form-control placeholder-no-fix"  
														name="TPParentCode"  maxlength="12"></input>
														onchange="parentsubdiv()"
														</td>
						                               
						                            </tr>						
							<tr id="accNo">
							
							                            <td>Acc No</td>
							                            <td><input type="text"   id="accnoData"  class="form-control placeholder-no-fix" 
														name="TPParentCode"  maxlength="12"></input>
														onchange="parentsubdiv()"
														</td>
							                       
							                        </tr>    
						                          						
							<tr id="consumName">
														
														
														<td>Consumer Name</td>
														<td><input type="text"   id="consumrNameData"  class="form-control placeholder-no-fix"  
														name="TPParentCode"  maxlength="12"></input>
														onchange="parentsubdiv()"
														</td>
													</tr>
						   <tr id="consumAddress">
														
														<td>Consumer Address</td>
														<td><input type="text"   id="consumAddressdata"  class="form-control placeholder-no-fix"  
														name="TPParentCode"  maxlength="12"></input>
														onchange="parentsubdiv()"
														</td>
													</tr>		
						  <tr id="landmark">
													<td>land Mark</td>
														<td><input type="text"   id="landmarkData"  class="form-control placeholder-no-fix"  
														name="TPParentCode"  maxlength="12"></input>
														onchange="parentsubdiv()"
														</td>
																
													</tr>
					  <tr id="latitude">
													<td>Latitude</td>
														<td><input type="text"   id="latitudeData"  class="form-control placeholder-no-fix"  
														name="TPParentCode"  maxlength="12"></input>
														onchange="parentsubdiv()"
														</td>
																
													</tr>
						  <tr id="logitude">
													<td>Longitude</td>
														<td><input type="text"   id="logitudeData"  class="form-control placeholder-no-fix"  
														name="TPParentCode"  maxlength="12"></input>
														onchange="parentsubdiv()"
														</td>
																
													</tr>
						  <tr id="oldMeterNo">
													<td>Old Meter No</td>
														<td><input type="text"   id="oldMeterNoData"  class="form-control placeholder-no-fix"  
														name="TPParentCode"  maxlength="12"></input>
														onchange="parentsubdiv()"
														</td>
																
													</tr>
					  <tr id="newMeterNo">
													<td>New Meter No</td>
														<td><input type="text"   id="newMeterNoData"  class="form-control placeholder-no-fix"  
														name="TPParentCode"  maxlength="12"></input>
														onchange="parentsubdiv()"
														</td>
																
													</tr>
					 <tr id="metrChangDate">
													<td>Meter Change Date</td>
														<td><input type="text"   id="meterChangeDateData"  class="form-control placeholder-no-fix"  
														name="TPParentCode"  maxlength="12"></input>
														onchange="parentsubdiv()"
														</td>
																
													</tr>
						                               							
		                   </thead>																																									
                           </table> -->
                          
                           </form>
                           </div>
					       </div>
					       </div>
					       </div>
               