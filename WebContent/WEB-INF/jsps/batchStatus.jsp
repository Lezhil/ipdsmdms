<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  <head>
  <script src="€https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="<c:url value='/resources/assets/scripts/table-editable.js'/>" type="text/javascript"></script>
  <!-- <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script> -->
  <script src="<c:url value='/resources/assets/scripts/sweetalert.min.js' />" type="text/javascript"></script>
  
  </head>
  <script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
  <script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
	
	<script src="resources/assets/scripts/app.js"></script>
	<script src="resources/assets/scripts/ui-extended-modals.js"></script>
  
  <script  type="text/javascript">
$(".page-content").ready(function()
    	   {     
	     App.init();
	     FormComponents.init();
    	 TableEditable.init();
    	
    	 UIExtendedModals.init();
  $("#billmonth").val(getPresentMonthDate('${selectedMonth}'));
    var mm='${batch1}';
  
    if(mm =='')
        {
    	 $('#mMake').val(0);
    	 $("#mMake").removeAttr("disabled", "disabled");
        }
    else{
		 $('#mMake').val(mm);
		 //$("#mMake").attr("disabled", "true");
		 importBatch('${UnZipFolderPath}');
	    }
    	   	 FormDropzone.init();
    	   $('#batchStatusId').addClass('start active ,selected');
    	   
    	   $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#seal-Manager,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
    	
    	   });
 

</script>

<style>
.input-medium {
    width: 160px !important;
}
</style>

<div class="page-content" >
 <div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Batch Status
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				<div class="row" style="margin-left:-1px;">
					<form:form action=""  method="post" id="batchStatusformId" modelAttribute="batchStatusmodel" commanName="batchStatusmodel">
								
									<div class="form-body">
									
									
										<div class="form-group">
					
						<table style="width: 53%">
						<tbody>
						<tr>
							<td>
							
							<select class="form-control select2me input-medium" name="circleId" id="circleId" style="margin-top: 10px;"  onchange="return changeSdocode();"> 
					   		<option value="">Select Circle</option>
					   		<c:forEach items="${circle}" var="element">
							<option value="${element}">${element}</option>
							</c:forEach></select>
					
							</td>
							
							<td id="sdocodeId">
					   		</td>
					   		
					   		<td id="batchDate" >
					   		      
						 	  
					   		</td>
					   		
					   		<tr>
							<td>
							<input type="button" id="dataview1" class="btn green" style="margin-left: 30px;margin-top: 11px;border-right-width: -17px;" onclick="return mmUpdateFunction();" value="MM Updated" >
							</td>
								<td>
							<input type="button" id="dataview2" class="btn green" style="margin-left: 30px;margin-top: 11px;border-right-width: -17px;" onclick="return mmNotUpdateFunction();" value="MM Not Updated" >
							</td>
								<td>
							<input type="button" id="dataview3" class="btn green" style="margin-left: 30px;margin-top: 11px;border-right-width: -17px;" onclick="return xmlUpdateFunction();" value="XML Updated" >
							</td>
								<td>
							<input type="button" id="dataview4" class="btn green" style="margin-left: 30px;margin-top: 11px;border-right-width: -17px;" onclick="return xmlNotUpdateFunction();" value="XML Not Updated" >
							</td>
								<td>
							<input type="button" id="dataview5" class="btn green" style="margin-left: 30px;margin-top: 11px;border-right-width: -17px;" onclick="return xmlAndMMUpdateFunction();" value="MM And XML Updated" >
							</td>
							<td>
							<input type="button" id="dataview6" class="btn green" style="margin-left: 30px;margin-top: 11px;border-right-width: -17px;" onclick="return allDataFunction();" value="Meter Doesn't Exist" >
							</td>
							</tr>
					
					</table>
					</div>
					</div>
					
						</form:form>
					</div>
				</div>
				
				
			</div>
			
			
			
			
				<table class="table table-striped table-hover table-bordered" id=sample_editable_1>
									<thead>									
										<tr>								
											    <th>CIRCLE</th>
												<th>SDO CODE</th>
												<th>SDO NAME</th>
												<th>METER NO</th>
												<th>READING DATE</th>
												<th>FILE NAME</th>
												<th>PARSE STATUS</th>
												<th>AMR STATUS</th>
												
												
	            						</tr>
									</thead>
									<tbody id="batchTabId">
							
									
									</tbody>
								</table>
</div>


<script>
function changeSdocode()
{
	var circle=$("#circleId").val();

	$.ajax({
		type : "POST",
		url:"./getSdocodeBacedoncircle",
		data:{
			   circle:circle
		     },
		success :function(response)
		{	
			 if(response != null)
	    		{
	    			
	    			var html='<select class="form-control input-medium" name="sdocode" id="sdocode"  style="margin-top: 10px;" style="margin-top: 10px;" onchange="return changebatchDate();">'
	    			+'<option value=0>Select SDO Code</option>'
	    			+'<option value="All">All</option>';
	    			
	    			 for( var i=0;i<response.length;i++)
	    			 {
	    				
	    				html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
	    			 }
	    			 html+='</select>';
	    			
	    			 $("#sdocodeId").empty();
	    			 $("#sdocodeId").append(html);
	    			 
	    		}
		}
		
	});
	
}


/* function changebatchDate()
{
	var circle =$("#circleId").val();
	var sdocode=$("#sdocode").val();
	
	$.ajax({
		type : "POST",
		url:"./getDateOnSdocodeAndcircle",
		data:{
			   circle:circle,sdocode:sdocode
		     },
		success :function(response)
		{	
			alert(response);
			 if(response != null)
	    		{
	    			
	    			var html='<select class="form-control input-medium" name="batchDateId" id="batchDateId"  style="margin-top: 10px;" style="margin-top: 10px;" >'
	    			+'<option value=0>Select Reading Date</option>';
	    			
	    			 for( var i=0;i<response.length;i++)
	    			 {
	    				
	    				html+="<option value='"+moment(response[i]).format("YYYY-MM-DD")+"'>"+moment(response[i]).format("YYYY-MM-DD")+"</option>"; 
	    			 }
	    			 html+='</select>';
	    			
	    			 $("#batchDate").empty();
	    			 $("#batchDate").append(html);
	    			 
	    		}
		}
		
	});

}	 */


/* function mmUpdateFunction()
{
	var circle=$("#circleId").val();
	var sdocode=$("#sdocode").val();
	
	//var batchdate=$("#batchDateId").val();
	
	if(circle=="")
		{
		bootbox.alert("Please Select Circle");
		return false;
		}
	if(sdocode=="0")
	{
	bootbox.alert("Please Select SDO Code");
	return false;
	}

	
	  $("#batchStatusformId").attr("action","./searchMMUpdatedRecords");
	
} */


 function mmUpdateFunction()
{
	var circle=$("#circleId").val();
	var sdocode=$("#sdocode").val();
	
	//var batchdate=$("#batchDateId").val();
	
	if(circle=="")
		{
		bootbox.alert("Please Select Circle");
		return false;
		}
	if(sdocode=="0")
	{
	bootbox.alert("Please Select SDO Code");
	return false;
	}
	
   $.ajax({
		type : "POST",
		url:"./searchMMUpdatedRecords",
		data:{
			   circle:circle,sdocode:sdocode
		     },
		dataType:'json',
		success :function(response)
		{	
			if(response=="")
			 {
			 bootbox.alert("Records Are Not Found");
			 }
			var html="";
			for(var i=0;i<response.length;i++)
				{
				
					html+="<tr>";
				if(response[i][0]==null || response[i][0] =='null')
					{html+="<td></td>";}
				else{html+="<td>"+response[i][0]+"</td>";}
				if(response[i][1]==null || response[i][1] =='null')
				 	{html+="<td></td>";}
			    else{html+="<td>"+response[i][1]+"</td>";}
				if(response[i][2]==null || response[i][2] =='null')
					{html+="<td></td>";}
				else{html+="<td>"+response[i][2]+"</td>";}
				if(response[i][3]==null || response[i][3] =='null')
			 		{html+="<td></td>";}
		   	    else{html+="<td>"+response[i][3]+"</td>";}
				if(response[i][4]==null || response[i][4] =='null')
					{html+="<td></td>";}
				else{html+="<td>"+response[i][4]+"</td>";}
				if(response[i][5]==null || response[i][5] =='null')
			 		{html+="<td></td>";}
		   		else{html+="<td>"+response[i][5]+"</td>";}
				if(response[i][6]==null || response[i][6] =='null')
					{html+="<td></td>";}
				else{html+="<td>"+response[i][6]+"</td>";}
				if(response[i][7]==null || response[i][7] =='null')
		 			{html+="<td></td>";}
	   	    	else{html+="<td>"+response[i][7]+"</td>";}
			 		html+= "</tr>";
            
				}
			$("#batchTabId").empty();
			$("#batchTabId").append(html);
			
		}
		
	}); 
} 
   
   function mmNotUpdateFunction()
   {
    var circle=$("#circleId").val();
	var sdocode=$("#sdocode").val();
		
	if(circle=="")
	{
	bootbox.alert("Please Select Circle");
	return false;
	}
	if(sdocode=="0")
	{
	bootbox.alert("Please Select SDO Code");
	return false;
	}
	
	  //$("#batchStatusformId").attr("action","./searchNotMMUpdatedRecords");
	  
	 $("#batchTabId").empty();
			
	   $.ajax({
			type : "POST",
			url:"./searchNotMMUpdatedRecords",
			data:{
				   circle:circle,sdocode:sdocode
			     },
			dataType:'json',
			success :function(response)
			{	
				 if(response=="")
					 {
					 bootbox.alert("Records Are Not Found");
					 }
				 
				var html="";
				for(var i=0;i<response.length;i++)
					{
					
						html+="<tr>";
					if(response[i][0]==null || response[i][0] =='null')
						{html+="<td></td>";}
					else{html+="<td>"+response[i][0]+"</td>";}
					if(response[i][1]==null || response[i][1] =='null')
					 	{html+="<td></td>";}
				    else{html+="<td>"+response[i][1]+"</td>";}
					if(response[i][2]==null || response[i][2] =='null')
						{html+="<td></td>";}
					else{html+="<td>"+response[i][2]+"</td>";}
					if(response[i][3]==null || response[i][3] =='null')
				 		{html+="<td></td>";}
			   	    else{html+="<td>"+response[i][3]+"</td>";}
					if(response[i][4]==null || response[i][4] =='null')
						{html+="<td></td>";}
					else{html+="<td>"+response[i][4]+"</td>";}
					if(response[i][5]==null || response[i][5] =='null')
				 		{html+="<td></td>";}
			   		else{html+="<td>"+response[i][5]+"</td>";}
					if(response[i][6]==null || response[i][6] =='null')
						{html+="<td></td>";}
					else{html+="<td>"+response[i][6]+"</td>";}
					if(response[i][7]==null || response[i][7] =='null')
			 			{html+="<td></td>";}
		   	    	else{html+="<td>"+response[i][7]+"</td>";}
						html+= "</tr>";
	            
					}
				$("#batchTabId").empty();
				$("#batchTabId").append(html);
				
			}
			
		});  
   }
   
   function xmlUpdateFunction()
   {
	    var circle=$("#circleId").val();
		var sdocode=$("#sdocode").val();
			
		if(circle=="")
		{
		bootbox.alert("Please Select Circle");
		return false;
		}
		if(sdocode=="0")
		{
		bootbox.alert("Please Select SDO Code");
		return false;
		}
		
		$("#batchTabId").empty();
				
		   $.ajax({
				type : "POST",
				url:"./searchXmlUpdatedRecords",
				data:{
					   circle:circle,sdocode:sdocode
				     },
				dataType:'json',
				success :function(response)
				{	
					 if(response=="")
						 {
						 bootbox.alert("Records Are Not Found");
						 }
					 
					var html="";
					for(var i=0;i<response.length;i++)
						{
						
							html+="<tr>";
						if(response[i][0]==null || response[i][0] =='null')
							{html+="<td></td>";}
						else{html+="<td>"+response[i][0]+"</td>";}
						if(response[i][1]==null || response[i][1] =='null')
						 	{html+="<td></td>";}
					    else{html+="<td>"+response[i][1]+"</td>";}
						if(response[i][2]==null || response[i][2] =='null')
							{html+="<td></td>";}
						else{html+="<td>"+response[i][2]+"</td>";}
						if(response[i][3]==null || response[i][3] =='null')
					 		{html+="<td></td>";}
				   	    else{html+="<td>"+response[i][3]+"</td>";}
						if(response[i][4]==null || response[i][4] =='null')
							{html+="<td></td>";}
						else{html+="<td>"+response[i][4]+"</td>";}
						if(response[i][5]==null || response[i][5] =='null')
					 		{html+="<td></td>";}
				   		else{html+="<td>"+response[i][5]+"</td>";}
						if(response[i][6]==null || response[i][6] =='null')
							{html+="<td></td>";}
						else{html+="<td>"+response[i][6]+"</td>";}
						if(response[i][7]==null || response[i][7] =='null')
				 			{html+="<td></td>";}
			   	    	else{html+="<td>"+response[i][7]+"</td>";}
							html+= "</tr>";
		            
						}
					$("#batchTabId").empty();
					$("#batchTabId").append(html);
					
				}
				
			}); 
   }

   
   function xmlNotUpdateFunction()
   {
	    var circle=$("#circleId").val();
		var sdocode=$("#sdocode").val();
			
		if(circle=="")
		{
		bootbox.alert("Please Select Circle");
		return false;
		}
		if(sdocode=="0")
		{
		bootbox.alert("Please Select SDO Code");
		return false;
		}
		
		$("#batchTabId").empty();
				
		   $.ajax({
				type : "POST",
				url:"./searchXmlNotUpdatedRecords",
				data:{
					   circle:circle,sdocode:sdocode
				     },
				dataType:'json',
				success :function(response)
				{	
					
					 if(response=="")
						 {
						 bootbox.alert("Records Are Not Found");
						 }
					 
					var html="";
					for(var i=0;i<response.length;i++)
						{
						
							 html+="<tr>";
						if(response[i][0]==null || response[i][0] =='null')
							{html+="<td></td>";}
						else{html+="<td>"+response[i][0]+"</td>";}
						if(response[i][1]==null || response[i][1] =='null')
						 	{html+="<td></td>";}
					    else{html+="<td>"+response[i][1]+"</td>";}
						if(response[i][2]==null || response[i][2] =='null')
							{html+="<td></td>";}
						else{html+="<td>"+response[i][2]+"</td>";}
						if(response[i][3]==null || response[i][3] =='null')
					 		{html+="<td></td>";}
				   	    else{html+="<td>"+response[i][3]+"</td>";}
						if(response[i][4]==null || response[i][4] =='null')
							{html+="<td></td>";}
						else{html+="<td>"+response[i][4]+"</td>";}
						if(response[i][5]==null || response[i][5] =='null')
					 		{html+="<td></td>";}
				   		else{html+="<td>"+response[i][5]+"</td>";}
						if(response[i][6]==null || response[i][6] =='null')
							{html+="<td></td>";}
						else{html+="<td>"+response[i][6]+"</td>";}
						if(response[i][7]==null || response[i][7] =='null')
				 			{html+="<td></td>";}
			   	    	else{html+="<td>"+response[i][7]+"</td>";}
							html+= "</tr>";
		            
						}
					$("#batchTabId").empty();
					$("#batchTabId").append(html);
					
				}
				
			}); 
   }
   
   function xmlAndMMUpdateFunction()
   {
	    var circle=$("#circleId").val();
		var sdocode=$("#sdocode").val();
			
		if(circle=="")
		{
		bootbox.alert("Please Select Circle");
		return false;
		}
		if(sdocode=="0")
		{
		bootbox.alert("Please Select SDO Code");
		return false;
		}
		$("#batchTabId").empty();
				
		   $.ajax({
				type : "POST",
				url:"./searchXmlAndMMUpdatedRecords",
				data:{
					   circle:circle,sdocode:sdocode
				     },
				dataType:'json',
				success :function(response)
				{	
					 if(response=="")
						 {
						 bootbox.alert("Records Are Not Found");
						 }
					 
					var html="";
					for(var i=0;i<response.length;i++)
						{
						
							html+="<tr>";
						if(response[i][0]==null || response[i][0] =='null')
							{html+="<td></td>";}
						else{html+="<td>"+response[i][0]+"</td>";}
						if(response[i][1]==null || response[i][1] =='null')
						 	{html+="<td></td>";}
					    else{html+="<td>"+response[i][1]+"</td>";}
						if(response[i][2]==null || response[i][2] =='null')
							{html+="<td></td>";}
						else{html+="<td>"+response[i][2]+"</td>";}
						if(response[i][3]==null || response[i][3] =='null')
					 		{html+="<td></td>";}
				   	    else{html+="<td>"+response[i][3]+"</td>";}
						if(response[i][4]==null || response[i][4] =='null')
							{html+="<td></td>";}
						else{html+="<td>"+response[i][4]+"</td>";}
						if(response[i][5]==null || response[i][5] =='null')
					 		{html+="<td></td>";}
				   		else{html+="<td>"+response[i][5]+"</td>";}
						if(response[i][6]==null || response[i][6] =='null')
							{html+="<td></td>";}
						else{html+="<td>"+response[i][6]+"</td>";}
						if(response[i][7]==null || response[i][7] =='null')
				 			{html+="<td></td>";}
			   	    	else{html+="<td>"+response[i][7]+"</td>";}
							html+= "</tr>";
		            
						}
					$("#batchTabId").empty();
					$("#batchTabId").append(html);
					
				}
				
			}); 
   }

   
   function allDataFunction()
   {
	   $.ajax({
			type : "POST",
			url:"./searchAllRecordsForNoCircle",
			data:{},
			dataType:'json',
			success :function(response)
			{	
				 if(response=="")
					 {
					 bootbox.alert("Records Are Not Found");
					 }
				 
				var html="";
				for(var i=0;i<response.length;i++)
					{
					
						html+="<tr>";
					if(response[i][0]==null || response[i][0] =='null')
						{html+="<td></td>";}
					else{html+="<td>"+response[i][0]+"</td>";}
					if(response[i][1]==null || response[i][1] =='null')
					 	{html+="<td></td>";}
				    else{html+="<td>"+response[i][1]+"</td>";}
					if(response[i][2]==null || response[i][2] =='null')
						{html+="<td></td>";}
					else{html+="<td>"+response[i][2]+"</td>";}
					if(response[i][3]==null || response[i][3] =='null')
				 		{html+="<td></td>";}
			   	    else{html+="<td>"+response[i][3]+"</td>";}
					if(response[i][4]==null || response[i][4] =='null')
						{html+="<td></td>";}
					else{html+="<td>"+response[i][4]+"</td>";}
					if(response[i][5]==null || response[i][5] =='null')
				 		{html+="<td></td>";}
			   		else{html+="<td>"+response[i][5]+"</td>";}
					if(response[i][6]==null || response[i][6] =='null')
						{html+="<td></td>";}
					else{html+="<td>"+response[i][6]+"</td>";}
					if(response[i][7]==null || response[i][7] =='null')
			 			{html+="<td></td>";}
		   	    	else{html+="<td>"+response[i][7]+"</td>";}
						html+= "</tr>";
	            
					}
				$("#batchTabId").empty();
				$("#batchTabId").append(html);
				
			}
			
		}); 
   }
</script>