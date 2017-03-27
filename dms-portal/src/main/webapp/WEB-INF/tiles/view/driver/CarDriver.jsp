<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="assets/scripts/jquery.validate.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#id").change(function(event) {
			var carId = $(this).val();
			$.ajax({
				type : "POST",
				url : "<%=UrlConstants.GET_CARNO%>",
				data : "${_csrf.parameterName}=${_csrf.token}&carId=" + carId,
				success : function(data) {
					$('#carNameValidation').val(data.carName.carName);
					$('#carTypeValidation').val(data.carType.carType);
				},
				error : function(e) {
					alert("error in car number")
				}
			});
		});
	});
</script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						jQuery.validator.addMethod("carNumberRequired",
								function(value, element) {
									var val = value;
									if (val === "Select CarNo") {
										return false;
									} else {
										return true;
									}
								}, "Please Select Car Number!");
						jQuery.validator.addMethod("driNameRequired", function(
								value, element) {
							var val = value;
							if (val === "Select Driver Name") {
								return false;
							} else {
								return true;
							}
						}, "Please Select Driver Name!");
						$("#carDriver")
								.validate(
										{
											rules : {
												id : {
													required : true,
													carNumberRequired : true
												},
												driId : {
													required : true,
													driNameRequired : true
												},
											},
											messages : {
												id : "Please Select Car Number!",
												driId : "Please Select Driver Name!"
											},
											submitHandler : function(form) {
												$("#carDriver")
														.attr("action",
																"<%=UrlConstants.UPDATE_CAR_DRIVER_MAPPING%>?${_csrf.parameterName}=${_csrf.token}");
												form.submit();
											}
										});
					});
</script>
<div>
	<div class="page-content">
		<div class="row">
			<div class="col-lg-12">
				<div class="portlet">
					<div class="portlet-header">
						<div class="caption">Car Driver</div>
					</div>
					<div class="portlet-body">
						 <form:form method="POST" id="carDriver" class="form-horizontal" ondragstart="return false;" ondrop="return false;">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<div class="form-body pal">
								<div class="form-group">
									<label for="inputAddress" class="col-md-2 control-label">Select
										Car No</label>
									<div class="col-md-6">
										<div class="input-icon right">
											<form:select class="form-control" path="id" itemValue="id">
												<form:option value="Select CarNo">Select CarNo</form:option>
												<c:forEach var="carNoList" items="${carNoList}">
													<form:option id="${carNo}" value="${carNoList.id}">${carNoList.carNo}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="inputAddress" class="col-md-2 control-label">Car
										Name</label>
									<div class="col-md-6">
										<div class="input-icon right">
											<input type="text" class="form-control"
												id="carNameValidation" readonly="true" />
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="inputAddress" class="col-md-2 control-label">Car
										Type</label>
									<div class="col-md-6">
										<div class="input-icon right">
											<input type="text" class="form-control"
												id="carTypeValidation" readonly="true" />
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="inputAddress" class="col-md-2 control-label">Driver
										Name</label>
									<div class="col-md-6">
										<div class="input-icon right">
											<form:select class="form-control" path="driId"
												itemValue="driId">
												<form:option value="Select Driver Name">Select Driver Name</form:option>
												<c:forEach var="driver" items="${DriverList}">
													<form:option value="${driver.id}">${driver.fullName}</form:option>
												</c:forEach>
											</form:select>
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
							</div>
						</form:form> 
					</div>
				</div>
			</div>
		</div>
	</div>
</div>