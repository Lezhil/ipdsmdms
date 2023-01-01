<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		
		App.init();
		TableManaged.init();
		FormComponents.init();
		 $("#print").click(function(){ 
   	    	 printcontent($(".table-scrollable").html());
			});
		$('#rdgDateId2').val(moment(new Date()).format('MM/DD/YYYY'));
		$('#cmri-Manager').addClass('start active ,selected');
		$("#dash-board,#360d-view,#cumulative-Analysis,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	});
  
	function getCMRIIssuedDetails(param,id,checkvalue)
	{	
		
		hideShow(checkvalue)//to hide remaining buttons
		
		  $.ajax({
		    	url:'./getCMRIDataForRecieve/'+id,
		    	type:'GET',		    	
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	success:function(response)
		    	{	
		    		$('#cmrino,#rdgDateId,#issueId,#subdiv,#mrname,#accessories,#rDate,#mrSecure,#mrLnt,#mrGenusc,#mrGenusd,#mrHplmacs,#mrHpld,#mrlng,#total,#mipCmri,#mipMannual,#sipCmri,#sipMannual,#gxtFiles,#mrRemark').prop('disabled', 'disabled');
		    		$('#six,#eight').hide();
		    		$('#issueId').val(response.id);
		    	    $('#subdiv').val(response.subDiv);
		    		$('#mrname').val(response.name);
		    		$('#cmrino').val(response.mriNo);
		    		$('#accessories').val(response.accessories);
		    		$('#subdiv').val(response.subDiv);
		    		$('#iDateId').val(moment(response.iDate).format("MM/DD/YYYY"));
		    		$('#rdgDateId').val(moment(response.rdgDate).format("MM/DD/YYYY"));
		    		$('#rDate').val(moment(response.rDate).format("MM/DD/YYYY"));
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
		    		$('#secure').val(response.secure);
		    		$('#lnt').val(response.lnt);
		    		$('#gCommon').val(response.gCommon);
		    		$('#gDlms').val(response.gDlms);
		    		$('#hplm').val(response.hplm);
		    		$('#hpld').val(response.hpld);
		    		$('#lng').val(response.lng);
		    		$('#remark').val(response.remark);
		    		if(response.dumped==null || response.dumped=='')
		    			{
		    			$('#dumped').val(0);
		    			}
		    		else
		    			{
		    			$('#dumped').val(response.dumped);
		    			}
		    		if(response.prepared==null || response.prepared=='')
	    			{
	    			$('#prepared').val(0);
	    			}
	    		else
	    			{
	    			$('#prepared').val(response.prepared);
	    			}		    		
		    		if(response.dumpedBy==null || response.dumpedBy=='')
	    			{
	    			$('#dumpedBy').val(0);
	    			}
	    		else
	    			{
	    			$('#dumpedBy').val(response.dumpedBy);	
	    			}		    		
		    		$('#pcNo').val(response.pcNo);		    			    		
		    		calculate('mrSecure','mrLnt','mrGenusc','mrGenusd','mrHplmacs','mrHpld','mrlng','total');
		    		calculate('secure','lnt','gCommon','gDlms','hplm','hpld','lng','total2');
		    		
		    	}		    	
		    })
		    $('#'+param).attr("data-toggle", "modal");
			 $('#'+param).attr("data-target","#stack1");
	}
	function checkWhich(param)
	{
		   if(param=='refresh')
			{			
			 $('#preparedValues,#differenceValues,#statusValues,#tallyValues').hide();
		     $('#refreshId').show();
			}
				if(param=='prepared')
				{			
				 $('#refreshId,#differenceValues,#statusValues,#tallyValues').hide();
			     $('#preparedValues').show();
				}
			if(param=='difference')
			{			
			 $('#preparedValues,#refreshId,#statusValues,#tallyValues').hide();
		     $('#differenceValues').show();
			}
				if(param=='status')
				{			
				 $('#preparedValues,#differenceValues,#refreshId,#tallyValues').hide();
			     $('#statusValues').show();
				}
			if(param=='tally')
			{			
			 $('#preparedValues,#differenceValues,#statusValues,#refreshId').hide();
		     $('#tallyValues').show();
			}
		
		return false;
	}
	
	function hideShow(checkvalue)
	{
		if(checkvalue=='tally')
		{
		  $('#refreshId1,#prepareId1,#differeanceId1').hide();
		  $('#tallyId1').show();		  
		}
		
		else if(checkvalue=='prepared')
		{
		  $('#refreshId1,#tallyId1,#differeanceId1').hide();
		  $('#prepareId1').show();
		  
		}
		else if(checkvalue=='difference')
		{
		  $('#refreshId1,#prepareId1,#tallyId1').hide();
		  $('#differeanceId1').show();
		  
		}
		else
		{
		  $('#tallyId1,#prepareId1,#differeanceId1').hide();
		  $('#refreshId1').show();	  
		}
		
	}
	
	
	function updateData(param)
	{
		if($('#secure').val()=='' || isNaN($('#secure').val()))
		{
		bootbox.alert('please enter secure, should contain only digits');
		return false;
		}
		if($('#lnt').val()=='' || isNaN($('#lnt').val()))
		{
		bootbox.alert('please enter LnT, should contain only digits');
		return false;
		}
		if($('#gCommon').val()=='' || isNaN($('#gCommon').val()))
		{
			bootbox.alert('please enter genus c, should contain only digits');
		return false;
		}
		if($('#gDlms').val()=='' || isNaN($('#gDlms').val()))
		{
			bootbox.alert('please enter genus d, should contain only digits');
		return false;
		}
		if($('#hplm').val()=='' || isNaN($('#hplm').val()))
		{
			bootbox.alert('please enter mrHpl mac, should contain only digits');
			return false;
		}
		if($('#hpld').val()=='' || isNaN($('#hpld').val()))
		{
			bootbox.alert('please enter Hpl Daksh, should contain only digits');
			return false;
		}
		if($('#lng').val()=='' || isNaN($('#lng').val()))
		{
			bootbox.alert('please enter lng, should contain only digits');
			return false;
		}
		
		if($('#pcNo').val()=='0')
		{
			bootbox.alert('please select pcNo');
			return false;
		}
		if($('#dumped').val()=='0')
		{
			bootbox.alert('please select dumped');
			return false;
		}
		if($('#prepared').val()=='0')
		{
			bootbox.alert('please select prepared');
			return false;
		}
		if($('#dumpedBy').val()=='0')
		{
			bootbox.alert('please select dumped by');
			return false;
		}
		$('#cmrino,#rdgDateId,#issueId,#subdiv,#mrname,#accessories,#rDate,#mrSecure,#mrLnt,#mrGenusc,#mrGenusd,#mrHplmacs,#mrHpld,#mrlng,#mipCmri,#mipMannual,#sipCmri,#sipMannual,#gxtFiles,#mrRemark').prop('disabled', false);
		$('#myForm').attr('action',param).submit();
	}
	
	function getData(param)
	{
		$('#myForm2').attr('action',param).submit();
	}
	
	function calculate(sec,lnt,genc,gend,hplm,hpld,lng,total)
	{
		
		var s=parseInt($('#'+sec).val(), 10);		
		var l=parseInt($('#'+lnt).val(), 10);
		var gc=parseInt($('#'+genc).val(), 10);
		var gd=parseInt($('#'+gend).val(), 10);
		var hp=parseInt($('#'+hplm).val(), 10);
		var hd=parseInt($('#'+hpld).val(), 10);
		
		var lg=0;
		if($('#'+lng).val()!='')
			{
			lg=parseInt($('#'+lng).val(), 10);
			}
		$('#'+total).val(s+l+gc+gd+hp+hd+lg);
		return false;
	}
	
	
</script>
<div class="page-content" >

<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>CMRI Dumping</div>
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
											
						<div class="portlet-body">
						
						
						
						             <div class="btn-group">
									    <button class="btn blue" data-target="#stack2" data-toggle="modal"  id="refreshData" onclick="return checkWhich('refresh')">
									      Refresh
									    </button>
									    
									    <button class="btn green" data-target="#stack2" data-toggle="modal"  id="PreparedData" onclick="return checkWhich('prepared')">
									      Prepared
									    </button>
									     <button class="btn green" data-target="#stack2" data-toggle="modal"  id="differenceData" onclick="return checkWhich('difference')">
									      Difference
									    </button>
									     <button class="btn green" data-target="#stack2" data-toggle="modal"  id="statusData" onclick="return checkWhich('status')">
									      Status
									    </button>
									    
									     <button class="btn blue" data-target="#stack2" data-toggle="modal"  id="tallyData" onclick="return checkWhich('tally')">
									      Tally
									    </button>
									    
									     <!--  <button class="btn blue" data-target="#stack4" data-toggle="modal"  id="addData">
									      Tally
									    </button> -->
								         </div><br/><br/>
								      <div class="btn-group pull-right">   
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li id="print"><a href="#">Print</a></li>										
										<li><a href="#" onclick="tableToExcel('sample_3','CMRI_Dumping');">Export to Excel</a></li>
										<!--  -->
									</ul>
								</div>
								<br>
								<br>
								<table class="table table-striped table-bordered table-hover" id="sample_3">
									
									<c:choose>
									<c:when test="${check eq 'dumpedStatus'}">
									<thead>	
									<tr>
									  <td>MRI No.</td>
									  <td>Total Mr<br/>MRD</td>
									  <td>MRD <br/> Dumped</td>	 
									  <td>Secure</td>
									  <td>LnT</td>
									  <td>GCom</td>
									  <td> GDlms</td>
									  <td>HplM</td>	
									  <td>HplD</td>
									 <td>LNG</td>									
									 <td>Dumped</td>
									 <td>Prepared</td>									
									 <td>PcNo</td>
									 <td>Remark</td>
									 
								 </tr>								 
								 </thead>
								 
								 <tbody>
										<c:forEach items="${cmriDumpedData}" var="element">
										 	<tr>
										 	<td>${element.mriNo}</td>
										 	<td>${element.totalMrd}</td>
										 	<td>${element.mrdDumped}</td>										 	
										 	<td>${element.secure}</td>										 	
										 	<td>${element.lnt}</td>										 	
										 	<td>${element.gCommon}</td>										 	
										 	<td>${element.gDlms}</td>										 	
										 	<td>${element.hplm}</td>										 	
										 	<td>${element.hpld}</td>										 	
										 	<td>${element.lng}</td>
										 	<td>${element.dumped}</td>
										 	<td>${element.prepared}</td>
										 	<td>${element.pcNo}</td>
										 	<td>${element.remark}</td>
										 	</tr>
										</c:forEach>
									</tbody>
								 
									</c:when>
									<c:otherwise>
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
									 <td>MR <br/>Secure</td>
									 <td>Dmp <br/>Secure</td>
									 <td>MR <br/>LnT</td>
									 <td>Dmp <br/>LnT</td>									 
									 <td>Mr <br/>GCom</td>
									 <td>Dump <br/>GCom</td>
									 <td>Mr<br/> GDlms</td>
									  <td>Dmp<br/> GDlms</td>
									 <td>MR <br/>HplM</td>
									 <td>Dmp <br/>HplM</td>
									 <td>MR <br/>HplD</td>
									 <td>Dmp <br/>HplD</td>
									 <td>MR <br/>LNG</td>
									 <td>Dmp <br/>LNG</td>	
									 <td>Dumped</td>
									 <td>Prepared</td>									
									 <td>Edit</td>
									 </tr>
									</thead>
									<tbody>
										<c:forEach items="${cmriDumpedData}" var="element">
										<tr><td hidden="true">${element.id}</td>
										 	<td>${element.billMonth}</td>
										 	<td><fmt:formatDate pattern="dd/MM/YYYY" value="${element.rdgDate}" /></td>
										 	
										 	<td><fmt:formatDate pattern="dd/MM/YYYY" value="${element.iDate}" /></td>
										 	
										 	<td>${element.mriNo}</td>
										 	<td>${element.subDiv}</td>
										 	<td>${element.name}</td>
										 	<td><fmt:formatDate pattern="dd/MM/YYYY" value="${element.rDate}" /></td>
										 	
										 	<td>${element.mrSecure}</td>
										 	<td>${element.secure}</td>
										 	<td>${element.mrLnt}</td>
										 	<td>${element.lnt}</td>
										 	<td>${element.mrGenusc}</td>
										 	<td>${element.gCommon}</td>
										 	<td>${element.mrGenusd}</td>
										 	<td>${element.gDlms}</td>
										 	<td>${element.mrHplmacs}</td>
										 	<td>${element.hplm}</td>
										 	<td>${element.mrHpld}</td>
										 	<td>${element.hpld}</td>
										 	<td>${element.mrlng}</td>
										 	<td>${element.lng}</td>
										 	<td>${element.dumped}</td>
										 	<td>${element.prepared}</td>										 	
										 	<td><a href="#" onclick="return getCMRIIssuedDetails(this.id,${element.id},'${check}')" id="edit${element.id}">Edit</a></td>
										 	</tr>
										</c:forEach>
									</tbody>
									</c:otherwise>
									</c:choose>
								</table>
						
						
						<div id="stack1" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content" style="width:800px;">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<h4 class="modal-title">CMRI Dumping</h4>
										</div>
										<div class="modal-body">
								<form:form action="./dumpedDataUpdate" modelAttribute="cmriManager" commandName="cmriManager" method="post" id="myForm">
								
									<div class="form-body">
										<div class="form-group">
										<table id="tableData2">
															<tr hidden="true"><td>Id</td>
															
																<td>
																		<form:input path="id" type="text" class="form-control"  id="issueId"  readonly="true" ></form:input>
																					</td>
															</tr>
															<tr><td>Month</td>
															
																<td>
																		<form:input path="billMonth" type="text" class="form-control" name="billMonthName" id="billMonthId"  readonly="true" ></form:input>
																					</td>
																					<td>&nbsp;&nbsp;&nbsp;</td>
																					<td>Reading Date</td>
															
															<td><div class="input-group input-medium date date-picker" >
																					<form:input path="rdgDate" type="text" class="form-control" name="rdgDateName" id="rdgDateId"  ></form:input>
																					<span class="input-group-btn">
																					<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																					</span>
																					</div></td>
															</tr>
															<tr>
															<td>CMRI No</td>
															
														
															
														        <td><form:select path="mriNo" name="cmrino" class="form-control" id="cmrino" >
															
															     <option value="0">select</option>
															   <c:forEach items="${cmriNo}" var="element">
															     <option value="${element.cmri_no}">${element.cmri_no}</option>
															     </c:forEach> 
					                                             </form:select></td> 
					                                             <td>&nbsp;&nbsp;&nbsp;</td>
					                                             <td>Sub Div </td>
															
														        <td><form:select path="subDiv" name="subdiv" class="form-control" id="subdiv">
															
															     <form:option value="0">select</form:option>
															     <c:forEach items="${subDivision}" var="element">
															      <form:option value="${element.sdoName}">${element.sdoName}</form:option>
															     </c:forEach>
															     
					                                            </form:select></td>
															
															</tr>
															
															
															<tr>
															
															<td>MR Name</td>
																			
														        <td><form:select path="name" name="mrname" class="form-control" id="mrname">
															
															     <form:option value="0">select</form:option>
															     <c:forEach items="${mrNames}" var="element">
															      <form:option value="${element.mrname}">${element.mrname}</form:option>
															     </c:forEach>
					                                            					                                             
															   </form:select></td>
															    <td>&nbsp;&nbsp;&nbsp;</td>
															<td>Accessories</td>
															
														        <td><form:select path="accessories" name="accessories" class="form-control" id="accessories"  required="required">
															
															     <form:option value="0">select</form:option>					                                             
					                                             <form:option value="Common Cable">Common Cable</form:option>
					                                              <form:option value="Sems Cable">Sems Cable</form:option>
					                                               <form:option value="LnT Cable">LnT Cable</form:option>
					                                                <form:option value="Charger">Charger</form:option>
					                                                 <form:option value="Bag and All Accessories">Bag and All Accessories</form:option>
					                                                 <form:option value="None">None</form:option>			
															    </form:select></td> 
															
															</tr>
															
															
														<tr hidden="true">
															<td>Issue Date</td>
															
															<td><div class="input-group input-medium date date-picker" >
																					<form:input path="iDate" type="text" class="form-control" name="iDateName" id="iDateId" ></form:input>
																					<span class="input-group-btn">
																					<button class="btn default" type="button" id="seven" > <i class="fa fa-calendar"></i></button>
																					</span>
																					</div></td>
																					<td>&nbsp;&nbsp;&nbsp;</td>
															<td>Recieve Date</td>
															
															<td><div class="input-group input-medium date date-picker" >
																					<form:input path="rDate" type="text" class="form-control" name="rDateName" id="rDate" ></form:input>
																					<span class="input-group-btn">
																					<button class="btn default" type="button" id="eight" > <i class="fa fa-calendar"></i></button>
																					</span>
																					</div></td>
																					
															</tr>									
									                	
										
										<tr><td>Secure</td><td>
									    <form:input path="mrSecure" type="text" class="form-control"   name="mrSecure" id="mrSecure"></form:input>
									   
									    </td>
									    <td>&nbsp;&nbsp;&nbsp;</td>
									    <td>Secure</td>
									    <td>
									    <form:input path="secure" type="text" class="form-control"   name="secure" id="secure"></form:input>
									   
									    </td>
									    </tr>
									    
									    <tr><td>
									    LnT</td>
									    <td><form:input path="mrLnt" type="text" class="form-control"  name="mrLnt" id="mrLnt"></form:input>
									    </td>
									    <td>&nbsp;&nbsp;&nbsp;</td>
									    <td>
									    LnT</td>
									    <td><form:input path="lnt" type="text" class="form-control"  name="lnt" id="lnt"></form:input>
									    </td>
									    </tr>
									    
									    <tr>
									    <tr><td>Genus C</td>
									    <td>
									    <form:input path="mrGenusc" type="text" class="form-control"   name="mrGenusc" id="mrGenusc"></form:input>
									    </td>
									    <td>&nbsp;&nbsp;&nbsp;</td>
									    <td>Genus C</td>
									    <td>
									    <form:input path="gCommon" type="text" class="form-control"   name="gCommon" id="gCommon"></form:input>
									    </td>
									    </tr>
									    
									    <tr><td>Genus D</td>
									    <td>
									    <form:input path="mrGenusd" type="text" class="form-control"   name="mrGenusd" id="mrGenusd"></form:input>
									    </td>
									    <td>&nbsp;&nbsp;&nbsp;</td>
									    <td>Genus D</td>
									    <td>
									    <form:input path="gDlms" type="text" class="form-control"   name="gDlms" id="gDlms"></form:input>
									    </td>
									    </tr>
									    
									    <tr><td>HPL MAC</td><td>
									    <form:input path="mrHplmacs" type="text" class="form-control"   name="mrHplmacs" id="mrHplmacs"></form:input>
									    </td>
									    <td>&nbsp;&nbsp;&nbsp;</td>
									    <td>HPL MAC</td>
									    <td>
									    <form:input path="hplm" type="text" class="form-control"   name="hplm" id="hplm"></form:input>
									    </td>
									    </tr>
									    
									    <tr><td>HPL Daksh</td>
									    <td>
									    <form:input path="mrHpld" type="text" class="form-control"   name="mrHpld" id="mrHpld"></form:input>
									    </td>
									    <td>&nbsp;&nbsp;&nbsp;</td>
									    <td>HPL Daksh</td>
									    <td>
									    <form:input path="hpld" type="text" class="form-control"   name="hpld" id="hpld"></form:input>
									    </td>
									    </tr>
									    
									    <tr><td>LnG</td>
									    <td>
									    <form:input path="mrlng" type="text" class="form-control"   name="mrlng" id="mrlng"></form:input>
									    </td>
									    <td>&nbsp;&nbsp;&nbsp;</td>
									    <td>LnG</td>
									    <td>
									    <form:input path="lng" type="text" class="form-control"   name="lng" id="lng" onkeypress="if(event.keyCode==9)calculate('secure','lnt','gCommon','gDlms','hplm','hpld','lng','total2')" ></form:input>
									    </td>
									    </tr>
									    
									    <tr><td>Total</td><td>
									    <input  type="text" class="form-control"   name="total" id="total"></input>
									    </td>
									    <td>&nbsp;&nbsp;&nbsp;</td>
									    <td>Total</td>
									    <td>
									    <input  type="text" class="form-control"   name="total2" id="total2" ></input>
									    </td>
									    </tr>
									    
									    <tr><td>MIP C</td><td>
									    <form:input path="mipCmri" type="text" class="form-control"   name="mipCmri" id="mipCmri"></form:input>
									    </td>
									    <td>&nbsp;&nbsp;&nbsp;</td>
									    <td>MIP M</td><td>
									    <form:input path="mipMannual" type="text" class="form-control"   name="mipMannual" id="mipMannual"></form:input>
									    </td>
									    </tr>
									   
									    <tr><td>SIP C</td><td>
									    <form:input path="sipCmri" type="text" class="form-control"   name="sipCmri" id="sipCmri"></form:input>
									    </td>
									    <td>&nbsp;&nbsp;&nbsp;</td>
									    <td>SIP M</td><td>
									    <form:input path="sipMannual" type="text" class="form-control"   name="sipMannual" id="sipMannual"></form:input>
									    </td>
									    </tr>
									    
									    <tr><td>GXT file</td><td>
									    <form:input path="gxtFiles" type="text" class="form-control"   name="gxtFiles" id="gxtFiles"></form:input>
									    </td>
									    <td>&nbsp;&nbsp;&nbsp;</td>
									    <td>PC No</td>
															
														        <td><form:select path="pcNo" name="pcNo" class="form-control" id="pcNo"  required="required">
															
															     <form:option value="0">select</form:option>					                                             
					                                             <form:option value="1">1</form:option>
					                                              <form:option value="2">2</form:option>
					                                               <form:option value="3">3</form:option>
					                                                <form:option value="4">4</form:option>
					                                                 <form:option value="5">5</form:option>
					                                                 <form:option value="6">6</form:option>
					                                                 <form:option value="7">7</form:option>
					                                                 <form:option value="8">8</form:option>
					                                                 			
															    </form:select></td>
									    
									    </tr>
									    
									    <tr><td>MR Remark</td><td>
									    <form:input path="mrRemark" type="text" class="form-control"   name="mrRemark" id="mrRemark"></form:input>
									    </td>
									    <td>&nbsp;&nbsp;&nbsp;</td>
									    <td>Remark</td>
															
														        <td><form:input path="remark" type="text" class="form-control"   name="remark" id="remark"></form:input></td> 	
															   
									    </tr>
									    
									   
															
															<tr><td>Dumped</td>
															
														        <td><form:select path="dumped" name="dumped" class="form-control" id="dumped" >
															
															     <form:option value="0">select</form:option>					                                             
					                                             <form:option value="YES">YES</form:option>
					                                             <form:option value="NO">NO</form:option>		                                              
					                                                 			
															    </form:select></td> 
															    <td>&nbsp;&nbsp;&nbsp;</td>
															    <td>Prepared</td>
															
														        <td><form:select path="prepared" name="prepared" class="form-control" id="prepared"  >
															
															     <form:option value="0">select</form:option>					                                             
					                                             <form:option value="YES">YES</form:option>
					                                             <form:option value="NO">NO</form:option>			
															    </form:select></td> 
															    
															</tr>
															
															<tr> 
														      <td>Dumped By</td>
															
														        <td><form:select path="dumpedBy" name="dumpedBy" class="form-control" id="dumpedBy" >
															
															     <form:option value="0">select</form:option>					                                             
					                                             <form:option value="AJAY">AJAY</form:option>
					                                             <form:option value="DEVENDRA">DEVENDRA</form:option>
					                                             <form:option value="CHANNU">CHANNU</form:option>
					                                             <form:option value="NAGARAJ">NAGARAJ</form:option>
					                                             <form:option value="RAJESH">RAJESH</form:option>
					                                             <form:option value="RAJGURU">RAJGURU</form:option>	    </form:select></td> 
														      
														      
															</tr>
															
															
									    
								     	</table>
										
													
										</div>																																	
													
										<div class="modal-footer">		
															
																<div class="btn-group pull-right">
																	<button name="cmriIssueAdd"  type="submit" class="btn blue" id="refreshId1" onclick="return updateData('./dumpedDataUpdate');">update</button>
																	<button name="cmriIssueAdd"  type="submit" class="btn blue" id="prepareId1" onclick="return updateData('./preparedDataUpdate');">update</button>
																	<button name="cmriIssueAdd"  type="submit" class="btn blue" id="differeanceId1" onclick="return updateData('./differenceDataUpdate');">update</button>	
																	<button name="cmriIssueAdd"  type="submit" class="btn blue" id="tallyId1" onclick="return updateData('./tallyDataUpdate');">update</button>
																	
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
													<form:form action="./dumpedDataRefresh" modelAttribute="cmriManager" commandName="cmriManager" method="post" id="myForm2">
													
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
																					<button class="btn default" type="button" id="nine" > <i class="fa fa-calendar"></i></button>
																					</span>
																					</div></td>
															
															</tr>
																						
															</table>
																		
															</div>																																	
																 <div class="modal-footer">		
															
																<div class="btn-group pull-right">
																	<button name="cmriIssueAdd"  type="submit" class="btn blue" id="refreshId" onclick="return getData('./dumpedDataRefresh');">Refresh</button>
																	<button name="cmriIssueAdd"  type="submit" class="btn blue" id="preparedValues" onclick="return getData('./alreadyPrepared');">GetPreparedData</button>
																	<button name="cmriIssueAdd"  type="submit" class="btn blue" id="differenceValues" onclick="return getData('./dumpingDifference');">GetDifferenceData</button>
				 													<button name="cmriIssueAdd"  type="submit" class="btn blue" id="statusValues" onclick="return getData('./dumpedStatusData');">GetStatusData</button>
																	<button name="cmriIssueAdd"  type="submit" class="btn blue" id="tallyValues" onclick="return getData('./alreadyDumped');">GetTallyData</button>
																	
																	
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