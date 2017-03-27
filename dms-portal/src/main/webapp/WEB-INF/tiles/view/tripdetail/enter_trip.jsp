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
					<div class="caption">ENTER TRIP OR CAR NO.</div>
				</div>
				<div class="portlet-body">
					<div class="row">
						<div class="form-group col-lg-5 col-md-5">
							<label class="col-lg-5 col-md-5 control-label">TRIP
								ID</label>
							<div class="col-lg-7 col-md-7">
								<input type="text"
									class="form-control form-cascade-control input-small"
									name="tripId" id="tripId" />
								<div class="pull-left" id="tripIDErrorMessage"
									style="display: none; color: red;"></div>
								<br />
							</div>
						</div>
						<div class="form-group col-lg-2 col-md-2 text-center">
							<strong style="font-size: 30px;">OR</strong>
						</div>
						<div class="form-group col-lg-5  col-md-5">
							<label class="col-lg-5 col-md-5 control-label">CAR
								NUMBER</label>
							<div class="col-lg-7 col-md-7">
								<input type="text"
									class="form-control form-cascade-control input-small"
									name="carNo" id="carNo">
								<div class="pull-left" id="carNoErrorMessage"
									style="display: none; color: red;"></div>
							</div>
						</div>
						<input type="hidden" id="searchType" value="trip" />
						<div class="form-group col-lg-12 col-md-12 text-center"
							id="tripIDUsedErrorMessage"
							style="display: none; color: red;"></div>
						<div class="form-group">
							<div class="col-lg-12 col-md-12 text-center">
								<input type="button" value="SUBMIT"
									class="btn btn-success submeet_btn" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--END BEGIN CONTENT -->
<script type="text/javascript">
	$(document).ready(function(){
		// if trip id change
		$("#tripId").change(function(){
			$("#tripIDErrorMessage").hide();
			$("#tripIDUsedErrorMessage").hide();
			$("#searchType").val("trip");
			$("#carNo").val("");
		});
		$("#carNo").change(function(){
			$("#carNoErrorMessage").hide();
			$("#tripIDUsedErrorMessage").hide();
			$("#searchType").val("carno");
			$("#tripId").val("");
		});

		// submit
		$(".submeet_btn").click(function(){
			var tripId = $("#tripId").val();
			var carNo = $("#carNo").val();
			var searchType = $("#searchType").val();
			var errorMessageValidFormatCarNo = "Car No. not in valid format!";
			if(searchType === 'carno'){
				var carNoVal = /^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{4}$/;
		        if (!carNoVal.test(carNo)) {
		        	$("#carNoErrorMessage").html("");
					$("#carNoErrorMessage").html(errorMessageValidFormatCarNo);
					$("#carNoErrorMessage").show();
		            return false;
		        }
			}
			if(searchType === 'trip' && tripId === '') {
				$("#tripIDErrorMessage").html("");
				$("#tripIDErrorMessage").html("Please Enter Trip ID!");
				$("#tripIDErrorMessage").show();
				$("#carNoErrorMessage").hide();
				return false;
			} else if(searchType === 'carno' && carNo === ''){
				$("#tripIDErrorMessage").hide();
				$("#carNoErrorMessage").html("");
				$("#carNoErrorMessage").html("Please Enter Car No!");
				$("#carNoErrorMessage").show();
				return false;
			} else {
				$("#tripIDErrorMessage").hide();
				$("#carNoErrorMessage").hide();
				$("#tripIDUsedErrorMessage").hide();
				var errorMessageCarNo = "Car No is Incorrect!";
				var errorMessageTripId = "Trip ID is incorrect!";
				$.ajax({
					type : "POST",
					url : "<%=UrlConstants.GET_TRIP_BY_ID_IN_DETAIL%>",
					data: "${_csrf.parameterName}=${_csrf.token}&tripID=" + tripId + "&carNo=" + carNo + "&searchType=" + searchType,
					success : function(data) {
						if(data === 'tripError') {
							$("#tripIDErrorMessage").html("");
							$("#tripIDErrorMessage").html(errorMessageTripId);
							$("#tripIDErrorMessage").show();
							return false;
						}
						if(data === 'carNoError') {
							$("#carNoErrorMessage").html("");
							$("#carNoErrorMessage").html(errorMessageCarNo);
							$("#carNoErrorMessage").show();
							return false;
						}
						if(data == 'trip'){
							window.location.href = "<%=UrlConstants.GET_TRIP_IN_DETAIL%>?tripID="+tripId;
						} else if(data == 'carno'){
							window.location.href = "<%=UrlConstants.GET_TRIPS_DETAIL_CLIENTLIST%>?carNo="+carNo;
						}
					},
					error : function(e) {
						if(searchType === 'trip') {
							$("#tripIDErrorMessage").html("");
							$("#tripIDErrorMessage").html(errorMessageTripId);
							$("#tripIDErrorMessage").show();
						} else {
							$("#carNoErrorMessage").html("");
							$("#carNoErrorMessage").html(errorMessageCarNo);
							$("#carNoErrorMessage").show();
						}
					}
				});
			}
		});
	});
</script>