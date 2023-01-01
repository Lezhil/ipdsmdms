<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.text.*,java.util.*" session="false"%>

<script  type="text/javascript">
	$(".page-content").ready(function(){  
		 $("#accno").val('${sdoaccno}');
		 $("#rdngmonth").val('${readingMonth}');
		 $("#readingdateVal").val('${manualReadingDate}');
		 $("#cmriNoErr").hide();
		 $("#cmriNoErr1").hide();
	    // $('#readingdateVal').val('${getManulReadingData.readingdate}');

	    //$('#readingdateVal').val('${getManulReadingData.readingdate}');

	     if(document.getElementById("accno").value == "" || document.getElementById("accno").value == null)
	    	 {
	    	 //alert(document.getElementById("accno").value);
	    	    $("#readingremark").attr("tabindex",3);
	    	    $("#accno").attr("tabindex",1);
	    	    $("#metrno").attr("tabindex",2);
	    	 }
	     else{
	    	 var accno = document.getElementById("accno");
			    accno.onfocus = function() {
			        moveCaretToEnd(this);
			        
			        /* // Work around Chrome's little problem
			        text.onmouseup = function() {
			            // Prevent further mouseup intervention
			            moveCaretToEnd(this);
			            text.onmouseup = null;
			            return false;
			        }; */
			    };
		    	
	    	 $("#accno").focus();
	    	 $("#readingremark").attr("tabindex",3);
	    	 $("#accno").attr("tabindex",1);
	    	 $("#metrno").attr("tabindex",2); 
	         }
		App.init();
		TableManaged.init();
		FormComponents.init();		
		$('#MDASSideBarContents,#newConnectionId,#manualreading').addClass('start active ,selected');
		$("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports,#meterMaster,#other-Reports").removeClass('start active ,selected');

		//$("#showData").hide(); 
		
	});
	
	
	 function myMeterChange() 
	   {
		 var rdngremark=$("#readingremark").val();
		 if(rdngremark=="METER CHANGE" || rdngremark=="METER ROUND")
			 {
			   $("#remark").focus();
			   $("#remark").attr('disabled',false);
			   document.getElementById("remarkcheck").innerHTML = "Enter the new meter no plesae";
			   return true;
			 }
		 else
		  {
			// $("#remark").attr('disabled',true);
			 $("#remark").val('');
			 document.getElementById("remarkcheck").innerHTML = " ";
			 return false;
		   }
		 
		}
	 
	    
	 
	  
	 function getMeterMasterData()
     {   
		 $("#metrno").val("");
	    document.getElementById('dataNotFoundMsg').style.display = "none";
	   	$("#mrino").empty();
	   	var accno=document.getElementById('accno').value;
	   	var readingMonth = document.getElementById('rdngmonth').value;
	   	var accnocheck = /^[0-9a-zA-Z]{12,13}$/;
       if(accno=='')
		{
    	  return false;
		}
      	    $.ajax({
      	    	type : "GET",
	     		url : "./getManualReadingDataAjax/"+readingMonth+"/"+accno,
	     		dataType : "json",
	     		
	     		cache:false,
	     	
	     		  success : function(response)
	     		  {
	     			 if(response.length > 0)
	     				 {
	     				$("#readingremark").focus();
		     			  var res = response[0];
		     			$("#accno").val(res[0]);
		     			$("#metrno").val(res[1]);
		     			$("#prevmeterstatus").val(res[2]);
		     			//  $("#readingremark").val(res[3]);
		     			$("#remark").val(res[4]);
		     			$('#readingremark option[value="OK"]').html("OK");
		     			 $("#accno").val(res[0]);
		     			 $("#metrno").val(res[1]);
		     			 
		     			// $("#readingremark").val(res[3]);
		     			 $("#remark").val(res[4]);

		     			$("#sdocode").val(res[5]);
		     			$("#prkwh").val(res[8]);
		     			$("#currdngkwh").val(res[9]);
		     			$("#unitskwh").val(res[10]);
		     			$("#xcurrdngkwh").val(res[11]);
		     			$("#oldxcurrdngkwh").val(res[11]);
		     			$("#sdoname").val(res[6]);
		     			$("#prkvah").val(res[12]); 
		     			$("#currrdngkvah").val(res[13]);
		     			$("#unitskvah").val(res[14]);
		     			$("#xcurrrdngkvah").val(res[15]);
		     			$("#oldtariffcode").val(res[7]);
		     			$("#prkva").val(res[16]);
		     			$("#currdngkva").val(res[17]);
		     			$("#unitskva").val(res[18]);
		     			$("#xcurrdngkva").val(res[19]);
		     			$("#pf").val(res[20]);
		     			$("#xpf").val(res[21]);
		     			$("#oldseal").val(res[22]);
		     			$("#newseal").val(res[23]);
		     			$("#mrdstatus").val(res[24]);
		     			$("#mrname").val(res[25]);
		     			$("#oldsel").val(res[23]);
		     			$("#mrino").val(res[26]);
		     			if(res[27] == null || res[27] == "")
		     				{
		     				
		     				}
		     			else{
		     				
		     				$("#readingdateVal").val(res[27]);
		     			}
		     			$("#circle").val(res[28]);
		     			$("#xmldate").val(res[29]);
		     			$("#t1kwh").val(res[30]);
		     			$("#t2kwh").val(res[31]);
		     			$("#t3kwh").val(res[32]);
		     			$("#t4kwh").val(res[33]);
		     			$("#t5kwh").val(res[34]);
		     			$("#t6kwh").val(res[35]);
		     			$("#t7kwh").val(res[36]);
		     			$("#t8kwh").val(res[37]);
		     			
		     			$("#t1kvah").val(res[38]);
		     			$("#t2kvah").val(res[39]);
		     			$("#t3kvah").val(res[40]);
		     			$("#t4kvah").val(res[41]);
		     			$("#t5kvah").val(res[42]);
		     			$("#t6kvah").val(res[43]);
		     			$("#t7kvah").val(res[44]);
		     			$("#t8kvah").val(res[45]);

		     			//$("#mrino").append("<option value='"+res[26]+"'>"+res[26]+"</option>");
	     				 }
	     			 else{
	     				myClearFunction1();
	     				document.getElementById('dataNotFoundMsg').style.display = "block";
	     				document.getElementById('dataNotFoundMsg').innerHTML = "Data Not Found For This AccountNo.";
	     			 }
	     			 
	     			//$("#").val();
	     		  }
      	    });
      	  
      	    
      	  //   $("#showData").show();
   		return true;
       	/* }
       else
       	{
       	 document.getElementById("accnocheck").innerHTML = "Enter minimum 12 digits only";
       	 return false;
       	}  */
       }
	 
	    function getMeterMasterData1()
	      {   
	    	var readingMonth = document.getElementById('rdngmonth').value;
	    	 var meterno=document.getElementById('metrno').value;
	    	  var letters = /^[A-Za-z]+$/;
	          var code = /^[@#!$%&*/]+$/;
	          
	          if(meterno=='')
	    		{
	    		  //document.getElementById("meternocheck").innerHTML = "Enter the meter no plesae";
	 	    	//  document.getElementById("metrno").focus();
	 	    	  return false;
	    		}
	          
	          else if(meterno.match(letters) || meterno.match(code))
	     		{
	     	      document.getElementById("meternocheck").innerHTML = "<b style='color:red'>Please provide alphanumeric value only</b>";
	     	      $("#manualReading input").val("");
	     		  $("#metrno").focus();
	     		} 
	          else
	    		{
	    		//$('#manualReading').attr('action','./getManualReading1').submit();
	    		//$("#showData").show();
	    		   $.ajax({
      	    	type : "GET",
	     		url : "./getManualReadingDataAjaxMeterBased/"+readingMonth+"/"+meterno,
	     		dataType : "json",
	     		cache:false,
	     	
	     		  success : function(response)
	     		  {
	     			 if(response.length > 0)
     				 {
	     				
	     			 $("#readingremark").focus();
	 	    	 
	     			  var res = response[0];
	     			  var res1 = response[1];
	     			 var res2 = response[2];
	     			// alert(res2);
	     			/* 
	     			 if(res[3]==null || res[3]=="" || res[3]== "0")
    				  {
	     				
	     				$('#readingremark option[value="OK"]').text("OK")
    				  //$("#readingremark").val("OK");
    				  }
	     			 else{
	     				$("#readingremark").val(res[3]);
	     			 } */
	     			 
	     			$('#readingremark option[value="OK"]').html("OK");
	     			 
	     			//$("#circle").val(res2);
	     			$("#accno").val(res[0]);
	     			$("#metrno").val(res[1]);
	     			$("#prevmeterstatus").val(res1);

	     			 
	     			$("#accno").val(res[0]);
	     			$("#metrno").val(res[1]);
	     			$("#prevmeterstatus").val(res[2]);
	     			 
	     			$("#remark").val(res[4]);
	     			$("#sdocode").val(res[5]);
	     			$("#prkwh").val(res[8]);
	     			$("#currdngkwh").val(res[9]);
	     			$("#unitskwh").val(res[10]);
	     			$("#xcurrdngkwh").val(res[11]);
	     			$("#sdoname").val(res[6]);
	     			$("#prkvah").val(res[12]);
	     			$("#currrdngkvah").val(res[13]);
	     			$("#unitskvah").val(res[14]);
	     			$("#xcurrrdngkvah").val(res[15]);
	     			$("#oldtariffcode").val(res[7]);
	     			$("#prkva").val(res[16]);
	     			$("#currdngkva").val(res[17]);
	     			$("#unitskva").val(res[18]);
	     			$("#xcurrdngkva").val(res[19]);
	     			$("#pf").val(res[20]);
	     			$("#xpf").val(res[21]);
	     			$("#oldseal").val(res[22]);
	     			$("#newseal").val(res[23]);
	     			$("#mrdstatus").val(res[24]);
	     			$("#mrname").val(res[25]);
	     			$("#oldsel").val(res[23]);

	     		
	     			$("#mrino").append("<option value='"+res[26]+"'>"+res[26]+"</option>"); 

	     			$("#mrino").val(res[26]);
	     			$("#readingdateVal").val(res[27]);
	     			if(res[27] == null || res[27] == "")
     				{
     				
     				}
     			else{
     				$("#readingdateVal").val(res[27]);
     			}
	     			//alert(res[28]);
	     			$("#circle").val(res[28]);
	     			$("#xmldate").val(res[29]);
	     			//$("#mrino").append("<option value='"+res[26]+"'>"+res[26]+"</option>");

	     			//$("#").val();
	     		  }
	     			 else{
	     				myClearFunction1();
	     				document.getElementById('dataNotFoundMsg').style.display = "block";
	     				document.getElementById('dataNotFoundMsg').innerHTML = "<b>Data Not Found For This MeterNo.</b>"; 
	     				 
	     			 } 
	     		  }
      	        });
      	  
	    		
	    		return true;
	    		}
	    			
	      }
	    function myClearFunction1()
	    {
	    	$("#accno").val("");$("#metrno").val("");$("#pf").val("");$("#xpf").val("");$("#prevmeterstatus").val("");
	    	$("#sdocode").val("");$("#sdoname").val("");$("#oldtariffcode").val("");
	     	$("#metrno").val("");
	    	$("#pf").val("");
	    	$("#xpf").val("");
	    	$("#prevmeterstatus").val("");
	    	$("#sdocode").val("");
	    	$("#sdoname").val("");
	    	$("#oldtariffcode").val("");
	    	

	    	$("#prkwh").val("");$("#prkvah").val("");$("#prkva").val(""); 
	    	$("#unitskwh").val("");$("#unitskvah").val("");$("#unitskva").val(""); 
	    	
	    	$("#currdngkwh").val("");  
	        $("#xcurrdngkwh").val(""); 
	    	
	    	$("#currrdngkvah").val('');
	    	$("#currdngkva").val('');
	    	
	    	$("#xcurrrdngkvah").val('');
	    	$("#xcurrdngkva").val('');
	    	
	    	
	    	
	    	$("#oldseal").val("");$("#newseal").val("");$("#mrdstatus").val("");$("#mrname").val("");
	    	$("#mrino").val("");$("#newseal").val("");$("#newsealval").val("");
	    	$("#accno").focus();
	    	$("#Sufix").val($("#Sufix").val());
	    	$("#circle").val("");$("#xmldate").val("");
	    	
	    }
	    
	 //Cursor Moves End
	    function moveCaretToEnd(el) {
	        if (typeof el.selectionStart == "number") {
	            el.selectionStart = el.selectionEnd = el.value.length;
	        } else if (typeof el.createTextRange != "undefined") {
	            el.focus();
	            var range = el.createTextRange();
	            range.collapse(false);
	            range.select();
	        }
	    }

	    
	    
	    
	    function myClearFunction()
	    {

	    	$("#metrno").val("");$("#pf").val("");$("#xpf").val("");$("#prevmeterstatus").val("");
	    	$("#sdocode").val("");$("#sdoname").val("");$("#oldtariffcode").val("");
	    	$("#xmldate").val("");$("#circle").val("");

	    	var accno = document.getElementById("accno");
		    accno.onfocus = function() {
		        moveCaretToEnd(this);
		        
		        // Work around Chrome's little problem
		        textarea.onmouseup = function() {
		            // Prevent further mouseup intervention
		            moveCaretToEnd(this);
		            textarea.onmouseup = null;
		            return false;
		        };
		    };
	    	
	    	$("#metrno").val("");
	    	$("#pf").val("");
	    	$("#xpf").val("");
	    	$("#prevmeterstatus").val("");
	    	$("#sdocode").val("");
	    	$("#sdoname").val("");
	    	$("#oldtariffcode").val("");
	    	

	    	$("#prkwh").val("");$("#prkvah").val("");$("#prkva").val(""); 
	    	$("#unitskwh").val("");$("#unitskvah").val("");$("#unitskva").val(""); 
	    	
	    	$("#currdngkwh").val("");  
	        $("#xcurrdngkwh").val(""); 
	    	
	    	$("#currrdngkvah").val('');
	    	$("#currdngkva").val('');
	    	
	    	$("#xcurrrdngkvah").val('');
	    	$("#xcurrdngkva").val('');
	    	
	    	
	    	
	    	$("#oldseal").val("");$("#newseal").val("");$("#mrdstatus").val("");$("#mrname").val("");
	    	$("#mrino").val("");$("#newseal").val("");$("#newsealval").val("");
	    	$("#accno").focus();
	    	$("#Sufix").val($("#Sufix").val());
	    }
	    
	    
	    function myFormvalidation(form)
	    {
		   
			$("#mrino").attr("disabled",false);
			$("#readingdateVal").attr("disabled",false);
	    	 var regex = /^[a-zA-Z ]*$/;
	    	
	    	 if(form.accno.value=='' || form.metrno.value=='')
	    		 {
	    			bootbox.alert('Enter either account number or Metrno please ');
					return false;
	    		 }
	    	 if(form.pf.value==99999999)
	    		 {
	    		  $("#pf").val('');
	    		 }
	    	 
	    	 if(form.currrdngkvah.value==99999999)
    		 {
    		  $("#currrdngkvah").val('');
    		 }
	    	 
	    	 
	    	 if(form.currdngkva.value==99999999)
    		 {
    		  $("#currdngkva").val('');
    		 }
	    	 
	    /* 	 if(form.prevmeterstatus.value!='' && !form.prevmeterstatus.value.match(regex))
   		      {
   			    bootbox.alert('Enter Previous Remark and must conatons only alphabets');
				return false;
   		      } 
	    	 
	    	 if( form.sdocode.value!='' && isNaN(form.sdocode.value))
				{
				bootbox.alert('SDOCODE cannot be blank should contain only digits');
				return false;
				}
	    	 
	    	 if(form.sdoname.value!='' && form.sdoname.value.match(regex))
	    		 {
	    		 bootbox.alert('SDONAME cannot be blank and must not be only alphabets ');
				 return false;
	    		 }
	    	 
	    	 
	    	 if( /[^a-zA-Z0-9]/.test(form.oldtariffcode.value) || form.oldtariffcode.value!='' || form.oldtariffcode.value.match(regex))
   		    {
   		     bootbox.alert('TarrifCode cannot be blank and must be alphanumeric  ');
				return false;
   		   } */
	    	 
	    	 
		   /*   if( form.prkwh.value=='' || isNaN(form.prkwh.value))
				{
				bootbox.alert('PRKWH cannot be blank should contain only digits');
				return false;
				} */
		     
		     if( form.currdngkwh.value=='' || isNaN(form.currdngkwh.value))
				{
				bootbox.alert('CURRDNGKWH cannot be blank should contain only digits');
				return false;
				}
		     
			
			
		      if(form.pf.value!="" && form.pf.value > 1 )
			  {
			   bootbox.alert('PF cannot be greater than one ');
			   return false;
			  }
		     
			 
			/*   if(form.xpf.value=='' || isNaN(form.xpf.value))
				{
				bootbox.alert('XPF cannot be blank should contain only digits');
				return false;
				} */
		
			   /*  if(/[^a-zA-Z0-9]/.test(form.oldseal.value) || form.oldseal.value=='' || form.oldseal.value.match(regex) )
	    		 {
	    		     bootbox.alert('Old Seal  cannot be blank and must be alphanumeric  ');
					return false;
	    		 } */
			  
			   /*  if(/[^a-zA-Z0-9]/.test(form.newseal.value) || form.newseal.value=='' || form.newseal.value.match(regex) )
	    		 {
	    		     bootbox.alert('New Seal  cannot be blank and must be alphanumeric  ');
					return false;
	    		 } */
			    
			 /* ---------   if(form.mrdstatus.value=='0' )
				{
					bootbox.alert('please select MRI status Type');
					return false;
				} */
			    
			   /*  if(form.mrname.value=='0' )
				{
					bootbox.alert('please select mrname Type');
					return false;
				}
			    
			    if(form.mrino.value=='0' )
				{
					bootbox.alert('please select cmrino Type');
					return false;
				}*/
			    
				 
		  /* -------  	 if(form.readingdateVal.value=='' || form.readingdateVal.value.match(regex))
	    		 {
	    			bootbox.alert('Enter Reading date please');
					return false;
	    		 }  */
	    		 
	    	   if(form.mrino.value==''||form.mrino.value==null && form.mrdstatus.value=='Y')
	    		   {
	    		   $("#mrino").attr('disabled',false);
	    		   $("#mrino").focus();
	    	       
	    		   }
	    	  if(form.mrdstatus.value=='N')
	    		   {
	    		  $("#mrino").attr('disabled','disabled');
	    		  $("#mrino").attr('value','NA');
	               
	    		   }	 
	    		 
	    		 
	    	
	    		
	    	   if((form.prkwh.value==form.currdngkwh.value) && (form.readingremark.value == "OK"))
			   {
				   bootbox.alert("PRKWH and CURRDNGKWH both are equal Please enter Other Reading Reamrk ");
				   return false;
			   } 
	    	  
	    	  if(form.unitskva.value!='' && form.unitskva.value>100)
	    		  {
	    		  bootbox.alert("CURRDNGKVA cannot be greater than 100");
	    		  return false;
	    		  }
	    	  
	    	  if(form.currdngkva.value == "" || form.currdngkva.value == null)
		    	 {
	    		
		    	 $("#currdngkva").val(99999999);
		    	 }
	    	  
	    	  if(form.currrdngkvah.value == "" || form.currrdngkvah.value == null)
		    	 {
	    		
		    	 $("#currrdngkvah").val(99999999);
		    	 }
	    	  
	    	  
	    	  if(form.pf.value == "" || form.pf.value == null)
		    	 {
	    		
		    	 $("#pf").val(99999999);
		    	 }
	    	  
	    	 
	    	  
	    }
	    
	    
	    function MrnameBasis(rdate)
	    {
	    	
	    	//alert(param);
	    	var name=document.getElementById("mrname").value;
	   		var mriStatus = document.getElementById("mrdstatus").value;
	    	$("#mrino").empty();
	    	var readingDate = "";
	    	/*  if(rdate== "" || rdate.value == null)
	    	{
	    		bootbox.alert('PLease Enter Reading Date...');
	    		//$("#mrname").val(0);
			     return false;
	    	} 
	    	else{
	    		readingDate =rdate;
	    	} */
	    	readingDate =rdate;
	    	 $.ajax({
	     		type : "GET",
	     		url : "./getCmriDepandsOnMrname/"+readingDate+"/"+name,
	     		dataType : "json",
	     		cache:false,
	     	
	     		  success : function(response)
	     		  {
	     			 var html;
	     			 if(response.length > 0)
	     				 {
	     				
	     				/*   html = html + "<option value='"+response[i].mriNo+"'>"+response[i].mriNo+"</option>" */
	     				  //$("#mrino").append("<option value='"+response[i].mriNo+"'>"+response[i].mriNo+"</option>");
	     				 if(mriStatus == "N")
	     					 {
	     					$("#mrino").attr("disabled",false);
	     					$("#mrino").val("N/A");
	     					 }else{
	     						$("#mrino").val(response[0].mriNo);
	   	     				 $("#mrino").attr("disabled","disabled");
	     					 }
	     				 
	     				  
	     				 }else{
	     					 if(mriStatus == "Y")
	     						 {
	     						$("#mrino").attr("disabled",false);
	     						 }
	     					 else{
	     						$("#mrino").attr("disabled",true);
	     					 }
	     				 }
	     			  
	     			  //$("#mrino").append(html);
	     		  }
	    	 });
	    	
	    }
	    
	   function getNewSeal()
	   {
		   var data=document.getElementById("newsealval").value;
		   if(data!='' && data==1)
			   {
			    $("#newseal").attr("value","MR");
			   }
		   else if(data!='' && data==2)
			   {
			   $("#newseal").attr("value","R");
			   }
	   }
	   
	   function getUnits1()
	   {
		   var prkwhCheck = document.getElementById("prkwh").value;
		   if(prkwhCheck == "" || prkwhCheck == null)
			   {
			   prkwhCheck = "0";
			   }
		   var prkwh=parseInt(prkwhCheck);
		 
		   var currdngkwh=parseInt(document.getElementById("currdngkwh").value);
		   var readingremark=document.getElementById("readingremark").value;
		   
		 /*  if(currdngkwh>40000)
			  {
			    bootbox.alert("CURRDNGKWH cannot be gretaer than 40,000  ");
			   $("#currdngkwh").val('');
			   return false;
			  }
		    */
		  /*  if(prkwh==currdngkwh)
			   {
			   bootbox.alert("PRKWH and CURRDNGKWH both are equal Please enter Reading Reamrk ");
			   $("#readingremark").focus();
			   return false;
			   } */
			   
			   
			if(readingremark=="METER ROUND" || readingremark=="METER CHANGE") 
				{
				var data=currdngkwh-0;
				$("#unitskwh").val(data);
				if(data>40000)
	    	     {
	    	     bootbox.alert("UNITSKWH should not be greater than 40000");
	    	     }
	            else
	             $("#unitskwh").attr("value",data);
				}
			else{
				
				if(prkwh =='' && currdngkwh =='')
				   {
					
					}else{
						
					   var data1 = currdngkwh-prkwh;
						 if(data1 < 0 )
							 {
							 bootbox.alert("Please check the Reading Remark");
							 $("#unitskwh").attr("value",data1);
							 }
						 else{
							 if(data1>40000)
				    	     {
				    	     bootbox.alert("UNITSKWH should not be greater than 40000");
				    	     $("#unitskwh").attr("value",data1);
				    	     }
				            else
				             $("#unitskwh").attr("value",data1);
						 }
				   }
			}
			   
			   
		    if(prkwh==currdngkwh && readingremark=="OK")
		    	{
		    	bootbox.alert("PRKWH and CURRDNGKWH both are equal Please enter other than \"OK\" Reading Reamrk  "); 
		    	}
		    
		    
		    /* if(prkwh!='' && currdngkwh!='')
			   {
			     var data=currdngkwh-prkwh;
			     if(data<0 && (readingremark!="METER ROUND" || readingremark!="METER CHANGE"))
			    	 {
			    	   bootbox.alert("Please check the Reading Remark");
			    	 }
			     else
			    	 {
			    	 var data1=currdngkwh-0;
			    	    if(data1>40000)
			    	     {
			    	     bootbox.alert("UNITSKWH should not be greater than 40000");
			    	     }
			            else
			             $("#unitskwh").attr("value",data1);
			    	 }
			   } */
		 
		   
	   }
	   function getUnits2()
	   {
		   var prkvah=document.getElementById("prkvah").value;
		   var currrdngkvah=document.getElementById("currrdngkvah").value;
		   
		   if(currrdngkvah==99999999)
  		   {
  		    $("#currrdngkvah").val(0);
  		   }
		   
		  /*  if(currrdngkvah>40000)
			  {
			    bootbox.alert("currrdngkvah cannot be gretaer than 40,000  ");
			   $("#currrdngkvah").val('');
			   return false;
			  } */
		  /*  
		   if(prkvah==currrdngkvah)
		   {
		   bootbox.alert("PRKVAH and CURRDNGKVAH both are equal Please enter Reading Reamrk ");
		   $("#readingremark").focus();
		   return false;
		   } */
		   if(prkvah!='' && currrdngkvah!='')
			   {
			     var data=currrdngkvah-prkvah;
			     
			    /* if(data>40000)
			    	 {
			    	 bootbox.alert("UNITSKVAH should not be greate than 40,000");
			    	 }
			     else
			    	  $("#unitskvah").attr("value",data); */
			    $("#unitskvah").val(data);
			   }
		 
		   
	   }
	   function getUnits3()
	   {
		 
		   var prkva=document.getElementById("prkva").value;
		
		   var currdngkva=document.getElementById("currdngkva").value;
	
		   var currrdngkvah=document.getElementById("currrdngkvah").value;
		
		   var unitskvah=document.getElementById("unitskvah").value;
		  
		   var unitskwh=document.getElementById("unitskwh").value;
		   
		   if(currrdngkvah==99999999)
  		   {
  		    $("#currrdngkvah").val(0);
  		   }
		   
		   if(currdngkva==0)
			   {
			   $("#unitskva").attr("value",0);
			   }
		   else
			   {
			   if(currrdngkvah==0)
				   $("#unitskva").attr("value",0);
			   else
				   {
				   if(unitskvah==0)
					   $("#unitskva").attr("value",0);
				   else
					   {
					   var data=unitskwh/unitskvah;
					   
					   if(data>100)
						  
						   bootbox.alert("UNITSKVA should not be greater than 100");
					   else
					       $("#unitskva").attr("value",data.toFixed(3));
					   }
				    }
			   }
			   
		   /* 
		     if(currdngkva>100)
			   {
			   bootbox.alert("CURRDNGKVA cannot be greater than 100 ");
			   $("#currdngkva").val(''); */
			  // return false;
			  
		  /* if(prkva!='' && currdngkva!='')
			   {
			     var data=currdngkva-prkva;
			     if(data>100)
			    	 {
			    	 bootbox.alert("UNITSKVA should not be greater than 100");
			    	 }
			     else
			     $("#unitskva").attr("value",data);
			   }
		 */ 
		   
	   }
	   
	   function validateBackspace(e)
	   {
	   
	   var keyPressed = e.keyCode;
	  
	    var accval=document.getElementById("accno").value;
	    if(accval.length>0)
	    	{
	    	if(keyPressed == 46)
		      {
		      return false;
		      }
	    	}
         if(accval.length == 4 )
	     {
	     if(keyPressed == 8)
	      {
	      return false;
	      }
	      else
	       {
	       return true;
	      }
	      
	     }
	   }
	   
	   function setCmriValue(status)
	   {
		 
		   var readingdate = document.getElementById("readingdateVal").value;
		   var mrname = document.getElementById("mrname").value;
		   var mrino = document.getElementById("mrino").value;
		   
		   
		   
		   if(status=="Y")
		   {
			   
		 
			  if(readingdate == "" || readingdate == null)
				  {
				  	$("#readingdateVal").focus();
				  	if(mrino==null || mrino=="" || mrino=="N/A")
				  		{
				  			$("#mrino").val("");
				  			$("#mrino").attr('disabled',false);
				  		}
				  }
			  else{
				  
				  $.ajax({
			     		type : "GET",
			     		url : "./getCmriDepandsOnMrname/"+readingdate+"/"+mrname,
			     		dataType : "json",
			     		cache:false,
			     	
			     		  success : function(response)
			     		  {
			     			 var html;
			     			 if(response.length > 0)
			     				 {
			     				 
			     				 $("#mrino").val(response[0].mriNo);
		   	     				 $("#mrino").attr("disabled","disabled");
			     				 }
			     			 else{
			     				$("#mrino").val("");
		   	     				 $("#mrino").attr("disabled",false);
			     			 }
			     			 }
			     			 
			     		});
				  
			  
				   $("#mrname").focus();
				   if(mrino==null || mrino=="" || mrino=="N/A")
			  		{
			  			$("#mrino").val("");
			  			$("#mrino").attr('disabled',false);
			  		}
			  }
			  
		      //$("#mrino").focus();
	       }
	      if(status=="N")
		   {
	    	  
	    	  if(readingdate == "" || readingdate == null)
			  {
	    		  $("#readingdateVal").focus();
			  }
	    	  else{
	    		  $("#mrname").focus();
	    	  }
	    	 
	    	 
		  $("#mrino").attr('disabled',true);
		  $("#mrino").val("N/A");
           
		   }	 
		 
	   }
	  
	   function checkAvail()
	   {
	 	  $("#cmriNoErr").hide();
	 	
	 	  var operation=document.getElementById('mrino').value;
	 	/*  alert(operation); */
	 	  $.ajax(
	 	  			{
	 	  					type : "GET",
	 	  					url : "./checkDuplicate/" + operation,
	 	  					dataType : "json",
	 	  					success : function(response)
	 	  												{	
	 	  					
	 	  											    if(response==null)
	 									    	    	{
	 	  											    	//$("#cmriNoErr").hide();
	 									    	        
	 									    	    	     //document.getElementById("cmri_no").value = "";
	 									    	    	 
	 									    	    	  $("#cmriNoErr").show();
	 									    	    	  $("#cmri_no").focus();
	 									    	    	
	 								                     return true;
	 								                     }
	 	  											    else
	 	  											    {
	 	  											    	$("#cmriNoErr").hide();
	 	  											    	//document.getElementById("cmri_no_check").innerHTML = " ";
	 	  											    	return false;
	 	  											    }
	 	  											  
	 	  												}
	 	  			}); 
	   }	
	   
	   
	   var specialKeys = new Array();
	   specialKeys.push(8); //Backspace
	   specialKeys.push(9); //Tab
	   specialKeys.push(46); //Delete
	   specialKeys.push(36); //Home
	   specialKeys.push(35); //End
	   specialKeys.push(37); //Left
	   specialKeys.push(39); //Right
	   function IsAlphaNumeric(e) {
	       var keyCode = e.keyCode == 0 ? e.charCode : e.keyCode;
	       var ret = ((keyCode >= 48 && keyCode <= 57) || (keyCode >= 65 && keyCode <= 90) || (specialKeys.indexOf(e.keyCode) != -1 && e.charCode != e.keyCode));
	       document.getElementById("error").style.display = ret ? "none" : "inline";
	       return ret;
	   }
	   
	function checkNeswSeal(newSeal)
	{
		var newseal=newSeal.toString().length;
		
		 if((newSeal!='' || newSeal!=null) && newseal  > 8)
			{
			bootbox.alert("NewSeal number length cannot be greater than 8.");
		    return false;
			} 
	}
	 
</script>
<div class="page-content" >

<div class="row">
				<div class="col-md-12">
				
				
					<!-- Display Success Message -->
		           
		           
		           <c:if test = "${not empty msg}">
			         <div class="alert alert-danger display-show">
					 <button class="close" data-close="alert"></button>
					 <span style="color:red" >${msg}</span>
			       </div>
	                </c:if>
	                
	                	 <span style="color:red;display: none;" id="dataNotFoundMsg" ></span>
		          <!-- End Success Message -->
				
				
				
				
				
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Manual Reading Entry</div>
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
							
							
							
							
											
						<div class="portlet-body">
								<form:form action="./manualReadingEntryUpdate" modelAttribute="getManulReadingData" commandName="getManulReadingData" method="post" id="manualReading" name="myForm">
									<div class="form-body">
										<div class="form-group">
										<table>
											<tr>
												<td><b>Total INLTN :  ${totalinst} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
												<td><b>Total Entry :  ${totalnoi} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
												<td><b>Total Pending : ${totalpending} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
											</tr>
										</table><br><br>
										
										<table>
										<tr>
											<td>RDNG Month</td>
											<td><div class="input-group input-medium date date-picker"  data-date-format="yyyymm" data-date-viewmode="years" id="five">
																<form:input path="rdngmonth" type="text" class="form-control" name="rdngmonth" id="rdngmonth" required="true"></form:input>
																<span class="input-group-btn">
																<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span><span id="rdngmonthcheck" style="color:red;font-weight:bold;" ></span>
												</div></td>
										
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Acc No.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									    	<td><form:input path="accno" type="text" autocomplete="off" class="form-control"  name="accno" id="accno" required="true" maxlength="13" onblur="getMeterMasterData()"  tabindex="1" ></form:input>
									    		<span id="accnocheck" style="color:red;font-weight:bold;" ></span></td>
									    
										
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Meter No.</td>
									   	 	<td><form:input path="metrno" type="text" autocomplete="off" class="form-control" name="metrno" id="metrno" required="true" onblur="getMeterMasterData1()" maxlength="12" tabindex="2"></form:input>
									    	 <span id="meternocheck" style="color:red;font-weight:bold;" ></span> </td>
									</tr>
										
									<tr>
									     <td>Previous Remark &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									     <td><input type="text" class="form-control" autocomplete="off" name="prevmeterstatus1" id="prevmeterstatus" maxlength="49"></td>		
										   
										 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reading Remark &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						                 <td><form:select path="readingremark" name="readingremark" class="form-control" id="readingremark" onchange="myMeterChange()" tabindex="3">
										     <form:option value="OK">OK</form:option>
										     <c:forEach items="${readingremark}" var="element">
											<form:option value="${element.readingremark}">${element.readingremark}</form:option>
											</c:forEach>	
										    </form:select></td> 
										 	
										    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Remark &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						                    <td><form:input path="remark" name="remark" autocomplete="off" class="form-control" id="remark" tabindex="4" ondrop="return false;"></form:input>
										     <span id="remarkcheck" style="color:red;font-weight:bold;" ></span></td>
									</tr>
								</table></br>
										
								<table>
									<tr>
										 <td>Circle</td> 
									    <td><form:input path="" type="text" class="form-control"   name="circle" id="circle" disabled="true"></form:input></td>
										
									    <td>SDO Code</td>
									    <td><form:input path="sdocode" type="text" class="form-control" name="sdocode" id="sdocode" disabled="true"></form:input></td>
									   
									    <td>SDO Name</td>
									    <td><form:input path="master.oldaccno"  type="text" class="form-control"  name="sdoname" id="sdoname" value="${masterdata.sdoname}" disabled="true"></form:input></td>
									    
										<td>Tariff Code</td>
									    <td><form:input path="master.tariffcode" type="text" class="form-control"   name="master.oldtariffcode" id="oldtariffcode"  disabled="true"></form:input></td>
									</tr>
									
									<tr>
									    <td>PREVRING_KWH</td>
									    <td><form:input path="prkwh" autocomplete="off" type="text" class="form-control"  name="prkwh" id="prkwh" ></form:input></td>
									    
									    <td>CURRDNG_KWH</td> 
									    <td><form:input path="currdngkwh" autocomplete="off" type="text" class="form-control" name="currdngkwh" id="currdngkwh" tabindex="5" onblur="return getUnits1()"></form:input></td>
									    
									    <td>UNITS-1</td> 
									    <td><form:input path="unitskwh" autocomplete="off" type="text" class="form-control"  name="unitskwh" id="unitskwh"></form:input></td>
									    
									    <td>XKWH</td> 
									    <td><form:input  path="xcurrdngkwh" autocomplete="off"  type="text" class="form-control"  name="xcurrdngkwh" id="xcurrdngkwh" tabindex="21"></form:input></td>
									 </tr>
									  	
									 <tr>
									    <td>PREVRING_KVAH</td>
									    <td><form:input path="prkvah" autocomplete="off" type="text" class="form-control" name="prkvah" id="prkvah" ></form:input></td>
									  
									    <td>CURRDNG_KVAH</td> 
									    <td><form:input path="currrdngkvah" autocomplete="off" type="text" class="form-control"  name="currrdngkvah" id="currrdngkvah" tabindex="6" onblur="return getUnits2()"></form:input></td>
									    
									    <td>UNITS-2</td> 
									    <td><form:input path="unitskvah" autocomplete="off" type="text" class="form-control"   name="unitskvah" id="unitskvah"></form:input></td>
									    
									    <td>XKVAH</td> 
									    <td><form:input path="xcurrrdngkvah" autocomplete="off" type="text" class="form-control"   name="xcurrrdngkvah" id="xcurrrdngkvah" tabindex="22"></form:input></td>
									</tr>
										
									<tr>
									    <td>PREVRING_KVA</td>
									    <td> <form:input path="prkva" autocomplete="off" type="text" class="form-control"    name="prkva" id="prkva"></form:input></td>
									    
									    <td>CURRDNG_KVA</td> 
									    <td> <form:input path="currdngkva" autocomplete="off" type="text" class="form-control"   name="currdngkva" id="currdngkva" tabindex="7" onblur="return getUnits3()"></form:input></td>
									    
									    <td>UNITS-3</td> 
									    <td> <form:input path="unitskva" autocomplete="off" type="text" class="form-control"   name="unitskva" id="unitskva"></form:input></td>
									    
									    <td>XKVA</td> 
									    <td> <form:input path="xcurrdngkva" autocomplete="off" type="text" class="form-control"   name="xcurrdngkva" id="xcurrdngkva" tabindex="22"></form:input></td>
									</tr>
										
									<tr>
										<td>T1KWH</td>
									    <td><form:input path="t1kwh" autocomplete="off" type="text" class="form-control" name="t1kwh" id="t1kwh"></form:input></td>
									    
									    <td>T2KWH</td>
									    <td> <form:input path="t2kwh" autocomplete="off" type="text" class="form-control"  name="t2kwh" id="t2kwh"></form:input></td>
									  
									    <td>T3KWH</td> 
									    <td> <form:input path="t3kwh" autocomplete="off" type="text" class="form-control"   name="t3kwh" id="t3kwh"></form:input></td>
									    
									    <td>T4KWH</td> 
									    <td> <form:input path="t4kwh" autocomplete="off" type="text" class="form-control"   name="t4kwh" id="t4kwh"></form:input></td>
									</tr>
									<tr>    
									    <td>T5KWH</td> 
									    <td> <form:input path="t5kwh" autocomplete="off" type="text" class="form-control" name="t5kwh" id="t5kwh" ></form:input></td>
									
										<td>T6KWH</td>
									    <td><form:input path="t6kwh" autocomplete="off" type="text" class="form-control"   name="t6kwh" id="t6kwh"  ></form:input></td>
									    
									    <td>T7KWH</td>
									    <td> <form:input path="t7kwh" autocomplete="off" type="text" class="form-control"    name="t7kwh" id="t7kwh"></form:input></td>
									  
									    <td>T8KWH</td> 
									    <td> <form:input path="t8kwh" autocomplete="off" type="text" class="form-control"   name="t8kwh" id="t8kwh"></form:input></td>
									</tr>	
									
									<tr>
										<td>T1KVAH</td> 
									    <td> <form:input path="t1kvah" autocomplete="off" type="text" class="form-control"   name="t1kvah" id="t1kvah"></form:input></td>
									    
									    <td>T2KVAH</td> 
									    <td> <form:input path="t2kvah" autocomplete="off" type="text" class="form-control"   name="t2kvah" id="t2kvah" ></form:input></td>
										
										<td>T3KVAH</td>
									    <td><form:input path="t3kvah" autocomplete="off" type="text" class="form-control"   name="t3kvah" id="t3kvah"  ></form:input></td>
									    
									    <td>T4KVAH</td>
									    <td> <form:input path="t4kvah" autocomplete="off" type="text" class="form-control"    name="t4kvah" id="t4kvah"></form:input></td>
									</tr>
									<tr>  
									    <td>T5KVAH</td> 
									    <td> <form:input path="t5kvah" autocomplete="off" type="text" class="form-control"   name="t5kvah" id="t5kvah"></form:input></td>
									    
									    <td>T6KVAH</td> 
									    <td><form:input path="t6kvah" autocomplete="off" type="text" class="form-control"   name="t6kvah" id="t6kvah"></form:input></td>
									    
									    <td>T7KVAH</td> 
									    <td> <form:input path="t7kvah" autocomplete="off" type="text" class="form-control"   name="t7kvah" id="t7kvah" ></form:input></td>
										
									    <td>T8KVAH</td> 
									    <td> <form:input path="t8kvah" autocomplete="off" type="text" class="form-control"   name="t8kvah" id="t8kvah" ></form:input></td>
									</tr>
									
									<tr>
										<td>PF</td>
									    <td><form:input path="pf" autocomplete="off" type="text" class="form-control"   name="pf" id="pf" tabindex="8"></form:input></td>
									 	<td hidden="hide"><input type="text" name="oldxcurrdngkwh" id="oldxcurrdngkwh" /></td>
									    
									    <td>XPF</td>
									    <td><form:input path="xpf" autocomplete="off" type="text" class="form-control"   name="xpf" id="xpf" tabindex="23"></form:input></td>
									</tr>
								</table><br><br>
								
								<table>
									<tr>
										<td>OLD_Seal</td>
									    <td><form:input autocomplete="off" path="oldseal" type="text" class="form-control input-medium" name="oldseal" id="oldseal" tabindex="9" maxlength="8" ></form:input></td>
									    <td><input type="text" name="newsealval" id="newsealval" class="form-control"  tabindex="10" size="2" onblur="return getNewSeal()"></td>
									
									    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;New Seal</td>
									    <td><form:input autocomplete="off" path="newseal" type="text" class="form-control"   name="newseal" id="newseal" tabindex="11" maxlength="8" onblur="checkNeswSeal(this.value)"></form:input></td>
									    <td hidden="true"><input type="text" name="oldsel" id="oldsel" /></td>
									</tr>
									<tr>
										   <td>MRI Status</td>
						                    <td><form:select path="mrdstatus" name="mrdstatus" class="form-control input-medium" id="mrdstatus" tabindex="12" onchange="return setCmriValue(this.value)">
										      <form:option value="0">Select</form:option>
                                              <form:option value="Y">Y</form:option>
                                              <form:option value="N">N</form:option>
										   </form:select></td> <td></td>
									
										   <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reading Date</td>
										    <td><div class="input-group input-medium date date-picker" data-date-format="yyyy-mm-dd" data-date-viewmode="years">
											<form:input path="readingdate" autocomplete="off" type="text" class="form-control" name="readingdateVal" id="readingdateVal"   onchange="return MrnameBasis(this.value)"  tabindex="13"></form:input>
											<span class="input-group-btn">
											<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
											</span>
											</div></td>
									</tr>
										
									<tr>
											<td>MR Name </td>
									        <td><form:select path="mrname"  id="mrname" class="form-control  input-medium placeholder-no-fix" type="text" autocomplete="off" placeholder=""  tabindex="14">
										    <form:option value="0">select</form:option>
													<c:forEach items="${mrNames}" var="element">
													<form:option value="${element}">${element}</form:option>
													</c:forEach>	
													 <span id="cmrinocheck" style="color:red;font-weight:bold;" ></span> 
										   </form:select></td> <td></td>
										
										  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CMRI No. &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
								          <td><form:input path="mrino" name="mrino" class="form-control" id="mrino" tabindex="15"  disabled="true" onblur="checkAvail()"  onkeypress="return IsAlphaNumeric(event);" ondrop="return false;"></form:input> 
								            <span id="error" style="color: Red; display: none">* Special Characters and Small alphabets not allowed</span>
								            <span id="cmri_no_check" > <b style='color:red' id='cmriNoErr'>This CMRIno is not available</b></span>
								           </td>
									</tr> 
								</table>
										
							    <br/>
										
											<div class="modal-footer">
												<button name="cmriManualUpdate" type="submit" class="btn green" onclick="return myFormvalidation(this.form)" tabindex="16">Update</button>
												<button name="cmriManualNext"  type="button" class="btn green" onclick="myClearFunction()" tabindex="17">Next</button>
												<button name="cmriManualRefresh" onclick="return myClearFunction1()" type="button" class="btn green">Clear</button>
											</div>
										</div>
									
									</div>
									
								</form:form>	
						</div>
					</div>
		<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
			
			<!-- <div id="" style="display:none">
				<table id="">
					<thead>									
						<tr>	
						<td>AccountNo</td>	
						<td>MeterNo</td>	
         			   </tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
			</div> -->
</div>