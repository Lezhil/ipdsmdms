<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>



		
		<script type="text/javascript">

			
			  $(".page-content").ready(function()
		   	    	   {    
		   	    	      App.init();
		   	    	      TableEditable.init();
			   	    	  TableManaged.init();
			   	    	 	loadSearchAndFilter('sample1');  
			   	    	   FormComponents.init();
			   	    	  UIDatepaginator.init(); 
			   	    	//  $("#tableid").hide();
			   	    	 
			   	    	
			   	    	/*   $("#selectedDateId").val(getPresentMonthDate('${selectedMonth}')); */
			   	   		$('#eaId,#fdrEneryUpdateId').addClass('start active ,selected');
			    	 $("#MDMSideBarContents,#MDASSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
		   	    	   });

		  $("#month").datepicker( {
			    format: "mm-yyyy",
			    startView: "months", 
			    minViewMode: "months"
			}); 
		  
		  function showCircle(zone)
		  {
		   $.ajax({
		      	url:'./getCircleByZone',
		      	type:'GET',
		      	dataType:'json',
		      	asynch:false,
		      	cache:false,
		      	data : {
		  			zone : zone
		  		},
		      	success:function(response)
		      	{
		  			var html='';
		      		html+="<select id='LFcircle' name='LFcircle' class='form-control' onchange='showTownNameandCode(this.value)' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
		  			for( var i=0;i<response.length;i++)
		  			{
		  				html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
		  			}
		  			html+="</select><span></span>";
		  			$("#LFcircleTd").html(html);
		  			$('#LFcircle').select2();
		      	}
		  	});
		  }
		  
			
			function showTownNameandCode(circle){
			var zone =  $('#LFzone').val(); 
			
			   $.ajax({
			      	url:'./getTownNameandCodebyCircle',
			      	type:'GET',
			      	dataType:'json',
			      	asynch:false,
			      	cache:false,
			      	data : {
			  			zone : zone,
			  			circle:circle
			  		},
			  		success : function(response1) {
			  			
		                var html = '';
		                html += "<select id='LFtown' name='LFtown'  class='form-control input-medium' onchange='showResultsbasedOntownCode(this.value)'  type='text'><option value=''>Select</option>";
		                for (var i = 0; i < response1.length; i++) {
		                    //var response=response1[i];
		                    
		                    html += "<option value='"+response1[i][0]+"'>"
		                            +response1[i][0] +"-"  +response1[i][1] + "</option>";
		                }
		                html += "</select><span></span>";
		                $("#LFtownTd").html(html);
		                $('#LFtown').select2();
		            }
			  	});
			  }

			
			
			
			
			
		  function showTownBySubdiv(subdiv) {
			     // var zone = $('#zone').val();
			      var circle = $('#circle').val();
			      var division = $('#division').val();
			      $.ajax({
			          url : './getTownNameandCodeBySubDiv',
			          type : 'GET',
			          dataType : 'json',
			          asynch : false,
			          cache : false,
			          data : {
			        	  sitecode :subdiv
			          },
			                  success : function(response1) {
			                      var html = '';
			                      html += "<select id='town' name='town'  class='form-control  input-medium'  type='text'><option value=''>Select</option>";
			                      for (var i = 0; i < response1.length; i++) {
			                          //var response=response1[i];
			                          
			                          html += "<option value='"+response1[i][0]+"'>"
			                                  + response1[i][1] + "</option>";
			                      }
			                      html += "</select><span></span>";
			                      $("#townTd").html(html);
			                      $('#town').select2();
			                  }
			              });
			  }

		  function showResultsbasedOntownCode(townCode){
			   $.ajax({
			      	url:'./getFeederTpandName',
			      	type:'GET',
			      	dataType:'json',
			      	asynch:false,
			      	cache:false,
			      	data : {
			      		townCode:townCode
			  		},
			  		success : function(response1) {
			  			 var html = '';
			                html += "<select id='feederTpId' name='feederTpId'  class='form-control input-medium'  type='text'><option value=''>Select Feeder</option><option value='%'>All</option>";
			                for (var i = 0; i < response1.length; i++) {
			                    //var response=response1[i];
			                    
			                    html += "<option value='"+response1[i][0]+"'>"
			                            + response1[i][1] + "</option>";
			                }
			                html += "</select><span></span>";
			                $("#feederDivId").html(html);
			                $('#feederTpId').select2();
		              
		           }
			  	});
			  }



		  function view()
		  {
			 	
			var zone=$("#LFzone").val();
			var circle=$("#LFcircle").val();
			var town=$("#LFtown").val();
			var fromdate = $("#month").val(); 
			var mtrno =$("#mtrno").val();

		

			if(zone == ''){
				bootbox.alert("Please Select  Region");
				return false;

			}
			if(circle == ''){
				bootbox.alert("Please Select  Circle");
				return false;

			}

			if(town == ''){
				bootbox.alert("Please Select  Town");
				return false;

			}

		
			if(fromdate == ''){
				bootbox.alert("Please Select  Bill Month");
				return false;

			}
			
		 	
		 	 $("#imageee").show();
		 	//$("#tableid").show();
		 		$.ajax({
		 			url:"./getfdrEnergyUpdate",
		 			type: 'GET', 
		 			data : {
			 			circle : circle,
		 				fromdate : fromdate,
		 				mtrno:mtrno,
						town : town
						
						},

		 				 success: function(response) {
		 					 $("#imageee").hide();


		 					if (response != null && response.length > 0) {

								  var html="";
						        	 // alert(response);
						        	   for (var i = 0; i < response.length; i++) {

									
	
						        		   var resp = response[i];
						        		  
											var j = i + 1;


											html += "<tr>" 
												+ "<td>"+ j + "</td>"
												+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
												+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"
												+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"
												+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"	
												+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"	
												+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"	
												+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
												+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
												html +="<td><a href='#' onclick=editUserNew('"
													
													+ resp[8]
													+ "','"
													+ resp[5]
													+ "')>Edit</a></td>"
													+ +"</tr>";
												
												
											/* 	html += "<td><a href='#' onclick='editUserNew(\""+ resp[5]+ "\",\""+resp[0]+"\");' mtrno="+resp[5]+" billmonth="+resp[0]+">Edit</a></td>";	 */		


											
												
											
				        	   }

						        	   $('#sample1').dataTable().fnClearTable();
						            	$("#getfdrEneryBody").html(html);
						            	loadSearchAndFilter('sample1');
						        	   
								 
							 }else {
									bootbox.alert("No Relative Data Found.");
								}  
		 			            
							
			 			            
		 		            }


			            
		 	});

		 	 	
		 		
		 	 }


		 /*  function editUserNew(mtrno,month) {
		
		
				$.ajax({
					type : "GET",
					url : "./editimpexpNew/" + mtrno +'/'+month,
					dataType : "json",
					data:{
						
							mtrno : mtrno,
				    		month : month,
				    	
				     },
					success : function(response) {
				
						var data = response[0];
						//alert(data[7]);
						$("#impvalue").val(data[6]);
						$("#expvalue").val(data[7]);
						$("#modifyId").val(data[8]);
						
					

					}
				});
				$("#stack2").modal('show');
			} 
 */

 	function editUserNew(id,mtrno) {									
	 	document.getElementById('UpdateConsumption').reset();
		$('#Updateareabt').hide();
		$('#Updatedtbt').show();
		//$("#EditRemarkId").val(month);
		$('#mtrnoId').val(mtrno);

		$("#stack2").modal('show');

		$.ajax({
			url : "./editimpexpNew",
			type : 'POST',
			data : {
				id : id,
				
			},
			success : function(response) {
				$('#ConsumptionId').val(response);
				$('#oldConsumptionId').val(response);
				$('#monthlyConsId').val(id);
				
			},

		});
		editUser(id,mtrno); 

		}


 function editUser(id,mtrno) {									
	 	document.getElementById('UpdateConsumption').reset();
		$('#Updateareabt').hide();
		$('#Updatedtbt').show();
		//$("#EditRemarkId").val(month);
		$('#mtrnoId').val(mtrno);

		$("#stack2").modal('show');

		$.ajax({
			url : "./editexp",
			type : 'POST',
			data : {
				id : id,
				
			},
			success : function(response) {
				$('#ConsumptId').val(response);
				$('#oldConsumptId').val(response);
				$('#monthlyConsId').val(id);
				
			},

		});


		}
 
		

			  function update(){

				  var mtrno = $('#mtrnoId').val();
					var month = $('#MonthYearId').val();
					
					var consumption = $("#ConsumptionId").val();
					var oldconsumption = $("#oldConsumptionId").val();
					var mid = $('#monthlyConsId').val();

					$.ajax({
						url : "./updateEnergy",
						type : 'POST',
						data : {
							oldconsumption : oldconsumption,
							consumption : consumption,
							id : mid,
							mtrno : mtrno,
							month : month
						},
						success : function(response) {
							if (response == "exist") {
								bootbox.alert("Enery Updated Successfully");
								
								return false;
							} else if (response == "notexist") {
								bootbox.alert("Energy NotSaved ");
								return false;
							}
						},
						complete : function() {
								view();
							
						}
					});
					updateExp()
				  }



			  function updateExp(){

				  var mtrno = $('#mtrnoId').val();
					var month = $('#MonthYearId').val();
					var consumpt = $("#ConsumptId").val();
					var oldconsumpt = $("#oldConsumptId").val();
					var mid = $('#monthlyConsId').val();

					$.ajax({
						url : "./updateEnergyExp",
						type : 'POST',
						data : {
							oldconsumpt : oldconsumpt,
							consumpt : consumpt,
							id : mid,
							mtrno : mtrno,
							month : month
						},
						success : function(response) {
							if (response == "exist") {
								bootbox.alert("Enery Updated Successfully");
								
								return false;
							} else if (response == "notexist") {
								bootbox.alert("Energy NotSaved ");
								return false;
							}
						},
						complete : function() {
								view();
							
						}
					});
					
				  }
			  
								 
		</script>
			
			
			
			<div class="page-content">
			
				<div class="portlet box blue " id="boxid">
					<div class="portlet-title">
					
						<div class="caption">
							<i class="fa fa-reorder"></i>Feeder Energy Calculation
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a> <a
								href="#portlet-config" data-toggle="modal" class="config"></a> <a
								href="javascript:;" class="reload"></a> <a href="javascript:;"
								class="remove"></a>
						</div>
					</div>
					<div class="portlet-body ">
					
								<jsp:include page="locationFilter.jsp" />
								
								<div class="row" style="margin-left: -1px;">
					
					
											<div class="col-md-3">
												<div class="input-group ">
													<strong>Report Month :</strong><font color="red">*</font><input
															type="text" class="form-control from"  id="month"
															name="month" required="required" placeholder="Select MonthYear">  <span
															class="input-group-btn">
												<button class="btn default" type="button" style="margin-bottom: -17px;">
								
														<i class="fa fa-calendar"></i>
												</button>
													</span>
										</div>
					
					
									</div>
				

					<div class="col-md-2">
						<button type="submit" onclick="view()" style="margin-top: 15px;"
							class="btn green">View</button>
					</div>
				</div>
				
				<div id="imageee" hidden="true" style="text-align: center;">
					<h3 id="loadingText">Loading..... Please wait.</h3>
					<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
						style="width: 4%; height: 4%;">
				</div>
				
					<div class="col-md-12">
					
						<div class="btn-group pull-right">
						
						<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							
							<ul class="dropdown-menu pull-right">
								<!-- <li id="print"><a href="#">Print</a></li> -->
								<!-- <li><a href="#" id=""
									onclick="exportReport('sample1','DT Losses')">Export to PDF</a></li> -->
								<li>
								<a href="#" id="excelExport" onclick="tableToExcel('sample1', 'Feeder Losses')">Export to Excel</a>
								</li> 
								
							</ul>
						
						</div>
					
						<div class="table-responsive">
						
								
						<table class="table table-striped table-hover table-bordered"
							id="sample1">
							
							<thead>
									
									<tr>
										<th>S.NO</th>
										<th>Month Year</th>
										<th>Subdivision</th>
										<th>Subdivision Code</th>
										<th>Feeder Code</th>
										<th>Feeder Name</th>
										<th>Meterno</th>
										<th>Import</th>
										<th>Export</th>
										<th>EDIT</th>
									</tr>
							
							
							
							</thead>
							
							<tbody id="getfdrEneryBody">
									
									<%-- <c:forEach var="element" items="${energy}">
									
										<tr>
											<td>${element.adjacent_value_imp}</td>
											<td>${element.adjacent_value_exp}</td>
										</tr>
									
									</c:forEach> --%>
							</tbody>
							
							
							</table>
						
						
						
						</div>
					
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
<!-- 		 <div class="modal-body">
			
					 <table id="stack2" class="table table-striped table-hover table-bordered ">
					
			
					<thead>
							<tr id="impvalue">
							
							<td>Adjacent Value Import</td>
								<td><input type="text" id="import"
								class="form-control placeholder-no-fix"
								placeholder="Enter Import Value" name="import" maxlength="12"></input></td>
							
							</tr>
							
							<tr id="expvalue">
							
							<td>Adjacent Value Export</td>
								<td><input type="text" id="export"
								class="form-control placeholder-no-fix"
								placeholder="Enter Export Value" name="export" maxlength="12"></input></td>
							
							</tr>
						
					
					
					</thead> 
			</table>
			
				 <div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn">Cancel</button>
					<button class="btn blue pull-right" id="modifyId" type="submit"
						value="" onclick="return modifyImpExp(this.value);">Update</button>
				</div>
				
				
				
			</div>
		  -->
		 
		 <div class="modal-body">
				<form action="" method="post" id="UpdateConsumption"
					modelAttribute="UpdateConsumption" commandName="UpdateConsumption">

					<div class="inline-labels">
						<div class="form-group">
							
							<label>Import: <input type="hidden"
								id="oldConsumptionId" name="oldConsumptionId" /> <input
								type="hidden" id="monthlyConsId" name="monthlyConsId" /> <input
								type="hidden" id="mtrnoId" name="meternoId" /><input
								type="text" class="form-control placeholder-no-fix"
								id="ConsumptionId" name="ConsumptionId"
								placeholder="Enter Consumption"></input></label>
								
								<br>
							
							<label>Export: <input type="hidden"
								id="oldConsumptId" name="oldConsumptId" /> <input
								type="hidden" id="monthlyConsId" name="monthlyConsId" /> <input
								type="hidden" id="mtrnoId" name="meternoId" /><input
								type="text" class="form-control placeholder-no-fix"
								id="ConsumptId" name="ConsumptId"
								placeholder="Enter Consumption"></input></label>	

								
								
						</div>
					

					</div>



					<div class="modal-footer">
						<button class="btn blue pull-right" id="Updatedtbt" type="button"
							value="update" onclick="return update(this.value)"
							data-dismiss="modal" aria-hidden="true">UPDATE</button>
						<button class="btn red pull-left" type="button"
							data-dismiss="modal">Cancel</button>
					</div>

				</form>
			</div>
		 
	
		 
		 
		 
		</div>
	</div>
		
</div>			
			
			
<script type="text/javascript">

	var date=new Date();
	var year=date.getFullYear();
	var month=date.getMonth();
	//var startDate = new Date();
			
			$('.from').datepicker
			({
			    format: "yyyymm",
			    minViewMode: 1,
			    autoclose: true,
			    startDate :new Date(new Date().getFullYear()),
			    endDate: new Date(year, month, '31')
			
			});
	</script>
			
