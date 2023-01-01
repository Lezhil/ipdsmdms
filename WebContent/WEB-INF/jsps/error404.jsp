<!DOCTYPE html>
<html lang="en" class="no-js">

<head>
	<meta charset="utf-8" />
	         
	<link href="resources/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>	
	<link href="resources/assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/css/plugins.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>
	<link href="resources/assets/css/pages/error.css" rel="stylesheet" type="text/css"/>
	<link href="resources/assets/css/custom.css" rel="stylesheet" type="text/css"/>
	<!-- END THEME STYLES -->
	<link rel="shortcut icon" href="favicon.ico" />
</head>
<body class="page-404-full-page">
	<div class="row">
		<div class="col-md-12 page-404">
			<div class="number">
				404
			</div>
			<div class="details">
				<h3>Oops!  You're lost.</h3>
				<p>				
					We can not find the page you're looking for.<br />
					<!-- <a  onclick="linkSearch();">Return home</a> or try the search bar below. -->
				</p>				
			</div>
		</div>
	</div>	
	<script src="resources/assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="resources/assets/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
	<script src="resources/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="resources/assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js" type="text/javascript" ></script>
	<script src="resources/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="resources/assets/plugins/jquery.blockui.min.js" type="text/javascript"></script>  
	<script src="resources/assets/plugins/jquery.cookie.min.js" type="text/javascript"></script>
	<script src="resources/assets/plugins/uniform/jquery.uniform.min.js" type="text/javascript" ></script>
	
	<script src="resources/assets/scripts/app.js"></script>  
	<script>
		jQuery(document).ready(function() {    
		   App.init();			  
		});
	</script>
	<script>
	function linkSearch()
	{		
	   var previousPath='<%=(String)session.getAttribute("path") %>';
	   <%session.removeAttribute("path");%>	   
	   if(previousPath=='/' || previousPath=='/logout' || previousPath=='' || previousPath=='null')
		  {		   
		   window.location.href = "./";
		  }
	 else
		 {
	  window.location.href = "."+previousPath;	
		 }
	}
	</script>
	
</body>
</html>