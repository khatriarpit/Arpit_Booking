<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#cartypeDetails").DataTable();
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
					<div class="caption">
						<a class="btn btn-success" title="Add New Car Type" href="<%=UrlConstants.ADD_CARTYPE%>">Add
							Car Type</a>
					</div>
				</div>
				<div class="portlet-body">
					<div class="form-group">
						<h2>Car Type List</h2>
						<table class="table table-striped table-bordered table-hover"
							align="left" border="1" id="cartypeDetails">
							<thead>
								<tr>
									<th>Serial No</th>
									<th>Car Type</th>
									<th class="text-center">Edit</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${cartypeDetails}" var="car"
									varStatus="LoopCounter">
									<tr>
										<td>${LoopCounter.count}</td>
										<td>${car.carType }</td>
										<td align="center">
											<a class="glyphicon glyphicon-pencil"
												href="<%=UrlConstants.ADD_CARTYPE %>?id=${car.id}" title="Edit">
											</a>
											<!-- &nbsp;|&nbsp; -->
											<%--Updated By-Johnson Chunara On 22-03-2017 As Per SRS--%>
											<%-- <a class="glyphicon glyphicon-trash" title="Delete"
												href="<%=UrlConstants.DELETE_CARTYPE %>?id=${car.id}"
												onclick="return confirm('Are you sure want to Delete this CarType?')">
											</a> --%>
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