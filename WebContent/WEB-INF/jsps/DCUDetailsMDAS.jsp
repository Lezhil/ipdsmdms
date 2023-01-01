
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<script src="highcharts/exporting.js" ></script>
	<script src="highcharts/highcharts.js" ></script>
	<script src="highcharts/networkgraph.js"> </script>
<!-- <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDFsv7MwN3q9GNl-kasQWAWqLtgAi1aaF4&libraries=geometry"></script>  -->
<script>

$(".page-content").ready(
		function() {
			App.init();
			TableEditable.init();
			FormComponents.init();
			loadSearchAndFilter('sample_1');
			 $('#MDASSideBarContents,#mdmDashId,#dcuDetails').addClass('start active ,selected');
			 $("#MDMSideBarContents,#ADMINSideBarContents,#DATAEXCHsideBarContents,#metermang,#surveydetails,#360MeterDataViewID,#dataValidId,#DPId,#alarmID,#reportsId,#eaId,#todEconomcsId").removeClass('start active ,selected');
			showDCUComm();
			showDCUList();
		});
</script>
<style>
#container {
	min-width: 320px;
	max-width: 700px;
	margin: 0 auto;
}
</style>

<div class="page-content">
 <div  id="alertMsg1" >
</div>
	<div class="row">
		<div class="col-md-12">
			

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i> DCU Communication <!-- <span id="connectedId"></span> -->
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				<div class="row">
				<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<div class="dashboard-stat blue">
						<div class="visual">
							<i class="icofont-network-tower"></i>
						</div>
						<div class="details">
							<div class="number">
								<span id="tdid"></span>
							</div>
							<div class="desc">                           
								Total
							</div>
						</div>
						                 
					</div>
				</div>
				<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<div class="dashboard-stat green">
						<div class="visual">
							<i class="icofont-network-tower"></i>
						</div>
						<div class="details">
							<div class="number"><span id="tcdid"></span></div>
							<div class="desc">Total Communicating</div>
						</div>
						                 
					</div>
				</div>
				
				 <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<div class="dashboard-stat red">
						<div class="visual">
							<i class="icofont-ui-network"></i>
						</div>
						<div class="details">
							<div class="number"><span id="ttncdid"></span></div>
							<div class="desc">Total Non Communicating</div>
						</div>
						  <!-- <a class="more" href="#">
						View more <i class="m-icon-swapright m-icon-white"></i>
						</a> -->                
					</div>
				</div> 
				 <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<div class="dashboard-stat red">
						<div class="visual">
							<i class="icofont-ui-network"></i>
						</div>
						<div class="details">
							<div class="number"><span id="todayid"></span></div>
							<div class="desc">Today </div>
						</div>
						  <!-- <a class="more" href="#">
						View more <i class="m-icon-swapright m-icon-white"></i>
						</a> -->                
					</div>
				</div> 
				<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<div class="dashboard-stat red">
						<div class="visual">
							<i class="icofont-ui-network"></i>
						</div>
						<div class="details">
							<div class="number"><span id="last5id"></span></div>
							<div class="desc">Last 5 Days </div>
						</div>
						  <!-- <a class="more" href="#">
						View more <i class="m-icon-swapright m-icon-white"></i>
						</a> -->                
					</div>
				</div> 
				<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<div class="dashboard-stat red">
						<div class="visual">
							<i class="icofont-ui-network"></i>
						</div>
						<div class="details">
							<div class="number"><span id="last10id"></span></div>
							<div class="desc">Last 10 Days </div>
						</div>
						  <!-- <a class="more" href="#">
						View more <i class="m-icon-swapright m-icon-white"></i>
						</a> -->                
					</div>
				</div> 
			</div>
					<!-- <div class="row" style="margin-left: -1px;">

						<table style="width: 38%">
							<tbody>
								<tr>
								<td><b>DCU ID</b></td>
									<td><select class="form-control select2me input-medium"
										name="dcuid" id="dcuid" onchange="">
											
											<option value='All'>ALL</option>
											
									</select></td>

								

									<td>
										<button type="button" id="viewFeeder" style="margin-left: 20px;"
											onclick="dcuwiseConnMeters()" name="viewFeeder"
											class="btn yellow">
											<b>View</b>
										</button> <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button>
									</td>
								</tr>
							</tbody>
						</table>
					</div> -->
				
					
					<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Total Meters')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					<table class="table table-striped table-hover table-bordered" id="sample_1">
						<thead>
							<tr>
                                <th>S.No</th>
								<th>DCU Sr No</th>
								<th>DCU Id</th>
								<th>Communication Update Time</th>
								<th>Communication Status</th>
								<th>Network Details</th>
							</tr>
						</thead>
						<tbody id="dculist">
						
						</tbody>
					</table>
					
					
				</div>
			</div>
		</div>
	</div>
	
	
						
		
		
		</div>
<div class="modal fade" id="wide" tabindex="-1" role="basic" aria-hidden="true">
								<div class="modal-dialog modal-wide">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<!-- <h4 class="modal-title">Modal Title</h4> -->
										</div>
										<div class="modal-body">
											<div id="container"></div>
										</div>
										<!-- <div class="modal-footer">
											<button type="button" class="btn default" data-dismiss="modal">Close</button>
											<button type="button" class="btn blue">Save changes</button>
										</div> -->
									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal-dialog -->
							</div>   
							<div class="modal fade" id="wide1" tabindex="-1" role="basic" aria-hidden="true">
								<div class="modal-dialog modal-wide">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											 <h4 class="modal-title">DCU Connected Meters</h4> 
										</div>
										<div class="modal-body">
											<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel2('sample_2', 'Total Meters')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					<table class="table table-striped table-hover table-bordered" id="sample_2">
						<thead>
							<tr>
                                <th>S.No</th>
								<th>DCU Gateway Id</th>
								<th>Meter Mesh Id</th>
								<th>Meter No</th>
								
							</tr>
						</thead>
						<tbody id="dculist1">
						
						</tbody>
					</table>
										</div>
										
									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal-dialog -->
							</div>           
<script>
function showDCUList(){
	$.ajax({
		url:"./dcuList",
       type:'GET',
       success:function(res){
          
             html="<option value='All'>ALL</option>";
                 $.each(res,function(i,v){
                     
                    html+='<option value='+v+'>'+v+'</option>';
                     
                     });
                 $("#dcuid").html(html);
                 
           }
       
		});
}

function showDCUComm(){
	$.ajax({
		url:"./dcuCommu",
       type:'GET',
       success:function(res){
          var dcul=res[0];
           $("#tdid").html(dcul[0][0]);
           $("#tcdid").html(dcul[0][1]);
           $("#ttncdid").html(dcul[0][2]);
           $("#todayid").html(dcul[0][3]);
           $("#last5id").html(dcul[0][4]);
           $("#last10id").html(dcul[0][5]);
            var dculist=res[1];
            var html='';
            $.each(dculist,function(i,v){
                   html+="<tr><td>"+(i+1)+"</td><td>"+v[0]+"</td><td>"+v[1]+"</td><td>"+moment(v[2]).format('DD-MM-YYYY hh:mm:ss')+"</td><td>"+v[3]+"</td><td><div class='clearfix'><a class='btn btn-primary' data-toggle='modal' href='#wide'  id='"+v[1]+"@node' onclick='topologyView(this.id)'>Node View</a><a class='btn btn-success' data-toggle='modal' href='#wide'  id='"+v[1]+"@meter' onclick='topologyView(this.id)'>Meter Topology View</a><a class='btn btn-info' data-toggle='modal' href='#wide1'  id='"+v[1]+"' onclick='dcuwiseConnMeters(this.id)'>Meters View</a></div></td></tr>";
                    		
									
					
				   
                });
            clearTabledataContent('sample_1');
            $("#dculist").html(html);
            loadSearchAndFilter('sample_1'); 
           }
       
		});
}
function dcuwiseConnMeters(v){
	//var v=$("#dcuid").val();
	if(v=='All'){
        v='All';
		}
	$.ajax({
             type:'GET',
             url:"./dcuWiseCommuMeters/"+v,
             success:function(res){
            	
                 var html='';
                $.each(res,function(i,v){
                       html+="<tr><td>"+(i+1)+"</td><td>"+v[1]+"</td><td>"+v[0]+"</td><td>"+v[2]+"</td></tr>";
                    
                    });
                clearTabledataContent('sample_2');
                $("#dculist1").html(html);
                loadSearchAndFilter('sample_2');
                $("#wide").hide();
   			 $("#wide1").show();	
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
<script type="text/javascript">
var datav;
var dcuid;
function topologyView(dcuid){
	var dcuNo=dcuid.split('@');
	 dcuid=dcuNo[0].substr(dcuNo[0].length-5,dcuNo[0].length);
	 var urlm="";
	 if(dcuNo[1]=='meter'){
		urlm="./nodeTopologyView/"+dcuNo[0]+"/"+dcuNo[1];
		} 
	 if(dcuNo[1]=='node'){
			urlm="./nodeTopologyView/"+dcuNo[0]+"/"+dcuNo[1];
			}
$.ajax({
	url:urlm,
	type:'GET',
	success:function(res){
		var datav= new Array();
		data = $.parseJSON(res);
		$.each(data, function (i,v)
		{
			if(v.length>0){
				datav.push([v[0],v[1]]);
				}
			
		});
		
		Highcharts.addEvent(
			    Highcharts.seriesTypes.networkgraph,
			    'afterSetOptions',
			    function (e) {
			        var colors = Highcharts.getOptions().colors,
			            i = 0,
			            nodes = {};
			        e.options.data.forEach(function (link) {

			            if (link[0] === dcuid) {
			                nodes[dcuid] = {
			                    id: dcuid,
			                    marker: {
			                        radius: 20
			                    },
			                color: 'black'
			                };
			                nodes[link[1]] = {
			                    id: link[1],
			                    marker: {
			                        radius: 10
			                    },
			                    color: '#66ff33'
			                };
			            } else if (nodes[link[2]] && nodes[link[2]].color) {
			                nodes[link[2]] = {
			                    id: link[2],
			                    color: '#66ff33'
			                };
			            }
			        });

			        e.options.nodes = Object.keys(nodes).map(function (id) {
			            return nodes[id];
			        });
			    }
			);

			Highcharts.chart('container', {
			    chart: {
			        type: 'networkgraph',
			        height: '100%'
			    },
			    title: {
			        text: 'Topology View'
			    },
			    /* subtitle: {
			        text: 'A Force-Directed Network Graph in Highcharts'
			    }, */
			    plotOptions: {
			        networkgraph: {
			            keys: ['from', 'to'],
			            layoutAlgorithm: {
			                enableSimulation: true,
			                friction: -0.9
			            }
			        }
			    },
			    series: [{
			        dataLabels: {
			            enabled: true,
			            linkFormat: ''
			        },
			        data:datav
			    }]
			});
			$("#wide1").hide();
			 $("#wide").show();	 
		}

	
});

}


</script>
