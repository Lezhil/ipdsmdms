<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script type="text/javascript" src="./resources/js/mfs100-9.0.2.6.js"></script>

<script type="text/javascript"
	src="./resources/layout_3/assets/js/plugins/tables/datatables/datatables.min.js"></script>
<%@ page import="java.util.ResourceBundle"%>
<%
	ResourceBundle resource = ResourceBundle.getBundle("application");
	String message = resource.getString("application.billcorrection");
%>
<script>

</script>

<style>
.col-sm-6 {
	width: 50%;
	!
	Important
}

.focus {
	margin-left: 30px;
	border-radius: 7px;
	border: 2px solid #cce6ff;
	padding: 12px;
	width: 150px;
	height: 20px;
	border-radius: 7px;
}

.focus1 {
	margin-left: 75px;
	border-radius: 7px;
	border: 2px solid #cce6ff;
	padding: 12px;
	width: 150px;
	height: 20px;
}

.focus2 {
	margin-left: 88px;
	border-radius: 7px;
	border: 2px solid #cce6ff;
	padding: 12px;
	width: 150px;
	height: 20px;
}

.focus3 {
	margin-left: 60px;
	border-radius: 7px;
	border: 2px solid #cce6ff;
	padding: 12px;
	width: 150px;
	height: 20px;
}

.focus4 {
	margin-left: 70px;
	border-radius: 7px;
	border: 2px solid #cce6ff;
	padding: 12px;
	width: 150px;
	height: 20px;
}

.row {
	margin-left: 20px; !
	Important margin-right: 20px;
	!
	Important
}

.button {
	background-color: #4CAF50; /* Green */
	border: none;
	margin-left: 100px;
	color: white;
	padding: 1px 15px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	color: white;
}
#message {
  display:none;
  background: #f1f1f1;
  color: #000;
  position: relative;
  padding: 20px;
  margin-top: 10px;
}

#message p {
  padding: 10px 35px;
  font-size: 18px;
}

/* Add a green text color and a checkmark when the requirements are right */
.valid {
  color: green;
}

.valid:before {
  position: relative;
  left: -35px;
  content: "✔";
}

/* Add a red text color and an "x" when the requirements are wrong */
.invalid {
  color: red;
}

.invalid:before {
  position: relative;
  left: -35px;
  content: "✖";
}

</style>


<script type="text/javascript">
//$(document).ready(function() {

		/* 	$('#billingScreens').show();
		 $('#billingModule').addClass('active'); */
//	var modal = document.getElementById('id01');
		<!--
		alert('${msg}');
		var data = '${msg}';
		alert(data.studentname);
		-->

	//});
	/*  var frm = $('#saveStudent');

	    frm.submit(function (e) {

	        e.preventDefault();

	        $.ajax({
	            type: frm.attr('method'),
	            url: frm.attr('action'),
	            data: frm.serialize(),
	            success: function (data) {
	                console.log('Submission was successful.');
	                console.log(data);
	            },
	            error: function (data) {
	                console.log('An error occurred.');
	                console.log(data);
	            },
	        });
	    });
 */
 function validateform(){  
	 //var email = document.getElementById('email');
	 var myInput = document.getElementById("pincode").val();
	 if(myInput=='' || myInput==null|| myInput.value.length!=6){
		 alert('invalid pincode');
		 return false;
	 }
	    /* var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

	    if (!filter.test(email.value)) {
	    alert('Please provide a valid email address');
	    email.focus; */
	    
	 
	 }  
 
 
 var myInput = document.getElementById("pincode").val();
 alert(myInput);
 var length = document.getElementById("length");

 // When the user clicks on the password field, show the message box
 myInput.onfocus = function() {
   document.getElementById("message").style.display = "block";
 }

 // When the user clicks outside of the password field, hide the message box
 myInput.onblur = function() {
   document.getElementById("message").style.display = "none";
 }

 // When the user starts to type something inside the password field
 myInput.onkeyup = function() {
   

   
   
   // Validate length
   if(myInput.value.length >= 8) {
     length.classList.remove("invalid");
     length.classList.add("valid");
   } else {
     length.classList.remove("valid");
     length.classList.add("invalid");
   }
 }
//method to post data using akax
/* $(document).ready(function() {

    // process the form
    $('saveStudent').submit(function(event) {

        // get the form data
        // there are many ways to get this data using jQuery (you can use the class or id also)
        var formData = {
            'name'              : $('input[id=id]').val(),
            'email'             : $('input[studentname=studentname]').val(),
            'divison'           : $('input[divison=divison]').val(),
            'school'            : $('input[school=school]').val(),
            'address'           : $('input[address=address]').val(),
            'city'              : $('input[city=city]').val(),
            'mailid'            : $('input[mailid=mailid]').val(),
            'pincode'           : $('input[pincode=pincode]').val(),
            
        };
        alert(formdata)

        // process the form
        $.ajax({
            type        : 'POST', 
            url         : './save', 
            data        : {
            	         formdata : formData, 
            },
            dataType    : 'json', 
                        encode          : true
        })
            
            .done(function(data) {

               
                console.log(data); 

               
            });
        
        event.preventDefault();
    });

}); */
	function deleteStudent(id) {
		if (id != "") {
			$.ajax({
				url : './delete',
				type : 'GET',

				data : {
					id : id
				},
				dataType : 'text',
				asynch : false,
				cache : false,
				success : function() {
					alert('ok');
				}

			});
		}

	}
	/* function saveStudent(){
		//var form=$('#saveStudent');
		var form=$('#studentname').val();
		alert(form)
		 //var formMessages = $('#form-messages');
		 //var formData = $(form).serialize();
		 //var x = document.getElementById("saveStudent").elements.length; 
		 $.ajax({
			    type: 'POST',
			    url: './save',
			    data: {
			    	form:form,
			    },
			    dataType : 'text',
				asynch : false,
				cache : false,
				success : function() {
					alert('ok');
				}
			})
		
	} */
	function validateEmail(emailField){
        var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

        if (reg.test(emailField.value) == false) 
        {
            alert('Invalid Email Address');
            return false;
        }

        return true;

	function selectAll(source) {

		var flagChecked = 0;
		checkboxes = document.getElementsByName('toBeDeleted');
		alert(checkboxes.length);
		for (var i = 0; i < checkboxes.length; i++) {
			checkboxes[i].checked = source.checked;
			if (checkboxes[i].checked) {
				flagChecked = 1;
			}
		}

		if (flagChecked == 0) {
			$('#docketNumDiv1 span:first-child').removeClass("checked");
		} else {
			$('#docketNumDiv1 span:first-child').addClass("checked");
		}

	}

	//alert(values);
	function deleteMultipleStudent() {
		var checkboxes = document.getElementsByName('toBeDeleted');
		var selected = new Array();
		var cheeckedboxes = "";
		// toDelete=new Array();
		for (var i = 0; i < checkboxes.length; i++) {

			if (checkboxes[i].checked) {
				//selected.push(checkboxes[i].value);
				cheeckedboxes = cheeckedboxes + checkboxes[i].value + ",";

				//alert(checkboxes[i].value);
			}
		}
		alert(cheeckedboxes);
		//alert(selected.length);

		$.ajax({
			url : './deleteMultiple',
			type : 'GET',
			dataType : 'text',
			data : {
				'toDelete' : cheeckedboxes
			},

			success : function(data) {
				alert("SUCCESS");
			},
		});

	}
</script>
<div class="panel panel-flat" id="billC">
	<div class="panel-body">
		<h2>Student Portal   ${out}</h2>
		<h3></h3>
		<button class="btn-primary" data-toggle='modal'
			data-target='#modal_form_vertical' data-spinner-size="30"
			style="width: auto;">Add Student  </button>
		<!--${msg}-->


		<button class="btn-primary" data-toggle='modal'
			data-target='#modal_form_horizontal' data-spinner-size="20"
			style="width: auto;">List Of students</button>
		<button class="btn-danger" onclick="deleteMultipleStudent()">
			<i class="fa fa-trash"></i> Delete Multiple

		</button>
		<button class="btn-primary" data-toggle='modal'
			data-target='#modal_form_vertics' data-spinner-size="20"
			style="width: auto;">update student DIV</button>


		<div class="table-responsive">
			<table class="table datatable-button-html5-basic table-bordered">
				<thead>
					<!-- 					<tr class="bg-blue-800">-->
					<tr class="bg-blue-800" onClick="selectAll(this)">

						<!-- <th><input type="checkbox" class="control-primary"
							id="selectall"></th>-->
						<th><input type="checkbox" class="control-primary"
							id="selectall"></th>

						<!--<th>checkbox</th>-->
						<th>id</th>
						<th>Student Name</th>
						<th>divison</th>
						<th>school</th>
						<th>adderss</th>
						<th>city</th>
						<th>email id</th>
						<th>pincode</th>
						<th>delete</th>

						<th>update Student</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="count" value="1" scope="page"></c:set>
					<c:forEach var="app" items="${msg}">
						<tr>
							<td><div id="docketNumDiv1">
									<input type="checkbox" class="control-primary"
										style="margin-left: -15px; margin-top: -20px;"
										autocomplete="off" placeholder="" name="toBeDeleted"
										value="${app.id}" />
								</div></td>

							<%-- <div class="checkbox">
									<label><input type="checkbox" name="toBeDeleted"
										value="${app.id}"></label> --%>
							<td>${app.id}</td>
							<td>${app.studentname}</td>
							<td>${app.divison}</td>
							<td>${app.school}</td>
							<td>${app.address}</td>
							<td>${app.city}</td>
							<td>${app.mailid}</td>
							<td>${app.pincode}</td>

							<td><button class="btn-danger" data-toggle='modal'
									data-target='#modal_form_vh' data-spinner-size="20"
									style="width: auto;" onclick="deleteStudent('${app.id}')">delete
									student</button></td>
							<td><button class="btn-primary" data-toggle='modal'
									data-target='#modal_form_vertic' data-spinner-size="20"
									style="width: auto;" onclick="('${app}')">update
									student</button></td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div id="modal_form_vertical" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">Add Student</h5>
				</div>

				<form name="myform" id="saveStudent" action="./save"
					onsubmit="return validateform();" method="POST" role="form"
					class="form-horizontal form-validate-jquery">
					<fieldset class="content-group">
						<div class="form-group">
							<div class="row">
								<div class="col-sm-6">
									<label class="padd">student name</label> <input type="text"
										class="focus" id="studentname" maxlength="8"
										name="studentname">
								</div>
								<div class="col-sm-6">
									<label class="padd">class</label> <input type="text"
										class="focus1" id="divison" maxlength="8" name="divison">
								</div>
								<div class="col-sm-6">
									<label class="padd">school</label> <input type="text"
										class="focus4" id="school" maxlength="15" name="school">
								</div>
								<div class="col-sm-6">
									<label class="padd">address</label> <input type="text"
										class="focus4" id="address" maxlength="15" name="address">
								</div>
								<div class="col-sm-6">
									<label class="padd">city</label> <input type="text"
										class="focus2" id="city" maxlength="15" name="city">
								</div>
								<div class="col-sm-6">
									<label class="padd">email</label> <input type="text"
										class="focus1" id="email" maxlength="15" name="email">
								</div>
								<div class="col-sm-6">
									<label class="padd">pincode</label> <input type="text"
										class="focus3" id="pincode" maxlength="15" name="pincode" required>
								</div>
								<button type="submit" class="button">register</button>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>

	</div>
	<div id="message">
		<h3>pincode must contain the following:</h3>
		<p id="length" class="invalid">
			Minimum <b>8 characters</b>
		</p>
	</div>

	<div id="modal_form_horizontal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">Get Student List</h5>
				</div>

				<form action="./getallstudents" method="GET" id="studentDetails"
					role="form" class="form-horizontal form-validate-jquery">
					<fieldset class="content-group">
						<div class="form-group">
							<div class="row"></div>
							<button type="submit" class="button">get student detail</button>
						</div>

					</fieldset>
				</form>
			</div>
		</div>

	</div>

	<div id="modal_form_vertics" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">update Student</h5>
					<h6 class="modal-title">Please type in the id of the student
						to confirm.</h6>
				</div>

				<form action="./updatediv" method="POST" id="id" role="form"
					class="form-horizontal form-validate-jquery">
					<fieldset class="content-group">
						<div class="form-group">
							<div class="row">
								<div class="col-sm-6">
									<label class="padd">student id</label> <input type="text"
										class="focus" id="id" maxlength="8" name="id">
								</div>
								<div class="col-sm-6">
									<label class="padd">student class</label> <input type="text"
										class="focus" id="id" maxlength="8" name="divison">
								</div>
							</div>
							<div>
								<button type="submit" class="button">update div</button>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
	<!-- update form -->

	<div id="modal_form_vertic" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">update Student</h5>
				</div>

				<form action="./update" method="POST" role="form"
					class="form-horizontal form-validate-jquery">
					<fieldset class="content-group">
						<div class="form-group">
							<div class="row">
								<div class="col-sm-6">
									<label class="padd">student id</label> <input type="text"
										class="focus" id="id" maxlength="8" name="id">
								</div>
								<div class="col-sm-6">
									<label class="padd">student class</label> <input type="text"
										class="focus" id="divison" maxlength="8" name="divison">
								</div>
								<div class="col-sm-6">
									<label class="padd">address</label> <input type="text"
										class="focus3" id="address" maxlength="15" name="address">
								</div>
								<div class="col-sm-6">
									<label class="padd">city</label> <input type="text"
										class="focus2" id="city" maxlength="15" name="city">
								</div>

								<div class="col-sm-6">
									<label class="padd">pincode</label> <input type="text"
										class="focus3" id="pincode" pattern="\\d{6}" maxlength="15"
										name="pincode" title="Must contain only 6 digits" required>
								</div>


								<button type="submit" class="button">update</button>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>

	</div>


</div>