<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#invoicelist").DataTable();
		if("${message}" != "") {
			$(".alert-success-message").fadeIn("fast").delay("3000").fadeOut("fast");
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
					<c:if test="${empty invoicelist}">
					<div class="caption">
						<a class="btn btn-success" title="Add Static Invoice" href="<%=UrlConstants.INVOICE_STATIC%>">Add
							Static Invoice</a>
					</div>
					</c:if>
				</div>
				<div class="portlet-body">
					<div class="form-group">
						<h2>Static Invoice</h2>
						<table class="table table-striped table-bordered table-hover"
							align="left" border="1" id="invoicelist">
							<thead>
								<tr>
									<th>Serial No</th>
									<th>Company Name</th>
									<th>Mobile Number</th>
									<th>Email Id</th>
									<th>Address</th>
									<th class="text-center">Edit</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${invoicelist}" var="invoice"
									varStatus="LoopCounter">
									<tr>
										<td>${LoopCounter.count}</td>
										<td>${invoice.companyName }</td>
										<td>${invoice.phoneNo }</td>
										<td>${invoice.emailid }</td>
										<td>${invoice.address1 }&nbsp;${invoice.address2 }&nbsp;${invoice.address3 } </td>
										<td align="center">
											<a class="glyphicon glyphicon-pencil"
												href="<%=UrlConstants.INVOICE_STATIC %>?id=${invoice.id}" title="Edit">
											</a></td>
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