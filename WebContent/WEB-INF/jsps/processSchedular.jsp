<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<meta http-equiv="content-type"
	content="application/vnd.ms-excel; charset=UTF-8">
<style>
.table-toolbar {
	margin-bottom: 4px;
}

.col-md-2 {
	width: 24.500%;
}

.col-md-1 {
	width: 24.333%;
}
/* .paginate_button {
width: 10px;
} */
.paginate_button {
	padding: 15px 25px;
	font-size: 15px;
	text-align: center;
	cursor: pointer;
	outline: none;
	color: #fff;
	background-color: #717887;
	border: none;
	border-radius: 15px;
	box-shadow: 0 9px #999;
}

.paginate_button:hover {
	background-color: #597bc7
}

.paginate_button:active {
	background-color: #3e8e41;
	box-shadow: 0 5px #666;
	transform: translateY(4px);
}
</style>
<
<script type="text/javascript">

	$(".page-content")
			.ready(
					function() {
						App.init();
						FormComponents.init();
					    
					    
						$('#ADMINSideBarContents,#processScehdularId')
								.addClass('start active ,selected');
						$(
								'#MDASSideBarContents,#MDMSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
								.removeClass('start active ,selected');
						loadSearchAndFilter1('sample_save');
						   // $('#datetime24').combodate();  

					    //loadSearchAndFilter('sample_save');  
					    
					});

	var tableToExcelNew = (function () {
	    var uri = 'data:application/vnd.ms-excel;base64,'
	        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
	        , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
	        , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) }
	    return function (table, name) {
	        if (!table.nodeType) table = document.getElementById(table)
	        var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML }
	        document.getElementById("excelExport").href = uri + base64(format(template, ctx));
	        document.getElementById("excelExport").download = name+'_'+moment().format("DD-MM-YYYY_hh.mm.ss")+'.xls';
	    }
	})() 
</script>
<script type="text/javascript">
var editFlag=0;var editFlagName='';
function showtypes(process)
{
	//alert(process);
	$.ajax({
    	url:"./showProcessNameandId",
    	type:'GET',
    	dataType:'json',
    	data:{
             process:process
        	},
    	asynch:false,
    	cache:false,
    	success:function(response)
    	{
        	//alert(response);
    		var html='';
    		html+="<select id='processName' name='processName' class='form-control input-medium' type='text'><option value=''>Select</option>";
			for( var i=0;i<response.length;i++)
			{
				var resp=response[i];
				 /* html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";  */
				 html+="<option  value='"+resp[0]+"-"+resp[1]+"'>"+resp[0]+"-"+resp[1]+"</option>"
				
			}
			html+="</select><span></span>";
			$("#processNametd").html(html);
			$('#processName').select2();
			if(editFlag==1){
				$("#processName").val(editFlagName).trigger("change");
				editFlag=0;
			}
    	}
	}); 
}

function saveProcess()
{
 //alert();
 var processType = $('#processedTypes').val();
 var occtypeTime = $('#occtypeTime').val();
 var dateandTimevalue = $('#fromDate').val();
 var occuranceval = $('#occuranceval').val();
 var processName = $('#processName').val();
 
	
		if (processType == null || processType == "") {
			bootbox.alert("Please enter Process Type.");
			return false;
		}
		if (processName == null || processName == "") {
			bootbox.alert("Please enter Process Name .");
			return false;
		}
		if (occtypeTime == null || occtypeTime == "") {
			bootbox.alert("Please enter occurance type Time .");
			return false;
		}
		if (dateandTimevalue == null || dateandTimevalue == "") {
			bootbox.alert("Please enter date and Time value.");
			return false;
		}
		
		
		$
				.ajax({

					url : './savingProcessdata',
					type : 'GET',
					data : {
						process : processType,
						occtypeTime : occtypeTime,
						dateandTimevalue : dateandTimevalue,
						occuranceval : occuranceval,
						processName : processName

					},
					dataType : 'json',
					success : function(response) {
						//alert(response);
						var html = '';
						if (response != null) {
							for (var i = 0; i < response.length; i++) {

								var resp = response[i];
								html += "<tr>" + "<td hidden='true' id='id'>"
										+ resp[0]
										+ " </td>"
										+ "<td>"
										+ (resp[1] == null ? "" : resp[1])
										+ " </td>"
										+ "<td>"
										+ (resp[2] == null ? "" : resp[2])
										+ " </td>"
										+ "<td>"
										+ (resp[3] == null ? "" : resp[3])
										+ " </td>"
										+ "<td>"
										+ (resp[5] == null ? "" : resp[5])
										+ " </td>"
										+ "<td>"
										+ (resp[6] == null ? "" : resp[6])
										+ " </td>"
										+ "<td>"
										+ (resp[7] == null ? "" : resp[7])
										+ " </td>"
										+ "<td><a href='#' onclick='editUser(this.id)' id='"
										+ resp[0] + "' >EDIT</a></td>"
										/* + "<td><a href='#' onclick='deleteUser(this.id)' id=''>DELETE</a></td>" */
										+ "</tr>";
							}
							bootbox.alert("DATA SAVED SUCCESFULLY!!!!");
							$("#processedTypes").val('0').trigger("change");
							$("#occtypeTime").val('').trigger("change");
							$("#fromDate").val('');
							$("#occuranceval").val('').trigger("change");
							$("#processName").val('').trigger("change");
						} else {
							bootbox.alert("ERROR IN SAVING.....");
						}
						$('#sample_save').dataTable().fnClearTable();
						$("#savedData").html(html);
						loadSearchAndFilter1('sample_save');
					},

					complete : function() {
						loadSearchAndFilter1('sample_save');
					}

				});

	}
	function validation(val) {
		var occuranceval = $('#occuranceval').val();
		if (val == "Repeated") {
			$("#occurancess").show();
			$("#occurance").show();
		} else {
			$("#occurancess").hide();
			$("#occurance").hide();
		}
		
	}
	function updateUser() {
		var id = $('#proID').val();
		var processType = $('#processedTypes').val();
		var occtypeTime = $('#occtypeTime').val();
		var dateandTimevalue = $('#fromDate').val();
		var occuranceval = $('#occuranceval').val();
		var processName = $('#processName').val();
		
		if (processType == null || processType == "") {
			bootbox.alert("Please enter Process Type.");
			return false;
		}
		if (processName == null || processName == "") {
			bootbox.alert("Please enter Process Name.");
			return false;
		}
		if (occtypeTime == null || occtypeTime == "") {
			bootbox.alert("Please enter occurance type Time.");
			return false;
		}
		if (dateandTimevalue == null || dateandTimevalue == "") {
			bootbox.alert("Please enter date and Time value.");
			return false;
		}
		
		$
				.ajax({
					url : "./updateProcessTable",
					data : {
						id : id,
						process : processType,
						occtypeTime : occtypeTime,
						dateandTimevalue : dateandTimevalue,
						occuranceval : occuranceval,
						processName : processName
					},
					dataType : 'json',
					success : function(response) {
						//$('#processType').val("");

						//alert(response);
						var html = '';
						if (response != null) {
							for (var i = 0; i < response.length; i++) {

								var resp = response[i];
								html += "<tr>" + "<td hidden='true' id='id'>"
										+ resp[0]
										+ " </td>"
										+ "<td>"
										+ (resp[1] == null ? "" : resp[1])
										+ " </td>"
										+ "<td>"
										+ (resp[2] == null ? "" : resp[2])
										+ " </td>"
										+ "<td>"
										+ (resp[3] == null ? "" : resp[3])
										+ " </td>"
										+ "<td>"
										+ (resp[5] == null ? "" : resp[5])
										+ " </td>"
										+ "<td>"
										+ (resp[6] == null ? "" : resp[6])
										+ " </td>"
										+ "<td>"
										+ (resp[7] == null ? "" : resp[7])
										+ " </td>"
										+ "<td><a href='#' onclick='editUser(this.id)' id='"
										+ resp[0] + "' >EDIT</a></td>"
										/* + "<td><a href='#' onclick='deleteUser(this.id)' id=''>DELETE</a></td>" */
										+ "</tr>";
							}
							bootbox.alert("DATA UPDATED SUCCESFULLY!!!!");
							/* var dropDown = document.getElementById("processedTypes");
							dropDown.selectedIndex = 0; */
							// $('#processedTypes').empty();
							$("#processedTypes").val('0').trigger("change");
							$("#occtypeTime").val('').trigger("change");
							$("#fromDate").val('');
							$("#occuranceval").val('').trigger("change");
							$("#processName").val('').trigger("change");
						} else {
							bootbox.alert("ERROR IN UPDATING.....");
						}
						$('#sample_save').dataTable().fnClearTable();
						$("#savedData").html(html);
						loadSearchAndFilter1('sample_save');
					},

					complete : function() {
						loadSearchAndFilter1('sample_save');
					}

				});

	}
</script>

<script>

	function editUser(processid) {
		//alert(processid);
$("#saveprocesses").hide();
		$.ajax({
			url : "./editSavedData",
			data : {
				id : processid
			},
			dataType : 'json',
			success : function(response) {
				//alert(response);
				for (var i = 0; i < response.length; i++) {
					var resp = response[i];
					
					var pname=resp.processid+"-"+resp.processname;
					editFlag=1;
					editFlagName=pname;
					$("#processedTypes").val(resp.processtype).trigger("change");
					$("#processName").val(pname).trigger("change");
					$("#occtypeTime").val(resp.occurancetype).trigger("change");
					$("#fromDate").val(resp.occurancetime);
					$("#occuranceval").val(resp.repeatedocctime).trigger("change");
					$("#proID").val(processid);
				
				}

			}
		});

	}
</script>
<div class="page-content">
	<div class="row">
		<div class="col-md-12">

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Process Schedular
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body" >

					<div class="row" style="margin-left: -1px;" >

						<table style="width: 38%">
							<tbody>
								<tr>
									<th id="processType" class="block" name="processType">Process&nbsp;Type:</th>
									<th id="processTypee"><select
										class="form-control select2me input-medium"
										id="processedTypes" name="processType"
										onchange="showtypes(this.value);">
											<option value="0">Select</option>
											<option value="Validation Type">Validation Type</option>
											<option value="Estimation Type">Estimation Type</option>
											
									</select></th>
									<th class="block">Process&nbsp;Name:</th>
									<th id="processNametd"><select
										class="form-control select2me input-medium" id="processName"
										name="processName">
											<option value=""></option>

									</select></th>
									<th id="typeTimeVAL" class="block" name="typeTime">&nbsp;&nbsp;Type:</th>
									<th id="typeTime"><select
										class="form-control select2me input-medium" id="occtypeTime"
										name="occtypeTime" onchange="return validation(this.value);">
											<option value="">Select</option>
											<option value="Once">Once</option>
											<option value="Repeated">Repeated</option>

									</select></th>
								</tr>
								<tr>
									<th colspan="8"></th>
								</tr>
								<tr>
									<th colspan="8"></th>
								</tr>
								<tr>
									<th id="dateandTimevalue" class="block" name="dateandTimevalue">
										Date and&nbsp;Time:</th>
									<th><input type="text"
										class="form-control date form_datetime form_datetime bs-datetime"
										style="width: 239px;" name="fromDate" id="fromDate"></th>

									<th id="occurancess" class="block" name="typeTime" hidden="true">&nbsp;Occurance:</th>
									<th id="occurance"  hidden="true"><select
										class="form-control select2me input-medium" id="occuranceval"
										name="occuranceval">
											<option value="">Select</option>
											<option value="30min">30min</option>
											<option value="1 hour">1hour</option>
											<option value="1 day">1day</option>
											<option value="1 month">1month</option>

									</select></th>
									<th id="processID" class="block" name="processID"  hidden="true">ID:</th>
									<th id="procId" hidden="true">
										<input class="form-control input-medium"
										id="proID" name="proID"  >
										
									</th></tr>
									</tbody>
						</table>
									<div class="row" style="margin-left: 391px;">
										<button type="button" id="saveprocesses"
											style="margin-left: 20px;" onclick="return saveProcess();"
											name="saveprocesses" class="btn yellow">
											<b>Save</b>
										</button>
									
									
										<button type="button" id="updateprocesses"
											style="margin-left: 20px;"
											onclick="return updateUser();" name="updateprocesses"
											class="btn yellow">
											<b>Update</b>
										</button></div>
									
							

					</div>
				</div>
			</div>


			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>All Process Details
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
						
						<div class="table-toolbar">
							<div class="btn-group pull-right" style="margin-top: 5px;">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
									<li><a href="#" id="excelExport"
										onclick="tableToExcelNew('sample_save', 'Total Meters')">Export
											to Excel</a></li>
								</ul>
							</div>
						</div>
						
						<table class="table table-striped table-hover table-bordered"
							id="sample_save">
							<thead>
								<tr>
									<th hidden="true">id</th>
									<th>Process Type</th>
									<th>Process Name</th>
									<th>Process ID</th>
									<th>Occurance Type</th>
									<th>Occurance Date and Time</th>
									<th>Occurance Interval</th>
									<th>EDIT</th>
									
									
								</tr>
							</thead>
							<tbody id="savedData">
							 <c:forEach var="element" items="${savedList}">
									 <tr>
									 <td hidden="true">${element[0]}</td>
									 <td>${element[1]}</td>
									 <td>${element[2]}</td>
									 <td>${element[3]}</td>
									 <td>${element[5]}</td>
									 <td>${element[6]}</td>
									 <td>${element[7]}</td>
									 <td><a href='#' onclick="editUser(this.id)" id="${element[0]}" >EDIT</a></td>
									<%--  <td hidden="true"><a href='#' onclick="updateUser(this.id)" id="${element[0]}" >UPDATE</a></td> --%>
									 </tr>
									 
									 </c:forEach>	
									 



							</tbody>
						</table>
				</div>
			</div>


		</div>
	</div>


