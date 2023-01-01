<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {  
	              var elements = document.getElementsByName("metrno");
	  			  for (var i = 0; i < elements.length; i++) {
	      		  elements[i].oninvalid = function(e) {
	           	  e.target.setCustomValidity("");
	           	  if (!e.target.validity.valid) {
	                e.target.setCustomValidity("Please Enter Meter Number");
	              }
	          };
	            elements[i].oninput = function(e) {
	            e.target.setCustomValidity("");
	          };
	       }
	            
	  
   	    	       App.init();
	   	    	   TableManaged.init();
	   	    	   FormComponents.init();
	   	    	   UIDatepaginator.init();
	   	    	$('#MDASSideBarContents,#mobile-billing-data,#meterReaderDetails').addClass('start active ,selected');
	   	     	   $("#dash-board,#360d-view,#cumulative-Analysis,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	   	    	 
	   	    });

  
	 
	function deleteMrDetails(param,opera)
	{
		
		//var operation=parseInt(opera);
			
		  bootbox.confirm("Are you sure want to delete this record ?", function(result) {
			  if(result == true)
	             {
		          $("#delid").attr("value",opera);
		          $('form#delMrData').attr('action','./detletMrDetails').submit();
	             }
	
		  });
	}
	
	function validation(form) 
	{
		 var mrname=form.mrname.value;
		 var device=form.device.value;
		 var mdevice='${sbmno}';
		 if(mrname=="deafult")
			 {
			 bootbox.alert("Please select Mrname");
			 return false;
			 } 
		 if(mdevice.length==2)
		 {
			 bootbox.alert("No devices are avilable");
			 return false;
		 }
		 else
		 {
		   if(device!=null && device=="deafult")
		    {
		     bootbox.alert("Please select Device");
		     return false;
		    }
		 }
		 
		
	}
	/*  
	 function checkAvailDevice()
	 {
		
		var device=document.getElementById("device").value;
		
		$.ajax(
	  			{
	  					type : "GET",
	  					url : "./checkDeviceDulicate"+ device,
	  					dataType : "json",
	  					success : function(response)
	  												{
	  						                       
	  												if(response!=null)
	  													{
	  													 
												         alert("This Device  is already allocated ");
	  												   
	  													}
	  												}
	  			});
	
       }
	
	 
	 function checkMrname()
	 {
		 
		 var mrname=document.getElementById("mrname").value;
	     $.ajax(
	  			{
	  					type : "GET",
	  					url : "./checkMrnameDulicate/" + mrname,
	  					dataType : "json",
	  					success : function(response)
	  												{	
	  					
	  											    if(response!=null)
									    	    	{
	  											    	bootbox.alert(response);
									    	        }
	  											   
	  											  
	  											}
	  			}); 
}	 */


function getmrname(param)
{
	/* $("#mrdiv").empty(); */

$.ajax({
	type : "GET",
	url : "./getMrNameBasedOncir",
	data:{param:param},
	cache : false,
	async : false,
	dataType:'json',
	success : function(response){

		
		var newOption = $('<option>');
        newOption.attr('value',0).text(" "); 
        $('#mrname').empty(newOption);
        var defaultOption = $('<option>');
        defaultOption.attr('value',"").text("Select Mrname");
        $('#mrname').append(defaultOption);
        
			/*  var html='<select class="form-control select2me input-medium" name="mrnamediv" id="mrnamediv" >Select MRname</option>'; */
			 for( var i=0;i<response.length;i++)
			 {
				 $("#mrname").append('<option value="'+response[i]+'">'+response[i]+'</option>');
					
				/* html+="<option value='"+response[i]+"'>"+response[i]+"</option>";  */
			 }
		/* 	 html+='</select>';
			 
	$("#mrdiv").append(html); */


	}
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
	 
                        
			
          
						<div class="portlet-title">
							    <div class="caption"><i class="fa fa-reorder"></i>Meter Reader Details</div>
							    <div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						
						
						<div class="portlet-body">
						
						 <div class="col-md-12">
										
							<form:form action="./addMeterReaderDetails" name="form" method="post" id="" modelAttribute="meterReaderDetailsEntity" commandName="meterReaderDetailsEntity" onchange="checkAvailDevice()" >
							     <table>
							     
							     <td>Circle</td>
										   <td><form:select path="" name="circle" class="form-control" id="circle" onchange="return getmrname(this.value);">
										     <form:option value="0">select </form:option> 
										      <c:forEach items="${circle}" var="element">
													<form:option value="${element}">${element}</form:option>
													</c:forEach>	
										                                                   
										   </form:select></td> 
							     
									 <tr>
									  <%--  <td>MRName</td>									  
									   <td><select class="form-control select2me" name="mrname" id="mrname" >
									    <option value="deafult">Select Please</option>
									    <c:forEach var="elements" items="${mrnamedata}">
									    <option value="${elements}">${elements}</option>
									    </c:forEach></select>
									   </td> --%>
									    <td>MRName</td>
										   <td id="mrdiv"><form:select path="" name="mrname" class="form-control" id="mrname">
										     <form:option value="0">Select Mrname</form:option> 
										     <%--  <c:forEach items="${divisions}" var="element">
													<form:option value="${element}">${element}</form:option>
													</c:forEach>	 --%>
										                                                   
										   </form:select>  </td> 
									   
									   </tr>
									   
									   <tr>
									   <td>Device</td>
									   <td><select class="form-control select2me"  name="device" id="device" >
									    <option value="deafult">Select Please</option> 
									    <c:forEach var="elements" items="${sbmno}">
									    <option value="${elements.sbmnumber}">${elements.sbmnumber}</option>
									    </c:forEach>
									  
									    </select>
									    
									   </td>
									   
									   </tr>
									
										
									 </table>
									  <br><br>
									 <div class="modal-footer">	
                                      <button type="submit" class="btn green" onclick="return validation(this.form)">Submit</button>
												</div>
												
					                </form:form>
											
									
					
							
						</div>
						
						
				   <form method="post" id="delMrData">
				    <input type="hidden" id="delid" name="delMrId">
				   </form>		
					<div class="portlet-body">
						
						<table class="table table-striped table-bordered table-hover" id="sample_2">
						<thead>
						 <tr>
						     <!-- <th hidden="true">MriId</th> -->
						    <th>MRName</th>
						    <th>SBM Number</th>
						    <th>Date</th>
						    <th>Delete</th> 
						     
						</tr>
						</thead>
					    <tbody>
						<c:forEach items="${mrdetails}" var="elements">
						<tr>
						 <%-- <td hidden="true">${elements.mriid}</td> --%>
						 <td>${elements.mrname}</td>
						 <td>${elements.device}</td>
						 <td>${elements.datestamp}</td>
						 <td><a href="#" onclick="deleteMrDetails(this.device,${elements.device})">Delete</a></td>
						 </tr>
						 </c:forEach>
						</tbody>
						</table>
						
		         </div>
		
			
</div>
</div>
</div>