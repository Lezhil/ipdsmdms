<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- <link href="./resources/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" /> -->

<<script>
<!--

//-->
$( document ).ready(function() {
	$('#MDASSideBarContents,#modemMang,#modemMaster').addClass('start active ,selected');
	$('#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
	.removeClass('start active ,selected');
});

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
						<i class="fa fa-edit"></i>Modem Master
					</div>
				</div>
				<div class="portlet-body">
					<form:form action="#" modelAttribute="modemMaster" commandName="modemMaster" 
					role="form" method="post" id="modemMasterForm" >
						<form:input id="id" path="id" name="id" type="text" hidden="true" />
						<form:input id="working_status" path="working_status" name="working_status" type="text" value="1"  hidden="true"/>
						<div class="row">
							<div class="form-group col-md-4">
								<label>Modem IMEI No.</label><font color="red">*</font>
								<div class="input-group">
									<span class="input-group-addon"> <i
										class="fa fa-angle-right"></i>
									</span> <form:input type="text" class="form-control" id="modem_imei"
										name="modem_imei"  placeholder="IMEI NO" path="modem_imei" onchange="checkImeiNumber(this.value)"></form:input>
								</div>
							</div>
							<div class="form-group col-md-4">
								<label>Modem Sl No.</label><font color="red">*</font>
								<div class="input-group">
									<span class="input-group-addon"> <i
										class="fa fa-angle-right"></i>
									</span> <form:input type="text" class="form-control" placeholder="Modem Serial No"
										id="modem_serial_no" name="modem_serial_no" path="modem_serial_no" ></form:input>
								</div>
							</div>
							
							<div class="form-group col-md-4">
								<label>Sim IMSI</label><font color="red">*</font>
								<div class="input-group">
									<span class="input-group-addon"> <i
										class="fa fa-angle-right"></i>
									</span> <form:input type="text" class="form-control" id="sim_imsi"
										name="sim_imsi" path="sim_imsi" placeholder="Sim IMSI"></form:input>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="form-group col-md-4">
								<label>Sim CCID</label><font color="red">*</font>
								<div class="input-group">
									<span class="input-group-addon"> <i
										class="fa fa-angle-right"></i>
									</span> <form:input type="text" class="form-control" id="sim_ccid"
										name="sim_ccid" path="sim_ccid" placeholder="Sim CCID"></form:input>
								</div>
							</div>
							<div class="form-group col-md-4">
								<label>Attached Meter No.</label>
								<div class="input-group">
									<span class="input-group-addon"> <i class="fa fa-angle-right"></i>
									</span> <form:input type="text" class="form-control" id="attached_mtrno"
										name="attached_mtrno" path="attached_mtrno"
										placeholder="Attached Meter No."></form:input>
								</div>
							</div>
							<div class="form-group col-md-4">
								<label>Phone No</label><font color="red">*</font>
								<div class="input-group">
									<span class="input-group-addon"> <i class="fa fa-phone-square"></i>
									</span> <form:input type="text" class="form-control" id="phone_no"
										name="phone_no" path="phone_no"
										placeholder="Phone No"></form:input>
								</div>
							</div>
						</div>
					</form:form>
					<div class="text-center">
						<label class="mt-checkbox" style="margin-left: -130px; cursor: pointer;" > 
							<input type="checkbox" id="notWorking" onclick="notWorkingCheck()">  <span onselectstart="return false">Not Working</span>
						</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

						<button type="button" class="btn btn-warning"
							onclick="reloadPage();">Reset</button>
						<button type="button" id="savebtn" class="btn green"
							onclick="return validateFields(0);">Save</button>
						<button type="button" id="updtbtn" class="btn btn-info"
							onclick="return validateFields(1);" style="display: none;">Modify</button>
					</div>



				</div>






			</div>
		</div>
	</div>

</div>

<script>
function reloadPage(){
    location.reload(true);
}

function validateFields(val) {
	
	var modem_imei=$("#modem_imei").val();
	var modem_serial_no=$("#modem_serial_no").val();
	var sim_imsi=$("#sim_imsi").val();
	var sim_ccid=$("#sim_ccid").val();
	var phone_no=$("#phone_no").val();
	
	if(modem_imei==null || modem_imei==""){
		bootbox.alert("Please Enter IMEI No.");
		return false;
	} else{
		if(!$.isNumeric(modem_imei)){
			bootbox.alert("Please Enter IMEI No.");
			return false;
		}else{
			
			if(!(modem_imei.length>=14 && modem_imei.length<=16)){
				bootbox.alert("Invalid IMEI No.");
				return false;
			} else{
				var p=Number(modem_imei).toFixed();
				if(p!=modem_imei){
					bootbox.alert("Invalid IMEI No.");
					return false;
				}
			}
		}
	}
	if(modem_serial_no==null || modem_serial_no==""){
		bootbox.alert("Please Enter Modem Serial No");
		return false;
	}
	if(sim_imsi==null || sim_imsi==""){
		bootbox.alert("Please Enter Sim IMSI No");
		return false;
	}
	if(sim_ccid==null || sim_ccid==""){
		bootbox.alert("Please Enter Sim CCID No");
		return false;
	}
	if(phone_no==null || phone_no==""){
		bootbox.alert("Please Enter Phone No");
		return false;
	}else{
		
		if(phone_no.length!=10){
			bootbox.alert("Phone No. should be of 10 digit");
			return false;
		} else{
			var p=Number(phone_no).toFixed();
			if(p!=phone_no){
				bootbox.alert("Invalid Phone No.");
				return false;
			}
		}
	}
	
	
	if(val==0){
			bootbox.confirm("Are You Sure You want to Save!", function(result) {
				if(result){
					$('#modemMasterForm').attr('action', './addNewModemMasterMDAS').submit();
				} 
			});
			return false;
		} else if (val == 1) {
			bootbox.confirm("Are You Sure You want to Modify!", function(result) {
					if(result){
						if(result){
							$('#modemMasterForm').attr('action', './updateNewModemMasterMDAS').submit();
						}
					} 
				});
			return false;
		} else {
			return false;
		}

		/* bootbox.alert("Hi"); */

	}
	
	function checkImeiNumber(imei) {
		
		$.ajax({
	          type: 'GET',
	          url: './checkImeiNoInModemMaster/'+imei,
	          dataType : 'JSON',
			  asynch : false,
			  cache : false,
	          success: function (response) {
		           if(response.length !=0){
		        	   //alert(response.modem_imei);
		        	   $("#modem_imei").val(response.modem_imei);
	        		   $("#modem_serial_no").val(response.modem_serial_no);
	        		   $("#sim_imsi").val(response.sim_imsi);
	        		   $("#sim_ccid").val(response.sim_ccid);
	        		   $("#phone_no").val(response.phone_no);
	        		   $("#id").val(response.id);
	        		   $("#attached_mtrno").val(response.attached_mtrno);
	        		   $("#working_status").val(response.working_status);
		        	   
		        	   $("#savebtn").css("display","none");
		        	   $("#updtbtn").css("display","inline");
		        	   $("#modem_imei").attr("readonly",true);
	        		   $("#modem_serial_no").attr("readonly",true);
	        		   
	        		   //var wst=response.working_status;
	        		   if(response.working_status==0){
	        			   $('#notWorking').click();
	        		   }
	        		   
	        		   
		           }
	        	  
	          }
	        });
		
		
		
	}
	
	function notWorkingCheck() {
		//alert();
		if($('#notWorking').attr('checked')){
			$("#working_status").val("0");
		} else {
			$("#working_status").val("1");
		}
	}
	
</script>

