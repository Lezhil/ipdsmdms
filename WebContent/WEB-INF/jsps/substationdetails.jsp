
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	if (session.getAttribute("username") == null || session.getAttribute("username") == null) {
%>
<script>
	window.location.href = "./?sessionVal=expired";
</script>
<%
	}
%>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
  <script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
	<!-- <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> -->
	 <script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
	<script src="resources/assets/scripts/app.js"></script>
	<script src="resources/assets/scripts/ui-extended-modals.js"></script>
	

<script type="text/javascript">
var regexForGox=/^[0-9]*\.?[0-9]*$/;
var regexForDcu = /^[a-zA-Z0-9]*$/;
var regexForCapacity=/^[0-9]*$/;
var officeTyperesult = '${officeType}';
	$(".page-content").ready(function() {
		
		App.init();
		TableEditable.init();
		Tasks.initDashboardWidget();
		FormComponents.init();
		loadSearchAndFilter('sample1');
		TableManaged.init();
		 
		 $('#MDMSideBarContents,#staDtlsId,#addSubstationId,#mpmId').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#buisnessRoleDetails,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
		 loadSearchAndFilter("sample1");
		 
    	 $("#analysedPrintId").click(function(){ 
	    	 printcontent($("#excelUpload .table-scrollable").html());
		});

		 $('#circleId').change(function() { 
	    	    var dropVal = $(this).val();
	    	    sessionStorage.setItem("SelectedItem", dropVal);
	    	});

		 var result_flag="${results}";
			var a_flag="${alert_type}";
			if (result_flag !="notDisplay"){
				if (a_flag =="success"){
					bootbox.alert(result_flag);
				}

				if (a_flag =="error"){
					bootbox.alert(result_flag);
				}
				
			}

			//$("#toid").hide();


	    	
		 getSubStationDetailsNew();
	 });
	
	
		
	function validation(){
		var sdname=$('#subdivcode').val();
		var ssname=$('#substaname').val();
		var sscapacity=$('#substacap').val();
		//var circle=$('#circle').val;
		var tpsscode=$('#substacode').val();
		//var tppcode=$('#tpparcode').val();
		
		var psubdiv=$('#parsubdiv').val();
		var pfvoltge=$('#parfeedervol').val();
		//var pfeeder=$('#parfeeder').val();
		var TownCode =$('#ParentTown').val();
		var regex = /^[a-zA-Z0-9]*$/;
		var townCode = $('#Towncode').val();

		var substationCapacityinMVA = $('#substationCapacityinMVA').val();
		
		
		
	      if(sdname=='' )
	    	{
	    	bootbox.alert("Select  subdivision");
	        return false;
		    }
	      
	      if(townCode == ''){
		    	bootbox.alert("Please select Town");	
		    	return false;
		    }
	      
		  if(ssname=='' )
 	    	{
 	    	bootbox.alert("Enter the substation name");
 	        return false;
 		    }
		  if(sscapacity==null )
	    	{
	    	bootbox.alert("Enter the substation Voltage leve");
	        return false;
		    }
		  if(!sscapacity.match(regex))
	    	{
	    	bootbox.alert("Substation Voltage leve has to contains only alphanumeric");
	        return false;
		    }
		  if(substationCapacityinMVA !=''|| substationCapacityinMVA != null){
		  if(!substationCapacityinMVA.match(regex))
	    	{
	    	bootbox.alert("Substation Capacity MVA has to contains only alphanumeric");
	        return false;
		    }
		  }
		  
		  if(tpsscode=='' )
	    	{
	    	bootbox.alert("Enter TP SubstationCode");
	        return false;
		    }
		  /* 
		  if(tppcode=='' )
	    	{
	    	bootbox.alert("Enter TP Parent Code");
	        return false;
		    }
		  		   */
		  
		  
		   /* if(circle=='' ||circle==null )
	    	{
	    	bootbox.alert("select circle");
	        return false;
		    } */ 
		  
		   /* if(!ssname.match(regex))
	    	{
	    	bootbox.alert("substation name has to contains only alphanumeric ");
	        return false;
		    }  */
		  if(psubdiv=='')
	    	{
	    	bootbox.alert("Enter the parent subdivision");
	        return false;
		    } 
		  if(pfvoltge=='' ||pfvoltge==null)
	    	{
	    	bootbox.alert("Enter the parent feeder voltage");
	        return false;
		    }
		  /*  if(pfeeder=='' ||pfeeder==null)
	    	{
	    	bootbox.alert("Enter the parent feeder");
	        return false;
		    }     */
		    
		
		    
		  addSubStation();
	}
	function addSubStation(){
		var sdname=$('#subdivcode').val();
		var ssname=$('#substaname').val();
		var sscapacity=$('#substacap').val();
		var tpsscode=$('#substacode').val();
		//var tppcode=$('#tpparcode').val();
		var psubdiv=$('#parsubdiv').val();
		var pfvoltge=$('#parfeedervol').val();
		//var pfeeder=$('#parfeeder').val();
	    var subdiv1=$('#subdivcode').val();
	    var TownCode =$('#Towncode').val();
		//alert(subdiv1+"subdiv1");
		
		var substationCapacityinMVA = $('#substationCapacityinMVA').val();

		   $.ajax({
			      url  :'./addSubstationdetails',
			      type :"GET",
			      data : {
			    	  sdname : sdname,
			    	  ssname : ssname,
			    	  sscapacity: sscapacity,
			    	  psubdiv: psubdiv,
			    	  pfvoltge: pfvoltge,
			    	  pfeeder: pfeeder,
			    	 tppcode : tppcode,
			    	  tpsscode : tpsscode,
			    	  subdiv1 : subdiv1,
			    	  TownCode :TownCode,
			    	  substationCapacityinMVA :substationCapacityinMVA
						
					},
			    success:function(res){
			    	//alert(res);
			    	 
			    	bootbox.alert(" Substation added Successfully");
			    	clearfieldsNew();
			    	$('#stack1').hide();
			    	//$("#stack1").load("getSubStationDetails");
			    	
			    	
			    	getSubStationDetailsNew();
			    	
			    }
		  });
				
	}
	
	function modifySubstationdetails()
	{
        //var id=$('#substaidd').val();
		//var ssname=$('#substanamee').val();
		var sscapacity=$('#substacapp').val();
		var tpsubstacode=$('#substacodee').val();
		//var tpparcode=$('#tpparcodee').val();
		var regex = /^[a-zA-Z0-9]*$/;
		
 	 
	    modifySubstation();
	}
	
	
	
	function modifySubstation(){
		
		var sscapacity=$('#substacapp').val();
		var subStatName=$('#substanamee').val();
		var substaidd=$('#substaidd').val();
		var latitude=$('#latitude').val();
		var longitude=$('#longitude').val();
		var dcuno=$('#dcuno').val();

		var editSubstationCapacityinMVA=$('#editSubstationCapacityinMVA').val();
	//	alert(dcuno);
		
		
		if(subStatName=='' || subStatName==null)
			{
			bootbox.alert("Enter Substation Name")
			return false;
			}
		/* if(sscapacity=='' || sscapacity==null)
			{
			bootbox.alert("Enter Substation Capacity")
			return false;
			} */
		 /* if(tpparcode=='' || tpparcode==null)
			{
			bootbox.alert("Enter TP Parent Code")
			}  */
			if(sscapacity != null)
			{
			if(!sscapacity.match(regexForCapacity)){
				bootbox.alert("Only Numbers allowed in Substation Capacity");
			    return false;
			}
			}

			if(dcuno !=''|| dcuno != null)
			{
			if(!dcuno.match(regexForDcu)){
				bootbox.alert("Special charecter not allowed for DCU number");
			    return false;
			}
			}
			
			if(latitude !=''|| latitude != null)
			{
			if(!latitude.match(regexForGox)){
				bootbox.alert("Only Decimal Numbers allowed in Latitude");
			    return false;
			}
			}
			if(longitude !=''|| longitude != null)
			{
			if(!longitude.match(regexForGox)){
				bootbox.alert("Only Decimal Numbers allowed in Longitude");
			    return false;
			}
			}

			if(editSubstationCapacityinMVA !=''|| editSubstationCapacityinMVA != null)
			{
			if(!editSubstationCapacityinMVA.match(regexForGox)){
				bootbox.alert("Only Decimal Numbers allowed in Substation Capacity");
			    return false;
			}
			}
		
		$.ajax({
		      url  :'./modifySubstationDetails',
		      type :"GET",
		      data : {
		    	  substacapp : sscapacity,
		    	  subStatName : subStatName,
		    	  substaidd : substaidd,
		    	  latitude:latitude,
  	              longitude:longitude,
  	              dcuno:dcuno,
  	            editSubstationCapacityinMVA: editSubstationCapacityinMVA
				},
		    success:function(res){
		    	//alert(res);
		    	bootbox.alert(" Substation modified Successfully");
		    	$('#stack2').hide();
			    $('#stack2').modal('hide');
			    getSubStationDetailsNew();
		    	
		    }
	  });
	}

   function editUser(param)
  {   if(officeTyperesult == 'region'){
		
	}

	     
	   var id=param;
		if(id==""||id==null)
			{
			bootbox.alert("Nodata ")
			return false;
			}
		  $.ajax(
		  			{
		  					type : "GET",
		  					url : "./editSubStationDetails/" + id,
		  					dataType : "json",
		  					success : function(response)
		  												{	
		  										    		var data=response[0];
		  										    		$("#substaidd").val(data[10]);
		  										    		$("#substanamee").val(data[1]);
		  										    		$("#substacapp").val(data[2]);
		  										    		$("#substacodee").val(data[3]);
		  										    		$("#tpparcodee").val(data[6]);
		  										    		$("#subdivcodee").val(data[5]);
			  							  					$("#modifyId").val(data[0]);
			  							  				
			  							  					
		  											
		  												}
		  			});
		  $('#'+param).attr("data-toggle", "modal");
		  $('#'+param).attr("data-target","#stack2");
		  }
   
	
	function deleteSubstation(param)
	{ 
		 $("#substaidd").val(param);
		 var id=$("#substaidd").val();
		 bootbox.confirm("Are you sure want to delete this record ?", function(confirmed) {
			 if(confirmed == true){
				
				 $.ajax(
				  			{
				  					type : 'GET',
				  					url : "./deleteSubStationDetails/" + '/'+id,
				  					dataType : "text",
				  					success : function(response)
				  												   {
				  						                            if(response=="Deleted")
		  										    			   {
		  										    			   bootbox.alert("Substation has been deleted successfully.");
		  										    			 getSubStationDetailsNew();
		  										    			   }else if(response="Record Exist") 
		  										    			   {
		  										    			   bootbox.alert("Substation can not be deleted as feeder/s attached to it.")
		  										    			   }  
				  												}
				  								});
			 }
		 });
	}


	
	
	
 </script>
<div class="page-content" >
<!-- <div class="row">
<div class="col-md-12"> -->
<c:if test = "${not empty msg}">
			         <div class="alert alert-success display-show">
					 <button class="close" data-close="alert"></button>
					 <span style="color:green" >${msg}</span>
			       </div>
</c:if>
<c:if test = "${editRights eq 'yes'}">
 <div class="row">
		<div class="col-md-12">

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-cogs"></i>Bulk Edit(Sub-Station)				
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
					</div>
				</div>
				<div class="portlet-body">
             <%-- <c:if test = "${editRights eq 'yes'}"> --%>
					<form action="./uploadSubstationFile" id="uploadFile"
						enctype="multipart/form-data" method="post">

						<div class="form-group">
							<div class="row" style="margin-left: -1px;">
							  <div class="col-md-6" >
							  	<div>
									<label for="exampleInputFile1">Only Upload xlsx File(<a href='#' id='substationSample' onclick='downLoadSampleExcel()' >Download Sample-File</a>)</label> 
									<input type="file" id="fileUpload" onchange="ValidateSingleInput(this);" name="fileUpload"><br />
		
									<button class="btn blue pull-left" style="display: block;"
										id="uploadButton"  onclick="return finalSubmit();">Upload
										File</button>
									&nbsp;
								</div>
							 </div>
					
							 <div class="col-md-6">
								 <div  style="margin-left: 0px;">
									 <label><b>Note:-</b></label> 
						
									 <ol>
									  <li>User can able to do bulk Update Only.</li>  
									  <li>User can able to update only SubStation Name,Latitude,Longitude and Voltage level.</li>
									  <!-- <li>Latitude, Longitude  and Voltage level should be Number.</li> -->
									  <li>Latitude and Longitude should be Number.</li>
									  <li>Voltage level should be in Number and value should be 11, 33, 100, 200, 300 and 400. </li>
									</ol> 
								 </div>
							  </div>
							</div>
						</div>
					</form>

					<div align="center">
						<div class="col-md-6">
							<div id='loadingmessage' style='display: none'>
								<img alt="image" src="./resources/assets/img/Preloader_3.gif"
									class="ajax-loader" width=100px; height=100px;> <span
									id="load" class="wait" style="color: blue;"><b>Uploading
										Data Please Wait</b>!!!!</span>
							</div>
						</div>
					</div>

				</div>

			</div>
		</div>
	</div>
	
</c:if>

						<div class="portlet box blue">
						
						<div class="portlet-title">
							<div class="caption">
							<i class="fa fa-edit"></i>SubStation Details
							</div>
						</div>
						
						 <div class="portlet-body">
						
						 	<c:if test = "${officeType eq 'corporate' }">
						  <div class="btn-group">
						  <button class="btn green" data-target="#stack1" data-toggle="modal" onclick="return ClearData()" id="addData" >
									      Add Substation <i class="fa fa-plus"></i>
						  </button></div>	</c:if>
						  <br>
							<br>
							<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>	
						  
				<div class="row">
						<div class="col-md-12">
						 <%--  <form action="./substationdetails"> --%>
						  
						  <div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<!-- <li id="print"><a href="#">Print</a></li> -->	
										<li><a href="#" id=""
								 				onclick="exportPDF('sample1','SubStationData')">Export to PDF</a></li>									
										<li><a href="#" id="excelExport" onclick="exportToExcelSubMethod('sample_1', 'SubstationDetails')">Export to Excel</a></li>
									</ul>
								</div>
							<!-- <div class="portlet-body"> -->	
						  <table class="table table-striped table-hover table-bordered" id="sample1" >
                          
						  <thead>
							<tr>
								<th>SL No</th>
						       <c:if test = "${editRights eq 'yes'}">
				                <th>EDIT</th></c:if>
								<!-- <th>ID</th> -->
								<!-- <th>Substation Id</th>  -->
								<th>Substation Name</th>
								<th>Voltage level</th>
								<th>Substation Code</th>
								<th>Feeder Count</th>
								<th>Town Count</th>
								<th>Latitude</th>
								<th>Longitude</th>
								<th>Dcu NO</th>
								<th>Substation Capacity(in MVA)</th>
								<th>EntryBy</th>
								<th>EntryDate</th>
								<th>UpdateBY</th>
								<th>UpdateDate</th>
								
								
			         <c:if test = "${editRights eq 'yes'}">
								<th>Delete</th> </c:if>
								<c:if
									test="${officeType eq 'Office_Staff' ||  userType eq 'ADMIN'}">
									<th>ACTION</th>
								</c:if>
							</tr>
						</thead>
					<tbody id="getsubstationdetails">
			<%-- 		<c:set var="count" value="1" scope="page"></c:set>
					<c:forEach var="element" items="${substationList}">
					<tr>
					<c:if test = "${officeType eq 'subdivision' || userType eq 'ADMIN'}">
					    <td><a href="#" onclick="editUser(this.id)" id="${element[13]}">Edit</a></td></c:if>
					    
					    <td>${element[14]}</td>
					    <td>${element[0]}</td>
					    <td>${element[1]}</td>
					    <td>${element[3]}</td>
					    <td>${element[4]}</td>
					    <td>${element[6]}</td>
					    <td>${element[7]}</td>
					    <td>${element[8]}</td>
					    <td>${element[9]}</td> 
					    <c:if test = "${officeType eq 'subdivision' ||  userType eq 'ADMIN'}">
					    <td><a href="#" onclick="deleteSubstation(this.id)" id="${element[13]}">Delete</a></td></c:if> 
					</tr>
					<c:set var="count" value="{count+1}" scope="page"></c:set>
					</c:forEach> --%>
					</tbody>
					    </table>
					  <%--   </form> --%>
					    </div></div> 
						</div>
						</div>
                          
                </div>
                
              		
					   
<div id="stack1" hidden="false" class="modal fade" role="dialog" id="popUp" aria-labelledby="myModalLabel10" aria-hidden="true">
					   <div class="modal-dialog">
					   <div class="modal-content">
					   <div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h5 class="modal-title"><b>Add Substation</b></h5>
						</div>
						<div class="modal-body" >
                       <%--  <form action="./addSubstationdetails" id="addSubstationdetailsId"> --%>  							  
                           <table id="stack1id" class="table table-striped table-hover table-bordered ">
                           <thead>
                          
                          <c:if test = "${officeType eq 'subdivision' }">
                            <tr id="Subdivision_Code">
												    <td>Subdivision<font color="red">*</font></td>
													<td><input type="text" id="subdivcode" value="${SubdivName}" readonly="readonly"   class="form-control placeholder-no-fix" placeholder="Enter Substation Name" name="subdivcode" maxlength="200" ></input>
											        </td>
						   </tr>
						   </c:if>
						     <c:if test = "${userType eq 'ADMIN' && officeType ne 'subdivision'}">
						      <tr id="Subdivision_Code">
												    <td>Subdivision<font color="red">*</font></td>
													<td><%-- <input type="text" id="subdivcode" value="${SubdivName}"    class="form-control placeholder-no-fix" placeholder="Enter Substation Name" name="subdivcode" maxlength="200" ></input>  --%>
													  	<select class="form-control select2me input-medium" name="subdiv1" id="subdivcode" onchange="showTownbyMainSubdiv(this.value)" data-placeholder="Select SubDivision" >
										                   	    <option value="">Select SubDivision</option>
										                       	<c:forEach var="elements" items="${subdivisionList}">
																<option value="${elements}">${elements}</option>
																</c:forEach> 
									                     </select>
											        </td>
						   </tr>
						     </c:if>
						     
						        <tr id="Town_code">
												    <td>Town<font color="red">*</font></td>
													<td><%-- <input type="text" id="subdivcode" value="${SubdivName}"    class="form-control placeholder-no-fix" placeholder="Enter Substation Name" name="subdivcode" maxlength="200" ></input>  --%>
													  	<select class="form-control select2me input-medium" name="toenDiv1" id="Towncode" data-placeholder="Select Town" >
										                   	    <option value="">Select Town</option>
										                       
									                     </select>
											        </td>
						   </tr>
						     
						     		 			
						   <tr id="Substation_Name">
													<td>Substation Name<font color="red">*</font></td>
													<td><input type="text" id="substaname" class="form-control placeholder-no-fix" placeholder="Enter Substation Name"  name="SubstationName" maxlength="200" ></input>
											        </td>
						  </tr>
										
						  <tr id="Substation_Capacity">
													<td>Substation Voltage level<font color="red">*</font></td>
													<td><!-- <input type="number" id="substacap"  class="form-control placeholder-no-fix"
													 placeholder="Enter Substation Capacity" name="Substationcapacity" maxlength="6"/></input> -->
													 
													 <select  name="substacap"
								id="substacap" class="form-control">
								<option selected disabled value="">Select Voltage level</option>
								<option value=110/11>110/11KV</option>
								<option value=33/11>33/11KV</option>
								<option value=110/33/11>110/33/11KV</option>
								<option value=110/33/22>110/33/22KV</option>
								<option value=110/22>110/22KV</option>
							</select>
													</td>
					 	  </tr>
					 	 <tr id="Substation_Capacityin_MVA)">
													<td>Substation Capacity(in MVA)</td>
													<td><input type="text" id="substationCapacityinMVA" class="form-control placeholder-no-fix" placeholder="Enter Substation Capacity"  name="substationCapacityinMVA" maxlength="10" ></input>
											        </td>
						  </tr>
							<tr id="TP_Substation_Code">
														<td>TP Substation Code<font color="red">*</font></td>
														<td><input type="text"  id="substacode"  class="form-control placeholder-no-fix" onchange="checkAvail()" placeholder="Enter TP Substation Code" 
														name="TPSubstationCode" maxlength="12"></input>
														
														</td>
													</tr>
							<!--  <tr id="TP_Parent_Code" >
														<td>TP Parent Code</td> <font color="red">*</font>
														<td><input type="text"   id="tpparcode"  class="form-control placeholder-no-fix"   placeholder="Enter TP Parent Code" 
														name="TPParentCode"  maxlength="12"></input>
														onchange="parentsubdiv()"
														</td>
																
													</tr>  -->
													
									</thead>		 																																							
                           </table>
                           </br>
                           <table id="stack2id" class="table table-striped table-hover table-bordered " style='width:100%'>
                           <thead >	
													
						    <tr id="Zone"  >
						    
						                               <td style='width:36%'>Region<font color="red">*</font></td>
						                               <td><select class="form-control select2me  placeholder-no-fix" id="zone" type="text" name="zone" onclick="showCircle(this.value)" >
							                           <option value="">Select Region</option>
										               
										               <c:forEach var="elements" items="${zoneList}">
														<option value="${elements}">${elements}</option>
														</c:forEach> 
						                               </select></td>
						                               
						                            </tr>						
							<tr id="Circle_Name">
							
							                            <td>Parent Circle<font color="red">*</font></td>
							                            <td><select class="form-control select2me placeholder-no-fix" id="circle" onchange='showDivision(this.value)'  name="circle" >
							                            <option value="">Select Circle</option>
							                            </select></td>
							                       
							                        </tr>    
							                       <tr id="Division_Name">
							
							                            <td>Parent Division<font color="red">*</font></td>
							                            <td><select class="form-control select2me  placeholder-no-fix"  id="division" onchange='showSubdivByDiv(this.value)' name="division" >
							                            <option value="">Select Division</option>
							                            </select></td> 
							                            
							                         </tr>                           						
							<tr id="Parent_Subdivision">
														<td>Parent Feeder Subdivision<font color="red">*</font></td>
														<td><select path="ParentSubdivision" id="parsubdiv" class="form-control select2me"   onclick="showTownbySubdiv(this.value)"  
														name="ParentSubdivision" required="required" >
										                <option value="">Select Sub-Division</option>
                                                        </select>
														</td>
													</tr>
													
							<tr id="Parent_TownCode">
														<td>Parent Feeder Town<font color="red">*</font></td>
														<td><select path="ParentTown" id="ParentTown" class="form-control select2me" 
														name="ParentTown" required="required" >
										                <option value="">Select Town</option>
                                                        </select>
														</td>
													</tr>						
						   <tr id="Parent_Feeder_Voltage">
														
														<td>Parent Feeder Voltage<font color="red">*</font></td>
														<!-- <td><input type="text"   id="parfeedervol" class="form-control placeholder-no-fix" placeholder="Enter Parent Feeder Voltage" name="ParentFeederVoltage" maxlength="10"></input> -->
													<td>	<select id="parfeedervol" class="form-control select2me"   name="ParentFeederVoltage"  onchange="showParFeeder(this.value);">
															    <option value="">Select Voltage</option>
										                       	<option value="400000">400000</option>
										                       	<option value="220000">220000</option>
										                       	<option value="66000">66000</option>
										                       	<option value="33000">33000</option>
										                       
													 		</select></td>
													</tr>		
						  
						  
						  <%--  <tr id="Parent_Feeder_Voltage">
														
														<td>Parent Feeder Voltage</td>
														<td><font color="red">*</font></td>
														<!-- <td><input type="text"   id="parfeedervol" class="form-control placeholder-no-fix" placeholder="Enter Parent Feeder Voltage" name="ParentFeederVoltage" maxlength="10"></input> -->
														<td><select id="parfeedervol" class="form-control select2"  placeholder="select Roles" name="ParentFeederVoltage"  onchange="showParFeeder(this.value);">
														<option value="default">Please Select</option>
														<c:forEach var="type" items="${parentfeedervol}">
														<option value="${type}">${type}</option>
														</c:forEach> 
                                                        </select>
														</td>
																
													</tr>	 --%>


							<!-- 	<tr id="Parent_Feeder">
													  <td>Parent Feeder</td> <font color="red">*</font>  
														<td><input type="text"   id="parfeeder" name="ParentFeeder" class="form-control placeholder-no-fix" placeholder="Enter Parent Feeder" maxlength="60"></input>
														<td><select id="parfeeder" class="form-control select2me"   name="ParentFeeder" >
												 	    <option value="">Please Select Feeder</option>
												 	    </select>
														</td>
																
													</tr>  -->
						                               							
		                   </thead>																																									
                           </table>
                           <div class="modal-footer">
                           <button type="button"   data-dismiss="modal" class="btn" onclick="return clearfieldsNew()" >Cancel</button>
                           <button class="btn blue pull-right" id="addOption" type="submit" onclick="return validation()">Add</button>
                           </div>
                          <%--  </form> --%>
                           </div>
					       </div>
					       </div>
					       </div>


<div id="stack2" class="modal fade" role="dialog"
	aria-labelledby="myModalLabel10" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h5 class="modal-title">Edit Substation Details</h5>
			</div>
			<div class="modal-body">
				<table id="stack2id"
					class="table table-striped table-hover table-bordered ">
					<thead>
						<tr id="Subdivision_Code">
							<td>Subdivision Code<font color="red">*</font></td>
							<td><input type="text" id="subdivcodee"
								class="form-control placeholder-no-fix"
								placeholder="Enter Subdivision Code" name="subdivcodee"
								readonly="readonly" ></input></td>
						</tr>
						<tr id="Town_Code_edit_tr">
							<td>Town Code<font color="red">*</font></td>
							<td><input type="text" id="town_code_edit"
								class="form-control placeholder-no-fix"
								name="town_code_edit"
								readonly="readonly" ></input></td>
						</tr>
						<tr id="Substation_ID">
							<td>Substation ID<font color="red">*</font></td>
							<td><input type="text" id="substaidd"
								class="form-control placeholder-no-fix" readonly="readonly"
								placeholder="Enter Substation ID" name="substaidd"
								maxlength="12"></input></td>
						</tr>
						<tr id="Substation_Name">
							<td>Substation Name<font color="red">*</font></td>
							<td><input type="text" id="substanamee"
								class="form-control placeholder-no-fix"
								placeholder="Enter Substation Name" name="substanamee"
								maxlength="200"></input></td>
						</tr>
						<tr id="Substation_Capacity">


							<td>Substation Voltage level<!-- <font color="red">*</font> --></td>
							<td><!-- <input type="text" id="substacapp"
								class="form-control placeholder-no-fix"
								placeholder="Enter Substation Capacity" name="substacapp"
								maxlength="6" /></input> -->
								<select  name="substacapp"
								id="substacapp" class="form-control">
								<option selected disabled value="">Select Voltage level</option>
								<option value=110/11>110/11KV</option>
								<option value=33/11>33/11KV</option>
								<option value=110/33/11>110/33/11KV</option>
								<option value=110/33/22>110/33/22KV</option>
								<option value=110/22>110/22KV</option>
							</select>
								</td>
						</tr>
						<tr id="Substation_Capacityin_MVA)">
													<td>Substation Capacity(in MVA)</td>
													<td><input type="text" id="editSubstationCapacityinMVA" class="form-control placeholder-no-fix" placeholder="Enter Substation Capacity"  name="editSubstationCapacityinMVA" maxlength="10" ></input>
											        </td>
						  </tr>
						
						<tr id="Latitude">
							<td>Latitude<!-- <font color="red">*</font> --></td>
							<td><input type="text" id="latitude"
								class="form-control placeholder-no-fix"
								name="Latitude" ></input></td>
						</tr>
						<tr id="Longitude">
							<td>Longitude<!-- <font color="red">*</font> --></td>
							<td><input type="text" id="longitude"
								class="form-control placeholder-no-fix"
								name="longitude"></input></td>
						</tr>
						
						<tr id="DcuNo">
							<td>DCU No<!-- <font color="red">*</font> --></td>
							<td><input type="text" id="dcuno"
								class="form-control placeholder-no-fix"
								name="dcuno"></input></td>
						</tr>
											</thead>
				</table>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn">Cancel</button>
					<button class="btn blue pull-right" id="modifyId" type="submit"
						value="" onclick="return modifySubstation();">Modify</button>
				</div>
				<%-- </form> --%>
			</div>
		</div>
	</div>
</div>





<div id="stack3" class="modal fade" role="dialog"
	aria-labelledby="myModalLabel10" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header" style="background-color: #4b8cf8;">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h5 class="modal-title" ><font style="font-weight: bold; color: white;">Subdivision Details</font></a></h5>
			</div>
			<div class="modal-body">
				<table id="subdibPopTbl" class="table table-striped table-hover table-bordered ">
					<thead>
						<tr>
							<th>SL No</th>
							<th>Feeder Name</th>
							<th>Feeder Code</th>
						</tr>
						
					</thead>
					<tbody id="subdivDetailsForSubstation">
					</tbody>
				
				</table>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn">Cancel</button>

				</div>

			</div>
		</div>
	</div>
</div>


<div id="stack4" class="modal fade" role="dialog"
	aria-labelledby="myModalLabel10" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header" style="background-color: #4b8cf8;">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h5 class="modal-title" ><font style="font-weight: bold; color: white;">Town Details</font></a></h5>
			</div>
			<div class="modal-body">
				<table id="townPopTbl" class="table table-striped table-hover table-bordered ">
					<thead>
						<tr>
							<th>SL No</th>
							<th>Town Name</th>
							<th>Town Code</th>
						</tr>
						
					</thead>
					<tbody id="townDetailsForSubstation">
					</tbody>
				</table>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn">Cancel</button>

				</div>

			</div>
		</div>
	</div>
</div>

<div id="stack10" hidden="false" class="modal fade" role="dialog"
	id="popUp" aria-labelledby="myModalLabel10" aria-hidden="true">
	<div class="modal-dialog">

				
		<div class="modal-content">
		<div class="modal-header">
		 <div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-cogs"></i>Upload Town Wise SLD File</div>
							
						</div>
		
			
			<div class="portlet-body">
			<div class="modal-body">
			
			<form action="./uploadSLDSubstnFile" id="uploadSLDSSFile" enctype="multipart/form-data" method="post">
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label">Substation Code</label> <input
								type="text" id="ssid"
								class="form-control placeholder-no-fix"
								name="ssid" maxlength="12"  readonly="readonly"></input>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label">Substation Name</label> <input
								type="text" id="ssname"
								class="form-control placeholder-no-fix"
								name="ssname" readonly="readonly" maxlength="50"></input>
						</div>
					</div>											
				</div>
				
				<div class="row">
				
					<div class="col-md-6">
							<div class="form-group">
								<label class="control-label">Only Upload SLD pdf File</label>
								 <input type="file" id="fileUpload" onchange="ValidateSingleInput(this);" name="fileUpload"><br />
										
							</div>
						</div>
			   </div>

				
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn"
						onclick="return formreset()">Cancel</button>
					<button class="btn blue pull-right" id="addOption" type="submit"
						onclick="return validationModal()">Add/Modify</button>
				</div>
			</form>

			</div>
			</div>
			</div>
			</div>
		</div>
	</div>
</div>

<script>
	//New Methods to pull Hirerachy

	function showCircle(zone) {
		var zone = $("#zone").val();
		$
				.ajax({
					url : './getCircleByZone',
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					data : {
						zone : zone
					},
					success : function(response) {
						var html = '';
						html += "<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control select2me input-medium' type='text'><option value=''>Select Circle</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#circle").html(html);
						$('#circle').select2();
					}
				});
	}
	function showDivision(circle) {
		var zone = $("#zone").val();
		$
				.ajax({
					url : './getDivByCircle',
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					data : {
						zone : zone,
						circle : circle
					},
					success : function(response) {
						var html = '';
						html += "<select id='divisionId' name='divisionId' onchange='showSubdivByDiv(this.value)' class='form-control select2me input-medium' type='text'><option value=''>Select Division</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#division").html(html);
						$('#division').select2();
					}
				});
	}

	function showSubdivByDiv(division) {
		var zone = $("#zone").val();
		var circle = $('#circle').val();
		$
				.ajax({
					url : './getSubdivByDiv',
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					data : {
						zone : zone,
						circle : circle,
						division : division
					},
					success : function(response1) {
						var html = '';
						html += "<select id='sdoCode' name='sdoCode' onchange='showTownbySubdiv(this.value)' class='form-control select2me input-medium' type='text'><option value=''>Select Sub-Division</option>";
						for (var i = 0; i < response1.length; i++) {
							//var response=response1[i];
							html += "<option value='"+response1[i]+"'>"
									+ response1[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#parsubdiv").html(html);
						$('#parsubdiv').select2();
					}
				});
	}

	function showTownbySubdiv(sitecode) {
		// var zone = $('#zone').val();

		var circle = $('#circle').val();
		$
				.ajax({
					url : './getTownNameandCodeBySubDiv',
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					data : {
						sitecode : sitecode
					},
					success : function(response1) {
						var html = '';
						html += "<select id='townCode' name='townCode' class='form-control select2me input-medium' type='text'><option value=''>Select Town</option>";
						for (var i = 0; i < response1.length; i++) {
							//var response=response1[i];
							html += "<option value='"+response1[i][0]+"'>"
									+ response1[i][1] + "</option>";
						}
						html += "</select><span></span>";
						$("#ParentTown").html(html);
						$('#ParentTown').select2();
					}
				});

	}

	function showTownbyMainSubdiv(sitecode) {
		// var zone = $('#zone').val();

		var circle = $('#circle').val();
		$
				.ajax({
					url : './getTownNameandCodeBySubDiv',
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					data : {
						sitecode : sitecode
					},
					success : function(response1) {
						var html = '';
						html += "<select id='townCode' name='townCode' class='form-control select2me input-medium' type='text'><option value=''>Select Town</option>";
						for (var i = 0; i < response1.length; i++) {
							//var response=response1[i];
							html += "<option value='"+response1[i][0]+"'>"
									+ response1[i][0] + "-" + response1[i][1]
									+ "</option>";
						}
						html += "</select><span></span>";
						$("#Towncode").html(html);
						$('#Towncode').select2();
					}
				});

	}

	/* function showCircle(zone)
	 { 
	 $.ajax({
	 url:'./showCircle'+'/'+zone,
	 type:'GET',
	 success:function(response)
	 {
	 var html = '';
	 html += "<option value=''></option>";
	 for (var i = 0; i < response.length; i++) {
	 html += "<option  value='"+response[i]+"'>" + response[i]
	 + "</option>";
	 }

	 $("#circle").html(html);
	 $('#circle').select2();
	
	 }
	
	 });
	 } */

	/* function showDivision(circle)
	{
		$.ajax({
			url:'./showDivision'+'/'+circle,
			type:'GET',
			success:function(response)
			{
				var html = '';
				html += "<option value=''></option>";
				for (var i = 0; i < response.length; i++) {
					html += "<option  value='"+response[i]+"'>" + response[i]
							+ "</option>";
				}

				$("#division").html(html);
				$('#division').select2();
			}
		});
	} */

	/* function showSubdivision(division)
	{
		$.ajax({
			     url:'./showSubdivision'+'/'+division,
			     type:'GET',
			     success:function(response)
			     {
			    	 var html = '';
						html += "<option value=''></option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option  value='"+response[i]+"'>" + response[i]
									+ "</option>";
						}

						$("#parsubdiv").html(html);
						$('#parsubdiv').select2();
			     }
		});
	} */

	function checkAvail() {
		var tpSubstaCode = document.getElementById('substacode').value;

		$.ajax({
			url : "./checkDuplicateParent/" + tpSubstaCode,
			type : "GET",
			dataType : "text",
			async : false,
			cache : false,
			success : function(response) {

				if (response == 'Code Exist') {
					bootbox.alert("TP Substation Code is already Exist");
					$('#substacode').val('');
				}

			}
		});
	}

	function checkAvailEdit() {

		var modtpSubstaCode = document.getElementById('substacodee').value;

		$.ajax({
			url : "./checkDuplicateParent/" + modtpSubstaCode,
			type : "GET",
			dataType : "text",
			async : false,
			cache : false,
			success : function(response) {

				if (response == 'Code Exist') {
					bootbox.alert("TP Substation Code is already Exist");
					$('#substacodee').val('');
					return false;
				}

			}
		});
	}

	/* function showParFeeder(parentfeedervol) {
		var subdiv = document.getElementById("parsubdiv").value;
		$.ajax({
			url : "./showParentFeeder" + '/' + parentfeedervol + '/' + subdiv,
			type : 'GET',
			success : function(response) {
				var html = '';
				html += "<option value=''></option>";
				for (var i = 0; i < response.length; i++) {
					html += "<option  value='"+response[i]+"'>" + response[i]
							+ "</option>";
				}

				$("#parfeeder").html(html);
				$('#parfeeder').select2();

			}
		});
	} */

	function getSubStationDetailsNew() {

		var officeType = "${officeType}";
		var userType = "${userType}";
		$('#imageee').show();
		$.ajax({
					url : './getsubstationdetails',
					type : 'GET',
					success : function(response) {
						//alert(response);
						$('#imageee').hide();
						if (response.length != 0) {
							var html = "";
							for (var i = 0; i < response.length; i++) {
								var element = response[i];
								html += "<tr>";
								html += "<td>" + (i + 1) + "</td>";
								if ( officeType == 'corporate') {
									html += "<td><a href='#' onclick='editUserNew(\""+element[2]+"\")' id="+element[2]+">Edit</a></td>";

									}
								html += "<td>"
										+ (element[0] == null ? "" : element[0])
										+ "</td>"
										+ "<td>"
										+ (element[1] == null ? "" : element[1])
										+ "</td>"
										+ "<td>"
										+ (element[2] == null ? "" : element[2])
										+ "</td>"

										+ "<td style='text-align: center;'>"
										+ (element[3] == null ? "": "<a href='#' style='font-weight: bold;' onclick='showSubdivisionsModal(\""
														+ element[2]
														+ "\")'>"
														+ element[3] + "</a>")
										+ "</td>"

										+ "<td style='text-align: center;'>"
										+ (element[4] == null ? ""
												: "<a href='#' style='font-weight: bold;' onclick='showTownsModal(\""
														+ element[2]
														+ "\")'>"
														+ element[4] + "</a>")
										+ "</td>"

										+ "<td>"
										+ (element[9] == null ? "" : element[9])
										+ "</td>"

										+ "<td>"
										+ (element[10] == null ? "" : element[10])
										+ "</td>"

										+ "<td>"
										+ (element[11] == null ? "" : element[11])
										+ "</td>"
										+ "<td>"
										+ (element[12] == null ? "" : element[12])
										+ "</td>"
										+ "<td>"
										+ (element[5] == null ? "" : element[5])
										+ "</td>"
										+ "<td>"
										+ (element[6] == null ? "" : moment(
												element[6]).format(
												'DD-MM-YYYY hh:mm:ss'))
										+ "</td>"
										+ "<td>"
										+ (element[7] == null ? "" : element[7])
										+ "</td>"
										+ "<td>"
										+ (element[8] == null ? "" : moment(
												element[8]).format(
												'DD-MM-YYYY hh:mm:ss'))
										+ "</td>"

								if ( officeType == 'corporate') {
									/* html += "<td><a href='#' onclick='deleteSubstationNew(\""+element[2]+"\")' data-toggle='modal' >Delete</a></td>"; */
									html += "<td><a>Delete</a></td>";
									}

								if(element[13]!=null){
									html += "<td>"+ "<a class='btn btn-info' onclick='viewSLDData(\""+ element[0]+ "\",\""+ element[2]+ "\")' data-toggle='tooltip' title='Open PDF'  aria-label='View'><i class='fa fa-file-pdf-o' ></i> </a>&nbsp;<a class='btn btn-success' onclick='uploadSLDData(\""+ element[0]+ "\",\""+ element[2]+ "\")' data-toggle='modal'  data-target='#stack10' title='Add/Modify' aria-label='Add'> <i class='fa fa-upload'  ></i></a>&nbsp;<a class='btn btn-danger' onclick='deleteSLDData(\""+ element[0]+ "\",\""+ element[2]+ "\")' data-toggle='tooltip' title='Delete PDF' aria-label='Delete'><i class='fa fa-trash-o'></i></a>"+ "</td>"				
									
								} else{
									html += "<td>"+ "<a class='btn btn-success'  onclick='uploadSLDData(\""+ element[0]+ "\",\""+ element[2]+ "\")' data-toggle='modal'  data-target='#stack10' title='Add/Modify'  aria-label='Add'><i class='fa fa-upload' ></i></a>"+ "</td>"				
									
								}
								html += "</tr>";
							}
							$('#sample1').dataTable().fnClearTable();
							$('#getsubstationdetails').html(html);
							loadSearchAndFilter('sample1');
						
							$('#stack1').modal('hide');
						} else {
							bootbox.alert("No substation found");
						}
					}

				});

	}

	function getSubStationDetails() {
		$
				.ajax({
					url : './getSubStationDetails',
					type : 'GET',
					success : function(response) {
						//alert(response);
						if (response.length != 0) {
							var html = "";
							for (var i = 0; i < response.length; i++) {
								var element = response[i];
								html += "<tr>"
										+ <c:if test = "${officeType eq 'subdivision' || userType eq 'ADMIN'}">
								"<td><a href='#' onclick='editUser(this.id)' id="
										+ element[13]
										+ ">Edit</a></td></c:if>"

										/* +"<td>"+ (element[14] == null ? "" :element[14])+"</td>" */
										+ "<td>"
										+ (element[0] == null ? "" : element[0])
										+ "</td>"
										+ "<td>"
										+ (element[1] == null ? "" : element[1])
										+ "</td>"
										+ "<td>"
										+ (element[5] == null ? "" : element[5])
										+ "</td>"
										+ "<td>"
										+ (element[4] == null ? "" : element[4])
										+ "</td>"
										+ "<td>"
										+ (element[15])
										+ "</td>"
										+ "<td>"
										+ (element[6] == null ? "" : element[6])
										+ "</td>"
										+ "<td>"
										+ (element[7] == null ? "" : moment(
												element[7]).format(
												'DD-MM-YYYY hh:mm:ss'))
										+ "</td>"
										+ "<td>"
										+ (element[9] == null ? "" : element[8])
										+ "</td>"
										+ "<td>"
										+ (element[9] == null ? "" : moment(
												element[9]).format(
												'DD-MM-YYYY hh:mm:ss'))
										+ "</td>"
										+ "<td>"
										+ <c:if test = "${officeType eq 'subdivision' || userType eq 'ADMIN'}">
								"<a href='#' onclick='deleteSubstation(this.id)' id="
										+ element[14]
										+ " data-toggle='modal' >Delete</a></c:if>"
										+ "</td>";
							}
							$('#sample1').dataTable().fnClearTable();
							$('#getsubstationdetails').html(html);
							loadSearchAndFilter('sample1');
							$('td:nth-child(1)').hide();
							$('#stack1').modal('hide');
						} else {
							bootbox.alert("No substation found");
						}
					}

				});
	}

	function uploadSLDData(ssName,ssCode) {
		 $("#ssid").val(ssCode);
		 $("#ssname").val(ssName);

	}
	function viewSLDData(ssName,ssCode){
		window.open("./viweSLDSubstanData/"+ssCode);

	}

	function deleteSLDData(ssName,ssCode){
		bootbox.confirm("Are you sure want to delete this record ?", function(result) {
			  if(result == true)
	            {
				  $("#imageee").show();
					$
					.ajax({
						url : './deleteSLDSubstanData',
						type : 'POST',
						data : {
							ssCode : ssCode,
							ssName : ssName
						},
						dataType : 'text',
						success : function(response) {
						 $("#imageee").hide();
								if(response=='success'){
									bootbox.alert("SLD File Deleted Succusfully");
									$('#sample1').dataTable().fnClearTable();
									$('#viewTownDetails').click();
									$('#viewTownDetails').click();
									loadSearchAndFilter('sample1');
			
								} else{
									bootbox.alert("OOPS! Something went wrong!!");
									$('#sample1').dataTable().fnClearTable();
									loadSearchAndFilter('sample1');
								}
							},
							error: function (e) {
								   $("#imageee").hide();
									bootbox.alert("OOPS! Something went wrong!!");
					        }
					});
	            }

		  });
	}


	var _validFileExtensions = [".pdf"];    //,".jpg","jpeg",".png",".gif"
	function ValidateSingleInput(oInput) {
	    if (oInput.type == "file") {
	        var sFileName = oInput.value;
	         if (sFileName.length > 0) {
	            var blnValid = false;
	            for (var j = 0; j < _validFileExtensions.length; j++) {
	                var sCurExtension = _validFileExtensions[j];
	                if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
	                    blnValid = true;
	                    break;
	                }
	            }
	             
	            if (!blnValid) {
	            	//bootbox.alert("Sorry,  " + sFileName + " is invalid, allowed extensions is: " + _validFileExtensions.join(", "));
	            	bootbox.alert("Only PDF file is allowed to Upload");
	                oInput.value = "";
	                return false;
	            }
	        }
	    }
	    return true;
	}
	

	/* function ShowTownCode(sitecode){
		$.ajax({
		     url:'./showTown',
		     type:'GET',
		     data:{
		    	 sitecode:sitecode
		     },
		     success:function(response)
		     {
		    	 var html = '';
					html += "<option value=''></option>";
					for (var i = 0; i < response.length; i++) {
						html += "<option  value='"+response[i]+"'>" + response[i]
								+ "</option>";
					}

					$("#parsubdiv").html(html);
					$('#parsubdiv').select2();
		     }
	});
	} */

	function clearfieldsNew() {

		document.getElementById("substaname").value = '';
		document.getElementById("substacap").value = '';
		document.getElementById("substacode").value = '';
		 //document.getElementById("tpparcode").value = ''; 
		$("#zone").val("").trigger("change");
		$("#circle").val("").trigger("change");
		$("#division").val("").trigger("change");
		$("#parsubdiv").val("").trigger("change");
		//$("#parfeedervol").val("").trigger("change");
		$("#parfeeder").val("").trigger("change");

	}
</script> 
 
 <script>
function showSubdivisionsModal(ssid){
	$("#stack3").modal('show');
	$.ajax({
	     url:'./getSubdivisionForSubstation',
	     type:'POST',
	     data:{
	    	 ssid : ssid,
	     },
	     success:function(response)
	     {
	    	 var html = '';
	    	 for (var i = 0; i < response.length; i++) {
					var element = response[i];
					html += "<tr>";
					html +="<td>" + (i + 1) + "</td>"
						 + "<td>" + element[1] + "</td>"
						 + "<td>" + element[0] + "</td>";
					html += "</tr>";
	    	}
	    	$('#subdibPopTbl').dataTable().fnClearTable();
			$('#subdivDetailsForSubstation').html(html);
			loadSearchAndFilter('subdibPopTbl');

				
	     }
	});
	
}
function showTownsModal(ssid){
	$("#stack4").modal('show');
	$.ajax({
	     url:'./getTownForSubstation',
	     type:'POST',
	     data:{
	    	 ssid : ssid,
	     },
	     success:function(response)
	     {
	    	 var html = '';
	    	 for (var i = 0; i < response.length; i++) {
					var element = response[i];
					html += "<tr>";
					html +="<td>" + (i + 1) + "</td>"
						 + "<td>" + element[1] + "</td>"
						 + "<td>" + element[0] + "</td>";
					html += "</tr>";
	    	}
	    	$('#townPopTbl').dataTable().fnClearTable();
			$('#townDetailsForSubstation').html(html);
			loadSearchAndFilter('townPopTbl');
	     }
	});
	
}


	function editUserNew(param) {
		
		var id = param;

		$.ajax({
			type : "GET",
			url : "./editSubStationDetailsNew/" + id,
			dataType : "json",
			success : function(response) {
				
				var data = response[0];
			//	alert(data[7]);
				$("#subdivcodee").val(data[3]);
				$("#town_code_edit").val(data[4]);
				$("#substaidd").val(data[2]);
				$("#substanamee").val(data[0]);
				$("#substacapp").val(data[1]);
				$("#latitude").val(data[5]);
				$("#longitude").val(data[6]);
				$("#dcuno").val(data[7]);
				$("#editSubstationCapacityinMVA").val(data[8]);
				

			}
		});
		$("#stack2").modal('show');
	}


		function deleteSubstationNew(param) {
			//alert(param);
			bootbox.confirm("Are you sure want to delete this record ?",
				function(confirmed) {
					if (confirmed == true) {
						$.ajax({
							type : 'GET',
							url : "./deleteSubStationDetailsNew/"+ param,
							dataType : "TEXT",
							success : function(response) {
								if (response == "Deleted") {
									bootbox.alert("Substation has been deleted successfully.");
									getSubStationDetailsNew();
								} else if (response = "Record Exist") {
									//bootbox.alert("Substation can not be deleted as feeder/s attached to it.")
									bootbox.confirm("Substation attached to feeder are you sure want to delete this record ?",
											function(confirmed) {
												if (confirmed == true) {
													$.ajax({
														type : 'GET',
														url : "./subStationDetails/"+ param,
														dataType : "TEXT",
														success : function(response) {
															if (response == "Deleted") {
																bootbox.alert("Substation has been deleted successfully.");
																getSubStationDetailsNew();
															} else {
																bootbox.alert(response);
															}
														}
													});
												}
											});
									
								} else {
									bootbox.alert(response);
								}
							}
						});
					}
				});
		}
		
		function exportPDF()
		{
			window.location.href=("./SubstationDetailsPDF");
		}


		function downLoadSample(type){
			
			window.location.href="http://1.23.144.187:8102/downloads/sldreport/SubstationDetailsSample.xlsx";
		}


		function finalSubmit()
		{
			 if(document.getElementById("fileUpload").value == "" || document.getElementById("fileUpload").value == null)
				  {
				    bootbox.alert(' Please Select xlsx file to upload');
			 	    return false;
				  }
		}


		var _validFileExtensions1 = [".xlsx"];    //,".jpg","jpeg",".png",".gif"
		function ValidateSingleInput(oInput) {
		    if (oInput.type == "file") {
		        var sFileName = oInput.value;
		         if (sFileName.length > 0) {
		            var blnValid = false;
		            for (var j = 0; j < _validFileExtensions.length; j++) {
		                var sCurExtension = _validFileExtensions[j];
		                if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
		                    blnValid = true;
		                    break;
		                }
		            }
		             
		            if (!blnValid) {
		            	//bootbox.alert("Sorry,  " + sFileName + " is invalid, allowed extensions is: " + _validFileExtensions.join(", "));
		            	bootbox.alert("Only xlsx file is allowed to Upload");
		                oInput.value = "";
		                return false;
		            }
		        }
		    }
		    return true;
		}
		



     function exportToExcelSubMethod(){
    	 window.open("./SubstationExcel");
           
         }


function downLoadSampleExcel()
{
	window.open("./SampleSubstationExcelDownload");
	}


		

	</script>
