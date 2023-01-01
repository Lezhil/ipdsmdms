<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>   
  <script src="<c:url value='/resources/bsmart.lib.js/formEditSettings.js'/>" type="text/javascript"></script>  
	<script type="text/javascript">
	 $(".page-content").ready(function()
			   {    	
			    	App.init();
			    	FormComponents.init();	        	
			    	$('#ADMINSideBarContents,#emailgateway').addClass('start active ,selected');  	
			    	$("#MDASSideBarContents,#MDMSideBarContents,#alarmID,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
			    	
			    }); 
	 
	 function editable(id)
		{
			 
			 initAjaxMock();
			 $.mockjax({
		            url: '/groups',
		            cache:false,
		            response: function (settings) {
		                this.responseText = [
								{
								    value: 0,
								    text: 'Select'
								},                
		                                     
		                     {
		                        value: 'True',
		                        text: 'True'
		                    }, {
		                        value: 'False',
		                        text: 'False'
		                    }
		                ];
		                log(settings, this);
		            }
		        });
			 $.mockjax({
		            url: '/ssl',
		            cache:false,
		            response: function (settings) {
		                this.responseText = [
								{
								    value: 0,
								    text: 'Select'
								},                 
		                     {
		                        value: 'YES',
		                        text: 'YES'
		                    }, {
		                        value: 'NO',
		                        text: 'NO'
		                    }
		                ];
		                log(settings, this);
		            }
		        });
			 $.fn.editable.defaults.inputclass = 'form-control';
            $.fn.editable.defaults.url = '/post';
			var s = "#"+id;
			var assignId = s.substring(1, s.length-1);
		    $(s).editable({
	            validate: function (value) {
	                if ($.trim(value) == '' || $.trim(value) =='0' ) return 'This field should be required';
	                else 
	                	document.getElementById(assignId).value=$.trim(value);
	               
	            }
	        }); 
		  
		    //alert(document.getElementById('ssl1').value);
		    //document.getElementById('ssl').value=document.getElementById('ssl1').value;
			
		}
	</script>
	 
		<div class="page-content" >
     <%@include file="pagebreadcrum.jsp" %>
 
			<div class="row">
				<div class="col-md-12">
					<div class="alert alert-info">
				To change please <strong>double click</strong> on the text and change the text. To save click on update settings
							
							</div>
					<hr>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
				<c:if test = "${results ne 'notDisplay'}"> 			        
			        <div class="alert alert-danger display-show">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${results}</span>
						</div>
			        </c:if>
				<form:form action="./updateEmailGatewaySettings" modelAttribute="emailGatewaySettings" commandName="emailGatewaySettings" method="post" id="emailGatewaySettings">
				
				<button class="btn blue" formaction="./updateEmailGatewaySettings">Update Settings</button>
				
					<table id="user" class="table table-bordered table-striped">
						<tbody>
						<tr hidden="hidden">
						<td><form:input path="id" id="id" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="id" value="${element.id}"></form:input></td></td>
						<td><form:input path="smtpHost" id="smtpHost" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="smtpHost" value="${element.smtpHost}"></form:input></td></td>
						<td><form:input path="smtpPort" id="smtpPort" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="smtpPort" value="${element.smtpPort}"></form:input></td></td>
						<td><form:input path="ssl" id="ssl" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="ssl" value="${element.ssl}"></form:input></td></td>
						<td><form:input path="smtpAuth" id="smtpAuth" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="smtpAuth" value="${element.smtpAuth}"></form:input></td></td>
						<td><form:input path="mailId" id="mailId" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="mailId" value="${element.mailId}"></form:input></td></td>
						<td><form:input path="mailPassword" id="mailPassword" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="mailPassword" value="${element.mailPassword}"></form:input></td></td>
						
						</tr>
						
						 
							<tr>
								<td style="width:15%">SMTP Host</td>
								<td style="width:50%"><a href="#" id="smtpHost1" data-type="text" data-pk="1" data-original-title="Enter SMTP Host" onclick="editable(this.id)">${element.smtpHost} </a></td>
								 <td style="width:35%"><span class="text-muted">Enter SMTP Host</span></td> 
							</tr>
							<tr>
								<td style="width:15%">SMTP Port</td>
								<td style="width:50%"><a href="#" id="smtpPort1" data-type="text" data-pk="1" data-original-title="Enter SMTP Port" onclick="editable(this.id)">${element.smtpPort}</a></td>
							    <td style="width:35%"><span class="text-muted">Enter SMTP Port</span></td> 
							</tr>
							<tr>
								<td style="width:15%">SSL Enabled</td>
								<td style="width:50%"><a href="#" id="ssl1" data-type="select" data-value="0" data-pk="1" data-source="/ssl" data-original-title="Enter SSL" onclick="editable(this.id)">${element.ssl}</a></td>
								 <td style="width:35%"><span class="text-muted">SSL Enabled</span></td> 
							</tr>
							<tr>
								<td>SMTP Auth</td>
								<td><a href="#" id="smtpAuth1" data-type="select" data-pk="1" data-value="0" data-source="/groups" data-original-title="Enter SMtP Auth" onclick="editable(this.id)">${element.smtpAuth}</a></td>
							 
								
								
								<%-- <td style="width:50%"><a href="#" id="smtpAuth1" data-type="text" data-pk="1"  data-original-title="Enter SMtP Auth" onclick="editable(this.id)">${element.smtpAuth}</a></td> --%>
								 <td><span class="text-muted">Enter SMTP Auth</span></td> 
							</tr>
							
							<tr>
								<td>Sender Mail ID</td>
								<td style="width:50%"><a href="#" id="mailId1" data-type="text" data-pk="1"  data-original-title="Enter MAIL ID" onclick="editable(this.id)">${element.mailId}</a></td>
								 <td><span class="text-muted">Sender Mail Id </td> 
							</tr>
							
							<tr>
								<td>Sender Mail Password</td>
								<td style="width:50%"><a href="#" id="mailPassword1" data-type="text" data-pk="1"  data-original-title="Enter MAIL PASSWORD" onclick="editable(this.id)">${element.mailPassword}</a></td>
								 <td><span class="text-muted">Sender Mail Password <strong>No buttons</strong> mode</span></td> 
							</tr>
							<!-- <tr>
								<td>Status</td>
								<td><a href="#" id="status" data-type="select" data-pk="1" data-value="0" data-source="/status" data-original-title="Select status">Active</a></td>
								<td><span class="text-muted">Error when loading list items</span></td>
							</tr> -->
							
							
							
							
						</tbody>
					</table>
					
					</form:form>
				</div>
			</div>
			
			
			<div class="row">
				<div class="col-md-12">
				
					<form:form action="./updateEmailMessageSettings" modelAttribute="MessageSettings" commandName="MessageSettings" method="post" id="MessageSettings">  
					<button class="btn blue" formaction="./updateEmailMessageSettings">Email Alert Settings</button>
					<table id="" class="table table-bordered table-striped">
					
						<tbody>
						<tr hidden="hidden">
						<td><form:input path="id" id="id" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="id" value="${message.id}"></form:input></td></td>
						<td><form:input path="emailAlert" id="emailAlert" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="emailAlert" value="${message.emailAlert}"></form:input></td></td> 
						<td><form:input path="emailScheduleAlert" id="emailScheduleAlert" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="emailScheduleAlert" value="${message.emailScheduleAlert}"></form:input></td></td> 
						<td><form:input path="smsAlert" id="smsAlert" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="smsAlert" value="${message.smsAlert}"></form:input></td></td> 
						<td><form:input path="smsScheduleAlert" id="smsScheduleAlert" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="smsScheduleAlert" value="${message.smsScheduleAlert}"></form:input></td></td>
						<td><form:input path="smsScheduleTime" id="smsScheduleTime" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="smsScheduleTime" value="${message.smsScheduleTime}"></form:input></td></td> 
						<%-- <td><form:input path="emailScheduleTime" id="emailScheduleTime" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="emailScheduleTime" value="${message.emailScheduleTime}"></form:input></td></td> --%> 
						
						</tr> 
					 
							<tr>
								<td style="width:15%">Live Email Alert</td>
								<td style="width:50%"><a href="#" id="emailAlert1" data-type="select" data-pk="1" data-value="0" data-source="/ssl" data-original-title="Enter SMtP Auth" onclick="editable(this.id)">		
								 ${message.emailAlert}</a></td>
								 <td style="width:35%"><span class="text-muted">SMS Alert Enabled or Disabled for all Tasks to concerned users</span></td> 
							</tr>
							 
							<tr>
								<td style="width:15%">Scheduled Email Alert</td>
								<td style="width:50%"><a href="#" id="emailScheduleAlert1" data-type="select" data-pk="1" data-value="0"  data-source="/ssl" data-original-title="Enter SMtP Auth" onclick="editable(this.id)">${message.emailScheduleAlert}</a></td>
							    <td style="width:35%"><span class="text-muted">Scheduled SMS Alert Enabled or Disabled for all Tasks to concerned users</span></td> 
							</tr>
						 
							 
							<tr>
							<td style="width:15%">Scheduled Email Trigger Time</td>
							<td>
							 <div class="form-group" id="timePicker" >
										
										<div class="col-md-3">
											<div class="input-group bootstrap-timepicker">
											
											<form:input path="emailScheduleTime" id="emailScheduleTime" class="form-control timepicker-24" type="text" autocomplete="off" placeholder="" name="emailScheduleTime" value="${message.emailScheduleTime}" readonly="readonly" style="width:100px;"></form:input>                                       
												<!-- <input type="text" class="form-control timepicker-24" id="emailScheduleTime1" readonly="readonly" style="width:100px;"> -->
												<span class="input-group-btn">
												<button type="button" class="btn default"><i class="fa fa-clock-o"></i></button>
												</span>
											</div>
										</div> 
									</div>
									</td>
									<td style="width:35%"><span class="text-muted">Time of the day when Scheduled Email will be Triggered</span></td> 
                                         </tr>
						</tbody>
					
					</table>
					
					
								
					</form:form>
				</div>
			</div>
		
			<!-- END PAGE CONTENT-->
		</div>
 
	 