<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<style>
.page-content .page-breadcrumb.breadcrumb>li>i {
	color: #fff;
}

.btn {
	padding: 7px 33px;
}

div.scrollmenu {
	background-color: #333;
	overflow: auto;
	white-space: nowrap;
	padding: 0px;
}

div.scrollmenu a {
	display: inline-block;
	color: white;
	text-align: center;
	padding: 10px;
	text-decoration: none;
}

div.scrollmenu a:hover {
	background-color: #777;
}

.dateHead {
	font-size: xx-small !important;
	padding: 0px !important;
	text-align: center !important;
	font-weight: normal !important; 
}
.dateData {
	padding: 0px !important; 
	height: 20px !important; 
	text-align: center !important;
}
.dateDataRed {
	padding: 0px !important;
	background: #e02222 !important;
	height: 20px !important; 
	text-align: center !important;
	color:white;
}

.dateDataGreen {
	padding: 0px !important;
	background: #3cc051 !important;
	height: 20px !important; 
	text-align: center !important;
	color:white;
}
.fa {
    font-size: 9px;
}

.noBorder{
border:0px ! important;
}
.largeFont{
 font-size: large !important;
 color: black;
 font-weight: bold !important;
 background: white !important;
 min-width: 176px;
 text-align: left;
}

.instantaneous{
 font-size: medium !important;
 color: black !important;
 font-weight: bold !important;
 background: white !important;
 width: 120px;
 text-align: left;
}

.portlet.box.gray > .portlet-title {
    background-color: #5f5f5f;
}
.bold{
font-weight: bold;
}

.width140{
width: 122px;
}

.mainHead{
font-size: x-large ! important;
font-weight: bold ! important;
}
.mainHead2{
font-size: small ! important;
font-weight: bold ! important;
color: teal ! important;
}

.portfolio-info {   
    padding: 7px 15px;
    margin-bottom: 5px;
    text-transform: uppercase;
}
.portfolio-block{
margin-bottom:5px ! important;
}

</style>
<script>
	$(".page-content")
			.ready(
					function() {

						if ('${showTotalModems.size()}' > 0) {
							$('#totalFeeder').show();
						}
						if ('${showWorkingsModems.size()}' > 0) {
							$('#workingFeeder').show();
						}
						if ('${showNotWorkingsModems.size()}' > 0) {
							$('#notWorkingFeeder').show();
						}
						App.init();
						Index.initMiniCharts();
						TableEditable.init();
						/*  loadSearchAndFilter('table_1'); 
						 loadSearchAndFilter('table_2'); 
						 loadSearchAndFilter('table_3');  */
						$('#totalModemNotCommDetails').addClass('start active ,selected');
						$(
								'#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
								.removeClass('start active ,selected');

					});
	
</script>
<div class="page-content">
 

<div class="portlet box blue" style="margin-bottom: 0px;">
						<div class="portlet-title blue">
									<label 
				style='color: #000000; font-size: 20px; font-weight:bold; float:left;'><span style="color: white;"><i class="fa fa-building"  style="font-size: 20px; font-weight:bold;"></i> Previous 6 months communicated information for the modem  ${imei} </span> </label>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								
							</div>
						</div>
						<div class="portlet-body">

  
     <div  id="divID">
     
     
     </div>

</div>
</div>
</div>
<style>
table thead>tr>th, .table tbody>tr>th, .table tfoot>tr>th, .table thead>tr>td,
	.table tbody>tr>td, .table tfoot>tr>td {
	border-top: none;
}
</style>
<script>
$( document ).ready(function() {

/*  */
$.ajax({
			url : "./fourMonthsModemChartMDAS/${imei}",
			data:"",
			type : "GET",
			dataType : "text",
			async : false,
			success : function(response) {
			
				 var array=JSON.parse(response);
				
					 for(var i=0;i<array.length;i++)
					{
						 
						 var obj1=array[i];
							
							 var key =Object.keys(obj1);
							
			 var  divtag="<div class='portlet box gray' style='margin-bottom: 0px; margin-top: 10px;border:solid'>"
						+"<div class='portlet-title blue'><label style='color: #FFF; font-size: 14px; float:left;'>"
						 +"<i class='fa fa-calendar'  style='font-size: medium;'></i>&nbsp;"+key+"</label>"
						+"</div>"
						+"<div class='' style='margin-bottom: 0px; margin-top: 10px;' id=tableID"+i+">"
						 +"</div>"
						 +"<table class='table table-bordered'  style='margin-top: 5px; border: white;'>"
					     +"<tbody id=table"+i+"><tbody></table>";
					     +"</div>"
						 +"</div>";
						 
					      $('#divID').append(divtag);
						
						 
						 var innerArrayarray=obj1[key];
						// alert(JSON.stringify('innerArray=='+innerArrayarray));
						
						 var html2="<tr id=daterow"+i+"></tr>";
						 var html3="<tr id=statusrow"+i+"></tr>";
						 
						 
						 $('#table'+i).append(html2);
						 $('#table'+i).append(html3);
						 
						  var tdhtml1="";
						  var tdhtml2="";
						  for(var j=0;j<innerArrayarray.length;j++)
						  { 
								var obj=innerArrayarray[j];
							
								if(j==0)
								{
									//$('#dataHeadTh').append('<td  style="width:41px; border:none;" ></td>');//ADDING DATE FIRST COLUMN
									//$('#activeTd').append('<td class="dateHead" style="width:41px; border:none;" ><b>Active</b></td>');
									
								}
								
								if(obj.yesNO=='Y')
								{ 
									tdhtml2+="<td class='dateDataGreen'  ><span class='fa fa-check'/></td>";
									tdhtml2+="<td  style='width:1px; border-width: thin;border-style:solid' ></td>";
								//	activeTd='<td class="dateDataGreen"><span class="fa fa-check"/></td>';
								}else
								{
								//	alert("no");
									//activeTd='<td class="dateDataRed"><span class="fa fa-times"/></td>';
									tdhtml2+="<td class='dateDataRed' style='border-width: thin;border-style:solid'><span class='fa fa-times'/></td>";
									tdhtml2+="<td  style='width:1px; border-width: thin;border-style:solid;' ></td>";
								}
								var viewDate=moment(obj.date).format('MMM<br/>DD');
								tdhtml1+="<td class='dateHead'style='border-width: thin;border-style:solid;'>"+viewDate+"</td>";
								tdhtml1+="<td  style='width:4px;border-width: thin;border-style:solid' ></td>";
							//	$('#dataHeadTh').append('<th class="dateHead" >'+viewDate+'</th>');//ADDING DATE
								
							//	$('#activeTd').append(activeTd);
								
						  }  //innerArray
						 // html2+="</tr>";
						 
						
						 $('#statusrow'+i).append(tdhtml2);
						 $('#daterow'+i).append(tdhtml1);
						
						
						 
					}//outerArray
					
			
				 
			}
});

/*  */
});
</script>
