<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>
 <script  type="text/javascript">
	$(".page-content").ready(function(){  
		
		App.init();
		TableManaged.init();
		FormComponents.init();
		//$('#dataPopTable').dataTable({"ordering": false});
		//loadSearchAndFilter('dataPopTable');
		 
			$('#ADMINSideBarContents,#dtdashboardReports')
								.addClass('start active ,selected');
						$('#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
								.removeClass('start active ,selected');

		   
		    $('#dataPopTable').show();
		 if('${result}' !='')
		 {
			 alert('${result}')
		 }
		   $("#schemaName").val('').trigger("change"); 
		 $("#sqlqry").val('').trigger("change");
	});

	
	</script>
<style>
.input-medium {
    width: 160px !important;
}

.legitRipple {
    position: relative;
    overflow: hidden;
    z-index: 0;
    .user-select(none);
}
</style>
<div class="page-content">

	<div class="row">
		<div class="col-md-12">
			<div class="portlet box blue" >
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>DT Dashboard Reports
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
					</div>
				<div class="portlet-body">
						<div class="row" style="margin-left: 2%">
						 	 
						 	  <div class="form-group col-md-9">
							    <label><span><strong>View All Reports</strong></span></label>
					   		   <select class="form-control select2me " name="groupbyStatements" id="groupbyStatements" onchange="viewReportDetails(this.value)">
						 	   <option value="">Select</option>
						 	   
						<c:forEach items="${report_name}" var="elements">
								<option value="${elements}">${elements}</option>
							</c:forEach>
						 	   </select>
						 	   </div>
						 	 
					 
					</div>
					</div>
					</div>
					</div>
		<%-- <div class="row">
		<div class="col-md-12">
			<div class="portlet box blue" >
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>SQL QUERY 
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
					</div>
				<div class="portlet-body">
				<form  id="FormID" method="post">
				<div class="form-group col-md-12">
				<textarea class="form-control" id="sqlqry" name="sqlqry"></textarea>
				<br>
				<button class="btn btn-info" style="margin-left:40%;" onclick="ClearSQLQuery();">ClearText</button>
				<button type="button" class="btn btn-info" style="margin-left: 5%;" id="dataview" onclick="getData();">View</button>
				
				<button type="button" class="btn btn-info" style="margin-left: 5%;" id="savereportbutton" onclick="saveReport();">Save Report</button>
				</div>
				</form>
				</div>
				</div>
				</div>
				</div> --%>
				
				
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
 					 <div class="modal-dialog">
    				<div class="modal-content">
      				<div class="modal-header">
        			<h4 class="modal-title" id="myModalLabel">Modal title</h4>
      				</div>
      					<div class="modal-body">
       						<span></span> <input class="form-control" id="inputValue">
      					</div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-primary" onclick=" return validateData();">OK</button>
				      </div>
				    </div>
				  </div>
				 
				</div>
				
				
				<!-- Showing popup for saving the query -->

	<!-- <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel">Save Report</h4>
				</div>
				<div class="modal-body">
					<span></span> <label>Enter Report Name.<font color="red">*</font></label>
						<input type="text" class="form-control" name="report_name_popup" id="report_name_popup" onchange="checkVal(this.value)"
							placeholder="Enter The Report Name.."/>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick=" return validateSaveData();">Save</button>  - check this and proceed.....
				</div>
			</div>
		</div>

	</div> -->


	<!-- <div class="row" hidden="true">


		<label>Report Name.</label> <input
			type="text" class="form-control" name="report_name" id="report_name"
			placeholder="Enter The Report Name.." /> <label>Report
			Description
		</label>
		<textarea class="form-control" name="report_description"
			id="report_description"
			placeholder="Enter The Select Query Descripton.."></textarea>





	</div> -->






	<div class="row">
		<div class="col-md-12">
			<div class="portlet box blue" >
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>RESULT
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
					</div>
				     <div class="portlet-body">
				       
				    <div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('dataPopTable', 'TABLE DATA')">Export
										to Excel</a></li>
							</ul>
						</div>
				</div>
				<%-- <table class="table table-striped table-hover table-bordered" id="sample_editable_1">
				<thead>	
				<tr>
				<c:forEach items="${colunmName}" var="element">
				<th>${element}</th>
				</c:forEach>
				</tr>
				</thead>
				<tbody>
				 <c:forEach items="${dataList}" var="element">
				<tr>
				<c:forEach items="${element}" var="colunmdata">
				<td>${colunmdata}</td>
				</c:forEach>
				</tr>
				</c:forEach>
				</tbody>
				</table>  --%>
				<table class="table table-striped table-hover table-bordered" id="dataPopTable">
				<thead id="theaddivId">	
				</thead>
				<tbody id="tbodyDivId">
				</tbody>
				</table> 
				</div>
				</div>
				</div>
				</div>
				<div id="imageee" hidden="true" style="text-align: center;">
                         <h3 id="loadingText">Loading..... Please wait. 
						 </h3> 
						 <img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width:3%;height: 3%;">
						</div>
		               </div>
		               </div>				
				

				
				
				
 <script>
 var sqlQuery="";
 var conditionFlag=0;
 var GroupBYStatement=0;
 var GroupColunmCount=0;
 var  operator='';
 
   function getTableName(schemaName)
   {
	   $("#tableName").empty();
	   if(schemaName !='')
	   {
			$.ajax({
			    	url:'./getTableList',
			    	type:'POST',
			    	dataType:'json',
			    	data:{schemaName:schemaName},
			    	asynch:false,
			    	cache:false,
			    	success:function(response)
			    	{
			    		var tableName="<option value=''>select</option>";
			    	  if(response.length>0)
			    	  {
			    		  for(var i=0;i<response.length;i++)
			    		  {
			    			  tableName=tableName+"<option value="+response[i]+">"+response[i]+"</option>";
			    		  }
			    	  }
			    	  $("#tableName").append(tableName);
			    	}
			});
	   }
   }
   function getColumnName(tableName)
   {
	   conditionFlag=0;
	   GroupBYStatement=0;
	   GroupColunmCount=0;
	   var schemaName=$("#schemaName").val();
	   $("#sqlqry").val('');
	   $("#columnName").empty();
	   $("#filter").empty();
	   $("#groupbyAttribute").empty();
	   if(tableName !='')
	   {
			$.ajax({
			    	url:'./getColumnList',
			    	type:'POST',
			    	dataType:'json',
			    	data:{tableName:tableName,
			    		schemaName:schemaName},
			    	asynch:false,
			    	cache:false,
			    	success:function(response)
			    	{
			    		var columnName="<option value=''>select</option>";
			    		var filtervalue="<option value=''>select</option><option value='limit'>Limit</option>";
			    	  if(response.length>0)
			    	  {
			    		  for(var i=0;i<response.length;i++)
			    		  {
			    			  columnName=columnName+"<option value="+response[i]+">"+response[i]+"</option>";
			    			  filtervalue=filtervalue+"<option value="+response[i]+">"+response[i]+"</option>";
			    		  }
			    	  }
			    	  $("#columnName").append(columnName);
			    	  $("#groupbyAttribute").append(columnName);
			    	  $("#filter").append(filtervalue);
			    	}
			});
	   }
	   
	   sqlQuery="select * from "+schemaName+"."+tableName;
	   $("#sqlqry").val(sqlQuery);
   }
   
   function setCondition(filtervalue)
   {
	    if(conditionFlag==0 )
	    {
	    	 if(filtervalue == 'limit')
	    	 {
	    		 sqlQuery=sqlQuery+" "+filtervalue+" ";
	    	 }else
	    	 {
	    		 sqlQuery=sqlQuery+"  where "+filtervalue+" ";
	    	 }
	    	
		     $("#sqlqry").val(sqlQuery);
	    	 conditionFlag=1;
	    }
	    else
	    { 
	    	if(filtervalue == 'limit')
	    	 {
	    		alert("After Where Condition Limit is Not Allowed");
	    		return false;
	    	 }
	    	if(!(sqlQuery.includes('=') || sqlQuery.includes('<') || sqlQuery.includes('>') || sqlQuery.includes('>=') || 
	    	   sqlQuery.includes('<=') || sqlQuery.includes('<>') || sqlQuery.includes('NOT') || sqlQuery.includes('LIKE') ||
	    	   sqlQuery.includes('IN') || sqlQuery.includes('IS')))
	    	{
	    		alert("Please Select any oneoff Operators");
	    		return false;
	    	}
	    	 sqlQuery=sqlQuery+" "+filtervalue;
	    	 $("#sqlqry").val(sqlQuery);
	    }
	  
   }
   
   function setOPerator(operators)
   {
	  
	   operator=operators
	   if(operators !='AND' && operators !='OR' && operators !='NULL' && operators !='NOT'  && operators !='IS')
	   {
		   var heading=$("#filter").val();
		   $(".modal-header h4").html(heading);
		   $('#myModal').modal('show');
	   }	
	   if(operators =='AND' || operators =='OR' ||  operators =='NULL' ||  operators =='NOT' || operators =='IS' )
	   {
		 qry=$("#sqlqry").val();
		 $("#sqlqry").val(qry+" "+operators);
		 sqlQuery=qry+" "+operators;
	   }	
   }
 function ClearSQLQuery()
 {
	 $("#sqlqry").val('');
 }
 function selectedColunm(colunms)
 {
	 var selectedColunms=$("#columnName").val()
	 var qry=$("#sqlqry").val();
	 
	 if(qry.includes("*"))
	 {
		 var removeStar=qry.split("*");
		 var QueryFirst= removeStar[0]
		var QuerySecond= removeStar[1]
		sqlQuery=QueryFirst+" "+selectedColunms+" "+QuerySecond;
		 
	 }
	 else
	 {
		 if(colunms !='')
		 {
			    var removeStar=qry.split("from");
				var QueryFirst= removeStar[0]
				var QuerySecond= removeStar[1]
				sqlQuery="select "+selectedColunms+" from "+QuerySecond;
		 }
		 else
		 {
			    var removeStar=qry.split("from");
				var QueryFirst= removeStar[0]
				
				var subquery=QueryFirst.split("select");
				//alert(subquery)
				var QuerySecond= removeStar[1]
				sqlQuery=QueryFirst+","+colunms+" from "+QuerySecond;
		 }
		 
	 }
	 $("#sqlqry").val(sqlQuery);
 }
 
 function setGroupBySTatement(statement)
 {
	 var qry=$("#sqlqry").val();
	 if(qry.length>0)
	 {
		 if(GroupBYStatement<2)
		 {
			 qry=qry+" "+statement; 
			 GroupBYStatement=GroupBYStatement+1;
		 }
	 }
	 else
	 {
		 alert("First Select Table")
	 }
	 $("#sqlqry").val(qry);
 }
 function setGroupBySTatement(statement)
 {
	 var qry=$("#sqlqry").val();
	 if(qry.length>0)
	 {
		 if(GroupBYStatement<2)
		 {
			 qry=qry+" "+statement; 
			 GroupBYStatement=GroupBYStatement+1;
		 }
	 }
	 else
	 {
		 alert("First Select Table")
	 }
	 $("#sqlqry").val(qry);
 }
 
 function setGroupByColunm(coulumName)
 {
	 var qry=$("#sqlqry").val();
	 var checkGroup=qry.split('Group BY')
	 var checkOrder=qry.split('Order BY')
	 var checkG=checkGroup[1]+"";
	 if(GroupBYStatement>0)
	 {
		 if(checkGroup[1] =='' && (qry.indexOf("Order BY") == -1))
		 {
			 qry=qry+" "+coulumName;
		 }
		 else if(checkGroup[1] !='' && (qry.indexOf("Order BY") == -1))
		 {
			 qry=qry+","+coulumName;
		 }
		 else if(checkOrder[1]=='')
		 {
			 qry=qry+" "+coulumName;
			
		 }
		 else if(checkOrder[1] !='')
		 {
			 qry=qry+","+coulumName;
		 }
	 }
	 $("#sqlqry").val(qry);
 }
 
 function validateData()
 {
	var data=$("#inputValue").val();
	if(data =='')
	{
		alert("Enter Value");
		return false;
	}
	else
	{
		if(operator !='AND' && operator !='OR' && operator !='NULL' && operator !='NOT' )
		   {
			   var heading=$("#filter").val();
			   $(".modal-header h4").html(heading);
			   $('#myModal').modal('show');
			   var data=$("#inputValue").val();
			   $("#inputValue").val('');
			   if(operator=='LIKE')
			   {
			   sqlQuery=$("#sqlqry").val()+" "+operator+" "+"'"+data+"'";
			   }
			   else if(operator !='AND' && operator !='OR' && operator !='NULL' && operator !='NOT')
			   {
				   sqlQuery=$("#sqlqry").val()+" "+operator+" "+data;
			   }
			   else if(operator =='IN')
			   {
				   sqlQuery=$("#sqlqry").val()+" "+operator+"("+data+")";
			   }
			   $("#sqlqry").val(sqlQuery);
		   }
		   else if(operator =='AND' || operator =='OR' || operator =='NULL' || operator =='NOT')
		   {
			   sqlQuery+=$("#sqlqry").val()+" "+operator+"("+data+")";
			   $("#sqlqry").val(sqlQuery);
		   }
		
		$('#myModal').modal('hide');
		return true;
		}
   }
 function loadSearchFilter1(param,tableData,temp)
 {
     $('#'+param).dataTable().fnClearTable();
     $('#'+param).dataTable().fnDestroy();
  
/*    	 $('#'+param).dataTable( {
   	   	  "columnDefs": [
   	  	    { "orderable": false }
   	  	  ]
   	  	}); */
   	/*  $('#'+param).dataTable({
   	   "aaSorting" : [[0,"desc"]],
   	   "bDestroy":true
   	}); */
   
 	 //jQuery('#' + param + '_wrapper .dataTables_filter input').addClass("form-control input-small"); // modify table search input 
	 //jQuery('#' + param + '_wrapper .dataTables_length select').addClass("form-control input-xsmall"); // modify table per page dropdown 
	 //jQuery('#' + param + '_wrapper .dataTables_length select').select2(); // initialize select2 dropdown 
	 $('#'+temp).html(tableData);
     $('#'+param).dataTable();

 } 
 function getData()
 {
	 
	 $("#theaddivId").empty();
	 $("#tbodyDivId").empty();
	 var qry=$("#sqlqry").val();
	 var schemaName=$("#schemaName").val(); 
		
		if(schemaName == ''){
			bootbox.alert("Please Select Schema");
			return false;
		}
   var tableName=$("#tableName").val(); 
		
		if(tableName == ''){
			bootbox.alert("Please Select Data Objects");
			return false;
		}
		
	 $.ajax({
		 type:"POST",
		 url:"./getQueryBuilderData",
		 data:{
			 "sqlqry":qry,
		 },
		 dataType:"json",
		 beforeSend: function(){
		        $('#imageee').show();
		    },
		 success:function(response)
		 {
			     $("#theaddivId").empty();
			     $("#tbodyDivId").empty();
			     if(response.length==1)
			     {
			    	 if(response.includes("ERROR"))
			    	 {
			    	    alert("Please Select valid operator after selecting filter column names");
			    	 }
			     }
			     else if(response[1].length!=0 && response[0].length!=0)
			     {
			    	     var htmlhead="";
						 htmlhead='<tr>';
						 for(var i=0;i<response[0].length;i++)
					     {
						    // alert(response[0][i]);
							 htmlhead+='<th>'+response[0][i]+'</th>'; 
						 }
						 htmlhead+= '</tr>';
					     $("#theaddivId").append(htmlhead);
					
						 var htmlbody="";
						 for(var j=0; j<response[1].length;j++)
						 {
							 var parentResponse=response[1][j];
							// alert(response[1][j]);
							 htmlbody+='<tr>';
							 for(var i=0;i<parentResponse.length;i++)
							 {
								// alert(parentResponse[i]);
								 if(jQuery.type(parentResponse[i])=='number' && parentResponse[i]>=1054801960000){
								  htmlbody+= '<td>'+moment(parentResponse[i]).format('DD-MM-YYYY:HH-mm-ss')+'</td>';
								 }
								 else{
									 var data=parentResponse[i];

									 if (data =='' || data =='null' || data ==null)
										 data='';
									 
									 htmlbody+= '<td>'+data+'</td>';
								 }
							 }
							 htmlbody+='</tr>';
						 }
						// alert("Hi----"+htmlbody);
						 
						 // $("#tbodyDivId").append(htmlbody);
						  //loadSearchFilter1('dataPopTable',htmlbody,'tbodyDivId');

						  
						  $('#dataPopTable').dataTable().fnClearTable();
						 // $('#dataPopTable').dataTable({"ordering": false});
						  $("#tbodyDivId").html(htmlbody);
						  loadSearchAndFilter('dataPopTable');
						  

/* 
						  $('#dataPopTable').DataTable( {
					   			destroy: true,
					 	        dom: 'Bfrtip',
					 	       "ordering": false,


					 	      "pagingType": "numbers",
						 	     "scrollX": true,
						 	       buttons: [
							 	              
							 	            ]
					 	    
					 	    } );
						   */
			     }else
			     {
			    	 $('#dataPopTable').dataTable().fnClearTable();
			    	 alert("NO result For this Query");
			     }
			     $('#dataPopTable').dataTable().fnClearTable();
			     $('#imageee').hide();
			     }
		
	 }); 
	/*  $("#FormID").attr('action','./querybuilder').submit();
	  */
		
	
 }
 
 
 function saveReport(){
		

	    $('#report_name_popup').val("");
		$('#myModal1').modal('show');
	 
	 
 }
 function checkVal(val){
	
	if(val == ''){
	 alert("Please Enter Report Name");
	 return false;
	}
	else{
		return true;
	}
 
 }
 
 function validateSaveData(){
	 
		if(document.getElementById("report_name_popup").value=="" || document.getElementById("report_name_popup").value==null)
		{		
			alert("Enter Report Name");
		
		 return false;	 
		}
	 else{
		 $('#report_name').val(document.getElementById("report_name_popup").value);
		 $('#myModal1').modal('hide');
		 querySave();
	 }
	 
	 
	
	
 }
 
 function querySave(){
	 
	var reportname= $('#report_name_popup').val();
	var qry=$('#sqlqry').val();
	var schemaName=$("#schemaName").val(); 
	
	if(qry == ''){
		alert("Please Enter Query");
	}
	
	else{
		//alert(qry);
		//alert("hitting /saveQuery");		
		$.ajax({
	        type:'POST',
	        url:'./saveQuery',
	        dataType:'text',
	        data:{
	        	reportname: reportname,
	        	qry :qry,
	        	schemaName :schemaName
	        },
	        
	        success:function(response){
	        if(response=='Report Name Already Exist'){
	        	$('#report_name_popup').val('');
	        	bootbox.alert(response);
		        
	        }else{
	        	bootbox.alert(response);
	        	location.reload();
	        }

	        }
	
		});
	}
 }
 
 
 function viewReportDetails(val){
	// alert(val);
	 

	//alert(schemaName);
	 $("#theaddivId").empty();
	 $("#tbodyDivId").empty();
	 
	 //var qry=$("#sqlqry").val();
	 $.ajax({
		 type:"POST",
		 url:"./getQueryBuilderbyRpt_name",
		 data:{
			 "report_name":val,
		 },
		 dataType:"text",
		 success:function(response)
		 {
			 //alert(response);
			 $("#sqlqry").val(response);
			 var qry=response;
			 $.ajax({
				 type:"POST",
				 url:"./getQueryBuilderDataw",
				 data:{
					 "sqlqry":qry,
				 },
				 dataType:"json",
				 beforeSend: function(){
				        $('#imageee').show();
				    },
				 success:function(response)
				 {
					 $('#imageee').hide();
					     $("savereportbutton").hide();
					     
					     $("#theaddivId").empty();
					     $("#tbodyDivId").empty();
					     if(response.length==1)
					     {
					    	 if(response.includes("ERROR"))
					    	 {
					    	    alert("Please Select valid operator after selecting filter column names");
					    	 }
					     }
					     else if(response[1].length!=0 && response[0].length!=0)
					     {
					    	     var htmlhead="";
								 htmlhead='<tr>';
								 for(var i=0;i<response[0].length;i++)
							     {
									 htmlhead+='<th>'+response[0][i]+'</th>'; 
								 }
								 htmlhead+= '</tr>';
							     $("#theaddivId").append(htmlhead);
							
								 var htmlbody="";
								 for(var j=0; j<response[1].length;j++)
								 {
									 var parentResponse=response[1][j];
									 
									 htmlbody+='<tr>';
									 for(var i=0;i<parentResponse.length;i++)
									 {
										 
										 if(jQuery.type(parentResponse[i])=='number' && parentResponse[i]>=1054801960000){
										  htmlbody+= '<td>'+moment(parentResponse[i]).format('DD-MM-YYYY:HH-mm-ss')+'</td>';
										 }
										 else{
											 htmlbody+= '<td>'+parentResponse[i]+'</td>';
										 }
									 }
									 htmlbody+='</tr>';
								 }
								 
								  /* $("#tbodyDivId").append(htmlbody);
								  
								  loadSearchFilter1('dataPopTable',htmlbody,'tbodyDivId');
 */
								  $('#dataPopTable').dataTable().fnClearTable();
								  $('#tbodyDivId').html(htmlbody);
								  loadSearchAndFilter('dataPopTable');
					     }else
					     {
					    	 $('#dataPopTable').dataTable().fnClearTable();
					    	 $('#theaddivId').dataTable().fnClearTable();
					    	 $('#tbodyDivId').dataTable().fnClearTable();
					    	 alert("NO result For this Query");
					  
					     }
					    
					     $('#imageee').hide();
					     }
				
			 });  
			     
			  
		  }
		
	 }); 
	 
 }

 
 </script>