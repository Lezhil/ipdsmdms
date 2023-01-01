
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import = "java.util.ResourceBundle" %>

<!DOCTYPE html>
<!-- 
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.0
Version: 1.5.3
Author: KeenThemes
Website: http://www.keenthemes.com/
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->


<script type="text/javascript">
history.pushState(null, null, 'login');
window.addEventListener('popstate', function(event) {
history.pushState(null, null, 'login');
});
</script>

<head>
	<meta charset="utf-8" />
	<title>TANGEDCO-MDM</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<meta name="MobileOptimized" content="320">
	<!-- BEGIN GLOBAL MANDATORY STYLES -->          
	<link href="<c:url value='/resources/assets/plugins/font-awesome/css/font-awesome.min.css' />" rel="stylesheet" type="text/css"/>
	<link href="<c:url value='/resources/assets/plugins/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css"/>
	<link href="<c:url value='/resources/assets/plugins/uniform/css/uniform.default.css' />" rel="stylesheet" type="text/css"/>
	<!-- END GLOBAL MANDATORY STYLES -->
	<!-- BEGIN PAGE LEVEL STYLES --> 
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/assets/plugins/select2/select2_metro.css' />" />
	<!-- END PAGE LEVEL SCRIPTS -->
	<!-- BEGIN THEME STYLES --> 
	<link href="<c:url value='/resources/assets/css/style-metronic.css'/>" rel="stylesheet" type="text/css"/>
	<link href="<c:url value='/resources/assets/css/style.css'/>" rel="stylesheet" type="text/css"/>
	<link href="<c:url value='/resources/assets/css/style-responsive.css'/>" rel="stylesheet" type="text/css"/>
	<link href="<c:url value='/resources/assets/css/plugins.css'/>" rel="stylesheet" type="text/css"/>
	<link href="<c:url value='/resources/assets/css/themes/default.css'/>" rel="stylesheet" type="text/css" id="style_color"/>
	<link href="<c:url value='/resources/assets/css/pages/login-soft.css'/>" rel="stylesheet" type="text/css"/>
	<link href="<c:url value='/resources/assets/css/custom.css'/>" rel="stylesheet" type="text/css"/>
	<!-- END THEME STYLES -->
	<link rel="shortcut icon" href="<c:url value='/resources/bsmart.lib.js/favicon.ico'/>" />
	<!-- refresh button -->
	 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	 
	
	 <head>
	<meta charset="utf-8" />
	<title>Metronic | Admin Dashboard Template</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<meta name="MobileOptimized" content="320">
	<!-- BEGIN GLOBAL MANDATORY STYLES -->          
	<link href="resources/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
	<!-- END GLOBAL MANDATORY STYLES -->
	<!-- BEGIN PAGE LEVEL PLUGIN STYLES --> 
	<link href="resources/assets/plugins/gritter/css/jquery.gritter.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css" />
	<link href="resources/assets/plugins/fullcalendar/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/plugins/jqvmap/jqvmap/jqvmap.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/plugins/jquery-easy-pie-chart/jquery.easy-pie-chart.css" rel="stylesheet" type="text/css"/>
	<!-- END PAGE LEVEL PLUGIN STYLES -->
	<!-- BEGIN THEME STYLES --> 
	<link href="resources/assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/css/plugins.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/css/pages/tasks.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>
	<link href="resources/assets/css/custom.css" rel="stylesheet" type="text/css"/>
	<!-- END THEME STYLES -->
	<link rel="shortcut icon" href="favicon.ico" />
</head>
	 
	 
	<script type="text/javascript">
	
	 
	 function clear()
	 {
		 
		 document.getElementById("login").reset();
	 }
    </script>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="login" onload="Captcha();">
	<!-- BEGIN LOGO -->	
	<!-- END LOGO -->
	<!-- BEGIN LOGIN -->
	
	<!-- END LOGIN -->
	<!-- BEGIN COPYRIGHT -->
	
	<!-- END COPYRIGHT -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->   
	
	<script src="<c:url value='/resources/assets/plugins/jquery-1.10.2.min.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/assets/plugins/jquery-migrate-1.2.1.min.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/assets/plugins/bootstrap/js/bootstrap.min.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js' />" type="text/javascript" ></script>
	<script src="<c:url value='/resources/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/assets/plugins/jquery.blockui.min.js' />" type="text/javascript"></script>  
	<script src="<c:url value='/resources/assets/plugins/jquery.cookie.min.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/assets/plugins/uniform/jquery.uniform.min.js' />" type="text/javascript" ></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="<c:url value='/resources/assets/plugins/jquery-validation/dist/jquery.validate.min.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/assets/plugins/backstretch/jquery.backstretch.min.js' />" type="text/javascript"></script>
	<script type="text/javascript" src="<c:url value='/resources/assets/plugins/select2/select2.min.js' />"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="<c:url value='/resources/assets/scripts/app.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/assets/scripts/login-soft.js' />" type="text/javascript"></script>      
	<script src="<c:url value='/resources/assets/plugins/bootbox/bootbox.min.js'/>" type="text/javascript" ></script>
	<!-- END PAGE LEVEL SCRIPTS --> 
	<script>
		jQuery(document).ready(function() {     
		  App.init();
		  Login.init();

		 
        $("#session_timeout").delay(4000).fadeOut(1600, function () {
            
        });
		 
		  
		});
	</script>
	
	
	
	<script type="text/javascript">
                 function Captcha(){
                  
                	 
                	 $("#pid").hide();
                	 $("#pid1").hide();
                	 $("#txtInput").val("");
                	 var alpha = new Array('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9');
                     var i;
                     for (i=0;i<6;i++){
                       var a = alpha[Math.floor(Math.random() * alpha.length)];
                       var b = alpha[Math.floor(Math.random() * alpha.length)];
                       var c = alpha[Math.floor(Math.random() * alpha.length)];
                       var d = alpha[Math.floor(Math.random() * alpha.length)];
                       var e = alpha[Math.floor(Math.random() * alpha.length)];
                       var f = alpha[Math.floor(Math.random() * alpha.length)];
                       var g = alpha[Math.floor(Math.random() * alpha.length)];
                      }
                    var code = a + ' ' + b + ' ' + ' ' + c + ' ' + d + ' ' + e + ' '+ f + ' ' + g;
                    document.getElementById("mainCaptcha").value = code;
                    $("#pid").style.display='none';
               	 
                  }
                
                 
                 
                 function ValidCaptcha(){
                	
                	 var logintype=$("#logintype").val();
                	 if(logintype==0)
                		 {
                		 $(".ltype").show();
                		 return false;
                		 }
                	 if(logintype!=0)
            		 {
            		 $(".ltype").hide();
            		 
            		 }
                      var string1 = removeSpaces(document.getElementById('mainCaptcha').value);
                      var string2 = removeSpaces(document.getElementById('txtInput').value);
                      if(string2=='')
                    	  {
                    	      $(".wrong1").show();
                    	  }
                      else
                    	  {
                    	  	  if (string1 == string2)
	                          {
	                        	  $("#login").submit();
	                          }
	                          else
	                          {        
	                        	  $(".wrong").show();
	                        	  $("#pid1").hide();
	                        	  document.getElementById('txtInput').value(' ');
	                        	  document.getElementById('txtInput').placeholder="Enter above Captcha";
	                        	  return false;
	                          }
                    	  }
                     
                  }
                  function removeSpaces(string){
                    return string.split(' ').join('');
                  } 
                  
                  function searchKeyPress(e)
                  {
                      e = e || window.event;
                      if (e.keyCode == 13)
                      {
                          document.getElementById('btnSearch').click();
                          return false;
                      }
                      return true;
                  }
             </script>
             
             
      <div class="landingPage">
		<div class="container" style="width:100%">
			<div class="row">
				<div class="col-md-12">
					<div class="branding">
					<% ResourceBundle resource = ResourceBundle.getBundle("messages");
						    String header=resource.getString("pageheader");
						    String logopath=resource.getString("pagelogo");
						 %>
						<!-- <h1><img src="resources/assets/img/JVVNL-Recruitment.png" alt="logo"> Jaipur Vidyut Vitran Nigam Limited</h1> -->
						<h1><img src="<%=logopath%>"alt="logo"><font style="font-size: 26pt;">
						
						   <%=header%> 
						  </font></h1>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-9 loginContainer">
					<div class="container-fluid">
						<div class="row">
							<div class="col-md-7 loginSide">
								
							</div>
							<div class="col-md-5 loginForm">
								 <form:form class="login-form" action="./login" modelAttribute="user" commandName="user" method="post" id="login" >
								<h2>Login</h2>
							<div class="form-group" hidden="true">
				<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
				<label class="control-label visible-ie8 visible-ie9">Login Type</label>
				
					
					<form:select path="" id="logintype" class="form-control" name="logintype">
					 <%-- <form:option value="0">Select LoginType</form:option>
					<form:option value="Jvvnl_Staff">JVVNL USERS</form:option> --%>
					<form:option value="Office_Staff">BCITS USERS</form:option>
					
					</form:select>
					 <p class="ltype" style="display : none; color:red;" id="pid1"> Please Select LoginType</p>
    				
			
			</div>
							
								<div class="input-group">
									<span class="input-group-addon" id="sizing-addon2"><i class="fa fa-user"></i></span>
								<!-- 	<input type="text" class="form-control" placeholder="Username" aria-describedby="sizing-addon2"> -->
								<form:input path="username" id="username" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="username" name="username" onkeydown ="return searchKeyPress(event);"></form:input>
								</div>
								<div class="input-group">
									<span class="input-group-addon" id="sizing-addon2"><i class="fa fa-lock"></i></span>
									<!-- <input type="password" class="form-control" placeholder="Username" aria-describedby="sizing-addon2"> -->
										<form:input path="userPassword"  class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="Password" onkeydown ="return searchKeyPress(event);"></form:input>
								</div>
								<div class="input-group">
								
								<input type="text" id="mainCaptcha" class="form-control placeholder-no-fix test" onkeydown ="return searchKeyPress(event);" style="width: 84%;text-align: center;" />
								<a href='#' class='pull-right ' style="margin-top:0px; margin-right: 0px;">
								<button type="button" class="btn" onclick="Captcha();" id="refresh" >
								<span class="   glyphicon glyphicon-refresh " ></span>
								</button>
								</a>
					
								</div>
								<div class="input-group">
					 			<!-- <input type="text" id="txtInput" class="form-control placeholder-no-fix test" type="text" placeholder="Enter above Captch" onkeydown ="return searchKeyPress(event);"/> --> 
									<input type="text" class="form-control"  placeholder="Enter above Captch" id="txtInput"  onkeydown ="return searchKeyPress(event);" >
								</div>
								<button type="button" class="btn btn-primary btn-block" id="btnSearch" onclick="return ValidCaptcha();">Login</button>
								
								<p class="wrong info" style="display : none; color:red;" id="pid">Entered Text Wrong!!!</p>
					 		   	<p class="wrong1 info" style="display : none; color:red;" id="pid1"> please Enter the above Text.</p>
					 		   	<c:if test = "${msg ne 'notDisplay'}"> 	
							        <div class="alert alert-danger display-show" id="session_timeout">
											<button class="close" data-close="alert"></button> 
											<span style="color:red" >${msg}</span>
									</div>
        						</c:if> 
								<div class="bSmart" >
									<img src="resources/assets/img/bsmart-logo.png" alt="logo" width="120px">
								</div>
								</form:form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="copyR">

						2019-2020 © BSmartMDM. Developed by <a href="http://www.bcits.co.in/">BCITS</a>

					</div>
				</div>
			</div>
		</div>
	</div>    
	
</body>

   <style>
#mainCaptcha{
       color:black;
       background-image: url("https://previews.123rf.com/images/molodec/molodec1211/molodec121101273/16328400-Crushed-paper-texture-of-old-paper-background--Stock-Photo.jpg");
       
      
     font-size: 40px; 
     font-size: large;
     text-align:justify;
    font-weight: bolder;
    }
     
   
    #mainCaptcha{
           width: 220px;
        }
        
        .error {
	color: #ff0000;
	 }
 
.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 8px;
	}
 
 .header {

    filter: none !important;
    background-image: none !important;
    background-color: #0091EA !important;
	height: 70px;
	box-shadow: -2px 3px 6px -1px rgba(0,0,0,0.6);
} 
.container-fluid {
    padding-right: 0px;
    padding-left: 0px;
    margin-right: 0px;
    margin-left: 0px;
}

.alert {
    padding: 5px;
    margin-bottom: 0px;
    border: 1px solid transparent;
        border-top-color: transparent;
        border-right-color: transparent;
        border-bottom-color: transparent;
        border-left-color: transparent;
    border-radius: 4px;
    margin-top: 10px;
}
</style>

<!-- END BODY -->
</html>