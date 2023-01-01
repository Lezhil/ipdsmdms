<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.text.*,java.util.*" session="false"%>

<style>

label{
	margin-left:50px	
	
}
</style>
<script  type="text/javascript">

	$(".page-content").ready(function(){			
		//$("#datedata").val(getPresentMonthDate('${selectedMonth}'));
			App.init();
			TableManaged.init();
			FormComponents.init();
			$('#conndate').val(moment(new Date()).format('MM/DD/YYYY'));
			$('#MDMSideBarContents,#newConnectionId,#disconnection').addClass('start active ,selected');
			$("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports,#meterMaster,#other-Reports").removeClass('start active ,selected');

			});
		
	
	
	
	
	function viewdata()
	{
			var circle=$('#circle').val();
		    var subdivision=$('#subdivision').val();
		    var division=$('#division').val();
		    var date=$('#datedata').val();
		    
			$.ajax({
				type : "GET",
				url : "./getconnectiondetails/"+circle+"/"+subdivision+"/"+division+"/"+date,
				dataType: "json",
				cache : false,
				async : false,
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
				    		for(var i=0;i<expRptData.length;i++)
				    		{
					    		if(expRptData[i]==null)
				    			{
					    			expRptData[i]='';
				    			}
					    	}
				    		
				    		html += "<tr><td>"+expRptData[0]+"</td>"+
				    		
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
				    				"<td>"+'<button class="btn green " id='+expRptData[41]+' value='+expRptData[6]+' onclick="return disconnect('+expRptData[2]+',this.id,'+expRptData[12]+',this.value)">'+expRptData[41]+'</button>'+"</td>"+ 
				    				"</tr>";
			    		}
				    	
				    	$("#expReadingData").html(html);
				    	//loadSearchAndFilter('sample_1');
					}
				},
				complete:function(response)
				{
					loadSearchAndFilter('sample_3');
				}
			});
		}
	
	function updateLifeCycleTable(mtrno,accno,finalkwh)
	{
			$.ajax({
				type : "GET",
				url : "./updateMeterlifecycle/"+mtrno+"/"+accno+"/"+finalkwh,
				dataType: "json",
				cache : false,
				async : false,
				success : function(response)
				{
				}
			});
		}
	
	
	function disconnect(accno,msg,finalkwh,mtrno)
	{
		var date=$("#datedata").val();
		var msgrequired=null;
		var value=null;
		if(msg=="ACTIVE"||msg=="Active")
		 {
		 msgrequired= "Are you confirm you want to Disconnect!";
		 value="RECONNECT";
		 }
		else
		 {
		 msgrequired= "Are you confirm you want to Connect!";
		 value="ACTIVE";
		 }
		var box =bootbox.confirm({
			 message:msgrequired,
		        buttons: {
		            confirm: {
		                label: 'Yes',
		                className: 'btn-success'
		            },
		            cancel: {
		                label: 'No',
		                className: 'btn-danger'
		            }
		        },
		        callback: function (result) {
		            if(result){
		            	 $.ajax({
		            		 type : "GET",
		 					url : "./updateStatus/" + accno+"/"+value+"/"+date,
		 					dataType : "json",
		 					cache:false,
		 					async:false,
		 					success : function(response)
		 								{ 
		 								if(response==1)
		 									{
		 										 if(value=="RECONNECT")
		 											{
		 												bootbox.alert("Meter Disconnected Succesfully");
		 												updateLifeCycleTable(mtrno,accno,finalkwh);
		 												box.modal('hide');
		 											}
		 											else
		 											{
			 											bootbox.alert("Meter Connected Succesfully");
			 											box.modal('hide');
		 											}  
		 												 viewdata();
		 										} 
		 									else
		 										{
		 										bootbox.alert("Please Try Again!");
		 										}
		 							    }
							});
		            }
		        }
		    });
		}
	
	  function getdivVal(param)
	  {
	  	$.ajax({
	  		url : "./getdivBasedOncir",
				data:{param:param},
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	success:function(response)
		    	{
	    			var html='';
		    		html+="<select id='division' name='division' class='form-control select2me input-medium' onchange='return getsubdivValue(this.value);' type='text'><option value='deafult' >Division</option>";
					for( var i=0;i<response.length;i++)
					{
						html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
					}
					html+="</select><span></span>";
					
					$("#masterTd2").html(html);
					$('#subdiv').select2();
		    	}
			});
	  }
	
	  function getsubdivValue(param)
	  {
		  var circle=$("#circle").val();
	  	$.ajax({
	  		url : "./getsubdivBasedOncir",
				data:{param:param,circle:circle},
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	success:function(response)
		    	{
	    			var html='';
		    		html+="<select id='subdivision' name='subdivision' class='form-control select2me input-medium' type='text'><option value='deafult' >Subdivision</option>";
					for( var i=0;i<response.length;i++)
					{
						html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
					}
					html+="</select><span></span>";
					$("#masterTd3").html(html);
					$('#subdiv').select2();
		    	}
			});
	  }
</script>
<div class="page-content" >
<!-- Display Error Message -->
		<c:if test = "${not empty result}">
			<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color:red" >${result}</span>
			</div>
	    </c:if>	
					<span style="color:red" id="accountNotExistMsg"></span>
			
		<!-- End Error Message -->
  <div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Disconnection</div></div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
							</div>
							
						<div class="portlet-body">	
							<form>
							<table>
								<tr>
									<td>
										<div data-date-viewmode="years" data-date-format="yyyymm"  class="input-group input-medium date date-picker">
											<input type="text" name="month" id="datedata" readonly="readonly" class="form-control" required="required">
											<span class="input-group-btn">
												<button type="button" class="btn default"><i class="fa fa-calendar"></i></button>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</span>	
										</div>	
									</td>
								
									<td id="masterTd1">
									<select  id="circle" class="form-control select2me input-medium"  type="text" name="circle" onchange="return getdivVal(this.value);">
									    <option value="noVal">SELECT CIRCLE</option>
										<c:forEach items="${circleVal}" var="element">
											<option value="${element}">${element}</option>
										</c:forEach>	
									</select></td>
										   
								    <td id="masterTd2">
									<select class="form-control select2me input-medium" name="subdivision" id="division" onchange="return getsubdivValue(this.value);">
									    <option value="noVal">Select division</option>
									    <c:forEach var="elements" items="${divlist}">
									    	<option value="${elements}">${elements}</option>
									    </c:forEach>
									</select></td>
										   
									<td id="masterTd3">
									<select class="form-control select2me input-medium" name="subdivision" id="subdivision">
									    <option value="noVal">Select Subdiv</option>
									    <c:forEach var="elements" items="${subdivlist}">
									    	<option value="${elements}">${elements}</option>
									    </c:forEach>
									</select></td>
									
									<td>
										<button type ="button" class="btn btn-primary" onclick="viewdata()">
											<b>View</b>
										</button>
									</td>
													 
								</tr>
						</table></form>
					
					<!-- ED EXAMPLE TABLE PORTLET-->
					<!-- <div id="exportReadingData2"> -->
				     	<table class="table table-striped table-bordered table-hover" id="sample_3" >
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
										<th>Connect/Disconnect</th>
								 	</tr>
								</thead>
								<tbody id="expReadingData">
									
								</tbody>
					</table> 
				</div> 
			</div>
		</div>
</div>
</div>
	