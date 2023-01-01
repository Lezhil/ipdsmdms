
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8">
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>

<script src="<c:url value='/resources/assets/scripts/pdfmake.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/assets/scripts/html2canvas.min.js'/>" type="text/javascript"></script>

<style>
.table-toolbar {
	margin-bottom: 4px;
}

.col-md-2 {
	width: 24.500%;
}

.col-md-1 {
	width: 24.333%;
}
/* .paginate_button {
width: 10px;
} */
.paginate_button {
	padding: 15px 25px;
	font-size: 15px;
	text-align: center;
	cursor: pointer;
	outline: none;
	color: #fff;
	background-color: #717887;
	border: none;
	border-radius: 15px;
	box-shadow: 0 9px #999;
}

.paginate_button:hover {
	background-color: #597bc7
}

.paginate_button:active {
	background-color: #3e8e41;
	box-shadow: 0 5px #666;
	transform: translateY(4px);
}
</style>
<style>
a {
	text-decoration: none;
	display: inline-block;
	padding: 8px 16px;
}

a:hover {
	background-color: #ddd;
	color: black;
}

.previous {
	background-color: #f1f1f1;
	color: black;
}

.next {
	background-color: #4CAF50;
	color: white;
}

.round {
	border-radius: 50%;
}

.portlet.box.blue > .portlet-title {
    background-color: #4b8cf8;
}
.alert {
	border: 1px solid transparent;
	border-radius: 4px;
	margin-bottom: 10px;
	padding: 5px;
} 

/*  .modal-dialog {
	padding: 40px 100px 30px 220px;
	width: 100%;
} */

 .table thead>tr>th, .table tbody>tr>th, .table tfoot>tr>th, .table thead>tr>td, .table tbody>tr>td, .table tfoot>tr>td {
    padding: 8px;
    line-height: 1.428571429;
    vertical-align: top;
    
} 
 .badge-success {
    background-color: #3cc051;
   
} 
</style>
<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						App.init();
						FormComponents.init();
						$("#month").val('');
						$("#circle").val("").trigger("change");
						//loadSearchAndFilter('sample_1');
						//loadSearchAndFilter('sample_2');
						$('#dtWiseReport,#dtLoading,#dtLoadingSummary')
								.addClass('start active ,selected');
						$('#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
								.removeClass('start active ,selected');

					});
	
	$("#month").datepicker( {
	    format: "mm-yyyy",
	    startView: "months", 
	    minViewMode: "months"
	}); 

	var tableToExcel=(function () {
	    var uri = 'data:application/vnd.ms-excel;base64,'
	        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
	        , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
	        , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) }
	    return function (table, name) {
	        if (!table.nodeType) table = document.getElementById(table)
	        var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML }
	        document.getElementById("excelExport12").href = uri + base64(format(template, ctx));
	        document.getElementById("excelExport12").download = name+'_'+moment().format("DD-MM-YYYY_hh.mm.ss")+'.xls';
	    }
	})() 
	var tableToExcelNew=(function () {
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
	
	
	var tableToExcelNew1= (function () {
	    var uri = 'data:application/vnd.ms-excel;base64,'
	        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
	        , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
	        , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) }
	    return function (table, name) {
	        if (!table.nodeType) table = document.getElementById(table)
	        var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML }
	        document.getElementById("excelExport1").href = uri + base64(format(template, ctx));
	        document.getElementById("excelExport1").download = name+'_'+moment().format("DD-MM-YYYY_hh.mm.ss")+'.xls';
	    }
	})() 
	
	var tableToExcelNew2= (function () {
	    var uri = 'data:application/vnd.ms-excel;base64,'
	        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
	        , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
	        , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) }
	    return function (table, name) {
	        if (!table.nodeType) table = document.getElementById(table)
	        var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML }
	        document.getElementById("excelExport2").href = uri + base64(format(template, ctx));
	        document.getElementById("excelExport2").download = name+'_'+moment().format("DD-MM-YYYY_hh.mm.ss")+'.xls';
	    }
	})() 
		var tableToExcelNew3= (function () {
	    var uri = 'data:application/vnd.ms-excel;base64,'
	        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
	        , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
	        , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) }
	    return function (table, name) {
	        if (!table.nodeType) table = document.getElementById(table)
	        var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML }
	        document.getElementById("excelExport3").href = uri + base64(format(template, ctx));
	        document.getElementById("excelExport3").download = name+'_'+moment().format("DD-MM-YYYY_hh.mm.ss")+'.xls';
	    }
	})() 
		/* $(document).ready(function(){
		  var textbox = $("#textbox1");
		  textbox.change(function() {
		     $("#table1").find("td").each(function(){
		        if($(this).html()==textbox.val()){
		           alert(textbox.val());
		        }; */
	
	</script>
	<script>
var zone="%"
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
                    // html+="<select id='circle' name='circle' onchange='showTownNameandCode(this.value)' class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
                     html+="<select id='LFcircle' name='LFcircle' class='form-control' onchange='showTownNameandCode(this.value)' type='text'><option value=''></option><option value='%'>ALL</option>";
                     for( var i=0;i<response.length;i++)
                     {
                         html+="<option value='"+response[i]+"'>"+response[i]+"</option>";
                     }
					html+="</select><span></span>";
                     $("#LFcircleTd").html(html);
                     $('#LFcircle').select2();
                 }
             });
     } 

      function showDivision(circle) {
             $.ajax({
                 url : './getDivByCircle',
                 type : 'GET',
                 dataType : 'json',
                 asynch : false,
                 cache : false,
                 data : {
                     zone : zone,
                     circle : circle
                 },
                         success : function(response) {
                             var html = '';
                             html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
                             for (var i = 0; i < response.length; i++) {
                                 html += "<option value='"+response[i]+"'>"
                                         + response[i] + "</option>";
                             }
                             html += "</select><span></span>";
                             $("#divisionTd").html(html);
                             $('#division').select2();
                         }
                     });
         }
      function showSubdivByDiv(division) {
            // var zone = $('#zone').val();
             var circle = $('#circle').val();
             $.ajax({
                 url : './getSubdivByDiv',
                 type : 'GET',
                 dataType : 'json',
                 asynch : false,
                 cache : false,
                 data : {
                     zone : zone,
                     circle : circle,
                     division : division
                 },
                         success : function(response1) {
                             var html = '';
                             html += "<select id='sdoCode' name='sdoCode' onchange='showTownBySubdiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
                             for (var i = 0; i < response1.length; i++) {
                                 //var response=response1[i];
                                 html += "<option value='"+response1[i]+"'>"
                                         + response1[i] + "</option>";
                             }
                             html += "</select><span></span>";
                             $("#subdivTd").html(html);
                             $('#sdoCode').select2();
                         }
                     });
         }

      function showTownBySubdiv(subdiv) {
 	     // var zone = $('#zone').val();
 	      var circle = $('#circle').val();
 	      var division = $('#division').val();
 	      $.ajax({
 	          url : './getTownsBaseOnSubdivision',
 	          type : 'GET',
 	          dataType : 'json',
 	          asynch : false,
 	          cache : false,
 	          data : {
 	              zone : zone,
 	              circle : circle,
 	              division : division,
 	             subdivision :subdiv
 	          },
 	                  success : function(response1) {
 	                      var html = '';
 	                      html += "<select id='town' name='town'  class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
 	                      for (var i = 0; i < response1.length; i++) {
 	                          //var response=response1[i];
 	                          html += "<option value='"+response1[i][0]+"'>"
 	                                  + response1[i][0]+"-"+response1[i][1] + "</option>";
 	                      }
 	                      html += "</select><span></span>";
 	                      $("#townTd").html(html);
 	                      $('#town').select2();
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
    	                html += "<select id='LFtown' name='LFtown'  class='form-control  input-medium'  type='text'><option value=''></option><option value='%'>ALL</option>";
    	               
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
   

function getDTLoadingdata()
{ 
	var zone=$('#LFzone').val();
	var circle=$('#LFcircle').val();
	//var division=$('#division').val();
	//var subdiv=$('#sdoCode').val();
	var month=$('#month').val(); 
	var town=$("#LFtown").val();
	/* var mon=month.replaceAll("[\\s\\-()]", "");*/
	//alert(month); 
	if (zone == "" ||zone==null) {
		bootbox.alert("Please Select Region");
		return false;
	}
	if (circle == "" ||circle==null) {
		bootbox.alert("Please Select Circle");
		return false;
	}
	/* if (division == "" ||division==null) {
		bootbox.alert("Please Select Division");
		return false;
	}
	if (subdiv == "" ||subdiv==null) {
		bootbox.alert("Please Select Subdivision");
		return false;
	} */
	if (town == "" ||town==null) {
		bootbox.alert("Please Select Town");
		return false;
	}
	if (month == "") {
		bootbox.alert("Please Select Month Year");
		return false;
	}
	$("#sample_1").show();
	$("#excel1").show();
	$("#sample_2").hide();
	$('#imageee').show();
	$.ajax({

        url:"./getDtLoadingSummaryDetail",
        type : 'GET',
		dataType : 'json',
		asynch : false,
		cache : false,
		data : {
			zone  : zone,
			circle : circle,
			month : month,
			//subdiv : subdiv,
			//division : division,
			town : town
			
			},
		success : function(response) {
		$('#imageee').hide();
			var html = '';
		//alert(response);
		for(var i=0;i<response.length;i++)
			{
               var resp=response[i];
              // alert(resp[9])
               html += "<tr>"
					+ "<td>"+(i+1)+"</td>"
					+ "<td>"+resp[8]+"</td>"
					+ "<td>"+(resp[0]==null?"":resp[0])+"</td>"
					+ "<td>"+(resp[1]==null?"":resp[1])+"</td>"
					+ "<td><a href='#'  data-toggle='modal' data-target='#addingDataForTotalDT' title='Details of total DT' style='color:blue' onclick='return totalDTdetails(\""+ resp[9]+ "\",\""+ resp[2]+ "\",\""+ resp[10]+"\",\""+ resp[0]+ "\",\""+resp[3]+"\");'>"+ resp[6] +"</td>"
					+ "<td><a href='#'  data-toggle='modal' data-target='#addingDataForoverloaded' title='Details of overloaded DT' style='color:blue' onclick='return overloadedDetails(\""+resp[9]+ "\",\""+ resp[10]+"\",\""+ resp[0]+ "\",\""+resp[3]+"\");'>"+ resp[4] +"</td>"
					+ "<td><a href='#' data-toggle='modal' data-target='#addingDataForunderloaded' title='Details of underload DT' style='color:blue' onclick='return underloadedDetails(\""+resp[9]+"\",\""+ resp[10]+"\",\""+ resp[0]+ "\",\""+resp[3]+"\");'>"+ resp[5]+"</td>"
					+ "<td><a href='#' data-toggle='modal' data-target='#addingDataForunbalanced' title='Details of unbalance DT' style='color:blue' onclick='return unbalancedDetails(\""+resp[9]+"\",\""+ resp[10]+"\",\""+ resp[0]+ "\",\""+resp[3]+"\");'>"+ resp[7]+"</td>"
					+ "</tr>";
			}
		$('#sample_1').dataTable().fnClearTable();
			$("#dtLoad").html(html);
			loadSearchAndFilter1('sample_1');
		},
	
	complete : function() {
		loadSearchAndFilter('sample_1');
	}


		});

}


function overloadedDetails(id,towncode,zonecode,circlecode)
{

//	alert(id);
	
	var towncode = towncode;
	var zonecode = zonecode;
	var circlecode = circlecode;
	 $("#sample_1").show();
	$("#excel1").show();
	$("#sample_2").show(); 
	$("#ntwrkBlock").show();
	var month=$('#month').val();
	 var subdiv=$('#sdoCode').val();
	 var town=$("#LFtown").val();
	 var zone=$("#LFzone").val();
	 var circle=$("#LFcircle").val();
	//alert(id);
	//alert(subdiv);
	//alert(month);

	 $.ajax({

			url:"./getoverloadedSummaryDetail",
			 type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data : {
					id  : id,
					month : month,
					subdiv : subdiv,
					town : town,
					towncode : towncode,
					zone : zone,
					circle : circle,
					zonecode : zonecode,
					circlecode : circlecode
					},
				success : function(response) {
				//alert(response);
				var html1="";
				for(var i=0;i<response.length;i++)
				{
	               var resp=response[i];
	               html1 += "<tr>"
	            	   + "<td>"+(i+1)+"</td>"
						/* + "<td>"+(resp[33]==null?"":resp[33])+"</td>"
						+ "<td>"+(resp[34]==null?"":resp[34])+"</td>"
						+ "<td>"+(resp[35]==null?"":resp[35])+"</td>" */
						+ "<td>"+(resp[1]==null?"":resp[1]) +"</td>"
						+ "<td>"+(resp[2]==null?"":resp[2]) +"</td>"
						+ "<td>"+(resp[3]==null?"":resp[3]) +"</td>"
						+ "<td>"+ (resp[4]==null?"":resp[4]) +"</td>"
						+ "<td>"+ (resp[0]==null?"":resp[0])+"</td>"
						+ "<td>"+ (resp[6]==null?"":resp[6])+"</td>"
						+ "<td>"+ (resp[5]==null?"":resp[5])+"</td>"
						+ "<td>"+ (resp[7]==null?"":resp[7])+"</td>"
						/* + "<td>"+ (resp[7]==null?"":resp[7])+"</td>"
						+ "<td>"+ (resp[8]==null?"":resp[8])+"</td>"
						+ "<td>"+  ((resp[9] == null) ? "": moment(resp[9]).format('YYYY-MM-DD HH:mm:ss')) +"</td>"
						+ "<td>"+ ((resp[10] == null) ? "": moment(resp[10]).format('YYYY-MM-DD HH:mm:ss'))+"</td>"
					 */	
						+ "</tr>";
				}
			$('#sample_2').dataTable().fnClearTable();
				$("#dtoverloadedAsset").html(html1);
				loadSearchAndFilter1('sample_2');
			},
		
		complete : function() {
			loadSearchAndFilter('sample_2');
		}



		});
		 

}

function underloadedDetails(id,towncode,circlecode,zonecode)
{
	var towncode = towncode;
	var circlecode = circlecode;
	var zonecode = zonecode;
	
	
	$("#sample_1").show();
	$("#excel1").show();
	$("#sample_2").show(); 
	$("#ntwrkBlock").show();
	var month=$('#month').val();
	 var subdiv=$('#sdoCode').val();
	 var town=$("#LFtown").val();
	 var zone=$("#LFzone").val();
	 var circle=$("#LFcircle").val();
	 
	
	 $.ajax({

			url:"./getunderloadedSummaryDetail",
			 type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data : {
					id  : id,
					month : month,
					subdiv : subdiv,
					town : town,
					zone : zone,
					circle : circle,
					towncode : towncode
					
					},
				success : function(response) {
				//alert(response);
				var html1="";
				for(var i=0;i<response.length;i++)
				{
	               var resp=response[i];
	               html1 += "<tr>"
	            	   + "<td>"+(i+1)+"</td>"
						/* + "<td>"+(resp[33]==null?"":resp[33])+"</td>"
						+ "<td>"+(resp[34]==null?"":resp[34])+"</td>"
						+ "<td>"+(resp[35]==null?"":resp[35])+"</td>" */
						+ "<td>"+(resp[1]==null?"":resp[1]) +"</td>"
						+ "<td>"+(resp[2]==null?"":resp[2]) +"</td>"
						+ "<td>"+(resp[3]==null?"":resp[3]) +"</td>"
						+ "<td>"+ (resp[4]==null?"":resp[4]) +"</td>"
						+ "<td>"+ (resp[0]==null?"":resp[0])+"</td>"
						+ "<td>"+ (resp[6]==null?"":resp[6])+"</td>"
						+ "<td>"+ (resp[5]==null?"":resp[5])+"</td>"
						+ "<td>"+ (resp[7]==null?"":resp[7])+"</td>"
						/* + "<td>"+ (resp[7]==null?"":resp[7])+"</td>"
						+ "<td>"+ (resp[8]==null?"":resp[8])+"</td>"
						+ "<td>"+ ((resp[9] == null) ? "": moment(resp[9]).format('YYYY-MM-DD HH:mm:ss')) +"</td>"
						+ "<td>"+ ((resp[10] == null) ? "": moment(resp[10]).format('YYYY-MM-DD HH:mm:ss'))+"</td>"
						 */+ "</tr>";
				}
			$('#sample_3').dataTable().fnClearTable();
				$("#dtunderloadedAsset").html(html1);
				loadSearchAndFilter1('sample_3');
			},
		
		complete : function() {
			loadSearchAndFilter('sample_3');
		}



		});
		 

}

function unbalancedDetails(id,towncode,circlecode,zonecode)
{
	
	var towncode = towncode;
	var circlecode = circlecode;
	var zonecode = zonecode;
	
	$("#sample_1").show();
	$("#excel1").show();
	$("#sample_2").show(); 
	$("#ntwrkBlock").show();
	var month=$('#month').val();
	 var subdiv=$('#sdoCode').val();
	 var town=$("#LFtown").val();
	 var circle=$("#LFcircle").val();
	//alert(id);
	//alert(month);
	
	 $.ajax({

			url:"./getunbalancedSummaryDetail",
			 type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data : {
					id  : id,
					month : month,
					subdiv : subdiv,
					town : town,
					zone : zone,
					circle : circle,
					towncode : towncode
					},
				success : function(response) {
				//alert(response);
				var htmll = "<thead><tr style='background-color: lightgray'><th>Sl NO</th><th>Sub Division</th><th>Substation Name</th> <th>Feeder Name</th> <th>DT Name</th><th>DT ID</th><th>DT Capacity</th><th>Meter Serial No.</th><th>Cross DT</th></tr></thead>";
				var html="";
				for(var i=0;i<response.length;i++)
				{
					//alert(response)
					var html1="";
	               var resp=response[i];
	               html1 += "<tr>"
	            	   + "<td>"+(i+1)+"</td>"
						/* + "<td>"+(resp[33]==null?"":resp[33])+"</td>"
						+ "<td>"+(resp[34]==null?"":resp[34])+"</td>"
						+ "<td>"+(resp[35]==null?"":resp[35])+"</td>" */
						+ "<td>"+(resp[1]==null?"":resp[1]) +"</td>"
						+ "<td>"+(resp[2]==null?"":resp[2]) +"</td>"
						+ "<td>"+(resp[3]==null?"":resp[3]) +"</td>"
						+ "<td>"+ (resp[4]==null?"":resp[4]) +"</td>"
						+ "<td>"+ (resp[0]==null?"":resp[0])+"</td>"
						+ "<td>"+ (resp[6]==null?"":resp[6])+"</td>"
						+ "<td>"+ (resp[5]==null?"":resp[5])+"</td>"
						+ "<td>"+ (resp[7]==null?"":resp[7])+"</td>"
						/* + "<td>"+ (resp[7]==null?"":resp[7])+"</td>"
						+ "<td>"+ (resp[8]==null?"":resp[8])+"</td>"
						+ "<td>"+ ((resp[9] == null) ? "": moment(resp[9]).format('YYYY-MM-DD HH:mm:ss')) +"</td>"
						+ "<td>"+ ((resp[10] == null) ? "": moment(resp[10]).format('YYYY-MM-DD HH:mm:ss'))+"</td>"
			 */			+ "</tr>";
				}
			$('#sample_4').dataTable().fnClearTable();
				$("#dtunbalancedAsset").html(html1);
				loadSearchAndFilter1('sample_4');
			},
		
		complete : function() {
			loadSearchAndFilter('sample_4');
		}



		});
		 

}

function totalDTdetails(id,town,towncode,circlecode,zonecode)
{
 
 // alert(id);
	
	var otown=id;
     var towncode=towncode;
     var circlecode = circlecode;
     var zonecode = zonecode;
	 $("#sample_1").show();
	$("#excel1").show();
	$("#sample_2").show(); 
	$("#ntwrkBlock").show();
	var month=$('#month').val();
	 var subdiv=$('#sdoCode').val();
	 var town=$("#LFtown").val();
	 var circle=$("#LFcircle").val();
	 var zone=$("#LFzone").val();


	//alert(id);
	//alert(month);
	
	 $.ajax({

			url:"./gettotalDTDetail",
			 type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data : {
					id  : id,
					month : month,
					subdiv : subdiv,
					town : town,
					otown:otown,
					circle : circle,
					zone : zone,
					towncode:towncode,
					circlecode : circlecode,
					zonecode : zonecode
					},
				success : function(response) {
				//alert(response[34]);
				/* var html2 = "<thead><tr style='background-color: lightgray'><th>Sl NO</th><th>Sub Division</th><th>Substation Name</th> <th>Feeder Name</th> <th>DT Name</th><th>DT ID</th><th>DT Capacity</th><th>Meter Serial No.</th><th>Cross DT</th></tr></thead>"; */
				var html1="";
				for(var i=0;i<response.length;i++)
				{
				
	               var resp=response[i];
	               //alert(resp[2]);
	               html1 += "<tr>"
	            	   + "<td>"+(i+1)+"</td>"
	            	   + "<td>"+(resp[1]==null?"":resp[1])+"</td>"
	            	   + "<td>"+(resp[2]==null?"":resp[2])+"</td>"
	            	   + "<td>"+(resp[3]==null?"":resp[3]) +"</td>"
					   + "<td>"+(resp[4]==null?"":resp[4])+"</td>"
					   + "<td>"+(resp[0]==null?"":resp[0]) +"</td>"
					   + "<td>"+(resp[5]==null?"":resp[5]) +"</td>"
					   + "<td>"+(resp[6]==null?"":resp[6]) +"</td>"
					   + "<td>"+(resp[7]==null?"":resp[7]) +"</td>"
						 
						+ "</tr>";
				}
			$('#sample_5').dataTable().fnClearTable();
				$("#totaldtAsset").html(html1);
				loadSearchAndFilter1('sample_5');
			},
		
		complete : function() {
			loadSearchAndFilter('sample_5');
		}



		});
		 

}
</script>

<div class="page-content">
	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-Book"></i><strong>DT Loading Summary</strong>
			</div>
		</div>

		<div class="portlet-body">
		<jsp:include page="locationFilter.jsp"/> 
		
			<div class="row" style="margin-left: -1px;">
			<div class="col-md-3">
			<div class="input-group ">
						<strong>Month Year :</strong><font color="red">*</font><input
							type="text" class="form-control from"  id="month"
							name="month" required="required" placeholder="Select MonthYear">  <span
							class="input-group-btn">
							<button class="btn default" type="button"
								style="margin-bottom: -17px;">
								<i class="fa fa-calendar"></i>
							</button>
						</span>
					</div></div>
										
					<div class="col-md-3">
						<button type="button" id="dtLoadingData" style="margin-top: 19px;"
					onclick="return getDTLoadingdata()" name="dtLoadingData"
					class="btn green">
					<b>View</b>
				</button>
				</div>
				
				<div class="col-md-6">
								 <div  style="margin-left: 0px;">
									 <label><b>Note:-</b></label> 
						
									 <ol>
									  <li>Overload when greater than 100 percentage.</li>  
									  <li>Underload when lesser than 20 percentage.</li>
									  <li>Unbalance when lesser than 50 percentage.</li>
									</ol> 
								 </div>
							   </div>


			</div>	
			
			
			<%-- <div class="col-md-3">
					<strong>Region:</strong><div id="zoneTd" class="form-group">
						<select class="form-control select2me"
							id="zone" name="zone"
							onchange="showCircle(this.value);">
							<option value="">Select</option>
							<option value="%">ALL</option> 
							<c:forEach items="${ZoneList}" var="elements">
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
							<option value="">Select</option>
							<option value="%">ALL</option> 
							<c:forEach items="${circles}" var="elements">
								<option value="${elements}">${elements}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-md-3">
			<strong>Town:</strong>
							<div id="townTd" class="form-group"><select
								class="form-control select2me input-medium" id="town"
								name="town">
								<option value="">Select</option>
								<option value="%">ALL</option> 
							</select></div>
				</div> --%>
				<!-- <div class="col-md-3">
					<strong>Division:</strong><div id="divisionTd" class="form-group">
						 <select class="form-control select2me"
							id="division" name="division" onchange="showSubdivByDiv(this.value);">
							<option value="">Select</option>
							<option value="%">ALL</option>
						
						</select>
					</div>
				</div> -->
				<!-- <div class="col-md-3">
					<strong>Sub-Division:</strong><div id="subdivTd" class="form-group">
						 <select
							class="form-control select2me" id="sdoCode" name="sdoCode"  onchange="showTownBySubdiv(this.value);">
							<option value="">Select</option>
							<option value="%">ALL</option>
						
						</select>
					</div>
				</div> -->
		<%-- 	 <div class="row" style="margin-left: 13px;">
				<div data-date-viewmode="years" data-date-format="yyyymm" class="input-group input-medium date date-picker">
					<strong>Month Year :</strong><font color="red">*</font><input
						type="text" class="form-control"  id="month" value="${rdngMonth}"
						name="month" placeholder="Select Month Year"> <span class="input-group-btn">
														<button type="button" class="btn default" style="margin-bottom: -16px;" >
															<i class="fa fa-calendar"></i>
														</button>
													</span>
				</div>
			</div>  --%>
			
					
					<!-- <th class="block">Month&nbsp;Year<span style="color: red">*</span></th>
									<td>
										<div class="input-group ">
											<input type="text" class="form-control from" id="month"
												name="month" placeholder="Select Month"
												style="cursor: pointer"> <span
												class="input-group-btn">
												<button class="btn default" type="button"
												style="margin-bottom: -17px;">
													<i class="fa fa-calendar"></i>
												</button>
											</span>
										</div>
									</td> -->
					
					
					
				
			<br>
			
			<br> <br>

			<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>


			<div id="excel1" hidden="true">
			<div class="btn-group pull-right">
				<button class="btn dropdown-toggle" data-toggle="dropdown">
					Tools <i class="fa fa-angle-down"></i>
				</button>
				<ul class="dropdown-menu pull-right">
					<li><a href="#" id=""
								onclick="exportPDF('sample_1','DT Loading Data')">Export to PDF</a></li>
					<li><a href="#" id="excelExport12"
						onclick="tableToExcel('sample_1',' DT Loading Data');">Export to
							Excel</a></li>
				</ul>
			</div></div>
			<table class="table table-striped table-hover table-bordered"
				id="sample_1" hidden="true">
				<thead>
					<tr>
						<th>Sl.No</th>
						<th>Month Year</th>
						<th>Circle</th>
						<th>Town</th>
						<th>Total DT</th>
						<th>Overloaded DT</th>
						<th>Under Loaded DT</th>
						<th>Unbalanced DT</th>
						

					</tr>
				</thead>
				<tbody id="dtLoad">

				</tbody>
			</table>
</div></div>

       

</div>

<!-- -----------------modal generation------------- -->
<div id="popUpGrid2">
			<div id="addingDataForoverloaded" class="modal fade" tabindex="-1">
					<div class="modal-dialog" style="width: 70%;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" id="closePopUp">
							</button>
							<h4 class="modal-title">
								<span id="popUpHeading"></span>
							</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col-md-12">
									<div class="portlet box blue">
										<div class="portlet-title">
											<div class="caption">
												<i class="fa fa"></i>Overloaded DT<font color="yellow"><b><span id="subCode"></span></b></font>
											</div>
										</div>

										<div class="portlet-body">
			<div class="btn-group pull-right" >
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="print" onclick="exportGridData1()">Export To PDF</a></li>								
										<li><a href="#" id="excelExport" onclick="tableToExcelNew('sample_2','Overloaded DT Data');">Export to Excel</a></li>
									</ul>
								</div>
				<table class="table table-striped table-hover table-bordered"
					id="sample_2" >
					<thead>
						<tr>
							<th>Sl.No</th>
							<th>Circle</th>
							<th>Division</th>
							<th>Town</th>
							<th>DT Name</th>
							<th>DT ID</th>
							<th>DT Capacity</th>
							<th>Meter Serial No.</th>
							<th>KVA (max)</th>
							<!-- <th>Min Kva.</th>
							<th>Max Kva.</th>
							<th>Min Kva Interval</th>
							<th>Max Kva Interval</th> -->
							

						</tr>
					</thead>
					<tbody id="dtoverloadedAsset">

					</tbody>
				</table>
			
											<div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                                    
                                   
                                        </div>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		<div id="popUpGrid3">
			<div id="addingDataForunderloaded" class="modal fade" tabindex="-1">
				<div class="modal-dialog" style="width: 70%">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" id="closePopUp">
							</button>
							<h4 class="modal-title">
								<span id="popUpHeading"></span>
							</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col-md-12">
									<div class="portlet box blue">
										<div class="portlet-title">
											<div class="caption">
												<i class="fa fa"></i>Underloaded DT<font color="yellow"><b><span id="subCode"></span></b></font>
											</div>
										</div>

										<div class="portlet-body">
			<div class="btn-group pull-right" >
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="print" onclick="exportGridData2()">Export To PDF</a></li>								
										<li><a href="#" id="excelExport1" onclick="tableToExcelNew1('sample_3','Underloaded DT Data');">Export to Excel</a></li>
									</ul>
								</div>
				<table class="table table-striped table-hover table-bordered"
					id="sample_3" >
					<thead>
						<tr>
							<th>Sl.No</th>
							<th>Circle</th>
							<th>Division</th>
							<th>Town</th>
							<th>DT Name</th>
							<th>DT ID</th>
							<th>DT Capacity</th>
							<th>Meter Serial No.</th>
							<th>KVA (max)</th>
							<!-- <th>Min Kva.</th>
							<th>Max Kva.</th>
							<th>Min Kva Interval</th>
							<th>Max Kva Interval</th> -->
						</tr>
					</thead>
					<tbody id="dtunderloadedAsset">

					</tbody>
				</table>
			
											<div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                                    
                                   
                                        </div>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		<div id="popUpGrid4">
			<div id="addingDataForunbalanced" class="modal fade" tabindex="-1">
					<div class="modal-dialog" style="width: 70%">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" id="closePopUp">
							</button>
							<h4 class="modal-title">
								<span id="popUpHeading"></span>
							</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col-md-12">
									<div class="portlet box blue">
										<div class="portlet-title">
											<div class="caption">
												<i class="fa fa"></i>Unbalanced DT<font color="yellow"><b><span id="subCode"></span></b></font>
											</div>
										</div>

										<div class="portlet-body">
			<div class="btn-group pull-right" >
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="print" onclick="exportGridData3()">Export To PDF</a></li>								
										<li><a href="#" id="excelExport2" onclick="tableToExcelNew2('sample_4','Unbalanced DT Data');">Export to Excel</a></li>
									</ul>
								</div>
				<table class="table table-striped table-hover table-bordered"
					id="sample_4" >
					<thead>
						<tr>
							<th>Sl.No</th>
							<th>Circle</th>
							<th>Division</th>
							<th>Town</th>
							<th>DT Name</th>
							<th>DT ID</th>
							<th>DT Capacity</th>
							<th>Meter Serial No.</th>
							<th>KVA (max)</th>
							<!-- <th>Min Kva</th>
							<th>Min Kva Interval</th>
							<th>Max Kva.</th>
							<th>Max Kva Interval</th> -->

						</tr>
					</thead>
					<tbody id="dtunbalancedAsset">

					</tbody>
				</table>
			
											<div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                                    
                                   
                                        </div>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="popUpGrid5">
			<div id="addingDataForTotalDT" class="modal fade" tabindex="-1">
					<div class="modal-dialog" style="width: 70%">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" id="closePopUp">
							</button>
							<h4 class="modal-title">
								<span id="popUpHeading"></span>
							</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col-md-12">
									<div class="portlet box blue">
										<div class="portlet-title">
											<div class="caption">
												<i class="fa fa"></i>Total DT<font color="yellow"><b><span id="subCode"></span></b></font>
											</div>
										</div>

										<div class="portlet-body">
			<div class="btn-group pull-right" >
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
																		
										<li><a href="#" id="excelExport3" onclick="tableToExcelNew3('sample_5','Total DT Data');">Export to Excel</a></li>
									</ul>
								</div>
				<table class="table table-striped table-hover table-bordered"
					id="sample_5" >
					<thead>
						<tr>
							<th>Sl.No</th>
							<th>Circle</th>
							<th>Division</th>
							<th>Town</th>
							<th>Feeder Name</th>
							<th>DT ID</th>
							<th>Meter Serial No.</th>
							<th>DT Capacity</th>
							<th>KVA (max)</th>
						</tr>
					</thead>
					<tbody id="totaldtAsset">

					</tbody>
				</table>
			
											<div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                                    
                                   
                                        </div>
											</form>
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
var date=new Date();
var year=date.getFullYear();
var month=date.getMonth();
//var startDate = new Date();


$('.from').datepicker
({
    format: "yyyymm",
    minViewMode: 1,
    autoclose: true,
    startDate :new Date(new Date().getFullYear(),'-55'),
    endDate: new Date(year, month-1, '31')
});

function exportPDF()
{
	var zone=$('#LFzone').val();
	var circle=$('#LFcircle').val();
	var town=$('#LFtown').val();
	var month=$('#month').val();
	var townname = $("#LFtown option:selected").map(function(){return this.text}).get().join(',');
		
	var zne="",cir="",townn="",townname1="";
	
	if(zone=="%"){
		zne="ALL";
	}else{
	    zne=zone;
	}
	if(circle=="%"){
		cir="ALL";
	}else{
	    cir=circle;
	}
	/* if(division=="%"){
		div="ALL";
	}else{
		div=division;
	}
	if(subdiv=="%"){
		subdvn="ALL";
	}else{
		subdvn=subdiv;
	} */
	if(town=="%"){
		townn="ALL";
	}else{
		townn=town;
	}
	if(townname=="%"){
		townname1="ALL";
	}else{
		townname1=townname;
	}
	window.location.href=("./DTLoadingSummPDF?zne="+zne+"&cir="+cir+"&townn="+townn+"&month="+month+"&townname1="+townname1);
	
	//window.location.href=("./DTLoadingSummPDF?cir="+cir+"&townn="+townn+"&zone1="+zone1+"&month="+month);
}


	function printPdf(){
		var doc = new jsPDF('p', 'pt');
		var elem = document.getElementById("sample_2");
		var res = doc.autoTableHtmlToJson(elem);
		doc.autoTable(res.columns, res.data);
		doc.save("OverloadedDTData.pdf");
	}

	function exportGridData1()
	{
		html2canvas(document.getElementById('sample_2'), {
	        onrendered: function (canvas) {
	            var data = canvas.toDataURL();
	            var docDefinition = {
	                content: [{
	                    image: data,
	                    width: 500
	                }]
	            };
	            pdfMake.createPdf(docDefinition).download("OverloadedDTData.pdf");
	         }
	       });

	}

	function exportGridData2()
	{
		html2canvas(document.getElementById('sample_3'), {
	        onrendered: function (canvas) {
	            var data = canvas.toDataURL();
	            var docDefinition = {
	                content: [{
	                    image: data,
	                    width: 500
	                }]
	            };
	            pdfMake.createPdf(docDefinition).download("UnderLoadedDT.pdf");
	         }
	       });

	}

	function exportGridData3()
	{
		html2canvas(document.getElementById('sample_4'), {
	        onrendered: function (canvas) {
	            var data = canvas.toDataURL();
	            var docDefinition = {
	                content: [{
	                    image: data,
	                    width: 500
	                }]
	            };
	            pdfMake.createPdf(docDefinition).download("UnbalancedDT.pdf");
	         }
	       });

	}
	
</script>			