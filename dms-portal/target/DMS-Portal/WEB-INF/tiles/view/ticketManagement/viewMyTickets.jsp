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


                    <div class="form-group">
                        <h2>My Ticket Status</h2>
                        <table class="table table-striped table-bordered table-hover"
                               id="tickets" border="1">
                            <thead>
                            <tr>
                                <th>SR NO</th>
                                <th>Ticket ID</th>
                                <th>Topic</th>
                                <th>My Status</th>
                                <th>Ticket Current Status</th>
                                <th>Done By Me?</th>

                            </tr>
                            </thead>
                            <tbody>
                            <c:set var="count" value="1" scope="page" />
                            <c:forEach items="${tsh}" var="tsh"
                                       varStatus="loopcounter">
                                <tr>
                                    <td><c:out value="${count}" /></td>
                                    <c:set var="count" value="${count + 1}" scope="page"/>
                                    <td><c:out value="${tsh.ticket.ticketId}" /></td>
                                    <td><c:out value="${tsh.ticket.topicName}" /></td>
                                    <td><c:choose>
                                        <c:when test="${tsh.toStatus eq 1}" >
                                            OPEN
                                        </c:when>
                                        <c:when test="${tsh.toStatus eq 3}" >
                                            Resolved
                                        </c:when>
                                        <c:otherwise>
                                            CLOSE
                                        </c:otherwise>
                                    </c:choose>

                                    <td><c:choose>
                                        <c:when test="${tsh.ticket.status eq 1}" >
                                            OPEN
                                        </c:when>
                                        <c:when test="${tsh.ticket.status eq 3}" >
                                            Resolved
                                        </c:when>
                                        <c:otherwise>
                                            CLOSE
                                        </c:otherwise>
                                    </c:choose>
                                    </td>

                                    <td><c:choose>
                                        <c:when test="${tsh.ticket.updatedDate == tsh.updatedDate}" >
                                        YES
                                        </c:when>
                                        <c:when test="${tsh.ticket.updatedDate != tsh.updatedDate}" >
                                        NO
                                        </c:when>
                                    </c:choose>
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