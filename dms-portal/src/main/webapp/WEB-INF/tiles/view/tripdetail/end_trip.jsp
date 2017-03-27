<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@page import="com.emxcel.dms.core.business.constants.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<div class="portlet">
				<div class="portlet-header">
					<div class="caption">TRIP ID - ${tripDetail.tripId}</div>
				</div>
				<div class="portlet-body">
					<div class="row">
						<div class="col-mol-md-offset-2">
							<form class="form-horizontal cascde-forms" method="GET"
								action="endtrip.htm" name="clientDetailForm"
								id="clientDetailForm">
								<input type="hidden" name="tripID" id="tripID" value="${tripDetail.tripId}" />
								<c:if test="${tripDetail.statusID eq 2}">
									<input type="hidden" name="action" id="action" value="end_trip" />
								</c:if>
								<c:if test="${tripDetail.statusID eq 1}">
									<input type="hidden" name="action" id="action" value="start_trip" />
								</c:if>
								<div class="form-group col-lg-6">
									<label class="col-lg-5 col-md-5 col-sm-6 control-label">GUEST
										NAME</label>
									<div class="col-lg-7 col-md-7 col-sm-6">
										<input type="text"
											class="form-control form-cascade-control "
											id="clientName" name="clientName"
											value="${tripDetail.guest.personName}" readonly="readonly" />
									</div>
								</div>
								<div class="form-group col-lg-6">
									<label class="col-lg-5 col-md-5 col-sm-6 control-label">GUEST
										NO.</label>
									<div class="col-lg-7 col-md-7 col-sm-6">
										<input type="text"
											class="form-control form-cascade-control "
											id="contactNo" name="contactNo"
											value="${tripDetail.guest.contactNo}"
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
											value="${tripDetail.car.carNo}" readonly="readonly" />
									</div>
								</div>
								<div class="form-group col-lg-6">
									<label class="col-lg-5 col-md-5 col-sm-6 control-label">CAR
										TYPE</label>
									<div class="col-lg-7 col-md-7 col-sm-6">
										<input type="text"
											class="form-control form-cascade-control "
											name="carName" id="carName"
											value="${carType.carType}"
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
											value="${carName.carName}"
											readonly="readonly" />
									</div>
								</div>
								<div class="form-group col-lg-6">
									<label class="col-lg-5 col-md-5 col-sm-6 control-label">BOOKING
										DATE</label>
									<div class="col-lg-7 col-md-7 col-sm-6">
										<fmt:formatDate value='${tripDetail.createdDate}'
											var='bookingFormat' type='date' pattern="<%=Constants.PORTAL_DATE_FORMAT %>" />
										<input type="text"
											class="form-control form-cascade-control "
											name="bookingDdate" id="bookingDdate"
											value="${bookingFormat}" readonly="readonly" />
									</div>
								</div>
								<div class="form-group col-lg-6">
									<label class="col-lg-5 col-md-5 col-sm-6 control-label">PICKUP
										DATE</label>
									<div class="col-lg-7 col-md-7 col-sm-6">
										<fmt:formatDate value='${tripDetail.pickUpDateTime}'
											var='startFormat' type='date' pattern="<%=Constants.PORTAL_DATE_FORMAT %>" />
										<input type="text"
											class="form-control form-cascade-control "
											id="pickUpDate" name="pickUpDate" value="${startFormat}"
											readonly="readonly" />
									</div>
								</div>
								<c:if test="${tripDetail.statusID eq 3}">
									<div class="form-group col-lg-6">
										<label class="col-lg-5 col-md-5 col-sm-6 control-label">DROP
											DATE</label>
										<div class="col-lg-7 col-md-7 col-sm-6">
											<fmt:formatDate value='${tripDetail.dropDateTime}'
												var='endFormat' type='date' pattern="<%=Constants.PORTAL_DATE_FORMAT %>" />
											<input type="text"
												class="form-control form-cascade-control "
												id="dropDdate" name="dropDdate" value="${endFormat}"
												readonly="readonly" />
										</div>
									</div>
								</c:if>
								<div class="form-group col-lg-6">
									<label class="col-lg-5 col-md-5 col-sm-6 control-label">DRIVER
										NAME</label>
									<div class="col-lg-7 col-md-7 col-sm-6">
										<input type="text"
											class="form-control form-cascade-control "
											name="dirName" id="dirName"
											value="${tripDetail.driver.fullName}"
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
											value="${tripDetail.driver.contactNo}"
											readonly="readonly" />
									</div>
								</div>
								<div class="form-group col-lg-6">
									<label class="col-lg-5 col-md-5 col-sm-6 control-label">TRIP START KM</label>
									<div class="col-lg-7 col-md-7 col-sm-6">
										<input type="text" class="form-control form-cascade-control" name="tripStartKm" id="tripStartKm"
											maxlength="10" required value="${tripDetails.startKm}" ${tripDetail.statusID != 1 ? 'readonly' : ''} onkeypress="return onlyNos(event, this)" />
									</div>
								</div>
								<c:if test="${tripDetail.statusID != 1}">
									<div class="form-group col-lg-6">
										<label class="col-lg-5 col-md-5 col-sm-6 control-label">TRIP END KM</label>
										<div class="col-lg-7 col-md-7 col-sm-6">
											<input type="text" class="form-control form-cascade-control" name="tripEndKm" id="tripEndKm"
												maxlength="10" required value="${tripDetails.endKm}" ${tripDetail.statusID != 2 ? 'readonly' : ''} onkeypress="return onlyNos(event, this)"/>
										</div>
									</div>
									<div class="form-group col-lg-6">
										<label class="col-lg-5 col-md-5 col-sm-6 control-label">TOTAL TOLL TAX</label>
										<div class="col-lg-7 col-md-7 col-sm-6">
											<input type="text" class="form-control form-cascade-control" name="tripTolltax" id="tripTolltax"
												maxlength="4" required value="${tripDetails.tolltax}" ${tripDetail.statusID != 2 ? 'readonly' : ''} onkeypress="return onlyNos(event, this)"/>
										</div>
									</div>
								</c:if>
								<div class="form-group">
									<div class="col-lg-12 col-md-12 text-center"
										style="padding-top: 20px;">
										<a href="enter_trip.htm" id="send-ok-button"
											class="btn btn-success">BACK</a>
										<c:if test="${tripDetail.statusID eq 1 || tripDetail.statusID eq 2}">
											<input type="submit" name="submit"
												value="${tripDetail.statusID eq 1 ? 'START TRIP' : 'END TRIP'}"
												class="btn btn-success text-center submeet_btn" />
										</c:if>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		$("#tripStartDate, #tripEndDate").datepicker({
			dateFormat: "dd/mm/yy"
		});
		$("#send-ok-button").click(function() {
			location.href = "<%=UrlConstants.INDEX%>";
		});
	});
	
	function onlyNos(e, t) {
	    try {
	        if (window.event) {
	            var charCode = window.event.keyCode;
	        }
	        else if (e) {
	            var charCode = e.which;
	        }
	        else { return true; }
	        if (charCode > 31 && (charCode < 48 || charCode > 57)) {
	            return false;
	        }
	        return true;
	    } catch (err) {
	        alert(err.Description);
	    }
	}
</script>