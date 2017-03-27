<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
    $(document).ready(
        function() {
            $("#tickets").DataTable();

        });
</script>
<!--BEGIN CONTENT-->
<div class="page-content">
    <div class="row">
        <div class="col-lg-12">
            <div class="portlet">

                <hr>
                <div class="portlet-body">
                    <c:out value="${message}" ></c:out>
                    <div class="form-group">

                        <h2>Ticket Assignment History</h2>
                        <table class="table table-striped table-bordered table-hover"
                               id="tickets" border="1">
                            <thead>
                            <tr>
                                <th>SR No</th>
                                <th>Ticket ID</th>
                                <th>Topic Name</th>
                                <th>Assigned User</th>
                                <th>Assigned Date</th>
                                <th>view</th>

                            </tr>
                            </thead>
                            <tbody>
                            <c:set var="count" value="1" scope="page" />
                            <c:forEach items="${ticketAssignmentHistoryList}" var="ticketAssignmentHistoryList"
                                       varStatus="loopcounter">
                                <tr>
                                    <td><c:out value="${count}" /></td>
                                    <c:set var="count" value="${count + 1}" scope="page"/>
                                    <td><c:out value="${ticketAssignmentHistoryList.ticket.ticketId}" /></td>
                                    <td><c:out value="${ticketAssignmentHistoryList.ticket.topicName}" /></td>
                                    <td><c:out value="${ticketAssignmentHistoryList.assignedToUser.firstName}" /></td>
                                    <td><c:out value="${ticketAssignmentHistoryList.createdDate}" /></td>
                                    </td>
                                    <td align="center"><a class="glyphicon glyphicon-eye-open"
                                                          title="View" href="<%=UrlConstants.TICKET_DETAILS%>?ticketId=${ticketAssignmentHistoryList.ticket.id}"></a></td>
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