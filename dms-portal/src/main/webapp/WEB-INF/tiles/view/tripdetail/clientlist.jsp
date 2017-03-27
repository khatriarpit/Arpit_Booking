<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<!--BEGIN CONTENT-->
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
				<div class="portlet-header">
					<div class="caption">Trip List By Car No.</div>
				</div>
				<div class="portlet-body">
					<div class="ro">
					<div class="col-mol-md-offset-2">
						<table class="table table-striped table-bordered" id="clientList">
							<thead>
						        <tr>
						            <th><strong>Sr. No.</strong></th>
									<th><strong>Trip ID</strong></th>
									<th><strong>Car Number</strong></th>
									<th><strong>Car Type</strong></th>
									<th><strong>Car Name</strong></th>
									<th><strong>Driver Name</strong></th>
									<th><strong>Driver Contact Number</strong></th>
						        </tr>
						    </thead>
						    <tbody>
						    	<c:forEach items="${clientDetailList}" var="clientList" varStatus="loop">
							    	<tr class="pointer" data-raw-id="${clientList.tripId}">
							    		<td>${loop.index + 1}</td>
							    		<td>${clientList.tripId}</td>
							    		<td>${not empty clientList.car.carNo ? clientList.car.carNo : ''}</td>
							    		<td>${not empty clientList.car.carType ? clientList.car.carType.carType : ''}</td>
							    		<td>${not empty clientList.car.carType ? clientList.car.carName.carName : ''}</td>
							    		<td>${not empty clientList.driver ? clientList.driver.fullName : ''}</td>
							    		<td>${not empty clientList.driver ? clientList.driver.contactNo : ''}</td>
							    	</tr>
						    	</c:forEach>
						    </tbody>
						</table>
					</div>
				</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--END CONTENT-->
<script type="text/javascript">
	$(document).ready(function() {
		$("#clientList").DataTable();
		var table = $("#clientList").DataTable();
        $("#clientList").on('click', 'tr', function() {
               var data = table.row(this).data();
               var tripID = $(this).data("rawId");
   			window.location.href= "<%=UrlConstants.GET_TRIP_IN_DETAIL%>?tripID="+tripID;
        });
	});
</script>
