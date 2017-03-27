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
                <div class="portlet-body">
                    <div class="portlet-header">
                    </div>
                    <h4>${message}</h4>
                    <div class="portlet-body">
                        <div class="box col-md-6">
                            <div class="box-header">
                                <div class="caption box-caption">Generate Ticket</div>
                                <!--<div class="tools"><i class="fa fa-chevron-up"></i><i data-toggle="modal" data-target="#modal-config" class="fa fa-cog"></i><i class="fa fa-refresh"></i><i class="fa fa-times"></i></div>-->
                            </div>
                            <div class="box-body col-md-6">
                                <form:form method="POST" action="<%=UrlConstants.TICKET_COMMENT_SUPPORTER%>" class="form-horizontal"
                                           ondragstart="return false;" ondrop="return false;" modelAttribute="ticketComments">
                                <div class="form-body pal">

                                    <input type="hidden" name="${_csrf.parameterName}"
                                           value="${_csrf.token}" />

                                    <div class="form-group"><label for="inputAddress" class="col-md-3 control-label">Add Comment<span class="require">*</span></label>

                                        <div class="col-md-9">
                                            <div class="input-icon right">
                                                <form:textarea type="text" name="comment" path="comment"
                                                               value="" required = "true" class="form-control" placeholder="Enter Your Comment" />
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-md-9">
                                        <div class="input-icon right">
                                            <form:hidden name="ticket" path="ticket.id"
                                                         value="${ticket.id}"  />
                                            <input type="hidden" name="${_csrf.parameterName}"
                                                   value="${_csrf.token}" />
                                        </div>
                                    </div>



                                </div>



                                <div class="form-group">
                                    <div class="col-md-6 col-md-offset-3">
                                        <div class="input-icon"><button class="btn orange_bg" type="submit"> Submit Button</button>
                                        </div>
                                    </div>
                                    </form:form>
                                </div>


                                <!-- List all the ticket anc comments --->

                                <h3>${ticket.topicName}</h3>
                                <h3>${ticket.description}<h3/>
                                    <!--If logged in user is super admin, then display Assign button -->


                                    <!-- Display only if user logged in user is ticket manager -->
                                    <c:if test="${role==6}">
                                    <!-- Assign ticket to different user -->
                                    <h3>${ticket.assignedUser.username}</h3>

                                    <form:form method="POST" action="<%=UrlConstants.ASSIGN_TICKET%>" class="form-horizontal"
                                               ondragstart="return false;" ondrop="return false;" modelAttribute="ticket">
                                    <div class="form-body pal">

                                        <input type="hidden" name="${_csrf.parameterName}"
                                               value="${_csrf.token}" />
                                        <input type="hidden" name="${_csrf.parameterName}"
                                               value="${_csrf.token}" />
                                        <form:hidden name="ticket" path="id"
                                                     value="${ticket.id}"  />

                                        <form:select path = "assignedUser.id" class="form-control select2">
                                            <c:forEach var="usersToAssignTickets" items = "${usersToAssignTickets}">
                                                <form:option value="${usersToAssignTickets.id}">${usersToAssignTickets.username}</form:option>
                                            </c:forEach>
                                        </form:select>

                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-6 col-md-offset-3">
                                            <div class="input-icon"><button class="btn orange_bg" type="submit"> Assign</button>
                                            </div>
                                        </div>

                                    </div>


                                    </form:form>
                                    </c:if>
                                    <!-- ticket manager display ticket ends -->


                                    <!--Close Ticket Option -->

                                    <h3>Status : </h3> <h3>${ticket.status}</h3>

                                    <c:if test="${ticket.status==1}">
                                    <form:form method="POST" action="<%=UrlConstants.UPDATE_STATUS%>" class="form-horizontal"
                                               ondragstart="return false;" ondrop="return false;" modelAttribute="ticket">
                                    <div class="form-body pal">

                                        <input type="hidden" name="${_csrf.parameterName}"
                                               value="${_csrf.token}" />
                                        <input type="hidden" name="${_csrf.parameterName}"
                                               value="${_csrf.token}" />
                                        <form:hidden name="ticket" path="id"
                                                     value="${ticket.id}"  />




                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-6 col-md-offset-3">
                                            <div class="input-icon"><button class="btn orange_bg" type="submit"> Close</button>
                                            </div>
                                        </div>

                                    </div>


                                    </form:form>

                                    </c:if>
                                    <!-- Close Ticket Option Ends -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>