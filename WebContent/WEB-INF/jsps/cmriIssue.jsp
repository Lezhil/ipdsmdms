<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script  type="text/javascript">


	$(".page-content").ready(function(){  
		App.init();
		TableManaged.init();
		FormComponents.init();	
		$('#rdgDateId,#rdgDateId2,#iDateId').val(moment(new Date()).format('MM/DD/YYYY'));
		$('#addCmri').click(function()		
		{	
			$('#cmrino').prop('disabled', false);
    		document.getElementById('six').style.display='block';
		});
		
		//document.getElementById("rdgDateId").disabled="true";
		
		 $("#print").click(function(){ 
   	    	 printcontent($(".table-scrollable").html());
			});
		$('#cmri-Manager').addClass('start active ,selected');
		$("#dash-board,#360d-view,#cumulative-Analysis,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	});
	
	
	function dateReturn(inputDate)
	  {
		  var str = inputDate.split("/");
		  var date1 = new Date(str[2],str[1],str[0]);
		  return date1;
	  }
	
	function myClearFunction() {
	    document.getElementById("myForm").reset();
	}
	
	function edit(param,id)
	{
		document.getElementById('issueUpdate').style.display='block';
		document.getElementById('issueAdd').style.display='none';
		 $.ajax({
		    	url:'./getCMRIDataForEdit/'+id,
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	success:function(response)
		    	{	
		    		$('#issueId').val(response.id);
		    		$('#rdgDateId').val(moment(response.rdgDate).format("MM/DD/YYYY"));
		    		$('#iDateId').val(moment(response.iDate).format("MM/DD/YYYY"));
		    		$('#cmrino').val(response.mriNo);
		    		$('#subdiv').val(response.subDiv);
		    		$('#mrname').val(response.name);
		    		$('#accessories').val(response.accessories);
		    		$('#cmrino,#rdgDateId').prop('disabled', 'disabled');
		    		document.getElementById('six').style.display='none';   		
		    			
		    	}		    	
		    })
		
		$('#'+param).attr("data-toggle", "modal");
		 $('#'+param).attr("data-target","#stack1");
	}
	
	function issueDataUpdate(path,form1)
	{
		
		var readingDate = dateReturn(form1.rdgDateId.value);
		var issueDate = dateReturn(form1.iDateId.value);
		if(form1.rdgDateId.value=='')
			{
			bootbox.alert('please select reading date');
			return false;
			}
		
		if(form1.iDateId.value=='')
		{
		bootbox.alert('please select issue date');
		return false;
		}
		
		if(form1.rdgDateId.value != form1.iDateId.value)
			{
			
			if(issueDate > readingDate)
				{
				 bootbox.alert('Reading Date should be Same or Greater than Issue Date');
				  return false;
				}
			  
			}
		
		if(form1.cmrino.value=='0')
		{
		bootbox.alert('please select cmri No');
		return false;
		}
		
		if(form1.subdiv.value=='0')
		{
		bootbox.alert('please select sub div');
		return false;
		}
		
		if(form1.mrname.value=='0')
		{
		bootbox.alert('please select mrname');
		return false;
		}
		
		if(form1.accessories.value=='0')
		{
		bootbox.alert('please select accessories');
		return false;
		}
				
		$('#cmrino,#rdgDateId').prop('disabled', false);
		$('#myForm').attr("action",path).submit();	
		
	}
	
	function checkAlreadyIssued()
	{
		var cmri=$('#myForm').serialize();		
		$.ajax({
	    	url:'./getCMRIIssued',
	    	type:'GET',
	    	data:cmri,
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response)
	    	{	
	    		if(response=='1')
	    			{
	    			
	    			$('#cmrino').val('0');
	    			bootbox.alert('selected CMRI already issued');
	    			return false;
	    			}
	    		}		    	
	    })
	}
	
</script>
<div class="page-content" >
		 <c:if test = "${not empty result}">
					<div class="alert alert-danger display-show">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${result}</span>
					</div>
			    </c:if>	
     <div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box green">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>CMRI Issue</div>
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
											
						<div class="portlet-body">
						<div class="btn-group">
										<button class="btn green" data-target="#stack1" data-toggle="modal"  id="addCmri">
									      Issue CMRI<i class="fa fa-plus"></i>
									    </button>
									    
									    <button class="btn blue" data-target="#stack2" data-toggle="modal"  id="addData">
									      Refresh
									    </button>
								         </div><div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									 <ul class="dropdown-menu pull-right">
										<li id="print"><a href="#">Print</a></li>										
										<li><a href="#" onclick="tableToExcel('sample_2','CMRI_issue');">Export to Excel</a></li>
										<!-- <li><a href="#" onClick ="$('#sample_2').tableExport({type:'pdf',escape:'false',pdfLeftMargin:-25,ownColWidth:30,fileName:'ABC'});">Export to pdf</a></li> -->
										<!-- <li><a href="#" id="cmd" onclick="$('#sample_2').tableExport({fileName:'manjaSuni',type:'pdf',tableId:'sample_2'})">Export to pdf</a></li> -->
										
									 </ul>
							    	</div>
							    	<br/><br/>
								         
								      <table class="table table-striped table-bordered table-hover" id="sample_2">
								<thead>									
									<tr>										
										<th hidden="true"> id</th>
										<th>RdgMonth</th>
										<th>RdgDate</th>
										<th>Issue Date</th>
										<th>CMRI No</th>
										<th>sub Div</th>
										<th>Mrname</th>
										<th>Accessories</th>
										<th>Edit</th>
            					</tr>
								</thead>
								<tbody>
									
									<c:forEach items="${cmriData}" var="element">
									 	<tr>									 		
										 	<td hidden="true">${element.id}</td>
										 	<td>${element.billMonth}</td>
										 	<td><fmt:formatDate pattern="dd/MM/YYYY" value="${element.rdgDate}" /></td>										 	
										 	<td><fmt:formatDate pattern="dd/MM/YYYY" value="${element.iDate}" /></td>										 	
										 	<td>${element.mriNo}</td>
										 	<td>${element.subDiv}</td>
										 	<td>${element.name}</td>
										 	<td>${element.accessories}</td>
										 	<td><a href="#" onclick="return edit(this.id,${element.id})" id="edit${element.id}">Edit</a></td>
										 	
										 	
									 	</tr>
									 </c:forEach>	
								</tbody>
							</table>   
						
						<div id="stack1" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<h4 class="modal-title">New User</h4>
										</div>
										<div class="modal-body">
													<form:form action="./addCmriIssue" modelAttribute="cmriManager" commandName="cmriManager" method="post" id="myForm">
													
														<div class="form-body">
															<div class="form-group">
															<table id="tableData2">
															<tr hidden="true"><td>Id</td>
															
																<td>
																		<form:input path="id" type="text" class="form-control"  id="issueId"  readonly="true" ></form:input>
																					</td>
															</tr>
															<tr><td>Month</td>
															
																<td>
																		<form:input path="billMonth" type="text" class="form-control" name="billMonthName" id="billMonthId"  readonly="true" ></form:input>
																					</td>
															</tr><tr>
														
															<td>Reading Date</td>
															
															<td><div class="input-group input-medium date date-picker" id="abc">
																					<form:input path="rdgDate" type="text" class="form-control" name="rdgDateName" id="rdgDateId" title="please select reading date" ></form:input>
																					<span class="input-group-btn">
																					<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																					</span>
																					</div></td>
															
															
															
															</tr>
															
															
															<tr>
															<td>Issue Date</td>
															
															<td><div class="input-group input-medium date date-picker" >
																					<form:input path="iDate" type="text" class="form-control" name="iDateName" id="iDateId" ></form:input>
																					<span class="input-group-btn">
																					<button class="btn default" type="button" id="seven" > <i class="fa fa-calendar"></i></button>
																					</span>
																					</div></td>
															
															</tr>
															<tr>
															<td>CMRI No</td>
															
														
															
														        <td><form:select path="mriNo" name="cmrino" class="form-control" id="cmrino" onchange="return checkAlreadyIssued(this.value);">
															
															     <option value="0">select</option>
															   <c:forEach items="${cmriNo}" var="element">
															     <option value="${element.cmri_no}">${element.cmri_no}</option>
															     </c:forEach> 
					                                             </form:select></td> 
															
															</tr>
															
															
															
															
															
															<tr><td>Sub Div </td>
															
														        <td><form:select path="subDiv" name="subdiv" class="form-control" id="subdiv">
															
															     <form:option value="0">select</form:option>
															     <c:forEach items="${subDivision}" var="element">
															      <form:option value="${element.sdoName}">${element.sdoName}</form:option>
															     </c:forEach>
															     
					                                            </form:select></td>
															</tr>
															
															<tr>
															
															<td>MR Name</td>
																			
														        <td><form:select path="name" name="mrname" class="form-control" id="mrname">
															
															     <form:option value="0">select</form:option>
															     <c:forEach items="${mrNames}" var="element">
															      <form:option value="${element.mrname}">${element.mrname}</form:option>
															     </c:forEach>
					                                            					                                             
															   </form:select></td> 
															
															
															</tr>
															
															
															
															<tr><td>Accessories</td>
															
														        <td><form:select path="accessories" name="accessories" class="form-control" id="accessories"  required="required">
															
															     <form:option value="0">select</form:option>					                                             
					                                             <form:option value="Common Cable">Common Cable</form:option>
					                                              <form:option value="Sems Cable">Sems Cable</form:option>
					                                               <form:option value="LnT Cable">LnT Cable</form:option>
					                                                <form:option value="Charger">Charger</form:option>
					                                                 <form:option value="Bag and All Accessories">Bag and All Accessories</form:option>
					                                                 <form:option value="None">None</form:option>
					                                                 			
															    </form:select></td> 
														      
															</tr>
															
															</table>
															
																		
															</div>																																	
																 <div class="modal-footer">		
															
																<div class="btn-group pull-right">
																	<button name="cmriIssueAdd"  type="submit" style="display: block;" class="btn blue" id="issueAdd" onclick="return issueDataUpdate('./addCmriIssue',this.form)">Issue</button>
																	<button name="cmriIssupdate"  type="submit" style="display: none;" class="btn blue" id="issueUpdate" onclick="return issueDataUpdate('./issueDataUpdate',this.form)">Update</button>
																	<button type="button" data-dismiss="modal" class="btn">Cancel</button>
																	</div>
																</div>
															
														</div>
														
													</form:form>
													
													
													</div>
													</div>
													</div>
													</div>
							
							
							<div id="stack2" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<h4 class="modal-title">New User</h4>
										</div>
										<div class="modal-body">
													<form:form action="./getIssueDataForRefresh" modelAttribute="cmriManager" commandName="cmriManager" method="post" id="myForm">
													
														<div class="form-body">
															<div class="form-group">
															<table id="tableData2">
															<tr><td>Month</td>
															
																<td>
																		<form:input path="billMonth" type="text" class="form-control" name="billMonthName2" id="billMonthId2"  readonly="true" ></form:input>
																					</td>
															</tr>
															<tr>
															<td>Reading Date</td>
															
															<td><div class="input-group input-medium date date-picker" >
																					<form:input path="rdgDate" type="text" class="form-control" name="rdgDateName2" id="rdgDateId2"  ></form:input>
																					<span class="input-group-btn">
																					<button class="btn default" type="button" id="eight" > <i class="fa fa-calendar"></i></button>
																					</span>
																					</div></td>
															
															
															
															</tr>
																						
															</table>
																		
															</div>																																	
																 <div class="modal-footer">		
															
																<div class="btn-group pull-right">
																	<button name="cmriIssueAdd"  type="submit" class="btn blue" id="refreshId">Refresh</button>
																	
																	<button type="button" data-dismiss="modal" class="btn">Cancel</button>
																	</div>
																</div>
															
														</div>
														
													</form:form>
													
													
													</div>
													</div>
													</div>
													</div>	
								
								
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
			
			<div id="" style="display:none">
				<table id="">
					<thead>									
						<tr>	
							
         						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
			</div>
</div>