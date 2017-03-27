<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--BEGIN CONTENT123-->
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-body">
					<div class="box col-md-6">
						<div class="box-header">
							<div class="caption">${empty rateofContract.id ? 'Add' : 'Update'}Rate Of Contract</div>
						</div>
						<div class="box-body">
							<form:form method="POST" class="form-horizontal" id="rateofContract" ondragstart="return false;" ondrop="return false;">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<form:input type="hidden" path="id" value="${rateofContract.id}"></form:input>
								<div class="form-group">
									<label for="inputAddress" class="col-md-2 control-label"></label>
									<div class="col-md-6">
										<div class="input-icon right">
											<strong class="error"><c:out value="${msg}"></c:out></strong>
										</div>
									</div>
								</div>
								<div class="form-body pal">
									<div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">
											Rate Of Contract <span class='require'>*</span>
										</label>
										<div class="col-md-9">
											<div class="input-icon right">
												<form:input class="form-control" name="carType"
													path="rateOfContract" value="${rateofContract.rateOfContract}" maxlength="30"></form:input>
											</div>
										</div>
									</div>
									<div class="form-group">
										<!--<label for="inputAddress" class="col-md-2 control-label">&nbsp;</label>-->
										<div class="col-md-6 col-md-offset-3">
											<div class="input-icon">
												<button class="btn orange_bg" type="submit" >Save</button>
												<a href="<%=UrlConstants.LIST_RATE_OF_CONTRACT%>" class="btn grey_bg">Cancel</a>
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
</div>
<script type="text/javascript">
    $(function(){
        jQuery.validator.addMethod("alpha", function(value, element) {
            return this.optional(element) || /^[A-Z a-z]+$/.test(value)
        },"Please Enter Characters Only");

        $("#rateofContract").validate({
            rules : {
            	rateOfContract : {
                    required : true,
                    alpha : true
                }
            },
            message: {
            	rateOfContract: "Please Enter Rate Of Contract"
            },
            submitHandler : function(form) {
                $("#rateofContract").attr("action", "<%=UrlConstants.SAVE_RATE_OF_CONTRACT%>?${_csrf.parameterName}=${_csrf.token}");
                form.submit();
            }
        });
    });
</script>