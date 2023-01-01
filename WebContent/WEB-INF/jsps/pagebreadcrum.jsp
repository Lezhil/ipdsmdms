<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div class="col-md-12">
		<ul class="page-breadcrumb breadcrumb">
			<c:url var="home" value="/welcome" />
			<c:url value="/images/site/breadcrumb-home.png" var="homeimage" />
			<c:forEach var="bc" items="${breadcrumb.tree}" varStatus="status">
				<c:choose>

					<c:when test="${ViewName=='DashBoard'}">
						<li><i class="fa fa-home"></i>DashBoard</li>
					</c:when>

					<c:when test="${status.index==0}">
						<li><i class="fa fa-home"></i><a href="#"
							onclick="loadPage()" id="userSchedule">Home</a></li>
					</c:when>
					<c:when
						test="${status.index == fn:length(breadcrumb.tree)-1 && status.index!=0}">
						<li><i class="fa fa-angle-right"></i>${bc.name}</li>
					</c:when>
					<c:otherwise>
						<li><a href="${bc.value}">${bc.name}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			 <c:forEach var="entry" items="${sessionScope.currentBreadCrumb}">
				<c:choose>
					<c:when test="${entry.currentPage == true}">
						<li><i class="fa fa-angle-right"></i>${entry.label}</li>
					</c:when>
					<c:otherwise>
						<a href="${entry.url}">${entry.label}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<li class="pull-right">
				<div id="dashboard-report-range"
					class="dashboard-date-range tooltips" data-placement="top"
					data-original-title="Change dashboard date range">
					<i class="fa fa-calendar"></i> <span></span> <i
						class="fa fa-angle-down"></i>
				</div>
			</li>
		</ul>

	</div>
</div>
