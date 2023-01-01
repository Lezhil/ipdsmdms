<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="<c:url value='/resources/assets/scripts/filesaver.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/assets/scripts/image.min.js'/>" type="text/javascript"></script>

<style>
.table-toolbar {
	margin-bottom: 4px;
}

.tabbable-custom.tabbable-full-width .nav-tabs>li>a {
	color: #424242;
	font-size: 14px !important;
	padding: 9px 15px;
}

.divi {
	width: 350px;
	padding: 20px;
	border: 2px solid blue;
	margin: 8px;
}
</style>

<!-- <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> -->
<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>


<script type="text/javascript">
    var selectedoption=null;
	var meterNum = null;
	var frmDate = null;
	var tDate = null;
	var radioVal = null;
	var parametersradio = null;
	$(".page-content")
			.ready(function() {
						console.log('${officeCode}' + " " + '${officeType}');
						// alert('${officeCode}');
						var session = '';
						session = "${officeType}";
						if (session == "circle") {
							$("#zone").hide();
							$("#zones").hide();
							//write javascript function 
							//getCircle('${officeCode}');
							//$("#circleTd").hide();
						} else if (session == "divison") {

							//$("#")
						}
						$('#LFzone').val('${zone}').trigger('change');
						//alert(LFzone);
						//showCircle('${zone}');
						//$('#LFcircle').val('${circle}').trigger('change');
						//alert();
						$('#fromDate').val('${from}');
						$('#toDate').val('${to}');
						var from='${from}';
						//alert(from);
						if(from != null && from !=''){
							getMinMaxData();
						}
						/* var radio = "${radioVal}";
						if (radio == 'kno') {
							$('#meterNum').val('${mtrno}');
							$("#kno_radio").click();

						} else {
							$('#meterNum').val('${accno}');
							$("#meterno_radio").click();
						} */

						//meterNumber = "${accno}";
						//console.log("meter number----" + meterNumber);
						var radio = "${radioVal}";
						if (radio == 'kno') {
							$('#meterNum').val('${mtrno}');
							$("#kno_radio").click();

						} else {
							$('#meterNum').val('${mtrno}');
							$("#meterno_radio").click();
						}

						frmDate = '${fromDate}';
						tDate = '${toDate}';
						mtrNum = '${mtrno}';
						radioVal = $("input[name='optionsRadios']:checked").val();
						$('input:checkbox').removeAttr('checked');

						
						$('.datepicker').datepicker({
				            format: 'yyyy-mm-dd',
				            autoclose:true,
				            endDate: "today",

				        }).on('changeDate', function (ev) {
				                $(this).datepicker('hide');
				            });


				        $('.datepicker').keyup(function () {
				            if (this.value.match(/[^0-9]/g)) {
				                this.value = this.value.replace(/[^0-9^-]/g, '');
				            }
				        });
						App.init();
						TableEditable.init();
						FormComponents.init();
						$('#MDMSideBarContents,#360MeterDataViewID,#getMinMaxData').addClass('start active ,selected');
					/* 	$('#360d-view').addClass('start active ,selected'); */
						$("#dash-board,#user-location,#MIS-Reports,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn")
								.removeClass('start active ,selected');
					});
</script>

<script type="text/javascript">

	function setRadioVal(val) {
		$("#radioVal").val(val);
		if (val == 'meterno') {
			$("#meterNum").attr("placeholder", "Enter Meter No");
		} else {
			$("#meterNum").attr("placeholder", "Enter K No");
		}
	}
	/* function setParamVal(val) {
		$("#parametersradio").val(val);
		alert(val);
		if (val == 'meterno') {
			$("#paramValue").attr("placeholder", "Enter Single Parameter");
		} else {
			$("#paramValue").attr("placeholder", "Enter Multiple Parameters");
		}
	} */
	/* var s=function ch(){
		 var favorite = [];

         $.each($("input[name='checkboxValues']:checked"), function(){            

             favorite.push($(this).val());

         });
         return favorite.join(", ");
         alert("My favourite sports are: " + favorite.join(", "));
	} */
	
	function getMinMaxData(){
		
		$('#tabularViewLi').addClass('start active ,selected');
		$("#graphicalViewLi").removeClass('start active ,selected');
		$('#tab_1_2').addClass('active');
		$("#tab_1_3").removeClass('active');
		
		var dataPoints = [];
		var maxData = [];
		const date1 = new Date(frmDate);
		const date2 = new Date(tDate);
		const diffTime = Math.abs(date2.getTime() - date1.getTime());
		const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
		
		 radioVal = $("input[name='optionsRadios']:checked").val();
		 //var x = document.getElementById("fromDate").value; 
		 region=$('#LFzone').val();
		 circle=$('#LFcircle').val();
		 town=$('#LFtown').val();
		 frmDate=$('#fromDate').val();
		 tDate = $("#toDate").val(); 
		 meterNum=$("#meterNum").val();
		 selectedoption=$("#selectoptions").val();
		 //alert(radioVal);
		 if(region=="")
			{
			bootbox.alert("Please Select Region");
			return false;
		    }
		 /*  if(circle=="")
			{
			bootbox.alert("Please Select Circle");
			return false;
		    } 
		 if(town=="")
			{
			bootbox.alert("Please Select Town");
			return false;
			}
		   if(meterNum=="")
			{
			bootbox.alert("Please Enter Meter Number");
			return false;
			}   */
		 if(frmDate=="")
			{
			bootbox.alert("Please Select from Date");
			return false;
			}
		 if(tDate=="")
			{
			bootbox.alert("Please Select To Date");
			return false;
			}
		 if(new Date(frmDate)>new Date(tDate))
			{
				bootbox.alert("Please Select Correct Date Range");
				return false;
			}
		
			if(diffDays>31){
				bootbox.alert("Difference Between Date Should be less than 31 days ");
				return false;
			}
		
		 
		 
		 $('#minParameters').empty();
		 $('#maxParameters').empty();
		 $('#avgParameters').empty();
		 var checkBox = document.getElementById("mf");
		// alert(checkBox.checked);
		 var tourl="";
		
		 if(!"meterno".localeCompare(radioVal)){
			 if(checkBox.checked){
				 tourl='./getMinMaxAvgDataByMtrNoandMf/'+meterNum+'/'+frmDate+'/'+tDate;
				
			 }else{
				 tourl='./getMinMaxAvgDataByMtrNo/'+meterNum+'/'+frmDate+'/'+tDate;
				
			 }
			 
		 }
		 else{
			 if(checkBox.checked){
				 tourl='./getMinMaxAvgDataByKNoandMf/'+meterNum+'/'+frmDate+'/'+tDate;
			 }
			 else{
				 tourl='./getMinMaxAvgDataByKNo/'+meterNum+'/'+frmDate+'/'+tDate;
			 }
			
		 }
	 
		 /* var parametersRadio=$("input[name='parametersradio']:checked").val();
		 var parameter=$('#single').val();
          var favorite = [];

         $.each($("input[name='checkboxValues']:checked"), function(){            

             favorite.push($(this).val());

         });
         var s= favorite.join(", ");
         var multipleRadioVal=$("input[name='multiparamRadios']:checked").val(); 
         var tourl="";
         if("meterno".localeCompare(radioVal)){
        	
         }minParameters*/
		$.ajax({
			url:tourl,
			type:'GET',
			success:function(response){
				if(response.length == 0 || response.length == null ){
					bootbox.alert("No data for this meter number "+meterNum);
				}
				else{
				var minimumParameters=response[0];
				var maximumParameters=response[1];
				var averageParameters=response[2];
				//alert(minimumParameters+"sdd"+maximumParameters +maximumParameters.length);
				var htmlq='';
				var html='';
				var htmls='';
				
				for(var i=0;i<minimumParameters.length;i++){
					var element = minimumParameters[i];
				
			
		htmlq +="<tr>"
				+"<td>"+(element[1]==null?"":element[1])+"</td>"
				+"<td>"+(element[2]==null?"":element[2])+"</td>"				
				+"<td>"+(element[3]==null?"":element[3])+"</td>"
				+"<td>"+(element[4]==null?"":element[4])+"</td>"
				+"<td>"+(element[5]==null?"":element[5])+"</td>"
				+"<td>"+(element[6]==null?"":element[6])+"</td>"
				+"<td>"+(element[7]==null?"":element[7])+"</td>"
				+"<td>"+(element[8]==null?"":element[8])+"</td>"
				+"<td>"+(element[9]==null?"":element[9])+"</td>"
				+"<td>"+(element[10]==null?"":element[10])+"</td>"
				+"<td>"+(element[11]==null?"":element[11])+"</td>";
				
				
				//+"</tr>";
				}
				for(var i=0;i<maximumParameters.length;i++){
					var element = maximumParameters[i];
				
				html +="<tr>"
					+"<td>"+(element[1]==null?"":element[1])+"</td>"
					+"<td>"+(element[2]==null?"":element[2])+"</td>"
					+"<td>"+(element[3]==null?"":element[3])+"</td>"
					+"<td>"+(element[4]==null?"":element[4])+"</td>"
					+"<td>"+(element[5]==null?"":element[5])+"</td>"
					+"<td>"+(element[6]==null?"":element[6])+"</td>"
					+"<td>"+(element[7]==null?"":element[7])+"</td>"
					+"<td>"+(element[8]==null?"":element[8])+"</td>"
					+"<td>"+(element[9]==null?"":element[9])+"</td>"
					+"<td>"+(element[10]==null?"":element[10])+"</td>"
					+"<td>"+(element[11]==null?"":element[11])+"</td>";
				
				//+"</tr>";
				}
				for(var i=0;i<averageParameters.length;i++){
					var element = averageParameters[i];
				htmls +="<tr>"
					+"<td>"+(element[1]==null?"":element[1])+"</td>"
					+"<td>"+(element[2]==null?"":(element[2]).toFixed(3))+"</td>"
					+"<td>"+(element[3]==null?"":(element[3]).toFixed(3))+"</td>"
					+"<td>"+(element[4]==null?"":(element[4]).toFixed(3))+"</td>"
					+"<td>"+(element[5]==null?"":(element[5]).toFixed(3))+"</td>"
					+"<td>"+(element[6]==null?"":(element[6]).toFixed(3))+"</td>"
					+"<td>"+(element[7]==null?"":(element[7]).toFixed(3))+"</td>"
					+"<td>"+(element[8]==null?"":(element[8]).toFixed(3))+"</td>"
					+"<td>"+(element[9]==null?"":(element[9]).toFixed(3))+"</td>"
					+"<td>"+(element[10]==null?"":(element[10]).toFixed(3))+"</td>"
					+"<td>"+(element[11]==null?"":(element[11]).toFixed(3))+"</td>";
				
				//+"</tr>";
				}
				$('#sample_1').dataTable().fnClearTable();
				$('#minParameters').html(htmlq);
				loadSearchAndFilter('sample_1');
				$('#sample_2').dataTable().fnClearTable();
				$('#maxParameters').html(html);
				loadSearchAndFilter('sample_2');
				$('#sample_3').dataTable().fnClearTable();
				$('#avgParameters').html(htmls);
				loadSearchAndFilter('sample_3');
				$("#parameters").show();
			
				}
			
			}
			
	
		});
		
		
	}
	
	function locationDetails(){
		var tourl="";
		radioVal = $("input[name='optionsRadios']:checked").val();
		
		frmDate=$('#fromDate').val();
		tDate = $("#toDate").val(); 
		meterNum=$("#meterNum").val();
		console.log(radioVal+tDate+meterNum);
		 if(!"meterno".localeCompare(radioVal)){
			
				tourl='./getConsumerData/'+meterNum;
		 }
		 else{
			 tourl='./getConsumerDatabyKno/'+meterNum;
		 }
		

		$.ajax({
			url:tourl,
			type:'GET',
			success:function(response){
				//alert(response[0][2]);
				var html='';
				for(var i=0; i<response.length; i++)
				{ var data=response[i];
					console.log("date....."+response[i][0]);
			    	
			    	html += '<tr><th>circle</th><td>'+data.circle+'<td><tr>'
			    		+'<tr><th>Consumer Name</th><td>'+data.customer_name+'<td><tr>'
			    		+'<tr><th>Division</th><td>'+data.division+'<td><tr>'
			    		+'<tr><th>subdivision</th><td>'+data.subdivision+'<td><tr>'
			    		+'<tr><th>Consumer Mobile</th><td>'+(data.customer_mobile== null?"":data.customer_mobile)+'<td><tr>'
			    		+'<tr><th>Consumer Address</th><td>'+data.customer_address+'<td><tr>'	
			    		+'<tr><th>Account Number</th><td>'+data.accno+'<td><tr>'
			    		+'<tr><th>Meter Number</th><td>'+data.mtrno+'<td><tr>'
			    		+'<tr><th>k Number</th><td>'+data.kno+'<td><tr>'
			    		+'<tr><th>Tarrif Code</th><td>'+data.tariffcode+'<td><tr>'
			    		+'<tr><th>Supply Type</th><td>'+data.fdrcategory+'<td><tr>'
			    		/* +'<tr><th>Phase</th><td>'+data.phase+'<td><tr>' */;
				} 
				$("#consumerDetails").html(html);
			}
			
		
	});
		
	}
	function graphicalView(){
		
		var dataPoints = [];
		var maxData = [];
		var avgData = [];
		var minCurrentIr = [];
		var maxCurrentIr = [];
		var avgCurrentIr =[];
		var minVoltVr = [];
		var maxVoltVr = [];
		var avgVoltVr = [];
		var minBlockKwh = [];
		var maxBlockKwh = [];
		var avgBlockKwh = [];
		
		
		 radioVal = $("input[name='optionsRadios']:checked").val();
		 //var x = document.getElementById("fromDate").value; 
		 frmDate=$('#fromDate').val();
		 tDate = $("#toDate").val(); 
		 meterNum=$("#meterNum").val();
		 selectedoption=$("#selectoptions").val();
		 //alert(radioVal+frmDate+tDate+meterNum);
		 var tourl="";
		 var checkBox = document.getElementById("mf1");
		// alert(checkBox.checked);
		 if(!"meterno".localeCompare(radioVal)){
			 if(checkBox.checked){
				 tourl='./getMinMaxAvgDataByMtrNoandMf/'+meterNum+'/'+frmDate+'/'+tDate;
				
			 }else{
				 tourl='./getMinMaxAvgDataByMtrNo/'+meterNum+'/'+frmDate+'/'+tDate;
				
			 }
			 
		 }
		 else{
			 if(checkBox.checked){
				 tourl='./getMinMaxAvgDataByKNoandMf/'+meterNum+'/'+frmDate+'/'+tDate;
			 }
			 else{
				 tourl='./getMinMaxAvgDataByKNo/'+meterNum+'/'+frmDate+'/'+tDate;
			 }
		 }
		 
		 $.ajax({
				url:tourl,
				type:'GET',
				success:function(response){
					if(response.length == 0 || response.length == null || response == ",," ){
						bootbox.alert("No data for this meter number "+meterNum);
					}
					else{
					var minimumParameters=response[0];
					var maximumParameters=response[1];
					var averageParameters=response[2];
					//alert(minimumParameters+"sdd"+maximumParameters +maximumParameters.length);
					for(var i=0;i<minimumParameters.length;i++){
						var resp = minimumParameters[i];
						dataPoints.push({
							x : new Date(resp[1]),
							y : (resp[10])
						});
						minCurrentIr.push({
							x : new Date(resp[1]),
							y : (resp[12])
						});
						minVoltVr.push({
							x : new Date(resp[1]),
							y : (resp[13])
						});
						minBlockKwh.push({
							x : new Date(resp[1]),
							y : (resp[11])
						});
					}
					for(var i=0;i<maximumParameters.length;i++){
						var respo = maximumParameters[i];
						maxData.push({
							x : new Date(respo[1]),
							y : (respo[10])
						});
						maxCurrentIr.push({
							x : new Date(respo[1]),
							y : (respo[12])
						});
						maxVoltVr.push({
							x : new Date(respo[1]),
							y : (respo[13])
						});
						maxBlockKwh.push({
							x : new Date(respo[1]),
							y : (respo[11])
						});
						//alert(maxData);
					}
					for(var i=0;i<averageParameters.length;i++){
						var data = averageParameters[i];
						//alert(data[1]);
						avgData.push({
							x : new Date(data[1]),
							y : (data[10])
						});
						avgCurrentIr.push({
							x : new Date(data[1]),
							y : (data[12])
						});
						avgVoltVr.push({
							x : new Date(data[1]),
							y : (data[13])
						});
						avgBlockKwh.push({
							x : new Date(data[1]),
							y : (data[11])
						});
						
					}
					
					if(selectedoption=="0"){
					$("#chartContainer1")
					$("#chartContainer2")
					$("#chartContainerVr")
					$("#chartContainerBkwh")
					}else if(selectedoption=="chartContainer1"){
					$("#chartContainer1").show();
					$("#chartContainer2").hide();
					$("#chartContainerVr").hide();
					$("#chartContainerBkwh").hide();
					}else if(selectedoption=="chartContainer2"){
						$("#chartContainer1").hide();
						$("#chartContainer2").show();
						$("#chartContainerVr").hide();
						$("#chartContainerBkwh").hide();
					
					}else if(selectedoption=="chartContainerVr"){
						$("#chartContainer1").hide();
						$("#chartContainer2").hide();
						$("#chartContainerVr").show();
						$("#chartContainerBkwh").hide();
					
					}else if(selectedoption=="chartContainerBkwh"){
						$("#chartContainer1").hide();
						$("#chartContainer2").hide();
						$("#chartContainerVr").hide();
						$("#chartContainerBkwh").show();
					}
						var chart = new CanvasJS.Chart("chartContainer1", {
							animationEnabled: true,

					        chart: {
					             spacingbottom: 100
					        }, 
							
							title:{
								text: "Daily Parameters Kvah"
							},
							axisX: {    
								valueFormatString: "DD MMM,YY",
								interval: 1,
								intervalType: "day",
								//offset: 20
								
							},
							axisY: {
								title: "Kvah",
								includeZero: false,
								suffix: " ",
								
							},
							legend:{
								cursor: "pointer",
								fontSize: 16,
								itemclick: toggleDataSeries,
								 gapSize: 1
							},
							 plotOptions: {
							     align: 'left',
							     
							     
							    },
							toolTip:{
								shared: true,
								
							},
							data: [{
								name: "Min Kvah",
								type: "spline",
								yValueFormatString: "",
								showInLegend: true,
								dataPoints: dataPoints,
								
							},
							{
								type: "line",
								name: "Max Kvah",
								color: "#C24642",
								axisYIndex: 0,
								showInLegend: true,
								dataPoints: maxData
							 },
							{
								type: "line",
								name: "Avg Kvah",
								color: "#7F6084",
								axisYIndex: 0,
								//axisYType: "secondary",
								showInLegend: true,
								dataPoints: avgData 
							}
							] 
						});
								
							
						chart.render();
						var chartIr = new CanvasJS.Chart("chartContainer2", {
							animationEnabled: true,

							chart: {
					             spacingbottom: 100000
					        },
							title:{
								text: "Daily Parameters Current"
							},
							axisX: {
								valueFormatString: "DD MMM,YY",
								interval: 1,
								intervalType: "day"
							},
							axisY: {
								title: "Current",
								includeZero: false,
								suffix: " "
							},
							legend:{
								cursor: "pointer",
								fontSize: 16,
								itemclick: toggleDataSeries
							},
							toolTip:{
								shared: true
							},
							data: [{
								name: "Min Current",
								type: "spline",
								yValueFormatString: "",
								showInLegend: true,
								dataPoints: minCurrentIr
							},
							{
								type: "line",
								name: "Max Current",
								color: "#C24642",
								axisYIndex: 0,
								showInLegend: true,
								dataPoints: maxCurrentIr
							 },
							{
								type: "line",
								name: "Avg Current",
								color: "#7F6084",
								axisYIndex: 0,
								//axisYType: "secondary",
								showInLegend: true,
								dataPoints: avgCurrentIr 
							}
							] 
						});
							chartIr.render();
							var chartVr = new CanvasJS.Chart("chartContainerVr", {
								animationEnabled: true,
								title:{
									text: "Daily Parameters Voltage"
								},
								
								axisX: {
									valueFormatString: "DD MMM,YY",
									interval: 1,
									intervalType: "day"
								},
								axisY: {
									title: "Voltage",
									includeZero: false,
									suffix: " "
								},
								legend:{
									cursor: "pointer",
									fontSize: 16,
									itemclick: toggleDataSeries
								},
								toolTip:{
									shared: true
								},
								data: [{
									name: "Min Voltage",
									type: "spline",
									yValueFormatString: "",
									showInLegend: true,
									dataPoints: minVoltVr
								},
								{
									type: "line",
									name: "Max Voltage",
									color: "#C24642",
									axisYIndex: 0,
									showInLegend: true,
									dataPoints: maxVoltVr
								 },
								{
									type: "line",
									name: "Avg Voltage",
									color: "#7F6084",
									axisYIndex: 0,
									//axisYType: "secondary",
									showInLegend: true,
									dataPoints: avgVoltVr 
								}
								] 
							});
							chartVr.render();
							var chartBKwh = new CanvasJS.Chart("chartContainerBkwh", {
								animationEnabled: true,
								title:{
									text: "Daily Parameters Block Kwh"
								},
								axisX: {
									valueFormatString: "DD MMM,YY",
									interval: 1,
									intervalType: "day"
								},
								axisY: {
									title: "kwh",
									includeZero: false,
									suffix: " "
								},
								legend:{
									cursor: "pointer",
									fontSize: 16,
									itemclick: toggleDataSeries
								},
								toolTip:{
									shared: true
								},
								data: [{
									name: "Min kwh",
									type: "spline",
									yValueFormatString: "",
									showInLegend: true,
									dataPoints: minBlockKwh
								},
								{
									type: "line",
									name: "Max kwh",
									color: "#C24642",
									axisYIndex: 0,
									showInLegend: true,
									dataPoints: maxBlockKwh
								 },
								{
									type: "line",
									name: "Avg kwh",
									color: "#7F6084",
									axisYIndex: 0,
									//axisYType: "secondary",
									showInLegend: true,
									dataPoints: avgBlockKwh 
								}
								] 
							});
							chartBKwh.render();
							//document.getElementById("printChart").addEventListener("click",function(){
								//var a=document.getElementById("tab_1_3");
								//a.print()
						    	//chart.print();
						    	/* chartIr.print();
						    	chartVr.print();
						    	chartBKwh.print(); */
						    //}); 
						function toggleDataSeries(e) {
							if (typeof (e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
								e.dataSeries.visible = false;
							} else {
								e.dataSeries.visible = true;
							}
							e.chart.render();
						}
					}
				
				}
				
		
			});
			
		
		 
	}
	function getDataWithMf(){
		var todourl;
		
		 radioVal = $("input[name='optionsRadios']:checked").val();
		 //var x = document.getElementById("fromDate").value; 
		 frmDate=$('#fromDate').val();
		 tDate = $("#toDate").val(); 
		 meterNum=$("#meterNum").val();
		 $('#minParameters').empty();
		 $('#maxParameters').empty();
		 $('#avgParameters').empty();
		 var checkBox = document.getElementById("mf");
		// alert(checkBox.checked);
		 if(checkBox.checked){
			 todourl="";
		 }
		 else{
			 todourl='./getMinMaxAvgDataByMtrNo/'+meterNum+'/'+frmDate+'/'+tDate;
		 }
		 $.ajax({
				url:tourl,
				type:'GET',
				success:function(response){
					if(response.length == 0 || response.length == null ){
						bootbox.alert("No data for this meter number "+meterNum);
					}
					else{
					var minimumParameters=response[0];
					var maximumParameters=response[1];
					var averageParameters=response[2];
					//alert(minimumParameters+"sdd"+maximumParameters +maximumParameters.length);
					var htmlq='';
					var html='';
					var htmls='';
					for(var i=0;i<minimumParameters.length;i++){
						var resp = minimumParameters[i];
						dataPoints.push({
							x : new Date(resp[1]),
							y : ((parseInt(resp[10])))
						});
						//alert(dataPoints);
					}
					for(var i=0;i<maximumParameters.length;i++){
						var respo = maximumParameters[i];
						maxData.push({
							x : new Date(respo[1]),
							y : ((parseInt(respo[10])))
						});
						//alert(maxData);
					}
					for(var i=0;i<minimumParameters.length;i++){
						var element = minimumParameters[i];
					
					htmlq +="<tr>"
					+"<td>"+(element[1]==null?"":element[1])+"</td>"
					+"<td>"+(element[2]==null?"":element[2])+"</td>"
					+"<td>"+(element[3]==null?"":element[3])+"</td>"
					+"<td>"+(element[4]==null?"":element[4])+"</td>"
					+"<td>"+(element[5]==null?"":element[5])+"</td>"
					+"<td>"+(element[6]==null?"":element[6])+"</td>"
					+"<td>"+(element[7]==null?"":element[7])+"</td>"
					+"<td>"+(element[8]==null?"":element[8])+"</td>"
					+"<td>"+(element[9]==null?"":element[9])+"</td>"
					+"<td>"+(element[10]==null?"":element[10])+"</td>"
					+"<td>"+(element[11]==null?"":element[11])+"</td>";
					
					//+"</tr>";
					}
					for(var i=0;i<maximumParameters.length;i++){
						var element = maximumParameters[i];
					html +="<tr>"
						+"<td>"+(element[1]==null?"":element[1])+"</td>"
						+"<td>"+(element[2]==null?"":element[2])+"</td>"
						+"<td>"+(element[3]==null?"":element[3])+"</td>"
						+"<td>"+(element[4]==null?"":element[4])+"</td>"
						+"<td>"+(element[5]==null?"":element[5])+"</td>"
						+"<td>"+(element[6]==null?"":element[6])+"</td>"
						+"<td>"+(element[7]==null?"":element[7])+"</td>"
						+"<td>"+(element[8]==null?"":element[8])+"</td>"
						+"<td>"+(element[9]==null?"":element[9])+"</td>"
						+"<td>"+parseFloat(Math.round(element[10])).toFixed(2)+"</td>"
						+"<td>"+(element[11]==null?"":element[11])+"</td>";
					
					//+"</tr>";
					}
					for(var i=0;i<averageParameters.length;i++){
						var element = averageParameters[i];
					htmls +="<tr>"
						+"<td>"+(element[1]==null?"":element[1])+"</td>"
						+"<td>"+(element[2]==null?"":element[2])+"</td>"
						+"<td>"+(element[3]==null?"":element[3])+"</td>"
						+"<td>"+(element[4]==null?"":element[4])+"</td>"
						+"<td>"+(element[5]==null?"":element[5])+"</td>"
						+"<td>"+(element[6]==null?"":element[6])+"</td>"
						+"<td>"+(element[7]==null?"":element[7])+"</td>"
						+"<td>"+(element[8]==null?"":element[8])+"</td>"
						+"<td>"+(element[9]==null?"":element[9])+"</td>"
						+"<td>"+parseFloat(Math.round(element[10])).toFixed(2)+"</td>"
						+"<td>"+(element[11]==null?"":element[11])+"</td>";
					
					//+"</tr>";
					}
					$('#sample_1').dataTable().fnClearTable();
					$('#minParameters').html(htmlq);
					loadSearchAndFilter('sample_1');
					$('#sample_2').dataTable().fnClearTable();
					$('#maxParameters').html(html);
					loadSearchAndFilter('sample_2');
					$('#sample_3').dataTable().fnClearTable();
					$('#avgParameters').html(htmls);
					loadSearchAndFilter('sample_3');
					$("#parameters").show();
					$("#mf").clear();
					}
				}
		 });
		 
		
	}
	function printGraph(){
		 html2canvas(document.getElementById('tab_1_3'), {
             onrendered: function (canvas) {
                 var data = canvas.toDataURL();
                 var docDefinition = {
                     content: [{
                         image: data,
                         width: 500
                     }],
                 };
                 pdfMake.createPdf(docDefinition).download("Table.pdf");
             }
		 });
		chart.print();
		
	}
</script>



<div class="page-content">

	<div class="portlet box blue">
		<c:if test="${not empty msg}">
			<div class="alert alert-danger display-show">
				<button class="close" data-close="alert"></button>
				<span style="color: red">${msg}</span>
			</div>
		</c:if>

		<!-- /.col-md-6 -->

		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>Daily Min Max Avg Data
			</div>
		
		</div>


		<div class="portlet-body">
		<jsp:include page="locationFilter.jsp"/>
			<div class="row" style="margin-left: -1px;">
				<div class="mt-radio-inline" style="display: none;">
					<label class="mt-radio"> <input type="radio"
						name="optionsRadios" id="meterno_radio" value="meterno" checked
						onchange="setRadioVal(this.value)"> Meter No <span></span>
					</label> <label class="mt-radio"> <input type="radio"
						name="optionsRadios" id="kno_radio" value="kno"
						onchange="setRadioVal(this.value)"> K No/Asset Code <span></span>
					</label>
				</div> 

                <%--    <form> --%>
                <div class="row" style="margin-left: -1px;">
						
						<div class="col-md-3">
							<strong>MeterNo:</strong><div id="mtrTd" class="form-group">
								 <select class="form-control select2me" 
									id="meterNum" name="meterNum" >
								</select>
							</div>
						</div>

					<%-- <div class="col-md-3">
						<strong>Date Selection:</strong>
						<div class="input-group input-large date-picker input-daterange "
							data-date-format="yyyy-mm-dd">
							<input type="text" autocomplete="off" placeholder="From Date"
								class="datepicker form-control input-small"
								<fmt:parseDate value="${currentDate}" pattern="yyyy-MM-dd" var="myDate"/>
								value="<fmt:formatDate value="${myDate}" pattern="yyyy-mm-dd" />"
								data-date="${myDate}" data-date-format="yyyy-mm-dd"
								data-date-viewmode="years" name="fromDate" id="fromDate">
							<span class="input-group-addon">to</span>&nbsp;&nbsp; <input
								type="text" autocomplete="off" placeholder="To Date"
								class="datepicker form-control input-small"
								<fmt:parseDate value="${currentDate}" pattern="yyyy-MM-dd" var="mytoDate"/>
								value="<fmt:formatDate value="${mytoDate}" pattern="yyyy-mm-dd" />"
								data-date="${mytoDate}" data-date-format="yyyy-mm-dd"
								data-date-viewmode="years" name="toDate" id="toDate">
						</div>
					</div> --%>
					
					<div class="col-md-3">
						<div class="form-group">
							 <strong>From Date:</strong>
							<div class="input-group input-medium date date-picker"
								data-date-end-date="-1d" data-date-format="yyyy-mm-dd"
								data-date-viewmode="years">
								<input type="text" autocomplete="off" class="form-control"
									name="fromDate" id="fromDate"
									placeholder="Select Date"> <span
									class="input-group-btn">
									<button class="btn default" type="button" id="">
										<i class="fa fa-calendar"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-group">
							 <strong>To Date:</strong>
							<div class="input-group input-medium date date-picker"
								data-date-end-date="-1d" data-date-format="yyyy-mm-dd"
								data-date-viewmode="years">
								<input type="text" autocomplete="off" class="form-control"
									placeholder="Select Date"  name="toDate" id="toDate"> <span
									class="input-group-btn">
									<button class="btn default" type="button" id="">
										<i class="fa fa-calendar"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
					<!-- <div class="row" style="margin-left: -1px;">
					<div class="col-md-3">			
					<div><b>Types:</b><select class="form-control select2me input-medium" id="selectoptions" style="width: 492px;">
						<option value="0">Select Type</option>
						<option value="chartContainer1">Daily Parameters Kvah</option>
						<option value="chartContainer2">Daily Parameters Current</option>
						<option value="chartContainerVr">Daily Parameters Voltage</option>
						<option value="chartContainerBkwh">Daily Parameters Block Kwh</option>
					</select></div></div>
					
					</div> -->
					
					<!-- <div class="col-md-3">
							<strong>Types:</strong><div id="selectoptions" class="form-group">
								 <select class="form-control select2me" >
										<option value="0">Select Type</option>
						<option value="chartContainer1">Daily Parameters Kvah</option>
						<option value="chartContainer2">Daily Parameters Current</option>
						<option value="chartContainerVr">Daily Parameters Voltage</option>
						<option value="chartContainerBkwh">Daily Parameters Block Kwh</option>
								</select>
							</div>
						</div>
					</div> -->
				
				
					
					<!-- <button type="button" id="dataview" class="btn yellow" style="margin-top: 20px; margin-left: 550px;" onclick="return getMinMaxData()" >
						<b>View</b>
					</button> -->
											
								

			</div>
			
			<div class="row" style="margin-left: -1px;">
			<div class="col-md-3">
							<strong>Types:</strong><div  class="form-group">
								 <select id="selectoptions"class="form-control select2me" >
										<option value="0">Select Type</option>
						<option value="chartContainer1">Daily Parameters Kvah</option>
						<option value="chartContainer2">Daily Parameters Current</option>
						<option value="chartContainerVr">Daily Parameters Voltage</option>
						<option value="chartContainerBkwh">Daily Parameters Block Kwh</option>
								</select>
							</div>
						</div>
						
						
					<button type="button" id="dataview" class="btn yellow" style="margin-top: 20px; margin-right: 550px;" onclick="return getMinMaxData()" >
						<strong>View</strong>
					</button>
					
			</div>

			<div id="imageee" hidden="true" style="text-align: center;">
				<h3 id="loadingText">Loading..... Please wait.</h3>
				<img alt="image" src="./resources/assets/img/loading.gif"
					style="width: 4%; height: 4%; align-content: center;">
			</div>
			<div class="modal-body">

				<div class="tabbable tabbable-custom tabbable-full-width">
					<ul class="nav nav-tabs">
						<li class="active" id="tabularViewLi"><a href="#tab_1_2" data-toggle="tab">Tabular
								View</a></li>

						<!-- <li><a href="#tab_1_8" onclick="return locationDetails();"
							data-toggle="tab">Location Details</a></li> -->
						<li id="graphicalViewLi"><a href="#tab_1_3" onclick="return graphicalView();"
							data-toggle="tab">Graphical View</a></li>
						
					</ul>
					<div class="tab-content">

						<!--tab_1_2-->
						<div class="tab-pane active" id="tab_1_2">

							<div class="box" style="display: none;" id="parameters">

							<label class="checkbox-inline">
			     				<input type="checkbox" name="checkboxValues" id="mf"  
			     				onclick="return getMinMaxData();"> Apply MF
							</label>
								<div id="tab_1-1" class="tab-pane active">
									<form role="form" class="form-horizontal" action=" "
										method="post">
										<div class="form-body">
											<div class="table-responsive">
												<h4>
													<b>Minimum Parameter Values</b>
												</h4>
												<div class="table-toolbar">
													<div class="btn-group pull-right" style="margin-top: 1px;">
														<div id="excelExportDiv2">
															<button class="btn dropdown-toggle"
																data-toggle="dropdown">
																Tools <i class="fa fa-angle-down"></i>
															</button>
															<ul class="dropdown-menu pull-right">
																
																<li><a href="#" id="excelExport2"
																	onclick="tableToExcel2('sample_1', 'minimum parameters')">Export
																		to Excel</a></li>
																		<!-- <li><a href="#" id="excelPdf2"
								onclick="exportDailyMinMaxPDF()">Export to PDF</a></li> -->
															</ul>
														</div>
													</div>
												</div>
												<table class="table table-striped table-hover table-bordered"
													id="sample_1">
													<thead>
														<tr>
															<!-- <th>Meter No</th> -->
															<th> Date</th>
															<th>Ir</th>
															<th>Iy</th>
															<th>Ib</th>
															<th>Vr</th>
															<th>Vy</th>
															<th>Vb</th>
															<th>kVArh lag</th>
															<th>KVArh lead</th>
															<th>Kvah</th>
															<th>Block kWh</th>
														</tr>
													</thead>
													<tbody id="minParameters">
														
													</tbody>
												</table>

												<h4>
													<br>
													<b>Maximum Parameter Values</b></br>
												</h4>
												<div class="table-toolbar">
													<div class="btn-group pull-right" style="margin-top: 1px;">
														<div id="excelExportDiv2">
															<button class="btn dropdown-toggle"
																data-toggle="dropdown">
																Tools <i class="fa fa-angle-down"></i>
															</button>
															<ul class="dropdown-menu pull-right">
																
																<li><a href="#" id="excelExport3"
																	onclick="tableToExcel3('sample_2', 'maximum parameters')">Export
																		to Excel</a></li>
																		<!-- <li><a href="#" id="excelPdf3"
								onclick="exportDailyMaxPDF()">Export to PDF</a></li> -->
															</ul>
														
															
														</div>
													</div>
												</div>
												
												<table class="table table-striped table-hover table-bordered"
													id="sample_2">
													<thead>
														<tr>
															<!-- <th>Meter No</th> -->
															<th> Date</th>
															<th>Ir</th>
															<th>Iy</th>
															<th>Ib</th>
															<th>Vr</th>
															<th>Vy</th>
															<th>Vb</th>
															<th>kVArh lag</th>
															<th>KVArh lead</th>
															<th>Kvah</th>
															<th>Block kWh</th>
														</tr>
													</thead>
													<tbody id="maxParameters">
												
															 
															
													</tbody>
												</table>
												
												<h4>
													<br>
													<b>Average Parameter Values</b></br>
												</h4>
												<div class="table-toolbar">
													<div class="btn-group pull-right" style="margin-top: 1px;">
														<div id="excelExportDiv2">
															<button class="btn dropdown-toggle"
																data-toggle="dropdown">
																Tools <i class="fa fa-angle-down"></i>
															</button>
															<ul class="dropdown-menu pull-right">
																
																<li><a href="#" id="excelExport4"
																	onclick="tableToExcel4('sample_3', 'average parameters')">Export
																		to Excel</a></li>
																		<!-- <li><a href="#" id="excelPdf3"
								onclick="exportDailyAvgPDF()">Export to PDF</a></li> -->
															</ul>
														</div>
													</div>
												</div>
												<table class="table table-striped table-hover table-bordered"
													id="sample_3">
													<thead>
														<tr>
															<!-- <th>Meter No</th> -->
															<th> Date</th>
															<th>Ir</th>
															<th>Iy</th>
															<th>Ib</th>
															<th>Vr</th>
															<th>Vy</th>
															<th>Vb</th>
															<th>kVArh lag</th>
															<th>KVArh lead</th>
															<th>Kvah</th>
															<th>Block kWh</th>
														</tr>
													</thead>
													<tbody id="avgParameters">
												
													</tbody>
												</table>

											</div>
										</div>
									</form>
								</div>
                
							</div>
						</div>

						<div class="tab-pane" id="tab_1_3">

							<div class="box">
									<div id="tab_1-1" class="tab-pane active">
										<form role="form" class="form-horizontal" action=" "
											method="post">
											<div class="portlet-body">
												<div class="table-toolbar">
														<div align="right">
													
					<a class="btn yellow" id="btn-Convert-Html2Image" onclick="printImage();" href="#">Download</a>
				 
								                       </div> 
									
											</div>
											 <label class="checkbox-inline">
											<input type="checkbox" name="checkboxValues" id="mf1" 
											 onclick="return graphicalView();"> Apply MF
											</label>
											</div>
										</form>
									</div>
						   
								 <div id="mainimage">
								 <div id="chartContainer1" style="height: 370px; width: 90%; margin-bottom: 100px;"></div>
								 <div id="chartContainer2" style="height: 370px; width: 90%; margin-bottom: 100px;"></div>
								 <div id="chartContainerVr" style="height: 370px; width: 90%;margin-bottom: 100px;"></div>
								 <div id="chartContainerBkwh" style="height: 370px; width: 90%;"></div>
								 </div>
							</div>
						</div>




						<div class="tab-pane" id="tab_1_8">

							<div class="box">
								<div class="tab-content">
									<div id="tab_1-1" class="tab-pane active">
										<div class="portlet-body">
											<div class="table-toolbar">
												<div class="btn-group pull-right" style="margin-top: 1px;">
													<div id="excelExportDiv6">
														<button class="btn dropdown-toggle" data-toggle="dropdown">
															Tools <i class="fa fa-angle-down"></i>
														</button>
														<ul class="dropdown-menu pull-right">

															<li><a href="#" id="excelExport6"
																onclick="tableToExcel6('sample_6', 'Location Details')">Export
																	to Excel</a></li>
														</ul>
													</div>
												</div>
											</div>
											<table class="table table-striped table-bordered"
													id="sample_6">
													
													<tbody id=consumerDetails>
															
													</tbody>
												</table>

										</div>
<!-- 										
 -->									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
	
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
	
	function printcharts(){
	window.print();
	}
	
	

/* ui page save as	image format function*/
function printImage()
{ 

	var image;
	selectedoption=$("#selectoptions").val();
	
	if(selectedoption=="chartContainer1"){
		
		image=document.getElementById('chartContainer1');
	
	}else if(selectedoption=="chartContainer2"){

		image=document.getElementById('chartContainer2');
	
	}else if(selectedoption=="chartContainerVr"){

		image=document.getElementById('chartContainerVr');
	
	}else if(selectedoption=="chartContainerBkwh"){

		image=document.getElementById('chartContainerBkwh');
	}
	else if((selectedoption=="0"))
		image=document.getElementById('mainimage');
	    
		  domtoimage.toBlob(image)
		    .then(function(blob) {
		      window.saveAs(blob, 'DailyMinMaxAvgData.png');
		    });
}


</script>
<script>

function showCircle(zone)
{
	//alert(zone);
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
                html+="<select id='LFcircle' name='LFcircle' onchange= 'showTownNameandCode(this.value)' class='form-control input-medium' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
                for( var i=0;i<response.length;i++)
                {
                    html+="<option value='"+response[i]+"'>"+response[i]+"</option>";
                }
				html+="</select><span></span>";
                $("#LFcircle").html(html);
                $('#LFcircle').select2();
				if('${circle}'!=''&&'${circle}'!=null){
					$('#LFcircle').val('${circle}').trigger('change');
				}
            }
        });
}


function showTownNameandCode(circle) {
	var zone = $('#LFzone').val();

    $.ajax({
        url : './getTownNameandCodebyCircle',
        type : 'GET',
        dataType : 'json',
        asynch : false,
        cache : false,
        data : {
        	zone : zone,
  			circle:circle
        },
                success : function(response1) {
                    var html = '';
                    html += "<select id='LFtown' name='LFtown' onchange= 'showResultsbasedOntownCode(this.value)' class='form-control input-medium' type='text'><option value=''>Select Town</option><option value='%'>ALL</option>";
                    for (var i = 0; i < response1.length; i++) {
                        //var response=response1[i];
                        html += "<option value='"+response1[i][0]+"'>"
                                +response1[i][0]+"-"+response1[i][1] + "</option>";
                    }
                    html += "</select><span></span>";
                    $("#LFtown").html(html);
	                $('#LFtown').select2();
	                if('${town}'!=''&&'${town}'!=null){
						$('#LFtown').val('${town}').trigger('change');
					}
                }
            });
}


function showResultsbasedOntownCode()
{
	var zone = $('#LFzone').val();
	var circle = $('#LFcircle').val();
	var twncode = $('#LFtown').val();
	
	$.ajax({
		url   : './getmMtrnoByZonCirTwn',
		type  : 'GET',
	 dataType : 'json',
        asynch: false,
        cache : false,
        data  : {
        	zone : zone,
			circle : circle,
			twncode : twncode,
            },
         success:function(response)
         {
             var html = '';
             html += "<select id='meterNum' name='meterNum'  class='form-control input-medium' type='text'><option value=''>Select MeterNo</option>";
             for (var i = 0; i < response.length; i++) {
              html += "<option value='"+response[i]+"'>"
                         +response[i]+ "</option>";
             }
             html += "</select><span></span>";
             $("#meterNum").html(html);
             $('#meterNum').select2();
             if('${mtrno}'!=''&&'${mtrno}'!=null){
					$('#meterNum').val('${mtrno}').trigger('change');
				}
         },
		});
}

function exportDailyMinMaxPDF()
{
	var zone = $('#LFzone').val();
	var circle = $('#LFcircle').val();
	var town = $('#LFtown').val();
	var mtrno = $('#meterNum').val();
	var fdate = $('#fromDate').val();
	var tdate = $('#toDate').val();
	var checkBox = document.getElementById("mf");
	var minmf=checkBox.checked;
	
	if(zone == "%")
	{
		zne = "ALL";
	}else{
		zne = zone;
	}
	if(circle == "%")
	{
		cir = "ALL";
	}else{
		cir = circle;
	}
	if(town == "%")
	{
		twn = "ALL";
	}else{
		twn  = town;
	}
	
	window.location.href="./DailyMinMaxPDF?zne="+zne+"&cir="+cir+"&twn="+twn+"&mtrno="+mtrno+"&fdate="+fdate+"&tdate="+tdate+"&minmf="+minmf;
	
	}

function exportDailyMaxPDF()
{
	var zone = $('#LFzone').val();
	var circle = $('#LFcircle').val();
	var town = $('#LFtown').val();
	var mtrno = $('#meterNum').val();
	var fdate = $('#fromDate').val();
	var tdate = $('#toDate').val();

	var checkBox = document.getElementById("mf");
	var maxmf=checkBox.checked;

	if(zone == "%")
	{
		zne = "ALL";
	}else{
		zne = zone;
	}
	if(circle == "%")
	{
		cir = "ALL";
	}else{
		cir = circle;
	}
	if(town == "%")
	{
		twn = "ALL";
	}else{
		twn  = town;
	}
	
	window.location.href="./DailyMaxPDF?zne="+zne+"&cir="+cir+"&twn="+twn+"&mtrno="+mtrno+"&fdate="+fdate+"&tdate="+tdate+"&maxmf="+maxmf;
	
	}

function exportDailyAvgPDF()
{
	var zone = $('#LFzone').val();
	var circle = $('#LFcircle').val();
	var town = $('#LFtown').val();
	var mtrno = $('#meterNum').val();
	var fdate = $('#fromDate').val();
	var tdate = $('#toDate').val();
	
	var checkBox = document.getElementById("mf");
	var avgmf=checkBox.checked;

	if(zone == "%")
	{
		zne = "ALL";
	}else{
		zne = zone;
	}
	if(circle == "%")
	{
		cir = "ALL";
	}else{
		cir = circle;
	}
	if(town == "%")
	{
		twn = "ALL";
	}else{
		twn  = town;
	}
	
	window.location.href="./DailyAvgPDF?zne="+zne+"&cir="+cir+"&twn="+twn+"&mtrno="+mtrno+"&fdate="+fdate+"&tdate="+tdate+"&avgmf="+avgmf;
	
	}







</script>

