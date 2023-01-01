 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {     
   	    	      App.init();
   	    	   //  TableEditable.init();
		    	 Tasks.initDashboardWidget();
		    	 FormComponents.init();
		    //	 TableManaged.init();
		    	
		    	 
		    	
		    		$('#MDMSideBarContents,#exceptionManagementConfig,#emcConfig').addClass('start active ,selected');
	   	    	     $("#MDASSideBarContents,#admin-location,#dash-board,#MIS-Reports").removeClass('start active ,selected');
	   	    	  configList();
	   	    	  
   	    	   });
   	   
  

  
  </script>
  <script>
  function configList() {
	  $.ajax({
		 url:'./exceptionMangementList',
		 type:'GET',
		 success:function(res){
			 var html='';
			 $.each(res, function( index, value ) {
				
					 html+='<tr  class="odd gradeX">	';
						 html+='	<td>'+value.eventCode+'</td>';
							 html+='   <td>'+value.eventDescription+'</td>';
							  if(value.eventConfig=='I'){
								html+=' <td><a href="#" onclick="config(this.id)" id="inactive_'+value.eventCode+'" class="btn btn-lg red">Inactive</a></td></tr>'; 
							 }
							 else{
								 html+='  <td><a href="#" class="btn btn-lg green" onclick="config(this.id)" id="active_'+value.eventCode+'">Active</a></td></tr> ';
							 } 
						
				});
			 clearTabledataContent('sample_3');
			 $("#configlist").html(html);
			// loadSearchAndFilter('sample_3');
		 },
		 complete: function()
			{  
				loadSearchAndFilter('sample_3');
			} 
	  });
	}
  function clearTabledataContent(tableid)
	{
		 //TO CLEAR THE TABLE DATA
		var oSettings = $('#'+tableid).dataTable().fnSettings();
		var iTotalRecords = oSettings.fnRecordsTotal();
		for (i = 0; i <= iTotalRecords; i++) 
		{
			$('#'+tableid).dataTable().fnDeleteRow(0, null, true);
		} 
		
	}
function config(status){
	
	$.ajax({
		url:'./configModification/'+status,
		type:'post',
	    success:function(res){
	    	if(res=='1'){
	    		bootbox.alert("Successfully Modified");
	    		configList();
	    	}
	    	else{
	    		bootbox.alert("Configuration Not Changed");
	    	}
	    }
	});
	
}
  </script>
  
  <div class="page-content">
  <div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Event Exceptions Configuration</div>
							
						</div>					
											
						<div class="portlet-body">
							<!-- <div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_3', 'Total Meters')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div> -->
							 <%-- <c:if test="${not empty events }"> --%>
							
								<BR><BR>
								<!-- <div class="table-scrollable">	 -->
								<table class="table table-striped table-bordered table-hover" id="sample_3" >
									<thead>		
															
										<tr>								
											    <th>Event Code</th>
											    <th>Event Description</th>
											    <th>State</th>
												
												
										</tr>
									</thead>
									<tbody id="configlist">
																		
									 						 									 
												
									</tbody>
								</table>	
							<!-- 	</div> -->
								<%-- </c:if>	  --%>
						
				
							
											
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
  
 
  
  
  </div>