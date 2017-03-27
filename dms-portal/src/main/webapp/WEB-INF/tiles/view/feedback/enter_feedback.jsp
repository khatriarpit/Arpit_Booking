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
				<div class="portlet-header">
					<div class="caption">FEEDBACK</div>
				</div>
				<div class="portlet-body">
					<div class="row">
						<div class="col-lg-2 col-md-2"></div>
						<div class="form-group col-lg-8 col-md-8">
							<div class="col-lg-12 col-md-12 col-sm-12"
								style="padding-top: 20px;">
								<label class="col-lg-5 col-md-5 col-sm-12 control-label">ENTER
									TRIP ID</label>
								<div class="col-lg-7 col-md-7 col-sm-12">
									<input type="text"
										class="form-control form-cascade-control input-small"
										name="tripId" id="tripId" />
									<div class="pull-left" id="tripIDErrorMessage"
										style="display: none; color: red;"></div>
									<br />
									<div id="enter-trip-id"
										class="btn btn-success text-center submeet_btn">SUBMIT</div>
								</div>
							</div>
							<div class="col-lg-12 col-md-12 col-sm-12 text-center"
								style="padding-top: 20px;">
								<!-- feedback-report.htm -->
								<a href="<%=UrlConstants.FEEDBACK_REPORT %>" class="btn btn-success text-center submeet_btn">FEEDBACK
									REPORTS</a>
							</div>
						</div>
						<div class="col-lg-2 col-md-2"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--END PAGE WRAPPER-->
<script type="text/javascript">
	$(document).ready(function() {
		// submit
		$("#enter-trip-id").click(function() {
			var tripID = $("#tripId").val();
			if (tripID === '') {
				$("#tripIDErrorMessage").html("");
				$("#tripIDErrorMessage").html("Please Enter Trip ID!");
				$("#tripIDErrorMessage").show();
				$("#carNoErrorMessage").hide();
				return false;
			} else {
				$("#tripIDErrorMessage").hide();
				$("#tripIDUsedErrorMessage").hide();
				var errorMessageTripStatus = "Trip Not Ended!";
				var errorMessageTripID = "Trip ID is incorrect!";
				var tripIDUsedErrorMessage = "The Trip ID is already have feedback!";
				$.ajax({
					type : "POST",
					url : "<%=UrlConstants.CHECK_FEEDBACK_BY_TRIP_ID%>",
					data : "${_csrf.parameterName}=${_csrf.token}&tripID=" + tripID,
					success : function(data) {
						if (data === 'tripStatusIDError') {
							$("#tripIDErrorMessage").html("");
							$("#tripIDErrorMessage").html(errorMessageTripStatus);
							$("#tripIDErrorMessage").show();
							return false;
						} else if (data === 'feedback') {
							$("#tripIDErrorMessage").html("");
							$("#tripIDErrorMessage").html(tripIDUsedErrorMessage);
							$("#tripIDErrorMessage").show();
							return false;
						} else if (data === 'tripIDError') {
							$("#tripIDErrorMessage").html("");
							$("#tripIDErrorMessage").html(errorMessageTripID);
							$("#tripIDErrorMessage").show();
							return false;
						} else {
							window.location.href = "<%=UrlConstants.FEEDBACK%>?tripID="+ tripID;
						}
					},
					error : function(e) {
						$("#tripIDErrorMessage").html("");
						$("#tripIDErrorMessage").html(errorMessageTripID);
						$("#tripIDErrorMessage").show();
						return false;
					}
				});
			}
		});
	});
</script>