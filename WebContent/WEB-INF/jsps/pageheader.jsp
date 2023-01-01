
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "java.util.ResourceBundle" %>
		 
		  <%if(session.getAttribute("username")==null || session.getAttribute("username")==null){ %>
		<script>
		window.location.href="./?sessionVal=expired";
		</script>
		<%} %> 
		 
		 
	<script>
	var officeType='<%=session.getAttribute("officeType")%>';
	var officeCode='<%=session.getAttribute("officeCode")%>';
	var officeName='<%=session.getAttribute("officeName")%>';
	/* var session='';
	session = "${officeType}";
	if(session=="circle"){
		$("#zone").hide();
		$("#zones").hide();
		//write javascript function 
		getCircle('${officeCode}');
		//$("#circleTd").hide();
	} */
		function getPresentMonthDate(param){			
			var date = new Date();
			
			var month = date.getMonth()+1;	
			if(month<10)
				month="0"+month;
			var year = date.getFullYear();
			if(param==null || param=="")
				return currentDate = year+""+month;
			else
				return param;
					
		}
		
		
		/* function disableBackButton()
		  {
		  window.history.forward()
		  } 
		  disableBackButton()
		  window.onload=disableBackButton(); 
		  window.onpageshow=function(evt) 
		  
		  { if(evt.persisted) 
			  disableBackButton() 
		  }  */
		  
		  window.onunload=function() { void(0); };
		
		  
		  function loadSearchAndFilter(param) 
		  	{ 
		  		$('#'+param).dataTable().fnDestroy(); 
		  		$('#'+param).dataTable(
		  				 { "aLengthMenu": [
				                    [20, 50, 100, -1],
				                    [20, 50, 100, "All"] // change per page values here
				                ],
				                "iDisplayLength": 20
			  }); 
	      /*    $('#'+param).DataTable(
		   	  	    { "ordering": false }
		   		
			  );  */ 
		  		jQuery('#'+param+'_wrapper .dataTables_filter input').addClass("form-control input-small"); // modify table search input 
		  		jQuery('#'+param+'_wrapper .dataTables_length select').addClass("form-control input-xsmall"); // modify table per page dropdown 
		  		jQuery('#'+param+'_wrapper .dataTables_length select').select2(); // initialize select2 dropdown 
		  	}
		  
		  function loadSearchAndFilter1(param) 
			{ 
				$('#'+param).dataTable().fnDestroy(); 
				$('#'+param).dataTable(
						 { "aLengthMenu": [
			                    [5, 10, 20, 50, 100, -1],
			                    [5, 10, 20, 50, 100, "All"] // change per page values here
			                ],
			                "iDisplayLength": 5
		  }); 
				jQuery('#'+param+'_wrapper .dataTables_filter input').addClass("form-control input-small"); // modify table search input 
				jQuery('#'+param+'_wrapper .dataTables_length select').addClass("form-control input-xsmall"); // modify table per page dropdown 
				jQuery('#'+param+'_wrapper .dataTables_length select').select2(); // initialize select2 dropdown 
			}
		  function clearTabledataContent(tableid) {
				//TO CLEAR THE TABLE DATA
				var oSettings = $('#' + tableid).dataTable().fnSettings();
				var iTotalRecords = oSettings.fnRecordsTotal();
				for (i = 0; i <= iTotalRecords; i++) {
					$('#' + tableid).dataTable().fnDeleteRow(0, null, true);
				}

			} 
	</script>
	
	<script type="text/javascript"> 
		function display_c(){
		var refresh=1000; // Refresh rate in milli seconds
		mytime=setTimeout('display_ct()',refresh)
		}
		
		function display_ct() {
			var x = new Date();
			var month = x.getMonth() + 1;
			var x1=x.getDate() + "-" + month + "-" +  x.getFullYear(); 
			x1 = x1 + " " +  x.getHours( )+ ":" +  x.getMinutes() + ":" +  x.getSeconds();
			document.getElementById('ct').innerHTML = x1;
			display_c();
			}

		 
</script>

	<div class="header navbar navbar-inverse navbar-fixed-top">
		<div class="header-inner">			
		
		
		<%-- 	<a class="navbar-brand" href="#" style="cursor: default;">			
			<!-- <font color="white" size="5">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;BSmart</font><font color="red" size="5">MDM</font> -->
		<img src="<c:url value='resources/assets/img/bsmart-mdm1.png'/>" alt="logo" class="img-responsive" style="margin: -13px 118px 0px auto;height: 43px;width: 102px;" />
			</a>	 --%>
			
			<a class="navbar-brand" href="./dashBoard2MDAS?type=${officeType}&value=${officeName}">
				<img src="resources/assets/img/bsmart-logo.png" alt="logo" class="img-responsive" />
			</a>
					
			<%-- <a href="javascript:;" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
			<img src="<c:url value='/resources/assets/img/menu-toggler.png'/>" alt="" />
			</a> --%>
			
			<% ResourceBundle resource = ResourceBundle.getBundle("messages");
				 String header=resource.getString("pageheader");
				 String logopath=resource.getString("pageheaderlogo");
			%>
<!-- 			<a href="#" class="companyName"><img src="resources/assets/img/tneb_logo.jpg" alt="" /><span class="title" style="color: white;">Tamil Nadu Generation and Distribution Corporation Limited</span></a>
 -->			<a href="#" class="companyName"><img src="<%=logopath%>" alt="" /><span class="title" style="color: white;"><%=header %></span></a>
			<!-- <span style="display: inline-block; margin-top: 6px;width: 58%;text-align: center;"><font style="color: white;font-size: 15pt;font-weight: bold;margin-left: 24%;">Jaipur Vidyut Vitran Nigam Limited</font></span> -->
			
			<%if(session.getAttribute("userType")!=null ){ %>
			<%String userType1 = (String)session.getAttribute("userType");%>			
			<% if(userType1.equalsIgnoreCase("admin") || userType1.equalsIgnoreCase("ADMIN")) { %> 			
			
			
			
			
			
			<ul class="nav navbar-nav pull-right">							
				<a href="#" style="margin-left: -265px; font-size: 15px; color: white;">
					Current Time: <span id="ct" onload=display_ct();></span>
					<script type="text/javascript">window.onload = display_ct();</script>
				</a>
			</ul>
			<ul class="nav navbar-nav pull-right" style="padding-top: 4px;">				
				
				<li class="">
					<a href="#" style="font-size: 15px;">		
						Welcome <span class="username">${name}</span> !	
					</a>	
				</li>
				<li>
					<a href="./logout" class="fa fa-power-off" aria-hidden="true" title="Logout" id="logOut"></a>
				</li>
			   

				
				<!-- <li class="">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					
					Welcome <span class="username">User</span>
					
					</a>
					<li>
					<a href="./logout" title="Logout" id="logOut"><i class="fa fa-power-off" aria-hidden="true"></i>Log Out</a>
				</li> -->
					<!-- <ul class="dropdown-menu">
										
						 <li ><a href="./profile" id="profile" ><i class="fa fa-user"></i> My Profile</a>
						</li>
						
						<li ><a href="javascript:;" id="trigger_fullscreen" ><i class="fa fa-move"></i> Full Screen</a>
						</li>
						<li > <a href="./lockScreen" id="lock" ><i class="fa fa-lock"></i> Lock Screen</a>
						</li>
						<li ><a href="./logout" id="logOut" ><i class="fa fa-key"></i> Log Out</a>
						</li>
					</ul> -->
				<!-- </li> -->				
			</ul>
			<%}else{%>	
			<ul class="nav navbar-nav pull-right">				
				<%-- <%String userLevel = (String)session.getAttribute("userLevel");%>
				<% if (!userLevel.equalsIgnoreCase("TEAMMEMBER")) { %>
				
				<li>
				<div align="right">
				<div class="form-group">
				 <a href="#" onclick="inboxOpen(this.id)" id="inbox">
			      <img src="<c:url value='/resources/bsmart.lib.js/ask1.jpg'/>" alt="" style="height: 41px;width:45px;" />
			      </a>
			      </div>
			      </div>
				</li>
				<%} %> --%>
				
	
				<li class="dropdown user">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<%-- <img alt="" src="<c:url value='/resources/assets/img/avatar1_small.jpg'/>"/> --%>
					Welcome <span class="username">${name}</span>!
					<i class="fa fa-angle-down"></i>
					</a>
					
					<ul class="dropdown-menu">
						<c:if test = "${userType eq 'ADMIN'}"> 				
						 <li ><a href="./myProfile" id="profile" ><i class="fa fa-user"></i> My Profile</a>
						</li>
						</c:if>					
						
						<!-- <li ><a href="./changepwd" id="schedule" ><i class="fa fa-user"></i>Change Password</a>
						</li>  -->
						<!-- <li ><a href="#" id="logs" ><i class="fa fa-envelope"></i> My DailyLogs </a><span class="badge badge-danger">3</span>
						</li>
						<li ><a href="#" id="task" ><i class="fa fa-tasks"></i> My Tasks </a><span class="badge badge-success">7</span>
						</li>	 					
						<li class="divider"></li>-->
						<li ><a href="javascript:;" id="trigger_fullscreen" ><i class="fa fa-move"></i> Full Screen</a>
						</li>
						<!-- <li > <a href="./lockScreen" id="lock" ><i class="fa fa-lock"></i> Lock Screen</a>
						</li> -->
						<li ><a href="./logout" id="logOut" ><i class="fa fa-key"></i> Log Out</a>
						</li>
					</ul>
				</li>				
			</ul>
			<%} }%>
		
		</div>		
	</div>	
	<%-- <div id="stack4" class="modal fade" tabindex="-1" data-width="400">
							 <div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
								        <h4 class="modal-title">Compose</h4>
										</div>
										
										<div class="modal-body">
											<div class="row">
												<div class="col-md-12">
													<form action="./askToUser" id="ask">
													<input type="hidden"  id="askEmpId" name="askEmpId"/>	  
													<table >
													<tr>
													<td>Project
													</td>
													<td id="askProjectData">
													<select  id="askProject" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="askProject">
																	<option value="0">select</option>
																	<c:forEach items="${projectList}" var="element" >
																 <option value="${element.id}">${element.projectName}</option>
																	</c:forEach>
																	
																	</select>
													<input type="hidden" id="askProject" name="askProject">
													
													</td>
													</tr>
													<tr>
													<td>Department
													</td>
													<td id="askDepartmentData">
													<input type="hidden" id="askDepartment" name="askDepartment">
													<select  id="askDepartment" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="askDepartment" onchange="return showList();">
																	<option value="0">select</option>
																	<c:forEach items="${departmentList}" var="element" >
																 <option value="${element.id}">${element.departmentName}</option>
																	</c:forEach>
																	
																	</select>
													</td >
													</tr>
													<tr  id="tableData2"> 
													</tr>
													
													<tr>
													<td>Subject</td>
													<td><input type="text" class="form-control placeholder-no-fix" id="subject" name="subject" value=""></input></td>
													</tr>													
													<tr>
													<td></td>
													<td><textarea  class="form-control placeholder-no-fix"  style="width: 434px; height: 222px;" id="content" name="content" ></textarea></td>
													</tr>
													</table>
			 											<div class="modal-footer">
															<button type="button" data-dismiss="modal" class="btn">Close</button>
															<button class="btn blue pull-right" onclick = "return validateTaskStatus()">Send</button>	
															</div>
															
															</form>	
			 				
												</div>
											</div>
											 
										</div>
										
									</div>
								</div>
							</div> --%>
	<div class="clearfix"></div>
	
<script  type="text/javascript">

var load_date=new Date();
var load_time;
  $(".page-content").ready(function()
   {     
	  load_time=moment(load_date).format('YYYY-MM-DD hh:mm:ss');
	  //alert(load_time);
	  //load_time="2018-12-12 12:35:11";
	 /*  toastr.options = {
	    "closeButton": true,
	    "debug": false,
	    "positionClass": "toast-top-right",
	    "onclick": null,
	    "showDuration": "1000",
	    "hideDuration": "1000",
	    "timeOut": "5000",
	    "extendedTimeOut": "1000",
	    "showEasing": "swing",
	    "hideEasing": "linear",
	    "showMethod": "fadeIn",
	    "hideMethod": "fadeOut"
	  }; */
	  toastr.options = {
			  "closeButton": true,
			  "debug": false,
			  "newestOnTop": true,
			  "progressBar": false,
			  "positionClass": "toast-top-right",
			  "preventDuplicates": false,
			  "onclick": null,
			  "showDuration": "300",
			  "hideDuration": "1000",
			  "timeOut": "5000",
			  "extendedTimeOut": "1000",
			  "showEasing": "swing",
			  "hideEasing": "linear",
			  "showMethod": "fadeIn",
			  "hideMethod": "fadeOut"
			};
	  
	  
	  //showToast();
	  
	  
   });	 
  
  function showToast(){
	  //alert(load_time);
	  setInterval(function () {
		  $.ajax({
	    		url : "./getMeterAlarmByDate",
	    		data:{
	    			load_time : load_time,
	    		},
	    		type : "GET",
	    		dataType : "JSON",
	    		async : true,
	    		success : function(response) {
	    			
	    			if(response.length>0){
	    				for(var i=0;i<response.length;i++){
		    				var obj=response[i]; 
		    				var alerm_time=moment(obj[4]).format('YYYY-MM-DD hh:mm:ss');
		    				var t=obj[5]+" in Meter No : "+obj[1]+" ,at : "+alerm_time;
		    				
		    				toastr.warning(t, "Meter Alarms");
		    						    				
		    				if(i==(response.length-1)){
		    					load_time=alerm_time;
		    				}
		    			}
	    			}
	    			
	    		}
	      	}); 
	    },5000);
  }
	  
	  //toastr.warning("fsdfdsf", "Meter Alarms");
  
  
  
  
  </script>
  
  <style>
/*   .header.navbar-fixed-top {
    z-index: 9995 !important;
    background-color: #0091EA !important;
} */
  
.header {

    filter: none !important;
    background-image: none !important;
    background-color: #2b3643 !important;

} 
  
</style>
	
	