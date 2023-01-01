<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>

<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
<!-- <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> -->
<script>
$(".page-content").ready(
		function() {
			App.init();
			TableEditable.init();
			FormComponents.init();
			
			$('#MDMSideBarContents,#eaId,#NPPReportFeeder').addClass('start active ,selected');
			$('#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment').removeClass('start active ,selected');
		});
</script>


 <script>
 /* function validation(param){
		var scheme=${'#govt'}
		var town=${'#town'}
		var period=${'#period'}
		
		if(scheme=='' || scheme==null){
			bootbox.alert("Select scheme");
		}
		
		if(town=='' || town==null){
			bootbox.alert("Select town");
		}
		
		if(period=='' || period==null){
			bootbox.alert("Select period");
		}
	}  */
	
	
	
function validation() {
  	
		
		
		var scheme=$("#govt").val()
  		var town=$("#town").val()
  		var period=$("#period").val()
  		
  	 	if(scheme=='' || scheme==null){
			bootbox.alert("Select scheme");
			return false;
		}
  		
  		
		if(town=='' || town==null){
			bootbox.alert("Select town");
			return false;
		}
		
		if(period=='' || period==null){
			bootbox.alert("Select period");
			return false;
		} 
	
  		
  		
  		 /* clearTabledataContent('sample_editable_1'); */
		var html1="";
			$.ajax({
		    	
				url : './getPFCreportD1data/',
		    	type:'GET',
		    	data:{
		    		town:town,
		    		period:period
					
				
				},
		    	success:function(response)
		    	
		    	{
		    	  $("#sample_1").show();
		    	  
		    	   for (var i = 0; i < response.length; i++) 
		    	 {
		    		  var res=response[i];
		    		  
		    		  html1+='<tr><td>'+res[0]+'</td>'+
						'<td>'+res[1]+'</td>'+
						'<td>'+res[2]+'</td>'+
						'<td>'+res[3]+'</td>'+
						'<td>'+res[4]+'</td>'+
						'<td>'+res[5]+'</td></tr>'
		    		 
				}
		    	   $("#feederdata").html(html1);
		    	 
		    	},
		    	
				
			  });
	}
 
function showtownByScheme(scheme)

	{
	
	$.ajax({
			url : './showTownByScheme' + '/' + scheme,
		    	type:'GET',
		    	dataType:'json',
		    	success:function(response)
		    	{
		    		var html='';
		    		html+="<option value=''></option>";
					for( var i=0;i<response.length;i++)
					{
						html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
					}
					$("#town").html(html);
					$('#town').select2();
		    	}
			});
	}

function periodMonth(billmonth){
	
	   var year = param.substring(0, 4);
	   var monthNo = param.substring(4, 6);
	   var monthName =  moment().month(monthNo-1).format('MMM');
	   var firstDate= 01;
	   var lastDate =  new Date(year, monthNo , 0).getDate(); 
	   var fromDate =   monthName+" " +firstDate+ " "+ year;
	   var toDate =  monthName+" " +lastDate+ " "+ year; 
	   var finalDate = fromDate +" "+ "TO" +" " + toDate; 
	   

	   $('#periodMonth').val(finalDate);
	   
}



	
	 
</script> 




<div class="page-content">
               <div class="portlet box blue">
                   <div class="portlet-title">
                   <div class="caption">
                   <i class="fa fa-bars"></i>NPP Report-Feeder
                   </div>
                   </div>
                <div class="portlet-body">
                 
                
				
               
              <table style="border-collapse: separate; border-spacing: 8px;"> 
               
               <tr>
               <td style="width:100px;"><b>Govt.Scheme:</b>
               <select class="form-control select2me input-large" name="govt" id="govt" onchange="showtownByScheme(this.value)" >
               <option value="">Select Govt scheme</option>
               <option value="RAPDRP">RAPDRP</option>
               <option value="IPDS ">IPDS</option>
               </select>
               </td>
               
              
               <td style="width:100px;"><b>Town:</b>
               <select class="form-control select2me input-large" name="town" id="town">
               <option value="">Select Town</option>
               </select>
               </td>
               
               
               <td style="width:100px;"><b>Period:</b>
               <select class="form-control select2me input-large" onchange="periodMonth(this.value)" name="period" id="period">
               <option value="">Select Period</option>
               <c:forEach var="elements" items="${periodList}">
						<option value="${elements}">${elements}</option>
						</c:forEach> 
               </select>
               </td>
               </tr>
               
               </table>
               <div class="row"><br>
               <div class="col-md-2">
                <button class="btn blue pull-left" id="viewrpt" type="button" onclick="validation()">View Report</button>
               
                <!-- <a href="#" id="excelExport" class="btn green pull-right" type="button"  onclick="tableToExcel('sample_1', 'Feeder Count')">Export to Excel</a> -->
               </div>
               
                <div class="col-md-2">
                <button class="btn blue pull-left" id="viewrpt" type="button" onclick="validation()">Export To JSON</button>
             <!--    <a href="#" id="excelExport" class="btn green pull-right" type="button"  onclick="tableToExcel('sample_1', 'Feeder Count')">Export to Excel</a> -->
               </div>
               
               
               </div>
               
             <br><br>
              
               <div><label style="color: #000000; background-color:#DCDCDC; width:100%; padding-top: 20px; float: left; text-align: center;"  data-toggle="modal"><b>NPP Report</b>
				 </label>
			   </div>
			   
			   <div><label style="color: #000000; background-color:#DCDCDC; width:100%; padding-top: 10px; float: left; text-align: center;"  data-toggle="modal"><b>Level of Monitoring:NPP</b>
				 </label>
			   </div>
			   
			   <div><label style="color: #000000; background-color:#DCDCDC; width:100%; padding-top: 10px; float: left; text-align: center;"  data-toggle="modal"><b>Format:Feeder</b>
				 </label>
			   </div>
			   
			   
               
               <table style="color:#000000;  padding-bottom: 25px;  width:100%; " class="table table-bordered">
               
               
               <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC; "><b>Name of State:</b>
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large" name="" autocomplete="off" id="" readonly type="text" style="width: 100%;" value=${stateName}>
               
               </td>
               </tr>
               
                <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC;"><b>Name of Discom:</b>
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large" name="" autocomplete="off" id="" readonly type="text" style="width: 100%;" value=${discomName}>
               </td>
               </tr>
               
               <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC;"><b>Report Month:</b>
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large" name="" autocomplete="off" id="" readonly type="text" style="width: 100%;" >
               </td>
               </tr>
                
               <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC;"><b>Period:1Month:</b>
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large" name="" autocomplete="off" id="periodMonth" readonly="readonly"  type="text" style="width: 100%;" >
               </td>
               </tr>   
              </table>
              
              <div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 10px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> --> 
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Feeder Count Details')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>

								<table class="table table-striped table-bordered table-hover" id="sample_3"  >
									<thead>		
										<tr>		
										 <th>Sil. No</th>
									    <th>TOWN NAME</th>
									    <th>REP_MONTH</th>
										<th>PERIOD_NA</th>
										<th>FEEDER NAME</th>						
									    <th>FEEDER CODE</th>
									    <th>FEEDER TYPE</th>
									    <th>START BILLING_PERIOD</th>
										<th>END BILLING_PERIOD</th>
									
									
												
										</tr>
									</thead>
									<tbody id="">
									
										 <th></th>
										 <th></th>
									    <th></th>
										<th></th>
										<th></th>						
									    <th></th>
									    <th></th>
									  <th></th>
									 <th></th>
									
									</tbody>
								</table>
               </div>	
               </div>
               </div>