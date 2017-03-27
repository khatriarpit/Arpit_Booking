<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
	$(document).ready(
			function() {
				$("#userList").DataTable();
				if ("${message}" != "") {
					$(".alert-success-message").fadeIn("fast").delay("3000")
							.fadeOut("fast");
				}
			});
</script>
<!--BEGIN CONTENT-->
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="caption">
					<c:if test="${not empty message}">
						<div class="form-group">
							<label for="inputAddress" class="col-md-2 control-label"></label>
							<div class="col-md-12">
								<div class="alert col-md-6 alert-success-message" role="alert">${message}</div>
							</div>
						</div>
					</c:if>
					<div class="caption">
						<security:authorize access="hasRole('ROLE_SUPERADMIN')">
							<a class="btn btn-success" title="Add SuperAdmin"
								href="<%=UrlConstants.USER%>">Add SuperAdmin</a>
						</security:authorize>
						<security:authorize access="hasRole('ROLE_ADMIN')">
							<a class="btn btn-success" title="Add User"
								href="<%=UrlConstants.USER%>">Add User</a>
						</security:authorize>
					</div>
					<c:if test="${add_restrict eq 'false'}">
						<div class="caption">
							<h4 class="error">You Can Not Add More Users</h4>
						</div>
					</c:if>
				</div>
				<hr>
				<div class="portlet-body">
					<div class="form-group">
						<security:authorize access="hasRole('ROLE_SUPERADMIN')">
							<h2>List Of SuperAdmins</h2>
						</security:authorize>
						<security:authorize access="hasRole('ROLE_ADMIN')">
							<h2>List Of Users</h2>
						</security:authorize>

						<table class="table table-striped table-bordered table-hover"
							id="userList" border="1">
							<thead>
								<tr>
									<th>Serial No</th>
									<th>First Name</th>
									<th>Last Name</th>
									<th>Mobile No</th>
									<th>Status</th>
									<th class="text-center">Edit | Delete</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${userList}" var="user"
									varStatus="loopcounter">
									<tr>
										<td><c:out value="${loopcounter.count}" /></td>
										<td><c:out value="${user.firstName}" /></td>
										<td><c:out value="${user.lastName}" /></td>
										<td><c:out value="${user.contactNo}" /></td>
										<td><c:out
												value="${user.enabled eq 0 ? 'Active' : 'Deactive'}" /></td>
										<td align="center"><a class="glyphicon glyphicon-pencil"
											title="Edit" href="<%=UrlConstants.USER%>?userId=${user.id}">
										</a> &nbsp;|&nbsp; <a class="glyphicon glyphicon-trash"
											title="Delete"
											href="<%=UrlConstants.DELETE_USER%>?userId=${user.id}"
											onclick="return confirm('Are you sure want to Delete this Super Admin?')"
											${user.defaultFlag eq true ? 'disabled=disabled' : ''}> </a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>