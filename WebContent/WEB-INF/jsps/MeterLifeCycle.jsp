<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
 $(".page-content").ready(function()
   	    	   {//  	$('#meterno').val('').trigger('change');  
				   App.init();
   	    	   	 FormDropzone.init();
   	    		 FormComponents.init();
   	    		/* loadSearchAndFilter('table_4'); */
   	    	   //$('#mdmPageSideBarContents').addClass('start active ,selected');
   	    		$('#MDMSideBarContents,#metermang,#MeterLifeCycle').addClass('start active ,selected');
    	    	   $("#MDASSideBarContents,#cdf-Import,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#seal-Manager,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
    	    	
   	    	   });


 function showCircle(zone) {
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
						html += "<select id='LFcircle' name='LFcircle' onchange='showTownNameandCode(this.value)' class='form-control select2me' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#LFcircle").html(html);
						$('#LFcircle').select2();
					}
				});
	} 

 function showTownNameandCode(circle){
		var zone = $('#LFzone').val();
		//var zone =  '${newRegionName}';   
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

	                html += "<select id='LFtown' name='LFtown'   onchange='showResultsbasedOntownCode (this.value)'class='form-control  input-medium'  type='text'><option value=''>Select </option><option value='%'>ALL</option>";

	                

	               
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




 function showResultsbasedOntownCode(town){
//	alert(town);
		var zone = $('#LFzone').val();
		var circle = $('#LFcircle').val();
		
		   $.ajax({
		      	url:'./showmeteronBasisofTown',
		      	type:'GET',
		      	dataType:'json',
		      	asynch:false,
		      	cache:false,
		      	data : {
			      	zone:zone,
	      	        circle:circle,
		  			town:town
		  		},
		  		success : function(response1) {
			  	//	alert(response1);
		  			
	                var html = '';

	                html += "<select id='meterno' name='meterno' class='form-control select2'  type='text'><option value=''>Select </option> ";
	                    
	                for (var i = 0; i < response1.length; i++) {
	                    //var response=response1[i];
	                    
	                    html += "<option value='"+response1[i]+"'>"
	                            +response1[i]+ "</option>";
	                               }
	                html += "</select><span></span>";
	                $("#error").html(html);
	                $('#meterno').select2();
	                
	            }
		  	});
		  }


 

 function getMeterLifeCycleData()
 {	
//alert("inside track");
var zone =$('#LFzone').val();
var circle =$('#LFcircle').val();
var town =$('#LFtown').val();
//alert(circle);

	 	$("#circleId").html("");
		$("#divisionId").html("");
		$("#subdivId").html("");
		$("#locTypeId").html("");
		$("#meterNoId").html("");
		$("#locIdentityId").html("");
		$("#locNameId").html("");
		$("#mtrmakeId").html("");
		$("#latId").html("");
		$("#longId").html("");
		
		var first_communicated_date="";
		var installed_date="";
		var last_communicated_date="";
		var intlkwh="";
		var fnlkwh="";
		var Mapped_Date="";
		var Remarks="";
		var Remark_date="";
		$("#meterLifeDataBody").empty();
		
	 var mtrno=$("#meterno").val().trim();

	 if(zone=="")
		 {
		 	bootbox.alert("Please Select Region.");
		 	return false;
		 }

	 if(circle=="")
	 {
	 	bootbox.alert("Please Select Circle.");
	 	return false;
	 }
	 if(town=="")
	 {
	 	bootbox.alert("Please Select Town.");
	 	return false;
	 }
 	 if(mtrno=="")
 		 {
 		 	bootbox.alert("Please Select MeterNo.");
 		 	return false;
 		 }
 	$('#imageee').show();
	 $.ajax({
	    	url:"./getMeterLifeData",
	    	type:"GET",
	    	dataType:"JSON",
	    	asynch:false,
	    	cache:false,
	    	data:{
	    		mtrno:mtrno
	    	},
	    	success:function(data)
	    	{
	    		$('#imageee').hide();	
		    	if(data.length>0){
		    	var response=data[0];
		    	var html="";
		    	if(response[12]=="" || response[12]==null){
//alert(response);
					$("#circleId").html(response[1]);
					$("#divisionId").html(response[2]);
					$("#subdivId").html(response[3]);
					$("#locTypeId").html(response[4]);
					$("#meternoId").html(response[5]);
					$("#locIdentityId").html(response[6]);
					$("#locNameId").html(response[7]);
					$("#mtrmakeId").html(response[8]);
					$("#latId").html(response[13]);
					$("#longId").html(response[14]);

				//	alert(response[13]);
					
			/* 		if(response[9]!=null)
	    			{
						installed_date=moment(response[09]).format('YYYY-MM-DD HH:mm:ss');
	    			} */
					if(response[15]!=null)
	    			{
						first_communicated_date=moment(response[15]).format('YYYY-MM-DD HH:mm:ss');
						//alert(first_communicated_date);
	    			}
					if(response[17]!=null)
	    			{
						last_communicated_date=moment(response[17]).format('YYYY-MM-DD HH:mm:ss');
	    			}
					 if(response[11]!=null)
	    			{
						Mapped_Date=moment(response[11]).format('YYYY-MM-DD HH:mm:ss');
	    			} 
				 if(response[16]!=null)
	    			{
						fnlkwh=response[16];
	    			}	
					if(response[18]==null)
	    			{
						intlkwh=0;
	    			}
					else {intlkwh=response[18]; }
	    			
					

					 html+="<tr>"  
		    			/* +"<tr><td>installed at metering location</td>"
		    			+"<td>"+installed_date+"</td>"
		    			+"</tr>" */
		    			
		    			+"<td>First Communicated date&time</td>"  
		    			+"<td>"+first_communicated_date+"</td></tr>"
		    			
		    			+"<td>First Communication reading:</td>"
		    			+"<td style='color:green'>"+fnlkwh+" kWh</td></tr>"
		    			
		    			+"<td>Last Communicated date&time</td>"
		    			+"<td>"+last_communicated_date+"</td></tr>"
		    			
		    			+"<td>Last Communication reading:</td>"
		    			+"<td style='color:green'>"+intlkwh+" kWh</td></tr>"

		    			  +"<td>Mapped Date</td>"
		    			+"<td>"+Mapped_Date+"  </td></tr>"

		    			
		    			$("#meterLifeDataBody").html(html);
						$("#meterDataGrid").show();
			    	}
		    	else if(response.length>0)
			    	{
							$("#circleId").html(response[1]);
							$("#divisionId").html(response[2]);
							$("#subdivId").html(response[3]);
							$("#locTypeId").html(response[4]);
							$("#meternoId").html(response[5]);
							$("#locIdentityId").html(response[6]);
							$("#locNameId").html(response[7]);
							$("#mtrmakeId").html(response[8]);
							
							$("#latId").html(response[13]);
							$("#longId").html(response[14]);
							

							//alert(response[10]);
							//alert(response[13]);
						/* 	if(response[9]!=null)
			    			{
								installed_date=moment(response[09]).format('YYYY-MM-DD HH:mm:ss');
			    			} */
							if(response[16]!=null)
			    			{
								first_communicated_date=moment(response[16]).format('YYYY-MM-DD HH:mm:ss');
							//	alert(first_communicated_date);
								
			    			}
							if(response[18]!=null)
			    			{
								last_communicated_date=moment(response[18]).format('YYYY-MM-DD HH:mm:ss');
			    			}
							 if(response[11]!=null)
			    			{
								Mapped_Date=moment(response[11]).format('YYYY-MM-DD HH:mm:ss');
			    			} 
							if(response[19]==null)
			    			{
								intlkwh=0;
			    			}
						   else {
							   intlkwh=response[19]; 
						   }
							
							if(response[17]!=null)
			    			{
								fnlkwh=response[17];
			    			}
							if(response[13]!=null)
							{
								Remarks=response[13];
							}

							if(response[15]!=null)
			    			{
								Remark_date=moment(response[15]).format('YYYY-MM-DD HH:mm:ss');
								
			    			}

							 html+="<tr>" 
									+"<tr><td>installed at metering location</td>"
					    			+"<td>"+installed_date+"</td>"
					    			+"</tr>"
					    			
					    			+"<td>First Communicated date&time</td>"  
					    			+"<td>"+first_communicated_date+"</td></tr>"
					    			
					    			+"<td>First Communication reading:</td>"
					    			+"<td style='color:green'>"+intlkwh+" kWh</td></tr>"
					    			
					    			+"<td>Last Communicated date&time</td>"
					    			+"<td>"+last_communicated_date+"</td></tr>"
					    			
					    			+"<td>Last Communication reading:</td>"
					    			+"<td style='color:green'>"+fnlkwh+" kWh</td></tr>"

					    		    +"<td>Mapped Date</td>"
					    			+"<td>"+Mapped_Date+"  </td></tr>"
				    			
				    			+"<td>"+Remark_date+"</td>"
				    			+"<td>Remarks  :<span style='color:green'>"+Remarks+"</span></td></tr>"
				    			
				    			$("#meterLifeDataBody").html(html);
								$("#meterDataGrid").show();
			    	}
		    	}
		    	else if(response.length<0)
			    	{
		    			$('#imageee').hide();	
		    			bootbox.alert("Data Not Found For Entered meterno");
						return false;
			    	}
		    	
		    }
		});
 }
 function getMeterLifeCycleDataKno()
 {
	 var mtrno=$("#meterno").val();
	// alert(mtrno);
 	 if(mtrno=="" || mtrno==null)
 		 {
 		 	bootbox.alert("Please Enter KNO.");
 		 	return false;
 		 }
	 $.ajax({
	    	url:"./getMeterLifeDataKno",
	    	type:"GET",
	    	dataType:"JSON",
	    	asynch:false,
	    	cache:false,
	    	data:{
	    		mtrno:mtrno
	    	},
	    	success:function(response)
	    	{
		    	if(response.length>0)
			    {
	    		var basicDaTotal=0;
	    		 var html="";
 			
	    		for (var i = 0; i < response.length; i++)
	    		{
	    			var data=response[i];
	    			
	    			
	    			var installed_date="";
	    			var devicer_registration="";
	    			var provision_date="";
	    			var first_communicated="";
	    			var last_communicated="";
	    			var connected_date="";
	    			var dissconnected_date="";
	    			//alert("connn--"+data[0]);
	    			if(data[9]!=null)
		    			{
	    				installed_date=moment(data[9]).format('YYYY-MM-DD hh:mm:ss');
		    			}
	    			 if(data[10]!=null)
		    			{
	    				 devicer_registration=moment(data[10]).format('YYYY-MM-DD hh:mm:ss');
		    			}
					 if(data[11]!=null )
						 {
						 provision_date=moment(data[11]).format('YYYY-MM-DD hh:mm:ss');
						 }
					
					 if(data[12]!=null )
						 {
						 first_communicated=moment(data[12]).format('YYYY-MM-DD hh:mm:ss');
						 }
					 if(data[13]!=null )
						 {
						 
						 last_communicated=moment(data[13]).format('YYYY-MM-DD hh:mm:ss');
						 }
					 if(data[14])
						 {
						 dissconnected_date=moment(data[14]).format('YYYY-MM-DD hh:mm:ss');
						 }
					
					 if(data[15])
						 {
						 connected_date=moment(data[15]).format('YYYY-MM-DD hh:mm:ss');
						 }
					

					$("#LFcircle").html(data[1]);
					$("#divId").html(data[2]);
					$("#sdoId").html(data[3]);
					$("#mtrId").html(data[7]);
					$("#accId").html(data[8]);
					$("#kno").html(data[0]);
					$("#supId").html("LT");
					$("#mtrmkId").html(data[4]); 
					$("#addressId").html(data[6]);
					$("#nameId").html(data[5]);
					$("#lsdateId").html(last_communicated);
					

	    			html+="<tr>"  
		    			+"<tr><td>"+installed_date+"</td>"
		    			+"<td>Installed in "+data[6]+"</td>"
		    			+"</tr>"
		    			
		    			+"<tr><td>"+devicer_registration+"</td>"
		    			+"<td>Device Registration</td>"
		    			+"</tr>"
		    			
		    			+"<tr><td>"+provision_date+"</td>"
		    			+"<td>Provisioning Done</td>"
		    			+"</tr>"
		    			
		    			+"<td>"+first_communicated+"</td>"
		    			+"<td>First Communicated</td></tr>"
		    			
		    			+"<td>"+dissconnected_date+"</td>"
		    			+"<td>Disonnected Final Reading   :<span style='color:green'>"+data[16]+" kWh</span></td></tr>"
		    			
		    			+"<td>"+connected_date+"</td>"
		    			+"<td>Connected intial Reading    :<span style='color:green'>"+data[16]+" kWh</span></td></tr>"
		    			
		    			+"<td>"+last_communicated+"</td>"
		    			+"<td>Last Communicated</td></tr>"
		    			
				}
				$("#meterLifeDataBody").html(html);
				$("#meterDataGrid").show();
				//$('#meterLifeData').select2(); 
	    	}
		    	else {
					bootbox.alert("Data not found for this meterno");
			    	}
	    	}
		});
 }
 
   	  </script>
   	<c:if test="${not empty msg}">
	<script>
		var msg = "${msg}";
		bootbox.alert(msg);
	</script>
</c:if>

<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Meter Life Cycle
					</div>
				</div>
				<div class="portlet-body">
				<jsp:include page="locationFilter.jsp"/>
				<div class="col-md-3">
					<strong>MeterNo:</strong><div id="error" class="form-group">
					<select class="form-control select2me" id="meterno" name="meterno">
						<option value="">Select MeterNo</option>
					</select>
					</div>
				</div>
				
				<div class="col-md-1" style="padding-top: 15px;" id="kno">
					<button type="button" class="btn btn green" onclick="return getMeterLifeCycleData();">Track</button>
				</div>
						
					<!--  <table>
				 
						<tr><td>Enter MeterNo:<font color="red">*</font></td><td><select class="form-control select2me"autocomplete="off" class="form-control input-small" id="meterNo"   onkeyup="meterfind()"/><div id="error"></div></td><td><button type="button" class="btn btn green" onclick="return getMeterLifeCycleData();">Track</button></td>
							
							<td>Enter KNo:<font color="red">*</font></td><td><input type="text" autocomplete="off" class="form-control input-small" id="kno"/></td><td><button type="button" class="btn btn green" onclick="return getMeterLifeCycleDataKno();">Track</button></td>
							
							</tr>       
							
							
				    </table>  -->
				    <br>
				    <br>
				    <br>
				    <br>
				    <div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
					
					<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> -->
								<li><a href="#" id=""
								onclick="exportPDF('sample_1','Total Meters')">Export to PDF</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample', 'Total Meters')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
				    <table class="table table-striped table-hover table-bordered"
													id="sample">
										    
						                     
						                      <tr><td>CIRCLE&nbsp;&nbsp;:</td>
										      <td style="min-width: 120px;" ><span id="circleId"></span></td>
										      <td>METER NO&nbsp;&nbsp;:</td>
										      <td style="min-width: 120px;" ><span id='meternoId'></span></td>
										      
										      </tr>
						                      
						                      <tr><td>DIVISION&nbsp;&nbsp;:</td>
						                       <td style="min-width: 120px;" ><span id='divisionId'></span></td>
						                       
						                       <td>METER MAKE&nbsp;&nbsp;:</td>
										      <td style="min-width: 120px;" ><span id='mtrmakeId'></span></td>
						                      </tr>
						          
						                       <tr><td>SUBDIVISION&nbsp;&nbsp;:</td>
						                       <td style="min-width: 120px;" ><span id='subdivId'></span></td>
						                       
						                       <td>LOCATION TYPE&nbsp;&nbsp;:</td>
										      <td style="min-width: 120px;" ><span id='locTypeId'></span></td>
						                       
						                      </tr>
						                      
						                      <tr><td>LOCATION NAME&nbsp;&nbsp;:</td>
						                       <td style="min-width: 120px;" >&nbsp;&nbsp;&nbsp;<span id='locNameId'></span></td>
						                        <td>LOCATION IDENTITY&nbsp;&nbsp;:</td>
										      <td style="min-width: 120px;" ><span id='locIdentityId'></span></td>
						                       
						                      </tr>
						                      
						                       <tr><td>LATITUDE &nbsp;&nbsp;:</td>
						                       <td style="min-width: 120px;" >&nbsp;&nbsp;&nbsp;<span id='latId'></span></td>
						                        <td>LONGITUDE &nbsp;&nbsp;:</td>
										      <td style="min-width: 120px;" ><span id='longId'></span></td>
						                       
						                      </tr>
						                      </table>
				    
				</div>
			</div>
		</div>
	</div>
	
	<div class="row"  id="meterDataGrid"  >
		<div class="col-md-12">
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Meter Details 
					</div>
				</div>
				<div class="portlet-body" style="overflow-x:auto;">
					 <table id="meterLifeData" class="table table-striped table-bordered table-hover" >
					 	<thead>
							<tr>
								
								
								<th>
									Description
								</th>
								
								<th>
									DATE
								</th>
								<!-- <th>
									Reason
								</th> -->
								<!-- <th>
									METER NO
								</th>
								<th>
									CONS NAME
								</th>
								
								<th>
									INSTALLED DATE
								</th>
								<th>
									INTIAL READING
								</th>
								<th>
									FINAL READING
								</th>
								<th>
									CIRCLE
								</th>
								<th>
									DIVISION
								</th>
								<th>
									SUB-DIV
								</th> -->
							</tr>
						</thead>
						<tbody id="meterLifeDataBody">
							
						</tbody>
				    </table> 
				</div>
			</div>
		</div>
	</div>
</div>


<style>
	
	.tdcls1
	{
 padding-bottom:15px;
	width:10%;
	text-align: right;
	font-weight:bold;
	}
	
	.tdcls2
	{
 padding-bottom:15px;
	width:40%;
	/* text-align: left; */
	text-align: justify;
	}
	
		.tdcls3
	{
 padding-bottom:15px;
	width:10%;
	text-align: right;
	font-weight:bold;
	}
	
		.tdcls4
	{
 padding-bottom:15px;
	width:40%;
	/* text-align: left; */
	text-align: justify;
	}
	

	
	</style>	
	
<script>

function meterfind(){
	 var mtrno=$("#meterno").val().trim();
	$.ajax({
		type:'get',
		url:'./meterfind',
		data:{
			mtrno:mtrno
		},
		success:function(res){
			if(res==true){
				$('#error').hide();
				
				
			}	
			else
				{
				$('#error').show();
				document.getElementById('error').innerHTML="Please Enter Existing MeterNo";
				document.getElementById('error').style.color="green";
			//	bootbox.alert("No Meter");
				}
		}
	});
}





</script>	
			

