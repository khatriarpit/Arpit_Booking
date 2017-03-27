<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#carDetails").DataTable();
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
					<div></div>
					<div class="caption">
						<div class="caption">
							<a class="btn btn-success" title="Add New Template" href="<%=UrlConstants.EMAIL_TEMPLATE%>">Add
								New Template</a>
						</div>
					</div>
				</div>
				<div class="form-group">
					<h2>List Of Templates</h2>
					<table class="table table-striped table-bordered table-hover"
						id="carDetails" border="1">
						<thead>
							<tr>

								<th>Serial No</th>
								<th>Tenant Id</th>
								<th>Subject</th>
								<th>Template</th>
								<th class="text-center">Edit / Delete</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${emailTemplateList}" var="templateList"
								varStatus="loopcounter">
								<tr>
									<td><c:out value="${loopcounter.count}" /></td>
									<td><c:out value="${templateList.tanentID}" /></td>
									<td><c:out value="${templateList.subject}" /></td>
									<td><c:out value="${templateList.template}" /></td>
									<%-- <td align="center">
										<a class="glyphicon glyphicon-pencil" title="Edit"
											href="<%=UrlConstants.CAR%>?carId=${carVo.id}">
										</a>
										&nbsp;|&nbsp;
										<a class="glyphicon glyphicon-trash" title="Delete"
											href="<%=UrlConstants.DELETE_CAR%>?id=${carVo.id}"
											onclick="return confirm('Are you sure want to Delete this Car?')">
										</a>
									</td> --%>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>