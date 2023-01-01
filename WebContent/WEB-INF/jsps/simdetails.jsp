<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
  <script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
	<!-- <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> -->
	<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
	<script src="resources/assets/scripts/app.js"></script>
	<script src="resources/assets/scripts/ui-extended-modals.js"></script>
	
<script>

$(".page-content").ready(
		function() {
			App.init();
			TableEditable.init();
			FormComponents.init();
			loadSearchAndFilter('sample_1');
		 $('#MDMSideBarContents,#metermang,#simdetailsID').addClass('start active ,selected');
			$('#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment').removeClass('start active ,selected'); 
		
			getSimDetails();
		});
		
	function addSimDetails(){
		
		var simsrno=$("#simnumber").val();
		var mdnum=$("#mdnumber").val();
		var simip=$("#simstaticip").val();
		var nsprov=$("#nsprovider").val();
		var simsta=$("#simstatus").val();
		var barcode=$("#barcodeip").val();
		var regex = /^[a-zA-Z0-9]*$/;

		if( simsrno=='') 
		{
			bootbox.alert("Enter SIM Sr Number")
			return false;
		}
		if(!simsrno.match(regex))
    	{
    	bootbox.alert("SIM Sr Number contains only alphanumeric");
        return false;
	    }
		
		if( nsprov=='') 
		{
			bootbox.alert("Select NSP")
			return false;
		}
		
		if( simsta=='') 
		{
			bootbox.alert("Select SIM Status")
			return false;
		}
		
		$.ajax({
			url:'./addSimDetails',
			type:'GET',
			data:{
				simsrno : simsrno,
				mdnum   : mdnum,
				simip   : simip,
				nsprov  : nsprov,
				simsta  : simsta,
				barcode : barcode
			},
			success:function(response){
				//alert(response)
				bootbox.alert(" SIM Details Added Successfully");
				clearfieldsNew();
				$('#stack1').hide();
				getSimDetails();
			},
		});
	}
	
	function editSimDetails(param){
	    //alert(param);
		var id=param;
		//alert(id)
		if(id==""||id==null)
		{
		bootbox.alert("Nodata")
		return false;
		}
		
		var mdnumber=$("#mdnumberr").val();
		var simstaticip=$("#simstaticipp").val();
		var nsprovider=$("#nsproviderr").val();
		var simstatus=$("#simstatuss").val();
		//$("#simid").hide();
		$.ajax({
			   url : './editSimDetails/'+id,
			   type: 'POST',
		   dataType: 'json',
			    
			   success:function(response){
				       //alert(response)
				         var data=response[0]
				           $("#simid").val(data[0]);
				           $("#nsproviderr").val(data[1]);
				           $("#mdnumberr").val(data[2]);
				           $("#simstatuss").val(data[3]);
				           $("#simstaticipp").val(data[4]);
				           $("#modifyId").val(data[0]);
			   }
		})
		$('#'+param).attr("data-toggle", "modal");
		$('#'+param).attr("data-target","#stack2");
	}
	
	function modifySimDetails(){
		
		var simid=$("#simid").val();
		var mdnumber=$("#mdnumberr").val();
		var simstaticip=$("#simstaticipp").val();
		var nsprovider=$("#nsproviderr").val();
		var simstatus=$("#simstatuss").val();
		
		if( nsprovider=='') 
		{
			bootbox.alert("Select NSP")
			return false;
		}
		
		if( simstatus=='') 
		{
			bootbox.alert("Select SIM Status")
			return false;
		}

		
		$.ajax({
			   url : './modifySimDetails',
			   type: 'POST',
		      data :{
		    	     simid  : simid,
		    	  mdnumberr : mdnumber,
		       simstaticipp : simstaticip,
		    	nsproviderr : nsprovider,
		    	 simstatuss : simstatus,
		      },
		      success:function(response){
		    	  //alert(response)
			    	bootbox.alert(" SIM Details Modified Successfully");
		    	  $('#stack2').hide();
				  $('#stack2').modal('hide');
				  getSimDetails();
		      },
		});
	}
	
	function checkSrnoAvail(){
		
		var srno=document.getElementById('simnumber').value;
		//alert("srno")
		$.ajax({
			 url : './checkSrnoAvail/'+srno,
			 type:'GET',
			 dataType : "text",
			 async : false,
			 cache : false,
			 success:function(response){
				 //alert(response)
				 if(response=='Code Exist') {
			        bootbox.alert("SIM Srno already Exist");
			        $('#simnumber').val('');
			        }
			 }
		})
	}
	
	function checkMdnAvail(){
	
		var mdno=document.getElementById('mdnumber').value;
		//alert("Onchange" +mdno);
		//alert("mdno")
		$.ajax({
			url : './checkMdnAvail/'+mdno,
			 type:'GET',
			 dataType : "text",
			 async : false,
			 cache : false,
			 success:function(response){
					//alert(response)
					if(response=='Num Exist') {
					        bootbox.alert("MDN already Exist");
					        $('#mdnumber').val('');
			}
			 }
		});
	}
	
	function checkStaticIpAvail(){
		
		var simip=document.getElementById('simstaticip').value;
		//alert("simip")
		$.ajax({
			url : './checkStaticIpAvail/'+simip,
			 type:'GET',
			 dataType : "text",
			 async : false,
			 cache : false,
			 success:function(response){
					//alert(response)
					if(response=='Ip Exist') {
					        bootbox.alert("SIM StaticIp already Exist");
					        $('#simstaticip').val('');
			}
			 }
		});
	}
	
function checkMdnAvailEdit(){
		//alert("Modifys");
		var modmdno=document.getElementById('mdnumberr').value;
		//alert("modmdno")
		$.ajax({
			url : './checkMdnAvail/'+modmdno,
			 type:'GET',
			 dataType : "text",
			 async : false,
			 cache : false,
			 success:function(response){
					//alert(response)
					if(response=='Num Exist') {
					        bootbox.alert("MDN already Exist");
					        $('#mdnumberr').val('');
					        return false;
			}
			 }
		});
	}
	
	function checkStaticIpAvailEdit(){
		//alert("Modify....");
		var modsimip=document.getElementById('simstaticipp').value;
		//alert("modsimip")
		$.ajax({
			url : './checkStaticIpAvail/'+modsimip,
			 type:'GET',
			 dataType : "text",
			 async : false,
			 cache : false,
			 success:function(response){
					//alert(response)
					if(response=='Ip Exist') {
					        bootbox.alert("SIM StaticIp is already Exist");
					        $('#simstaticipp').val('');
					        return false;
			}
			 }
		});
	}
	
</script>

<div class="page-content" >
     <div class="portlet box blue">
     <div class="portlet-title line">
     <div class="portlet-title">
          <div class="caption">
              <i class="fa fa-bars"></i> SIM Details
          </div>
     </div>
     </div>
     <div class="portlet-body">
     <div class="btn-group">
						  <button class="btn green" data-target="#stack1" data-toggle="modal"  id="addSimData" >
						  Add SIM <i class="fa fa-plus"></i>
						  </button><br></br><br>
	</div>
	   
					<div class="btn-group pull-right" >
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i> 
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'SIM Details')">Export
										to Excel</a></li>
							</ul>
					</div>
					
					<table class="table table-striped table-hover table-bordered" id="sample_1">
					   <thead>
                          <tr>
                              <th>Edit</th>
                              <th>SIMSrNumber</th>
                              <th>NSP</th>
                              <th>MDN</th>
                              <th>Static IP</th>
                              <th>Status</th>  
                              <th>Entry By</th>
                              <th>Entry Date</th>
                              <th>Update By</th>
                              <th>Update Date</th>
                          </tr>					
					   </thead>
					   <tbody id="getsimdetails">
					   </tbody>
					</table>
				
     </div>
     </div>
</div>

<div id="stack1" class="modal fade" role="dialog" id="popUp1">
     <div class="modal-dialog">
          <div class="modal-content">
               <div class="modal-header">
               <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
			   <h5 class="modal-title"><b>Add SIM</b></h5>
               </div>
               <div class="modal-body">
               <div id="stack1id" >
                 	<div class="row"><br>
                       	<div class="col-md-6">
                       	SIM Sr Number <font color="red">*</font><input type="text" class="form-control" name="simnumber" id="simnumber" maxlength="15" placeholder="Enter SIM Sr Number" onchange="checkSrnoAvail()">
                       	</div>
                       	<div class="col-md-6">
                       	MDN <input type="text" class="form-control" name="mdnumber" id="mdnumber" maxlength="10" placeholder="Enter MDN" onchange="checkMdnAvail()" >
                       	</div>
                    </div><br>
                    
                    <div class="row"><br>
                       	<div class="col-md-6">
                       	SIM Static IP <input type="text" class="form-control" name="simstaticip" id="simstaticip"  placeholder="Enter SIM Static IP" onchange="checkStaticIpAvail()">
                       	</div>
                       	<div class="col-md-6">
                       	NSP <font color="red">*</font><select class="form-control" name="nsprovider" id="nsprovider" >
                       	<option value="">Select NSP</option>
                        <option value="Airtel">Airtel</option>
                       	<option value="BSNL">BSNL</option>
                       	<option value="Idea">Idea</option>
                       	</select>
                       	</div>
                    </div><br>
               
                   <div class="row"><br>
                       	<div class="col-md-6">
                       	SIM Status <font color="red">*</font><select class="form-control" name="simstatus" id="simstatus" >
                       	<option value="">Select SIM Status</option>
                       	<option value="Installed">Installed</option>
                       	<option value="In Stock">In Stock</option>
                       	<option value="Damaged">Damaged</option>
                       	</select>
                       	</div>
                       	<div class="col-md-6">
                       	Barcode <input type="text" class="form-control" name="barcodeip" id="barcodeip"  placeholder="Enter Barcode">
                       	</div>
                    </div><br>
                    
                   <div class="modal-footer">
                          <button class="btn red pull-left" data-dismiss="modal" class="btn" onclick="return clearfieldsNew()">Close</button>
                          <button class="btn blue pull-right" id="addsimDetails" type="button" onclick="addSimDetails()">Save</button>
                   </div>
               </div>
               </div>
          </div>
     </div>
</div>

<div id="stack2" class="modal fade" role="dialog" id="popUp2">
	<div class="modal-dialog">
		<div class="modal-content">
		<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h5 class="modal-title">Edit SIM Details</h5>
		</div>
		<div class="modal-body">
		    <div id="stack2id" >
		      <div class="row"><br>
		            <div class="col-md-6" hidden="true">
                    id <input type="text" class="form-control" name="simid" id="simid" readonly="readonly" placeholder="Enter SIM Sr Number" >
                    </div>
                    <div class="col-md-6">
                    MDN <input type="text" class="form-control" name="mdnumberr" id="mdnumberr" maxlength="10" placeholder="Enter MDN" onchange="checkMdnAvailEdit()">
                    </div>
                    <div class="col-md-6">
                     SIM Static IP <input type="text" class="form-control" name="simstaticipp" id="simstaticipp" onchange="checkStaticIpAvailEdit()" placeholder="Enter SIM Static IP" >
                     </div>
              </div><br>
            
              <div class="row"><br>
                       	<!-- <div class="col-md-6">
                       	SIM Static IP <input type="text" class="form-control" name="simstaticipp" id="simstaticipp" onchange="checkStaticIpAvailEdit()" placeholder="Enter SIM Static IP" >
                       	</div> -->
                       	<div class="col-md-6">
                       	NSP <font color="red">*</font><select class="form-control" name="nsproviderr" id="nsproviderr" >
                       	<option value="">Select NSP</option>
                        <option value="Airtel">Airtel</option>
                       	<option value="BSNL">BSNL</option>
                       	<option value="Idea">Idea</option>
                       	</select>
                       	</div>
                       	
                       	<div class="col-md-6">
                       	SIM Status <font color="red">*</font><select class="form-control" name="simstatuss" id="simstatuss" >
                       	<option value="">Select SIM Status</option>
                       	<option value="Installed">Installed</option>
                       	<option value="In Stock">In Stock</option>
                       	<option value="Damaged">Damaged</option>
                       	</select>
                       	</div>
              </div><br>
                    
          <!--     <div class="row"><br>
                       	<div class="col-md-6">
                       	SIM Status <font color="red">*</font><select class="form-control" name="simstatuss" id="simstatuss" >
                       	<option value="">Select SIM Status</option>
                       	<option value="Installed">Installed</option>
                       	<option value="In Stock">In Stock</option>
                       	<option value="Damaged">Damaged</option>
                       	</select>
                       	</div>
              </div><br> -->
              
              <div class="modal-footer">
                    <button type="button" data-dismiss="modal"  class="btn">Cancel</button>
					<button class="btn blue pull-right" id="modifyId" type="submit"  value="" onclick="return modifySimDetails();">Modify</button>
			  </div>
					
		    </div>
		</div>
		</div>
	</div>
</div>


<script>

function getSimDetails(){
	$.ajax({
		    url:'./getsimdetails',
		    type:'POST',
		    success:function(response){
		    	
		    	if(response.length !=0){
		    		var html = "";
		    		for (var i = 0; i < response.length; i++){
		    			var element = response[i];
		    			var date=element.entrydate;
		    			var updatedDate=element.updatedate;
		    			html += "<tr>" +
					 "<td><a href='#' onclick='editSimDetails(this.id)' id="+element.id+">Edit</a></td>"   	
						html+="<td>"+(element.simsrno == null ? "" : element.simsrno)+"</td>";
						html+="<td>"+(element.nsprovider == null ? "" : element.nsprovider)+"</td>";
						html+="<td>"+(element.mdnumber == null ? "" : element.mdnumber)+"</td>";
						html+="<td>"+(element.simstaticip == null ? "" : element.simstaticip)+"</td>";
						html+="<td>"+(element.simstatus == null ? "" : element.simstatus)+"</td>";
						html+="<td>"+(element.entryby == null ? "" : element.entryby)+"</td>";
						html+="<td>"+(date == null ? "" :moment(date).format('DD-MM-YYYY hh:mm:ss'))+"</td>";
						html+="<td>"+(element.updateby == null ? "" : element.updateby)+"</td>";
						html+="<td>"+( updatedDate== null ? "" :moment(updatedDate).format('DD-MM-YYYY hh:mm:ss'))+"</td>";
		    		}
    				$('#sample_1').dataTable().fnClearTable();
    				$('#getsimdetails').html(html);
    				loadSearchAndFilter('sample_1');
    				$('#stack1').modal('hide');
		    	}else{
		    		bootbox.alert("No SIM Found");
		    	}
		    }
	})
}

function clearfieldsNew(){
	
	document.getElementById("simnumber").value='';
	document.getElementById("mdnumber").value='';
	document.getElementById("simstaticip").value='';
	document.getElementById("nsprovider").value='';
	document.getElementById("simstatus").value='';

}

</script>