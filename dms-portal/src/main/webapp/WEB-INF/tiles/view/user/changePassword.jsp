<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
$(document).ready(function() {
	$.validator.addMethod("passwordRequired", function (value, element) {
        return /[\@\#\$\%\^\&\*\(\)\_\+\!]/.test(value) && /[a-z]/.test(value) && /[0-9]/.test(value) && /[A-Z]/.test(value)
    }, 'Must not be equal to 1.');
	//form validation rules
	$("#register-form").validate({
		rules : {
			password : {
				required : true,
				minlength : 5
			},
			newPassword : {
				required : true,
				minlength : 5,
				maxlength : 15,
				passwordRequired : true
			},
			conpass : {
				required : true,
				maxlength : 15
			}
		},
		messages : {
			password : "Please enter Password",
			newPassword : {
				required : "Please Enter New Password",
				minlength : "Please Enter Minimum 5 Character Password",
				maxlength : "Please Enter Maximum 15 Character Password",
				passwordRequired : "Please Enter Password Including 1 Uppercase and 1 Special character and 1 Number must be there, Like : 'Example@123'",
			},
			conpass : "Please Enter Password Including 1 Uppercase and 1 Special character and 1 Number must be there, Like : 'Example@123'"
		},
		submitHandler : function(form) {
			checkConfirmPassword(form);
		}
	});
});
	function checkConfirmPassword(form) {
		var confirm = $("#conpass").val();
		var password = $("#newPassword").val();
		if (password != confirm) {
			$("#conpassError").show();
			return false;
		} else {
			$("#conpassError").hide();
			$("#register-form").attr('action', "<%=UrlConstants.UPDATE_PASSWORD%>?${_csrf.parameterName}=${_csrf.token}");
			form.submit();
		}
	}
	$(document).ready(function() {
		$("#UserDTL").DataTable();
	});
</script>
<!--BEGIN CONTENT-->
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-header">
					<div class="caption">Change Password</div>
				</div>
				<div class="portlet-body">
					<form:form method="POST" id="register-form" class="form-horizontal" ondragstart="return false;" ondrop="return false;">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<div class="form-group text-center">
							<c:if test="${message != ''}">
								<label for="inputAddress" class="col-md-12"> <font
									color="green" style="font-weight: bold" size="4">${message}</font>
								</label>
							</c:if>
							<%-- <c:if test="${message != ''}">
								<label for="inputAddress" class="col-md-12"> <font
									color="red" style="font-weight: bold" size="4">${message}</font>
								</label>
							</c:if> --%>
						</div>
						<div class="form-group">
							<label for="inputAddress" class="col-md-2 control-label">Password</label>
							<div class="col-md-6">
								<div class="input-icon right">
									<form:password class="form-control" path="password"
										id="password" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="inputAddress" class="col-md-2 control-label">New
								Password</label>
							<div class="col-md-6">
								<div class="input-icon right">
									<form:password class="form-control" path="newPassword"
										id="newPassword" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="inputAddress" class="col-md-2 control-label">Confirm
								Password</label>
							<div class="col-md-6">
								<div class="input-icon right">
									<input class="form-control" type="password" name="cpass"
										id="conpass" /> <label style="display: none;"
										id="conpassError" class="error">Enter Confirm Password
										Same as Password</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="inputAddress" class="col-md-2 control-label">&nbsp;</label>
							<div class="col-md-6">
								<input class="btn btn-success" class="submit" type="submit"
									value="Submit" />
							</div>
						</div>
					</form:form>

				</div>
			</div>
		</div>
	</div>
</div>
<!--END CONTENT-->