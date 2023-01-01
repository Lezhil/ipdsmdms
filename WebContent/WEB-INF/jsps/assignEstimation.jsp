<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	if (session.getAttribute("username") == null || session.getAttribute("username") == null) {
%>
<script>
	window.location.href = "./?sessionVal=expired";
</script>
<%
	}
%>

<script>
$(".page-content").ready(	 	
		function() {
			App.init();
			TableEditable.init();
			FormComponents.init();
			loadSearchAndFilter('sample_1');	
			loadSearchAndFilter('sample_2');
			loadSearchAndFilter('sample_3');
			loadSearchAndFilter('sample_4');
			loadSearchAndFilter('sample_5');
			loadSearchAndFilter('sample_6');

			loadSearchAndFilter('sample_unBoundary');
			loadSearchAndFilter('sample_boundary');
					
			var zoneVal="${zoneVal}";
			var circleVal="${circleVal}";
			getESTRuleDetails();
			getConsumercategory();
			//hideconfeed("DT");
		//	$('input[id="Consumer"]').prop('checked', true);
			/* $('#Consumer').click();
			$('#Consumer').click();
			$('#uConsumer').click();
			$('#uConsumer').click(); */
			$('#DT').click();
			$('#uDT').click();
			$('#DT').click();
			$('#uDT').click();

			  $("#dtcheck").click(function() {
					$(".checkboxes").prop('checked',$(this).prop('checked'));
				});
		       $("#undtcheck").click(function() {
					$(".checkboxes1").prop('checked',$(this).prop('checked'));
				});

		       $("#fdrcheck").click(function() {
					$(".checkboxes2").prop('checked',$(this).prop('checked'));
				});
		       $("#unfdrcheck").click(function() {
					$(".checkboxes3").prop('checked',$(this).prop('checked'));
				});

			
		//	$('input[id="uConsumer"]').prop('checked', true);
	       if(zoneVal!='' && circleVal!=''){	    	   
				$('#zone').find('option').remove().end().append('<option value="'+ zoneVal +'">'+ zoneVal +'</option>');
				$("#zone").val(zoneVal).trigger("change");
				
				setTimeout(function(){ 
					$('#circle').find('option').remove().end().append('<option value="'+ circleVal +'">'+ circleVal +'</option>');
					$("#circle").val(circleVal).trigger("change");
				}, 200);
			} else{
				$("#zone").val("").trigger("change");
			}
			
	       $('#MDMSideBarContents,#dataValidId,#assignEstimation').addClass('start active ,selected');
		   $("#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#360d-view,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
			.removeClass('start active ,selected');
			
		});

</script>
<script type="text/javascript">



function getESTRuleDetails()
{	
	$('#estimationrule').find('option').remove();
 	$('#estimationrule').append($('<option>', {
		value : "",
		text : "Select Estimation Rule"
	})); 

	$('#unestimationrule').find('option').remove();
	$('#unestimationrule').append($('<option>', {
		value : "",
		text : "Select Estimation Rule"
	})); 
	
	$.ajax({
		    url : "./getEstimationActiveRule",
	    	type:'POST',
	    	success:function(response)
	    	{
	    		 for(var i=0;i<=response.length;i++){
		    		// alert(response[i].ruleid);
					 $('#estimationrule').append($('<option>', {
							value : response[i].eruleid,
							text : response[i].erulename
						}));	
					 $('#unestimationrule').append($('<option>', {
							value : response[i].eruleid,
							text : response[i].erulename
						}));					 
			}
	    	}
		}); 
}


function getConsumercategory()
{	

	$('#consumerCatgry').find('option').remove();
	$('#consumerCatgry').append($('<option>', {
		value : "",
		text : "Select Consumer Category"
	})); 
	$('#unconsumerCatgry').find('option').remove();
	$('#unconsumerCatgry').append($('<option>', {
		value : "",
		text : "Select Consumer Category"
	})); 
	
	$.ajax({
		url : "./getConsumercategory",
	    	type:'POST',
	    	success:function(response)
	    	{
	    		 for(var i=0;i<=response.length;i++){
					 $('#consumerCatgry').append($('<option>', {
							value : response[i].trim(),
							text : response[i].trim()
						}));	

					 $('#unconsumerCatgry').append($('<option>', {
							value : response[i].trim(),
							text : response[i].trim()
						}));				 
			}
	    	}
		});
}

function hidefeeddt(x)
{
	if (x.checked) {  

		$('#consumerCatgry').val('');
		$('#acno').val('');
		$('#kno').val();
		$('#mtrno').val('');
		$("#showConsumer").show();
		$("#showfeeder").hide();
		$("#showDT").hide(); 
     }

}

function hideconfeed(x)
{
	if (x.checked) {
		$('#asgdtcode').val('');
		$('#asgdtmtrno').val('');
		$("#showConsumer").hide();
		$("#showfeeder").hide();
		$("#showBoundary").hide();
		$("#showDT").show(); 
	}
}

function  hidecondt(x)
{
	if (x.checked) {
		$('#asgfdcode').val('');
		$('#asgfdrmtrno').val('');

		$("#showConsumer").hide();
		$("#showBoundary").hide();
		$("#showfeeder").show();
		$("#showDT").hide(); 
		crossfeeder = false;
	}
}

function  hideconboundary(x)
{
	if (x.checked) {
		$('#asgfdcode').val('');
		$('#asgfdrmtrno').val('');

		$("#showConsumer").hide();
		$("#showfeeder").hide();
		$("#showDT").hide();
		$("#showBoundary").show(); 
		crossfeeder = true;
		
	}
}

function uhidefeeddt(x)
{
	if (x.checked) {  
		$('#unconsumerCatgry').val('');
		$('#uacno').val('');
		$('#ukno').val();
		$('#umtrcode').val('');
		$("#ushowConsumer").show();
		$("#ushowfeeder").hide();
		$("#ushowDT").hide(); 
    }

}

function uhideconfeed(x)
{
	if (x.checked) {
		$('#udtcode').val('');
		$('#umtrcode').val('');
		$("#ushowConsumer").hide();
		$("#ushowfeeder").hide()
		$("#ushowBoundary").hide();
		$("#ushowDT").show(); 
	}
}

function  uhidecondt(x)
{
	if (x.checked) {
		$('#ufdcode').val('');
		$('#unasgfdrmtrno').val('');
		$("#ushowConsumer").hide();
		$("#ushowBoundary").hide();
		$("#ushowfeeder").show();
		$("#ushowDT").hide(); 
		crossfeeder = false;
	}
}

function  uhideconboundary(x)
{
	if (x.checked) {
		$('#ufdcode').val('');
		$('#unasgfdrmtrno').val('');
		$("#ushowConsumer").hide();
		$("#ushowBoundary").show();
		$("#ushowfeeder").hide();
		$("#ushowDT").hide(); 
		crossfeeder = true;
	}
}

function getConsumerData(){

	var zone = $('#LFzone').val();
	var circle = $('#LFcircle').val();
	var town = $('#LFtown').val();
	var consumerCatgry = $('#consumerCatgry').val();
	var acno = $('#acno').val();
	var kno = $('#kno').val();
	var mtrno = $('#mtrno').val();
	var ruleType= $("#estimationrule").val();

	if (zone == "") {
		bootbox.alert("Please Select Region");
		return false;
	}

	if (circle == "") {
		bootbox.alert("Please Select circle");
		return false;
	}

	

	if (town == "") {
		bootbox.alert("Please Select Town");
		return false;
	}

	if(ruleType==""){
    	bootbox.alert("Please Select Estimation Rule");
		return false;
    }
	$('#imageee').show();
		$
		.ajax({
		    url : './getEstConsumerData',
	    	type:'POST',
	    	data : {
		    	zone :zone,
		    	circle:circle,
		    	division:'%',
		    	sdoCode:'%',
		    	consumerCatgry:consumerCatgry,
		    	acno:acno,
		    	kno:kno,
		    	mtrno:mtrno,
		    	ruleType:ruleType
		    	
		    },
			dataType : 'json',
	    	success:function(response)
	    	{
	    		$('#imageee').hide();
	    		$("#updateMaster").html('');
	    		if(response!=null && response.length>0 )
				{
	    		  $('#assignConsumerData').show();
			      var html="";
				  for (var i = 0; i < response.length; i++) 
				  {
					  var resp=response[i];
					//  alert(resp[0]);
					   html+="<tr>"+
					   "<td><input id=check name=check type=checkbox class=checkboxes value="+resp[3]+" /></td>"+
					    "<td>"+(resp[0]==null?"":resp[0])+" </td>"+
					    "<td>"+(resp[1]==null?"":resp[1])+" </td>"+
					    "<td>"+(resp[2]==null?"":resp[2])+" </td>"+
						"<td>"+(resp[3]==null?"":resp[3])+" </td>"+
						"<td>"+(resp[4]==null?"":resp[4])+" </td>"+
						"<td>"+(resp[5]==null?"":resp[5])+" </td>"+
						"<td>"+(resp[6]==null?"":resp[6])+" </td>"+
						"<td>"+(resp[7]==null?"":resp[7])+" </td>"+
					  "</tr>";				
				   }                                
				  $('#sample_1').dataTable().fnClearTable();
				  $("#updateMaster").html(html);
				  loadSearchAndFilter('sample_1'); 
				}else{
					 $('#sample_1').dataTable().fnClearTable();
					 $('#assignConsumerData').hide();
					}
	    						 
			},
			complete: function()
			{  
				loadSearchAndFilter('sample_1'); 
			}
		});

}

function getAssignConsumerData(){

	var zone = $('#zone').val();
	var circle = $('#circle').val();
	var division = $('#division').val();
	var sdoCode = $('#sdoCode').val();
	var town = $('#town').val();
	var consumerCatgry = $('#unconsumerCatgry').val();
	var acno = $('#uacno').val();
	var kno = $('#ukno').val();
	var mtrno = $('#umtrcode').val();
	var ruleType= $("#unestimationrule").val();

	if (zone == "") {
		bootbox.alert("Please Select Region");
		return false;
	}

	if (circle == "") {
		bootbox.alert("Please Select circle");
		return false;
	}

	if (division == "") {
		bootbox.alert("Please Select division");
		return false;
	}

	if (sdoCode == "") {
		bootbox.alert("Please Select Subdivision");
		return false;
	}
	if (town == "") {
		bootbox.alert("Please Select Town");
		return false;
	}

	if(ruleType==""){
    	bootbox.alert("Please Select Estimation Rule");
		return false;
    }
	$('#imageee').show();
		$
		.ajax({
		    url : './getEstAssignConsumerData',
	    	type:'POST',
	    	data : {
		    	zone :zone,
		    	circle:circle,
		    	division:division,
		    	sdoCode:sdoCode,
		    	consumerCatgry:consumerCatgry,
		    	acno:acno,
		    	kno:kno,
		    	mtrno:mtrno,
		    	ruleType:ruleType
		    	
		    },
			dataType : 'json',
	    	success:function(response)
	    	{
	    		$('#imageee').hide();
	    		$("#updateMaster4").html('');
	    		if(response!=null && response.length>0 )
				{
	    			$('#unassignConsumerData').show();
			     var html="";
				  for (var i = 0; i < response.length; i++) 
				  {
					  var resp=response[i];
					   html+="<tr>"+
					   "<td><input id=unasgcheck name=unasgcheck type=checkbox class=checkboxes value="+resp[3]+" /></td>"+
					   "<td>"+(resp[0]==null?"":resp[0])+" </td>"+
					   "<td>"+(resp[1]==null?"":resp[1])+" </td>"+
					   "<td>"+(resp[2]==null?"":resp[2])+" </td>"+
					   "<td>"+(resp[3]==null?"":resp[3])+" </td>"+
					   "<td>"+(resp[4]==null?"":resp[4])+" </td>"+
					   "<td>"+(resp[5]==null?"":resp[5])+" </td>"+
					   "<td>"+(resp[6]==null?"":resp[6])+" </td>"+
					   "<td>"+(resp[7]==null?"":resp[7])+" </td>"+
					  "</tr>";				
				   }                                
				  $('#sample_4').dataTable().fnClearTable();
				  $("#updateMaster4").html(html);
				  loadSearchAndFilter('sample_4'); 
				}else{
					 $('#sample_4').dataTable().fnClearTable();
					$('#unassignConsumerData').hide();
					}
	    						 
			},
			complete: function()
			{  
				loadSearchAndFilter('sample_4'); 
			}
		});
}

function getDTData(){
	var zone = $('#LFzone').val();
	var circle = $('#LFcircle').val();
	
	var town = $('#LFtown').val();
	var crossdt = $("input[name='asg_dt_radio']").is(":checked");
	var dtcode = $('#asgdtcode').val();
	var dtmtrno = $('#asgdtmtrno').val();
	var ruleType= $("#estimationrule").val();

	if (zone == "") {
		bootbox.alert("Please Select Region");
		return false;
	}

	if (circle == "") {
		bootbox.alert("Please Select circle");
		return false;
	}

	
	if (town == "") {
		bootbox.alert("Please Select Town");
		return false;
	}

	if(ruleType==""){
    	bootbox.alert("Please Select Estimation Rule");
		return false;
    }
	$('#imageee').show();
		 $
		.ajax({
		    url : './getEstDTData',
	    	type:'POST',
	    	data : {
		    	zone :zone,
		    	circle:circle,
		    	division:'%',
		    	sdoCode:'%',
		    	dtcode:dtcode,
		    	crossdt:crossdt,
		    	dtmtrno:dtmtrno,
		    	ruleType:ruleType,
		    	town:town
		    	
		    },
			dataType : 'json',
	    	success:function(response)
	    	{
	    		$('#imageee').hide();
	    		$("#updateMaster2").html('');
	    		 if(response!=null && response.length>0 )
				{
	    			 $('#AssignDTData').show();
			     var html="";
				  for (var i = 0; i < response.length; i++) 
				  {
					  var resp=response[i];
					//  alert(resp[0]);
					   html+="<tr>"+
					   "<td><input id='dtcheck' name='dtcheck' type='checkbox' class='checkboxes' value="+resp[7]+"></td>"+
					   "<td>"+(resp[0]==null?"":resp[0])+" </td>"+
						  "<td>"+(resp[1]==null?"":resp[1])+" </td>"+
						  "<td>"+(resp[2]==null?"":resp[2])+" </td>"+
						  "<td>"+resp[6]+" </td>"+
						  /* "<td>"+(resp[3]==null?"":resp[3])+" </td>"+ */
						  "<td>"+(resp[4]==null?"":resp[4])+" </td>"+
						  "<td>"+(resp[5]==null?"":resp[5])+" </td>"+
					  "</tr>";				
				   }                                
				  $('#sample_2').dataTable().fnClearTable();
				  $("#updateMaster2").html(html);
				  loadSearchAndFilter('sample_2'); 
				} else{
					$('#AssignDTData').hide();
					 loadSearchAndFilter('sample_2');
					}
	    						 
			},
			complete: function()
			{  
				loadSearchAndFilter('sample_2'); 
			}
		});	 
}


function getAssignDTData(){
	var zone = $('#LFzone').val();
	var circle = $('#LFcircle').val();
	//var division = $('#division').val();
	//var sdoCode = $('#sdoCode').val();
	var town = $('#LFtown').val();
	var crossdt = $("input[name='unasg_dt_radio']").is(":checked");
	var dtcode = $('#udtcode').val();
	var dtmtrno = $('#umtrcode').val();
	var ruleType= $("#unestimationrule").val();
    
	if (zone == "") {
		bootbox.alert("Please Select Region");
		return false;
	}

	if (circle == "") {
		bootbox.alert("Please Select circle");
		return false;
	}

	/* if (division == "") {
		bootbox.alert("Please Select division");
		return false;
	}

	if (sdoCode == "") {
		bootbox.alert("Please Select Subdivision");
		return false;
	} */
	if (town == "") {
		bootbox.alert("Please Select Town");
		return false;
	}

	if(ruleType==""){
    	bootbox.alert("Please Select Estimation Rule");
		return false;
    }
	//alert(zone);
	$('#imageee').show();
		 $
		.ajax({
		    url : './getEstAssignDTData',
	    	type:'POST',
	    	data : {
		    	zone :zone,
		    	circle:circle,
		    	division:'%',
		    	sdoCode:'%',
		    	dtcode:dtcode,
		    	crossdt:crossdt,
		    	dtmtrno:dtmtrno,
		    	ruleType:ruleType,
		    	town:town
		    	
		    },
			dataType : 'json',
	    	success:function(response)
	    	{   //alert(response);
	    		$('#imageee').hide();
	    		$("#updateMaster5").html('');
	    		 if(response!=null && response.length>0 )
				{
	    			 $('#UnAssignDTData').show();
			     var html="";
				  for (var i = 0; i < response.length; i++) 
				  {
					  var resp=response[i];
					//  alert(resp[0]);
					   html+="<tr>"+
					   "<td><input id='undtcheck' name='undtcheck' type='checkbox' class='checkboxes1' value="+resp[7]+" /></td>"+
					     "<td>"+(resp[0]==null?"":resp[0])+" </td>"+
					     "<td>"+(resp[1]==null?"":resp[1])+" </td>"+
						 "<td>"+(resp[2]==null?"":resp[2])+" </td>"+
						 "<td>"+resp[6]+" </td>"+
						 /* "<td>"+(resp[3]==null?"":resp[3])+" </td>"+ */
						 "<td>"+(resp[4]==null?"":resp[4])+" </td>"+
						 "<td>"+(resp[5]==null?"":resp[5])+" </td>"+
					  "</tr>";				
				   }                                
				  $('#sample_5').dataTable().fnClearTable();
				  $("#updateMaster5").html(html);
				  loadSearchAndFilter('sample_5'); 
				} else{
					$('#sample_5').dataTable().fnClearTable();
					 $('#UnAssignDTData').hide();
					}
	    						 
			},
			complete: function()
			{  
				loadSearchAndFilter('sample_5'); 
			}
		});	 
}
var crossfeeder = true;

function getfeederData(){
	var zone = $('#LFzone').val();
	var circle = $('#LFcircle').val();
	var town = $('#LFtown').val();
	//var crossfeeder = $("input[name='asg_fdr_radio']").is(":checked");
	var feedercode =""; 
	var fdrmtrno = "";
	if(crossfeeder){
		   fdrmtrno=$('#asgbdrmtrno').val();
		   feedercode =$('#asgbdcode').val();
		}else{
			fdrmtrno=$('#asgfdrmtrno').val();
			feedercode =$('#asgfdcode').val();
			}
	
	var ruleType= $("#estimationrule").val();

	if (zone == "") {
		bootbox.alert("Please Select Region");
		return false;
	}

	if (circle == "") {
		bootbox.alert("Please Select circle");
		return false;
	}

	if (town == "") {
		bootbox.alert("Please Select Town");
		return false;
	}

	if(ruleType==""){
    	bootbox.alert("Please Select Estimation Rule");
		return false;
    }
	$('#imageee').show();
	//alert(" sdoCode: "+sdoCode+"circle: "+circle+" division: "+division+" feedercode : "+feedercode+" fdrmtrno: "+fdrmtrno );

		 $
		.ajax({
		    url : './getEstUnassignFeederData',
	    	type:'POST',
	    	data : {
		    	zone :zone,
		    	circle:circle,
		    	division:'%',
		    	sdoCode:'%',
		    	feedercode:feedercode,
		    	crossfeeder:crossfeeder,
		    	fdrmtrno:fdrmtrno,
		    	ruleType:ruleType,
		    	town:town
		    	
		    },
			dataType : 'json',
	    	success:function(response)
	    	{
	    		$('#imageee').hide();
	    		if(crossfeeder){
	    			$("#updateMasterboundary").html('');
		    		
		    		 if(response!=null && response.length>0 )
					{
		    			 $("#assignBoundaryData").show();
				     var html="";
					  for (var i = 0; i < response.length; i++) 
					  {
						  var resp=response[i];
						   html+="<tr>"+
						   "<td><input id='fdrcheck' name='fdrcheck' type='checkbox' class='checkboxes2' value="+resp[8]+" /></td>"+
						   "<td>"+(resp[0]==null?"":resp[0])+" </td>"+
						   "<td>"+(resp[1]==null?"":resp[1])+" </td>"+
						   "<td>"+(resp[2]==null?"":resp[2])+" </td>"+
						   "<td>"+resp[7]+" </td>"+
						   "<td>"+(resp[3]==null?"":resp[3])+" </td>"+
						   "<td>"+(resp[5]==null?"":resp[5])+" </td>"+
						   "<td>"+(resp[4]==null?"":resp[4])+" </td>"+
						   "<td>"+(resp[6]==null?"":resp[6])+" </td>"+
						  "</tr>";				
					   }                                
					  $('#sample_boundary').dataTable().fnClearTable();
					  $("#updateMasterboundary").html(html);
					  loadSearchAndFilter('sample_boundary'); 
					} else{
						$("#updateMasterboundary").html('');
						$('#sample_boundary').dataTable().fnClearTable();
						 $("#assignBoundaryData").hide();
						}
		    		
	    		}else{
	    		$("#updateMaster3").html('');
	    		
	    		 if(response!=null && response.length>0 )
				{
	    			 $("#assignFeederData").show();
			     var html="";
				  for (var i = 0; i < response.length; i++) 
				  {
					  var resp=response[i];
					   html+="<tr>"+
					   "<td><input id='fdrcheck' name='fdrcheck' type='checkbox' class='checkboxes2' value="+resp[8]+" /></td>"+
					   "<td>"+(resp[0]==null?"":resp[0])+" </td>"+
					   "<td>"+(resp[1]==null?"":resp[1])+" </td>"+
					   "<td>"+(resp[2]==null?"":resp[2])+" </td>"+
					   "<td>"+resp[7]+" </td>"+
					   "<td>"+(resp[3]==null?"":resp[3])+" </td>"+
					   "<td>"+(resp[5]==null?"":resp[5])+" </td>"+
					   "<td>"+(resp[4]==null?"":resp[4])+" </td>"+
					   "<td>"+(resp[6]==null?"":resp[6])+" </td>"+
					  "</tr>";				
				   }                                
				  $('#sample_3').dataTable().fnClearTable();
				  $("#updateMaster3").html(html);
				  loadSearchAndFilter('sample_3'); 
				} else{
					$("#updateMaster3").html('');
					$('#sample_3').dataTable().fnClearTable();
					 $("#assignFeederData").hide();
					}

	    	}
	    						 
			},
			complete: function()
			{  
				//loadSearchAndFilter('sample_3'); 
			}
		});	 
}


function getAssignFeederData(){
	//alert(crossfeeder);
	var zone = $('#LFzone').val();
	var circle = $('#LFcircle').val();
	var town = $('#LFtown').val();
	//var crossfeeder = $("input[name='unasg_fdr_radio']").is(":checked");
	var feedercode =""; 
	var fdrmtrno = "";
	var ruleType= $("#unestimationrule").val();

	if(crossfeeder){
		feedercode =$('#ubdcode').val();
		fdrmtrno =$('#unasgbdrmtrno').val();

	}else{
		feedercode =$('#ufdcode').val();
		fdrmtrno =$('#unasgfdrmtrno').val();
		}
	//alert(feedercode);

	if (zone == "") {
		bootbox.alert("Please Select Region");
		return false;
	}

	if (circle == "") {
		bootbox.alert("Please Select circle");
		return false;
	}

	if (town == "") {
		bootbox.alert("Please Select Town");
		return false;
	}

	if(ruleType==""){
    	bootbox.alert("Please Select Estimation Rule");
		return false;
    }
	$('#imageee').show();
		 $
		.ajax({
		    url : './getEstAssignFeederData',
	    	type:'POST',
	    	data : {
		    	zone :zone,
		    	circle:circle,
		    	division:'%',
		    	sdoCode:'%',
		    	feedercode:feedercode,
		    	crossfeeder:crossfeeder,
		    	fdrmtrno:fdrmtrno,
		    	ruleType:ruleType,
		    	town:town
		    	
		    },
			dataType : 'json',
	    	success:function(response)
	    	{  // alert(response);
	    		$('#imageee').hide();
	    		if(crossfeeder){
	    			$("#updateMasterUnBoundary").html('');
		    		if(response!=null && response.length>0 )
					{
		    			 $("#UnassignBoundaryData").show();
				     var html="";
					  for (var i = 0; i < response.length; i++) 
					  {
						  var resp=response[i];
						   html+="<tr>"+
						   "<td><input id='unfdrcheck' name='unfdrcheck' type='checkbox' class='checkboxes3' value="+resp[8]+" /></td>"+
						   "<td>"+(resp[0]==null?"":resp[0])+" </td>"+
						   "<td>"+(resp[1]==null?"":resp[1])+" </td>"+
						   "<td>"+(resp[2]==null?"":resp[2])+" </td>"+
						   "<td>"+resp[7]+" </td>"+
						   "<td>"+(resp[3]==null?"":resp[3])+" </td>"+
						   "<td>"+(resp[5]==null?"":resp[5])+" </td>"+
						   "<td>"+(resp[4]==null?"":resp[4])+" </td>"+
						   "<td>"+(resp[6]==null?"":resp[6])+" </td>"+
						  "</tr>";				
					   }                                
					  $('#sample_unBoundary').dataTable().fnClearTable();
					  $("#updateMasterUnBoundary").html(html);
					  loadSearchAndFilter('sample_unBoundary'); 
					} else{
						$("#updateMasterUnBoundary").html('');
						$('#sample_unBoundary').dataTable().fnClearTable();
						$("#UnassignBoundaryData").hide();
					}
		    		}else{
	    		$("#updateMaster6").html('');
	    		if(response!=null && response.length>0 )
				{
	    			 $("#UnassignFeederData").show();
			     var html="";
				  for (var i = 0; i < response.length; i++) 
				  {
					  var resp=response[i];
					   html+="<tr>"+
					   "<td><input id='unfdrcheck' name='unfdrcheck' type='checkbox' class='checkboxes3' value="+resp[8]+" /></td>"+
					   "<td>"+(resp[0]==null?"":resp[0])+" </td>"+
					   "<td>"+(resp[1]==null?"":resp[1])+" </td>"+
					   "<td>"+(resp[2]==null?"":resp[2])+" </td>"+
					   "<td>"+resp[7]+" </td>"+
					   "<td>"+(resp[3]==null?"":resp[3])+" </td>"+
					   "<td>"+(resp[5]==null?"":resp[5])+" </td>"+
					   "<td>"+(resp[4]==null?"":resp[4])+" </td>"+
					   "<td>"+(resp[6]==null?"":resp[6])+" </td>"+
					  "</tr>";				
				   }                                
				  $('#sample_6').dataTable().fnClearTable();
				  $("#updateMaster6").html(html);
				  loadSearchAndFilter('sample_6'); 
				} else{
					$("#updateMaster6").html('');
					$('#sample_6').dataTable().fnClearTable();
					$("#UnassignFeederData").hide();
				}
	    	}
	    						 
			},
			complete: function()
			{  
				loadSearchAndFilter('sample_6'); 
			}
		});	 
}




</script>

<script>
function assignData(){
	var ruleType= $("#estimationrule").val();
	var locationType= $("input[name='optionsRadios']:checked").val();
	if(ruleType==""){
    	bootbox.alert("Please Select Estimation Rule");
		return false;
    }
	if(locationType == 'Consumer'){
		   
		  var locationCode = [];
		    $.each($("input[name='check']:checked"), function(){            
		    	locationCode.push($(this).val());
		    }); 
  }

	if(locationType == 'DT'){
		  var locationCode = [];
		    $.each($("input[name='dtcheck']:checked"), function(){            
		    	locationCode.push($(this).val());
		    }); 
  }
	if(locationType == 'Feeder'){

		  var locationCode = [];
		    $.each($("input[name='fdrcheck']:checked"), function(){            
		    	locationCode.push($(this).val());
		    }); 
  }
	if(locationType == 'Boundary'){

		  var locationCode = [];
		    $.each($("input[name='fdrcheck']:checked"), function(){            
		    	locationCode.push($(this).val());
		    });
	}    
	  if (locationCode.length === 0) {
			bootbox.alert("Please Select Data to Assign Rule.");
	  }else{	
    $
	.ajax({
	    url : './assignEstimationData',
    	type:'POST',
    	data : {
    		ruleType : ruleType,
    		locationType : locationType,
    		locationCode : JSON.stringify({ locationCode:locationCode })  	
	    },
		dataType : 'text',
    	success:function(response)
    	{
        	if(response =='success'){
        		bootbox.alert("Data Assign successfully.");
        		if(locationType == 'Consumer'){
        			  $('#sample_1').dataTable().fnClearTable();
        			  getConsumerData();
        	    }
        		if(locationType == 'DT'){
        			  $('#sample_2').dataTable().fnClearTable();
        			  getDTData();
        	    }
        		if(locationType == 'Feeder'){
        			  $('#sample_3').dataTable().fnClearTable();
        			  getfeederData();
        	    }
        	    if(locationType == 'Boundary'){
      			  $('#sample_boundary').dataTable().fnClearTable();
      			  getfeederData();
      	    }

            }else{
            	bootbox.alert("Some error occured.");
            }					 
		}
	});	
	  }
       
}



function UnassignData(){
	var ruleType= $("#unestimationrule").val();
	var locationType= $("input[name='uoptionsRadios']:checked").val();
	if(ruleType==""){
    	bootbox.alert("Please Select Estimation Rule");
		return false;
    }
	if(locationType == 'Consumer'){
		   
		  var locationCode = [];
		    $.each($("input[name='unasgcheck']:checked"), function(){            
		    	locationCode.push($(this).val());
		    }); 
  }

	if(locationType == 'DT'){
		  var locationCode = [];
		    $.each($("input[name='undtcheck']:checked"), function(){            
		    	locationCode.push($(this).val());
		    }); 
  }
	if(locationType == 'Feeder'){

		  var locationCode = [];
		    $.each($("input[name='unfdrcheck']:checked"), function(){            
		    	locationCode.push($(this).val());
		    }); 
  }

	if(locationType == 'Boundary'){

		  var locationCode = [];
		    $.each($("input[name='unfdrcheck']:checked"), function(){            
		    	locationCode.push($(this).val());
		    }); 
}
	if (locationCode.length === 0) {
		bootbox.alert("Please Select Data to Unassign Rule.");
	}else{
     $
	.ajax({
	    url : './unassignEstimationData',
    	type:'POST',
    	data : {
    		ruleType : ruleType,
    		locationType : locationType,
    		locationCode : JSON.stringify({ locationCode:locationCode })  	
	    },
		dataType : 'text',
    	success:function(response)
    	{
        	if(response =='success'){
        		bootbox.alert("Data Unassigned successfully.");
        		if(locationType == 'Consumer'){
        			  $('#sample_4').dataTable().fnClearTable();
        			  getAssignConsumerData();
        	    }
        		if(locationType == 'DT'){
        			  $('#sample_5').dataTable().fnClearTable();
        			  getAssignDTData();
        	    }
        		if(locationType == 'Feeder'){
        			  $('#sample_6').dataTable().fnClearTable();
        			  getAssignFeederData();
        	    }
        		if(locationType == 'Boundary'){
      			  $('#sample_unBoundary').dataTable().fnClearTable();
      			  getAssignFeederData();
      	        }
            }else{
            	bootbox.alert("Some error occured.");
            }					 
		}
	});	 
	}
}

</script>


<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<%-- <c:if test="${results ne 'notDisplay'}">
				<c:choose>
				<c:when test="${alert_type eq 'success'}">
					<div class="alert alert-success display-show">
						<button class="close" data-close="alert"></button>
						<span style="color: green">${results}</span>
					</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-danger display-show">
						<button class="close" data-close="alert"></button>
						<span style="color: red">${results}</span>
					</div>
				</c:otherwise>
				</c:choose>				
			</c:if>
 --%>
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Assign Estimation Rules
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				<jsp:include page="locationFilter.jsp"/> 
				
				<%-- <div class="row" style="margin-left: -1px;">
					<div class="col-md-3">
						<div class="form-group">
						<label class='control-label'>Region:</label>
							<select class="form-control select2me" name="zone" id="zone"
									onchange="showCircle(this.value);">
									<option value="">Select Region</option>
									<option value="%">ALL</option>
									<c:forEach var="elements" items="${zoneList}">
									<option value="${elements}">${elements}</option>
									</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-3">
						<div id="circleTd" class="form-group">
						<label class='control-label'>Circle:</label>
							<select class="form-control select2me" id="circle" name="circle">
									<option value="">Select Circle</option>
									<option value="ALL">ALL</option>
									<c:forEach items="${circleList}" var="elements">
									<option value="${elements}">${elements}</option>
									</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-3">
						<div id="divisionTd" class="form-group">
						<label class='control-label'>Division:</label>
							<select class="form-control select2me" id="division" name="division">
									<option value="">Select Division</option>
									<option value="ALL">ALL</option>
									<c:forEach items="${divisionList}" var="elements">
									<option value="${elements}">${elements}</option>
									</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-3">
						<div id="subdivTd" class="form-group">
						<label class='control-label'>Sub-Division:</label>
							<select class="form-control select2me" id="sdoCode" name="sdoCode">
									<option value="">Select Sub-Division</option>
									<option value="ALL">ALL</option>
									<c:forEach items="${subdivList}" var="sdoVal">
									<option value="${sdoVal}">${sdoVal}</option>
									</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="row" style="margin-left: -1px;">
					<div class="col-md-3">
						<div class="form-group">
						<label class='control-label'>Town:</label>
							<select class="form-control select2me" name="town" id="town">
									<option value="">Select Town</option>
									<option value="%">ALL</option>
									<c:forEach var="elements" items="${townList}">
									<option value="${elements}">${elements}</option>
									</c:forEach>
							</select>
						</div>
					</div>
				
				</div> --%>
					<!--BEGIN TABS-->
					<div class="tabbable tabbable-custom">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#tab_1_1"   data-toggle="tab">Assign</a></li>
							<li><a href="#tab_1_2" data-toggle="tab" hidden="true">Unassign</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_1_1">
									<select class="form-control select2me" id="estimationrule" name="estimationrule">
										<option value="">Select Estimation Rule</option>
										
									</select>
								<hr />
								<div class="form-group">
									<div class="mt-radio-inline">
										<!-- <label class="mt-radio"> <input type="radio" name="optionsRadios" id="Consumer" value="Consumer" onchange="hidefeeddt(this)" checked> Consumer <span></span></label>
										 --><label class="mt-radio"> <input type="radio" name="optionsRadios" id="DT" value="DT" onchange="hideconfeed(this)" > DT <span></span></label> 
										   <label class="mt-radio"> <input type="radio" name="optionsRadios" id="feeder" value="Feeder" onchange="hidecondt(this)"> Feeder <span></span></label>
										   <label class="mt-radio"> <input type="radio" name="optionsRadios" id="boundary" value="Boundary" onchange="hideconboundary(this)"> Boundary <span></span></label>
									                                                                                                             
									</div>
								</div>


								<div id="showConsumer" style="display: none">
									<div class="row" style="margin-left: -1px;">
											<div class="col-md-3">
													<div class="form-group">
														<!-- <label class="control-label">Consumer Category:</label> -->
															<select class="form-control select2me"
																id="consumerCatgry" name="consumerCatgry">
																<option value="">Select Consumer Category</option>
															</select>
													</div>
											 </div>
											 <div class="col-md-3">
													<div class="form-group">
														<input type="text" id="acno"
																class="form-control placeholder-no-fix"
																placeholder="Enter Account No." name="acno"
																maxlength="12"></input>
													</div>
											</div>
											<div class="col-md-3">
													<div class="form-group">
															<!-- <label class="control-label">K No</label>  
															<span style="color: red" class="required">*</span> --> 
															<input type="text" id="kno"
																class="form-control placeholder-no-fix"
																placeholder="Enter K No." name="kno" maxlength="12"></input>
													</div>
											</div>
											<div class="col-md-3">
													<div class="form-group">
															<!-- <label class="control-label">Meter Sr No</label> <span
																style="color: red" class="required">*</span> -->
															 <input type="text" id="mtrno"
																class="form-control placeholder-no-fix"
																placeholder="Enter Meter Sr No." name="mtrno"
																maxlength="12"></input>
													</div>
												</div>
									</div>
									<button type="button" id="viewConsumerData"
										style="margin-left: 480px;" onclick="getConsumerData()"
										name="viewConsumerData" class="btn yellow">
										<b>View</b>
									</button>
									<br />
									<hr />
									<button type="button" id="assignConsumerData"  onclick="assignData()" style="margin-left: 475px; display: none" class="btn green"><b>Assign</b></button>
									<table class="table table-striped table-hover table-bordered"
										id="sample_1" hidden="true">
										<thead>
											<tr>
												<th>Select</th>
												<th>Circle</th>
												<th>Division</th>
												<th>Sub Division</th>
												<th>Account No</th>
												<th>K No</th>
												<th>Customer Name</th>
												<th>Category</th>
												<th>Meter Sr. No</th>
											</tr>
										</thead>
										<tbody id="updateMaster">

										</tbody>
									</table>
								</div>

								<div id="showDT" >
									<div class="row" style="margin-left: -1px;">
										<div class="col-md-3">
												<div class="form-group">
															<!-- <label class="control-label">DT Code</label> -->
															 <input type="text" id="asgdtcode"
																class="form-control placeholder-no-fix"
																placeholder="Enter DT Code." name="asgdtcode"
																maxlength="12"></input>
												</div>
										</div>
										<!-- <div class="col-md-3">
												<div class="form-group">
													  <label>Cross DT : </label>
														<div class="make-switch" data-on="success"
														data-off="warning" style="position:relative; left: 11%;">
														<input type="checkbox" id="asg_dt_radio" name="asg_dt_radio" checked="checked" class="toggle" />
														</div>
												</div>
										</div> -->
										<div class="col-md-3">
														<div class="form-group">
															<!-- <label class="control-label">Meter Sr No</label> <span
																style="color: red" class="required">*</span>  -->
															<input type="text" id="asgdtmtrno"
																class="form-control placeholder-no-fix"
																placeholder="Enter Meter Sr No." name="asgdtmtrno"
																maxlength="12"></input>
														</div>
										</div>
									</div>
									<button type="button" id="viewDTData" style="margin-left: 480px;" onclick="getDTData()" name="viewDTData" class="btn yellow"> <b>View</b></button>
									<br /><hr />
									<button type="button" id="AssignDTData" style="margin-left: 475px; display: none" class="btn green" onclick="assignData();"><b>Assign</b> </button>
									<table class="table table-striped table-hover table-bordered"
										id="sample_2">
										<thead>
											<tr>
												<th><input id='dtcheck' name='dtcheck' type='checkbox' class='checkboxes'>Select</th>
												<th>Circle</th>
												<th>Division</th>
												<th>Sub Division</th>
												<th>Town Code</th>
												<!-- <th>Cross DT</th> -->
												<th>DT Code</th>
												<th>Meter Sr. No</th>
											</tr>
										</thead>
										<tbody id="updateMaster2">
										</tbody>
									</table>
								</div>

								<div id="showfeeder" style="display: none">
									<div class="row" style="margin-left: -1px;">
										<div class="col-md-3">
												<div class="form-group">
															<!-- <label class="control-label">Feeder Code</label>  -->
														<input type="text" id="asgfdcode"
															class="form-control placeholder-no-fix"
															placeholder="Enter Feeder Code." name="asgfdcode"
															maxlength="12"></input>
												</div>
										</div>
										<!-- <div class="col-md-3">
												<div class="form-group">
													  <label>Boundary Feeder : </label>
														<div class="make-switch" data-on="success"
														data-off="warning" style="position:relative; left: 11%;">
														<input type="checkbox" id="asg_fdr_radio" name="asg_fdr_radio" checked="checked" class="toggle" />
														</div>
												</div>
										</div> -->
										<div class="col-md-3">
														<div class="form-group">
															<!-- <label class="control-label">Meter Sr No</label> <span
																style="color: red" class="required">*</span>  -->
															<input
																type="text" id="asgfdrmtrno"
																class="form-control placeholder-no-fix"
																placeholder="Enter Meter Sr No." name="asgfdrmtrno"
																maxlength="12"></input>
														</div>
										</div>
									</div>
									<button type="button" id="viewFeederData" style="margin-left: 480px;" onclick="getfeederData()" name="viewFeederData" class="btn yellow">
										<b>View</b>
									</button>
									<br />
									<hr />
									<button type="button" id="assignFeederData"
										style="margin-left: 475px; display: none" onclick="assignData()" class="btn green">
										<b>Assign</b>
									</button>
									<table class="table table-striped table-hover table-bordered"
										id="sample_3">
										<thead>
											<tr>
												<th><input id='fdrcheck' name='fdrcheck' type='checkbox' class='checkboxes2'>Select</th>
												<th>Circle</th>
												<th>Division</th>
												<th>Sub Division</th>
												<th>Town Code</th>
												<th>Feeder Name</th>
												<th>Boundary Feeder</th>
												<th>Feeder Code</th>
												<th>Meter Sr. No</th>
											</tr>
										</thead>
										<tbody id="updateMaster3">

										</tbody>
									</table>
								</div>
								<div id="showBoundary" style="display: none">
									<div class="row" style="margin-left: -1px;">
										<div class="col-md-3">
												<div class="form-group">
															<!-- <label class="control-label">Feeder Code</label>  -->
														<input type="text" id="asgbdcode"
															class="form-control placeholder-no-fix"
															placeholder="Enter Boundary Code." name="asgbdcode"
															maxlength="12"></input>
												</div>
										</div>
										<!-- <div class="col-md-3">
												<div class="form-group">
													  <label>Boundary Feeder : </label>
														<div class="make-switch" data-on="success"
														data-off="warning" style="position:relative; left: 11%;">
														<input type="checkbox" id="asg_fdr_radio" name="asg_fdr_radio" checked="checked" class="toggle" />
														</div>
												</div>
										</div> -->
										<div class="col-md-3">
														<div class="form-group">
															<!-- <label class="control-label">Meter Sr No</label> <span
																style="color: red" class="required">*</span>  -->
															<input
																type="text" id="asgbdrmtrno"
																class="form-control placeholder-no-fix"
																placeholder="Enter Meter Sr No." name="asgbdrmtrno"
																maxlength="12"></input>
														</div>
										</div>
									</div>
									<button type="button" id="viewFeederData" style="margin-left: 480px;" onclick="getfeederData()" name="viewFeederData" class="btn yellow">
										<b>View</b>
									</button>
									<br />
									<hr />
									<button type="button" id="assignBoundaryData"
										style="margin-left: 475px; display: none" onclick="assignData()" class="btn green">
										<b>Assign</b>
									</button>
									<table class="table table-striped table-hover table-bordered"
										id="sample_boundary">
										<thead>
											<tr>
												<th><input id='fdrcheck' name='fdrcheck' type='checkbox' class='checkboxes2'>Select</th>
												<th>Circle</th>
												<th>Division</th>
												<th>Sub Division</th>
												<th>Town Code</th>
												<th>Boundary Name</th>
												<th>Boundary Feeder</th>
												<th>Boundary Code</th>
												<th>Meter Sr. No</th>
											</tr>
										</thead>
										<tbody id="updateMasterboundary">

										</tbody>
									</table>
								</div>
							</div>
							
							
							<!-- New Tab -->
							
							<div class="tab-pane" id="tab_1_2">
									<select class="form-control select2me" id="unestimationrule" name="unestimationrule">
										<option value="">Select Estimation Rule</option>
									</select>
								<hr />
								<div class="form-group">
									<div class="mt-radio-inline">
										<!-- <label class="mt-radio"> <input type="radio" name="uoptionsRadios" id="uConsumer" value="Consumer" onchange="uhidefeeddt(this)" checked> Consumer <span></span></label>
										 --><label class="mt-radio"> <input type="radio" name="uoptionsRadios" id="uDT" value="DT" onchange="uhideconfeed(this)" checked="checked"> DT <span></span></label> 
										<label class="mt-radio mt-radio-disabled"> <input type="radio" name="uoptionsRadios" id="ufeeder" value="Feeder" onchange="uhidecondt(this)"> Feeder <span></span></label>
										<label class="mt-radio mt-radio-disabled"> <input type="radio" name="uoptionsRadios" id="boundary" value="Boundary" onchange="uhideconboundary(this)"> Boundary <span></span></label>
									</div>
								</div>


								<div id="ushowConsumer" style="display: none">
									<div class="row" style="margin-left: -1px;">
										<div class="col-md-3">
														<div class="form-group">
															<select class="form-control select2me"
																id="unconsumerCatgry" name="unconsumerCatgry">
																<option value="">Select Consumer Category</option>
																
															</select>
														</div>
										</div>
										<div class="col-md-3">
														<div class="form-group">
															<!-- <label class="control-label">Account No</label> <span
																style="color: red" class="required">*</span>  -->
																<input
																type="text" id="uacno"
																class="form-control placeholder-no-fix"
																placeholder="Enter Account No." name="uacno"
																maxlength="12"></input>
														</div>
										</div>
										<div class="col-md-3">
														<div class="form-group">
															<!-- <label class="control-label">K No</label> <span
																style="color: red" class="required">*</span>  -->
																<input
																type="text" id="ukno"
																class="form-control placeholder-no-fix"
																placeholder="Enter K No." name="ukno" maxlength="12"></input>
														</div>
										</div>
										<div class="col-md-3">
														<div class="form-group">
															<!-- <label class="control-label">Meter Sr No</label> <span
																style="color: red" class="required">*</span> -->
																 <input
																type="text" id="umtrcode"
																class="form-control placeholder-no-fix"
																placeholder="Enter Meter Sr No." name="umtrcode"
																maxlength="12"></input>
														</div>
										</div>
									</div>
									<button type="button" id="viewasgConsumerData"
										style="margin-left: 480px;" onclick="getAssignConsumerData()"
										name="viewasgConsumerData" class="btn yellow">
										<b>View</b>
									</button>
									<br />
									<hr />
									<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
									<button type="button" id="unassignConsumerData" onclick="UnassignData()"
										style="margin-left: 465px; display: none" class="btn red">
										<b>Unassign</b>
									</button>
									<table class="table table-striped table-hover table-bordered"
										id="sample_4" style="display: none">
										<thead>
											<tr>
												<th>Select</th>
												<th>Circle</th>
												<th>Division</th>
												<th>Sub Division</th>
												<th>Account No</th>
												<th>K No</th>
												<th>Customer Name</th>
												<th>Category</th>
												<th>Meter Sr. No</th>
											</tr>
										</thead>
										<tbody id="updateMaster4">

										</tbody>
									</table>
								</div>
								<div id="ushowDT" >
									<div class="row" style="margin-left: -1px;">
									      <div class="col-md-3">
												<div class="form-group">
															<!-- <label class="control-label">DT Code</label>  -->
															<input
																type="text" id="udtcode"
																class="form-control placeholder-no-fix"
																placeholder="Enter DT Code." name="udtcode"
																maxlength="12"></input>
												</div>
											</div>
											<!-- <div class="col-md-3">
												<div class="form-group">
													  <label>Cross DT : </label>
														<div class="make-switch" data-on="success"
														data-off="warning" style="position:relative; left: 11%;">
														<input type="checkbox" id="unasg_dt_radio" name="unasg_dt_radio" checked="checked" class="toggle" />
														</div>
												</div>
											</div> -->
											<div class="col-md-3">
														<div class="form-group">
															<!-- <label class="control-label">Meter Sr No</label> <span
																style="color: red" class="required">*</span>  -->
																<input
																type="text" id="umtrcode"
																class="form-control placeholder-no-fix"
																placeholder="Enter Meter Sr No." name="umtrcode"
																maxlength="12"></input>
														</div>
											</div>
									</div>
									<button type="button" id="getAssignDTData"
										style="margin-left: 480px;" onclick="getAssignDTData()"
										name="getAssignDTData" class="btn yellow">
										<b>View</b>
									</button>
									<br />
									<hr />
									<button type="button" id="UnAssignDTData"
										style="margin-left: 465px; display:none;" onclick="UnassignData()" class="btn red">
										<b>Unassign</b>
									</button>
									<table class="table table-striped table-hover table-bordered"
										id="sample_5">
										<thead>
											<tr>
												<th><input id='undtcheck' name='undtcheck' type='checkbox' class='checkboxes1'>Select</th>
												<th>Circle</th>
												<th>Division</th>
												<th>Sub Division</th>
												<th>Town Code</th>
												<!-- <th>Cross DT</th> -->
												<th>DT Code</th>
												<th>Meter Sr. No</th>
											</tr>
										</thead>
										<tbody id="updateMaster5">
										</tbody>
									</table>
								</div>

								<div id="ushowfeeder" style="display: none">
									<div class="row" style="margin-left: -1px;">
									    <div class="col-md-3">
												<div class="form-group">
															<!-- <label class="control-label">Feeder Code</label>  -->
															<input
																type="text" id="ufdcode"
																class="form-control placeholder-no-fix"
																placeholder="Enter Feeder Code." name="ufdcode"
																maxlength="12"></input>
												</div>
											</div>
											<!-- <div class="col-md-3">
												<div class="form-group">
													  <label>Boundary Feeder : </label>
														<div class="make-switch" data-on="success"
														data-off="warning" style="position:relative; left: 11%;">
														<input type="checkbox" id="unasg_fdr_radio" name="unasg_fdr_radio" checked="checked" class="toggle" />
														</div>
												</div>
											</div> -->
										
											<div class="col-md-3">
														<div class="form-group">
															<!-- <label class="control-label">Meter Sr No</label> <span
																style="color: red" class="required">*</span>  -->
																<input
																type="text" id="unasgfdrmtrno"
																class="form-control placeholder-no-fix"
																placeholder="Enter Meter Sr No." name="unasgfdrmtrno"
																maxlength="12"></input>
														</div>
											</div>
									</div>
									<button type="button" id="viewFeeder"
										style="margin-left: 480px;" onclick="getAssignFeederData()"
										name="viewFeeder" class="btn yellow">
										<b>View</b>
									</button>
									<br />
									<hr />
									<button type="button" id="UnassignFeederData" onclick="UnassignData()"
										style="margin-left: 465px; display: none" class="btn red">
										<b>Unassign</b>
									</button>
									<table class="table table-striped table-hover table-bordered"
										id="sample_6">
										<thead>
											<tr>
												<th><input id='unfdrcheck' name='unfdrcheck' type='checkbox' class='checkboxes3'>Select</th>
												<th>Circle</th>
												<th>Division</th>
												<th>Sub Division</th>
												<th>Town Code</th>
												<th>Feeder Name</th>
												<th>Boundary Feeder</th>
												<th>Feeder Code</th>
												<th>Meter Sr. No</th>
											</tr>
										</thead>
										<tbody id="updateMaster6">

										</tbody>
									</table>
								</div>
								
								<div id="ushowBoundary" style="display: none">
									<div class="row" style="margin-left: -1px;">
									    <div class="col-md-3">
												<div class="form-group">
															<!-- <label class="control-label">Feeder Code</label>  -->
															<input
																type="text" id="ubdcode"
																class="form-control placeholder-no-fix"
																placeholder="Enter Boundary Code" name="ubdcode"
																maxlength="12"></input>
												</div>
											</div>
											<!-- <div class="col-md-3">
												<div class="form-group">
													  <label>Boundary Feeder : </label>
														<div class="make-switch" data-on="success"
														data-off="warning" style="position:relative; left: 11%;">
														<input type="checkbox" id="unasg_fdr_radio" name="unasg_fdr_radio" checked="checked" class="toggle" />
														</div>
												</div>
											</div> -->
										
											<div class="col-md-3">
														<div class="form-group">
															<!-- <label class="control-label">Meter Sr No</label> <span
																style="color: red" class="required">*</span>  -->
																<input
																type="text" id="unasgbdrmtrno"
																class="form-control placeholder-no-fix"
																placeholder="Enter Meter Sr No." name="unasgbdrmtrno"
																maxlength="12"></input>
														</div>
											</div>
									</div>
									<button type="button" id="viewFeeder"
										style="margin-left: 480px;" onclick="getAssignFeederData()"
										name="viewFeeder" class="btn yellow">
										<b>View</b>
									</button>
									<br />
									<hr />
									<button type="button" id="UnassignBoundaryData" onclick="UnassignData()"
										style="margin-left: 465px; display: none" class="btn red">
										<b>Unassign</b>
									</button>
									<table class="table table-striped table-hover table-bordered"
										id="sample_unBoundary">
										<thead>
											<tr>
												<th><input id='unfdrcheck' name='unfdrcheck' type='checkbox' class='checkboxes3'>Select</th>
												<th>Circle</th>
												<th>Division</th>
												<th>Sub Division</th>
												<th>Town Code</th>
												<th>Boundary Name</th>
												<th>Boundary Feeder</th>
												<th>Boundary Code</th>
												<th>Meter Sr. No</th>
											</tr>
										</thead>
										<tbody id="updateMasterUnBoundary">

										</tbody>
									</table>
								</div>
							</div>
							
							
							<!--  end new tab-->	
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>



<script>

/* function showCircle(zone) {
	$
			.ajax({
				url : './showCircleMDAS' + '/' + zone,
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				success : function(response) {
					var html = '';
					html += "<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control' type='text'><option value=''>Select Circle</option><option value='ALL'>ALL</option>";
					for (var i = 0; i < response.length; i++) {
						html += "<option  value='"+response[i]+"'>"
								+ response[i] + "</option>";

					}
					html += "</select><span></span>";
					$("#circleTd").html(html);
					$('#circle').select2();

				}
			});
}


function showDivision(circle) {
	var zone = $('#zone').val();
	$
			.ajax({
				url : './showDivisionMDAS' + '/' + zone + '/' + circle,
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				success : function(response) {
					var html = '';
					html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control' type='text'><option value=''>Select Division</option><option value='ALL'>ALL</option>";
					
					for (var i = 0; i < response.length; i++) {
						html += "<option  value='"+response[i]+"'>"
								+ response[i] + "</option>";
					}
					html += "</select><span></span>";
					$("#divisionTd").html(html);
					$('#division').select2();
				}
			});
}




function showSubdivByDiv(division) {
	var zone = $('#zone').val();
	var circle = $('#circle').val();
	$('#sdoCode').empty();
	$('#sdoCode').find('option').remove();
	$('#sdoCode').append($('<option>', {
		value : "",
		text : "Select Sub-Division"
	}));
	$('#sdoCode').append($('<option>', {
		value : "ALL",
		text : "All"
	}));
	$.ajax({
		url : './showSubdivByDivMDAS' + '/' + zone + '/' + circle + '/'
				+ division,
		type : 'GET',
		dataType : 'json',
		asynch : false,
		cache : false,
		success : function(response1) {
			for (var i = 0; i < response1.length; i++) {
				$('#sdoCode').append($('<option>', {
					value : response1[i],
					text : response1[i]
				}));
			}
		}
	});
} */





/* function showCircle(zone) {
	$
			.ajax({
				url : './getcirclebyzone',
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data : {
					zone : zone
				},
				success : function(response) {
					var html = '';
				html += "<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control' type='text'><option value=''>Select Circle</option><option value='ALL'>ALL</option>";
					for (var i = 0; i < response.length; i++) {
						html += "<option  value='"+response[i]+"'>"
								+ response[i] + "</option>";

					}
					html += "</select><span></span>";
					$("#circleTd").html(html);
					$('#circle').select2();

				}
			});
}


function showDivision(circle) {
	$("#circledivsub").show();
	var circle = $("#circle").val();
	$
			.ajax({
				url : './getdivisionbycircle',
				type : "GET",
				dataType : 'json',
				data : {
					circle : circle
				},
				success : function(response) {
					var html = "";
					html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control' type='text'><option value=''>Select Division</option><option value='ALL'>ALL</option>";
					for (var i = 0; i < response.length; i++) {					
					html += "<option  value='"+response[i]+"'>"
								+ response[i] + "</option>"; 
					}
				html += "</select><span></span>";
					$("#divisionTd").html(html);
					$('#division').select2(); 
				}
			});
}
function showSubdivByDiv(division) {
	var circle = $('#circle').val();
	var division = $('#division').val();
	$
			.ajax({
				url : './getSubdivByDiv',
				type : 'GET',
				dataType : 'json',
				data : {
					circle : circle,
					division : division
				},
				asynch : false,
				cache : false,
				success : function(response1) {
					var html = '';
					html += "<select id='sdoCode' name='sdoCode'  class='form-control' type='text'><option value=''>Select Sub-Division</option><option value='ALL'>ALL</option>";
					for (var i = 0; i < response1.length; i++) {
						html += "<option  value='"+response1[i]+"'>"
								+ response1[i] + "</option>";
					}
					html += "</select><span></span>";
					$("#subdivTd").html(html);
					$('#sdoCode').select2();
				}
			});
} */



/* function showCircle(zone)
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
	    		html+="<label class='control-label'>Circle:</label><select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control input-medium' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
				for( var i=0;i<response.length;i++)
				{
					html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
				}
				html+="</select><span></span>";
				$("#circleTd").html(html);
				$('#circle').select2();
	    	}
		});
} */

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
                html += "<select id='LFtown' name='LFtown'  class='form-control  input-medium'  onchange='showResultsbasedOntownCode(this.value)' type='text'><option value=''>Select Town</option><option value='%'>ALL</option>";
               
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


 function showDivision(circle) {
	 var zone = $('#zone').val();
	
		$.ajax({
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
						html += "<label class='control-label'>Division:</label><select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select Division</option><option value='%'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option  value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#divisionTd").html(html);
						$('#division').select2();
					}
				});
	}
 function showSubdivByDiv(division) {
		
		var zone = $('#zone').val();
		var circle = $('#circle').val();
		$.ajax({
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
						html += "<label class='control-label'>Sub-Division:</label><select id='sdoCode' name='sdoCode'  onchange='showTownsBySubDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
						for (var i = 0; i < response1.length; i++) {
							//var response=response1[i];
							html += "<option  value='"+response1[i]+"'>"
									+ response1[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#subdivTd").html(html);
						$('#sdoCode').select2();
					}
				});
	}


 function showTownsBySubDiv(subdivIdName)
	{

	    var zone = $('#zone').val();
		var circle = $('#circle').val();
	    var division = $('#division').val();
		var subdivision=subdivIdName;
		
		$.ajax({
			type : 'GET',
			
			url : "./getTownsBaseOnSubdivision",
			data:{
				zone : zone,
				circle : circle,
				division : division,
				subdivision : subdivision
				},
			async : false,
			cache : false,
			success : function(response)
			{
				var html="";
				if(response!=null)
					{
					html+="<option value=''>Select Town</option><option value='%'>ALL</option>"; 
					for(var i=0;i<response.length;i++)
						{
						html+="<option value='"+response[i][0]+"'>"+ response[i][0]+"-" +response[i][1]+"</option>"; 
					
					$("#town").empty();
					$("#town").append(html);
					$("#town").select2();
					
					}
					}
				
			}
		});
	}



</script>
<style>
#s2id_estimationrule, #s2id_unestimationrule  {
width: 236px;
}

</style>