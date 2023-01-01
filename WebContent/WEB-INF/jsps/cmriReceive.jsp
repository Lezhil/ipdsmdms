<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		$("#secureCount").html('${secureCount}');
		$("#lntCount").html('${lntCount}');
		$("#gcCount").html('${genusC}');
		$("#gdCount").html('${genusD}');
		$("#hplmCount").html('${hplM}');
		$("#hpldCount").html('${hplD}');
		$("#lngCount").html('${lngCount}');
		$("#mipCCount").html('${mipCCount}');
		$("#mipMCount").html('${mipMCount}');
		$("#sipCCount").html('${sipCCount}');
		$("#sipMCount").html('${sipMCount}');
		App.init();
		TableManaged.init();
		FormComponents.init();
		 $("#print").click(function(){ 
   	    	 printcontent($(".table-scrollable").html());
			});
		$('#rdgDateId2,#rdgDateId3,#rDate').val(moment(new Date()).format('MM/DD/YYYY'));
		$('#cmri-Manager').addClass('start active ,selected');
		$("#dash-board,#360d-view,#cumulative-Analysis,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	});
  
	function getCMRIIssuedDetails(param,id,revievedButton)
	{	
		if(revievedButton=='YES')
			{
			document.getElementById('alreadyRecieveDataId').style.display='block';
			document.getElementById('recieveData').style.display='none';
			}
		else
			{
			document.getElementById('recieveData').style.display='block';
			document.getElementById('alreadyRecieveDataId').style.display='none';
			}
		
		  $.ajax({
		    	url:'./getCMRIDataForRecieve/'+id,
		    	type:'GET',		    	
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	success:function(response)
		    	{	
		    		
		    		$('#cmrino,#rdgDateId,#issueId,#subdiv,#mrname,#accessories').prop('disabled', 'disabled');
		    		document.getElementById('six').style.display='none';
		    		$('#issueId').val(response.id);
		    	    $('#subdiv').val(response.subDiv);
		    		$('#mrname').val(response.name);
		    		$('#cmrino').val(response.mriNo);
		    		$('#accessories').val(response.accessories);
		    		//$('#subdiv').val(response.subDiv);
		    		//$('#iDateId').val(moment(response.iDate).format("MM/DD/YYYY"));
		    		if(response.rDate!=null)
		    			{
		    			$('#rDate').val(moment(response.rDate).format("MM/DD/YYYY"));
		    			}
		    		
		    		$('#rdgDateId').val(moment(response.rdgDate).format("MM/DD/YYYY"));
		    		
		    		$('#mrSecure').val(response.mrSecure);
		    		$('#mrLnt').val(response.mrLnt);
		    		$('#mrGenusc').val(response.mrGenusc);
		    		$('#mrGenusd').val(response.mrGenusd);
		    		$('#mrHplmacs').val(response.mrHplmacs);
		    		$('#mrHpld').val(response.mrHpld);
		    		$('#mrlng').val(response.mrlng);
		    		$('#mipCmri').val(response.mipCmri);
		    		$('#mipMannual').val(response.mipMannual);
		    		$('#sipCmri').val(response.sipCmri);
		    		$('#sipMannual').val(response.sipMannual);
		    		$('#gxtFiles').val(response.gxtFiles);
		    		$('#mrRemark').val(response.mrRemark);
		    		calculate();		    		
		    	}		    	
		    })
		    $('#'+param).attr("data-toggle", "modal");
			 $('#'+param).attr("data-target","#stack1");
	}
	
	function alreadyRecievedData()
	{
		$('#cmrino,#rdgDateId,#issueId,#subdiv,#mrname,#accessories').prop('disabled', false);
		$('#cmriRecieveForm').attr('action','./alreadyRecieveDataUpdate').submit();
	}
	
	function checkValid(path)
	{	
		if($('#rDate').val()=='')
		{
		bootbox.alert('please select recieve date');
		return false;
		}
		
		if($('#rDate').val() != $('#rdgDateId').val())
		{
		bootbox.alert('Receive Date Should be Same as Reading Date ...');
		return false;
		}
		
		if($('#mrSecure').val()=='' || isNaN($('#mrSecure').val()))
		{
		bootbox.alert('please enter secure, should contain only digits');
		return false;
		}
		if($('#mrLnt').val()=='' || isNaN($('#mrLnt').val()))
		{
		bootbox.alert('please enter LnT, should contain only digits');
		return false;
		}
		if($('#mrGenusc').val()=='' || isNaN($('#mrGenusc').val()))
		{
			bootbox.alert('please enter genus c, should contain only digits');
		return false;
		}
		if($('#mrGenusd').val()=='' || isNaN($('#mrGenusd').val()))
		{
			bootbox.alert('please enter genus d, should contain only digits');
		return false;
		}
		if($('#mrHplmacs').val()=='' || isNaN($('#mrHplmacs').val()))
		{
			bootbox.alert('please enter mrHpl mac, should contain only digits');
			return false;
		}
		if($('#mrHpld').val()=='' || isNaN($('#mrHpld').val()))
		{
			bootbox.alert('please enter Hpl Daksh, should contain only digits');
			return false;
		}
		if($('#mrlng').val()=='' || isNaN($('#mrlng').val()))
		{
			bootbox.alert('please enter lng, should contain only digits');
			return false;
		}
		
		if($('#mipCmri').val()=='' || isNaN($('#mipCmri').val()))
		{
			bootbox.alert('please enter mip c, should contain only digits');
			return false;
			}
		
		if($('#mipMannual').val()=='' || isNaN($('#mipMannual').val()))
		{
			bootbox.alert('please enter mip M, should contain only digits');
			return false;
			}
		
		if($('#sipCmri').val()=='' || isNaN($('#sipCmri').val()))
		{
			bootbox.alert('please enter sip C, should contain only digits');
			return false;
			}
		
		if($('#sipMannual').val()=='' || isNaN($('#sipMannual').val()))
		{
			bootbox.alert('please enter sip M, should contain only digits');
			return false;
		}
		
		if($('#gxtFiles').val().length>10)
		{
			bootbox.alert('gxtFiles length should not be greater than 10');
			return false;
		}
		
		$('#cmrino,#rdgDateId,#issueId,#subdiv,#mrname,#accessories').prop('disabled', false);
		$('#cmriRecieveForm').attr('action',path).submit();
	}
	function calculate()
	{
		var s=parseInt($('#mrSecure').val(), 10);
		var l=parseInt($('#mrLnt').val(), 10);
		var gc=parseInt($('#mrGenusc').val(), 10);
		var gd=parseInt($('#mrGenusd').val(), 10);
		var hp=parseInt($('#mrHplmacs').val(), 10);
		var hd=parseInt($('#mrHpld').val(), 10);
		
		var lg=0;
		if($('#mrlng').val()!='')
			{
			lg=parseInt($('#mrlng').val(), 10);
			}
		$('#total').val(s+l+gc+gd+hp+hd+lg);
		
	}
	
	function uploadData(id)
	{
		$("#hiddenCmriId").val(id);
		$("#uploadToCmriFormId").attr("action","./uploadToCmriFormId").submit();
	}
</script>
<div class="page-content" >
 <c:if test = "${not empty result}">
					<div class="alert alert-danger display-show">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${result}</span>
					</div>
			    </c:if>
<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>CMRI Receive</div>
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
											
						<div class="portlet-body">
						
						
						
						             <div class="btn-group">
									    <button class="btn blue" data-target="#stack2" data-toggle="modal"  id="addData">
									      Refresh
									    </button>
									    
									    <button class="btn green" data-target="#stack3" data-toggle="modal"  id="addData">
									      Received CMRI
									    </button>
									    
									    <!-- <button class="btn blue" data-target="#stack4" data-toggle="modal"  id="addData">
									      Tally
									    </button> -->
								         </div>
								         <div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									 <ul class="dropdown-menu pull-right">
										<li id="print"><a href="#">Print</a></li>										
										<li><a href="#" onclick="tableToExcel('sample_3','CMRI_Recieve');">Export to Excel</a></li>
										
									 </ul>
							    	</div><br/><br/>
								     
								     <c:if test="${not empty totalshow}">
								     <table>
								     <tr>
								     <td>Secure&nbsp;: <b id="secureCount"></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								     <td>LnT&nbsp;: <b id="lntCount"></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								     <td>Genus&nbsp;Common&nbsp;: <b id="gcCount"></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								     <td>Genus&nbsp;DLMS&nbsp;: <b id="gdCount"></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								     </tr>
								     <tr>
								     <td>HPL&nbsp;MAC&nbsp;: <b id="hplmCount"></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								     <td>HPL&nbsp;DAKSH&nbsp;: <b id="hpldCount"></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								     <td>LnG&nbsp;: <b id="lngCount"></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								     <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								     </tr>
								     <tr>
								     <td>MIP&nbsp;Common&nbsp;: <b id="mipCCount"></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								      <td>MIP&nbsp;Manual&nbsp;: <b id="mipMCount"></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								       <td>SIP&nbsp;Common&nbsp;: <b id="sipCCount"></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								        <td>SIP&nbsp;Manual&nbsp;: <b id="sipMCount"></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								     </tr>
								     </table> 
								     </c:if>
								<br>
								<br>
								<table class="table table-striped table-bordered table-hover" id="sample_3">
									
									
									<thead>									
									<tr>
									<td hidden="true">Id</td>
									<td>BillMonth</td>
									 <td>Reading Date</td>
									 <td>IDate</td>
									 <td>CMRI No.</td>
									 <td>Sub Div</td>
									 <td>MR Name</td>									 
									 <td>RDate</td>
									 <td>Secure</td>
									 <td>LNT</td>									 
									 <td>Genus C</td>
									 <td>Genus D</td>
									 <td>HPL Macs</td>
									 <td>HPL Daksh</td>
									 <td>LNG </td>
									 <td>Accessories</td>
									 <td>Edit</td>
									 <td>UploadData</td>
									 </tr>
									</thead>
									<tbody>
										<c:forEach items="${cmriRecieveData}" var="element">
										<tr><td hidden="true">${element.id}</td>
										 	<td>${element.billMonth}</td>
										 	<td><fmt:formatDate pattern="dd/MM/YYYY" value="${element.rdgDate}" /></td>
										 	<td><fmt:formatDate pattern="dd/MM/YYYY" value="${element.iDate}" /></td>
										 	<td>${element.mriNo}</td>
										 	<td>${element.subDiv}</td>
										 	<td>${element.name}</td>
										 	<td><fmt:formatDate pattern="dd/MM/YYYY" value="${element.rDate}" /></td>
										 	<td>${element.mrSecure}</td>
										 	<td>${element.mrLnt}</td>
										 	<td>${element.mrGenusc}</td>
										 	<td>${element.mrGenusd}</td>
										 	<td>${element.mrHplmacs}</td>
										 	<td>${element.mrHpld}</td>
										 	<td>${element.mrlng}</td>
										 	<td>${element.accessories}</td>
										 	<td><a href="#" onclick="return getCMRIIssuedDetails(this.id,${element.id},'${alreadyRecievedData}')" id="edit${element.id}">Edit</a></td>
										 	<td><a href="#" onclick="uploadData(${element.id})">Upload</a></td>
										 	</tr>
										</c:forEach>
									</tbody>
								</table>
						
						<form action="./uploadToCMRIRecieve" id="uploadToCmriFormId">
						
						<input type="hidden" name="hiddenCmriId" id="hiddenCmriId">
						
						</form>
						
						
						<div id="stack1" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<h4 class="modal-title">CMRI Recieve</h4>
										</div>
										<div class="modal-body">
								<form:form action="./recieveDataUpdate" modelAttribute="cmriManager" commandName="cmriManager" method="post" id="cmriRecieveForm">
								
									<div class="form-body">
										<div class="form-group">
										<table id="tableData2">
															<tr hidden="true"><td>Id</td>
															
																<td>
																		<form:input path="id" type="text" class="form-control"  id="issueId"  readonly="true" ></form:input>
																					</td>
																					<td></td>
															</tr>
															<tr><td>Month</td>
															
																<td>
																		<form:input path="billMonth" type="text" class="form-control" name="billMonthName" id="billMonthId"  readonly="true" ></form:input>
																					</td>
																					<td></td>
															</tr><tr>
														
															<td>Reading Date</td>
															
															<td><div class="input-group input-medium date date-picker" >
																					<form:input path="rdgDate" type="text" class="form-control" name="rdgDateName" id="rdgDateId"  ></form:input>
																					<span class="input-group-btn">
																					<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																					</span>
																					</div></td>
																					<td></td>
															
															</tr>
																
															<tr>
															<td>CMRI No</td>
															
														
															
														        <td><form:select path="mriNo" name="cmrino" class="form-control" id="cmrino" >
															
															     <option value="0">select</option>
															   <c:forEach items="${cmriNo}" var="element">
															     <option value="${element.cmri_no}">${element.cmri_no}</option>
															     </c:forEach> 
					                                             </form:select></td> 
					                                             <td></td>
															
															</tr>
															
															
															
															
															
															<tr><td>Sub Div </td>
															
														        <td><form:select path="subDiv" name="subdiv" class="form-control" id="subdiv">
															
															     <form:option value="0">select</form:option>
															     <c:forEach items="${subDivision}" var="element">
															      <form:option value="${element.sdoName}">${element.sdoName}</form:option>
															     </c:forEach>
															     
					                                            </form:select></td>
					                                            <td></td>
															</tr>
															
															<tr>
															
															<td>MR Name</td>
																			
														        <td><form:select path="name" name="mrname" class="form-control" id="mrname">
															
															     <form:option value="0">select</form:option>
															     <c:forEach items="${mrNames}" var="element">
															      <form:option value="${element.mrname}">${element.mrname}</form:option>
															     </c:forEach>
					                                            					                                             
															   </form:select></td> 
															   <td></td>
															
															
															</tr>
															
															
															
															<tr><td>Accessories</td>
															
														        <td><form:select path="accessories" name="accessories" class="form-control" id="accessories"  required="required">
															
															     <form:option value="0">select</form:option>					                                             
					                                             <form:option value="Common Cable">Common Cable</form:option>
					                                              <form:option value="Sems Cable">Sems Cable</form:option>
					                                               <form:option value="LnT Cable">LnT Cable</form:option>
					                                                <form:option value="Charger">Charger</form:option>
					                                                 <form:option value="Bag and All Accessories">Bag and All Accessories</form:option>
					                                                 	<form:option value="None">None</form:option>		
															    </form:select></td> 
															    <td></td>
														      
															</tr>
														<%-- <tr hidden="true">
															<td>Issue Date</td>
															
															<td><div class="input-group input-medium date date-picker" >
																					<form:input path="iDate" type="text" class="form-control" name="iDateName" id="iDateId" ></form:input>
																					<span class="input-group-btn">
																					<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																					</span>
																					</div></td>
															
															</tr> --%>
										
									                	<tr>
															<td>Recieve Date</td>
															
															<td><div class="input-group input-medium date date-picker" >
																					<form:input path="rDate" type="text" class="form-control" name="rDateName" id="rDate" ></form:input>
																					<span class="input-group-btn">
																					<button class="btn default" type="button" id="seven" > <i class="fa fa-calendar"></i></button>
																					</span>
																					</div></td>
																					<td></td>
															
															</tr>
										
										<tr><td>Secure</td><td>
									    <form:input path="mrSecure" type="text" class="form-control"   name="mrSecure" id="mrSecure"></form:input>
									   
									    </td><td></td>
									   <%--  <td hidden="true">
									    <form:input path="secure" type="text" class="form-control"   name="secure" id="secure"></form:input>
									   
									    </td> --%>
									    </tr>
									    
									    <tr><td>
									    LnT</td>
									    <td><form:input path="mrLnt" type="text" class="form-control"  name="mrLnt" id="mrLnt"></form:input>
									   
									    </td>
									    <td></td>
									    <%-- <td hidden="true"><form:input path="lnt" type="text" class="form-control"  name="lnt" id="lnt"></form:input>
									    </td> --%>
									    </tr>
									    
									    <tr>
									    <tr><td>Genus C</td>
									    <td>
									    
									    <form:input path="mrGenusc" type="text" class="form-control"   name="mrGenusc" id="mrGenusc"></form:input>
									    </td>
									   <%--  <td hidden="true">
									    <form:input path="gCommon" type="text" class="form-control"   name="gCommon" id="gCommon"></form:input>
									    </td> --%>
									    <td></td>
									    </tr>
									    
									    <tr><td>Genus D</td>
									    <td>
									    <form:input path="mrGenusd" type="text" class="form-control"   name="mrGenusd" id="mrGenusd"></form:input>
									    </td><td></td>
									   <%--  <td hidden="true">
									    <form:input path="gDlms" type="text" class="form-control"   name="gDlms" id="gDlms"></form:input>
									    </td> --%>
									    </tr>
									    
									    <tr><td>HPL MAC</td><td>
									    <form:input path="mrHplmacs" type="text" class="form-control"   name="mrHplmacs" id="mrHplmacs"></form:input>
									    </td><td></td>
									    <%-- <td hidden="true">
									    <form:input path="hplm" type="text" class="form-control"   name="hplm" id="hplm"></form:input>
									    </td> --%>
									    </tr>
									    
									    <tr><td>HPL Daksh</td>
									    <td>
									    <form:input path="mrHpld" type="text" class="form-control"   name="mrHpld" id="mrHpld"></form:input>
									    </td><td></td>
									   <%--  <td hidden="true">
									    <form:input path="hpld" type="text" class="form-control"   name="hpld" id="hpld"></form:input>
									    </td> --%>
									    </tr>
									    
									    <tr><td>LnG</td>
									    <td>
									    <form:input path="mrlng" type="text" class="form-control"   name="mrlng" id="mrlng" onblur="return calculate();"></form:input>
									    </td><td><span id="lngCount"></span></td>
									   <%--  <td hidden="true">
									    <form:input path="lng" type="text" class="form-control"   name="lng" id="lng"></form:input>
									    </td> --%>
									    </tr>
									    
									    <tr><td>Total</td><td>
									    <input  type="text" class="form-control"   name="total" id="total" readonly="readonly"></input>
									    </td>
									    <td></td>
									    </tr>
									    
									    <tr><td>MIP C</td><td>
									    <form:input path="mipCmri" type="text" class="form-control"   name="mipCmri" id="mipCmri"></form:input>
									    </td><td></td></tr>
									    
									    <tr><td>MIP M</td><td>
									    <form:input path="mipMannual" type="text" class="form-control"   name="mipMannual" id="mipMannual"></form:input>
									    </td><td></td></tr>
									    
									    <tr><td>SIP C</td><td>
									    <form:input path="sipCmri" type="text" class="form-control"   name="sipCmri" id="sipCmri"></form:input>
									    </td><td></td></tr>
									    
									    <tr><td>SIP M</td><td>
									    <form:input path="sipMannual" type="text" class="form-control"   name="sipMannual" id="sipMannual"></form:input>
									    </td><td></td></tr>
									    
									    <tr><td>GXT file</td><td>
									    <form:input path="gxtFiles" type="text" class="form-control"   name="gxtFiles" id="gxtFiles"></form:input>
									    </td><td></td></tr>
									    
									    <tr><td>Remark</td><td>
									    <form:input path="mrRemark" type="text" class="form-control"   name="mrRemark" id="mrRemark"></form:input>
									    </td><td></td></tr>
									    
									    
								     	</table>
										
													
										</div>																																	
													
										<div class="modal-footer">		
															
																<div class="btn-group pull-right">
																	<button name="cmriIssueAdd"  type="submit" class="btn blue" id="recieveData" onclick="return checkValid('./recieveDataUpdate');">update</button>
																	<button name="cmriIssue1"  type="submit" class="btn blue" id="alreadyRecieveDataId" onclick="return checkValid('./alreadyRecieveDataUpdate');">update</button>
																	
																	<button type="button" data-dismiss="modal" class="btn">Cancel</button>
																	</div>
												</div>
									</div>
									
								</form:form>
								</div>
								</div>
								</div>
								</div>
								
								
								
								<div id="stack2" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<h4 class="modal-title">Select Date to get Details</h4>
										</div>
										<div class="modal-body">
													<form:form action="./cmriReceiveRefresh" modelAttribute="cmriManager" commandName="cmriManager" method="post" id="myForm">
													
														<div class="form-body">
															<div class="form-group">
															<table id="tableData2">
															<tr><td>Month</td>
															
																<td>
																		<form:input path="billMonth" type="text" class="form-control" name="billMonthName2" id="billMonthId2"  readonly="true" ></form:input>
																					</td>
															</tr>
															<tr>
															<td>Reading Date</td>
															
															<td><div class="input-group input-medium date date-picker" >
																					<form:input path="rdgDate" type="text" class="form-control" name="rdgDateName2" id="rdgDateId2"  ></form:input>
																					<span class="input-group-btn">
																					<button class="btn default" type="button" id="eight" > <i class="fa fa-calendar"></i></button>
																					</span>
																					</div></td>
															
															</tr>
																						
															</table>
																		
															</div>																																	
																 <div class="modal-footer">		
															
																<div class="btn-group pull-right">
																	<button name="cmriIssueAdd"  type="submit" class="btn blue" id="refreshId">Refresh</button>
																	
																	<button type="button" data-dismiss="modal" class="btn">Cancel</button>
																	</div>
																</div>
															
														</div>
														
													</form:form>
													
													
													</div>
													</div>
													</div>
													</div>
													
													<div id="stack3" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" aria-hidden="true">
													   <div class="modal-dialog">
														<div class="modal-content">
															<div class="modal-header">
																<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
																<h4 class="modal-title">CMRI Recieved Details</h4>
															</div>
															<div class="modal-body">
													<form:form action="./cmriAlreadyReceivedData" modelAttribute="cmriManager" commandName="cmriManager" method="post" id="myForm">
													
														<div class="form-body">
															<div class="form-group">
															<table id="tableData2">
															<tr><td>Month</td>
															
																<td>
																		<form:input path="billMonth" type="text" class="form-control" name="billMonthName2" id="billMonthId2"  readonly="true" ></form:input>
																					</td>
															</tr>
															<tr>
															<td>Reading Date</td>
															
															<td><div class="input-group input-medium date date-picker" >
																					<form:input path="rdgDate" type="text" class="form-control" name="rdgDateName3" id="rdgDateId3"  ></form:input>
																					<span class="input-group-btn">
																					<button class="btn default" type="button" id="nine" > <i class="fa fa-calendar"></i></button>
																					</span>
																					</div></td>
															
															</tr>
																						
															</table>
																		
															</div>																																	
																 <div class="modal-footer">		
															
																<div class="btn-group pull-right">
																	<button name="cmriIssueAdd"  type="submit" class="btn blue" id="refreshId">Refresh</button>
																	
																	<button type="button" data-dismiss="modal" class="btn">Cancel</button>
																	</div>
																</div>
															
														</div>
														
													</form:form>
													
													
													</div>
													</div>
													</div>
													</div>
								
								
								<br>							
								
								
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
</div>