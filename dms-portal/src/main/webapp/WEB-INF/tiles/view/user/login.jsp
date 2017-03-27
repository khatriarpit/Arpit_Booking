<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<link type="text/css" rel="stylesheet"
	href="assets/css/themes/blue-grey.css">
<script src="assets/scripts/jquery.validate.min.js"></script>
<script type="text/javascript" language="javascript">

    jQuery(document).ready(function($) {

        if (window.history && window.history.pushState) {

            $(window).on('popstate', function() {
                var hashLocation = location.hash;
                var hashSplit = hashLocation.split("#!/");
                var hashName = hashSplit[1];

                if (hashName !== '') {
                    var hash = window.location.hash;
                    if (hash === '') {
                        window.location= '/login';
                        return false;
                    }
                }
            });

            window.history.pushState('forward', null, './login');
        }

    });

</script>
<script type="text/javascript">
	$(document).ready(function() {
		jQuery.validator.addMethod("noSpace", function(value, element) {
			return value.indexOf(" ") < 0 && value != "";
		}, "No space please and don't leave it empty");

		$("#loginForm").validate({
			rules : {
				username : {
					required : true,
					noSpace : true
				},
				password : {
					required : true,
					noSpace : true
				},
				submitHandler : function(form) {
					form.submit();
				}
			}
		});
	});
</script>
<!--BEGIN TO TOP-->
<a id="totop" href="#"><i class="fa fa-angle-up"></i></a>
<!--END BACK TO TOP-->
<div id="signin-page-content" class="animated bounceInDown">
	<form action="<c:url value='<%=UrlConstants.LOGIN%>' />" method='POST'
		class="form" id="loginForm">
		<h1 class="block-heading">
			<img src="assets/images/logo.png">
		</h1>
		<p>Please Sign In</p>
		<div class="form-group">
			<c:if test="${not empty error}">
				<label class="error">${error}</label>
			</c:if>
			<c:if test="${not empty msg}">
				<label class="error">${msg}</label>
			</c:if>
		</div>
		<div class="form-group">
			<div class="input-icon">
				<i class="fa fa-user"></i> <input class="form-control" id="username"
					placeholder="Username or Email Id" type="text" name='username'
					autofocus>
			</div>
		</div>
		<div class="form-group">
			<div class="input-icon">
				<i class="fa fa-key"></i> <input class="form-control"
					placeholder="Password" name="password" id="password"
					type="password">
			</div>
		</div>
		<div class="form-group">
			<div class="row">
				<div class="col-md-6">
					<div class="checkbox">
						<label><input type="checkbox" id="rememberme" name="remember-me">&nbsp; Remember me</label>
					</div>
				</div>
				<div class="col-md-6">
					<button type="submit"
						class="btn btn-primary btn-primary_bl orange_bg pull-right">
						Submit &nbsp;<i class="fa fa-chevron-circle-right"></i>
					</button>
				</div>
			</div>
		</div>
		<hr>
		<a id="btn-forgot-pwd"
			href="<%=UrlConstants.FORGOTTEN_PASSWORD_PAGE%>">Forgot your
			password?</a>
	</form>
</div>
