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
			loadSearchAndFilter('sample_1');
			$('#eaId,#eaWiseReport,#pfcd7report').addClass('start active ,selected');
			$('#MDMSideBarContents,#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment').removeClass('start active ,selected');
		});
</script>

<script>
function validation() {
	$('#sample_1').dataTable().fnClearTable();
	  var scheme=$("#govt").val();
	  var town=$("#town").val();
	  var rdngmnth=$("#period").val();
	  		
	  	if(scheme=='' || scheme==null){
			bootbox.alert("Select scheme");
			return false;
		}
	  		
	  		
		if(town=='' || town==null){
		   bootbox.alert("Select town");
			return false;
		}
			
		if(rdngmnth=='' || rdngmnth==null){
			bootbox.alert("Select period");
			return false;
		}
		
		$('#imageee').show();	
	  		
		var html="";
				$.ajax({
			    	
					url : "./getpfcd7reportdata",
					type:'GET',
					data:{
						rdngmnth:rdngmnth,
						town:town
					},
			    	success:function(res) {
			    		$('#imageee').hide();
			    		html = "";
						if(res.length == 0 || res.length == null ){
							bootbox.alert("No data found ");
						}
						else{
						for(var i=0;i<res.length;i++){
							var element = res[i];
						
						html +="<tr>"
						+"<td>"+(i+1)+"</td>"
						+"<td>"+(element[6]==null?"":element[6])+"</td>"
						+"<td>"+(element[1]==null?"":element[1])+"</td>"
						+"<td>"+(element[2]==null?"":element[2])+"</td>"
						+"<td>"+(element[3]==null?"":element[3])+"</td>"
						+"<td>"+(element[4]==null?"":element[4])+"</td>";
			
						}
						clearTabledataContent('sample_1');
						$('#sample_1').dataTable().fnClearTable();
			    	   $("#edata").html(html);
			    	   loadSearchAndFilter('sample_1');
						}
			    	 
			    	},
			  });
		}
		
		
		function freezeReporttValidate(){
			
			var billmonth = $("#period").val();
			if(billmonth == '' ){
				bootbox.alert('Please select period to freeze data');
			}
			$.ajax({
		    	url : "./validatefreezeD7Report",
				type:'GET',
				data:{billmonth:billmonth},
		    	success:function(res) {
		    		
					if(res == 'dataFreezed'){
						bootbox.alert("Data has been already freezed");
					}else{
						$.ajax({
					    	url : "./freezeD7Report",
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
		
		
	function showFinalDateByPeriod(param)
	{
	   var year = param.substring(2, 6); 
	   var monthNo = param.substring(0, 2);
	   var monthName =  moment().month(monthNo-2).format('MMM');
	   var firstDate=01;
	   var lastDate =  new Date(year, monthNo , 0).getDate(); 
	   var fromDate =   monthName+" " +firstDate+ ","+ year;
	   var toDate =  monthName+" " +lastDate+ ","+ year; 
	   var finalDate = fromDate +" "+ "TO" +" " + toDate; 
	   $("#periodMonth").val(finalDate);
		
	   
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
	   $("#rptmonth").val(endMonth);
	 }
	</script>
	
	<script>
	 
	function showtownByScheme(scheme)
	{
		
		$.ajax({
				url : './showTownByScheme',
			    	type:'GET',
			    	dataType:'json',
			    	data:{
			    		scheme:scheme	
			    	},
			    	success:function(response)
			    	{
			    		var html='';
			    		html+="<option value='All'>All Towns</option>";
						for( var i=0;i<response.length;i++)
						{
							
							html+="<option  value='"+response[i][1]+"'>"+response[i][1]+"-"+response[i][0]+"</option>";
						}
						$("#town").html(html);
						$('#town').select2();
						
			    	}
				});
		}
		
	
	</script>
	
	
	
	
	<div class="page-content">
               <div class="portlet box blue">
                   <div class="portlet-title">
                   <div class="caption">
                   <i class="fa fa-bars"></i>PFC Report-D7
                   </div>
                   </div>
                <div class="portlet-body">
                 
                
				
               
              <table style="border-collapse: separate; border-spacing: 8px;"> 
               
               <tr>
               <td style="width:100px;"><b>Select&nbsp;Govt.Scheme:</b>
               <select class="form-control select2me input-large" name="govt" id="govt" onchange="showtownByScheme(this.value)" >
               <option value="">Select Govt scheme</option>
               <option value="IPDS ">IPDS</option>
               </select>
               </td>
               
              
               <td style="width:100px;"><b>Town:</b>
               <select class="form-control select2me input-large" multiple="multiple" name="town" id="town">
               <option value="">Select Town</option>
               </select>
               </td>
               
               
               <td style="width:100px;"><b>Report Month:</b>
               
               	<input type="text" class="form-control from"  id="period" style="width:200px;"  onchange="showFinalDateByPeriod(this.value);"
										name="period" placeholder="Select Report Month" style="cursor: pointer">
              <%--  <select class="form-control select2me input-large" name="period" id="period" onchange="showFinalDateByPeriod(this.value)">
               <option value="">Select Period</option>
               <c:forEach var="elements" items="${periodList}">
            
						<option value="${elements}">${elements}</option>
						</c:forEach> 
               </select> --%>
               </td>
               </tr>
               
               </table>
              
              <div class="row"><br>
               <div class="col-md-2">
                <button class="btn blue pull-left" id="viewrpt" type="button" onclick="validation()">View Report</button>
                               
               </div>
               <!-- <div class="col-md-2">
                <button class="btn blue pull-left" id="viewrpt" type="button" onclick="freezeReporttValidate()">Freeze Report</button>
                
                
               </div> -->
               </div>
             <!--   <div class="row"><br>
               <div class="col-md-4">
                <button class="btn blue pull-left" id="viewrpt" type="button" onclick="view()" style="position: relative; left: 200%">View Report</button>
                <button class="btn blue pull-left" id="freezerpt" type="button" onclick="freezeDdataValidation()" style="position: relative; left: 200%">Freeze Data</button>
               
               </div>
               </div> -->
               
               </div>
               
              
               </div>
               
               <div><label style="color: #000000; background-color:#DCDCDC; width:100%; padding-top: 20px; float: left; text-align: center;"  data-toggle="modal"><b>E-Payment report</b>
				 </label>
			   </div>
			   
			   <div><label style="color: #000000; background-color:#DCDCDC; width:100%; padding-top: 10px; float: left; text-align: center;"  data-toggle="modal"><b>Level of Monitoring:PFC/MoP</b>
				 </label>
			   </div>
			   
			   <div><label style="color: #000000; background-color:#DCDCDC; width:100%; padding-top: 10px; float: left; text-align: center;"  data-toggle="modal"><b>Format:D7</b>
				 </label>
			   </div>
			   
			   
               
               <table style="color:#000000;  padding-bottom: 25px;  width:100%; " class="table table-bordered">
               
               
               <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC; "><b>Name of State:</b>
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large" name="" autocomplete="off" id="stateid" readonly="readonly" type="text" style="width: 100%;" value=${stateName}>
               
               </td>
               </tr>
               
                <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC;"><b>Name of Discom:</b>
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large" name="" autocomplete="off" id="discomid" readonly="readonly" type="text" style="width: 100%;" value=${discomName}>
               </td>
               </tr>
               
               <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC;"><b>Report Month:</b>
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large" name="" autocomplete="off" id="rptmonth" readonly type="text" style="width: 100%;" >
               </td>
               </tr>
                
               <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC;"><b>Period:</b>
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
								<li><a href="#" id="" 
									onclick="exportPDF('sample_1','E-Payment report')">Export to PDF</a></li> 
								<li><a href="#" id="excelExport"
									onclick="exportToExcelMethod('sample_1', 'E-Payment report')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
            <!--   <div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 10px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li> 
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Feeder Count Details')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div> -->
               
              
              <table class="table table-striped table-hover table-bordered" id="sample_1">
				<thead>
				<tr>
				<th>sl.no</th>
				<th>Name Of The Town</th>
				<th>Total Consumers(Nos)</th>
				<th>Total Consumers Using E-Payment(Nos)</th>
				<th>Total Collection(Rs.in Lac)</th>
				<th>Collection Through E-Payment(Rs.in Lac)</th>
				</tr>
				</thead>
				<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
				<tbody id="edata">
				</tbody>
				</table>
				</div>

<script>
function loadSearchAndFilter(param) {
	$('#' + param).dataTable().fnDestroy();
	$('#' + param).dataTable(
			{
				"aLengthMenu" : [ [ 10, 20, 50, 100, -1 ],
						[ 10, 20, 50, 100, "All" ] // change per page values here
				],
				"iDisplayLength" : 10
			});
	jQuery('#' + param + '_wrapper .dataTables_filter input').addClass(
			"form-control input-small"); // modify table search input 
	jQuery('#' + param + '_wrapper .dataTables_length select').addClass(
			"form-control input-xsmall"); // modify table per page dropdown 
	jQuery('#' + param + '_wrapper .dataTables_length select').select2(); // initialize select2 dropdown 
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
  	    var scheme=$("#govt").val();
		var town=$("#town").val();
		var period=$("#period").val();
		var state=$('#stateid').val();
		var discom=$('#discomid').val();
		var month=$('#rptmonth').val();
		var ieperiod=$('#periodMonth').val();
		var townname = $("#town option:selected").map(function(){return this.text}).get().join(',');
		//window.open("./PFCreportD7PDF/"+scheme+"/"+town+"/"+period+"/"+state+"/"+discom+"/"+month+"/"+ieperiod)
		
		window.location.href=("./PFCreportD7PDF?scheme="+scheme+"&town="+town+"&period="+period+"&state="+state+"&discom="+discom+"&month="+month+"&ieperiod="+ieperiod+"&townname="+townname);
}

function exportToExcelMethod()
{
	var scheme=$("#govt").val();
	var town=$("#town").val();
	var period=$("#period").val();
	var ieperiod=$('#periodMonth').val();

	var per=period;
	   var year = per.substring(2, 6);
	   var monthNo = per.substring(0, 2);
	   if(monthNo>=12)
		   {
		   year++;
		   }
	   var monthVal=moment().month(monthNo).format('MMM');
	   var endMonth=monthVal +"-"+year;
		
		window.open("./exportToExcelPFCReportD7Data?scheme="+scheme+"&town="+town+"&period="+period+"&ieperiod="+ieperiod+"&endMonth="+endMonth);
}

</script>


                   
					
					
					
					
					
                                 
 	                   
					 
						
           
          
        							
					
					