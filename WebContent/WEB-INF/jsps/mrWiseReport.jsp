<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>

<style>
p.padding {
	height: 0px;
	padding-left: 365px;
}
</style> 
<script type="text/javascript">	
   	    $(".page-content").ready
   	    (function(){   
   	    		
   	    			$('#rdngmonth').val("${rdngmonth}");
  
				   	    	  App.init();
				   	    	 TableEditable.init();
				   	    	 FormComponents.init();
				   	    	 UIExtendedModals.init();
				   	  	$('#MDASSideBarContents,#other-Reports,#mrWiseReport').addClass('start active ,selected');
				   	    	  $('#Circl_Data').addClass('start active ,selected');
				    	   	  $("#dash-board").removeClass('start active ,selected');
							  $('#other-Reports').addClass('start active ,selected');
								$('#circle').change(function() { 
					   	    	    var dropVal = $(this).val();
					   	    	    sessionStorage.setItem("SelectedItem", dropVal);
					   	    	});
					   	     var selectedItem = sessionStorage.getItem("SelectedItem");  
						   	    if(selectedItem==null)
						   	    {
						   	    	$("#circle").val("0").trigger("change"); 	 
						   	    }else{
						   	    	$("#circle").val(selectedItem).trigger("change");
						   	    }
   	    
			   	    	   }); 
   	    
  
     	    function editDetails(param,operation,sdoName)
     	   {
     	    var rdng=document.getElementById("rdngmonth").value; 
     	   	$.ajax({
     	   	url : "./getNumberOfInstaltionDetails/"+operation+"/"+sdoName+"/"+rdng,
     	 	    	type:'GET',
     	 	    	dataType:'json',
     	 	    	asynch:false,
     	 	    	cache:false,
     	 	    	success:function(response)
     	 	    	{
     	 	    		 var ListContent="";
	  					     $.each(response,function(i, obj) 
	                        		 {
	  					    	    ListContent= ListContent+"<tr>";
	  					    	  	ListContent= ListContent+"<td>"+(i+1) +" </td>";
	  					    		ListContent= ListContent+"<td id='sdoNameId'>"+ obj[0] +" </td>";
	  							    ListContent= ListContent+"<td id='tenderid'>"+obj[1]+"</td>";
	  							    ListContent= ListContent+"<td id='type'>"+ obj[2] +"</td>";
	  								ListContent= ListContent+"<td id='slno'>"+ obj[3] +" </td>";
	  							    ListContent= ListContent+"<td id='pqrdesc'> "+ obj[4] +"</td>";
	  							 	ListContent= ListContent+"<td id='phoneId'>"+ obj[5] +"</td>";
	  						        ListContent= ListContent+"<td id='marks'>"+ obj[6] +"</td>";
	  					            ListContent= ListContent+"</tr>";

	                            });
	  					
	                        $('#stack1 tbody').html(ListContent);
     	 	    	}
     	 		    
     	 	    });
     	   $('#'+param).attr("data-toggle", "modal");
 		  $('#'+param).attr("data-target","#stack1");
     	   	return false;
     	   }
       		 
       		 
       		function editDetails1(param,operation,sdoname)
   	     {
    	       var rdng=document.getElementById("rdngmonth").value; 
      		     $.ajax(
     					{
     							type : "GET",
     							url : "./getNumberOfPendingDetails/" +operation+"/"+sdoname+"/"+rdng,
     							dataType : "json",
     							cache:false,
     							async:false,
     							success : function(response)
     										{	
     		  					     			var ListContent="";
     		  					    			 $.each(response,function(i, obj) 
     		                        		 {
		     		  					    	    ListContent= ListContent+"<tr>";
		     		  					    		ListContent= ListContent+"<td>"+(i+1) +" </td>";
		     		  							    ListContent= ListContent+"<td >"+obj[0]+"</td>";
		     		  							    ListContent= ListContent+"<td >"+ obj[1] +"</td>";
		     		  								ListContent= ListContent+"<td >"+ obj[2] +" </td>";
		     		  							    ListContent= ListContent+"<td > "+ obj[3] +"</td>";
		     		  						        ListContent= ListContent+"<td >"+ obj[4] +"</td>";
		     		  						     	ListContent= ListContent+"<td > "+ obj[5] +"</td>";
		  		  						        	ListContent= ListContent+"<td >"+ obj[6] +"</td>";
		     		  					            ListContent= ListContent+"</tr>";
     		                           		 });
											

     		                        $('#stack2 tbody').html(ListContent);
     							 },
     					});
     		  $('#'+param).attr("data-toggle", "modal");
   		      $('#'+param).attr("data-target","#stack2");
      		 } 
       		 
      		
   	    

</script> 
<div class="page-content">
	
	<div class="row">
		<div class="col-md-12">
			<!-- BEGIN SUCCESS MESSAGE-->
			<c:if test = "${not empty msg}">
			   <div class="alert alert-danger display-show">
				<button class="close" data-close="alert"></button>
				<span style="color:red" >${msg}</span>
			   </div>
	        </c:if>
	                <!-- END BEGIN SUCCESS MESSAGE-->
    	  <a href="#" id="loading1" ></a>
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Meter Reader Wise Reports 
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> 
						<a href="javascript:;" class="remove"></a>
					</div> 
				</div>
					
					<div class="portlet-body">
					<form:form action="./meterReaderWiseSearchReport" modelAttribute="meterMasterEntity" commandName="meterMasterEntity">
					  <table>
					   <tr>
							<td>
								<div class="form-group"> 
								<label class="control-label col-md-11">Billed Month</label>
								<div class="input-group input-medium date date-picker"  data-date-format="yyyymm" data-date-viewmode="years" id="five">
									<form:input path="rdngmonth" type="text" class="form-control" name="rdngmonth" id="rdngmonth" ></form:input>
									<span class="input-group-btn">
									<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button></span>
								</div>
								</div>
						   </td>
											
						   <td> 
								<div class="form-group"> 
								<label class="control-label col-md-8">Circle</label>
								<form:select  class="form-control" path="" name="circle" id="circle" >
									<form:option value="0">Select Circle</form:option> 
									<c:forEach items="${circle}" var="element">
									<form:option value="${element}">${element}</form:option>
									</c:forEach>	
								</form:select>
								</div>
					      </td> 
						</tr>
					  </table>
					
					<div class="modal-footer">
						<button type="submit" class="btn blue pull-right" id="serachID" >Search</button> &nbsp;&nbsp;&nbsp;
					</div>
					  </form:form>
							
							 <div class="btn-group pull-right">
								 <button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i></button>
								 <ul class="dropdown-menu pull-right">
									<li><a href="#" id="print">Print</a></li>
									<li><a href="#" id="excelExport" onclick="tableToExcel('sample_editable_1', 'Holiday Info')">Export to Excel</a></li>
								</ul>
							</div> 

                  <table class="table table-striped table-hover table-bordered" id="sample_editable_1">
						<thead>
							<tr>
								<th>MR Name</th>
								<th>SDO Name</th>
								<th>Total NOI</th>
							  	<c:forEach var="i" begin="1" end="${days.size()}">
                                <th>Day${i}</th>
                               </c:forEach>
								<%-- <c:forEach var="element" items="${days}" >
								       <th><c:out value="${element[0]}"></c:out></th>
								</c:forEach>
								 --%>
							    <th>Total Entered</th>
								<th>Pending</th>
							</tr>
						</thead>
						<tbody>
					<c:set var="c" scope="page" value="0"></c:set>
					<c:set var="totalNOISum" scope="page" value="0"/>
						 <c:forEach var="element" items="${result2}" >
						 <c:set var="totalNOI" scope="page" value="${element[1]}"/>
						   <tr>
						   		<td>${element[0]}</td>
						    	<td>${element[2]}</td>
						   			<c:set var="c" scope="page" value="${c+1}"></c:set>
						  		<td><a href="#" onclick="editDetails(this.id,'${element[0]}','${element[2]}')" id="e${c}"><c:out value="${element[1]}"></c:out></a></td>
								   <c:set scope="page" var="count1" value="0"></c:set>
								   <c:set scope="page" var="pending1" value="0"></c:set>
								   <c:set scope="page" var="totalEntered1" value="0"></c:set>
								     <c:forEach var="elements" items="${result1}" >
								     <c:if test="${element[0] eq elements.mrname}">
								     <c:if test="${element[2] eq elements.sdoname}">
						          <c:set scope="page" var="count1" value="${count1+1}"></c:set>
						          <c:set scope="page" var="pending1" value="${pending1 + elements.count}"></c:set>
						          <c:set var="c" scope="page" value="${c+2}"></c:set>
						       <td><a href="#" onclick="getDaywiseData(this.id,'${elements.mrname}','${elements.sdoname}','${elements.date}')" id="getDayId${c}"><c:out value="${elements.count}"></c:out></a></td>
						     </c:if>
						     </c:if>
						    </c:forEach>
						    <c:if test="${count1 lt days.size()}">
						    <c:forEach begin="${count1}" end="${days.size()-1}" var="i">
						     <td></td>
						    </c:forEach>
						    </c:if>
						    <td>${pending1}</td>
						    <td><a href="#" onclick="editDetails1(this.id,'${element[0]}','${element[2]}')" id="editData1${c}">${element[1]-pending1}</a></td>
						    <c:set scope="page" var="count1" value="0"></c:set>
						    <c:set scope="page" var="pending1" value="0"></c:set>
						    <c:set scope="page" var="totalEntered1" value="0"></c:set>
						   </tr>
						   <c:set var="totalNOISum" scope="page" value="${totalNOI+totalNOISum}"/>
						  </c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
            
                   <div id="stack1" class="modal fade" tabindex="-1" data-replace="true">
								<div class="modal-dialog modal-full">
									<div class="modal-content">
									   <div class="modal-header">
									        <h4 class="modal-title">MR Wise Total NOI </h4>
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
										</div> 
										 <div class="btn-group pull-right">
								         <button class="btn dropdown-toggle" data-toggle="dropdown"> Tools <i class="fa fa-angle-down"></i></button>
										  <ul class="dropdown-menu pull-right">
											<li><a href="#" id="excelExport" onclick="tableToExcel('sample_entered_report', 'Holiday Info')">Export to Excel</a></li>
										  </ul>
										</div> 
						   		    <table class="table table-striped table-hover table-bordered" id="sample_entered_report">
						   		     <thead>
						   		          <tr>
							   		          <th>SlNo</th>
							   		          <th>SdoName</th>
							   		          <th>AccNo</th>
							   		          <th>Meterno</th>
							   		          <th>Name</th>
							   		          <th>Address</th>
							   		          <th>PhoneNo</th>
							   		          <th>MR Name</th>
						   		          </tr>
						   		         </thead>
						   		         <tbody >
						   		         </tbody>
						   		        </table>
										<div class="modal-footer">
											<button type="button" class="btn default" data-dismiss="modal">Close</button>
										</div>
										</div>
									</div>
								</div>
							
							   <div id="stack2" class="modal fade" tabindex="-1" data-replace="true">
								<div class="modal-dialog modal-full">
									<div class="modal-content">
									   <div class="modal-header">
									        <h4 class="modal-title">MR Wise Pending </h4>
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
										</div> 
						   		        <table class="table table-striped table-hover table-bordered">
						   		         <thead>
						   		          <tr>
							   		           <th>SlNo</th>
							   		          <th>SdoName</th>
							   		          <th>AccNo</th>
							   		          <th>Meterno</th>
							   		          <th>Name</th>
							   		          <th>Address</th>
							   		          <th>PhoneNo</th>
							   		          <th>MR Name</th>
						   		          </tr>
						   		         </thead>
						   		         <tbody >
						   		         </tbody>
						   		        </table>
										<div class="modal-footer">
											<button type="button" class="btn default" data-dismiss="modal">Close</button>
										</div>
										</div>
									</div>
								</div>

<!-- ----------------------Daywise Stack----------------------------- -->

 				<div id="stack3" class="modal fade" tabindex="-1" data-replace="true">
								<div class="modal-dialog modal-full">
									<div class="modal-content">
									   <div class="modal-header">
									        <h4 class="modal-title">DayWise Mr Details </h4>
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
										</div> 
										 <div class="btn-group pull-right">
								         	<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i></button>
												  <ul class="dropdown-menu pull-right">
													<li><a href="#" id="excelExport"onclick="tableToExcel('sample_entered_report', 'Holiday Info')">Export
															to Excel</a></li>
												  </ul>
										</div> 
						   		        <table class="table table-striped table-hover table-bordered" id="sample_entered_report">
						   		         <thead>
						   		          <tr>
							   		          <th>SDONAME</th>
							   		          <th>TADESC</th>
							   		          <th>ACCNO</th>
							   		          <th>METERNO</th>
							   		          <th>NAME</th>
							   		          <th>ADDRESS</th>
							   		          <th>MRNAME</th>
							   		          <th>READINGDATE</th>
							   		          <th>PHONENO</th>
						   		          </tr>
						   		         </thead>
						   		         <tbody >
						   		         </tbody>
						   		        </table>
										<div class="modal-footer">
											<button type="button" class="btn default" data-dismiss="modal">Close</button>
										</div>
										</div>
									</div>
								</div>

<!-- -------------------------------- Day Wise Details --------------------------------------- -->
<script>
function getDaywiseData(param,mrname,sdoname,readingdate)
{
 //  alert("param-==="+param+"mrname===="+mrname+"sdoname==="+sdoname+"readingdate=====>"+readingdate);
   
    var $modal = $('#loading1');
			 $('body').modalmanager('loading');
			  $modal.modal('loading').css({
				  'position': 'fixed',
			  'left': '50%',
			  'top': '50%',
			  'display' : 'block',
			  'overflow': 'hidden'
			 
			  });
   var rdng=document.getElementById("rdngmonth").value; 
   var circle=document.getElementById("circle").value;
   
   //alert("Reading Month--"+rdng+"circle=====>"+circle);
	     $.ajax(
				{
						type : "GET",
						url : "./getDaywiseMrDetails/"+mrname+"/"+sdoname+"/"+readingdate+"/"+circle+"/"+rdng,
						dataType : "json",
						cache:false,
						async:false,
						
						success : function(response)
									{	   
							//alert("Daywise Response===>"+response);
							 
	  					     var ListContent="";
	  					     $.each(response,function(i, obj) 
	                        		 {
	  					    	    ListContent= ListContent+"<tr>";
	  					    		ListContent= ListContent+"<td id='sdoNameId'>"+ obj[0] +" </td>";
							   	    ListContent= ListContent+"<td id='tadescId'> "+ obj[1] +"</td>";
	  							    ListContent= ListContent+"<td id='accnoId'>"+obj[2]+"</td>";
	  							    ListContent= ListContent+"<td id='meterNoId'>"+ obj[3] +"</td>";
	  								ListContent= ListContent+"<td id='nameId'>"+ obj[4] +" </td>";
	  								if(obj[5]== null)
	  								 {
	  								      ListContent= ListContent+"<td id='addId'></td>";
	  								 }
	  								else
	  								 {
	  							       ListContent= ListContent+"<td id='addId'> "+ obj[5] +"</td>";
	                        		 }
	  							 	ListContent= ListContent+"<td id='mrnameId'>"+ obj[6] +"</td>";
	  						        ListContent= ListContent+"<td id='rdngDateId'>"+ obj[7] +"</td>";
	  						        
	  						        if(obj[8] ==null)
	  						        	{
	  						          ListContent= ListContent+"<td id='phoneId'></td>";
	  						        	}
	  						        else
	  						        	{
	  						        ListContent= ListContent+"<td id='phoneId'>"+ obj[8] +"</td>";
	  						        	}
	  					            ListContent= ListContent+"</tr>";

	                            });
	  					
	                        $('#stack3 tbody').html(ListContent);
						    
						 
						 },
	                    error: function(e)
	                    {
							alert('Error: ' + e.responseText);
	                    } 
											
				
				});
	  $('body').modalmanager();
 $modal.modal();
	  $('#'+param).attr("data-toggle", "modal");
 $('#'+param).attr("data-target","#stack3");
  
	 } 						
								
</script>