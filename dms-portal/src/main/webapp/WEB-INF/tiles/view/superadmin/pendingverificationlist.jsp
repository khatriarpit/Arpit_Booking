<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@page import="com.emxcel.dms.core.business.constants.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#pendingVerificationList").DataTable();
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
					<div class="caption">Pending Verification List</div>
				</div>
				<div class="portlet-body">
					<div class="col-md-12 pal">
						<table class="table table-striped table-bordered table-hover" border="1"
							id="pendingVerificationList">
							<thead>
                                <tr>
                                    <th>No</th>
                                    <th>Tenant Name</th>
                                    <th>Creation Date</th>
                                    <th>Contact Person</th>
                                    <th>Mobile No</th>
                                    <th>Email Id</th>
                                    <th class="text-center">Edit / Delete</th>
                                </tr>
							</thead>
							<tbody>
                                <c:forEach items="${pendingVerificationList}"
                                    var="pendingVerification" varStatus="LoopCounter">
                                    <tr>
                                        <td class="text-center">${LoopCounter.count}</td>
                                        <td><c:out
                                                value="${pendingVerification.tenant.companyname}" /></td>
                                        <td><fmt:formatDate value='${pendingVerification.createdDate}' var='createdDate' type='date' pattern='<%=Constants.PORTAL_DATE_FORMAT%>' /><c:out value="${createdDate}" /></td>
                                        <td><c:out
                                                value="${pendingVerification.tenant.firstName1} ${pendingVerification.tenant.lastName1}" /></td>
                                        <td><c:out value="${pendingVerification.tenant.contactno}" /></td>
                                        <td><c:out value="${pendingVerification.tenant.emailid}" /></td>
                                        <td align="center">
                                            <a title="Edit"
                                                class="glyphicon glyphicon-pencil"
                                                href="<%=UrlConstants.PENDING_VERIFICATION_PAGE%>?id=${pendingVerification.id}">
                                            </a>
                                            &nbsp;|&nbsp;
                                            <a title="Delete"
                                                class="glyphicon glyphicon-trash" title="Delete"
                                                href="<%=UrlConstants.PENDING_VERIFICATION_DELETE%>?id=${pendingVerification.id}"
                                                onclick="return confirm('Are you sure want to Delete this Package?')">
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