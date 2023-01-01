<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		
		 $("#selectedDateId").val('${month}');
	   			App.init();
				TableEditable.init();
				FormComponents.init();
				
				
				$('#MDMSideBarContents,#DPId,#dailyConsumption').addClass('start active ,selected');
				 $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
	  				"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
	  		
     	});
	
	$(document).ready(function(){
		$('.js-example-basic-multiple').select2();
	});
	function showCircle(zone)
	{
		var zone="All";
		$.ajax({
			url : './showCircleMDM' + '/' + zone ,
		    	type:'GET',
		    	
		    	success:function(response)
		    	{
	  			var html='';
		    		html+="<option value=''></option>";
					for( var i=0;i<response.length;i++)
					{
						html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
					}
					
					$("#circle").html(html);
					$('#circle').select2();
		    	}
			});
	}
	
	function showDivision(circle)
	{
		var zone="All";
		$.ajax({
			url : './showDivisionMDM' + '/' + zone + '/' + circle,
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	success:function(response)
		    	{
	  			var html='';
		    		html+="<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Division</option>";
					for( var i=0;i<response.length;i++)
					{
						html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
					}
					html+="</select><span></span>";
					$("#divisionTd").html(html);
					$('#division').select2();
		    	}
			});
	}
	
	
	function showSubdivByDiv(division)
	{
		var circle=$('#circle').val();
		var zone="All";
		$.ajax({
			url : './showSubdivByDivMDM' + '/' + zone + '/' + circle + '/'
			+ division,
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	success:function(response1)
		    	{
	  			var html='';
		    		html+="<select id='sdoCode' name='sdoCode' class='form-control input-medium' type='text'><option value=''>Sub-Division</option>";
					for( var i=0;i<response1.length;i++)
					{
						//var response=response1[i];
						html+="<option  value='"+response1[i]+"'>"+response1[i]+"</option>";
					}
					html+="</select><span></span>";
					$("#subdivTd").html(html);
					$('#subdiv').select2();
		    	}
			});
	}
	
	
</script>
<div class="page-content" >

<div class="row">
				<div class="col-md-12">
				
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>TOD Definition</div>
							
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
											
						<div class="portlet-body form">
								<form id="dailyAvgLoadId" action="">
										<table ><tbody>
										<tr><th>Group&nbsp;Name&nbsp;:</th><th><div class="form-group">
										
										<input type="email" class="form-control" id="exampleInputEmail1" placeholder="Group Name">
										
									</div></th>
									<th>Meter&nbsp;Type&nbsp;:</th><th><select class="form-control select2me input-medium"
									id="mtrtype" name="mtrtype" >
									<option value=""></option>
										<option value="Consumer">Consumer</option>
										<option value="Feeder">Feeder</option>
										<option value="DT">DT</option>
								</select>
									</th>
									<th>Meters&nbsp;:</th>
									<th><select
								class="js-example-basic-multiple placeholder-no-fix input-large"
								id="metrno" multiple="multiple" autofocus="autofocus">
								 <option value="8000003">8000003</option>
												<option value="8000004">8000004</option> 
												 <option value="8000005">8000005</option>
												<option value="8000006">8000006</option> 
												 <option value="8000007">8000007</option>
												<option value="8000008">8000008</option> 
												 <option value="8000009">8000009</option>
												<option value="8000010">8000010</option> 
									<%-- <c:forEach items="${meters}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach> --%>

							</select></th>
							<th>Slots&nbsp;:</th><th><select class="form-control select2me input-medium"
									id="groupSlot" name="groupSlot" >
									<option value=""></option>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
								</select></th>
							<th >
																<div >
																
									<div  >                        
										<!-- <button type="button" class="btn yellow">Reset</button>   -->
										<button type="button" onclick="validation()" class="btn green">Save</button> 
									</div>
								</div>
																</th>
									</tr>
										<!-- <tr> -->
																
																<!-- </tr>  --></tbody>
										</table>
									
										
										</form>
									
								
							
						</div>
					</div>
					
					<div class="portlet box blue"  id="boxid">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>TOD Group List</div>
							<div class="actions">
								<!-- <a href="#" class="btn blue"><i class="fa fa-pencil"></i> Add</a> -->
								<div class="btn-group">
									<a class="btn green" href="#" data-toggle="dropdown">
									<i class="fa fa-cogs"></i> Tools
									<i class="fa fa-angle-down"></i>
									</a>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="excelExport2"
																	onclick="tableToExcel2('sample_1', 'Daily Consumption')">Export
																		to Excel</a></li>
									</ul>
								</div>
							</div>
						</div>					
											
						<div class="portlet-body">
					
								<BR><BR>
					
					<table class="table table-striped table-hover table-bordered" id="sample_1" >
									
									<thead>
							<tr>
												
												<th>S.No</th>
												<th>Group Name</th>
												<th>Meters View </th>
												<th>Slot Number</th>
											    <th>Creation Time</th>
												
												
												
							</tr>
						</thead>
									
									<tbody id="listID">
						
						</tbody>
								</table>
								</div>
								</div>
								</div>
								
					
				</div>
				
			</div>
		
		<script>	
			
	function validation()
		{
		/* var zone=$("#zone").val();
			
			var fdate=$("#selectedFromDateId").val();
			var tdate=$("#selectedToDateId").val();
			
			if(zone=="")
				{
				bootbox.alert("Please Select Zone");
				return false;
				} 
			

			if(fdate=="")
				{
					bootbox.alert("Please Select  From Date");
					return false;
				}
			if(tdate=="")
			{
				bootbox.alert("Please Select  To Date");
				return false;
			}
			if(new Date(fdate)>new Date(tdate))
			{
				bootbox.alert("Wrong Date Inputs");
				return false;
			} */
		view();
		}		
	function view(){
		/* var zone=$("#zone").val();
		var circle=$("#circle").val();
		var division=$("#division").val();
		var sdoname=$("#sdoCode").val();
		var fdate=$("#selectedFromDateId").val();
		var tdate=$("#selectedToDateId").val(); */
		$.ajax({
			url:"./dailyConsumptionList",
			type:"get",
			data:{
				zone:zone,
				circle:circle,
				division:division,
				sdoname:sdoname,
				fdate:fdate,
				tdate:tdate
			},
			success:function(res){
				/* html="";
				$.each(res,function(i,v){
					html+="<tr><td>"+(i+1)+"</td><td>"+v[0]+"</td><td>"+v[1]+"</td><td>"+v[2]+"</td><td>"+v[3]+"</td><td>"+v[4]+"</td><td>"+v[5]+"</td><td>"+v[6]+"</td><td>"+v[7]+"</td><td>"+v[8]+"</td><td>"+v[9]+"</td><td>"+v[10]+"</td></tr>";
				});
				//$("#listID").empty().
				clearTabledataContent('sample_1');
				$("#listID").html(html);
				loadSearchAndFilter('sample_1');
				$("#boxid").show(); */
			}
		});
		
	}	
	function clearTabledataContent(tableid) {
		//TO CLEAR THE TABLE DATA
		var oSettings = $('#' + tableid).dataTable().fnSettings();
		var iTotalRecords = oSettings.fnRecordsTotal();
		for (i = 0; i <= iTotalRecords; i++) {
			$('#' + tableid).dataTable().fnDeleteRow(0, null, true);
		}

	}
	</script>	
			