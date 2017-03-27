<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
        jQuery.validator.addMethod("carTypeRequired", function(
                value, element) {
            var val = value;
            if (val === "0") {
                return false;
            } else {
                return true;
            }
        }, "Please Select Car Type");

        $("#addcarname").validate({
            rules : {
                carTypeId : {
                    required : true,
                    carTypeRequired : true
                },
                carName : {
                    required : true

                }
            },
            // Specify the validation error messages
            messages : {
                carTypeId : "Please Select CarType",
                carName : "Please Enter Car Model"
            },
            submitHandler : function(form) {
                $("#addcarname")
                        .attr("action", "<%=UrlConstants.SAVE_CARNAME%>?${_csrf.parameterName}=${_csrf.token}");
					form.submit();
				}
			});
		});
</script>
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-body">
					<div class="box col-md-12">
						<div class="box-header">
							<div class="caption box-caption">${empty carName.id ? 'Add' : 'Update'}
								Car Model</div>
						</div>
					</div>
					<div class="box col-md-6">	
						<div class="box-body">
							<form:form method="POST" class="form-horizontal" id="addcarname" ondragstart="return false;" ondrop="return false;">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<div class="form-body pal">
									<form:input path="id" type="hidden" value="${carName.id}" />
									<form:input path="tanentID" type="hidden"
										value="${carName.tanentID}" />
									<div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">Car
											Type <span class='require'>*</span>
										</label>
										<div class="col-md-9">
											<div class="input-icon right">
												<form:select class="form-control" path="carTypeId"
													itemValue="carTypeId">
													<form:option value="0">Select Type</form:option>
													<c:forEach var="car" items="${TypeDTL}">
														<form:option id="${carType}" value="${car.id}"
															selected="${car.id eq carName.carTypeId ? 'selected':'' }">${car.carType}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label for="inputAddress" class="col-md-3 control-label">
											Car Model<span class='require'>*</span>
										</label>
										<div class="col-md-9">
											<div class="input-icon right">
												<form:input class="form-control" maxlength="50"
													path="carName" value="${carName.carName}"></form:input>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-6 col-md-offset-3">
											<div class="input-icon">
												<button class="btn orange_bg" type="submit">${empty carName.id ? 'Submit' : 'Update'}</button>
												<a href="<%=UrlConstants.CARNAME_LIST%>" class="btn grey_bg">Cancel</a>
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