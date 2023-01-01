<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <script  type="text/javascript">
  
  $(".page-content").ready(function()
   	    	   {     
	
	             App.init();
   	    	   	 FormDropzone.init();
   	    		 FormComponents.init();
   	    	     $('#cdf-Import').addClass('start active ,selected');
   	    	     $('#MDASSideBarContents,#meterOper,#cmriupload').addClass('start active ,selected');
	    	     $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#seal-Manager,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	   			//$("#billmonth").val(getPresentMonthDate('${selectedMonth}'));
   	    		getParsedXml();
   	    		loadSearchAndFilter("sample_1");

   	    	   //CALING THE PARSE METHOD AUTOMATICALLY
// 	    	   alert('${parse}');
// 	    	   alert('${UnZipFolderPath}');
// 	    	   alert('${filename}');
// 	    	   alert('${UnZipFilename}');
	    	   getXmlUploadSummery();
   	    	   if('${parse}'=='parse')
				{
   	    		  return parseSubmit('${UnZipFolderPath}','${filename}','${UnZipFilename}');
				}
   	    		   
   	    		   
   	    	   });
  
  //window.onload = setInterval("getParsedXml()", 5000);
  function finalSubmit()
  {
	 if(document.getElementById("fileUpload").value == "" || document.getElementById("fileUpload").value == null)
		  {
		    bootbox.alert(' Please select zip file to upload');
	 	    return false;
		  }
	 //$("#uploadFile").attr("action","./uploadFile"); 
	}
  
  function parseSubmit(path,filename,filee)
  {
	    document.getElementById('parsedcount').innerHTML ="";
		document.getElementById('dupcount').innerHTML ="";
		document.getElementById('notcount').innerHTML ="";
		document.getElementById('timeCount').innerHTML ="";
	    document.getElementById('parsedRow').style.display = 'block';
	  $("#parseStatusData").empty();
	  //loadingmessage
	  $("#loadingmessage").show();
		$.ajax({
		type : "GET",
		url : "./parseFileMDAS/"+path+"/"+filename+"/"+filee,
		dataType:"JSON",
		cache:false,
 		 success : function(response)
		  {
 			//alert(response);
 			 /*alert(response.parsed);
			 alert(response.parsedCount);
			 alert(response.meterNotExistCount);
			 alert(response.duplicateCount); */
 			document.getElementById('alertMsg').style.display = 'block';
 			document.getElementById('otherMsg').style.display = 'none';
 			document.getElementById('parseCompleteMsg').innerHTML ="time taken : "+ response.time;
 			document.getElementById('excelExport').style.display = 'block';
 			
 			document.getElementById('parsedcount').innerHTML ="Parsed Xml : <font color='red'>"+response.parsedCount+"</font>";
			document.getElementById('dupcount').innerHTML ="Duplicate Xml : <font color='red'>"+response.duplicateCount+"</font>";
			document.getElementById('notcount').innerHTML ="Meter Not Exist : <font color='red'>"+response.meterNotExistCount+"</font>";
			document.getElementById('timeCount').innerHTML ="Time Taken is  : <font color='red'>"+response.time+"</font>";
			
			
			var html = "";
			
			var parsedfiles=response.parsed.split("<br/>");
			var notinMasterfiles=response.meterNotExist.split("<br/>");
			var duplicatedFiles=response.duplicate.split("<br/>");
			
			if(parsedfiles!="")
			{
				for(var i=0;i<parsedfiles.length;i++)
				{
				 
				 html = html + "<tr><td>"+parsedfiles[i]+"</td>"
				 				+"<td>"+''+"</td>"
				 				+"<td>"+''+"</td></tr>";
				}
			}
			if(notinMasterfiles!="")
				{
					for(var j=0;j<notinMasterfiles.length;j++)
					{
					
					 html = html + "<tr><td>"+''+"</td>"
					 	+"<td>"+''+"</td>"
		 				+"<td>"+notinMasterfiles[j]+"</td></tr>";
					}
				}
			
		    if(duplicatedFiles!="")
			{
		    	for(var k=0;k<duplicatedFiles.length;k++)
				{
				
				html = html + "<tr><td>"+''+"</td>"
 				+"<td>"+duplicatedFiles[k]+"</td>"
 				+"<td>"+''+"</td></tr>";
				}
			}
			//html = html + "</tr>";
  			$("#parseStatusData").html(html);
  			$("#loadingmessage").hide();
		  }
	});
	
		//bootbox.alert('Parsing Started ...');
	  return false;
	 //$("#uploadFile").attr("action","./parseFile?path="+path+"&filename="+filename+"&filee="+filee); 
  }
  
  
  function getParsedXml()
  {
	    document.getElementById('parsedcount').innerHTML ="";
		document.getElementById('dupcount').innerHTML ="";
		document.getElementById('notcount').innerHTML ="";
	  $("#parseStatusData").empty();
 	 $.ajax({
  		type : "GET",
  		url : "./AllXmls",
  		dataType: "text",
  		cache:false,
  		 success : function(response)
		  {
  			//alert("hi");
  		
  			var html = "<tr>";
  			var res1 = response.split("@");
  			
  				var res = res1[0].split("$");
  				for(var i=0;i<res.length;i++)
  				{
  				
  				 html = html + "<td>"+res[i]+"</td>";
  				}
  				var countRes = res1[1].split("$");
  				document.getElementById('parsedcount').innerHTML ="Parsed Xml : <font color='red'>"+countRes[0]+"</font>";
  				document.getElementById('dupcount').innerHTML ="Duplicate Xml : <font color='red'>"+countRes[1]+"</font>";
  				document.getElementById('notcount').innerHTML ="Meter Not Exist Xml : <font color='red'>"+countRes[2]+"</font>";
  				document.getElementById('timeCount').innerHTML ="Time Taken is  : <font color='red'>"+res1[2]+"</font>";
  			
  			html = html + "</tr>";
  			$("#parseStatusData").append(html);
		  }
  		});
  }
  </script>
  <style>
  #loadingmessage {
  height: 400px;
  position: relative;
//  background-color: gray; /* for demonstration */
}
.ajax-loader {
  position: absolute;
  left: 50%;
  top: 50%;
  margin-left: -32px; /* -1 * image width / 2 */
  margin-top: -32px; /* -1 * image height / 2 */
}
#load {
  height: 500px;
  position: relative;
//  background-color: gray; /* for demonstration */
}
.wait{
  position: absolute;
  left: 50%;
  top: 60%;
  margin-left: -2px; /* -1 * image width / 2 */
  margin-top: -32px; /* -1 * image height / 2 */
}
</style>
<div class="page-content" >
	<!-- BEGIN PAGE CONTENT-->
	<c:if test = "${result ne 'notDisplay'}"> 			        
			        <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${result}</span>
						</div>
			        </c:if>
			        
			      
			 <div class="alert alert-danger display-show" id="alertMsg" style="display: none;">
							<button class="close" data-close="alert"></button>
							<span style="color:red" id="parseCompleteMsg"></span>
				</div>
			<!-- END PAGE CONTENT-->
<div class="clearfix"></div>

<div class="row">
<div class="col-md-12">
					<!-- BEGIN PROGRESS BARS PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-cogs"></i>CMRI File Upload</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								
							</div>
						</div>
				<div class="portlet-body">

					<form action="./uploadFile" id="uploadFile"
						enctype="multipart/form-data" method="post">

							<div class="form-group">
							<label for="exampleInputFile1">Only Upload ZIP File</label> <input
								type="file" id="fileUpload" name="fileUpload"><br />

							<button class="btn blue pull-left" style="display: block;"
								id="uploadButton" onclick="return finalSubmit();">Upload & Parse</button>
								
							&nbsp;
							<%--  <c:if test="${parse eq 'parse'}">
				      <button class="btn blue"  id="parseButton" onclick="return parseSubmit('${UnZipFolderPath}','${filename}','${UnZipFilename}','${billmonth}');" >Parse</button>  
					 	
					 </c:if> --%>
						</div>
							
							<%--      <c:if test="${parse eq 'parse'}">
				      <button class="btn blue"  id="parseButton" onclick="return parseSubmit('${UnZipFolderPath}','${filename}','${UnZipFilename}');" >Parse</button>  
						</c:if> --%>
					</form>

					<div align="center">
						<div class="col-md-6">
							<div id='loadingmessage' style='display: none'>
								<!-- <img src="/resources/assets/img/loading.gif" /> -->
								<img alt="image" src="./resources/assets/img/Preloader_3.gif"
									class="ajax-loader" width=100px; height=100px;> <span
									id="load" class="wait" style="color: blue;"><b>Uploading
										Data Please Wait</b>!!!!</span>
							</div>
						</div>
					</div>

				</div>
							
						</div>
					</div>
					<!-- END PROGRESS BARS PORTLET-->
</div>	

<div class="row">
		<div class="col-md-12">
			
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>CMRI Upload Summary 
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
									<li><a href="#" id="print">Print</a></li>
									<li><a href="#" id="excelExport"
										onclick="tableToExcel('sample_1', 'Total Meters')">Export
											to Excel</a></li>
								</ul>
							</div>
					</div>
					
					<table class="table table-striped table-hover table-bordered" id="sample_1">
						<thead>
							<tr>
								<th>SL No.</th>
								<th>meterno</th>
								<th>File Name</th>
								<th>uploaded Date</th>
								<th>meter Time</th>
								<th>Device Time</th>
								<th>Status</th>
								
							</tr>
						</thead>
								<tbody id="xmlUploadSummaryId">
									
								</tbody>
					</table>
					
					
				</div>
			</div>
		</div>
	</div>

		

         <div class="row" id="parsedRow" style="display: none;">
          
			<div class="col-md-12">
					<!-- BEGIN PROGRESS BARS PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><span id="parsedcount"></span>
							<span id="dupcount" ></span>
							<span id="notcount" ></span>
							<span id="timeCount" ></span> </div>
							
							<div class="tools">
								
								<a href="#" id="excelExport" style="display:none;" onclick="tableToExcel('sample_editable_1', 'Parsing Status')">Export to Excel</a>
							</div>
						</div>
						<div class="portlet-body" style="overflow: auto;height: 600px;" id="datadisplay">
						<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
										<thead>
											<tr>
										
											<th>Parsed Xml</th>
											<th>Duplicate Xml</th>
											<th>Meter Doesnot Exist Xml</th>
											</tr>
											</thead>
												<tbody id="parseStatusData">
													<%-- <c:forEach items="${listOfMaps}" var="map">
													<tr>
													    <c:forEach items="${map}" var="entry">
													       <td> ${entry.parsed}</td>
													       <td> ${entry.parsedCount}</td>
													       <td> ${entry.meterNotExistCount}</td>
													       <td> ${entry.duplicateCount}</td>
													    </c:forEach>
													  </tr>
													</c:forEach> --%>
												</tbody>			
												</table>
						</div>	
				</div>	
		</div>
		</div>
	</div>	
	
	
	<script>
	
	function getXmlUploadSummery(){

		$.ajax({
			type : "GET",
	  		url : "./getXmlUploadSummery",
	  		dataType: 'json',
	  		cache:false,
	  		 success : function(response)
			  {

				  if(response.length>0)
					{
						var html="";
						for(var i=0;i<response.length;i++){
							var data=response[i];
							var count=i+1;
							html+="<tr>";          
							html+="<td>"+count+"</td>";
							html+="<td>"+(data[1]==null?"":data[1])+"</td>";
							html+="<td>"+(data[2]==null?"":data[2])+"</td>";
							html+="<td>"+(data[3]==null?"":moment(data[3]).format("YYYY-MM-DD HH:mm:ss"))+"</td>";
							html+="<td>"+(data[4]==null?"":moment(data[4]).format("YYYY-MM-DD HH:mm:ss"))+"</td>";
							html+="<td>"+(data[5]==null?"":moment(data[5]).format("YYYY-MM-DD HH:mm:ss"))+"</td>";
							html+="<td>"+(data[6]==null?"":data[6])+"</td>";
							
							html+="</tr>";
							}
						$("#xmlUploadSummaryId").html(html);
						loadSearchAndFilter('sample_1');
					}
			  }
			});
		}

	</script>		