<%@page import="com.emxcel.dms.core.business.constants.Constants"%>
<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#CancelList").DataTable();
		if("${message}" != "") {
			$(".alert-success-message").fadeIn("fast").delay("3000").fadeOut("fast");
		}
	});


    $(function(){
    	$("body").delegate(".cancel-form-submit", "click", function(){
            var clientId = $(this).data("rowId");
                  $("#clientId").val(clientId);
            })

        });


        $("body").delegate(".form-submit", "click", function(){
            var clientId = $("#clientId").val();
           var choiceSelection= $('input[name=driverrequest]:radio:checked').val();
           var url;
           if(choiceSelection==='reject'){
             $.ajax({
                               type : "POST",
                               url : "<%=UrlConstants.REJECT_DRIVER_CANCEL_REQUEST%>",
                               data : "${_csrf.parameterName}=${_csrf.token}&clientId=" + clientId ,
                               success : function(data) {
                                   location.href = "<%=UrlConstants.CANCEL_REQUEST_LIST%>";
                               },
                               error : function(e) {
                                   alert("error in Rejection");
                               }
                           });
           }else if(choiceSelection ==='changeLater'){
              $.ajax({
                                type : "POST",
                                url : "<%=UrlConstants.APPROVE_CHANGE_LATER%>",
                                data : "${_csrf.parameterName}=${_csrf.token}&clientId=" + clientId ,
                                success : function(data) {
                                    location.href = "<%=UrlConstants.CANCEL_REQUEST_LIST%>";
                                },
                                error : function(e) {
                                    alert("error in Approval");
                                }
                            });
           }else
           {
               var tag = '';
               var changeBy='driver';
               tag += '<form style="display:none;" method="POST" class="form-horizontal" action="<%=UrlConstants.CHANGE_REQUEST%>">'+
                   '<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />'+
                   '<input type="hidden" id="clientId" name="clientId" value="'+clientId+'" />'+
                   '<input type="hidden" id="flag" name="flag" value="'+changeBy+'" />'+
                   '<input type="submit" id="submit" />'+
                   '</form>';
               $("#dynamicForm").html(tag);
               $('#submit').trigger("click");
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
						<h2>Driver Cancel Request List</h2>
						<table class="table table-striped table-bordered table-hover"
							border="1" id="CancelList">
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
							<th>Status</th>
							<th class="text-center">Action Button</th>
						</tr>
					</thead>
							<tbody>
						<c:forEach items="${CancelList}" var="clientModel"
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
								<td data-raw-id="${clientModel.id},${clientModel.car.id},${clientModel.pickUpDateTime},${clientModel.dropDateTime},${clientModel.statusID}">${clientModel.statusID eq 5 ? 'Requested' : 'Cancel' }</td>
								<td width="${clientModel.statusID eq 2 || clientModel.statusID eq 3 ? '8%' : clientModel.statusID eq 4 ? '4%' : '16%'}" align="center" class="removeRowTd tanent-action">
									<%-- <c:if test="${clientModel.statusID eq 5}">
										<div class="pull-left">
											<a title="Approve" class="btn btn-success"
												href="<%=UrlConstants.APPROVE_DRIVER_REQUEST%>?tripId=${clientModel.tripId}">
											Approve</a>
										</div>
									</c:if>
									<c:if test="${clientModel.statusID eq 5}">
									<div class="pull-left">
										<a title="Reject" class="btn btn-danger"
										   href="<%=UrlConstants.REJECT_DRIVER_REQUEST%>?tripId=${clientModel.tripId}">
										Reject</a>
									</div>
									</c:if> --%>
									<button type="button" class="btn btn-success cancel-form-submit" data-toggle="modal" data-target="#myModal" data-row-id="${clientModel.id}" data-map-id="${clientModel.id}">
                                     Action</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>

		<div class="modal fade" id="myModal" role="dialog">
        			<div class="modal-dialog">
        				<!-- Modal content-->
        				<div class="modal-content">
        					<div class="modal-header">
        						<button type="button" class="close" data-dismiss="modal">&times;</button>
        						<h4 class="modal-title">Modal Header</h4>
        					</div>
        					<div class="modal-body">
        					<div class="portlet">

        						<div class="col-md-5">
        						 <input type="radio" name="driverrequest" value="changeNow" checked>Approve & Change Now<br>
                                 <input type="radio" name="driverrequest" value="changeLater">Approve & Change Later<br>
                                 <input type="radio" name="driverrequest" value="reject"> Reject  <br>
        						</div>
        						<input type="hidden" id="clientId" />
        						<div class="col-md-12">
        						</div>
        						<div class="col-md-4">
        							<input class="btn btn-success form-submit" type="submit"
        								value="Ok" />
        						<%--	 <input class="btn btn-cancel form-submit" type="submit"
                                                								value="Cancel" />--%>
        						</div>
        						</div>		</div>
        					<div class="modal-footer"></div>
							<div id="dynamicForm"></div>
        				</div>
        			</div>
        		</div>
	</div>
</div>