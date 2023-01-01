<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script  type="text/javascript">
   	 $(".page-content").ready
	    (function(){   
	    		
	    			  App.init();
			   	    	 TableEditable.init();
			   	    	 FormComponents.init();
			   	    	$('#MDASSideBarContents,#other-Reports,#dataExport').addClass('start active ,selected');

				   	    	  $('#Circl_Data').addClass('start active ,selected');
				   	    	
				    	   	  $("#dash-board").removeClass('start active ,selected');
				    	      $('#other-Reports').addClass('start active ,selected');	
				   	    	    
							  $('#addData').click
							  (
							 
							  ); 
				   	    	     
							  $("#print").click
							  (
				   	    	    	function()
				   	    	    	{ 
				   	    	    	   printcontent($(".table-scrollable").html());
				   				    }
				   	    	   );  
			   	    	   }); 
	    
   /* 	window.onload = function(){ document.getElementById("loading").style.display = "none" }  */

  
</script>
<style>
.input-medium {
    width: 160px !important;
}
</style>

<div class="page-content">

	<div class="row">
		<div class="col-md-12">
			<%-- <c:if test="${results ne 'notDisplay'}">
				<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${results}</span>
				</div>
			</c:if> --%>
			<div class="portlet box blue" >
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>DATA EXPORT
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				<div class="row" style="margin-left:-1px;">
				<form action="./dataExportView" method="post" id="myform">
					<table style="width: 53%">
						<tbody>
						<tr>
							<td><select class="form-control select2me input-medium" name="sdocode" id="sdocode1"  onchange="getMrName();" >
						 	   <option value="0">Select SDO Code</option>
						 	   <option  value="%">ALL</option>
								<c:forEach items="${sdoCodesData}" var="element">
								<option value="${element}">${element}</option>
								</c:forEach></select>
					   		</td>
					   		<td id="tadesc1"><%-- <select class="form-control select2me input-medium" name="tadesc" id="tadesc1"  onchange="getMrName();">
						 	  <option value="0">Select TADESC </option>
								<c:forEach items="${tadesc}" var="element">
								<option value="${element}">${element}</option>
								</c:forEach></select>  --%>
					   		</td>
					   		
					   		<td id="MrNameSdo" >
					   		</td>
						    <td><button type="button" id="dataview" class="btn green" onclick="validationSDO();" style="margin-left: 105px; display:none;"><b>VIEW</b></button></td>
						  <td>
								
<!-- 								<a href="#" id="excelExport" class="btn blue"    onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
 -->								
							</td>
						</tr>
					</tbody>
					</table>
				</form>
					</div>
				</div>
				
				
			</div>
		</div>
	</div>
	
	
	<c:if test="${DataExport.size() > 0}">
	
	<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					
					
						<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption" style="width: 80%;"><i class="fa fa-edit"></i>Viewing Data
							
						  <a href="#" id="excelExport" class="btn green"  style="margin-left:67%;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
							
						 
						 <!-- <a href="#" id="excelExport" class="btn green"  style="margin-left:67%;" >Export to Excel</a>
						 -->
							
							</div>
							
							<div class="tools">
							   
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
									<th>RDNGMONTH</th>
							<!-- <th>TADESC</th> -->
							<th>SDOCODE</th>
							<th>ACCNO</th>
							<th>METRNO</th>
							<th>NAME</th>
							<th>ADDRESS</th>
							<th>CONSUMERSTATUS </th>
							<th>MCST</th>
							<th>CURRDNGKWH</th>
							<th>CURRRDNGKVAH</th>
							<th>CURRDNGKVA</th>
							<th>PF</th>
							<th>READINGREMARK</th>
							<th>REMARK</th>
							<th>XMLDATE</th>
							<th>XCURRDNGKWH</th>
							<th>XCURRRDNGKVAH</th>
							<th>XCURRDNGKVA </th>
							<th>XPF</th>
							<th>UNITSKWH</th>
							<th>UNITSKVAH</th>
							<th>UNITSKVA</th>
							<th>MRDSTATUS</th>
							<th>MTRMAKE</th>
							<th>MRNAME</th>
							<th>DNAME</th>
							<th>READINGDATE</th>
							<th>OLDSEAL</th>
							<th>NEWSEAL </th>
							<th>TARIFFCODE</th>
							<th>KWORHP</th>
							<th>SANLOAD</th>
							<th>PREVMETERSTATUS</th>
							<th>RTC</th>
							<th>MTRTYPE</th>
							<th>PRKWH</th>
							<th>PRKVAH</th>
							<th>PRKVA</th>
							<th>CONTRACTDEMAND</th>
							<th>CTRN </th>
							<th>CTRD</th>
							<th>MF</th>
							<th>MMRNAME</th>
							<th>MRINO</th>
							<th>TN</th>
							<th>SUPPLYVOLTAGE</th>
							<th>MRCODE</th>
							<th>MRD</th>
							<th>USERNAME</th>
							<th>AVGSIX</th>
							<th>OLDACCNO </th>
							<th>MNP</th>
							<th>INDUSTRYTYPE</th>
							<th>PHONENO</th>
							<th>DEMANDTYPE</th>
							<th>KNO</th>
							<th>MM</th>
							<th>SDONAME </th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="element" items="${DataExport }">
							<%-- <tr>
							<c:forEach begin="0" end="${DataExportList}" var="i">
									<td>${element[i]}</td>
							</c:forEach>
						  </tr> --%>
								<tr>
							<td>${element[0] }</td>
							<%-- <td>${element[1] }</td> --%>
							<td>${element[2] }</td>
							<td>${element[3] }</td>
							<td>${element[4] }</td>
							<td>${element[5] }</td>
							<td>${element[6] }</td>
							<td>${element[7] }</td>
							<td>${element[8] }</td>
							<td>${element[9] }</td>
							<td>${element[10] }</td>
							<td>${element[11] }</td>
							<td>${element[12] }</td>
							<td>${element[13] }</td>
							<td>${element[14] }</td>
							<td>${element[15] }</td>
							<td>${element[16] }</td>
							<td>${element[17] }</td>
							<td>${element[18] }</td>
							<td>${element[19] }</td>
							<td>${element[20] }</td>
							<td>${element[21] }</td>
							<td>${element[22] }</td>
							<td>${element[23] }</td>
							<td>${element[24] }</td>
							<td>${element[25] }</td>
							<td>${element[26] }</td>
							<td>${element[27] }</td>
							<td>${element[28] }</td>
							<td>${element[29] }</td>
							<td>${element[30] }</td>
							<td>${element[31] }</td>
							<td>${element[32] }</td>
							<td>${element[33] }</td>
							<td>${element[34] }</td>
							<td>${element[35] }</td>
							<td>${element[36] }</td>
							<td>${element[37] }</td>
							<td>${element[38] }</td>
							<td>${element[39] }</td>
							<td>${element[40] }</td>
							<td>${element[41] }</td>
							<td>${element[42] }</td>
							<td>${element[43] }</td>
							<td>${element[44] }</td>
							<td>${element[45] }</td>
							<td>${element[46] }</td>
							<td>${element[47] }</td>
							<td>${element[48] }</td>
							<td>${element[49] }</td>
							<td>${element[50] }</td>
							<td>${element[51] }</td>
							<td>${element[52] }</td>
							<td>${element[53] }</td>
							<td>${element[54] }</td>
							<td>${element[55] }</td>
							<td>${element[56] }</td>
							<td>${element[57] }</td>
							<td>${element[58] }</td>
								</tr> 
								</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					
					
	
	</div>
	</div>
	
	</c:if>
	<c:if test="${DataExport.size() == 0}">
	 <div class="alert alert-danger display-show">
					 <button class="close" data-close="alert"></button>
					 <span style="color:red" >NO RECORDS FOUND</span>
			       </div>
			       <script type="text/javascript">
			       
			       setTimeout(function() {  document.location.href = '/bsmartmdm/dataExport'; },5000);
			       /* document.location.href = '/bsmartmdm/dataExport'; */
			     
			       </script>
			       
	</c:if>
	<!-- <div id="loading">
  <img id="loading-image" src="/bsmartmdm/WebContent/resources/assets/img/loading.gif" alt="Loading..." />
</div> -->
	</div>






<script>


 

 function validationSDO()
 {
 	 
 	  if($("#sdocode1").val()==0 ||$("#sdocode1").val()==null)
 	  {
 	    bootbox.alert('Please Select SDOCODE');
 	    return false;
 	  }
 	  
 	  if($("#name1").val()==0 || $("#name1").val()==null)
 	  {
 	    bootbox.alert('Please Select MRNAME');
 	    return false;
 	  }
 	  
 	  $("#myform").submit();
 	
 }
function getTadescCode()
{
	var sitecode= $('#sdocode1').val();
	//var tadesc= $('#tadesc1').val();
	//alert(sitecode);
	
	$.ajax({
    	url:'./getTadescCode',
    	type:'GET',
    	dataType:'json',
    	data:{sitecode:sitecode},
    	asynch:false,
    	cache:false,
    	success:function(response)
    	{
    		
    		if(response != null)
    		{
    			
    			var html='<select class="form-control select2me input-medium" name="tadesc" id="tadesc2"  onchange="getMrName();"><option value=0>Select Category</option><option value=%>ALL</option>';
    			 for( var i=0;i<response.length;i++)
    			 {
    				
    				html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; ;
    			 }
    			 html+='</select>';
    			
    			 $("#tadesc1").empty();
    			 $("#tadesc1").append(html);
    			 $("#name1").select2();
    			 
    		}
    	}
    	
    });
}
function getMrName()
{
	var sitecode= $('#sdocode1').val();
	var tadesc= "all";
	
	//alert(sitecode);
	//alert(tadesc);
	$.ajax({
    	url:'./getMRName',
    	type:'GET',
    	dataType:'json',
    	data:{sitecode:sitecode,tades:tadesc},
    	asynch:false,
    	cache:false,
    	success:function(response)
    	{
    		
    		if(response != null)
    		{
    			
    			var html='<select class="form-control select2me input-medium" name="mrname1" id="name1" ><option value=0>Select MrName</option><option value=%>ALL</option>';
    			 for( var i=0;i<response.length;i++)
    			 {
    				
    				html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; ;
    			 }
    			 html+='</select>';
    			
    			 $("#MrNameSdo").empty();
    			 $("#MrNameSdo").append(html);
    			 $("#name1").select2();
    			 $('#dataview').show();
    			
    		}
    	}
    	
    });
	
	
}

/* loading button  */
 
 /* <script language="javascript" type="text/javascript">
 $(window).load(function() {
 $('#loading').hide();
}); */

 
</script>

<style>
#loading {
   width: 100%;
   height: 100%;
   top: 0;
   left: 0;
   position: fixed;
   display: block;
   opacity: 0.7;
   background-color: #fff;
   z-index: 99;
   text-align: center;
}

#loading-image {
  position: absolute;
  top: 100px;
  left: 240px;
  z-index: 100;
}

</style>
