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
		    
		    
		    	/*  $("#eventFromDate").val('${eventFromDate}');
		    	 $("#eventToDate").val('${eventToDate}'); */
		    		$('#MDASSideBarContents,#schedule-meter,#meterOper').addClass('start active ,selected');
	   	    	     $("#admin-location,#dash-board,#MIS-Reports").removeClass('start active ,selected');
	   	    	  loadSearchAndFilter('sample_3');
   	    	   });
   	    	   
  
  
  
  </script>
  <script>
  function view(seqId,type){
	  
	  $.ajax({
		  type:"GET",
		  url:"./seqView/"+seqId+"/"+type,
			  
			  success:function(response){
				  var v=response[0][0];
				  var v1=response[1];
				  var html='<tr ">';
	
					  for(var i=0;i<v.length;i++){
						  html+="<th>"+v[i]+"</th>";
					  }
					  html+="</tr>";
						  var html1='<tr>';
					  for(var k=0;k<v1.length;k++){
						  for(var j=0;j<v.length;j++){
							  if(v1[k][j]==null){
								  html1+="<td></td>";
							  }
							  else{
								  html1+="<td>"+v1[k][j]+"</td>";
							  }
							 
						  }
					  }
					  
				
				
				  
				  html1+="</tr>";
				  $("#thid").html(html);
				  $("#tbid").html(html1);
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
							<div class="caption"><i class="fa fa-globe"></i>Schedule History</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								</div>
						</div>					
											
						<div class="portlet-body">
							
							<form action="./smc">
							<table>
							
							
							<tr>
							<td>Latest Record's: 
							</td>
							<td>
							<select  id="event" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="event" >
							<option value="10">10</option>
							<option value="20">20</option>
							<option value="30">30</option>
							<option value="50">50</option>
							<option value="50">100</option>
							</select>
							</td>
							<td><button class="btn blue pull-left" >Submit</button>  </td>
							</tr>
							
							
							
							</table>
							
							</form>	
							
				<div class="table-toolbar">
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
					</div>
							<c:if test="${not empty tracklist }">
							
								<BR><BR>
								<!-- <div class="table-scrollable">	 -->
								<table class="table table-striped table-bordered table-hover" id="sample_3" >
									<thead>									
										<tr>								
											    <th>S.no</th>
											    <th>Sequence Id</th>
											    <th>Type</th>
												<th>Schedule Time</th>
												<th>Meter Number</th>
												<th>View</th>
										</tr>
									</thead>
									<tbody>
										<c:set var="count" value="1" scope="page" />								
									<c:forEach var="tl" items='${tracklist}'> 
										 	<tr id="sampel" class="odd gradeX">	
										 		<td>${count}</td>
											    <td>${tl[0]}</td>
											    <td>${tl[1]}</td>
											    <td>${tl[2]}</td>
											    <td>${tl[3]}</td>
											    <td  onclick="view('${tl[0]}','${tl[1]}' )"><a href="#">view</a></td>
											    
											   
											   </tr>
											<c:set var="count" value="${count + 1}" scope="page"/>   
										 </c:forEach>						 									 
												
									</tbody>
								</table>	
							<!-- 	</div> -->
								</c:if>	
								<c:if test="${not empty eventLength1 }">
								<font color="red">Data Not Found</font>
								</c:if>				
						</div>
					</div><div style="overflow-x:auto;">
					<table class="table table-striped table-bordered table-hover table-condensed" id="sample_4" style="table-layout: auto;" >
									<thead id="thid">									
										
									</thead>
									<tbody id="tbid">
																 									 
												
									</tbody>
								</table>	
								</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
  
  </div>
  <style>


#sample_4 th {
    padding-top: 12px;
    padding-bottom: 12px;
    text-align: center;
    background-color: #4b8df8;
    color: white;
     overflow:hidden;
    white-space: nowrap;
}
#sample_4 td {
  text-align: center;
     overflow:hidden;
    white-space: nowrap;
}


</style>