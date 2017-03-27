<%@page import="com.emxcel.dms.core.business.constants.Constants"%>
<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#PackageDTL").DataTable();
		if ("${message}" != null && "${message}" != '') {
			$(".alert-success-message").fadeIn("fast").delay("3000")
					.fadeOut("fast");
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
							<div class="alert col-md-6 alert-success-message">${message}</div>
						</div>
					</div>
				</c:if>
				<div class="portlet-header">
					<a class="btn btn-success" href="<%=UrlConstants.TENANT_LIST%>">Back</a>
					<a class="btn btn-success"
						href="<%=UrlConstants.PACKAGE_CREATE%>?tanentID=${tanentID}">Add
						Package</a>
				</div>
				<div class="portlet-body">
					<h2>Created Package List</h2>
					<c:if test="${!empty PackageDTL}">
						<table class="table table-striped table-bordered table-hover"
							border="1" id="PackageDTL">
							<thead>
								<tr>
									<th>Serial No</th>
									<th>Package Type</th>
									<th>Name</th>
									<th>Cars</th>
									<th>Users</th>
									<th>Drivers</th>
									<th>Subscription From</th>
									<th>Subscription To</th>
									<th>TotalAmount</th>
									<th class="text-center">Action Button</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${PackageDTL}" var="PackageDTL"
									varStatus="LoopCounter">
									<tr>
										<td><c:out value="${LoopCounter.count}" /></td>
										<td><c:out
												value="${PackageDTL.packageStatus eq '1' ? 'Flexible' : 'Fixed'}" /></td>
										<td><c:out value="${PackageDTL.name}" /></td>
										<td><c:out value="${PackageDTL.cars}" /></td>
										<td><c:out value="${PackageDTL.users}" /></td>
										<td><c:out value="${PackageDTL.drivers}" /></td>
										<td><fmt:formatDate
														value="${PackageDTL.fromDate}"
														var='fromDate' type='date'
														pattern='<%=Constants.PORTAL_ONLY_DATE_FORMAT%>' />
											<c:out value="${fromDate}"/>
										</td>
										<td>
											<fmt:formatDate
														value="${PackageDTL.toDate}"
														var='toDate' type='date'
														pattern='<%=Constants.PORTAL_ONLY_DATE_FORMAT%>' />
											<c:out value="${toDate}"/>
										</td>
										<td><c:out value="${PackageDTL.totalAmount}" /></td>
										<td align="center">
											<a class=" btn glyphicon glyphicon-pencil"
	       											title="Edit" href="<%=UrlConstants.PACKAGE_CREATE%>?packageId=${PackageDTL.id}&tanentID=${tanentID}" >
											</a>
											<a class="glyphicon glyphicon-trash btn"
												title="Delete"
												href="<%=UrlConstants.DELETE_PACKAGE_CREATE%>?packageId=${PackageDTL.id}&tanentID=${tanentID}"
												onclick="return confirm('Are you sure want to Detele this Package?')" ${PackageDTL.approvedFlag eq true ? 'disabled=' : ''}>
											</a>
	                                    	   <!-- ${tenant.emailFlag eq true || tenant.packageEnable eq false ? 'disabled=' : ''} -->
                                    		<a title="Resend Verification Mail" class="btn fa fa-envelope" onclick="resendEmail('${PackageDTL.id}')" ${PackageDTL.emailFlag eq true ? 'disabled=' : ''}></a>
                                    		<a title="Renew Package" href="<%=UrlConstants.RENEW_PACKAGE%>?packageId=${PackageDTL.id}" class="btn fa fa-refresh" ${PackageDTL.enableRenew eq true ? 'disabled=' : ''}></a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	function resendEmail(packageId){
		callblock();
        $.ajax({
            type : 'POST',
            url : '<%=UrlConstants.RESEND_EMAIL%>',
            data : '${_csrf.parameterName}=${_csrf.token}&id='+packageId,
            success:function(data){
            	endblockUI();
            	$("#alert-success-message-resend-email").hide();
                if(data != "false") {
                	$("#alert-success-message-resend-email").show();
                	$("#alert-success-message-resend-email").html("Email Send Successfully !!!");
                } else {
                	$("#alert-success-message-resend-email").show();
                	$("#alert-success-message-resend-email").html("Something Went Wrong !!!");
                }
            },
            error: function(){
            	alert("error");
            }
        });
	}
</script>