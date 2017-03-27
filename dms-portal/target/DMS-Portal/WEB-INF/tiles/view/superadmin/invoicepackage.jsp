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
							<%-- <div class="caption">${empty invoice.id ? 'Add' : 'Update'} Invoice Category</div> --%>
						</div>
						<div class="box-body">
							<form:form method="POST" class="form-horizontal" id="invoicecategoryvalidate" ondragstart="return false;" ondrop="return false;">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<form:input type="hidden" path="id" value="${invoice.id}"></form:input>
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
											Invoice Category <span class='require'>*</span>
										</label>
										<div class="col-md-9">
											<div class="input-icon right">
												<form:input class="form-control" 
													path="categoryname" value="${invoice.categoryname}" maxlength="30"></form:input>
											</div>
										</div>
									</div>
									<div class="form-group">
										<!--<label for="inputAddress" class="col-md-2 control-label">&nbsp;</label>-->
										<div class="col-md-6 col-md-offset-3">
											<div class="input-icon">
												<button class="btn orange_bg" value="Save">${empty invoice.id ? 'Save' : 'Update'}</button>
												<a href="<%=UrlConstants.LIST_IINVOICE_PACKAGE%>" class="btn grey_bg">Cancel</a>
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
            return this.optional(element) || /^[A-Za-z]+$/.test(value)
        },"Alpha only");

        $("#invoicecategoryvalidate").validate({
            rules : {
            	categoryname : {
                    required : true,
                    alpha : true
                }
            },
            message: {
            	categoryname: "Please enter Invoice Category"
            },
            submitHandler : function(form) {
                $("#invoicecategoryvalidate").attr("action", "<%=UrlConstants.SAVE_INVOICE_PACKAGE%>?${_csrf.parameterName}=${_csrf.token}");
                form.submit();
            }
        });
    });
</script>