<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>


<!-- <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> -->
 <script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
<script>
$(".page-content").ready(
		function() {
			App.init();
			TableEditable.init();
			FormComponents.init();
			loadSearchAndFilter('sample_1');
			$('#eaId,#eaWiseReport,#PFCreportD4').addClass('start active ,selected');
			$('#MDMSideBarContents,#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment').removeClass('start active ,selected');
		});

var tableToExcelNew = (function () {
    var uri = 'data:application/vnd.ms-excel;base64,'
        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
        , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
        , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) }
    return function (table, name) {
        if (!table.nodeType) table = document.getElementById(table)
        var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML }
        document.getElementById("excelExport").href = uri + base64(format(template, ctx));
        document.getElementById("excelExport").download = name+'_'+moment().format("DD-MM-YYYY_hh.mm.ss")+'.xls';
    }
})() 
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
		var scheme=$("#govt").val();
  		var town=$("#TownId").val();
  		var period=$("#period").val();
  		
  	 	if(scheme == '' || scheme == null){
			bootbox.alert("Select scheme");
			return false;
		}
  		
		if(town=='' || town==null){
			bootbox.alert("Select town");
			return false;
		}
		
		if(period=='' || period==null){
			bootbox.alert("Select Period");
			return false;
		}
		$('#imageee').show();
		//$("#pfcD4ReportDetails").show();
		/* var html1=""; */
			$.ajax({
				url : './getPFCreportD4data',
		    	type:'GET',
		    	data:{
		    		TownId:town,
		    		period :period
				},
		    	success:function(response)
		    	{
		    		$('#imageee').hide();
		    		var html1="";
		    	  //$("#sample_1").show();
		    	   for (var i = 0; i < response.length; i++) 
		    	 {
		    		  var res=response[i];
		    		  
		    		  html1+='<tr><td>'+(i+1)+'</td>'+
						'<td>'+res[2]+'</td>'+
						'<td>'+res[4]+'</td>'+
						'<td>'+res[5]+'</td>'+
						'<td>'+res[6]+'</td>'+
						'<td>'+res[7]+'</td>'+
						'<td>'+res[8]+'</td></tr>'
		    		 
				}

		   		$('#sample_1').dataTable().fnClearTable();
		   		$("#feederdata").html(html1);
		   		loadSearchAndFilter('sample_1');
		   	},

		   complete : function() {
		   	loadSearchAndFilter('sample_1');
		   },
			  });
	}
 
function showtownByScheme(scheme)

	{
	//alert();
	$.ajax({
			url : './showTownByScheme' ,
		    	type:'GET',
		    	dataType:'json',
		    	data:{scheme:scheme},
		    	success:function(response)
		    	{
		    		/* html+="<select id='town' name='town' class='form-control select2me input-medium' multiple='multiple' type='text'><option value='All'><b>ALL Towns</b></option>";
		    		var html=''; */
		    		var html = '';
	                 html+="<option value='All'><b>ALL Towns</b></option>";;
		    		//html+="<option value=''>Select Town</option>";
					for( var i=0;i<response.length;i++)
					{
						html+="<option  value='"+response[i][1]+"'>"+response[i][1]+"-"+response[i][0]+"</option>";
					}
					$("#TownId").html(html);
					$('#TownId').select2();
		    	}
			});
	}

/* function periodMonth(billmonth){
	
	   var year = param.substring(0, 4);
	   var monthNo = param.substring(4, 6);
	   var monthName =  moment().month(monthNo-1).format('MMM');
	   var firstDate= 01;
	   var lastDate =  new Date(year, monthNo , 0).getDate(); 
	   var fromDate =   monthName+" " +firstDate+ " "+ year;
	   var toDate =  monthName+" " +lastDate+ " "+ year; 
	   var finalDate = fromDate +" "+ "TO" +" " + toDate; 
	   

	   $('#periodMonth').val(finalDate);
	   
} */


/* function dateformat(param){
	
	   var year = param.substring(0, 4);
	   var monthNo = param.substring(4, 6);
		var date = year+monthNo;
	
		var currentReportMonth = moment(date,'YYYYMM').format('MMMM-YYYY');
	 var initialMonth = moment(date,'YYYYMM').subtract(14, 'months').format('MMMM-YYYY');
	 var endMonthUpdate = moment(date,'YYYYMM').subtract(3, 'months').format('MMMM-YYYY');


	 var forMonth = moment(date,'YYYYMM').subtract(3, 'months').format('MM');
	 var forYear = moment(date,'YYYYMM').subtract(3, 'months').format('YYYY');
	   
	//   alert(currentReportMonth);
	 
	   var monthName =  moment().month(monthNo-1).format('MMMM');
	  
	   var firstDate= 01;
	   var lastDate =  new Date(forYear, forMonth , 0).getDate(); 
	   var fromDate =   monthName+" " +firstDate+ " "+ year;
	   var toDate =  monthName+" " +lastDate+ " "+ year; 
	   var finalDate = fromDate +" "+ "To" +" " + toDate;
	   
	   
	   var updatedFinalInputPeriod = firstDate+" "+initialMonth +" " +" To " +" "+ lastDate +" "+endMonthUpdate;
	   
	   $("#periodmonth").val(updatedFinalInputPeriod);
	   
	   var year = param.substring(0, 4);
	   var monthNo = param.substring(4, 6);
	   if(monthNo>=12)
		   {
		   //alert(year);
		   year++;
		   }
	   var monthVal=moment().month(monthNo-1).format('MMM');
	   var endMonth=monthVal +"-"+year;
	
	   $("#repmonth").val(currentReportMonth); 
	   
	 
}
 */

 function dateformat(param){
 	
	   var year = param.substring(0, 4);
	   var monthNo = param.substring(4, 6);
		var date = year+monthNo;
		//alert(monthNo);
	
		var currentReportMonth = moment(date,'MMYYYY').format('MMMM-YYYY');
	 var initialMonth = moment(date,'MMYYYY').subtract(14, 'months').format('MMMM-YYYY');
	 var endMonthUpdate = moment(date,'MMYYYY').subtract(3, 'months').format('MMMM-YYYY');


	 var forMonth = moment(date,'MMYYYY').subtract(3, 'months').format('MM');
	 var forYear = moment(date,'MMYYYY').subtract(3, 'months').format('YYYY');
	   
	//   alert(currentReportMonth);
	 
	   var monthName =  moment().month(monthNo-1).format('MMMM');
	  
	   var firstDate= 01;
	   var lastDate =  new Date(forYear, forMonth , 0).getDate(); 
	   var fromDate =   monthName+" " +firstDate+ " "+ year;
	   var toDate =  monthName+" " +lastDate+ " "+ year; 
	   var finalDate = fromDate +" "+ "To" +" " + toDate;
	   
	   
	   var updatedFinalInputPeriod = firstDate+" "+initialMonth +" " +" To " +" "+ lastDate +" "+endMonthUpdate;
	   
	   $("#periodmonth").val(updatedFinalInputPeriod);
	   
	   var year = param.substring(2, 6);
	   var monthNo = param.substring(0, 2);
	   
     
	   if(monthNo>=12)
		   {
		   //alert(year);
		   year++;
		   }
	  // alert(monthNo);
	   var monthVal=moment().month(monthNo-1).format('MMM');
	   var endMonth=monthVal +"-"+year;
	
	  // $("#ReportMonthId").val(currentReportMonth); 
	  $("#repmonth").val(endMonth);
	 //$("#ReportMonthId").val(endMonth); 
	   
	 
 }

/*  function dateformat(param)
{
   /* var year = param.substring(0, 4); 
   var monthNo = param.substring(4, 6);
   var monthName =  moment().month(monthNo-1).format('MMM');
   var firstDate=01;
   var lastDate =  new Date(year, monthNo , 0).getDate(); 
   var fromDate =   monthName+" " +firstDate+ ","+ year;
   var toDate =  monthName+" " +lastDate+ ","+ year; 
   var finalDate = fromDate +" "+ "TO" +" " + toDate; 
   $("#periodmonth").val(finalDate); 
   
   var year = param.substring(0, 4);
   var monthNo = param.substring(4, 6);
   var monthName =  moment().month(monthNo).format('MMM');
   var finalDate = monthName +" "+ "-" +" " + year; 
   var year = param.substring(2, 6); 
   var monthNo = param.substring(0, 2);
   var monthName =  moment().month(monthNo-1).format('MMM');
   var firstDate=01;
   var lastDate =  new Date(year, monthNo , 0).getDate(); 
   var fromDate =   monthName+" " +firstDate+ ","+ year;
   var toDate =  monthName+" " +lastDate+ ","+ year; 
   var finalDate = fromDate +" "+ "TO" +" " + toDate; 
   $("#periodmonth").val(finalDate);
	
   
   var year = param.substring(2, 6);
	   var monthNo = param.substring(0, 2);
	   if(monthNo>=12)
		   {
		   //alert(year);
		   year++;
		   }
	   var monthVal=moment().month(monthNo-1).format('MMM');
	   var endMonth=monthVal +"-"+year;
   //var year = param.substring(0, 4);
   //var monthNo = param.substring(4, 6);
   //var monthName =  moment().month(monthNo-1).format('MMM');
   var finalDate = monthName +" "+ "-" +" " + year; 

   $("#repmonth").val(finalDate);
 } */
 

function freezeReporttValidate(){
	
	var billmonth = $("#period").val();
	if(billmonth == '' ){
		bootbox.alert('Please select period to freeze data');
	}
	$.ajax({
    	url : "./validatefreezeD4Report",
		type:'GET',
		data:{billmonth:billmonth},
    	success:function(res) {
    		
			if(res == 'dataFreezed'){
				bootbox.alert("Data has been already freezed");
			}else{
				$.ajax({
			    	url : "./freezeD4Report",
					type:'GET',
					data:{billmonth:billmonth},
			    	success:function(res) {
			    		
						if(res == 'DataFreezed'){
							bootbox.alert("Data has been successfully freezed");
						
						}else {
							bootbox.alert("Failed to freeze data");
						}
			    	 
			    	},
			  });
			}
    	 
    	},
  });
	
}

	 
</script> 




<div class="page-content">
               <div class="portlet box blue">
                   <div class="portlet-title">
                   <div class="caption">
                   <i class="fa fa-bars"></i>PFC Report-D4
                   </div>
                   </div>
                <div class="portlet-body">
                 
                
				
               
              <table style="border-collapse: separate; border-spacing: 8px;"> 
               
               <tr>
               <td style="width:100px;"><b>Govt.Scheme:</b>
               <select class="form-control select2me input-large" name="govt" id="govt" onchange="showtownByScheme(this.value)" >
               <option value="">Select Govt scheme</option>
               <!-- <option value="RAPDRP">RAPDRP</option> -->
               <option value="IPDS ">IPDS</option>
               </select>
               </td>
               
              
               <td style="width:100px;"><b>Town:</b>
                 <select
					class="form-control select2me input-medium" name="TownId" id="TownId" multiple="multiple"
						data-placeholder="Select Town">
					</select>
               </select>
               </td>
               
               
               <td style="width:100px;"><b>Report Month:</b>
               <input type="text" class="form-control from"  id="period" style="width:200px;"  onchange="dateformat(this.value);"
										name="period" placeholder="Select Report Month" style="cursor: pointer">
              <%--  <select class="form-control select2me input-large" onchange="dateformat(this.value);" name="period" id="period">
               <option value="">Select Period</option>
               <c:forEach var="elements" items="${periodList}">
						<option value="${elements}">${elements}</option>
						</c:forEach> 
               </select> --%>
               </td>
               </tr>
               
               </table>
              <!--  <div class="row"><br>
               <div class="col-md-4">
                <button class="btn blue pull-left" id="viewrpt" type="button" onclick="return validation()">View Report</button>
                <a href="#" id="excelExport" class="btn green pull-right" type="button"  onclick="tableToExcel('sample_1', 'PFC D4 Report')">Export to Excel</a> 
               </div>
               </div> -->
                <div class="row"><br>
               <div class="col-md-2">
                <button class="btn blue pull-left" id="viewrpt" type="button" onclick="return validation()">View Report</button>
                
                <!-- <a href="#" id="excelExport" class="btn green pull-right" type="button"  onclick="tableToExcel('sample_1', 'Feeder Count')">Export to Excel</a> -->
               </div>
              <!--  <div class="col-md-2">
                <button class="btn blue pull-left" id="viewrpt" type="button" onclick="freezeReporttValidate()">Freeze Report</button>
                
                <a href="#" id="excelExport" class="btn green pull-right" type="button"  onclick="tableToExcel('sample_1', 'Feeder Count')">Export to Excel</a>
               </div> -->
               </div>
               </div>
              
               </div>
               <div id="pfcD4ReportDetails">
               <div><label style="color: #000000; background-color:#DCDCDC; width:100%; padding-top: 20px; float: left; text-align: center;"  data-toggle="modal"><b>Feeder wise AT & C loss report ( 10% worst feeder)</b>
				 </label>
			   </div>
			   
			   <div><label style="color: #000000; background-color:#DCDCDC; width:100%; padding-top: 10px; float: left; text-align: center;"  data-toggle="modal"><b>Level of Monitoring:PFC/MoP</b>
				 </label>
			   </div>
			   
			   <div><label style="color: #000000; background-color:#DCDCDC; width:100%; padding-top: 10px; float: left; text-align: center;"  data-toggle="modal"><b>Format:D4</b>
				 </label>
			   </div>
			   
			   
               
               <table style="color:#000000;  padding-bottom: 25px;  width:100%; " class="table table-bordered">
               
               
               <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC; "><b>Name of State:</b>
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large" name="" autocomplete="off" id="stateid" readonly type="text" style="width: 100%;" value="${stateName}">
               
               </td>
               </tr>
               
                <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC;"><b>Name of Discom:</b>
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large" name="" autocomplete="off" id="discomid" readonly type="text" style="width: 100%;" value="${discomName}">
               </td>
               </tr>
               
               <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC;"><b>Report Month:</b>
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large" name="repmonth" autocomplete="off" id="repmonth" readonly type="text" style="width: 100%;" >
               </td>
               </tr>
                
               <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC;"><b>Period:</b>
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large" name="periodmonth" autocomplete="off" id="periodmonth" readonly="readonly"  type="text" style="width: 100%;" >
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
								<li><a href="#" id="" 
									onclick="exportPDF('sample_1','Feeder wise AT & C loss report')">Export to PDF</a></li> 
								<li><a href="#" id="excelExport"
									onclick="exportToExcelMethod('sample_1', 'Feeder wise AT & C loss report ')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
               
          
                    <table class="table table-striped table-hover table-bordered" id="sample_1">
               <thead>
			    <tr>
								<th rowspan="2" style="text-align: center;max-width: 150px; min-width: 120px; white-space: normal;">Sl.no</th>
								<th rowspan="2" style="text-align: center;max-width: 150px; min-width: 120px; white-space: normal;">Town Code</th>
								<th rowspan="2" style="text-align: center;max-width: 200px; min-width: 180px; white-space: normal;">Feeder Code</th>
								<th rowspan="2" style="text-align: center;max-width: 200px; min-width: 180px; white-space: normal;">Name Of Feeder</th>
								
								<th colspan="3" style="text-align: center;">Cumulative Billing Efficiency, Collection Efficiency & AT&C Losses in %</th>
								
							</tr>
							<tr>
								<th style="text-align: center;max-width: 180px; min-width: 120px; white-space: normal;">Billing Efficiency (%)</th>
								<th style="text-align: center;max-width: 120px; min-width: 120px; white-space: normal;">Collection Efficiency (%)</th>
								<th style="text-align: center;max-width: 200px; min-width: 120px; white-space: normal;">AT&C Loss(%)</th>
								
							</tr>
			    </thead>
			    <div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
			    	  <tbody id="feederdata">
					 
					 </tbody> 
               </table>
               </div>	
               </div>
               
               
               
               
               	<script>
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
	  	    var scheme=$("#govt").val();
			var town=$("#TownId").val();
			var period=$("#period").val();
			var state=$('#stateid').val();
			var discom=$('#discomid').val();
			var month=$('#repmonth').val();
			var ieperiod=$('#periodmonth').val();
			var townname = $("#TownId option:selected").map(function(){return this.text}).get().join(',');
			//window.open("./PFCreportD4PDF/"+scheme+"/"+town+"/"+period+"/"+state+"/"+discom+"/"+month+"/"+ieperiod)
			
			window.location.href=("./PFCreportD4PDF?scheme="+scheme+"&town="+town+"&period="+period+"&state="+state+"&discom="+discom+"&month="+month+"&ieperiod="+ieperiod+"&townname="+townname);
	}

	function exportToExcelMethod()
	{
		var scheme=$("#govt").val();
		var town=$("#TownId").val();
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
  		
  		window.open("./exportToExcelPFCReportD4Data?scheme="+scheme+"&town="+town+"&period="+period+"&ieperiod="+ieperiod+"&endMonth="+endMonth);
	}

</script>
               