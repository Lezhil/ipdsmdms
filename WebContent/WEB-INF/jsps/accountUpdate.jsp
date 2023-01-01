 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {  
			  $("#accNo").val('0');
			  $("#newaccNo").val('');
   	    	     App.init();
   	    	  TableEditable.init();
   	    	 FormComponents.init();
   	    	$('#billingParameters').addClass('start active ,selected');
   	    	$('#fromDate').val('${currentDate}');
   	   });
  
  function validation()
  {
	 
	  if($("#accNo").val()==0)
		  {
		    bootbox.alert('Please Select MR.');
		    return false;
		  }
	
	  if($("#newaccNo").val()=="" || $("#newaccNo").val()==null)
	  {
	    bootbox.alert('Please Enter Account No.');
	    return false;
	  }
  }
</script>

<div class="page-content">
  
  <div class="row">
				<div class="col-md-12">
				<c:if test="${results ne 'notDisplay'}">
				<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${results}</span>
				</div>
			</c:if>
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Account No Update</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								</div>
						</div>					
											
				<div class="portlet-body">
				<div class="row" style="margin-left:-1px;">
				<form>
					<table style="width: 53%">
						<tbody>
						<tr>
							<td>
							<select class="form-control select2me input-medium" name="accNo" id="accNo">
						 	   <option value="0">select</option>
								<c:forEach items="${accountNos}" var="element">
								<option value="${element}">${element}</option>
								</c:forEach>
					   		</td>
					   		<td>
					   		<input type="text" id="newaccNo" class="form-control input-medium" name="newaccNo" placeholder="Enter New AccNO">
					   		</td>
					   	
						    <td><button type="submit" id="dataview" class="btn purple" onclick="return validation();" formaction="./UpdateNewAccNo" style="margin-left: 105px;"><b>Update AccNo</b></button></td>
						</tr>
					</tbody></table>
				</form>
				</div>
				</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
  
  </div>