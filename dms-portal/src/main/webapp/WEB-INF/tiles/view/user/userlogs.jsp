<%@page import="com.emxcel.dms.core.business.constants.Constants"%>
<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!--BEGIN CONTENT-->
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-body">
					<div class="form-group">
						<h2>List Of User Log</h2>
						<table class="table table-striped table-bordered" id="logList">
							<thead>
								<tr>
									<th>Serial No</th>
									<th>Login By</th>
									<th>Login Datetime</th>
									<th>Logout Datetime</th>
									<th>Role</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${logListData}" var="log"
									varStatus="loopCounter">
									<tr>
										<td>${loopCounter.count}</td>
										<td>${log.createdBy}</td>
										<td>
											<fmt:formatDate pattern="<%=Constants.PORTAL_DATE_FORMAT %>" var="loginDate" value="${log.login_dateTime}" />
											${loginDate}
										</td>
										<td>
											<fmt:formatDate pattern="<%=Constants.PORTAL_DATE_FORMAT %>" var="logoutDate" value="${log.logout_dateTime}" />
											${logoutDate}
										</td>
										<td>${log.role}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#logList").DataTable();
			})
		</script>
	</div>
</div>