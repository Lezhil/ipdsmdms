<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <script  type="text/javascript">
  
  $(".page-content").ready(function()
   	    	   {     
	
	   App.init();
   	    	   	 FormDropzone.init();
   	    		 FormComponents.init();
   	    	  $('#ADMINSideBarContents,#cmriupload').addClass('start active ,selected');
	    	     $("#MDASSideBarContents,#MDMSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#seal-Manager,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	   		//$("#billmonth").val(getPresentMonthDate('${selectedMonth}'));
   	    		//getParsedXml();
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
		$.ajax({
		type : "GET",
		url : "./parseFile/"+path+"/"+filename+"/"+filee,
		
		cache:false,
 		 success : function(response)
		  {
 			document.getElementById('alertMsg').style.display = 'block';
 			document.getElementById('otherMsg').style.display = 'none';
 			document.getElementById('parseCompleteMsg').innerHTML = response;
 			document.getElementById('excelExport').style.display = 'block';
 			 //alert(response);
		  }
	});
		bootbox.alert('Parsing Started ...');
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
					<div class="portlet box green">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-cogs"></i>CDF batch upload & parsing progress status</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								
							</div>
						</div>
						<div class="portlet-body">
							
					<form action="./uploadFileMDAS" id="uploadFile" enctype="multipart/form-data" method="post">
			       
			          <div class="form-group">
					 <label for="exampleInputFile1">Attach CDF File</label> 
					<input type="file" id="fileUpload" name="fileUpload"><br/>
					
					<button class="btn blue pull-left" style="display: block;" id="uploadButton" onclick="return finalSubmit();"  >Upload</button>  
				     &nbsp;
				     <c:if test="${parse eq 'parse'}">
				      <button class="btn blue"  id="parseButton" onclick="return parseSubmit('${UnZipFolderPath}','${filename}','${UnZipFilename}');" >Parse</button>  
						</c:if>
				     </form>
				     
				      
					
					</div>
					
					
						
			
			
			
							
						<!-- 	Batch1<div class="progress progress-striped active">
								<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
									<span class="sr-only">40% Complete (success)</span>
								</div>
							</div>
							Batch2<div class="progress progress-striped active">
								<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 20%">
									<span class="sr-only">20% Complete</span>
								</div>
							</div>
							Batch3<div class="progress progress-striped active">
								<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%">
									<span class="sr-only">60% Complete (warning)</span>
								</div>
							</div>
							Batch4<div class="progress progress-striped active">
								<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%">
									<span class="sr-only">80% Complete (danger)</span>
								</div>
							</div> -->
							
						</div>
					</div>
					<!-- END PROGRESS BARS PORTLET-->
</div>					
</div>
         <div class="row" id="parsedRow" style="display: none;">
          
			<div class="col-md-12">
					<!-- BEGIN PROGRESS BARS PORTLET-->
					<div class="portlet box green">
						<div class="portlet-title">
							<div class="caption"><span id="parsedcount""></span>
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
												</tbody>			
												</table>
						</div>	
				</div>	
		</div>
		
				