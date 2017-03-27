<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<link type="text/css" rel="stylesheet" href="assets/css/themes/blue-grey.css">
<SCRIPT type="text/javascript">
window.onbeforeunload = function() { return "You work will be lost."; };
	window.history.forward();
	function noBack() { window.history.forward(); }
	
</SCRIPT>
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
			<p class="Black_text">please check your email id and click the secure link.<br>
            If you donot see our email check your spam folder:</p>
			<c:if test="${not empty error}">
				<p class="white_text">${error}</p>
			</c:if>
			<div class="form-group">
				<div class="input-icon">
					<a href="<%=UrlConstants.FORGOTTEN_PASSWORD_PAGE %>" class="btn btn-lg orange_bg">Resend Link</a>
					<a href="<%=UrlConstants.LOGIN %>" class="btn btn-default btn-lg">Login Page</a>
				</div>
			</div>
			<hr>
		</form>
	</c:if>
</div>