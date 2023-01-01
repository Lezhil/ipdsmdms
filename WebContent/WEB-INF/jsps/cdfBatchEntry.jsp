<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
  <head>
  <script src=”https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js”></script>
  <script src="<c:url value='/resources/assets/scripts/table-editable.js'/>" type="text/javascript"></script>
  <!-- <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script> -->
  <script src="<c:url value='/resources/assets/scripts/sweetalert.min.js' />" type="text/javascript"></script>
  
  </head>

<script  type="text/javascript">
$(".page-content").ready(function()
    	   {     
  $("#billmonth").val(getPresentMonthDate('${selectedMonth}'));
    var mm='${batch1}';
    //alert("mm==>"+mm);
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


    
	App.init();

    	   	 FormDropzone.init();
    		 FormComponents.init();
    	   $('#cdfBatch1').addClass('start active ,selected');
    	   
    	   $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#seal-Manager,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
    	
    	   });
 
  
</script>
  
<style>
.input-medium {
    width: 160px !important;
}
</style>
<div class="page-content" >
	<!-- BEGIN PAGE CONTENT-->
	<c:if test = "${result ne 'notDisplay'}"> 			        
			        <div class="alert alert-success display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${result}</span>
							</div>
			        </c:if>
			 <div class="alert alert-success display-show" id="alertMsg" style="display: none;">
							<button class="close" data-close="alert"></button>
							 <span style="color:green" id="parseCompleteMsg"></span> 
				</div>
			<!-- END PAGE CONTENT-->

<div class="row">
<div class="col-md-7">
					<!-- BEGIN PROGRESS BARS PORTLET-->
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-upload"></i>Batch upload For Different Metermake
					</div>
				</div>
				<div class="portlet-body">

					<form action="./uploadCdfBatchFile" id="uploadFile"
						enctype="multipart/form-data" method="post" >
						<div class="row">
						<div class="col-md-4">
					   		<select class="form-control select2me input-medium" name="batch1" id="mMake"  > 
					   		 <option value="0">Select Metermake</option>
													
													<option value="GENUSD">GENUSD</option>
													<option value="4">GENUS NON DLMS</option>
													<option value="6">HPL</option>
													<option value="101">LNG</option>
													<option value="3">LNT</option>
													<option value="1">SECURE</option>
													
								</select>
							</div>
						<div class="col-md-4" id="ImportUpload">
							<label for="exampleInputFile1"> <input
								type="file" id="fileUpload" name="fileUpload" onchange="return browse();"><br/>

							<button class="btn blue pull-left" style="display: block;"
								id="uploadButton" onclick="return finalSubmit();" >Upload & Import</button>
								
							&nbsp;&nbsp;
							
				      <%-- <button class="btn green"  id="importButton" onclick="return importBatch('${UnZipFolderPath}')" >Import</button> </label>
					  --%>	
					 
						</div>
						</div>
					</form>
					<div id='loadingmessage' style="margin-left: 175px;margin-top: -30px; display: none">
					
					<label>Please Wait Inserting <span id="lableDisplay" style="color: #570214;font-weight: bold;" ></span>Data..</label>
  									<img src="resources/assets/img/home/ajax-loader(2).gif" class="ajax-loader" width=150px;  height=30px;>
  									<!-- <span id="load" class="wait" style="color:blue;">Parsing Started</span> -->
								</div>
						</div>
				</div>
			</div>
		</div>
		
			<div class="row" id="parsedRow"  style="display: none;">
			<div class="col-md-12" >
					<!-- BEGIN PROGRESS BARS PORTLET-->
					<div class="portlet box green">
						<div class="portlet-title">
							<div class="caption"><span id="parsedcount" >Batch Upload Status:  </span><span id="BUstatus" style="color:yellow"></span>
							
							
							
							<span id="total" style="margin-left: 256px;"> </span>
							</div>
							
							<div class="tools">
								<a href="#" id="excelExport" style="display:none;color: yellow" onclick="tableToExcel('sample_editable_1', 'Import_Status')">Export to Excel</a>
							</div>
						</div>
						<%-- <div class="table" >
							<table class="table table-striped table-hover table-bordered" >
							
							<thead>
											<tr>
											<th colspan="2" align="center"><center>XMLImport Table</center></th>
											<th colspan="2" align="center"><center>MeterMaster Table</center></th>
											</tr>
											<tr>
											<th><center>MeterNo Inserted : <span id="MXIcount" style="color:red"></span> </center></th>
											<th><center>MeterNo NotInserted : <span id="MXNIcount" style="color:red"></span></center></th>
											<th><center>MeterNo Updated : <span id="MUcount" style="color:red"></span></center></th>
											<th><center>MeterNo NotUpdated : <span id="MNUcount" style="color:red"></span></center></th>
											</tr>
											
											</thead>
							
							
							</table>
							</div> --%>
						<div class="portlet-body" style="overflow: auto;height: 500px;">
						<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
						
										 <thead>
											<tr>
											<th colspan="2" align="center"><center>XMLImport Table</center></th>
											<th colspan="2" align="center"><center>MeterMaster Table</center></th>
											</tr>
											<tr>
											<th><center>MeterNO Inserted : <span id="MXIcount" style="color:red"></span> </center></th>
											<th><center>MeterNO NotInserted : <span id="MXNIcount" style="color:red"></span></center></th>
											<th><center>MeterNO Updated : <span id="MUcount" style="color:red"></span></center></th>
											<th><center>MeterNO NotUpdated : <span id="MNUcount" style="color:red"></span></center></th>
											</tr>
											
											</thead> 
												<tbody id="FileStatus">
												</tbody>			
												</table>
												
										</div>	
										
								</div>	
							</div>
							</div>
			</div>
			

         
		<script>


		function browse()
		{
			var meterMake=$("#mMake").val(); 

			var MK="";
						
			if(meterMake=="6")
			{
			 MK="HPL";
			}
			if(meterMake=="1")
			{
			 MK="SECURE";
			}
			if(meterMake=="GENUSD")
			{
			 MK="GENUSD";
			}
			if(meterMake=="3")
			{
			MK="LNT";
			}
			if(meterMake=="101")
			{
			MK="LNG";
			}
			if(meterMake=="4")
			{
			MK="Genus Non DLMS";
			}
			 swal({
	            title:  "Warning !",
	            text: "Upload Only "+MK+ " Metermake Files !" ,
	            confirmButtonColor: "#57020F",
	            
	        }); 
		}

		 function finalSubmit()
		  {
			  
			 if(document.getElementById("fileUpload").value == "" || document.getElementById("fileUpload").value == null)
				  {
				    bootbox.alert(' Please select zip file to upload');
			 	    return false;
				  }
			 var meterMake=$("#mMake").val(); 
			 if(meterMake==""||meterMake==null||meterMake==0)
				{
				 swal({
			            title:  "",
			            text: "Please select MeterMake",
			            confirmButtonColor: "#d7dde5",
			        });
					return false;
				}
			  
			
			}
//
		 var mke='${batch1}'; 
		
			//alert(mke);
			var MMK="";
						
			if(mke=="6")
			{
				MMK="HPL";
			}
			if(mke=="1")
			{
			 MMK="SECURE";
			}
			if(mke=="GENUSD")
			{
			 MMK="GENUSD";
			}
			if(mke=="3")
			{
			MMK="LNT";
			}
			if(mke=="101")
			{
			MK="LNG";
			}
			if(mke=="4")
			{
			MMK="Genus Non DLMS";
			}

			//	
 function importBatch(path)
  {
	    
	    var meterMake=$("#mMake").val(); 
		 if(meterMake==""||meterMake==null||meterMake==0)
			{
			 swal({
		            title:  "Please select MeterMake",
		            text: "",
		            confirmButtonColor: "#d7dde5",
		        });
				return false;
			}
		 if(path==""||path==null||path==0)
			{
			 swal({
		            title:  "Please Upload File",
		            text: "",
		            confirmButtonColor: "#d7dde5",
		        });
				return false;
			}
		
		 if(meterMake=="6")
		{

			 var mke=$("#mMake").val('${batch1}'); 
			
				
			 document.getElementById('MXIcount').innerHTML ="";
				document.getElementById('MXNIcount').innerHTML ="";
				document.getElementById('MUcount').innerHTML ="";
				document.getElementById('MNUcount').innerHTML ="";
				document.getElementById('total').innerHTML ="";
				document.getElementById("BUstatus").innerHTML = MMK;
				
			 $("#parsedRow").hide();
			 $("#ImportUpload").hide();
			 $("#loadingmessage").show();
			 $("#lableDisplay").text("HPL ");
			
	    $.ajax({
			type : "POST",
			url : "./HPLCDFImport",
			data:{path:path},
			cache:false,
	 		 success : function(response)
			 {
				 
	 			 $("#loadingmessage").hide();
	 			 $("#parsedRow").show();
	 			 $("#ImportUpload").show();
	 			$("#mMake").val(0);
	 			//alert(response+"=="+response);
				document.getElementById('excelExport').style.display = 'block';
	 			for(var i=0;i<response.length;i++)
				{
					var res1=response[i].split("@");

					var count=res1[1].split("$");

					document.getElementById('total').innerHTML ="Total No Files : <font color='yellow'>"+count[4]+"</font>";
					//each count
					document.getElementById('MXIcount').innerHTML =""+count[0];
					document.getElementById('MXNIcount').innerHTML =""+count[1];
					document.getElementById('MUcount').innerHTML =""+count[2];
					document.getElementById('MNUcount').innerHTML =""+count[3];
					
					
					var mtno=res1[0].split("$");
	 			
	 		var html="<tr>";
			
			 html = html + "<td><center>"+mtno[0]+"</center></td>";
			 html = html + "<td><center>"+mtno[1]+"</center></td>";
			 html = html + "<td><center>"+mtno[2]+"</center></td>";
			 html = html + "<td><center>"+mtno[3]+"</center></td>";
			 html = html + "</tr>";
			 $("#FileStatus").append(html);
			}
						swal("", "HPL Data Inserted successfully!", "success", {
							  button: "OK",
							});
						
						//setTimeout("location.href = './cdfBatchEntry'", 5000);
			}
		});
		 
	   return false;
  	} 
		if(meterMake=="3")
		{
			document.getElementById('MXIcount').innerHTML ="";
			document.getElementById('MXNIcount').innerHTML ="";
			document.getElementById('MUcount').innerHTML ="";
			document.getElementById('MNUcount').innerHTML ="";
			document.getElementById('total').innerHTML ="";
			document.getElementById("BUstatus").innerHTML = MMK;
			
		 $("#parsedRow").hide();
			 $("#ImportUpload").hide();
			 $("#loadingmessage").show();
			 $("#lableDisplay").text("L & T ");
	    $.ajax({
			type : "POST",
			url : "./LNTCDFImport",
			data:{path:path},
			cache:false,
	 		 success : function(response)
			  {
	 			 $("#loadingmessage").hide();
	 			 $("#parsedRow").show();
	 			 $("#ImportUpload").show();
	 			$("#mMake").val(0);
	 			//alert(response+"=="+response);
				document.getElementById('excelExport').style.display = 'block';
	 			for(var i=0;i<response.length;i++)
				{
					var res1=response[i].split("@");

					var count=res1[1].split("$");

					document.getElementById('total').innerHTML ="Total No Files : <font color='yellow'>"+count[4]+"</font>";
					//each count
					document.getElementById('MXIcount').innerHTML =""+count[0];
					document.getElementById('MXNIcount').innerHTML =""+count[1];
					document.getElementById('MUcount').innerHTML =""+count[2];
					document.getElementById('MNUcount').innerHTML =""+count[3];
					
					
					var mtno=res1[0].split("$");
	 			
	 		var html="<tr>";
			
			 html = html + "<td><center>"+mtno[0]+"</center></td>";
			 html = html + "<td><center>"+mtno[1]+"</center></td>";
			 html = html + "<td><center>"+mtno[2]+"</center></td>";
			 html = html + "<td><center>"+mtno[3]+"</center></td>";
			 html = html + "</tr>";
			 $("#FileStatus").append(html);
			}
	 			swal("", "LNT Data Inserted successfully!", "success", {
					  button: "OK",
					});
						
						//setTimeout("location.href = './cdfBatchEntry'", 5000);
			
	 			
	 			 /* $("#loadingmessage").hide();
						
						swal("", "LNT Data Inserted successfully!", "success", {
							  button: "OK",
							}); */
						
						//setTimeout("location.href = './cdfBatchEntry'", 5000);
			}
		});
		 
	   return false;
  	}
		if(meterMake=="1")
		{
			 document.getElementById('MXIcount').innerHTML ="";
				document.getElementById('MXNIcount').innerHTML ="";
				document.getElementById('MUcount').innerHTML ="";
				document.getElementById('MNUcount').innerHTML ="";
				document.getElementById('total').innerHTML ="";
				document.getElementById("BUstatus").innerHTML = MMK;
				
			 $("#parsedRow").hide();
			 $("#ImportUpload").hide();
			 $("#loadingmessage").show();
			 $("#lableDisplay").text("Secure ");
	    $.ajax({
			type : "POST",
			url : "./SecureCDFImport",
			data:{path:path},
			cache:false,
	 		 success : function(response)
			  {


	 			 $("#loadingmessage").hide();
	 			 $("#parsedRow").show();
	 			 $("#ImportUpload").show();
	 			//alert(response+"=="+response);
				document.getElementById('excelExport').style.display = 'block';
	 			for(var i=0;i<response.length;i++)
				{
					var res1=response[i].split("@");

					var count=res1[1].split("$");

					document.getElementById('total').innerHTML ="Total No Files : <font color='yellow'>"+count[4]+"</font>";
					//each count
					document.getElementById('MXIcount').innerHTML =""+count[0];
					document.getElementById('MXNIcount').innerHTML =""+count[1];
					document.getElementById('MUcount').innerHTML =""+count[2];
					document.getElementById('MNUcount').innerHTML =""+count[3];
					
					
					var mtno=res1[0].split("$");
	 			
	 		var html="<tr>";
			
			 html = html + "<td><center>"+mtno[0]+"</center></td>";
			 html = html + "<td><center>"+mtno[1]+"</center></td>";
			 html = html + "<td><center>"+mtno[2]+"</center></td>";
			 html = html + "<td><center>"+mtno[3]+"</center></td>";
			 html = html + "</tr>";
			 $("#FileStatus").append(html);
			}
	 			swal("", "Secure Data Inserted successfully!", "success", {
					  button: "OK",
					});
						
						//setTimeout("location.href = './cdfBatchEntry'", 5000);
			
	 			 /* $("#loadingmessage").hide();
					
					swal("", "Secure Data Inserted successfully!", "success", {
						  button: "OK",
						});
					
					setTimeout("location.href = './cdfBatchEntry'", 5000); */
			}
		});
		 
	   return false;
  	}
		if(meterMake=="GENUSD")
		{
			 document.getElementById('MXIcount').innerHTML ="";
				document.getElementById('MXNIcount').innerHTML ="";
				document.getElementById('MUcount').innerHTML ="";
				document.getElementById('MNUcount').innerHTML ="";
				document.getElementById('total').innerHTML ="";
				document.getElementById("BUstatus").innerHTML = MMK;
				
			 $("#parsedRow").hide();
			 $("#ImportUpload").hide();
			 $("#loadingmessage").show();
			 $("#lableDisplay").text("GENUSD ");
	    $.ajax({
			type : "POST",
			url : "./GenusdCDFImport",
			data:{path:path},
			cache:false,
	 		 success : function(response)
			  {

	 			 $("#loadingmessage").hide();
	 			 $("#parsedRow").show();
	 			 $("#ImportUpload").show();
	 			//alert(response+"=="+response);
				document.getElementById('excelExport').style.display = 'block';
	 			for(var i=0;i<response.length;i++)
				{
					var res1=response[i].split("@");

					var count=res1[1].split("$");

					document.getElementById('total').innerHTML ="Total No Of Files : <font color='yellow'>"+count[4]+"</font>";
					//each count
					document.getElementById('MXIcount').innerHTML =""+count[0];
					document.getElementById('MXNIcount').innerHTML =""+count[1];
					document.getElementById('MUcount').innerHTML =""+count[2];
					document.getElementById('MNUcount').innerHTML =""+count[3];
					
					
					var mtno=res1[0].split("$");
	 			
	 		var html="<tr>";
			
			 html = html + "<td><center>"+mtno[0]+"</center></td>";
			 html = html + "<td><center>"+mtno[1]+"</center></td>";
			 html = html + "<td><center>"+mtno[2]+"</center></td>";
			 html = html + "<td><center>"+mtno[3]+"</center></td>";
			 html = html + "</tr>";
			 $("#FileStatus").append(html);
			}
	 			swal("", "GENUSD Data Inserted successfully!", "success", {
					  button: "OK",
					});
						
						//setTimeout("location.href = './cdfBatchEntry'", 5000);
			
	 			/* $("#loadingmessage").hide();
				swal("", "GENUSD Data Inserted successfully!", "success", {
					  button: "OK",
					});
				
				setTimeout("location.href = './cdfBatchEntry'", 5000); */
			}
		});
		 
	   return false;
  	}
		if(meterMake=="101")
		{
			document.getElementById('MXIcount').innerHTML ="";
			document.getElementById('MXNIcount').innerHTML ="";
			document.getElementById('MUcount').innerHTML ="";
			document.getElementById('MNUcount').innerHTML ="";
			document.getElementById('total').innerHTML ="";
			document.getElementById("BUstatus").innerHTML = MMK;
			
		 $("#parsedRow").hide();
		 $("#ImportUpload").hide();
		 $("#loadingmessage").show();
			 $("#lableDisplay").text("LNG ");
	    $.ajax({
			type : "POST",
			url : "./LNGCDFImport",
			data:{path:path},
			cache:false,
	 		 success : function(response)
			  {

	 			 $("#loadingmessage").hide();
	 			 $("#parsedRow").show();
	 			 $("#ImportUpload").show();
	 			//alert(response+"=="+response);
				document.getElementById('excelExport').style.display = 'block';
	 			for(var i=0;i<response.length;i++)
				{
					var res1=response[i].split("@");

					var count=res1[1].split("$");

					document.getElementById('total').innerHTML ="Total No Files : <font color='yellow'>"+count[4]+"</font>";
					//each count
					document.getElementById('MXIcount').innerHTML =""+count[0];
					document.getElementById('MXNIcount').innerHTML =""+count[1];
					document.getElementById('MUcount').innerHTML =""+count[2];
					document.getElementById('MNUcount').innerHTML =""+count[3];
					
					
					var mtno=res1[0].split("$");
	 			
	 		var html="<tr>";
			
			 html = html + "<td><center>"+mtno[0]+"</center></td>";
			 html = html + "<td><center>"+mtno[1]+"</center></td>";
			 html = html + "<td><center>"+mtno[2]+"</center></td>";
			 html = html + "<td><center>"+mtno[3]+"</center></td>";
			 html = html + "</tr>";
			 $("#FileStatus").append(html);
			}
						swal("", "LNG Data Inserted successfully!", "success", {
							  button: "OK",
							});
						
						//setTimeout("location.href = './cdfBatchEntry'", 5000);
			
				
				/* swal("", "LNG Data Inserted successfully!", "success", {
					  button: "OK",
					});
				
				setTimeout("location.href = './cdfBatchEntry'", 5000); */
			}
		});
		 
	   return false;
  	}

		if(meterMake=="4")
		{ 
		document.getElementById('MXIcount').innerHTML ="";
		document.getElementById('MXNIcount').innerHTML ="";
		document.getElementById('MUcount').innerHTML ="";
		document.getElementById('MNUcount').innerHTML ="";
		document.getElementById('total').innerHTML ="";
		document.getElementById("BUstatus").innerHTML = MMK;
		
	 $("#parsedRow").hide();
	 $("#ImportUpload").hide();
	 $("#loadingmessage").show();
			 $("#lableDisplay").text("Genus Non DLMS ");
	    $.ajax({
			type : "POST",
			url : "./GenusNonDLMSCDFImport",
			data:{path:path},
			cache:false,
	 		 success : function(response)
			  {
	 			 $("#loadingmessage").hide();
	 			 $("#parsedRow").show();
	 			 $("#ImportUpload").show();
	 			$("#mMake").val(0);
	 			//alert(response+"=="+response);
				document.getElementById('excelExport').style.display = 'block';
	 			for(var i=0;i<response.length;i++)
				{
					var res1=response[i].split("@");

					var count=res1[1].split("$");

					document.getElementById('total').innerHTML ="Total No Files : <font color='yellow'>"+count[4]+"</font>";
					//each count
					document.getElementById('MXIcount').innerHTML =""+count[0];
					document.getElementById('MXNIcount').innerHTML =""+count[1];
					document.getElementById('MUcount').innerHTML =""+count[2];
					document.getElementById('MNUcount').innerHTML =""+count[3];
					var mtno=res1[0].split("$");
	 			
	 		var html="<tr>";
			
			 html = html + "<td><center>"+mtno[0]+"</center></td>";
			 html = html + "<td><center>"+mtno[1]+"</center></td>";
			 html = html + "<td><center>"+mtno[2]+"</center></td>";
			 html = html + "<td><center>"+mtno[3]+"</center></td>";
			 html = html + "</tr>";
			 $("#FileStatus").append(html);
			}
	 			swal("", "Genus Non DLMS Data Inserted successfully!", "success", {
					  button: "OK",
					});
						
			
				/* $("#loadingmessage").hide();
				
				swal("", "Genus Non DLMS Data Inserted successfully!", "success", {
					  button: "OK",
					});
				
				setTimeout("location.href = './cdfBatchEntry'", 5000);	 */
			}
		});
		 
	   return false;
  	}
	  	
	else {
	
			
		}
  } 

 




</script>
