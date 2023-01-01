<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {   
	  
	  $("#billMonth").val(getPresentMonthDate('${selectedMonth}'));
   	    	     App.init();
   	    	  TableManaged.init();
   	    	  TableEditable.init();
	   	    	  FormComponents.init();
	   	    	  if('${sealPdf}'=='yes')
	   	    		  {	   	    		 
	   	    		    $('#sealPdfData').attr('hidden',false);
	   	    		   $('#sealPdf').tableExport({fileName:'${docName}',type:'pdf',tableId:'sealPdf',divId:'sealPdfData',condition:'custom'});	
                        $('#sealPdfData').attr('hidden',true);
	   	    		  }
	   	    	  if('${mrwiseSealList.size()}'>0)
	   	    		  {
                          $("#sealSummaryDivId").show();
                          loadSearchAndFilter('table_4');
	   	    		  }
	   	    	 if('${mrwiseSealReturnList.size()}'>0)
  	    		  {
                     $("#sealReturnDivId").show();
  	    		  }
	   	    	
	   	    	  $('#selectIssuedDate,#selectReceiveDate,#selectReturnDate,#selectReturnDate3').val(moment(new Date()).format("MM/DD/YYYY"));
	   	    	   $('#seal-Manager').addClass('start active ,selected');
	   	    	 $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	   	    	getMaxCardNo1(1);
   	      });
  
  
  function onEnter(form1,sealFrom,sealTo,total)
  {	  
	  var sealNum=$('#'+sealFrom).val();	
	  var sealNum2=$('#'+sealTo).val();	
	  if(sealNum.trim().length!=sealNum2.trim().length)
		  {
		  bootbox.alert('seal From and seal To should be same length');
		  $('#'+sealTo).val('');	
		  return false;
		  }
	  
	  var SealFromStr=sealNum.replace(/[0-9]/g, '');
	  var SealToStr=sealNum2.replace(/[0-9]/g, '');
	  if(SealFromStr!=SealToStr)
		  {
		  bootbox.alert('Entered seal From and seal To alphabets are not matching');
		  $('#'+sealTo).val('');
		  return false;
		  }
	  
	  var SealFrom=sealNum.replace(/[^0-9]/g, '');
	  var SealTo=sealNum2.replace(/[^0-9]/g, '');
	  
		  if(SealTo<SealFrom)
		  {
		  bootbox.alert('seal To should be greater than seal from');
		  $('#'+sealTo).val('');
		  return false;
		  }
	  
	  $('#'+total).val((SealTo-SealFrom)+1);
	  if(form1.name=='sealOutWard')
		  {
		  getCardSlNo(sealTo,'SealCardNo',1);		   
		  }
	  if(form1.name=='multipleSeal')
	  {
	  getCardSlNo(sealTo,'cardSealNo3',1);		   
	  }
	  
	  return false;	
  }
  
  function getCardSlNo(id,toWhich,incVal)
  {  
	  if($('#'+id).val()=='')
	  {
	  bootbox.alert('seal To should be greater than seal from');	  
	  return false;
	  }
	  
	  $.ajax({
	    	url:'./getSealCardNum',
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(max)
	    	{
	    		$('#'+toWhich).val(max+incVal);
	    	}
	    	
	    })
  }
  
 /*  function sealInwarValidation(form1)
  { 
	      if(form1.selectReceiveDate.value.trim()=='')
		  {
		  bootbox.alert(' please select seal recieve date');
		  return false
		  }
		  if(form1.sealNoId.value.trim()=='')
		  {
		  bootbox.alert(' please enter seal from');
		  return false
		  }
		  if(form1.sealTo.value.trim()=='' || form1.sealTo.value.trim()=='0')
		  {
		  bootbox.alert(' please enter seal to');
		  return false
		  }
		  
  } */
  
  function sealOutwarValidation(form1,action,url)
  { 
	     
	      if(form1.selectIssuedDate.value.trim()=='')
		  {
		  bootbox.alert(' please select issue date');
		  return false
		  }
		  if(form1.dvision.value.trim()=='0')
		  {
		  bootbox.alert(' please select division');
		  return false
		  }
		  if(form1.subDiv.value.trim()=='')
		  {
		  bootbox.alert(' please enter sdoCode');
		  return false
		  }
		  
		      if(action=='update')
			  {
			  if(form1.sealNoId1.value.trim()=='')
			  {
			  bootbox.alert(' please enter seal no From');
			  return false;
			  }
			  
			  if(form1.sealTo1Id.value.trim()=='')
			  {
			  bootbox.alert(' please enter seal no To');
			  return false;
			  }
			  
			  }
		  
			  if(action !='update')
			  {
				  //alert('hoi'+action+" "+form1.sealNoId1.value);
				  //alert(form1.sealNoId1.value.trim());
				  //alert(document.getElementById('sealTo1Id').value);
			   if(form1.sealNoId1.value.trim() !='' || document.getElementById('sealTo1Id').value !='')
			   {
			    bootbox.alert(' please do not enter seal no From/To for preview');
			    return false
			  } 
			   
			   var sealCard1=form1.SealCardNo.value;
			   
			   $('#SealCardNo1').val(sealCard1);
			   $('#SealCardNo').val(0);
			   
			  }
		  
		  if(form1.mrnameId1.value.trim()=='0')
		  {
		  bootbox.alert(' please select mrname');
		  return false
		  }
		  
		$('#'+form1.id).attr('action',url).submit();
		  
  }
  
  function singleValidation(form1)
  {
	  if($('#sealNo2Id').val().trim()=='')
	  {
	  bootbox.alert(' please enter seal No.');
	  return false
	  }
	  
	 if($('#selectReturnDate').val().trim()=='')
		  {
		  bootbox.alert(' please enter issued by');
		  return false
		  }
	  
  }
  function MultipleValidation(form1)
  {
	  if(form1.sealNo3.value.trim()=='')
	  {
	  bootbox.alert(' please enter seal No. From');
	  return false
	  }
	  
	  if(form1.sealTo3.value.trim()=='')
	  {
	  bootbox.alert(' please enter seal No. To');
	  return false
	  }
	  
	 if(form1.selectReturnDate3.value.trim()=='')
		  {
		  bootbox.alert(' please select recieved date');
		  return false
		  }
  }
  
   </script>
   
   
 <style>
 .modal-body {
    position: relative;
    overflow-y: auto;
    max-height: 600px;
    max-width: 600px;
    padding: 15px;
}

</style>
<script type="text/javascript">

$('.modal').on('show', function () {

    $(this).find('.modal-body').css({width:'auto',
                               height:'auto', 
                              'max-height':'100%'});
});



function printPreview(form)
{

	$("#printDate").html(form.selectIssuedDate.value);
	$("#printName").html(form.mrname.value);
	$("#printDivision").html(form.dvision.value);
	$("#printCardNo").html(form.SealCardNo.value);
	var sealFrom = form.sealNoId1.value;
	var sealTo = form.sealTo1Id.value;
	var fromNum = parseInt(sealFrom.substr(sealFrom.length-3));
	var toNum = parseInt(sealTo.substr(sealTo.length-3));
	var html = "";
	for(var i =fromNum;i<=toNum;i++ )
		{
		html = html + "<tr>"
		var  f = i.toString();
		
		if(f.length == 1)
		{
		 
		var sealno = sealFrom.substr(0,sealFrom.length-1);
		sealno = sealno+i;
		
		html = html + "<td>"+sealno+"</td>";
		}
		else if(f.length == 2)
			{
			 
			var sealno = sealFrom.substr(0,sealFrom.length-2);
			sealno = sealno+i;
			
			html = html + "<td>"+sealno+"</td>";
			}
		 else {
			var sealno = sealFrom.substr(0,sealFrom.length-3);
			sealno = sealno+i;
			
			html = html + "<td>"+sealno+"</td>";
		} 
		
		html = html + "<td></td><td></td><td></td></tr>";
		
		}
	$("#printPreviewData").html(html);
	
	$('#sealPdfDataPrint').attr('hidden',false);
	 $('#sealPdfPrint').tableExport({fileName:'sealManager1',type:'pdf',tableId:'sealPdfPrint',divId:'sealPdfDataPrint',condition:'custom'});	
     $('#sealPdfDataPrint').attr('hidden',true);
	return false;
	}
	
	
/* function displaySearchAndFilter()
{  
	$('#table_3').dataTable().fnDestroy(); 
	  $('#table_3').dataTable(
			  {
				 
				               "aLengthMenu": [
				                    [10, 50, 100, -1],
				                    [10, 50, 100, "All"] // change per page values here
				                ]
			  });
	  jQuery('#table_3_wrapper .dataTables_filter input').addClass("form-control input-small"); // modify table search input
    jQuery('#table_3_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
    jQuery('#table_3_wrapper .dataTables_length select').select2(); // initialize select2 dropdown
	 
} */
	function displaySealIssued(mrName,billMonth)
	{
		
		$.ajax(
				{
					type : "GET",
					url : "./displayMrWiseSeals/" + mrName+"/"+billMonth,
					dataType : "json",
					cache:false,
					async: false,
				    success : function(response)
			  		  {
				    	var html = "";
				    	
				    	for(var j=0;j<response.length;j++)
			    		{		 
				    		var sealNo=response[j];
				    		html = html + "<tr><td>"+sealNo[0]+"</td>"+
				    				"<td>"+sealNo[1]+"</td>"+
				    				"<td>"+sealNo[2]+"</td>"+
				    				"<td>"+sealNo[3]+"</td>"+
				    				"<td>"+sealNo[4]+"</td>"+
				    				"<td>"+sealNo[5]+"</td>"+
				    				"</tr>";
			    		}
				    	var str = mrName;
				    	var mrNameVal = str.replace(/\s+/g, "");
				    	$("#mrwiseSealNoData").html(html);
				    	$('#excelPrintId').html("<a href=# id=excelExport onclick=tableToExcel('sample_editable_1','SEAL_"+mrNameVal+"')/>");
				    	document.getElementById("excelExport").click();
				    	/* $('#mrwiseSealNoPdfdata').attr('hidden',false);
				    	 $('#sealNosPdfPrint').tableExport({fileName:'sealManager1',type:'pdf',tableId:'sealNosPdfPrint',divId:'mrwiseSealNoPdfdata',condition:'custom'});	
				         $('#mrwiseSealNoPdfdata').attr('hidden',true); */
				         return false;
			    	}
				}		
		       );
		
	}
	
function displaySealUsed(mrName,billMonth)
{
	
	$.ajax(
			{
				type : "GET",
				url : "./displayMrWiseSealsUsed/" + mrName+"/"+billMonth,
				dataType : "json",
				cache:false,
				async: false,
			    success : function(response)
		  		  {
			    	var html = "";
			    	
			    	for(var j=0;j<response.length;j++)
		    		{		 
			    		var sealNo=response[j];
			    		html = html + "<tr><td>"+sealNo[0]+"</td>"+
			    				"<td>"+sealNo[1]+"</td>"+
			    				"<td>"+sealNo[2]+"</td>"+
			    				"<td>"+sealNo[3]+"</td>"+
			    				"<td>"+sealNo[4]+"</td>"+
			    				"<td>"+sealNo[5]+"</td>"+
			    				"</tr>";
		    		}
			    	var str = mrName;
			    	var mrNameVal = str.replace(/\s+/g, "");
			    	$("#mrwiseSealNoData").html(html);
			    	$('#excelPrintId').html("<a href=# id=excelExport onclick=tableToExcel('sample_editable_1','SEAL_"+mrNameVal+"')/>");
			    	document.getElementById("excelExport").click();
			    	/* $('#mrwiseSealNoPdfdata').attr('hidden',false);
			    	 $('#sealNosPdfPrint').tableExport({fileName:'sealManager1',type:'pdf',tableId:'sealNosPdfPrint',divId:'mrwiseSealNoPdfdata',condition:'custom'});	
			         $('#mrwiseSealNoPdfdata').attr('hidden',true); */
			         return false;
		    	}
			}		
	       );
	
}
	
	
function displaySealDamaged(mrName,billMonth)
{
	
	$.ajax(
			{
				type : "GET",
				url : "./displayMrWiseSealsDamaged/" + mrName+"/"+billMonth,
				dataType : "json",
				cache:false,
				async: false,
			    success : function(response)
		  		  {
			    	var html = "";
			    	
			    	for(var j=0;j<response.length;j++)
		    		{		 
			    		var sealNo=response[j];
			    		html = html + "<tr><td>"+sealNo[0]+"</td>"+
			    				"<td>"+sealNo[1]+"</td>"+
			    				"<td>"+sealNo[2]+"</td>"+
			    				"<td>"+sealNo[3]+"</td>"+
			    				"<td>"+sealNo[4]+"</td>"+
			    				"<td>"+sealNo[5]+"</td>"+
			    				"</tr>";
		    		}
			    	var str = mrName;
			    	var mrNameVal = str.replace(/\s+/g, "");
			    	$("#mrwiseSealNoData").html(html);
			    	$('#excelPrintId').html("<a href=# id=excelExport onclick=tableToExcel('sample_editable_1','SEAL_"+mrNameVal+"')/>");
			    	document.getElementById("excelExport").click();
			    	/* $('#mrwiseSealNoPdfdata').attr('hidden',false);
			    	 $('#sealNosPdfPrint').tableExport({fileName:'sealManager1',type:'pdf',tableId:'sealNosPdfPrint',divId:'mrwiseSealNoPdfdata',condition:'custom'});	
			         $('#mrwiseSealNoPdfdata').attr('hidden',true); */
			         return false;
		    	}
			}		
	       );
	
}
	
	var noOfSeals=0;
	function displaySealReturned(rmrName)
	{
		$('#checkAll').prop("checked", false);
		$('#mrNameUpdate').val(rmrName);
		$.ajax(
				{
					type : "GET",
					url : "./displayMrWiseSealsReturn/" + rmrName,
					dataType : "json",
					cache:false,
					async: false,
				    success : function(response)
			  		  {
				    	noOfSeals=response.length;
				    	//$('#cardslno1').val('');
				    	var m=1;
				    	 var html2 = "<thead><tr><th class=table-checkbox><input id=checkAll onclick=allSelect("+response.length+"); type=checkbox class=group-checkable data-set=#sample_1 .checkboxes /></th><th>Seal Numbers</th></tr></thead><tbody>";
			    		 
			    	       for(var j=0;j<response.length;j++)
				    		{	
				    		     html2 +="<tr class=oddgradeX><td><input id=check"+m+"  onclick=getAllCheckedSealNo(this.id,this.value) type=checkbox class=checkboxes value="+response[j]+" /></td><td>"+response[j]+"</td></tr>";
				    		      m=m+1; 
				    		}
				    	html2+="</tbody>";	
					      $('#table_3').html(html2);
					      loadSearchAndFilter('table_3');
					      getMaxCardNo1(1);
			    	}
				}		
		       );
	}
	
	function displaySealReturnedPc(rmrName,condition)
	{
		$('#checkAll').prop("checked", false);
		$('#mrNameUpdate').val(rmrName);
		$("#mrName").val(rmrName);
		$.ajax(
				{
					type : "GET",
					url : "./displayMrWiseSealsReturnPc/" + rmrName+"/"+condition,
					dataType : "json",
					cache:false,
					async: false,
				    success : function(response)
			  		  {
				    	noOfSeals=response.length;
				    	//$('#cardslno1').val('');
				    	var m=1;
				    	 var html2 = "<thead><tr><th class=table-checkbox><input id=checkAll onclick=allSelect("+response.length+"); type=checkbox class=group-checkable data-set=#sample_1 .checkboxes /></th><th>Seal Numbers</th></tr></thead><tbody>";
			    		 
			    	       for(var j=0;j<response.length;j++)
				    		{	
				    		     html2 +="<tr class=oddgradeX><td><input id=check"+m+"  onclick=getAllCheckedSealNo(this.id,this.value) type=checkbox class=checkboxes value="+response[j]+" /></td><td>"+response[j]+"</td></tr>";
				    		      m=m+1; 
				    		}
				    	html2+="</tbody>";	
					      $('#table_3').html(html2);
					      loadSearchAndFilter('table_3');
					      getMaxCardNo1(1);
			    	}
				}		
		       );
	}
	
	
	
	var availableSealNo1 = [];
	function allSelect(sealCount)
	{
		if(document.getElementById('checkAll').checked) 
		{
			$('.checkboxes').prop("checked", true);
			getAllSealsChecked(sealCount);
		} else 
		{
			$('#cardslno1').val('');
			availableSealNo1.length=0;
			$('.checkboxes').prop("checked", false);
		}
	}
	
	
	function getAllSealsChecked(sealCount)
	{
		availableSealNo2.length=0;
		for(var i=1;i<=sealCount;i++)
		 {
			if( $('#check' + i).is(":checked")==true)
			{
				availableSealNo2.push( $('#check' + i).val()); 
				availableSealNo1.push( $('#check' + i).val()); 
			}
		/* else if( $('#check' + i).is(":checked")==false)
			{
				  for(var i = availableSealNo1.length; i--;) 
				  {
			          if(availableSealNo1[i] === $('#check' + i).val()) 
			          {
			        	  availableSealNo1.splice(i, 1);
			          }
			      }
			} */
		 }
		/* if(availableSealNo1.length>0)
		{
		   getMaxCardNo(1);
		} */
	}
	var availableSealNo2 = [];
	 function getAllCheckedSealNo(id,sealNos)
	 {
		 availableSealNo1.length=0;
		 if(document.getElementById('checkAll').checked)
			 {
			    $('#checkAll').prop("checked", false);
			    if( $('#' + id).is(":checked")==false)
				{
					  for(var i = availableSealNo2.length; i--;) 
					  {
				          if(availableSealNo2[i] === sealNos) 
				          {
				        	  availableSealNo2.splice(i, 1);
				          }
				      }
				}
			   
			 }
		 else
			 {
				 if( $('#' + id).is(":checked")==true)
					{
					   availableSealNo2.push(sealNos); 
					}
				else if( $('#' + id).is(":checked")==false)
					{
						  for(var i = availableSealNo2.length; i--;) 
						  {
					          if(availableSealNo2[i] === sealNos) 
					          {
					        	  availableSealNo2.splice(i, 1);
					          }
					      }
					}
			 }
		
		/* if(availableSealNo2.length>0)
			{
			   getMaxCardNo(1);
			}
		else
			{
			  $('#cardslno1').val('');
			} */
	 } 
	
	 /* function getAllCheckedSealNo(id,sealNos)
	 {
		 alert('llllll');
		 getMaxCardNo(1);
	 }
	  */


	  /* =============== Commented by Vijayalaxmi======================= */
	/* function getMaxCardNo(incVal)
	{
		if($('#cardslno1').val()!=null && $('#cardslno1').val()=='')
			{
			
			$.ajax({
		    	url:'./getSealCardNum',
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	success:function(max)
		    	{
		    		$('#cardslno1').val(max+incVal);
		    	}
		    	
		    });
			}
		
	} */
	
	  /* =============== Commented by Vijayalaxmi======================= */
	/* function getMaxCardNo1(incVal)
	{
		
		$.ajax({
		    	url:'./getSealCardNum1',
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	success:function(max)
		    	{
		    		
		    		$('#cardslno1').val(max+incVal);
		    		
		    	}
		    	
		    });
		
	} */

/* =============== Commented by Vijayalaxmi======================= */


	/* function updateCardSlno1()
	{
		
		if(availableSealNo1.length>0)
			{
			  availableSealNo2.length=0;
			}
		if(availableSealNo2.length>0)
			{
			
			$.ajax(
					{
							type : "GET",
							url : "./updateCardSlNo1/"+$('#cardslno1').val()+"/"+availableSealNo2+"/"+$('#mrName').val(),
							dataType: "text",
							cache:false,
							async:false,
							success:function(response)
							{
								if(response=='updated')
									{
									    $('#stack6').modal('toggle');
									} 
							}
					}
				);
			availableSealNo2.length=0;
			}
		else if(availableSealNo1.length>0)
		{
			
			 $.ajax(
						{
								type : "GET",
								url : "./updateCardSlNo1/"+$('#cardslno1').val()+"/"+availableSealNo1+"/"+$('#mrName').val(),
								dataType: "text",
								cache:false,
								async:false,
								success:function(response)
								{
									if(response=='updated')
										{
										    $('#stack6').modal('toggle');
										} 
								}
						}
					);
			 availableSealNo1.length=0;
			 $('#checkAll').prop("checked", false);
		} 
		 else
		 {
		     bootbox.alert('Please select the Seal Numbers to update.');
		     return false;
		 }
		window.location.href = "./showReturnSeals1";
		
	} */
	
	
/* =============== Commented by Vijayalaxmi======================= */
	
	/* function updateMultipleCardSlno1()
	{
		var checkboxes = document.getElementsByName('multipleCardSealCheck');
		var selected = [];
		for (var i=0; i<checkboxes.length; i++) {
		    if (checkboxes[i].checked) {
		        selected.push(checkboxes[i].value);
		    }
		}
		for(var j=0;j<selected.length;j++)
			{
			var str = selected[j].split("$");
			getMaxCardNo1(1);
			var cardsealNo1 = $("#cardslno1").val();
			
			$.ajax({
					type : "GET",
					url : "./updateMultipleCardSealNo1/"+str[0]+"/"+str[1]+"/"+cardsealNo1,
					cache : false,
					async : false,
					success : function(response){
						
					}
				});
			}
		
		window.location.href = "./showReturnSeals1";
	} */
	
</script>
<div class="page-content" >
<!-- BEGIN PAGE CONTENT-->

<c:if test = "${not empty result}">
			<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color:red" >${result}</span>
			</div>
			
	    </c:if>	
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box light-grey">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Seal Manager</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
								
							</div>
							<div id="excelPrintId" hidden="true">
								
							</div>
							
						</div>
						<div class="portlet-body">
							<div class="table-toolbar">
								
								<div class="btn-group">	
									<button id="sample_editable_1_new1" class="btn green"  data-toggle="modal" data-target="#stack1">
									Seal OutWard
									</button>
								</div>
								
							 <!-- 	<div class="btn-group">	
									<button id="sample_editable_1_new2" class="btn green"  data-toggle="modal" data-target="#stack2">
									Seal Inward
									</button>
								</div> -->
								
								<!-- <div class="btn-group">	
									<button id="sample_editable_1_new3" class="btn green"  data-toggle="modal" data-target="#stack3">
									Return Entry
									</button>
								</div>  -->
								
								<div class="btn-group">	
									<button id="sample_editable_1_new4" class="btn green"  onclick="window.location.href='./showBunches'">
									Show bunches
									</button>
								</div>
								
								<!-- <div class="btn-group">	
									<button id="sample_editable_1_new5" class="btn green"  data-toggle="modal" data-target="#stack4">
									Report
									</button>
								</div> -->
																
						</div>
					
					
					<c:if test="${value eq 'bunches'}">
					<table class="table table-striped table-bordered table-hover" id="sample_2">
								<thead>									
									<tr>
										<th>CardSlNo1</th>
										<th>No. of Seals</th>
								 </tr>
								</thead>
								<tbody>
									
									<c:forEach items="${allBunches}" var="element">
									 	<tr>									 		
										 	<td>${element[0]}</td>
										 	<td>${element[1]}</td>
										 	
									 	</tr>
									 </c:forEach>	
								</tbody>
							</table> 
				    </c:if>
				    <div id="sealPdfData" hidden="true">
				              <table id="sealPdf" border="1px" > <!--  -->
								<thead>									
									<tr>
										<th>Date:<fmt:formatDate pattern="dd/MM/YYYY" value="${pdfData[0].iDate}" /></th>
										<th>Name:${pdfData[0].mrname}</th>
										<th>Division:${pdfData[0].dvision}</th>
										<th>CardNo:${pdfData[0].cardSealNo}</th>
								 </tr>
								 <tr>
										<th>SEALNo</th>
										<th>ACCNo</th>
										<th>METERNo</th>
										<th>SDOCODE</th>
								 </tr>
								</thead>
								<tbody>
									
									<c:forEach items="${pdfData}" var="element">
									 	<tr>								 		
										 	<td>${element.sealNo}</td>
										 	<td>  </td>
										 	<td>  </td>
										 	<td>  </td>
									 	</tr>
									 </c:forEach>	
								</tbody>
							</table> 
							</div>
							
							<div id="sealPdfDataPrint" hidden="true">
				              <table id="sealPdfPrint" border="1px" > <!--  -->
								<thead>									
									<tr>
										<th>Date:<span id="printDate"></th>
										<th>Name:<span id="printName"></th>
										<th>Division:<span id="printDivision"></th>
										<th>CardNo:<span id="printCardNo"></th>
								 </tr>
								 <tr>
										<th>SEALNo</th>
										<th>ACCNo</th>
										<th>METERNo</th>
										<th>SDOCODE</th>
								 </tr>
								</thead>
								<tbody id="printPreviewData">
									
									
								</tbody>
							</table> 
							</div>
							
							<div id="mrwiseSealNoPdfdata" hidden="true">
				              <table id="sample_editable_1" border="1px" > 
								<thead>										
								 <tr>
										<th>SEAL NUMBER</th>
										<th>Account No</th>
										<th>Meter No</th>
										<th>Billmonth</th>
										<th>MR Name</th>
										<th>Issued by</th>
								 </tr>
								</thead>
								<tbody id="mrwiseSealNoData">
									
									
								</tbody>
							</table> 
							</div>
							
				    </div>
					<!-- END EXAMPLE TABLE PORTLET-->
					<div id="stack1" class="modal fade" tabindex="-1" data-width="500" data-backdrop="static" data-keyboard="false">
					   <div class="modal-dialog">
					      <div class="modal-content">
					      <div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
								<h4 class="modal-title"> Seal Issued To Meter Reader</h4>
										</div>
					         <div class="modal-body">
					        
					            <div class="row">
					              
					              <div class="col-md-12">
					            
					              	 <form:form action="./sealOutWard"  modelAttribute="sealManager" commandName="sealManager" method="post" id="sealManagerOutWard" name="sealOutWard">
											
								 <table>
											
									
			     							      
										<tr><td>Issued Date </td>
										
											<td><div class="input-group input-medium date date-picker" >
																<form:input path="iDate" type="text" class="form-control" name="selectIssuedDate" id="selectIssuedDate"   ></form:input>
																<span class="input-group-btn">
																<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></td>
										
										
										</tr>
										<tr><td>RdgMonth</td>
											<td>
											<form:input path="billmonth" type="text" class="form-control" name="billmonth" id="billmonth" readonly="true"></form:input>
											</td>
										</tr>
										<tr>
										
										
											
										   <td>Division</td>
										
						                    <td><form:select path="dvision" name="dvision" class="form-control" id="dvision">
										
										     <form:option value="0">select</form:option>
                                             <form:option value="CD-I">CD-I</form:option>
                                             <form:option value="CD-II">CD-II</form:option>
                                             <form:option value="CD-III">CD-III</form:option>
                                             <form:option value="CD-IV">CD-IV</form:option>
                                             <form:option value="CD-V">CD-V</form:option>
                                             <form:option value="CD-VI">CD-VI</form:option>
                                             <form:option value="CD-VII">CD-VII</form:option>
										   </form:select></td> 
										   
									   </tr>	   
									   <tr>
										  
										 <td>SDO Code</td>
									     <td><form:input path="subDiv" type="text" class="form-control" required="required" name="subDiv" id="subDiv"></form:input></td>
								         </tr>
										
										<tr>
										
										<td>Seal No. From</td>
										 <td> <form:input path="sealNo" type="text" class="form-control" name="sealNum" id="sealNoId1"></form:input></td>
									   </tr>
									   
									   <tr>
										
										<td>Seal No. To</td>
									     <td> <form:input path="cmri" type="text" class="form-control" name="sealTo1" id="sealTo1Id" onchange="return onEnter(this.form,'sealNoId1',this.id,'total1');"></form:input></td>									      
									   </tr>
									     
									     
									      <tr>
									      <td>Total.</td>
									      <td><input type="text" class="form-control" name="total1" id="total1"></td>
									      </tr>
									     
									     
									      <tr>
									        <td>SealCardNo</td>
									        <td> <form:input path="cardSealNo" type="text" class="form-control" name="SealCardNo" id="SealCardNo" ></form:input></td>
									        <td> <button type ="submit" class="btn blue pull-right" onclick="return sealOutwarValidation(this.form,'preview','./sealReIssue')" >Preview</button></td>
									        </tr>
										   
										   <tr hidden="true">
									        <td>SealCardNo</td>
									        <td> <form:input path="cardSealNo1" type="text" class="form-control" name="SealCardNo1" id="SealCardNo1" ></form:input></td>
									        
									        </tr>
										 
										  <tr>
										   <td>MR Name</td>
										
						                    <td><form:select path="mrname" name="mrname1" class="form-control" id="mrnameId1">
										     <form:option value="0">select</form:option> 
										      <c:forEach items="${mrNames}" var="element">
													<form:option value="${element.mrname}">${element.mrname}</form:option>
													</c:forEach>	
										                                                   
										   </form:select></td> 
							
										</tr>
										
										
								    <%--  <tr>
								        <td>Issued By</td>
									     <td><form:input path="issuedBy" type="text" class="form-control"  name="issuedBy" id="issuedBy"></form:input></td>
								     </tr> --%>
								
										
									</table> 					
												
							                 <div align="right">	
							                 <button  class="btn blue "  onclick="return sealOutwarValidation(this.form,'update','./sealOutWard')">Update</button>												 
													 <button  class="btn green " onclick="return printPreview(this.form)" >Print Preview</button>													 
													 <button type="button" data-dismiss="modal" class="btn ">Close</button>
									
												</div>
											</form:form>
									 
					              </div>
					           </div>
					       </div>
					   </div>
					  </div>
					</div>
					<!-- END SEAL OURTWARD -->
					
					<!-- BEGUN SEAL INWARD -->
					
					
					<%-- <div id="stack2" class="modal fade" tabindex="-1" data-width="500">
					   <div class="modal-dialog">
					      <div class="modal-content">
					         <div class="modal-body">
					            <h4 class="modal-title">New Seal Entry</h4>
					            <hr></hr>
					            <div class="row">
					              
					              <div class="col-md-12">
					            
					              	 <form:form action="./pushAllseal" modelAttribute="sealManager" commandName="sealManager"  method="post" id="sealManagerInward">
											
											<table>
										<tr><td>Seal Received Date </td>
										
											<td><div class="input-group input-medium date date-picker"  id="five">
																<form:input path="rDate" type="text" class="form-control" name="selectReceiveDate" id="selectReceiveDate" ></form:input>
																<span class="input-group-btn">
																<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></td>
										
										</tr>
									
									  <tr hidden="true"><td>RdgMonth</td>
											<td>
											<form:input path="billmonth" type="text" class="form-control" name="billmonth" id="billmonth" readonly="true"></form:input>
											</td>
										</tr>
										
									     <tr>
										  <td>seal From</td>
										  <td><form:input path="sealNo" type="text" class="form-control" name="sealNo" id="sealNoId"></form:input></td>
									      	</tr>
									    <tr>
									    <td>seal To</td>	
									      <td><form:input path="mrname" type="text" class="form-control" name="sealTo" id="sealTo" onblur="return onEnter(this.form,'sealNoId',this.id,'totalId');" ></form:input></td><!-- onkeypress="if(event.keyCode==13) return onEnter('sealNoId',this.id,'totalId');" -->
									    
									    </tr>
									    <tr>
									    <td>Total</td>
										    <td><input type="text" class="form-control" name="total" id="totalId"></td>
									    </tr>
										
									</table>
																	
												
							                 <div class="modal-footer">
													 <button type="button" data-dismiss="modal" class="btn">Close</button>
													 <button type ="submit" class="btn blue pull-right"  onclick="return sealInwarValidation(this.form)">Update</button>
													
												</div>
											</form:form>
									 
					              </div>
					           </div>
					       </div>
					   </div>
					  </div>
					</div> --%>
					
					<!-- END SEAL INWARD -->
					
					
					<!--BEGUN RETURN ENTRY -->
					
					<%-- <div id="stack3" class="modal fade" tabindex="-1" data-width="500">
					   <div class="modal-dialog">
					      <div class="modal-content">
					      
					         
					          <div class="modal-body">
					            <h4 class="modal-title">Return Entry</h4>
					            <hr></hr>
					            
					            <!-- TAB BEGINS -->
					            <div class="tabbable tabbable-custom">
					             <ul class="nav nav-tabs">
									<li class="active"><a href="#tab_1_1" data-toggle="tab">Single Update</a>
									
									</li>
									<li><a href="#tab_1_2" data-toggle="tab">Multi Entry Update</a>
									
									</li>
									
								</ul>
					            
					                 <div class="tab-content">
					                 
					                  <!-- BEGUN FIRST TAB OF SINGLE UPDATE -->
					                  <div class="tab-pane active" id="tab_1_1">
					                  
					                   <form:form action="./singleSealUpdate" modelAttribute="sealManager"  commandName="sealManager" method="post" id="singleUpdate" name="singleSeal">
											
									   <table>	
									    <tr>
										<td>Seal No.</td>
										  <td><form:input path="sealNo" type="text" class="form-control" name="sealNo" id="sealNo2Id" onchange="return getCardSlNo(this.id,'sealCard2',0);"></form:input></td>
									    </tr>
									    <tr>
									    <td>Card No.</td>
									      <td><form:input path="cardSealNo1" type="text" class="form-control" name="sealCard" id="sealCard2" ></form:input></td>
									    </tr>
									         
									      <tr hidden="true"><td>RdgMonth</td>
											<td>
											<form:input path="billmonth" type="text" class="form-control" name="billmonth" id="billmonth" readonly="true"></form:input>
											</td>
										</tr>   
									         
										<tr><td>Return Date </td>
										
											<td><div class="input-group input-medium date date-picker" >
																<form:input path="revDate" type="text" class="form-control" name="selectReturnDate" id="selectReturnDate"  ></form:input>
																<span class="input-group-btn">
																<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></td>
										    </tr>																	
											</table>	
							                 <div class="modal-footer">
													 <button type="button" data-dismiss="modal" class="btn">Close</button>
													<button type ="submit" class="btn blue pull-right"  onclick="return singleValidation(this.form);">Single Update</button>
													
												</div>
											</form:form>
			                             </div>
					                   <!-- END FIRST TAB OF SINGLE UPDATE -->
					                  
					                  <!-- BEGUN SECOND TAB OF MULTIUPDATE -->
					                  <div class="tab-pane" id="tab_1_2">
					                  
					                   
									   <form:form action="./sealMultipleUpdate"  modelAttribute="sealManager" commandName="sealManager" method="post" id="multiUpdate" name="multipleSeal">
									   
									   <table>
										<tr>
										  <td>Seal No. From</td>
										  <td><form:input path="sealNo" type="text" class="form-control" name="sealNo" id="sealNo3"></form:input></td>
									      
									    </tr>
									    
									    <tr>
									    <td>Seal No. To</td>
									    <td><form:input path="cmri" type="text" class="form-control" name="sealTo3" id="sealTo3" onchange="return onEnter(this.form,'sealNo3',this.id,'total3');"></form:input></td>
									    </tr>
									    
									    <tr>
									      <td>Total.</td>
									      <td><input type="text" class="form-control" name="total3" id="total3"></td>
									      </tr>
									      
									     <tr>
									     <td>Card No</td>
									      <td><form:input path="cardSealNo1" type="text" class="form-control" name="sealCard" id="cardSealNo3"></form:input></td>
									     </tr>
									     
									     <tr hidden="true"><td>RdgMonth</td>
											<td>
											<form:input path="billmonth" type="text" class="form-control" name="billmonth" id="billmonth" readonly="true"></form:input>
											</td>
										</tr>
									    <tr>
									    
									    <td>Return Date </td>
										
											<td><div class="input-group input-medium date date-picker">
																<form:input path="revDate" type="text" class="form-control" name="selectReturnDate" id="selectReturnDate3" ></form:input>
																<span class="input-group-btn">
																<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></td>
									    </tr>
								
								     
										
									</table>
																	
												
							                 <div class="modal-footer">
													 <button type="button" data-dismiss="modal" class="btn">Close</button>
													 <button type ="submit" class="btn blue pull-right" onclick="return MultipleValidation(this.form)">Multi Entry Update</button>
													
												</div>
											</form:form>
					                  
					                  </div>
					              	
									  <!-- END SECOND TAB OF MULTIUPDATE -->
					              </div>
					           </div>
					        <!-- TAB ENDS -->
					       </div>
					   </div>
					  </div>
					</div>
	 --%>
	<!-- <div id="stack4" class="modal fade" tabindex="-1" data-width="400">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true"></button>
									<h4 class="modal-title">Report</h4>
								</div>
								<div class="modal-body">
									<div class="row">
										<div class="col-md-12">
										
			
												<div class="btn-group" >	
													  <button class="btn blue" style="display: block;" id="mrwiseSealSummary"  data-target="#stack5" data-toggle="modal" >Summary</button>
											    </div>
											    <div class="btn-group" >	
													  <button class="btn green" style="display: block;" id="remarksSummary"  onclick="window.location.href='./showReturnSeals'">Returned Seals Summary</button>
											    </div>
											    <div class="modal-footer">
													 <button type="button" data-dismiss="modal" class="btn" id="closeData" >Close</button>
											    </div>

										</div>
									</div>
								</div>
							</div>
						</div>
					</div> -->
	
	
	
	
	
			<%-- <div id="stack5" class="modal fade" tabindex="-1" data-width="500">
					   <div class="modal-dialog">
					      <div class="modal-content">
					         <div class="modal-body">
					            <h4 class="modal-title">Show Summary</h4>
					            <hr></hr>
					            <div class="row">
					              
					              <div class="col-md-12">
					            
					              	 <form:form action="./showMrwiseSealSummary" modelAttribute="sealManager" commandName="sealManager"  method="post" id="mrwiseSealSummaryId">
											
											<table>
										<tr>
														<td>Month</td>
														<td></td>
														<td>														
															<div class="input-group input-medium date date-picker"  data-date-format="yyyymm" data-date-viewmode="years" data-date-minviewmode="months">
																<input type="text" class="form-control" name="billMonth" id="billMonth">
																<span class="input-group-btn">
																<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
																</span>
															</div>														                          
														</td>
													</tr>
									 
										
									</table>
																	
												
							                 <div class="modal-footer">
													 <button type="button" data-dismiss="modal" class="btn">Close</button>
													 <button type ="submit" class="btn blue pull-right"  onclick="return showSealSummaryReport()">view</button>
												</div>
											</form:form>
									 
					              </div>
					           </div>
					       </div>
					   </div>
					  </div>
					</div>
	 --%>
						
					<!-- END RETURN ENTRY -->
					
					
					
					
				</div>
				<%-- <div class="portlet box purple"     id="sealSummaryDivId"  style="display: none;" >
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-edit"></i>MRWise Seal Summary
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a>
							<a href="javascript:;"
								class="remove"></a>
						</div>
					</div>
					<div class="portlet-body">

						<div class="table-toolbar">
							<div class="btn-group pull-right">
							</div>
						</div>

						<table class="table table-striped table-hover table-bordered" id="sample_editable_2">
									<thead>
										<tr style="background-color: lightgray; ">
											<th hidden="true"></th>
									        <th>SLNO</th>
											<th>MR NAME</th>
											<th>SEAL ISSUED</th>
										</tr>
									</thead>
									<tbody>
										<c:set var="count" value="1" scope="page" />
								<c:forEach var="element" items="${mrwiseSealList}">
										<tr>
										     <td>${count}</td>											
											<td>${element[0]}</td>
											<td><a href="#"  onclick="return displaySealIssued('${element[0]}','${element[2]}');">${element[1]}</a></td>
										</tr>
										<c:set var="count" value="${count + 1}" scope="page"/>
									</c:forEach>
									</tbody>
								</table>
					</div>
				</div> --%>
				
				<div class="row"  id="sealSummaryDivId"  style="display: none;" >
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box purple">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>MRWise Seal Summary</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-toolbar">
							</div>
							<table class="table table-striped table-bordered table-hover" id="table_4">
								<thead>
										<tr style="background-color: lightgray; " >
									        <th>SLNO</th>
											<th>MR NAME</th>
											<th>SEAL ISSUED</th>
											<th>SEAL USED</th>
											<th>SEAL DAMAGE</th>
										</tr>
									</thead>
									<tbody>
										<c:set var="count" value="1" scope="page" />
								<c:forEach var="element" items="${mrwiseSealList}">
										<tr class="odd gradeX">
										     <td>${count}</td>											
											<td>${element[0]}</td>
											<td><a href="#"  onclick="return displaySealIssued('${element[0]}','${element[2]}');">${element[1]}</a></td>
											<td><a href="#"  onclick="return displaySealUsed('${element[0]}','${element[2]}');">${element[3]}</a></td>
											<td><a href="#"  onclick="return displaySealDamaged('${element[0]}','${element[2]}');">${element[4]}</a></td>
										</tr>
										<c:set var="count" value="${count + 1}" scope="page"/>
									</c:forEach>
									</tbody> 
							</table>
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
				
				 <div  id="alertMsg1" >
							
					</div>
					
					<c:if test = "${not empty results1}"> 			        
			        <div class="alert alert-danger display-show">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${results1}</span>
						</div>
			        </c:if>
				<div class="portlet box purple"     id="sealReturnDivId"  style="display: none;" >
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-edit"></i>MRWise Seal Return
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a>
							<a href="javascript:;"
								class="remove"></a>
						</div>
					</div>
					<div class="portlet-body">

						<div class="table-toolbar">
							<div class="btn-group pull-right">
							</div>
						</div>


						<a href="#" id="editbutton"></a>

						<table class="table table-striped table-hover table-bordered"
						id="sample_editable_1">
									<thead>
										<tr style="background-color: lightgray; ">
											<th hidden="true"></th>
									        <th>SLNO</th>
											<th>MR NAME</th>
											<th>PC Seals</th>
											<th></th>
											<th>Paper Seals</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<c:set var="count" value="1" scope="page" />
								<c:forEach var="element" items="${mrwiseSealReturnList}">
										<tr>
										     <td>${count}</td>											
											<td>${element[0]}</td>
											<td><a href="#" data-target="#stack6" data-toggle="modal" onclick="return displaySealReturnedPc('${element[0]}','R');">${element[3]}</a></td>
											<td>
											<label id="rCheckbox">
											<input type="checkbox"  value ="${element[0] }$R" name="multipleCardSealCheck" />
											</label>
											</td>
											<td><a href="#" data-target="#stack6" data-toggle="modal" onclick="return displaySealReturnedPc('${element[0]}','MR');">${element[4]}</a></td>
											<td>
											<label id="mrCheckbox">
											<input type="checkbox"  value ="${element[0] }$MR" name="multipleCardSealCheck"/>
											</label>
											</td>
										</tr>
										<c:set var="count" value="${count + 1}" scope="page"/>
									</c:forEach>
									</tbody>
								</table>
								
					</div>
					<button type ="submit" class="btn blue pull-right"  onclick="return updateMultipleCardSlno1();">Update CardSealNo</button>
				</div>
				 
				 
				
				<div id="stack6" class="modal fade" tabindex="-1" data-width="400" data-backdrop="static" data-keyboard="false">
							<div class="modal-dialog" style="width: 600px;" >
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true"></button>
										<h4 class="modal-title">Seal Numbers</h4>
										<div >
										<table >
										<tr >
										 <td>MR NAME</td>
										 <td><input type="text" class="form-control input-medium"  placeholder="" id="mrName" readonly="readonly"></td>
										</tr>
										<tr>
										 <td>CARD SLNO1</td>
										 <td><input type="text" class="form-control input-medium"  placeholder="" id="cardslno1"></td>
										</tr>
										</table>
										</div>
										
									</div>

									<div class="modal-body" >
										<div class="row">
											<div class="col-md-12">
												<form>
													<table class="table table-striped table-hover table-bordered" id="table_3" >
													</table>	
													<div class="modal-footer" >
													 <button type="button" data-dismiss="modal" class="btn" id="closePopUp">Close</button>
													 <button type ="submit" class="btn blue pull-right"  onclick="return updateCardSlno1();">Update</button>
												</div>
												</form>
											</div>
										</div>

									</div>

								</div>
							</div>
						</div>
						
			</div>
</div>