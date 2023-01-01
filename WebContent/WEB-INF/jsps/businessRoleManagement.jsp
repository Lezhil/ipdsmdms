
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
  <script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
	<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
	<script src="resources/assets/scripts/app.js"></script>
	<script src="resources/assets/scripts/ui-extended-modals.js"></script>
	

<script type="text/javascript">
	$(".page-content").ready(function() {
		
		$("#MIS-Reports").addClass('start active , selected');
		
		App.init();
		TableEditable.init();
		Tasks.initDashboardWidget();
		FormComponents.init();
		TableManaged.init();
		 
		 $('#ADMINSideBarContents,#buisnessRoleDetails,#userManagement').addClass('start active ,selected');
		 $("#MDMSideBarContents,#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
	
		 
    	 $("#analysedPrintId").click(function(){ 
	    	 printcontent($("#excelUpload .table-scrollable").html());
		});

    
		 $('#circleId').change(function() { 
	    	    var dropVal = $(this).val();
	    	    sessionStorage.setItem("SelectedItem", dropVal);
	    	});
	    	
	     var selectedItem = sessionStorage.getItem("SelectedItem");  
	   
	   	    if(selectedItem==null)
	   	    {
	   	    	$("#circleId").val("0").trigger("change"); 	 
	   	    }else{
	   	    	$("#circleId").val(selectedItem).trigger("change");
	   	    }
      
	   	    
	});
	
	function addroleName() {
		
		var roleName= document.getElementById('roleName')
		
			$.ajax({
			    	url : './validateBuisness/'+roleName,
			    	type:'GET',
			    	dataType:'json',
			    	
			    	success:function(data)
			    	{
			    		
			    	},
					  
				  });
		  
}
	

</script>

<style>
.col-sm-1 {
	width: 10.3333%;
}
</style>

<div class="page-content" >
<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Business Role Details</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								</div>
						</div>					
								<c:if test = "${not empty formatDate}"> 			        
						   <script>	
						   
						  
						   </script>
	  					</c:if>					
						<div class="portlet-body">
							
							<form:form action="#" commandName="addBusinessRoles" modelAttribute="addBusinessRoles" id="addbusinessRole" method="POST">
							<table>

							<tr>
							
							<td style="font-size:120%;"><u>Add Business Role</u></td></tr>
						
							<tr>
							<td style="font-size:120%;">Role Name :</td>
								<td>
							
							<input type="text" class="form-control" name="roleName" id="roleName" autocomplete="off">
							</td>
							
							<td><button type="button" class="btn blue pull-left" onclick="return addroleName(this.form)">Add</button><td>  
							</tr>
							
							
							</table>
							<div class="modal-footer">
							</div>
							</form:form>						
						</div>
					</div>					
				</div>
				<div class="col-md-12">
					<div class="portlet box blue">
			<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Existing Business Role Details</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								</div>
						</div>	
			
			<div class="portlet-body">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Role Name</th>
							<th>Entry By</th>
							<th>Entry Date</th>
							<th>Action</th>
							
							<!-- <th>Username</th>
							<th>Date Stamp</th> -->
						</tr>
					</thead>
					<tbody>

						<c:forEach var="app" items="${resultList}">
							<tr>
								<td>${app[1]}</td>
								<td>${app[2]}</td>
								<td>${app[3]}</td>
								<td><button type="button" id= "deleteRole" class="btn blue pull-left" onclick="return deleteRole('${app[1]}')">Delete</button></td>
								
								<%--- <td>${app.userName}</td>
								<td>${app.dateStamp}</td>--%>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
	</div>
			</div>
</div>
</div>
<style>
imgId
{
margin-left: 50%;
}
</style>


<script>
function addroleName(form)
{
	
   var roleName=form.roleName.value;
  
	    if(roleName=='')
	    	{
	    	bootbox.alert("Enter Role Name");
	        return false;
		    }
	    else{
	    	$.ajax({
 				type : 'GET',
				url : './validateBuisnessRoles',
				dataType : 'text',
				data : {
					roleName:roleName
	            	 
				},
				success : function(response) {
					
					if (response === 'rollExits') {
						 alert("Category Code already exits");
						 return false;
					}else {
                	$("#addbusinessRole").attr("action",
					"./addBuisnessRoleDetails?").submit();

					}
				}
			});

	    }
} 
  function deleteRole(roleName) {
	  //alert("Users are assigned to this role, so unable to delete this role")
		    	$.ajax({
	 				type : 'GET',
					url : './isAnyUserAssigned',
					dataType : 'text',
					data : {
						roleName:roleName
		            	 
					},
					success : function(response) {
						alert(response);
						
						if (response>=1) {
							 alert("deleted successfully");
							 window.location.reload();
							 return false;
						}else {
							
							bootbox.alert("Enter Role Name");	


						}
					}
				});		 
		 
		 
		 
	 }
	  
</script>
