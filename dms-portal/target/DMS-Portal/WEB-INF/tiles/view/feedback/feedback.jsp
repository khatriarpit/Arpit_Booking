<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@page import="com.emxcel.dms.core.business.constants.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- External CSS -->
<link href="assets/css/star-rating.min.css" rel="stylesheet">
<!-- External script -->
<script src="assets/scripts/star-rating.min.js"></script>
<!-- Jquery and css include file end here -->
<style type="text/css">
.rating-md {
	font-size: 2.13em;
}

.clear-rating {
	display: none !important;
}
</style>
<div>
	<!--BEGIN CONTENT-->
	<div class="page-content">
		<div class="row">
			<div class="col-lg-12">
				<div class="portlet">
					<div class="portlet-header">
						<div class="caption">${feedbackDetail.id != null ? 'Update' : 'Add'}
							Feedback</div>
					</div>
					<div class="portlet-body">
						<form:form class="form-horizontal cascde-forms" method="POST"
							action="<%=UrlConstants.SAVE_FEEDBACK %>" modelAttribute="feedback">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<form:input type="hidden" path="tripID"
								value="${feedbackDetail.clientModel.tripId}" />
							<form:input type="hidden" path="id" value="${feedbackDetail.id}" />
							<div class="form-group col-lg-6">
								<label class="col-lg-5 col-md-5 col-sm-6 control-label">GUEST
									NAME</label>
								<div class="col-lg-7 col-md-7 col-sm-6">
									<input type="text"
										class="form-control form-cascade-control "
										name="clientName" id="clientName"
										value="${feedbackDetail.clientModel.guest.personName}"
										readonly="readonly" />
								</div>
							</div>
							<div class="form-group col-lg-6">
								<label class="col-lg-5 col-md-5 col-sm-6 control-label">GUEST
									NO.</label>
								<div class="col-lg-7 col-md-7 col-sm-6">
									<input type="text"
										class="form-control form-cascade-control "
										name="contactNo" id="contactNo"
										value="${feedbackDetail.clientModel.guest.contactNo}"
										readonly="readonly" />
								</div>
							</div>
							<div class="form-group col-lg-6">
								<label class="col-lg-5 col-md-5 col-sm-6 control-label">CAR
									NUMBER</label>
								<div class="col-lg-7 col-md-7 col-sm-6">
									<input type="text"
										class="form-control form-cascade-control "
										name="carType" id="carType"
										value="${feedbackDetail.clientModel.car.carNo}"
										readonly="readonly" />
								</div>
							</div>
							<div class="form-group col-lg-6">
								<label class="col-lg-5 col-md-5 col-sm-6 control-label">CAR
									TYPE</label>
								<div class="col-lg-7 col-md-7 col-sm-6">
									<input type="text"
										class="form-control form-cascade-control "
										name="carName" id="carName"
										value="${feedbackDetail.clientModel.car.carType.carType}"
										readonly="readonly" />
								</div>
							</div>
							<div class="form-group col-lg-6">
								<label class="col-lg-5 col-md-5 col-sm-6 control-label">CAR
									NAME</label>
								<div class="col-lg-7 col-md-7 col-sm-6">
									<input type="text"
										class="form-control form-cascade-control "
										name="carName" id="carName"
										value="${feedbackDetail.clientModel.car.carName.carName}"
										readonly="readonly" />
								</div>
							</div>
							<div class="form-group col-lg-6">
								<label class="col-lg-5 col-md-5 col-sm-6 control-label">BOOKING
									DATE</label>
								<div class="col-lg-7 col-md-7 col-sm-6">
									<fmt:formatDate value='${feedbackDetail.clientModel.createdDate}'
										var='bookingFormat' type='date' pattern="<%=Constants.PORTAL_DATE_FORMAT %>" />
									<input type="text"
										class="form-control form-cascade-control "
										id="bookingDdate" name="bookingDdate" value="${bookingFormat}"
										readonly="readonly" />
								</div>
							</div>
							<div class="form-group col-lg-6">
								<label class="col-lg-5 col-md-5 col-sm-6 control-label">START
									DATE</label>
								<div class="col-lg-7 col-md-7 col-sm-6">
									<fmt:formatDate
										value='${feedbackDetail.clientModel.pickUpDateTime}'
										var='startDateFormat' type='date' pattern='<%=Constants.PORTAL_DATE_FORMAT %>' />
									<input type="text"
										class="form-control form-cascade-control "
										name="pickUpDate" id="pickUpDate" value="${startDateFormat}"
										readonly="readonly" />
								</div>
							</div>
							<div class="form-group col-lg-6">
								<label class="col-lg-5 col-md-5 col-sm-6 control-label">END
									DATE</label>
								<div class="col-lg-7 col-md-7 col-sm-6">
									<fmt:formatDate
										value='${feedbackDetail.clientModel.dropDateTime}'
										var='endDateFormat' type='date' pattern='<%=Constants.PORTAL_DATE_FORMAT %>' />
									<input type="text"
										class="form-control form-cascade-control "
										name="pickUpDate" id="pickUpDate" value="${endDateFormat}"
										readonly="readonly" />
								</div>
							</div>
							<div class="form-group col-lg-6">
								<label class="col-lg-5 col-md-5 col-sm-6 control-label">DRIVER
									NAME</label>
								<div class="col-lg-7 col-md-7 col-sm-6">
									<input type="text"
										class="form-control form-cascade-control "
										name="dirName" id="dirName"
										value="${feedbackDetail.clientModel.driver.fullName}"
										readonly="readonly" />
								</div>
							</div>
							<div class="form-group col-lg-6">
								<label class="col-lg-5 col-md-5 col-sm-6 control-label">DRIVER
									NUMBER</label>
								<div class="col-lg-7 col-md-7 col-sm-6">
									<input type="text"
										class="form-control form-cascade-control "
										name="driContact" id="driContact"
										value="${feedbackDetail.clientModel.driver.contactNo}"
										readonly="readonly" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 text-center">
								<h2>Rating our Service</h2>
							</div>
							<div class="form-group col-lg-12 col-md-12">
								<label class="col-lg-3 col-md-3 col-sm-12 control-label"></label>
								<label class="col-lg-3 col-md-3 col-sm-12 control-label">1)
									Driving</label>
								<div class="col-lg-6 col-md-6 col-sm-12">
									<form:input
										readonly="${feedbackDetail.id != null ? 'true' : 'false'}"
										path="drivingRating" value="${feedbackDetail.drivingRating}"
										class="rating-loading" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12">
								<label class="col-lg-3 col-md-3 col-sm-12 control-label"></label>
								<label class="col-lg-3 col-md-3 col-sm-12 control-label">2)
									Driver's Behaviour</label>
								<div class="col-lg-6 col-md-6 col-sm-12">
									<form:input
										readonly="${feedbackDetail.id != null ? 'true' : 'false'}"
										path="driverBehaviourRating"
										value="${feedbackDetail.driverBehaviourRating}"
										class="rating-loading" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12">
								<label class="col-lg-3 col-md-3 col-sm-12 control-label"></label>
								<label class="col-lg-3 col-md-3 col-sm-12 control-label">3)
									Driver's Timings</label>
								<div class="col-lg-6 col-md-6 col-sm-12">
									<form:input
										readonly="${feedbackDetail.id != null ? 'true' : 'false'}"
										path="driverTestingRating"
										value="${feedbackDetail.driverTestingRating}"
										class="rating-loading" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12">
								<label class="col-lg-3 col-md-3 col-sm-12 control-label"></label>
								<label class="col-lg-3 col-md-3 col-sm-12 control-label">4)
									Car Condition</label>
								<div class="col-lg-6 col-md-6 col-sm-12">
									<form:input
										readonly="${feedbackDetail.id != null ? 'true' : 'false'}"
										path="carConditionRating"
										value="${feedbackDetail.carConditionRating}"
										class="rating-loading" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12">
								<label class="col-lg-3 col-md-3 col-sm-3 control-label"></label>
								<label class="col-lg-3 col-md-3 col-sm-3 control-label">5)
									Overall Services</label>
								<div class="col-lg-6 col-md-6 col-sm-12">
									<form:input
										readonly="${feedbackDetail.id != null ? 'true' : 'false'}"
										path="overallServiceRating"
										value="${feedbackDetail.overallServiceRating}"
										class="rating-loading" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12">
								<div class="col-lg-6 col-md-6 col-sm-3">
									<label class="col-lg-5 col-md-5 col-sm-3 control-label">Remark</label>
									<div class="col-lg-7 col-md-7 col-sm-12">
										<form:textarea path="remark" value="${feedbackDetail.remark}"
											readonly="${feedbackDetail.id != null ? 'true' : 'false'}"
											class="form-control form-cascade-control " />
									</div>
								</div>
								<div class="col-lg-6 col-md-6 col-sm-12 text-center"
									style="padding-top: 20px;">
									<c:if test="${feedbackDetail.id == null}">
										<input type="submit" name="submit" value="Submit"
											class="btn btn-success">
									</c:if>
									<a
										href="${feedbackDetail.id == null ? 'enter_trip.htm' : 'enter-feedback.htm'}"
										class="btn btn-danger text-center">Close</a>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--END CONTENT-->
	<!--BEGIN FOOTER-->
	<!--END FOOTER-->
</div>
<!--END PAGE WRAPPER-->
<script type="text/javascript">
	$(document).ready(function() {
		$('#drivingRating').rating({
			min : 0,
			max : 5,
			step : 0.1,
			stars : 5
		});
		$('#driverBehaviourRating').rating({
			min : 0,
			max : 5,
			step : 0.1,
			stars : 5
		});
		$('#driverTestingRating').rating({
			min : 0,
			max : 5,
			step : 0.1,
			stars : 5
		});
		$('#carConditionRating').rating({
			min : 0,
			max : 5,
			step : 0.1,
			stars : 5
		});
		$('#overallServiceRating').rating({
			min : 0,
			max : 5,
			step : 0.1,
			stars : 5
		});
	});
</script>
