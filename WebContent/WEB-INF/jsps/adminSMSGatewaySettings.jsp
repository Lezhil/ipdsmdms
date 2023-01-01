<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <script src="<c:url value='/resources/bsmart.lib.js/formEditSettings.js'/>" type="text/javascript"></script>  
 
 
  <script type="text/javascript">

	  		
	 $(".page-content").ready(function()
			   {    	
			   /*  	App.init();
			    	FormComponents.init();	    	
			    	$('#ADMINSideBarContents,#gate-way,#smsgateway').addClass('start active ,selected'); 	 */

			    	
			    	App.init();
					TableManaged.init();
					FormComponents.init();			
					 $('#ADMINSideBarContents,#smsgateway').addClass('start active ,selected');
					$("#MDASSideBarContents,#MDMSideBarContents,#alarmID,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
			    	
			    	
			    	var schedule = document.getElementById("smsScheduleAlert").value;
			    	 
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
 				
 			}
	</script>
 
		<div class="page-content" >
     <%@include file="pagebreadcrum.jsp" %>
			<div class="row">
				<div class="col-md-12">
					<div class="alert alert-info">
				To change please <strong>double click</strong> on the text and change the text. To save click on update settings
							
							</div>
					<!-- <button id="enable" class="btn blue">Enable / Disable</button> -->
					<hr>
				</div>
			</div>
			<c:if test = "${results ne 'notDisplay'}"> 			        
			        <div class="alert alert-danger display-show">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${results}</span>
						</div>
			        </c:if>
			
			<div class="row">
				<div class="col-md-12">
				<form:form action="./updateSmsGatewaySettings" modelAttribute="smsGatewaySettings" commandName="smsGatewaySettings" method="post" id="smsGatewaySettings">
				 <button class="btn blue" formaction="./updateSmsGatewaySettings">Update Settings</button>
					<table id="" class="table table-bordered table-striped">
					 <tbody>
						<tr hidden="hidden">
						<td><form:input path="id" id="id" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="id" value="${element.id}"></form:input></td></td>
						<td><form:input path="username" id="username" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="username" value="${element.username}"></form:input></td></td> 
						<td><form:input path="password" id="password" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="password" value="${element.password}"></form:input></td></td>
						<td><form:input path="url" id="url" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="url" value="${element.url}"></form:input></td></td>
						<td><form:input path="mobileno" id="mobileno" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="mobileno" value="${element.mobileno}"></form:input></td></td>
						<td><form:input path="senderId" id="senderId" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="senderId" value="${element.senderId}" ></form:input></td></td>
						 </tr>
					  			<tr>
								<td style="width:15%">SMS Gateway User Name</td>
								<td style="width:50%"><a href="#" id="username1" data-type="text" data-pk="1" data-original-title="Enter username" onclick="editable(this.id)">		
								 ${element.username}</a></td>
								 <td style="width:35%"><span class="text-muted">Enter SMS Gateway UserName</span></td> 
							</tr>
							 
							<tr>
								<td style="width:15%">SMS Gateway Password</td>
								<td style="width:50%"><a href="#" id="password1" data-type="text" data-pk="1" data-original-title="Enter password" onclick="editable(this.id)">${element.password}</a></td>
							    <td style="width:35%"><span class="text-muted">SMS Gateway Password</span></td> 
							</tr>
						 
							<tr>
								<td style="width:15%">SMS URL</td>
								<td style="width:50%"><a href="#" id="url1" data-type="text" data-pk="1" data-original-title="Enter URL" onclick="editable(this.id)">${element.url}</a></td>
								 <td style="width:35%"><span class="text-muted">Enter SMS URL</span></td> 
							</tr>
							 
							<tr>
								<td>Mobile Number</td>
								<td style="width:50%"><a href="#" id="mobileno1" data-type="text" data-pk="1" data-placement="right" data-placeholder="Required" data-original-title="Enter your Mobile Number" onclick="editable(this.id)">${element.mobileno}</a></td>
								 <td><span class="text-muted">Enter Mobile Number</span></td> 
							</tr>
							 
							<tr>
								<td>Sender ID</td>
								<td style="width:50%"><a href="#" id="senderId1" data-type="text" data-pk="1"  data-original-title="Enter ID" onclick="editable(this.id)">${element.senderId}</a></td>
								 <td><span class="text-muted">Sender Id <strong>No buttons</strong> mode</span></td> 
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
				
					<form:form action="./updateMessageSettings" modelAttribute="MessageSettings" commandName="MessageSettings" method="post" id="MessageSettings">  
					<button class="btn blue" formaction="./updateMessageSettings">SMS Alert Settings</button>
					<table id="" class="table table-bordered table-striped">
					
						<tbody>
						<tr hidden="hidden">
						<td><form:input path="id" id="id" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="id" value="${message.id}"></form:input></td></td>
						<td><form:input path="smsAlert" id="smsAlert" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="smsAlert" value="${message.smsAlert}"></form:input></td></td> 
						<td><form:input path="smsScheduleAlert" id="smsScheduleAlert" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="smsScheduleAlert" value="${message.smsScheduleAlert}"></form:input></td></td> 
						<td><form:input path="emailAlert" id="emailAlert" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="emailAlert" value="${message.emailAlert}"></form:input></td></td> 
						<td><form:input path="emailScheduleAlert" id="emailScheduleAlert" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="emailScheduleAlert" value="${message.emailScheduleAlert}"></form:input></td></td> 
						
						<td><form:input path="emailScheduleTime" id="emailScheduleTime" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="emailScheduleTime" value="${message.emailScheduleTime}"></form:input></td></td>
						</tr> 
					 
							<tr>
								<td style="width:15%">Live SMS Alert</td>
								<td style="width:50%"><a href="#" id="smsAlert1" data-type="select" data-pk="1" data-value="0" data-source="/groups" data-original-title="Enter SMtP Auth" onclick="editable(this.id)">		
								 ${message.smsAlert}</a></td>
								 <td style="width:35%"><span class="text-muted">SMS Alert Enabled or Disabled for all Tasks to concerned users</span></td> 
							</tr>
							 
							<tr>
								<td style="width:15%">Scheduled SMS Alert</td>
								<td style="width:50%"><a href="#" id="smsScheduleAlert1" data-type="select" data-pk="1" data-value="0"  data-source="/groups" data-original-title="Enter SMtP Auth" onclick="editable(this.id)">${message.smsScheduleAlert}</a></td>
							    <td style="width:35%"><span class="text-muted">Scheduled SMS Alert Enabled or Disabled for all Tasks to concerned users</span></td> 
							</tr>
						 
							 
							<tr>
							<td style="width:15%">Scheduled SMS Trigger Time</td>
							<td>
							 <div class="form-group" id="timePicker" >
										
										<div class="col-md-3">
											<div class="input-group bootstrap-timepicker"> 
											<form:input path="smsScheduleTime" id="smsScheduleTime" class="form-control timepicker-24" type="text" autocomplete="off" placeholder="" name="smsScheduleTime" value="${message.smsScheduleTime}" readonly="readonly" style="width:100px;"></form:input>                                      
												<!-- <input type="text" class="form-control timepicker-24" readonly="readonly" style="width:100px;"> -->
												<span class="input-group-btn">
												<button type="button" class="btn default"><i class="fa fa-clock-o"></i></button>
												</span>
											</div>
										</div> 
									</div>
									</td>
									<td style="width:35%"><span class="text-muted">Time of the day when Scheduled SMS will be Triggered</span></td> 
                                         </tr>
						</tbody>
					
					</table>
					
					
								
					</form:form>
				</div>
			</div>
		   
			<!-- END PAGE CONTENT-->
		</div>
 
	
 