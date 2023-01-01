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
		   if('${subdivisionVal}'!='')
		   {
			 $('#datedata').val('${month}');
		     $('#s2id_subdivision .select2-chosen').html('${subdivisionVal}');
		     $('#subdivision').val('${subdivisionVal}');
		     //showSubdivGroupValue('${subdivisionVal}');
		     var value='${groupVal}';
    		$('#s2id_group .select2-chosen').html(value);
    		$('#group').val(value);
		     $('#s2id_category .select2-chosen').html('${categoryVal}');
		     $('#category').val('${categoryVal}');
		     $('#s2id_billingCategory .select2-chosen').html('${billingCatVal}');
		     $('#billingCategory').val('${billingCatVal}');
		     $('#alertDiv').fadeOut(4000); 
		   }
			$('#MDMSideBarContents,#MIS-Reports,#showAsciiVal').addClass('start active ,selected');
			 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
				"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
		
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
  function showSubdivGroupValue(subdiv)
    {
    	var month=$('#datedata').val();
    	$.ajax({
	    	url:'./showSubdivGroupValue'+'/'+subdiv+'/'+month,
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response)
	    	{
	    		if('${subdivisionVal}'!='')
	    		   {
	    			
	    		   }
	    		else
	    			{
		    			var html='';
			    		html+="<select id='group' name='group' class='form-control input-small' type='text'><option value='deafult' >Select Group</option>";
						for( var i=0;i<response.length;i++)
						{
							html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
						}
						html+="</select><span></span>";
						$("#groupTd").html(html);
						$('#group').select2();
	    			}
	    		
	    	}
  		});
    	
    	/* if('${subdivisionVal}'!='')
    		{
    		 var value='${groupVal}';
    		$('#s2id_group .select2-chosen').html(value);
    		$('#group').val(value);
    		} */
    }
  </script>
<div class="page-content" >
	<!-- BEGIN DASHBOARD STATS -->
	<div class="portlet box purple">
	 
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-reorder"></i>Ascii File Generation</div>   
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body"  style="width: 100%">
					
						 <div class="col-md-12">
	 <div class="row" > 
	
	<form >
	    <table style="width: 60%">
	    <tr><td><div data-date-viewmode="years" data-date-format="yyyymm"  class="input-group input-small date date-picker">
									<input type="text" name="month" id="datedata" readonly="readonly" class="form-control" required="required">
										<span class="input-group-btn">
											<button type="button" class="btn default"><i class="fa fa-calendar"></i></button>
											<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
										</span>	
								</div>	</td>
									   <td><select class="form-control select2me input-small" name="subdivision" id="subdivision" onchange="showSubdivGroupValue(this.value);" >
									    <option value="">Subdivision</option>
									    <c:forEach var="elements" items="${subdivlist}">
									    <option value="${elements}">${elements}</option>
									    </c:forEach></select>
									   </td>
									   <%-- <td id="groupTd"><select class="form-control select2me input-small" name="group" id="group" >
									    <option value="deafult">Group Value</option>
									    <c:forEach var="elements" items="${groupList}">
									    <option value="${elements}">${elements}</option>
									    </c:forEach></select>
									   </td> --%>
									   <td><select class="form-control select2me input-small" name="category" id="category" >
									    <option value="">Category</option>
									    <c:forEach var="elements" items="${category}">
									    <option value="${elements}">${elements}</option>
									    </c:forEach></select>
									   </td>
									   <td><select class="form-control select2me input-small" name="billingCategory" id="billingCategory" >
									    <option value=" ">BillingCategory</option>
									    <c:forEach var="elements" items="${billingCategory}">
									    <option value="${elements}">${elements}</option>
									    </c:forEach></select>
									   </td>
									   
								<td>
								<button type="submit" id="dataview" class="btn yellow" formaction="./generateAsciiVal" formmethod="post" >Generate file</button>
								</td>
									   </tr>
									   
	    </table>
	
	
		</form>
		</div>
		<br/>
		<c:if test = "${result ne 'notDisplay'}">
			<div class="alert alert-danger display-show" id="alertDiv">
					<button class="close" data-close="alert"></button>
					<span style="color:red" >${result}</span>
			</div>
			
	    </c:if>	
	</div>
	</div>
	
	</div>

	<br/>
		
			
</div>