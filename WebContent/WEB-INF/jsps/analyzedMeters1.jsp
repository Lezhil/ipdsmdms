  <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {     
   	    	     App.init();
   	    	 	 FormComponents.init();
   	    	   $('#MIS-Reports').addClass('start active ,selected');
   	    	   $("#admin-location,#dash-board").removeClass('start active ,selected');
   	    	     
   	    	   });
  function sendToReport() {
		
	   open(
				"./reportGenerate",
				"active",
				'width=440, height=440, toolbar=no, location=no, resizable=yes,scrollbars=yes, directories=no,status=no, titlebar=no');
//window.open("jsps/reportGenerator.jsp");
	}

  
  </script>
<div class="page-content" >
<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Meter Master</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								</div>
						</div>					
											
						<div class="portlet-body">
							
							<form action="./simpleReport">
							From Date : 
							<div class="input-group input-medium date date-picker" data-date-format="dd-mm-yyyy" data-date-viewmode="years">
							<input type="text" class="form-control" name="reportFromDate" id="reportFromDate"  readonly>
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div>	
							
							To Date : 
							<div class="input-group input-medium date date-picker" data-date-format="dd-mm-yyyy" data-date-viewmode="years">
							<input type="text" class="form-control" name="reportToDate" id="reportToDate"  readonly>
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div>
							
							<div class="modal-footer">
							<button class="btn blue pull-left">Download Report</button>  
							
							</div>
							</form>								
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>


			
			<div  class="row">	
			<c:if test = "${result eq 'reportGenerator'}"> 		        
			<%@include file="./reportGenerator.jsp" %>
			</c:if>
			</div>
			

</div>