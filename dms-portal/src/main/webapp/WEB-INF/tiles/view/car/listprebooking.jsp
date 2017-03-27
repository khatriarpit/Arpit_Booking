<%@page import="com.emxcel.dms.core.business.constants.Constants"%>
<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page import="com.emxcel.dms.core.business.constants.Constants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#preBookingList").DataTable();
	});
    $("body").delegate("#confirmPrebookingTrip", "click", function(){
        var _bookId=$(this).data("rowId");
        var preBookingId=$("#preBookingId-"+_bookId).val();
        var pickupDt=$("#pickDt-"+_bookId).val();
        var dropDt=$("#dropDt-"+_bookId).val();
            $('#prebookingid').val(preBookingId);
        $('#pickUpDateTime').val(pickupDt);
        $('#dropDateTime').val(dropDt);
            $("#submit-confirm-trip").attr("action", "<%=UrlConstants.BOOK_CAR_LIST%>");
            $("#submit-confirm-trip").submit();

    });
</script>
<div class="page-content">
	<div class="row">
		<div class="col-lg-12">
			<div class="portlet">
   				<div class="portlet-body">
					<div class="form-group">
						<h2>Pre Booking List</h2>
						<table class="table table-striped table-bordered table-hover"
							border="1" id="preBookingList">
							<thead>
								<tr>
									<th>Serial No</th>
									<th>Request Id</th>
									<th>Guest Name</th>
									<th>Guest Contact</th>
									<th>Trip Start Date</th>
									<th>Trip End Date</th>
									<th>Is mobile request or web?</th>
									<!-- <th>Car Type</th>
									<th>PickUp Location</th>
									<th>Drop Location</th> -->
									<th class="text-center">Edit / Delete</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${preBookingList}" var="prebooking"
									varStatus="LoopCounter">
									<tr>
										<input type="hidden" name="preBookingId"  id="preBookingId-${LoopCounter.count}" value="${prebooking.id}"/>
										<td data-raw-id="${prebooking.id}"><c:out value="${LoopCounter.count }" /></td>
										<td data-raw-id="${prebooking.id}"><c:out value="${prebooking.requestedBookingId}" /></td>
										<td data-raw-id="${prebooking.id}"><c:out value="${prebooking.guest.personName}" /></td>
										<td data-raw-id="${prebooking.id}"><c:out value="${prebooking.guest.contactNo}" /></td>
										<td data-raw-id="${prebooking.id}"><fmt:formatDate value='${prebooking.pickUpDateTime}' var='pickUpDate' type='date' pattern='<%=Constants.PORTAL_DATE_FORMAT%>' /><c:out value="${pickUpDate}" /></td>
										<td data-raw-id="${prebooking.id}"><fmt:formatDate value='${prebooking.dropDateTime}' var='dropDate' type='date' pattern='<%=Constants.PORTAL_DATE_FORMAT%>' /><c:out value="${dropDate}" /></td>
										<td data-raw-id="${prebooking.id}">
											<c:if test="${prebooking.fromMobile eq false}">
										Portal
											</c:if>
											<c:if test="${prebooking.fromMobile eq true}">
												Mobile
											</c:if>
										</td>
										<%-- <td data-raw-id="${prebooking.id}"><c:out value="${prebooking.carType.carType}" /></td>
										<td data-raw-id="${prebooking.id}"><c:out value="${prebooking.pickUpLocation}" /></td>
										<td data-raw-id="${prebooking.id}"><c:out value="${prebooking.drop_location}" /></td> --%>
										<td align="center">
										    <a 
											    href="<%=UrlConstants.PRE_BOOKING%>?prebookingId=${prebooking.id}"
											    title="Edit" class="glyphicon glyphicon-pencil btn fa fa-refresh" ${prebooking.statusID ne '0' ? 'disabled=' : ''}>
											</a>
											&nbsp;|&nbsp;
											   <a 
											    class="glyphicon glyphicon-trash btn fa fa-refresh"
											    title="Delete"
											    href="<%=UrlConstants.DELETE_PRE_BOOKING%>?prebookingId=${prebooking.id}"
											    onclick="return confirm('Are you sure want to Delete this Booking?')" ${prebooking.statusID ne '0' ? 'disabled': ''}>
											</a>
									    <form:form method="POST"  id="submit-confirm-trip" >
										<input type="hidden" name="prebookingid"  id="prebookingid"/>

										<fmt:formatDate value='${prebooking.pickUpDateTime}'  var='pickUpDate' type='date' pattern='<%=Constants.PORTAL_DATE_FORMAT%>' />
										<fmt:formatDate value='${prebooking.dropDateTime}' var='dropDate' type='date' pattern='<%=Constants.PORTAL_DATE_FORMAT%>' />
										<input type="hidden" name="pickDt" id="pickDt-${LoopCounter.count}" value="${pickUpDate}"/>
										<input type="hidden" name="dropDt" id="dropDt-${LoopCounter.count}" value="${dropDate}"/>

										<input type="hidden" name="pickUpDateTime" id="pickUpDateTime" />
										<input type="hidden" name="dropDateTime" id="dropDateTime"/>

											<div class="pull-left">
												<button type="button" ${prebooking.statusID ne '0' ? 'disabled': ''} id="confirmPrebookingTrip"  data-row-id="${LoopCounter.count}" class="btn pointer fa fa-chevron-circle-right" title="Confirm Trip">

												</button>
											</div>
									</form:form>
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