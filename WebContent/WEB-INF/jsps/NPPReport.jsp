
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
  <script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
	<!-- <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> -->
	<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
	<script src="resources/assets/scripts/app.js"></script>
	<script src="resources/assets/scripts/ui-extended-modals.js"></script>
	

<script>

$(".page-content").ready(
		function() {
			App.init();
			TableEditable.init();
			FormComponents.init();
			loadSearchAndFilter('nppreportid');
		 $('#eaId,#eaWiseReport,#NPPReport').addClass('start active ,selected');
			$('#MDMSideBarContents,#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment').removeClass('start active ,selected'); 

			$("#scheme").val("").trigger("change");
			$("#period").val("").trigger("change");
		});
		
		
function showtown(param)
{
	
   var scheme=$("#scheme").val(); 
   

    //alert(scheme)
    
	  $.ajax({

		       type:'GET',
		       url:'./showTownByScheme',
		       dataType:'json',
		       data:{
		    	   scheme:scheme
		       },
		       success:function(response)
		       {
		    	   
		    	   var html='';

		    		
		    		html+="<option value=''><b>Select Town</b></option>";
					for( var i=0;i<response.length;i++)
					{
						html+="<option  value='"+response[i][1]+"'>"+response[i][1]+"-"+response[i][0]+"</option>";
					}
					$("#town").html(html);
					$('#town').select2(); 
		       }
		      
	  }); 
}  

function view()
{
	var scheme=$("#scheme").val();
	var period=$('#period').val();
	var town=$('#town').val();
	
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
   $('#imageee').show();
   $('#nppreportid').dataTable().fnClearTable();
	 $.ajax({
		
		url:'./NPPReportDetails',
		type : "GET",
		data:{
			period:period,
			town:town,
			scheme:scheme
				
		},
		success:function(res){
			 $('#imageee').hide();
			if (res.length==0 || res.length == null) {
				bootbox.alert("No data found for this selected town and period");
			} else {

				html="";
				$.each(res,function(i,v){
					html+="<tr><td>"+(i+1)+"</td><td>"+v[0]+"</td><td>"+v[1]+"</td><td>"+v[2]+"</td><td>"+v[3]+"</td><td>"+v[4]+"</td><td>"+v[5]+"</td><td>"+v[6]+"</td><td>"+v[7]+"</td><td>"+v[8]+"</td><td>"+v[9]+"</td></tr>";
				});
				
				clearTabledataContent('nppreportid');
				$('#nppreportid').dataTable().fnClearTable();
				$("#npptbodyidData").html(html);
				loadSearchAndFilter('nppreportid');

			}
					}
	}); 
	
	}
		
		
function downloadReport() {

	var govtscheme = $('#scheme').val();
	var town = $('#town').val();
	//town = JSON.stringify(town);
	var month = $('#period').val();
	
	//alert(month);
	//$('#imageee').show();
	/* $.ajax({
		url : "./downloadNPPDataGeneration",
		type : "GET",
		dataType : 'json',
		asynch : false,
		cache : false,
		success : function(data) {
			alert(data);
			
		}
	}); */

	window.open("./downloadNPPDataGeneration?monthyear="+month+ "&govtscheme=" + govtscheme + "&town=" + town);
	//window.location.href=("./NPPReportConsumerPDF?scheme="+scheme+"&town="+town+"&period="+period+"&state="+state+"&discom="+discom+"&month="+month+"&ieperiod="+ieperiod+"&townname="+townname);
}
</script>

<div class="page-content">
               <div class="portlet box blue">
                   <div class="portlet-title">
                   <div class="caption">
                   <i class="fa fa-bars"></i>NPP Report Consumer
                   </div>
                   </div>
                <div class="portlet-body">
       		    <div class="row">
       		    
				<div class="col-md-3" id="schemeid">
					<label class="control-label">Select Govt.Scheme:-</label> 

					<select class="select2_category form-control" name="scheme" id="scheme" onchange="showtown(this.value);">
						<!-- <option value="RAPDRP">RAPDRP</option> -->
						<option value="">Select Govt scheme</option>
               <!-- <option value="RAPDRP">RAPDRP</option> -->
               <option value="IPDS ">IPDS</option>
					</select>
				</div>
				
				<div class="col-md-3" id="townid">
					<label class="control-label">Town:-</label> 
					<select class="form-control select2me " name="town" id="town" >
					<option value="Select Town">Select Town</option>
					</select>
				</div>
				
				<div class="col-md-3" id="periodid">

					<label class="control-label">Period:-</label>
					
					<input type="text" class="form-control from"  id="period"  
					onchange="dateformat(this.value);"		name="period" placeholder="Select From Month" style="cursor: pointer">  
					<%-- <select class="select2_category form-control" name="period" id="period" onchange="dateformat(this.value);">

					<label class="control-label">Period:-</label> 
					<select class="form-control select2me" name="period" id="period" onchange="dateformat(this.value);">

					        <option value="">Select Period</option>
						<c:forEach var="type" items="${periodlist}">
							<option value="${type}">${type}</option>
						</c:forEach> 
					</select> --%>
				</div>

			</div>
			
               <div class="row"><br>
               <div class="col-md-2">
               <button class="btn green " id="" type="button" onclick="view('nppreportid','NPPReport details')">View Report</button>
               </div>
               <div class="col-md-2">
               <a href="#" id="excelExport" class="btn green" type="button"  onclick="exportToExcelMethod('nppreportid', 'NPPReport')">Export to Excel</a>
               </div>
               <div class="col-md-2">
               <a href="#" id="" class="btn green" type="button"  onclick="downloadReport()">Export to JSON</a>
               </div>
               <div class="col-md-2">
               <a href="#" id="" class="btn green " type="button"  onclick="exportPDF('nppreportid', 'NPPReport')">Export to PDF</a>
               </div>
               </div>
               <br>
            
              <div><label style="color: #000000; background-color:#DCDCDC; width:100%; padding-top: 20px; float: left; text-align: center;"  data-toggle="modal">NPP Report
				 </label>
			   </div>
			   
			   <div><label style="color: #000000; background-color:#DCDCDC; width:100%; padding-top: 10px; float: left; text-align: center;"  data-toggle="modal">Level of Monitoring:NPP
				 </label>
			   </div>
			   
			   <div><label style="color: #000000; background-color:#DCDCDC; width:100%; padding-top: 10px; float: left; text-align: center;"  data-toggle="modal">Format:Consumer
				 </label>
			   </div>
			   
			   
      			 <table style="color:#000000;  padding-bottom: 25px;  width:100%; " class="table table-striped table-bordered table-hover">
               
               <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC;">Name of State :
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large" name="state"  id="state" readonly="readonly" value="${stateName}" style="width: 100%; padding-bottom: 10px;" >
               </td>
               </tr>
               
               <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC;">Name of Discom :
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large" name="discom"  id="discom" readonly="readonly" value="${discomName}" style="width: 100%; padding-bottom: 10px;" >
               </td>
               </tr>
               
               <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC;">Report Month :
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large" name="repmonth" id="repmonth" readonly="readonly" style="width: 100%; padding-bottom: 10px;" >
               </td>
               </tr>  
               
               <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC;">Period:1Month :
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large" name="periodmonth" id="periodmonth" readonly="readonly" style="width: 100%; padding-bottom: 10px;" >
               </td>
               </tr>  
               
      </table>
       

       <div class="row">
             <div class="col-md-12">
                 <form action="./NPPReport">
                       <table class="table table-striped table-hover table-bordered" id="nppreportid">
                              <thead>
                                     <tr>
                                         <th rowspan="2" style="text-align: center;max-width: 120px; min-width: 100px; white-space: normal;">Sl.No</th>
								         <th rowspan="2" style="text-align: center;max-width: 120px; min-width: 100px; white-space: normal;">Town Code</th>
								         <th rowspan="2" style="text-align: center;max-width: 120px; min-width: 100px; white-space: normal;">DISCOM Code (NPP)</th>
                                         <!-- <th rowspan="2" style="text-align: center;max-width: 120px; min-width: 100px; white-space: normal;">Town Code (NPP)</th> -->
                                     
                                     	 <th colspan="4" style="text-align: center;">New Connection</th>
                                     	 <th colspan="4" style="text-align: center;">Complaints</th>
                                     </tr> 
                                     <tr>
								         <th style="text-align: center;max-width: 200px; min-width: 120px; white-space: normal;">New connection pending from previous month</th>
								         <th style="text-align: center;max-width: 180px; min-width: 120px; white-space: normal;">New connection applied in current month</th>
								         <th style="text-align: center;max-width: 200px; min-width: 120px; white-space: normal;">Total connection released in current month</th>
								         <th style="text-align: center;max-width: 200px; min-width: 120px; white-space: normal;">Connection released within SERC limit</th>
							        
								         <th style="text-align: center;max-width: 180px; min-width: 120px; white-space: normal;">Complaints pending from previous month</th>
								         <th style="text-align: center;max-width: 120px; min-width: 120px; white-space: normal;">New complaints received in current month</th>
								         <th style="text-align: center;max-width: 200px; min-width: 120px; white-space: normal;">Total complaints closed in current month</th>
								         <th style="text-align: center;max-width: 200px; min-width: 120px; white-space: normal;">Complaints closed within SERC limit</th>
							         </tr> 
                              </thead>
                              <div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
                              <tbody id="npptbodyidData">
                              </tbody>
                       </table>
                 </form>
           </div> 
       </div>
</div>
			   </div>
			   
               </div>	
               
 <script>
 
/*  function dateformat(param)
 {
	   var year = param.substring(2, 6);
	   var monthNo = param.substring(0, 2);
	   var monthName =  moment().month(monthNo-1).format('MMM');
	   var firstDate= 01;
	   var lastDate =  new Date(year, monthNo , 0).getDate(); 
	   var fromDate =   monthName+" " +firstDate+ " "+ year;
	   var toDate =  monthName+" " +lastDate+ " "+ year; 
	   var finalDate = fromDate +" "+ "to" +" " + toDate;

	   $("#periodmonth").val(finalDate);
	   
	   var year = param.substring(2, 6);
	   var monthNo = param.substring(0, 2);
	   if(monthNo>=12)
	   {
	   year++;
	   }
	   var monthVal=moment().month(monthNo).format('MMM');
	   var endMonth=monthVal +"-"+year
	   
       $("#repmonth").val(endMonth);
 } */
 
 
	
 function dateformat(param)
 {
	   var year = param.substring(2, 6);
	   var monthNo = param.substring(0, 2);
	   var monthName =  moment().month(monthNo-1).format('MMM');
	   var firstDate= 01;
	   var lastDate =  new Date(year, monthNo , 0).getDate(); 
	   var fromDate =   monthName+" " +firstDate+ " "+ year;
	   var toDate =  monthName+" " +lastDate+ " "+ year; 
	   var finalDate = fromDate +" "+ "to" +" " + toDate;
	   
	   $("#periodmonth").val(finalDate);
	   
	   var year = param.substring(2, 6);
	   var monthNo = param.substring(0, 2);
	   if(monthNo>=12)
	   {
	   year++;
	   }
	   var monthVal=moment().month(monthNo).format('MMM');
	   var endMonth=monthVal +"-"+year
	   
       $("#repmonth").val(endMonth);
 }
 
 
 
	var date=new Date();
	var year=date.getFullYear();
	var month=date.getMonth();

	$('.from').datepicker
	({
	    format: "mmyyyy",
	    minViewMode: 1,
	    autoclose: true,
	    startDate :new Date(new Date().getFullYear()),
	    endDate: new Date(year, month-1, '31')
	
	});
	
	function exportPDF()
	{
    	var scheme=$("#scheme").val();
  		var town=$("#town").val();
  		var period=$("#period").val();
  		var state=$('#state').val();
  		var discom=$('#discom').val();
  		var month=$('#repmonth').val();
  		var ieperiod=$('#periodmonth').val();
  		var townname = $("#town option:selected").map(function(){return this.text}).get().join(',');
  		//window.open("./NPPReportConsumerPDF/"+scheme+"/"+town+"/"+period+"/"+state+"/"+discom+"/"+month+"/"+ieperiod)
  		
  		window.location.href=("./NPPReportConsumerPDF?scheme="+scheme+"&town="+town+"&period="+period+"&state="+state+"&discom="+discom+"&month="+month+"&ieperiod="+ieperiod+"&townname="+townname);
	}

	function exportToExcelMethod()
	{
		var scheme=$("#scheme").val();
  		var town=$("#town").val();
  		var period=$("#period").val();
  		var ieperiod=$('#periodmonth').val();

  		var per=period;
		   var year = per.substring(2, 6);
		   var monthNo = per.substring(0, 2);
		   if(monthNo>=12)
			   {
			   year++;
			   }
		   var monthVal=moment().month(monthNo).format('MMM');
		   var endMonth=monthVal +"-"+year;
  		
  		window.open("./exportToExcelNPPRepConsumerData?scheme="+scheme+"&town="+town+"&period="+period+"&ieperiod="+ieperiod+"&endMonth="+endMonth);
	}
	
 </script>              
