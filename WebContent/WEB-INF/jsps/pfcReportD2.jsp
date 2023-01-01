<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script
	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"
	type="text/javascript"></script>
<script
	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js"
	type="text/javascript"></script>
<!-- <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> -->
 <script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>
<script type="text/javascript">
	

						$(".page-content").ready(
								function() {
									App.init();
									TableEditable.init();
									FormComponents.init();
						 			loadSearchAndFilter('sample1');
								 $('#eaId,#eaWiseReport,#pfcReportD2Id').addClass('start active ,selected');
									$('#MDMSideBarContents,#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment').removeClass('start active ,selected'); 
								});
</script>

<!--  <style>
 .pfc{
 background-color: #DCDCDC;
width: 100%;
text-align: center;
padding-top: 10px; 

}
</style> -->



<div class="page-content">
	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>PFC Report D2<span
					style="color: aqua; font-size: 18px; font-weight: bold;"></span>
			</div>
		</div>
		<div class="portlet-body">
		
		  <table style="border-collapse: separate; border-spacing: 8px;"> 
               
               <tr>
               <td style="width:100px;"><b>Select&nbsp;Govt.Scheme:</b>
               <select class="form-control select2me input-large" name="GovtSchemeId" id="GovtSchemeId" onclick="gettowns(this.value)" >
               <option value="">Select Govt scheme</option>
               <option value="IPDS ">IPDS</option>
               </select>
               </td>
               
              
               <td style="width:100px;"><b>Town:</b>
              <select
					class="form-control select2me input-medium" name="TownId" id="TownId" multiple="multiple"
						data-placeholder="Select Town">
					</select>
               </td>
               
               
               <td style="width:100px;"><b>Report Month:</b>
				<input type="text" class="form-control from"  id="PeriodId" style="width:150px;"  
					name="PeriodId" placeholder="Select Report Month" style="cursor: pointer"> 
						<!-- <span class="input-group-btn">
									<button class="btn default" type="button">
										<i class="fa fa-calendar"></i>
									</button>
								</span> -->
						             
             <%--   <select class="form-control select2me input-large" name="PeriodId" id="PeriodId" onchange="getperiodmonth(this.value)">
               <option value="">Select Period</option>
               <c:forEach var="elements" items="${Period}">
            
						<option value="${elements}">${elements}</option>
						</c:forEach> 
               </select> --%>
               </td>
               </tr>
               
               </table>
		
		<div class="row"><br>
               <div class="col-md-2">
                <button class="btn blue pull-left" id="viewrpt" type="button" onclick="view()">View Report</button>
                
                <!-- <a href="#" id="excelExport" class="btn green pull-right" type="button"  onclick="tableToExcel('sample_1', 'Feeder Count')">Export to Excel</a> -->
               </div>
              <!--  <div class="col-md-2">
                <button class="btn blue pull-left" id="viewrpt" type="button" onclick="freezeReporttValidate()">Freeze Report</button>
                
                <a href="#" id="excelExport" class="btn green pull-right" type="button"  onclick="tableToExcel('sample_1', 'Feeder Count')">Export to Excel</a>
               </div> -->
               </div>
			<!-- <div class="row">
				<br>
				<div class="col-md-4">
					<button class="btn blue pull-left" id="" type="button"
						onclick="view()" style="position: relative; left: 240%">View
						Report</button>
					<a href="#" id="excelExport" class="btn blue pull-right"
						type="button"
						onclick="tableToExcel('sample1', 'New Connection Details')"
						style="position: relative; left: 210%">Export to Excel</a>
				</div>
			</div>
 -->


			<br>

			<!-- <div class="pfc" >
<label style="height:40px;padding-top:20px">New Service Connection Report</label>
</div>


<div class="pfc" >
<label style="height:30px;padding-top:20px">Level of Monitoring:PFC/MoP</label>
</div>  -->

			<div>
				<label
					style="color: #000000; background-color: #DCDCDC; height: 40px; width: 100%; padding-top: 10px; text-align: center;"
					data-toggle="modal">New Service Connection Report</label>
			</div>
			<div>
				<label
					style="color: #000000; background-color: #DCDCDC; height: 30px; width: 100%; padding-top: 10px; text-align: center;"
					data-toggle="modal">Level of Monitoring:PFC/MoP </label>
			</div>

			<div>
				<label
					style="color: #000000; background-color: #DCDCDC; height: 30px; width: 100%; padding-top: 10px; text-align: center;"
					data-toggle="modal">Format:D2 </label>
			</div>

			<table class="table table-striped table-hover table-bordered" id="">
				<tr id="State_Id">
					<td
						style="color: #000000; width: 150px; background-color: #DCDCDC; padding-top: 10px;">Name
						of State :</td>
					<td style="background-color: #DCDCDC;"><input
						class="form-control input-large" name="StateId" id="StateId"
						readonly="readonly" value="${States}"
						style="width: 100%; padding-bottom: 10px;" /> <%-- <c:forEach var="type" items="${States}">
								<option value="${type}">${type}</option>
							</c:forEach> --%></td>
				</tr>
				<tr id="Discom_Id">
					<td
						style="color: #000000; width: 150px; background-color: #DCDCDC; padding-top: 10px;">Name
						of Discom :</td>
					<td style="background-color: #DCDCDC;"><input
						class="form-control input-large" name="DiscomId" id="DiscomId"
						readonly="readonly" value="${Discom}"
						style="width: 100%; padding-bottom: 10px;" /> <%-- <c:forEach var="type" items="${Discom}">
								<option value="${type}">${type}</option>
							</c:forEach> --%></td>
				</tr>
				<tr id="Report_Month_Id">
					<td
						style="color: #000000; width: 150px; background-color: #DCDCDC; padding-top: 10px;">Report
						Month :</td>
					<td style="background-color: #DCDCDC;"><input type="text"
						class="form-control input-large" name="ReportMonthId"
						readonly="readonly" id="ReportMonthId"
						style="width: 100%; padding-bottom: 10px;" /></td>
				</tr>
				<tr id="Period_Month_Id">
					<td
						style="color: #000000; width: 150px; background-color: #DCDCDC; padding-top: 10px;">Period:</td>
					<td style="background-color: #DCDCDC;"><input type="text"
						class="form-control input-large" name="PeriodMonthId"
						id="PeriodMonthId" readonly="readonly"
						style="width: 100%; padding-bottom: 10px;" /></td>
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
								onclick="exportPDF('sample1','New Service Connection Report')">Export to PDF</a></li>
								<li><a href="#" id="excelExport"
									onclick="exportToExcelMethod('sample1', 'New Service Connection Report')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
			<table class="table table-striped table-hover table-bordered"
				id="sample1">
				<thead>
					<tr>
						<th>S:No</th>
						<th>Name of Town</th>
						<th>New Connections<br> pending from<br> previous period</th>
						<th>New Connections <br>applied in current<br> period</th>
						<th>Total new Connections<br> Pending for release</th>
						<th>Total Connections<br> released in<br> current period</th>
						<th>Connections Yet<br> to be released</th>
						<th>Connections released <br>within SERC time <br>limit</th>
						<th>Connections released <br>beyond SERC time  <br>limit</th>
						<th>% of Connections released <br>within SERC <br>limit</th>
						<th> Connections released <br>by it  <br>system</th>
					</tr>
				</thead>
				<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
				<tbody id="pfcrptTRId">
				</tbody>
			</table>

		</div>
	</div>
</div>

<script>

	 function gettowns(param)
		{
		 var scheme= $("#GovtSchemeId").val();
			$.ajax({
				url : './showTownByScheme',
		    	type:'GET',
		    	dataType:'json',
		    	data:{
		    		scheme:scheme	
		    	},
				success:function(response)
				{
					var html = '';
	                 html+="<option value='All'><b>ALL Towns</b></option>";;
	                 for (var i = 0; i < response.length; i++) {
							html += "<option  value='"+response[i][1]+"'>" + response[i][1]+"-"+response[i][0]
									+ "</option>";
						}
					$("#TownId").html(html);
					$('#TownId').select2();
				}
			});
		} 
	  
	 
	 function view() {
		 var scheme = $("#GovtSchemeId").val();
		 var TownId= $("#TownId").val();
		 var PeriodId= $("#PeriodId").val();
		 
		
		 
		 
		 if(scheme == '' || scheme == null){
			 bootbox.alert('Please select Govt.Scheme');
			 return false;
		 }
		 if(TownId == '' || TownId == null){
			 bootbox.alert('Please select Town');
			 return false;
		 }
		 
		 if(PeriodId == '' || PeriodId == null){
			 bootbox.alert('Please select Period');
			 return false;
		 }
		 $('#imageee').show();
		 $.ajax({
				url:"./getpfcConnectionData",
				type:"get",
				data:{
					PeriodId:PeriodId,
					TownId:TownId
				},
				success:function(res){
					$('#imageee').hide();
					if(res.length == 0 || res.length == null ){
						bootbox.alert("No data found ");
					}else{
					html="";
					$.each(res,function(i,v){
						html +="<tr>"
						+"<td>"+(i+1)+"</td>"
						+"<td>"+v[11]+"</td>"
						+"<td>"+v[1]+"</td>"
						+"<td>"+v[2]+"</td>"
						+"<td>"+v[3]+"</td>"
						+"<td>"+v[4]+"</td>"
						+"<td>"+v[5]+"</td>"
						+"<td>"+v[6]+"</td>"
						+"<td>"+v[7]+"</td>"
						+"<td>"+v[8]+"</td>"
						+"<td>"+v[9]+"</td>"
						
						"</tr>";
					});
					
					
					clearTabledataContent('sample1');
					$("#pfcrptTRId").html(html);
					loadSearchAndFilter('sample1');
					}
				}
			});
		 getperiodmonth(PeriodId);
		}
	/*  function getPeriod()
		{
			
			$.ajax({
				url:"./getPeriod" ,
				type:'GET',
				success:function(response)
				{
					
					var html = '';
				
	                 html+="<label class='control-label' >Period</label><select id='PeriodId' name='PeriodId' class='select2_category form-control' type='text'><option value=''>All</option>";
	                 for (var i = 0; i < response.length; i++) {
							html += "<option  value='"+response[i]+"'>" + response[i]
									+ "</option>";
						}
					$("#Period_Id").html(html);
					$('#PeriodId').select2();
				}
			});
		}  */
	 
	 function getperiodmonth(param){
		 
		   var year = param.substring(2, 6);
		   var monthNo = param.substring(0, 2);
		   var monthName =  moment().month(monthNo-1).format('MMMM');
		   var firstDate= 01;
		   var lastDate =  new Date(year, monthNo , 0).getDate(); 
		   var fromDate =   monthName+" " +firstDate+ " "+ year;
		   var toDate =  monthName+" " +lastDate+ " "+ year; 
		   var finalDate = fromDate +" "+ "To" +" " + toDate;
		   
		   $("#PeriodMonthId").val(finalDate);
		   
		   var year = param.substring(2, 6);
		   var monthNo = param.substring(0, 2);
		   if(monthNo>=12)
			   {
			   //alert(year);
			   year++;
			   }
		   var monthVal=moment().month(monthNo-1).format('MMM');
		   var endMonth=monthVal +"-"+year;
		 
		   $("#ReportMonthId").val(endMonth); 
		 
	 }
	 
		function freezeReporttValidate(){
			
			var billmonth = $("#PeriodId").val();
			if(billmonth == '' ){
				bootbox.alert('Please select period to freeze data');
			}
			$.ajax({
		    	url : "./validatefreezeD2Report",
				type:'GET',
				data:{billmonth:billmonth},
		    	success:function(res) {
		    		
					if(res == 'dataFreezed'){
						bootbox.alert("Data has been already freezed");
					}else{
						$.ajax({
					    	url : "./freezeD2Report",
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
		 var scheme = $("#GovtSchemeId").val();
		 var town= $("#TownId").val();
		 var period= $("#PeriodId").val();
  		 var state=$('#StateId').val();
  		 var discom=$('#DiscomId').val();
  		 var month=$('#ReportMonthId').val();
  		 var ieperiod=$('#PeriodMonthId').val();
  		 var townname = $("#TownId option:selected").map(function(){return this.text}).get().join(',');
  		//window.open("./PFCreportD2PDF/"+scheme+"/"+town+"/"+period+"/"+state+"/"+discom+"/"+month+"/"+ieperiod)
  		
  		window.location.href=("./PFCreportD2PDF?scheme="+scheme+"&town="+town+"&period="+period+"&state="+state+"&discom="+discom+"&month="+month+"&ieperiod="+ieperiod+"&townname="+townname);
	}

	function exportToExcelMethod()
	{
		 var scheme = $("#GovtSchemeId").val();
		 var town= $("#TownId").val();
		 var period= $("#PeriodId").val();
		 var ieperiod=$('#PeriodMonthId').val();

		 var per=period;
		   var year = per.substring(2, 6);
		   var monthNo = per.substring(0, 2);
		   if(monthNo>=12)
			   {
			   year++;
			   }
		   var monthVal=moment().month(monthNo).format('MMM');
		   var endMonth=monthVal +"-"+year;
  		
  		window.open("./exportToExcelPFCReportD2Data?scheme="+scheme+"&town="+town+"&period="+period+"&ieperiod="+ieperiod+"&endMonth="+endMonth);
	}
	
</script>