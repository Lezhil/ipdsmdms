<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>

<!-- <script>
$(".page-content").ready(function(){
	$('#LFzone').val('').trigger('change');
	$('#LFcircle').val('').trigger('change');
	$('#LFtown').val('').trigger('change');
});
</script> -->

<c:if test="${officeType eq 'circle'}">
	<div class="row" style="margin-left: -1px;">


		<div class="col-md-3" id="regid">
			<strong>Region:</strong>
			<div id="LFzoneTd" class="form-group">
				<select class="form-control select2me" id="LFzone" name="LFzone"
					disabled="disabled" onchange="showCircle(this.value);">
					<option value="${newRegionName}">${newRegionName}</option>

				</select>
			</div>
		</div>

		<div class="col-md-3" id="circid">
			<strong>Circle:</strong>
			<div id="LFcircleTd" class="form-group">
				<select class="form-control select2me" id="LFcircle" name="LFcircle"
					disabled="disabled">
					<option value="${officeName}">${officeName}</option>
					
				</select>
			</div>
		</div>


		<div class="col-md-3" id="toid">
			<strong>Town:</strong>
			<div id="LFtownTd" class="form-group">
				<select class="form-control select2me" id="LFtown" name="LFtown"
					onchange="showResultsbasedOntownCode(this.value);">
					<option value=''>Select Town</option>
					<option value="%">ALL</option> 
					<c:forEach items="${townList}" var="elements">
						<option value=${elements[0]}>${elements[0]}-${elements[1]}</option>
						
					</c:forEach>
					
				</select>
			</div>
		</div>
	</div>
</c:if>


<c:if test="${officeType eq 'region'}">
	<div class="row" style="margin-left: -1px;">
		<div class="col-md-3" id="regid">
			<strong>Region:</strong>
			<div id="LFzoneTd" class="form-group">
				<select class="form-control select2me" id="LFzone" name="LFzone"
					disabled="disabled" onchange="showCircle(this.value);">

					<option value="${newRegionName}">${newRegionName}</option>
				</select>
			</div>
		</div>

		<div class="col-md-3" id="circid">
			<strong>Circle:</strong>
			<div id="circleTd" class="form-group">
				<select class="form-control select2me" id="LFcircle" name="LFcircle"
					onchange="showTownNameandCode(this.value);">
					<option value="">Select Circle</option>
					<option value="%">ALL</option>
					<c:forEach items="${circleList}" var="elements">
						<option value="${elements}">${elements}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="col-md-3" id="toid">
			<strong>Town:</strong>
			<div id="LFtownTd" class="form-group">
				<select class="form-control select2me" id="LFtown" name="LFtown"
					onchange="showResultsbasedOntownCode(this.value);">
					<option value="">Select Town</option>
				</select>
			</div>
		</div>
	</div>
</c:if>


<c:if test="${officeType eq 'corporate'}">
	<div class="row" style="margin-left: -1px;">
		<div class="col-md-3" id="regid">
			<strong>Region:</strong>
			<div id="LFzoneTd" class="form-group">
				<select class="form-control select2me" id="LFzone" name="LFzone"
					onchange="showCircle(this.value);">
					<option value="">Select Region</option>
					<option value="%">ALL</option>
					<c:forEach items="${allRegions}" var="elements">
						<option value="${elements}">${elements}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="col-md-3" id="circid">
			<strong>Circle:</strong>
			<div id="LFcircleTd" class="form-group">
				<select class="form-control select2me" id="LFcircle" name="LFcircle"
					onchange="showTownNameandCode(this.value);">
					<option value="">Select Circle</option>
					<option value="%">ALL</option>
				</select>
			</div>
		</div>

		<div class="col-md-3" id="toid">
			<strong>Town:</strong>
			<div id="LFtownTd" class="form-group">
				<select class="form-control select2me" id="LFtown" name="LFtown"
					onchange="showResultsbasedOntownCode(this.value);">
					<option value="">Select Town</option>
				</select>
			</div>
		</div>
	</div>
</c:if>
