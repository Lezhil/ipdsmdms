
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	$(".page-content").ready(function() {
		App.init();
		TableEditable.init();
		Tasks.initDashboardWidget();
		FormComponents.init();
		TableManaged.init();
		 $("#reportsMonth").val(getPresentMonthDate('${selectedMonth}'));
		 
		
		 $('#MIS-Reports').addClass('start active ,selected');
		 $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#other-Reports,#newConnectionId").removeClass('start active ,selected');
		
		 var sdoName='${sdoName}';

		
		
		 
		 $('#circleId').change(function() { 
	    	    var dropVal = $(this).val();
	    	    sessionStorage.setItem("SelectedItem", dropVal);
	    	});
	    	
	     var selectedItem = sessionStorage.getItem("SelectedItem");  
	    //alert(selectedItem);
	   	    if(selectedItem==null)
	   	    {
	   	    	//alert('if');
	   	    	$("#circleId").val("0").trigger("change"); 	 
	   	    }else{
	   	    	//alert('else');
	   	    	$("#circleId").val(selectedItem).trigger("change");
	   	    }

	   	 /* var rr='${result}';
	   		if(rr=="dataFound")
	   		{
		   	
	   		document.getElementById('mipsipreportId').style.display='block';
	   		document.getElementById('loadingmessageD2table').style.display='none';
	   		} */
	   	    
	    	 
	});
</script>

<style>
.col-sm-1 {
	width: 10.3333%;
}
</style>

<div class="page-content" >


<%-- <div class="row">
			<div class="col-md-12">
										
			<form:form action="./downloadExcelMIP"  enctype="multipart/form-data" method="post" id="meterNoUpload">
	 			<div class="form-group">
						<div >
							<div class="modal-footer">
									<!-- <button type="button" data-dismiss="modal" class="btn default pull-left">Close</button> -->
											<button class="btn green pull-left" style="display: block;" id="downloadOption" >DownloadTemplate</button>
												</div>
				             </div>
	               </div>
	
			</form:form> 

			</div>
</div> --%>
<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>MIP,SIP and NDS Reading Report</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								</div>
						</div>					
											
						<div class="portlet-body">
							
							<form action="" id="cmriId" method="POST">
							<table>

							<tr>
							<td>Rdngmonth :</td>
							<td>
							<div class="input-group date date-picker" data-date-format="yyyymm" data-date-viewmode="years">
							<input type="text" class="form-control" name="reportFromDate" id="reportFromDate" value="${readingMonthMip}" readonly>
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</td>	
							
							<td>CIRCLE :</td>
								<td><select id="circleId"
									class="form-control placeholder-no-fix" name="circleId" onchange="return getSdoName(this.value);">
										<option value="0">Select</option>
										<c:forEach var="element" items="${circle}">
											<option value="${element}">${element}</option>
										</c:forEach>
								</select></td>
								
								<td>SUBDIVISION :</td>
								<td ><select id="sdoId"
									class="form-control input-medium" name="sdoId">
								    	<option value="0">Select SdoName</option>
										<option value="%">ALL</option>
										<%-- <c:forEach var="element" items="${circle}">
											<option value="${element}">${element}</option>
										</c:forEach> --%>
								</select></td>
							</tr>
							
							</table>
							<div class="modal-footer">
							<button type="submit" class="btn blue pull-left" onclick="return validation()">Generate Report</button>  
							
							</div>
							
							
							</form>								
						</div>
						
						
						
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
					
					
					<div class="portlet box blue"  >
	
	                <c:if test = "${cmrierror eq 'MIP,SIP,NDS Reading Data Not Found...'}"> 			        
			           <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${cmrierror}</span>
						</div>
			        </c:if>
			        
			        
						<div class="portlet-title" >
						
							<div class="caption" ><i class="fa fa-edit" ></i>MIP,SIP & NDS Reading Report
							
						  <a href="#" id="excelExport" class="btn green" style="margin-left: 410px;"  onclick="mipExport()">Export to Excel</a>
						 	</div>
							
						</div>
						<div class="portlet-body" >
							<table class="table table-striped table-hover table-bordered"   id="sample_editable_1" >
								<thead>
									<tr>
										<th>CIRCLE</th>
										<th>SDOCODE</th>
										<th>SDONAME</th>
										<th>CATEGORY</th>
										<th>ACCNO</th>
										<th>METRNO</th>
										<th>REMARK</th>
										<th>KWH</th>
										<th>KVAH</th>
										<th>KVA</th>
										<th>PF</th>
										<th>NAME AND ADDRESS</th>
										
										
									</tr>
								</thead>
								<tbody id="miptable">
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${cmriList}">
								<tr>
								
									<td>${element[0]}</td>
									<td>${element[1]}</td>
									<td>${element[2]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>

									<c:choose>
										<c:when test="${not empty element[7]}">
											<td>OK</td>
										</c:when>
										<c:otherwise>
											<td>${element[6]}</td>
										</c:otherwise>
									</c:choose>


									<c:choose>
								<c:when test="${not empty element[7]}">
								 <td>${element[7]}</td> 
								<td>${element[8]}</td>
								<td>${element[9]}</td>
								<td>${element[10]}</td>
								
								</c:when>
								<c:otherwise>
								<c:choose>
								
								<c:when test="${element[16]==99999999||element[16]==9999999||element[16]==999999}">
								<td></td>
								</c:when>
								<c:otherwise>
								<td>${element[16]}</td>
								</c:otherwise>
								</c:choose>
								<c:choose>
								
								<c:when test="${element[17]==99999999||element[17]==9999999||element[17]==999999}">
								<td></td>
								</c:when>
								<c:otherwise>
								<td>${element[17]}</td>
								</c:otherwise>
								</c:choose>
								
								
								<c:choose>
								<c:when test="${element[18]==99999999||element[18]==9999999||element[18]==999999}">
								<td></td>
								</c:when>
								<c:otherwise>
								<td>${element[18]}</td>
								</c:otherwise>
								</c:choose>
								
								
								<c:choose>
								<c:when test="${element[19]==99999999||element[19]==9999999||element[19]==999999}">
								<td></td>
								</c:when>
								<c:otherwise>
								<td>${element[19]}</td>
								</c:otherwise>
								</c:choose>
								
								
								</c:otherwise>
								
								</c:choose>
								
									<td>${element[15]}</td>
									
									
									
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
							
							<div id='loadingmessageD2table' class="image" style="display: none;">
  									
  									<img src="resources/assets/img/home/loader-transparent-gif-12.gif"  class="img-responsive" height="150px" width="220px" style="margin-left: auto;margin-right: auto;">
  									
								</div>
						</div>
					</div>
				</div>
			</div>
</div>



<script>

function validation()
{
	var circle=$("#circleId").val();
	var rdngMonth=$("#reportFromDate").val();
	
	if(circle=="0")
		{
		bootbox.alert("Please Select Circle");
		return false;
		}
	
		 $("#cmriId").attr("action","./XmlReadingReport");

		$("#miptable").empty();
		
		 document.getElementById("loadingmessageD2table").style.display='block';
		
		// $("#reportFromDate").val('${rdng_Month}'); 
	}

function getSdoName(param)
{

var circle=param;
	 $.ajax({
		 type : "POST",
		 url:"./analysedReportgetSdoName",
		 data:{circle:circle},
		 success:function(response)
		 {
			 if(response != null)
	    		{
				 
				var html='<option value="0">Select SdoName</option>';
	    			 html+='<option value="%">ALL</option>'; 
	    			/* html+='<option value="%">ALL</option>'; */
	    			
	    			 for( var i=0;i<response.length;i++)
	    			 {
	    				html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
	    			 }
	    			 html+='</select>';
	    			 $("#sdoId").empty();
	    			 $("#sdoId").append(html);
	    			 $("#sdoId").val('${sdoName}');
	    			 
	    		}
		 }
		 
	 });
	 
}

//excel



/* ---------------------  Export To Excel-------------------------- */
function mipExport()
{
	var rdngMonth = $("#reportFromDate").val();
	var circle=$("#circleId").val();
	var sdoname=$("#sdoId").val();
	//alert("In Excel Export"+" "+"circle---->"+circle+"sdoname--->"+sdoname);
	
	if(sdoname=="%")
		{
		sdoname='all';
		}
	
	if (circle == "0") {
		bootbox.alert("Please Select Circle");
		return false;
	} else {
		// window.open("./mipSipRptExportToExcel/" +rdngMonth+ "/" + circle + "/" + sdoname);
		 window.location.href="./mipSipRptExportToExcel?rdngMonth="+rdngMonth+"&circle="+circle+"&sdoname="+sdoname;
		

	}
}
 

</script>

<!-- 
<style>


body
{
    font-size: 12pt;
    font-family: Calibri;
    padding : 10px;
}

table
{
    border: 1px solid black;
    
}
th
{
    border: 1px solid black;
    padding: 5px;
    background-color:grey;
    color: white;
    
}
td
{
    border: 1px solid black;
    padding: 5px;
}

input
{
    font-size: 12pt;
    font-family: Calibri;
}

</style>
 -->



