<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <script  type="text/javascript">
  
  $(".page-content").ready(function()
   	    	   {     
	  $("#billmonth").val(getPresentMonthDate('${selectedMonth}'));
		
	
	   App.init();
   	    	   	 FormDropzone.init();
   	    		 FormComponents.init();
   	    	   $('#MobileParsing').addClass('start active ,selected');
   	    	   $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#seal-Manager,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
   	    	
   	    	   });
  
  //window.onload = setInterval("getParsedXml()", 5000);
 
  
  /* function PraseFiles()
  {
	    alert("calling download fun");
		$.ajax({
		type : "GET",
		url : "./parseFileDirect",
		
		cache:false,
 		 success : function(response1)
		  {
		  }
	});
		 //bootbox.alert('Parsing Started ...');
	   return false;
	 //$("#uploadFile").attr("action","./parseFile?path="+path+"&filename="+filename+"&filee="+filee); 
  } */
  

  function PraseFiles()
  {
	  var billmonth=$("#billmonth").val();
	    document.getElementById('parsedcount').innerHTML ="";
		document.getElementById('dupcount').innerHTML ="";
		document.getElementById('notcount').innerHTML ="";
		document.getElementById('timeCount').innerHTML ="";
		document.getElementById('corruptedCount').innerHTML="";
	   document.getElementById('parsedRow').style.display = 'block';
	
	  $("#parseStatusData").empty();
	  $("#loadingmessage").show();
		$.ajax({
		type : "GET",
		url : "./parseFileDirect",
		data:{billmonth:billmonth},
		cache:false,
 		 success : function(response1)
		  {
 			//alert(response1);
			if(response1==0)
				{
				$("#loadingmessage").hide();
					bootbox.alert("No files found to parse");
					return false;
				}
 		
 			$("#loadingmessage").hide();
 			if(response1.length>0)
 	 		{
 				var respon=""; 	
 			$("#parseStatusData").empty();
 			var len=response1.length;
 			 respon=response1[len-1];
 			document.getElementById('alertMsg').style.display = 'block';
 			//document.getElementById('otherMsg').style.display = 'none';
 			document.getElementById('parseCompleteMsg').innerHTML = "Parsing Completed.. Time Taken is : "+respon;
 			document.getElementById('excelExport').style.display = 'block';
 			var parsecount=0;
  			var Duplicatecount=0;
  			var Meternotexistcount=0;
  			var corrupetedcount=0;

  			
            for(var j=0;j<response1.length -1;j++)
                {
                var response=response1[j];
               
	 			var html = "<tr>";
	  			var res1 = response.split("@");
	  			
	  			
	  			var  counts=res1[1].split("$");
	  			
	  			  if(counts[0] !='0')
		  			{
	  				parsecount++;
			  		}
	  			  if(counts[1] !='0')
		  			 {
	  				Duplicatecount++;
			  		 }
	  			  if(counts[2] !='0')
		  			{
	  				Meternotexistcount++;
		  			}
	  			  if(counts[3] !='0')
		  			{
	  				corrupetedcount++;
		  			}
	  		
	  			
  				var fname = res1[0].split("$");
  				for(var i=0;i<fname.length;i++)
  				{
  				 html = html + "<td>"+fname[i]+"</td>";
  				}
  				
  				
  				document.getElementById('parsedcount').innerHTML ="Parsed Xml : <font color='red'>"+parsecount+"</font>";
  				document.getElementById('dupcount').innerHTML ="Duplicate Xml : <font color='red'>"+Duplicatecount+"</font>";
  				document.getElementById('notcount').innerHTML ="Meter Not Exist Xml : <font color='red'>"+Meternotexistcount+"</font>";
  				document.getElementById('corruptedCount').innerHTML ="Corrupted Xml : <font color='red'>"+corrupetedcount+"</font>";
  				document.getElementById('timeCount').innerHTML ="Time Taken is  : <font color='red'>"+respon+"</font>";
  				
  				
  			html = html + "</tr>";
  			$("#parseStatusData").append(html);

                }
           
 			
 			
		  }
		  }
	});
		 //bootbox.alert('Parsing Started ...');
	   return false;
	 //$("#uploadFile").attr("action","./parseFile?path="+path+"&filename="+filename+"&filee="+filee); 
  }
  
  </script>
  
<div class="page-content" >
	<!-- BEGIN PAGE CONTENT-->
	<c:if test = "${result ne 'notDisplay'}"> 			        
			        <div class="alert alert-danger display-show" id="otherMsg" style="display: none;">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${result}</span>
							</div>
			        </c:if>
			 <div class="alert alert-success display-show" id="alertMsg" style="display: none;">
							<button class="close" data-close="alert"></button>
							 <span style="color:green" id="parseCompleteMsg"></span> 
				</div>
			<!-- END PAGE CONTENT-->
<div class="clearfix"></div>
<div class="row">
<div class="col-md-12">
					<!-- BEGIN PROGRESS BARS PORTLET-->
			<div class="portlet box green">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-cogs"></i>Mobile Upload Parsing 
						
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>

					</div>
				</div>
				<div class="portlet-body">

					<form  id="parsing"
						enctype="multipart/form-data" method="post">
						Select BillMonth :
						<div data-date-viewmode="years" data-date-format="yyyymm"
							class="input-group input-medium date date-picker">
							<input type="text" name="billmonth" id="billmonth"
								class="form-control" required="required" readonly="readonly">
							<span class="input-group-btn">
								<button type="button" class="btn default">
									<i class="fa fa-calendar"></i>
								</button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</span>
						</div>
						<br />
						<div class="form-group">

							<button class="btn blue pull-left" style="display: block;"
								id="parsebtn"  onclick="return PraseFiles();">ParseFiles</button>
								
							&nbsp;
							<%--  <c:if test="${parse eq 'parse'}">
				      <button class="btn blue"  id="parseButton" onclick="return parseSubmit('${UnZipFolderPath}','${filename}','${UnZipFilename}','${billmonth}');" >Parse</button>  
					 	
					 </c:if> --%>
						</div>
					</form>

				</div>

			</div>
			 	<!-- Batch1<div class="progress progress-striped inactive">
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
							</div> 
							
						</div> -->
					</div>
					<!-- END PROGRESS BARS PORTLET-->
</div>					

         <div class="row" id="parsedRow" style="display: none;">
          
			<div class="col-md-12">
					<!-- BEGIN PROGRESS BARS PORTLET-->
					<div class="portlet box green">
						<div class="portlet-title">
							<div class="caption"><span id="parsedcount"></span>
							<span id="dupcount" ></span>
							<span id="notcount" ></span>
							<span id="timeCount" ></span> 
							<span id="corruptedCount" ></span> </div>
							<div class="tools">
								
								<a href="#" id="excelExport" style="display:none;" onclick="tableToExcel('sample_editable_1', 'Parsing Status')">Export to Excel</a>
							</div>
						</div>
						<div class="portlet-body" style="overflow: auto;height: 700px;">
						<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
										<thead>
											<tr>
										
											<th>Parsed Xml</th>
											<th>Duplicate Xml</th>
											<th>Meter Doesnot Exist Xml</th>
											<th>Corrupted Xml</th>
											</tr>
											</thead>
												<tbody id="parseStatusData">
												</tbody>			
												</table>
												<div id='loadingmessage' style='display:none'>
  									<!-- <img src="/resources/assets/img/loading.gif" /> -->
  									<img src="resources/assets/img/home/ajax-loader(1).gif" class="ajax-loader" width=150px;  height=130px;>
  									<span id="load" class="wait" style="color:blue;">Parsing Started</span>
								</div>
										</div>	
										
								</div>	
							</div>
							</div>
							
							
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
