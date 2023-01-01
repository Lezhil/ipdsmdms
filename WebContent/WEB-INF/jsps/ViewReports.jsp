<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%-- 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
 --%>
<%-- <script src="<c:url value='/bsmart.lib.js/formEditSettings.js'/>" type="text/javascript"></script> --%>

<script type="text/javascript">

$(".page-content").ready(function(){
	App.init();
	TableEditable.init();
	FormComponents.init();
	 var mainmenu='${mainmenu}'; var submenu='${submenu}'; var submenuchild='${submenuchild}';
	    $("#"+mainmenu+",#"+submenu+",#"+submenuchild+"").addClass('start active ,selected');
});

$(window).load(function() {
	$("#viewReport").hide();
	$("#loading").hide();
	$("#pagenumber").val("1");
	getAllFolders('/JVVNL');
	//getAllFolders('/SPDCL');
});

function getAllFolders(folderuri){
	$.ajax({
		type : 'GET',
		dataType : 'json',
		async : false,
		url : './reportResourcesPage',
		data : {

			folderuri:folderuri
		},
		success : function(response) {

			var html='<option value="select">Select Report Folder</option>';
			var text='';
			if(response.length>0){
				for(var i=0;i<response.length;i++){
					if(response[i].resourceType=='reportUnit'){
						var reportFolder = (response[i].uri).split("/")[2];
						if(! text.includes("/"+reportFolder+"/") ){
							text+="/"+reportFolder+"/";
							html+="<option value='"+ folderuri + "/" + reportFolder +"'>"+reportFolder+"</option>";
						}
					}
				}
				$("#reportfolder").html(html);
				$("#errormsg").html("Jasper Report");
			}
			else{
				$("#reportfolder").val("Select Report Folder");
				$("#reportpath").val("Select Report");
				$("#reportfolder").html('<option value="select">Select Report Folder</option>');
				$("#reportpath").html('<option value="select">Select Report</option>');
				$("#viewReport").hide();
				$("#errormsg").html("Jasper Report  : Please specify a correct ROOT PATH for Reporting server");
				$("#tbody").html("");
				alert("RESOURCE FOLDERS NOT FOUND");
			}
		}
	});
}

function searchReource(folderuri){
	$("#tbody").html("");
	$("#viewReport").hide();
	$("#pagenumber").val("1");
	$.ajax({
		type : "GET",
		dataType : "JSON",
		async : false,
		url : "./searchReourcePage",
		data : {
			folderuri:folderuri
		},
		success : function(response) {
			var html='<option value="select">Select Report</option>';
			var text='';
			for(var i=0;i<response.length;i++){
				var report = (response[i].uri).split("/")[3];
				if(! text.includes("/"+report+"/") ){
					text+="/"+report+"/";
					html+="<option value='"+ folderuri + "/" + report +"'>"+report+"</option>";
				}
			}
			$("#reportpath").html(html);
		}
	});
}

var params=[];
function getReportParameters(reportname){
	$("#pagenumber").val("1");
	if(reportname=='select'){
		$("#viewReport").hide();
		$("#tbody").html("");
		return false;
	}
	
	params=[];
	$("#viewReport").hide();
	$("#tbody").html("");
	
	$.ajax({
		type : "GET",
		dataType : "JSON",
		async : false,
		url : "http://192.168.10.205:8083/ReportingEngine/JasperReport/parameters",
		data : {
			reportname:reportname
		},
		success : function(response) {
			var html='<tr><td></td></tr>';
			if(response=='null' || response==null || response==''){
				params=[];
				alert("NOT ABLE TO FETCH REPORT PARAMETERS \nIT MIGHT NOT BE CORRECT REPORT PATH");				
			}
			else{
				var html="<tr>";
				for(var i=0;i<response.length;i++){
					params[i]=response[i].id;
					html+='<td style="width:100px;"><b>'+response[i].label+'</b>' +
					'<input class="form-control select2me input-large" type="text" id='+response[i].id+' name='+response[i].id+'></td>';
					if((i+1)%3==0 && i!=0)
						html+='</tr><tr>';
				}
				html+="</tr>";
				$("#tbody").html(html);
			}
		}
	});
}

function getReport(){
	var reportname=$("#reportpath").val();
	if(reportname=='select'){
		alert("please select a report !");
		return false;
	}
	
	var sendParamToBackEnd='';
	for(var i=0;i<params.length;i++){
		if($("#"+params[i]).val()==''){
			alert("please insert all parameters !");
			return false;
		}else{
			sendParamToBackEnd+=params[i]+":"+$("#"+params[i]).val()+",";
		}
	}
	
	$("#viewReport").hide();
	$("#viewrpt").attr("disabled", true);
	document.getElementById('viewrpt').style.cursor='not-allowed'
	$("#loading").show();
	
	$.ajax({
		type : "GET",
		dataType : "text",
		async : false,
		url : "http://192.168.10.205:8083/ReportingEngine/JasperReport/view",
		data : {
			reportname:reportname,
			pageNumber:$("#pagenumber").val(),
			param:sendParamToBackEnd
		},
		success : function(response) {
			$("#showReportHere").html(response);
			$("#loading").hide();
			$("#viewrpt").attr("disabled", false);
			document.getElementById('viewrpt').style.cursor='';
			$("#reportNameDisplay").html("Report Name : "+reportname)
			$("#viewReport").show();
			window.location.hash = '#viewReport';
			//params=[];
		}
	});
}

function editable(id){
	$("#tbody").html("");
	$("#viewReport").hide();
	initAjaxMock();
		$.mockjax({
			url : '/groups',
			cache : false,
			response : function(settings) {
				this.responseText = [ {
					value : 0,
					text : 'Select'
				},

				{
					value : 'YES',
					text : 'YES'
				}, {
					value : 'NO',
					text : 'NO'
				} ];
				log(settings, this);
			}
		});

		$.fn.editable.defaults.inputclass = 'form-control';
		$.fn.editable.defaults.url = '/post';
		var s = "#" + id;
		var assignId = s.substring(1, s.length - 1);

		$(s).editable({
			validate : function(value) {
				if ($.trim(value) == '' || $.trim(value) == '0')
					return 'This field should be required';
				else{
					$("#"+id).val(value);
					getAllFolders(value);
				}
			}
		});

}

function changePageNumber(page){
	if(page<1){
		$("#pagenumber").val("1");
	}else{
		getReport();
	}
}

function exportReport(){
	var sendParamToDownload='';
	for(var i=0;i<params.length;i++){
		sendParamToDownload+=params[i]+":"+$("#"+params[i]).val()+",";
	}
	location.href = 
		/* "http://192.168.2.156:9090/JapserReport/JasperReport/download" */
		"http://192.168.10.205:8083/ReportingEngine/JasperReport/download"
				  + "?reportname=" + $("#reportpath").val()
				  + "&exporttype=" + $("#exportOption").val()
				  + "&pageNumber=" + 0
				  + "&param=" + sendParamToDownload;
}

function exportReportUsingAJAX(){
	$("#viewrpt").attr("disabled", true);
	$("#ajaxExport").attr("disabled", true);

	document.getElementById('viewrpt').style.cursor='not-allowed'
	document.getElementById('ajaxExport').style.cursor='not-allowed'
	
	var sendParamToDownload='';
	for(var i=0;i<params.length;i++){
		sendParamToDownload+=params[i]+":"+$("#"+params[i]).val()+",";
	}
	
	$.ajax({
		type : "GET",
		dataType : "text",
		async : false,
		url : "http://192.168.10.205:8083/ReportingEngine/JasperReport/downloadAjax",
		data : {
			reportname:$("#reportpath").val(),
			exporttype:$("#exportOption").val(),
			pageNumber:0,
			param:sendParamToDownload
		},
		success : function(base64Response) {
			//TO OPEN FILE IN OTHER TAB
			//window.open("data:application/" + $("#exportOption").val() + ";base64," + resultByte);
			
			$("#viewrpt").attr("disabled", false);
			$("#ajaxExport").attr("disabled", false);
			document.getElementById('viewrpt').style.cursor=''
			document.getElementById('ajaxExport').style.cursor=''
			
			if(base64Response=='null' || base64Response==null || base64Response==''){
				alert("NO RESPONSE FROM SERVER");
			}
			else{
					const byteCharacters = atob(base64Response);
					const byteNumbers = new Array(byteCharacters.length);
					for (let i = 0; i < byteCharacters.length; i++) {
						byteNumbers[i] = byteCharacters.charCodeAt(i);
					}
					const byteArray = new Uint8Array(byteNumbers);
					var blob = new Blob([ byteArray ], {
						type : "application/" + $("#exportOption").val()
					});
					const url = window.URL.createObjectURL(blob);
					const a = document.createElement('a');
					a.style.display = 'none';
					a.href = url;
					a.download = $("#reportpath").val() + "_" + new Date().toDateString() + "." + $("#exportOption").val();
					document.body.appendChild(a);
					a.click();
					window.URL.revokeObjectURL(url);
				}
			}
		});
}

function checkHead(){
	var username=prompt("enter usernmae");//"praveen";
	var password=prompt("enter password");//"bcits";
	$.ajax({
		type : "POST",
		dataType : "json",
		async : false,
		url : "http://192.168.10.205:8083/ReportingEngine/JasperReport/getDataFromApplication",
		headers: {
		   "Authorization":"Basic " + btoa(username + ":" + password)
		},
		data : {},
		success : function(response) {
			if ("ERROR" in response){
				alert("ERROR:"+response.ERROR);
			}else{
				alert("data:"+response.data);
			}
		}
	});
}
</script>
<div class="page-content">
	<%-- <jsp:include page="LocationHierarchy.jsp" /> --%>
	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption" style="width: 100%;height: 20px;;">
				<i class="fa fa-bars"></i><p id="errormsg">Jasper Report </p>
			</div>
		</div>
		<div class="portlet-body">
			<table style="border-collapse: separate; border-spacing: 8px;">
				<thead>
					<tr>
						<td style="width: 28%"><b>Root Folder : </b> <a href="#"
							id="rootname" data-type="text" data-pk="1"
							data-original-title="Enter Root Folder"
							onclick="editable(this.id)"><b>/JVVNL</b></a></td>

						<td style="width: 100px;"><b>Report Folder</b> <select
							class="form-control select2me input-large"
							onchange="searchReource(this.value)" name="reportfolder" id="reportfolder">
								<option value="">Select Report Type</option>
						</select></td>

						<td style="width: 100px;"><b>Select Report:</b> <select
							class="form-control select2me input-large" id="reportpath"
							name="reportpath" onchange="getReportParameters(this.value);">
								<option value="select">Select Report</option>
						</select></td>
					</tr>
				</thead>
				<tbody id="tbody"></tbody>
			</table>

			<div class="row" style="align-content: center;padding-left: 440px;padding-top: 18px;">
				<div class="col-md-12">
					<button class="btn blue pull-left" id="viewrpt" type="button" onclick="getReport()">View Report</button>
				</div>
			</div>
		</div>

	</div>
	
	<div class="portlet box blue" id="viewReport">
		<div class="portlet-title">
			<div class="caption" style="width: 100%;height:20px">
				<i class="fa fa-bars"></i><p id="reportNameDisplay"></p>
			</div>
		</div>
		<div id="pageno">
		<table style="width: 100%;">
			<tr>
				<td align="left" style="padding-left: 10px;">
					PageNumber 
					<input type="number" value="0" onchange="changePageNumber(this.value);" id="pagenumber"
					style="width: 5%; text-align: center; border-inline: double; box-sizing: unset; border-block-end-style: outset;">
				</td>
				<td align="right" style="padding-right: 5px;">
					<select id="exportOption">
						<option value="pdf">PDF</option>
						<option value="xlsx">XLSX</option>
						<option value="xls">XLS</option>
						<option value="rtf">RTF</option>
						<option value="csv">CSV</option>
						<option value="xml">XML</option>
						<option value="docx">DOCX</option>
						<option value="pptx">PPTX</option>
					</select>
			   <!-- <input type="button" id="normalExport" value="Export" onclick="exportReport();"> -->
					<input type="button" id="ajaxExport" value="Export" onclick="exportReportUsingAJAX();">
				</td>
			</tr>
		</table>
			
		</div>
		<div class="portlet-body" id="showReportHere"></div>
	</div>
	
	<div id="loading">
		<table style="width: 100%;">
			<tr>
				<td align="center"><img alt="Loading.." src="assets/img/loading.gif" style="width: 10%;"></td>
			</tr>
			<tr>
				<td align="center" style="padding-top: 25px;">Loading ...</td>
			</tr>
		</table>
	</div>
</div>