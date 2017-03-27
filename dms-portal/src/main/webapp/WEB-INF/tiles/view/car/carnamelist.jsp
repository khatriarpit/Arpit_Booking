<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#CarNamesDTL").DataTable();
		if("${message}" != "") {
			$(".alert-success-message").fadeIn("fast").delay("3000").fadeOut("fast");
		}
	});
</script>
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
					<a class="btn btn-success" href="<%=UrlConstants.ADD_CARNAME%>">Add
						Car Model</a>
				</div>
				<div class="portlet-body">
					<div class="form-group">
						<h2>Car Model List</h2>
						<table class="table table-striped table-bordered table-hover"
							border="1" id="CarNamesDTL">
							<thead>
								<tr>
									<th>Serial No</th>
									<th>Car Model</th>
									<th>Car Type</th>
									<th class="text-center">Edit / Delete</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${CarNameDTL}" var="car"
									varStatus="LoopCounter">
									<tr>
										<td><c:out value="${LoopCounter.count }" /></td>
										<td><c:out value="${car.carName }" /></td>
										<td><c:out value="${car.carType.carType }" /></td>
										<td align="center">
										    <a class="glyphicon glyphicon-pencil"
											    href="<%=UrlConstants.ADD_CARNAME%>?carId=${car.id}"
											    title="Edit">
											</a>
											<!-- &nbsp;|&nbsp; -->
											<%--Updated By-Johnson Chunara On 22-03-2017 As Per SRS--%>
											<%-- <a class="glyphicon glyphicon-trash"
											    title="Delete"
											    href="<%=UrlConstants.DELETE_CARNAME%>?carId=${car.id }"
											    onclick="return confirm('Are you sure want to Delete this Car Name?')">
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