  <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {     
   	    	     App.init();
   	    	  TableEditable.init();
		    	Tasks.initDashboardWidget();
		    	 FormComponents.init();
		    	 TableManaged.init();
		      	 $('#MDASSideBarContents,#other-Reports,#otherReports').addClass('start active ,selected');
		    	//  $('#other-Reports').addClass('start active ,selected');
		    	//   $("#admin-location,#dash-board,#MIS-Reports").removeClass('start active ,selected');
		    	 
		    	 $("#htPendindPrint").click(function(){ 
	    	    	 printcontent($("#excelUpload .table-scrollable").html());
				});
		    	 
		    	 
   	    	 $("#print").click(function(){ 
	    	    	 printcontent($("#pending .table-scrollable").html());
				});
   	    	$("#print2").click(function(){ 
   	    	 printcontent($("#cmri .table-scrollable").html());
			});
   	    	$("#print3").click(function(){ 
   	    	 printcontent($("#manual .table-scrollable").html());
			});
   	    	$("#print4").click(function(){ 
      	    	 printcontent($("#pendingSummary").html());
   			});
   	    	$("#print5").click(function(){ 
     	    	 printcontent($("#HTPending").html());
  			});
   	    	$("#print6").click(function(){ 
     	    	 printcontent($("#comConsumList").html());
  			});
   	    	$("#print7").click(function(){ 
     	    	 printcontent($("#mtrchngList").html());
  			});
   	    	
   	    	  $("#reportsMonth").val(getPresentMonthDate('${selectedMonth}'));
   	     
	    	
   	    	
   	    	if('${conCompleteList.size()}'>0)
   	    	{
   	    	   loadSearchAndFilter('sample_4');
   	    	}   
   	    	if('${mtrChangeList.size()}'>0)
   	    	{
   	    		loadSearchAndFilter('sample_7');
   	    	}
   	    	
   	    	
   	    	/* $('#circle').change(function() { 
   	    	    var dropVal = $(this).val();
   	    	    sessionStorage.setItem("SelectedItem", dropVal);
   	    	});
   	    	
   	    	
   	     var selectedItem = sessionStorage.getItem("SelectedItem");  
   	    //alert(selectedItem);
	   	    if(selectedItem==null)
	   	    {
	   	    	//alert('if');
	   	    	$("#circle").val("0").trigger("change"); 	 
	   	    }else{
	   	    	//alert('else');
	   	    	$("#circle").val(selectedItem).trigger("change");
	   	    } */
 		 
   	    	
   	    	
   	    	
   	    	});
  
  function selectAll(source) {
		 
	   var flagChecked = 0;
		checkboxes = document.getElementsByName('sdoAccess');
		//alert(checkboxes.length)
		for(var i =0;i<checkboxes.length;i++)
			{
			checkboxes[i].checked = source.checked;
			if(checkboxes[i].checked)
			 {
				//alert("value : "+checkboxes[i]);
				flagChecked = 1;
			}
			}
		
		if(flagChecked==0)
			{
			$('#sdo span:first-child').attr("class", "");
			}
		else{
			$('#sdo span:first-child').attr("class", "checked");
		}
		
		
	}
  
  function validation()
  {
	  var flagChecked = 0;
	  var flagChecked1 = 0;
	  var sdo = "";
	  var levels = "";
		checkboxes = document.getElementsByName('sdoAccess');
		//alert(checkboxes.length);
		for(var i =0;i<checkboxes.length;i++)
			{
			if(checkboxes[i].checked)
			 {
				sdo = sdo + "'"+checkboxes[i].value + "',";
			//	alert("sdo==>"+sdo);
				//alert("value : "+checkboxes[i].value);
				flagChecked = 1;
			}
			}
		if(document.getElementById("mrName").value == "0")
			{

			if(flagChecked==0)
			{
				  bootbox.alert('PLease Select Atleast one SDO Code ');
				   return false;
			}else{
				sdo = sdo.substring(0,sdo.length-1);
				$("#sdoValue").val(sdo);
			}
			}else{
				if(flagChecked==0)
				{
					for(var i =0;i<checkboxes.length;i++)
					{
						checkboxes[i].checked = true;
					if(checkboxes[i].checked)
					 {
						sdo = sdo + "'"+checkboxes[i].value + "',";
						//alert("value : "+checkboxes[i].value);
						flagChecked = 1;
					}
					}
					sdo = sdo.substring(0,sdo.length-1);
					$("#sdoValue").val(sdo);
				}
				else{
					sdo = sdo.substring(0,sdo.length-1);
					$("#sdoValue").val(sdo);
				}
			}
		
		checkboxes = document.getElementsByName('levels');
	//	alert(checkboxes.length)
		for(var i =0;i<checkboxes.length;i++)
			{
			
			if(checkboxes[i].checked)
			 {
				levels = levels + checkboxes[i].value + ",";
				//alert("value : "+checkboxes[i]);
				flagChecked1 = 1;
			}
			}
		if(flagChecked1==0)
		{
			  bootbox.alert('PLease Select Atleast One level ');
			   return false;
		}
		levels = levels.substring(0,levels.length-1);
		
		$("#levelValue").val(levels);

		/* if(document.getElementById("SecondSdocodeId").value=="0")
		{
			  bootbox.alert('PLease Select sdoname ');
			   return false;
		} */

		
		if(document.getElementById("mrName").value == "0")
			{
			  $("#generateOtherReports").attr("action","./generateOtherReports");
			}else{
				$("#generateOtherReports").attr("action","./generateOtherReportsWithMR");
			}
		return true;
		
		
  }
  
  function validationPendingSummary(param)
  {
	  if(param == "1")
		  {
		  $("#generateOtherReports").attr("action","./pendingSummary");
		  }
	  else if(param == "2")
		  {
		  $("#generateOtherReports").attr("action","./newReportConnectionDetails");
		  }
	  
	  else if(param == "3")
	  {
		  var circle=$("#circle").val();
		//  alert(circle);
		  if((circle=='0') || (circle=="null"))
			  {
			  bootbox.alert("please select circle");
			  return false;
			  }
		  else{
			
	 		 $("#generateOtherReports").attr("action","./newReportHTPending");
	 		
		  }
	  }
	  
	  else if(param == "4")
	  {
	  $("#generateOtherReports").attr("action","./newHtpendingSummary");
	  }
	  
  }


/* function callUpdate()
{
	alert("call update");
	window.location.href = "./callUpdateMeterMaster";
} */
  
   	    	   </script>
 
<style>
.col-sm-1 {
    width: 10.3333%;
}
</style>	    	   
   	    	   
  <div class="page-content" >
  
    <div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Reading Reports
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				<div class="row" style="margin-left:1px;">
				
				
  <form action="" id="generateOtherReports">
   <div class="row">
  		<div class="form-body">
			<div class="form-group">
			<div class="portlet-body">		
			
			<table>

					<tr>
						
						<td >
							<div data-date-viewmode="years" data-date-format="yyyymm"
								class="input-group input-medium date date-picker">
								<input type="text" name="reportsMonth" id="reportsMonth"
									readonly="readonly" class="form-control" required="required">
								<span class="input-group-btn">
									<button type="button" class="btn default">
										<i class="fa fa-calendar"></i>
									</button> 
								</span>
							</div>
						</td>
						
						<!-- <td style="margin-top: 89px;padding-bottom: 100px;">SDO CODE</td> -->
						<%-- <td><input type="hidden" id="sdoValue" name="sdoValue">
							<input type="hidden" id="levelValue" name="levelValue">

							
								<input type="checkbox" id="selectall" onClick="selectAll(this)" />Select
								All SDO Code

								<div class="checkbox-list"
									style="height: 100px; overflow: auto; width: 240px;">
									<c:forEach var="sdoCode1" items="${sdoList}">
										<label id="sdo"> <input type="checkbox"
											id="${sdoCode1.id }" name="sdoAccess"
											value="${sdoCode1.sdoCode }"> ${sdoCode1.sdoCode }${sdoCode1.sdoName }
										</label>
									</c:forEach>
								</div>
							</td> --%>
					<!-- </tr>


					<tr> -->
						
						<td><select name="circle" class="form-control"
								id="circle" onchange="SdoByCircle();">
								<option value="0">Select Circle </option>
								 <c:forEach items="${circle}" var="element">
								 
									<option value="${element}">${element}</option>
								</c:forEach> 
								
							</select></td>
							<td >
							<select name="SecondSdocodeId" class="form-control medium"
								id="SecondSdocodeId"  onchange="getMrnamesBySDO(this.value);">
								<option value="0">Select SDONAME </option>
								</select>
								
					   		</td>
							
						<td>
								<select class="form-control medium" name="mrName" id="mrName">
									<option value="0">Select MRName</option>
									<%-- <c:forEach var="element" items="${mrnamrList}">
										<option value="${element}">${element}</option>
									</c:forEach> --%>
								</select>
							
						</td>
					</tr>
					<tr></tr>
					<tr>
					<td><input type="hidden" id="sdoValue" name="sdoValue">
							<input type="hidden" id="levelValue" name="levelValue">

							
							<%-- 	<input type="checkbox" id="selectall" onClick="selectAll(this)" />Select
								All SDO Code
								<div class="checkbox-list" style="height: 100px; overflow: auto; width:190px;">
									<c:forEach var="sdoCode1" items="${sdoList}">
										<label id="sdo"> <input type="checkbox"
											id="${sdoCode1.id }" name="sdoAccess"
											value="${sdoCode1.sdoName}"> ${sdoCode1.sdoCode}${sdoCode1.sdoName}
										</label>
									</c:forEach>
								</div> --%>
							</td>
							
							
							
					<!-- <td>LEVELS</td> -->
					   		<td>
					   		<label>LEVELS</label><div class="form-group">
						<div class="checkbox-list" style="height:150px;overflow:auto;width:220px;">
							
									<label > 
									<input type="checkbox" id="pending" name="levels" value="1">
									Pending
									</label>
									<label>
									<input type="checkbox" id="amr" name="levels" value="6">
									AMR
									</label>
									<label>
									<input type="checkbox" id="cmri" name="levels" value="2">
									CMRI
									</label>
									<label>
									<input type="checkbox" id="manual" name="levels" value="3">
									Manual
									</label>
									<label>
									<input type="checkbox" id="comConList" name="levels" value="4">
									Complete Consumers List
									</label>
									<label>
									<input type="checkbox" id="mtrChngList" name="levels" value="5">
									Meter Change List
									</label>
									
													</div>
												</div> 
					   		</td>
					   		</tr>
					   		
					</table>
					</div>
					<div align="left">
						<button  class="btn blue" id="htPending"
							onclick="return validationPendingSummary(3);">HT Pending
						</button>
						<button  class="btn green" id="newConnection"
							onclick="return validationPendingSummary(2);">New
							Connection</button>
					
						<button  class="btn green" id="htPendingSummary"
							onclick="return validationPendingSummary(4);">HT Pending
							Summary</button>
							
						<button class="btn green" id="showData"
							onclick="return validation();">Show</button>

						<button class="btn blue" id="showData"
							onclick="return validationPendingSummary(1)">Pending
							Summary</button>
							
							<!-- <button class="btn red" id="showData"
							onclick="return callUpdate()">UpdateMM</button> -->
					</div>

				</div>
					</div>
					</div>
					
					
					
   <%--  <div class="row">
 	
 	<input type="hidden" id="sdoValue" name="sdoValue">
 	<input type="hidden" id="levelValue" name="levelValue">
			<div class="col-md-3">
	    		<div class="form-group">
						<input type="checkbox" id="selectall" onClick="selectAll(this)" />Select All SDO Code
							
								<div class="checkbox-list" style="height:100px;overflow:auto;width:150px;">
								<c:forEach var="sdoCode1" items="${sdoList}">
									<label id="sdo"> 
									<input type="checkbox" id="${sdoCode1.id }" name="sdoAccess" value="${sdoCode1.sdoCode }">
									${sdoCode1.sdoCode }${sdoCode1.sdoName }
										</label>
										</c:forEach>
													</div>
												</div> 
							</div>
							
							<div class="col-md-2">
	    		<div class="form-group">
						<div class="checkbox-list" style="height:150px;overflow:auto;width:220px;">
								Levels
									<label > 
									<input type="checkbox" id="pending" name="levels" value="1">
									Pending
									</label>
									<label>
									<input type="checkbox" id="cmri" name="levels" value="2">
									CMRI
									</label>
									<label>
									<input type="checkbox" id="manual" name="levels" value="3">
									Manual
									</label>
									<label>
									<input type="checkbox" id="comConList" name="levels" value="4">
									Complete Consumers List
									</label>
									<label>
									<input type="checkbox" id="mtrChngList" name="levels" value="5">
									Meter Change List
									</label>
									
													</div>
												</div> 
							</div>
							
							<div class="col-md-2">
							<div class="form-group">
										
											<select  class="form-control input-md" name="mrName" id="mrName">
												<option value="0">Select MRName</option>
												<c:forEach var="element" items="${mrnamrList}">
												<option value="${element}">${element}</option>
												</c:forEach>
											</select>
										
									</div>
							</div>
										<div class="col-md-2 ">
									<div class="form-group">
										   <select  class="form-control" path="" name="circle" id="circle" >
										     <option value="0">Select Circle</option> 
										      <c:forEach items="${circle}" var="element">
													<option value="${element}">${element}</option>
													</c:forEach>	
										                                                   
										   </select>
										   </div>
										   </div>
									  
							
							
							
							<div class="col-md-1">
							<div data-date-viewmode="years" data-date-format="yyyymm"  class="input-group input-medium date date-picker">
									<input type="text" name="reportsMonth" id="reportsMonth" readonly="readonly" class="form-control" required="required">
										<span class="input-group-btn">
											<button type="button" class="btn default"><i class="fa fa-calendar"></i></button>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>	
								</div>	
							</div>
					
												
						
										
					</div>
					<div class="row">
					<div class="modal-footer">
					<button class="btn blue " id="htPending" onclick="return validationPendingSummary(3)"  >HT Pending </button>
					<button class="btn green " id="newConnection" onclick="return validationPendingSummary(2);"  >New Connection</button>
					<button class="btn green " id="htPendingSummary" onclick="return validationPendingSummary(4);"  >HT Pending Summary</button>
					<button class="btn blue pull-right" id="showData" onclick="return validationPendingSummary(1)"  >Pending Summary</button>  
					
					<button class="btn green " id="showData" onclick="return validation();"  >Show</button>
					
					
					
						
						
					</div>
					</div> --%>
					</form>	 
						</div>
				</div>
				
				
			</div>
					
					<div class="clearfix"></div>
					 
					<c:if test = "${pendingerror ne 'notDisplay'}"> 			        
			        <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${pendingerror}</span>
						</div>
			        </c:if>
			        
			        <div class="row">
					<div class="col-md-12">
					<c:if test="${pending eq 'pending'}">
					<div class="portlet box green tasks-widget">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-check"></i>
							<c:choose>
							<c:when test="${title eq 'pendingTitle'}">
							  Pending Details 
							</c:when>
							<c:otherwise>
							New Connection Details
							</c:otherwise>
							</c:choose>
							
									 
									        </div>
							<div class="tools">
								<a href="" class="reload"></a>
							</div>
						
						</div>
						<div class="portlet-body">
						<div class="table-toolbar">
								 <div class="btn-group" >
								
									<br/>
									<br/>
								</div>
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="print">Print</a></li>
										<li><a href="#" id="excelExport" onclick="tableToExcel('sample_editable_1', 'Manual Details')">Export to Excel</a></li>
									</ul>
								</div>
							</div>
							<div id="pending">
						<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>									
									<tr>										
										<th>Account No</th>
										<th>Meter No</th>
										<th>Name</th>
										<th>Address</th>
										<th>MR Name</th>
								</tr>
								</thead>
								<tbody>
						<c:forEach var="element" items="${pendingList}">
						<tr>
						<td>${element[0]}</td>
						<td>${element[1]}</td>
						<td>${element[2]}</td>
						<td>${element[3]}</td>
						<td>${element[4]}</td>
						</tr>
						</c:forEach>
						</tbody>
								
							</table>
								
						</div>
						</div>
					</div>
					
					</c:if>
 					
 					
 					<c:if test="${CMRI eq 'CMRI'}">
 					<div class="portlet box blue tasks-widget">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-check"></i>
									    CMRI Details
									        </div>
							<div class="tools">
								<a href="" class="reload"></a>
							</div>
						
						</div>
						<div class="portlet-body">
						<div class="table-toolbar">
								 <div class="btn-group" >
								
									<br/>
									<br/>
								</div>
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="print2">Print</a></li>
										<li><a href="#" id="excelExport" onclick="tableToExcel('sample_2', 'CMRI Details')">Export to Excel</a></li>
									</ul>
								</div>
							</div>
							<div id="cmri">
						<table class="table table-striped table-bordered table-hover" id="sample_2">
								<thead>									
									<tr>										
										<th>Account No</th>
										<th>Meter No</th>
										<th>Name</th>
										<th>Address</th>
										<th>MR Name</th>
								</tr>
								</thead>
								<tbody>
						<c:forEach var="element" items="${cmriList}">
						<tr>
						<td>${element[0]}</td>
						<td>${element[1]}</td>
						<td>${element[2]}</td>
						<td>${element[3]}</td>
						<td>${element[4] }</td>
						</tr>
						</c:forEach>
						</tbody>
							</table>
							
							</div>
							
							
						</div>
					</div>
					
					
					 
 					</c:if>
 					<c:if test="${Manual eq 'Manual'}">
 					<div class="portlet box blue tasks-widget">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-check"></i>
									    Manual Details
									        </div>
							<div class="tools">
								<a href="" class="reload"></a>
							</div>
						
						</div>
						<div class="portlet-body">
						<div class="table-toolbar">
								 <div class="btn-group" >
								
									<br/>
									<br/>
								</div>
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="print3">Print</a></li>
										<li><a href="#" id="excelExport" onclick="tableToExcel('sample_3', 'Manual Details')">Export to Excel</a></li>
									</ul>
								</div>
							</div>
							<div id="manual">
						<table class="table table-striped table-bordered table-hover" id="sample_3">
								<thead>									
									<tr>										
										<th>Account No</th>
										<th>Meter No</th>
										<th>Name</th>
										<th>Address</th>
										<th>MR Name</th>
								</tr>
								</thead>
								<tbody>
						<c:forEach var="element" items="${manualList}">
						<tr>
						<td>${element[0]}</td>
						<td>${element[1]}</td>
						<td>${element[2]}</td>
						<td>${element[3]}</td>
						<td>${element[4] }</td>
						</tr>
						</c:forEach>
						</tbody>
							</table>
							
							
							</div>
							
						</div>
					</div>
					
					
					
 					</c:if>
 					
 					<c:if test="${Consumer_Complete_List eq 'Consumer Complete List'}">
 					<div class="portlet box blue tasks-widget">
						<div class="portlet-title">
							<div class="caption"><i class="glyphicon glyphicon-book"></i>
									    Complete Consumer List Details
									        </div>
							<div class="tools">
								<a href="" class="reload"></a>
							</div>
						
						</div>
						<div class="portlet-body">
						<div class="table-toolbar">
								 <div class="btn-group" >
								
									<br/>
									<br/>
								</div>
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="print6">Print</a></li>
										<li><a href="#" id="excelExport" onclick="tableToExcel('sample_4', 'Complete Consumer List Details')">Export to Excel</a></li>
									</ul>
								</div>
							</div>
							<div id="comConsumList">
						<table class="table table-striped table-bordered table-hover" id="sample_4">
								<thead>									
									<tr>		
										<th>RDNGMONTH</th>
										<th>TADESC</th>
										<th>SDOCODE</th>
										<th>ACCNO</th>
										<th>METRNO</th>
										<th>MTRMAKE</th>
										<th>MTRTYPE</th>
										<th>CONTRACTDEMAND</th>
										<th>MF</th>
										<th>KWORHP</th>
										<th>SANLOAD</th>
										<th>NAME</th>
										<th>ADDRESS1</th>
										<th>CURRDNGKWH</th>
										<th>CURRRDNGKVAH</th>
										<th>CURRDNGKVA</th>
										<th>PF</th>
										<th>READINGREMARK</th>
										<th>REMARK</th>
										<th>XMLDATE</th>
										<th>XCURRDNGKWH</th>
										<th>XCURRRDNGKVAH</th>
										<th>XCURRDNGKVA</th>
										<th>XPF</th>
										<th>READINGDATE</th>
										<th>RTC</th>
										<th>MRNAME</th>
										<th>MNP</th>
										<th>PHONENO</th>
								</tr>
								</thead>
								<tbody>
						<c:forEach var="element" items="${conCompleteList}">
						<tr>
							<c:forEach begin="0" end="${conCompleteListArr}" var="i">
									<td>${element[i]}</td>
							</c:forEach>
						</tr>
						</c:forEach>
						</tbody>
							</table>
							
							
							</div>
							
						</div>
					</div>
					
					
					
 					</c:if>
 					
 					<c:if test="${Meter_Change_List eq 'Meter Change List'}">
 					<div class="portlet box blue tasks-widget">
						<div class="portlet-title">
							<div class="caption"><i class="glyphicon glyphicon-book"></i>
									    Meter Change List Details
									        </div>
							<div class="tools">
								<a href="" class="reload"></a>
							</div>
						
						</div>
						<div class="portlet-body">
						<div class="table-toolbar">
								 <div class="btn-group" >
								
									<br/>
									<br/>
								</div>
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="print7">Print</a></li>
										<li><a href="#" id="excelExport" onclick="tableToExcel('sample_7', 'Meter Change List Details')">Export to Excel</a></li>
									</ul>
								</div>
							</div>
							<div id="mtrchngList">
						<table class="table table-striped table-bordered table-hover" id="sample_7">
								<thead>									
									<tr>		
										<th>ACCNO</th>
										<th>METRNO</th>
										<th>RTC</th>
										<th>READINGREMARK</th>
										<th>REMARK</th>
										<th>MRNAME</th>
										
								</tr>
								</thead>
								<tbody>
						<c:forEach var="element" items="${mtrChangeList}">
						<tr>
							<c:forEach begin="0" end="${mtrChangeListArr}" var="i">
									<td>${element[i]}</td>
							</c:forEach>
						</tr>
						</c:forEach>
						</tbody>
							</table>
							
							
							</div>
							
						</div>
					</div>
					
					
					
 					</c:if>
 					
 					<c:if test="${pendingSummary eq 'pendingSummary'}">
 					<div class="portlet box blue tasks-widget">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-check"></i>
									   ${Summarytitle }
									        </div>
							<div class="tools">
								<a href="" class="reload"></a>
							</div>
						
						</div>
						<div class="portlet-body">
						<div class="table-toolbar">
								 <div class="btn-group" >
								
									<br/>
									<br/>
								</div>
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									 <ul class="dropdown-menu pull-right">
										<li><a href="#" id="print4">Print</a></li>
										<li><a href="#" id="excelExport" onclick="tableToExcel('sample_4', 'Manual Details')">Export to Excel</a></li>
									</ul>
								</div>
							</div>
							<div id="pendingSummary">
						<table class="table table-striped table-bordered table-hover" id="sample_4">
								<thead>									
									<tr>										
										<th>SL No.</th>
										<th>SDO Code</th>
										<th>NOI</th>
										
								</tr>
								</thead>
								<tbody>
								<c:set var="count" value="0" scope="page" />
						<c:forEach var="element" items="${pendingSummaryList}">
						<c:set var="count" value="${count + 1}" scope="page"/>
						<tr>
						<td>${count}</td>
						<td>${element[0]}</td>
						<td>${element[1]}</td>
						
						</tr>
						</c:forEach>
						</tbody>
							</table>
							
							
							</div>
							
						</div>
					</div>
					
					
					
 					</c:if>
 					
 					<%-- <c:if test="${HTPending eq 'HTPending'}"> --%>
 					<%-- <div class="portlet box blue tasks-widget" class="scroller">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-check"></i>
									   HT Pending Details of ${billmonth}
									        </div>
							<div class="tools">
								<a href="" class="reload"></a>
							</div>
						
						</div>
						<div class="portlet-body">
						<div class="table-toolbar">
								 <div class="btn-group" >
								
									<br/>
									<br/>
								</div>
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									 <ul class="dropdown-menu pull-right">
										 <li><a href="#" id="print5">Print</a></li> 
										<li><a href="#" id="excelExport" onclick="tableToExcel('sample_5', 'HT Pending Details')">Export to Excel</a></li>
									</ul> 
								</div>
							</div>
							<div id="HTPending" >
						<!-- <table class="table table-striped table-bordered table-hover " id="sample_5"> -->
						<table class="table table-striped table-hover table-bordered" id="sample_5">
								<thead>									
									<tr>										
										<th>CIRCLE</th>
										<th>TADESC</th>
										<th>SDONAME</th>
										<th>ACCNO</th>
										<th>METRNO</th>
										<th>NAME</th>
										<th>ADDRESS1</th>
										<th>MRNAME</th>
										<th>READINGREMARK</th>
										<th>REMARK</th>
										<th>XMLDATE</th>
										<th>XCURRDNGKWH</th>
								</tr>
								</thead>
								<tbody>
						<c:forEach var="element" items="${HTPendingList}">
						<tr>
						<td>${element[0]}</td>
						<td>${element[1]}</td>
						<td>${element[2]}</td>
						<td>${element[3]}</td>
						<td>${element[4]}</td>
						<td>${element[5]}</td>
						<td>${element[6]}</td>
						<td>${element[7]}</td>
						<td>${element[8]}</td>
						<td>${element[9]}</td>
						<td>${element[10]}</td>
						<td>${element[11]}</td>
						<td>${element[12]}</td>
						</tr>
						</c:forEach>
						</tbody>
							</table>
							
							
							</div>
							
						</div>
					</div> --%>
					<c:if test="${HTPending eq 'HTPending'}">
					
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					
					
						<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i> HT Pending Details of ${billmonth}
							
						  <a href="#" id="excelExport" class="btn green" style="margin-left: 500px;" onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
							<!-- <a href="#" id="print" class="btn green" onclick="tableToPrint('sample_editable_1', 'HTPENDING STATUS')">Print</a> -->
				       <a href="#" id="htPendindPrint" class="btn green"><font size="2" color="white">PRINT</font></a><img alt="" src="resources/assets/img/print1.jpg" style="height: 35px; width: 35px;"></img>
							</div>
							
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
									<th hidden>CIRCLE</th>
									
										<th>ACTION</th>
										<th>TADESC</th>
										<th>SDONAME</th>
										<th>ACCNO</th>
										<th>METRNO</th>
										
										<th>READINGREMARK</th>
										<th>REMARK</th>
										
										<th>NAME</th>
										<th>ADDRESS1</th>
										<th>MRNAME</th>
										
										<th>READINGDATE</th>
										
										<th>CURRDNGKWH</th>
										<th>CURRRDNGKVAH</th> 
										<th>CURRDNGKVA</th>
										
										<!-- <th>XMLDATE</th>
										<th>XCURRDNGKWH</th> -->
										
										<th>READING REMARK </th>
									
										
										
									</tr>
								</thead>
								<tbody>
								<c:set var="count" value="1" scope="page"> </c:set>
									<c:forEach var="element" items="${HTPendingList}">
								<tr>
						<td hidden>${element[0]}</td>
						
						<td>
					   		<button type="button" class="btn btn-info" id="manualId"
							onclick="return manualUpdateMethod('${element[8]}','${element[3]}',${count});">Manual Update
							</button>
					   	</td>
					   		
						<td>${element[1]}</td>
						<td>${element[2]}</td>
						<td>${element[3]}</td>
						<td>${element[4]}</td>
						
						<td>${element[8]}</td>
						<td>${element[9]}</td>
						
						<td>${element[5]}</td>
						<td>${element[6]}</td>
						<td>${element[7]}</td>
						
						<td>${element[10]}</td>
						
						<%-- <td>${element[11]}</td>
						<td>${element[12]}</td> --%>
						
					    <td><input class="form-control" type="text" id="ckwhId${count}" name="ckwhId" value="${element[13]}">
					    </td>
					    
						<td>
						<input class="form-control" type="text" id="ckvahId${count}" name="ckvahId" value="${element[14]}">
						</td>
						<td>
						<input class="form-control" type="text" id="ckvaId${count}" name="ckvaId" value="${element[15]}">
						</td>
					   		
					   	 	<td>
					   	 	<select  class="form-control input-medium" id="remarkId${count}" name="remarkId">
					   	 	<option value="0">Select Reading Remark</option>
					   	 	<c:forEach var="remarkList" items="${rremark}">
					   	 	<option value="${remarkList.readingremark}">${remarkList.readingremark}</option>
					   	 	</c:forEach>
					   	 	</select>
					   		
					   		</td> 
					   		
					   		
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
	</div>
					
 					</c:if>
 					
 					
 					
 	</div>
 	</div>
 	</div>
 	 	   
 	 	   
<script>

	function manualUpdateMethod(oldrdngRemark, accNo,count)
	{
		var newrdngRemark = $("#remarkId"+count).val();
		var billMonth = $("#reportsMonth").val();
		
		//alert("newrdngRemark==="+newrdngRemark+"billMonth==="+billMonth);
		
		
		var currdngkwh=$("#ckwhId"+count).val();
		var currdngkvah=$("#ckvahId"+count).val();
		var currdngkva=$("#ckvaId"+count).val();
		
		//alert("ckwh===="+currdngkwh+"ckvah==="+currdngkvah+"ckva==="+currdngkva);
		
		if (oldrdngRemark == 'OK') 
		{
			bootbox.alert("You cannot update for manual");
			return false;
		}
		
		if (oldrdngRemark != 'OK') {
			
		    	if (newrdngRemark == '0') {
					bootbox.alert("Select Reading Remark for Manual Update");
					return false;
				} else {
					
					var r = confirm("Are you sure you want to update manual !");
				    if (r == true) 
				    {
				    	
							$.ajax({
								type : "POST",
								url : "./updateHtManual",
								data : {
									billMonth : billMonth,
									accNo : accNo,
									newrdngRemark : newrdngRemark,
									currdngkwh:currdngkwh,
									currdngkvah:currdngkvah,
									currdngkva:currdngkva
								},
								success : function(response) {
									bootbox.alert(response);
									location.reload(); 
								}
		
							});
				    }
				    else 
				    {
					   alert("You Canceled");
				    }
				    
				}

		    }  

	}
	function SdoByCircle()
	 {
		
		 var circle=$("#circle").val();
		 $.ajax({
			 type : "POST",
			 url:"./getSdoNamesCirclewise",
			 data:{circle:circle},
			 success:function(response)
			 {
				 var html="";
				 if(response != null)
		    		{
		    			/* var html='<select class="form-control input-medium" name="SecondSdocode" id="SecondSdocode"   style="margin-top: 10px;" style="margin-top: 10px;" onchange="changeEditSecondmrName();">'
		    			+'<option value=0>Select SDO Code</option>'; */

		    			html='<option value=0>Select SDO Code</option>';
		    			 for( var i=0;i<response.length;i++)
		    			 {
		    				html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
		    			 }
		    			 html+='</select>';
		    			 $("#SecondSdocodeId").empty();
		    			 $("#SecondSdocodeId").append(html);
		    			
		    			
		    		}
			 }
			 
		 });
		 
	 }


function getMrnamesBySDO(sdoname)
	 {
		 
		 $.ajax({
			 type : "POST",
			 url:"./getMrnamesBySDO",
			 data:{sdoname:sdoname},
			 success:function(response)
			 {
				 var html="";
				 if(response != null)
		    		{
					  html='<option value=0>Select MRName </option>';
		    			 for( var i=0;i<response.length;i++)
		    			 {
		    				html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
		    			 }
		    			 html+='</select>';
		    			 $("#mrName").empty();
		    			 $("#mrName").append(html);
		    		}
			 }
			 
		 });
		 
	 } 
</script>