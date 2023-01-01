<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>
<script src="{{asset('/plugins/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js')}}"></script>
 
 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {    
   	    	      App.init();
   	    	      TableEditable.init();
	   	    	  TableManaged.init();
	   	    	  //loadSearchAndFilter('sample_3');  
	   	    	   FormComponents.init();
	   	    	  UIDatepaginator.init(); 
	   	    	  
	   	    	$("#feederDivMultiple").hide();  
	   	    	
	   	    	/*   $("#selectedDateId").val(getPresentMonthDate('${selectedMonth}')); */
	   	   	$('#DATAEXCHsideBarContents,#ServiceMonitoringId,#serviceExceptionNotifSettingID').addClass('start active ,selected');
	    	 $("#MDASSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	    	
   	    	   });
	
  </script>
  
  
 <div class="page-content" >
    <div class="portlet box blue "  id="boxid">
			    <div class="portlet-title">
					<div class="caption"><i class="fa fa-reorder"></i>Service Exception Notification Setting</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
						<a href="#portlet-config" data-toggle="modal" class="config"></a>
						<a href="javascript:;" class="reload"></a>
						<a href="javascript:;" class="remove"></a>
					</div>
				</div>
				<div class="portlet-body ">
						
			<table >
			   <tbody>
				
					<tr >
						<th  >Service Name :</th>
							<th id="ServiceName">
								<select	class="form-control select2me input-medium" id="ServiceNameList"
								name="ServiceName" >
									<option value="">Select</option>
									   <c:forEach var="elements" items="${ServiceName}">
								  	    	<option value="${elements}">${elements}</option>
									    </c:forEach>
						     	</select>
							</th>
							
						
					 </tr></br>
					 <tr >
						<th  >Add Email's :</th>
							<th id="emailTHId">
						<!-- <input type="text" class="form-control"  id="emailId"  placeholder="Add Emails" /> -->
							<input type="email" class="form-control" data-role="tagsinput" id= "emailId" placeholder="Add Email No" />
							</th>
							</tr></br>
							
					 <tr >
						<th  >Add Mobile No's :</th>
							<th id="mobileNoId">
						<input type="number" class="form-control" data-role="tagsinput" id= "mobileId" placeholder="Add mobile No" />
							</th>
							</tr>		
					<tr>
						<th>
						 <div >
							<div class="">                        
								<!-- <button type="button" class="btn yellow">Reset</button>   -->
								<button type="submit" onclick="addServiceNotifcation()" class="btn green">Add </button> 
							</div>
						</div>
						</th>	
					</tr>
							
			</tbody>
		 </table>
		 		<table class="table table-striped table-hover table-bordered"  id="sample_3">
				<thead>
						<tr>
						
							<th>Sil NO</th>
							<th>Edit</th>
							<th>Service Name</th>
							<th>Email</th>
							<th>Mobile No</th>
							<th>Created By</th>
							<th>Created Time</th>
						</tr>
				</thead>
 				<tbody id="getsubstationdetails">
					<c:set var="count" value="1" scope="page"></c:set>
						<c:forEach var="element" items="${serviceNotification}">
							<tr>
								<td>${count}</td>
							    <td><a href="#" onclick="editServiceExcep(this.id)" id="${element[0]}">Edit</a></td>
							    <td>${element[1]}</td>
							    <td>${element[2]}</td>
							    <td>${element[3]}</td>
							    <td>${element[4]}</td>
							    <td>${element[5]}</td>
							</tr>
							<c:set var="count" value="${count + 1}" scope="page"/>   
					
					</c:forEach>
					
				</tbody>
							</table>
							
							
							
							
							<div id="stack2" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" aria-hidden="true">
					   <div class="modal-dialog">
					   <div class="modal-content">
					 <div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h5 class="modal-title">Edit Service Exception Notification </h5>
					 </div>
						<div class="modal-body">
<%--                         <form action="modifySubStationDetails"  id="modifySubStationDetailsId" >							  
 --%>                           <table id="stack2id" class="table table-striped table-hover table-bordered ">
                           <thead>
                           
                           <td><input type="hidden" id="idHidden"   ></input>
                            <tr id="EditSeviceNameTR">
														<td>Service Name<font color="red">*</font></td>
														<td><input type="text" id="EditSeviceName" readonly="readonly" class="form-control placeholder-no-fix" placeholder="Select Service Name" name="EditSeviceName"  ></input>
							 </td>
													</tr>
							<tr id="editEmailIdTR">
														<td>Email ID<font color="red">*</font></td>
														<td><input type="email" id="editEmailId" class="form-control placeholder-no-fix"  placeholder="Enter Email ID" name="editEmailId" ></input>
											
																</td>
													</tr>						
							<tr id="editmobileNOTR">
														<td>Mobile Number <font color="red">*</font></td>
														<td><input type="number" id="editmobileNO" class="form-control placeholder-no-fix" placeholder="Enter Mobile No " name="editmobileNO" maxlength="10" ></input>
											
																</td>
													</tr>
							
		                   </thead>																																									
                           </table>
                           <div class="modal-footer">
                           <button type="button" data-dismiss="modal"  class="btn">Cancel</button>
						<button class="btn blue pull-right" id="modifyId" type="submit"  value="" onclick="return modifyServiceException();">Modify</button>
					</div>
                    <%-- </form> --%>
                    </div>
					   </div>
					   </div>
					  </div> 
		</div>
	</div>
 </div>
<script>



function addServiceNotifcation(){
	var serviceName=$('#ServiceNameList').val();
	var emailId=$('#emailId').val();
	var mobileId=$('#mobileId').val();
	 var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
alert(emailId);
	if(serviceName == ''){
		bootbox.alert("Select Service");
		return false;
	}
	if(emailId == ''){
		bootbox.alert("Enter Email Id ");
		return false;
	}
	
     //var address = document.getElementById[email].value;
     if (reg.test(emailId) == false) 
     {
         alert('Invalid Email Address');
         return (false);
     }
	if(mobileId == ''){
		bootbox.alert("Enter Mobile Number");
		return false;
	}
	
	   $.ajax({
		      url  :'./saveServiceExceptionNotifSetting',
		      type :"GET",
		      data : {
		    	  serviceName : serviceName,
		    	  emailId : emailId,
		    	  mobileId: mobileId
				},
		    success:function(res){
		    if(res == 'success'){
		    	bootbox.alert("Service Exception  added Successfully");
		    }else{
		    	bootbox.alert("Failed to add");
		    }
		    }
	  });
			
}
function editServiceExcep(param)
{     
	   var id=param;
		if(id==""||id==null)
			{
			bootbox.alert("No data")
			return false;
			}
		  $.ajax(
		  			{
		  					type : "GET",
		  					url : "./editServiceException",
		  					dataType : "json",
		  					data : {
		  						id : id
		  			    
		  					},
		  					success : function(response)
		  												{	
		  										    		var data=response[0];
		  										    		$("#EditSeviceName").val(data[1]);
		  										    		$("#editEmailId").val(data[2]);
		  										    		$("#editmobileNO").val(data[3]);
		  										    		$("#idHidden").val(data[0]);
		  										    
		  											
		  												}
		  			});
		  $('#'+param).attr("data-toggle", "modal");
		  $('#'+param).attr("data-target","#stack2");
		  }



function modifyServiceException(){
	
	var serviceName=$('#EditSeviceName').val();
	var emailId=$('#editEmailId').val();
	var mobileNo=$('#editmobileNO').val();
	var id=$('#idHidden').val();
	
	$.ajax({
	      url  :'./modifyServiceException',
	      type :"GET",
	      data : {
	    	  mobileNo : mobileNo,
	    	  emailId : emailId,
	    	  serviceName:serviceName,
	    	  id:id
			},
	    success:function(res){
	    	//alert(res);
	    	
	    	if(res == 'Success'){
	    	bootbox.alert(" Service exception modifed  Successfully");
	    	$('#stack2').hide();
		    $('#stack2').modal('hide');
	    	}else {
	    		bootbox.alert(" Service exception Failed to update");	
	    		
	    	}
	    	
	    }
  });
}

</script>

