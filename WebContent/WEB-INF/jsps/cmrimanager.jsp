<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
  <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {     
   	    	     App.init();
   	    	    TableManaged.init();
   	 
   	    	   $('#cmri-Manager').addClass('start active ,selected');
   	    	   $("#dash-board,#360d-view,#cumulative-Analysis,#seal-Manager,#cdf-Import,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
   	    	   $("#cmriNoErr").hide();
     	      });
  
  
  function edit(param,opera)
  {
	  $("#cmriNoErr").hide();
	  var operation=parseInt(opera);
      $.ajax(
  			{
  					type : "GET",
  					url : "./editCmriNumber/" + operation,
  					dataType : "json",
  					success : function(response)
  												{	
  												 var obj=response.toString();
  											   /* alert(obj); */
 												document.getElementById("cmri_num").value=obj;	
  												document.getElementById("cmri_no").value=obj;		
  												}
  			});
  	  $('#'+param).attr("data-toggle", "modal");
      $('#'+param).attr("data-target","#stack2"); 	    
  	    
  };
  
  function checkAvail()
  {
	  $("#cmriNoErr").hide();
	  var operation=document.getElementById('cmri_no').value;
	/*  alert(operation); */
	  $.ajax(
	  			{
	  					type : "GET",
	  					url : "./checkDuplicate/" + operation,
	  					dataType : "json",
	  					success : function(response)
	  												{	
	  					
	  											    if(response!=null)
									    	    	{
	  											    	//$("#cmriNoErr").hide();
									    	        
									    	    	     //document.getElementById("cmri_no").value = "";
									    	    	 
									    	    	  $("#cmriNoErr").show();
									    	    	  $("#cmri_no").focus();
									    	    	
								                     return true;
								                     }
	  											    else
	  											    {
	  											    	$("#cmriNoErr").hide();
	  											    	//document.getElementById("cmri_no_check").innerHTML = " ";
	  											    	return false;
	  											    }
	  											  
	  												}
	  			}); 
  }
   
  function clearMyForm()
  {
	  $("#newCmri input").val("");
  }
  
  var specialKeys = new Array();
  specialKeys.push(8); //Backspace
  specialKeys.push(9); //Tab
  specialKeys.push(46); //Delete
  specialKeys.push(36); //Home
  specialKeys.push(35); //End
  specialKeys.push(37); //Left
  specialKeys.push(39); //Right
  function IsAlphaNumeric(e) {
      var keyCode = e.keyCode == 0 ? e.charCode : e.keyCode;
      var ret = ((keyCode >= 48 && keyCode <= 57) || (keyCode >= 65 && keyCode <= 90) || (specialKeys.indexOf(e.keyCode) != -1 && e.charCode != e.keyCode));
      document.getElementById("error").style.display = ret ? "none" : "inline";
      return ret;
  }
  
  </script>
<div class="page-content" >


<!-- BEGIN PAGE CONTENT-->
			
			<div class="row">
				<div class="col-md-12">
				
					
					
					<!-- Display Success Message -->
		           
		           
		           <c:if test = "${not empty msg}">
			         <div class="alert alert-danger display-show">
					 <button class="close" data-close="alert"></button>
					 <span style="color:red" >${msg}</span>
			       </div>
	                </c:if>
	                
	                	
		          <!-- End Success Message -->
				
				
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box light-grey">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>CMRI Manager</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-toolbar">
								<div class="btn-group">
									<button id="sample_editable_1_new" class="btn green"  data-toggle="modal" data-target="#stack1" onclick="clearMyForm()">
									Add New CMRI <i class="fa fa-plus" ></i>
									</button>
								</div>
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#">Print</a></li>
										<li><a href="#">Save as PDF</a></li>
										<li><a href="#">Export to Excel</a></li>
									</ul>
								</div>
							</div>
							
                         <table class="table table-striped table-bordered table-hover" id="sample_3">
								<thead>
									<tr>
										
									    <th>CMRI NO</th>
										<th>Edit</th>
							
									</tr>
								</thead>
								<tbody>
									<c:forEach var="element" items="${CmriList}">
									<tr class="odd gradeX">
									
										<td>${element.cmri_no}</td>
										<td><a href="#" onclick="edit(this.id,${element.cmri_no})" id="editData${element.cmri_no}"  data-toggle="modal" data-target="#stack2">Edit</a></td>
										
										
									</tr>
							 </c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					
					
		       
	<!-- END EXAMPLE TABLE PORTLET-->
					
					
					
					<!-- BEGUN CMRINO -->
					
					
					<div id="stack1" class="modal fade" tabindex="-1" data-width="500">
					   <div class="modal-dialog">
					      <div class="modal-content">
					         <div class="modal-body">
					            <h4 class="modal-title">Add New CMRI</h4>
					            <hr></hr>
					            <div class="row">
					              
					              <div class="col-md-12">
					            
					              	 <form:form action="./addNewCmriNo"  modelAttribute="cmriManager" commandName="cmriManager"  method="post" id="newCmri" enctype="multipart/form-data">
											
									 <table>
										 <tr hidden="true">
										
										
										<td>CMRI Id</td>
						                 <td><form:input path="cmri_id" type="text" class="form-control" name="cmriid" id="cmriid"></form:input></td>
									    </tr>
										<tr>
										<td>CMRI No.</td>
						                 <td><form:input path="cmri_no" type="text" class="form-control" name="mriNo" id="mriNo" required="true" onkeypress="return IsAlphaNumeric(event);" ondrop="return false;"></form:input>
									      <span id="error" style="color: Red; display: none">* Special Characters and Small alphabets not allowed</span></td>
									    </tr>
									     
									    </table>
																	
												
							                 <div class="modal-footer">
													 <button type="button" data-dismiss="modal" class="btn">Close</button>
													 <button type ="submit" class="btn blue pull-right" style="display: block;" id="addOption" onclick="IsAlphaNumeric()" >Add</button>
											
												</div>
											</form:form>
									 
					              </div>
					           </div>
					       </div>
					   </div>
					  </div>
					</div>
					
					<!--END  CMRINO-->

					<!-- BEGUN CMRINO -->
					
					
					<div id="stack2" class="modal fade" tabindex="-1" data-width="500">
					   <div class="modal-dialog">
					      <div class="modal-content">
					         <div class="modal-body">
					            <h4 class="modal-title">Update CMRI number</h4>
					            <hr></hr>
					            <div class="row">
					              
					              <div class="col-md-12">
					            
					              	 <form:form action="./updateCmriNumber"  modelAttribute="cmriManager" commandName="cmriManager"  method="post" id="newCmri" enctype="multipart/form-data">
											
									 <table>
									   <tr hidden="true">
										<td><input  type="text" class="form-control" name="cmri_numb" id="cmri_num"></input></td>
									   </tr>
									 
									 
										 <tr>
										
										<td>CMRI No.</td>
						                 <td><form:input path="cmri_no" type="text" class="form-control" name="cmri_no" id="cmri_no" onblur="checkAvail()"></form:input>
						                 
						                <span id="cmri_no_check" > <b style='color:red' id='cmriNoErr'>This CMRIno is already exist</b></span>
						                  	
						                 </td>
									    </tr>
									     
									    </table>
																	
												
							                 <div class="modal-footer">
													 <button type="button" data-dismiss="modal" class="btn">Close</button>
                                                     <button type ="submit" class="btn blue pull-right" style="display: block;" id="addOption" >Update</button>
													
												</div>
											</form:form>
									 
					              </div>
					           </div>
					       </div>
					   </div>
					  </div>
					</div>
					
					<!--END  CMRINO-->
					
				
					
					
				</div>
			</div>
</div>