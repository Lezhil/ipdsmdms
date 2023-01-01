
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
  <script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
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
		 
		$('#ADMINSideBarContents,#rolePrevilegeDetails,#userManagement').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#buisnessRoleDetails,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
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

<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>Role Privilege Details
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="reload"></a> <a href="javascript:;"
							class="remove"></a>
					</div>
				</div>
				<c:if test="${not empty formatDate}">
					<script>	
						   
						  
						   </script>
				</c:if>
				<div class="portlet-body">

					<form:form action="#" commandName="selectrole"
						modelAttribute="selectrole" id="selectrole" method="POST">
						<table>

							<tr>

								<td style="font-size: 120%;">Select Role</td>
							</tr>

							<!-- <tr>
								<td style="font-size: 120%;">Role Name :</td>
								<td><input type="text" class="form-control" name="roleName"
									id="roleName" autocomplete="off"></td>
                            </tr> -->
							<tr>	
							<td style="font-size: 120%;">Select Role</td>
							<th id="roles"><select
									class="form-control select2me input-medium" id="zone"
									name="zone" onchange="showCircle(this.value);">
										<option value="">Select Roles</option>
										<c:forEach items="${rolesList}" var="elements">
															<option value="${elements}">${elements}</option>
														</c:forEach>
								</select></th>
						</tr>

						</table>
						<div class="modal-footer"></div>
					</form:form>
				</div>
			</div>
		</div>
		<div class="col-md-12">
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>List of Application Pages
						 <a href="#" id="add" class="btn green" style="margin-left: 50px;"  onclick="add('sample_editable_1', 'add')">ADD</a>
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="reload"></a> <a href="javascript:;"
							class="remove"></a>
					</div>
				</div>
				<div class="portlet-body">            
					<table class="table table-striped table-hover table-bordered" id="">
						<thead>
							<tr>
								<th>Module Name</th>
								<th>Submodule Name</th>
								<th>Application Page</th>
								<th style='min-width: 100px; text-align: center;'>Allow
									View</th>
								<th style='min-width: 100px; text-align: center;'>Allow
									Edit</th>
							</tr>
						</thead>

						<tbody>


					    </tbody>
					</table>

				</div>
			</div>
		</div>
	</div>

</div>
































