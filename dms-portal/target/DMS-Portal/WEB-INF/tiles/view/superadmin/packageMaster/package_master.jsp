<%@page import="com.emxcel.dms.core.business.constants.Constants"%>
<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
        jQuery.validator.addMethod("daysvalidataion", function(
                value, element) {
            var val = value;
            if (val > 365) {
                return false;
            } else {
                return true;
            }
        }, "Please Enter Days Below 365");

        $("#addPackage").validate({
            rules : {
                name : "required",
                cars : "required",
                carrate : "required",
                users : "required",
                userrate : "required",
                drivers : "required",
                driverrate : "required",
                validity :{
                    required:true,
                    daysvalidataion:true,
                    maxlength : 3
                }
            },
            messages : {
                name : "Please enter Name!",
                cars : "Please enter Cars!",
                carrate : "Please enter Rate per car!",
                users : "Please enter Users!",
                userrate :"Please enter Rate per User!",
                drivers : "Please enter Drivers!",
                driverrate :"Please enter Rate per Driver!",
                validity :{
                    required:"Please Enter Validity",
                    daysvalidataion:"Please Enter Days Below 365",
                    maxlength : "Max Length 3 Digit"
                }
            },
            submitHandler : function(form) {
                $("#addPackage").attr("action","<%=UrlConstants.SAVE_PACKAGE%>?${_csrf.parameterName}=${_csrf.token}");
					form.submit();
					}
				});
			});
</script>
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-header">
					<div class="caption">Master package/subscription</div>
				</div>
				<div class="portlet-body">
					<form:form method="POST" class="form-horizontal" id="addPackage" ondragstart="return false;" ondrop="return false;">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<form:input path="id" type="hidden" value="${packages.id}" />
						<form:input path="status" type="hidden" value="${packages.status}" />
						<div class="form-group">
							<label for="inputAddress" class="col-md-2 control-label">Name<span
								class='require'>*</span></label>
							<div class="col-md-6">
								<div class="input-icon right">
									<form:input class="form-control" maxlength="50" path="name"
										value="${packages.name}" placeholder="Enter Name"></form:input>
									<c:if test="${not empty message}">
										<label class="error">${message}</label>
									</c:if>
								</div>
							</div>
						</div>
						<%@include file="../common-package-page.jsp"%>
						<div class="form-group">
							<div class="col-md-6 col-md-offset-2">
								<hr>
								<div class="input-icon">
									<button class="btn orange_bg" type="Submit">Save</button>
									<a href="<%=UrlConstants.PACKAGE_LIST%>" class="btn grey_bg"
										type="button">Cancel</a>
								</div>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>