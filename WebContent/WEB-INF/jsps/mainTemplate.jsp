<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <!DOCTYPE html>
<html>
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
<head>
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
 <meta content="width=device-width, initial-scale=1.0" name="viewport" />
 <meta content="" name="description" />
 <meta content="" name="author" />
 <meta name="MobileOptimized" content="320"> 
  <%@include file="headerFiles.jsp" %>
<%-- <title>
	<tiles:insertAttribute name="title" ignore="true"></tiles:insertAttribute>
</title> --%>
</head>
<body class="page-header-fixed">
        	<tiles:insertAttribute name="header"></tiles:insertAttribute>
        	<div class="page-container">
        	<tiles:insertAttribute name="menu"></tiles:insertAttribute>
            <tiles:insertAttribute name="body"></tiles:insertAttribute>
            </div>
        	<tiles:insertAttribute name="footer"></tiles:insertAttribute>
        	
   <!-- END FOOTER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->   
	
</body>
</html>