
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		
		var elements = document.getElementsByName("meternumber");
	    for (var i = 0; i < elements.length; i++) {
	        elements[i].oninvalid = function(e) {
	            e.target.setCustomValidity("");
	            if (!e.target.validity.valid) {
	                e.target.setCustomValidity("Please Enter Meter Number");
	            }
	        };
	        elements[i].oninput = function(e) {
	            e.target.setCustomValidity("");
	        };
	    }
	    
		App.init();
		TableManaged.init();
		FormComponents.init();			
		$('#MDMSideBarContents,#search').addClass('start active ,selected');
		$("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	});
  
  

	 function myClearFunction() {
		   $("#metrno").val('');
		   $("#newseal").val('');
		   $("#accno").val('').focus();
		}
	  
	 
	function  myValidate(form)
	{
		   var accnum=document.getElementById("accno").value;//form.accno.value;
		   var metrnum=form.metrno.value;
		   var newseal=form.newseal.value;
		   var kno=form.knoId.value;
		  
		   
		   var regex = /^[a-zA-Z ]*$/;
	
			if(accnum == null || accnum == "")
			{
				if(metrnum == null || metrnum == "")
				{
					if(newseal==null || newseal== "")
						{
						
						if(kno==null || kno== "")
							{
								bootbox.alert('Atleast Enter either account No or Meter No or New Seal no or KNo... ');
								return false;
							}
						
						else
							{
							  $("#searchMeterMaster").attr("action","./searchMeterMaster3");
							}
						}
					else
						{
						//alert("/searchMeterMaster2");
						$("#searchMeterMaster").attr("action","./searchMeterMaster2");
						}
				
				}
			    else
			    {
			    	//alert("/searchMeterMaster1");
				$("#searchMeterMaster").attr("action","./searchMeterMaster1");
			     }
				
				
			} 
			else
			{
				//alert("/searchMeterMaster");
				$("#searchMeterMaster").attr("action","./searchMeterMaster");
			}
			
		/* 	if(accnum.match(regex))
			{
				bootbox.alert('Only alphabets are not allowed in accno no   ');
				return false;
			}
	   
			 if(/[^a-zA-Z0-9]/.test(accnum))
		    {
		     bootbox.alert('Special characters are not allowed ');
			 return false;
		    }
	  
	         if(/[^a-zA-Z0-9]/.test(metrnum))
	    		{
	    			 bootbox.alert('Special characters are not allowed in meter no  ');
					 return false;
			    }  */
	}
//	var t = new ScrollableTable(document.getElementById('myScrollTable'), 150,250);
</script>
<div class="page-content" >

<div class="row">
				<div class="col-md-12">
				
				<!-- Display Failure Message -->
		           
		           
		           <c:if test = "${not empty msg}">
			         <div class="alert alert-danger display-show">
					 <button class="close" data-close="alert"></button>
					 <span style="color:red" >${msg}</span>
			       </div>
	                </c:if>
	                
	                	
		          <!-- End Failure Message -->
							
				
				
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
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
								<form:form action=""  method="post" id="searchMeterMaster" modelAttribute="metermasterSearch" commanName="metermasterSearch">
								
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
								       <td><form:input path="accno" autocomplete="off" name="accno" id="accno" class="form-control" value="${accno}"></form:input>
								       
										  <tr>
										  <td>Meter No</td>
									      <td><form:input path="metrno" autocomplete="off" type="text" class="form-control" name="metrno" id="metrno" value="${metrno}" maxlength="12"></form:input>
									      <span id="meternocheck" style="color:red;font-weight:bold;"></span>
									      </td>
										  <td hidden="true">New Seal No</td>
									      <td hidden="true"><form:input path="newseal" type="text" class="form-control" name="newseal" id="newseal" value="${newseal}" maxlength="12"></form:input>
									     <!--  <span id="meternocheck" style="color:red;font-weight:bold;"></span> -->
									      </td>
										
										 <td hidden="true">KNo</td>
									      <td hidden="true"><form:input path="kno" type="text" class="form-control" name="knoId" id="knoId" value="${kno}" maxlength="12"></form:input>
									      </td>
									      
										  </tr>
										  
										  </table>
										  <br>
										  <br>
										    <table>
										      <tr class="trcls">
										          <td class="tdcls1">Circle&nbsp;&nbsp;:</td> 
						                      <td class="tdcls2">&nbsp;&nbsp;${masterdata.circle}</td>
						                       <td class="tdcls1">Division&nbsp;&nbsp;:</td>
										      <td class="tdcls2">&nbsp;&nbsp;${masterdata.division}</td>
										       <td class="tdcls1">Sdocode&nbsp;&nbsp;:</td>
										      <td class="tdcls2">&nbsp;&nbsp;${masterdata.sdocode}</td>
										      </tr>
										      
										      <tr>
										        <td class="tdcls1">SDONAME&nbsp;&nbsp;:</td> 
						                      <td class="tdcls2">&nbsp;&nbsp;${masterdata.sdoname}</td>
						                         <td class="tdcls1">MNP&nbsp;&nbsp;:</td>
										      <td class="tdcls2">&nbsp;&nbsp;${masterdata.mnp}</td>
										       <td class="tdcls1">KNO&nbsp;&nbsp;:</td>
										      <td class="tdcls2" >&nbsp;&nbsp;${masterdata.kno}</td>
										      </tr>
										      
										      <tr>
										       <td class="tdcls1">Name&nbsp;&nbsp;:</td>
										       <td class="tdcls2">&nbsp;&nbsp;${masterdata.name}</td>
						                       <td class="tdcls3">Phone No&nbsp;&nbsp;:</td>
										       <td class="tdcls4" >&nbsp;&nbsp;${masterdata.phoneno2 }</td>
						                       <td class="tdcls1">Mobile No &nbsp;&nbsp;:</td>
						                       <td class="tdcls2">&nbsp;&nbsp;${masterdata.phoneno }</td>  
										      
										      <tr>
										      <td class="tdcls3">TADESC&nbsp;&nbsp;:</td>
										      <td class="tdcls4">&nbsp;&nbsp;${masterdata.tadesc}</td>
										       <td class="tdcls1">MTR Make&nbsp;&nbsp;:</td>
						                       <td class="tdcls2">&nbsp;&nbsp;${mtrmake}</td>
						                         <td class="tdcls3">MTRTYPE&nbsp;&nbsp;:</td>
										      <td class="tdcls4">&nbsp;&nbsp;${mtrtype}</td>
										      </tr>
										  
										  
										  <tr>
										   <td class="tdcls3">TN&nbsp;&nbsp;:</td>
										      <td class="tdcls4">&nbsp;&nbsp;${masterdata.tn}</td>
										        <td class="tdcls1">Status&nbsp;&nbsp;:</td>
						                       <td class="tdcls2">&nbsp;&nbsp;${masterdata.consumerstatus}</td>
						                         <td class="tdcls1">CD&nbsp;&nbsp;:</td>
										      <td class="tdcls2">&nbsp;&nbsp;${masterdata.contractdemand}</td>
										  </tr>
										  
										  <tr>
										   <td class="tdcls3">KWORHP&nbsp;&nbsp;:</td>
										      <td class="tdcls4">&nbsp;&nbsp;${masterdata.kworhp}</td>
										      <td class="tdcls3">SANLOAD&nbsp;&nbsp;:</td>
										      <td class="tdcls4">&nbsp;&nbsp;${masterdata.sanload}</td>
										         <td class="tdcls3">SUPPLYVOLTAGE&nbsp;&nbsp;:</td>
										      <td class="tdcls4">&nbsp;&nbsp;${masterdata.supplyvoltage}</td>
										  </tr>
										  
										  <tr>
										  <td class="tdcls1">MF&nbsp;&nbsp;:</td>
						                       <td class="tdcls2">&nbsp;&nbsp;${data}</td>
						                          <td class="tdcls3">MRNAME&nbsp;&nbsp;:</td>
										      <td class="tdcls4">&nbsp;&nbsp;${masterdata.mrname}</td>
										      <td class="tdcls3">SUPPLYTYPE&nbsp;&nbsp;:</td>
										      <td class="tdcls4">&nbsp;&nbsp;${masterdata.supplytype}</td>
										  </tr>
										  
										  <tr>
										
										       <td class="tdcls3">TarrifCode&nbsp;&nbsp;:</td>
						                       <td class="tdcls4">&nbsp;&nbsp;${masterdata.tariffcode}</td>
						                      
						                       <td class="tdcls3">INDUSTRYTYPE&nbsp;&nbsp;:</td>
						                       <td class="tdcls4">${masterdata.industrytype}</td>
						                        <td class="tdcls3" style="padding-top: 7px;">Address&nbsp;&nbsp;:&nbsp;&nbsp;</td> 
						                       <td class="tdcls4" >${masterdata.address1}</td>
										  </tr>
										  
										   <%-- <tr>
										
										       <td class="tdcls3">Read By&nbsp;&nbsp;:</td>
						                       <td class="tdcls4" style="color:red;">&nbsp;&nbsp;<b>${ReadBy}</b></td>
						                      
						                       <td class="tdcls3"></td>
						                       
						                       <td class="tdcls4"></td>
						                        <td class="tdcls3"></td> 
						                       <td class="tdcls4" ></td>
										  </tr> --%>
										  
										      </table>
										</div>																																	
											
										<div class="modal-footer">
												<button name="search"  onclick="return myValidate(this.form)" type="submit" class="btn green" id="getSearch" >Search</button>
												<button name="searchClear" onclick="myClearFunction()" type="button" class="btn green">Clear</button>
		                                   </div>
							              </div>
									
								</form:form>
									
								
							
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
					<!-- BEGUN METERMASTER TABLE DATA -->
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
					<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li id="print"><a href="#">Print</a></li>										
										<li><a href="#" onclick="tableToExcel('sample_3','Export Meter Data');">Export to Excel</a></li>
										<!--  -->
									</ul>
								</div>
								<BR><BR>
					
					<table class="table table-striped table-hover table-bordered" id="sample_3" >
									<thead>									
										<tr>								
												<th hidden="true">#</th>
											    <th>READING MONTH</th>
												<th>MCST</th>
												<th>ACCNO</th>
												<th>METERNO</th>
												<th>READINGDATE</th>
												<th>CURRENT KWH</th>
												<th>CURRENTKVAH</th>
												<th>CURRENT KVA</th>
												<th>PF</th>
												<th>READING REMARK</th>
												<th>REMARK</th>
												<th>MRNAME</th>
												<th>RTC</th>
												 <th>UNITSKWH</th>
												 <th>UNITSKVAH</th>
												 <th>OLDSEAL</th>
												<th>NEWSEAL</th>
												<th>CMRI</th>
												<th>MRNIO</th>
												<th>DNAME</th>
												<th>XMLDATE</th>
												<th>XCURRDNGKWH</th>
											     <th>XCURRDNGKVAH</th>
											    <th>XCURRDNGKVA</th> 
												<th>XPF</th>
												<th>PREVMETERSTATUS</th>
												<th>KWHE</th>
												<th>KVHE</th>
												<th>KVAE</th>
												<th>PFE</th>
												
												
												
	            						</tr>
									</thead>
									<tbody>
																		
									<c:forEach var='meter' items='${meterMasterData}'>
										 	<tr id="sampel" class="odd gradeX">	
										 	<td hidden="true"></td>		
											 	 <td><c:out value="${meter.rdngmonth}"/></td>
											 	<td><c:out value="${meter.mcst}"/></td>
											 	<td><c:out value="${meter.accno}"/></td>
											    <td><c:out value="${meter.metrno}"/></td>
											    <td><fmt:formatDate pattern="dd-MMM-yy" value="${meter.readingdate}" /></td>
											     <td><c:out value="${meter.currdngkwh}"/></td>
											     <td><c:out value="${meter.currrdngkvah}"/></td>
											    <td><c:out value="${meter.currdngkva}"/></td>
											    <td><c:out value="${meter.pf}"/></td>
											       <td><c:out value="${meter.readingremark}"/></td>
											    <td><c:out value="${meter.remark}"/></td>
											    
											   	
											     <td><c:out value="${meter.mrname}"/></td>
											    
											     <c:set var = "rtc" scope = "page" value = "${meter.rtc}"/>
                                                 <c:if test = "${rtc == 'true'}">
                                                 <td><c:out value="1"/></td> 
                                                 </c:if>
                                                 
                                                 <c:set var = "rtc" scope = "page" value = "${meter.rtc}"/>
                                                 <c:if test = "${rtc == 'false'}">
                                                 <td><c:out value="0"/></td> 
                                                 </c:if>
                                                
      
											    <td><c:out value="${meter.unitskwh}"/></td>
											     <td><c:out value="${meter.unitskvah}"/></td>
											     
											    <td><c:out value="${meter.oldseal}"/></td>
											    <td><c:out value="${meter.newseal}"/></td>
											     <td><c:out value="${meter.cmri}"/></td>
											    
											     <td><c:out value="${meter.mrino}"/></td>
											   
											     <td><c:out value="${meter.dname}"/></td>
											       <td><fmt:formatDate pattern="dd-MMM-yy" value="${meter.xmldate}" /></td>
											       <td><c:out value="${meter.xcurrdngkwh}"/></td>
											    <td><c:out value="${meter.xcurrrdngkvah}"/></td>
											    <td><c:out value="${meter.xcurrdngkva}"/></td>
											    <td><c:out value="${meter.xpf}"/></td>
											    
											    <td><c:out value="${meter.prevmeterstatus}"/></td>	
											     <td><c:out value="${meter.kwhe}"/></td>	
											      <td><c:out value="${meter.kvhe}"/></td>	
											       <td><c:out value="${meter.kvae}"/></td>	
											        <td><c:out value="${meter.pfe}"/></td>	
											
											    
											    
											  
											    <td><c:out value="${meter.xmldate}"/></td>
											  
											     
											     
											 	
											 	
										 	</tr>
										 </c:forEach>								 									 
												
									</tbody>
								</table>
								</div>
								</div> --%>
								</div>
								
								
								
								
						
					<!-- END METERMASTER TABLE DATA -->
				</div>
				
				
				<!--  newwwwwwwwwwwwww-->
				
				<%-- <div class="row">
				<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Search2</div>
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
											
						<div class="portlet-body">
					
					<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li id="print"><a href="#">Print</a></li>										
										<li><a href="#" onclick="tableToExcel('sample_4','Export Meter Data');">Export to Excel</a></li>
										<!--  -->
									</ul>
								</div>
								<BR><BR>
					
					<div class="col-md-12"  style="overflow: scroll;">
					<table class="table table-striped table-bordered table-hover" id="sample_4" >
									<thead>									
										<tr>								
												<th hidden="true">#</th>
											    <th>READING MONTH</th>
												
												<th>ACCNO</th>
												<th>METERNO</th>
												<th>XMLDATE</th>
											    <th>XCURRDNGKWH</th>
											    <th>XCURRDNGKVAH</th>
											    <th>XCURRDNGKVA</th> 
												<th>XPF</th>
												<th>KWHE</th>
												<th>KVHE</th>
												<th>KVAE</th>
												<th>PFE</th>
												
												
	            						</tr>
									</thead>
									<tbody>
																		
									 <c:forEach var='meter' items='${meterMasterData}'>
										 	<tr id="sampelNew" class="odd gradeX">	
										 	<td hidden="true"></td>		
											 	 <td><c:out value="${meter.rdngmonth}"/></td>
											 	<td><c:out value="${meter.accno}"/></td>
											    <td><c:out value="${meter.metrno}"/></td>
											    <td><c:out value="${meter.xmldate}"/></td>
											    
											    <td><c:out value="${meter.xcurrdngkwh}"/></td>
											    <td><c:out value="${meter.xcurrrdngkvah}"/></td>
											    <td><c:out value="${meter.xcurrdngkva}"/></td>
											    
											    <td><c:out value="${meter.xpf}"/></td>
											    <td><c:out value="${meter.kwhe}"/></td>
											   
											    <td><c:out value="${meter.kvhe}"/></td>
											    <td><c:out value="${meter.kvae}"/></td>
											     <td><c:out value="${meter.pfe}"/></td>
											     
											     
											 	
											 	
										 	</tr>
										 </c:forEach>	 						 									 
												
									</tbody>
								</table>
								</div>
								</div>
								</div>
								
								
				</div> --%>
				
			</div>
			
			
			<style>
	
	.tdcls1
	{
 padding-bottom:15px;
	width:10%;
	text-align: right;
	font-weight:bold;
	}
	
	.tdcls2
	{
 padding-bottom:15px;
	width:40%;
	/* text-align: left; */
	text-align: justify;
	}
	
		.tdcls3
	{
 padding-bottom:15px;
	width:10%;
	text-align: right;
	font-weight:bold;
	}
	
		.tdcls4
	{
 padding-bottom:15px;
	width:40%;
	/* text-align: left; */
	text-align: justify;
	}
	

	
	</style>	
			
			
			