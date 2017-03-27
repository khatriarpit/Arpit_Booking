<%@page import="com.emxcel.dms.core.business.constants.Constants"%>
<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#CustomerCancelList").DataTable();
		if("${message}" != "") {
			$(".alert-success-message").fadeIn("fast").delay("3000").fadeOut("fast");
		}
	});
</script>
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
                <c:if test="${not empty message}">
                    <div class="form-group">
                        <label for="inputAddress" class="col-md-2 control-label"></label>
                        <div class="col-md-12">
                            <div class="alert col-md-6 alert-success-message" role="alert">${message}</div>
                        </div>
                    </div>
                </c:if>
				<div class="portlet-body">
					<div class="form-group">
						<h2>Customer Cancel Request List</h2>
						<table class="table table-striped table-bordered table-hover"
							border="1" id="CustomerCancelList">
							<thead>
						<tr>
						    <th>Serial No</th>
							<th>Trip Id</th>
							<th>Guest Name</th>
							<th>Contact No</th>
							<th>Pickup Place</th>
							<th>Driver Name</th>
							<th>Driver No</th>
							<th>Car No</th>
							<th>PickUp Date</th>
							<th>Drop Date</th>
							<th class="text-center">Action Button</th>
						</tr>
					</thead>
							<tbody>
						<c:forEach items="${CustomerCancelList}" var="clientModel"
							varStatus="LoopCounter">
							<tr class="pointer">
							    <td>${LoopCounter.count}</td>
								<td data-raw-id="${clientModel.id},${clientModel.car.id},${clientModel.pickUpDateTime},${clientModel.dropDateTime},${clientModel.statusID}">${clientModel.tripId}</td>
								<td data-raw-id="${clientModel.id},${clientModel.car.id},${clientModel.pickUpDateTime},${clientModel.dropDateTime},${clientModel.statusID}">${clientModel.guest.personName}</td>
								<td data-raw-id="${clientModel.id},${clientModel.car.id},${clientModel.pickUpDateTime},${clientModel.dropDateTime},${clientModel.statusID}">${clientModel.guest.contactNo }</td>
								<td data-raw-id="${clientModel.id},${clientModel.car.id},${clientModel.pickUpDateTime},${clientModel.dropDateTime},${clientModel.statusID}">${clientModel.pickUpLocation}</td>
								<td data-raw-id="${clientModel.id},${clientModel.car.id},${clientModel.pickUpDateTime},${clientModel.dropDateTime},${clientModel.statusID}">${clientModel.driver.fullName}</td>
								<td data-raw-id="${clientModel.id},${clientModel.car.id},${clientModel.pickUpDateTime},${clientModel.dropDateTime},${clientModel.statusID}">${clientModel.driver.contactNo}</td>
								<td data-raw-id="${clientModel.id},${clientModel.car.id},${clientModel.pickUpDateTime},${clientModel.dropDateTime},${clientModel.statusID}">${clientModel.car.carNo}</td>
								<td data-raw-id="${clientModel.id},${clientModel.car.id},${clientModel.pickUpDateTime},${clientModel.dropDateTime},${clientModel.statusID}"><fmt:formatDate value='${clientModel.pickUpDateTime}' var='pickUpDate' type='date' pattern='<%=Constants.PORTAL_DATE_FORMAT%>' /><c:out value="${pickUpDate}" /></td>
								<td data-raw-id="${clientModel.id},${clientModel.car.id},${clientModel.pickUpDateTime},${clientModel.dropDateTime},${clientModel.statusID}"><fmt:formatDate value='${clientModel.dropDateTime}' var='dropDate' type='date' pattern='<%=Constants.PORTAL_DATE_FORMAT%>' /><c:out value="${dropDate}" /></td>
								<td width="${clientModel.statusID eq 2 || clientModel.statusID eq 3 ? '8%' : clientModel.statusID eq 4 ? '4%' : '16%'}" align="center" class="removeRowTd tanent-action">
										<div class="pull-left">
											<a title="Approve" class="btn btn-success"
												href="<%=UrlConstants.CUSTOMER_CANCEL_APPROVE%>?tripId=${clientModel.tripId}">
											Approve</a>
										</div>
									<div class="pull-left">
										<a title="Reject" class="btn btn-danger"
										   href="<%=UrlConstants.CUSTOMER_CANCEL_REJECT%>?tripId=${clientModel.tripId}&pickupdate=${clientModel.pickUpDateTime}">
										Reject</a>
									</div>
								</td>
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