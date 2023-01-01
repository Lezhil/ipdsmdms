<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>

<!-- <export pdf> -->
<!-- <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.22/pdfmake.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>
 -->
 <script src="<c:url value='/resources/assets/scripts/pdfmake.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/assets/scripts/html2canvas.min.js'/>" type="text/javascript"></script>
 
 <!-- <head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" pageEncoding="UTF-8"%/>
 </head> -->   
 
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		App.init();
		TableManaged.init();
		FormComponents.init();
		loadSearchAndFilter('sample_1');
		 $('#reportsId,#nonReadMetersReport').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
		 $("#zone").val("").trigger("change");
		 $("#circle").val("").trigger("change");
		 $("#town").val("").trigger("change");
		});
	//getConsisCommFailMtrsRep();
	
	function getConsisCommFailMtrsRep()
	{
		var zone=$('#LFzone').val();
		var circle=$('#LFcircle').val();
		var town=$('#LFtown').val();
		if(zone=="" ||zone==null)
		{
			bootbox.alert("Please Select Region");
			return false;
		}
		if(circle=="" ||circle==null)
		{
			bootbox.alert("Please Select Circle");
			return false;
		}
		if(town=="" ||town==null)
		{
			bootbox.alert("Please Select Town");
			return false;
		}
       $('#imageee').show();
		$.ajax({
			url : './showCommFailmtrs',
			type:'GET',
			data:{
				zone:zone,
				circle:circle,
				town:town
			},
			success:function(response)
			{
				$('#imageee').hide();
				if(response.length!=0){
				var html="";
				for (var i = 0; i < response.length; i++){
					var data = response[i];
					html += "<tr>"+
					 "<td style='text-align: center; vertical-align: middle;'>"+(data[11] == null?"": data[11])+ "</td>"+
					 "<td style='text-align: center; vertical-align: middle;'>"+(data[10]==null?"":data[10])+"</td>"+
					 "<td style='text-align: center; vertical-align: middle;'>"+(data[13] == null?"": data[13])+ "</td>"+
					 "<td style='text-align: center; vertical-align: middle;'>"+(data[12]==null?"":data[12])+"</td>"+
					 "<td style='text-align: center; vertical-align: middle;'>"+(data[0] == null?"": data[0])+ "</td>"+
					 "<td style='text-align: center; vertical-align: middle;'>"+(data[9]==null?"":data[9])+"</td>"+
					 "<td style='text-align: center; vertical-align: middle;'><a href='#' data-toggle='modal'  data-target='#stack1' onclick='return totalconsumers(1,this.id)' id='"+data[0]+"'>"+(data[1]==null?"":data[1])+"</a></td>"+
					 
					 /* "<td style='text-align: center; vertical-align: middle;'><a href='#' data-toggle='modal'  data-target='#stack1' onclick='return totalconsumers(2,this.id)' id='"+data[0]+"'>"+(data[2]==null?"":data[2])+"</a></td>"+ */
					 "<td style='text-align: center; vertical-align: middle;'><a href='#' data-toggle='modal'  data-target='#stack1' onclick='return totalconsumers(3,this.id)' id='"+data[0]+"'>"+(data[3]==null?"":data[3])+"</a></td>"+
					 "<td style='text-align: center; vertical-align: middle;'><a href='#' data-toggle='modal'  data-target='#stack1' onclick='return totalconsumers(4,this.id)' id='"+data[0]+"'>"+(data[4]==null?"":data[4])+"</a></td>"+
					 "<td style='text-align: center; vertical-align: middle;'><a href='#' data-toggle='modal'  data-target='#stack1' onclick='return totalconsumers(5,this.id)' id='"+data[0]+"'>"+(data[5]==null?"":data[5])+"</a></td>"+
					 "<td style='text-align: center; vertical-align: middle;'><a href='#' data-toggle='modal'  data-target='#stack1' onclick='return totalconsumers(6,this.id)' id='"+data[0]+"'>"+(data[6]==null?"":data[6])+"</a></td>"+			
					 "<td style='text-align: center; vertical-align: middle;'><a href='#' data-toggle='modal'  data-target='#stack1' onclick='return totalconsumers(7,this.id)' id='"+data[0]+"'>"+(data[7]==null?"":data[7])+"</a></td>"+
					 "<td style='text-align: center; vertical-align: middle;'><a href='#' data-toggle='modal'  data-target='#stack1' onclick='return totalconsumers(8,this.id)' id='"+data[0]+"'>"+(data[8]==null?"":data[8])+"</a></td>"+
					 /* "<td style='text-align: center; vertical-align: middle;'><a href='#' data-toggle='modal'  data-target='#stack1'  id='"+data[0]+"'>"+(data[9]==null?"":data[9])+"</a></td>"+ */
					"</tr>";
				}
				$('#sample_1').dataTable().fnClearTable();
				$('#getConsisCommFailMtrs').html(html);
				loadSearchAndFilter('sample_1'); 
				}else{
					$('#sample_1').dataTable().fnClearTable();
					bootbox.alert("No data Found");
				}
			},  
			complete: function()
       		{  
       			loadSearchAndFilter('sample_1');
       		}
		});
	}
	
	function totalconsumers(id,towncode){

		//var given = moment("2019-12-10", "YYYY-MM-DD");
        var current = moment().startOf('day');

        //Difference in number of days
       // alert(moment.duration(current.diff(given)).asDays());


		 $('#sample_5').dataTable().fnClearTable();
		 $('#imageee').show();
		 
		    if(id==1)
		    {
			   $('#tabTitle').html("Total Meters");
			}
			else if(id==2)
			{
               $('#tabTitle').html("Active Meters");
			}
			else if(id==3)
			{
	            $('#tabTitle').html("Total count of NonComm Meters");
			}
			else if(id==4)
			{
	            $('#tabTitle').html("NonComm From last 24Hrs");
			}
			else if(id==5)
			{
	            $('#tabTitle').html("NonComm From last 5 Days");
			}
			else if(id==6)
			{
	            $('#tabTitle').html("NonComm From last 10 Days");
			}
			else if(id==7)
			{
	            $('#tabTitle').html("NonComm From last 20 Days");
			}
			else if(id==8)
			{
	            $('#tabTitle').html("NonComm From last 30 Days");
			}
		    
		    var Id=id;
			var towncodee=towncode; 
			
		    $.ajax({
		    	 url:"./showCommFailConsumers",
	               type:'GET',
	               data:{
	            	   Id:Id,
	            	   towncodee:towncodee
	                   },
	               success:function(response)
	               {
	            	   var html="";
	            	   for (var i = 0; i < response.length; i++){
	            		   //$('#sample_5').dataTable().fnClearTable();
                    	   var resp=response[i];
               			html+="<tr>"
               				    + "<td>"+(i+1)+"</td>"
               				 	+" <td>"+(resp[9]==null?"":resp[9])+"</td>"
               				 	+" <td>"+(resp[8]==null?"":resp[8])+"</td>"
               				 	+" <td>"+(resp[11]==null?"":resp[11])+"</td>"
            				 	+" <td>"+(resp[10]==null?"":resp[10])+"</td>"
            				 	+" <td>"+(resp[6]==null?"":resp[6])+"</td>"
               				 	+" <td>"+(resp[7]==null?"":resp[7])+"</td>"
               				   /*  +" <td>"+(resp[0]==null?"":resp[0])+"</td>" */
               					+" <td>"+(resp[1]==null?"":resp[1])+"</td>"
               					/* +" <td>"+(resp[2]==null?"":resp[2])+"</td>" */ 
               					+" <td>"+(resp[3]==null?"":resp[3])+"</td>"
               					+" <td>"+(resp[4]==null?"":resp[4])+"</td>";
               				if(resp[5]==null){
               					html+=" <td>"+"Not Comm"+"</td>"
               					/* +" <td>"+(moment(moment(resp[5]).format('YYYY-MM-DD'), 'YYYY-MM-DD').fromNow())+"</td>" */
               					+" <td>"+"NA"+"</td>";
                   			}else{
                   				html+=" <td>"+(resp[5]==null?"":moment(resp[5]).format('YYYY-MM-DD HH:mm:ss'))+"</td>"
                   					/* +" <td>"+(moment(moment(resp[5]).format('YYYY-MM-DD'), 'YYYY-MM-DD').fromNow())+"</td>" */
                   					+" <td>"+(moment.duration(current.diff(moment(resp[5]).format('YYYY-MM-DD'))).asDays())+"</td>";
                       		}
               				
              			html+=" </tr>";
               				 } 
	                    $('#sample_5').dataTable().fnClearTable();
	                	$("#commfailmtrsId").html(html);
	               },
	               complete: function()
              		{  
              			loadSearchAndFilter('sample_5');
              			$('#imageee').hide();
              		} 
		    });
	}
	
	
</script>


<div class="page-content">
<div class="portlet box blue">
   <div class="portlet-title">
       <div class="caption">
           <i class="fa fa-edit"></i>Consistent Communication Fail Meters Report
       </div>
       
       <div class="tools">
				<a href="javascript:;" class="collapse"></a> 
			    <a href="javascript:;" class="reload"></a> <a href="javascript:;" class="remove"></a>
			</div>
    </div>
   
   
    <div class="portlet-body">
    			<jsp:include page="locationFilter.jsp"/> 
    			 <div class="row" style="margin-top: -49px;">
    			
    			<button type="submit" onclick="getConsisCommFailMtrsRep()" class="btn green">Generate</button>
									</div>
    
						<%-- 			<div class="col-md-3">
										<strong>Region:</strong><div id="zoneTd" class="form-group">
											<select class="form-control select2me"
												id="zone" name="zone"
												onchange="showCircle(this.value);">
												<option value="">Select Region</option>
												<option value="%">ALL</option> 
												<c:forEach items="${zoneList}" var="elements">
													<option value="${elements}">${elements}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-3">
										<strong>Circle:</strong><div id="circleTd" class="form-group">
											 <select class="form-control select2me"
												id="circle" name="circle"
												onchange="showTownNameandCode(this.value);">
												<option value="">Select Circle</option>
												<option value="%">ALL</option> 
												
											</select>
										</div>
									</div>
								
									<div class="col-md-3">
								<strong>Town:</strong>
												<div id="townTd" class="form-group"><select
													class="form-control select2me input-medium" id="town" 
													name="town">
													<option value="">Select Town</option>
												    <option value="%">ALL</option> 
												</select></div>
									</div> --%>
									
                            <div class="table-toolbar">
								<div class="btn-group pull-right" >
									<button class="btn dropdown-toggle" data-toggle="dropdown" style="margin-top: 34px;">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li>
										<a href="#" id="excelExport1" onclick="tableToExcel1('sample_1', 'ConsistentCommunicationFailMeters')">Export to Excel</a>
										</li>
									</ul>
								</div>
							</div>
							<br>
			       <table class="table table-striped table-hover table-bordered" id="sample_1">
			           <thead>
							<tr>
										<th style="text-align: center;max-width: 150px; min-width: 120px; white-space: normal; vertical-align: middle;" class="info">Region Code</th>
										<th style="text-align: center;max-width: 150px; min-width: 107px; white-space: normal; vertical-align: middle;" class="info"> Region Name</th>
										<th style="text-align: center;max-width: 150px; min-width: 120px; white-space: normal; vertical-align: middle;" class="info">Circle Code</th>
										<th style="text-align: center;max-width: 150px; min-width: 107px; white-space: normal; vertical-align: middle;" class="info"> Circle Name</th>
										<th style="text-align: center;max-width: 150px; min-width: 120px; white-space: normal; vertical-align: middle;" class="info">Town Code</th>
										<th style="text-align: center;max-width: 150px; min-width: 107px; white-space: normal; vertical-align: middle;" class="info"> Town Name</th>
										<th style="text-align: center;max-width: 150px; min-width: 65px; white-space: normal; vertical-align: middle;" class="info">Total Meters</th>
										
										<!-- <th style="text-align: center;max-width: 150px; min-width: 65px; white-space: normal; vertical-align: middle;" class="success" >Active Meters</th> -->
										<th style="text-align: center;max-width: 150px; min-width: 107px; white-space: normal; vertical-align: middle;" class="warning" >Total count of NonComm Meters</th>
										<th style="text-align: center;max-width: 150px; min-width: 107px; white-space: normal; vertical-align: middle;" class="warning">NonComm from Last 24 Hrs</th>
										<th style="text-align: center;max-width: 150px; min-width: 107px; white-space: normal; vertical-align: middle;" class="warning">NonComm from Last 5 Days</th>
										<th style="text-align: center;max-width: 150px; min-width: 107px; white-space: normal; vertical-align: middle;" class="warning">NonComm from Last 10 Days</th>
										<th style="text-align: center;max-width: 150px; min-width: 107px; white-space: normal; vertical-align: middle;" class="warning">NonComm from Last 20 Days</th>
										<th style="text-align: center;max-width: 150px; min-width: 107px; white-space: normal; vertical-align: middle;" class="warning">NonComm from Last 30 Days</th>
			                         	
							</tr>
					  </thead>
					   	<tbody id="getConsisCommFailMtrs">
						</tbody> 
			       </table>
   </div> 
    </div> 
</div>
					   
					   
<div id="stack1" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" >
	<div class="modal-dialog" style="width: 60%;">
		<div class="modal-dailog">
		<div class="modal-content">
        <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
			<h4 class="modal-title" id="tabTitle"></h4>
        </div>
        <div class="modal-body">
        <div class="portlet-body">
         <div id="imageee" hidden="true" style="text-align: center;">
                         <h3 id="loadingText">Loading..... Please wait. 
						 </h3> 
						 <img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width:3%;height: 3%;">
						</div>
						  <div class="table-toolbar">
								<div class="btn-group pull-right" >
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li>
										<a href="#" id="excelExport2" onclick="tableToExcel2('sample_5', 'MetersData')">Export to Excel</a>
										</li>
									</ul>
								</div>
							</div> 
						<table class="table table-striped table-hover table-bordered" id="sample_5">
						<thead>
						<tr>
						    <th>Sl.No</th>
						    <th style="text-align: center;max-width: 150px; min-width: 120px; white-space: normal; vertical-align: middle;" class="info">Region Code</th>
							<th style="text-align: center;max-width: 150px; min-width: 107px; white-space: normal; vertical-align: middle;" class="info"> Region Name</th>
							<th style="text-align: center;max-width: 150px; min-width: 120px; white-space: normal; vertical-align: middle;" class="info">Circle Code</th>
							<th style="text-align: center;max-width: 150px; min-width: 107px; white-space: normal; vertical-align: middle;" class="info"> Circle Name</th>
							<th style="text-align: center;max-width: 150px; min-width: 120px; white-space: normal; vertical-align: middle;" class="info">Town Code</th>
							<th style="text-align: center;max-width: 150px; min-width: 107px; white-space: normal; vertical-align: middle;" class="info"> Town Name</th>
							<!-- <th>Subdivision</th> -->
							<th>MeterNo</th>
							<!-- <th>AccNo</th> -->
							<th>FeederName</th>
							<th>FeederCategory</th>
							<th>Last Meter Read Date</th>
							<th>NonComm Days</th>
						</tr>
						</thead>
						<tbody id="commfailmtrsId">
						</tbody>
						</table>
						</div>
        </div>
		</div>
		</div>
		</div>
		</div>

<script>

function showCircle(zone)
{
 $.ajax({
    	url:'./getCircleByZone',
    	type:'GET',
    	dataType:'json',
    	asynch:false,
    	cache:false,
    	data : {
			zone : zone
		},
    	success:function(response)
    	{
			var html='';
    		//html+="<select id='circle' name='circle' class='form-control' onchange='showTownNameandCode(this.value)' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
       html+="<select id='LFcircle' name='LFcircle' class='form-control' onchange='showTownNameandCode(this.value)' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
    		
			for( var i=0;i<response.length;i++)
			{
				html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
			}
			html+="</select><span></span>";
			$("#LFcircleTd").html(html);
			$('#LFcircle').select2();
    	}
	});
}

function showResultsbasedOntownCode (){
	
}
	
	function showTownNameandCode(circle){
	var zone =  $('#LFzone').val(); 
	
	   $.ajax({
	      	url:'./getTownNameandCodebyCircle',
	      	type:'GET',
	      	dataType:'json',
	      	asynch:false,
	      	cache:false,
	      	data : {
	  			zone : zone,
	  			circle:circle
	  		},
	  		success : function(response1) {
	  			
              var html = '';
             // html += "<select id='town' name='town'  class='form-control  input-medium'  type='text'><option value=''>Select Town</option><option value='%'>ALL</option>";
           html += "<select id='LFtown' name='LFtown'  class='form-control input-medium' type='text'><option value=''>Select Town</option><option value='%'>ALL</option>";
             
              for (var i = 0; i < response1.length; i++) {
                  //var response=response1[i];
                  
                  html += "<option value='"+response1[i][0]+"'>"
                          +response1[i][0] +"-"  +response1[i][1] + "</option>";
              }
              html += "</select><span></span>";
              $("#LFtownTd").html(html);
              $('#LFtown').select2();
          }
	  	});
	  }

</script>
