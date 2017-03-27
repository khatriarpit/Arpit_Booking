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
                    <h4><c:out value="${message}" ></c:out></h4>

                    <!--If User wants to generate ticket -->
                    <div class="col-md-6">
                        <a href="<%=UrlConstants.SUPPORT_TIKCKET%>" class="btn orange_bg">Create Ticket</a>
                    </div>


                    <div class="form-group">
                        <h2>List Of Tickets</h2>
                        <table class="table table-striped table-bordered table-hover"
                               id="tickets" border="1">
                            <thead>
                            <tr>
                                <th>SR NO</th>
                                <th>Ticket ID</th>
                                <th>Topic</th>
                                <th>Department Name</th>
                                <th>Status</th>
                                <th>Created Date</th>
                                <th>View</th>

                            </tr>
                            </thead>
                            <tbody>
                            <c:set var="count" value="1" scope="page" />
                            <c:forEach items="${tickets}" var="ticket"
                                       varStatus="loopcounter">
                                <tr>
                                    <td><c:out value="${count}" /></td>
                                    <c:set var="count" value="${count + 1}" scope="page"/>
                                    <td><c:out value="${ticket.ticketId}" /></td>
                                    <td><c:out value="${ticket.topicName}" /></td>
                                    <td><c:out value="${ticket.departmentType.departmentName}" /></td>
                                    <td><c:choose>
                                            <c:when test="${ticket.status eq 1}" >
                                                OPEN
                                            </c:when>
                                            <c:when test="${ticket.status eq 3}" >
                                                Resolved
                                            </c:when>
                                            <c:otherwise>
                                                CLOSE
                                            </c:otherwise>
                                        </c:choose>
                                    </td>


                                    <td><c:out value="${ticket.createdDate}" /></td>
                                    <td align="center"><a class="glyphicon glyphicon-eye-open"
                                                          title="View" href="<%=UrlConstants.TICKET_DETAILS%>?ticketId=${ticket.id}"></a></td>
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