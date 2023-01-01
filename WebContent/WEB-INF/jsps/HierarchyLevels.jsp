<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
span.a{
display: inline;
}
</style>


<script type="text/javascript">

	$(".page-content")
			.ready(
					function() {
						App.init();
						TableEditable.init();
						FormComponents.init();
						//loadSearchAndFilter('hsnSubDataTable'); 
						
						$('#ADMINSideBarContents,#adminArea,#adminAreaDetails').addClass('start active ,selected');
						$("#MDASSideBarContents,#MDMSideBarContents#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
						}
						//$("#MatchedDataGrid").show();
					  
					   /* $('#existingCircle_Section').val('').trigger('change');
					   $('#existingDivisions_Section').val('').trigger('change');
					   $('#existingSubdivisions_Section').val('').trigger('change');
					   $('#existingSubdivisions_Towns').val('').trigger('change');
					   $('#existingZone_Section').find('option').remove() .end() .append('<option value="0">'+'Select'+'</option>') .val('0'); */
					   
					   
					);
	
	$( document ).ready(function() 
	{
		loadSearchAndFilter('hierarchyDataTable');
	});

//FUNTCTION FOR CLEARING THE TABLE
function clearTabledataContent(tableid)
	{
		 //TO CLEAR THE TABLE DATA
		var oSettings = $('#'+tableid).dataTable().fnSettings();
		var iTotalRecords = oSettings.fnRecordsTotal();
		for (i = 0; i <= iTotalRecords; i++) 
		{
			$('#'+tableid).dataTable().fnDeleteRow(0, null, true);
		} 
		
	}

	//getting both Groups codes and its data
	function getExistingCircles()
	{
		var selectedZone =$("#existingZone_Section").val();
		$.ajax({
            type : "POST",
            url : "./getCirclesUnderZones",
            dataType : "JSON",
            cache : false,
            async : false,
            data:{
            	 zone:selectedZone
            },
            success : function(response)
            {
            	//$('#existingCircle_Section').val('').trigger('change');
		            if(response==null||response=='')
					{
						bootbox.alert('NO DATA EXISTS UNDER SELECTED REGION!!!');
						$('#existingCircle_Section')
					    .find('option')
					    .remove()
					    .end()
					    .append('<option value="0">'+'Select'+'</option>')
					    .val('0')
					;
					} else
						{
						var html = "";
						var toAppendGroups= '<option value="0">'+'Select'+'</option>';
						for (var i = 0; i < response.length; i++)
						{
							//var data = response[i];
							toAppendGroups+='<option value='+encodeURIComponent(response[i])+'>'+response[i]+'</option>';
						}
						
						$("#existingCircle_Section").html(toAppendGroups);
					}
			
		},
       });
	}
	
	function getExistingDivisions()
	{

		//$('option:selected', this).remove();
		$('#existingDivisions_Section')
		    .find('option')
		    .remove()
		    .end()
		    .append('<option value="0">'+'Select'+'</option>')
		    .val('0')
		;


		var selectedZone =$("#existingZone_Section").val();
		var selectedCircle =decodeURIComponent($("#existingCircle_Section").val());
		$.ajax({
            type : "POST",
            url : "./getDivisionsUnderCircles",
            dataType : "JSON",
            cache : false,
            async : false,
            data:{
            	zone:selectedZone,
            	circle:selectedCircle
            },
            success : function(response)
            {
            	//$('#existingDivisions_Section').val('').trigger('change');
		            if(response==null||response=='')
					{		            	
						bootbox.alert('NO DATA EXISTS UNDER SELECTED CIRCLE!!!');
						$('#existingDivisions_Section')
					    .find('option')
					    .remove()
					    .end()
					    .append('<option value="0">'+'Select'+'</option>')
					    .val('0');
					} else
						{
						var html = "";
						var toAppendGroups= '<option value="0">'+'Select'+'</option>';
						 for (var i = 0; i < response.length; i++)
						{
							//alert(response[i]);
							//var data = response[i];
							toAppendGroups+='<option value='+encodeURIComponent(response[i])+'>'+response[i]+'</option>';
						} 
						$("#existingDivisions_Section").html(toAppendGroups);
					}
			
		},
       });
	}
	
	function getExistingSubdivisions()
	{
		var selectedZone =$("#existingZone_Section").val();
		var selectedCircle =decodeURIComponent($("#existingCircle_Section").val());
		var selectedDivision =decodeURIComponent($('#existingDivisions_Section').val());
		//alert(decodeURIComponent($('#existingDivisions_Section').val()));
		$.ajax({
            type : "POST",
            url : "./getSubdivisionsUnderDivision",
            dataType : "JSON",
            cache : false,
            async : false,
            data:{
            	zone:selectedZone,
            	circle:selectedCircle,
            	division:selectedDivision
            },
            success : function(response)
            {
				  // $('#existingSubdivisions_Section').val('').trigger('change');
		            if(response==null||response=='')
					{
						bootbox.alert('NO DATA EXISTS UNDER SELECTED DIVISION!!!');
						$('#existingSubdivisions_Section')
					    .find('option')
					    .remove()
					    .end()
					    .append('<option value="0">'+'Select'+'</option>')
					    .val('0');
					} else
						{
						var html = "";
						var toAppendGroups= '<option value="0">'+'Select'+'</option>';
						for (var i = 0; i < response.length; i++)
						{
							//var data = response[i];
							toAppendGroups+='<option value='+response[i]+'>'+response[i]+'</option>';
						}
						
						$("#existingSubdivisions_Section").html(toAppendGroups);
					}
			
		},
       });
	}

	function getExistingTowns()
	{
		var selectedZone =$("#existingZone_Section").val();
		var selectedCircle =decodeURIComponent($("#existingCircle_Section").val());
		var selectedDivision =decodeURIComponent($('#existingDivisions_Section').val());
		var selectedSubDivision =decodeURIComponent($('#existingSubdivisions_Section').val());
		//alert(decodeURIComponent($('#existingDivisions_Section').val()));
		$.ajax({
            type : "POST",
            url : "./getTownsUnderSubdivisions",
            dataType : "JSON",
            cache : false,
            async : false,
            data:{
            	zone:selectedZone,
            	circle:selectedCircle,
            	division:selectedDivision,
            	subdiv:selectedSubDivision
            },
            success : function(response)
            {
            	//$('#existingSubdivisions_Towns').val('').trigger('change');
            	//alert(response);
		            if(response==null||response=='')
					{
						bootbox.alert('NO DATA EXISTS UNDER SELECTED SUB-DIVISION!!!');
						$('#existingSubdivisions_Towns')
					    .find('option')
					    .remove()
					    .end()
					    .append('<option value="0">'+'Select'+'</option>')
					    .val('0');
					} else
						{
						var html = "";
						var toAppendGroups= '<option value="0">'+'Select'+'</option>';
						for (var i = 0; i < response.length; i++)
						{
							//var data = response[i];
							toAppendGroups+='<option value='+response[i]+'>'+response[i]+'</option>';
						}
						
						$("#existingSubdivisions_Towns").html(toAppendGroups);
					}
			
		},
       });
	}
	

 
 function getExistingSections()
	{
		var selectedZone =$("#existingZone_Section").val();
		var selectedCircle =decodeURIComponent($("#existingCircle_Section").val());
		var selectedDivision =decodeURIComponent($('#existingDivisions_Section').val());
		var selectedSubDivision =decodeURIComponent($('#existingSubdivisions_Section').val());
		var selectedTown =decodeURIComponent($('#existingSubdivisions_Towns').val());
		
		//var selectedTown =$("#existingSubdivisions_Towns").val();
		//alert(decodeURIComponent($('#existingTowns_section').val()));
		$.ajax({
         type : "POST",
         url:"./getSectionUnderTown",
         dataType : "JSON",
         cache : false,
         async : false,
         data:{
         	zone:selectedZone,
         	circle:selectedCircle,
         	division:selectedDivision,
        	subdiv:selectedSubDivision,
        	town:selectedTown
   
        	
         },
         success : function(response)
         {
				  // $('#existingTowns_section').val('').trigger('change');
		            if(response==null||response=='')
					{
						bootbox.alert('NO DATA EXISTS UNDER SELECTED DIVISION!!!');
						$('#existingTowns_section')
					    .find('option')
					    .remove()
					    .end()
					    .append('<option value="0">'+'Select'+'</option>')
					    .val('0');
					} else
						{
						var html = "";
						var toAppendGroups= '<option value="0">'+'Select'+'</option>';
						for (var i = 0; i < response.length; i++)
						{
							var data = response[i];
							toAppendGroups+='<option value='+response[i]+'>'+response[i]+'</option>';
						}
						
						$("#existingTowns_section").html(toAppendGroups);
					}
			
		},
    });
	}


	//getting the recorda to update
	function editSACCodesGSTRate(editingID)
	{
		
		$.ajax({
            type : "GET",
            url : "./getsacCodeData/"+editingID,
            dataType : "JSON",
            cache : false,
            async : false,
            success : function(response)
            {
            	var data=response[0];
                $("#subCode").html(data.service_code);
                $("#idgstRate").val(data.id);
				$("#sacCode_gstRate").val(data.rate);
            },
    });
	$('#' + editingID).attr("data-toggle", "modal");
	$('#' + editingID).attr("data-target", "#editinggstratePopUp");
	}
	
	//updating the records
	function updateSACcodeGstRate()
	{

		
		var toidUpdate=$("#idgstRate").val();
		var newRate=$("#sacCode_gstRate").val();
		
		$.ajax({
			
			    type     : "POST",
	            url      : "./updatesacCodeGSTrate",
	            dataType : "TEXT",
	            data	 :{
	            			toidUpdate:toidUpdate,
	            			newRate:newRate
	            },
	            cache    : false,
	            async    : false,
	            success  : function(response)
	            {
	            	bootbox.alert(response);
	            	document.getElementById('getSACdescriptionButton').click();
		        	document.getElementById('closePopUp').click();
	            	
	            }
		});
		
		
		
	}
	
	//ADDING THE CIRCLE 
	function addNewCircle()
	{
		var zoneVal=$("#existingZone_Section").val();
		 var circleName=$("#newCircle").val();
		 var circleCode=$("#newCircleCode").val();
		// var newCircle=$("#newCircle").val(); 
		$("#zoneFor").val(zoneVal);
		if(zoneVal==0)
			{
				bootbox.alert('Select Region First!');
				return false;
			}else if(circleName == ''){
				bootbox.alert('Enter Circle name');
				return false;
			}else if(circleCode == ''){
				bootbox.alert('Enter Circle code');
				return false;
			}else{
				$('#addingnewCircleForm').attr('action','./newcircleunderzone').submit(); 
				}
		
	}
	
	//ADDING NEW DIVISIOM
	
	function addNewDivision()
	{
		var zoneVal=$("#existingZone_Section").val();
		var circle=$("#existingCircle_Section").val();
		
		var divisionName=$("#newDivision").val();
		var divisionCode=$("#newDivisionCode").val();
		
		
		//var division=$("#existingDivisions_Section").val();
		$('#newZoneFor2').val(zoneVal);
		$('#selCircle1').val(circle);
		//$('#division1').val(division);
		
		
		if(zoneVal==0||circle==0)
			{
			bootbox.alert('Select Region and Circle first!');
			return false;
			}else if(divisionName == ''){
				bootbox.alert('Enter Division name');
				return false;
			}else if(divisionCode == ''){
				bootbox.alert('Enter Division code');
				return false;
			}else{
				$('#addingnewDivisionForm').attr('action','./newdivisionunderzone').submit();
				}
		//else if(circle==0||circle){}
	}
	
	function addNewsubDivision()
	{
		var zoneVal=$("#existingZone_Section").val();
		var circle=$("#existingCircle_Section").val();
		var division=$("#existingDivisions_Section").val();
		
		var SubdivisionName=$("#newSubdivision").val();
		var SubdivisionCode=$("#newSubdivisionCode").val();
		
		$('#newZoneFor3').val(zoneVal);
		$('#selCircle2').val(circle);
		$('#div1').val(division);
		
		if(zoneVal==0||circle==0||division==0)
		{
		bootbox.alert('Select Region,Circle and Division first!');
		return false;
		}else if(SubdivisionName == ''){
			bootbox.alert('Enter Sub-Division name');
			return false;
		}else if(SubdivisionCode == ''){
			bootbox.alert('Enter Sub-Division code');
			return false;
		}else
			{
			$('#addingnewSubDivisionForm').attr('action','./newsubdivisionunderzone').submit();
			}
		
	}

	function addNewTown()
	{
		var zoneVal=$("#existingZone_Section").val();
		var circle=$("#existingCircle_Section").val();
		var division=$("#existingDivisions_Section").val();
		var subdivision=$("#existingSubdivisions_Section").val();
		

		var townName=$("#newTown").val();
		var townCode=$("#newTownCode").val();
		
		$('#newZoneFor4').val(zoneVal);
		$('#selCircle3').val(circle);
		$('#div2').val(division);
		$('#subdiv').val(subdivision);
		
		if(zoneVal==0||circle==0||division==0||subdivision==0)
		{
		bootbox.alert('Select Region,Circle,Division and Sub-division first!');
		return false;
		}else if(townName == ''){
			bootbox.alert('Enter Town name');
			return false;
		}else if(townCode == ''){
			bootbox.alert('Enter Town code');
			return false;
		}else{
			$('#addingnewTownForm').attr('action','./newTownunderzone').submit();
		}
		
	}
	
	function addNewSection(){
		
		var zoneVal=$("#existingZone_Section").val();
		var circle=$("#existingCircle_Section").val();
		var division=$("#existingDivisions_Section").val();
		var subdivision=$("#existingSubdivisions_Section").val();
		var town = $("#existingSubdivisions_Towns").val();
		
		
		var sectionName=$("#newSection").val();
		var sectionCode=$("#newSectionCode").val();
		
		
		$('#newZoneFor5').val(zoneVal);
		$('#selCircle4').val(circle);
		$('#div3').val(division);
		$('#subdiv').val(subdivision);
		$('#town').val(town);
		
		if(zoneVal==0||circle==0||division==0||subdivision==0||town==0)
			{
			bootbox.alert('Select Region,Circle,Division and Sub-division first!');
			return false;
			}
		else if(sectionName ==''){
			
			bootbox.alert('Enter Section name');
			return false;
		}
		else if(sectionCode ==''){
			
			bootbox.alert('Enter Section Code');
			return false;
			
			
		}else{
			
			$('#addingnewSectionForm').attr('action','./newSectionunderzone').submit();
			
		}
		
		
	}
	
	
	
	
	function circleCheck()
	{
		var zoneVal=$("#existingZone_Section").val();
		$("#dataid #zoneCode").text(zoneVal);
		return false;
	}
	function divisonCheck()
	{
		var zoneVal=$("#existingZone_Section").val();
		var circle=$("#existingCircle_Section").val();
		$("#divZone").text(zoneVal);
		$("#divCircle").text(circle);
		return false;
	}
	function subdivisionCheck()
	{
		var zoneVal=$("#existingZone_Section").val();
		var circle=$("#existingCircle_Section").val();
		var division=$("#existingDivisions_Section").val();
		$("#subdivZone").text(zoneVal);
		$("#subdivCircle").text(circle);
		$("#subdivDivision").text(division);
		return false;
	}

	function townCheck()
	{
		var zoneVal=$("#existingZone_Section").val();
		var circle=$("#existingCircle_Section").val();
		var division=$("#existingDivisions_Section").val();
		var subdivision=$("#existingSubdivisions_Section").val();
		$("#townZone").text(zoneVal);
		$("#townCircle").text(circle);
		$("#townDivision").text(division);
		$("#townSubDivision").text(subdivision);
		return false;
	}
	function sectionCheck()
	{
		
		var zoneVal=$("#existingZone_Section").val();
		var circle=$("#existingCircle_Section").val();
		var division=$("#existingDivisions_Section").val();
		var subdivision=$("#existingSubdivisions_Section").val();
		var town = $("#existingSubdivisions_Towns").val();
		
		$("#sectionZone").text(zoneVal);
		$("#sectionCircle").text(circle);
		$("#sectionDivison").text(division);
		$("#sectionSubDivision").text(subdivision);
		$("#sectionTown").text(town);
	}
	
	function loadSearchAndFilter(param) {
		$('#' + param).dataTable().fnDestroy();
		$('#' + param).dataTable(
				{
					"aLengthMenu" : [ [ 10, 20, 50, 100, -1 ],
							[ 10, 20, 50, 100, "All" ] // change per page values here
					],
					"iDisplayLength" : 10
				});
		jQuery('#' + param + '_wrapper .dataTables_filter input').addClass(
				"form-control input-small"); // modify table search input 
		jQuery('#' + param + '_wrapper .dataTables_length select').addClass(
				"form-control input-xsmall"); // modify table per page dropdown 
		jQuery('#' + param + '_wrapper .dataTables_length select').select2(); // initialize select2 dropdown 
	}
	
	
 					//ON SEARCH KEY PRESS
					function searchKeyPress(event,id)
					{
	
						  if(event.keyCode===13){
						    	 if(id=="getHsnCodesData"){
						    		 document.getElementById('getHsnCodesData').click();
						    		 return false;
						    	 }
						     }
					}
	 
</script>



<div class="page-content">


	<c:if test="${not empty results}">
		<div class="alert alert-danger display-show">
			<button class="close" data-close="alert"></button>
			<span style="color: green">${results}</span>
		</div>
		<c:remove var="results" scope="session" />
	</c:if>

	 <div class="row">
		<div class="col-md-12">
			<div class="portlet box purple">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-bar-chart-o"></i><font >Administrative Area Details</font>
					</div>
				</div>

				<div class="portlet-body">
					<form role="form" id="Filters" class="form-inline">
						<div class="form-body">
							<div class="row">
								<div class="col-md-12">
									<!-- col-lg-3 col-md-4 col-sm-6 col-xs-12 -->
									<div class="row">
										<div class="col-sm-3">
											<span><b>Select Region:</b><font color="red">*</font></span><span><select
												class="form-control select2me input-medium"
												id="existingZone_Section"
												onchange="return getExistingCircles();">
													<option value="">Select</option>
													<c:forEach items="${existingZones}"
														var="existingZonesFromLocation">
														<option value="${existingZonesFromLocation}">${existingZonesFromLocation}</option>
													</c:forEach>
											</select></span>
										</div>
										<div class="col-sm-3">
											<span class="b"><b>Select Circle:</b><font color="red">*</font></span><span
												class="b"><select
												class="form-control select2me input-medium"
												id="existingCircle_Section"
												onchange="return getExistingDivisions();">
													<option value="">Select</option>
													<c:forEach items="${existingCircles}"
														var="exisingCircleFromLocation">
														<option value="${exisingCircleFromLocation}">${exisingCircleFromLocation}</option>
													</c:forEach>
											</select></span>
										</div>
										<div class="col-sm-3">
											<span><b>Select Division:</b><font color="red">*</font></span><span><select
												class="form-control select2me input-medium"
												id="existingDivisions_Section"
												onchange="return getExistingSubdivisions();">
													<!-- onchange="return getbothgroupsandData();" -->
													<option value="">Select</option>
											</select></span>
										</div>
										<div class="col-sm-3">
											<span><b>Select Subdivision:</b><font color="red">*</font></span><span><select
												hidden="true" class="form-control select2me input-medium"
												id="existingSubdivisions_Section" onchange="return getExistingTowns();">
													<!-- onchange="return getbothgroupsandData();" -->
													<option value="">Select</option>
											</select></span>
										</div>
									</div>
									<div class="row">
										<div class="col-md-3">
											<span style="float:left;"><a href="#"><font
													style="" data-toggle="modal" data-target="#addingZonePopUp"
													title="Adding new Region"><b>Add New Region</b></font></a></span>
										</div>
										<div class="col-md-3">
											<span style="float: left;"><a href="#"
												onclick="circleCheck();"><font style=""
													data-toggle="modal" data-target="#addingCriclePopUp"
													title="Adding new Circle Under Region"><b>Add New
															Circle</b></font></a></span>
										</div>
										<div class="col-md-3">
											<span style="float:left;"><a href="#"
												onclick="divisonCheck();"><font style=""
													data-toggle="modal" data-target="#addingDivisionPopUp"
													title="Adding new Division Under Circle"><b>Add
															New Division</b></font></a></span>
										</div>
										<div class="col-md-3">
											<span style="float:left;"><a href="#"
												onclick="subdivisionCheck();"><font style=""
													data-toggle="modal" data-target="#addingSubdivisionPopUp"
													title="Adding new Subdivision Under Division"><b>Add
															New Sub-division</b></font></a></span>
										</div>
										<br></br>
									</div>
									
									<div class="row">
										<div class="col-md-3">
											<span><b>Select Town:</b><font color="red">*</font></span><span><select
												 class="form-control select2me input-medium"
												id="existingSubdivisions_Towns" onchange="return getExistingSections();">
													<!-- onchange="return getbothgroupsandData();" -->
													<option value="">Select</option>
											</select></span>
										</div>
										
										<div class="col-md-3">
												<span><b>Select Section:</b><font color="red">*</font></span>
												<span><select  class="form-control select2me input-medium" id="existingTowns_section">
												<option value="">Select</option>
												</select></span>
										</div>
										
									</div>

									<div class="row">
										<div class="col-sm-3">
											<span style="float:left;"><a href="#" onclick="townCheck();"><font
													style="" data-toggle="modal" data-target="#addingTownPopUp"
													title="Adding new Town Under Subdivision"><b>Add New Town</b></font></a></span>
										</div>
										
										<div class="col-sm-3">
											<span style="float:left;"><a href="#" onclick="sectionCheck();"><font
													style="" data-toggle="modal" data-target="#addingSectionPopUp"
													title="Adding new Section Under Town"><b>Add New Section</b></font></a></span>
										</div>
										
										
										
									</div>
									
									
									
								<!-- 	
									<div class="row">
									
										<div class="col-md-3">
										
												<span><b>Select Section:</b><font color="red">*</font></span>
												<span><select hidden="true" class="form-control select2me input-medium" id="existingTowns_section">
												<option value="">Select</option>
												</select></span>
										
										
										</div>
									
									
									</div> -->

									<%-- <table>
										<tr>
										<td><b>Select Zone:</b><font color="red">*</font></td>
											<td  id="existingZone">
											<select class="form-control select2me input-medium"
												id="existingZone_Section" onchange="return getExistingCircles();">
													<option value="0">Select</option>
													<c:forEach items="${existingZones}" var="existingZonesFromLocation">
														<option value="${existingZonesFromLocation}">${existingZonesFromLocation}</option>
													</c:forEach>
											</select></td>
											
											
											<td><b>Select Circle:</b><font color="red">*</font></td>
											<td  id="existingCircle">
											<select class="form-control select2me input-medium"
												id="existingCircle_Section" onchange="return getExistingDivisions();">
													 <option value="0">Select</option>
													<c:forEach items="${existingCircles}" var="exisingCircleFromLocation">
														<option value="${exisingCircleFromLocation}">${exisingCircleFromLocation}</option>
													</c:forEach>
											</select></td>
											
											
											<td><b>Select Division:</b><font color="red">*</font></td>
											<td  id="existingDivisions" >
											<select  class="form-control select2me input-medium"
												id="existingDivisions_Section" onchange="return getExistingSubdivisions();"> <!-- onchange="return getbothgroupsandData();" -->
												<option value="0">Select</option>	
											</select></td>
											
											
												<td><b>Select Subdivision:</b><font color="red">*</font></td>
											<td  id="existingSubdivisions" >
											<select hidden="true" class="form-control select2me input-medium"
												id="existingSubdivisions_Section" > <!-- onchange="return getbothgroupsandData();" -->
												<option value="0">Select</option>	
											</select></td>
											
											
												<td><button type="button" class="button button5"
														id="addingExistingButton" title="Add the data"
														onclick="return assigningToHierarchy();">ADD</button></td>  <!-- getSACdescriptionButton -->
										</tr>
										<tr>
										    <td>&nbsp;</td>
											<td align="right"><a href="#" ><font style="italic" data-toggle="modal" data-target="#addingZonePopUp" title="Adding new Zone"><b>Add New Zone</b></font></a></td>
											<td>&nbsp;</td>
											<td align="right"><a href="#" onclick="circleCheck();" ><font style="italic" data-toggle="modal" data-target="#addingCriclePopUp" title="Adding new Circle Under Zone"><b>Add New Circle</b></font></a></td> 
											<td>&nbsp;</td>
											<td align="right"><a href="#" onclick="divisonCheck();"><font style="italic" data-toggle="modal" data-target="#addingDivisionPopUp" title="Adding new Division Under Circle"><b>Add New Division</b></font></a></td>
											<td>&nbsp;</td>
											<td align="right"><a href="#" onclick="subdivisionCheck();"><font style="italic" data-toggle="modal" data-target="#addingSubdivisionPopUp" title="Adding new Subdivision Under Division"><b>Add New Sub-division</b></font></a></td>
											<td>&nbsp;</td>
										</tr>
										
									</table> --%>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div> 


	<div class="row" id="groupDataGrid" ><!-- hidden="true" -->
		<!-- hidden="true" -->
		<div class="col-md-12">
			<div class="portlet box green">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-bar-chart-o"></i><font color="white">HIERARCHY DATA</font>
					</div>

				</div>
				<div class="portlet-body" > 
					<div class="row" id="hsnSubDataGridtwo">
						<div class="col-md-12">
							<div class="btn-group pull-right">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
									<li><a href="#" id="excelExport"
										onclick="tableToExcel('sample_editable_1', 'Hierarchy Data')">Export
											to Excel</a></li>
								</ul>
							</div>
							<table 
								class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
										<th style="text-align: center;" bgcolor="#ffc1ea" hidden="true">ID</th>
										<th style="text-align: center;" bgcolor="#ffc1ea">S.NO</th>
										<th style="text-align: center;" bgcolor="#ffc1ea">REGION</th>
										<th style="text-align: center;"	bgcolor="#ffc1ea">CIRCLE</th>
										<th style="text-align: center;" bgcolor="#ffc1ea">DIVISION</th>
										<th style="text-align: center;" bgcolor="#ffc1ea">SUBDIVISION</th>
										<th style="text-align: center;" bgcolor="#ffc1ea">TOWN</th>
										<th style="text-align: center;" bgcolor="#ffc1ea">SECTION</th>
										<!-- <th style="text-align: center;" bgcolor="#ffc1ea">EDIT</th>
										<th style="text-align: center;" bgcolor="#ffc1ea">DELETE</th> -->
									</tr>
								</thead>
								<tbody >

										<c:set var="count" value="1" scope="page"/>
							<c:forEach items="${latestHierarchyData}" var="HierarchyData">
							     <tr>
							        <td style="text-align: center;" hidden="true">${HierarchyData.id}</td>
									<td style="text-align: center;" >${count}</td>
									<td style="text-align: center;" >${HierarchyData.zone}</td>
									<td style="text-align: center;" >${HierarchyData.circle}</td>
									<td style="text-align: center;" >${HierarchyData.division}</td>
									<td style="text-align: center;" >${HierarchyData.subDivision}</td>
									<td style="text-align: center;" >${HierarchyData.town_ipds}</td>
									<td style="text-align: center;" >${HierarchyData.section}</td>
				<!-- 					<td style="text-align: center;" >Edit</td>
									<td style="text-align: center;" >Delete</td> -->
									<%-- <td>${HierarchyData.zone}</td> --%>
								</tr>
							<c:set var="count" value="${count + 1}" scope="page"/>		
							</c:forEach>
										
										
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

		<div id="popUpGrid">
			<div id="addingZonePopUp" class="modal fade" tabindex="-1">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" id="closePopUp">
							</button>
							<h4 class="modal-title">
								<span id="popUpHeading"></span>
							</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col-md-12">
									<div class="portlet box blue">
										<div class="portlet-title">
											<div class="caption">
												<i class="fa fa-bar-chart-o"></i>ADDING REGION:<font color="yellow"><b><span id="subCode"></span></b></font>
											</div>
										</div>

										<div class="portlet-body">
										<form action="./addingZone" method="POST" id="addingZoneForm">
												 <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/> 
											<table id="zonePopUpDataTable"
												class='table table-striped table-hover table-bordered'>
												<tbody>
												<tr>
												<!-- <td  hidden="true"><input id="idgstRate" name="idgstRate"  type="text"></input> </td> -->
												<td style="text-align: center;"><font color="purple"><b>ENTER NEW REGION:</b></font></td>
												<td style="text-align: center;"><input class="form-control input-medium" type="text" id="newZone" name="newZone" placeholder="Enter new Region name"></td>
												</tr>
												<tr>
												<td style="text-align: center;"><font color="purple"><b>ENTER NEW REGION CODE:</b></font></td>
												<td style="text-align: center;"><input class="form-control input-medium" type="number" id="newZoneCode" name="newZoneCode" placeholder="Enter new Region code"></td>
												</tr>
												 <tr>
												 <td style="text-align: center;" colspan=2><button type="button" class="btn green" 
													id="addingZoneButton" title="Submit new Region" onclick="zoneValidations();"
													>Submit</button></td>
													<!-- onclick="return addNewZone();"  -->
												</tr> 
												</tbody>
											</table>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="popUpGrid1">
			<div id="addingCriclePopUp" class="modal fade" tabindex="-1">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" id="closePopUp">
							</button>
							<h4 class="modal-title">
								<span id="popUpHeading"></span>
							</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col-md-12">
									<div class="portlet box blue">
										<div class="portlet-title">
											<div class="caption">
												<i class="fa fa-bar-chart-o"></i>ADDING CIRCLE UNDER:<br><br>
												<div id="dataid"><span>REGION:</span><font color="yellow"><b><span id="zoneCode"></span></b></font></div>
											</div>
										</div>

										<div class="portlet-body">
										<form action="#" method="POST" id="addingnewCircleForm">
												 <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/> 
											<table id="zonePopUpDataTable"
												class='table table-striped table-hover table-bordered'>
												<tbody>
												<tr>
												<td hidden="true"><input type="text" id="zoneFor" name="zoneFor"/></td>
												<td style="text-align: center;"><font color="purple"><b>ENTER NEW CIRCLE:</b></font></td>
												<td style="text-align: center;"><input class="form-control input-medium" type="text" id="newCircle" name="newCircle" placeholder="Enter new Circle name"></td>
												</tr>
												<tr>
												<td style="text-align: center;"><font color="purple"><b>ENTER NEW CIRCLE CODE:</b></font></td>
												<td style="text-align: center;"><input class="form-control input-medium" type="text" id="newCircleCode" name="newCircleCode" placeholder="Enter new Circle code"></td>
												</tr>
												<tr>
												<td style="text-align: center;" colspan=2><button type="button" class="btn green"
													id="addingCircleButton" title="Submit new Circle"
													onclick="return addNewCircle();">Submit</button></td>
												</tr>
												</tbody>
											</table>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="popUpGrid2">
			<div id="addingDivisionPopUp" class="modal fade" tabindex="-1">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" id="closePopUp">
							</button>
							<h4 class="modal-title">
								<span id="popUpHeading"></span>
							</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col-md-12">
									<div class="portlet box blue">
										<div class="portlet-title">
											<div class="caption">
												<i class="fa fa-bar-chart-o"></i>ADDING DIVISION:<br><br>
												<span>REGION:</span><font color="yellow"><b><span id="divZone"></span></b></font>
												<span>CIRCLE:</span><font color="yellow"><b><span id="divCircle"></span></b></font>
											</div>
										</div>

										<div class="portlet-body">
										<form action="#" method="POST" id="addingnewDivisionForm">
												 <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/> 
											<table id="zonePopUpDataTable"
												class='table table-striped table-hover table-bordered'>
												<tbody>
												<tr>
												<!-- <td  hidden="true"><input id="division1" name="division1"  type="text"></input> </td> -->
												<td  hidden="true"><input id="selCircle1" name="selCircle1"  type="text"></input> </td>
												<td  hidden="true"><input id="newZoneFor2" name="newZoneFor2"  type="text"></input> </td>
												<td style="text-align: center;"><font color="purple"><b>ENTER NEW DIVISION:</b></font></td>
												<td style="text-align: center;"><input class="form-control input-medium" type="text" id="newDivision" name="newDivision" placeholder="Enter new Division name"></td>
												</tr>
												<tr>
												<td style="text-align: center;" ><font color="purple"><b>ENTER NEW DIVISION CODE:</b></font></td>
												<td style="text-align: center;"><input class="form-control input-medium" type="text" id="newDivisionCode" name="newDivisionCode" placeholder="Enter new Division code"></td>
												</tr>
												<tr>
												<td style="text-align: center;" colspan=2><button type="button" class="btn green"
													id="addingCircleButton" title="Submit new Circle"
													onclick="return addNewDivision();">Submit</button></td>
												</tr>
												</tbody>
											</table>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="popUpGrid3">
			<div id="addingSubdivisionPopUp" class="modal fade" tabindex="-1">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" id="closePopUp">
							</button>
							<h4 class="modal-title">
								<span id="popUpHeading"></span>
							</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col-md-12">
									<div class="portlet box blue">
										<div class="portlet-title">
											<div class="caption">
												<i class="fa fa-bar-chart-o"></i>ADDING SUBDIVISION:<br><br>
												<span>REGION:</span><font color="yellow"><b><span id="subdivZone"></span></b></font>
												<span>CIRCLE:</span><font color="yellow"><b><span id="subdivCircle"></span></b></font>
												<span>DIVISION:</span><font color="yellow"><b><span id="subdivDivision"></span></b></font>
											</div>
										</div>

										<div class="portlet-body">
										<form action="#" method="POST" id="addingnewSubDivisionForm">
												 <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/> 
											<table id="zonePopUpDataTable"
												class='table table-striped table-hover table-bordered'>
												<tbody>
												<tr>
												<td  hidden="true"><input id="selCircle2" name="selCircle2"  type="text"></input> </td>
												<td  hidden="true"><input id="newZoneFor3" name="newZoneFor3"  type="text"></input> </td>
												<td  hidden="true"><input id="div1" name="div1"  type="text"></input> </td>
												<td style="text-align: center;"><font color="purple"><b>ENTER NEW SUBDIVISION:</b></font></td>
												<td style="text-align: center;"><input class="form-control input-medium" type="text" id="newSubdivision" name="newSubdivision" placeholder="Enter new Sub-division name"></td>
												</tr>
												<tr>
												<td style="text-align: center;" ><font color="purple"><b>ENTER NEW SUBDIVISION CODE:</b></font></td>
												<td style="text-align: center;"><input class="form-control input-medium" type="text" id="newSubdivisionCode" name="newSubdivisionCode" placeholder="Enter new Sub-division code"></td>
												</tr>
												<tr>
												<td style="text-align: center;" colspan=2><button type="button" class="btn green"
													id="addingCircleButton" title="Submit new Circle"
													onclick="return addNewsubDivision();">Submit</button></td>
												</tr>
												</tbody>
											</table>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="popUpGrid4">
			<div id="addingTownPopUp" class="modal fade" tabindex="-1">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" id="closePopUp">
							</button>
							<h4 class="modal-title">
								<span id="popUpHeading"></span>
							</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col-md-12">
									<div class="portlet box blue">
										<div class="portlet-title">
											<div class="caption">
												<i class="fa fa-bar-chart-o"></i>ADDING TOWN:<br><br>
												<span>REGION:</span><font color="yellow"><b><span id="townZone"></span></b></font>
												<span>CIRCLE:</span><font color="yellow"><b><span id="townCircle"></span></b></font>
												<span>DIVISION:</span><font color="yellow"><b><span id="townDivision"></span></b></font>
												<span>SUB-DIVISION:</span><font color="yellow"><b><span id="townSubDivision"></span></b></font>
											</div>
										</div>

										<div class="portlet-body">
										<form action="#" method="POST" id="addingnewTownForm">
												 <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/> 
											<table id="zonePopUpDataTable"
												class='table table-striped table-hover table-bordered'>
												<tbody>
												<tr>
												<td  hidden="true"><input id="selCircle3" name="selCircle3"  type="text"></input> </td>
												<td  hidden="true"><input id="newZoneFor4" name="newZoneFor4"  type="text"></input> </td>
												<td  hidden="true"><input id="div2" name="div2"  type="text"></input> </td>
												<td  hidden="true"><input id="subdiv" name="subdiv"  type="text"></input> </td>
												<td style="text-align: center;"><font color="purple"><b>ENTER NEW TOWN:</b></font></td>
												<td style="text-align: center;"><input class="form-control input-medium" type="text" id="newTown" name="newTown" placeholder="Enter new Town name"></td>
												</tr>
												<tr>
												<td style="text-align: center;" ><font color="purple"><b>ENTER NEW TOWN CODE:</b></font></td>
												<td style="text-align: center;"><input class="form-control input-medium" type="text" id="newTownCode" name="newTownCode" placeholder="Enter new Town code"></td>
												</tr>
												<tr>
												<td style="text-align: center;" colspan=2><button type="button" class="btn green"
													id="addingCircleButton" title="Submit new Town"
													onclick="return addNewTown();">Submit</button></td>
												</tr>
												</tbody>
											</table>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		<div id="popUpGrid5">
			<div id="addingSectionPopUp" class="modal fade" tabindex="-1">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" id="closePopUp">
							</button>
							<h4 class="modal-title">
								<span id="popUpHeading"></span>
							</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col-md-12">
									<div class="portlet box blue">
										<div class="portlet-title">
											<div class="caption">
												<i class="fa fa-bar-chart-o"></i>ADDING SECTION:<br><br>
												<span>REGION:</span><font color="yellow"><b><span id="sectionZone"></span></b></font>
												<span>CIRCLE:</span><font color="yellow"><b><span id="sectionCircle"></span></b></font>
												<span>DIVISION:</span><font color="yellow"><b><span id="sectionDivision"></span></b></font>
												<span>SUB-DIVISION:</span><font color="yellow"><b><span id="sectionSubDivision"></span></b></font>
												<span>TOWN:</span><font color="yellow"><b><span id="sectionTown"></span></b></font>
											</div>
										</div>

										<div class="portlet-body">
										<form action="#" method="POST" id="addingnewSectionForm">
												 <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/> 
											<table id="zonePopUpDataTable"
												class='table table-striped table-hover table-bordered'>
												<tbody>
												<tr>
												<td  hidden="true"><input id="selCircle4" name="selCircle4"  type="text"></input> </td>
												<td  hidden="true"><input id="newZoneFor5" name="newZoneFor5"  type="text"></input> </td>
												<td  hidden="true"><input id="div3" name="div3"  type="text"></input> </td>
												<td  hidden="true"><input id="subdiv" name="subdiv"  type="text"></input> </td>
												<td  hidden="true"><input id="town" name="town"  type="text"></input> </td>
												<td style="text-align: center;"><font color="purple"><b>ENTER NEW SECTION:</b></font></td>
												<td style="text-align: center;"><input class="form-control input-medium" type="text" id="newSection" name="newSection" placeholder="Enter new Section name"></td>
												</tr>
												<tr>
												<td style="text-align: center;" ><font color="purple"><b>ENTER NEW SECTION CODE:</b></font></td>
												<td style="text-align: center;"><input class="form-control input-medium" type="text" id="newSectionCode" name="newSectionCode" placeholder="Enter new Section code"></td>
												</tr>
												<tr>
												<td style="text-align: center;" colspan=2><button type="button" class="btn green"
													id="addingCircleButton" title="Submit new Section"
													onclick="return addNewSection();">Submit</button></td>
												</tr>
												</tbody>
											</table>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
	</div>
	<script>
	function zoneValidations(){
		
		 var zoneName=$("#newZone").val();
		 var zoneCode=$("#newZoneCode").val();
		
		 if(zoneName == ''){
			 bootbox.alert("Enter Region Name");
			 return false;
		 }

		 if(zoneCode == ''){
			 bootbox.alert("Enter Region Code");
			 return false;
		 }
		 $("#addingZoneForm").submit();
	}
	
	
	
	
	
	
	
	
	</script>
		<style>
		
				.button {
						    background-color: black; /* Green */
						    border: none;
						    color: white;
						    padding: 4px 15px;
						    text-align: center;
						    text-decoration: none;
						    display: inline-block;
						    font-size: 16px;
						    margin: 4px 2px;
						    -webkit-transition-duration: 0.4s; /* Safari */
						    transition-duration: 0.4s;
						    cursor: pointer;
						}
				.button5 {
						    background-color: white;
						    color: black;
						    border: 2px solid #555555;
					    }

				.button5:hover {
 								   background-color: #555555;
   								   color: white;
								}
								
					#bcitsBrk{
						  			 word-wrap: break-word;
						 			 min-width:450px;
						  			 // max-width:200px;
						  			 white-space:normal;
						  	 }			
								
          
						       
		</style>
		
		
		
