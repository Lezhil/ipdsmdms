<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- <script src="//ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script> -->
<script src="<c:url value='/resources/assets/plugins/jquery-validation/dist/jquery.validate.min.js' />" type="text/javascript"></script>

<script  type="text/javascript">
	$(".page-content").ready(function(){			
		App.init();
		TableManaged.init();
		FormComponents.init();	
		$('#addAndUpdateAssessment').addClass('start active ,selected');
		$("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports,#meterMaster,#newConnectionId").removeClass('start active ,selected');
	    
	});
	
	
	function updateAssessment(form)
	{
	 myFormValidate();
	  $('#assessmentAddUpdate').attr('action','./updateAsessment').submit();
	}
	
	
	  function editAssessment(param,opera)
	  {
		$("#addAssesmentOption").hide();
		$("#updateAssessmentOption").show();
		
		  var operation=parseInt(opera);
		  		  $.ajax(
		  			{
		  					type : "GET",
		  					url : "./editAssessment/" + operation,
		  					dataType : "json",
		  					success : function(response)
		  												{	
		  										    		
		  													document.getElementById("assid").value=response.id;
		  													document.getElementById("billmonth").value=response.billmonth;
		  													document.getElementById("totalReads").value=response.totalReads;
		  													document.getElementById("totalAssesedConsumers").value=response.totalAssesedConsumers;
		  													document.getElementById("totalAssesment").value=response.totalAssesment;
		  																 
		  												}
		  			});
		  $('#'+param).attr("data-toggle", "modal");
		  $('#'+param).attr("data-target","#stack1");
		  }
   

	  function clearMyForm()
		  {
		
		    $("#addAssesmentOption").show();
			$("#updateAssessmentOption").hide();
			$("#updateMrOption").hide();
			
			$("#billmonth").val('');
			$("#totalReads").val('');
			$("#totalAssesedConsumers").val('');
			$("#totalAssesment").val('');
		
		 }  
	  
		 function myFormValidate()
		 {
	      
		 $("#assessmentAddUpdate").validate(
		  {
			  rules:{
				   billmonth: "required",
				   totalReads:{
					          required:true,
					          number:true
				              },
			        totalAssesedConsumers:{
				 					required:true,
				 					number:true
				 
			                      },
			         totalAssesment:{
			        	             required:true,
			        	             number:true
			        	   			}
				    },
		        messages:{
		    	    billmonth: "<font color='red'>Please Choose month please</font>",
		    	    totalReads:{
		    	    	          required:"<font color='red'>Please enter totalReads</font>",
		    	    	          number:"<font color='red'>Plase enter number only</font>"
		    	                },
		    	                
		    	totalAssesedConsumers:{
				 					required:"<font color='red'> Please enter totalAssesConsumer</font>",
				 					number:"<font color='red'>Plase enter number only</font>"
									 },
			      totalAssesment:{
			        	             required:"<font color='red'>Please enter totalAssesment</font>",
			        	             number:"<font color='red'>Plase enter number only</font>"
			        	   			}
		    	        },
				  
			  
		  });
		 }
		
		  
		  
	  
	  
	  
	  
</script>
<div class="page-content" >

					<!-- Display Success Message -->
		           
		           
		           <c:if test = "${not empty msg}">
			         <div class="alert alert-danger display-show">
					 <button class="close" data-close="alert"></button>
					 <span style="color:red" >${msg}</span>
			       </div>
	                </c:if>
	                
	                	
		          <!-- End Success Message -->
				
            <div class="portlet box blue">
						<div class="portlet-title line">
							<div class="caption"><i class="fa fa-globe"></i>Add And Update Assesment</div>
							<div class="tools">
								<a href="" class="collapse"></a>
								<a href="" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body">
							<!--BEGIN TABS-->
							<div class="tabbable tabbable-custom">
								
								
								
							<!-- 	<div class="scroller" style="height: 290px;" data-always-visible="1" data-rail-visible="0"> -->
										
										<div class="btn-group">
										<button class="btn green" data-target="#stack1" data-toggle="modal"  id="addData" onclick="return clearMyForm()">
									      Add Assessment <i class="fa fa-plus"></i>
									     </button>
								         </div><br/><br/>
							  <table class="table table-striped table-bordered table-hover" id="sample_2">
								<thead>									
									<tr>										
										<th hidden="true"> id</th>
										<th>BILLMONTH</th>
										<th>TOTAL READS</th>
										<th>TOTAL ASSESED CONSUMERS</th>
										<th>TOTAL ASSESMENT (In Lakhs )</th>
										<th>Edit</th>
									
										
            					</tr>
								</thead>
								<tbody>
									
								<c:forEach var="element" items="${assList}">
									<tr >
										
										<td hidden="true">${element.id}</td>
										<td>${element.billmonth}</td>
										<td>${element.totalReads}</td>
										<td>${element.totalAssesedConsumers}</td>
										<td>${element.totalAssesment}</td>
									    <td><a href="#" onclick="editAssessment(this.id,${element.id})" id="editData${element.id}">Edit</a></td>
									
								</tr>
									</c:forEach>
									 							
								</tbody>
							</table>
			
							<div id="stack1" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<h4 class="modal-title">Assessment Management </h4>
										</div>
										<div class="modal-body">
						 	               <form:form action="./addNewAssesment" modelAttribute="assessmentAddUpdate" commandName="assessmentAddUpdate" method="post" id="assessmentAddUpdate">		  
													<table id="tableData2">
													
													<tr hidden="true">
														<td>Id</td>
														<td><font color="red">*</font></td>
														<td><form:input path="id" id="assid"
																class="form-control placeholder-no-fix,error" type="text"
																autocomplete="off" placeholder="" name="id"></form:input></td>
													</tr>

													<tr>
														<td>Bill Month</td>
														
														<td><font color="red">*</font></td>
														<td>
											         <div class="input-group input-medium date date-picker"  data-date-format="yyyymm" data-date-viewmode="years" id="five">
																<form:input path="billmonth" type="text" class="form-control" name="billmonth" id="billmonth" ></form:input>
																<span class="input-group-btn">
																<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span><span id="rdngmonthcheck" style="color:red;font-weight:bold;" ></span>
												</div>  
																
													  </td>
													</tr>
													
													<tr>
														<td>Total Reader </td>
														<td><font color="red">*</font></td>
														<td><form:input path="totalReads" id="totalReads"
														class="form-control placeholder-no-fix" type="text" 
													     name="totalReads"/>
													 
													   </td>
													  
													</tr>
													<tr>
													<td>TOTAL ASSESED CONSUMERS </td>
													<td><font color="red">*</font></td>
													<td><form:input path="totalAssesedConsumers" id="totalAssesedConsumers"
														class="form-control placeholder-no-fix" type="text" 
														name="totalAssesedConsumers"/>
													 
													   </td>
													
													</tr>
													
													
													
													
												
												   <tr>
														<td>Total Assessment </td>
														<td><font color="red">*</font></td>
														<td><form:input path="totalAssesment" id="totalAssesment"
														class="form-control placeholder-no-fix"  type="text" 
								                        name="totalAssesment" />
														
													   </td>
													</tr>
					
													</table>
										                     <div class="modal-footer">
															    
															 <button class="btn blue pull-right" id="addAssesmentOption" type="submit" onclick=" return myFormValidate()">Add User</button>  
															 <button type="button" data-dismiss="modal" id="updateAssessmentOption" class="btn blue pull-right" onclick="return updateAssessment(this.form)">Update Assessment</button>
															 <button type="button" data-dismiss="modal" class="btn">Cancel</button>
													</div>	
														
										 </form:form>
							
							</div>
							</div>
							</div>
							</div>
							
			
										</div>
									</div>
									
									
							
							</div>
							</div>
						
					
							

