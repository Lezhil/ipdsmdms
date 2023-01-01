<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="java.util.Date"%>


<script type="text/javascript">
var sectionName;
  	$(".page-content").ready(function()
  		   {    	
  		
  		 
  		 $("#datedata").val(getPresentMonthDate('${selectedMonth}'));
  		    	App.init();
  		    	TableEditable.init(); 
  		      FormComponents.init();
  		  
  		    $('#MDMSideBarContents,#MIS-Reports,#exportReadingData').addClass('start active ,selected');
			 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
				"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
		
  		
  		   }); 	
  	
  	
  	function exportReadingDataDetails()
	{
			var circle=$('#circle').val();
		    var subdivision=$('#subdivision').val();
		    var month=$('#datedata').val();
		    
			$.ajax({
				type : "GET",
				url : "./exportReadingDataDetails/"+circle+"/"+subdivision+"/"+month,
				dataType: "json",
				cache : false,
				async : false,
				beforeSend: function(){
			        $('#imageee').show();
			        
			    },
			    complete: function(){
			        $('#imageee').hide();
			    },
				success : function(response)
				{
					if(response.length==0)
					{
					  bootbox.alert('Data Not Available');
					}
					else
					{
						var html = "";
				    	
				    	for(var j=0;j<response.length;j++)
			    		{		 
				    		var expRptData=response[j];
				    		for(var i=0;i<response.length;i++)
				    		{
					    		
					    		if(expRptData[i]==null)
				    			{
					    			alert("expRpt"+expRptData[i])
					    			expRptData[i]='';
				    			}
					    	}
					    	
				    		//alert(expRptData);
				    		html = html + "<tr><td>"+expRptData[0]+"</td>"+
				    				"<td>"+expRptData[1]+"</td>"+
				    				"<td>"+expRptData[2]+"</td>"+
				    				"<td>"+expRptData[3]+"</td>"+
				    				"<td>"+expRptData[4]+"</td>"+
				    				"<td>"+expRptData[5]+"</td>"+
				    				"<td>"+expRptData[6]+"</td>"+
				    				"<td>"+expRptData[7]+"</td>"+
				    				"<td>"+expRptData[8]+"</td>"+
				    				"<td>"+expRptData[9]+"</td>"+
				    				"<td>"+expRptData[10]+"</td>"+
				    				"<td>"+expRptData[11]+"</td>"+
				    				"<td>"+expRptData[12]+"</td>"+
				    				"<td>"+expRptData[13]+"</td>"+
				    				"<td>"+expRptData[14]+"</td>"+
				    				"<td>"+expRptData[15]+"</td>"+
				    				"<td>"+expRptData[16]+"</td>"+
				    				"<td>"+expRptData[17]+"</td>"+
				    				"<td>"+expRptData[18]+"</td>"+
				    				"<td>"+expRptData[19]+"</td>"+
				    				"<td>"+expRptData[20]+"</td>"+
				    				"<td>"+expRptData[21]+"</td>"+
				    				"<td>"+expRptData[22]+"</td>"+
				    				"<td>"+expRptData[23]+"</td>"+
				    				"<td>"+expRptData[24]+"</td>"+
				    				"<td>"+expRptData[25]+"</td>"+
				    				"<td>"+expRptData[26]+"</td>"+
				    				"<td>"+expRptData[27]+"</td>"+
				    				"<td>"+expRptData[28]+"</td>"+
				    				"<td>"+expRptData[29]+"</td>"+
				    				"<td>"+expRptData[30]+"</td>"+
				    				"<td>"+expRptData[31]+"</td>"+
				    				"<td>"+expRptData[32]+"</td>"+
				    				"<td>"+expRptData[33]+"</td>"+
				    				"<td>"+expRptData[34]+"</td>"+
				    				"<td>"+expRptData[35]+"</td>"+
				    				"<td>"+expRptData[36]+"</td>"+
				    				"<td>"+expRptData[37]+"</td>"+
				    				"<td>"+expRptData[38]+"</td>"+
				    				"<td>"+expRptData[39]+"</td>"+
				    				"<td>"+expRptData[40]+"</td>"+ 
				    				"</tr>";
			    		}
				    	
				    	var str=circle;
				    	var str1 = subdivision;
				    	var mrNameVal = str.replace(/\s+/g, "");
				    	var mrNameVal1 = str1.replace(/\s+/g, "");
				    	$("#expReadingData").html(html);
				    	$('#excelPrintId').html("<a href=# id=excelExport onclick=tableToExcel('table_1','Export_Reading_Data_"+mrNameVal+"_"+mrNameVal1+"')/>");
				    	document.getElementById("excelExport").click();
				    	
					}
				}
					
				
			});
			
		}
	
	  
  	
 	</script>
 	

				<div class="page-content">
						<div id="exportReadingData2" hidden="true">
				              <table id="table_1" border="1px" > 
								<thead>										
								 <tr>
										<th>DIVISION</th>
										<th>SUBDIV</th>
										<th>ACCNO</th>
										<th>CONSUMERNAME</th>
										<th>ADDRESS</th>
										<th>CATEGORY</th>
										<th>METRNO</th>
										<th>RDATE</th>
										<th>READINGDATE</th>
										<th>CURRDNGKWH</th>
										<th>CURRRDNGKVAH</th>
										<th>CURRDNGKVA</th>
										<th>XCURRDNGKWH</th>
										<th>XCURRRDNGKVAH</th>
										<th>XCURRDNGKVA</th>
										<th>T1KWH</th>
										<th>T2KWH</th>
										<th>T3KWH</th>
										<th>T4KWH</th>
										<th>T5KWH</th>
										<th>T6KWH</th>
										<th>T7KWH</th>
										<th>T8KWH</th>
										<th>T1KVAH</th>
										<th>T2KVAH</th>
										<th>T3KVAH</th>
										<th>T4KVAH</th>
										<th>T5KVAH</th>
										<th>T6KVAH</th>
										<th>T7KVAH</th>
										<th>T8KVAH</th>
										<th>T1KVAV</th>
										<th>T2KVAV</th>
										<th>T3KVAV</th>
										<th>T4KVAV</th>
										<th>T5KVAV</th>
										<th>T6KVAV</th>
										<th>T7KVAV</th>
										<th>T8KVAV</th> 
										<th>READINGREMARK</th> 
										<th>REMARK</th>
								 </tr>
								</thead>
								<tbody id="expReadingData">
									
								</tbody>
					</table> 
				</div>


					<div class="portlet box blue">
	 
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-file-excel-o"></i>Export Reading Data</div>   
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						
						<div id="excelPrintId" hidden="true">
								
							</div>
						<div class="portlet-body">
					
					
					
						 <div class="col-md-12">
							
							<table >
								<tr >
									<td id="masterTd1">
									<select  id="circle" class="form-control select2me input-medium"  type="text" name="circle">
									    <option value="noVal">SELECT CIRCLE</option>
										<c:forEach items="${circleVal}" var="element">
											<option value="${element}">${element}</option>
										</c:forEach>	
									</select></td>
										   
									<td>
									<select class="form-control select2me input-medium" name="subdivision" id="subdivision">
									    <option value="noVal">Select Subdiv</option>
									    <c:forEach var="elements" items="${subdivlist}">
									    	<option value="${elements}">${elements}</option>
									    </c:forEach>
									</select>				 </td>
										   
										    
									 <td>
										<div data-date-viewmode="years" data-date-format="yyyymm"  class="input-group input-medium date date-picker">
											<input type="text" name="month" id="datedata" readonly="readonly" class="form-control" required="required">
											<span class="input-group-btn">
												<button type="button" class="btn default"><i class="fa fa-calendar"></i></button>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</span>	
										</div>	
									</td>
									
									<td>
										<button type ="submit" class="btn btn-primary" onclick="exportReadingDataDetails()">
											<b>EXPORT TO EXCEL</b>
										</button>
									</td>
													 
								</tr>
								
						</table>
									
					</div>
					
				</div>
					
		</div>
		
		<div id="imageee" hidden="true" style="text-align: center;">
		    <h3 id="loadingText">Loading.... Please wait. </h3> 
			<!-- <img alt="image" src="./resources/bsmart.lib.js/359.gif" style="width:3%;height: 3%;"> -->
		</div>
		
	</div>
	




