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
                    <h2>
                        <c:out value="${message}" ></c:out>
                    </h2>

                    <div class="col-md-6">
                        <a href="<%=UrlConstants.ADD_DEPARTMENT_TYPE%>" class="btn orange_bg">Create Department</a>
                    </div>

                    <div class="form-group">
                        <h2>Department Type List</h2>

                        <table class="table table-striped table-bordered table-hover"
                               id="tickets" border="1">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Department Name</th>
                                <th>Delete</th>


                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${ticketDepartmentTypes}" var="ticketDepartmentTypes"
                                       varStatus="loopcounter">
                                <tr>
                                    <td><c:out value="${ticketDepartmentTypes.id}" /></td>
                                    <td><c:out value="${ticketDepartmentTypes.departmentName}" /></td>

                                    <td align="center"><a class="glyphicon glyphicon-remove"
                                                          title="View" href="<%=UrlConstants.DELETE_DEPARTMENT_TYPE%>?id=${ticketDepartmentTypes.id}"></a></td>
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