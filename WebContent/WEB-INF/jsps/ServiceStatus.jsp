<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<!-- <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> -->
 <script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>
<script type="text/javascript">
	

						$(".page-content").ready(
								function() {
									App.init();
									TableEditable.init();
									FormComponents.init();
						 			loadSearchAndFilter('sample1');
								 	$('#ServiceMonitoringId,#ServiceStatusId').addClass('start active ,selected');
									$('#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment').removeClass('start active ,selected'); 
								});
</script>
<div class="page-content">
<div class="portlet box blue">
<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>Service Status<span
					style="color: aqua; font-size: 18px; font-weight: bold;"></span>
			</div>
		</div>
		<div class="portlet-body">
		<!-- <div class="btn-group">
					<button class="btn green" data-toggle="modal" data-target="#stack1"
						id="addData" onclick=>
						Add <i class="fa fa-plus"></i>
					</button>
				</div><br> -->
				<table >
			   <tbody>
					<tr >
						<th>Service Name :</th>
							<th id="ServiceName">
								<select	class="form-control select2me input-medium" id="ServiceNameList"
								name="ServiceName" >
									<option value="">Select</option>
									   <c:forEach var="elements" items="${ServiceName}">
								  	    	<option value="${elements}">${elements}</option>
									    </c:forEach>
						     	</select>
							</th>
						<br> 	
					 
						<th  >Service Status :</th>
							<th id="servicestatus">
						<input type="text" class="form-control" id= "servicestatusId" placeholder="Add Service Status" />
							</th>
							
						<th  >Service Type :</th>
							<th id="servicetype">
						<input type="text" class="form-control" id= "servicetypeId" placeholder="Add Service Type" />
							</th>
						<br><br>	
						
						<th>
						 <div >
								<button type="submit" onclick="addServicestatus()" class="btn green">Add </button> 
						</div>
						</th>	
					</tr>
			</tbody> 
		 </table>
			
		<br>
		<table class="table table-striped table-hover table-bordered"
				id="sample1">
				<thead>
					<tr>
					    <th>ID</th>
					    <th>Edit</th>
						<th>Service Name</th>
						<th>Service Status</th>
						<th>Service Type</th>
						<th>Last Modification Date</th>
					</tr>
				</thead>
				
				<tbody id="ServStatusTbId">
					<c:set var="count" value="1" scope="page"></c:set>
						<c:forEach var="element" items="${Servicestatus}">
							<tr>
								<td>${count}</td>
			                <td><a href="#" onclick="editServicestatus(this.id)" id="${element[4]}">Edit</a></td>
							    <td>${element[0]}</td>
								<td>${element[1]}</td>
								<td>${element[2]}</td>
								<td>${element[3]}</td>
							</tr>
							<c:set var="count" value="${count + 1}" scope="page"/>   
					
					</c:forEach>
					
				</tbody>
			</table>
</div>
</div>
</div> 

 
 
 	<div id="stack1" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" aria-hidden="true">
					   <div class="modal-dialog">
					   <div class="modal-content">
					 <div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h5 class="modal-title">Edit Service Status </h5>
					 </div>
						<div class="modal-body">
                           <table id="stack1id" class="table table-striped table-hover table-bordered ">
                           <thead>
                           
                          <tr id="editidd" type="hidden">  <td><input type="hidden" id="idHidden"   ></input> </td></tr>
                            <tr id="EditSeviceName">
														<td>Service Name</td>
														<td><select type="text" id="editSeviceNameId"  class="form-control placeholder-no-fix" placeholder="Select Service Name" name="editSeviceNameId"  >
														<option value=""></option>
									                    <c:forEach var="elements" items="${ServiceName}">
								  	    	            <option value="${elements}">${elements}</option>
									                    </c:forEach>
														</select>
							 </td>
													</tr>
							<tr id="EditServiceStatus">
														<td>Service Status</td>
														<td><input type="text" id="editServiceStatusId" class="form-control placeholder-no-fix"  placeholder="Enter Service Status" name="editServiceStatusId" ></input>
											
																</td>
													</tr>						
							<tr id="EditServiceType">
														<td>Service Type</td>
														<td><input type="text" id="editServiceType" class="form-control placeholder-no-fix" placeholder="Enter Service Type " name="editServiceType" ></input>
											
																</td>
													</tr>
							
		                   </thead>																																									
                           </table>
                           <div class="modal-footer">
                           <button type="button" data-dismiss="modal"  class="btn">Cancel</button>
						<button class="btn blue pull-right" id="modifyid" type="submit" data-dismiss="modal" value="" onclick="return modifyServiceStatus();">Modify</button>
					</div>
                    </div>
					   </div>
					   </div>
					  </div>


 <script>
function addServicestatus(){
	var servicename=$('#ServiceNameList').val();
	var servicestatus=$('#servicestatusId').val();
	var servicetype=$('#servicetypeId').val();
	 
	if(servicename==''){
		bootbox.alert("Please select servicename");
		return false;
	}
	
	if(servicestatus==''){
		bootbox.alert("Please select servicestatus");
		return false;
	}
	
	if(servicetype==''){
		bootbox.alert("Please select servicetype");
		return false;
	}
	
	$.ajax({
		url:"./addservice",
		type:'POST',
		data:{
			servicename:servicename,
			servicestatus:servicestatus,
			servicetype:servicetype
		},
		success:function(response){
			if(response=="success"){
				bootbox.alert("Service Status added Successfully.");
				return false;
			}else{
				bootbox.alert("Failed to add Service Status.");
				return false;
			}
			
		},
	});
	
	//$("#servicestatusId").attr('action', './serviceform').submit();
}


function editServicestatus(param){
	var id=param;
	
	if(id==""||id==null)
	{
		bootbox.alert("No data");
		return false;
	}
		$.ajax({
			url : './editServiceStatus',
			type:'POST',
			dataType:'JSON',
			data:{
				id:id
			},
			success:function(response){
				
				                        var data=response[0];
				                        $("#editSeviceNameId").val(data[0]);
				                        $("#editServiceStatusId").val(data[1]);
				                        $("#editServiceType").val(data[2]);
				                        $("#idHidden").val(data[3]);
				
			}
		});
		$('#'+param).attr("data-toggle", "modal");
		$('#'+param).attr("data-target","#stack1");
	
}


function modifyServiceStatus(){
	
	var id=$('#idHidden').val();
	var servname=$('#editSeviceNameId').val();
	var servsta=$('#editServiceStatusId').val();
	var sertype=$('#editServiceType').val();
	
	$.ajax({
		url : './modifyServiceStatus',
		type:'POST',
		data:{
			     id : id,
			servname: servname,
			servsta :servsta,
			sertype :sertype
		},
		success:function(response){
			
			bootbox.alert(" Service Status modified Successfully");
			$('#stack1').hide();
		    $('#stack1').modal('hide');
		    //$("#ServStatusTbId").html(html);
		}
	});
}
</script>


