<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>   
  <script src="<c:url value='/resources/bsmart.lib.js/formEditSettings.js'/>" type="text/javascript"></script>  
	<script type="text/javascript">
	 $(".page-content").ready(function()
			   {    	
			    	App.init();
			    	FormComponents.init();	        	
			    	 $('#MDMSideBarContents,#alarmID,#networkgateway').addClass('start active ,selected');
			    	/* $('#ADMINSideBarContents,#gate-way,#networkgateway').addClass('start active ,selected'); */  	
			    	$('#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment').removeClass('start active ,selected');
			    	
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
				<form:form action="./updateadminNetworkGateWaySettings" modelAttribute="networkgateway" commandName="networkgateway" method="post" id="networkgateway">
				
				<button class="btn blue" formaction="./updateadminNetworkGateWaySettings">Update Settings</button>
				
					<table id="user" class="table table-bordered table-striped">
						<tbody>
						<tr hidden="hidden">
						<td><form:input path="id" id="id" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="id" value="${element.id}"></form:input></td></td>
						<td><form:input path="folder_path" id="folder_path" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="folder_path" value="${element.folder_path}"></form:input></td></td>
						<td><form:input path="folder_name" id="folder_name" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="folder_name" value="${element.folder_name}"></form:input></td></td>
						<td><form:input path="final_path" id="final_path" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="final_path" value="${element.final_path}"></form:input></td></td>
						</tr>
						
						 
							<tr>
								<td style="width:15%">Folder Path</td>
								<td style="width:50%"><a href="#" id="folder_path1" data-type="text" data-pk="1" data-original-title="Enter SMTP Host" onclick="editable(this.id)">${element.folder_path} </a></td>
								 <td style="width:35%"><span class="text-muted">Enter Folder Path</span></td> 
							</tr>
							<tr>
								<td style="width:15%">Folder Name</td>
								<td style="width:50%"><a href="#" id="folder_name1" data-type="text" data-pk="1" data-original-title="Enter SMTP Port" onclick="editable(this.id)">${element.folder_name}</a></td>
							    <td style="width:35%"><span class="text-muted">Enter Folder Name</span></td> 
							</tr>
							
							
							<tr>
								<td>Full Path Name</td>
								<td style="width:50%">${element.final_path}</td>
								 <td><span class="text-muted">Full Path </td> 
							</tr>
							
						
							
							
							
						</tbody>
					</table>
					
					</form:form>
				</div>
			</div>
			

		
			<!-- END PAGE CONTENT-->
		</div>
 
	 