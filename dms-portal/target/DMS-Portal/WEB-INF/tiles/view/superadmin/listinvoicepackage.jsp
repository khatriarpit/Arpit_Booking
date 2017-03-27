<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#invoicePackageList").DataTable();
	});
</script>
<!--BEGIN CONTENT1213-->
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-header">
					<div class="caption">
						<a class="btn btn-success" title="Add New Car Type" href="<%=UrlConstants.INVOICE_PACKAGE%>">Add
							Invoice Category</a>
					</div>
				</div>
				<c:if test="${!empty message}">
					<div class="input-icon right">
						<label class="error"><strong>${message}</strong></label>
					</div>
				</c:if>
				<c:if test="${!empty invoicePackageList}">
					<h2>Invoice Packages</h2>
					<div>
						<table class="table table-striped table-bordered table-hover"
							align="left" border="1" id="invoicePackageList">
							<thead>
								<tr>
									<th>Serial No</th>
									<th>Invoice Package</th>
									<th align="center">Edit</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${invoicePackageList}" var="invoice"
									varStatus="LoopCounter">
									<tr>
										<td>${LoopCounter.count}</td>
										<td>${invoice.categoryname }</td>
										<td align="center">
											<a class="glyphicon glyphicon-pencil"
												href="<%=UrlConstants.INVOICE_PACKAGE %>?invoicePackageid=${invoice.id}" title="Edit">
											</a>
											<!-- &nbsp;|&nbsp; -->
											<%--Updated By-Johnson Chunara On 22-03-2017 As Per SRS--%>
											<%-- <a class="glyphicon glyphicon-trash" title="Delete"
												href="<%=UrlConstants.DELETE_INVOICE_PACKAGE %>?invoicePackageid=${invoice.id}"
												onclick="return confirm('Are you sure want to Delete this Invoice Package?')">
											</a> --%>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</div>