<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>

<!-- <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> -->
<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
<script>
	$(".page-content")
			.ready(
					function() {
						
						 
						App.init();
						TableEditable.init();
						FormComponents.init();
						loadSearchAndFilter('sample_1');
						/* var chyes=$('#uniform-fdryes span').attr('class'); 
						  var chno=$('#uniform-fdrno span').attr('class');
						 if(chyes=='checked'){
							 $( "#uniform-fdryes span" ).removeClass( "checked" );
							 $( "#uniform-fdrno span" ).addClass( "checked" ); */
						$(
								'#MDMSideBarContents,#meterpointmgmt,#fdrdetails,#mpmId')
								.addClass('start active ,selected');
						$(
								'#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
								.removeClass('start active ,selected');
						//getAllFeederList();
						$('#zone').val('').trigger('change');
						$('#circle').val('').trigger('change');
						$('#town').val('').trigger('change');
						

					});
</script>

<script>
	function handlechange2(param) {

		if (param == 'crossFeederNo') {

			$("#abc").show();
			$("#abc123").hide();
			$("#abc1234").hide();
			$("#hide5").hide();
			$("#hide4").hide();

		} else {
			$("#abc").hide();
			$("#abc123").show();
			$("#abc1234").hide();
			$("#hide5").hide();
			$("#hide4").show();

		}

	}

	function handlechange4(param) {
		if (param == 'crosspointno') {
			$("#meterManufactureDivId").hide();
			$("#meterSerialDivId").hide();
			$("#mfDivId").hide();
			$("#consumerPercentage").show();

		} else {

			$("#meterManufactureDivId").show();
			$("#meterSerialDivId").show();
			$("#mfDivId").show();
			$("#consumerPercentage").hide();

		}

	}

	function optionCheck() {
		var chyes = $('#uniform-fdryes span').attr('class');
		var chno = $('#uniform-fdrno span').attr('class');

		/* $("#onno").prop("checked", true);
		 
		 $("#fdryes").prop("checked", false);
		 $( "#uniform-fdryes" ).removeClass( "span checked" );
		 var ic = $('#fdryes').prop('checked'); */
		if (chyes == 'checked') {
			$("#uniform-fdryes span").removeClass("checked");
			$("#uniform-fdrno span").addClass("checked");

		}

	}

	function hide(param) {
		optionCheck();

		$("#abc123").hide();
		$("#abc").show();
		$("#abc1234").hide();
		$("#hide4").hide();
		$("#hide5").hide();
	}

	function handlechange6(param) {
		$("#abc123").show();
		$("#abc1234").hide();
		$("#hide4").show();
		$("#hide5").hide();
	}

	function getMeterNo(param) {

		var res = param.split("-");
		var feederId = res[0];

		$.ajax({

			type : "GET",
			url : "./getFeederDetails/" + feederId,
			dataType : "json",
			success : function(response) {
				var data = response[0];

				$("#mtrsrno2").val(data[0]);
				$("#mtrmanufacturer2").val(data[1]);
				$("#cp").val(data[3]);
				$("#mf2").val(data[2]);
				$("#feederMainId").val(data[5]);
			}

		});

	}

	function validationNo(param) {
		var fdrname = $("#fdrname1").val();
		var mtrsrlno = $("#mtrsrlno1").val();
		var mtrmanufacturer = $("#mtrmanufacturer1").val();
		var mf = $("#mf1").val();
		var subdiv = $("#subdiv1").val();
		var subid = $("#subid1").val();
		var voltglvl = $("#voltglvl1").val();
		var tpfdrcode = $("#tpfdrcode").val();
		var feedertype = $("#fdrtype").val();
		var regex = /^[a-zA-Z]*$/;
		var specials=/[*|\":<>[\]{}`\\()';@&$]/;

		if (fdrname == '') {
			bootbox.alert("Enter Feeder Name");
			return false;
		}

		if (specials.test(fdrname)) { 
			bootbox.alert("Only AlphaNumeric is allowed in Feeder Name");
			return false;
		 }

		if (mtrsrlno == '') {
			bootbox.alert("Enter Meter Serial No.");
			return false;
		}

		if (mtrmanufacturer == "") {
			bootbox.alert("Enter Meter Manufacturer");
			return false;
		}

		if (!mtrmanufacturer.match(regex)) {
			bootbox.alert("Please Enter valid Meter Manufacturer ");
			return false;
		}

		if (mf == '') {
			bootbox.alert("Enter MF");
			return false;
		}

		if (isNaN(mf)) {
			bootbox.alert("MF Enter Only Numbers");
			return false;
		}
		;

		if (subdiv == "") {
			bootbox.alert("Select Subdivision");
			return false;
		}

		if (subid == "") {
			bootbox.alert("Select Substation");
			return false;
		}

		if (voltglvl == '') {
			bootbox.alert("Select Voltage Level");
			return false;
		}

		if (tpfdrcode == '') {
			bootbox.alert("Entet TP Feeder Code");
			return false;
		}

		if (feedertype == '') {
			bootbox.alert("Select Feeder Type");
			return false;
		}

		$("#addfdrdetails").submit();
	}

	function validationYes(param) {

		var mtrsrno2 = $("#mtrsrno2").val();
		var mtrmanufacturer2 = $("#mtrmanufacturer2").val();
		var mf2 = $("#mf2").val();
		var subdiv = $("#subdiv2").val();
		var regex = /^[a-zA-Z]*$/;
		var checkpointradioValue = $("input[name='optradio']:checked").val();
		var tpparentCode = $("#tpParentCode").val();

		if (checkpointradioValue == 'crosspointyes') {

			if (mtrsrno2 == '') {
				bootbox.alert("Enter Meter Serial No.");
				return false;
			}

			if (mtrmanufacturer2 == '') {
				bootbox.alert("Enter Meter Manufacturer");
				return false;
			}

			if (!mtrmanufacturer2.match(regex)) {
				bootbox.alert("Please Enter valid Meter Manufacturer ");
				return false;
			}

			if (mf2 == '') {
				bootbox.alert("Enter MF");
				return false;
			}

			if (isNaN(mf2)) {
				bootbox.alert("MF Enter Only Numbers");
				return false;
			}
			;

			if (subdiv == '') {
				bootbox.alert("Select Subdivision");
				return false;
			}
			if (tpparentCode == '') {
				bootbox.alert("Enter TP code");
				return false;
			}
		}

		else {

			var cp = $("#cp").val();
			var subdiv = $("#subdiv2").val();

			if (cp == '') {
				bootbox.alert("Enter Consumption Percentage");
				return false;
			}

			if (subdiv == '') {
				bootbox.alert("Select Subdivision");
				return false;
			}

		}

		$("#addfdrdetailsyes").submit();

	}

	//Ajax Calls 

	function getAllFeederList(){
		 var region= $('#LFzone').val();
		 var circle=$('#LFcircle').val();
		 var town=$('#LFtown').val();
		 var feedertype=$('#feederTpId').val();
		 if(region == ''){
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
		 if(feedertype == ''){
			 bootbox.alert("Please Select  Feeder Type");
			  return false;
			 }
		
		 
		 $("#imageee").show();
			$.ajax({
	         url : './getAllFeederList',
	         type : 'GET',
	         data:{
	        	 region:region,
	        	 circle:circle,
	        	 town:town,
	        	 feedertype:feedertype
	             },
	         success:function(response)
	         {
	        	 $("#imageee").hide();
	        		if(response.length == '0' ){
	    				bootbox.alert('No data found');
	    				clearTabledataContent('sample_1');
	    			}else{
	    			html="";
	    			
	    			for (var i = 0; i < response.length; i++) {
						var element = response[i];
						html += "<tr>"
						<c:if test = "${editRights eq 'yes'}">
					    +"<td><a href='#' id="+element[0]+"  onclick='edit(this.id)'  data-toggle='modal' data-target='#stack2'  >Edit</a></td>" 
					    </c:if>
					    
	/* 					+"<td>"+ (element[13] == null ? "" :element[13])+"</td>"
	 */					+"<td>"+(element[25] == null ? "" :	element[25])+ "</td>"
						+"<td>"+(element[1] == null ? "" :	element[1])+ "</td>"
						+ "<td>"+(element[22] == null ? "" : element[22])+ "</td>"
						+ "<td>"+(element[2] == null ? "" : element[2])+ "</td>"
	 					+ "<td>"+(element[3] == null ? "" :	element[3])+ "</td>"	
						+ "<td>"+(element[7] == null ? "" : element[7])+ "</td>"
						/* + "<td>"+(element[5] == null ? "" : element[5])+ "</td>"
						+ "<td>"+(element[6] == null ? "" :	element[6])+ "</td>" 
					*/
						+ "<td>"+(element[8] == null ? "" :	element[8])+ "</td>"
						+ "<td>"+(element[9] == null ? "" :	element[9])+ "</td>"
						+"<td><a target='_blank' href='./viewFeederMeterInfoMDAS?mtrno="+element[10]+"' style='color:blue;'>"+element[10]+"</a></td>"
						+ "<td>"+(element[11] == null ? "" : element[11])+ "</td>"
						+ "<td>"+(element[12] == null ? "" : element[12])+ "</td>"
						/* + "<td>"+(element[12] == null ? "" : element[12])+ "</td>" */
						 + "<td>"+(element[23] == null ? "" : element[23])+ "</td>"
						+ "<td>"+(element[24] == null ? "" : element[24])+ "</td>" 
						+ "<td>"+(element[20] == null ? "" : element[20])+ "</td>"
						+ "<td>"+(element[15] == null ? "" : element[15])+ "</td>"
						+ "<td>"+(element[16] == null ? "" : moment(element[16]).format('DD-MM-YYYY HH:mm:ss'))+   "</td>"
						+ "<td>"+(element[17] == null ? "" : element[17])+ "</td>"
						+ "<td>"+(element[18] == null ? "" : moment(element[18]).format('DD-MM-YYYY HH:mm:ss'))+  "</td>"

						
						<c:if test = "${editRights eq 'yes'}">
						/* + "<td><a href='#'  id="+element[0]+"  onclick='deletefdr(this.id)' data-toggle='modal' >Delete</a></td>" */
						+ "<td><a>Delete</a></td>"
						</c:if>
						+ "</tr>"; 
						
					}
	    			
	    			clearTabledataContent('sample_1');
	    			$("#allFeederList").html(html);
	    			loadSearchAndFilter('sample_1');
	    			
	    			}
	         }
			
		});
		}


		function getipnonipds(){

			 var region=$('#LFzone').val();
			 var circle=$('#LFcircle').val();
			 var town=$('#LFtown').val();
			 
			 var checkboxes = document.getElementsByName('ipdsnon');
			 var selected = [];

			 for (var i=0; i<checkboxes.length; i++) {
				    if (checkboxes[i].checked) {
				        selected.push(checkboxes[i].value);
				    }
				}
			
			 $("#imageee").show();
				$.ajax({
		         url : './getAllipdsnonipdsFeederList',
		         type : 'GET',
		         data:{
		        	 region:region,
		        	 circle:circle,
		        	 town:town,
		        	  checked: JSON.stringify(selected),
		             },
		         success:function(response)
		         {
		        	 	
		        	 $("#imageee").hide();
		        		if(response.length == '0' ){
		    				bootbox.alert('No data found');
		    				clearTabledataContent('sample_1');
		    			}else{
		    			html="";
		    			
		    			for (var i = 0; i < response.length; i++) {
							var element = response[i];
							html += "<tr>"
							<c:if test = "${editRights eq 'yes'}">
						    +"<td><a href='#' id="+element[0]+"  onclick='edit(this.id)'  data-toggle='modal' data-target='#stack2'  >Edit</a></td>" 
						    </c:if>
						    
		/* 					+"<td>"+ (element[13] == null ? "" :element[13])+"</td>"
		 */					
							+"<td>"+(element[1] == null ? "" :	element[1])+ "</td>"
							+ "<td>"+(element[2] == null ? "" : element[2])+ "</td>"
							+ "<td>"+(element[3] == null ? "" :	element[3])+ "</td>"
							+ "<td>"+(element[4] == null ? "" : element[4])+ "</td>"
							+ "<td>"+(element[7] == null ? "" : element[7])+ "</td>"
							/* + "<td>"+(element[5] == null ? "" : element[5])+ "</td>"
							+ "<td>"+(element[6] == null ? "" :	element[6])+ "</td>" 
						*/
							+ "<td>"+(element[8] == null ? "" :	element[8])+ "</td>"
							+ "<td>"+(element[9] == null ? "" : element[9])+ "</td>"
							+ "<td>"+(element[10] == null ? "" : element[10])+ "</td>"
							+ "<td>"+(element[11] == null ? "" : element[11])+ "</td>"
							/* + "<td>"+(element[12] == null ? "" : element[12])+ "</td>" */
							+ "<td>"+(element[14] == null ? "" : element[14])+ "</td>"
							+ "<td>"+(element[15] == null ? "" : moment(element[15]).format('DD-MM-YYYY HH:mm:ss'))+   "</td>"
							+ "<td>"+(element[16] == null ? "" : element[16])+ "</td>"
							+ "<td>"+(element[17] == null ? "" : moment(element[17]).format('DD-MM-YYYY HH:mm:ss'))+  "</td>"
							+ "<td>"+(element[19] == null ? "" : element[19])+ "</td>"
							<c:if test = "${editRights eq 'yes'}">
							+ "<td><a href='#'  id="+element[0]+"  onclick='deletefdr(this.id)' data-toggle='modal' >Delete</a></td>"
							</c:if>
							+ "</tr>"; 
							
						}
		    			
		    			clearTabledataContent('sample_1');
		    			$("#allFeederList").html(html);
		    			loadSearchAndFilter('sample_1');
		    			
		    			}
		         }
				
			});


	}

	function edit(param) {
		$("#fdrrid").hide();
		var id = param;
		if (id == "" || id == null) {
			bootbox.alert("Empty Data");
			return false;
		}

		$.ajax({
			type : "GET",
			url : "./editfeederdetails/" + id,
			dataType : "json",
			success : function(response) {
				var data = response[0];
				$("#editfdrname").val(data[0]);
				$('#editvoltagelvl').val(data[1]).trigger('change');
				// $("#editvoltagelvl").val(data[1]);
				$("#editdtsubstation").val(data[2]);
				$("#editfdrcode").val(data[3]);
				$("#edittpprntcode").val(data[4]);
				$("#editmtrno").val(data[5]);
				$("#editmf").val(data[6]);
				$("#editConsumptionper").val(data[7]);
				$("#editfdrId").val(data[8]);
				$("#hiddenMeterNo").val(data[5]);
				$("#hiddeMF").val(data[6]);
				$('#efdrtype').val(data[9]).trigger('change');
				$("#editfdrlat").val(data[10]);
				$("#editfdrlong").val(data[11]);
				$("#editmanufac").val(data[12]);
				// $("#efdrtype").val(data[9]);
			}
		});
	}

	function deletefdr(param) {

		//alert(param); 
		$("#fdrrid").hide();
		var id = param;
		var url = "./fdrdata"
		if (id == "" || id == null) {
			bootbox.alert("Empty Data");
			return false;
		}
		bootbox
				.confirm(
						"Are you sure want to delete this record ?",
						function(confirmed) {
							if (confirmed == true) {
								$
										.ajax({
											type : "GET",
											url : "./deletefeederdetails",
											dataType : "text",
											data : {
												id : id
											},
											success : function(response) {
												//alert(response)
												if (response == "deleted") {
													bootbox
															.alert("Record Deleted");
													getAllFeederList();
												} else {
													bootbox
															.alert("Record cannot be deleted as feeder is attached to Substation/DT/Consumer")
												}

											}

										});
							}
						});
	}

	function modifyNew() {

		var editfdrname = $("#editfdrname").val();
		var editvoltagelvl = $("#editvoltagelvl").val();
		var editfdrcode = $("#editfdrcode").val();
		var edittpprntcode = $("#edittpprntcode").val();
		var editmtrno = $("#editmtrno").val();
		//alert(editmtrno);
		var editmf = $("#editmf").val();
		/*   var editConsumptionper=$("#editConsumptionper").val(); */
		var editSubstation = $("#editdtsubstation").val();
		var meterChangeDate = $("#selectedFromDateId").val();
		var editlat=$("#editfdrlat").val();
		var editlong=$("#editfdrlong").val();
		var editmanufac=$("#editmanufac").val();
		

		if (editfdrname == '') {
			bootbox.alert("Enter Feeder Name");
			return false;
		}

		if (editvoltagelvl == '') {
			bootbox.alert("Select Voltage Level");
			return false;
		}

		/*   if(editConsumptionper=='') 
			{
				bootbox.alert("Enter Consumption percentage");
				return false;
			} */

		if (editmtrno == '') {
			bootbox.alert("Enter Meter No");
			return false;
		}

		if (editmf == '') {
			bootbox.alert("Enter MF value");
			return false;
		}
		if(editmanufac == ''){
			bootbox.alert("Enter Manufacturer");
			return false;
		}

		if (isMeterNoChanged == 'yes') {
			if (meterChangeDate == '') {
				bootbox.alert('Enter Meter Change Date.');
				return false;
			} else {
				$('#Modifyfdrdetails').attr("action", "./Modifyfeederdetails")
						.submit();
			}
		} else {
			$('#Modifyfdrdetails').attr("action", "./Modifyfeederdetails")
					.submit();
		}
	}
	// Below code is to validate mtr no change
	/* $.ajax({
		type : "GET",
				url : "./checkMeterNoexistance",
				dataType : "json",
				data:{
					meterno:editmtrno
				},
				success : function(response)
				{
					if(response != '0')
				
				{
					bootbox.alert("Entered Meter No is already Assigned");
					$("#mtrsrlno1").val("");
					return false;
				}else{
				
					$.ajax(
				 			{
	 					type : "GET",
	 					url : "./validateMeterfromMM" ,
	 					dataType : "json",
	 					data : {
	 						meterno:editmtrno
	 					},
	 					success : function(response)
		 					{
						if(response.length == '0')
						{
							bootbox.alert("Please enter valid meter No.");
							$("#editMeterId").val("");
							return false;
		 				}else{
		 					 $('#Modifyfdrdetails').attr("action","./Modifyfeederdetails").submit();
		 				
	 				}
	 			}
			});
				}
			}
		}); */

	function checkMtrNoCFNO(meterno) {

		$.ajax({
			type : "GET",
			url : "./checkMeterNoexistance",
			dataType : "json",
			data : {
				meterno : meterno
			},
			success : function(response) {
				if (response != '0') {
					bootbox.alert("Entered Meter No is already Assigned");
					$("#mtrsrlno1").val("");
					return false;
				} else {
					$.ajax({
						type : "GET",
						url : "./validateMeterfromMM",
						dataType : "json",
						data : {
							meterno : meterno
						},
						success : function(response) {

							if (response.length == '0') {
								bootbox.alert("Please enter valid meter No.");

								$("#mtrsrlno1").val("");

								return false;
							} else {

								$("#mtrmanufacturer1").val(response[0][1]);
							}
						}
					});
				}
			}
		});
	}

	function checkMtrNoCFYES(meterno) {

		$.ajax({
			type : "GET",
			url : "./checkMeterNoexistance",
			dataType : "json",
			data : {
				meterno : meterno
			},
			dataType : "json",
			success : function(response) {
				if (response != '0') {
					bootbox.alert("Entered Meter No is already Assigned");
					$("#mtrsrno2").val("");
					return false;
				} else {
					$.ajax({
						type : "GET",
						url : "./validateMeterfromMM",
						dataType : "json",
						data : {
							meterno : meterno
						},
						success : function(response) {
							if (response.length == '0') {
								bootbox.alert("Please enter valid meter No.");
								$("#mtrsrlno2").val("");
								return false;
							} else {
								$("#mtrmanufacturer2").val(response[0][1]);

							}
						}
					});
				}
			}
		});
	}

	/*  function checkFeederCode(fdrCode)
	 {
	
	 $.ajax(
	 {
	 type : "GET",
	 url : "./checkFeederCode/" + fdrCode,
	 dataType : "json",
	 success : function(response)
	 {
	 if(response != '0')
	 {
	 bootbox.alert("Entered TP Feeder Code is already Assigned");
	 $("#tpfdrcode").val("");
	 return false;
	 }
	
	 }
	 });
	 } */

	function showSubdivByCircle(circle) {

		$.ajax({
			url : './showSubdivByCircle' + '/' + circle,
			type : 'GET',
			dataType : 'json',
			success : function(response) {

				var html = '';
				html += "<option value=''></option>";
				for (var i = 0; i < response.length; i++) {
					html += "<option  value='"+response[i]+"'>" + response[i]
							+ "</option>";
				}
				$("#subdiv2").html(html);
				$("#subdiv3").html(html);

			}
		});
	}

	/* function showTownbySubdiv(sitecode) {
	    // var zone = $('#zone').val();
	   
	     var circle = $('#circle').val();
	     $.ajax({
	         url : './getTownNameandCodeBySubDiv',
	         type : 'GET',
	         dataType : 'json',
	         asynch : false,
	         cache : false,
	         data : {
	          sitecode:sitecode
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
	
	
	} */

	function checkMtrNoEDIT(meterno) {

		$.ajax({
			type : "GET",
			url : "./checkMeterNoexistance",
			dataType : "json",
			data : {
				meterno : meterno
			},

			success : function(response) {
				if (response != '0') {
					bootbox.alert("Entered Meter No is already Assigned");
					$("#editMeterId").val("");
					return false;
				} else {
					$.ajax({
						type : "GET",
						url : "./validateMeterfromMM",
						dataType : "json",
						data : {
							meterno : meterno
						},
						success : function(response) {

							if (response.length == '0') {
								bootbox.alert("Please enter valid meter No.");

								$("#editMeterId").val("");
								return false;
							}
						}
					});
				}

			}
		});
	}

	function editMFValidations(mfValue) {

		var hiddenMFNo = $("#hiddeMF").val();

		if (hiddenMFNo != mfValue) {

			$("#MFChangeDate").show();
		} else {
			$("#MFChangeDate").hide();

		}

	}
	var isMeterNoChanged = 'no';
	function editMeterValidations(meterNo) {
		var hiddenMeterNo = $("#hiddenMeterNo").val();
		var exsitingMeterNo = meterNo;

		if (hiddenMeterNo != exsitingMeterNo) {

			$
					.ajax({
						type : "GET",
						url : "./checkMeterNoexistance",
						dataType : "json",
						data : {
							meterno : exsitingMeterNo
						},
						success : function(response) {
							if (response != '0') {
								bootbox
										.alert("Entered Meter No is already Assigned");
								$("#editmtrno").val(hiddenMeterNo);
								return false;
							} else {
								$
										.ajax({
											type : "GET",
											url : "./validateMeterfromMM",
											dataType : "json",
											data : {
												meterno : exsitingMeterNo
											},
											success : function(response) {

												if (response.length == '0') {
													bootbox
															.alert("Please enter valid meter No or Meter no Instock.");

													$("#editmtrno").val(
															hiddenMeterNo);
													return false;
												} else {
													$("#meterChageDate").show();
													isMeterNoChanged = 'yes';
												}
											}
										});
							}

						}
					});
		} else {
			$("#meterChageDate").hide();
			isMeterNoChanged = 'no';
		}
	}

	function clearfieldsNew() {

		document.getElementById("fdrname1").value = '';
		document.getElementById("mtrsrlno1").value = '';
		document.getElementById("mf1").value = '';

		document.getElementById("tpfdrcode").value = '';
		document.getElementById("tpprntcode").value = '';
		$("#subdiv1").val("").trigger("change");
		$("#subid1").val("").trigger("change");
		$("#voltglvl1").val("").trigger("change");

	}

	/*  function showSSnameBySubdiv(subdivision)
	{
		
		$.ajax({
				url : './showSSnameBySubdiv',
			    	type:'GET',
			    	dataType:'json',
			    	data : {
			              
			        	  subdivision : subdivision
			          },
			    	success:function(response)
			    	{
			    		
			    		var html='';
			    		html+="<option value=''></option>";
						for( var i=0;i<response.length;i++)
						{
							html+="<option  value='"+response[i][0]+"'>"+response[i][1]+"</option>";
						}
						$("#subid1").html(html);
						$("#subid2").html(html);
						
						showTownbySubdiv(subdivision);
			    	}
				});
		} */

	function showSSnameByParentTown(TownCode) {

		$
				.ajax({
					url : './getSubStationsByTownCode',
					type : 'GET',
					dataType : 'json',
					data : {

						towncode : TownCode
					},
					success : function(response) {

						var html = '';

						html += "<select id='subid1' name='subid1' class='form-control select2me input-medium' type='text'><option value=''>Select SubStation</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option  value='"+response[i][0]+"-"+response[i][1]+"'>"
									+ response[i][1] + "</option>";
						}
						$("#subid1").html(html);

						$('#subid1').select2();

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
									+ response1[i][0] + "-" + response1[i][1]
									+ "</option>";
						}
						html += "</select><span></span>";
						$("#ParentTown1").html(html);
						$('#ParentTown1').select2();
						$('#subid1').select2();
						/* $("#subid1 option").remove(); */
					}
				});

	}

	function showfdrIdNameBySubdivAndSSname(parentid) {

		$.ajax({
			url : './showfdrIdNameBySubdivAndSSname' + '/' + parentid,
			type : 'GET',
			dataType : 'json',
			success : function(response) {

				var html = '';
				html += "<option value=''></option>";
				for (var i = 0; i < response.length; i++) {
					html += "<option  value='"+response[i]+"'>" + response[i]
							+ "</option>";
				}
				$("#fdr1").html(html);
				$("#fdr2").html(html);

			}
		});
	}

	function checkFdrNoExist(fdrcode) {
		$
				.ajax({
					type : "GET",
					url : "./checkFdrNoexistance",
					dataType : "json",
					data : {
						fdrcode : fdrcode
					},
					success : function(response) {
						if (response != '0') {
							bootbox
									.alert("Entered TP Feeder Code is already Assigned");
							$("#tpfdrcode").val("");
							return false;
						} else {
							$("#tpfdrcode").val();
						}
					},

				})

	}
</script>


<div class="page-content">
	<c:if test="${not empty msg}">
		<div class="alert alert-success display-show">
			<button class="close" data-close="alert"></button>
			<span style="color: green">${msg}</span>
		</div>
	</c:if>
<c:if test = "${editRights eq 'yes'}">
	<div class="row">
		<div class="col-md-12">

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-cogs"></i>Bulk Edit(Feeder)
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
					</div>
				</div>
				<div class="portlet-body">

					<form action="./uploadFeederFile" id="uploadFile"
						enctype="multipart/form-data" method="post">

						<div class="form-group">
							<div class="row" style="margin-left: -1px;">
								<div class="col-md-6">
									<div>
										<label for="exampleInputFile1">Only Upload xlsx File(<a
											href='#' id='feederSample' onclick='downLoadSampleExcel()'>Download
												Sample-File</a>)
										</label> <input type="file" id="fileUpload"
											onchange="ValidateSingleInput(this);" name="fileUpload"><br />

										<button class="btn blue pull-left" style="display: block;"
											id="uploadButton" onclick="return finalSubmit();">Upload
											File</button>
										&nbsp;
									</div>
								</div>
								<div class="col-md-6">
									<div style="margin-left: 0px;">
										<label><b>Note:-</b></label>

										<ol>
											<li>User can able to do bulk Update Only.</li>
											<li>User can able to update only
												Voltage_Level,Meter_Manufacturer,MF,Latitude,Longitude and Feeder_Type.</li>
											<li>Voltage_Level,Latitude,Longitude and MF should be Number.</li>
											<li>User can able to bulk edit Feeder Name also.</li>

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
						<i class="fa fa-edit"></i>Feeder Details
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
					<c:if test = "${editRights eq 'yes'}">
						<div class="btn-group">
							<button id="addData" class="btn green" data-toggle="modal"
								data-target="#stack1" onclick="return hide();">
								Add Feeder <i class="fa fa-plus"></i>
							</button>
						</div>
					</c:if>
			
				<%-- 	<div class="row">

						<div class="col-md-4">
							<div id="ZoneTd" class="form-group">
								<label class="control-label">Region:</label> <select
									class="form-control select2me" id="zone" name="zone"
									onchange="showCircle(this.value);">
									<option value="">Select Region</option>
									<option value="%">ALL</option>
									<c:forEach var="elements" items="${zoneList}">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>
						</div>


						<div class="col-md-4">
							<div id="circleTd" class="form-group">
								<label class="control-label">Circle:</label> <select
									class="form-control select2me" id="circle" name="circle"
									onchange="showTownBySubdiv(this.value);">
									<option value="">Select Circle</option>
									<option value="ALL">ALL</option> 
									<c:forEach items="${circleList}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="col-md-4">
							<div id="townTd" class="form-group">
								<label class="control-label">Town:</label> <select
									class="form-control select2me" id="town" name="town">
									<option value="">Select Town</option>
									<option value="ALL">ALL</option> 
									<c:forEach items="${townList}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>

						</div>
					</div> --%>
			<jsp:include page="locationFilter.jsp"/> 
			
				<div class="row" style="margin-left: -1px;">
				<div class="col-md-3">
						<div id="feederDivId" class="form-group">
							<label class="control-label"><strong>Feeder Type:</strong></label> 
							<select
								class="form-control select2me input-medium" id="feederTpId"
								name="feederTpId">
								<option value="">Select Feeder Type</option>
								<option value="%">All</option>
								<option value="IPDS">IPDS</option>
								<option value="NON-IPDS">NON-IPDS</option>
								<option value="GC">GC</option>
								<option value="LV">LV</option>
								<option value="OTHERS">OTHERS</option>
							</select>
						</div>
					</div>
						
					<div class="row">
					
						<div class="col-md-4">
							<div class="tabbable tabbable-custom">
								<div id="showFeederData">
									<button type="button" id="viewFeederData"
										style="margin-top: 25px;" onclick="getAllFeederList();"
										name="viewFeederData" class="btn yellow">
										<b>View</b>
									</button>
									<br />
								</div>
							</div>
						</div>

						<!-- <div class="col-md-2">
							<input type="checkbox" name="ipdsnon" id="ipdsId" value="ipds"
								checked onclick="getipnonipds(this.value)">IPDS<br>
						</div>

						<div class="col-md-2">
							<input type="checkbox" name="ipdsnon" id="nonipdsId"
								value="nonipds" checked onclick="getipnonipds(this.value)">NON-IPDS<br>
						</div>
 -->
 
						<!-- <div class="radio-inline" >
                         <label><input type="radio" name="ipdsnon" id="ipdsId"  value="ipds" checked onclick="getipnonipds()"; >IPDS</label>
                       	</div>
                       	
                       	<div class="radio-inline" >
                        <label><input type="radio" name="ipdsnon" id="nonipdsId" value="nonipds" checked onclick="getipnonipds()"; >NON-IPDS</label>
                       	 </div> -->

					</div>
					<!-- <div class="row"> -->
						<div style="margin-top: 20px; margin-right: 15px;">
						<div class="btn-group pull-right">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> -->
								<li><a href="#" id=""
									onclick="exportPDF('sample_1','Feeder Details')">Export to
										PDF</a></li>
								<li><a href="#" id="" onclick="exportExcelFeeder()">Export
										to Excel</a></li>
							</ul>
						</div>

					<table class="table table-striped table-hover table-bordered"
						id="sample_1">
						<thead>
							<tr>
								<c:if test = "${editRights eq 'yes'}">
									<th>Edit</th>
								</c:if>
								
								<th>Region</th>
								<th>Circle Name</th>
								<th>Town Name</th>
								<th>Feeder Name</th>
								<th>Voltage Level</th>							
								
								<!-- 						<th>Subdivision Code</th>
								
								<th>Parent</th>
								<th>TP Feeder Code</th>
 
 -->
								<th>Feeder Code</th>
								<th>Sub station Code</th>
								<th>Sub station Name</th>
								<th>Meter Sr Number</th>
								<th>Meter Manufacturer</th>
								<th>MF</th>
								<th>Latitude</th>
								<th>Longitude</th>
								<th>Feeder Type</th>
								<!-- <th>Consumption Percentage</th> -->
								<th>Entry By</th>
								<th>Entry Date</th>
								<th>Update By</th>
								<th>Update Date</th>
								
							<c:if test = "${editRights eq 'yes'}">
									<th>Delete</th>
								</c:if>
							</tr>
						</thead>
						<tbody id="allFeederList"></tbody>
					</table>
					</div>
					<!-- </div> -->
					<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
							style="width: 4%; height: 4%;">
					</div>
				</div>
			</div>
		</div>
	</div>


<!-- BELOW CODE IS TO ADD NEW FEEDER -->


<div id="stack1" class="modal fade" role="dialog"
	aria-labelledby="myModalLabel10" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h4 class="modal-title">
					<b>Add Feeder</b>
				</h4>
			</div>

			<div class="modal-body">
				<form action="./addfdrdetailsno" id="addfdrdetails" method="POST">
					<label style="visibility: hidden"><b>Cross Feeder:</b></label>

					<div class="radio-inline" style="visibility: hidden">
						<label><input type="radio" name="crosspoint" id="fdryes"
							value="crossFeederYes" onclick="handlechange2(this.value);">YES</label>
					</div>

					<div class="radio-inline" style="visibility: hidden">
						<label><input type="radio" name="crosspoint" id="fdrno"
							value="crossFeederNo" checked="checked"
							onclick="handlechange2(this.value);">NO</label>
					</div>

					<div id="abc">

						<div class="row">
							<div class="col-md-6">
								Feeder Name:<font color="red">*</font><input type="text"
									class="form-control" name="fdrname1" id="fdrname1"
									placeholder="Enter Feeder Name">
							</div>
							<div class="col-md-6">
								Meter Serial No:<font color="red">*</font><input type="text"
									class="form-control" name="mtrsrlno1" id="mtrsrlno1"
									placeholder="Enter mtr Serial No"
									onchange="return checkMtrNoCFNO(this.value)">
							</div>
						</div>
						<br>



						<div class="row">
							<div class="col-md-6">
								Meter Manufacturer:<font color="red">*</font><input type="text"
									class="form-control" name="mtrmanufacturer1"
									readonly="readonly" id="mtrmanufacturer1"
									placeholder="Enter mtr Manufacturer">
							</div>
							<div class="col-md-6">
								MF:<font color="red">*</font><input type="number" min='1'  onkeypress="return (event.charCode == 8 || event.charCode == 0) ? null : event.charCode >= 48 && event.charCode <= 57"
									onchange="checkDecimal(this.value)" class="form-control"
									name="mf1" id="mf1" placeholder="Enter MF">
							</div>
						</div>
						<br>



						<div class="row">
							<div class="col-md-6">
								SubDivision:<font color="red">*</font><select
									class="form-control select2me" name="subdiv1" id="subdiv1"
									data-placeholder="Select SubDivision"
									onchange="showTownbySubdiv(this.value)">
									<option value="">Select SubDivision</option>
									<c:forEach var="elements" items="${subdivisionList}">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>

							<div class="col-md-6">
								Town :<font color="red">*</font><select
									class="form-control select2me" name="ParentTown1"
									id="ParentTown1" onchange="showSSnameByParentTown(this.value)"
									data-placeholder="Select Town">
									<option value="">Select Town</option>
								</select>
							</div>

						</div>
						<br>


						<div class="row">

							<div class="col-md-6">
								SubStation:<font color="red">*</font><select
									class="form-control select2me" name="subid1" id="subid1"
									data-placeholder="Select SubStation"
									onchange="getSSTPCode(this.value)">
									<option value="">Select SubStation</option>
								</select>
							</div>

							<div class="col-md-6">
								Voltage Level:<font color="red">*</font><select
									class="form-control select2me" name="voltglvl1" id="voltglvl1"
									data-placeholder="Select Voltage Level">
									<option value="">Select Voltage</option>
									<option value="11000">11000</option>
									<option value="22000">22000</option>
									<option value="33000">33000</option>
									<option value="110000">110000</option>
								</select>
							</div>


						</div>
						<br>

						<div class="row">
							<div class="col-md-6">
								TP Feeder Code:<font color="red">*</font><input type="text"
									class="form-control" name="tpfdrcode" id="tpfdrcode"
									placeholder="Enter TP Feeder Code"
									onchange="return checkFdrNoExist(this.value)">
							</div>
							<div class="col-md-6">
								TP Parent Code:<font color="red">*</font><input type="text"
									class="form-control" name="tpprntcode" id="tpprntcode"
									placeholder="Enter TP Parent Code">
							</div>
						</div>

						<div class="row">
							<div class="col-md-6">
								Feeder Type:<font color="red">*</font><select
									class="form-control select2me" name="fdrtype" id="fdrtype"
									data-placeholder="Select Feeder Type">
									<option value=""></option>
									<option value="IPDS">IPDS</option>
									<option value="NON-IPDS">NON-IPDS</option>
								</select>
							</div>
						</div>

						<div class="modal-footer">
							<button class="btn blue pull-right" id="addfdrOption"
								type="button" onclick="validationNo()">Add</button>
							<button class="btn red pull-left" data-dismiss="modal"
								onclick="clearfieldsNew();" class="btn">Close</button>
						</div>
					</div>
				</form>
			</div>

			<br>
		</div>
	</div>
</div>


<!-- Below Code is to Edit feeder Details -->

<div id="stack2" class="modal fade" role="dialog"
	aria-labelledby="myModalLabel10" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"></button>
				<h4>
					<b>Edit Feeder Details</b>
				</h4>
			</div>
			<div class="modal-body">
				<form action="./Modifyfeederdetails" method="post"
					id="Modifyfdrdetails">
					<input type="hidden" id="hiddenMeterNo" name="hiddenMeterNo"></input>
					<input type="hidden" id="hiddeMF" name="hiddeMF"></input>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label">Feeder Name<font
									color="red">*</font></label> <input type="text" id="editfdrname"
									class="form-control placeholder-no-fix"
									placeholder="Enter feeder Name" name="editfdrname"
									maxlength="50"></input>
							</div>
						</div>

						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label">Voltage Level<font
									color="red">*</font></label> <select
									class="select2_category form-control select2me"
									name="editvoltagelvl" id="editvoltagelvl"
									data-placeholder="Select voltage level" tabindex="1">
									<option value="">Select voltage</option>
									<option value="11000">11000</option>
									<option value="22000">22000</option>
									<option value="33000">33000</option>
									<option value="110000">110000</option>
									<!--  <option value="11000">11000</option> -->
								</select>
							</div>
						</div>
					</div>

					<div class="row">


						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label">TP Feeder Code</label> <input
									type="text" id="editfdrcode"
									class="form-control placeholder-no-fix"
									placeholder="Enter feeder code" name="editfdrcode"
									maxlength="12" readonly="readonly"></input>
							</div>
						</div>


						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label"> TP Parent Code</label> <input
									type="text" id="edittpprntcode"
									class="form-control placeholder-no-fix"
									placeholder="Enter TP parent Code" name="edittpprntcode"
									maxlength="12" readonly="readonly"></input>
							</div>
						</div>
					</div>

					<div class="row">


						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label">Meter Serial No.<font
									color="red">*</font></label> <input type="text"
									class="form-control placeholder-no-fix"
									placeholder="Enter Meter Sr No." id="editmtrno"
									onchange="return editMeterValidations(this.value);"
									name="editmtrno" maxlength="12" readonly="readonly"></input>
							</div>
						</div>


						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label">Feeder Type:<font
									color="red">*</font></label> <select class="form-control select2me"
									name="efdrtype" id="efdrtype"
									data-placeholder="Select Feeder Type">
									<option value="">Select Feeder Type</option>
									<option value="IPDS">IPDS</option>
									<option value="NON-IPDS">NON-IPDS</option>
									<option value="GC">GC</option>
									<option value="LV">LV</option>
									<option value="OTHERS">OTHERS</option>
								</select>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label">Latitude</label> <input type="number" min="0" onkeypress="return isNumberKey(event)" id="editfdrlat"
									class="form-control placeholder-no-fix"
									placeholder="Enter latitude" name="editfdrlat"
									maxlength="50"></input>
							</div>
							</div>
							<div class="col-md-6">
							<div class="form-group">
								<label class="control-label">Longitude</label> <input type="number" min="0" onkeypress="return isNumberKey(event)" id="editfdrlong"
									class="form-control placeholder-no-fix"
									placeholder="Enter longitude" name="editfdrlong"
									maxlength="50"></input>
							</div>
							</div>



						<div class="col-md-6" id="">
							<div class="form-group">
								<label class="control-label">MF<font color="red">*</font></label>
								<input type="number" min="0" onkeypress="return (event.charCode == 8 || event.charCode == 0) ? null : event.charCode >= 48 && event.charCode <= 57" id="editmf"
									class="form-control placeholder-no-fix" placeholder="Enter Mf"
									onchange="editMFValidations(this.value)" name="editmf"
									maxlength="12"></input>
							</div>
						</div>
						
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label">Meter Manufacturer</label> <input type="text" id="editmanufac"
									class="form-control placeholder-no-fix"
									placeholder="Enter Manufacturer" name="editmanufac"
									maxlength="50"></input>
							</div>
							</div>

					</div>

					<div class="row">
						<div class="col-md-6" id="meterChageDate" hidden="true">
							<label class="control-label">Meter Change Date<font
								color="red">*</font></label>
							<div class="input-group input-medium date date-picker"
								data-date-end-date="-1d" data-date-format="yyyy-mm-dd"
								data-date-viewmode="years">
								<input type="text" autocomplete="off" class="form-control"
									name="selectedMeterDateName" id="selectedFromDateId">
								<span class="input-group-btn">
									<button class="btn default" type="button" id="">
										<i class="fa fa-calendar"></i>
									</button>
								</span>
							</div>
						</div>




						<div class="col-md-6" id="MFChangeDate" hidden="true">
							<label class="control-label">MF Change Date</label>
							<div class="input-group input-medium date date-picker"
								data-date-end-date="-1d" data-date-format="yyyy-mm-dd"
								data-date-viewmode="years">
								<input type="text" autocomplete="off" class="form-control"
									name="selectedMFDateName" id="selectedMFDateId"> <span
									class="input-group-btn">
									<button class="btn default" type="button" id="">
										<i class="fa fa-calendar"></i>
									</button>
								</span>
							</div>

						</div>
						<div class="col-md-6" id="fdrrid" hidden="true">
							<div class="form-group">
								<label class="control-label">Feeder Id</label> <input
									type="text" id="editfdrId"
									class="form-control placeholder-no-fix" value=""
									placeholder="Enter Feeder ID" name="editfdrId" maxlength="12"></input>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button class="btn red pull-left" type="button"
							data-dismiss="modal">Cancel</button>
						<button class="btn blue pull-right" id="modify" type="button"
							onclick="modifyNew()">Update</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<!-- Do Not delete below code -->
<%-- <form action="./addfdrdetailsyes" id="addfdrdetailsyes" method="POST">
                       	<div id="abc123">
                       	<div class="row">
                  
                  
                  		<input type="hidden"  class="form-control" name="feederMainId" id="feederMainId">
                      
                  
                  
                       	<div class="col-md-6">
                       Parent Circle:<select class="form-control select2me" name="circle" id="circle" data-placeholder="Select Circle" onchange="showSubdivByCircle(this.value)">
                       	<option value="">Select Circle</option>
                       	<c:forEach var="elements" items="${circleList}">
						<option value="${elements}">${elements}</option>
						</c:forEach>
                       	</select>
                       	</div>
                       	
                       	
                      <div class="col-md-6">
                      <label><b>Cross Point Metered:</b></label>
			          <div class="radio-control">
                      <label><input type="radio" name="optradio" value="crosspointyes" checked="checked" onclick="handlechange4(this.value);">YES</label>
                      <label><input type="radio" name="optradio" value="crosspointno" onclick="handlechange4(this.value);">NO</label>
                      </div>
                      </div>
                      </div><br>
                       	
                      <div class="row">
                      <div class="col-md-6">
                   		 Parent  SubDivision:<font color="red">*</font><select class="form-control select2me" name="subdiv2" id="subdiv2" data-placeholder="Select SubDivision" onchange="showSSnameBySubdiv(this.value)" >
                   	  <option value="">Select SubDivision</option>
                      <c:forEach var="elements" items="${subdivisionList}">
				      <option value="${elements}">${elements}</option>
					  </c:forEach> 
                      </select>
                      </div>
                       
                      <div class="col-md-6"  id="meterSerialDivId">
                      Meter Sr No:<font color="red">*</font><input type="text" class="form-control" name="mtrsrlno2" id="mtrsrno2"  placeholder="Enter Mtr Sr No" onchange="return checkMtrNoCFYES(this.value)">
                      </div>
                      
                      <div class="col-md-6" id="consumerPercentage" hidden="true">
             		     Consumption Percentage:<font color="red">*</font><input type="text" class="form-control" name="cp" id="cp" placeholder="0.00">
              		    </div><br>
                      </div><br>
                      
                       
                       
                      <div class="row">
                      <div class="col-md-6">
                   	 Parent SubStation:<font color="red">*</font><select class="form-control select2me" name="subid2" id="subid2" data-placeholder="Select SubStation" onchange="showfdrIdNameBySubdivAndSSname(this.value)">
                      <option value="">Select SubStation</option>
                      </select>
                      </div>
                       	
                      <div class="col-md-6" id="meterManufactureDivId" >
                      Meter Manufacturer:<font color="red">*</font><input type="text" class="form-control" name="mtrmanufacturer2" readonly="readonly" id="mtrmanufacturer2" placeholder="Enter Mtr Manufacturer">
                      </div>
                      </div><br>
                       
                       <div class="row">
                       <div class="col-md-6">
                       Parent Feeder:<select class="form-control select2me" name="fdr1" id="fdr1"    data-placeholder="Select Feeder"><!-- onchange="getMeterNo(this.value)" -->
                       <option value="">Select Feeder Id-Name</option> 
                       </select>
                       </div>
                       	
                      <div class="col-md-6" id="mfDivId">
                     Current MF:<font color="red">*</font><input type="text" class="form-control" name="mf2" id="mf2" placeholder="Enter MF">
                      </div>
                      </div><br>
                     
                     
                     
                      <div class="row">
                   
                      
                      <c:if test = "${userType eq 'ADMIN' && officeType ne 'subdivision'}"> 
                      <div class="col-md-6">
              		  Cross Feeder SubDivision:<font color="red">*</font><select class="form-control select2me" name="crossFeedersubDiv" id="crossFeedersubDiv" data-placeholder="Select SubDivision" onchange="showSSnameBySubdiv(this.value)" >
                   	  <option value="">Select SubDivision</option>
                      <c:forEach var="elements" items="${subdivisionList}">
				      <option value="${elements}">${elements}</option>
					  </c:forEach> 
                      </select>
                      </div>
                       	</c:if>
                       	
                       	
                       		<c:if test = "${officeType eq 'subdivision' }">
                       		<div class="col-md-6">
                       		 Cross Feeder SubDivision:
                       	<input type="text" id="subdivcode" value="${SubdivName}" readonly="readonly"  id= "crossFeedersubDiv" class="form-control placeholder-no-fix" placeholder="Enter Substation Name" name="crossFeedersubDiv" maxlength="200" ></input>
                       	</div>
                    </c:if>
                    
                    
                     <div class="col-md-6" id="tpParentDivId" >
                     TP Parent code: <font color="red">*</font><input type="text" class="form-control" name="tpParentCode"  id="tpParentCode" placeholder="Enter TP Code">
                      </div>
                      </div>
                 </div>
             </form> --%>
<script>
	function getSSTPCode(val) {
		var ssid=val;
		
		$("#tpprntcode").val(ssid.split('-')[0]);
		$("#tpprntcode").prop("readonly", true);
		/* $.ajax({
			url : './getSSTPCode',
			type : 'GET',
			dataType : 'TEXT',
			data : {
				ssid : val
			},
			success : function(response) {
				//tpprntcode
				$("#tpprntcode").val(response);
				$("#tpprntcode").prop("readonly", true);
			}
		}); */
	}

	function exportPDF() {
		var region = $('#LFzone').val();
		var circle = $('#LFcircle').val();
		var town = $('#LFtown').val();
		var feedertype = $('#feederTpId').val();
  		var townname = $("#LFtown option:selected").map(function(){return this.text}).get().join(',');

		var reg = "", cir = "", twn = "",feeder = "";

		if (region == "%") {
			reg = "ALL";
		} else {
			reg = region;
		}
		if (circle == "%") {
			cir = "ALL";
		} else {
			cir = circle;
		}
		if (town == "%") {
			twn = "ALL";
		} else {
			twn = town;
		}
		if (feedertype == "%") {
			feeder = "ALL";
		} else {
			feeder = feedertype;
		}		
		window.location.href = ("./FeederDetailsPDF?reg="+ reg + "&cir="+ cir + "&twn=" + twn + "&feeder=" + feeder + "&townname=" +townname);
	}

	function exportExcelFeeder() {
  
		var region = $('#LFzone').val();
		var circle = $('#LFcircle').val();
		var town = $('#LFtown').val();
		var feedertype = $('#feederTpId').val();
		//alert(region);
		//alert(circle);
		//alert(town);
		var reg = "", cir = "", twn = "",feeder = "";

		if (region == "%") {
			reg = "ALL";
		} else {
			reg = region;
		}
		if (circle == "%") {
			cir = "ALL";
		} else {
			cir = circle;
		}
		if (town == "%") {
			twn = "ALL";
		} else {
			twn = town;
		}
		if (feedertype == "%") {
			feeder = "ALL";
		} else {
			feeder = feedertype;
		}

		window.location.href = ("./FeederDetailsExcel?reg=" + reg + "&cir=" + cir + "&twn=" + twn + "&feeder=" + feeder);
	}

	function downLoadSample(type) {

		window.location.href = "http://1.23.144.187:8102/downloads/sldreport/FeederDetailsSample.xlsx";
	}

	function finalSubmit() {
		if (document.getElementById("fileUpload").value == ""
				|| document.getElementById("fileUpload").value == null) {
			bootbox.alert(' Please Select xlsx file to upload');
			return false;
		}
	}

	var _validFileExtensions = [ ".xlsx" ]; //,".jpg","jpeg",".png",".gif"
	function ValidateSingleInput(oInput) {
		if (oInput.type == "file") {
			var sFileName = oInput.value;
			if (sFileName.length > 0) {
				var blnValid = false;
				for (var j = 0; j < _validFileExtensions.length; j++) {
					var sCurExtension = _validFileExtensions[j];
					if (sFileName.substr(
							sFileName.length - sCurExtension.length,
							sCurExtension.length).toLowerCase() == sCurExtension
							.toLowerCase()) {
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
</script>

<script>

function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : evt.keyCode;
   if (charCode != 46 && charCode > 31 
     && (charCode < 48 || charCode > 57))
      return false;

   return true;
}
function showCircle(zone) {
	//alert("hiiiii...")
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
	                html += "<select id='LFtown' name='LFtown' class='form-control  select2me'  type='text'><option value=''>Select Town</option><option value='%'>ALL</option>";
	               
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
	  

	function showfeederResultsbasedOntownCode(townCode) {
		//alert(townCode);
		var circle = $("#circle").val();

	$('#feederTpId').val('').trigger('change');
	//var town = $('#townDT').val();
	$('#feederTpId').empty();
	$('#feederTpId').find('option').remove();
	$('#feederTpId').append($('<option>', {
		value : "",
		text : "Select Feeder"
	}));
	$('#feederTpId').append($('<option>', {
		value : "%",
		text : "ALL"
	}));

	$.ajax({
		url : './getFeederTypeBasedOnTown',
		type : 'POST',
		dataType : 'json',
		asynch : false,
		cache : false,
		data : {
			townCode : townCode,
			circle :circle
		},
		success : function(response1) {
			var html = '';
			/*    html += "<select id='feederTpId' name='feederTpId'  class='form-control input-medium'  type='text'><option value=''>Select Feeder</option><option value='%'>ALL</option>";
			   for (var i = 0; i < response1.length; i++) {
			       html += "<option value='"+response1[i][0]+"'>"
			               + response1[i][1] + "</option>";
			   }
			   html += "</select><span></span>";
			   $("#feederDivId").html(html);
			   $('#feederTpId').select2(); */

			for (var i = 0; i < response1.length; i++) {
				var resp = response1[i];
				$('#feederTpId').append($('<option>', {
					value : resp[0],
					text : resp[1]
				}));
			}
			$('#feederTpId').select2();

		}
	});
}

	/* function showTownNameandCode(subdiv) {
		var zone = $('#zone').val();
		var circle = $('#circle').val();
		var division = '%';
		subdiv = '%';
		$
				.ajax({
					url : './getTownsBaseOnSubdivision',
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					data : {
						zone : zone,
						circle : circle,
						division : division,
						subdivision : subdiv
					},
					success : function(response1) {
						var html = '';
						html += "<select id='town' name='town'  class='form-control select2me' type='text'><option value=''>Select Town</option><option value='%'>ALL</option>";
						for (var i = 0; i < response1.length; i++) {
							//var response=response1[i];
							html += "<option value='"+response1[i][0]+"'>"
									+ response1[i][0] + "-" + response1[i][1]
									+ "</option>";
						}
						html += "</select><span></span>";
						$("#town").html(html);
						$('#town').select2();
					}
				});
	} */

	 function downLoadSampleExcel() {
		var region = $('#LFzone').val();
		var circle = $('#LFcircle').val();
		var town = $('#LFtown').val();
		var feedertype = $('#feederTpId').val();
		var reg = "", cir = "", twn = "",feeder = "";

		if (region == "%") {
			reg = "ALL";
		} else {
			reg = region;
		}
		if (circle == "%") {
			cir = "ALL";
		} else {
			cir = circle;
		}
		if (town == "%") {
			twn = "ALL";
		} else {
			twn = town;
		}
		if (feedertype == "%") {
			feeder = "ALL";
		} else {
			feeder = feedertype;
		}

		window.location.href = ("./exportToExcelFeederDetails?reg=" + reg + "&cir=" + cir + "&twn=" + twn + "&feeder=" + feeder);
	
	}
	
	
	function showResultsbasedOntownCode(towncode){
		
	}


</script>






