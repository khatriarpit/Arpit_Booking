<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--BEGIN CONTENT-->
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-body">
					<div class="portlet-header">
						<div class="caption box-caption">${empty cartypeVo.id ? 'Add' : 'Update'} Car Type</div>
					</div>
					<div class="portlet-body">
						<form:form method="POST" class="form-horizontal" id="carTypevalidate" ondragstart="return false;" ondrop="return false;">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<form:input type="hidden" path="id" value="${cartypeVo.id}"></form:input>
							<form:input type="hidden" path="tanentID" value="${cartypeVo.tanentID}"></form:input>
							<c:if test="${not empty msg}">
								<div class="form-group">
									<label for="inputAddress" class="col-md-2 control-label"></label>
									<div class="col-md-12">
										<div class="alert col-md-12 col-md-offset-1 alert-error-message" role="alert">${msg}</div>
									</div>
								</div>
							</c:if>
							<div class="form-body pal">
								<div class="form-group">
									<label for="inputAddress" class="col-md-3 control-label">
										Car Type <span class='require'>*</span>
									</label>
									<div class="col-md-4">
										<div class="input-icon right">
											<form:input class="form-control" name="carType"
												path="carType" value="${cartypeVo.carType}"></form:input>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-md-6 col-md-offset-3">
										<div class="input-icon">
											<button class="btn orange_bg" type="submit">${empty cartypeVo.id ? 'Save' : 'Update'}</button>
											<a href="<%=UrlConstants.CARTYPE_LIST%>" class="btn grey_bg">Cancel</a>
										</div>
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
<script type="text/javascript">
    $(function(){
        jQuery.validator.addMethod("alpha", function(value, element) {
            return this.optional(element) || /^[A-Za-z]+$/.test(value)
        },"Alpha only");

        $("#carTypevalidate").validate({
            rules : {
            	carType : {
                    required : true,
                    alpha : true
                }
            },
            messages : {
            	carType: {
                	required : "Please Enter Car Type"
                }
            },
            submitHandler : function(form) {
                $("#carTypevalidate").attr("action", "<%=UrlConstants.SAVE_CARTYPE%>?${_csrf.parameterName}=${_csrf.token}");
                form.submit();
            }
        });
    });
</script>