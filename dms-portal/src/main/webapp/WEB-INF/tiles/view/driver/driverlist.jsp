<%@ page import="com.emxcel.dms.portal.constants.UrlConstants" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#DriverDTL").DataTable();
		if("${message}" != "") {
			$(".alert-success-message").fadeIn("fast").delay("3000").fadeOut("fast");
		}
	});
</script>
<div>
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
							<div class="caption">
								<a class="btn btn-success" href="<%=UrlConstants.DRIVER%>">Add
									Driver</a>
							</div>
						</div>
					</div>
					<div class="form-body pal">
						<div class="form-group">
							<h2>Driver List</h2>
							<table class="table table-striped table-bordered table-hover"
								border="1" id="DriverDTL">
								<thead>
									<tr>
										<th>Serial No</th>
										<th>FULL NAME</th>
										<th>CONTACT</th>
										<th>ADDRESS</th>
										<th>CITY NAME</th>
										<th class="text-center">Edit / Delete</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${DriverDTL}" var="Driver"
										varStatus="LoopCounter">
										<tr>
											<td><c:out value="${LoopCounter.count }" /></td>
											<td><c:out value="${Driver.fullName }" /></td>
											<td><c:out value="${Driver.contactNo }" /></td>
											<td><c:out value="${Driver.address}" /></td>
											<td><c:out value="${Driver.cityDetail.cityName}" /></td>
											<td align="center">
												<a class="glyphicon glyphicon-pencil"
													href="<%=UrlConstants.DRIVER%>?driId=${Driver.id }">
												</a>
												&nbsp;|&nbsp;
												<a class="glyphicon glyphicon-trash"
													href="<%=UrlConstants.DELETE_DRIVER %>?driId=${Driver.id}"
													onclick="return confirm('Are you sure want to Delete this Driver?')">
												</a>
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
</div>