  <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {
   //onload=noBack();
	  $("#datedata").val(getPresentMonthDate('${selectedMonth}'));
   	       App.init();
   	    	TableManaged.init();
   	    	FormComponents.init();
   	    	  /* Index.init();
   		   Index.initJQVMAP(); // init index page's custom scripts
   		   Index.initCalendar(); // init index page's custom scripts
   		   Index.initCharts(); // init index page's custom scripts
   		   Index.initChat();
   		   Index.initMiniCharts();
   		   Index.initDashboardDaterange();
   		   Tasks.initDashboardWidget();
   		   FormComponents.init();
   		   Charts.init();
		   Charts.initCharts();
		   Charts.initPieCharts() */;
		$('#dash-board').addClass('start active ,selected');
   	    	 $("#360d-view,cumulative-Analysis,cmri-Manager,seal-Manager,cdf-Import,system-Security,meter-Observations,interval-DataAnalyzer,events-Analyzer,exceptions-Analyzer,Load-SurveyAnalyzer,instantaneous-Parameters,VEE-RulesEngine,Assessment-Reports,MIS-Reports").removeClass('start active ,selected');
   	    	   });
  
  
 
/*
    
  function disableBackButton()
  {
  window.history.forward()
  } 
  disableBackButton()
  window.onload=disableBackButton(); 
  window.onpageshow=function(evt) 
  { if(evt.persisted) 
	  disableBackButton() 
  } 
  window.onunload=function() { void(0) }  
    */
  
  </script>
<div class="page-content" >
	<!-- BEGIN DASHBOARD STATS -->
	
	
	
	<div class="row">

	<!-- <img id="ïmg1" src="resources/assets/img/home/mdm.jpg" alt="" style="margin-left: -15px;margin-top: -149px;margin-bottom: 0px;">
	  -->
	
	<div id="containerid">
   <!--  <img src="resources/assets/img/home/i1.jpg" width=1150px;  height=600px;>
    <img src="resources/assets/img/home/i2.jpg" width=1150px;  height=600px;> -->
    <img src="resources/assets/img/home/i3.jpg" width=1150px;  height=600px;>
   <!--  <img src="resources/assets/img/home/mdm.jpg" width=1150px;  height=600px;> -->
</div> 
	</div>
	</div>
	
	
	<style>
	
	
	/* set the container to be relative position, and the images to be absolute so they stack ontop of each other.
	 */
	#containerid {
  position:relative;
  height:610px;
  width:610px;
}
#containerid img {
  position:absolute;
  left:0;
}

/* Then add your keyframes, with the different browser variations */

@-webkit-keyframes imgFade {
 0% {
   opacity:1;
 }
 17% {
   opacity:1;
 }
 25% {
   opacity:0;
 }
 92% {
   opacity:0;
 }
 100% {
   opacity:1;
 }
}

@-moz-keyframes imgFade {
 0% {
   opacity:1;
 }
 17% {
   opacity:1;
 }
 25% {
   opacity:0;
 }
 92% {
   opacity:0;
 }
 100% {
   opacity:1;
 }
}

@-o-keyframes imgFade {
 0% {
   opacity:1;
 }
 17% {
   opacity:1;
 }
 25% {
   opacity:0;
 }
 92% {
   opacity:0;
 }
 100% {
   opacity:1;
 }
}

@keyframes imgFade {
 0% {
   opacity:1;
 }
 17% {
   opacity:1;
 }
 25% {
   opacity:0;
 }
 92% {
   opacity:0;
 }
 100% {
   opacity:1;
 }
}

/*Then your animation details for all browsers  */

#container img {
  -webkit-animation-name: imgFade;
  -webkit-animation-timing-function: ease-in-out;
  -webkit-animation-iteration-count: infinite;
  -webkit-animation-duration: 8s;

  -moz-animation-name: imgFade;
  -moz-animation-timing-function: ease-in-out;
  -moz-animation-iteration-count: infinite;
  -moz-animation-duration: 8s;

  -o-animation-name: imgFade;
  -o-animation-timing-function: ease-in-out;
  -o-animation-iteration-count: infinite;
  -o-animation-duration: 8s;

  animation-name: imgFade;
  animation-timing-function: ease-in-out;
  animation-iteration-count: infinite;
  animation-duration: 8s;
}

#container img:nth-of-type(1) {
  -webkit-animation-delay: 6s;
  -moz-animation-delay: 6s;
  -o-animation-delay: 6s;
  animation-delay: 6s;
}
#container img:nth-of-type(2) {
  -webkit-animation-delay: 4s;
  -moz-animation-delay: 4s;
  -o-animation-delay: 4s;
  animation-delay: 4s;
}
#container img:nth-of-type(3) {
  -webkit-animation-delay: 2s;
  -moz-animation-delay: 2s;
  -o-animation-delay: 2s;
  animation-delay: 2s;
}
#container img:nth-of-type(4) {
  -webkit-animation-delay: 0;
  -moz-animation-delay: 0;
  -o-animation-delay: 0;
  animation-delay: 0;
}
</style>
	
	
	
	