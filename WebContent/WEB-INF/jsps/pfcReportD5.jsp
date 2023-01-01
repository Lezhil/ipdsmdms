
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
 			loadSearchAndFilter('sample_1');
		 $('#eaId,#eaWiseReport,#pfcReportD5').addClass('start active ,selected');
			$('#MDMSideBarContents,#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment').removeClass('start active ,selected'); 
		});
		
		
    function showtown(param)
  {
      var scheme=$("#scheme").val();
     
	  $.ajax({
		       type:"GET",
		       url:'./showTownByScheme',
		       dataType:'json',
		       data:{scheme:scheme},
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
    

    function view() {
    	
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
		$.ajax({
			
			url:'./pfcd5tableDetails',
			type : "GET",
			data:{
				period:period,
				town:town
			},
			
			success:function(res){
				$('#imageee').hide();
				if (res.length==0 || res.length == null) {
					bootbox.alert ("No data found for this selected town and period");
				} else {
					html="";
					$.each(res,function(i,v){
						html+="<tr><td>"+(i+1)+"</td><td>"+v[6]+"</td><td>"+v[1]+"</td><td>"+v[2]+"</td><td>"+v[3]+"</td><td>"+v[4]+"</td></tr>";
					});
					
					$('#sample_1').dataTable().fnClearTable();
					clearTabledataContent('sample_1');
					$("#feederstatusid").html(html);

				}
				
			},
		complete : function() {
		   	loadSearchAndFilter('sample_1');
		   }
		});

	}
    
    
    
    function freezeReporttValidate(){
		
		var billmonth = $("#period").val();
		if(billmonth == '' ){
			bootbox.alert('Please select period to freeze data');
		}
		$.ajax({
	    	url : "./validatefreezeD5Report",
			type:'GET',
			data:{billmonth:billmonth},
	    	success:function(res) {
	    		
				if(res == 'dataFreezed'){
					bootbox.alert("Data has been already freezed");
				}else{
					$.ajax({
				    	url : "./freezeD5Report",
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
                   <i class="fa fa-bars"></i>PFC Report-D5
                   </div>
                   </div>
                <div class="portlet-body">
                
       <!-- <table class="table table-striped table-hover table-bordered "> -->
       <table style="border-collapse: separate; border-spacing: 8px;">  
               
               <tr>
               <td style="width:100px;"><b>Govt.Scheme :</b>
               <select class="form-control input-large select2me" name="scheme" id="scheme" onchange="showtown(this.value);" >
               <option value="">Select Scheme</option>
               <!-- <option value="RAPDRP">RAPDRP</option> -->
               <option value="IPDS">IPDS</option>
               </select>
               </td>
               
               <td  style="width:100px"><b>Town :</b>
               <select class="form-control input-large select2me" name="town" id="town" multiple="multiple" >
               <option value="">Select Town</option>  
               </select>
               </td>
               
               <td style="width:100px"><b>Report Month:</b> 
             
             	<input type="text" class="form-control from"  id="period" style="width:200px;"  onchange="dateformat(this.value);"
										name="period" placeholder="Select Report Month" style="cursor: pointer">
             <%--   <select class="form-control input-large select2me" name="period"  id="period" onchange="dateformat(this.value);">
               <option value="">Select Period</option>
               <c:forEach var="type" items="${periodList}">
			   <option value="${type}">${type}</option>
			   </c:forEach>
               </select>  --%>
               </td>
               </tr>
 
 
 				
       </table>
              <div class="row"><br>
               <div class="col-md-2">
                <button class="btn blue pull-left" id="viewrpt" type="button" onclick="view('sample_1','Table details')">View Report</button>
                
                <!-- <a href="#" id="excelExport" class="btn green pull-right" type="button"  onclick="tableToExcel('sample_1', 'Feeder Count')">Export to Excel</a> -->
               </div>
             <!--   <div class="col-md-2">
                <button class="btn blue pull-left" id="viewrpt" type="button" onclick="freezeReporttValidate()">Freeze Report</button>
                
                <a href="#" id="excelExport" class="btn green pull-right" type="button"  onclick="tableToExcel('sample_1', 'Feeder Count')">Export to Excel</a>
               </div> -->
               </div>
              <%--  <div class="row"><br>
               <div class="col-md-4">
               <c:if test = "${officeType eq 'discom' || userType eq 'ADMIN'}">
               <button class="btn green pull-left" id="" type="button" onclick="view('sample_1','Table details')" style="position:relative; left:240%">View Report</button></c:if>
               <a href="#" id="excelExport" class="btn green pull-right" type="button"  onclick="tableToExcel('sample_1', 'Feeder Status')" style="position:relative; left:200%">Export to Excel</a>
               </div>
               </div> --%>
               <br>
            
              <div><label style="color: #000000; background-color:#DCDCDC; width:100%; padding-top: 20px; float: left; text-align: center;"  data-toggle="modal">SAIDI-SAIF I Report
				 </label>
			   </div>
			   
			   <div><label style="color: #000000; background-color:#DCDCDC; width:100%; padding-top: 10px; float: left; text-align: center;"  data-toggle="modal">Level of Monitoring:PFC/MOP
				 </label>
			   </div>
			   
			   <div><label style="color: #000000; background-color:#DCDCDC; width:100%; padding-top: 10px; float: left; text-align: center;"  data-toggle="modal">Format:D5
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
               <td style="color: #000000; width:150px; background-color:#DCDCDC;">Period:
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large" name="periodmonth" id="periodmonth" readonly="readonly" style="width: 100%; padding-bottom: 10px;" >
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
									onclick="exportPDF('sample_1','SAIDI-SAIF I Report')">Export to PDF</a></li>
								<li><a href="#" id="excelExport"
									onclick="exportToExcelMethod('sample_1', 'SAIDI-SAIF I Report')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
       
 			<!-- <div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 10px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li> 
								<li><a href="#" id="excelExport"
									onclick="tableToExcelNew('sample_1', 'SAIDI-SAIF I Report')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div> -->
    
                       <table class="table table-striped table-hover table-bordered" id="sample_1">
                              <thead>
                                     <tr>
                                         <th>Sl.No</th> 
                                         <th>Name of Town</th>
                                         <th>Name of Feeder</th>
                                         <th>Number of Consumers</th>
                                         <th>Number of Outages (Nos.)</th>
                                         <th>Duration of Outages(Sec)</th>
                                     </tr>
                              </thead>
                              <div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
                              <tbody id="feederstatusid">
                                               
                              </tbody>
                       </table>
            
</div>
			   </div>
               </div>	
               
 <script>
 
 function dateformat(param)
 {
	   var year = param.substring(0, 4);
	   var monthNo = param.substring(4, 6);
	   var monthName =  moment().month(monthNo-2).format('MMM');
	   var firstDate= 01;
	   var lastDate =  new Date(year, monthNo , 0).getDate(); 
	   var fromDate =   monthName+" " +firstDate+ " "+ year;
	   var toDate =  monthName+" " +lastDate+ " "+ year; 
	   var finalDate = fromDate +" "+ "to" +" " + toDate;
	   
	   $("#periodmonth").val(finalDate);
	   
	   var year = param.substring(0, 4);
	   var monthNo = param.substring(4, 6);
	   if(monthNo>=12)
	   {
	   year++;
	   }
	   var monthVal=moment().month(monthNo-1).format('MMM');
	   var endMonth=monthVal +"-"+year
	   
       $("#repmonth").val(endMonth);
 }
 
 </script>              
	<script>
	var date=new Date();
	var year=date.getFullYear();
	var month=date.getMonth();

	$('.from').datepicker
	({
	    format: "yyyymm",
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
	    		//window.open("./PFCreportD5PDF/"+scheme+"/"+town+"/"+period+"/"+state+"/"+discom+"/"+month+"/"+ieperiod)
	    		
	    		window.location.href=("./PFCreportD5PDF?scheme="+scheme+"&town="+town+"&period="+period+"&state="+state+"&discom="+discom+"&month="+month+"&ieperiod="+ieperiod+"&townname="+townname);
	    }

	 function exportToExcelMethod(para)
		{
		// alert(para)
		 var scheme=$("#scheme").val();
 		 var town=$("#town").val();
 		 var period=$("#period").val();
 		 var ieperiod=$('#periodmonth').val();
 		var endMonth=$('#repmonth').val();
 		 var per=period;
		   var year = per.substring(0, 4);
		   var monthNo = per.substring(4, 2);
		   if(monthNo>=12)
			   {
			   year++;
			   }
		  // var monthVal=moment().month(monthNo).format('MMM');
		  // var endMonth=monthVal +"-"+year;
		  // alert(endMonth)
		   //alert("...."+endMonth)
	  		
	  	window.open("./exportToExcelPFCReportD5Data?scheme="+scheme+"&town="+town+"&period="+period+"&ieperiod="+ieperiod+"&endMonth="+endMonth);
		}
			

</script>