<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<link type="text/css" rel="stylesheet" href="assets/css/themes/blue-grey.css">
<script type="text/javascript">
	$(document).ready(function() {
		$("#loginForm").validate({
			rules : {
				username : "required"
			},
			messages : {
				username : "Please Enter Username or Email"
			},
			submitHandler : function(form) {
				$("#loginForm").attr('action', "<%=UrlConstants.FORGOTTEN_PASSWORD%>");
				$("#loginForm").submit();
			}
		});
	});
</script>
<div id="signin-page-content"
	class="animated bounceInDown body-background">
	<c:if test="${not empty successMsg}">
		<h1 class="white_text">${successMsg}</h1>
		<a href="<%=UrlConstants.LOGIN%>" class="white_text">Sign In</a>
	</c:if>
	<c:if test="${empty successMsg}">
		<form name='loginForm' method="POST" class="form" id="loginForm" ondragstart="return false;" ondrop="return false;">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" /> <input type="hidden" name="color"
				id="color" value="${themeColor}" />
			<h1 class="block-heading">
				<img src="assets/images/logo.png">
			</h1>
			<p class="white_text">Enter Email or Username</p>
			<c:if test="${not empty error}">
				<p class="white_text">${error}</p>
			</c:if>
			<div class="form-group">
				<div class="input-icon">
					<i class="fa fa-user"></i><input class="form-control"
						placeholder="username" type="text" name='username' id="username"
						autofocus>
				</div>
			</div>
			<div class="form-group">
				<button type="submit"
					class="btn btn-primary btn-primary_bl login-button extra_width">
					Get Password &nbsp; <i class="fa fa-chevron-circle-right"></i>
				</button>
			</div>
			<hr>
		</form>
	</c:if>
</div>