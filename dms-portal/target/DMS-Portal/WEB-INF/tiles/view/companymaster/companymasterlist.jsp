<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#companyMasterList").DataTable();
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
				<div class="caption">
					<a class="btn btn-success" title="Add New User"
						href="<%=UrlConstants.ADD_COMPANY_MASTER%>">Add Company
						Master</a>
				</div>
				<div class="portlet-body">
					<div class="form-group">
						<h2>List Of Company Master</h2>
						<table class="table table-striped table-bordered table-hover"
							id="companyMasterList" border="1">
							<thead>
								<tr>
									<th>Serial No</th>
									<th>Company Name</th>
									<th>Company Id</th>
									<th>Contact Person Name</th>
									<th>Email Id</th>
									<th class="text-center">Edit / Delete</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${companyMasterList}" var="companyMaster"
									varStatus="loopcounter">
									<tr>
										<td><c:out value="${loopcounter.count}" /></td>
										<td><c:out value="${companyMaster.comapanyName}" /></td>
										<td><c:out value="${companyMaster.companyId}" /></td>
										<td><c:out value="${companyMaster.contactPersonName}" /></td>
										<td><c:out value="${companyMaster.emailId}" /></td>
										<td align="center">
											<a class="glyphicon glyphicon-pencil"
												href="<%=UrlConstants.ADD_COMPANY_MASTER%>?companyMasterId=${companyMaster.id}">
											</a>&nbsp;|&nbsp;
											<a class="glyphicon glyphicon-trash" title="Delete"
												href="<%=UrlConstants.DELETE_COMAPANY_MASTER%>?companyMasterId=${companyMaster.id}"
												onclick="return confirm('Are you sure want to Delete this Company Master?')">
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