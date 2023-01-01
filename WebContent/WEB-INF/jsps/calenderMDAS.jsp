<!-- BEGIN PAGE -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script src="<c:url value='/resources/assets/scripts/html2canvas.js'/>" type="text/javascript"></script>
<!-- <script src="http://html2canvas.hertzen.com/build/html2canvas.js"></script> -->
<link
	href="resources/assets/plugins/fullcalendar/fullcalendar/fullcalendar.css"
	rel="stylesheet" />

<script>
	$(".page-content")
			.ready(

					function() {
						if ('${showTotalModems.size()}' > 0) {
							$('#totalFeeder').show();
						}
						if ('${showWorkingsModems.size()}' > 0) {
							$('#workingFeeder').show();
						}
						if ('${showNotWorkingsModems.size()}' > 0) {
							$('#notWorkingFeeder').show();
						}
						App.init();
						//Index.initCalendar(); // init index page's custom scripts
						checkOnload();
						Index.initMiniCharts();
						TableEditable.init();
						FormComponents.init();
						
						/*  loadSearchAndFilter('table_1'); 
						 loadSearchAndFilter('table_2'); 
						 loadSearchAndFilter('table_3');  */
						$('#MDASSideBarContents,#mdmDashId,#calenderEventsMDAS')
								.addClass('start active ,selected');
						$(
								'#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
								.removeClass('start active ,selected');
					});
</script>



<script>
$(document).ready(function(){

var element = $("#calendar"); // global variable
var getCanvas; // global variable
 
    $("#btn-Preview-Image").on('click', function () {
         html2canvas(element, {
         onrendered: function (canvas) {
                $("#previewImage").append(canvas);
                getCanvas = canvas;
             }
         });
    });

	$("#btn-Convert-Html2Image").on('click', function () {
    var imgageData = getCanvas.toDataURL("image/jpeg");
    var newData = imgageData.replace(/^data:image\/jpeg/, "data:application/octet-stream");
    $("#btn-Convert-Html2Image").attr("download", "calender.jpeg").attr("href", newData);
	});

});

</script>




<style>
.alert {
	border: 1px solid transparent;
	border-radius: 4px;
	margin-bottom: 10px;
	padding: 5px;
}

.modal-dialog {
	padding: 40px 100px 30px 220px;
	width: 100%;
}

.table thead>tr>th, .table tbody>tr>th, .table tfoot>tr>th, .table thead>tr>td,
	.table tbody>tr>td, .table tfoot>tr>td {
	padding: 8px;
	line-height: 1.428571429;
	vertical-align: top;
}
</style>




<div class="page-content">

	<div class="row">
		<div class="col-md-12">
			<div class="portlet box blue calendar">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-calendar"></i>Calendar
					</div>

				</div>

				<div class="portlet-body light-grey">

					<form role="form">
						<div class="form-body row">
							<div class="form-group col-xs-3">
								<label>Select Year</label> <select id="yearSelectId"
									class="form-control select2me input-medium">

								</select>
							</div>
							<div class="form-group col-xs-3">
								<label>Select Month</label> <select id="monthSelectId"
									class="form-control select2me input-medium">
								</select>
							</div>


							<div class=" right" style="margin-top: 25px;">
								<button type="submit" class="btn green"
									onclick="check(); return false;">Check</button>
								<a class="btn yellow" id="btn-Convert-Html2Image" href="#">Download</a>
								<input id="btn-Preview-Image" style="display: none"
									type="button" value="Preview" />
							</div>
						</div>
					</form>

					<div id="calendar"></div>
				</div>
			</div>

		</div>
	</div>
	<div style="display: none" id="previewImage"></div>
</div>




<script>
	var currentYear = new Date().getFullYear();
	var currentMonth = new Date().getMonth(); 
	var cascadedDropDownMonthId = "#monthSelectId";
	
		//Adding Last 10 Years to Year Drop Down
	for (var i = currentYear; i > currentYear - 10; i--) {
		$("#yearSelectId").append(
				'<option value="' + i.toString() + '">' + i.toString()
						+ '</option>');
	}


	//$(cascadedDropDownMonthId).prop("disabled", true);
	$("#yearSelectId").change(function() {
		
		monthsSelect();
	});

	function monthsSelect() {
		
		
		var currentSelectedValue = $(yearSelectId).val();

		/*  if (currentSelectedValue == "-1")
		 {
		     $(cascadedDropDownMonthId).prop("disabled", true);
		 }
		 else
		 { 
		     $(cascadedDropDownMonthId).prop("disabled", false);
		 */
		//Get Current Year from Dropdown and Converting to Integer for performing math operations
		var currentSelectedYear = parseInt($(yearSelectId).val());

		//As Index of Javascript Month is from 0 to 11 therefore totalMonths are 11 NOT 12
		var totalMonths = 11;
		if (currentSelectedYear == currentYear) {
			totalMonths = currentMonth;
		}
		var monthNames = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" ];
		//Emptying the Month Dropdown to clear our last values
		$(cascadedDropDownMonthId).empty();

		$(cascadedDropDownMonthId)
		$("#gate option[value='Gateway 2']").prop('selected', true);
		//Appending Current Valid Months
		for (var month = 00; month <= totalMonths; month++) {
			if (currentMonth == month && currentYear == currentSelectedValue  ) {
				$(cascadedDropDownMonthId).append(
						'<option value="' + (month + 1) + '" selected>'
								+ monthNames[month] + '</option>');
			} else {
				$(cascadedDropDownMonthId).append(
						'<option value="' + (month + 1) + '">'
								+ monthNames[month] + '</option>');
			}
		}
		
		const monthNames1 =  [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
			"Aug", "Sep", "Oct", "Nov", "Dec" ];
			const d = new Date();				
		var currentmonthName = monthNames1[d.getMonth()];
		$("#monthSelectId").append(currentmonthName).trigger("change");
	}

	function check() {
		var year = $("#yearSelectId").val();
		var month = $("#monthSelectId").val();
		year = moment(year).format('YYYY');
		if (month.length == 1) {
			month = "0" + month;
		}
		//alert(year+"---"+month);
		var mon = month - 1;
		$('#calendar').fullCalendar('destroy');
		/* 	var toTime = $.fullCalendar.moment('"+year+"-"+month+"-01');
			alert(toTime); */
		/* $('#calendar').fullCalendar( 'gotoDate', toTime ); */
		$('#calendar').fullCalendar({
			defaultDate : new Date(year, month, 01),
			/* eventClick: function(calEvent, jsEvent, view) 
			{
				var selectedDate = moment(calEvent.start).format('DD-MM-YYYY');
				var eventType=calEvent.id.split("_")[0];
				calendarEventClick(eventType,selectedDate);
			}, */
			events : './getStatsCalenderMDAS/' + month + '/' + year
		});

		$('#calendar').fullCalendar('gotoDate', year, mon);

		setTimeout(function(){
			//alert(year+"---"+month);
			$("#btn-Preview-Image").click();
			
	    },4000);
	}

	function checkOnload() {
		var d = new Date();

		d.setDate(d.getDate() - 1);

		var year = moment(d).format('YYYY');
		var month = moment(d).format('MM');
		$('#calendar').fullCalendar('destroy');
		$('#calendar').fullCalendar({
			eventClick : function(calEvent, jsEvent, view) {
				var selectedDate = moment(calEvent.start).format('DD-MM-YYYY');
				var eventType = calEvent.id.split("_")[0];
				//calendarEventClick(eventType, selectedDate);
			},
			events : './getStatsCalenderMDAS/' + month + '/' + year
		});
		var year = $("#yearSelectId").val(year);
		monthsSelect();
		var month = $("#monthSelectId").val(month);


		setTimeout(function(){
			//alert(year+"New---"+month);
			$("#btn-Preview-Image").click();
			
	    },3000);
		
		
	}

	function calendarEventClick(eventType, selectedDate) {
		window.location.href = "./calendarEventClick?eventType=" + eventType
				+ "&selectedDate=" + selectedDate;
	}
</script>
<style>
.fc-event-time {
	display: none;
}

.fc-event-title {
	font-weight: bold !important;
}

.fc-button-prev {
	display: none;
}

.fc-button-next {
	display: none;
}
</style>

