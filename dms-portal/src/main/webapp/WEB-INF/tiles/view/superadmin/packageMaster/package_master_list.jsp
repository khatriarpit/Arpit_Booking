<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#PackageDTL").DataTable();
	});
</script>
<div>
	<div class="page-content">
		<div class="row">
			<div class="col-lg-12">
				<div class="portlet">
					<div class="caption">
                        <c:if test="${not empty message}">
                            <div class="alert alert-success-message">${message}</div>
                        </c:if>
                        <a class="btn btn-success" href="<%=UrlConstants.PACKAGE%>?tanentID=${tanentID}">Add Package</a>
                    </div>
					<div class="portlet-body">
						<h2>Package List</h2>
						<table class="table table-striped table-bordered table-hover"
							border="1" id="PackageDTL">
							<thead>
								<tr>
									<th>Serial No</th>
									<th>Package Name</th>
									<th>Users</th>
									<th>Cars</th>
									<th>Drivers</th>
									<th>Validity</th>
									<th>status</th>
									<th class="text-center">Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${PackageDTL}" var="PackageDTL"
									varStatus="LoopCounter">
									<tr>
										<td>${LoopCounter.count}</td>
										<td>${PackageDTL.name}</td>
										<td>${PackageDTL.users}</td>
										<td>${PackageDTL.cars}</td>
										<td>${PackageDTL.drivers}</td>
										<td>${PackageDTL.validity}</td>
										<td>${PackageDTL.status eq 0 ? 'Active' : 'Inactive' }</td>
										<td align="center">
											<a title="Edit" class="btn glyphicon glyphicon-pencil"
												href="<%=UrlConstants.PACKAGE%>?packageId=${PackageDTL.id }"  ${PackageDTL.status eq 1 ? 'disabled=' : ''}>
											</a>
											<%--Updated By-Johnson Chunara On 22-03-2017 As Per SRS--%>
											<%-- <a title="Delete" class="btn glyphicon glyphicon-trash"
												href="<%=UrlConstants.DELETE_PACKAGE%>?packageId=${PackageDTL.id }" onclick="return confirm('Are you sure want to Detele this Package?')" ${PackageDTL.status eq 0 ? 'disabled=' : ''}>
											</a> --%>
											<c:if test="${PackageDTL.status eq 0}">
												<a title="Inactive"
													href="<%=UrlConstants.INACTIVE_PACKAGE_MASTER%>?packageId=${PackageDTL.id }" onclick="return confirm('Are you sure want to Inactive this Package?')">Inactive
												</a>
											</c:if>
											<c:if test="${PackageDTL.status eq 1}">
												<a title="Active"
													href="<%=UrlConstants.INACTIVE_PACKAGE_MASTER%>?packageId=${PackageDTL.id}" onclick="return confirm('Are you sure want to Active this Package?')">Active</a>
											</c:if>
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