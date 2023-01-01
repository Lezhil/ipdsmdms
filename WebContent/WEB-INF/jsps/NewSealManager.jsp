<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <!-- <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script> -->
  <script src="<c:url value='/resources/assets/scripts/sweetalert.min.js' />" type="text/javascript"></script>
 <script  type="text/javascript">

  
  $(".page-content").ready(function()
   	    	   {     
	              App.init();
   	    	   	  FormDropzone.init();
   	    		  FormComponents.init();
      	    	  TableManaged.init();
      	    	  TableEditable.init();
   	   	    	 
   	   	    	  
   	    	   $('#newSealManager').addClass('start active ,selected');
   	    	   $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#seal-Manager,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
   	    	
   	    	 $('#selectIssuedDate,#selectReceiveDate,#selectReturnDate,#selectReturnDate3').val(moment(new Date()).format("MM/DD/YYYY"));



   	    	if('${sealPdf}'=='yes')
	    		  {	   	    		 
	    		    $('#sealPdfData').attr('hidden',false);
	    		   $('#sealPdf').tableExport({fileName:'${docName}',type:'pdf',tableId:'sealPdf',divId:'sealPdfData',condition:'custom'});	
              $('#sealPdfData').attr('hidden',true);
	    		  }
	    	  if('${mrwiseSealList.size()}'>0)
	    		  {
                $("#sealSummaryDivId").show();
                loadSearchAndFilter('table_4');
	    		  }
	    	 if('${mrwiseSealReturnList.size()}'>0)
		  {
           $("#sealReturnDivId").show();
		  }

	    	 getMaxCardNo1(1);  

	    	   });
  

  
	</script>
  
  
  
<div class="page-content" >

<c:if test = "${not empty result}">
			<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color:red" >${result}</span>
			</div>
			
	    </c:if>	
	    
	<!-- BEGIN PAGE CONTENT-->
	
			 <div class="alert alert-success display-show" id="alertMsg" style="display: none;">
							<button class="close" data-close="alert"></button>
							 <span style="color:green" id="parseCompleteMsg"></span> 
				</div>
			<!-- END PAGE CONTENT-->
<div class="clearfix"></div>
<div class="row">
<div class="col-md-12">
					<!-- BEGIN PROGRESS BARS PORTLET-->
			<div class="portlet box light-grey">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-cogs"></i>SEAL MANAGER
						
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>

					</div>
					<div id="excelPrintId" hidden="true">
								
							</div>
				</div>
				<div class="portlet-body">
							<div class="table-toolbar">
								
								<div class="btn-group">	
									<button id="sample_editable_1_new1" class="btn green"  data-toggle="modal" data-target="#stack1">
									AccNo Updated Seal 
									</button>
								</div>
								
								 <div class="btn-group">	
									<button id="sample_editable_1_new2" class="btn green"  data-toggle="modal" data-target="#stack2">
									MR Wise Seal Transfer
									</button>
								</div>
								

								 <div class="btn-group">	
									<button id="sample_editable_1_new3" class="btn green" onclick="location.href = './mrWisePending';">
									MR Wise Pending Data
									</button>
								</div>
								

								 <div class="btn-group">	
									<button id="sample_editable_1_new3" class="btn green"  data-toggle="modal" data-target="#stack3">
									Seal Issue to MR
									</button>
								</div>

								<div class="btn-group">
									<button id="sample_editable_1_new2" class="btn green"
										data-toggle="modal" data-target="#stack4">Seal Inward</button>
								</div>

								<div class="btn-group">
									<button id="sample_editable_1_new3" class="btn green"
										data-toggle="modal" data-target="#stack5">Return Entry
									</button>
								</div>
						
						
						     <div class="btn-group">	
									<button id="sample_editable_1_new6" class="btn green"  data-toggle="modal" 
									data-target="#stack6">
									Report
									</button>
								</div>
								
								
						<!-- <div class="btn-group">	
									<button id="sample_editable_1_new3" class="btn green"  data-toggle="modal" data-target="#stack3">
									Return Entry
									</button>
								</div>
								
								<div class="btn-group">	
									<button id="sample_editable_1_new4" class="btn green"  onclick="window.location.href='./showBunches'">
									Show bunches
									</button>
								</div>
								
								<div class="btn-group">	
									<button id="sample_editable_1_new5" class="btn green"  data-toggle="modal" data-target="#stack4">
									Report
									</button>
								</div> -->
																
						</div>
					
					
					<c:if test="${value eq 'bunches'}">
					<table class="table table-striped table-bordered table-hover" id="sample_2">
								<thead>									
									<tr>
										<th>CardSlNo1</th>
										<th>No. of Seals</th>
								 </tr>
								</thead>
								<tbody>
									
									<c:forEach items="${allBunches}" var="element">
									 	<tr>									 		
										 	<td>${element[0]}</td>
										 	<td>${element[1]}</td>
									 	</tr>
									 </c:forEach>	
								</tbody>
							</table> 
				    </c:if>
				    <div id="sealPdfData" hidden="true">
				              <table id="sealPdf" border="1px" > <!--  -->
								<thead>									
									<tr>
										<th>Date:<fmt:formatDate pattern="dd/MM/YYYY" value="${pdfData[0].iDate}" /></th>
										<th>Name:${pdfData[0].mrname}</th>
										<th>Division:${pdfData[0].dvision}</th>
										<th>CardNo:${pdfData[0].cardSealNo}</th>
								 </tr>
								 <tr>
										<th>SEALNo</th>
										<th>ACCNo</th>
										<th>METERNo</th>
										<th>SDOCODE</th>
								 </tr>
								</thead>
								<tbody>
									
									<c:forEach items="${pdfData}" var="element">
									 	<tr>								 		
										 	<td>${element.sealNo}</td>
										 	<td>  </td>
										 	<td>  </td>
										 	<td>  </td>
									 	</tr>
									 </c:forEach>	
								</tbody>
							</table> 
							</div>
							
							<div id="sealPdfDataPrint" hidden="true">
				              <table id="sealPdfPrint" border="1px" > <!--  -->
								<thead>									
									<tr>
										<th>Date:<span id="printDate"></th>
										<th>Name:<span id="printName"></th>
										<th>Division:<span id="printDivision"></th>
										<th>CardNo:<span id="printCardNo"></th>
								 </tr>
								 <tr>
										<th>SEALNo</th>
										<th>ACCNo</th>
										<th>METERNo</th>
										<th>SDOCODE</th>
								 </tr>
								</thead>
								<tbody id="printPreviewData">
									
									
								</tbody>
							</table> 
							</div>
							
							<div id="mrwiseSealNoPdfdata" hidden="true">
				              <table id="sample_editable_1" border="1px" > 
								<thead>										
								 <tr>
										<th>SEAL NUMBER</th>
										<th>Account No</th>
										<th>Meter No</th>
										<th>Billmonth</th>
										<th>MR Name</th>
										<th>Issued by</th>
								 </tr>
								</thead>
								<tbody id="mrwiseSealNoData">
									
									
								</tbody>
							</table> 
							</div>
							
				    </div>
				    
				    
				    <c:if test = "${Updated eq 'Updated Successfully'}"> 			        
			        <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${Updated}</span>
						</div>
			         </c:if>
			         
			          <c:if test = "${Notupdated eq 'Not Updated'}"> 			        
			        <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${Notupdated}</span>
						</div>
			         </c:if>
			         
			    
			    
				    <!--Account and Meterno Updated to Seal  -->
				    <c:if test = "${Updated eq 'MR Wise Total Records Not Found...'}"> 			        
			        <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${mrTotalError}</span>
						</div>
			    </c:if>
				    <div id="stack1" class="modal fade" tabindex="-1" data-width="500" data-backdrop="static" data-keyboard="false">
					   <div class="modal-dialog">
					      <div class="modal-content">
					      <div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
								<h4 class="modal-title"> AccNo and MeterNo Updated To Seal</h4>
										</div>
					         <div class="modal-body">
					        
					            <div class="row">
					              
					              <div class="col-md-12">
					            
					              	 <form:form action="./updateNewSeal"  modelAttribute="Newseal" commandName="Newseal" method="post" id="sealAccUpdate" name="sealAccUpdate"  >
											
								 <table>
									
										<tr>
										<td>RDNGMONTH</td>	
								<td>	
								<div data-date-viewmode="years" data-date-format="yyyymm"
							class="input-group input-medium date date-picker">
							<form:input type="text" path="" name="rdngmonth1" id="rdngmonth1" value="${readingMonthSeal}"
								class="form-control" required="required" readonly="readonly" ></form:input>
							<span class="input-group-btn">
								<button type="button" class="btn default">
									<i class="fa fa-calendar"></i>
								</button> 
							</span>
						</div>
								</td>
										
										</tr>
									
									   <tr>
										  
										 <td>ACCNO:<span style="color: red">*</span></td>
									     <td><form:input type="text" class="form-control" required="required" name="accno" id="accno" onchange="return getSealByAcc();" maxlength="13" placeholder="Enter Account No" path=""></form:input></td>
								         </tr>
										
										<tr>
										
										<td>METERNO:<span style="color: red">*</span></td>
										 <td> <form:input path="" type="text" class="form-control" name="meterno" id="meterno" onchange="return getSealByMtr();"></form:input></td>
									   </tr>
									   
									 <tr>
										
										<td>NEW Seal No:</td>
										 <td> <form:input path="newseal" type="text" class="form-control" name="newsealNum" id="newsealNum"></form:input></td>
									   </tr>
									   
									    <tr hidden="true">
										<td>MR Name:</td>
										 <td> <form:input path="" type="text" class="form-control" name="mrnameId" id="mrnameId"></form:input></td>
									   </tr>
									   
										
									</table> 					
												
							                 <div align="right">	
							                 <!-- <button  class="btn blue "  onclick="return sealOutwarValidation(this.form,'update','./sealOutWard')">Update</button>	 -->		
							                 <button type="submit" class="btn blue "  >Update</button>									 
												<button type="button" data-dismiss="modal" class="btn ">Close</button>
												</div>
										</form:form>
					              </div>
					           </div>
					       </div>
					   </div>
					  </div>
					</div>
					
					
					<!--MrWise Update  -->
					<div id="stack2" class="modal fade" tabindex="-1" data-width="500" data-backdrop="static" data-keyboard="false">
					   <div class="modal-dialog">
					      <div class="modal-content">
					      <div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
								<h4 class="modal-title"> MR-Wise Seal Transfer For Next Month</h4>
										</div>
					         <div class="modal-body">
					        
					            <div class="row">
					              
					              <div class="col-md-12">
					            
					              	 <form:form action=""  modelAttribute="Newseal" commandName="Newseal" method="post" id="sealAccUpdate" name="sealAccUpdate"  >
											
								 <table>
											
									
									   <tr>
									    <td>Circle</td>
										   <td><form:select path="" name="circle" class="form-control" id="circle" onchange="return getmrname(this.value);">
										     <form:option value="0">select </form:option> 
										      <c:forEach items="${circle}" var="element">
													<form:option value="${element}">${element}</form:option>
													</c:forEach>	
										                                                   
										   </form:select></td> 
										   </tr>
									   <tr>
										   <td>MRName</td>
										   <td id="mrdiv"><form:select path="" name="MrNameDiv" class="form-control" id="MrNameDiv">
										     <form:option value="0">Select Mrname</form:option> 
										     <%--  <c:forEach items="${divisions}" var="element">
													<form:option value="${element}">${element}</form:option>
													</c:forEach>	 --%>
										                                                   
										   </form:select>  </td> 
									   </tr> 
										<tr>
										
										<td>FROM</td>	
								<td>	
								<div data-date-viewmode="years" data-date-format="yyyymm"
							class="input-group input-medium date date-picker">
							<form:input type="text" path="rdngmonth" name="Fromrdngmonth1" id="Fromrdngmonth1"
								class="form-control" required="required" readonly="readonly" ></form:input>
							<span class="input-group-btn">
								<button type="button" class="btn default">
									<i class="fa fa-calendar"></i>
								</button> 
							</span>
						</div>
								</td>
										</tr>
										<tr>
										<td>TO</td>	
								<td>	
								<div data-date-viewmode="years" data-date-format="yyyymm"
							class="input-group input-medium date date-picker">
							<form:input type="text" path="rdngmonth" name="Tordngmonth1" id="Tordngmonth1"
								class="form-control" required="required" readonly="readonly" ></form:input>
							<span class="input-group-btn">
								<button type="button" class="btn default">
									<i class="fa fa-calendar"></i>
								</button> 
							</span>
						</div>
								</td>
										</tr>
									   <tr>
										 <td>TOTAL
									     <td><form:input type="text" class="form-control" required="required" name="totalSeal" id="totalSeal"  maxlength="12" placeholder="Total No Of seal" path=""></form:input></td>
									     <td>
									     <button type="button" class="btn blue " onclick ="return getTotalNoSeal();" >Seal Count</button>
									     </td>
								         </tr>
										<tr>
										<td>
										<br/>
										</td>
										</tr>
										
									</table> 					
							                 <div align="right">	
							                 <!-- <button  class="btn blue "  onclick="return sealOutwarValidation(this.form,'update','./sealOutWard')">Update</button>	 -->		
							                 <button type="button" class="btn green "  onclick="return validationSingle();">Single Transfer to next month</button>									 
												<button type="button" data-dismiss="modal" id="close"class="btn ">Close</button>
												</div>
										</form:form>
					              </div>
					           </div>
					       </div>
					   </div>
					  </div>
					</div>




<!-- -------------------------------------------------- -->

	<!--Seals Issue To MR  -->
					<div id="stack3" class="modal fade" tabindex="-1" data-width="500" data-backdrop="static" data-keyboard="false">
					   <div class="modal-dialog">
					      <div class="modal-content">
					      <div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
								<h4 class="modal-title"> Seals Issue To Meter Reader</h4>
										</div>
					         <div class="modal-body">
					        
					            <div class="row">
					              
					              <div class="col-md-12">
					            
					              	 <form:form action=""  modelAttribute="Newseal" commandName="Newseal" method="post" id="sealAccUpdate" name="sealAccUpdate"  >

											<table id="sealIssueTabId">

<tr>
													<td>Reading Month</td>
													<td>
														<div class="input-group input-medium date date-picker" data-date-viewmode="years" data-date-format="yyyymm"
															  data-date-minviewmode="months">
															<form:input path="rdngmonth" type="text" class="form-control"
																name="readingMonth" id="readingMonth" value="${rdngMonthSealissue}"></form:input>
															<span class="input-group-btn">
																<button class="btn default" type="button">
																	<i class="fa fa-calendar"></i>
																</button>
															</span>
														</div>
													</td>
												</tr>

												<tr>
													<td>Circle</td>
													<td><form:select path="" name="circleId"
															class="form-control" id="circleId"
															onchange="return getmrnameForSealIssue(this.value);">
															<form:option value="0">select </form:option>
															<c:forEach items="${circle}" var="element">
																<form:option value="${element}">${element}</form:option>
															</c:forEach>

														</form:select></td>
												</tr>

												<tr>
													<td>MRName</td>
													<td id="mrdiv"><form:select path="" name="MrNameId"
															class="form-control" id="MrNameId">
															<form:option value="0">Select Mrname</form:option>
														</form:select></td>
												</tr>

												<tr>
													<td>Seal No. From</td>
													<td><form:input path="" type="text"
															class="form-control" name="sealNum" id="sealNoId1" onkeyup="return makeUppercase1();"></form:input></td>
												</tr>

												<tr>
													<td>Seal No. To</td>
													<td><form:input path="" type="text"
															class="form-control" name="sealTo1" id="sealTo1Id" onkeyup="return makeUppercase2();"
															onchange="return onEnterForSeal(this.form,'sealNoId1',this.id,'total1');"></form:input></td>
												</tr>

												<tr>
													<td>Total.</td>
													<td><input type="text" class="form-control"
													name="total1" id="total1"></td>
												</tr>


												<tr>
												
												<tr>
													<td>Reading Date</td>
													<td>
														<div class="input-group input-medium date date-picker" 
															data-date-format="yyyy-mm-dd" data-date-viewmode="years" data-date-minviewmode="months">
															<form:input path="" type="text" class="form-control" value="${readingDate}"
																name="fromdate" id="fromdate"></form:input>
															<span class="input-group-btn">
																<button class="btn default" type="button">
																	<i class="fa fa-calendar"></i>
																</button>
															</span>
														</div>
													</td>
												</tr>
												
												
												


												<tr>
													<td><br /></td>
												</tr>

											</table>
											<div align="right">	
							                 <!-- <button  class="btn blue "  onclick="return sealOutwarValidation(this.form,'update','./sealOutWard')">Update</button>	 -->		
							                 <button type="button" class="btn green "  onclick="return validateNewSealIssue();">Update New Seal Issue</button>									 
												<button type="button" data-dismiss="modal" id="close"class="btn ">Close</button>
												</div>
										</form:form>
					              </div>
					           </div>
					       </div>
					   </div>
					  </div>
					</div>
					
					
					
					
<!-- 	--------------------------------------------------------------------- -->

<!-- Begin SEAL INWARD -->

<div id="stack4" class="modal fade" tabindex="-1" data-width="500">
					   <div class="modal-dialog">
					      <div class="modal-content">
					         <div class="modal-body">
					            <h4 class="modal-title">New Seal Entry</h4>
					            <hr></hr>
					            <div class="row">
					              
					              <div class="col-md-12">
					            
					              	 <form:form action="./pushAllseal" modelAttribute="sealManager" commandName="sealManager"  method="post" id="sealManagerId">
											
											<table>
										<tr><td>Seal Received Date </td>
										
											<td><div class="input-group input-medium date date-picker"  id="five">
																<form:input path="rDate" type="text" class="form-control" name="selectReceiveDate" id="selectReceiveDate" ></form:input>
																<span class="input-group-btn">
																<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></td>
										
										</tr>
									
									  <tr hidden="true"><td>RdgMonth</td>
											<td>
											<form:input path="billmonth" type="text" class="form-control" name="billmonthInward" id="billmonthInward" readonly="true"></form:input>
											</td>
										</tr>
										
									     <tr>
										  <td>seal From</td>
										  <td><form:input path="sealNo" type="text" class="form-control" name="sealNo" id="sealNoIdInward"></form:input></td>
									      	</tr>
									    <tr>
									    <td>seal To</td>	
									      <td><form:input path="mrname" type="text" class="form-control" name="sealToInward" id="sealToInward" onblur="return onEnterInward(this.form,'sealNoIdInward',this.id,'totalIdInward');" ></form:input></td><!-- onkeypress="if(event.keyCode==13) return onEnter('sealNoId',this.id,'totalId');" -->
									    
									    </tr>
									    <tr>
									    <td>Total</td>
										    <td><input type="text" class="form-control" name="totalInward" id="totalIdInward"></td>
									    </tr>
										
									</table>
																	
												
							                 <div class="modal-footer">
													 <button type="button" data-dismiss="modal" class="btn">Close</button>
													 <button type ="submit" class="btn blue pull-right"  onclick="return sealInwarValidation(this.form)">Update</button>
													
												</div>
											</form:form>
									 
					              </div>
					           </div>
					       </div>
					   </div>
					  </div>
					</div>
					
					
					
					<!-- END SEAL INWARD -->
					
					
					<!--BEGUN RETURN ENTRY -->
					
					<div id="stack5" class="modal fade" tabindex="-1" data-width="500">
					   <div class="modal-dialog">
					      <div class="modal-content">
					      
					         
					          <div class="modal-body">
					            <h4 class="modal-title">Return Entry</h4>
					            <hr></hr>
					            
					            <!-- TAB BEGINS -->
					            <div class="tabbable tabbable-custom">
					             <ul class="nav nav-tabs">
									<li class="active"><a href="#tab_1_1" data-toggle="tab">Single Update</a>
									
									</li>
									<li><a href="#tab_1_2" data-toggle="tab">Multi Entry Update</a>
									
									</li>
									
								</ul>
					            
					                 <div class="tab-content">
					                 
					                  <!-- BEGUN FIRST TAB OF SINGLE UPDATE -->
					                  <div class="tab-pane active" id="tab_1_1">
					                  
					                   <form:form action="./singleSealUpdate" modelAttribute="sealManager"  commandName="sealManager" method="post" id="singleUpdate" name="singleSeal">
											
									   <table>	
									    <tr>
										<td>Seal No.</td>
										  <td><form:input path="sealNo" type="text" class="form-control" name="sealNo" id="sealNo2Id" onchange="return getCardSlNo(this.id,'sealCard2',0);"></form:input></td>
									    </tr>
									    <tr>
									    <td>Card No.</td>
									      <td><form:input path="cardSealNo1" type="text" class="form-control" name="sealCard" id="sealCard2" ></form:input></td>
									    </tr>
									         
									      <tr hidden="true"><td>RdgMonth</td>
											<td>
											<form:input path="billmonth" type="text" class="form-control" name="billmonth" id="billmonth" readonly="true"></form:input>
											</td>
										</tr>   
									         
										<tr><td>Return Date </td>
										
											<td><div class="input-group input-medium date date-picker" >
																<form:input path="revDate" type="text" class="form-control" name="selectReturnDate" id="selectReturnDate"  ></form:input>
																<span class="input-group-btn">
																<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></td>
										    </tr>																	
											</table>	
							                 <div class="modal-footer">
													 <button type="button" data-dismiss="modal" class="btn">Close</button>
													<button type ="submit" class="btn blue pull-right"  onclick="return singleValidation(this.form);">Single Update</button>
													
												</div>
											</form:form>
			                             </div>
					                   <!-- END FIRST TAB OF SINGLE UPDATE -->
					                  
					                  <!-- BEGUN SECOND TAB OF MULTIUPDATE -->
					                  <div class="tab-pane" id="tab_1_2">
					                  
					                   
									   <form:form action="./sealMultipleUpdate"  modelAttribute="sealManager" commandName="sealManager" method="post" id="multiUpdate" name="multipleSeal">
									   
									   <table>
										<tr>
										  <td>Seal No. From</td>
										  <td><form:input path="sealNo" type="text" class="form-control" name="sealNo" id="sealNo3"></form:input></td>
									      
									    </tr>
									    
									    <tr>
									    <td>Seal No. To</td>
									    <td><form:input path="cmri" type="text" class="form-control" name="sealTo3" id="sealTo3" onchange="return onEnterInward(this.form,'sealNo3',this.id,'total3');"></form:input></td>
									    </tr>
									    
									    <tr>
									      <td>Total.</td>
									      <td><input type="text" class="form-control" name="total3" id="total3"></td>
									      </tr>
									      
									     <tr>
									     <td>Card No</td>
									      <td><form:input path="cardSealNo1" type="text" class="form-control" name="sealCard" id="cardSealNo3"></form:input></td>
									     </tr>
									     
									     <tr hidden="true"><td>RdgMonth</td>
											<td>
											<form:input path="billmonth" type="text" class="form-control" name="billmonth" id="billmonth" readonly="true"></form:input>
											</td>
										</tr>
									    <tr>
									    
									    <td>Return Date </td>
										
											<td><div class="input-group input-medium date date-picker">
																<form:input path="revDate" type="text" class="form-control" name="selectReturnDate" id="selectReturnDate3" ></form:input>
																<span class="input-group-btn">
																<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></td>
									    </tr>
								
								     
										
									</table>
																	
												
							                 <div class="modal-footer">
													 <button type="button" data-dismiss="modal" class="btn">Close</button>
													 <button type ="submit" class="btn blue pull-right" onclick="return MultipleValidation(this.form)">Multi Entry Update</button>
													
												</div>
											</form:form>
					                  
					                  </div>
					              	
									  <!-- END SECOND TAB OF MULTIUPDATE -->
					              </div>
					           </div>
					        <!-- TAB ENDS -->
					       </div>
					   </div>
					  </div>
					</div>
					
					
					
<!-- ----------------------- Report Tab (from Seal Manager) (vijayalaxmi)----------------------------------- -->
				
					<div id="stack6" class="modal fade" tabindex="-1" data-width="400">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true"></button>
									<h4 class="modal-title">Report</h4>
								</div>
								<div class="modal-body">
									<div class="row">
										<div class="col-md-12">
										
			
												<div class="btn-group" >	
													  <button class="btn blue" style="display: block;" id="mrwiseSealSummary"  data-target="#stack7" data-toggle="modal" >Summary</button>
											    </div>
											    <div class="btn-group" >	
													  <button class="btn green" style="display: block;" id="remarksSummary"  onclick="window.location.href='./showReturnSeals'">Returned Seals Summary</button>
											    </div>
											    <div class="modal-footer">
													 <button type="button" data-dismiss="modal" class="btn" id="closeData" >Close</button>
											    </div>

										</div>
									</div>
								</div>
							</div>
						</div>
					</div>	
					
<!-----------------  Report 2nd Tab-------------------------------- -->
					
						<div id="stack7" class="modal fade" tabindex="-1" data-width="500">
					   <div class="modal-dialog">
					      <div class="modal-content">
					         <div class="modal-body">
					            <h4 class="modal-title">Show Summary</h4>
					            <hr></hr>
					            <div class="row">
					              
					              <div class="col-md-12">
					            
					              	 <form:form action="./showMrwiseSealSummary" modelAttribute="sealManager" commandName="sealManager"  method="post" id="mrwiseSealSummaryId">
											
											<table>
										<tr>
														<td>Month</td>
														<td></td>
														<td>														
															<div class="input-group input-medium date date-picker"  data-date-format="yyyymm" data-date-viewmode="years" data-date-minviewmode="months">
																<input type="text" class="form-control" name="billMonth" id="billMonth" value="${rdngMonthSealissue}">
																<span class="input-group-btn">
																<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
																</span>
															</div>														                          
														</td>
													</tr>
									 
										
									</table>
																	
												
							                 <div class="modal-footer">
													 <button type="button" data-dismiss="modal" class="btn">Close</button>
													 <button type ="submit" class="btn blue pull-right">view</button>
												</div>
											</form:form>
									 
					              </div>
					           </div>
					       </div>
					   </div>
					  </div>
					</div>
					
					
<!-- -------------------------- MRWise Seal Summary For (REPORT)------------------------- -->

			<div class="row"  id="sealSummaryDivId"   style="display: none;">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box purple">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>MRWise Seal Summary</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-toolbar">
							</div>
							<table class="table table-striped table-bordered table-hover" id="table_4">
								<thead>
										<tr style="background-color: lightgray; " >
									        <th>SLNO</th>
											<th>MR NAME</th>
											<th>SEAL ISSUED</th>
											<th>SEAL USED</th>
											<th>SEAL DAMAGE</th>
										</tr>
									</thead>
									<tbody>
										<c:set var="count" value="1" scope="page" />
								<c:forEach var="element" items="${mrwiseSealList}">
										<tr class="odd gradeX">
										     <td>${count}</td>											
											<td>${element[0]}</td>
											<td><a href="#"  onclick="return displaySealIssued('${element[0]}','${element[2]}');">${element[1]}</a></td>
											<td><a href="#"  onclick="return displaySealUsed('${element[0]}','${element[2]}');">${element[3]}</a></td>
											<td><a href="#"  onclick="return displaySealDamaged('${element[0]}','${element[2]}');">${element[4]}</a></td>
										</tr>
										<c:set var="count" value="${count + 1}" scope="page"/>
									</c:forEach>
									</tbody> 
							</table>
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
			
			
			<!-- -----------------------  Returned Seals Summary (REPORT)---------------------------------- -->
			
			 <div  id="alertMsg1" >
							
					</div>
					
					<c:if test = "${not empty results1}"> 			        
			        <div class="alert alert-danger display-show">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${results1}</span>
						</div>
			        </c:if>
				<div class="portlet box purple"  id="sealReturnDivId"  style="display: none;" >
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-edit"></i>MRWise Seal Return
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a>
							<a href="javascript:;"
								class="remove"></a>
						</div>
					</div>
					<div class="portlet-body">

						<div class="table-toolbar">
							<div class="btn-group pull-right">
							</div>
						</div>


						<a href="#" id="editbutton"></a>

						<table class="table table-striped table-hover table-bordered"
						id="sample_editable_1">
									<thead>
										<tr style="background-color: lightgray; ">
											<th hidden="true"></th>
									        <th>SLNO</th>
											<th>MR NAME</th>
											<th>PC Seals</th>
											<th></th>
											<th>Paper Seals</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<c:set var="count" value="1" scope="page" />
								<c:forEach var="element" items="${mrwiseSealReturnList}">
										<tr>
										     <td>${count}</td>											
											<td>${element[0]}</td>
											<td><a href="#" data-target="#stack10" data-toggle="modal" onclick="return displaySealReturnedPc('${element[0]}','R');">${element[3]}</a></td>
											<td>
											<label id="rCheckbox">
											<input type="checkbox"  value ="${element[0] }$R" name="multipleCardSealCheck" />
											</label>
											</td>
											<td><a href="#" data-target="#stack10" data-toggle="modal" onclick="return displaySealReturnedPc('${element[0]}','MR');">${element[4]}</a></td>
											<td>
											<label id="mrCheckbox">
											<input type="checkbox"  value ="${element[0] }$MR" name="multipleCardSealCheck"/>
											</label>
											</td>
										</tr>
										<c:set var="count" value="${count + 1}" scope="page"/>
									</c:forEach>
									</tbody>
								</table>
								
					</div>
					<button type ="submit" class="btn blue pull-right"  onclick="return updateMultipleCardSlno1();">Update CardSealNo</button>
				</div>
					
					
					
					
					
	<!-- ------------------------------  MRWise Seal Return ------------------------------- -->
					
							<div id="stack10" class="modal fade" tabindex="-1" data-width="400" data-backdrop="static" data-keyboard="false">
							<div class="modal-dialog" style="width: 600px;" >
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true"></button>
										<h4 class="modal-title">Seal Numbers</h4>
										<div >
										<table >
										<tr >
										 <td>MR NAME</td>
										 <td><input type="text" class="form-control input-medium"  placeholder="" id="mrName" readonly="readonly"></td>
										</tr>
										<tr>
										 <td>CARD SLNO1</td>
										 <td><input type="text" class="form-control input-medium"  placeholder="" id="cardslno1"></td>
										</tr>
										</table>
										</div>
										
									</div>

									<div class="modal-body" >
										<div class="row">
											<div class="col-md-12">
												<form>
													<table class="table table-striped table-hover table-bordered" id="table_3" >
													</table>	
													<div class="modal-footer" >
													 <button type="button" data-dismiss="modal" class="btn" id="closePopUp">Close</button>
													 <button type ="submit" class="btn blue pull-right"  onclick="return updateCardSlno1();">Update</button>
												</div>
												</form>
											</div>
										</div>

									</div>

								</div>
							</div>
						</div>
						<!--End Of RETURN ENTRY -->
						
						
			</div>
			 	
					</div>
					<!-- END PROGRESS BARS PORTLET-->
</div>					

         <div class="row" id="parsedRow" style="display: none;">
          
			
		</div>
				</div>
							
							
<script>

function onEnter(accNum)
{
	//alert("calling onEnter");
	  var regex = /^[a-zA-Z ]*$/;
	  if(accNum.match(regex))
		 {
		 bootbox.alert('Only alphabets are not allowed in account number ');
		 return false;
		 }
	  if(/[^a-zA-Z0-9]/.test(accNum))
		 {
		 bootbox.alert('Special characters are not allowed in accno');
		 return false;
		 }
	  if(accNum.length<12)
		 {
		 bootbox.alert('AccNo should contain atleast 12 characters');
		 return false;
		 }

	  $('#sealAccUpdate').attr('action','./getSealByAccno').submit();
}



function getSealByAcc()
{
	  var accno=$("#accno").val(); 
	 
      var rdngmnth=$("#rdngmonth1").val();

	 $.ajax({
		type : "GET",
		url : "./getSealByAcc",
		data:{accno:accno,rdngmnth:rdngmnth},
		cache : false,
		async : false,
		dataType:'json',
		success : function(response){
			
			if(response==null)
				{
				bootbox.alert("NEW Seal No Not Found For This Acc No");
				}
		for(var i=0;i<response.length;i++)
			{
			
			$("#newsealNum").val(response[0]);
			$("#meterno").val(response[1]);
			$("#mrnameId").val(response[2]);
			}
		}
	}); 
}


function getSealByMtr()
{
var rdngmnth=$("#rdngmonth1").val();

var accno=$("#accno").val();
var meterno=$("#meterno").val();

if(accno=="" && meterno=="")
	  {
	  bootbox.alert('Enter Accno and Meterno Anyone');
	  return false;
	  }
	 $.ajax({
		type : "GET",
		url : "./getSealByMtr",
		data:{meterno:meterno,rdngmnth:rdngmnth},
		cache : false,
		async : false,
		success : function(response){
		
			if(response[0]==null)
			{
			bootbox.alert("NEW Seal No Not Found For This Meter No");
			}
			if(response[1]==null)
			{
			bootbox.alert("Account No Not Found For This Meter No");
			}
		for(var i=0;i<response.length;i++)
			{
			
			//alert(response[2]);
			$("#newsealNum").val(response[0]);
			$("#accno").val(response[1]);
			$("#mrnameId").val(response[2]);
			}
		}
	}); 
}


//mrname
function getmrname(param)
{
	
$.ajax({
	type : "GET",
	url : "./getMrNameBasedOncir",
	data:{param:param},
	cache : false,
	async : false,
	dataType:'json',
	success : function(response){

		//alert(response);
		var newOption = $('<option>');
        newOption.attr('value',0).text(" "); 
        $('#MrNameDiv').empty(newOption);
        var defaultOption = $('<option>');
        defaultOption.attr('value',"").text("Select Mrname");
        $('#MrNameDiv').append(defaultOption);
        
			/*  var html='<select class="form-control select2me input-medium" name="mrnamediv" id="mrnamediv" >Select MRname</option>'; */
			 for( var i=0;i<response.length;i++)
			 {
				 $("#MrNameDiv").append('<option value="'+response[i]+'">'+response[i]+'</option>');
					
				/* html+="<option value='"+response[i]+"'>"+response[i]+"</option>";  */
			 }
		/* 	 html+='</select>';
			 
	$("#mrdiv").append(html); */


	}
	});
}


function validateNewSealIssue()
{
	var circle= $("#circleId").val();

	var mrName=$("#MrNameId").val();

	var sealNoFrom=$("#sealNoId1").val();

	var sealNoTo=$("#sealTo1Id").val();

	var total=$("#total1").val();

	var date=$("#fromdate").val();
	
	var rdMonth=$("#readingMonth").val();
	
	if(circle==0)
		{
		bootbox.alert("Please Select Circle");
		}
	
	if(mrName==0)
	{
	bootbox.alert("Please Select Meter Reader Name");
	return false;			

	}
	
	if(sealNoFrom=="")
		{
		bootbox.alert("Enter Starting Sealno");
		}
	
	if(sealNoTo=="")
	{
	bootbox.alert("Enter Ending Sealno");
	}
	
	if(total<0 || total=="")
		{
			bootbox.alert("Total Seals not found for selected Billmonth OR MRname");
			return false;
		}
	
	if(date=="")
		{
		bootbox.alert("Please Select Date");
		}
	
	var seallen = sealNoFrom.length;
	//alert("seallen------"+seallen);
	
	$.ajax({
		type: "POST",
		url:"./updateSealIssueToMR",
		data:{sealNoFrom:sealNoFrom, sealNoTo:sealNoTo, seallen:seallen, mrName:mrName, date:date, rdMonth:rdMonth},
		success: function(response)
		{
			bootbox.alert("Total Seals Issued-->"+response);
			
			$("#circleId").val('');
			$("#MrNameId").val('');
			$("#sealNoId1").val('');
			$("#sealTo1Id").val('');
			$("#total1").val('');
		   // $("#fromdate").val('');
		    //$("#readingMonth").val('');
		//	$("#sealIssueTabId").empty();
		}
		
	});
	
}

// MR Name For Seal
function getmrnameForSealIssue(param)
{
	//alert(param);

$.ajax({
	type : "GET",
	url : "./getMrNameBasedOncir",
	data:{param:param},
	cache : false,
	async : false,
	dataType:'json',
	success : function(response){

		//alert(response);
		var newOption = $('<option>');
        newOption.attr('value',0).text(" "); 
        $('#MrNameId').empty(newOption);
        var defaultOption = $('<option>');
        defaultOption.attr('value',"").text("Select MR Name");
        $('#MrNameId').append(defaultOption);
        
			/*  var html='<select class="form-control select2me input-medium" name="mrnamediv" id="mrnamediv" >Select MRname</option>'; */
			 for( var i=0;i<response.length;i++)
			 {
				 $("#MrNameId").append('<option value="'+response[i]+'">'+response[i]+'</option>');
					
				/* html+="<option value='"+response[i]+"'>"+response[i]+"</option>";  */
			 }
		/* 	 html+='</select>';
			 
	$("#mrdiv").append(html); */


	}
	});
}


function onEnterForSeal(form1,sealFrom,sealTo,total)
{	  
	  var sealNum=$('#'+sealFrom).val();	
	  var sealNum2=$('#'+sealTo).val();	
	
	  
	  if(sealNum.trim().length!=sealNum2.trim().length)
		  {
		  bootbox.alert('seal From and seal To should be same length');
		  $('#'+sealTo).val('');	
		  return false;
		  }
	  
	  var SealFromStr=sealNum.replace(/[0-9]/g, '');
	  var SealToStr=sealNum2.replace(/[0-9]/g, '');
	  if(SealFromStr!=SealToStr)
		  {
		  bootbox.alert('Entered seal From and seal To alphabets are not matching');
		  $('#'+sealTo).val('');
		  return false;
		  }
	  
	  var SealFrom=sealNum.replace(/[^0-9]/g, '');
	  var SealTo=sealNum2.replace(/[^0-9]/g, '');
	  
		  if(SealTo<SealFrom)
		  {
		  bootbox.alert('seal To should be greater than seal from');
		  $('#'+sealTo).val('');
		  $('#'+total).val('');
		  return false;
		  }
		  
	  $('#'+total).val((SealTo-SealFrom)+1);
	  
	  var total3=$('#'+total).val();
	  //alert(total3);
	  
	  if(total3>=400)
		  {
		  //alert(total3);
		  bootbox.alert("You are Issuing More than 400 Seals, Check Once Again");
		  }
	  
	  if(form1.name=='sealOutWard')
		  {
		  getCardSlNo(sealTo,'SealCardNo',1);		   
		  }
	  if(form1.name=='multipleSeal')
	  {
	  getCardSlNo(sealTo,'cardSealNo3',1);		   
	  }
	  
	  return false;	
}


//get Total No of seal
function getTotalNoSeal()
{
var Frommnt=$("#Fromrdngmonth1").val();
	var MrNameDiv=$("#MrNameDiv").val();
	//alert(MrNameDiv);
	//alert(Frommnt);

	
	$("#totalSeal").empty();
	
	$.ajax({
		type : "GET",
		url : "./getTotalNoSeal",
		cache:false,
		data:{Frommnt:Frommnt,MrNameDiv:MrNameDiv},
 		 success : function(response)
		  {
 			//  alert(response);
 			  if(response!=0)
 	 			  {
					
					$("#totalSeal").val(response);
 	 			  }
 			  else if(response==0)
 	 			  {
 					bootbox.alert("seals not found for selected Billmonth OR MRname");
					return false;
 	 			  } 
	 			  
		  }

		  });
	
}
//Transfer seal
function validationSingle()
{
	
var tomnt=$("#Tordngmonth1").val();
var Frommnt=$("#Fromrdngmonth1").val();
var mrname=$("#MrNameDiv").val();
var total=$("#totalSeal").val();
//alert(total);

if(mrname==0)
	{
	bootbox.alert("Please select Mrname");
	return false;			

	}
	if(Frommnt>tomnt)
	{
		bootbox.alert("From_Billmonth  should be Lesser than  To_Billmonth");
		return false;			
	}
	if(Frommnt==tomnt)
	{
		bootbox.alert("From_Billmonth  should be Lesser than  To_Billmonth");
		return false;			
	}
	if(total<0 || total=="")
		{
			bootbox.alert("Seals Not Found For Selected Billmonth OR MRname");
			return false;
		}
	
	$.ajax({
		type : "POST",
		url : "./transferSealForNxtMnth",
		data:{tomnt:tomnt,Frommnt:Frommnt,mrname:mrname},
		cache:false,
		async : false,
		
		success : function(response){
		  
 			//alert(response);
 			if(response>0)
 	 			{
 				bootbox.alert(response+" "+"Records Transfered Successfully");
 	 			}
 			else{
 				bootbox.alert("Seals Not Found");
 				return false;
 	 			}
		  }
		  });
	$("#totalSeal").val('');
	$("#circle").val('');
	$("#Tordngmonth1").val('');
	$("#Fromrdngmonth1").val('');
	$("#MrNameDiv").val('');
	$("#totalSeal").val('');
}


function makeUppercase1(){
	
	$('#sealNoId1').keyup(function(){
		    $(this).val($(this).val().toUpperCase());
		});
	} 

function makeUppercase2(){
	
	$('#sealTo1Id').keyup(function(){
		    $(this).val($(this).val().toUpperCase());
		});
	} 



/* ------------------  Seal Inward  ------------------------------ */

function sealInwarValidation(form1)
{ 
	      if(form1.selectReceiveDate.value.trim()=='')
		  {
		  bootbox.alert(' please select seal recieve date');
		  return false
		  }
		  if(form1.sealNoIdInward.value.trim()=='')
		  {
		  bootbox.alert(' please enter seal from');
		  return false
		  }
		  if(form1.sealToInward.value.trim()=='' || form1.sealToInward.value.trim()=='0')
		  {
		  bootbox.alert(' please enter seal to');
		  return false
		  }
		  
}

function onEnterInward(form1,sealFrom,sealTo,total)
{	  
	  var sealNum=$('#'+sealFrom).val();	
	  var sealNum2=$('#'+sealTo).val();	
	  if(sealNum.trim().length!=sealNum2.trim().length)
		  {
		  bootbox.alert('seal From and seal To should be same length');
		  $('#'+sealTo).val('');	
		  return false;
		  }
	  
	  var SealFromStr=sealNum.replace(/[0-9]/g, '');
	  var SealToStr=sealNum2.replace(/[0-9]/g, '');
	  if(SealFromStr!=SealToStr)
		  {
		  bootbox.alert('Entered seal From and seal To alphabets are not matching');
		  $('#'+sealTo).val('');
		  return false;
		  }
	  
	  var SealFrom=sealNum.replace(/[^0-9]/g, '');
	  var SealTo=sealNum2.replace(/[^0-9]/g, '');
	  
		  if(SealTo<SealFrom)
		  {
		  bootbox.alert('seal To should be greater than seal from');
		  $('#'+sealTo).val('');
		  return false;
		  }
	  
	  $('#'+total).val((SealTo-SealFrom)+1);
	  if(form1.name=='sealOutWard')
		  {
		  getCardSlNo(sealTo,'SealCardNo',1);		   
		  }
	  if(form1.name=='multipleSeal')
	  {
	  getCardSlNo(sealTo,'cardSealNo3',1);		   
	  }
	  
	  return false;	
}



/* -------------------- Return Entry Method -------------------------- */

function getCardSlNo(id,toWhich,incVal)
{  
	  if($('#'+id).val()=='')
	  {
	  bootbox.alert('seal To should be greater than seal from');	  
	  return false;
	  }
	  
	  $.ajax({
	    	url:'./getSealCardNum',
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(max)
	    	{
	    		$('#'+toWhich).val(max+incVal);
	    	}
	    	
	    });
}


function singleValidation(form1)
{
	  if($('#sealNo2Id').val().trim()=='')
	  {
	  bootbox.alert(' please enter seal No.');
	  return false
	  }
	  
	 if($('#selectReturnDate').val().trim()=='')
		  {
		  bootbox.alert(' please enter issued by');
		  return false
		  }
	  
}

function MultipleValidation(form1)
{
	  if(form1.sealNo3.value.trim()=='')
	  {
	  bootbox.alert(' please enter seal No. From');
	  return false
	  }
	  
	  if(form1.sealTo3.value.trim()=='')
	  {
	  bootbox.alert(' please enter seal No. To');
	  return false
	  }
	  
	 if(form1.selectReturnDate3.value.trim()=='')
		  {
		  bootbox.alert(' please select recieved date');
		  return false
		  }
}
</script>


<!--  Added By Vijju(26-06-2018) -->
<script>
function displaySealIssued(mrName,billMonth)
{
	
	$.ajax(
			{
				type : "GET",
				url : "./displayMrWiseSeals/" + mrName+"/"+billMonth,
				dataType : "json",
				cache:false,
				async: false,
			    success : function(response)
		  		  {
			    	var html = "";
			    	
			    	for(var j=0;j<response.length;j++)
		    		{		 
			    		var sealNo=response[j];
			    		html = html + "<tr><td>"+sealNo[0]+"</td>"+
			    				"<td>"+sealNo[1]+"</td>"+
			    				"<td>"+sealNo[2]+"</td>"+
			    				"<td>"+sealNo[3]+"</td>"+
			    				"<td>"+sealNo[4]+"</td>"+
			    				"<td>"+sealNo[5]+"</td>"+
			    				"</tr>";
		    		}
			    	var str = mrName;
			    	var mrNameVal = str.replace(/\s+/g, "");
			    	$("#mrwiseSealNoData").html(html);
			    	$('#excelPrintId').html("<a href=# id=excelExport onclick=tableToExcel('sample_editable_1','SEAL_"+mrNameVal+"')/>");
                 
			    	 document.getElementById("excelExport").click();

			    	/* $('#mrwiseSealNoPdfdata').attr('hidden',false);
			    	 $('#sealNosPdfPrint').tableExport({fileName:'sealManager1',type:'pdf',tableId:'sealNosPdfPrint',divId:'mrwiseSealNoPdfdata',condition:'custom'});	
			         $('#mrwiseSealNoPdfdata').attr('hidden',true); */
			         return false;
		    	}
			}		
	       );
	
}



function displaySealUsed(mrName,billMonth)
{
	
	$.ajax(
			{
				type : "GET",
				url : "./displayMrWiseSealsUsed/" + mrName+"/"+billMonth,
				dataType : "json",
				cache:false,
				async: false,
			    success : function(response)
		  		  {
			    	var html = "";
			    	
			    	for(var j=0;j<response.length;j++)
		    		{		 
			    		var sealNo=response[j];
			    		html = html + "<tr><td>"+sealNo[0]+"</td>"+
			    				"<td>"+sealNo[1]+"</td>"+
			    				"<td>"+sealNo[2]+"</td>"+
			    				"<td>"+sealNo[3]+"</td>"+
			    				"<td>"+sealNo[4]+"</td>"+
			    				"<td>"+sealNo[5]+"</td>"+
			    				"</tr>";
		    		}
			    	var str = mrName;
			    	var mrNameVal = str.replace(/\s+/g, "");
			    	$("#mrwiseSealNoData").html(html);
			    	$('#excelPrintId').html("<a href=# id=excelExport onclick=tableToExcel('sample_editable_1','SEAL_"+mrNameVal+"')/>");

			    	document.getElementById("excelExport").click();


			    	/*  $('#mrwiseSealNoPdfdata').attr('hidden',false);
			    	 $('#sealNosPdfPrint').tableExport({fileName:'sealManager1',type:'pdf',tableId:'sealNosPdfPrint',divId:'mrwiseSealNoPdfdata',condition:'custom'});	
			         $('#mrwiseSealNoPdfdata').attr('hidden',true);  */
			         return false;
		    	}
			}		
	       );
	
}


function displaySealDamaged(mrName,billMonth)
{
	
	$.ajax(
			{
				type : "GET",
				url : "./displayMrWiseSealsDamaged/" + mrName+"/"+billMonth,
				dataType : "json",
				cache:false,
				async: false,
			    success : function(response)
		  		  {
			    	var html = "";
			    	
			    	for(var j=0;j<response.length;j++)
		    		{		 
			    		var sealNo=response[j];
			    		html = html + "<tr><td>"+sealNo[0]+"</td>"+
			    				"<td>"+sealNo[1]+"</td>"+
			    				"<td>"+sealNo[2]+"</td>"+
			    				"<td>"+sealNo[3]+"</td>"+
			    				"<td>"+sealNo[4]+"</td>"+
			    				"<td>"+sealNo[5]+"</td>"+
			    				"</tr>";
		    		}
			    	var str = mrName;
			    	var mrNameVal = str.replace(/\s+/g, "");
			    	$("#mrwiseSealNoData").html(html);
			    	$('#excelPrintId').html("<a href=# id=excelExport onclick=tableToExcel('sample_editable_1','SEAL_"+mrNameVal+"')/>");

			    	document.getElementById("excelExport").click();
			    	/* $('#mrwiseSealNoPdfdata').attr('hidden',false);
			    	 $('#sealNosPdfPrint').tableExport({fileName:'sealManager1',type:'pdf',tableId:'sealNosPdfPrint',divId:'mrwiseSealNoPdfdata',condition:'custom'});	
			         $('#mrwiseSealNoPdfdata').attr('hidden',true); */
			         return false;
		    	}
			}		
	       );
	
}

function getMaxCardNo1(incVal)
{
	
	$.ajax({
	    	url:'./getSealCardNum1',
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(max)
	    	{
	    		
	    		$('#cardslno1').val(max+incVal);
	    		
	    	}
	    	
	    });
	
}



/* --------------------- Returned Seals Summary ----------------------- */

function displaySealReturnedPc(rmrName,condition)
{
	$('#checkAll').prop("checked", false);
	$('#mrNameUpdate').val(rmrName);
	$("#mrName").val(rmrName);
	$.ajax(
			{
				type : "GET",
				url : "./displayMrWiseSealsReturnPc/" + rmrName+"/"+condition,
				dataType : "json",
				cache:false,
				async: false,
			    success : function(response)
		  		  {
			    	noOfSeals=response.length;
			    	//$('#cardslno1').val('');
			    	var m=1;
			    	 var html2 = "<thead><tr><th class=table-checkbox><input id=checkAll onclick=allSelect("+response.length+"); type=checkbox class=group-checkable data-set=#sample_1 .checkboxes /></th><th>Seal Numbers</th></tr></thead><tbody>";
		    		 
		    	       for(var j=0;j<response.length;j++)
			    		{	
			    		     html2 +="<tr class=oddgradeX><td><input id=check"+m+"  onclick=getAllCheckedSealNo(this.id,this.value) type=checkbox class=checkboxes value="+response[j]+" /></td><td>"+response[j]+"</td></tr>";
			    		      m=m+1; 
			    		}
			    	html2+="</tbody>";	
				      $('#table_3').html(html2);
				      loadSearchAndFilter('table_3');
				      getMaxCardNo1(1);
		    	}
			}		
	       );
}


function updateCardSlno1()
{
	
	if(availableSealNo1.length>0)
		{
		  availableSealNo2.length=0;
		}
	if(availableSealNo2.length>0)
		{
		
		$.ajax(
				{
						type : "GET",
						url : "./updateCardSlNo1/"+$('#cardslno1').val()+"/"+availableSealNo2+"/"+$('#mrName').val(),
						dataType: "text",
						cache:false,
						async:false,
						success:function(response)
						{
							if(response=='updated')
								{
								 /*  $('#alertMsg1').html('<div class="alert alert-danger display-show"><button class="close" data-close="alert"></button><span style="color:red" >CardSlno1 Updated Successfully.</span></div>'); */
								    $('#stack10').modal('toggle');
								} 
						}
				}
			);
		availableSealNo2.length=0;
		}
	else if(availableSealNo1.length>0)
	{
		
		 $.ajax(
					{
							type : "GET",
							url : "./updateCardSlNo1/"+$('#cardslno1').val()+"/"+availableSealNo1+"/"+$('#mrName').val(),
							dataType: "text",
							cache:false,
							async:false,
							success:function(response)
							{
								if(response=='updated')
									{
									 /*  $('#alertMsg1').html('<div class="alert alert-danger display-show"><button class="close" data-close="alert"></button><span style="color:red" >CardSlno1 Updated Successfully.</span></div>'); */
									    $('#stack10').modal('toggle');
									} 
							}
					}
				);
		 availableSealNo1.length=0;
		 $('#checkAll').prop("checked", false);
	} 
	 else
	 {
	     bootbox.alert('Please select the Seal Numbers to update.');
	     return false;
	 }
	window.location.href = "./showReturnSeals1";
	
}


function updateMultipleCardSlno1()
{
	var checkboxes = document.getElementsByName('multipleCardSealCheck');
	var selected = [];
	for (var i=0; i<checkboxes.length; i++) {
	    if (checkboxes[i].checked) {
	        selected.push(checkboxes[i].value);
	    }
	}
	for(var j=0;j<selected.length;j++)
		{
		var str = selected[j].split("$");
		getMaxCardNo1(1);
		var cardsealNo1 = $("#cardslno1").val();
		
		$.ajax({
				type : "GET",
				url : "./updateMultipleCardSealNo1/"+str[0]+"/"+str[1]+"/"+cardsealNo1,
				cache : false,
				async : false,
				success : function(response){
					
				}
			});
		}
	
	window.location.href = "./showReturnSeals1";
}
</script>
							
							
 
