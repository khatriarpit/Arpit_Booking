<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(
			function() {
				$("#cartypeDetails").DataTable();
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
				<c:if test="${not empty message}">
					<div class="form-group">
						<label for="inputAddress" class="col-md-2 control-label"></label>
						<div class="col-md-12">
							<div class="alert col-md-6 alert-success-message" role="alert">${message}</div>
						</div>
					</div>
				</c:if>
				<div class="portlet-header">
					<div class="caption">
						<a class="btn btn-success" title="Add New Company Type"
							href="<%=UrlConstants.COMAPANY_TYPE%>">Add Company Type</a>
					</div>
				</div>
				<div class="portlet-body">
					<div class="form-group">
						<h2>Company Type List</h2>
						<table class="table table-striped table-bordered table-hover"
							align="left" border="1" id="cartypeDetails">
							<thead>
								<tr>
									<th>Serial No</th>
									<th>Company Type</th>
									<th class="text-center">Edit / Status</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${companytypeDetails}" var="company"
									varStatus="LoopCounter">
									<tr>
										<td>${LoopCounter.count}</td>
										<td>${company.companyType}</td>
										<td align="center">
											<a class="glyphicon glyphicon-pencil"
											href="<%=UrlConstants.COMAPANY_TYPE %>?companyTypeid=${company.id}"
											title="Edit"> </a> &nbsp;|&nbsp;
											<a class="btn orange_bg"
												title="Active"
												href="<%=UrlConstants.UPDATE_COMPANY_TYPE %>?id=${company.id}&status=${company.companyStatus}"
												${company.companyStatus eq true ? 'disabled=' : ''}>Active</a>
											<a class="btn orange_bg"
												title="Inactive"
												href="<%=UrlConstants.UPDATE_COMPANY_TYPE %>?id=${company.id}&status=${company.companyStatus}" 
												${company.companyStatus eq false ? 'disabled=' : ''}>Inactive</a>
										</td>
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