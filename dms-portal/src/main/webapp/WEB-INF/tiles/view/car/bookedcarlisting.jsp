<%@page import="com.emxcel.dms.core.business.utils.CommonUtil" %>
<%@page import="com.emxcel.dms.portal.constants.UrlConstants" %>
<%@page import="com.emxcel.dms.core.business.constants.Constants" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<script type="text/javascript">
    $(document).ready(function () {
        var table = $("#clientModelList").DataTable();
        $("#clientModelList").on('click', 'td', function () {
            if (!($(this).hasClass("removeRowTd"))) {
                var data = table.row(this).data();
                var values = $(this).data("rawId");
                var arrayOf = values.split(',');
                if (arrayOf[4] === "1") {
                    dynamicForm(arrayOf[0], arrayOf[1], arrayOf[2], arrayOf[3])
                }
            }
        });

        $('#canceledRequest').click(function () {
            var carTypeId = $(this).val();
            if (carTypeId != '0') {
                $.ajax({
                    type: 'POST',
                    url: '<%=UrlConstants.GET_CARNAME%>',
                    data: '${_csrf.parameterName}=${_csrf.token}&carTypeId=' + carTypeId,
                    success: function (data) {
                        $('#carNameId').html('<option value="">Select Car Name</option>');
                        for (var i = 0, len = data.length; i < len; ++i) {
                            var carName = data[i];
                            $('#carNameId').append("<option value=\"" + carName.id + "\">" + carName.carName + "</option>");
                        }
                    }
                });
            } else {
                $('#carNameId').html('<option value="">Select Car Type First</option>');
            }
        });

    });

    $("body").delegate("#changeRequest", "click", function(){
        var clientId=$(this).data("rowId");
        $('#clientId').val(clientId);
	        $("#submit-changeRequest").attr("action", "<%=UrlConstants.CHANGE_REQUEST%>");
        	$("#submit-changeRequest").submit();

    });
</script>
<!--END PAGE HEADER & BREADCRUMB-->
<!--BEGIN CONTENT-->
<div class="page-content">
    <div class="row">
        <div class="col-lg-12">
            <div class="portlet">
                <%--<c:if test="${not empty message}">
                    <div class="form-group">
                        <label for="inputAddress" class="col-md-2 control-label"></label>
                        <div class="col-md-12">
                            <div class="alert col-md-6 alert-success-message" role="alert">${message}</div>
                        </div>
                    </div>
                </c:if>--%>
                <h2>List Of Booked Car</h2>
                <hr>
                <div style="display:none;" id="dynamicForm"></div>
				<table id="clientModelList"
					class="table table-striped table-bordered table-hover" border="1">
					<thead>
						<tr>
						    <th>Serial No</th>
							<th>Trip Id</th>
							<th>Guest Name</th>
							<th>Guest Contact No</th>
							<th>Trip Start Date</th>
							<th>Trip End Date</th>
							<th>Driver Name</th>
							<th>Car No</th>
							<th>Status</th>
							<th class="text-center">Action Button</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${clientModelList}" var="clientModel"
							varStatus="LoopCounter">
							<tr class="pointer ${clientModel.statusID eq 5 ? 'client-model-color-by-status' : ''}">

							    <td>${LoopCounter.count}</td>
								<td data-raw-id="${clientModel.id},${clientModel.car.id},${pickUpDateTime},${dropDateTime},${clientModel.statusID}">${clientModel.tripId}</td>
								<td data-raw-id="${clientModel.id},${clientModel.car.id},${pickUpDateTime},${dropDateTime},${clientModel.statusID}">${clientModel.guest.personName}</td>
								<td data-raw-id="${clientModel.id},${clientModel.car.id},${pickUpDateTime},${dropDateTime},${clientModel.statusID}">${clientModel.guest.contactNo }</td>
								<td data-raw-id="${clientModel.id},${clientModel.car.id},${pickUpDateTime},${dropDateTime},${clientModel.statusID}">
									<fmt:formatDate value='${clientModel.pickUpDateTime}' var='pickUpDate' type='date' pattern='<%=Constants.PORTAL_DATE_FORMAT%>' />
									<c:out value="${pickUpDate}" />
								</td>
								<td data-raw-id="${clientModel.id},${clientModel.car.id},${pickUpDateTime},${dropDateTime},${clientModel.statusID}">
									<fmt:formatDate value='${clientModel.dropDateTime}' var='dropDate' type='date' pattern='<%=Constants.PORTAL_DATE_FORMAT%>' />
									<c:out value="${dropDate}" />
								</td>
								<td data-raw-id="${clientModel.id},${clientModel.car.id},${pickUpDateTime},${dropDateTime},${clientModel.statusID}">${clientModel.driver.fullName}</td>
								<td data-raw-id="${clientModel.id},${clientModel.car.id},${pickUpDateTime},${dropDateTime},${clientModel.statusID}">${clientModel.car.carNo}</td>
								<td data-raw-id="${clientModel.id},${clientModel.car.id},${pickUpDateTime},${dropDateTime},${clientModel.statusID}">
									<c:set var="statusID" value="${clientModel.statusID}"/>
									<%
										int status =  (int)pageContext.getAttribute("statusID");   //No exception.
										out.println(CommonUtil.getStatusByStatusID(status));
									%>
								</td>
								<td width="${clientModel.statusID eq 2 || clientModel.statusID eq 3 ? '8%' : clientModel.statusID eq 4 ? '4%' : '16%'}" align="center" class="removeRowTd tanent-action">
									<c:if test="${clientModel.statusID eq 1 || clientModel.statusID eq 7}">
										<div class="pull-left">

											<fmt:formatDate value='${clientModel.pickUpDateTime}' var='pickUpDateTime' type='date' pattern='<%=Constants.PORTAL_DATE_FORMAT%>' />
											<fmt:formatDate value='${clientModel.dropDateTime}' var='dropDateTime' type='date' pattern='<%=Constants.PORTAL_DATE_FORMAT%>' />
											<a title="Edit"
												class="btn pointer glyphicon glyphicon-pencil"
												href="javascript:dynamicForm('${clientModel.id}','${clientModel.car.id}','${pickUpDateTime}','${dropDateTime}');">
											</a>
										</div>
									</c:if>

									<c:if test="${clientModel.invoiceMode ne 'Auto'}">
									<c:if test="${clientModel.statusID eq 1  || clientModel.statusID eq 7 || clientModel.statusID eq 2 || clientModel.statusID eq 3}">
									<div class="pull-left">
										<a class="btn pointer fa fa-print" title="Invoice"
											href="<%=UrlConstants.MANUAL_INVOICE%>?tripId=${clientModel.tripId}" >
										</a>
									</div>
									</c:if>
									</c:if>
									<c:if test="${clientModel.statusID eq 3 && clientModel.invoiceMode eq 'Manual' && clientModel.manualInvStatus eq 1}">
									 <div class="pull-left">
										<a class="btn pointer fa fa-print" title="Show PDF"
											href="<%=UrlConstants.SHOW_MANUAL_INVOICE_PDF%>?tripId=${clientModel.tripId}">
										</a>
									</div>
									<c:if test="${clientModel.statusID eq 3 && clientModel.invoiceMode eq 'Manual' && clientModel.manualInvStatus eq 1}">
									<div class="pull-left">
										<a class="btn pointer fa fa-print" title="Send PDF"
											href="<%=UrlConstants.SEND_MANUAL_INVOICE_PDF%>">
										</a>
									</div>
									</c:if>
									</c:if>
									<c:if test="${clientModel.statusID eq 1 || clientModel.statusID eq 7 || clientModel.statusID eq 4 || clientModel.statusID eq 5}">
										<div class="pull-left">
											<a class="btn pointer fa fa-times" title="Cancel Trip"
												href="<%= UrlConstants.CANCELED_TRIP%>?tripId=${clientModel.tripId}"
												onclick="return confirm('Are you sure want to Cancel this Trip?')">
											</a>
										</div>
									</c:if>
									<c:if test="${clientModel.statusID eq 2 || clientModel.statusID eq 3}">
										<div class="pull-left">
											<a title="Location Map" class="btn pointer fa fa-map-marker"
												href="<%=UrlConstants.CAR_LOCATION%>?tripId=${clientModel.tripId}">
											</a>
										</div>
									</c:if>
									<c:if test="${clientModel.statusID eq 1  || clientModel.statusID eq 7}">
										<form:form method="POST"  id="submit-changeRequest" >

											<input type="hidden" name="clientId"  id="clientId"/>
											<input type="hidden" name="changedBy"  id="changedBy"/>
									<div class="pull-left">
										<button type="button" id="changeRequest"  data-row-id="${clientModel.id}" class="btn pointer fa fa-exchange" title="ChangeRequest">
										</button>
									</div>
										</form:form>
									</c:if>

									<c:if test="${clientModel.statusID eq 5 }">
									<div class="pull-left">
										<a title="Approve Or Reject" class="btn pointer fa fa-exchange"
										   href="<%=UrlConstants.DRIVER_REQUEST_OPERATION%>?clientId=${clientModel.id}">
										</a>
									</div>
									</c:if>
								</td>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
    function dynamicForm(clientID, carId, pickUpDateTime, dropDateTime) {
        var tag = '';
        tag += '<form style="display:none;" method="POST" class="form-horizontal" action="<%=UrlConstants.CLIENT_PAGE%>">' +
            '<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />' +
            '<input type="hidden" id="clientId" name="clientId" value="' + clientID + '" />' +
            '<input type="hidden" id="carId" name="carId" value="' + carId + '" />' +
            '<input type="hidden" id="pickUpDateTime1" name="pickUpDateTime1" value="' + pickUpDateTime + '" />' +
            '<input type="hidden" id="dropDateTime1" name="dropDateTime1" value="' + dropDateTime + '" />' +
            '<input type="submit" id="submit" />' +
            '</form>';
        $("#dynamicForm").html(tag);
        $('#submit').trigger("click");
    }
</script>