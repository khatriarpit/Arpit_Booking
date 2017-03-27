<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="assets/scripts/jquery.validate.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#tripID")
				.change(
						function(event) {
							var tripID = $(this).val();
							$
									.ajax({
										type : "POST",
										url : "<%=UrlConstants.GET_DRIVER_BY_TRIP%>",
										data : "${_csrf.parameterName}=${_csrf.token}&tripID="
												+ tripID,
										success : function(data) {
											if(data.length > 0) {
												$('#driId').html('<option value="0">Select Driver Name</option>');
						                        for ( var i = 0; i < data.length; ++i) {
						                            var driver = data[i];
						                            $('#driId').append("<option value=\"" + driver.id + "\">" + driver.fullName+ "</option>");
						                   	 }
											} else {
												$('#driId').html('<option value="0">Select Driver Name</option>');
											}
										},
										error : function(e) {
										}
									});
							
							$
							.ajax({
								type : "POST",
								url : "<%=UrlConstants.SEARCH_BY_TRIP%>",
								data : "${_csrf.parameterName}=${_csrf.token}&tripID="
										+ tripID,
								success : function(data) {
									if(data != ''){
										$('#carnoDiv').show();
										$('#edriver').show();
										$('#driverDiv').show();
										$('#carNo').val(data.carNo);
										$('#submitButton').show();
										$('#fullName').val(data.carDetail.driver.fullName);
									} else {
										$('#carnoDiv').hide();
										$('#edriver').hide();
										$('#driverDiv').hide();
										$('#tripmsj').show();
										$('#submitButton').hide();
										
									}
								},
								error : function(e) {
								}
							});
						});
	});

	$(document)
			.ready(
					function() {
						jQuery.validator.addMethod("driverNameRequired",
								function(value, element) {
									var val = value;
									if (val === "0") {
										return false;
									} else {
										return true;
									}
								}, "Please Select Driver!");

						$("#changedriver")
								.validate(
										{
											rules : {
												driId : {
													required : true,
													driverNameRequired : true
												},
												tripID : "required"
											},
											// Specify the validation error messages
											messages : {
												driId : "Please Select Driver!",
												tripID : "Please Enter Trip Id!"
											},
											submitHandler : function(form) {
												$("#changedriver").attr("action","<%=UrlConstants.CHANGE_DRIVER%>?${_csrf.parameterName}=${_csrf.token}");
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
						<div class="caption">Change Driver</div>
					</div>
					<div class="portlet-body">
						<form:form method="POST" class="form-horizontal" id="changedriver" ondragstart="return false;" ondrop="return false;">
							<%-- <form:form action="changedri.htm" method="POST" class="form-horizontal"
										> --%>
							<input class="form-control" type="hidden"
								name="${_csrf.parameterName}" value="${_csrf.token}" />
							<div class="form-body pal">
							<div class="caption">
							<h4 class="error" id="tripmsj" style="display: none">Invalid Trip ID !!</h4>
						</div>
								<div class="form-group">
									<label for="inputAddress" class="col-md-2 control-label">Trip
										Id<span class='require'>*</span></label>
									<div class="col-md-6">
										<div class="input-icon right">
											<form:input class="form-control" path="tripID"></form:input>
										</div>
									</div>
								</div>
								<div class="form-group" id="carnoDiv" style="display: none;">
									<label for="inputAddress" class="col-md-2 control-label">Car
										Number </label>
									<div class="col-md-6">
										<div class="input-icon right">
											<form:input class="form-control" name="carNo" path="carNo"
												readonly="true"></form:input>
										</div>
									</div>
								</div>
								<div class="form-group" id="edriver" style="display: none;">
									<label for="inputAddress" class="col-md-2 control-label">Existing
										Driver Name</label>
									<div class="col-md-6">
										<div class="input-icon right">
											<input class="form-control" name="fullName"
												id="fullName" readonly="readonly" />
										</div>
									</div>
								</div>
								<div class="form-group" id="driverDiv" style="display: none;">
									<label for="inputAddress" class="col-md-2 control-label">Driver
										Name<span class='require'>*</span></label>
									<div class="col-md-6">
										<div class="input-icon right">
											<form:select class="form-control" path="driId"
												itemValue="driId">
												<form:option value="0">Select Driver</form:option>
												<c:forEach var="driver" items="${DriverList}">
													<form:option value="${driver.id}">${driver.fullName}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
								</div>
								<div class="form-group" id="submitButton" style="display: none;">
									<label for="inputAddress" class="col-md-2 control-label">&nbsp;</label>
									<div class="col-md-6">
										<input type="submit" value="Submit" class="btn btn-success" />
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