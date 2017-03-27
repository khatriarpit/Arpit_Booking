<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page import="com.emxcel.dms.core.business.constants.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
<script type="text/javascript">
	tinymce.init({ selector:'textarea' });
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#user-role").validate({
			rules : {
				subject : {
					required : true,
					minlength : 2
				},
				template : {
					required : true,
					minlength : 2
				},
				submitHandler : function(form) {
					form.submit();
				}
			}
		});
	});
</script>
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-body">
					<div class="box col-md-6">
						<div class="box-header">
							<div class="caption box-caption">Email Template</div>
							<!--<div class="tools"><i class="fa fa-chevron-up"></i><i data-toggle="modal" data-target="#modal-config" class="fa fa-cog"></i><i class="fa fa-refresh"></i><i class="fa fa-times"></i></div>-->
						</div>
						<div class="form-group text-center">
							<c:if test="${message != ''}">
								<label for="inputAddress" class="col-md-12"> <font
									color="green" style="font-weight: bold" size="4">${message}</font>
								</label>
								<label id="error-alldetail" style="color:red;"></label>
							</c:if>
						</div>
						<div class="box-body col-md-6">
							<form:form action="<%=UrlConstants.SAVE_EMAIL_TEMPLATE%>"
								class="form-horizontal" method="POST" id="user-role">
								<div class="form-body pal">
									<div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">Subject<span
											class='require'>*</span></label>
										<div class="col-md-9">
											<form:input path="subject" class="form-control"
												placeholder="Enter Subject" />
										</div>

										</div>
									</div>
									<div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">
											Text Area<span class='require'>*</span>
										</label>
										<div class="col-md-9">
<!-- 											<textarea>Email Template</textarea> -->
												<form:textarea path="template"/>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-6 col-md-offset-3">

											<div class="input-icon">
												<input type="submit" class="btn btn-success" value="save">
												<button class="btn grey_bg" type="reset">Reset</button>
											</div>
										</div>
									</div>
							</form:form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>