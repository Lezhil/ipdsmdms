

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<link href="resources/assets/global/css/components.css" id="style_components" rel="stylesheet" type="text/css"/>


<style>

.col-xs-5{
width: auto;
}
.dropdown-menu>li>a>.badge {
    position: absolute;
    margin-top: 1px;
    right: 10px;
    display: inline;
    font-size: 14px ! important;
    height: 18px ! important;
    font-weight: bold;
    padding: 2px 5px 0px 5px ! important;
}
 
</style>

<script>

$(".page-content") .ready(
			function() {
				
			
				Index.initMiniCharts();
				TableEditable.init();
				FormComponents.init();

				
				$('#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
						.removeClass('start active ,selected');

				App.init();			
						
		});
</script>

<img alt="image" src="./resources/assets/img/transmissionlines.jpg"
							style="width: 83%;  align-content: center;margin-left: 229px;" border="1">